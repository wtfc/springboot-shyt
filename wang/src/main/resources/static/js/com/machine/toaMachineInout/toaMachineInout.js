var toaMachineInout = {};

//分页处理 start
//分页对象
toaMachineInout.oTable = null;

toaMachineInout.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyName"},	{ "mData":"machinaValue"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"intDate"},	{ "mData":"type"},	{ "mData":"countt"},	{ "mData":"deviceInfo"},	{ "mData":"intPeople"},	{ "mData":"isInput"},	{ "mData":"caozgcs"},	{ "mData":"origin"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineInout.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineInout.oTable, aoData);
	
	var companyName = $("#toaMachineInoutQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#toaMachineInoutQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	
};

//重置按钮功能
toaMachineInout.queryReset = function(){
	$('#toaMachineInoutQueryForm')[0].reset();
};


//分页查询
toaMachineInout.toaMachineInoutList = function () {
	if (toaMachineInout.oTable == null) {
		toaMachineInout.oTable = $('#toaMachineInoutTable').dataTable( {
			"iDisplayLength": toaMachineInout.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineInout/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineInout.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineInout.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]}
	        ]
		} );
	} else {
		toaMachineInout.oTable.fnDraw();
	}
};

toaMachineInout.deleteToaMachineInout = function (id) {
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
			toaMachineInout.deleteTimeSet(ids);
		}
	});
};


toaMachineInout.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineInout/deleteByIds.action",
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
			toaMachineInout.toaMachineInoutList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineInout.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineInout.toaMachineInoutList);
	$("#queryReset").click(toaMachineInout.queryReset);
	//初始化列表方法
	toaMachineInout.toaMachineInoutList();
});