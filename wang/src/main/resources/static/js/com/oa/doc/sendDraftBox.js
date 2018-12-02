var sendDraftBox = {};
sendDraftBox.pageRows = null;
//重复提交标识
sendDraftBox.subState = false;

//分页处理 start
//分页对象
sendDraftBox.oTable = null;
//显示列信息
sendDraftBox.oTableAoColumns = [
	{ "mData": "title"},
	{ "mData": "flowName"},
	{ "mData": "createDate"},
	//设置权限按钮
	{ "mData": null, "mRender" : function(mData, type, full) {
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = "0";
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "myBusiness";
		opt.showDeleteBtn = true;
		opt.deleteFun = "sendDraftBox.deleteSend(\'"+eval(full.id)+"\')";
		opt.action="/doc/send/loadSend.action";
		return getWorkflowListButton(opt);
	}}
 ];

//组装后台参数
sendDraftBox.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendDraftBox.oTable, aoData);
	//组装查询条件
	$.each($("#sendDraftBoxListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
sendDraftBox.sendDraftBoxList = function () {
	$('#sendDraftBox-list').fadeIn();
	if (sendDraftBox.oTable == null) {
		sendDraftBox.oTable = $('#sendDraftBoxTable').dataTable( {
			"iDisplayLength": sendDraftBox.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/send/manageDraftBoxList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendDraftBox.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendDraftBox.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,3]}]
		} );
	} else {
		sendDraftBox.oTable.fnDraw();
	}
};
//分页处理 end

sendDraftBox.queryReset = function(){
	$('#sendDraftBoxListForm')[0].reset();
	//sendDraftBox.sendDraftBoxList();
};

//添加成功清空表单数据
sendDraftBox.initForm = function(){
	sendDraftBox.clearForm();
};

//清空表单
sendDraftBox.clearForm = function(){
	$('#sendDraftBoxAddForm')[0].reset();
	$('#sendDraftBoxAddForm').find("input[type=hidden]").val("");
	$('#sendDraftBoxAddForm').find("textarea").text("");
	hideErrorMessage();
};

//删除方法
sendDraftBox.deleteSend = function(id){
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
		content:$.i18n.prop("JC_SYS_034"),
		success:function(){sendDraftBox.deleteCallBack(ids);}
	});
};

//删除用户回调方法
sendDraftBox.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/send/deleteByIds.action?time=" + new Date(),
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				sendDraftBox.sendDraftBoxList();
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			
		}
	});
};

//初始化
jQuery(function($){
	//计算分页显示条数
	sendDraftBox.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendDraftBox").click(sendDraftBox.sendDraftBoxList);
	$("#queryReset").click(sendDraftBox.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	sendDraftBox.sendDraftBoxList();
});