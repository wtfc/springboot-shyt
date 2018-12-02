var toaNetworkIpaddress = {};

//分页处理 start
//分页对象
toaNetworkIpaddress.oTable = null;

toaNetworkIpaddress.oTableAoColumns = [
	{ "mData":"machineValue"},
	{ "mData":"ipOne"},
	{ "mData":"ipTwo"},
	{ "mData":"ipNumber"},
	{ "mData":"bandwidthNumber"},
	{ "mData":"bandwidth"},
	{ "mData":"operationEnginner"},
	{ "mData":"operationDate"},
	{ "mData":"dividerDate"}
];

toaNetworkIpaddress.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkIpaddress.oTable, aoData);
	
	var machine = $("#toaNetworkIpaddressQueryForm #machine").val();
	if(machine.length > 0){
		aoData.push({ "name": "machine", "value":machine});
	}
	
};

//重置按钮功能
toaNetworkIpaddress.queryReset = function(){
	$('#toaNetworkIpaddressQueryForm')[0].reset();
};


//分页查询
toaNetworkIpaddress.toaNetworkIpaddressList = function () {
	if (toaNetworkIpaddress.oTable == null) {
		toaNetworkIpaddress.oTable = $('#toaNetworkIpaddressTable').dataTable( {
			"iDisplayLength": toaNetworkIpaddress.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/customer/toaNetResourceIpaddress/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkIpaddress.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkIpaddress.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8]}
	        ]
		} );
	} else {
		toaNetworkIpaddress.oTable.fnDraw();
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkIpaddress.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkIpaddress.toaNetworkIpaddressList);
	$("#queryReset").click(toaNetworkIpaddress.queryReset);
	//初始化列表方法
	toaNetworkIpaddress.toaNetworkIpaddressList();
});