var taskTempRele='off';//暂存任务是否可以在层次关系中显示； on:显示；off:关闭。
var taskTempButton='off';//暂存状态任务修改时，是否存在暂存按钮; on:显示；off:关闭。
var taskInfo = {};
taskInfo.callMathedVal=0;//用于操作项回调时方法调用；0：发起人；1：负责人;2:暂存
taskInfo.status=0;//用于限制暂存重复提交
var currentUserId=document.getElementById("currentUserId").value;
var parentTaskid="";
var isSave=0;//用于超期提醒设置预览
//用于返回按钮 start
var queryTaskName=document.getElementById("queryTaskName").value;
var qStartTime=document.getElementById("qStartTime").value;
var qEndTime=document.getElementById("qEndTime").value;
var querySponsorId=document.getElementById("querySponsorId").value;
var querySponsor=document.getElementById("querySponsor").value;
var queryDirectorId=document.getElementById("queryDirectorId").value;
var queryDirector=document.getElementById("queryDirector").value;
var queryInfoStatus=document.getElementById("queryInfoStatus").value;
var status0=document.getElementById("queryStatus0").value;
var status1=document.getElementById("queryStatus1").value;
var status2=document.getElementById("queryStatus2").value;
var status3=document.getElementById("queryStatus3").value;
var status4=document.getElementById("queryStatus4").value;
var status6=document.getElementById("queryStatus6").value;
var status7=document.getElementById("queryStatus7").value;
//用于返回按钮 end
taskInfo.pageRows = null;
var para_week=null;//提醒设置
//重复提交标识
taskInfo.subState = false;
//初始化申请部门选择树
selectControl.init("applyDeptTree","applyDept-applyDeptid", false, false);
//人员选择树--申请人
selectControl.init("showapplyNameTree","senderText-applyId", false, true);
//清空表单(重置)-发起人
taskInfo.clearForm = function () {
	$("#taskName").val("");
	$("#queryStartTime").val("");
	$("#queryEndTime").val("");
};
//清空表单(重置)-负责人
taskInfo.clearForm_fzr = function () {
	$("#taskName-fzr").val("");
	$("#queryStartTime-fzr").val("");
	$("#queryEndTime-fzr").val("");
};
//清空表单(重置)-暂存
taskInfo.clearForm_zc = function () {
	$("#taskName-zc").val("");
	$("#queryStartTime-zc").val("");
	$("#queryEndTime-zc").val("");
};
//清空表单(接收任务)
taskInfo.clear=function(){
	$('#receWorkTaskForm')[0].reset();
};
//查询子任务 start
var pageCount=10;
//用来拼table id 的全局变量
var tableId = "_";  	
/**
 * rowDetail的展开和关闭的方法
 * 参数id:对应数据唯一的key
 */
taskInfo.rowDetail = function(obj,id,qType){
	//绑定onClick事件.示例是绑定到每行第一个td上面.
	 //取得选中的row  
	var nTr = $(obj).parent().parent("tr")[0];
	//判断如果rowDetail是打开状态,那么则关闭.
    if ( $($(obj).closest("table")).DataTable().fnIsOpen(nTr) ){
    	$($(obj).closest("table")).DataTable().fnClose( nTr );
    	$(obj).removeClass("fa fa-minus");
    	$(obj).addClass("fa fa-plus2");
    }else{
    	//否则,未打开rowDetail时,则追加一个新row
    	//数据加载过程中,显示"数据加载中..."message.对应资源文件中的JC_SYS_068
    	var newRow = $($(obj).closest("table")).DataTable().fnOpen( nTr, "waiting....." ,  "pd");
    	//调用获得rowDetail数据的方法
    	taskInfo.getDetailData(id,newRow,obj,qType);
    	$(obj).removeClass("fa fa-plus2");
    	$(obj).addClass("fa fa-minus");
    }
};
/**
 * rowDetail的展开和关闭的方法
 * 参数id:对应数据唯一的key
 */
taskInfo.rowDetail_fzr = function(obj,id,qType){
	//绑定onClick事件.示例是绑定到每行第一个td上面.
	 //取得选中的row  
	var nTr = $(obj).parent().parent("tr")[0];
	//判断如果rowDetail是打开状态,那么则关闭.
    if ( $($(obj).closest("table")).DataTable().fnIsOpen(nTr) ){
    	$($(obj).closest("table")).DataTable().fnClose( nTr );
    	$(obj).removeClass("fa fa-minus");
    	$(obj).addClass("fa fa-plus2");
    }else{
    	//否则,未打开rowDetail时,则追加一个新row
    	//数据加载过程中,显示"数据加载中..."message.对应资源文件中的JC_SYS_068
    	var newRow = $($(obj).closest("table")).DataTable().fnOpen( nTr, "waiting....." ,  "pd");
    	//调用获得rowDetail数据的方法
    	taskInfo.getDetailData_fzr(id,newRow,obj,qType);
    	$(obj).removeClass("fa fa-plus2");
    	$(obj).addClass("fa fa-minus");
    }
};
/**
 * rowDetail的展开和关闭的方法
 * 参数id:对应数据唯一的key
 */
taskInfo.rowDetailNew = function(obj,id,qType){
	//绑定onClick事件.示例是绑定到每行第一个td上面.
	 //取得选中的row  
	var nTr = $(obj).parent().parent("tr")[0];
	//判断如果rowDetail是打开状态,那么则关闭.
    if ( $($(obj).closest("table")).DataTable().fnIsOpen(nTr) ){
    	$($(obj).closest("table")).DataTable().fnClose( nTr );
    	$(obj).removeClass("fa fa-minus");
    	$(obj).addClass("fa fa-plus2");
    }else{
    	//否则,未打开rowDetail时,则追加一个新row
    	//数据加载过程中,显示"数据加载中..."message.对应资源文件中的JC_SYS_068
    	var newRow = $($(obj).closest("table")).DataTable().fnOpen( nTr, "waiting....." ,  "pd");
    	//调用获得rowDetail数据的方法
    	taskInfo.getDetailDataNew(id,newRow,obj,qType);
    	$(obj).removeClass("fa fa-plus2");
    	$(obj).addClass("fa fa-minus");
    }
};
/**
 * 调用获得rowDetail数据的方法
 * 参数1:id对应每行数据的唯一标示
 * 参数2:新创建的row对象
 */
taskInfo.getDetailData = function(id,newRow,obj,qType){
	var urlPath="";
	if(qType=='1'){//查询历史
		urlPath+=getRootPath()+"/po/taskfinish/getChildList.action?id="+id+"&time="+new Date();
	}else{
		urlPath+=getRootPath()+"/po/workTask/getChildListNew.action?id="+id+"&time="+new Date();
	}
	//向新创建的row中追加detail数据
	//调用拼装html语句格式的方法
	$('td', newRow).html(taskInfo.getDetailHtml(id));
    $("#"+tableId+id).dataTable( {
		"iDisplayLength": taskInfo.pageCount,//每页显示多少条记录
		"sAjaxSource": urlPath,
		"fnServerData": oTableRetrieveData,//查询数据回调函数
		"aoColumns": taskInfo.oTableAoColumns,//table显示列
        "bPaginate":false,
        "bSort":false,
		//默认不排序列
        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}]
	} );
	tableId += "_";
};
/**
 * 调用获得rowDetail数据的方法
 * 参数1:id对应每行数据的唯一标示
 * 参数2:新创建的row对象
 */
taskInfo.getDetailData_fzr = function(id,newRow,obj,qType){
	var urlPath="";
	if(qType=='1'){//查询历史
		urlPath+=getRootPath()+"/po/taskfinish/getChildList.action?id="+id+"&time="+new Date();
	}else{
		urlPath+=getRootPath()+"/po/workTask/getChildListNew.action?id="+id+"&time="+new Date();
	}
	//向新创建的row中追加detail数据
	//调用拼装html语句格式的方法
	$('td', newRow).html(taskInfo.getDetailHtml(id));
    $("#"+tableId+id).dataTable( {
		"iDisplayLength": taskInfo.pageCount,//每页显示多少条记录
		"sAjaxSource": urlPath,
		"fnServerData": oTableRetrieveData,//查询数据回调函数
		"aoColumns": taskInfo.oTableAoColumns_fzr,//table显示列
        "bPaginate":false,
        "bSort":false,
		//默认不排序列
        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}]
	} );
	tableId += "_";
};
/**
 * 调用获得rowDetail数据的方法
 * 参数1:id对应每行数据的唯一标示
 * 参数2:新创建的row对象
 */
