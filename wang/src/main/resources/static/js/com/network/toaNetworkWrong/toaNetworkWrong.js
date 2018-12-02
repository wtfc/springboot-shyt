var toaNetworkWrong = {};

//分页处理 start
//分页对象
toaNetworkWrong.oTable = null;

toaNetworkWrong.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"equipmentNumber"},	{ "mData":"companyId"},	{ "mData":"companyName"},	{ "mData":"contact"},	{ "mData":"tel"},	{ "mData":"startDate"},	{ "mData":"endDate"},	{ "mData":"wrongType"},	{ "mData":"srcAddress"},	{ "mData":"isSrcForbidPing"},	{ "mData":"destAddress"},	{ "mData":"isDestForbidPing"},	{ "mData":"compareSrcAddress"},	{ "mData":"compareDestAddress"},	{ "mData":"belongCompanyName"},	{ "mData":"lostPercent"},	{ "mData":"lastJumpOut"},	{ "mData":"lastJumpIn"},	{ "mData":"excepIp"},	{ "mData":"excepDirection"},	{ "mData":"isRankAnalyse"},	{ "mData":"analyseStartDate"},	{ "mData":"analyseEndDate"},	{ "mData":"ipRange"},	{ "mData":"interface"},	{ "mData":"badIp"},	{ "mData":"isBackup"},	{ "mData":"problemStartTime"},	{ "mData":"realExcepType"},	{ "mData":"solveResult"},	{ "mData":"operationType"},	{ "mData":"operationTypeImg"},	{ "mData":"machina"},	{ "mData":"caozgcs"},	{ "mData":"caozgcsName"},	{ "mData":"status"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkWrong.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkWrong.oTable, aoData);
	
	//var companyName = $("#toaNetworkWrongQueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaNetworkWrong.queryReset = function(){
	$('#toaNetworkWrongQueryForm')[0].reset();
};


//分页查询
toaNetworkWrong.toaNetworkWrongList = function () {
	if (toaNetworkWrong.oTable == null) {
		toaNetworkWrong.oTable = $('#toaNetworkWrongTable').dataTable( {
			"iDisplayLength": toaNetworkWrong.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaNetworkWrong/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkWrong.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkWrong.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaNetworkWrong.oTable.fnDraw();
	}
};

toaNetworkWrong.deleteToaNetworkWrong = function (id) {
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
			toaNetworkWrong.deleteTimeSet(ids);
		}
	});
};


toaNetworkWrong.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaNetworkWrong/deleteByIds.action",
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
			toaNetworkWrong.toaNetworkWrongList();
		}
	});
};

//加载添加DIV
toaNetworkWrong.loadModule = function (){
	if($.trim($("#toaNetworkWrongModuleDiv").html()).length == 0){
		$("#toaNetworkWrongModuleDiv").load(getRootPath()+"/machine/toaNetworkWrong/loadForm.action",null,function(){
			toaNetworkWrongModule.show();
		});
	}
	else{
		toaNetworkWrongModule.show();
	}
};
		
//加载修改div
toaNetworkWrong.loadModuleForUpdate = function (id){
	if($.trim($("#toaNetworkWrongModuleDiv").html()).length == 0){
		$("#toaNetworkWrongModuleDiv").load(getRootPath()+"/machine/toaNetworkWrong/loadForm.action",null,function(){
			toaNetworkWrongModule.get(id);
		});
	}
	else{
		toaNetworkWrongModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkWrong.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaNetworkWrong.loadModule);
	$("#showAddDiv_t").click(toaNetworkWrong.loadModule);
	$("#queryMachine").click(toaNetworkWrong.toaNetworkWrongList);
	$("#queryReset").click(toaNetworkWrong.queryReset);
	$("#deleteToaNetworkWrongs").click("click", function(){toaNetworkWrong.deleteToaNetworkWrong(0);});
	//初始化列表方法
	toaNetworkWrong.toaNetworkWrongList();
});