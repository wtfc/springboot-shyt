var sendFinishTransact = {};
sendFinishTransact.pageRows = null;
//重复提交标识
sendFinishTransact.subState = false;

//分页处理 start
//分页对象
sendFinishTransact.oTable = null;
//显示列信息
sendFinishTransact.oTableAoColumns = [
	{ "mData": "noValue"},
	{ "mData": "title"},
	{ "mData": "flowTitle"},
	{ "mData": "flowStatus","mRender" : function(mData, type, full) {
		if(full.flowStatus == 0) {
			return "暂存";
		} else if(full.flowStatus == 7 || full.flowStatus == 8) {
			return "审批结束";
		} else {
			return "审批中";
		}
	}},
	{ "mData": "createUserName"},
	{ "mData": "createDate"},
	//设置权限按钮
	{ "mData":null,"mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = full.flowStatus;
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "view";
		opt.noPerson = true;
		return getWorkflowListButton(opt);//、、+ oTableSetButtons(mData,type,full);
	}}
 ];

//组装后台参数
sendFinishTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendFinishTransact.oTable, aoData);
	//组装查询条件
	$.each($("#sendFinishTransactListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	//aoData.push({"name":"flowStatusForQuery","value":"2"});
};

//分页查询
sendFinishTransact.sendFinishTransactList = function () {
	$('#sendFinishTransact-list').fadeIn();
	var path = '';
	if(docType == 1) {
		//收文
		path = getRootPath()+"/doc/search/manageReceiveList.action";
	}else {
		//发文
		path = getRootPath()+"/doc/search/manageSendList.action";
		
	}
	if (sendFinishTransact.oTable == null) {
		sendFinishTransact.oTable = $('#sendFinishTransactTable').dataTable( {
			"iDisplayLength": sendFinishTransact.pageRows,//每页显示多少条记录
			 
			"sAjaxSource": path,
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendFinishTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendFinishTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,3,4,5,6]}]
		} );
	} else {
		sendFinishTransact.oTable.fnDraw();
	}
};
//分页处理 end

sendFinishTransact.queryReset = function(){
	$('#sendFinishTransactListForm')[0].reset();
	selectControl.clearValue("createUser-createUser");
	//sendFinishTransact.sendFinishTransactList();
};

//分发公文
sendFinishTransact.showDistributionDiv = function (id){
	$(document).showInsideOutAddDiv_(sendFinishTransact.sendFinishTransactList,id,0);
};

//初始化
jQuery(function($){
	//处理首页portal带入的查询条件
	if(title != null && title != ""){
		$('#title').val(title);
	}
	if(noValue != null && noValue != ""){
		$('#noValue').val(noValue);
	}
	//计算分页显示条数
	if(docType == 1) {
//		$('#createUserSearchTD').html('登记人');
//		$('#createUserListTD').html('登记人');
//		$('#createDateListTD').html('登记时间');
//		$('#createDateSearchTD').html('登记时间');
		$('#toolbar').html('<a href="#">首页</a><i></i><a href="#">公文管理</a><i></i><a href="#">公文查询</a><i></i>收文查询');
		
	} else {
//		$('#createUserSearchTD').html('发起人');
//		$('#createUserListTD').html('发起人');
//		$('#createDateListTD').html('发起时间');
//		$('#createDateSearchTD').html('发起时间');
		$('#toolbar').html('<a href="#">首页</a><i></i><a href="#">公文管理</a><i></i><a href="#">公文查询</a><i></i>发文查询');
	}
	sendFinishTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendFinishTransact").click(sendFinishTransact.sendFinishTransactList);
	//查询使用人员选择树
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);
	$("#queryReset").click(sendFinishTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();		
	sendFinishTransact.sendFinishTransactList();
	

	$("#sendFinishTransactListForm").validate({
		ignore: ".ignore",
        rules: {
 		  title: 
			{
				maxlength: 100,
				fileName:true
			},
			noValue : {
				maxlength : 100,
				fileName : true
			}
	    }
	});
});