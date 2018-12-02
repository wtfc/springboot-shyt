var toaContractIncontract = {};

//分页处理 start
//分页对象
toaContractIncontract.oTable = null;

toaContractIncontract.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"contractNumber"},	{ "mData":"leard"},	{ "mData":"contractStatus"},	{ "mData":"contractMoney"},	{ "mData":"seal"},	{ "mData":"place"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaContractIncontract.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaContractIncontract.oTable, aoData);
	
	var companyName = $("#toaContractIncontractQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var contractStatus = $("#toaContractIncontractQueryForm #contractStatus").val();
	if(contractStatus.length > 0){
		aoData.push({ "name": "contractStatus", "value":contractStatus});
	}
	var leard = $("#toaContractIncontractQueryForm #leard").val();
	if(leard.length > 0){
		aoData.push({ "name": "leard", "value":leard});
	}
	var leardDate = $("#toaContractIncontractQueryForm #leardDate").val();
	if(leardDate.length > 0){
		aoData.push({ "name": "leardDate", "value":leardDate});
	}
	var contractMoney = $("#toaContractIncontractQueryForm #contractMoney").val();
	if(contractMoney.length > 0){
		aoData.push({ "name": "contractMoney", "value":contractMoney});
	}
	var contractNumber = $("#toaContractIncontractQueryForm #contractNumber").val();
	if(contractNumber.length > 0){
		aoData.push({ "name": "contractNumber", "value":contractNumber});
	}
	
};

//重置按钮功能
toaContractIncontract.queryReset = function(){
	$('#toaContractIncontractQueryForm')[0].reset();
};


//分页查询
toaContractIncontract.toaContractIncontractList = function () {
	if (toaContractIncontract.oTable == null) {
		toaContractIncontract.oTable = $('#toaContractIncontractTable').dataTable( {
			"iDisplayLength": toaContractIncontract.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/contract/toaContractIncontract/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaContractIncontract.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaContractIncontract.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10]}
	        ]
		} );
	} else {
		toaContractIncontract.oTable.fnDraw();
	}
};

toaContractIncontract.deleteToaContractIncontract = function (id) {
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
			toaContractIncontract.deleteTimeSet(ids);
		}
	});
};


toaContractIncontract.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/contract/toaContractIncontract/deleteByIds.action",
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
			toaContractIncontract.toaContractIncontractList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaContractIncontract.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaContractIncontract.toaContractIncontractList);
	$("#queryReset").click(toaContractIncontract.queryReset);
	//初始化列表方法
	toaContractIncontract.toaContractIncontractList();
});