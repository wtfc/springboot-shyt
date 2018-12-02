var toaMachineExpress = {};

//分页处理 start
//分页对象
toaMachineExpress.oTable = null;

toaMachineExpress.oTableAoColumns = [
	{ "mData":"expressNumber"},	{ "mData":"companyName"},	{ "mData":"machinaValue"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"type"},	{ "mData":"deviceDs"},	{ "mData":"payType"},	{ "mData":"moneyType"},	{ "mData":"expressPeople"},	{ "mData":"expressAddress"},	{ "mData":"daifuPay"},	{ "mData":"expressDate"},	{ "mData":"caozgcs"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineExpress.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineExpress.oTable, aoData);
	
	var companyName = $("#toaMachineExpressQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#toaMachineExpressQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	
};

//重置按钮功能
toaMachineExpress.queryReset = function(){
	$('#toaMachineExpressQueryForm')[0].reset();
};


//分页查询
toaMachineExpress.toaMachineExpressList = function () {
	if (toaMachineExpress.oTable == null) {
		toaMachineExpress.oTable = $('#toaMachineExpressTable').dataTable( {
			"iDisplayLength": toaMachineExpress.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineExpress/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineExpress.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineExpress.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]}
	        ]
		} );
	} else {
		toaMachineExpress.oTable.fnDraw();
	}
};

toaMachineExpress.deleteToaMachineExpress = function (id) {
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
			toaMachineExpress.deleteTimeSet(ids);
		}
	});
};


toaMachineExpress.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineExpress/deleteByIds.action",
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
			toaMachineExpress.toaMachineExpressList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineExpress.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineExpress.toaMachineExpressList);
	$("#queryReset").click(toaMachineExpress.queryReset);
	//初始化列表方法
	toaMachineExpress.toaMachineExpressList();
});