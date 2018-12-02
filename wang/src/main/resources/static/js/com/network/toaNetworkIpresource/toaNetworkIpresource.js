var toaNetworkIpresource = {};

//分页处理 start
//分页对象
toaNetworkIpresource.oTable = null;

toaNetworkIpresource.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"ipOne"},	{ "mData":"routeNumber"},	{ "mData":"airingStatus"},	{ "mData":"airingName"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkIpresource.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkIpresource.oTable, aoData);
	
	var ipOne = $("#toaNetworkIpresourceQueryForm #ipOne").val();
	if(ipOne.length > 0){
		aoData.push({ "name": "ipOne", "value":ipOne});
	}
	var airingStatus = $("#toaNetworkIpresourceQueryForm #airingStatus").val();
	if(airingStatus.length > 0){
		aoData.push({ "name": "airingStatus", "value":airingStatus});
	}
	
};

//重置按钮功能
toaNetworkIpresource.queryReset = function(){
	$('#toaNetworkIpresourceQueryForm')[0].reset();
};


//分页查询
toaNetworkIpresource.toaNetworkIpresourceList = function () {
	if (toaNetworkIpresource.oTable == null) {
		toaNetworkIpresource.oTable = $('#toaNetworkIpresourceTable').dataTable( {
			"iDisplayLength": toaNetworkIpresource.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkIpresource/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkIpresource.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkIpresource.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6]}
	        ]
		} );
	} else {
		toaNetworkIpresource.oTable.fnDraw();
	}
};

toaNetworkIpresource.deleteToaNetworkIpresource = function (id) {
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
			toaNetworkIpresource.deleteTimeSet(ids);
		}
	});
};


toaNetworkIpresource.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkIpresource/deleteByIds.action",
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
			toaNetworkIpresource.toaNetworkIpresourceList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkIpresource.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkIpresource.toaNetworkIpresourceList);
	$("#queryReset").click(toaNetworkIpresource.queryReset);
	//初始化列表方法
	toaNetworkIpresource.toaNetworkIpresourceList();
});