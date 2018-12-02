/**
 * 计划查询js
 */
var planQuery = {};

//定义编制分页变量
planQuery.planPageRows = null;
//定义转发分页变量
planQuery.sendPageRows = null;
//定义汇总分页变量
planQuery.collectPageRows = null;
//定义管理创新分页变量
planQuery.managePageRows = null;
//定义阅读情况分页变量
planQuery.readPageRows = null;
//重复提交标识
planQuery.subState = false;
//编制分页对象
planQuery.oTable = null;
//转发分页对象
planQuery.oTableSend = null;
//汇总分页对象
planQuery.oTableCollect = null;
//管理创新分页对象
planQuery.oTableManage = null;
//阅读情况分页对象
planQuery.oTableRead = null;

//编制显示列信息
planQuery.oTableAoColumns = [
	{mData: "planTitle"},//计划标题
	{mData: "planTypeValue"},//计划类型
	{mData: "applyIdValue"},//申请人
	{mData: "applyDeptidValue"},//申请部门
	{mData: "flowStatusMsg", "mRender" : function(mData, type, full) {
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
		opt.showDeleteBtn = true;//是否显示删除按钮
		opt.entrance = "business";
		opt.entranceType = "myBusiness";
		opt.action="/po/plan/getPlan.action";
		opt.deleteFun = "planQuery.deleteBusiness(\'" + eval(full.id) + "\')";
		return getWorkflowListButton(opt) + oTableSetButtons(mData,type,full);
	}}
 ];

//转发显示列信息
planQuery.oTableSendAoColumns = [
	{mData: "planTitle"},//计划标题
	{mData: "planTypeValue"},//计划类型
	{mData: "applyIdValue"},//申请人
	{mData: "applyDeptidValue"},//申请人部门
	{mData: "sendIdValue"},//接收人
	{mData: null, "mRender" : function(mData,type,full) {//接收人
		return "<a class=\"dark email-pucker\"href=\"#receiveUserName-list\" role=\"button\" data-toggle=\"modal\" id=\"email-aid\">"+
 		   "<i class=\"fa fa-users blue-dark\" data-toggle=\"tooltip\" data-placement=\"top\"" +
 		   "title=\"\" data-container=\"body\" data-original-title=\"点击查看接收人\" onclick=\"planQuery.tooltip('"+full.sendPlanUserNames+"')\"></i></a>";
	}},
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
		opt.deleteFun = "planQuery.deleteBusiness(\'" + eval(full.id) + "\')";
		return getWorkflowListButton(opt) + oTableSendSetButtons(mData,type,full);
	}}
 ];

//汇总显示列信息
planQuery.oTableCollectAoColumns = [
    {mData: "planTitle"},//计划标题
   	{mData: "planTypeValue"},//计划类型
	{mData: "applyIdValue"},//申请人
	{mData: "applyDeptidValue"},//申请部门
	{mData: "planStartTime"},//计划开始时间
	{mData: "planEndTime"},//计划结束时间
	{mData: "nowRangeCompRate"},//当次计划及实际完成百分比
	{mData: "nextRangeCompRate"}//下次计划
 ];

//管理创新显示列信息
planQuery.oTableManageAoColumns = [
	{mData: "planTitle"},//计划标题
	{mData: "planTypeValue"},//计划类型
	{mData: "applyIdValue"},//申请人
	{mData: "applyDeptidValue"},//申请人部门
	{mData: "manaInno"}//管理及创新
 ];

//阅读情况显示列信息
planQuery.oTableReadAoColumns = [
	{mData: "receiveId"},//阅读人
	{mData: "readTimeValue"},//阅读时间
	{mData: "anno.annoName"}//批注
 ];


