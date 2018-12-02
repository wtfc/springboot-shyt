/**
 * 我的计划js
 */
var myPlan = {};
//定义编制分页变量
myPlan.pageRows = null;
//定义转发分页变量
myPlan.sendPageRows = null;
//重复提交标识
myPlan.subState = false;
//编制分页对象
myPlan.oTable = null;
//转发分页对象
myPlan.oTableSend = null;

//编制显示列信息
myPlan.oTableAoColumns = [
	{mData: "planTitle"},//计划标题
	{mData: "planTypeValue"},//计划类型
	{mData: "flowStatusMsg", "mRender":function(mData, type, full) {
		return full.flowStatusMsg;
	}},//流程状态	
	{mData: "planStartTime"},//计划开始时间
	{mData: "planEndTime"},//计划结束时间
	//设置权限按钮
	{mData:null,"mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.flowStatus = full.flowStatus;
		opt.processStatus = full.processStatus;
		opt.workflowId = full.workflowId;
//		opt.beforeQuery= "myPlan.deleteBusiness";
		opt.showDeleteBtn = true;//是否显示删除按钮
		opt.entrance = "business";
		opt.entranceType = "myBusiness";
		opt.action="/po/plan/getPlan.action";
		opt.deleteFun = "myPlan.deleteBusiness(\'" + eval(full.id) + "\')";
		return getWorkflowListButton(opt) + oTableSetButtons(mData,type,full);
	}}
 ];

//转发显示列信息
myPlan.oTableSendAoColumns = [
	{mData: "planTitle"},//工作计划标题
	{mData: "planTypeValue"},//计划类型
	{mData: "sendIdValue"},//转发人
	{mData: null, "mRender" : function(mData,type,full) {//接收人
		return "<a class=\"dark email-pucker\"href=\"#receiveUserName-list\" role=\"button\" data-toggle=\"modal\" id=\"email-aid\">"+
 		   "<i class=\"fa fa-users blue-dark\" data-toggle=\"tooltip\" data-placement=\"top\"" +
 		   "title=\"\" data-container=\"body\" data-original-title=\"点击查看接收人\" onclick=\"myPlan.tooltip('"+full.sendPlanUserNames+"')\"></i></a>";
	}},
	{mData: "sendPlanState", mRender : function(mData) {
		return mData == 0 ? "转发" : "接收";
	}},//方式 
	{mData: "planStartTime"},//计划开始时间
	{mData: "planEndTime"},//计划结束时间
	//设置权限按钮
	{mData: null, "mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.flowStatus = full.flowStatus;
		opt.processStatus = full.processStatus;
		opt.workflowId = full.workflowId;
		opt.showDeleteBtn = false;//是否显示删除按钮
		opt.entrance = "business";
		opt.entranceType = "myBusiness";
		opt.action="/po/plan/getPlan.action";
		opt.deleteFun = "myPlan.deleteBusiness(\'" + eval(full.id) + "\')";
		return getWorkflowListButton(opt) + oTableSendSetButtons(mData,type,full);
	}}
 ];

//编制组装后台参数
myPlan.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(myPlan.oTable, aoData);
	//组装查询条件
	$.each($("#myPlanForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//转发组装后台参数
myPlan.oTableSendFnServerParams = function(aoData){
	//排序条件
	getTableParameters(myPlan.oTableSend, aoData);
	//组装查询条件
	$.each($("#myPlanSendForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//编制tab分页查询方法
myPlan.initList = function () {
	if (myPlan.oTable == null) {
		myPlan.oTable = $('#myPlanTable').dataTable( {
			"iDisplayLength": myPlan.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/manageMyPlanList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": myPlan.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				myPlan.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,5]}]
		} );
	} else {
		//table不为null时重绘表格
		myPlan.oTable.fnDraw();
	}
};

//转发tab分页查询方法
myPlan.initSendList = function () {
	if (myPlan.oTableSend == null) {
		myPlan.oTableSend = $('#sendTable').dataTable( {
			"iDisplayLength": myPlan.sendPageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/manageMyplanSendList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": myPlan.oTableSendAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				myPlan.oTableSendFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,7]}]
		} );
	} else {
		//table不为null时重绘表格
		myPlan.oTableSend.fnDraw();
	}
};

/**
 * 删除方法
 */
myPlan.deleteBusiness = function (id) {
	var ids = String(id);
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			myPlan.deleteCallBack(ids)
		}
	});
};

/**
 * 删除计划回调方法
 */
myPlan.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/plan/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback:myPlan.initList()
				});
			}else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

//tab点击返回事件
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	var dataId = $(e.target).attr("data-id");
	//编制
	if(dataId == "planListMsg"){
		myPlan.pageRows = TabNub > 0 ? TabNub : 10;
		myPlan.initList();
		//清空编制查询区域
		myPlan.queryFormReset(); 
	//转发
	}else if(dataId == "sendListMsg"){
		myPlan.sendPageRows = TabNub > 0 ? TabNub : 10;
		myPlan.initSendList();
		myPlan.querySendFormReset();
		//绑定查询
		$("#myPlanSendQuery").click(myPlan.initSendList);
		//绑定查询
		$("#myPlanSendReset").click(myPlan.querySendFormReset);
	}else {
		myPlan.initList();
	}
})


