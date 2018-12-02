var outContract = {};

//分页处理 start
//分页对象
outContract.oTable = null;

outContract.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "contractNumber"},
	{ "mData": "leard"},
	{ "mData": "leardDate"},
	{ "mData": "customer"},
	{ "mData": "companyName"},
	{ "mData": "contractMoney"},
	{ "mData": "agreement"},
	{ "mData": "startDate"},
	{ "mData": "endDate" },
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

outContract.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(outContract.oTable, aoData);
	
	var companyName = $("#outContractQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var customer = $("#outContractQueryForm #customer").val();
	if(customer.length > 0){
		aoData.push({ "name": "customer", "value": customer});
	}
	var contractNumber = $("#outContractQueryForm #contractNumber").val();
	if(contractNumber.length > 0){
		aoData.push({ "name": "contractNumber", "value": contractNumber});
	}
	var agreement = $("#outContractQueryForm #agreement").val();
	if(agreement.length > 0){
		aoData.push({ "name": "agreement", "value": agreement});
	}
	var startDate = $("#outContractQueryForm #startDate").val();
	if(startDate.length > 0){
		aoData.push({ "name": "startDate", "value": startDate});
	}
	var endDate = $("#outContractQueryForm #endDate").val();
	if(agreement.length > 0){
		aoData.push({ "name": "endDate", "value": endDate});
	}
};

//重置按钮功能
outContract.queryReset = function(){
	$('#outContractQueryForm')[0].reset();
};


//分页查询
outContract.outContractList = function () {
	
	if (outContract.oTable == null) {
		outContract.oTable = $('#outContractTable').dataTable( {
			
			"iDisplayLength": outContract.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/outContract/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": outContract.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				outContract.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10]}
	        ]
		} );
	} else {
		outContract.oTable.fnDraw();
	}
};

outContract.deleteOutContract = function (id) {
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
			outContract.deleteTimeSet(ids);
		}
	});
};


outContract.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/shyt/outContract/deleteByIds.action",
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
			outContract.outContractList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	outContract.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(outContract.outContractList);
	$("#queryReset").click(outContract.queryReset);
	//初始化列表方法
	outContract.outContractList();
});