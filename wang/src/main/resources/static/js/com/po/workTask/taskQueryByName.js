var queryTaskByName={};
var taskId=document.getElementById("taskId").value;
var userId=document.getElementById("userId").value;
var taskSponsorId=document.getElementById("taskSponsorId").value;
var taskDirectorId=document.getElementById("taskDirectorId").value;
var taskStatus=document.getElementById("taskStatus").value;
var childSize=document.getElementById("childSize").value;
var queryTaskName=document.getElementById("queryTaskName").value;
var querySponsorId=document.getElementById("querySponsorId").value;
var queryDirectorId=document.getElementById("queryDirectorId").value;
var queryStartTime=document.getElementById("queryStartTime").value;
var queryEndTime=document.getElementById("queryEndTime").value;
var queryInfoStatus=document.getElementById("queryInfoStatus").value;
var tTaskName=document.getElementById("tTaskName").value;
var delayStatus=document.getElementById("delayStatus").value;
var isLeader=document.getElementById("isLeader").value;//领导批注权限限制
/**重复提交标识*/
queryTaskByName.subState=false;
/**获取单条信息*/
queryTaskByName.get = function (t) {
	if(t !=null && t!=""){
//		if(queryTaskByName.operate(t,'0')==0){//操作校验（查询）
//			msgBox.info({
//				type:"fail",
//				//content:$.i18n.prop("JC_OA_COMMON_012")
//				content:$.i18n.prop("JC_OA_COMMON_014")
//			});
//			return;
//		}
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/workTask/getTask.action?time=" + new Date(),
			data : {"id": t},
			dataType : "json",
			success : function(data) {
				if (data) {
					//清除验证信息
					hideErrorMessage();
					$("#workTaskForm").fill(data);
					//附件使用 start
					$("#businessId").val(data.id);
					$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
					clearDelAttachIds();//设置附件上传为逻辑删除
					$("#islogicDel").val("1");//附件使用 逻辑删除
					echoAttachList(false);
					clearTable();
					//附件使用 end
				}
			}
		});
	}
};
/**汇报详细列表*/
queryTaskByName.backTable = null;
queryTaskByName.getWorkTask=function(dire,t){
	queryTaskByName.backFnServerParams = function(aoData){
		 getTableParameters(queryTaskByName.backTable, aoData);
		 aoData.push({ "name": "taskId", "value": dire});	
		 aoData.push({ "name": "taskEventType", "value": t});	
	};
	//显示列信息
	queryTaskByName.backColumns = [
       	{mData: "rowNum"},
    	{mData: "userName"},
    	{mData: "createDate"},
    	{mData: "reportProc", mRender : function(mData, type, full){
			return mData+"%";
		}},
    	{mData: "reportContent"}
	 ];
	if (queryTaskByName.backTable == null) {
		queryTaskByName.backTable = $('#workTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTaskHistory/getTaskHistListByTaskId.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": queryTaskByName.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				queryTaskByName.backFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4]}]
		} );
	} else {
		queryTaskByName.backTable.fnDraw();
	}
};
/**任务事件查询*/
queryTaskByName.backTableForTE = null;
queryTaskByName.queryTaskEvent=function(){
	queryTaskByName.backFnServerParamsForTE = function(aoData){
		 getTableParameters(queryTaskByName.backTableForTE, aoData);
		 aoData.push({ "name": "taskId", "value": taskId});
	};
	//显示列信息
	queryTaskByName.backColumnsForTE = [
	   	{mData: "userName"},
	   	{mData: "createDate"},
	   	{mData: "taskEventType", mRender : function(mData, type, full){
	   		var taskEventName="";
	   		if(mData=='0'){
	   			taskEventName="布置任务";
	   		}else if(mData=='1'){
	   			taskEventName="修改任务";
	   		}else if(mData=='2'){
	   			taskEventName="删除任务";
	   		}else if(mData=='3'){
	   			taskEventName="接收任务";
	   		}else if(mData=='4'){
	   			taskEventName="不接收任务";
	   		}else if(mData=='5'){
	   			taskEventName="汇报任务";
	   		}else if(mData=='6'){
	   			taskEventName="催办任务";
	   		}else if(mData=='7'){
	   			taskEventName="延期审批通过";
	   		}else if(mData=='8'){
	   			taskEventName="取消任务";
	   		}else if(mData=='9'){
	   			taskEventName="完成任务";
	   		}else if(mData=='10'){
	   			taskEventName="批注任务";
	   		}else if(mData=='11'){
	   			taskEventName="任务超期";
	   		}else if(mData=='12'){
	   			taskEventName="延期申请";
	   		}else if(mData=='13'){
	   			taskEventName="延期审批未通过";
	   		}else if(mData=='14'){
	   			taskEventName="延期取消";
	   		}else if(mData=='15'){
	   			taskEventName="暂存";
	   		}
			return taskEventName;
		}},
	   	{mData: "content"},
	   	{mData: "content", mRender : function(mData, type, full){
	   		var returnVal="";
	   		if(full.taskEventType=='5'){//汇报
	   			returnVal=full.reportContent;
	   		}else{//其他
	   			returnVal=full.delayReason;
	   		}
	   		return returnVal;
	   	}}
	    ];
	if (queryTaskByName.backTableForTE == null) {
		queryTaskByName.backTableForTE = $('#taskEventTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTaskHistory/getTaskHistListByTaskId.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": queryTaskByName.backColumnsForTE,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				queryTaskByName.backFnServerParamsForTE(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4]}]
		} );
	} else {
		queryTaskByName.backTableForTE.fnDraw();
	}
};
/**返回*/
queryTaskByName.returnWorkList=function(){
	loadrightmenu('/po/workTask/queryTask.action?time=' + new Date());
};
/**接收任务--查询*/
queryTaskByName.getReceTask = function (id) {
//	getToken();token 性能优化
	hideErrorMessage();
	if(queryTaskByName.operate(id,'3')==0){//操作校验（接收）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$("#taskId2").val(id);
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#receWorkTaskForm").fill(data);
				//实际开始日期限制最大不能超过当前日期 start
				if(isIE()){
					var dd = new Date();
					var x = dd.toLocaleString();
					var yp = x.indexOf('年'),
					mp = x.indexOf('月'),
					dp = x.indexOf('日');
					var y = x.substr(0,yp),
					m = x.substr(yp + 1,mp - yp - 1),
					d = x.substr(mp + 1,dp - mp - 1);
					var o =[y,m,d];
					$("#nowDate").val(o.join('-'));
				}else{
					var dd = new Date();
					var x = dd.toLocaleString();
					var yp = x.substring(0, 4);
					mp = x.substring(5, 7);
					dp = x.substring(8, 10);
					var o =yp+'-'+mp+'-'+dp;
					$("#nowDate").val(o);
				}
				//实际开始日期限制最大不能超过当前日期 end
				//附件使用 start
				$("#businessId").val(data.id);
				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				clearTable();
				echoAttachListDul(false,'attachList_0');
				$('#isshow').val('1');//只允许附件下载
				//附件使用 end
				$('#reActStartTime').val("");
				$('#recrivingTask').modal('show');
			}
		}
	});
};
/**接收任务方法*/
queryTaskByName.receWorkTask = function (hide) {
	if (queryTaskByName.subState)return;
	queryTaskByName.subState = true;
	if ($("#receWorkTaskForm").valid()) {
		if(queryTaskByName.operate(taskId,'3')==0){//操作校验（接收）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#receWorkTaskForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		queryTaskByName.addFormParameters(formData);//
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				queryTaskByName.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#recrivingTask').modal('hide');
					}
					queryTaskByName.oprateSuccessCallBack(taskId);
				} else {
					if(data.labelErrorMessage){
						showErrors("receWorkTaskForm", data.labelErrorMessage);
					} else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				queryTaskByName.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		queryTaskByName.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//操作成功返回--非任务汇报进度为100%（或任务状态非完成）
queryTaskByName.oprateSuccessCallBack=function(taskId){
	msgBox.tip({
		type:"success",
		content:$.i18n.prop("JC_SYS_054"),
		callback:function(){
			loadrightmenu('/po/workTask/getWorkTaskInfo.action?id='+taskId+'&time=' + new Date());
		}
	});
	
};
/**不接收任务-赋值*/
queryTaskByName.getNotRrce = function (id) {
	hideErrorMessage();
	if(queryTaskByName.operate(id,'4')==0){//操作校验（不接收）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#notRrce").fill(data);
				$("#noReTaskId").val(id);
				$("#noReEventTitle").val(data.taskName);
				$("#noReContent").val(data.content);
				$('#noReTaskReason').val("");
				//附件使用 start
				$("#businessId").val(data.id);
				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				clearTable();
				echoAttachListDul(false,'attachList_1');
				$('#isshow').val('1');//只允许附件下载
				//附件使用 end
				$('#no_task').modal('show');
			}
		}
	});
};	
/**不接收任务-保存*/
queryTaskByName.notRrce = function (hide) {
	if (queryTaskByName.subState)return;
	queryTaskByName.subState = true;
	if ($("#notRrce").valid()) {
		if(queryTaskByName.operate(taskId,'4')==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#notRrce").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		queryTaskByName.addFormParameters(formData);//
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				queryTaskByName.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#no_task').modal('hide');
					}
					$("#noReTaskReason").val("");
					queryTaskByName.oprateSuccessCallBack(taskId);
				} else {
					if(data.labelErrorMessage){
						showErrors("notRrce", data.labelErrorMessage);
					}  else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				queryTaskByName.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		queryTaskByName.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
/**汇报-赋值*/
queryTaskByName.getReport = function (id) {
	hideErrorMessage();
	if(queryTaskByName.operate(id,'5')==0){//操作校验（汇报）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#reportProc").val(data.taskProc);
				$("#reEventTitle").val(data.taskName);
				$("#reContent").val(data.content);
				if(data.taskProc >=100){
					$('#reportProc').attr("readonly","readonly");
					$('#reportContent').attr("readonly","readonly");
				}else{
					$('#reportProc').removeAttr("readonly");
					$('#reportContent').removeAttr("readonly");
				}
				$("#reportContent").val("");
			}
		}
	});
	$("#reportTaskId").val(id);
	$('#reportWindow').modal('show');
};
/**汇报-保存*/
queryTaskByName.reportWorkTask = function (hide) {
	if (queryTaskByName.subState)return;
	queryTaskByName.subState = true;
	if ($("#reportForm").valid()) {
		if(queryTaskByName.operate(taskId,'5')==0){//操作校验（汇报）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#reportForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		queryTaskByName.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache:false,
			async:false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				queryTaskByName.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#reportWindow').modal('hide');
					}
					$('#reportProc').val("");
					$('#reportContent').val("");
					queryTaskByName.oprateSuccessCallBack(taskId);
				} else {
					if(data.labelErrorMessage){
						showErrors("reportForm", data.labelErrorMessage);
					} else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				queryTaskByName.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		queryTaskByName.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
/**催办-查询*/
queryTaskByName.getReminders = function (id) {
	hideErrorMessage();
	if(queryTaskByName.operate(id,'6')==0){//操作校验（催办）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#remindEventTitle").val(data.taskName);
				$("#remindContent").val(data.content);
			}
		}
	});
	$("#remindersTaskId").val(id);
	$("#remindersCo").val("");
	$('#reminders').modal('show');
};
/**催办-保存*/
queryTaskByName.remindersWorkTask = function (hide) {
	if (queryTaskByName.subState)return;
	queryTaskByName.subState = true;
	if ($("#remindersForm").valid()) {
		if(queryTaskByName.operate(taskId,'6')==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#remindersForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		queryTaskByName.addFormParameters(formData);//
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				queryTaskByName.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#reminders').modal('hide');
					}
					$("#remindersCo").val("");
					queryTaskByName.oprateSuccessCallBack(taskId);
				} else {
					if(data.labelErrorMessage){
						showErrors("remindersForm", data.labelErrorMessage);
					}else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				queryTaskByName.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		queryTaskByName.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//延期-申请-查询
queryTaskByName.getDelayTask = function (id) {
	hideErrorMessage();
	if(queryTaskByName.operate(id,'12')==0){//操作校验（延期申请）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#delayTaskId").val(id);
				$("#delayTaskEventTitle").val(data.taskName);
				$("#delayContent").val(data.content);
				$("#delayTtartTime").val(data.startTime);
				$("#delayEndTime").val(data.endTime);
			}
		}
	});
	$('#delayDays').val("");
	$('#delayEnddate').val("");
	$('#delayReasons').val("");
	$('#delayApply').modal('show');
};
//延期-审批-查询
queryTaskByName.getDelayCheckTask = function (id) {
//	getToken();token 性能优化
	hideErrorMessage();
	if(queryTaskByName.operate(id,'7')==0){//操作校验（延期审批）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#delayCheckTaskId").val(id);
				$("#delayCheckTaskEventTitle").val(data.taskName);
				$("#delayCheckContent").val(data.content);
				$("#delayCheckTtartTime").val(data.startTime);
				$("#delayCheckEndTime").val(data.endTime);
			}
		}
	});
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTaskHistory/get.action?time=" + new Date(),
		data : {"taskId": id,"taskEventType":"12"},
		dataType : "json",
		success : function(data) {
			if (data) {
				$('#delayCheckEnddate').val(data.delayEnddate);
				$('#delayCheckReasons').val(data.delayReason);
			}
		}
	});
	$('#delay').modal('show');
};
//延期-取消-查询
queryTaskByName.getDelayCancelTask = function (id) {
	hideErrorMessage();
	if(queryTaskByName.operate(id,'14')==0){//操作校验（延期取消）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#delayCancelTaskId").val(id);
				$("#delayCancelTaskEventTitle").val(data.taskName);
				$("#delayCancelContent").val(data.content);
				$("#delayCancelTtartTime").val(data.startTime);
				$("#delayCancelEndTime").val(data.endTime);
			}
		}
	});
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTaskHistory/get.action?time=" + new Date(),
		data : {"taskId": id,"taskEventType":"12"},
		dataType : "json",
		success : function(data) {
			if (data) {
				$('#delayCancelEnddate').val(data.delayEnddate);
				$('#delayCancelReasons').val(data.delayReason);
			}
		}
	});
	$('#delayCancel').modal('show');
};
//延期-审批未通过
queryTaskByName.delayChUnPa= function () {
	$("#delayCheckTaskEventType").val("13");
	queryTaskByName.delayWorkTask(true,1);
};
//延期-审批通过
queryTaskByName.delayChPa= function () {
	$("#delayCheckTaskEventType").val("7");
	queryTaskByName.delayWorkTask(true,1);
};
//延期-申请、取消
queryTaskByName.delayWorkTask = function (hide,cType) {
	if (queryTaskByName.subState)return;
	queryTaskByName.subState = true;
	var cid='';//关闭DIV层
	var formId='';
	var opType=0;//功能类别
	if(cType==1){//审批
		cid='#delay';
		formId='delayCheckForm';
		opType=7;
	}else if(cType==2){//取消
		cid='#delayCancel';
		formId='delayCancelForm';
		opType=14;
	}else{//申请
		cid='#delayApply';
		formId='delayForm';
		opType=12;
	}
	if ($("#"+formId).valid()) {
		if(queryTaskByName.operate(taskId,opType)==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#"+formId).serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		queryTaskByName.addFormParameters(formData);//
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				queryTaskByName.subState = false;
				if(data.success == "true"){
					if (hide) {
						$(cid).modal('hide');
					}
					if(cType==0){
						$("#delayDays").val("");
						$("#delayEnddate").val("");
						$("#delayReasons").val("");
					}
					queryTaskByName.oprateSuccessCallBack(taskId);
				} else {
					if(data.labelErrorMessage){
						showErrors(formId, data.labelErrorMessage);
					} else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				queryTaskByName.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		queryTaskByName.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
/**日期加上天数得到新的日期 dateTemp 需要参加计算的日期，days要添加的天数，返回新的日期，日期格式：YYYY-MM-DD*/
queryTaskByName.getNewDay= function(dateTemp, days) {
  if(days <0){
	  return '天数不能小于0';
  }
  var dateTemp = dateTemp.split("-");
  var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式  
  var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);
  var rDate = new Date(millSeconds);
  var year = rDate.getFullYear();
  var month = rDate.getMonth() + 1;
  if (month < 10) month = "0" + month;
  var date = rDate.getDate();
  if (date < 10) date = "0" + date;
  return (year + "-" + month + "-" + date);
};
/**取消-查询*/
queryTaskByName.getCancel = function (id) {
	hideErrorMessage();
	if(queryTaskByName.operate(id,'8')==0){//操作校验（取消）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//token性能优化
				$("#cancelEventTitle").val(data.taskName);
				$("#cancelContent").val(data.content);
			}
		}
	});
	$("#cancelTaskId").val(id);
	$("#cancelReason").val("");
	$('#cancel').modal('show');
};
/**取消-保存*/
queryTaskByName.cancelWorkTask = function (hide) {
	if (queryTaskByName.subState)return;
	queryTaskByName.subState = true;
	if ($("#cancelForm").valid()) {
		if(queryTaskByName.operate(taskId,'8')==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#cancelForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		queryTaskByName.addFormParameters(formData);//
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				queryTaskByName.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#cancel').modal('hide');
					}
					queryTaskByName.oprateSuccessCallBack(taskId);
				} else {
					if(data.labelErrorMessage){
						showErrors("cancelForm", data.labelErrorMessage);
					} else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				queryTaskByName.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		queryTaskByName.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
/**按钮隐藏、显示逻辑判断*/
queryTaskByName.buttionShow=function(){
	if(null !=taskStatus && taskStatus==0){//未接收
		if(userId==taskSponsorId && userId!=taskDirectorId){//当前登录人为任务发起人
			//暂时注销掉
//			$("#returnTask").hide();
//			$("#returnTemp").show();
		}else if(userId==taskDirectorId && userId!=taskSponsorId){//当前登录人为任务负责人
			$("#receTask").show();
			$("#notReceTask").show();
		}else if(userId==taskDirectorId && userId==taskSponsorId){//当前登录人即为任务负责人，又为任务发起人
			$("#receTask").show();
			$("#notReceTask").show();
//			$("#returnTemp").hide();
//			$("#returnTask").show();
		}else{
//			$("#returnTask").hide();
//			$("#returnTemp").show();
		}
	}
	if(null !=taskStatus && (taskStatus==1 || taskStatus==2 || taskStatus==6)){//1-进行中;2-延期;6-超期;
		if(userId==taskSponsorId && userId!=taskDirectorId){//当前登录人为任务发起人
			$("#remindersTask").show();
			$("#cancelTask").show();
			if(delayStatus=='0'){
				$("#extensionTaskCheck").show();
			}
		}
		if(userId==taskDirectorId && userId!=taskSponsorId){//当前登录人为任务负责人
			if(childSize==0){
				$("#reportTask").show();
			}else{
//				$("#returnTask").hide();
//				$("#returnTemp").show();
			}
			 if(delayStatus =='0'){
				 if(childSize==0){
					 $("#extensionTaskCancel").show();
				 }else{
					 $("#extensionTaskCancelNew").show();
				 }		
//				 $("#returnTemp").hide();
//				 $("#returnTask").show();
			 }else{
				 if(childSize==0){
					 $("#extensionTaskApply").show();
				 }else{
					 $("#extensionTaskApplyNew").show();
				 }
//				 $("#returnTemp").hide();
//				 $("#returnTask").show();
			 }
		}
		if(userId==taskDirectorId && userId==taskSponsorId){//当前登录人即为任务负责人，又为任务发起人
			$("#remindersTask").show();
			if(childSize==0){
				$("#reportTaskNew").show();
			}
			$("#cancelTask").show();
//			$("#returnTemp").hide();
//			$("#returnTask").show();
			if(delayStatus =='0'){
				 $("#extensionTaskCheck").show();
				 $("#extensionTaskCancel").show();
			 }else{
				 $("#extensionTaskApply").show();
			 }
		}
	}
	if(null !=taskStatus && (taskStatus==3 || taskStatus==4 || taskStatus==5 || taskStatus==7)){//3-完成;4-取消;5-暂存;7-拒接收
//		$("#returnTask").hide();
//		$("#returnTemp").show();
	}
};
//超期提醒设置预览--接收任务
queryTaskByName.showRemindSetForJC=function(){
	var t=$('#receWorkTaskForm input[name="remindType"]:checked').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#jcOptionsRadios2').val(s);//提醒方式
	var directorId=$('#reDirector').val();
	var prticipantsId=$('#rePrticipants').val();
	if(t!=0){
		$('#jcRemindCon').val($('#reTaskName').val());//提醒内容
		$('#jcRemindPerName').val(directorId+','+prticipantsId);
	}else{
		$('#jcRemindCon').val("");
		$('#jcRemindPerName').val("");
	}
	$('#remindWindowForRe').modal('show');
};
//超期提醒设置预览--不接收任务
queryTaskByName.showRemindSetForNJ=function(){
	var t=$('#notRrce input[name="remindType"]:checked').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#njOptionsRadios2').val(s);//提醒方式
	var directorId=$('#noReDirector').val();
	var prticipantsId=$('#noRePrticipants').val();
	if(t!=0){
		$('#njRemindCon').val($('#noReTaskName').val());//提醒内容
		$('#njRemindPerName').val(directorId+','+prticipantsId);
	}else{
		$('#njRemindCon').val("");
		$('#njRemindPerName').val("");
	}
	$('#remindWindowForNR').modal('show');
};
//添加附件
queryTaskByName.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name":"fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};
//点击关闭附件弹出层是清空内容
queryTaskByName.fileupload = function (){
	$("#businessId").val("0");
	showAttachList(false);
};
//点击关闭时删除一上传文件
queryTaskByName.deleteAttach = function() {
	if($("#fileids").val().length > 0 && $("#fileid_old").val() != $("#fileids").val()){
		var ids = $("#fileids").val();
		$.ajax({
			type : "POST",
			url : getRootPath()+"/content/attach/delete.action",
			data : {"ids": ids},
			dataType : "json",
			success : function(data) {
				carInfo.carInfoList();
			}
		});
	}
};
//提醒设置
var para_week='';
queryTaskByName.showRemind= function(){
	$(document).showRemind_({tableName:'1',readonly:true,remind:$('#remind').val(),callback:function(r) {para_week = r;
		$("#remind").val(r);
	}});
};
//提醒设置(不接收任务)
queryTaskByName.showRemindForNotRe= function(){
	$(document).showRemind_({tableName:'1',readonly:true,remind:$("#notReRemind").val(),callback:function(r) {para_week = r;
		$("#notReRemind").val(r);
	}});
};
//提醒设置(接收任务)
queryTaskByName.showRemindForRe= function(){
	$(document).showRemind_({tableName:'1',readonly:true,remind:$("#reRemind").val(),callback:function(r) {para_week = r;
		$("#reRemind").val(r);
	}});
};
//批注按钮显示
queryTaskByName.isLeader = function (){
//	if(isLeader=='false'){
//		$('#leaderIdeaForm').hide();
//	}
};
//内容、标准样式
queryTaskByName.autoValue=function(){
    var co=$('#hiContent').val();
    if(co !=null && co !=''){
    	 co=queryTaskByName.enter2Br(co);
    	 co=queryTaskByName.br2Enter(co);
         $('#showContent').html(co);
    }
	var st=$('#hiStandard').val();
	if(st !=null && st !=''){
		st=queryTaskByName.enter2Br(st);
		st=queryTaskByName.br2Enter(st);
		$('#showStandard').html(st);
	}
};
//折行
queryTaskByName.enter2Br = function (str){
	return str.replace(/\n/g,'<br/>').replace(/\s/g,"&nbsp;");
};
//回车
queryTaskByName.br2Enter = function (str){
    return str.replace(/<br>/ig,"\r\n").replace(/(&nbsp;)/g," ");
};
//用于批注保存时，区分任务状态
queryTaskByName.checkAnnoSave=function(){
	annoManage.saveLeaderIdeaForm(taskStatus);
};
//用于批注回复时，区分任务状态
queryTaskByName.checkAnnoReply=function(p){
	annoManage.commentSend(p,taskStatus);
};

//操作事件校验
queryTaskByName.operate=function(taskId,operateType){
	var reVal=0;
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/workTask/checkOperation.action?time=" + new Date(),
		data : {"taskId": taskId,"operateType":operateType},
		dataType : "json",
        async : false,
		success : function(data) {
			queryTaskByName.subState=false;
			if(data>0){
				reVal=1;
			}
		},
		error : function() {
			queryTaskByName.subState = false;
		}
	});
	return reVal;
};
/**初始化*/
jQuery(function($)
{
	//获取单条信息
	queryTaskByName.get(taskId);
	//汇报详细列表
	queryTaskByName.getWorkTask(taskId,'5');
	//任务汇报
	$("#reportSure").click(function(){queryTaskByName.reportWorkTask(true);});
	//任务催办
	$("#remindersSure").click(function(){queryTaskByName.remindersWorkTask(true);});
	//延期申请
	$("#delaySure").click(function(){queryTaskByName.delayWorkTask(true,0);});
	//延期审批通过
	$("#delayPass").click(function(){queryTaskByName.delayChPa();});
	//延期审批未通过
	$("#delayNotPass").click(function(){queryTaskByName.delayChUnPa();});
	//延期取消
	$("#delayCanSure").click(function(){queryTaskByName.delayWorkTask(true,2);});
	//延期天数
	$("#delayDays").change(function(){
		    var delayEndTemp=document.getElementById("delayEndTime").value;
		    var delayDaysTemp=document.getElementById("delayDays").value;
		    var newDate=queryTaskByName.getNewDay(delayEndTemp,delayDaysTemp);
		    $("#delayEnddate").val(newDate);
	});
	//任务取消
	$("#cancelSure").click(function(){queryTaskByName.cancelWorkTask(true);});
	//接收任务
	$("#receWorkTask").click(function(){queryTaskByName.receWorkTask(true);});
	//不接收任务
	$("#notAccepted").click(function(){queryTaskByName.notRrce(true);});
	//按钮隐藏、显示逻辑判断
	queryTaskByName.buttionShow();
	//查询批注
	if(isLeader=='true'){
		annoManage.initAnno(taskId);
	}else{
		annoManage.initAnnoReadOnly(taskId);
	}
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){
		queryTaskByName.checkAnnoSave();
	});
	//绑定批注"回复"
	$('#comment').on('click','a[name="reply"]',function(){
		annoManage.commentReply(this);
	});
	//绑定批注"发送"
	$('#comment').on('click','a[name="send"]',function(){
		queryTaskByName.checkAnnoReply(this);
	});
	//绑定批注内容清除
	$("#clearleaderIdea").click(function(){
		annoManage.clearleaderIdea();
	});
	//隐藏错误信息
	hideErrorMessage();
	//提醒按钮事件绑定
	$("#remindSet").click(function(){queryTaskByName.showRemind();});
	//附件使用
	$("#closebtn").click(function(){queryTaskByName.fileupload();});
	$("#closeModalBtn").click(function(){queryTaskByName.fileupload();});
	$("#queryattach").click(function(){queryTaskByName.fileupload();});
	//提醒按钮事件绑定(不接收任务)
	$("#notReRemindSet").click(function(){queryTaskByName.showRemindForNotRe();});
	//提醒按钮事件绑定(接收任务)
	$("#reRemindSet").click(function(){queryTaskByName.showRemindForRe();});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//批注按钮显示
	queryTaskByName.isLeader();
	//内容、标准样式
	queryTaskByName.autoValue();
});