var toaContractIncontract = {};

//分页处理 start
//分页对象
toaContractIncontract.oTable = null;

toaContractIncontract.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"contractNumber"},	{ "mData":"leard"},	{ "mData":"contractStatus"},	{ "mData":"contractMoney"},	{ "mData":"seal"},	{ "mData":"place"},	{ "mData":"startDate"},	{ "mData":"endDate"}
];

toaContractIncontract.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaContractIncontract.oTable, aoData);
	
	var companyName = $("#toaContractIncontractQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var endDateBegin = $("#toaContractIncontractQueryForm #endDateBegin").val();
	if(endDateBegin.length > 0){
		aoData.push({ "name": "endDateBegin", "value":endDateBegin});
	}
	var endDateEnd = $("#toaContractIncontractQueryForm #endDateEnd").val();
	if(endDateEnd.length > 0){
		aoData.push({ "name": "endDateEnd", "value":endDateEnd});
	}
	var derive = $("#toaContractIncontractQueryForm #derive").val();
	if(derive.length > 0){
		aoData.push({ "name": "derive", "value":derive});
	}
	var leard = $("#toaContractIncontractQueryForm #leard").val();
	if(leard.length > 0){
		aoData.push({ "name": "leard", "value":leard});
	}
	
};

//重置按钮功能
toaContractIncontract.queryReset = function(){
	$('#toaContractIncontractQueryForm')[0].reset();
	$("#toaContractIncontractQueryForm #endDateBegin").val(toaContractIncontract.dateReckon(0));
	$("#toaContractIncontractQueryForm #endDateEnd").val(toaContractIncontract.dateReckon(-30));
};

toaContractIncontract.dateReckon = function(dayadd){
	var nowdate = new Date();
	nowdate = nowdate.valueOf();
	nowdate = nowdate - dayadd * 24 * 60 * 60 * 1000;
	nowdate = new Date(nowdate);
	
	var dict = {   
	    "yyyy": nowdate.getFullYear(),   
	    "M": nowdate.getMonth() + 1,   
	    "d": nowdate.getDate(),   
	    "H": nowdate.getHours(),   
	    "m": nowdate.getMinutes(),   
	    "s": nowdate.getSeconds(),   
	    "MM": ("" + (nowdate.getMonth() + 101)).substr(1),   
	    "dd": ("" + (nowdate.getDate() + 100)).substr(1),   
	}; 
	var format = "yyyy-MM-dd";
	return format.replace(/(yyyy|MM?|dd?)/g, function() {   
        return dict[arguments[0]];   
    });
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
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}
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
	
	$(".datepicker-input").datepicker();
	$("#toaContractIncontractQueryForm #endDateBegin").val(toaContractIncontract.dateReckon(0));
	$("#toaContractIncontractQueryForm #endDateEnd").val(toaContractIncontract.dateReckon(-30));
	//初始化列表方法
	toaContractIncontract.toaContractIncontractList();
});