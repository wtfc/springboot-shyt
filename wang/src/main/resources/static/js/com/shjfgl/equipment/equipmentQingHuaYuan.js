var equipmentQingHuaYuan = {};

//分页处理 start
//分页对象
equipmentQingHuaYuan.oTable = null;

equipmentQingHuaYuan.oTableAoColumns = [
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

equipmentQingHuaYuan.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentQingHuaYuan.oTable, aoData);
	
	var clientName = $("#equipmentQingHuaYuanQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentQingHuaYuanQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentQingHuaYuanQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentQingHuaYuanQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentQingHuaYuanQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentQingHuaYuanQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentQingHuaYuanQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentQingHuaYuanQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentQingHuaYuanQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentQingHuaYuan.queryReset = function(){
	$('#equipmentQingHuaYuanQueryForm')[0].reset();
};


//分页查询
equipmentQingHuaYuan.equipmentQingHuaYuanList = function () {
	
	if (equipmentQingHuaYuan.oTable == null) {
		equipmentQingHuaYuan.oTable = $('#equipmentQingHuaYuanTable').dataTable( {
			
			"iDisplayLength": equipmentQingHuaYuan.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentQingHuaYuan.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentQingHuaYuan.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentQingHuaYuan.oTable.fnDraw();
	}
};

equipmentQingHuaYuan.deleteEquipment = function (id) {
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
			equipmentQingHuaYuan.deleteTimeSet(ids);
		}
	});
};


equipmentQingHuaYuan.deleteTimeSet = function(ids) {
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
			equipmentQingHuaYuan.equipmentQingHuaYuanList();
		}
	});
};

//加载添加DIV
equipmentQingHuaYuan.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormQingHuaYuan.action",null,function(){
			equipmentQingHuaYuanModule.show();
		});
	}
	else{
		equipmentQingHuaYuanModule.show();
	}
};
		
//加载修改div
equipmentQingHuaYuan.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormQingHuaYuan.action",null,function(){
			equipmentQingHuaYuanModule.get(id);
		});
	}
	else{
		equipmentQingHuaYuanModule.get(id);
	}
};

//初始化方法
var equipmentQingHuaYuanSee = {};

//获取修改信息
equipmentQingHuaYuan.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentQingHuaYuanSee.clearForm();
				//$("#equipmentQingHuaYuanSeeForm").fill(data);
				$("#equipmentQingHuaYuanSeeForm #clientName").html(data.clientName);
				$("#equipmentQingHuaYuanSeeForm #type").html(data.type);
				$("#equipmentQingHuaYuanSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentQingHuaYuanSeeForm #contact").html(data.contact);
				$("#equipmentQingHuaYuanSeeForm #machina").html(data.machina);
				$("#equipmentQingHuaYuanSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentQingHuaYuanSeeForm #address").html(data.address);
				$("#equipmentQingHuaYuanSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentQingHuaYuanSeeForm #ip").html(data.ip);
				$("#equipmentQingHuaYuanSeeForm #uCount").html(data.uCount);
				$("#equipmentQingHuaYuanSeeForm #power").html(data.power);
				$("#equipmentQingHuaYuanSeeForm #plantPower").html(data.plantPower);
				$("#equipmentQingHuaYuanSeeForm #functionn").html(data.functionn);
				$("#equipmentQingHuaYuanSeeForm #port").html(data.port);
				$("#equipmentQingHuaYuanSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentQingHuaYuanSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentQingHuaYuanSeeForm #system").html(data.system);
				$("#equipmentQingHuaYuanSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentQingHuaYuanSeeForm #sn").html(data.sn);
				$("#equipmentQingHuaYuanSeeForm #device").html(data.device);
				$("#equipmentQingHuaYuanSeeForm #remark").html(data.remark);
				$("#equipmentQingHuaYuanSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentQingHuaYuanSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentQingHuaYuanSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentQingHuaYuanSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentQingHuaYuanSeeForm #extStr1").html(data.extStr1);
				$("#equipmentQingHuaYuanSeeForm #extStr2").html(data.extStr2);
				$("#equipmentQingHuaYuanSeeForm #extStr3").html(data.extStr3);
				$("#equipmentQingHuaYuanSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentQingHuaYuanSee.clearForm = function(){
	$('#equipmentQingHuaYuanSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentQingHuaYuan.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentQingHuaYuan.loadModule);
	$("#showAddDiv_t").click(equipmentQingHuaYuan.loadModule);
	
	$("#queryMachine").click(equipmentQingHuaYuan.equipmentQingHuaYuanList);
	$("#queryReset").click(equipmentQingHuaYuan.queryReset);
	$("#deleteMachines").click("click", function(){equipmentQingHuaYuan.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentQingHuaYuan.equipmentQingHuaYuanList();
});