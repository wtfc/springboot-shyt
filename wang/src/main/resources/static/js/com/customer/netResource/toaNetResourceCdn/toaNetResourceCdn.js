var toaProductCdn = {};

//分页处理 start
//分页对象
toaProductCdn.oTable = null;

toaProductCdn.oTableAoColumns = [
	{ "mData":"supporter"},	{ "mData":"userName"},	{ "mData":"speedName"},	{ "mData":"stick"},	{ "mData":"chargeMode"},	{ "mData":"price"},	{ "mData":"flooredNumber"},	{ "mData":"chargeNumber"},	{ "mData":"amount"},	{ "mData":"startDate"},	{ "mData":"startEnd"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaProductCdn.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaProductCdn.oTable, aoData);

	var supporter = $("#toaProductCdnQueryForm #supporter").val();
	if(supporter.length > 0){
		aoData.push({ "name": "supporter", "value":supporter});
	}
	
};

//重置按钮功能
toaProductCdn.queryReset = function(){
	$('#toaProductCdnQueryForm')[0].reset();
};


//分页查询
toaProductCdn.toaProductCdnList = function () {
	if (toaProductCdn.oTable == null) {
		toaProductCdn.oTable = $('#toaProductCdnTable').dataTable( {
			"iDisplayLength": toaProductCdn.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/customer/ToaNetResourceCdn/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaProductCdn.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaProductCdn.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11]}
	        ]
		} );
	} else {
		toaProductCdn.oTable.fnDraw();
	}
};



$(document).ready(function(){
	//计算分页显示条数
	toaProductCdn.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaProductCdn.toaProductCdnList);
	$("#queryReset").click(toaProductCdn.queryReset);
	//初始化列表方法
	toaProductCdn.toaProductCdnList();
});