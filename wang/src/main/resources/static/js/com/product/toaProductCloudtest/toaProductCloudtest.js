var toaProductCloudtest = {};

//分页处理 start
//分页对象
toaProductCloudtest.oTable = null;

toaProductCloudtest.oTableAoColumns = [
	{ "mData":"extStr1"},
	{ "mData":"companyName"},	{ "mData":"cooperateType"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{ "mData":"salePeople"},//	{ "mData":"cpu"},//	{ "mData":"ram"},//	{ "mData":"performance"},//	{ "mData":"stick"},//	{ "mData":"bandwidth"},//	{ "mData":"systemMachine"},	{ "mData":"email"},	{ "mData":"publicIp"},	{ "mData":"chargeTime"},	{ "mData":"finishDate"},	//{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaProductCloudtest.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaProductCloudtest.oTable, aoData);
	
	var extStr1 = $("#toaProductCloudtestQueryForm #extStr1").val();
	if(extStr1.length > 0){
		aoData.push({ "name": "extStr1", "value":extStr1});
	}
	
	var companyName = $("#toaProductCloudtestQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	
	var extStr2 = $("#toaProductCloudtestQueryForm #extStr2").val();
	if(extStr2.length > 0){
		aoData.push({ "name": "extStr2", "value":extStr2});
	}
	
	var salePeople = $("#toaProductCloudtestQueryForm #salePeople").val();
	if(salePeople.length > 0){
		aoData.push({ "name": "salePeople", "value":salePeople});
	}
	
	var startDate = $("#toaProductCloudtestQueryForm #startDate").val();
	if(startDate.length > 0){
		aoData.push({ "name": "startDate", "value":startDate});
	}
	
	var endDate = $("#toaProductCloudtestQueryForm #endDate").val();
	if(endDate.length > 0){
		aoData.push({ "name": "endDate", "value":endDate});
	}
	
	var publicIp = $("#toaProductCloudtestQueryForm #publicIp").val();
	if(publicIp.length > 0){
		aoData.push({ "name": "publicIp", "value":publicIp});
	}
	
	var chargeTime = $("#toaProductCloudtestQueryForm #chargeTime").val();
	if(chargeTime.length > 0){
		aoData.push({ "name": "chargeTime", "value":chargeTime});
	}
	
	var finishDate = $("#toaProductCloudtestQueryForm #finishDate").val();
	if(finishDate.length > 0){
		aoData.push({ "name": "finishDate", "value":finishDate});
	}
	
};

//重置按钮功能
toaProductCloudtest.queryReset = function(){
	$('#toaProductCloudtestQueryForm')[0].reset();
};


//分页查询
toaProductCloudtest.toaProductCloudtestList = function () {
	if (toaProductCloudtest.oTable == null) {
		toaProductCloudtest.oTable = $('#toaProductCloudtestTable').dataTable( {
			"iDisplayLength": toaProductCloudtest.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/product/toaProductCloudtest/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaProductCloudtest.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaProductCloudtest.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10]}
	        ]
		} );
	} else {
		toaProductCloudtest.oTable.fnDraw();
	}
};

toaProductCloudtest.deleteToaProductCloudtest = function (id) {
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
			toaProductCloudtest.deleteTimeSet(ids);
		}
	});
};


toaProductCloudtest.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/product/toaProductCloudtest/deleteByIds.action",
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
			toaProductCloudtest.toaProductCloudtestList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaProductCloudtest.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaProductCloudtest.toaProductCloudtestList);
	$("#queryReset").click(toaProductCloudtest.queryReset);
	//初始化列表方法
	toaProductCloudtest.toaProductCloudtestList();
});