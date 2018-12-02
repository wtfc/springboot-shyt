var toaMachineRestartEngine = {};

//分页处理 start
//分页对象
toaMachineRestartEngine.oTable = null;

toaMachineRestartEngine.oTableAoColumns = [
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
	}},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineRestartEngine.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineRestartEngine.oTable, aoData);
	
	var companyName = $("#toaMachineRestartEngineQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#toaMachineRestartEngineQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	var status = $("#toaMachineRestartEngineQueryForm #status").val();
	/*if(status.length > 0){*/
		aoData.push({ "name": "status", "value":status});
	/*}*/
};

//重置按钮功能
toaMachineRestartEngine.queryReset = function(){
	$('#toaMachineRestartEngineQueryForm')[0].reset();
};


//分页查询
toaMachineRestartEngine.toaMachineRestartEngineList = function () {
	if (toaMachineRestartEngine.oTable == null) {
		toaMachineRestartEngine.oTable = $('#toaMachineRestartEngineTable').dataTable( {
			"iDisplayLength": toaMachineRestartEngine.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineRestart/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineRestartEngine.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineRestartEngine.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}
	        ]
		} );
	} else {
		toaMachineRestartEngine.oTable.fnDraw();
	}
};
//审批
toaMachineRestartEngine.toaMachineRestartEngineUpdate=function(id){
	window.location.href= getRootPath()+"/machine/toaMachineRestart/loadEngine.action?id="+id+"";
};

toaMachineRestartEngine.deleteToaMachineRestartEngine = function (id) {
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
			toaMachineRestartEngine.deleteTimeSet(ids);
		}
	});
};


toaMachineRestartEngine.deleteTimeSet = function(ids) {
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
			toaMachineRestartEngine.toaMachineRestartEngineList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineRestartEngine.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineRestartEngine.toaMachineRestartEngineList);
	$("#queryReset").click(toaMachineRestartEngine.queryReset);
	//初始化列表方法
	toaMachineRestartEngine.toaMachineRestartEngineList();
});