var toaProductIdc = {};

//分页处理 start
//分页对象
toaProductIdc.oTable = null;

toaProductIdc.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"customersName"},	{ "mData":"machine"},	{ "mData":"equipment"},	{ "mData":"bandwidth"},	{ "mData":"port"},	{ "mData":"ipAddress"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaProductIdc.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaProductIdc.oTable, aoData);
	
	//var companyName = $("#toaProductIdc#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaProductIdc.queryReset = function(){
	$('#toaProductIdcQueryForm')[0].reset();
};


//分页查询
toaProductIdc.toaProductIdcList = function () {
	if (toaProductIdc.oTable == null) {
		toaProductIdc.oTable = $('#toaProductIdcTable').dataTable( {
			"iDisplayLength": toaProductIdc.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/product/toaProductIdc/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaProductIdc.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaProductIdc.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaProductIdc.oTable.fnDraw();
	}
};

toaProductIdc.deleteToaProductIdc = function (id) {
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
			toaProductIdc.deleteTimeSet(ids);
		}
	});
};


toaProductIdc.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/product/toaProductIdc/deleteByIds.action",
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
			toaProductIdc.toaProductIdcList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaProductIdc.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaProductIdc.toaProductIdcList);
	$("#queryReset").click(toaProductIdc.queryReset);
	//初始化列表方法
	toaProductIdc.toaProductIdcList();
});