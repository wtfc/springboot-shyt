var complain = {};

//分页处理 start
//分页对象
complain.oTable = null;

complain.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "companyName"},
	{ "mData": "partment" },
	{ "mData": "complainDate"},
	{ "mData": "complainStatus"},
	{ "mData": "status"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

complain.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(complain.oTable, aoData);
	
	var companyName = $("#complainQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var partment = $("#complainQueryForm #partment").val();
	if(partment.length > 0){
		aoData.push({ "name": "partment", "value": partment});
	}
	var status = $("#complainQueryForm #status").val();
	if(status.length > 0){
		aoData.push({ "name": "status", "value": status});
	}

};

//重置按钮功能
complain.queryReset = function(){
	$('#complainQueryForm')[0].reset();
};


//分页查询
complain.complainList = function () {
	
	if (complain.oTable == null) {
		complain.oTable = $('#complainTable').dataTable( {
			
			"iDisplayLength": complain.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/complain/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": complain.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				complain.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		complain.oTable.fnDraw();
	}
};

complain.deleteComplain = function (id) {
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
			complain.deleteTimeSet(ids);
		}
	});
};


complain.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/shyt/complain/deleteByIds.action",
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
			complain.complainList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	complain.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(complain.complainList);
	$("#queryReset").click(complain.queryReset);
	//初始化列表方法
	complain.complainList();
});