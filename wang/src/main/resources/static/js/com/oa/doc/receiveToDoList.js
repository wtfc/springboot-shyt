var businessList = {};
businessList.oTableAoColumns = [
            		{ "mData": "seqValue"},
					{ "mData": "title"},
					{ "mData": "flowName"},
					{ "mData": "createUserName"},
					{ "mData": "createDate"},
                	{ "mData": function(source) {
                		var opt = {};
                		opt.workId = source.workId;
                		opt.processStatus = source.processStatus;
                		opt.flowStatus = source.flowStatus;
                		opt.workflowId= source.workflowId;
                		opt.entrance = $("#entrance").val();			//业务类型：我的xx用buisiness,待办已办用$("#entrance").val()
                		opt.entranceType = $("#entranceType").val();	//查看类型：我的xx用view,待办已办用$("#entranceType").val()
                		opt.action="/doc/receive/loadReceive.action";
                		//返回流程公共按钮
                		//alert(opt.entrance);
                		//alert(opt.entranceType);
                		return getWorkflowListButton(opt);
                	}}
                  ];
businessList.oTable = null;

businessList.pageRows = 10;

businessList.businessToDoList = function () {
	if (businessList.oTable == null) {
		businessList.oTable = $('#receiveWaitTransactTable').dataTable( {
			"iDisplayLength": businessList.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/receive/manageToDoList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": businessList.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				businessList.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,3,5]}]
		} );
	} else {
		businessList.oTable.fnDraw();
	}
};

//组装后台参数
businessList.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(businessList.oTable, aoData);
	//组装查询条件
	//组装查询条件
	$.each($("#businessQueryForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
//	var title = $("#businessQueryForm #title").val();
	//var processStatus = $("#businessQueryForm #processStatus").val();
//	var createDateBegin = $("#businessQueryForm #createDateBegin").val();
//	var createDateEnd = $("#businessQueryForm #createDateEnd").val();
	//var createUser = $("#businessQueryForm #createUser").val();
//	if(title.length > 0){
//		aoData.push({ "name": "title", "value": title});
//	}
//	if(createUser!=undefined && createUser.length > 0){
//		aoData.push({ "name": "createUser", "value": createUser});
//	}
//	if(createDateBegin.length > 0){
//		aoData.push({ "name": "createDateBeginStr", "value": createDateBegin});
//	}
//	if(createDateEnd.length > 0){
//		aoData.push({ "name": "createDateEndStr", "value": createDateEnd});
//	}
	aoData.push({ "name": "processStatus", "value": "1"});
	var flowId = $("#flowId").val();
	if(flowId.length > 0){
		aoData.push({ "name": "flowId", "value": flowId});
	}
	/*if(processStatus.length > 0){
	}*/
};

businessList.queryReset = function(){
	$('#businessQueryForm')[0].reset();
	selectControl.clearValue("createUser-createUser");
//	businessList.businessToDoList();
};



jQuery(function($) {
	
	//流程初始化函数
	initWorkFlowStatus("businessQueryForm");
	
	//计算分页显示条数
	//businessList.pageRows = TabNub>0 ? TabNub : 10;
	$(".datepicker-input").datepicker();
	//businessList.pageRows = 20;
	$("#queryReceiveWaitTransact").click(businessList.businessToDoList);
	$("#queryReset").click(businessList.queryReset);
	//查询使用人员选择树
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);
	businessList.businessToDoList();
	$("#businessQueryForm").validate({
		
		ignore: ".ignore",
        rules: {
			title: 
			{
				maxlength: 100,
				fileName:true
			},
			seqValue : {
				maxlength : 100,
				fileName : true
			}
	    }
	});
	
});

