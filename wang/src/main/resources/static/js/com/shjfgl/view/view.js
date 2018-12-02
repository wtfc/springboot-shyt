var view = {};

//分页处理 start
//分页对象
view.oTable = null;

view.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "contact"},
	{ "mData": "machina"},
	{ "mData": "machinaNumber"},
	{ "mData": "isOpen"},
	{ "mData": "valume" },
	{ "mData": "num" },
	{ mData: function(source) {
		if(source.type=="#CD0000"){
			return "公司自用";
		}else if(source.type=="#228B22"){
			return "整包机柜";
		}else if(source.type=="#CDCD00"){
			return "散户机柜";
		}else if(source.type=="#1C86EE"){
			return "预留机柜";
		}else if(source.type=="black"){
			return "空机柜";
		}
	}},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

view.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(view.oTable, aoData);
	
	var machinaNumber = $("#viewQueryForm #machinaNumber").val();
	if(machinaNumber.length > 0){
		aoData.push({ "name": "machinaNumber", "value":machinaNumber});
	}
	var machina = $("#viewQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value": machina});
	}
	var contact = $("#viewQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	var type = $("#viewQueryForm #type").val();
	if(type.length > 0){
		aoData.push({ "name": "type", "value": type});
	}
};

//重置按钮功能
view.queryReset = function(){
	$('#viewQueryForm')[0].reset();
};


//分页查询
view.viewList = function () {
	
	if (view.oTable == null) {
		view.oTable = $('#viewTable').dataTable( {
			
			"iDisplayLength": view.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/view/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": view.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				view.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		view.oTable.fnDraw();
	}
};

view.deleteView = function (id) {
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
			view.deleteTimeSet(ids);
		}
	});
};


view.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/view/deleteByIds.action",
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
			view.viewList();
		}
	});
};

//加载添加DIV
view.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/view/loadForm.action",null,function(){
			viewModule.show();
		});
	}
	else{
		viewModule.show();
	}
};
		
//加载修改div
view.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/view/loadForm.action",null,function(){
			viewModule.get(id);
		});
	}
	else{
		viewModule.get(id);
	}
};

$(document).ready(function(){
	
	//计算分页显示条数
	view.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(view.loadModule);
	$("#showAddDiv_t").click(view.loadModule);
	
	$("#queryMachine").click(view.viewList);
	$("#queryReset").click(view.queryReset);
	$("#deleteMachines").click("click", function(){view.deleteview(0);});
	//初始化列表方法
	view.viewList();
});