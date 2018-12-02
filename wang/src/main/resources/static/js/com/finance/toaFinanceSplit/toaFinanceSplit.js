var toaFinanceSplit = {};

//分页处理 start
//分页对象
toaFinanceSplit.oTable = null;

toaFinanceSplit.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"orderNo"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaFinanceSplit.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaFinanceSplit.oTable, aoData);
	
	//var companyName = $("#toaFinanceSplit#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaFinanceSplit.queryReset = function(){
	$('#toaFinanceSplit#ueryForm')[0].reset();
};


//分页查询
toaFinanceSplit.toaFinanceSplitList = function () {
	if (toaFinanceSplit.oTable == null) {
		toaFinanceSplit.oTable = $('#toaFinanceSplit#able').dataTable( {
			"iDisplayLength": toaFinanceSplit.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaFinanceSplit/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinanceSplit.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinanceSplit.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaFinanceSplit.oTable.fnDraw();
	}
};

toaFinanceSplit.deleteToaFinanceSplit = function (id) {
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
			content: //$//.i18n.prop("JC_SYS_061")
		});
		return;
	}
	msgBox.confirm({
		content: //$//.i18n.prop("JC_SYS_034"),
		success: function(){
			toaFinanceSplit.deleteTimeSet(ids);
		}
	});
};


toaFinanceSplit.deleteTimeSet = function(ids) {
	$//.ajax({
		type : "POST",
		url : getRootPath()+"/shyt/toaFinanceSplit/deleteByIds.action",
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
			toaFinanceSplit.toaFinanceSplitList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaFinanceSplit.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaFinanceSplit.toaFinanceSplitList);
	$("#queryReset").click(toaFinanceSplit.queryReset);
	//初始化列表方法
	toaFinanceSplit.toaFinanceSplitList();
});