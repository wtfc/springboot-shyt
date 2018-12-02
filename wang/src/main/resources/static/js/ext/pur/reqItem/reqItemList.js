$(document).ready(function(){
	
	//计算分页显示条数
	reqItem.pageCount = TabNub>0 ? TabNub : 10;
	//初始化列表方法
	reqItem.reqItemList();
	
	
	//流程初始化函数
	initWorkFlowStatus("reqItemQueryForm");
	
	$("#queryReqItem").click(reqItem.reqItemList);
	$("#queryReset").click(reqItem.queryReset);
	$("#deleteReqItems").click("click", function(){reqItem.deleteReqItem(0);});



});
  	

	
var reqItem = {};

//分页处理 start
//分页对象
reqItem.oTable = null;

reqItem.oTableAoColumns = [
	
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

reqItem.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(reqItem.oTable, aoData);
	
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
reqItem.reqItemList = function () {
	if (reqItem.oTable == null) {
		reqItem.oTable = $('#reqItemTable').dataTable( {
			"iDisplayLength": reqItem.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/pur/reqItem/manageWorkflowList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": reqItem.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				reqItem.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0]}
	        ]
		} );
	} else {
		reqItem.oTable.fnDraw();
	}
};

//删除对象
reqItem.deleteReqItem = function (id) {
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
			reqItem.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
reqItem.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/pur/reqItem/deleteByIds.action",
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
			reqItem.reqItemList();
		}
	});
};

reqItem.queryReset = function(){
	$('#reqItemQueryForm')[0].reset();
};
	

