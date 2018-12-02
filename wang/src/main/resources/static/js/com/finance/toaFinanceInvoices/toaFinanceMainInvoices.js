var toaFinanceInvoices = {};

//分页处理 start
//分页对象
toaFinanceInvoices.oTable = null;

toaFinanceInvoices.oTableAoColumns = [
	{ "mData":"orderDate"},
//	{ "mData":"month"},
	{ "mData":"companyName"},
	{ "mData":"companyTypeValue"},
	{ "mData":"resourceType"},
	{ "mData":"department"},
	{ "mData":"sale"},
	{ "mData":"maintenanSale"},
	{ "mData":"roomNameValue"},
	{ "mData":"payType"},
	{ "mData":"cycleStart"},
	{ "mData":"cycleEnd"},
	{ "mData":"lineCategory"},
	{ "mData":"singleCharg"},
	{ "mData":"overflowCategory"},
	{ "mData":"cardAmount"},
	{ "mData":"performanceAmount"},
	{ "mData":"cardStockAmount"},
	{ "mData":"prestoreAmount"},
	{ "mData":"discount"},
	{ "mData":"minBandwidth"},
	{ "mData":"minBandwidthPrice"},
	{ "mData":"portBandwidth"},
	{ "mData":"portBandwidthPrice"},
	{ "mData":"overflowBandwidth"},
	{ "mData":"overflowBandwidthPrice"},
	{ "mData":"cabinetNum"},
	{ "mData":"cabinetPrice"},
	{ "mData":"serviceNum"},
	{ "mData":"servicePrice"},
	{ "mData":"ipNum"},
	{ "mData":"ipPrice"},
	{ "mData":"switchNum"},
	{ "mData":"switchPrice"},
	{ "mData":"odfNum"},
	{ "mData":"odfPrice"},
	{ "mData":"portNum"},
	{ "mData":"portPrice"},
	{ "mData":"memoryNum"},
	{ "mData":"memoryPrice"},
	{ "mData":"cpuNum"},
	{ "mData":"cpuPrice"},
	{ "mData":"diskNum"},
	{ "mData":"diskPrice"},
	{ "mData":"invoicesMonth"},	{ "mData":"monthAmount"},	{ "mData":"invoicesAccount"},	{ "mData":"noinvoicesAccount"},	{ "mData":"receivedAccount"},	{ "mData":"arrearage"},	{ "mData":"commision"}
//	{ mData: function(source) {
//		if(source.otherDeductions==1){
//			return "已出";
//		}else{
//			return "未出";
//		}
//	}}];

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
			"sAjaxSource": getRootPath()+"/finance/toaFinanceInvoices/mainInvoicesList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinanceInvoices.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinanceInvoices.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": []}
	        ]
		} );
	} else {
		toaFinanceInvoices.oTable.fnDraw();
	}
};


$(document).ready(function(){
	//计算分页显示条数
	toaFinanceInvoices.pageCount = TabNub>0 ? TabNub : 12;
	$("#queryMachine").click(toaFinanceInvoices.toaFinanceInvoicesList);
	$("#queryReset").click(toaFinanceInvoices.queryReset);
	//初始化列表方法
	toaFinanceInvoices.toaFinanceInvoicesList();
});