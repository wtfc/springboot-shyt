var equipmentKanDan = {};

//分页处理 start
//分页对象
equipmentKanDan.oTable = null;

equipmentKanDan.oTableAoColumns = [
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

equipmentKanDan.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentKanDan.oTable, aoData);
	
	var clientName = $("#equipmentKanDanQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentKanDanQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentKanDanQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentKanDanQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentKanDanQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentKanDanQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentKanDanQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentKanDanQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentKanDanQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentKanDan.queryReset = function(){
	$('#equipmentKanDanQueryForm')[0].reset();
};


//分页查询
equipmentKanDan.equipmentKanDanList = function () {
	
	if (equipmentKanDan.oTable == null) {
		equipmentKanDan.oTable = $('#equipmentKanDanTable').dataTable( {
			
			"iDisplayLength": equipmentKanDan.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentKanDan.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentKanDan.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentKanDan.oTable.fnDraw();
	}
};

equipmentKanDan.deleteEquipment = function (id) {
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
			equipmentKanDan.deleteTimeSet(ids);
		}
	});
};


equipmentKanDan.deleteTimeSet = function(ids) {
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
			equipmentKanDan.equipmentKanDanList();
		}
	});
};

//加载添加DIV
equipmentKanDan.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormKanDan.action",null,function(){
			equipmentKanDanModule.show();
		});
	}
	else{
		equipmentKanDanModule.show();
	}
};
		
//加载修改div
equipmentKanDan.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormKanDan.action",null,function(){
			equipmentKanDanModule.get(id);
		});
	}
	else{
		equipmentKanDanModule.get(id);
	}
};

//初始化方法
var equipmentKanDanSee = {};

//获取修改信息
equipmentKanDan.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentKanDanSee.clearForm();
				//$("#equipmentKanDanSeeForm").fill(data);
				$("#equipmentKanDanSeeForm #clientName").html(data.clientName);
				$("#equipmentKanDanSeeForm #type").html(data.type);
				$("#equipmentKanDanSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentKanDanSeeForm #contact").html(data.contact);
				$("#equipmentKanDanSeeForm #machina").html(data.machina);
				$("#equipmentKanDanSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentKanDanSeeForm #address").html(data.address);
				$("#equipmentKanDanSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentKanDanSeeForm #ip").html(data.ip);
				$("#equipmentKanDanSeeForm #uCount").html(data.uCount);
				$("#equipmentKanDanSeeForm #power").html(data.power);
				$("#equipmentKanDanSeeForm #plantPower").html(data.plantPower);
				$("#equipmentKanDanSeeForm #functionn").html(data.functionn);
				$("#equipmentKanDanSeeForm #port").html(data.port);
				$("#equipmentKanDanSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentKanDanSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentKanDanSeeForm #system").html(data.system);
				$("#equipmentKanDanSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentKanDanSeeForm #sn").html(data.sn);
				$("#equipmentKanDanSeeForm #device").html(data.device);
				$("#equipmentKanDanSeeForm #remark").html(data.remark);
				$("#equipmentKanDanSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentKanDanSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentKanDanSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentKanDanSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentKanDanSeeForm #extStr1").html(data.extStr1);
				$("#equipmentKanDanSeeForm #extStr2").html(data.extStr2);
				$("#equipmentKanDanSeeForm #extStr3").html(data.extStr3);
				$("#equipmentKanDanSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentKanDanSee.clearForm = function(){
	$('#equipmentKanDanSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentKanDan.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentKanDan.loadModule);
	$("#showAddDiv_t").click(equipmentKanDan.loadModule);
	
	$("#queryMachine").click(equipmentKanDan.equipmentKanDanList);
	$("#queryReset").click(equipmentKanDan.queryReset);
	$("#deleteMachines").click("click", function(){equipmentKanDan.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentKanDan.equipmentKanDanList();
});