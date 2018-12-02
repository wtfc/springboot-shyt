var toaNetworkShift = {};

//分页处理 start
//分页对象
toaNetworkShift.oTable = null;

toaNetworkShift.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"executor"},	{ "mData":"connectPeople"},	{ "mData":"shiftDate"},	{ "mData":"finishDate"},	{ "mData":"companyName"},	{ "mData":"phone"},	{ mData: function(source) {
		if(source.extStr1==1){
			return "交接完成";
		}else{
			return "待交接确认";
		}
	}},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaNetworkShift.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkShift.oTable, aoData);
	
	var executor = $("#toaNetworkShiftQueryForm #executor").val();
	if(executor.length > 0){
		aoData.push({ "name": "executor", "value":executor});
	}
	
	var connectPeople = $("#toaNetworkShiftQueryForm #connectPeople").val();
	if(connectPeople.length > 0){
		aoData.push({ "name": "connectPeople", "value":connectPeople});
	}
	
};

//重置按钮功能
toaNetworkShift.queryReset = function(){
	$('#toaNetworkShiftQueryForm')[0].reset();
};


//分页查询
toaNetworkShift.toaNetworkShiftList = function () {
	if (toaNetworkShift.oTable == null) {
		toaNetworkShift.oTable = $('#toaNetworkShiftTable').dataTable( {
			"iDisplayLength": toaNetworkShift.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkShift/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkShift.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkShift.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8]}
	        ]
		} );
	} else {
		toaNetworkShift.oTable.fnDraw();
	}
};

toaNetworkShift.deleteToaNetworkShift = function (id) {
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
			toaNetworkShift.deleteTimeSet(ids);
		}
	});
};


toaNetworkShift.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkShift/deleteByIds.action",
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
			toaNetworkShift.toaNetworkShiftList();
		}
	});
};
//交接确认
toaNetworkShift.stateToaNetworkShift = function (id) {
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
		content: $.i18n.prop("JC_SYS_145"),
		success: function(){
			toaNetworkShift.stateTimeSet(ids);
		}
	});
};


toaNetworkShift.stateTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/network/toaNetworkShift/state.action",
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
			toaNetworkShift.toaNetworkShiftList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkShift.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkShift.toaNetworkShiftList);
	$("#queryReset").click(toaNetworkShift.queryReset);
	//初始化列表方法
	toaNetworkShift.toaNetworkShiftList();
});