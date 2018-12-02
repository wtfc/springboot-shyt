var toaNetworkIpstop = {};

//分页处理 start
//分页对象
toaNetworkIpstop.oTable = null;

toaNetworkIpstop.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"machineValue"},	{ "mData":"stopEquipment"},	{ "mData":"ip"},	{ "mData":"stopType"},	{ "mData":"stopReason"},	{ "mData":"operatorHelp"},	{ "mData":"operator"},	{ "mData":"stopDate"},	{ "mData":"stopDatetime"},	{ "mData":"stopEnginer"},	{ "mData":"firstDate"},	{ "mData":"deblockingDate"},	{ "mData":"deblockingDatetime"},	{ "mData":"deblockingEnginer"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkIpstop.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkIpstop.oTable, aoData);
	
	var companyName = $("#toaNetworkIpstopQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machine = $("#toaNetworkIpstopQueryForm #machine").val();
	if(machine.length > 0){
		aoData.push({ "name": "machine", "value":machine});
	}
	var stopType = $("#toaNetworkIpstopQueryForm #stopType").val();
	if(stopType.length > 0){
		aoData.push({ "name": "stopType", "value":stopType});
	}
	var stopReason = $("#toaNetworkIpstopQueryForm #stopReason").val();
	if(stopReason.length > 0){
		aoData.push({ "name": "stopReason", "value":stopReason});
	}
	
};

//重置按钮功能
toaNetworkIpstop.queryReset = function(){
	$('#toaNetworkIpstopQueryForm')[0].reset();
};


//分页查询
toaNetworkIpstop.toaNetworkIpstopList = function () {
	if (toaNetworkIpstop.oTable == null) {
		toaNetworkIpstop.oTable = $('#toaNetworkIpstopTable').dataTable( {
			"iDisplayLength": toaNetworkIpstop.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkIpstop/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkIpstop.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkIpstop.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]}
	        ]
		} );
	} else {
		toaNetworkIpstop.oTable.fnDraw();
	}
};

toaNetworkIpstop.deleteToaNetworkIpstop = function (id) {
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
			toaNetworkIpstop.deleteTimeSet(ids);
		}
	});
};


toaNetworkIpstop.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkIpstop/deleteByIds.action",
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
			toaNetworkIpstop.toaNetworkIpstopList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkIpstop.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkIpstop.toaNetworkIpstopList);
	$("#queryReset").click(toaNetworkIpstop.queryReset);
	//初始化列表方法
	toaNetworkIpstop.toaNetworkIpstopList();
});