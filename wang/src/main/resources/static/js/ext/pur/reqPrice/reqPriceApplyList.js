var reqPriceApplyList = {};

reqPriceApplyList.oTableAoColumns = [
                	{ "mData": function(source) {
                		var opt = {};
                		opt.workId = source.workId;
                		opt.workflowId= source.workflowId;
                		opt.processStatus = source.processStatus;
                		opt.showDeleteBtn = true;			//是否显示删除按钮
                		opt.entrance = "business";			//业务类型：我的xx用buisiness,待办已办用$("#entrance").val()
                		opt.entranceType = "myBusiness";			//查看类型：我的xx用view,待办已办用$("#entranceType").val(),只是查看用view
                		opt.deleteFun = "reqPriceApplyList.deleteReqPrice(\'"+eval(source.id)+"\')";
                		opt.action="/pur/reqPrice/openReqPriceWorkflow.action";
                		opt.flowStatus = source.flowStatus;
                		//返回流程公共按钮
                		return getWorkflowListButton(opt);
                	}}
                  ];
reqPriceApplyList.oTable = null;

reqPriceApplyList.pageRows = null;

reqPriceApplyList.applyList = function () {
	if (reqPriceApplyList.oTable == null) {
		reqPriceApplyList.oTable = $('#reqPriceApplyTable').dataTable( {
			"iDisplayLength": reqPriceApplyList.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/pur/reqPrice/manageApplyList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": reqPriceApplyList.oTableAoColumns,//table显示列
			 aaSorting:[],//设置表格默认排序列
			//传参
			"fnServerParams": function ( aoData ) {
				reqPriceApplyList.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,0]}]
		} );
	} else {
		reqPriceApplyList.oTable.fnDraw();
	}
};

//组装后台参数
reqPriceApplyList.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(reqPriceApplyList.oTable, aoData);
	//组装查询条件
	var createDate = $("#createDate").val();
	if(createDate.length > 0){
		aoData.push({ "name": "createDate", "value": createDate});
	}
	
	var flowStatusForQuery = $("#reqPriceQueryForm #flowStatusForQuery").val();
	if(flowStatusForQuery.length > 0){
		aoData.push({ "name": "flowStatusForQuery", "value": flowStatusForQuery});
	}
};

//删除方法
reqPriceApplyList.deleteReqPrice = function(ids){
	var id = String(ids);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		alertx("请选择要删除的记录");
		return;
	}
	confirmx("确认删除吗?",function(){
		reqPriceApplyList.deleteCallBack(ids);
	});
};

//删除用户回调方法
reqPriceApplyList.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/pur/reqPrice/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			reqPriceApplyList.applyList();
		}
	});
};


reqPriceApplyList.queryReset = function(){
	$('#reqPriceQueryForm')[0].reset();
};

jQuery(function($) {
	//计算分页显示条数
	reqPriceApplyList.pageRows = TabNub>0 ? TabNub : 10;
	reqPriceApplyList.applyList();
	
	$("#queryReqPrice").click(reqPriceApplyList.applyList);
	$("#queryReset").click(reqPriceApplyList.queryReset);
});