//编制组装后台参数
planQuery.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(planQuery.oTable, aoData);
	//组装查询条件
	$.each($("#planListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//转发组装后台参数
planQuery.oTableSendFnServerParams = function(aoData){
	//排序条件
	getTableParameters(planQuery.oTableSend, aoData);
	//组装查询条件
	$.each($("#sendListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//汇总组装后台参数
planQuery.oTableCollectFnServerParams = function(aoData){
	//排序条件
	getTableParameters(planQuery.oTableCollect, aoData);
	//组装查询条件
	$.each($("#collectListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//管理创新组装后台参数
planQuery.oTableManageFnServerParams = function(aoData){
	//排序条件
	getTableParameters(planQuery.oTableManage, aoData);
	//组装查询条件
	$.each($("#manageListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//编制tab分页查询方法
planQuery.initList = function () {
	if (planQuery.oTable == null) {
		planQuery.oTable = $('#planQueryList').dataTable( {
			"iDisplayLength": planQuery.planPageRows,//每页显示多少条记录
//			bPaginate:false,//不分页
			"sAjaxSource": getRootPath()+"/po/plan/manageList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": planQuery.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {
				planQuery.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,7]}]
		} );
	} else {
		//table不为null时重绘表格
		planQuery.oTable.fnDraw();
	}
};

//转发tab分页查询方法
planQuery.initSendList = function () {
	planQuery.sendPageRows = TabNub > 0 ? TabNub : 10;
	
	if (planQuery.oTableSend == null) {	
		planQuery.oTableSend = $('#sendListTable').dataTable( {
			"iDisplayLength": planQuery.sendPageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/managePlanSendList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": planQuery.oTableSendAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {
				planQuery.oTableSendFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,8]}]
		} );
	} else {
		//table不为null时重绘表格
		planQuery.oTableSend.fnDraw();
	}
};

//汇总tab分页查询方法
planQuery.initCollectList = function () {
	planQuery.collectPageRows = TabNub > 0 ? TabNub : 10;
	
	if (planQuery.oTableCollect == null) {
		planQuery.oTableCollect = $('#collectListTable').dataTable( {
			"iDisplayLength": planQuery.collectPageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/manageCollectPlanList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": planQuery.oTableCollectAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {
				planQuery.oTableCollectFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,6,7]}]
		} );
	} else {
		//table不为null时重绘表格
		planQuery.oTableCollect.fnDraw();
	}
};

//管理创新tab分页查询方法
planQuery.initManageList = function () {
	planQuery.managePageRows = TabNub > 0 ? TabNub : 10;
	if (planQuery.oTableManage == null) {	
		planQuery.oTableManage = $('#manageListTable').dataTable( {
			"iDisplayLength": planQuery.managePageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/manageList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": planQuery.oTableManageAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {
				planQuery.oTableManageFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4]}]
		} );
	} else {
		//table不为null时重绘表格
		planQuery.oTableManage.fnDraw();
	}
};

//tab点击返回事件
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	var dataId = $(e.target).attr("data-id");
	//编制
	if(dataId == "planListMsg"){
		//清空编制查询列表
		planQuery.queryPlanFormReset();
		planQuery.initList();
	//转发
	}else if(dataId == "sendListMsg"){
		//清空转发查询列表
		planQuery.querySendFormReset();
		planQuery.initSendList();
	//汇总
	}else if(dataId == "collectListMsg"){
		//清空汇总查询列表
		planQuery.queryCollectFormReset();
		planQuery.initCollectList();
	//管理创新
	}else if(dataId == "manageListMsg"){
		//清空管理创新查询列表
		planQuery.queryManageFormReset();
		planQuery.initManageList();
	}else {
		planQuery.initList();
	}
})

/**
 * 删除计划方法
 */
planQuery.deleteBusiness = function (id) {
	var id = String(id);
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			planQuery.deleteCallBack(id)
		}
	});
};

/**
 * 删除计划回调方法
 */
planQuery.deleteCallBack = function(id) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/plan/deleteByIds.action",
		data : {"ids": id},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback:planQuery.initList()
				});
			}else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

//清空编制查询列表
planQuery.queryPlanFormReset = function(){
	$('#planListForm')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("planApplyId-applyIdsPrimaryKeys");
	//清空部门选择树隐藏域
	selectControl.clearValue("applyDeptid-applyDeptIdsPrimaryKeys");
};
//清空转发查询列表
planQuery.querySendFormReset = function(){
	//清空表单
	$('#sendListForm')[0].reset();
	//清空编制人人员选择树隐藏域
	selectControl.clearValue("sendApplyId-applyIdsPrimaryKeys");
	//清空转发人人员选择树隐藏域
	selectControl.clearValue("sendSenderId-sendIdsPrimaryKeys");
	//清空接收人人员选择树隐藏域
	selectControl.clearValue("receiveId-receiveIdsPrimaryKeys");
	//清空部门选择树隐藏域
	selectControl.clearValue("sendDeptid-applyDeptIdsPrimaryKeys");
};
//清空汇总查询列表
planQuery.queryCollectFormReset = function(){
	//清空表单
	$('#collectListForm')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("collectApplyId-applyIdsPrimaryKeys");
	//清空部门选择树隐藏域
	selectControl.clearValue("collectDeptid-applyDeptIdsPrimaryKeys");
};
//清空管理创新查询列表
planQuery.queryManageFormReset = function(){
	//清空表单
	$('#manageListForm')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("manageApplyId-applyIdsPrimaryKeys");
	//清空部门选择树隐藏域
	selectControl.clearValue("manageDeptid-applyDeptIdsPrimaryKeys");
};