taskInfo.getDetailDataNew = function(id,newRow,obj,qType){
	var urlPath="";
	if(qType=='1'){//查询历史
		urlPath+=getRootPath()+"/po/taskfinish/getChildListForZC.action?id="+id+"&time="+new Date();
	}else{
		urlPath+=getRootPath()+"/po/workTask/getChildListNewForZC.action?id="+id+"&time="+new Date();
	}
	//向新创建的row中追加detail数据
	//调用拼装html语句格式的方法
	$('td', newRow).html(taskInfo.getDetailHtml(id));
    $("#"+tableId+id).dataTable( {
		"iDisplayLength": taskInfo.pageCount,//每页显示多少条记录
		"sAjaxSource": urlPath,
		"fnServerData": oTableRetrieveData,//查询数据回调函数
		"aoColumns": taskInfo.oTableAoColumns,//table显示列
        "bPaginate":false,
        "bSort":false,
		//默认不排序列
        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}]
	} );
	tableId += "_";
};
/**
 * 拼装html的方法
 * 参数1:data对应ajax返回的数据
 */
taskInfo.getDetailHtml = function(priId){
	var html = "";
	//指定table的格式和css
	html += "<table id=\""+ tableId + priId +"\" class=\"table table-striped tab_height first-td-tc\">";
	html += "<thead style=\"display:none\"></thead>";
	html += "<tbody>";
	html += "</tbody>";
	// table收尾
	html += "</table>"; 
	return html;
};
//查询子任务 end
//分页处理 start-发起人
taskInfo.oTable = null;//分页对象-发起人
//显示列信息-发起人
taskInfo.oTableAoColumns = [
	//点击+
	{mData: function(source) {
		//查询暂存 start
		if(taskTempRele=='on'){
			if(source.totalCount >0 || source.taskZCCount >0){
				if(source.status=='3'){
					return "<a class=\"fa fa-plus2\" onClick=\"taskInfo.rowDetailNew(this," + source.taskId+",'1')\"></a>";
				}else{
					return "<a class=\"fa fa-plus2\" onClick=\"taskInfo.rowDetailNew(this,"+ source.id+",'0')\"></a>";
				}
			}else{
				return '';
			}
		}else{
			if(source.totalCount >0){
				if(source.status=='3'){
					return "<a class=\"fa fa-plus2\" onClick=\"taskInfo.rowDetail(this," + source.taskId+",'1')\"></a>";
				}else{
					return "<a class=\"fa fa-plus2\" onClick=\"taskInfo.rowDetail(this,"+ source.id+",'0')\"></a>";
				}
			}else{
				return '';
			}
		}
	}},
	{mData: "taskName", mRender : function(mData, type, full){
		return mData;
	}},
	{mData: "taskTypeName", mRender : function(mData, type, full){
		var reVal="无";
		if(mData !=null && mData !=''){
			reVal=mData;
		}
		return reVal;
	}},
	{mData: "taskProc", mRender : function(mData, type, full){
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
		}else{
			reVal="暂存";
		}
		return reVal;
	}},
	{mData: "parentTaskName",mRender : function(mData){
			return taskInfo.getParentName(mData);
		}
	},
	{mData: "sponsor"},
	{mData: "director"},
	{mData: "startTime"},
	{mData: "endTime"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
 ];
//组装后台参数-发起人
taskInfo.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(taskInfo.oTable, aoData);
	//组装查询条件
	$.each($("#zdsz-fqr").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};
//分页查询-发起人
taskInfo.infoList = function () {
	$('#insideIn-list').fadeIn();
	if (taskInfo.oTable == null) {
		taskInfo.oTable = $('#taskListTable').dataTable( {
			"iDisplayLength": taskInfo.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageList.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskInfo.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				taskInfo.oTableFnServerParams(aoData);
			},
			aaSorting:[],
			//默认不排序列
			 "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,10]}]
		} );
	} else {
		//table不为null时重绘表格
		taskInfo.oTable.fnDraw();
	}
};
//分页处理 end-发起人
//分页处理 start-负责人
taskInfo.oTable_fzr = null;//分页对象-负责人
//显示列信息-负责人
taskInfo.oTableAoColumns_fzr = [
	//点击+
	{mData: function(source) {
		if(source.totalCount >0){
			if(source.status=='3'){
				return "<a class=\"fa fa-plus2\" onClick=\"taskInfo.rowDetail_fzr(this," + source.taskId+",'1')\"></a>";
			}else{
				return "<a class=\"fa fa-plus2\" onClick=\"taskInfo.rowDetail_fzr(this,"+ source.id+",'0')\"></a>";
			}
		}else{
			return '';
		}
	}},
	{mData: "taskName", mRender : function(mData, type, full){
		return mData;
	}},
	{mData: "taskTypeName", mRender : function(mData, type, full){
		var reVal="无";
		if(mData !=null && mData !=''){
			reVal=mData;
		}
		return reVal;
	}},
	{mData: "taskProc", mRender : function(mData, type, full){
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
		}else{
			reVal="暂存";
		}
		return reVal;
	}},
	{mData: "parentTaskName",mRender : function(mData){
			return taskInfo.getParentName(mData);
		}
	},
	{mData: "sponsor"},
	{mData: "director"},
	{mData: "startTime"},
	{mData: "endTime"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons_fzr(source);
	}}
 ];
//组装后台参数-负责人
taskInfo.oTableFnServerParams_fzr = function(aoData){
	//排序条件
	getTableParameters(taskInfo.oTable_fzr, aoData);
	//组装查询条件
	$.each($("#zdsz-fzr").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};
//分页查询-负责人
taskInfo.infoList_fzr = function () {
	$('#insideIn-list-fzr').fadeIn();
	if (taskInfo.oTable_fzr == null) {
		taskInfo.oTable_fzr = $('#taskListTable-fzr').dataTable( {
			"iDisplayLength": taskInfo.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageList.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskInfo.oTableAoColumns_fzr,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				taskInfo.oTableFnServerParams_fzr(aoData);
			},
			aaSorting:[],
			//默认不排序列
			 "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,10]}]
		} );
	} else {
		//table不为null时重绘表格
		taskInfo.oTable_fzr.fnDraw();
	}
};
//分页处理 end-负责人

//分页处理 start-暂存
taskInfo.oTable_zc = null;//分页对象-暂存
//显示列信息-暂存
taskInfo.oTableAoColumns_zc = [
	{mData: "taskName", mRender : function(mData, type, full){
		return mData;
	}},
	{mData: "taskTypeName", mRender : function(mData, type, full){
		var reVal="无";
		if(mData !=null && mData !=''){
			reVal=mData;
		}
		return reVal;
	}},
	/*
	{mData: "taskProc", mRender : function(mData, type, full){
		return "<div class=\"progress progress-striped active\"><div class=\"progress-bar\" style=\"width:"+mData+"%\"></div><span class=\"progress-text\">"+mData+"%</span></div>";
	}},
	*/
	{mData: "parentTaskName",mRender : function(mData){
			return taskInfo.getParentName(mData);
		}
	},
	{mData: "sponsor"},
	{mData: "director"},
	{mData: "startTime"},
	{mData: "endTime"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons_zc(source);
	}}
 ];
//组装后台参数-暂存
taskInfo.oTableFnServerParams_zc = function(aoData){
	//排序条件
	getTableParameters(taskInfo.oTable_zc, aoData);
	//组装查询条件
	$.each($("#zdsz-zc").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};
//分页查询-暂存
taskInfo.infoList_zc = function () {
	$('#insideIn-list-zc').fadeIn();
	if (taskInfo.oTable_zc == null) {
		taskInfo.oTable_zc = $('#taskListTable-zc').dataTable( {
			"iDisplayLength": taskInfo.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfZC.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskInfo.oTableAoColumns_zc,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				taskInfo.oTableFnServerParams_zc(aoData);
			},
			aaSorting:[],
			//默认不排序列
			 "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7]}]
		} );
	} else {
		//table不为null时重绘表格
		taskInfo.oTable_zc.fnDraw();
	}
};
//分页处理 end-暂存

