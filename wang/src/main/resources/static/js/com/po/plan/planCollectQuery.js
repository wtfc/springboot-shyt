/**
 * 计划汇总查询js
 */
var planCollectQuery = {};

//定义汇总分页变量
planCollectQuery.collectPageRows = null;
//重复提交标识
planCollectQuery.subState = false;
//汇总分页对象
planCollectQuery.oTableCollect = null;

/**
 * 汇总显示列信息
 */
planCollectQuery.oTableCollectAoColumns = [
    {mData: "planTitle"},//计划标题
   	{mData: "planTypeValue"},//计划类型
	{mData: "applyIdValue"},//申请人
	{mData: "applyDeptidValue"},//申请部门
	{mData: "planStartTime"},//计划开始时间
	{mData: "planEndTime"},//计划结束时间
	{mData: "nowRangeCompRate"},//当次计划及实际完成百分比
	{mData: "nextRangeCompRate"}//下次计划
 ];

/**
 * 汇总组装后台参数
 */
planCollectQuery.oTableCollectFnServerParams = function(aoData){
	//排序条件
	getTableParameters(planCollectQuery.oTableCollect, aoData);
	//组装查询条件
	$.each($("#collectListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

/**
 * 汇总tab分页查询方法
 */
planCollectQuery.initCollectList = function () {
	if (planCollectQuery.oTableCollect == null) {
		planCollectQuery.oTableCollect = $('#collectListTable').dataTable( {
			"iDisplayLength": planCollectQuery.collectPageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/manageCollectPlanList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": planCollectQuery.oTableCollectAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {
				planCollectQuery.oTableCollectFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,6,7]}]
		} );
	} else {
		//table不为null时重绘表格
		planCollectQuery.oTableCollect.fnDraw();
	}
};

/**
 * 清空汇总查询列表
 */
planCollectQuery.queryCollectFormReset = function(){
	//清空表单
	$('#collectListForm')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("collectApplyId-applyIdsPrimaryKeys");
	//清空部门选择树隐藏域
	selectControl.clearValue("collectDeptid-applyDeptIdsPrimaryKeys");
};

/**
 * 初始化计划类型
 */
planCollectQuery.initPlanType = function(){
	var jumpType = $("#diaryJumpType").val();
	$("#collectListForm").find("[class='checkbox inline']").remove();
	var inputStr ='';
	if(jumpType == 0){
		inputStr = "<input type='hidden' id='planType' name='planType' value='0'>";
		$("#planTypeTd").html("周计划");
	}else{
		inputStr = "<input type='hidden' id='planType' name='planType' value='1'>";
		$("#planTypeTd").html("月计划");
	}
	$("#planTypeHid").html(inputStr);
};

/**
 * 初始化方法
 */
jQuery(function($){
	planCollectQuery.collectPageRows = TabNub > 0 ? TabNub : 10;
	//汇总查询按钮绑定事件
	$("#queryCollect").click(planCollectQuery.initCollectList);
	//汇总重置按钮绑定事件
	$("#resetCollect").click(planCollectQuery.queryCollectFormReset);
	//初始化汇总人员选择树
	selectControl.init("collectShowUserTree","collectApplyId-applyIdsPrimaryKeys", true, true);
	//初始化汇总部门选择树
	selectControl.init("collectDeptTree","collectDeptid-applyDeptIdsPrimaryKeys", true, false,0);
	//初始化汇总人员选择树
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化计划类型
	planCollectQuery.initPlanType();
	//初始化列表
	planCollectQuery.initCollectList();
});