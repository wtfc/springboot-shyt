var sendQueryTransact = {};
sendQueryTransact.pageRows = null;
//重复提交标识
sendQueryTransact.subState = false;

//分页处理 start
//分页对象
sendQueryTransact.oTable = null;
//显示列信息
sendQueryTransact.oTableAoColumns = [
	{ "mData": "reNum"},
	{ "mData": "rePeoName"},
	{ "mData": "reDeptName"},
	{ "mData": "reDate"},
	{ "mData": "gradeSum"},
	{ "mData": "reSunMoney"},
	{ "mData": "reStatus","mRender" : function(mData, type, full) {
		if(full.reStatus == 0) {
			return "未领取";
		}  else {
			return "已领取";
		}
	}},
	{ "mData": "flowStatusMsg"},
	//设置权限按钮
	{ "mData": function(source) {
		var opt ={};
		opt.workId = source.workId;
		opt.processStatus = source.processStatus;
		opt.flowStatus = source.flowStatus;
		opt.workflowId = source.piId;
		opt.entrance = $("#entrance").val();
		opt.entranceType = $("#entranceType").val();
		return getWorkflowListButton(opt);
	}}
 ];

//组装后台参数
sendQueryTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendQueryTransact.oTable, aoData);
	//组装查询条件
	$.each($("#sendQueryTransactListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	var flowId = $("#flowId").val();
	if(flowId.length > 0){
		aoData.push({ "name": "flowId", "value": flowId});
	}
};

//分页查询
sendQueryTransact.sendQueryTransactList = function () {
	$.ajaxSetup ({
		cache: false //设置成false将不会从浏览器缓存读取信息
	});
	$('#sendQueryTransact-list').fadeIn();
	if (sendQueryTransact.oTable == null) {
		sendQueryTransact.oTable = $('#sendQueryTransactTable').dataTable( {
			"iDisplayLength": sendQueryTransact.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/finance/reimbursement/reimbursementQueryList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendQueryTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendQueryTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,4,5,6,7,8]}]
		} );
	} else {
		sendQueryTransact.oTable.fnDraw();
	}
};
//分页处理 end

sendQueryTransact.queryReset = function(){
	$('#sendQueryTransactListForm')[0].reset();
	selectControl.clearValue("rePeo-rePeo");
	selectControl.clearValue("createUser-createUser");
	selectControl.clearValue("reDept-reDept");
};

//添加成功清空表单数据
sendQueryTransact.initForm = function(){
	sendQueryTransact.clearForm();
};

//清空表单
sendQueryTransact.clearForm = function(){
	$('#sendQueryTransactAddForm')[0].reset();
	$('#sendQueryTransactAddForm').find("input[type=hidden]").val("");
	$('#sendQueryTransactAddForm').find("textarea").text("");
	hideErrorMessage();
};

//初始化
jQuery(function($){
	//计算分页显示条数
	sendQueryTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendQueryTransact").click(sendQueryTransact.sendQueryTransactList);
	$("#queryReset").click(sendQueryTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//查询使用人员选择树
	selectControl.init("queryRePeoTree","rePeo-rePeo", false, true);
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);
	selectControl.init("reDeptTree","reDept-reDept", false, false, 0);
	sendQueryTransact.sendQueryTransactList();
});