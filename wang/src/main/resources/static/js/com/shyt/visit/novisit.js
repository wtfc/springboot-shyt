var visit = {};

//分页处理 start
//分页对象
visit.oTable = null;

visit.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "companyName"},
	{ "mData": "visitStatus" },
	{ "mData": "visitDate"},
	{ "mData": "visitIsReturn"},
	{ "mData": "visitPay"},
	/*{ "mData": "visit"},
	{ "mData": "remark" },*/
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

visit.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(visit.oTable, aoData);
	
	var companyName = $("#visitQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var visitStatus = $("#visitQueryForm #visitStatus").val();
	if(visitStatus.length > 0){
		aoData.push({ "name": "visitStatus", "value": visitStatus});
	}
	var visitDate = $("#visitQueryForm #visitDate").val();
	if(visitDate.length > 0){
		aoData.push({ "name": "visitDate", "value": visitDate});
	}
	/*var extStr1 = $("#visitQueryForm #extStr1").val();
	if(extStr1.length > 0){
		aoData.push({ "name": "extStr1", "value": extStr1});
	}*/
	var visitIsReturn = $("#visitQueryForm #visitIsReturn").val();
	if(visitIsReturn.length > 0){
		aoData.push({ "name": "visitIsReturn", "value": visitIsReturn});
	}
};

//重置按钮功能
visit.queryReset = function(){
	$('#visitQueryForm')[0].reset();
};


//分页查询
visit.visitList = function () {
	
	if (visit.oTable == null) {
		visit.oTable = $('#visitTable').dataTable( {
			
			"iDisplayLength": visit.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/visit/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": visit.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				visit.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6]}
	        ]
		} );
	} else {
		visit.oTable.fnDraw();
	}
};

visit.deleteVisit = function (id) {
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
			visit.deleteTimeSet(ids);
		}
	});
};


visit.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/shyt/visit/deleteByIds.action",
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
			visit.visitList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	visit.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(visit.visitList);
	$("#queryReset").click(visit.queryReset);
	//初始化列表方法
	visit.visitList();
});