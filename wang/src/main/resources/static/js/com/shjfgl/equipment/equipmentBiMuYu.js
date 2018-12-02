var equipmentBiMuYu = {};

//分页处理 start
//分页对象
equipmentBiMuYu.oTable = null;

equipmentBiMuYu.oTableAoColumns = [
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

equipmentBiMuYu.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentBiMuYu.oTable, aoData);
	
	var clientName = $("#equipmentBiMuYuQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentBiMuYuQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentBiMuYuQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentBiMuYuQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentBiMuYuQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentBiMuYuQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentBiMuYuQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentBiMuYuQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentBiMuYuQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
	
};

//重置按钮功能
equipmentBiMuYu.queryReset = function(){
	$('#equipmentBiMuYuQueryForm')[0].reset();
};


//分页查询
equipmentBiMuYu.equipmentBiMuYuList = function () {
	
	if (equipmentBiMuYu.oTable == null) {
		equipmentBiMuYu.oTable = $('#equipmentBiMuYuTable').dataTable( {
			
			"iDisplayLength": equipmentBiMuYu.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentBiMuYu.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentBiMuYu.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentBiMuYu.oTable.fnDraw();
	}
};

equipmentBiMuYu.deleteEquipment = function (id) {
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
			equipmentBiMuYu.deleteTimeSet(ids);
		}
	});
};


equipmentBiMuYu.deleteTimeSet = function(ids) {
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
			equipmentBiMuYu.equipmentBiMuYuList();
		}
	});
};

//加载添加DIV
equipmentBiMuYu.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormBiMuYu.action",null,function(){
			equipmentBiMuYuModule.show();
		});
	}
	else{
		equipmentBiMuYuModule.show();
	}
};
		
//加载修改div
equipmentBiMuYu.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormBiMuYu.action",null,function(){
			equipmentBiMuYuModule.get(id);
		});
	}
	else{
		equipmentBiMuYuModule.get(id);
	}
};

//初始化方法
var equipmentBiMuYuSee = {};

//获取修改信息
equipmentBiMuYu.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentBiMuYuSee.clearForm();
				//$("#equipmentBiMuYuSeeForm").fill(data);
				$("#equipmentBiMuYuSeeForm #clientName").html(data.clientName);
				$("#equipmentBiMuYuSeeForm #type").html(data.type);
				$("#equipmentBiMuYuSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentBiMuYuSeeForm #contact").html(data.contact);
				$("#equipmentBiMuYuSeeForm #machina").html(data.machina);
				$("#equipmentBiMuYuSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentBiMuYuSeeForm #address").html(data.address);
				$("#equipmentBiMuYuSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentBiMuYuSeeForm #ip").html(data.ip);
				$("#equipmentBiMuYuSeeForm #uCount").html(data.uCount);
				$("#equipmentBiMuYuSeeForm #power").html(data.power);
				$("#equipmentBiMuYuSeeForm #plantPower").html(data.plantPower);
				$("#equipmentBiMuYuSeeForm #functionn").html(data.functionn);
				$("#equipmentBiMuYuSeeForm #port").html(data.port);
				$("#equipmentBiMuYuSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentBiMuYuSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentBiMuYuSeeForm #system").html(data.system);
				$("#equipmentBiMuYuSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentBiMuYuSeeForm #sn").html(data.sn);
				$("#equipmentBiMuYuSeeForm #device").html(data.device);
				$("#equipmentBiMuYuSeeForm #remark").html(data.remark);
				$("#equipmentBiMuYuSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentBiMuYuSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentBiMuYuSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentBiMuYuSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentBiMuYuSeeForm #extStr1").html(data.extStr1);
				$("#equipmentBiMuYuSeeForm #extStr2").html(data.extStr2);
				$("#equipmentBiMuYuSeeForm #extStr3").html(data.extStr3);
				$("#equipmentBiMuYuSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentBiMuYuSee.clearForm = function(){
	$('#equipmentBiMuYuSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentBiMuYu.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentBiMuYu.loadModule);
	$("#showAddDiv_t").click(equipmentBiMuYu.loadModule);
	
	$("#queryMachine").click(equipmentBiMuYu.equipmentBiMuYuList);
	$("#queryReset").click(equipmentBiMuYu.queryReset);
	$("#deleteMachines").click("click", function(){equipmentBiMuYu.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentBiMuYu.equipmentBiMuYuList();
});