var toaNetworkBandwidth = {};

//分页处理 start
//分页对象
toaNetworkBandwidth.oTable = null;

toaNetworkBandwidth.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"gbps"},	{ "mData":"line"},	{ "mData":"wanglin"},	{ "mData":"yzl"},	{ "mData":"langfang"},	{ "mData":"lugu"},	{ "mData":"dongsi"},	{ "mData":"huasi"},	{ "mData":"shandong"},	{ "mData":"yidong"},	{ "mData":"tietong"},	{ "mData":"jiaoyu"},	{ "mData":"keji"},	{ "mData":"cnisp"},	{ "mData":"guoji"},	{ "mData":"transfer"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkBandwidth.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkBandwidth.oTable, aoData);
	
	var companyName = $("#toaNetworkBandwidthQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var gbps = $("#toaNetworkBandwidthQueryForm #gbps").val();
	if(gbps.length > 0){
		aoData.push({ "name": "gbps", "value":gbps});
	}
	
};

//重置按钮功能
toaNetworkBandwidth.queryReset = function(){
	$('#toaNetworkBandwidthQueryForm')[0].reset();
};


//分页查询
toaNetworkBandwidth.toaNetworkBandwidthList = function () {
	if (toaNetworkBandwidth.oTable == null) {
		toaNetworkBandwidth.oTable = $('#toaNetworkBandwidthTable').dataTable( {
			"iDisplayLength": toaNetworkBandwidth.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkBandwidth/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkBandwidth.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkBandwidth.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]}
	        ]
		} );
	} else {
		toaNetworkBandwidth.oTable.fnDraw();
	}
};

toaNetworkBandwidth.deleteToaNetworkBandwidth = function (id) {
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
			toaNetworkBandwidth.deleteTimeSet(ids);
		}
	});
};


toaNetworkBandwidth.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkBandwidth/deleteByIds.action",
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
			toaNetworkBandwidth.toaNetworkBandwidthList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkBandwidth.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkBandwidth.toaNetworkBandwidthList);
	$("#queryReset").click(toaNetworkBandwidth.queryReset);
	//初始化列表方法
	toaNetworkBandwidth.toaNetworkBandwidthList();
});