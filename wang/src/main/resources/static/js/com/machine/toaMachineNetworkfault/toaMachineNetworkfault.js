var toaMachineNetworkfault = {};

//分页处理 start
//分页对象
toaMachineNetworkfault.oTable = null;

toaMachineNetworkfault.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"faultNumber"},	{ "mData":"companyName"},	{ "mData":"operateDate"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"typeOne"},	{ "mData":"typeTwo"},	{ "mData":"processDate"},	{ "mData":"finishDate"},	{ "mData":"faultReason"},	{ "mData":"caozgcs"},	{ "mData":"feedbacks"},	{ "mData":"isFinish"},	{ "mData":"search"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineNetworkfault.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineNetworkfault.oTable, aoData);
	
	var companyName = $("#toaMachineNetworkfaultQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	
};

//重置按钮功能
toaMachineNetworkfault.queryReset = function(){
	$('#toaMachineNetworkfaultQueryForm')[0].reset();
};


//分页查询
toaMachineNetworkfault.toaMachineNetworkfaultList = function () {
	if (toaMachineNetworkfault.oTable == null) {
		toaMachineNetworkfault.oTable = $('#toaMachineNetworkfaultTable').dataTable( {
			"iDisplayLength": toaMachineNetworkfault.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineNetworkfault/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineNetworkfault.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineNetworkfault.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]}
	        ]
		} );
	} else {
		toaMachineNetworkfault.oTable.fnDraw();
	}
};

toaMachineNetworkfault.deleteToaMachineNetworkfault = function (id) {
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
			toaMachineNetworkfault.deleteTimeSet(ids);
		}
	});
};


toaMachineNetworkfault.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineNetworkfault/deleteByIds.action",
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
			toaMachineNetworkfault.toaMachineNetworkfaultList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineNetworkfault.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineNetworkfault.toaMachineNetworkfaultList);
	$("#queryReset").click(toaMachineNetworkfault.queryReset);
	//初始化列表方法
	toaMachineNetworkfault.toaMachineNetworkfaultList();
});