//json 日期格式化
taskInfo.customDateString = function (t) {
	var jDate="\/Date("+t+")\/";
    var r;
    var d = eval("new " + jDate.replace(/\//g, ""));
    //自定义格式
    if (d.getFullYear() > 1000) {
        month = d.getMonth() + 1;
        r = d.getFullYear() + "-" + month + "-" + d.getDate();
    }
    return r;
};
//查询上级任务名称
taskInfo.getParentName=function(t){
	 if(t==null){
		 t="无";
	 }
	 return t;
};
//为不接收任务页面赋值
taskInfo.getNotRrce = function (id) {
	hideErrorMessage();
	if(taskInfo.operate(id,'4')==0){//操作校验（不接收）
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
				$("#token").val(data.tokenTemp);//性能优化
				//清除验证信息
				hideErrorMessage();
				$("#notRrce").fill(data);
				$("#noReTaskId").val(id);
				$("#noReEventTitle").val(data.taskName);
				$("#noReContent").val(data.content);
				$('#noReTaskReason').val("");
				$("#noContent").val(data.content);
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
//不接收任务保存方法
taskInfo.notRrce = function (hide) {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	if ($("#notRrce").valid()) {
		if(taskInfo.operate($("#notRrce input[id=noReTaskId]").val(),'4')==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#notRrce").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		taskInfo.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				taskInfo.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#no_task').modal('hide');
					}
					$("#noReTaskReason").val("");
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:taskInfo.callMathed()
					});
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
				taskInfo.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//修改-汇报时限
taskInfo.showCont=function(){
 switch($('input:radio[name="hbsx"]:checked').attr("id")){
  case "hbsx-ts":
	  	$("#reportType").val("0");
	    $("#hbsx-rq").val("");
   break;
  case "hb-rq":
	  	$("#reportType").val("1");
	  	$("#hq-day").val("");
   break;
  default:
   break;
 }
};
//获取单条信息
taskInfo.get = function (id) {
	if(taskInfo.operate(id,'0')==0){//操作校验（查询）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_OA_COMMON_014")
		});
		return;
	}
	hideErrorMessage();
	taskInfo.status=0;
	$("#businessId").val(id);
	$.ajax({
		type : "GET",
//		url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),//为了获取token修改    lihongyu at 2014-11-26
		url : getRootPath()+"/po/workTask/queryInfoForUpdate.action?time=" + new Date(),
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//性能优化
				para_week=data.remind;//提醒设置反显
				//清除验证信息
				hideErrorMessage();
				$("#updateWorkTask").fill(data);
				$('#showDiTree').empty();
				selectControl.init("showDiTree","directors-directorId", false, true,null, {id:data.directorId,text:data.director});
				var prtTemp= new Array();//ID
				var prtValueTemp= new Array();//NAME
				var objs = "[";
				var prtIdValue="";
				if(data.prticipantsId==null || data.prticipants==null){
					selectControl.init("prtTree","prt-prticipantsId",true,true);
				}else{
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
					$('#prtTree').empty();
					selectControl.init("prtTree","prt-prticipantsId", true, true, null, eval(objs));
				}
				//附件使用 start
				$("#businessId").val(data.id);
				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				clearTable();
				echoAttachList(true);
				clearTable();
				$('#isshow').val('0');//只允许附件下载
				//附件使用 end
				//判断修改页面中是否显不暂存按钮
				if(taskTempButton=='on'){
					if(data.status=='8'){
						$("#updateTempTask").css('display','inline-block'); 
					}else{
						$("#updateTempTask").css('display','none'); 
					}
				}
				$('#myModal-edit').modal('show');
				parentTaskid=document.getElementById("parentTaskid").value;
				taskInfo.showTaskImpl(parentTaskid);
				taskInfo.getParentTask();
			}
		}
	});
};
//获取父任务信息
taskInfo.getParentTask = function () {
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),
			data : {"id": parentTaskid},
			dataType : "json",
			success : function(data) {
				if (data) {
					$('#parentStartTime').val(data.startTime);
					$('#parentEndTime').val(data.endTime);
				}
			}
		});
};
//校验子任务的开始、结束时间是否在父任务范围内
taskInfo.checkParentDate=function(){
	var reVal=1;
	if(parentTaskid!=-1){
		var parentStartTime=$('#parentStartTime').val();
		var parentEndTime=$('#parentEndTime').val();
		var updateStartTime=$('#updateStartTime').val();
		var updateEndTime=$('#updateEndTime').val();
		var ss=taskInfo.compareDate(updateStartTime,parentStartTime);
		var tt=taskInfo.compareDate(parentEndTime,updateEndTime);
		if(ss==false || tt==false){
			reVal=0;
		}
	}
	return reVal;
};
//js日期比较(yyyy-mm-dd)
taskInfo.compareDate=function(a, b) {
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
//保存模板
taskInfo.tempAddTask = function () {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	$("#status").val("5");
	if ($("#updateWorkTask").valid()) {
		taskInfo.remindDefault();
		var re=taskInfo.checkParentDate();
		if(re==0){
			taskInfo.subState = false;
			msgBox.tip({
				type:"fail",
				content: "开始、结束时间应在父任务范围内"
			});
			return;
		}
		var url = getRootPath()+"/po/workTask/update.action?time=" + new Date();
		var formData = $("#updateWorkTask").serializeArray();
		formData.push({"name": "reportTime", "value": $("#hbsx-rq").val()});
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		formData.push({"name": "remindContent", "value": $("#remindCon").val()});
		formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
		taskInfo.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				taskInfo.subState = false;
				if(data.success == "true"){
					if ($("#id").val() == 0) {
						taskInfo.initForm();
					}
					msgBox.tip({
						type:"success",
						content: $.i18n.prop("JC_SYS_001")
					});
				} else {
					taskInfo.status=0;
					taskInfo.subState = false;
					if(data.labelErrorMessage){
						msgBox.info({
							type:"fail",
							content: data.labelErrorMessage
						});
					}else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				taskInfo.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//修改方法
taskInfo.savaOrModify = function (hide,status) {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	if(status==null || status==''){
		status='0';
	}
	$("#status").val(status);
	if ($("#updateWorkTask").valid()) {
		var re=taskInfo.compareStaAndEnd();//汇报时限天数校验
		if(re==1){
			msgBox.info({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_044")
			});
			taskInfo.subState = false;
			return;
		}
		taskInfo.remindDefault();
		var re=taskInfo.checkParentDate();
		if(re==0){
			taskInfo.subState = false;
			msgBox.tip({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_026")
			});
			return;
		}
		if(taskInfo.operate($("#updateWorkTask input[id=id]").val(),'1')==0){//操作校验（修改）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var isRemind=taskInfo.isRemind();
		if(status=='8'){//暂存任务不进行超期校验
			isRemind=true;
		}
		if(isRemind==false){
			var opt={
				 content: $.i18n.prop("JC_OA_PO_034"),
					success: function(){
						//暂存任务保存时，查询其是否存在父任务 start
						if(status=='0' && taskInfo.getParentTaskForZC($('#id').val())==1){
							var opt={
									content: $.i18n.prop("JC_OA_PO_055"),
									success: function(){
										var url = getRootPath()+"/po/workTask/update.action?time=" + new Date();
										var formData = $("#updateWorkTask").serializeArray();
										formData.push({"name": "reportTime", "value": $("#hbsx-rq").val()});
										formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
										formData.push({"name": "remindContent", "value": $("#remindCon").val()});
										formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
										taskInfo.addFormParameters(formData);
										jQuery.ajax({
											url : url,
											type : 'POST',
											cache: false,
											contentType: "application/json;charset=UTF-8",
											data : JSON.stringify(serializeJson(formData),replaceJsonNull),
											success : function(data) {
												$("#token").val(data.token);//性能优化
												taskInfo.subState = false;
												if(data.success == "true"){
													if ($("#id").val() == 0) {
														taskInfo.initForm();
													}
													if (hide) {
														$('#myModal-edit').modal('hide');
													}
													msgBox.tip({
														type:"success",
														content: data.successMessage,
														callback:taskInfo.callMathed()
													});
												} else {
													taskInfo.subState = false;
													if(data.labelErrorMessage){
														msgBox.info({
															type:"fail",
															content: data.labelErrorMessage
														});
													}else {
														msgBox.info({
															type:"fail",
															content: data.errorMessage
														});
													}
												}
											},
											error : function() {
												taskInfo.subState = false;
												msgBox.tip({
													content: $.i18n.prop("JC_SYS_002")
												});
											}
										});
									},
									cancel:function(){
										taskInfo.subState = false;
									}
							}
							msgBox.confirm(opt);
						}
						//暂存任务保存时，查询其是否存在父任务 end
						else{
							var url = getRootPath()+"/po/workTask/update.action?time=" + new Date();
							var formData = $("#updateWorkTask").serializeArray();
							formData.push({"name": "reportTime", "value": $("#hbsx-rq").val()});
							formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
							formData.push({"name": "remindContent", "value": $("#remindCon").val()});
							formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
							taskInfo.addFormParameters(formData);
							jQuery.ajax({
								url : url,
								type : 'POST',
								cache: false,
								contentType: "application/json;charset=UTF-8",
								data : JSON.stringify(serializeJson(formData),replaceJsonNull),
								success : function(data) {
									$("#token").val(data.token);//性能优化
									taskInfo.subState = false;
									if(data.success == "true"){
										if ($("#id").val() == 0) {
											taskInfo.initForm();
										}
										if (hide) {
											$('#myModal-edit').modal('hide');
										}
										msgBox.tip({
											type:"success",
											content: data.successMessage,
											callback:taskInfo.callMathed()
										});
									} else {
										taskInfo.subState = false;
										if(data.labelErrorMessage){
											msgBox.info({
												type:"fail",
												content: data.labelErrorMessage
											});
										}else {
											msgBox.info({
												type:"fail",
												content: data.errorMessage
											});
										}
									}
								},
								error : function() {
									taskInfo.subState = false;
									msgBox.tip({
										content: $.i18n.prop("JC_SYS_002")
									});
								}
							});
						}
					},
					cancel:function(){
						taskInfo.subState = false;
					}
			}
			msgBox.confirm(opt);
		}else{
			//暂存任务保存时，查询其是否存在父任务 start
			if(status=='0' && taskInfo.getParentTaskForZC($('#id').val())==1){
				var opt={
						content: $.i18n.prop("JC_OA_PO_055"),
						success: function(){
							var url = getRootPath()+"/po/workTask/update.action?time=" + new Date();
							var formData = $("#updateWorkTask").serializeArray();
							formData.push({"name": "reportTime", "value": $("#hbsx-rq").val()});
							formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
							formData.push({"name": "remindContent", "value": $("#remindCon").val()});
							formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
							taskInfo.addFormParameters(formData);
							jQuery.ajax({
								url : url,
								type : 'POST',
								cache: false,
								contentType: "application/json;charset=UTF-8",
								data : JSON.stringify(serializeJson(formData),replaceJsonNull),
								success : function(data) {
									$("#token").val(data.token);//性能优化
									taskInfo.subState = false;
									if(data.success == "true"){
										if ($("#id").val() == 0) {
											taskInfo.initForm();
										}
										if (hide) {
											$('#myModal-edit').modal('hide');
										}
										msgBox.tip({
											type:"success",
											content: data.successMessage,
											callback:taskInfo.callMathed()
										});
									} else {
										taskInfo.subState = false;
										if(data.labelErrorMessage){
											msgBox.info({
												type:"fail",
												content: data.labelErrorMessage
											});
										}else {
											msgBox.info({
												type:"fail",
												content: data.errorMessage
											});
										}
									}
								},
								error : function() {
									taskInfo.subState = false;
									msgBox.tip({
										content: $.i18n.prop("JC_SYS_002")
									});
								}
							});
						},
						cancel:function(){
							taskInfo.subState = false;
						}
				}
				msgBox.confirm(opt);
			}
			//暂存任务保存时，查询其是否存在父任务 end
			else{
				var url = getRootPath()+"/po/workTask/update.action?time=" + new Date();
				var formData = $("#updateWorkTask").serializeArray();
				formData.push({"name": "reportTime", "value": $("#hbsx-rq").val()});
				formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
				formData.push({"name": "remindContent", "value": $("#remindCon").val()});
				formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
				taskInfo.addFormParameters(formData);
				jQuery.ajax({
					url : url,
					type : 'POST',
					cache: false,
					contentType: "application/json;charset=UTF-8",
					data : JSON.stringify(serializeJson(formData),replaceJsonNull),
					success : function(data) {
						$("#token").val(data.token);//性能优化
						taskInfo.subState = false;
						if(data.success == "true"){
							if ($("#id").val() == 0) {
								taskInfo.initForm();
							}
							if (hide) {
								$('#myModal-edit').modal('hide');
							}
							msgBox.tip({
								type:"success",
								content: data.successMessage,
								callback:taskInfo.callMathed()
							});
						} else {
							taskInfo.subState = false;
							if(data.labelErrorMessage){
								msgBox.info({
									type:"fail",
									content: data.labelErrorMessage
								});
							}else {
								msgBox.info({
									type:"fail",
									content: data.errorMessage
								});
							}
						}
					},
					error : function() {
						taskInfo.subState = false;
						msgBox.tip({
							content: $.i18n.prop("JC_SYS_002")
						});
					}
				});
			}
		}
		
	} else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//删除
taskInfo.deleteInfo = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		msgBox.info({
			content: "请选择要删除的记录"
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			type:"success",
			taskInfo.deleteCallBack(ids);
		}
	});
};
//删除回调方法
taskInfo.deleteCallBack = function(ids) {
	if(taskInfo.operate(ids,'2')==0){//操作校验（删除）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/workTask/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_SYS_005"),
					callback:taskInfo.callMathed()
				});
			}
		}
	});
};
//添加成功清空表单数据
taskInfo.initForm = function(){
	$('#zdsz')[0].reset();
	selectControl.clearValue("director-directorId");
	selectControl.clearValue("sponsor-sponsorId");
	selectControl.clearValue("senderText-applyId");
};

