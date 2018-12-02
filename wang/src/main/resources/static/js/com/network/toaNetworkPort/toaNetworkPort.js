var toaNetworkPort = {};

//分页处理 start
//分页对象
toaNetworkPort.oTable = null;

toaNetworkPort.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"companyType"},	{ "mData":"machineValue"},	{ "mData":"equipment"},	{ "mData":"board"},	{ "mData":"ports"},	{ "mData":"vlan"},	{ "mData":"bandwidth"},	{ "mData":"usings"},	{ "mData":"bandwidthNumber"},	{ "mData":"myselfNumber"},	{ "mData":"companyNumber"},	{ "mData":"myselfRate"},	{ "mData":"companyRate"},//	{ "mData":"myselfIdleness"},//	{ "mData":"companyIdleness"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkPort.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkPort.oTable, aoData);
	
	var companyName = $("#toaNetworkPortQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var companyType = $("#toaNetworkPortQueryForm #companyType").val();
	if(companyType.length > 0){
		aoData.push({ "name": "companyType", "value":companyType});
	}
	
};

//重置按钮功能
toaNetworkPort.queryReset = function(){
	$('#toaNetworkPortQueryForm')[0].reset();
};


//分页查询
toaNetworkPort.toaNetworkPortList = function () {
	if (toaNetworkPort.oTable == null) {
		toaNetworkPort.oTable = $('#toaNetworkPortTable').dataTable( {
			"iDisplayLength": toaNetworkPort.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkPort/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkPort.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkPort.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]}
	        ]
		} );
	} else {
		toaNetworkPort.oTable.fnDraw();
	}
};

toaNetworkPort.deleteToaNetworkPort = function (id) {
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
			toaNetworkPort.deleteTimeSet(ids);
		}
	});
};


toaNetworkPort.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkPort/deleteByIds.action",
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
			toaNetworkPort.toaNetworkPortList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkPort.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkPort.toaNetworkPortList);
	$("#queryReset").click(toaNetworkPort.queryReset);
	//初始化列表方法
	toaNetworkPort.toaNetworkPortList();
});