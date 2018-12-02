var receiveDraftBox = {};
receiveDraftBox.pageRows = null;
//重复提交标识
receiveDraftBox.subState = false;

//分页处理 start
//分页对象
receiveDraftBox.oTable = null;
//显示列信息
receiveDraftBox.oTableAoColumns = [
	{ "mData": "title"},
	{ "mData": "flowName"},
	{ "mData": "createDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		//设置权限按钮
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = "0";
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "myBusiness";
		opt.showDeleteBtn = true;
		opt.deleteFun = "receiveDraftBox.deleteReceive(\'"+eval(full.id)+"\')";
		opt.action="/doc/receive/loadReceive.action";
		return getWorkflowListButton(opt);
	}}
 ];

//组装后台参数
receiveDraftBox.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(receiveDraftBox.oTable, aoData);
	//组装查询条件
	$.each($("#receiveDraftBoxListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	aoData.push({"name":"processStatus", "value":1});
};

//分页查询
receiveDraftBox.receiveDraftBoxList = function () {
	$('#receiveDraftBox-list').fadeIn();
	if (receiveDraftBox.oTable == null) {
		receiveDraftBox.oTable = $('#receiveDraftBoxTable').dataTable( {
			"iDisplayLength": receiveDraftBox.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/receive/manageDraftList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": receiveDraftBox.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				receiveDraftBox.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,3]}]
		} );
	} else {
		receiveDraftBox.oTable.fnDraw();
	}
};
//分页处理 end

receiveDraftBox.queryReset = function(){
	$('#receiveDraftBoxListForm')[0].reset();
};

//添加成功清空表单数据
receiveDraftBox.initForm = function(){
	receiveDraftBox.clearForm();
};

//清空表单
receiveDraftBox.clearForm = function(){
	$('#receiveDraftBoxAddForm')[0].reset();
	$('#receiveDraftBoxAddForm').find("input[type=hidden]").val("");
	$('#receiveDraftBoxAddForm').find("textarea").text("");
	hideErrorMessage();
};

//删除方法
receiveDraftBox.deleteReceive = function(id){
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
		success:function(){receiveDraftBox.deleteCallBack(ids);}
	});
};

//删除用户回调方法
receiveDraftBox.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/receive/deleteByIds.action?time=" + new Date(),
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				receiveDraftBox.receiveDraftBoxList();
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
	receiveDraftBox.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryReceiveDraftBox").click(receiveDraftBox.receiveDraftBoxList);
	$("#queryReset").click(receiveDraftBox.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	receiveDraftBox.receiveDraftBoxList();
});