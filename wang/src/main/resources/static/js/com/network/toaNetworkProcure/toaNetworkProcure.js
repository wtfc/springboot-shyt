var toaNetworkProcure = {};

//分页处理 start
//分页对象
toaNetworkProcure.oTable = null;

toaNetworkProcure.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"machineNumber"},	{ "mData":"describes"},	{ "mData":"serialNumber"},	{ "mData":"applicationDate"},	{ "mData":"arrivalDate"},	{ "mData":"installs"},	{ "mData":"address"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkProcure.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkProcure.oTable, aoData);
	
	var serialNumber = $("#toaNetworkProcureQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value":serialNumber});
	}
	var applicationDate = $("#toaNetworkProcureQueryForm #applicationDate").val();
	if(applicationDate.length > 0){
		aoData.push({ "name": "applicationDate", "value":applicationDate});
	}
	
};

//重置按钮功能
toaNetworkProcure.queryReset = function(){
	$('#toaNetworkProcureQueryForm')[0].reset();
};


//分页查询
toaNetworkProcure.toaNetworkProcureList = function () {
	if (toaNetworkProcure.oTable == null) {
		toaNetworkProcure.oTable = $('#toaNetworkProcureTable').dataTable( {
			"iDisplayLength": toaNetworkProcure.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkProcure/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkProcure.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkProcure.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}
	        ]
		} );
	} else {
		toaNetworkProcure.oTable.fnDraw();
	}
};

toaNetworkProcure.deleteToaNetworkProcure = function (id) {
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
			toaNetworkProcure.deleteTimeSet(ids);
		}
	});
};


toaNetworkProcure.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkProcure/deleteByIds.action",
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
			toaNetworkProcure.toaNetworkProcureList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkProcure.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkProcure.toaNetworkProcureList);
	$("#queryReset").click(toaNetworkProcure.queryReset);
	//初始化列表方法
	toaNetworkProcure.toaNetworkProcureList();
});