var sendFinishTransact = {};
sendFinishTransact.pageRows = null;
//重复提交标识
sendFinishTransact.subState = false;

//分页处理 start
//分页对象
sendFinishTransact.oTable = null;
//显示列信息
sendFinishTransact.oTableAoColumns = [
	{ "mData": "noValue"},
	{ "mData": "title"},
	{ "mData": "flowName"},
	{ "mData": "createDate"},
	//设置权限按钮
	{ "mData":null,"mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = full.flowStatus;
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "view";
		opt.action="/doc/send/loadSend.action";
		return getWorkflowListButton(opt) + oTableSetButtons(mData,type,full);
	}}
 ];

//组装后台参数
sendFinishTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendFinishTransact.oTable, aoData);
	//组装查询条件
	$.each($("#sendFinishTransactListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	aoData.push({"name":"flowStatusForQuery","value":"2"});
};

//分页查询
sendFinishTransact.sendFinishTransactList = function () {
	$('#sendFinishTransact-list').fadeIn();
	if (sendFinishTransact.oTable == null) {
		sendFinishTransact.oTable = $('#sendFinishTransactTable').dataTable( {
			"iDisplayLength": sendFinishTransact.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/send/manageFinishList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendFinishTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendFinishTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,4]}]
		} );
	} else {
		sendFinishTransact.oTable.fnDraw();
	}
};
//分页处理 end

sendFinishTransact.queryReset = function(){
	$('#sendFinishTransactListForm')[0].reset();
	//sendFinishTransact.sendFinishTransactList();
};

//分发公文
sendFinishTransact.showDistributionDiv = function (id){
	$(document).showInsideOutAddDiv_(sendFinishTransact.sendFinishTransactList,id,0);
};

//初始化
jQuery(function($){
	//计算分页显示条数
	sendFinishTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendFinishTransact").click(sendFinishTransact.sendFinishTransactList);
	$("#queryReset").click(sendFinishTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	sendFinishTransact.sendFinishTransactList();
});