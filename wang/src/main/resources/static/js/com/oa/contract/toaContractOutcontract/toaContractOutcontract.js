var toaContractOutcontract = {};

//分页处理 start
//分页对象
toaContractOutcontract.oTable = null;

toaContractOutcontract.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"customer"},	{ "mData":"contractNumber"},	{ "mData":"leard"},	{ "mData":"leardDate"},	{ "mData":"agreement"},	{ "mData":"contractStatus"},	{ "mData":"contractMoney"},	{ "mData":"seal"},	{ "mData":"place"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{ "mData":"resource"},	{ "mData":"contractFile"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaContractOutcontract.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaContractOutcontract.oTable, aoData);
	
	//var companyName = $("#toaContractOutcontract#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaContractOutcontract.queryReset = function(){
	$('#toaContractOutcontractQueryForm')[0].reset();
};


//分页查询
toaContractOutcontract.toaContractOutcontractList = function () {
	if (toaContractOutcontract.oTable == null) {
		toaContractOutcontract.oTable = $('#toaContractOutcontractTable').dataTable( {
			"iDisplayLength": toaContractOutcontract.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/contract/toaContractOutcontract/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaContractOutcontract.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaContractOutcontract.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaContractOutcontract.oTable.fnDraw();
	}
};

toaContractOutcontract.deleteToaContractOutcontract = function (id) {
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
			toaContractOutcontract.deleteTimeSet(ids);
		}
	});
};


toaContractOutcontract.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/contract/toaContractOutcontract/deleteByIds.action",
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
			toaContractOutcontract.toaContractOutcontractList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaContractOutcontract.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaContractOutcontract.toaContractOutcontractList);
	$("#queryReset").click(toaContractOutcontract.queryReset);
	//初始化列表方法
	toaContractOutcontract.toaContractOutcontractList();
});