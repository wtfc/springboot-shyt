var equipmentShaHe = {};

//分页处理 start
//分页对象
equipmentShaHe.oTable = null;

equipmentShaHe.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "clientName"},
	{ "mData": "type" },
	//{ "mData": "contact"},
	{ "mData": "address"},
	/*{ "mData": "machinaNumber"},*/
	{ "mData": "sn"},
	{ "mData": "onlineDate" },
	{ "mData": "netmaskOne" },
	{ "mData": "ip" },
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

equipmentShaHe.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentShaHe.oTable, aoData);
	
	var clientName = $("#equipmentShaHeQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentShaHeQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentShaHeQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentShaHeQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentShaHeQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentShaHeQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentShaHeQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentShaHeQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentShaHeQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentShaHe.queryReset = function(){
	$('#equipmentShaHeQueryForm')[0].reset();
};


//分页查询
equipmentShaHe.equipmentShaHeList = function () {
	
	if (equipmentShaHe.oTable == null) {
		equipmentShaHe.oTable = $('#equipmentShaHeTable').dataTable( {
			
			"iDisplayLength": equipmentShaHe.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentShaHe.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentShaHe.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentShaHe.oTable.fnDraw();
	}
};

equipmentShaHe.deleteEquipment = function (id) {
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
			equipmentShaHe.deleteTimeSet(ids);
		}
	});
};


equipmentShaHe.deleteTimeSet = function(ids) {
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
			equipmentShaHe.equipmentShaHeList();
		}
	});
};

//加载添加DIV
equipmentShaHe.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormShaHe.action",null,function(){
			equipmentShaHeModule.show();
		});
	}
	else{
		equipmentShaHeModule.show();
	}
};
		
//加载修改div
equipmentShaHe.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormShaHe.action",null,function(){
			equipmentShaHeModule.get(id);
		});
	}
	else{
		equipmentShaHeModule.get(id);
	}
};

//初始化方法
var equipmentShaHeSee = {};

//获取修改信息
equipmentShaHe.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentShaHeSee.clearForm();
				//$("#equipmentShaHeSeeForm").fill(data);
				$("#equipmentShaHeSeeForm #clientName").html(data.clientName);
				$("#equipmentShaHeSeeForm #type").html(data.type);
				$("#equipmentShaHeSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentShaHeSeeForm #contact").html(data.contact);
				$("#equipmentShaHeSeeForm #machina").html(data.machina);
				$("#equipmentShaHeSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentShaHeSeeForm #address").html(data.address);
				$("#equipmentShaHeSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentShaHeSeeForm #ip").html(data.ip);
				$("#equipmentShaHeSeeForm #uCount").html(data.uCount);
				$("#equipmentShaHeSeeForm #power").html(data.power);
				$("#equipmentShaHeSeeForm #plantPower").html(data.plantPower);
				$("#equipmentShaHeSeeForm #functionn").html(data.functionn);
				$("#equipmentShaHeSeeForm #port").html(data.port);
				$("#equipmentShaHeSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentShaHeSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentShaHeSeeForm #system").html(data.system);
				$("#equipmentShaHeSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentShaHeSeeForm #sn").html(data.sn);
				$("#equipmentShaHeSeeForm #device").html(data.device);
				$("#equipmentShaHeSeeForm #remark").html(data.remark);
				$("#equipmentShaHeSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentShaHeSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentShaHeSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentShaHeSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentShaHeSeeForm #extStr1").html(data.extStr1);
				$("#equipmentShaHeSeeForm #extStr2").html(data.extStr2);
				$("#equipmentShaHeSeeForm #extStr3").html(data.extStr3);
				$("#equipmentShaHeSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentShaHeSee.clearForm = function(){
	$('#equipmentShaHeSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentShaHe.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentShaHe.loadModule);
	$("#showAddDiv_t").click(equipmentShaHe.loadModule);
	
	$("#queryMachine").click(equipmentShaHe.equipmentShaHeList);
	$("#queryReset").click(equipmentShaHe.queryReset);
	$("#deleteMachines").click("click", function(){equipmentShaHe.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentShaHe.equipmentShaHeList();
});