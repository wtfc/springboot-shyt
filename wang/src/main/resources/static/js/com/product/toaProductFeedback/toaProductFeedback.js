var toaProductFeedback = {};

//分页处理 start
//分页对象
toaProductFeedback.oTable = null;

toaProductFeedback.oTableAoColumns = [
	{ "mData":"extStr1"},                                  
	{ "mData":"companyName"},	{ "mData":"salePeople"},	{ "mData":"cooperateType"},	{ "mData":"finishDate"},	{ "mData":"appraisal"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaProductFeedback.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaProductFeedback.oTable, aoData);
	
	var companyName = $("#toaProductFeedbackQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	
	var salePeople = $("#toaProductFeedbackQueryForm #salePeople").val();
	if(salePeople.length > 0){
		aoData.push({ "name": "salePeople", "value":salePeople});
	}
	
	var extStr1 = $("#toaProductFeedbackQueryForm #extStr1").val();
	if(extStr1.length > 0){
		aoData.push({ "name": "extStr1", "value":extStr1});
	}
	
};

//重置按钮功能
toaProductFeedback.queryReset = function(){
	$('#toaProductFeedbackQueryForm')[0].reset();
};


//分页查询
toaProductFeedback.toaProductFeedbackList = function () {
	if (toaProductFeedback.oTable == null) {
		toaProductFeedback.oTable = $('#toaProductFeedbackTable').dataTable( {
			"iDisplayLength": toaProductFeedback.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/product/toaProductFeedback/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaProductFeedback.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaProductFeedback.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7]}
	        ]
		} );
	} else {
		toaProductFeedback.oTable.fnDraw();
	}
};

toaProductFeedback.deleteToaProductFeedback = function (id) {
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
			toaProductFeedback.deleteTimeSet(ids);
		}
	});
};


toaProductFeedback.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/product/toaProductFeedback/deleteByIds.action",
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
			toaProductFeedback.toaProductFeedbackList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaProductFeedback.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaProductFeedback.toaProductFeedbackList);
	$("#queryReset").click(toaProductFeedback.queryReset);
	//初始化列表方法
	toaProductFeedback.toaProductFeedbackList();
});