var toaShytTestresult = {};

//分页处理 start
//分页对象
toaShytTestresult.oTable = null;

toaShytTestresult.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"companyId"},	{ "mData":"machine"},	{ "mData":"tradeUser"},	{ "mData":"serviceDate"},	{ "mData":"linkUser"},	{ "mData":"companyOld"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaShytTestresult.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaShytTestresult.oTable, aoData);
	
	//var companyName = $("#toaShytTestresult#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaShytTestresult.queryReset = function(){
	$('#toaShytTestresultQueryForm')[0].reset();
};


//分页查询
toaShytTestresult.toaShytTestresultList = function () {
	if (toaShytTestresult.oTable == null) {
		toaShytTestresult.oTable = $('#toaShytTestresultTable').dataTable( {
			"iDisplayLength": toaShytTestresult.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytTestresult/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaShytTestresult.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaShytTestresult.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaShytTestresult.oTable.fnDraw();
	}
};

toaShytTestresult.deleteToaShytTestresult = function (id) {
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
			toaShytTestresult.deleteTimeSet(ids);
		}
	});
};


toaShytTestresult.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/shyt/toaShytTestresult/deleteByIds.action",
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
			toaShytTestresult.toaShytTestresultList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaShytTestresult.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaShytTestresult.toaShytTestresultList);
	$("#queryReset").click(toaShytTestresult.queryReset);
	//初始化列表方法
	toaShytTestresult.toaShytTestresultList();
});