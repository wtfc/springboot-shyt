var reqApplyList = {};

reqApplyList.oTableAoColumns = [
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
                		opt.showDeleteBtn = true;			//是否显示删除按钮
                		opt.entrance = "business";			//业务类型：我的xx用buisiness,待办已办用$("#entrance").val()
                		opt.entranceType = "myBusiness";			//查看类型：我的xx用view,待办已办用$("#entranceType").val(),只是查看用view
                		opt.deleteFun = "reqApplyList.deleteReq(\'"+eval(source.id)+"\')";
                		opt.action="/pur/req/openReqWorkflow.action";
                		opt.flowStatus = source.flowStatus;
                		//返回流程公共按钮
                		return getWorkflowListButton(opt);
                	}}
                  ];
reqApplyList.oTable = null;

reqApplyList.pageRows = null;

reqApplyList.applyList = function () {
	if (reqApplyList.oTable == null) {
		reqApplyList.oTable = $('#reqApplyTable').dataTable( {
			"iDisplayLength": reqApplyList.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/pur/req/manageApplyList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": reqApplyList.oTableAoColumns,//table显示列
			 aaSorting:[],//设置表格默认排序列
			//传参
			"fnServerParams": function ( aoData ) {
				reqApplyList.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [6]}]
		} );
	} else {
		reqApplyList.oTable.fnDraw();
	}
};

//组装后台参数
reqApplyList.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(reqApplyList.oTable, aoData);
	$.each($("#reqQueryForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	//组装查询条件
//	var createDate = $("#createDate").val();
//	if(createDate.length > 0){
//		aoData.push({ "name": "createDate", "value": createDate});
//	}
//	
//	var flowStatusForQuery = $("#reqQueryForm #flowStatusForQuery").val();
//	if(flowStatusForQuery.length > 0){
//		aoData.push({ "name": "flowStatusForQuery", "value": flowStatusForQuery});
//	}
};

//删除方法
reqApplyList.deleteReq = function(ids){
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
		reqApplyList.deleteCallBack(ids);
	});
};

//删除用户回调方法
reqApplyList.deleteCallBack = function(ids) {
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
			reqApplyList.applyList();
		}
	});
};


reqApplyList.queryReset = function(){
	$('#reqQueryForm')[0].reset();
};

jQuery(function($) {
	//计算分页显示条数
	reqApplyList.pageRows = TabNub>0 ? TabNub : 10;
	reqApplyList.applyList();
	
	$("#queryReq").click(reqApplyList.applyList);
	$("#queryReset").click(reqApplyList.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
});