//列表中查看接收人
planQuery.tooltip = function(sendPlanUserNames){
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
planQuery.deleteForward = function (id) {
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
							callback:planQuery.initSendList()
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
/**
 * 转发人员弹出层初始化
 */
planQuery.initSendUser = function (id,returnType) {
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
		$("#sendPlan").click(function(){planQuery.insertSendUser(id,returnType);});
	}
};

/**
 * 转发人员确定方法
 */
planQuery.insertSendUser = function (id,returnType){
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
//				callFunction=planQuery.initSendList();
//			}else{//编制
//				callFunction=planQuery.initList();
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
							callback:planQuery.initSendList()
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
							callback:planQuery.initList()
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
//						callback:callFunction
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
	planQuery.planPageRows = TabNub > 0 ? TabNub : 10;
	//阅读情况计算分页显示条数
	planQuery.readPageRows = TabNub > 0 ? TabNub : 10;
	//编制查询按钮绑定事件
	$("#queryPlan").click(planQuery.initList);
	//转发查询按钮绑定事件
	$("#querySend").click(planQuery.initSendList);
	//汇总查询按钮绑定事件
	$("#queryCollect").click(planQuery.initCollectList);
	//管理创新查询按钮绑定事件
	$("#queryManage").click(planQuery.initManageList);
	//编制重置按钮绑定事件
	$("#resetPlan").click(planQuery.queryPlanFormReset);
	//转发重置按钮绑定事件
	$("#resetSend").click(planQuery.querySendFormReset);
	//汇总重置按钮绑定事件
	$("#resetCollect").click(planQuery.queryCollectFormReset);
	//管理创新重置按钮绑定事件
	$("#resetManage").click(planQuery.queryManageFormReset);
	//初始化编制人员选择树
	selectControl.init("planShowUserTree","planApplyId-applyIdsPrimaryKeys", true, true);
	//初始化编制部门选择树
//	selectControl.init("planDeptTree","applyDeptid-applyDeptIdsPrimaryKeys", true, false);
	selectControl.init("planDeptTree","applyDeptid-applyDeptIdsPrimaryKeys", true, false,0);
	//初始化转发人员选择树		
	selectControl.init("sendShowUserTree","sendApplyId-applyIdsPrimaryKeys", true, true);		
	//初始化转发人人员选择树
	selectControl.init("senderShowUserTree","sendSenderId-sendIdsPrimaryKeys", false, true);	
	//初始化接收人人员选择树
	selectControl.init("receiveShowUserTree","receiveId-receiveIdsPrimaryKeys", false, true);	
	//初始化转发部门选择树
//	selectControl.init("sendDeptTree","sendDeptid-applyDeptIdsPrimaryKeys", true, false);
	selectControl.init("sendDeptTree","sendDeptid-applyDeptIdsPrimaryKeys", true, false,0);
	//初始化汇总人员选择树
	selectControl.init("collectShowUserTree","collectApplyId-applyIdsPrimaryKeys", true, true);
	//初始化汇总部门选择树
//	selectControl.init("collectDeptTree","collectDeptid-applyDeptIdsPrimaryKeys", true, false);
	selectControl.init("collectDeptTree","collectDeptid-applyDeptIdsPrimaryKeys", true, false,0);
	//初始化管理人员选择树
	selectControl.init("manageShowUserTree","manageApplyId-applyIdsPrimaryKeys", true, true);
	//初始化管理创新部门选择树
//	selectControl.init("manageDeptTree","manageDeptid-applyDeptIdsPrimaryKeys", true, false);
	selectControl.init("manageDeptTree","manageDeptid-applyDeptIdsPrimaryKeys", true, false,0);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化列表
	planQuery.initList();
});