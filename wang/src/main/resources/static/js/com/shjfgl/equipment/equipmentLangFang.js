var equipmentLangFang = {};

//分页处理 start
//分页对象
equipmentLangFang.oTable = null;

equipmentLangFang.oTableAoColumns = [
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

equipmentLangFang.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentLangFang.oTable, aoData);
	
	var clientName = $("#equipmentLangFangQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentLangFangQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentLangFangQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentLangFangQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentLangFangQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentLangFangQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentLangFangQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentLangFangQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentLangFangQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentLangFang.queryReset = function(){
	$('#equipmentLangFangQueryForm')[0].reset();
};


//分页查询
equipmentLangFang.equipmentLangFangList = function () {
	
	if (equipmentLangFang.oTable == null) {
		equipmentLangFang.oTable = $('#equipmentLangFangTable').dataTable( {
			
			"iDisplayLength": equipmentLangFang.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentLangFang.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentLangFang.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentLangFang.oTable.fnDraw();
	}
};

equipmentLangFang.deleteEquipment = function (id) {
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
			equipmentLangFang.deleteTimeSet(ids);
		}
	});
};


equipmentLangFang.deleteTimeSet = function(ids) {
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
			equipmentLangFang.equipmentLangFangList();
		}
	});
};

//加载添加DIV
equipmentLangFang.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormLangFang.action",null,function(){
			equipmentLangFangModule.show();
		});
	}
	else{
		equipmentLangFangModule.show();
	}
};
		
//加载修改div
equipmentLangFang.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormLangFang.action",null,function(){
			equipmentLangFangModule.get(id);
		});
	}
	else{
		equipmentLangFangModule.get(id);
	}
};

//初始化方法
var equipmentLangFangSee = {};

//获取修改信息
equipmentLangFang.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentLangFangSee.clearForm();
				//$("#equipmentLangFangSeeForm").fill(data);
				$("#equipmentLangFangSeeForm #clientName").html(data.clientName);
				$("#equipmentLangFangSeeForm #type").html(data.type);
				$("#equipmentLangFangSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentLangFangSeeForm #contact").html(data.contact);
				$("#equipmentLangFangSeeForm #machina").html(data.machina);
				$("#equipmentLangFangSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentLangFangSeeForm #address").html(data.address);
				$("#equipmentLangFangSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentLangFangSeeForm #ip").html(data.ip);
				$("#equipmentLangFangSeeForm #uCount").html(data.uCount);
				$("#equipmentLangFangSeeForm #power").html(data.power);
				$("#equipmentLangFangSeeForm #plantPower").html(data.plantPower);
				$("#equipmentLangFangSeeForm #functionn").html(data.functionn);
				$("#equipmentLangFangSeeForm #port").html(data.port);
				$("#equipmentLangFangSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentLangFangSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentLangFangSeeForm #system").html(data.system);
				$("#equipmentLangFangSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentLangFangSeeForm #sn").html(data.sn);
				$("#equipmentLangFangSeeForm #device").html(data.device);
				$("#equipmentLangFangSeeForm #remark").html(data.remark);
				$("#equipmentLangFangSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentLangFangSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentLangFangSeeForm #netmaskTwo").html(data.netmaskTwo);
			/*	$("#equipmentLangFangSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentLangFangSeeForm #extStr1").html(data.extStr1);
				$("#equipmentLangFangSeeForm #extStr2").html(data.extStr2);
				$("#equipmentLangFangSeeForm #extStr3").html(data.extStr3);
				$("#equipmentLangFangSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentLangFangSee.clearForm = function(){
	$('#equipmentLangFangSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentLangFang.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentLangFang.loadModule);
	$("#showAddDiv_t").click(equipmentLangFang.loadModule);
	
	$("#queryMachine").click(equipmentLangFang.equipmentLangFangList);
	$("#queryReset").click(equipmentLangFang.queryReset);
	$("#deleteMachines").click("click", function(){equipmentLangFang.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentLangFang.equipmentLangFangList();
});