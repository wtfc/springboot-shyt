var toaShjfglEquipmentWrongContent = {};

//分页处理 start
//分页对象
toaShjfglEquipmentWrongContent.oTable = null;

toaShjfglEquipmentWrongContent.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"wrongId"},	{ "mData":"operateAndResult"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaShjfglEquipmentWrongContent.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaShjfglEquipmentWrongContent.oTable, aoData);
	
	//var companyName = $("#toaShjfglEquipmentWrongContentQueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaShjfglEquipmentWrongContent.queryReset = function(){
	$('#toaShjfglEquipmentWrongContentQueryForm')[0].reset();
};


//分页查询
toaShjfglEquipmentWrongContent.toaShjfglEquipmentWrongContentList = function () {
	if (toaShjfglEquipmentWrongContent.oTable == null) {
		toaShjfglEquipmentWrongContent.oTable = $('#toaShjfglEquipmentWrongContentTable').dataTable( {
			"iDisplayLength": toaShjfglEquipmentWrongContent.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaShjfglEquipmentWrongContent/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaShjfglEquipmentWrongContent.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaShjfglEquipmentWrongContent.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaShjfglEquipmentWrongContent.oTable.fnDraw();
	}
};

toaShjfglEquipmentWrongContent.deleteToaShjfglEquipmentWrongContent = function (id) {
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
			toaShjfglEquipmentWrongContent.deleteTimeSet(ids);
		}
	});
};


toaShjfglEquipmentWrongContent.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaShjfglEquipmentWrongContent/deleteByIds.action",
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
			toaShjfglEquipmentWrongContent.toaShjfglEquipmentWrongContentList();
		}
	});
};

//加载添加DIV
toaShjfglEquipmentWrongContent.loadModule = function (){
	if($.trim($("#toaShjfglEquipmentWrongContentModuleDiv").html()).length == 0){
		$("#toaShjfglEquipmentWrongContentModuleDiv").load(getRootPath()+"/machine/toaShjfglEquipmentWrongContent/loadForm.action",null,function(){
			toaShjfglEquipmentWrongContentModule.show();
		});
	}
	else{
		toaShjfglEquipmentWrongContentModule.show();
	}
};
		
//加载修改div
toaShjfglEquipmentWrongContent.loadModuleForUpdate = function (id){
	if($.trim($("#toaShjfglEquipmentWrongContentModuleDiv").html()).length == 0){
		$("#toaShjfglEquipmentWrongContentModuleDiv").load(getRootPath()+"/machine/toaShjfglEquipmentWrongContent/loadForm.action",null,function(){
			toaShjfglEquipmentWrongContentModule.get(id);
		});
	}
	else{
		toaShjfglEquipmentWrongContentModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaShjfglEquipmentWrongContent.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaShjfglEquipmentWrongContent.loadModule);
	$("#showAddDiv_t").click(toaShjfglEquipmentWrongContent.loadModule);
	$("#queryMachine").click(toaShjfglEquipmentWrongContent.toaShjfglEquipmentWrongContentList);
	$("#queryReset").click(toaShjfglEquipmentWrongContent.queryReset);
	$("#deleteToaShjfglEquipmentWrongContents").click("click", function(){toaShjfglEquipmentWrongContent.deleteToaShjfglEquipmentWrongContent(0);});
	//初始化列表方法
	toaShjfglEquipmentWrongContent.toaShjfglEquipmentWrongContentList();
});