var toaFinanceAgency = {};

//分页处理 start
//分页对象
toaFinanceAgency.oTable = null;

toaFinanceAgency.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"orderNo"},	{ "mData":"customerId"},	{ "mData":"customerName"},	{ "mData":"department"},	{ "mData":"sale"},	{ "mData":"maintenanSale"},	{ "mData":"cycleStart"},	{ "mData":"cycleEnd"},	{ "mData":"cardNo"},	{ "mData":"cardAmount"},	{ "mData":"incontractStart"},	{ "mData":"incontractEnd"},	{ "mData":"roomName"},	{ "mData":"resources"},	{ "mData":"payType"},	{ "mData":"beforeTax"},	{ "mData":"beforeLittle"},	{ "mData":"paee"},	{ "mData":"payState"},	{ "mData":"payDate"},	{ "mData":"notPay"},	{ "mData":"orderRemark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaFinanceAgency.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaFinanceAgency.oTable, aoData);
	
	//var companyName = $("#toaFinanceAgency#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaFinanceAgency.queryReset = function(){
	$('#toaFinanceAgencyQueryForm')[0].reset();
};


//分页查询
toaFinanceAgency.toaFinanceAgencyList = function () {
	if (toaFinanceAgency.oTable == null) {
		toaFinanceAgency.oTable = $('#toaFinanceAgencyTable').dataTable( {
			"iDisplayLength": toaFinanceAgency.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/finance/toaFinanceAgency/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinanceAgency.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinanceAgency.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaFinanceAgency.oTable.fnDraw();
	}
};

toaFinanceAgency.deleteToaFinanceAgency = function (id) {
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
			toaFinanceAgency.deleteTimeSet(ids);
		}
	});
};


toaFinanceAgency.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/finance/toaFinanceAgency/deleteByIds.action",
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
			toaFinanceAgency.toaFinanceAgencyList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaFinanceAgency.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaFinanceAgency.toaFinanceAgencyList);
	$("#queryReset").click(toaFinanceAgency.queryReset);
	//初始化列表方法
	toaFinanceAgency.toaFinanceAgencyList();
});