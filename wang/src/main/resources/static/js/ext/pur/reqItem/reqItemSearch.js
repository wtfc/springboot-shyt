var reqItemSearchList = {};

reqItemSearchList.oTableAoColumns = [
                	{ "mData": function(source) {
                		var opt = {};
                		opt.workId = source.workId;
                		opt.workflowId= source.workflowId;
                		opt.processStatus = source.processStatus;
                		opt.entrance = "business";			//业务类型：我的xx用buisiness,待办已办用$("#entrance").val()
                		opt.entranceType = "view";			//查看类型：我的xx用view,待办已办用$("#entranceType").val(),只是查看用view
                		opt.action="/pur/reqItem/openReqItemWorkflow.action";
                		opt.flowStatus = source.flowStatus;
                		//返回流程公共按钮
                		return getWorkflowListButton(opt);
                	}}
                  ];
reqItemSearchList.oTable = null;

reqItemSearchList.pageRows = null;

reqItemSearchList.searchList = function () {
	if (reqItemSearchList.oTable == null) {
		reqItemSearchList.oTable = $('#reqItemSearchTable').dataTable( {
			"iDisplayLength": reqItemSearchList.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/pur/reqItem/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": reqItemSearchList.oTableAoColumns,//table显示列
			 aaSorting:[],//设置表格默认排序列
			//传参
			"fnServerParams": function ( aoData ) {
				reqItemSearchList.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,0]}]
		} );
	} else {
		reqItemSearchList.oTable.fnDraw();
	}
};

//组装后台参数
reqItemSearchList.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(reqItemSearchList.oTable, aoData);
	//组装查询条件
	var createDate = $("#createDate").val();
	if(createDate.length > 0){
		aoData.push({ "name": "createDate", "value": createDate});
	}
	
	var flowStatusForQuery = $("#reqItemQueryForm #flowStatusForQuery").val();
	if(flowStatusForQuery.length > 0){
		aoData.push({ "name": "flowStatusForQuery", "value": flowStatusForQuery});
	}
};

reqItemSearchList.queryReset = function(){
	$('#reqItemQueryForm')[0].reset();
};

jQuery(function($) {
	//计算分页显示条数
	reqItemSearchList.pageRows = TabNub>0 ? TabNub : 10;
	reqItemSearchList.searchList();
	
	$("#queryReqItem").click(reqItemSearchList.searchList);
	$("#queryReset").click(reqItemSearchList.queryReset);
});

