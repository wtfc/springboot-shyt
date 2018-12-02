var sendWaitTransact = {};
sendWaitTransact.pageRows = null;
//重复提交标识
sendWaitTransact.subState = false;

//分页处理 start
//分页对象
sendWaitTransact.oTable = null;
//显示列信息
sendWaitTransact.oTableAoColumns = [
    { "mData": "noValue"},
	{ "mData": "title"},
	{ "mData": "flowName"},
	{ "mData": "createUserName"},
	{ "mData": "createDate"},
	//设置权限按钮
	{ "mData": function(source) {
		var opt ={};
		opt.workId = source.workId;
		opt.processStatus = source.processStatus;
		opt.flowStatus = source.flowStatus;
		opt.workflowId = source.piId;
		opt.entrance = $("#entrance").val();
		opt.entranceType = $("#entranceType").val();
		opt.action="/doc/send/loadSend.action";
		return getWorkflowListButton(opt);
	}}
 ];

//组装后台参数
sendWaitTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendWaitTransact.oTable, aoData);
	//组装查询条件
	$.each($("#sendWaitTransactListForm").serializeArray(), function(i, o) {
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
sendWaitTransact.sendWaitTransactList = function () {
	$.ajaxSetup ({
		cache: false //设置成false将不会从浏览器缓存读取信息
	});
	$('#sendWaitTransact-list').fadeIn();
	if (sendWaitTransact.oTable == null) {
		sendWaitTransact.oTable = $('#sendWaitTransactTable').dataTable( {
			"iDisplayLength": sendWaitTransact.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/send/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendWaitTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendWaitTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,3,5]}]
		} );
	} else {
		sendWaitTransact.oTable.fnDraw();
	}
};
//分页处理 end

sendWaitTransact.queryReset = function(){
	$('#sendWaitTransactListForm')[0].reset();
	selectControl.clearValue("createUser-createUser");
	//sendWaitTransact.sendWaitTransactList();
};

//添加成功清空表单数据
sendWaitTransact.initForm = function(){
	sendWaitTransact.clearForm();
};

//清空表单
sendWaitTransact.clearForm = function(){
	$('#sendWaitTransactAddForm')[0].reset();
	$('#sendWaitTransactAddForm').find("input[type=hidden]").val("");
	$('#sendWaitTransactAddForm').find("textarea").text("");
	hideErrorMessage();
};

//初始化
jQuery(function($){
	//计算分页显示条数
	sendWaitTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendWaitTransact").click(sendWaitTransact.sendWaitTransactList);
	$("#queryReset").click(sendWaitTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//查询使用人员选择树
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);
	sendWaitTransact.sendWaitTransactList();
});