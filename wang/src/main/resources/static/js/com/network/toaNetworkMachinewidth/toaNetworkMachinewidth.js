var toaNetworkMachinewidth = {};

//分页处理 start
//分页对象
toaNetworkMachinewidth.oTable = null;

toaNetworkMachinewidth.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"gbps"},	{ "mData":"threeZhaowei"},	{ "mData":"threeYangqiao"},	{ "mData":"threeKandan"},	{ "mData":"threeQinghuayuan"},	{ "mData":"threeLugu"},	{ "mData":"threeFufengqiao"},	{ "mData":"threeShahe"},	{ "mData":"twoZhaowei"},	{ "mData":"twoYangqiao"},	{ "mData":"twoKandan"},	{ "mData":"twoQinghuayuan"},	{ "mData":"twoLugu"},	{ "mData":"twoFufengqiao"},	{ "mData":"twoShahe"},	{ "mData":"twoChuanshu"},	{ "mData":"wsLangfang"},	{ "mData":"wsShandong"},	{ "mData":"remark"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkMachinewidth.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkMachinewidth.oTable, aoData);
	
	var companyName = $("#toaNetworkMachinewidthQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var gbps = $("#toaNetworkMachinewidthQueryForm #gbps").val();
	if(gbps.length > 0){
		aoData.push({ "name": "gbps", "value":gbps});
	}
	
};

//重置按钮功能
toaNetworkMachinewidth.queryReset = function(){
	$('#toaNetworkMachinewidthQueryForm')[0].reset();
};


//分页查询
toaNetworkMachinewidth.toaNetworkMachinewidthList = function () {
	if (toaNetworkMachinewidth.oTable == null) {
		toaNetworkMachinewidth.oTable = $('#toaNetworkMachinewidthTable').dataTable( {
			"iDisplayLength": toaNetworkMachinewidth.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkMachinewidth/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkMachinewidth.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkMachinewidth.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21]}
	        ]
		} );
	} else {
		toaNetworkMachinewidth.oTable.fnDraw();
	}
};

toaNetworkMachinewidth.deleteToaNetworkMachinewidth = function (id) {
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
			toaNetworkMachinewidth.deleteTimeSet(ids);
		}
	});
};


toaNetworkMachinewidth.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkMachinewidth/deleteByIds.action",
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
			toaNetworkMachinewidth.toaNetworkMachinewidthList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkMachinewidth.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkMachinewidth.toaNetworkMachinewidthList);
	$("#queryReset").click(toaNetworkMachinewidth.queryReset);
	//初始化列表方法
	toaNetworkMachinewidth.toaNetworkMachinewidthList();
});