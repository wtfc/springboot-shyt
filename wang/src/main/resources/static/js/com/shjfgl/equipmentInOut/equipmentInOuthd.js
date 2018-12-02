var equipmentInOut = {};

//分页处理 start
//分页对象
equipmentInOut.oTable = null;

equipmentInOut.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "companyName"},
	{ "mData": "machina"},
	{ "mData": "origin"},
	{ "mData": "type" },
	{ "mData": "intDate"},
	{ "mData": "noter"},
	{  mData: function(source){
		if(source.status==0){
			return "待处理";
		}else if(source.status==1){
			return "已接单";
		}else if(source.status==2){
			return "操作完成";
		}else if(source.status==3){
			return "工单作废";
		}else if(source.status==4){
			return "工单完结";
		}else if(source.status==5){
			return "操作中";
		}else if(source.status==6){
			return "主管审核";
		}
		}},
//	{ "mData": "isInput"},
//	{ "mData": "countt"},
//	{ "mData": "implemtation"},
//	{ "mData": "implemtationName"},
//	{ "mData": "workLeader"},
//	{ "mData": "search"},
//	{ "mData": "rebackName"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

equipmentInOut.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentInOut.oTable, aoData);
	
	var companyName = $("#equipmentInOutQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machina = $("#equipmentInOutQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value": machina});
	}
	var origin = $("#equipmentInOutQueryForm #origin").val();
	if(origin.length > 0){
		aoData.push({ "name": "origin", "value": origin});
	}
	var type = $("#equipmentInOutQueryForm #type").val();
	if(type.length > 0){
		aoData.push({ "name": "type", "value": type});
	}
	var status = $("#equipmentInOutQueryForm #status").val();
	if(status.length > 0){
		aoData.push({ "name": "status", "value": status});
	}
	var intDate = $("#equipmentInOutQueryForm #intDate").val();
	if(intDate.length > 0){
		aoData.push({ "name": "intDate", "value": intDate});
	}
	var extStr1 = $("#equipmentInOutQueryForm #extStr1").val();
	if(extStr1.length > 0){
		aoData.push({ "name": "extStr1", "value": extStr1});
	}
};

//重置按钮功能
equipmentInOut.queryReset = function(){
	$('#equipmentInOutQueryForm')[0].reset();
};


//分页查询
equipmentInOut.equipmentInOutList = function () {
	
	if (equipmentInOut.oTable == null) {
		equipmentInOut.oTable = $('#equipmentInOutTable').dataTable( {
			
			"iDisplayLength": equipmentInOut.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipmentInOut/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentInOut.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentInOut.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentInOut.oTable.fnDraw();
	}
};

//工单作废
equipmentInOut.deleteEquipmentInOut = function (id) {
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
			content: $.i18n.prop("JC_SYS_134")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_128"),
		success: function(){
			equipmentInOut.deleteTimeSet(ids);
		}
	});
};


equipmentInOut.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/equipmentInOut/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				equipmentInOut.equipmentInOutList();
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

		


$(document).ready(function(){
	
	//计算分页显示条数
	equipmentInOut.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#queryReset").click(equipmentInOut.queryReset);
	//初始化列表方法
	equipmentInOut.equipmentInOutList();
});