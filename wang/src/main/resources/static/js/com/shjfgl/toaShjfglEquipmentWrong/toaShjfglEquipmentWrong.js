var toaShjfglEquipmentWrong = {};

//分页处理 start
//分页对象
toaShjfglEquipmentWrong.oTable = null;

toaShjfglEquipmentWrong.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyId"},	{ "mData":"companyName"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"billDate"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{ "mData":"equipmentId"},	{ "mData":"equipmentCheckResult"},	{ "mData":"isReport"},	{ "mData":"haveAfterOperate"},	{ "mData":"operationType"},	{ "mData":"operationTypeImg"},	{ "mData":"machina"},	{ "mData":"caozgcs"},	{ "mData":"caozgcsName"},	{ "mData":"status"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaShjfglEquipmentWrong.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaShjfglEquipmentWrong.oTable, aoData);
	
	//var companyName = $("#toaShjfglEquipmentWrongQueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaShjfglEquipmentWrong.queryReset = function(){
	$('#toaShjfglEquipmentWrongQueryForm')[0].reset();
};


//分页查询
toaShjfglEquipmentWrong.toaShjfglEquipmentWrongList = function () {
	if (toaShjfglEquipmentWrong.oTable == null) {
		toaShjfglEquipmentWrong.oTable = $('#toaShjfglEquipmentWrongTable').dataTable( {
			"iDisplayLength": toaShjfglEquipmentWrong.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaShjfglEquipmentWrong/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaShjfglEquipmentWrong.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaShjfglEquipmentWrong.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaShjfglEquipmentWrong.oTable.fnDraw();
	}
};

toaShjfglEquipmentWrong.deleteToaShjfglEquipmentWrong = function (id) {
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
			toaShjfglEquipmentWrong.deleteTimeSet(ids);
		}
	});
};


toaShjfglEquipmentWrong.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaShjfglEquipmentWrong/deleteByIds.action",
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
			toaShjfglEquipmentWrong.toaShjfglEquipmentWrongList();
		}
	});
};

//加载添加DIV
toaShjfglEquipmentWrong.loadModule = function (){
	if($.trim($("#toaShjfglEquipmentWrongModuleDiv").html()).length == 0){
		$("#toaShjfglEquipmentWrongModuleDiv").load(getRootPath()+"/machine/toaShjfglEquipmentWrong/loadForm.action",null,function(){
			toaShjfglEquipmentWrongModule.show();
		});
	}
	else{
		toaShjfglEquipmentWrongModule.show();
	}
};
		
//加载修改div
toaShjfglEquipmentWrong.loadModuleForUpdate = function (id){
	if($.trim($("#toaShjfglEquipmentWrongModuleDiv").html()).length == 0){
		$("#toaShjfglEquipmentWrongModuleDiv").load(getRootPath()+"/machine/toaShjfglEquipmentWrong/loadForm.action",null,function(){
			toaShjfglEquipmentWrongModule.get(id);
		});
	}
	else{
		toaShjfglEquipmentWrongModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaShjfglEquipmentWrong.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaShjfglEquipmentWrong.loadModule);
	$("#showAddDiv_t").click(toaShjfglEquipmentWrong.loadModule);
	$("#queryMachine").click(toaShjfglEquipmentWrong.toaShjfglEquipmentWrongList);
	$("#queryReset").click(toaShjfglEquipmentWrong.queryReset);
	$("#deleteToaShjfglEquipmentWrongs").click("click", function(){toaShjfglEquipmentWrong.deleteToaShjfglEquipmentWrong(0);});
	//初始化列表方法
	toaShjfglEquipmentWrong.toaShjfglEquipmentWrongList();
});