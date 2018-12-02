var equipment = {};

//分页处理 start
//分页对象
equipment.oTable = null;

equipment.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "clientName"},
	{ "mData": "type" },
	{ "mData": "contact"},
	{ "mData": "address"},
	/*{ "mData": "machinaNumber"},*/
	{ "mData": "sn"},
	{ "mData": "onlineDate" },
	{ "mData": "ip" },
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

equipment.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipment.oTable, aoData);
	
	var clientName = $("#equipmentQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
};

//重置按钮功能
equipment.queryReset = function(){
	$('#equipmentQueryForm')[0].reset();
};


//分页查询
equipment.equipmentList = function () {
	
	if (equipment.oTable == null) {
		equipment.oTable = $('#equipmentTable').dataTable( {
			
			"iDisplayLength": equipment.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipment.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipment.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipment.oTable.fnDraw();
	}
};

equipment.deleteEquipment = function (id) {
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
			equipment.deleteTimeSet(ids);
		}
	});
};


equipment.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/equipment/deleteByIds.action",
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
			equipment.equipmentList();
		}
	});
};

//加载添加DIV
equipment.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadForm.action",null,function(){
			equipmentModule.show();
		});
	}
	else{
		equipmentModule.show();
	}
};
		
//加载修改div
equipment.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadForm.action",null,function(){
			equipmentModule.get(id);
		});
	}
	else{
		equipmentModule.get(id);
	}
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipment.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipment.loadModule);
	$("#showAddDiv_t").click(equipment.loadModule);
	
	$("#queryMachine").click(equipment.equipmentList);
	$("#queryReset").click(equipment.queryReset);
	$("#deleteMachines").click("click", function(){equipment.deleteEquipment(0);});
	//初始化列表方法
	equipment.equipmentList();
});