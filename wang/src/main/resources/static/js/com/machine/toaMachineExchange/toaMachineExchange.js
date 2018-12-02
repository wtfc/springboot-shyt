var toaMachineExchange = {};

//分页处理 start
//分页对象
toaMachineExchange.oTable = null;

toaMachineExchange.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"restartId"},	{ "mData":"name"},	{ "mData":"startDate"},	{ "mData":"substance"},	{ "mData":"serviceName"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineExchange.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineExchange.oTable, aoData);
	
	//var companyName = $("#toaMachineExchange#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaMachineExchange.queryReset = function(){
	$('#toaMachineExchangeQueryForm')[0].reset();
};


//分页查询
toaMachineExchange.toaMachineExchangeList = function () {
	if (toaMachineExchange.oTable == null) {
		toaMachineExchange.oTable = $('#toaMachineExchangeTable').dataTable( {
			"iDisplayLength": toaMachineExchange.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineExchange/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineExchange.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineExchange.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaMachineExchange.oTable.fnDraw();
	}
};

toaMachineExchange.deleteToaMachineExchange = function (id) {
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
			toaMachineExchange.deleteTimeSet(ids);
		}
	});
};


toaMachineExchange.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineExchange/deleteByIds.action",
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
			toaMachineExchange.toaMachineExchangeList();
		}
	});
};

//加载添加DIV
toaMachineExchange.loadModule = function (){
	if($.trim($("#toaMachineExchangeModuleDiv").html()).length == 0){
		$("#toaMachineExchangeModuleDiv").load(getRootPath()+"/machine/toaMachineExchange/loadForm.action",null,function(){
			toaMachineExchangeModule.show();
		});
	}
	else{
		toaMachineExchangeModule.show();
	}
};
		
//加载修改div
toaMachineExchange.loadModuleForUpdate = function (id){
	if($.trim($("#toaMachineExchangeModuleDiv").html()).length == 0){
		$("#toaMachineExchangeModuleDiv").load(getRootPath()+"/machine/toaMachineExchange/loadForm.action",null,function(){
			toaMachineExchangeModule.get(id);
		});
	}
	else{
		toaMachineExchangeModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineExchange.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaMachineExchange.loadModule);
	$("#showAddDiv_t").click(toaMachineExchange.loadModule);
	$("#queryMachine").click(toaMachineExchange.toaMachineExchangeList);
	$("#queryReset").click(toaMachineExchange.queryReset);
	$("#deleteToaMachineExchanges").click("click", function(){toaMachineExchange.deleteToaMachineExchange(0);});
	//初始化列表方法
	toaMachineExchange.toaMachineExchangeList();
});