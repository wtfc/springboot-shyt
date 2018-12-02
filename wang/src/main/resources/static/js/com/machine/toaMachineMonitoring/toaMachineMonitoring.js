var toaMachineMonitoring = {};

//分页处理 start
//分页对象
toaMachineMonitoring.oTable = null;

toaMachineMonitoring.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"faultNumber"},	{ "mData":"companyName"},	{ "mData":"machinaValue"},	{ "mData":"startDate"},	{ "mData":"finishDate"},	{ "mData":"type"},	{ "mData":"typeTwo"},	{ "mData":"faultReason"},	{ "mData":"overflow"},	{ "mData":"inflow"},	{ "mData":"outflow"},	{ "mData":"fazhi"},	{ "mData":"overfazhi"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineMonitoring.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineMonitoring.oTable, aoData);
	
	var companyName = $("#toaMachineMonitoringQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#toaMachineMonitoringQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	
};

//重置按钮功能
toaMachineMonitoring.queryReset = function(){
	$('#toaMachineMonitoringQueryForm')[0].reset();
};


//分页查询
toaMachineMonitoring.toaMachineMonitoringList = function () {
	if (toaMachineMonitoring.oTable == null) {
		toaMachineMonitoring.oTable = $('#toaMachineMonitoringTable').dataTable( {
			"iDisplayLength": toaMachineMonitoring.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineMonitoring/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineMonitoring.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineMonitoring.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]}
	        ]
		} );
	} else {
		toaMachineMonitoring.oTable.fnDraw();
	}
};

toaMachineMonitoring.deleteToaMachineMonitoring = function (id) {
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
			toaMachineMonitoring.deleteTimeSet(ids);
		}
	});
};


toaMachineMonitoring.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineMonitoring/deleteByIds.action",
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
			toaMachineMonitoring.toaMachineMonitoringList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineMonitoring.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineMonitoring.toaMachineMonitoringList);
	$("#queryReset").click(toaMachineMonitoring.queryReset);
	//初始化列表方法
	toaMachineMonitoring.toaMachineMonitoringList();
});