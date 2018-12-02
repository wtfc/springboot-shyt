/**
 * 计划审批js
 */
var planApp = {};

planApp.pageRows = null;//分页变量
planApp.subState = false;//重复提交标识
planApp.oTable = null;//分页对象

//显示列信息
planApp.oTableAoColumns = [
	{mData:"planTitle"},//工作计划标题
	{mData: "planTypeValue"},//计划类型
	{mData: "processStatusMsg"},//流程状态
	{mData: "applyIdValue"},//申请人
	{mData: "applyDeptidValue"},//申请部门
	{mData: "planStartTime"},//计划开始时间
	{mData: "planEndTime"},//计划结束时间
	//设置权限按钮
	{ "mData": function(source) {
		var opt ={};
		opt.workId = source.workId;
		opt.flowStatus = source.flowStatus;
		opt.processStatus = source.processStatus;
		opt.workflowId = source.piId;
		opt.entrance = $("#entrance").val();
		opt.entranceType = $("#entranceType").val();
		opt.action="/po/plan/getPlan.action";
		return getWorkflowListButton(opt);
	}}
 ];

//组装后台参数
planApp.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(planApp.oTable, aoData);
	//多个流程对应一个表单
	var flowId = $("#flowId").val();
	if(flowId.length > 0){
		aoData.push({ "name": "flowId", "value": flowId});
	}
	//组装查询条件
	$.each($("#planAppListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value":o.value});
		}
	});
};

//分页查询方法
planApp.initList = function () {
	if (planApp.oTable == null) {
		planApp.oTable = $('#planAppList').dataTable( {
			"iDisplayLength": planApp.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/managePlanAppList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": planApp.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				planApp.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,7]}]
		});
	} else {
		//table不为null时重绘表格
		planApp.oTable.fnDraw();
	}
};

//清空表单数据
planApp.queryFormReset = function(){
	//清空表单
	$('#planAppListForm')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("applyId-applyIdsPrimaryKeys");
	//清空部门选择树隐藏域
	selectControl.clearValue("applyDeptid-applyDeptIdsPrimaryKeys");
};

/**
 * 初始化方法
 */
jQuery(function($){
	//流程初始化函数
	initWorkFlowStatus("planAppListForm");
	//计算分页显示条数
	planApp.pageRows = TabNub > 0 ? TabNub : 10;
	//查询按钮绑定事件
	$("#planAppQueryBtn").click(planApp.initList);
	//重置按钮绑定事件
	$("#planAppResetBtn").click(planApp.queryFormReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化转发人员选择树
	selectControl.init("planAppUserTree","applyId-applyIdsPrimaryKeys", true, true);
	//初始化编制部门选择树
//	selectControl.init("planAppDeptTree","applyDeptid-applyDeptIdsPrimaryKeys", true, false);
	selectControl.init("planAppDeptTree","applyDeptid-applyDeptIdsPrimaryKeys", true, false,0);
	//分页查询方法
	planApp.initList();
	
});