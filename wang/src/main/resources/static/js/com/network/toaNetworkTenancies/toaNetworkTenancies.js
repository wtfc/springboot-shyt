var toaNetworkTenancies = {};

//分页处理 start
//分页对象
toaNetworkTenancies.oTable = null;

toaNetworkTenancies.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"roomValue"},	{ "mData":"tenanciesDate"},	{ "mData":"bandwidth"},	{ "mData":"ipAddress"},	{ "mData":"machine"},	{ "mData":"port"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkTenancies.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkTenancies.oTable, aoData);
	
	//var companyName = $("#toaNetworkTenancies#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaNetworkTenancies.queryReset = function(){
	$('#toaNetworkTenanciesQueryForm')[0].reset();
};


//分页查询
toaNetworkTenancies.toaNetworkTenanciesList = function () {
	if (toaNetworkTenancies.oTable == null) {
		toaNetworkTenancies.oTable = $('#toaNetworkTenanciesTable').dataTable( {
			"iDisplayLength": toaNetworkTenancies.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkTenancies/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkTenancies.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkTenancies.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}
	        ]
		} );
	} else {
		toaNetworkTenancies.oTable.fnDraw();
	}
};

toaNetworkTenancies.deleteToaNetworkTenancies = function (id) {
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
			toaNetworkTenancies.deleteTimeSet(ids);
		}
	});
};


toaNetworkTenancies.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkTenancies/deleteByIds.action",
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
			toaNetworkTenancies.toaNetworkTenanciesList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkTenancies.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkTenancies.toaNetworkTenanciesList);
	$("#queryReset").click(toaNetworkTenancies.queryReset);
	//初始化列表方法
	toaNetworkTenancies.toaNetworkTenanciesList();
});