var toaProductCloud = {};

//分页处理 start
//分页对象
toaProductCloud.oTable = null;

toaProductCloud.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"customersName"},
	{ "mData":"supporter"},	{ "mData":"cpu"},	{ "mData":"ram"},	{ "mData":"stick"},	{ "mData":"performance"},	{ "mData":"cloudPhoto"},	{ "mData":"cloudDive"},	{ "mData":"publicIp"},	{ "mData":"bandwidth"},	{ "mData":"router"},	{ "mData":"loadBalancer"},	{ "mData":"amount"},	{ "mData":"resourceType"},	{ "mData":"startDate"},	{ "mData":"startEnd"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaProductCloud.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaProductCloud.oTable, aoData);
	
	var customersName = $("#toaProductCloudQueryForm #customersName").val();
	if(customersName.length > 0){
		aoData.push({ "name": "customersName", "value":customersName});
	}
	var supporter = $("#toaProductCloudQueryForm #supporter").val();
	if(supporter.length > 0){
		aoData.push({ "name": "supporter", "value":supporter});
	}
	
};

//重置按钮功能
toaProductCloud.queryReset = function(){
	$('#toaProductCloudQueryForm')[0].reset();
};


//分页查询
toaProductCloud.toaProductCloudList = function () {
	if (toaProductCloud.oTable == null) {
		toaProductCloud.oTable = $('#toaProductCloudTable').dataTable( {
			"iDisplayLength": toaProductCloud.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/product/toaProductCloud/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaProductCloud.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaProductCloud.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17]}
	        ]
		} );
	} else {
		toaProductCloud.oTable.fnDraw();
	}
};

toaProductCloud.deleteToaProductCloud = function (id) {
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
			toaProductCloud.deleteTimeSet(ids);
		}
	});
};


toaProductCloud.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/product/toaProductCloud/deleteByIds.action",
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
			toaProductCloud.toaProductCloudList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaProductCloud.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaProductCloud.toaProductCloudList);
	$("#queryReset").click(toaProductCloud.queryReset);
	//初始化列表方法
	toaProductCloud.toaProductCloudList();
});