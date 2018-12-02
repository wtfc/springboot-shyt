var toaNetworkIpaddress = {};

//分页处理 start
//分页对象
toaNetworkIpaddress.oTable = null;

toaNetworkIpaddress.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":function(source) {
		if(source.addressType==0){
			return "单线";
		}else{
			return "多线";
		}
	}},	{ "mData":"machineValue"},	{ "mData":"equipment"},	{ "mData":"ipOne"},	{ "mData":"ipTwo"},	{ "mData":"ipNumber"},
	{ "mData":"extStr1"},	{ "mData":"bandwidthNumber"},	{ "mData":"bandwidth"},	{ "mData":"operationEnginner"},	{ "mData":"operationDate"},	{ "mData":"dividerDate"},	{ "mData":"addressNumber"},	{ "mData":"usingNumber"},	{ "mData":"idlenessNumber"},	{ "mData":"usingRate"},	{ "mData":"idlenessRate"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkIpaddress.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkIpaddress.oTable, aoData);
	
	var companyName = $("#toaNetworkIpaddressQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var addressType = $("#toaNetworkIpaddressQueryForm #addressType").val();
	if(addressType.length > 0){
		aoData.push({ "name": "addressType", "value":addressType});
	}
	var machine = $("#toaNetworkIpaddressQueryForm #machine").val();
	if(machine.length > 0){
		aoData.push({ "name": "machine", "value":machine});
	}
	var equipment = $("#toaNetworkIpaddressQueryForm #equipment").val();
	if(equipment.length > 0){
		aoData.push({ "name": "equipment", "value":equipment});
	}
	var ipOne = $("#toaNetworkIpaddressQueryForm #ipOne").val();
	if(ipOne.length > 0){
		aoData.push({ "name": "ipOne", "value":ipOne});
	}
	var ipTwo = $("#toaNetworkIpaddressQueryForm #ipTwo").val();
	if(ipTwo.length > 0){
		aoData.push({ "name": "ipTwo", "value":ipTwo});
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
			"sAjaxSource": getRootPath()+"/network/toaNetworkIpaddress/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkIpaddress.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkIpaddress.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]}
	        ]
		} );
	} else {
		toaNetworkIpaddress.oTable.fnDraw();
	}
};

toaNetworkIpaddress.deleteToaNetworkIpaddress = function (id) {
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
			toaNetworkIpaddress.deleteTimeSet(ids);
		}
	});
};


toaNetworkIpaddress.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkIpaddress/deleteByIds.action",
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
			toaNetworkIpaddress.toaNetworkIpaddressList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkIpaddress.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkIpaddress.toaNetworkIpaddressList);
	$("#queryReset").click(toaNetworkIpaddress.queryReset);
	//初始化列表方法
	toaNetworkIpaddress.toaNetworkIpaddressList();
});