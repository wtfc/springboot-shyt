var toaMachineRestart = {};

//分页处理 start
//分页对象
toaMachineRestart.oTable = null;

toaMachineRestart.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyName"},	{ "mData":"machinaValue"},	/*{ "mData":"contact"},	{ "mData":"tel"},*/	{ "mData":"ip"},	/*{ "mData":"caozgcs"},*/
	{ "mData":"extStr4"},	{ "mData":"operateDate"},	{ "mData":"endDate"},
	{ "mData":function(source){
		var status=source.status;
		if(status=="0"){
			return "排队等待";
		}else if(status=="1"){
			return "操作者接单";
		}else if(status=="2"){
			return "到达设备现场";
		}else if(status=="3"){
			return "正在重启";
		}else if(status=="4"){
			return "重启完成,待评分";
		}else{
			return "工单完成";
		}
	}},
	{ "mData":"operationTypeValue"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineRestart.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineRestart.oTable, aoData);
	
	var companyName = $("#toaMachineRestartQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#toaMachineRestartQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	var status = $("#toaMachineRestartQueryForm #status").val();
	/*if(status.length > 0){*/
		aoData.push({ "name": "status", "value":status});
	/*}*/
};

//重置按钮功能
toaMachineRestart.queryReset = function(){
	$('#toaMachineRestartQueryForm')[0].reset();
};


//分页查询
toaMachineRestart.toaMachineRestartList = function () {
	if (toaMachineRestart.oTable == null) {
		toaMachineRestart.oTable = $('#toaMachineRestartTable').dataTable( {
			"iDisplayLength": toaMachineRestart.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineRestart/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineRestart.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineRestart.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10]}
	        ]
		} );
	} else {
		toaMachineRestart.oTable.fnDraw();
	}
};

toaMachineRestart.toaMachineRestartUpdate=function(id){
	window.location.href= getRootPath()+"/machine/toaMachineRestart/loadForm.action?id="+id+"";
};

toaMachineRestart.deleteToaMachineRestart = function (id) {
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
			toaMachineRestart.deleteTimeSet(ids);
		}
	});
};


toaMachineRestart.deleteTimeSet = function(ids) {
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
			toaMachineRestart.toaMachineRestartList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineRestart.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineRestart.toaMachineRestartList);
	$("#queryReset").click(toaMachineRestart.queryReset);
	//初始化列表方法
	toaMachineRestart.toaMachineRestartList();
});