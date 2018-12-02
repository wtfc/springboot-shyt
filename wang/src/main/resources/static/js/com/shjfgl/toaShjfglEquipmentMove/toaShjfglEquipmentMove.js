var toaShjfglEquipmentMove = {};

//分页处理 start
//分页对象
toaShjfglEquipmentMove.oTable = null;

toaShjfglEquipmentMove.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"equipmentId"},	{ "mData":"companyId"},	{ "mData":"companyName"},	{ "mData":"contact"},	{ "mData":"contactWay"},	{ "mData":"cabinet"},	{ "mData":"ip"},	{ "mData":"sn"},	{ "mData":"brand"},	{ "mData":"machina"},	{ "mData":"caozgcs"},	{ "mData":"caozgcsName"},	{ "mData":"status"},	{ "mData":"warnDate"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{ "mData":"arrivalDate"},	{ "mData":"shelfDate"},	{ "mData":"arrivalNewDate"},	{ "mData":"buildDate"},	{ "mData":"workTime"},	{ "mData":"storageLocation"},	{ "mData":"connectionRequirements"},	{ "mData":"cableUsage"},	{ "mData":"installSystem"},	{ "mData":"ipConfiguration"},	{ "mData":"ipEmly"},	{ "mData":"remoteShutdown"},	{ "mData":"shelfRequirements"},	{ "mData":"cabinetSpace"},	{ "mData":"cabinetPower"},	{ "mData":"checkAgainst"},	{ "mData":"powerOff"},	{ "mData":"equipmentConstruction"},	{ "mData":"mormalEquipment"},	{ "mData":"haveAfterOperate"},	{ "mData":"regionInformation"},	{ "mData":"operationType"},	{ "mData":"operationTypeImg"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaShjfglEquipmentMove.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaShjfglEquipmentMove.oTable, aoData);
	
	//var companyName = $("#toaShjfglEquipmentMoveQueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaShjfglEquipmentMove.queryReset = function(){
	$('#toaShjfglEquipmentMoveQueryForm')[0].reset();
};


//分页查询
toaShjfglEquipmentMove.toaShjfglEquipmentMoveList = function () {
	if (toaShjfglEquipmentMove.oTable == null) {
		toaShjfglEquipmentMove.oTable = $('#toaShjfglEquipmentMoveTable').dataTable( {
			"iDisplayLength": toaShjfglEquipmentMove.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaShjfglEquipmentMove/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaShjfglEquipmentMove.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaShjfglEquipmentMove.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaShjfglEquipmentMove.oTable.fnDraw();
	}
};

toaShjfglEquipmentMove.deleteToaShjfglEquipmentMove = function (id) {
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
			toaShjfglEquipmentMove.deleteTimeSet(ids);
		}
	});
};


toaShjfglEquipmentMove.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaShjfglEquipmentMove/deleteByIds.action",
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
			toaShjfglEquipmentMove.toaShjfglEquipmentMoveList();
		}
	});
};

//加载添加DIV
toaShjfglEquipmentMove.loadModule = function (){
	if($.trim($("#toaShjfglEquipmentMoveModuleDiv").html()).length == 0){
		$("#toaShjfglEquipmentMoveModuleDiv").load(getRootPath()+"/machine/toaShjfglEquipmentMove/loadForm.action",null,function(){
			toaShjfglEquipmentMoveModule.show();
		});
	}
	else{
		toaShjfglEquipmentMoveModule.show();
	}
};
		
//加载修改div
toaShjfglEquipmentMove.loadModuleForUpdate = function (id){
	if($.trim($("#toaShjfglEquipmentMoveModuleDiv").html()).length == 0){
		$("#toaShjfglEquipmentMoveModuleDiv").load(getRootPath()+"/machine/toaShjfglEquipmentMove/loadForm.action",null,function(){
			toaShjfglEquipmentMoveModule.get(id);
		});
	}
	else{
		toaShjfglEquipmentMoveModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaShjfglEquipmentMove.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaShjfglEquipmentMove.loadModule);
	$("#showAddDiv_t").click(toaShjfglEquipmentMove.loadModule);
	$("#queryMachine").click(toaShjfglEquipmentMove.toaShjfglEquipmentMoveList);
	$("#queryReset").click(toaShjfglEquipmentMove.queryReset);
	$("#deleteToaShjfglEquipmentMoves").click("click", function(){toaShjfglEquipmentMove.deleteToaShjfglEquipmentMove(0);});
	//初始化列表方法
	toaShjfglEquipmentMove.toaShjfglEquipmentMoveList();
});