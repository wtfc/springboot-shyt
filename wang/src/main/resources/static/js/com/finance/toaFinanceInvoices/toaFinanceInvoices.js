var toaFinanceInvoices = {};

//分页处理 start
//分页对象
toaFinanceInvoices.oTable = null;

toaFinanceInvoices.oTableAoColumns = [
	{mData: function(source) {
		if(source.otherDeductions==1){
			return "";
		}else{
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	}},
	{ "mData":"companyName"},
	{ "mData":"companyTypeValue"},	{ "mData":"invoicesMonth"},	{ "mData":"monthAmount"},	{ "mData":"invoicesAccount"},	{ "mData":"noinvoicesAccount"},	{ "mData":"receivedAccount"},	{ "mData":"arrearage"},	{ "mData":"commision"},
	{ mData: function(source) {
		if(source.otherDeductions==1){
			return "已出";
		}else{
			return "未出";
		}
	}},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaFinanceInvoices.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaFinanceInvoices.oTable, aoData);
	
	var companyName = $("#toaFinanceInvoicesQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var invoicesMonth = $("#toaFinanceInvoicesQueryForm #invoicesMonth").val();
	if(invoicesMonth.length > 0){
		aoData.push({ "name": "invoicesMonth", "value":invoicesMonth});
	}
	var companyType = $("#toaFinanceInvoicesQueryForm #companyType").val();
	if(companyType.length > 0){
		aoData.push({ "name": "companyType", "value":companyType});
	}
	var otherDeductions = $("#toaFinanceInvoicesQueryForm #otherDeductions").val();
	if(otherDeductions.length > 0){
		aoData.push({ "name": "otherDeductions", "value":otherDeductions});
	}
};

//重置按钮功能
toaFinanceInvoices.queryReset = function(){
	$('#toaFinanceInvoicesQueryForm')[0].reset();
};


//分页查询
toaFinanceInvoices.toaFinanceInvoicesList = function () {
	if (toaFinanceInvoices.oTable == null) {
		toaFinanceInvoices.oTable = $('#toaFinanceInvoicesTable').dataTable( {
			"iDisplayLength": toaFinanceInvoices.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/finance/toaFinanceInvoices/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinanceInvoices.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinanceInvoices.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11]}
	        ]
		} );
	} else {
		toaFinanceInvoices.oTable.fnDraw();
	}
};

toaFinanceInvoices.deleteToaFinanceInvoices = function (id) {
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
			toaFinanceInvoices.deleteTimeSet(ids);
		}
	});
};


toaFinanceInvoices.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/finance/toaFinanceInvoices/deleteByIds.action",
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
			toaFinanceInvoices.toaFinanceInvoicesList();
		}
	});
};
//生成账单
toaFinanceInvoices.chooseBill = function (id) {
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
			content: $.i18n.prop("JC_SYS_139")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_140"),
		success: function(){
			toaFinanceInvoices.createBill(ids);
		}
	});
};


toaFinanceInvoices.createBill = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/finance/toaFinanceInvoices/createBill.action",
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
			toaFinanceInvoices.toaFinanceInvoicesList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaFinanceInvoices.pageCount = TabNub>0 ? TabNub : 12;
	$("#queryMachine").click(toaFinanceInvoices.toaFinanceInvoicesList);
	$("#queryReset").click(toaFinanceInvoices.queryReset);
	$("#createBill").click("click", function(){toaFinanceInvoices.chooseBill(0);});
	//初始化列表方法
	toaFinanceInvoices.toaFinanceInvoicesList();
});