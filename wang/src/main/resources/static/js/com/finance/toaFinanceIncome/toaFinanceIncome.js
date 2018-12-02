var toaFinanceIncome = {};

//分页处理 start
//分页对象
toaFinanceIncome.oTable = null;

toaFinanceIncome.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"orderNo"},	{ "mData":"resourceType"},	{ "mData":"companyId"},	{ "mData":"companyName"},	{ "mData":"companyType"},	{ "mData":"oldDepartment"},	{ "mData":"department"},	{ "mData":"sale"},	{ "mData":"maintenanSale"},	{ "mData":"cardNo"},	{ "mData":"cardAmount"},	{ "mData":"roomName"},	{ "mData":"payType"},	{ "mData":"cycleStart"},	{ "mData":"cycleEnd"},	{ "mData":"orderDate"},	{ "mData":"months"},	{ "mData":"startIntel"},	{ "mData":"lineCategory"},	{ "mData":"singleCharg"},	{ "mData":"overflowCategory"},	{ "mData":"performanceAmount"},	{ "mData":"cardStockAmount"},	{ "mData":"prestoreAmount"},	{ "mData":"discount"},	{ "mData":"orderRemark"},	{ "mData":"productResources"},	{ "mData":"minBandwidth"},	{ "mData":"minBandwidthPrice"},	{ "mData":"portBandwidth"},	{ "mData":"portBandwidthPrice"},	{ "mData":"overflowBandwidth"},	{ "mData":"overflowBandwidthPrice"},	{ "mData":"cabinetNum"},	{ "mData":"cabinetPrice"},	{ "mData":"serviceNum"},	{ "mData":"servicePrice"},	{ "mData":"ipNum"},	{ "mData":"ipPrice"},	{ "mData":"switchNum"},	{ "mData":"switchPrice"},	{ "mData":"odfNum"},	{ "mData":"odfPrice"},	{ "mData":"portNum"},	{ "mData":"portPrice"},	{ "mData":"memoryNum"},	{ "mData":"memoryPrice"},	{ "mData":"cpuNum"},	{ "mData":"cpuPrice"},	{ "mData":"diskNum"},	{ "mData":"diskPrice"},	{ "mData":"cycle"},	{ "mData":"timingStatus"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaFinanceIncome.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaFinanceIncome.oTable, aoData);
	
	//var companyName = $("#toaFinanceIncome#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaFinanceIncome.queryReset = function(){
	$('#toaFinanceIncomeQueryForm')[0].reset();
};


//分页查询
toaFinanceIncome.toaFinanceIncomeList = function () {
	if (toaFinanceIncome.oTable == null) {
		toaFinanceIncome.oTable = $('#toaFinanceIncomeTable').dataTable( {
			"iDisplayLength": toaFinanceIncome.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/finance/toaFinanceIncome/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinanceIncome.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinanceIncome.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaFinanceIncome.oTable.fnDraw();
	}
};

toaFinanceIncome.deleteToaFinanceIncome = function (id) {
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
			toaFinanceIncome.deleteTimeSet(ids);
		}
	});
};


toaFinanceIncome.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/finance/toaFinanceIncome/deleteByIds.action",
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
			toaFinanceIncome.toaFinanceIncomeList();
		}
	});
};

//加载添加DIV
toaFinanceIncome.loadModule = function (){
	if($.trim($("#toaFinanceIncomeModuleDiv").html()).length == 0){
		$("#toaFinanceIncomeModuleDiv").load(getRootPath()+"/finance/toaFinanceIncome/loadForm.action",null,function(){
			toaFinanceIncomeModule.show();
		});
	}
	else{
		toaFinanceIncomeModule.show();
	}
};
		
//加载修改div
toaFinanceIncome.loadModuleForUpdate = function (id){
	if($.trim($("#toaFinanceIncomeModuleDiv").html()).length == 0){
		$("#toaFinanceIncomeModuleDiv").load(getRootPath()+"/finance/toaFinanceIncome/loadForm.action",null,function(){
			toaFinanceIncomeModule.get(id);
		});
	}
	else{
		toaFinanceIncomeModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaFinanceIncome.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaFinanceIncome.loadModule);
	$("#showAddDiv_t").click(toaFinanceIncome.loadModule);
	$("#queryMachine").click(toaFinanceIncome.toaFinanceIncomeList);
	$("#queryReset").click(toaFinanceIncome.queryReset);
	$("#deleteToaFinanceIncomes").click("click", function(){toaFinanceIncome.deleteToaFinanceIncome(0);});
	//初始化列表方法
	toaFinanceIncome.toaFinanceIncomeList();
});