//接收任务-查询
taskInfo.getReceTask = function (id) {
	hideErrorMessage();
	if(taskInfo.operate(id,'3')==0){//操作校验（接收）
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
				$("#token").val(data.tokenTemp);//性能优化
				//清除验证信息
				hideErrorMessage();
				$("#receWorkTaskForm").fill(data);
				$("#reTaskEventTitle").val(data.taskName);
				//实际开始日期限制最大不能超过当前日期 start
				 var nowDateFor='';
				 var now=new Date();
				 y=now.getFullYear();
				 m=now.getMonth()+1;
				 d=now.getDate();
				 m=m<10?"0"+m:m;
				 d=d<10?"0"+d:d;
				 nowDateFor=y+"-"+m+"-"+d;
				$("#nowDate").val(nowDateFor);
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
				$('#actStartTime').val("");
				$('#recrivingTask').modal('show');
			}
		}
	});
};

//接收任务方法
taskInfo.receWorkTask = function (hide) {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	if ($("#receWorkTaskForm").valid()) {
		if(taskInfo.operate($("#receWorkTaskForm input[id=taskId2]").val(),'3')==0){//操作校验（接收）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#receWorkTaskForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		taskInfo.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				taskInfo.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#recrivingTask').modal('hide');
					}
					msgBox.tip({
						type:"success",
						content: $.i18n.prop("JC_SYS_054"),
						callback:taskInfo.callMathed()
					});
				} else {
					if(data.labelErrorMessage){
						showErrors("receWorkTaskForm", data.labelErrorMessage);
					}else {
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				taskInfo.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//延期-申请-查询
taskInfo.getDelayTask = function (id) {
//	getToken();性能优化
	hideErrorMessage();
	if(taskInfo.operate(id,'12')==0){//操作校验（延期申请）
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
				$("#token").val(data.tokenTemp);//性能优化
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
taskInfo.getDelayCheckTask = function (id) {
	hideErrorMessage();
	if(taskInfo.operate(id,'7')==0){//操作校验（延期审批）
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
				$("#token").val(data.tokenTemp);//性能优化
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
taskInfo.getDelayCancelTask = function (id) {
	hideErrorMessage();
	if(taskInfo.operate(id,'14')==0){//操作校验（延期取消）
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
				$("#token").val(data.tokenTemp);//性能优化
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
//计算两个日期的天数（预留方法）
taskInfo.dateDiff=function(sDate1,  sDate2){//sDate1和sDate2是2006-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays;
    aDate  =  sDate1.split("-"); 
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);//转换为12-18-2006格式  
    aDate  =  sDate2.split("-");  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);//把相差的毫秒数转换为天数  
    return  iDays; 
};
//延期-审批未通过
taskInfo.delayChUnPa= function () {
	$("#delayCheckTaskEventType").val("13");
	taskInfo.delayWorkTask(true,1);
};
//延期-审批通过
taskInfo.delayChPa= function () {
	$("#delayCheckTaskEventType").val("7");
	taskInfo.delayWorkTask(true,1);
};
//延期-申请、取消
taskInfo.delayWorkTask = function (hide,cType) {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	var formInputId='';//表单下主键ID
	var opType=0;//功能类别
	var cid='';//关闭DIV层
	var formId='';
	if(cType==1){//审批
		cid='#delay';
		formId='delayCheckForm';
		formInputId='delayCheckTaskId';
		opType=7;
	}else if(cType==2){//取消
		cid='#delayCancel';
		formId='delayCancelForm';
		formInputId='delayCancelTaskId';
		opType=14;
	}else{//申请
		cid='#delayApply';
		formId='delayForm';
		formInputId='delayTaskId';
		opType=12;
	}
	if ($("#"+formId).valid()) {
		if(taskInfo.operate($("#"+formId+" input[id="+formInputId+"]").val(),opType)==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#"+formId).serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		taskInfo.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				taskInfo.subState = false;
				if(data.success == "true"){
					if (hide) {
						$(cid).modal('hide');
					}
					if(cType==0){
						$("#delayDays").val("");
						$("#delayEnddate").val("");
						$("#delayReasons").val("");
					}
					msgBox.tip({
						type:"success",
						content: $.i18n.prop("JC_SYS_054"),
						callback:taskInfo.callMathed()
					});
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
				taskInfo.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//日期加上天数得到新的日期 dateTemp 需要参加计算的日期，days要添加的天数，返回新的日期，日期格式：YYYY-MM-DD
taskInfo.getNewDay= function(dateTemp, days) {
	  var reg1 =  /^\d+$/;
	  if(days.match(reg1) != null){
		  var dateTemp = dateTemp.split("-");
		  var y=dateTemp[0].toString();
		  var m=dateTemp[1].toString();
		  var d=dateTemp[2].toString();
		  var mydate=m+'-'+d+'-'+y;
		  var nDate = new Date(y,(m-1),d);
		  var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);
		  var rDate = new Date(millSeconds);
		  var year = rDate.getFullYear();
		  var month = rDate.getMonth() + 1;
		  month=month<10?"0"+month:month;
		  var date = rDate.getDate();
		  date=date<10?"0"+date:date;
		  return (year + "-" + month + "-" + date);
	  }
};
//催办-查询
taskInfo.getReminders = function (id) {
//	getToken();性能优化
	hideErrorMessage();
	if(taskInfo.operate(id,'6')==0){//操作校验（催办）
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
		cache : false, 
        async : false,
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#token").val(data.tokenTemp);//性能优化
				$("#remindersTaskId").val(id);
				$("#remindEventTitle").val(data.taskName);
				$("#remindContent").val(data.content);
				$("#remindersCo").val("");
				if(data.prticipantsId!=null){
					$("#receivePersonIds").val(data.directorId+','+data.prticipantsId);
				}else{
					$("#receivePersonIds").val(data.directorId);
				}
				$('#reminders').modal('show');
			}
		}
	});
};
//催办-保存时进行校验
taskInfo.remindersValid= function (hide) {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	if ($("#remindersForm").valid()) {
		if(taskInfo.operate($("#remindersForm input[id=remindersTaskId]").val(),'6')==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		//如果催办时，发送短信，则先对发件人或收件人的短信参数进行校验
		var reminder = $('#remindersForm input[name="remindType"]:checked').val();
		if(reminder=='1'){//是短信
			taskInfo.validRemind($("#receivePersonIds").val(),hide);	
		}else{//邮件
			taskInfo.remindersWorkTask(hide);
		}
	}else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//催办-保存
taskInfo.remindersWorkTask=function(hide){
	var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
	var formData = $("#remindersForm").serializeArray();
	formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
	taskInfo.addFormParameters(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		cache: false,
		contentType: "application/json;charset=UTF-8",
		data : JSON.stringify(serializeJson(formData),replaceJsonNull),
		success : function(data) {
			taskInfo.subState = false;
			if(data.success == "true"){
				if (hide) {
					$('#reminders').modal('hide');
				}
				$("#remindersCo").val("");
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_SYS_054"),
					callback:taskInfo.callMathed()
				});
			} else {
				if(data.labelErrorMessage){
					showErrors("remindersForm", data.labelErrorMessage);
				} else {
					msgBox.info({
						type:"fail",
						content: data.errorMessage
					});
				}
			}
		},
		error : function() {
			taskInfo.subState = false;
			msgBox.tip({
				type:"fail",
				content: $.i18n.prop("JC_SYS_055")
			});
		}
	});
};
//取消-查询
taskInfo.getCancel = function (id) {
	hideErrorMessage();
	if(taskInfo.operate(id,'8')==0){//操作校验（取消）
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
				$("#token").val(data.tokenTemp);//性能优化
				$("#cancelTaskId").val(id);
				$("#cancelEventTitle").val(data.taskName);
				$("#cancelContent").val(data.content);
				$("#cancelReason").val("");
				if(data.prticipantsId!=null){
					$("#cancelReceivePersonIds").val(data.directorId+','+data.prticipantsId);
				}else{
					$("#cancelReceivePersonIds").val(data.directorId);
				}
				$('#cancel').modal('show');
			}
		}
	});
};
//取消-保存 校验
taskInfo.cancelWorkTaskValid = function (hide) {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	if ($("#cancelForm").valid()) {
		if(taskInfo.operate($("#cancelForm input[id=cancelTaskId]").val(),'8')==0){//操作校验（预留）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		//如果催办时，发送短信，则先对发件人或收件人的短信参数进行校验
		var reminder = $('#cancelForm input[name="remindType"]:checked').val();
		if(reminder=='1'){//是短信
			taskInfo.validRemindForCancel($("#cancelReceivePersonIds").val(),hide);	
		}else{//邮件
			taskInfo.cancelWorkTask(hide);
		}
	} else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//取消-保存
taskInfo.cancelWorkTask = function (hide) {
	var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
	var formData = $("#cancelForm").serializeArray();
	formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
	taskInfo.addFormParameters(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		cache: false,
		contentType: "application/json;charset=UTF-8",
		data : JSON.stringify(serializeJson(formData),replaceJsonNull),
		success : function(data) {
			taskInfo.subState = false;
			if(data.success == "true"){
				if (hide) {
					$('#cancel').modal('hide');
				}
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_SYS_054"),
					callback:taskInfo.callMathed()
				});
			} else {
				if(data.labelErrorMessage){
					showErrors("cancelForm", data.labelErrorMessage);
				}else {
					msgBox.info({
						type:"fail",
						content: data.errorMessage
					});
				}
			}
		},
		error : function() {
			taskInfo.subState = false;
			msgBox.tip({
				type:"fail",
				content: $.i18n.prop("JC_SYS_055")
			});
		}
	});
};
//汇报-赋值
taskInfo.getReport = function (id) {
	hideErrorMessage();
	if(taskInfo.operate(id,'5')==0){//操作校验（汇报）
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
				$("#token").val(data.tokenTemp);//性能优化
				$("#reportTaskId").val(id);
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
				$('#report').modal('show');
			}
		}
	});
};
//汇报-保存
taskInfo.reportWorkTask = function (hide) {
	if (taskInfo.subState)return;
	taskInfo.subState = true;
	if ($("#reportForm").valid()) {
		if(taskInfo.operate($("#reportForm input[id=reportTaskId]").val(),'5')==0){//操作校验（汇报）
			msgBox.info({
				type:"fail",
				content:$.i18n.prop("JC_SYS_032")
			});
			return;
		}
		var url = getRootPath()+"/po/workTaskHistory/save.action?time=" + new Date();
		var formData = $("#reportForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		taskInfo.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				taskInfo.subState = false;
				if(data.success == "true"){
					if (hide) {
						$('#report').modal('hide');
					}
					$('#reportProc').val("");
					$('#reportContent').val("");
					msgBox.tip({
						type:"success",
						content: $.i18n.prop("JC_SYS_054"),
						callback:taskInfo.callMathed()
					});
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
				taskInfo.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	} else {
		taskInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//任务统计
taskInfo.taskTatalLink=function () {
	loadrightmenu('/po/workTask/queryStatistics.action','任务统计');
};
//根据任务名称查询任务(未完成)
taskInfo.getWorkTaskInfo=function (id) {
	if(taskInfo.operate(id,'0')==1){//操作校验（查询）
		loadrightmenu('/po/workTask/getWorkTaskInfo.action?id='+id+'&time=' + new Date());
	}else{
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_OA_COMMON_014")
		});
	}
};
//根据任务名称查询任务(完成)
taskInfo.getWorkTaskFiniInfo=function (id) {
	loadrightmenu('/po/taskfinish/getWorkTaskInfo.action?id='+id+'&time=' + new Date());
};
//已发任务
taskInfo.backTable = null;
taskInfo.getWorkTask=function(){
	taskInfo.backFnServerParams = function(aoData){
		 getTableParameters(taskInfo.backTable, aoData);
		 aoData.push({ "name": "createUser", "value": currentUserId});//任务模板查询创建人为当前登录人
		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	taskInfo.backColumns = [
	    {mData: "taskName",mRender : function(mData, type, full) {
			return "<label class=\"radio inline\"><input type=\"radio\" name=\"optionsRadios\"  onclick=\"taskInfo.assignment('"+full.id+"','')\" /></label>";
	    }},           
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
       		return "<a href=\"#\" onclick=\"taskInfo.deleteWorkTask('"+full.id+"','1')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (taskInfo.backTable == null) {
		taskInfo.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskInfo.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				taskInfo.backFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		taskInfo.backTable.fnDraw();
	}
};
//暂存任务
taskInfo.backTableZC = null;
taskInfo.getWorkTaskZC=function(){
	taskInfo.backFnServerParamsZC = function(aoData){
		 getTableParameters(taskInfo.backTableZC, aoData);
		 aoData.push({ "name": "createUser", "value": currentUserId});//任务模板查询创建人为当前登录人
		 aoData.push({ "name": "status", "value": "5"});
		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	taskInfo.backColumnsZC = [
	    {mData: "taskName",mRender : function(mData, type, full) {
	    	return "<label class=\"radio inline\"><input type=\"radio\" name=\"optionsRadiosZc\"  onclick=\"taskInfo.assignment('"+full.id+"','5')\" /></label>";
		}},            
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
			return "<a href=\"#\" onclick=\"taskInfo.deleteWorkTask('"+full.id+"','0')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (taskInfo.backTableZC == null) {
		taskInfo.backTableZC = $('#workTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskInfo.backColumnsZC,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				taskInfo.backFnServerParamsZC(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		taskInfo.backTableZC.fnDraw();
	}
};
//提取任务模板中任务名称
taskInfo.assignment=function(t,s){
	$("#taskNameVal").val(t);
	if(s!=null && s!=""){
		$("#taskStatus").val(s);
	}else{
		$("#taskStatus").val("");
	}
};
//提取任务模板中任务名称
taskInfo.getTaskName=function (){
	var taskIdTemp=$("#taskNameVal").val();
	var taskSta=$("#taskStatus").val();
	if(taskIdTemp==null || taskIdTemp==''){
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_029","任务模板")
		});
		return false;
	}
	if(null!=taskIdTemp){
		var dataTemp;
		if(taskSta=='5'){
			dataTemp={"id": taskIdTemp,"status":'5'};
		}else{
			dataTemp={"id": taskIdTemp};
		}
		$("#taskStatus").val("");
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),
			data : dataTemp,
			dataType : "json",
			success : function(data) {
				if (data) {
					$("#modifyTaskName").val(data.taskName);
					$("#taskOrigin").val(data.taskOrigin);
					//人员选择树--发起人
					$("#sponsors-sponsorId").select2("data", {id:data.sponsorId,text:data.sponsor});				
					//人员选择树--负责人
					$("#directors-directorId").select2("data", {id:data.directorId,text:data.director});					
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
						$("#prt-prticipantsId").select2("data", eval(objs));
					}else{
						selectControl.clearValue("prt-prticipantsId");
					}
					$("#taskContent").val(data.content);
					$("#standard").val(data.standard);
					$("#startTime").val(data.startTime);
					$("#endTime").val(data.endTime);
				}
			}
		});
	}
	$('#new-agency').modal('hide');
};
//取消任务模板
taskInfo.deleteWorkTask=function(t,dType){
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
							taskInfo.subState=false;
							if (data > 0) {
								msgBox.tip({
									type:"success",
									content:$.i18n.prop("JC_SYS_005")
								});
							}
							if(dType==0){
								taskInfo.getWorkTaskZC();
							}else{
								taskInfo.getWorkTask();
							}
						}
					});
			},
			cancel:function(){
				taskInfo.subState=false;
			}
		}
		msgBox.confirm(opt);
	}
};
//选择计划
taskInfo.getPlan=function(){
	planRowDetail.resetPlanBoxList();
	//显示计划弹出层内容
	$('#viewPlan').modal('show');
	//初始化查看任务弹出层列表
	planRowDetail.planRowDetailInit();
};
//下发任务
taskInfo.toNextTask=function(t){
	if(taskInfo.operate(t,'15')==0){//操作校验（下发任务）
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_032")
		});
		return;
	}
	loadrightmenu('/po/workTask/queryNextTask.action?id='+t);
};
//提醒设置(修改)
taskInfo.showRemind= function(){
	$(document).showRemind_({tableName:'1',remind:para_week,callback:function(r) {para_week = r;
		$("#remind").val(r);
	}});
};
//提醒设置(不接收任务)
taskInfo.showRemindForNotRe= function(){
	$(document).showRemind_({tableName:'1',readonly:true,remind:$("#notReRemind").val(),callback:function(r) {para_week = r;
		$("#notReRemind").val(r);
	}});
};
//提醒设置(接收任务)
taskInfo.showRemindForRe= function(){
	$(document).showRemind_({tableName:'1',readonly:true,remind:$("#reRemind").val(),callback:function(r) {para_week = r;
		$("#reRemind").val(r);
	}});
};
//添加附件
taskInfo.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name":"fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};
//点击关闭附件弹出层是清空内容
taskInfo.fileupload = function (){
//	clearTable();
	$("#businessId").val("0");
//	showAttachList(false);
	showAttachList(true);
};
//点击关闭时删除一上传文件
taskInfo.deleteAttach = function() {
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
//清除汇报时限中天数
taskInfo.clearDays=function(){
	if($("#hq-day").val() ==0){
		$("#hq-day").val("");
	}
};
//添加汇报时限中天数
taskInfo.addDays=function(){
	if($("#hq-day").val()==null || $("#hq-day").val() ==''){
		$("#hq-day").val(0);
	}
};
//汇报时限--日期
taskInfo.addDate=function(){
	var today = new Date();
	var day   = today.getDate(); 
	var month = today.getMonth() + 1;
	var year  = today.getFullYear(); 
	month     = month<10?"0"+month:month; 
	day       = day<10?"0"+day:day; 
	var date  = year + "-" + month + "-" + day;
	$("#hbsx-rq").val(date);
};
//任务名称显示长度
taskInfo.taskNameLength=function(tVal){
	var mDataTemp="";
	if(tVal !=null && tVal !=""){
		if(tVal.length>=8){
	    	mDataTemp=tVal.substr(0, 6)+"...";
	    }else{
	    	mDataTemp=tVal;
	    }
	}
    return mDataTemp;
};
//关闭任务模板
taskInfo.closeWin=function(){
	$("#taskNameVal").val("");
	taskInfo.getWorkTask();
	taskInfo.getWorkTaskZC();
};
//返回按钮--任务状态反显
taskInfo.recheckBox=function(){
	if(status0==1){
		$("#status0").attr("checked",true);
	}else{
		$("#status0").removeAttr("checked");  
	}
	if(status1==1){
		$("#status1").attr("checked",true);
	}else{
		$("#status1").removeAttr("checked");  
	}
	if(status2==1){
		$("#status2").attr("checked",true);
	}else{
		$("#status2").removeAttr("checked");  
	}
	if(status3==1){
		$("#status3").attr("checked",true);
	}else{
		$("#status3").removeAttr("checked");  
	}
	if(status4==1){
		$("#status4").attr("checked",true);
	}else{
		$("#status4").removeAttr("checked");  
	}
	if(status6==1){
		$("#status6").attr("checked",true);
	}else{
		$("#status6").removeAttr("checked");  
	}
	if(status7==1){
		$("#status7").attr("checked",true);
	}else{
		$("#status7").removeAttr("checked");  
	}
};
//自动为任务名称及计划开始时间赋值
taskInfo.autoValue=function(){
	if(queryTaskName !=null){
		$("#taskName").val(queryTaskName);
	}
	if(qStartTime!=null){
		$("#queryStartTime").val(qStartTime);	
	}
	if(qEndTime!=null){
		$("#queryEndTime").val(qEndTime);
	}
};
//用于修改页面中任务重要程度显示
taskInfo.showTaskImpl=function(t){
	if(t==-1){
		$("#sele").css('display','none'); 
		$("#inpu").css('display','block'); 
	}else{
		$("#inpu").css('display','none'); 
		$("#sele").css('display','block'); 
	}
};
//超期提醒设置预览
taskInfo.showRemindSet=function(){
	var t=$('#updateWorkTask input[name="remindType"]:checked').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#optionsRadios2').val(s);//提醒方式
	var directorId=returnValue('directors-directorId');
	var prticipantsId=returnValue('prt-prticipantsId');
	var dire=$('#directors-directorId').val();
	var prti=$('#prt-prticipantsId').val();
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
		$("#remindCon").attr("disabled",false);
		if(isSave==0){
			$('#remindCon').val($('#modifyTaskName').val());//提醒内容
		}else{
			if($('#remindCon').val()==null || $('#remindCon').val()==''){
				$('#remindCon').val($('#modifyTaskName').val());//提醒内容
				isSave=0;
			}
		}
		$('#remindPerId').val(per);
		$('#remindPerName').val(perId);
		$('#remindContentTemp').val($('#remindCon').val());
	}else{
		$('#remindCon').val("");
		$('#remindPerId').val("");
		$('#remindPerName').val("");
		$('#remindContentTemp').val("");
		$("#remindCon").attr("disabled",true);
		isSave=0;
	}
	$('#remindWindow').modal('show');
};
//超期提醒-保存
taskInfo.showRemindSetSave=function(){
	var t=$('#updateWorkTask input[name="remindType"]:checked').val();
	if(t!=0){
		if($('#remindCon').val()==null || $('#remindCon').val()==''){
			isSave=0;
		}else{
			$('#remindContentTemp').val($('#remindCon').val());
			isSave=1;
		}
	}
};
//超期提醒-关闭
taskInfo.showRemindSetClose=function(){
	var t=$('#updateWorkTask input[name="remindType"]:checked').val();
	if(t!=0){
		if(isSave==1){
			$('#remindCon').val($('#remindContentTemp').val());
		}else{
			$('#remindCon').val($('#modifyTaskName').val());
		}
	}else{
		$('#remindCon').val($('#modifyTaskName').val());
	}
};
//超期提醒默认设置
taskInfo.remindDefault=function(){
	var t=$('#updateWorkTask input[name="remindType"]:checked').val();
	if(t!=0){
		var dire=$('#directors-directorId').val();
		var prti=$('#prt-prticipantsId').val();
		if($('#remindPerId').val()==null || $('#remindPerId').val()==''){
			$('#remindPerId').val(dire+','+prti);
		}
		if($('#remindCon').val()==null || $('#remindCon').val()==''){
			$('#remindCon').val($('#modifyTaskName').val());
		}
	}else{
		$('#remindPerId').val("");
		$('#remindCon').val("");
	}
};
//超期提醒设置预览--接收任务
taskInfo.showRemindSetForJC=function(){
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
	var directorId=$('#director').val();
	var prticipantsId=$('#prticipants').val();
	if(t!=0){
		$('#jcRemindCon').val($('#retaskName').val());//提醒内容
		$('#jcRemindPerName').val(directorId+','+prticipantsId);
	}else{
		$('#jcRemindCon').val("");
		$('#jcRemindPerName').val("");
	}
	$('#remindWindowForRe').modal('show');
};
//超期提醒设置预览--不接收任务
taskInfo.showRemindSetForNJ=function(){
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
	var directorId=$('#noDirector').val();
	var prticipantsId=$('#noPrticipants').val();
	if(t!=0){
		$('#njRemindCon').val($('#noRetaskName').val());//提醒内容
		$('#njRemindPerName').val(directorId+','+prticipantsId);
	}else{
		$('#njRemindCon').val("");
		$('#njRemindPerName').val("");
	}
	$('#remindWindowForNR').modal('show');
};
/**
 * 导入计划
 */
