var receiveFinishTransact = {};
receiveFinishTransact.pageRows = null;
//重复提交标识
receiveFinishTransact.subState = false;
//转发文对象
var receiveToSend = {};

//分页处理 start
//分页对象
receiveFinishTransact.oTable = null;
//显示列信息
receiveFinishTransact.oTableAoColumns = [
    { "mData": "seqValue"},
	{ "mData": "title"},
	{ "mData": "flowName"},
	{ "mData": "createDate"},
	{ "mData":null,"mRender":function(mData,type,full) {
		//设置权限按钮
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = full.flowStatus;
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "view";
		opt.action="/doc/receive/loadReceive.action";
		return getWorkflowListButton(opt) + oTableSetButtons(mData,type,full);
	}}
 ];

//组装后台参数
receiveFinishTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(receiveFinishTransact.oTable, aoData);
	//组装查询条件
	$.each($("#receiveFinishTransactListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	aoData.push({"name":"flowStatusForQuery","value":"2"});
};

//分页查询
receiveFinishTransact.receiveFinishTransactList = function () {
	$('#receiveFinishTransact-list').fadeIn();
	if (receiveFinishTransact.oTable == null) {
		receiveFinishTransact.oTable = $('#receiveFinishTransactTable').dataTable( {
			"iDisplayLength": receiveFinishTransact.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/receive/manageFinishList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": receiveFinishTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				receiveFinishTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,3,4]}]
		} );
	} else {
		receiveFinishTransact.oTable.fnDraw();
	}
};
//分页处理 end

receiveFinishTransact.queryReset = function(){
	$('#receiveFinishTransactListForm')[0].reset();
	//receiveFinishTransact.receiveFinishTransactList();
};

//添加成功清空表单数据
receiveFinishTransact.initForm = function(){
	receiveFinishTransact.clearForm();
};

//清空表单
receiveFinishTransact.clearForm = function(){
	$('#receiveFinishTransactAddForm')[0].reset();
	$('#receiveFinishTransactAddForm').find("input[type=hidden]").val("");
	$('#receiveFinishTransactAddForm').find("textarea").text("");
	hideErrorMessage();
};

//分发公文
receiveFinishTransact.showDistributionDiv = function (id){
	$(document).showInsideOutAddDiv_(receiveFinishTransact.receiveFinishTransactList,id,1);
};

//转发文 发文流程分页对象
receiveFinishTransact.sendWorkflowTable = null;
//转发文页面显示
receiveFinishTransact.showSendWorkflowDiv = function(receiveId,receiveTitle){
	$('#receiveFinishTransact-send').modal('show');
	
	receiveFinishTransact.sendWorkflowFnServerParams = function(aoData){
		getTableParameters(receiveFinishTransact.sendWorkflowTable, aoData);
	};
	
	//显示发文流程列信息
	receiveFinishTransact.sendWorkflowColumns = [	                                           
		{ "mData": "flowName"},
       	{ "mData": null, "mRender" : function(mData, type, full) {
       		var start = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"startSendDoc("+ full.id+ ",'"+ full.flowId+ "',"+receiveId+",'"+receiveTitle+"')\" role=\"button\" data-toggle=\"modal\"><i data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\"></i>发起</a> ";
       		var flow = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"workFlow.openProcessDefinitionMap('"+ full.flowId+ "')\" role=\"button\" data-toggle=\"modal\"><i data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\"></i>查看流程图</a>";
       		return start + flow;
       	}}
	 ];
	if (receiveFinishTransact.sendWorkflowTable == null) {
		receiveFinishTransact.sendWorkflowTable = $('#receiveFinishTransactSendTable').dataTable( {
			"sAjaxSource": getRootPath()+"/doc/send/getSendFLows.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": receiveFinishTransact.sendWorkflowColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				receiveFinishTransact.sendWorkflowFnServerParams(aoData);
			},
			bPaginate:false,
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		receiveFinishTransact.sendWorkflowTable.fnDraw();
	}
};

//转发文 跳转发文单
function startSendDoc(id,processDefinitionKey,receiveId,receiveTitle) {
	receiveToSend['id']=receiveId;
	receiveToSend['title']=receiveTitle;		
	var entrance = "business";
	var scanType = "create";
	setUrlParameter($.toJSON(receiveToSend));
	loadrightmenu("/workFlow/processDefinition/toStartProcess.action?scanType="+scanType+"&id_="+id+"&processDefinitionKey="+processDefinitionKey+"&entrance="+entrance,'unknown');
	/*confirmx($.i18n.prop("JC_SYS_030"),function(){
	});*/
}

//初始化
jQuery(function($){
	//计算分页显示条数
	receiveFinishTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryReceiveFinishTransact").click(receiveFinishTransact.receiveFinishTransactList);
	$("#queryReset").click(receiveFinishTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	receiveFinishTransact.receiveFinishTransactList();
});