var toaNetworkRing = {};

//分页处理 start
//分页对象
toaNetworkRing.oTable = null;

toaNetworkRing.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"ringType"},	{ "mData":"machineValue"},	{ "mData":"oppositeMachineValue"},	{ "mData":"lineGbps"},	{ "mData":"lineNumber"},	{ "mData":"gbps"},	{ "mData":"withFuyongbi"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkRing.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkRing.oTable, aoData);
	
	var ringType = $("#toaNetworkRingQueryForm #ringType").val();
	if(ringType.length > 0){
		aoData.push({ "name": "ringType", "value":ringType});
	}
	var machine = $("#toaNetworkRingQueryForm #machine").val();
	if(machine.length > 0){
		aoData.push({ "name": "machine", "value":machine});
	}
	
};

//重置按钮功能
toaNetworkRing.queryReset = function(){
	$('#toaNetworkRingQueryForm')[0].reset();
};


//分页查询
toaNetworkRing.toaNetworkRingList = function () {
	if (toaNetworkRing.oTable == null) {
		toaNetworkRing.oTable = $('#toaNetworkRingTable').dataTable( {
			"iDisplayLength": toaNetworkRing.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkRing/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkRing.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkRing.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}
	        ]
		} );
	} else {
		toaNetworkRing.oTable.fnDraw();
	}
};

toaNetworkRing.deleteToaNetworkRing = function (id) {
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
			toaNetworkRing.deleteTimeSet(ids);
		}
	});
};


toaNetworkRing.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkRing/deleteByIds.action",
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
			toaNetworkRing.toaNetworkRingList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkRing.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkRing.toaNetworkRingList);
	$("#queryReset").click(toaNetworkRing.queryReset);
	//初始化列表方法
	toaNetworkRing.toaNetworkRingList();
});