taskInfo.planBoxFillData=function() {
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
//接收任务关闭
taskInfo.closeReceTask=function(){
	taskInfo.clear();
};
//超期校验
taskInfo.isRemind=function(){
	var endDate=$("#updateEndTime").val();//结束日期
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
	return taskInfo.compareDate(endDate,nowDate);
};
//操作事件校验
taskInfo.operate=function(taskId,operateType){
	var reVal=0;
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/workTask/checkOperation.action?time=" + new Date(),
		data : {"taskId": taskId,"operateType":operateType},
		dataType : "json",
        async : false,
		success : function(data) {
			taskInfo.subState=false;
			if(data>0){
				reVal=1;
			}
		},
		error : function() {
			taskInfo.subState = false;
		}
	});
	return reVal;
};
//tab点击返回事件
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	var dataId = $(e.target).attr("data-id");
	//发起人
	if(dataId == "planListMsg"){
		//计算分页显示条数
		taskInfo.pageRows = TabNub>0 ? TabNub : 10;
		//进入页面后默认查询所有信息
		taskInfo.infoList();
		//清空编制查询区域
		taskInfo.clearForm();
		taskInfo.callMathedVal=0;
	//负责人
	}else if(dataId == "sendListMsg"){
		//计算分页显示条数
		taskInfo.pageRows = TabNub>0 ? TabNub : 10;
		//进入页面后默认查询所有信息
		taskInfo.infoList_fzr();
		taskInfo.clearForm_fzr();
		taskInfo.callMathedVal=1;
	//暂存	
	}else if(dataId == "tempListMsg"){
		//计算分页显示条数
		taskInfo.pageRows = TabNub>0 ? TabNub : 10;
		//进入页面后默认查询所有信息
		taskInfo.infoList_zc();
		taskInfo.clearForm_zc();
		taskInfo.callMathedVal=2;
	}else {
		//进入页面后默认查询所有信息
		taskInfo.infoList();
		taskInfo.callMathedVal=0;
	}
	$('#zdsz-fqr')[0].reset();
	$('#zdsz-fzr')[0].reset();
	$('#zdsz-zc')[0].reset();
	$("#status00").removeAttr("checked"); //当切换到其他tab页时，取消“未接收” 被选中
});
//用于操作项回调时方法调用；0：发起人；1：负责人；2：暂存
taskInfo.callMathed=function(){
	if(taskInfo.callMathedVal==0){
		return taskInfo.infoList();
	}else if(taskInfo.callMathedVal==1){
		return taskInfo.infoList_fzr();
	}else{//暂存
		return taskInfo.infoList_zc();
	}
};
/**校验短信提醒*/
taskInfo.validRemind = function(userIds,hide){
		jQuery.ajax({
			url : getRootPath()+"/po/diary/validRemind.action?time=" + new Date(),
			type : 'get',
			async: false,
			dataType : "json",
			data : {'agentId':userIds},
			success : function(data) {
				if(data.success=="success"){
					taskInfo.remindersWorkTask(hide);
				}else{
					if(data.success){
						msgBox.confirm({
							content: data.successMessage,
							success: function(){
								taskInfo.remindersWorkTask(hide);
							},
							cancel:function(){
							}
						});
						
					}else{
						msgBox.info({
							content: data.errorMessage
						});
					} 
				}
			},
			error : function() {
				msgBox.tip({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_015"),
					callback:function(){
					}
				});
			}
		});
};
/**校验短信提醒*/
taskInfo.validRemindForCancel = function(userIds,hide){
		jQuery.ajax({
			url : getRootPath()+"/po/diary/validRemind.action?time=" + new Date(),
			type : 'get',
			async: false,
			dataType : "json",
			data : {'agentId':userIds},
			success : function(data) {
				if(data.success=="success"){
					taskInfo.cancelWorkTask(hide);
				}else{
					if(data.success){
						msgBox.confirm({
							content: data.successMessage,
							success: function(){
								taskInfo.cancelWorkTask(hide);
							},
							cancel:function(){
							}
						});
						
					}else{
						msgBox.info({
							content: data.errorMessage
						});
					} 
				}
			},
			error : function() {
				msgBox.tip({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_015"),
					callback:function(){
					}
				});
			}
		});
};
//用于汇报提醒中短信提醒判断
taskInfo.remindMessage=function(){
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
taskInfo.compareStaAndEnd=function(){
	var reVal=0;
	var startTime=$('#updateStartTime').val();
	var endTime=$('#updateEndTime').val();
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
//暂存任务保存时，查询其父子关系
taskInfo.getParentTaskForZC = function (id) {
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
	//如果是从首页的统计数直接进入页面，那么页面锁定在负责人tab页中，同时查询条件“未接收”框 被选中  xuweiping 2014.12.08
//	if($("#fromPortal").val() == "yes"){
//		$("#status00").attr("checked",'checked');   
//	}
	//清空（接收任务表单）
	taskInfo.clear();
	//计算分页显示条数
	taskInfo.pageRows = TabNub>0 ? TabNub : 10;
	//计算分页显示条数
	taskInfo.pageCount = TabNub>0 ? TabNub : 10;
	//查询-发起人
	$("#queryInfo").click(function(){taskInfo.infoList();});
	//查询-负责人
	$("#queryInfo-fzr").click(function(){taskInfo.infoList_fzr();});
	//查询-暂存
	$("#queryInfo-zc").click(function(){taskInfo.infoList_zc();});
	//重置-发起人
	$("#resetInfo").click(function(){taskInfo.clearForm();});
	//查询-负责人
	$("#queryInfo-fzr").click(function(){taskInfo.infoList_fzr();});
	//重置-负责人
	$("#resetInfo-fzr").click(function(){taskInfo.clearForm_fzr();});
	//重置-暂存
	$("#resetInfo-zc").click(function(){taskInfo.clearForm_zc();});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//进入页面后默认查询所有信息
	//如果是从首页的统计数直接进入页面，那么页面锁定在负责人tab页中，同时查询条件“未接收”框 被选中  xuweiping 2014.12.08
	if($("#fromPortal").val() == "yes"){
		taskInfo.infoList_fzr();
		taskInfo.clearForm_fzr();
		taskInfo.callMathedVal=1;
	}else{
		taskInfo.infoList();
	}
	//不接收任务
	$("#notAccepted").click(function(){taskInfo.notRrce(true);});
	//接收任务
	$("#receWorkTask").click(function(){taskInfo.receWorkTask(true);});
	//编辑信息
	$("#updateTaskInfo").click(function(){taskInfo.savaOrModify(true,'0');});
	//暂存
	$("#updateTempTask").click(function(){taskInfo.savaOrModify(true,'8');});
	//延期天数--申请
	$("#delayDays").change(function(){
		    var delayEndTemp=document.getElementById("delayEndTime").value;
		    var delayDaysTemp=document.getElementById("delayDays").value;
		    var newDate=taskInfo.getNewDay(delayEndTemp,delayDaysTemp);
		    $("#delayEnddate").val(newDate);
	});
	//延期申请
	$("#delaySure").click(function(){taskInfo.delayWorkTask(true,0);});
	//延期审批通过
	$("#delayPass").click(function(){taskInfo.delayChPa();});
	//延期审批未通过
	$("#delayNotPass").click(function(){taskInfo.delayChUnPa();});
	//延期取消
	$("#delayCanSure").click(function(){taskInfo.delayWorkTask(true,2);});
	//催办
	$("#remindersSure").click(function(){taskInfo.remindersValid(true);});
	//取消
	$("#cancelSure").click(function(){taskInfo.cancelWorkTaskValid(true);});
	//汇报
	$("#reportSure").click(function(){taskInfo.reportWorkTask(true);});
	//任务统计
	$("#taskTatalLink").click(function(){taskInfo.taskTatalLink();});
	//汇报时限--修改
	$('input:radio[name="hbsx"]').click(function(){taskInfo.showCont();});
	//暂存--修改
	$("#tempTask").click(function(){taskInfo.tempAddTask();});
	//提取任务模板中任务名称
	$("#extractButtion").click(function(){taskInfo.getTaskName();});
	//选择工作计划
	$("#selectPlan").click(function(){taskInfo.getPlan();});
	//提醒按钮事件绑定
	$("#remindSet").click(function(){taskInfo.showRemind();});
	//人员选择树--负责人--查询
	selectControl.init("showDirectorIdTree","director-directorId", false, true, null, {id:queryDirectorId,text:queryDirector});
	//人员选择树--发起人--查询
	selectControl.init("showSponsorTree","sponsor-sponsorId", false, true, null, {id:querySponsorId,text:querySponsor});
	//返回按钮--任务状态反显
	taskInfo.recheckBox();
	//自动为任务名称及计划开始时间赋值
	taskInfo.autoValue();
	//绑定查看计划弹出层查询按钮事件(查看计划)
	$("#queryPlanBox").click(function(){planRowDetail.planRowDetailList();});
	//绑定查看计划弹出层重置按钮事件(查看计划)
	$("#resetPlanBox").click(function(){planRowDetail.resetPlanBoxList();});
	//绑定导入计划按钮事件(查看计划)
	$("#importContentBtn").click(function(){taskInfo.planBoxFillData();});
	//接收任务表单 关闭按钮
	$("#closeReceTask").click(function(){taskInfo.closeReceTask();});
	//隐藏错误信息
	hideErrorMessage();
	//附件使用
	$("#closebtn").click(function(){taskInfo.fileupload();});
	$("#closeModalBtn").click(function(){taskInfo.fileupload();});
	$("#queryattach").click(function(){taskInfo.fileupload();});
	//提醒按钮事件绑定(不接收任务)
	$("#notReRemindSet").click(function(){taskInfo.showRemindForNotRe();});
	//提醒按钮事件绑定(接收任务)
	$("#reRemindSet").click(function(){taskInfo.showRemindForRe();});
	//超期提醒保存
	$("#remindSave").click(function(){taskInfo.showRemindSetSave();});
	//超期提醒关闭
	$("#remindClose").click(function(){taskInfo.showRemindSetClose();});
	//用于汇报提醒中短信提醒判断
	taskInfo.remindMessage();
});