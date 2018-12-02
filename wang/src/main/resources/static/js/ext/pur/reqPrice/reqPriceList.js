$(document).ready(function(){
	
	//计算分页显示条数
	reqPrice.pageCount = TabNub>0 ? TabNub : 10;
	//初始化列表方法
	reqPrice.reqPriceList();
	
	
	//流程初始化函数
	initWorkFlowStatus("reqPriceQueryForm");
	
	$("#queryReqPrice").click(reqPrice.reqPriceList);
	$("#queryReset").click(reqPrice.queryReset);
	$("#deleteReqPrices").click("click", function(){reqPrice.deleteReqPrice(0);});



});
  	

	
var reqPrice = {};

//分页处理 start
//分页对象
reqPrice.oTable = null;

reqPrice.oTableAoColumns = [
	
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

reqPrice.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(reqPrice.oTable, aoData);
	
	var createDate = $("#createDate").val();
	if(createDate.length > 0){
		aoData.push({ "name": "createDate", "value": createDate});
	}
	
	
	var flowId = $("#flowId").val();
	if(flowId.length > 0){
		aoData.push({ "name": "flowId", "value": flowId});
	}
	var processStatus = $("#processStatus").val();
	if(processStatus.length > 0){
		aoData.push({ "name": "processStatus", "value": processStatus});
	}
	
	
};

//分页查询
reqPrice.reqPriceList = function () {
	if (reqPrice.oTable == null) {
		reqPrice.oTable = $('#reqPriceTable').dataTable( {
			"iDisplayLength": reqPrice.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/pur/reqPrice/manageWorkflowList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": reqPrice.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				reqPrice.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0]}
	        ]
		} );
	} else {
		reqPrice.oTable.fnDraw();
	}
};

//删除对象
reqPrice.deleteReqPrice = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		msgBox.info({
			content: $.i18n.prop("JC_SYS_061")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			reqPrice.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
reqPrice.deleteCallBack = function(ids) {
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
			reqPrice.reqPriceList();
		}
	});
};

reqPrice.queryReset = function(){
	$('#reqPriceQueryForm')[0].reset();
};
	

