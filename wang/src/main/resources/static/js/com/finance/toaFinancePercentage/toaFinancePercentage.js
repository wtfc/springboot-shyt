var toaFinancePercentage = {};

//分页处理 start
//分页对象
toaFinancePercentage.oTable = null;

toaFinancePercentage.oTableAoColumns = [
	{ "mData":"perNumber"},	{ "mData":"perYear"},	{ "mData":"perMonth"},	{ "mData":"perType"},	{ "mData":"companyName"},	{ "mData":"department"},	{ "mData":"sale"},	{ "mData":"tradeDepartment"},	{ "mData":"perEnSale"},	{ "mData":"perAccount"},	{ "mData":"perAgentAccount"},	{ "mData":"perPureAccount"},	{ "mData":"billAccount"},	{ "mData":"addBill"},	{ "mData":"kuorong"},	{ "mData":"perStart"},	{ "mData":"billDate"},	{ "mData":"perYers"},	{ "mData":"keweiRatio"},	{ "mData":"tuozhanRatio"},	{ "mData":"keweiMoney"},	{ "mData":"tuozhanMoney"},	{ "mData":"billNumber"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaFinancePercentage.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaFinancePercentage.oTable, aoData);
	
	var companyName = $("#toaFinancePercentageQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var perNumber = $("#toaFinancePercentageQueryForm #perNumber").val();
	if(perNumber.length > 0){
		aoData.push({ "name": "perNumber", "value":perNumber});
	}
	
	var perYear = $("#toaFinancePercentageQueryForm #perYear").val();
	if(perYear.length > 0){
		aoData.push({ "name": "perYear", "value":perYear});
	}
	var perMonth = $("#toaFinancePercentageQueryForm #perMonth").val();
	if(perMonth.length > 0){
		aoData.push({ "name": "perMonth", "value":perMonth});
	}
	
	var perType = $("#toaFinancePercentageQueryForm #perType").val();
	if(perType.length > 0){
		aoData.push({ "name": "perType", "value":perType});
	}
	var department = $("#toaFinancePercentageQueryForm #department").val();
	if(department.length > 0){
		aoData.push({ "name": "department", "value":department});
	}
	var sale = $("#toaFinancePercentageQueryForm #sale").val();
	if(sale.length > 0){
		aoData.push({ "name": "sale", "value":sale});
	}
	var tradeDepartment = $("#toaFinancePercentageQueryForm #tradeDepartment").val();
	if(tradeDepartment.length > 0){
		aoData.push({ "name": "tradeDepartment", "value":tradeDepartment});
	}
	var perEnSale = $("#toaFinancePercentageQueryForm #perEnSale").val();
	if(perEnSale.length > 0){
		aoData.push({ "name": "perEnSale", "value":perEnSale});
	}
};

//重置按钮功能
toaFinancePercentage.queryReset = function(){
	$('#toaFinancePercentageQueryForm')[0].reset();
};


//分页查询
toaFinancePercentage.toaFinancePercentageList = function () {
	if (toaFinancePercentage.oTable == null) {
		toaFinancePercentage.oTable = $('#toaFinancePercentageTable').dataTable( {
			"iDisplayLength": toaFinancePercentage.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/finance/toaFinancePercentage/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinancePercentage.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinancePercentage.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]}
	        ]
		} );
	} else {
		toaFinancePercentage.oTable.fnDraw();
	}
};

toaFinancePercentage.deleteToaFinancePercentage = function (id) {
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
			toaFinancePercentage.deleteTimeSet(ids);
		}
	});
};


toaFinancePercentage.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/finance/toaFinancePercentage/deleteByIds.action",
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
			toaFinancePercentage.toaFinancePercentageList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaFinancePercentage.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaFinancePercentage.toaFinancePercentageList);
	$("#queryReset").click(toaFinancePercentage.queryReset);
	//初始化列表方法
	toaFinancePercentage.toaFinancePercentageList();
});