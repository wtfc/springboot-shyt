var toaFinanceMain = {};

//分页处理 start
//分页对象
toaFinanceMain.oTable = null;

toaFinanceMain.oTableAoColumns = [
//	{mData: function(source) {
//			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
//		}
//	},
	{ "mData":"orderNo"},	{ "mData":"orderDate"},//	{ "mData":"month"},	{ "mData":"companyName"},	{ "mData":"companyTypeValue"},	{ "mData":"resourceType"},	//{ "mData":"department"},	//{ "mData":"sale"},	//{ "mData":"maintenanSale"},	{ "mData":"roomNameValue"},	{ "mData":"payType"},	{ "mData":"cycleStart"},	{ "mData":"cycleEnd"},	{ "mData":"lineCategory"},	{ "mData":"singleCharg"},	{ "mData":"overflowCategory"},	{ "mData":"cardAmount"}	//{ "mData":"extStr1"},//	{ "mData":"performanceAmount"},//	{ "mData":"cardStockAmount"},//	{ "mData":"prestoreAmount"},//	{ "mData":"discount"},//	{mData: function(source) {
//		return oTableSetButtones(source);
//	}}
];

toaFinanceMain.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaFinanceMain.oTable, aoData);
	
	var companyName = $("#toaFinanceMainQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var sale = $("#toaFinanceMainQueryForm #sale").val();
	if(sale.length > 0){
		aoData.push({ "name": "sale", "value":sale});
	}
	var resourceType = $("#toaFinanceMainQueryForm #resourceType").val();
	if(resourceType.length > 0){
		aoData.push({ "name": "resourceType", "value":resourceType});
	}
	var roomName = $("#toaFinanceMainQueryForm #roomName").val();
	if(roomName.length > 0){
		aoData.push({ "name": "roomName", "value":roomName});
	}
	
};

//重置按钮功能
toaFinanceMain.queryReset = function(){
	$('#toaFinanceMainQueryForm')[0].reset();
};


//分页查询
toaFinanceMain.toaFinanceMainList = function () {
	if (toaFinanceMain.oTable == null) {
		toaFinanceMain.oTable = $('#toaFinanceMainTable').dataTable( {
			"iDisplayLength": toaFinanceMain.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/finance/toaFinanceMain/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinanceMain.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinanceMain.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12]}
	        ]
		} );
	} else {
		toaFinanceMain.oTable.fnDraw();
	}
};

$(document).ready(function(){
	//计算分页显示条数
	toaFinanceMain.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaFinanceMain.toaFinanceMainList);
	$("#queryReset").click(toaFinanceMain.queryReset);
	//初始化列表方法
	toaFinanceMain.toaFinanceMainList();
});