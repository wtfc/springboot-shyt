var toaEquipmentUpDown = {};

//分页处理 start
//分页对象
toaEquipmentUpDown.oTable = null;

toaEquipmentUpDown.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyId"},	{ "mData":"companyName"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"billDate"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{ "mData":"equipmentId"},	{ "mData":"address"},	{ "mData":"lineAccess"},	{ "mData":"lineUseType"},	{ "mData":"isInstallOperation"},	{ "mData":"isConfigIp"},	{ "mData":"isSpaceSatisfied"},	{ "mData":"isElectricitySatisfied"},	{ "mData":"realLineAccess"},	{ "mData":"intDate"},	{ "mData":"isRemoteClose"},	{ "mData":"afterDown"},	{ "mData":"equipmentCheckResult"},	{ "mData":"closeResult"},	{ "mData":"downEquipmentList"},	{ "mData":"haveAfterOperate"},	{ "mData":"operationType"},	{ "mData":"operationTypeImg"},	{ "mData":"machina"},	{ "mData":"caozgcs"},	{ "mData":"caozgcsName"},	{ "mData":"status"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaEquipmentUpDown.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaEquipmentUpDown.oTable, aoData);
	
	//var companyName = $("#toaEquipmentUpDownQueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaEquipmentUpDown.queryReset = function(){
	$('#toaEquipmentUpDownQueryForm')[0].reset();
};


//分页查询
toaEquipmentUpDown.toaEquipmentUpDownList = function () {
	if (toaEquipmentUpDown.oTable == null) {
		toaEquipmentUpDown.oTable = $('#toaEquipmentUpDownTable').dataTable( {
			"iDisplayLength": toaEquipmentUpDown.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaEquipmentUpDown/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaEquipmentUpDown.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaEquipmentUpDown.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaEquipmentUpDown.oTable.fnDraw();
	}
};

toaEquipmentUpDown.deleteToaEquipmentUpDown = function (id) {
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
			toaEquipmentUpDown.deleteTimeSet(ids);
		}
	});
};


toaEquipmentUpDown.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaEquipmentUpDown/deleteByIds.action",
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
			toaEquipmentUpDown.toaEquipmentUpDownList();
		}
	});
};

//加载添加DIV
toaEquipmentUpDown.loadModule = function (){
	if($.trim($("#toaEquipmentUpDownModuleDiv").html()).length == 0){
		$("#toaEquipmentUpDownModuleDiv").load(getRootPath()+"/machine/toaEquipmentUpDown/loadForm.action",null,function(){
			toaEquipmentUpDownModule.show();
		});
	}
	else{
		toaEquipmentUpDownModule.show();
	}
};
		
//加载修改div
toaEquipmentUpDown.loadModuleForUpdate = function (id){
	if($.trim($("#toaEquipmentUpDownModuleDiv").html()).length == 0){
		$("#toaEquipmentUpDownModuleDiv").load(getRootPath()+"/machine/toaEquipmentUpDown/loadForm.action",null,function(){
			toaEquipmentUpDownModule.get(id);
		});
	}
	else{
		toaEquipmentUpDownModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaEquipmentUpDown.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaEquipmentUpDown.loadModule);
	$("#showAddDiv_t").click(toaEquipmentUpDown.loadModule);
	$("#queryMachine").click(toaEquipmentUpDown.toaEquipmentUpDownList);
	$("#queryReset").click(toaEquipmentUpDown.queryReset);
	$("#deleteToaEquipmentUpDowns").click("click", function(){toaEquipmentUpDown.deleteToaEquipmentUpDown(0);});
	//初始化列表方法
	toaEquipmentUpDown.toaEquipmentUpDownList();
});