var toaSysWorkOvertime = {};

//分页处理 start
//分页对象
toaSysWorkOvertime.oTable = null;

toaSysWorkOvertime.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"name"},	{ "mData":"startTime"},	{ "mData":"endTime"},	{ "mData":"workHour"},	{ "mData":"workContent"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaSysWorkOvertime.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaSysWorkOvertime.oTable, aoData);
	
	var name = $("#toaSysWorkOvertimeQueryForm #name").val();
	if(name.length > 0){
		aoData.push({ "name": "name", "value":name});
	}
	
};

//重置按钮功能
toaSysWorkOvertime.queryReset = function(){
	$('#toaSysWorkOvertimeQueryForm')[0].reset();
};


//分页查询
toaSysWorkOvertime.toaSysWorkOvertimeList = function () {
	if (toaSysWorkOvertime.oTable == null) {
		toaSysWorkOvertime.oTable = $('#toaSysWorkOvertimeTable').dataTable( {
			"iDisplayLength": toaSysWorkOvertime.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaSysWorkOvertime/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaSysWorkOvertime.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaSysWorkOvertime.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7]}
	        ]
		} );
	} else {
		toaSysWorkOvertime.oTable.fnDraw();
	}
};

toaSysWorkOvertime.deleteToaSysWorkOvertime = function (id) {
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
			toaSysWorkOvertime.deleteTimeSet(ids);
		}
	});
};


toaSysWorkOvertime.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaSysWorkOvertime/deleteByIds.action",
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
			toaSysWorkOvertime.toaSysWorkOvertimeList();
		}
	});
};

//加载添加DIV
toaSysWorkOvertime.loadModule = function (){
	if($.trim($("#toaSysWorkOvertimeModuleDiv").html()).length == 0){
		$("#toaSysWorkOvertimeModuleDiv").load(getRootPath()+"/machine/toaSysWorkOvertime/loadForm.action",null,function(){
			toaSysWorkOvertimeModule.show();
		});
	}
	else{
		toaSysWorkOvertimeModule.show();
	}
};
		
//加载修改div
toaSysWorkOvertime.loadModuleForUpdate = function (id){
	if($.trim($("#toaSysWorkOvertimeModuleDiv").html()).length == 0){
		$("#toaSysWorkOvertimeModuleDiv").load(getRootPath()+"/machine/toaSysWorkOvertime/loadForm.action",null,function(){
			toaSysWorkOvertimeModule.get(id);
		});
	}
	else{
		toaSysWorkOvertimeModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaSysWorkOvertime.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaSysWorkOvertime.loadModule);
	$("#showAddDiv_t").click(toaSysWorkOvertime.loadModule);
	$("#queryMachine").click(toaSysWorkOvertime.toaSysWorkOvertimeList);
	$("#queryReset").click(toaSysWorkOvertime.queryReset);
	$("#deleteToaSysWorkOvertimes").click("click", function(){toaSysWorkOvertime.deleteToaSysWorkOvertime(0);});
	//初始化列表方法
	toaSysWorkOvertime.toaSysWorkOvertimeList();
});