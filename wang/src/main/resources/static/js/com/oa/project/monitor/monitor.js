var monitor = {};

//分页处理 start
//分页对象
monitor.oTable = null;

monitor.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "name"},
	{ "mData": "extStr1"},
	{ "mData": "startDate"},
	{ "mData": "endDate"},
	{ "mData": "leared"},
	{ "mData": "people"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

monitor.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(monitor.oTable, aoData);
	
	var status = $("#monitorQueryForm #status").val();
	if(status.length > 0){
		aoData.push({ "name": "status", "value":status});
	}
	var name = $("#monitorQueryForm #name").val();
	if(name.length > 0){
		aoData.push({ "name": "name", "value": name});
	}
	var extStr1 = $("#monitorQueryForm #extStr1").val();
	if(extStr1.length > 0){
		aoData.push({ "name": "extStr1", "value": extStr1});
	}
	var extStr2 = $("#monitorQueryForm #extStr2").val();
	if(extStr2.length > 0){
		aoData.push({ "name": "extStr2", "value": extStr2});
	}
	var leared = $("#monitorQueryForm #leared").val();
	if(leared.length > 0){
		aoData.push({ "name": "leared", "value": leared});
	}
};

//重置按钮功能
monitor.queryReset = function(){
	$('#monitorQueryForm')[0].reset();
};

//分页查询
monitor.monitorList = function () {
	if (monitor.oTable == null) {
		monitor.oTable = $('#monitorTable').dataTable( {
			"iDisplayLength": monitor.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/project/monitor/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": monitor.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				monitor.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		monitor.oTable.fnDraw();
	}
};

monitor.deletemonitor = function (id) {
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
			monitor.deleteTimeSet(ids);
		}
	});
};


monitor.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/project/monitor/deleteByIds.action",
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
			monitor.monitorList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	monitor.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(monitor.monitorList);
	$("#queryReset").click(monitor.queryReset);
	//初始化列表方法
	monitor.monitorList();
});