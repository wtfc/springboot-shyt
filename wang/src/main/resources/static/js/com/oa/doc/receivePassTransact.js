var receivePassTransact = {};
receivePassTransact.pageRows = null;
//重复提交标识
receivePassTransact.subState = false;

//分页处理 start
//分页对象
receivePassTransact.oTable = null;
//显示列信息
receivePassTransact.oTableAoColumns = [
    { "mData": "seqValue"},
	{ "mData": "title"},
	{ "mData": "flowName"},
	{ "mData": "flowStatusMsg"},
	{ "mData": "createUserName"},
	{ "mData": "createDate"},
	{ "mData":null,"mRender":function(mData,type,full) {
		//设置权限按钮
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = full.flowStatus;
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "done";
		opt.action="/doc/receive/loadReceive.action";
		return getWorkflowListButton(opt);
		/*if(full.flowStatus == 1) {
		} else {
			return getWorkflowListButton(opt) + oTableSetButtons(mData,type,full);
		}*/
	}}
 ];

//组装后台参数
receivePassTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(receivePassTransact.oTable, aoData);
	//组装查询条件
	$.each($("#receivePassTransactListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
receivePassTransact.receivePassTransactList = function () {
	$('#receivePassTransact-list').fadeIn();
	if (receivePassTransact.oTable == null) {
		receivePassTransact.oTable = $('#receivePassTransactTable').dataTable( {
			"iDisplayLength": receivePassTransact.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/receive/managePassList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": receivePassTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				receivePassTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,3,4,6]}]
		} );
	} else {
		receivePassTransact.oTable.fnDraw();
	}
};
//分页处理 end

receivePassTransact.queryReset = function(){
	$('#receivePassTransactListForm')[0].reset();
	selectControl.clearValue("createUser-createUser");
	//receivePassTransact.receivePassTransactList();
};

//添加成功清空表单数据
receivePassTransact.initForm = function(){
	receivePassTransact.clearForm();
};

//清空表单
receivePassTransact.clearForm = function(){
	$('#receivePassTransactAddForm')[0].reset();
	$('#receivePassTransactAddForm').find("input[type=hidden]").val("");
	$('#receivePassTransactAddForm').find("textarea").text("");
	hideErrorMessage();
};

//初始化
jQuery(function($){
	//计算分页显示条数
	receivePassTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryReceivePassTransact").click(receivePassTransact.receivePassTransactList);
	$("#queryReset").click(receivePassTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//查询使用人员选择树
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);		
	receivePassTransact.receivePassTransactList();
});