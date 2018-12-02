var toaNetworkMachine = {};

//分页处理 start
//分页对象
toaNetworkMachine.oTable = null;

toaNetworkMachine.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"machine"},	{ "mData":"machineName"},	{ "mData":"machineType"},	{ "mData":"loginIp"},	{ "mData":"machineNumber"},	{ "mData":"describes"},	{ "mData":"serialNumber"},	{ "mData":"address"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkMachine.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkMachine.oTable, aoData);
	
	var machine = $("#toaNetworkMachineQueryForm #machine").val();
	if(machine.length > 0){
		aoData.push({ "name": "machine", "value":machine});
	}
	var machineName = $("#toaNetworkMachineQueryForm #machineName").val();
	if(machineName.length > 0){
		aoData.push({ "name": "machineName", "value":machineName});
	}
	var machineType = $("#toaNetworkMachineQueryForm #machineType").val();
	if(machineType.length > 0){
		aoData.push({ "name": "machineType", "value":machineType});
	}
	var loginIp = $("#toaNetworkMachineQueryForm #loginIp").val();
	if(loginIp.length > 0){
		aoData.push({ "name": "loginIp", "value":loginIp});
	}
	
};

//重置按钮功能
toaNetworkMachine.queryReset = function(){
	$('#toaNetworkMachineQueryForm')[0].reset();
};


//分页查询
toaNetworkMachine.toaNetworkMachineList = function () {
	if (toaNetworkMachine.oTable == null) {
		toaNetworkMachine.oTable = $('#toaNetworkMachineTable').dataTable( {
			"iDisplayLength": toaNetworkMachine.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkMachine/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkMachine.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkMachine.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10]}
	        ]
		} );
	} else {
		toaNetworkMachine.oTable.fnDraw();
	}
};

toaNetworkMachine.deleteToaNetworkMachine = function (id) {
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
			toaNetworkMachine.deleteTimeSet(ids);
		}
	});
};


toaNetworkMachine.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkMachine/deleteByIds.action",
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
			toaNetworkMachine.toaNetworkMachineList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkMachine.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkMachine.toaNetworkMachineList);
	$("#queryReset").click(toaNetworkMachine.queryReset);
	//初始化列表方法
	toaNetworkMachine.toaNetworkMachineList();
});