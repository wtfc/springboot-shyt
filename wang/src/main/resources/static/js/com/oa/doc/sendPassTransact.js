var sendPassTransact = {};
sendPassTransact.pageRows = null;
//重复提交标识
sendPassTransact.subState = false;

//分页处理 start
//分页对象
sendPassTransact.oTable = null;
//显示列信息
sendPassTransact.oTableAoColumns = [
	{ "mData": "noValue"},
	{ "mData": "title"},
	{ "mData": "flowName"},
	{ "mData": "flowStatusMsg"},
	{ "mData": "createUserName"},
	{ "mData": "createDate"},
	//设置权限按钮
	{ "mData":null,"mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = full.flowStatus;
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "done";
		opt.action="/doc/send/loadSend.action";
		return getWorkflowListButton(opt);
	}}
 ];

//组装后台参数
sendPassTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendPassTransact.oTable, aoData);
	//组装查询条件
	$.each($("#sendPassTransactListForm").serializeArray(), function(i, o) {
		///alert(o.value);
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
sendPassTransact.sendPassTransactList = function () {
	$.ajaxSetup ({
		cache: false //设置成false将不会从浏览器缓存读取信息
	});
	$('#sendPassTransact-list').fadeIn();
	if (sendPassTransact.oTable == null) {
		sendPassTransact.oTable = $('#sendPassTransactTable').dataTable( {
			"iDisplayLength": sendPassTransact.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/send/managePassList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendPassTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendPassTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
			"aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,3,4,6]}]
		} );
	} else {
		sendPassTransact.oTable.fnDraw();
	}
};
//分页处理 end

sendPassTransact.queryReset = function(){
	$('#sendPassTransactListForm')[0].reset();
	selectControl.clearValue("createUser-createUser");
	//sendPassTransact.sendPassTransactList();
};

//初始化
jQuery(function($){
	//流程初始化函数
	initWorkFlowStatus("sendPassTransactListForm");
	//计算分页显示条数
	sendPassTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendPassTransact").click(sendPassTransact.sendPassTransactList);
	$("#queryReset").click(sendPassTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//查询使用人员选择树
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);
	sendPassTransact.sendPassTransactList();
});