var onlineInfoSearchList = {};

onlineInfoSearchList.oTableAoColumns = [
					{'mData':'content' },
					{'mData':'deptName' },
					{'mData':'sendDate' },
					{'mData':'handlingUserName' },
					{ "mData": "flowStatus" ,"mRender": function(mData,type,full){
                		return full.flowStatusMsg;}},
                	{ "mData": function(source) {
                		var opt = {};
                		opt.workId = source.workId;
                		opt.dataTableId = "onlineInfoSearchTable";		//列表id
                		opt.queryFormId = "onlineInfoQueryForm";		//查询formId
                		opt.workflowId= source.workflowId;
                		opt.processStatus = source.processStatus;
                		opt.entrance = "business";			//业务类型：我的xx用buisiness,待办已办用$("#entrance").val()
                		opt.entranceType = "view";			//查看类型：我的xx用view,待办已办用$("#entranceType").val(),只是查看用view
                		opt.action="/im/onlineInfo/openOnlineInfoWorkflow.action";
                		opt.flowStatus = source.flowStatus;
                		//返回流程公共按钮
                		return getWorkflowListButton(opt);
                	}}
                  ];
onlineInfoSearchList.oTable = null;

onlineInfoSearchList.pageRows = null;

onlineInfoSearchList.searchList = function () {
	
	if (onlineInfoSearchList.oTable == null) {
		var pagingInfo = pagingDataForGoBack();
		var queryData =pagingInfo.queryData;
		if(queryData!=null){
			//需要针对自己的查询搜索框进行填入值
			//如果有人员选择框的特殊内容需要特殊实现
			$("#onlineInfoQueryForm").fill(queryData);
		}
		onlineInfoSearchList.oTable = $('#onlineInfoSearchTable').dataTable( {
			"iDisplayLength": onlineInfoSearchList.pageRows,//每页显示多少条记录
			"iDisplayStart" : pagingInfo.curPage*onlineInfoSearchList.pageRows,
			"aaSorting" : pagingInfo.sortSetting,
			"sAjaxSource": getRootPath()+"/im/onlineInfo/manageSearchList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": onlineInfoSearchList.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				onlineInfoSearchList.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [5]}]
		} );
	} else {
		onlineInfoSearchList.oTable.fnDraw();
	}
};

//组装后台参数
onlineInfoSearchList.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(onlineInfoSearchList.oTable, aoData);
	//组装查询条件
	var deptId = $('#deptId-deptId').val();
if(deptId.length > 0){
	aoData.push({ 'name': 'deptId', 'value': deptId});
}
	var content = $('#onlineInfoQueryForm #content').val();
if(content.length > 0){
	aoData.push({ 'name': 'content', 'value': content});
}
	var handlingUserId = $('#handlingUserId-handlingUserId').val();
if(handlingUserId.length > 0){
	aoData.push({ 'name': 'handlingUserId', 'value': handlingUserId});
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

onlineInfoSearchList.queryReset = function(){
	$('#onlineInfoQueryForm')[0].reset();
	selectControl.clearValue("deptId-deptId");
	selectControl.clearValue("handlingUserId-handlingUserId");
};

jQuery(function($) {
	//计算分页显示条数
	onlineInfoSearchList.pageRows = TabNub>0 ? TabNub : 10;
	selectControl.init("deptIdTree","deptId-deptId", false, false, 0);
	//经办人员
	selectControl.init("substitusUserTree","handlingUserId-handlingUserId", false, true);
	
	onlineInfoSearchList.searchList();
	$("#queryOnlineInfo").click(onlineInfoSearchList.searchList);
	$("#queryReset").click(onlineInfoSearchList.queryReset);
	//处室部门
	
});

