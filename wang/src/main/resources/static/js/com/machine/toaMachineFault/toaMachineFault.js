var toaMachineFault = {};

//分页处理 start
//分页对象
toaMachineFault.oTable = null;

toaMachineFault.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"faultNumber"},	{ "mData":"companyName"},	{ "mData":"intDate"},	{ "mData":"type"},	{ "mData":"operateDate"},	{ "mData":"restoreDate"},	{ "mData":"faultReason"},	{ "mData":"eazyName"},	{ "mData":"department"},	{ "mData":"network"},	{ "mData":"yunwei"},	{ "mData":"jiankong"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineFault.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineFault.oTable, aoData);
	
	//var companyName = $("#toaMachineFaultQueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	var companyName = $("#toaMachineFaultQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var type = $("#toaMachineFaultQueryForm #type").val();
	if(type.length > 0){
		aoData.push({ "name": "type", "value":type});
	}
	
};

//重置按钮功能
toaMachineFault.queryReset = function(){
	$('#toaMachineFaultQueryForm')[0].reset();
};


//分页查询
toaMachineFault.toaMachineFaultList = function () {
	if (toaMachineFault.oTable == null) {
		toaMachineFault.oTable = $('#toaMachineFaultTable').dataTable( {
			"iDisplayLength": toaMachineFault.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineFault/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineFault.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineFault.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13]}
	        ]
		} );
	} else {
		toaMachineFault.oTable.fnDraw();
	}
};

toaMachineFault.deleteToaMachineFault = function (id) {
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
			toaMachineFault.deleteTimeSet(ids);
		}
	});
};


toaMachineFault.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineFault/deleteByIds.action",
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
			toaMachineFault.toaMachineFaultList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineFault.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineFault.toaMachineFaultList);
	$("#queryReset").click(toaMachineFault.queryReset);
	//初始化列表方法
	toaMachineFault.toaMachineFaultList();
});