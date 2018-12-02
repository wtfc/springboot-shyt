var toaRoomVisitMaintenance = {};

//分页处理 start
//分页对象
toaRoomVisitMaintenance.oTable = null;

toaRoomVisitMaintenance.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyId"},	{ "mData":"companyName"},	{ "mData":"industryType"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"intDate"},	{ "mData":"type"},	{ "mData":"realityType"},	{ "mData":"preOperate"},	{ "mData":"intPeople"},	{ "mData":"intPeopleCard"},	{ "mData":"visitZone"},	{ "mData":"isInput"},	{ "mData":"caozgcs"},	{ "mData":"judge"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{ "mData":"status"},	{ "mData":"remark"},	{ "mData":"preOperateUrl"},	{ "mData":"fileUrl"},	{ "mData":"deleteFlag"},	{ "mData":"createUser"},	{ "mData":"createUserDept"},	{ "mData":"createDate"},	{ "mData":"modifyUser"},	{ "mData":"modifyDate"},	{ "mData":"extStr1"},	{ "mData":"extStr2"},	{ "mData":"extStr3"},	{ "mData":"extStr4"},	{ "mData":"extStr5"},	{ "mData":"extDate1"},	{ "mData":"extDate2"},	{ "mData":"extNum1"},	{ "mData":"extNum2"},	{ "mData":"extNum3"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaRoomVisitMaintenance.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaRoomVisitMaintenance.oTable, aoData);
	
	//var companyName = $("#toaRoomVisitMaintenance#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaRoomVisitMaintenance.queryReset = function(){
	$('#toaRoomVisitMaintenance#ueryForm')[0].reset();
};


//分页查询
toaRoomVisitMaintenance.toaRoomVisitMaintenanceList = function () {
	if (toaRoomVisitMaintenance.oTable == null) {
		toaRoomVisitMaintenance.oTable = $('#toaRoomVisitMaintenance#able').dataTable( {
			"iDisplayLength": toaRoomVisitMaintenance.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaRoomVisitMaintenance/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaRoomVisitMaintenance.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaRoomVisitMaintenance.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaRoomVisitMaintenance.oTable.fnDraw();
	}
};

toaRoomVisitMaintenance.deleteToaRoomVisitMaintenance = function (id) {
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
			toaRoomVisitMaintenance.deleteTimeSet(ids);
		}
	});
};


toaRoomVisitMaintenance.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaRoomVisitMaintenance/deleteByIds.action",
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
			toaRoomVisitMaintenance.toaRoomVisitMaintenanceList();
		}
	});
};

//加载添加DIV
toaRoomVisitMaintenance.loadModule = function (){
	if($.trim($("#toaRoomVisitMaintenance#oduleDiv").html()).length == 0){
		$("#toaRoomVisitMaintenance#oduleDiv").load(getRootPath()+"/machine/toaRoomVisitMaintenance/loadForm.action",null,function(){
			toaRoomVisitMaintenanceModule.show();
		});
	}
	else{
		toaRoomVisitMaintenanceModule.show();
	}
};
		
//加载修改div
toaRoomVisitMaintenance.loadModuleForUpdate = function (id){
	if($.trim($("#toaRoomVisitMaintenance#oduleDiv").html()).length == 0){
		$("#toaRoomVisitMaintenance#oduleDiv").load(getRootPath()+"/machine/toaRoomVisitMaintenance/loadForm.action",null,function(){
			toaRoomVisitMaintenanceModule.get(id);
		});
	}
	else{
		toaRoomVisitMaintenanceModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaRoomVisitMaintenance.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaRoomVisitMaintenance.loadModule);
	$("#showAddDiv_t").click(toaRoomVisitMaintenance.loadModule);
	$("#queryMachine").click(toaRoomVisitMaintenance.toaRoomVisitMaintenanceList);
	$("#queryReset").click(toaRoomVisitMaintenance.queryReset);
	$("#deleteToaRoomVisitMaintenances").click("click", function(){toaRoomVisitMaintenance.deleteToaRoomVisitMaintenance(0);});
	//初始化列表方法
	toaRoomVisitMaintenance.toaRoomVisitMaintenanceList();
});