//清空编制查询区域
myPlan.queryFormReset = function(){
	$('#myPlanForm')[0].reset();
};

//清空转发查询区域
myPlan.querySendFormReset = function(){
	$('#myPlanSendForm')[0].reset();
	//清空转发人人员选择树隐藏域
	selectControl.clearValue("sendSenderId-sendIdsPrimaryKeys");
};

//列表中查看接收人
myPlan.tooltip = function(sendPlanUserNames){
	var usersView = "";
	var users = sendPlanUserNames.split(",");
	if(users.length>0){
		for(var i=0;i<users.length;i++){
			usersView+= "<span class=\"email-f-r\">"+users[i]+"</span>";
		}
		$('#consignee').html(usersView);
	}else{
		$('#consignee').html("<span class=\"email-f-r first-td-tc\">无接收人</span>");
	}
	$('#countNum').html(users.length);
};
//李洪宇 添加 2014-07-23 开始
/**
 * 转发删除
 */
myPlan.deleteForward = function (id) {
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			$.ajax({
				type : "POST",
				url : getRootPath()+"/po/planSend/deleteForward.action",
				data : {"id": id},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox.tip({
							type:"success",
							content: data.successMessage,
							callback:myPlan.initSendList()
						});
					}else {
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
			});
		}
	});
};
//李洪宇 添加 2014-07-23 结束
//李洪宇 添加 2014-07-24 开始
myPlan.initSendUser = function (id,returnType) {
	if(!DeleteCascade.syncCheckExist("planSendAndRead",id)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		$('#showSendUserTree').modal('show');
		//清空人员选择树隐藏域
		selectControl.clearValue("sendApplyId-sendIdsPrimaryKeys");
		//弹出层清空方法
		$("#sendUserTree").empty();
		//计划转发弹出层人员选择树初始化
		selectControl.init("sendUserTree","receiveIds-receiveIds", true, true);
		//解除工作任务按钮绑定事件
		$("#sendPlan").unbind();
		//转发按钮绑定事件
		$("#sendPlan").click(function(){myPlan.insertSendUser(id,returnType);});
	}
};

/**
 * 转发人员确定方法
 */
myPlan.insertSendUser = function (id,returnType){
	if(!DeleteCascade.syncCheckExist("planSendAndRead",id)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		if($("#receiveIds-receiveIds").val() == ""){
			msgBox.tip({
				type : "fail",
				content : $.i18n.prop("JC_OA_PO_030"),
			})
		}else{
//			var callFunction='';
//			if(returnType=='1'){//转发
//				callFunction=myPlan.initSendList();
//			}else{//编制
//				callFunction=myPlan.initList();
//			}
			//转发人员名称集合获取
			var sendPlanUserNamesTemp = returnValue("receiveIds-receiveIds");
			if(sendPlanUserNamesTemp != null){
				var sendPlanUserNames = "";
				var temp = sendPlanUserNamesTemp.split(",");
				for(var i=0; i<temp.length;i++){
					sendPlanUserNames += (temp[i].split(":")[1]+",");
				}
			};
			var url = getRootPath()+"/po/planSend/save.action?time=" + new Date();
			var formData = $("#sendUserForm").serializeArray();
			formData.push({"name": "planId", "value": id});
			formData.push({"name": "sendPlanUserNames", "value": sendPlanUserNames.substring(0, sendPlanUserNames.length-1)});
			if(returnType=='1'){//转发
				jQuery.ajax({
					url : url,
					type : 'POST',
					data : formData,
					success : function(data) {
						msgBox.tip({
							type:"success",
							content:data.successMessage,
							callback:myPlan.initSendList()
						})
						$('#showSendUserTree').modal('hide');
					}
				});
			}else{//编制
				jQuery.ajax({
					url : url,
					type : 'POST',
					data : formData,
					success : function(data) {
						msgBox.tip({
							type:"success",
							content:data.successMessage,
							callback:myPlan.initList()
						})
						$('#showSendUserTree').modal('hide');
					}
				});
			}
//			jQuery.ajax({
//				url : url,
//				type : 'POST',
//				data : formData,
//				success : function(data) {
//					msgBox.tip({
//						type:"success",
//						content:data.successMessage,
//  					callback:callFunction
//					})
//					$('#showSendUserTree').modal('hide');
//				}
//			});
		}
	}
};
//李洪宇 添加 2014-07-24 结束
/**
 * 初始化方法
 */
jQuery(function($){
	//编制计算分页显示条数
	myPlan.pageRows = TabNub > 0 ? TabNub : 10;
	//编制查询按钮绑定事件
	$("#myPlanQuery").click(myPlan.initList);
	//编制重置按钮绑定事件
	$("#myPlanReset").click(myPlan.queryFormReset);
	//初始化转发人人员选择树
	selectControl.init("senderShowUserTree","sendSenderId-sendIdsPrimaryKeys", false, true);	
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化列表
	myPlan.initList();
});