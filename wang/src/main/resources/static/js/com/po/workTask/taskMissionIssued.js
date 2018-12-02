var mission = {};
var currentUserId=document.getElementById("currentUserId").value;
var parentIdT=document.getElementById("parentId").value;
var subTotalCount=document.getElementById("subTotalCount").value;
var subTotalCountNew=document.getElementById("subTotalCountNew").value;
var subTaskProc=document.getElementById("subTaskProc").value;
mission.pageRows = null;
mission.pageRowsNext = null;
//重复提交标识
mission.subState = false;
var isSave=0;//用于超期提醒设置预览
var parentId="";
//初始化申请部门选择树
selectControl.init("applyDeptTree","applyDeptid-applyDeptid", false, false);
//人员选择树--负责人
selectControl.init("showDirectorIdTree","director-directorId", false, true);
//人员选择树--参与人
selectControl.init("prticipantsTree","prticipantsName-prticipantsId", true, true);
//人员选择树--申请人
selectControl.init("showapplyNameTree","senderText-applyId", false, true);
//批量删除
mission.batchDelete = function(){
	var tName=document.getElementsByName("checkNum");
	parentId=document.getElementById("parentId").value;
	var checkedId="";
	var notChecked=0;
	for(var i=0;i<tName.length;i++){
		if(tName[i].checked){
			checkedId+=tName[i].value+",";
		}else{
			notChecked++;
		}
	}
	if(notChecked==tName.length){
		msgBox.info({
			type:"fail",
			content: $.i18n.prop("JC_SYS_061")			
		});
		return false;
	}
	if (mission.subState)return;
	mission.subState = true;
	//删除确认
	var opt={
			content:$.i18n.prop("JC_SYS_034"),
			success:function(){
				$('#dataLoad').fadeIn();
				var url = getRootPath()+"/po/workTask/deleteByIds.action?time=" + new Date();
				jQuery.ajax({
				url : url,
				type : 'POST',
				data :{ids:checkedId},
				success : function(data) {
					$('#dataLoad').fadeOut();
					mission.subState = false;
					if(data==1){
						msgBox.tip({
							type:"success",
							content:$.i18n.prop("JC_SYS_005"),
							callback:mission.setBusinessId(parentIdT)
						});
					}
				},
				error : function() {
					$('#dataLoad').fadeOut();
					mission.subState = false;
					msgBox.tip({
						type:"fail",
						content: $.i18n.prop("JC_SYS_006")
					});
				}
			});
			},
			cancel:function(){
				mission.subState = false;
			}
		}
		msgBox.confirm(opt);
};
//清空表单
mission.clearForm = function () {
	$('#newTaskForm')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("sponsor-sponsorId");
	selectControl.clearValue("director-directorId");
	selectControl.clearValue("prticipantsName-prticipantsId");
};
//添加成功清空表单数据
mission.initForm = function(){
	mission.clearForm();
};
//添加
mission.saveNewTask = function (sta) {
	if (mission.subState)return;
	para_week = '';
	mission.subState = true;
	if(sta!=null && sta!=''){
		$('#status').val(sta);
	}
	if ($("#newTaskForm").valid()) {
		if(mission.compareStaAndEnd()==1){//汇报时限天数校验
			msgBox.info({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_044")
			});
			mission.subState = false;
			return;
		}
		if(mission.operate(parentIdT,'15')==0){//操作校验（下发任务）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		mission.remindDefault();
		if(mission.checkParentDate()==0){
			mission.subState = false;
			msgBox.tip({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_026")
			});
			return;
		}
		var remindTemp=mission.isRemind();
		if(sta=='8'){//暂存任务不校验超期
			remindTemp==true;
		}
		if(remindTemp==false){
			var opt={
			    content: $.i18n.prop("JC_OA_PO_034"),
				success: function(){
					if(subTotalCountNew==0 && subTaskProc !=0){
						var opt={
//								content: $.i18n.prop("JC_OA_PO_027"),
								content: $.i18n.prop("JC_OA_PO_055"),
								success:function(){
									$('#dataLoad').fadeIn();
									var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
									var formData = $("#newTaskForm").serializeArray();
									formData.push({"name": "remindContent", "value": $("#remindContent").val()});
									formData.push({"name": "remindPerId", "value": $("#remindPerIdNew").val()});
									mission.addFormParameters(formData);
									jQuery.ajax({
										url : url,
										type : 'POST',
										cache: false,
										contentType: "application/json;charset=UTF-8",
										data : JSON.stringify(serializeJson(formData),replaceJsonNull),
										success : function(data) {
											$('#token').val(data.token);
											$('#dataLoad').fadeOut();
											mission.subState = false;
											if(data.success == "true"){
												if ($("#id").val() == 0) {
													mission.initForm();
												}
												$('#new-sub-task').modal('hide');
												msgBox.tip({
													type:"success",
													content: data.successMessage,
													callback:mission.setBusinessId(parentIdT)
												});
											} else {
												if(data.labelErrorMessage){
													msgBox.info({
														type:"fail",
														content: data.labelErrorMessage
													});
												} else{
													msgBox.info({
														type:"fail",
														content: data.errorMessage
													});
												}
											}
										},
										error : function() {
											$('#dataLoad').fadeOut();
											mission.subState = false;
											msgBox.info({
												type:"fail",
												content: data.errorMessage
											});
										}
									});
								},
								cancel:function(){
									mission.subState =false;
								}
							}
							msgBox.confirm(opt);
					}else{
						$('#dataLoad').fadeIn();
						var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
						var formData = $("#newTaskForm").serializeArray();
						formData.push({"name": "remindContent", "value": $("#remindContent").val()});
						formData.push({"name": "remindPerId", "value": $("#remindPerIdNew").val()});
						mission.addFormParameters(formData);
						jQuery.ajax({
							url : url,
							type : 'POST',
							cache: false,
							contentType: "application/json;charset=UTF-8",
							data : JSON.stringify(serializeJson(formData),replaceJsonNull),
							success : function(data) {
								$('#token').val(data.token);
								$('#dataLoad').fadeOut();
								mission.subState = false;
								if(data.success == "true"){
									if ($("#id").val() == 0) {
										mission.initForm();
									}
									$('#new-sub-task').modal('hide');
									msgBox.tip({
										type:"success",
										content: data.successMessage,
										callback:mission.setBusinessId(parentIdT)
									});
								} else {
									if(data.labelErrorMessage){
										msgBox.info({
											type:"fail",
											content: data.labelErrorMessage
										});
									} else{
										msgBox.info({
											type:"fail",
											content: data.errorMessage
										});
									}
								}
							},
							error : function() {
								$('#dataLoad').fadeOut();
								mission.subState = false;
								msgBox.info({
									type:"fail",
									content: data.errorMessage
								});
							}
						});
					}
				},
				cancel:function(){
					mission.subState = false;
				}
			}
			msgBox.confirm(opt);
		}else{
			if(subTotalCountNew==0 && subTaskProc !=0 && sta!='8'){//下发暂存任务，上级任务进度不清零
				var opt={
//						content: $.i18n.prop("JC_OA_PO_027"),
						content: $.i18n.prop("JC_OA_PO_055"),
						success:function(){
							$('#dataLoad').fadeIn();
							var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
							var formData = $("#newTaskForm").serializeArray();
							formData.push({"name": "remindContent", "value": $("#remindContent").val()});
							formData.push({"name": "remindPerId", "value": $("#remindPerIdNew").val()});
							mission.addFormParameters(formData);
							jQuery.ajax({
								url : url,
								type : 'POST',
								cache: false,
								contentType: "application/json;charset=UTF-8",
								data : JSON.stringify(serializeJson(formData),replaceJsonNull),
								success : function(data) {
									$('#token').val(data.token);
									$('#dataLoad').fadeOut();
									mission.subState = false;
									if(data.success == "true"){
										if ($("#id").val() == 0) {
											mission.initForm();
										}
										$('#new-sub-task').modal('hide');
										msgBox.tip({
											type:"success",
											content: data.successMessage,
											callback:mission.setBusinessId(parentIdT)
										});
									} else {
										if(data.labelErrorMessage){
											msgBox.info({
												type:"fail",
												content: data.labelErrorMessage
											});
										} else{
											msgBox.info({
												type:"fail",
												content: data.errorMessage
											});
										}
									}
								},
								error : function() {
									$('#dataLoad').fadeOut();
									mission.subState = false;
									msgBox.info({
										type:"fail",
										content: data.errorMessage
									});
								}
							});
						},
						cancel:function(){
							mission.subState =false;
						}
					}
					msgBox.confirm(opt);
			}else{
				$('#dataLoad').fadeIn();
				var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
				var formData = $("#newTaskForm").serializeArray();
				formData.push({"name": "remindContent", "value": $("#remindContent").val()});
				formData.push({"name": "remindPerId", "value": $("#remindPerIdNew").val()});
				mission.addFormParameters(formData);
				jQuery.ajax({
					url : url,
					type : 'POST',
					cache: false,
					contentType: "application/json;charset=UTF-8",
					data : JSON.stringify(serializeJson(formData),replaceJsonNull),
					success : function(data) {
						$('#token').val(data.token);
						$('#dataLoad').fadeOut();
						mission.subState = false;
						if(data.success == "true"){
							if ($("#id").val() == 0) {
								mission.initForm();
							}
							$('#new-sub-task').modal('hide');
							msgBox.tip({
								type:"success",
								content: data.successMessage,
								callback:mission.setBusinessId(parentIdT)
							});
						} else {
							if(data.labelErrorMessage){
								msgBox.info({
									type:"fail",
									content: data.labelErrorMessage
								});
							} else{
								msgBox.info({
									type:"fail",
									content: data.errorMessage
								});
							}
						}
					},
					error : function() {
						$('#dataLoad').fadeOut();
						mission.subState = false;
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				});
			}
		}
	} else {
		mission.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//暂存
mission.backTableZCTask = null;
mission.getWorkTaskZCTask=function(){
	mission.backFnServerParamsZCTask = function(aoData){
		 getTableParameters(mission.backTableZCTask, aoData);
		 aoData.push({ "name": "sponsorId", "value": currentUserId});//暂存任务查询发起人为当前登录人
		 aoData.push({ "name": "status", "value": "8"});
		 aoData.push({ "name": "isTemplet", "value": "0"});
		 aoData.push({ "name": "parentTaskid", "value": parentIdT});
	};
	//显示列信息
	mission.backColumnsZCTask = [
	    {mData: "taskName",mRender : function(mData, type, full) {
	    	return "<input type=\"radio\" name=\"optionsRadiosZc\"  onclick=\"mission.assignment('"+full.id+"','8')\" />";
		}},            
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
			return "<a href=\"#\" onclick=\"mission.deleteWorkTaskNew('"+full.id+"')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (mission.backTableZCTask == null) {
		mission.backTableZCTask = $('#taskTempTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": mission.backColumnsZCTask,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				mission.backFnServerParamsZCTask(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		mission.backTableZCTask.fnDraw();
	}
};
//已发任务
mission.backTable = null;
mission.getWorkTask=function(){
	mission.backFnServerParams = function(aoData){
		 getTableParameters(mission.backTable, aoData);
		 aoData.push({ "name": "createUser", "value": currentUserId});//任务模板查询创建人为当前登录人
		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	mission.backColumns = [
	    {mData: "taskName",mRender : function(mData, type, full) {
	    	if(full.status=='3'){
	    		return "<input type=\"radio\" name=\"optionsRadios\"  onclick=\"mission.assignment('"+full.taskId+"','')\" />";
	    	}else{
	    		return "<input type=\"radio\" name=\"optionsRadios\"  onclick=\"mission.assignment('"+full.id+"','')\" />";
	    	}
	    }},           
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
       		return "<a href=\"#\" onclick=\"mission.deleteWorkTask('"+full.id+"','1')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (mission.backTable == null) {
		mission.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": mission.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				mission.backFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		mission.backTable.fnDraw();
	}
};
//暂存任务
mission.backTableZC = null;
mission.getWorkTaskZC=function(){
	mission.backFnServerParamsZC = function(aoData){
		 getTableParameters(mission.backTableZC, aoData);
		 aoData.push({ "name": "createUser", "value": currentUserId});//任务模板查询创建人为当前登录人
		 aoData.push({ "name": "status", "value": "5"});
		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	mission.backColumnsZC = [
	    {mData: "taskName",mRender : function(mData, type, full) {
	    	return "<input type=\"radio\" name=\"optionsRadiosZc\"  onclick=\"mission.assignment('"+full.id+"','5')\" />";
		}},            
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
			return "<a href=\"#\" onclick=\"mission.deleteWorkTask('"+full.id+"','0')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (mission.backTableZC == null) {
		mission.backTableZC = $('#workTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": mission.backColumnsZC,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				mission.backFnServerParamsZC(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		mission.backTableZC.fnDraw();
	}
};
//提取任务模板中任务名称
mission.assignment=function(t,s){
	$("#taskNameVal").val(t);
	if(s!=null && s!=""){
		$("#taskStatus").val(s);
	}else{
		$("#taskStatus").val("");
	}
};
//提取任务模板中任务名称
mission.getTaskName=function (){
	var taskIdTemp=$("#taskNameVal").val();
	if(taskIdTemp==null || taskIdTemp==''){
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_029","要提取的任务")//任务模板
		});
		return false;
	}
	var taskSta=$("#taskStatus").val();
	if(null!=taskIdTemp){
		var dataTemp;
		if(taskSta=='5'){
			dataTemp={"id": taskIdTemp,"status":'5',"isTemplet":'0'};//添加是否取消模板
		}else{
			dataTemp={"id": taskIdTemp};
		}
		$("#taskStatus").val("");
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/workTask/getAll.action?time=" + new Date(),
			data : dataTemp,
			dataType : "json",
			success : function(data) {
				if (data) {
					$("#taskName").val(data.taskName);
					$("#taskWorkType").val(data.taskWorkType);
					if(data.taskImpCode !=null && data.taskImpCode !=''){
						$('#taskImpCode').val(data.taskImpCode);
					}else{
						$('#taskImpCode').val('');
					}
//					$("#taskOrigin").val(data.taskOrigin);
					//人员选择树--发起人
					$("#sponsor-sponsorId").select2("data", {id:data.sponsorId,text:data.sponsor});		
					//人员选择树--负责人
					$("#director-directorId").select2("data", {id:data.directorId,text:data.director});	
					//人员选择树--参与人
					var prtTemp= new Array();//ID
					var prtValueTemp= new Array();//NAME
					var objs = "[";
					var prtIdValue="";
					if(data.prticipantsId!=null && data.prticipants!=null ){//判断参与人不为空的情况
						prtTemp=data.prticipantsId.split(",");
						prtValueTemp=data.prticipants.split(",");
						if(prtTemp!=null && prtTemp.length>0 && prtValueTemp!=null && prtValueTemp.length>0){
							for (var i=0;i<prtTemp.length ;i++ )   
						    {
								if(i==prtTemp.length-1){
									prtIdValue+="{id:"+prtTemp[i]+",text:\""+prtValueTemp[i]+"\"}";
								}else{
									prtIdValue+="{id:"+prtTemp[i]+",text:\""+prtValueTemp[i]+"\"},";
								}
						    }
						}
						objs += prtIdValue + "]";
						$("#prticipantsName-prticipantsId").select2("data", eval(objs));
					}else{
						selectControl.clearValue("prticipantsName-prticipantsId");
					}
					$("#taskContent").val(data.content);
					$("#standard").val(data.standard);
					$("#startTime").val(data.startTime);
					$("#endTime").val(data.endTime);
//					$("#reportDay").val(data.reportDay);
//					if(data.reportType==1){
//						$("#notRemind").attr("checked","checked");
//						$("#isRemind").removeAttr("checked");
//						$('#isRemind').attr("disabled",false);
//						$('#notRemind').attr("disabled",false);						
//					}else{
//						$("#isRemind").attr("checked","checked");
//						$("#notRemind").removeAttr("checked");
//						$('#isRemind').attr("disabled",true);
//						$('#notRemind').attr("disabled",true);
//					}
					//用于暂存任务提取时赋值 start
					$("#taskId").val(data.id);
					$("#statusArr").val(data.status);
					//用于暂存任务提取时赋值 end
				}
			},
			error : function() {
				mission.subState = false;
				msgBox.info({
					type:"fail",
					content: $.i18n.prop("JC_OA_COMMON_014")
				});
			}
		});
	}
	$('#new-agency').modal('hide');
};
//取消任务模板
mission.deleteWorkTask=function(t,dType){
	if(t!=null && t!=""){
		var opt={
			content:$.i18n.prop("JC_SYS_034"),
			success:function(){
				 $.ajax({
						type : "POST",
						url : getRootPath()+"/po/workTask/cancalTemplate.action?time=" + new Date(),
						data : {"id": t,"isTemplet":"1"},
						dataType : "json",
						success : function(data) {
							mission.subState=false;
							if (data > 0) {
								msgBox.tip({
									type:"success",
									content:$.i18n.prop("JC_SYS_005")
								});
							}
							if(dType==0){
								mission.getWorkTaskZC();
							}else if(dType==1){
								mission.getWorkTask();
							}else{//暂存
								mission.getWorkTaskZCTask();
							}
						}
					});
			},
			cancel:function(){
				mission.subState=false;
			}
		}
		msgBox.confirm(opt);
	}
};
//选择计划
mission.getPlan=function(){
	planRowDetail.resetPlanBoxList();
	//显示计划弹出层内容
	$('#viewPlan').modal('show');
	//初始化查看任务弹出层列表
	planRowDetail.planRowDetailInit();
};
//分页处理 start
//分页对象
mission.oTableNext = null;
//显示列信息
var autoVal=0;
mission.oTableAoColumnsNext = [
    {mData: "report", mRender : function(mData, type, full){
    	if(full.status=='0' || full.status=='8'){
        	autoVal++;
        	return "<input class='cBox' type='checkbox'  name='checkNum'  onClick='mission.subCheckboxControl()' value="+full.id+">";
    	}
    }},                  
	{mData: "taskName", mRender : function(mData){
		return mData;
	}},
	{mData: "taskProc", mRender : function(mData){
		return "<div class=\"progress progress-striped active\"><div class=\"progress-bar\" style=\"width:"+mData+"%\"></div><span class=\"progress-text\">"+mData+"%</span></div>";
	}},
	{mData: "status", mRender : function(mData, type, full){
		var reVal="无";
		if(mData=='0'){
			reVal="未接收";
		}else if(mData=='1'){
			reVal="进行中";
		}else if(mData=='2'){
			reVal="延期";
		}else if(mData=='3'){
			reVal="完成";
		}else if(mData=='4'){
			reVal="取消";
		}else if(mData=='5'){
			reVal="暂存模板";
		}else if(mData=='6'){
			reVal="超期";
		}else if(mData=='7'){
			reVal="拒接收";
		}else{//8
			reVal="暂存";
		}
		return reVal;
	}},
	{mData: "sponsor"},
	{mData: "director"},
	{mData: "startTime"},
	{mData: "endTime"}
];
//组装后台参数
mission.oTableFnServerParamsNext = function(aoData){
	//排序条件
//	getTableParameters(mission.oTableNext, aoData);
//	aoData.push({ "name": "parentTaskid", "value": parentIdT});
};
//分页查询
mission.infoListNext = function () {
	if (mission.oTableNext == null) {
		mission.oTableNext = $('#subTaskTable').dataTable({
			"iDisplayLength": mission.pageRowsNext,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfsubTask.action?parentTaskid="+parentIdT+"&time=" + new Date(),
			"aoColumns": mission.oTableAoColumnsNext,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				mission.oTableFnServerParamsNext(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7]}]
		});
	} else {
		//table不为null时重绘表格
		mission.oTableNext.fnDraw();
	}
};
//添加附件
mission.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name":"fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};
//点击关闭附件弹出层是清空内容
mission.fileupload = function (){
	$("#businessId").val("0");
	echoAttachList(true);
	//showAttachList(false);
};
//点击关闭时删除一上传文件
mission.deleteAttach = function() {
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
//分页处理 end
//提醒设置
var para_weekTemp = '';
mission.showRemind= function(){
	$(document).showRemind_({tableName:'1',remind:para_weekTemp,callback:function(r) {para_weekTemp = r;
		$("#remind").val(r);
	}});
};
//提醒设置(只读)
var para_week = '';
mission.showRemindP= function(){
	$(document).showRemind_({tableName:'1',readonly:true,remind:$("#pRemind").val(),callback:function(r) {para_week = r;
		$("#pRemind").val(r);
	}});
};
//当新建子任务层被关闭时，将业务ID重新赋值，以便父任务查询附件
mission.setBusinessId=function(t){
	loadrightmenu('/po/workTask/queryNextTask.action?id='+t);
};
//当新建子任务层被关闭时，将业务ID重新赋值，以便父任务查询附件
mission.setBusinessIdNew=function(){
	if(mission.operate(parentIdT,'0')==0){//操作校验（预留）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	loadrightmenu('/po/workTask/queryNextTask.action?id='+parentIdT);
};
/**任务名称显示长度*/
mission.taskNameLength=function(tVal){
	var mDataTemp="";
	if(tVal !=null && tVal !=""){
		if(tVal.length>=12){
	    	mDataTemp=tVal.substr(0, 10)+"...";
	    }else{
	    	mDataTemp=tVal;
	    }
	}
	var aHref='<a style=\'cursor: hand;cursor: pointer;\' title='+tVal+' >'+mDataTemp+'</a>';
    return aHref;
};

/**
 * 每条子数据的checkbox处理
 */
mission.subCheckboxControl = function(){
	//拥有的子数据的总条数
	var checkboxNum =$(".cBox").length;
	//被选中的子数据的条数
	var checkedLength = $(".cBox:checked").length;
	//总条数与被选中的数据相等时,证明所有的子数据都被选中了,改变主数据checkbox的状态
	if(checkboxNum == checkedLength){
		$("#parentCheck").prop("checked",true);
	}else{
		$("#parentCheck").prop("checked",false);
	}
};
//关闭任务模板
mission.closeWin=function(){
	$("#taskNameVal").val("");
	mission.getWorkTaskZCTask();
	mission.getWorkTask();
	mission.getWorkTaskZC();
};
//超期提醒设置预览
mission.showRemindSet=function(){
	var t=$('#rRemindType').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#optionsRadios2').val(s);//提醒方式
	var directorId=$('#rDirector').val();
	var prticipantsId=$('#rPrticipants').val();
	if(t!=0){
		$('#remindCon').val($('#rTaskName').val());//提醒内容
		$('#remindPerName').val(directorId+','+prticipantsId);
	}else{
		$('#remindCon').val("");
		$('#remindPerName').val("");
	}
	$('#remindWindow').modal('show');
};
//超期提醒设置预览
mission.showRemindSetNew=function(){
	var t=$('#newTaskForm input[name="remindType"]:checked').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#optionsRadios').val(s);//提醒方式
	var directorId=returnValue('director-directorId');
	var prticipantsId=returnValue('prticipantsName-prticipantsId');
	var dire=$('#director-directorId').val();
	var prti=$('#prticipantsName-prticipantsId').val();
	var perId='';
	if(directorId!=null && prticipantsId==null){
		var director= new Array(); 
		director=directorId.split(":");
		perId+=director[1];
	}
	if(directorId==null && prticipantsId!=null){
		var prticipants= new Array(); 
		prticipants=prticipantsId.split(",");
		var participart='';
		for(var i=0;i<prticipants.length;i++){
			if(i==(prticipants.length-1)){
				participart+=prticipants[i].split(":")[1];
			}else{
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		perId+=participart;
	}
	if(directorId!=null && prticipantsId!=null){
		var director= new Array(); 
		director=directorId.split(":");
		var prticipants= new Array(); 
		prticipants=prticipantsId.split(",");
		var participart='';
		for(var i=0;i<prticipants.length;i++){
			if(prticipants[i].split(":")[1]!=director[1]){
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		perId+=director[1]+','+participart;
	}
	var per="";
	if(dire!=null && prti==null){
		per+=dire;
	}
	if(dire==null && prti!=null){
		per+=prti;
	}
	var partis='';
	if(dire!=null && prti!=null){
		var prt= new Array(); 
		prt=prti.split(",");
		for(var i=0;i<prt.length;i++){
			if(i==(prt.length-1)){
				if(dire!=prt[i]){
					partis+=prt[i];
				}
			}else{
				if(dire!=prt[i]){
					partis+=prt[i]+',';
				}
			}
		}
		if(partis!=''){
			per+=dire+','+partis;
		}else{
			per+=dire;
		}
	}
	if(t!=0){
		$("#remindContent").attr("disabled",false);
		if(isSave==0){
			$('#remindContent').val($('#taskName').val());//提醒内容
		}else{
			if($('#remindContent').val()==null || $('#remindContent').val()==''){
				$('#remindContent').val($('#taskName').val());//提醒内容
				isSave=0;
			}
		}
		$('#remindPerIdNew').val(per);
		$('#remindPerNameNew').val(perId);
		$('#remindContentTemp').val($('#remindContent').val());
	}else{
		$('#remindContent').val("");
		$('#remindPerIdNew').val("");
		$('#remindPerNameNew').val("");
		$('#remindContentTemp').val("");
		$("#remindContent").attr("disabled",true);
		isSave=0;
	}
	$('#remindWindowNew').modal('show');
};
//超期提醒-保存
mission.showRemindSetSave=function(){
	var t=$('#newTaskForm input[name="remindType"]:checked').val();
	if(t!=0){
		if($('#remindContent').val()==null || $('#remindContent').val()==''){
			isSave=0;
		}else{
			$('#remindContentTemp').val($('#remindContent').val());
			isSave=1;
		}
	}
};
//超期提醒-关闭
mission.showRemindSetClose=function(){
	var t=$('#newTaskForm input[name="remindType"]:checked').val();
	if(t!=0){
		if(isSave==1){
			$('#remindContent').val($('#remindContentTemp').val());
		}else{
			$('#remindContent').val($('#taskName').val());
		}
	}else{
		$('#remindContent').val($('#taskName').val());
	}
};
//超期提醒默认设置
mission.remindDefault=function(){
	var t=$('#newTaskForm input[name="remindType"]:checked').val();
	if(t!=0){
		var dire=$('#director-directorId').val();
		var prti=$('#prticipantsName-prticipantsId').val();
		$('#remindPerIdNew').val(dire+','+prti);
		if($('#remindContent').val()!=''){
			//nothing to do
		}else{
			$('#remindContent').val($('#taskName').val());
		}
	}
};
//校验子任务的开始、结束时间是否在父任务范围内
mission.checkParentDate=function(){
	var reVal=1;
	var parentStartTime=$('#parentStartTime').val();
	var parentEndTime=$('#parentEndTime').val();
	var updateStartTime=$('#startTime').val();
	var updateEndTime=$('#endTime').val();
	Date.prototype.format =function(format)
	{
		var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
		};
		if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
		(this.getFullYear()+"").substr(4- RegExp.$1.length));
		for(var k in o)if(new RegExp("("+ k +")").test(format))
		format = format.replace(RegExp.$1,
		RegExp.$1.length==1? o[k] :
		("00"+ o[k]).substr((""+ o[k]).length));
		return format;
	};
	var ps =new Date(parentStartTime).format('yyyy-MM-dd');
	var p =new Date(parentEndTime).format('yyyy-MM-dd');
	var pt =mission.trans(p,'0');
	var ss=mission.compareDate(updateStartTime,ps);
	var tt=mission.compareDate(pt,updateEndTime);
	if(ss==false || tt==false){
		reVal=0;
	}
	return reVal;
};
var ma = [['1','3','5','7','8','10'],['4','6','9','11']]; 
//实现加减f：'1'加，'0'减 
mission.trans=function(o,f) {
	var d = o.split('-'); 
	var l = isLeap(d[0]); 
	if(f == '1') {
		if((check(d[1],ma[0]) && (d[2] == '31')) || (check(d[1],ma[1]) && (d[2] == '30')) || 
		(d[1] == '2' && d[2] == '28' && !l) || (d[1] == '2' && d[2] == '29' && l)) {
			return d[0] + '-' + (d[1] * 1 + 1) + '-' + 1; 
		}else if(d[1] == '12' && d[2] == '31') {
			return (d[0] * 1 + 1) + '-' + '1-1'; 
		}else{
			return d[0] + '-' + d[1] + '-' + (d[2] * 1 + 1); 
		}
	}
	else if(f == '0') {
		if(check(d[1] - 1,ma[0]) && (d[2] == '1')) { 
			return d[0] + '-' + (d[1] - 1) + '-' + '31'; 
		} else if(check(d[1] - 1,ma[1]) && (d[2] == '1')) { 
			return d[0] + '-' + (d[1] - 1) + '-' + '30'; 
		} else if(d[1] == '1' && d[2] == '1') { 
			return (d[0] - 1) + '-' + '12-31'; 
		} else if(d[1] == '3' && d[2] == '1' && !l) { 
			return d[0] + '-' + '2-28'; 
		} else if(d[1] == '3' && d[2] == '1' && l) { 
			return d[0] + '-' + '2-29'; 
		} else { 
			return d[0] + '-' + d[1] + '-' + (d[2] - 1); 
		} 
	}else{
		return; 
	} 
}; 
//判断数组a是否存在在元素n 
function check(n,a) {
	for(var i = 0,len = a.length;i < len;i++) {
		if(a[i] == n) { 
			return true; 
		} 
	} 
	return false; 
} 
//闰?年? 
function isLeap(y) {
	return ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) ? true : false; 
} 
//js日期比较(yyyy-mm-dd)
mission.compareDate=function(a, b) {
   var arr = a.split("-");
   var starttime = new Date(arr[0], arr[1], arr[2]);
   var starttimes = starttime.getTime();
   var arrs = b.split("-");
   var lktime = new Date(arrs[0], arrs[1], arrs[2]);
   var lktimes = lktime.getTime();
   if (starttimes >= lktimes) {
       return true;
   }
   else{
	   return false;
   } 
};
/**
 * 导入计划
 */
mission.planBoxFillData=function() {
	var chkPlanValue = "";//未处理的计划内容字符串
	var standardValue="";//完成标准
	//处理任务内容字符串方法
	var checkNum=$("input[name^=checkPlanIds]:checked").length;
	$("input[name^=checkPlanIds]:checked").each(function(i,v){
		if(i==checkNum-1){
			chkPlanValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(1).innerHTML+'；';//主要完成事项
			standardValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(2).innerHTML+'；';//完成标准
		}else{
			chkPlanValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(1).innerHTML+'；\n';//主要完成事项
			standardValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(2).innerHTML+'；\n';//完成标准
		}
	});
	//如果没有计划内容为空,弹出提示
	if(chkPlanValue == ""){
		msgBox.info({
			content: $.i18n.prop("JC_OA_PO_042")
		});
	}else{
		if($("#taskContent").val() !=''){
			chkPlanValue=$("#taskContent").val()+'\n'+chkPlanValue;
			standardValue=$("#standard").val()+'\n'+standardValue;
		}
		$("#taskContent").val(chkPlanValue);
		$("#standard").val(standardValue);
		$("#viewPlan").modal("hide");//弹出层隐藏
	}
};
//查看任务信息(父任务)
mission.getParentTask = function (id) {
	$("#businessId").val(id);
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#receWorkTaskForm").fill(data);
				//附件使用 start
				$("#businessId").val(data.id);
				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				clearTable();
				echoAttachListDul(false,'attachList_0');
				//附件使用 end
			}
		}
	});
};
//超期校验
mission.isRemind=function(){
	var endDate=$("#endTime").val();//结束日期
	var dd = new Date(); 
	var x = dd.toLocaleString(); 
	var yp = x.indexOf('年'), 
	mp = x.indexOf('月'), 
	dp = x.indexOf('日'); 
	var y = x.substr(0,yp), 
	m = x.substr(yp + 1,mp - yp - 1), 
	d = x.substr(mp + 1,dp - mp - 1); 
	var o =[y,m,d];
	var nowDate=o.join('-');
	return mission.compareDate(endDate,nowDate);
};
//js日期比较(yyyy-mm-dd)
mission.compareDate=function(a, b) {
   var arr = a.split("-");
   var starttime = new Date(arr[0], arr[1], arr[2]);
   var starttimes = starttime.getTime();
   var arrs = b.split("-");
   var lktime = new Date(arrs[0], arrs[1], arrs[2]);
   var lktimes = lktime.getTime();
   if (starttimes >= lktimes) {
       return true;
   }
   else{
	   return false;
   } 
};
//操作事件校验
mission.operate=function(taskId,operateType){
	var reVal=0;
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/workTask/checkOperation.action?time=" + new Date(),
		data : {"taskId": taskId,"operateType":operateType},
		dataType : "json",
        async : false,
		success : function(data) {
			mission.subState=false;
			if(data>0){
				reVal=1;
			}
		},
		error : function() {
			mission.subState = false;
		}
	});
	return reVal;
};
//内容、标准样式
mission.autoValue=function(){
    var co=$('#hiContent').val();
    co=mission.enter2Br(co);
    co=mission.br2Enter(co);
	$('#showContent').html(co);
	var st=$('#hiStandard').val();
	st=mission.enter2Br(st);
	st=mission.br2Enter(st);
	$('#showStandard').html(st);
};
//折行
mission.enter2Br = function (str){
	return str.replace(/\n/g,'<br/>').replace(/\s/g,"&nbsp;");
};
//回车
mission.br2Enter = function (str){
    return str.replace(/<br>/ig,"\r\n").replace(/(&nbsp;)/g," ");
};
//用于汇报提醒中短信提醒判断
mission.remindMessage=function(){
	var reg1 =  /^\d+$/;
	var reportDay=$('#reportDay').val();
	if(reportDay==null || reportDay.trim()==''){
		$('#reportDay').val(0);
	}
	 if(reportDay.trim().match(reg1)==null) {
		 $('#isRemind').attr("disabled",true);
		 $('#notRemind').attr("disabled",true);
		 $('#isRemind').attr("checked",true);
		 $('#notRemind').attr("checked",false);
	 }else{
		 if(reportDay >0 && reportDay<1000){
			 $('#isRemind').attr("disabled",false);
			 $('#notRemind').attr("disabled",false);
		 }else{
			 $('#isRemind').attr("disabled",true);
			 $('#notRemind').attr("disabled",true);
			 $('#isRemind').attr("checked",true);
			 $('#notRemind').attr("checked",false);
		 }
	 }
};
//汇报时限天数校验
mission.compareStaAndEnd=function(){
	var reVal=0;
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	var reportDay=$('#reportDay').val();
	if(startTime!=null && endTime!=null && reportDay!=null && startTime!='' && endTime!=''){
	   var aDate  =  startTime.split("-");
       var oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);    //转换为12-18-2006格式  
       var aDate  =  endTime.split("-");
       var oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
       var iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
       if(iDays<reportDay){
    	   reVal=1;
       }
	}
	return reVal;
};
//点击新增任务时，将上级任务信息带给下级任务
mission.parentToSubTask=function(){
	if($("#parentId").val()!=null && $("#parentId").val() !=''){
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/workTask/getAll.action?time=" + new Date(),
			data : {"id": $("#parentId").val()},
			dataType : "json",
			success : function(data) {
				if (data) {
					$("#taskName").val(data.taskName);
					$("#taskWorkType").val(data.taskWorkType);
					$("#taskOrigin").val(data.taskOrigin);			
					//人员选择树--负责人
					$("#director-directorId").select2("data", {id:data.directorId,text:data.director});					
					//人员选择树--参与人
					var prtTemp= new Array();//ID
					var prtValueTemp= new Array();//NAME
					var objs = "[";
					var prtIdValue="";
					if(data.prticipantsId!=null && data.prticipants!=null ){//判断参与人不为空的情况
						prtTemp=data.prticipantsId.split(",");
						prtValueTemp=data.prticipants.split(",");
						if(prtTemp!=null && prtTemp.length>0 && prtValueTemp!=null && prtValueTemp.length>0){
							for (i=0;i<prtTemp.length ;i++ )   
						    {
								if(i==prtTemp.length-1){
									prtIdValue+="{id:"+prtTemp[i]+",text:\""+prtValueTemp[i]+"\"}";
								}else{
									prtIdValue+="{id:"+prtTemp[i]+",text:\""+prtValueTemp[i]+"\"},";
								}
						    }
						}
						objs += prtIdValue + "]";
						$("#prticipantsName-prticipantsId").select2("data", eval(objs));
					}else{
						selectControl.clearValue("prticipantsName-prticipantsId");
					}
					$("#taskContent").val(data.content);
					$("#standard").val(data.standard);
					$("#startTime").val(data.startTime);
					$("#endTime").val(data.endTime);
				}
			},
			error : function() {
				mission.subState = false;
				msgBox.info({
					type:"fail",
					content: $.i18n.prop("JC_OA_COMMON_014")
				});
			}
		});
	}
};
//暂存任务(删除)
mission.deleteWorkTaskNew=function(t){
	if(t!=null && t!=""){
		var opt={
			content:$.i18n.prop("JC_SYS_034"),
			success:function(){
				 $.ajax({
						type : "POST",
						url : getRootPath()+"/po/workTask/deleteByIds.action",
						data : {"ids": t},
						dataType : "json",
						success : function(data) {
							mission.subState=false;
							if (data > 0) {
								msgBox.tip({
									type:"success",
									content:$.i18n.prop("JC_SYS_005")
								});
							}
							mission.getWorkTaskZCTask();
						}
					});
			},
			error : function() {
				mission.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			},
			cancel:function(){
				mission.subState=false;
			}
		}
		msgBox.confirm(opt);
	}
};
//暂存任务保存时，查询其父子关系
mission.getParentTaskForZC = function (id) {
	var reVal=0;
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),
		data : {"id": id},
        cache : false, 
        async : false,
		dataType : "json",
		success : function(data) {
			if (data.status =='8' && data.parentTaskid !=-1) {//暂存状态 且 存在父任务 且 无非暂存任务
				$.ajax({
					type : "GET",
					url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),
					data : {"id": data.parentTaskid},
			        cache : false, 
			        async : false,
					dataType : "json",
					success : function(data) {
						if (data.normalToCount==0) {//暂存状态 且 存在父任务 且 无非暂存任务
							reVal=1;
						}
					}
				});
			}
		}
	});
	return reVal;
};
//初始化
jQuery(function($)
{
	//查询父任务
	mission.getParentTask($("#parentId").val());
	//计算分页显示条数
	mission.pageRows = TabNub>0 ? TabNub : 5;
	//计算分页显示条数
	mission.pageRowsNext = TabNub>0 ? TabNub : 5;
	//批量删除
	$("#batchDele").click(function(){mission.batchDelete();});
	//保存
	$("#addNewTask").click(function(){mission.saveNewTask('');});
	//暂存
	$("#addTempTask").click(function(){mission.saveNewTask('8');});
	//重置
	$("#resetInfo").click(function(){mission.clearForm();});
	//提取任务模板中任务名称
	$("#extractButtion").click(function(){mission.getTaskName();});
	//选择工作计划
	$("#selectPlan").click(function(){mission.getPlan();});
	//提醒按钮事件绑定
	$("#remindSet").click(function(){mission.showRemind();});
	//提醒按钮事件绑定(只读)
	$("#pRemindSet").click(function(){mission.showRemindP();});
	//新建子任务
	$("#addSubTaskId").click(function(){
		$("#new-sub-task").modal("show");
		//附件使用 start
		$("#businessId").val('0');
		$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
		clearDelAttachIds();//设置附件上传为逻辑删除
		$("#islogicDel").val("1");//附件使用 逻辑删除
		clearTable();
		echoAttachList(true);
		clearTable();
		$('#isshow').val('0');
		if($('#taskParentToSub').val()=='1'){//上级信息自动导入开关
			mission.parentToSubTask();
		}
		//附件使用 end
	});
	//绑定查看计划弹出层查询按钮事件(查看计划)
	$("#queryPlanBox").click(function(){planRowDetail.planRowDetailList();});
	//绑定查看计划弹出层重置按钮事件(查看计划)
	$("#resetPlanBox").click(function(){planRowDetail.resetPlanBoxList();});
	//绑定导入计划按钮事件(查看计划)
	$("#importContentBtn").click(function(){mission.planBoxFillData();});
	//附件使用
	$("#closebtn").click(function(){mission.fileupload();});
	$("#closeModalBtn").click(function(){mission.fileupload();});
	$("#queryattach").click(function(){mission.fileupload();});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//超期提醒保存
	$("#remindSave").click(function(){mission.showRemindSetSave();});
	//超期提醒关闭
	$("#remindClose").click(function(){mission.showRemindSetClose();});
	//隐藏错误信息
	hideErrorMessage();
	//内容、标准样式
	mission.autoValue();
});