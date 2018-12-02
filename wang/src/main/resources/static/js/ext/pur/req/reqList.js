$(document).ready(function(){
	
	//计算分页显示条数
	req.pageCount = TabNub>0 ? TabNub : 10;
	//初始化列表方法
	req.reqList();
	
	
	//流程初始化函数
	initWorkFlowStatus("reqQueryForm");
	
	$("#queryReq").click(req.reqList);
	$("#queryReset").click(req.queryReset);
	$("#deleteReqs").click("click", function(){req.deleteReq(0);});
	selectControl.init("applierTree","applier-applier", false, true,0);
	selectControl.init("deptTree","applyDept-applyDept", false, false,0);


});
  	

	
var req = {};

//分页处理 start
//分页对象
req.oTable = null;

req.oTableAoColumns = [
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
	{mData: function(source) {
		var opt ={};
		opt.workId = source.workId;
		opt.processStatus = source.processStatus;
		opt.flowStatus = source.flowStatus;
		opt.workflowId = source.piId;
		opt.entrance = $("#entrance").val();
		opt.entranceType = $("#entranceType").val();
		opt.action = "/pur/req/openReqWorkflow.action";
		return getWorkflowListButton(opt);
	}}
];

req.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(req.oTable, aoData);
	$.each($("#reqQueryForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	
//	var createDate = $("#createDate").val();
//	if(createDate.length > 0){
//		aoData.push({ "name": "createDate", "value": createDate});
//	}
	
	
	var flowId = $("#flowId").val();
	if(flowId.length > 0){
		aoData.push({ "name": "flowId", "value": flowId});
	}
//	var processStatus = $("#processStatus").val();
//	if(processStatus.length > 0){
//		aoData.push({ "name": "processStatus", "value": processStatus});
//	}
	
	
};

//分页查询
req.reqList = function () {
	if (req.oTable == null) {
		req.oTable = $('#reqTable').dataTable( {
			"iDisplayLength": req.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/pur/req/manageWorkflowList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": req.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				req.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [6]}
	        ]
		} );
	} else {
		req.oTable.fnDraw();
	}
};

//删除对象
req.deleteReq = function (id) {
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
			req.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
req.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/pur/req/deleteByIds.action",
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
			req.reqList();
		}
	});
};

req.queryReset = function(){
	$('#reqQueryForm')[0].reset();
	selectControl.init("applierTree","applier-applier", false, true,0);
	selectControl.init("deptTree","applyDept-applyDept", false, false,0);
};
	

