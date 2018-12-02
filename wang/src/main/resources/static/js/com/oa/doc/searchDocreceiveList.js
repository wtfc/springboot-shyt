var sendFinishTransact = {};
sendFinishTransact.pageRows = null;
//重复提交标识
sendFinishTransact.subState = false;

//分页处理 start
//分页对象
sendFinishTransact.oTable = null;
//显示列信息
sendFinishTransact.oTableAoColumns = [
	{ "mData": "seqValue"},
	{ "mData": "title"},
	{ "mData": "sendDept"},
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
		return getWorkflowListButton(opt);//+ oTableSetButtons(mData,type,full);
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
	if (sendFinishTransact.oTable == null) {
		sendFinishTransact.oTable = $('#sendFinishTransactTable').dataTable( {
			"iDisplayLength": sendFinishTransact.pageRows,//每页显示多少条记录
			 
			"sAjaxSource": getRootPath()+"/doc/search/manageReceiveList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendFinishTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendFinishTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [1,2,3,4,5,7]}]
		} );
	} else {
		sendFinishTransact.oTable.fnDraw();
	}
};
//分页处理 end

//来文单位分页对象
sendFinishTransact.fromDeptTable = null;
//显示来文单位信息
sendFinishTransact.showFromDeptDiv = function(){
	$('#fromDept-list').modal('show');
	
	sendFinishTransact.fromDeptFnServerParams = function(aoData){
		getTableParameters(sendFinishTransact.fromDeptTable, aoData);
	};
	
	//显示来文单位列信息
	sendFinishTransact.fromDeptColumns = [
        {mData: function(source) {
        	if($('#sendDeptId').val()==source.id){
        		return "<input type=\"radio\" name=\"fromDepts\" value='"+source.id+"#"+source.name+"' checked>";
        	}else
        		return "<input type=\"radio\" name=\"fromDepts\" value='"+source.id+"#"+source.name+"'>";
       	}},	                                           
		{ "mData": "name" },
		{ "mData": "offcial"},
		{ "mData": "description"}
	 ];
	if (sendFinishTransact.fromDeptTable == null) {
		sendFinishTransact.fromDeptTable = $('#fromDeptListTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/fromDept/manageDeptAndOrgList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendFinishTransact.fromDeptColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendFinishTransact.fromDeptFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3]}]
		} );
	} else {
		sendFinishTransact.fromDeptTable.fnDraw();
	}
};

//选择来文单位方法
sendFinishTransact.chooseFromDept = function(){
	var fromDeptName = $('input[name="fromDepts"]:checked').val();
	if (fromDeptName == null) {
		msgBox.info({
			content: $.i18n.prop("JC_SYS_029","来文单位")
		});
		return;
	}
	$("#sendDeptId").val(fromDeptName.split("#")[0]);
	$("#sendDept").val(fromDeptName.split("#")[1]);
	$('#fromDept-list').modal('hide');
};
sendFinishTransact.queryReset = function(){
	$('#sendFinishTransactListForm')[0].reset();
	$('#sendDeptId').val('');
	selectControl.clearValue("createUser-createUser");
	//sendFinishTransact.sendFinishTransactList();
};

//分发公文
sendFinishTransact.showDistributionDiv = function (id){
	$(document).showInsideOutAddDiv_(sendFinishTransact.sendFinishTransactList,id,0);
};

//初始化
jQuery(function($){
	//处理首页portal带入的默认值
	if(title != null && title != ""){
		$('#title').val(title);
	}
	if(seqValue != null && seqValue != ""){
		$('#seqValue').val(seqValue);
	}
	if(sendDept != null && sendDept != ""){
		$('#sendDept').val(sendDept);
	}
	
	$('#toolbar').html('<a href="#">首页</a><i></i><a href="#">公文管理</a><i></i><a href="#">公文查询</a><i></i>收文查询');
	sendFinishTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendFinishTransact").click(sendFinishTransact.sendFinishTransactList);
	//查询使用人员选择树
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);
	$("#queryReset").click(sendFinishTransact.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();		
	sendFinishTransact.sendFinishTransactList();
	//来文单位
	$("#showFromDeptDiv").click(sendFinishTransact.showFromDeptDiv);
	$("#chooseFromDept").click(sendFinishTransact.chooseFromDept);

	$("#sendFinishTransactListForm").validate({
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