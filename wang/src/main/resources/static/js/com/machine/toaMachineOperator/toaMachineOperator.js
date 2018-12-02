var toaMachineOperator = {};

//分页处理 start
//分页对象
toaMachineOperator.oTable = null;

toaMachineOperator.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyName"},	{ "mData":"machinaValue"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"operateType"},	{ "mData":"intDate"},	{ "mData":"caozgcs"},	{ "mData":"operateDate"},	{ "mData":"endDate"},	{ "mData":"isFinish"},	{ "mData":"countt"},	{ "mData":"search"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineOperator.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineOperator.oTable, aoData);
	
	var companyName = $("#toaMachineOperatorQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#toaMachineOperatorQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	
};

//重置按钮功能
toaMachineOperator.queryReset = function(){
	$('#toaMachineOperatorQueryForm')[0].reset();
};


//分页查询
toaMachineOperator.toaMachineOperatorList = function () {
	if (toaMachineOperator.oTable == null) {
		toaMachineOperator.oTable = $('#toaMachineOperatorTable').dataTable( {
			"iDisplayLength": toaMachineOperator.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineOperator/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineOperator.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineOperator.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]}
	        ]
		} );
	} else {
		toaMachineOperator.oTable.fnDraw();
	}
};

toaMachineOperator.deleteToaMachineOperator = function (id) {
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
			toaMachineOperator.deleteTimeSet(ids);
		}
	});
};


toaMachineOperator.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineOperator/deleteByIds.action",
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
			toaMachineOperator.toaMachineOperatorList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineOperator.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineOperator.toaMachineOperatorList);
	$("#queryReset").click(toaMachineOperator.queryReset);
	//初始化列表方法
	toaMachineOperator.toaMachineOperatorList();
});