var toaMachineRestartDirector = {};

//分页处理 start
//分页对象
toaMachineRestartDirector.oTable = null;

toaMachineRestartDirector.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyName"},	{ "mData":"machinaValue"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"ip"},	{ "mData":"caozgcs"},	{ "mData":"operateDate"},	{ "mData":"endDate"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineRestartDirector.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineRestartDirector.oTable, aoData);
	
	var companyName = $("#toaMachineRestartDirectorQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#toaMachineRestartDirectorQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	var status = $("#toaMachineRestartDirectorQueryForm #status").val();
	/*if(status.length > 0){*/
		aoData.push({ "name": "status", "value":status});
	/*}*/
	var extStr1 = $("#toaMachineRestartDirectorQueryForm #extStr1").val();
	/*if(extStr1.length > 0){*/
		aoData.push({ "name": "extStr1", "value":extStr1});
	/*}*/	
};

//重置按钮功能
toaMachineRestartDirector.queryReset = function(){
	$('#toaMachineRestartDirectorQueryForm')[0].reset();
};


//分页查询
toaMachineRestartDirector.toaMachineRestartDirectorList = function () {
	if (toaMachineRestartDirector.oTable == null) {
		toaMachineRestartDirector.oTable = $('#toaMachineRestartDirectorTable').dataTable( {
			"iDisplayLength": toaMachineRestartDirector.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineRestart/manageDirectors.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineRestartDirector.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineRestartDirector.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10]}
	        ]
		} );
	} else {
		toaMachineRestartDirector.oTable.fnDraw();
	}
};

//审批
toaMachineRestartDirector.toaMachineRestartDirectorUpdate=function(id){
	window.location.href= getRootPath()+"/machine/toaMachineRestart/loadDirector.action?id="+id+"";
};

toaMachineRestartDirector.deleteToaMachineRestartDirector = function (id) {
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
			toaMachineRestartDirector.deleteTimeSet(ids);
		}
	});
};


toaMachineRestartDirector.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineRestart/deleteByIds.action",
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
			toaMachineRestartDirector.toaMachineRestartDirectorList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineRestartDirector.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineRestartDirector.toaMachineRestartDirectorList);
	$("#queryReset").click(toaMachineRestartDirector.queryReset);
	//初始化列表方法
	toaMachineRestartDirector.toaMachineRestartDirectorList();
});