var reqSearchList = {};

reqSearchList.oTableAoColumns = [
					{ "mData": "applyType", "mRender": function(mData,type,full){
						if(full.applyType == 0)
							return "电子类教学设备";
						else if(full.applyType == 1)
							return "固定厂家";
						else if(full.applyType == 2)
							return "非固定厂家";
					}},
					{ "mData": "applierValue"},
					{ "mData": "applyDept", "mRender": function(mData,type,full){
							return full.applyDeptValue;
					}},
					{ "mData": "budget" },
					{ "mData": "createDate"},
					{ "mData": "flowStatus" ,"mRender": function(mData,type,full){
						return full.flowStatusMsg;}},
                	{ "mData": function(source) {
                		var opt = {};
                		opt.workId = source.workId;
                		opt.workflowId= source.workflowId;
                		opt.processStatus = source.processStatus;
                		opt.entrance = "business";			//业务类型：我的xx用buisiness,待办已办用$("#entrance").val()
                		opt.entranceType = "view";			//查看类型：我的xx用view,待办已办用$("#entranceType").val(),只是查看用view
                		opt.action="/pur/req/openReqWorkflow.action";
                		opt.flowStatus = source.flowStatus;
                		//返回流程公共按钮
                		return getWorkflowListButton(opt);
                	}}
                  ];
reqSearchList.oTable = null;

reqSearchList.pageRows = null;

reqSearchList.searchList = function () {
	if (reqSearchList.oTable == null) {
		reqSearchList.oTable = $('#reqSearchTable').dataTable( {
			"iDisplayLength": reqSearchList.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/pur/req/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": reqSearchList.oTableAoColumns,//table显示列
			 aaSorting:[],//设置表格默认排序列
			//传参
			"fnServerParams": function ( aoData ) {
				reqSearchList.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [6]}]
		} );
	} else {
		reqSearchList.oTable.fnDraw();
	}
};

//组装后台参数
reqSearchList.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(reqSearchList.oTable, aoData);
	//组装查询条件
	$.each($("#reqQueryForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

reqSearchList.queryReset = function(){
	$('#reqQueryForm')[0].reset();
	selectControl.init("applierTree","applier-applier", false, true,0);
	selectControl.init("deptTree","applyDept-applyDept", false, false,0);
};

jQuery(function($) {
	//计算分页显示条数
	reqSearchList.pageRows = TabNub>0 ? TabNub : 10;
	reqSearchList.searchList();
	
	$("#queryReq").click(reqSearchList.searchList);
	$("#queryReset").click(reqSearchList.queryReset);
	selectControl.init("applierTree","applier-applier", false, true,0);
	selectControl.init("deptTree","applyDept-applyDept", false, false,0);
});

