var toaShytAsset = {};

//分页处理 start
//分页对象
toaShytAsset.oTable = null;

toaShytAsset.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"assetsName"},	{mData: function(source) {
		if(source.type==0){
			return "办公家具";
		}else{
			return "办公设备";
		};
	}},	{ "mData":"bard"},	{ "mData":"assetsNum"},	{ "mData":"machineNum"},	{ "mData":"unit"},	{ "mData":"number"},	{ "mData":"price"},	{ "mData":"inDate"},	{ "mData":"useDept"},	{ "mData":"address"},	{ "mData":"tiaoboneirong"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaShytAsset.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaShytAsset.oTable, aoData);
	
	var assetsName = $("#toaShytAssetQueryForm #assetsName").val();
	if(assetsName.length > 0){
		aoData.push({ "name": "assetsName", "value":assetsName});
	}
	var type = $("#toaShytAssetQueryForm #type").val();
	if(type.length > 0){
		aoData.push({ "name": "type", "value":type});
	}
//	var assetsName = $("#toaShytAssetQueryForm #assetsName").val();
//	if(assetsName.length > 0){
//		aoData.push({ "name": "assetsName", "value":assetsName});
//	}
//	var assetsName = $("#toaShytAssetQueryForm #assetsName").val();
//	if(assetsName.length > 0){
//		aoData.push({ "name": "assetsName", "value":assetsName});
//	}
	
};

//重置按钮功能
toaShytAsset.queryReset = function(){
	$('#toaShytAssetQueryForm')[0].reset();
};


//分页查询
toaShytAsset.toaShytAssetList = function () {
	if (toaShytAsset.oTable == null) {
		toaShytAsset.oTable = $('#toaShytAssetTable').dataTable( {
			"iDisplayLength": toaShytAsset.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaShytAsset/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaShytAsset.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaShytAsset.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13]}
	        ]
		} );
	} else {
		toaShytAsset.oTable.fnDraw();
	}
};

toaShytAsset.deleteToaShytAsset = function (id) {
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
			toaShytAsset.deleteTimeSet(ids);
		}
	});
};


toaShytAsset.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaShytAsset/deleteByIds.action",
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
			toaShytAsset.toaShytAssetList();
		}
	});
};

//加载添加DIV
toaShytAsset.loadModule = function (){
	if($.trim($("#toaShytAssetModuleDiv").html()).length == 0){
		$("#toaShytAssetModuleDiv").load(getRootPath()+"/machine/toaShytAsset/loadForm.action",null,function(){
			toaShytAssetModule.show();
		});
	}
	else{
		toaShytAssetModule.show();
	}
};
		
//加载修改div
toaShytAsset.loadModuleForUpdate = function (id){
	if($.trim($("#toaShytAssetModuleDiv").html()).length == 0){
		$("#toaShytAssetModuleDiv").load(getRootPath()+"/machine/toaShytAsset/loadForm.action",null,function(){
			toaShytAssetModule.get(id);
		});
	}
	else{
		toaShytAssetModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaShytAsset.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaShytAsset.loadModule);
	$("#showAddDiv_t").click(toaShytAsset.loadModule);
	$("#queryMachine").click(toaShytAsset.toaShytAssetList);
	$("#queryReset").click(toaShytAsset.queryReset);
	$("#deleteToaShytAssets").click("click", function(){toaShytAsset.deleteToaShytAsset(0);});
	//初始化列表方法
	toaShytAsset.toaShytAssetList();
});