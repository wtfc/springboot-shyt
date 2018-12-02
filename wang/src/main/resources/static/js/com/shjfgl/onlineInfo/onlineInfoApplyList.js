var onlineInfoApplyList = {};

onlineInfoApplyList.oTableAoColumns = [
					{'mData':'content' },
					{'mData':'deptName' },
					{'mData':'sendDate' },
					{'mData':'handlingUserName' },
                	{ "mData": function(source) {
                		var opt = {};
                		opt.workId = source.workId;
                		opt.dataTableId = "onlineInfoApplyTable";		//列表id
                		opt.queryFormId = "onlineInfoQueryForm";		//查询formId
                		opt.workflowId= source.workflowId;
                		opt.processStatus = source.processStatus;
                		opt.showDeleteBtn = true;			//是否显示删除按钮
                		opt.entrance = "business";			//业务类型：我的xx用buisiness,待办已办用$("#entrance").val()
                		opt.entranceType = "myBusiness";			//查看类型：我的xx用view,待办已办用$("#entranceType").val(),只是查看用view
                		opt.deleteFun = "onlineInfoApplyList.deleteOnlineInfo(\'"+eval(source.id)+"\')";
                		opt.action="/im/onlineInfo/openOnlineInfoWorkflow.action";
                		opt.flowStatus = source.flowStatus;
                		//返回流程公共按钮
                		return getWorkflowListButton(opt);
                	}}
                  ];
onlineInfoApplyList.oTable = null;

onlineInfoApplyList.pageRows = null;

onlineInfoApplyList.applyList = function () {
	if (onlineInfoApplyList.oTable == null) {
		var pagingInfo = pagingDataForGoBack();
		var queryData =pagingInfo.queryData;
		if(queryData!=null){
			//需要针对自己的查询搜索框进行填入值
			//如果有人员选择框的特殊内容需要特殊实现
			$("#onlineInfoQueryForm").fill(queryData);
		}
		onlineInfoApplyList.oTable = $('#onlineInfoApplyTable').dataTable( {
			"iDisplayLength": onlineInfoApplyList.pageRows,//每页显示多少条记录
			"iDisplayStart" : pagingInfo.curPage*onlineInfoApplyList.pageRows,
			"aaSorting" : pagingInfo.sortSetting,
			"sAjaxSource": getRootPath()+"/im/onlineInfo/manageApplyList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": onlineInfoApplyList.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				onlineInfoApplyList.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [4]}]
		} );
	} else {
		onlineInfoApplyList.oTable.fnDraw();
	}
};

//组装后台参数
onlineInfoApplyList.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(onlineInfoApplyList.oTable, aoData);
	//组装查询条件
	var deptName = $('#deptName').val();
if(deptName.length > 0){
	aoData.push({ 'name': 'deptName', 'value': deptName});
}
	var content = $('#content').val();
if(content.length > 0){
	aoData.push({ 'name': 'content', 'value': content});
}
	var handlingUserName = $('#handlingUserName').val();
if(handlingUserName.length > 0){
	aoData.push({ 'name': 'handlingUserName', 'value': handlingUserName});
}
	var sendDateBegin = $("#sendDateBegin").val();
if(sendDateBegin.length > 0){
	aoData.push({ "name": "sendDateBegin", "value": sendDateBegin});
}
var sendDateEnd = $("#sendDateEnd").val();
if(sendDateEnd.length > 0){
	aoData.push({ "name": "sendDateEnd", "value": sendDateEnd});
}

	var flowStatusForQuery = $("#onlineInfoQueryForm #flowStatusForQuery").val();
	if(flowStatusForQuery.length > 0){
		aoData.push({ "name": "flowStatusForQuery", "value": flowStatusForQuery});
	}
};

//删除方法
onlineInfoApplyList.deleteOnlineInfo = function(ids){
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
		onlineInfoApplyList.deleteCallBack(ids);
	});
};

//删除用户回调方法
onlineInfoApplyList.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/im/onlineInfo/deleteByIds.action",
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
			onlineInfoApplyList.applyList();
		}
	});
};


onlineInfoApplyList.queryReset = function(){
	$('#onlineInfoQueryForm')[0].reset();
};

jQuery(function($) {
	//计算分页显示条数
	onlineInfoApplyList.pageRows = TabNub>0 ? TabNub : 10;

	
	onlineInfoApplyList.applyList();
	$("#queryOnlineInfo").click(onlineInfoApplyList.applyList);
	$("#queryReset").click(onlineInfoApplyList.queryReset);
	
});

