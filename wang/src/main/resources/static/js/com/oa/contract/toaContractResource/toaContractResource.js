var toaContractResource = {};

//分页处理 start
//分页对象
toaContractResource.oTable = null;

toaContractResource.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"customerId"},	{ "mData":"minBandwidth"},	{ "mData":"minBandwidthPrice"},	{ "mData":"portBandwidth"},	{ "mData":"portBandwidthPrice"},	{ "mData":"overflowBandwidth"},	{ "mData":"overflowBandwidthPrice"},	{ "mData":"cabinetNum"},	{ "mData":"cabinetPrice"},	{ "mData":"serviceNum"},	{ "mData":"servicePrice"},	{ "mData":"ipNum"},	{ "mData":"ipPrice"},	{ "mData":"switchNum"},	{ "mData":"switchPrice"},	{ "mData":"odfNum"},	{ "mData":"odfPrice"},	{ "mData":"portNum"},	{ "mData":"portPrice"},	{ "mData":"memoryNum"},	{ "mData":"memoryPrice"},	{ "mData":"cpuNum"},	{ "mData":"cpuPrice"},	{ "mData":"diskNum"},	{ "mData":"diskPrice"},	{ "mData":"router"},	{ "mData":"routerPrice"},	{ "mData":"otherss"},	{ "mData":"otherssPrice"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaContractResource.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaContractResource.oTable, aoData);
	
	//var companyName = $("#toaContractResource#ueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaContractResource.queryReset = function(){
	$('#toaContractResourceQueryForm')[0].reset();
};


//分页查询
toaContractResource.toaContractResourceList = function () {
	if (toaContractResource.oTable == null) {
		toaContractResource.oTable = $('#toaContractResourceTable').dataTable( {
			"iDisplayLength": toaContractResource.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/contract/toaContractResource/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaContractResource.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaContractResource.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaContractResource.oTable.fnDraw();
	}
};

toaContractResource.deleteToaContractResource = function (id) {
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
			toaContractResource.deleteTimeSet(ids);
		}
	});
};


toaContractResource.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/contract/toaContractResource/deleteByIds.action",
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
			toaContractResource.toaContractResourceList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaContractResource.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaContractResource.toaContractResourceList);
	$("#queryReset").click(toaContractResource.queryReset);
	//初始化列表方法
	toaContractResource.toaContractResourceList();
});