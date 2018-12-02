var equipmentFuFeng = {};

//分页处理 start
//分页对象
equipmentFuFeng.oTable = null;

equipmentFuFeng.oTableAoColumns = [
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

equipmentFuFeng.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentFuFeng.oTable, aoData);
	
	var clientName = $("#equipmentFuFengQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentFuFengQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentFuFengQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentFuFengQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentFuFengQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentFuFengQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentFuFengQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentFuFengQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentFuFengQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentFuFeng.queryReset = function(){
	$('#equipmentFuFengQueryForm')[0].reset();
};


//分页查询
equipmentFuFeng.equipmentFuFengList = function () {
	
	if (equipmentFuFeng.oTable == null) {
		equipmentFuFeng.oTable = $('#equipmentFuFengTable').dataTable( {
			
			"iDisplayLength": equipmentFuFeng.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentFuFeng.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentFuFeng.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentFuFeng.oTable.fnDraw();
	}
};

equipmentFuFeng.deleteEquipment = function (id) {
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
			equipmentFuFeng.deleteTimeSet(ids);
		}
	});
};


equipmentFuFeng.deleteTimeSet = function(ids) {
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
			equipmentFuFeng.equipmentFuFengList();
		}
	});
};

//加载添加DIV
equipmentFuFeng.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormFuFeng.action",null,function(){
			equipmentFuFengModule.show();
		});
	}
	else{
		equipmentFuFengModule.show();
	}
};
		
//加载修改div
equipmentFuFeng.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormFuFeng.action",null,function(){
			equipmentFuFengModule.get(id);
		});
	}
	else{
		equipmentFuFengModule.get(id);
	}
};

//初始化方法
var equipmentFuFengSee = {};

//获取修改信息
equipmentFuFeng.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentFuFengSee.clearForm();
				//$("#equipmentFuFengSeeForm").fill(data);
				$("#equipmentFuFengSeeForm #clientName").html(data.clientName);
				$("#equipmentFuFengSeeForm #type").html(data.type);
				$("#equipmentFuFengSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentFuFengSeeForm #contact").html(data.contact);
				$("#equipmentFuFengSeeForm #machina").html(data.machina);
				$("#equipmentFuFengSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentFuFengSeeForm #address").html(data.address);
				$("#equipmentFuFengSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentFuFengSeeForm #ip").html(data.ip);
				$("#equipmentFuFengSeeForm #uCount").html(data.uCount);
				$("#equipmentFuFengSeeForm #power").html(data.power);
				$("#equipmentFuFengSeeForm #plantPower").html(data.plantPower);
				$("#equipmentFuFengSeeForm #functionn").html(data.functionn);
				$("#equipmentFuFengSeeForm #port").html(data.port);
				$("#equipmentFuFengSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentFuFengSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentFuFengSeeForm #system").html(data.system);
				$("#equipmentFuFengSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentFuFengSeeForm #sn").html(data.sn);
				$("#equipmentFuFengSeeForm #device").html(data.device);
				$("#equipmentFuFengSeeForm #remark").html(data.remark);
				$("#equipmentFuFengSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentFuFengSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentFuFengSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentFuFengSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentFuFengSeeForm #extStr1").html(data.extStr1);
				$("#equipmentFuFengSeeForm #extStr2").html(data.extStr2);
				$("#equipmentFuFengSeeForm #extStr3").html(data.extStr3);
				$("#equipmentFuFengSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentFuFengSee.clearForm = function(){
	$('#equipmentFuFengSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentFuFeng.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentFuFeng.loadModule);
	$("#showAddDiv_t").click(equipmentFuFeng.loadModule);
	
	$("#queryMachine").click(equipmentFuFeng.equipmentFuFengList);
	$("#queryReset").click(equipmentFuFeng.queryReset);
	$("#deleteMachines").click("click", function(){equipmentFuFeng.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentFuFeng.equipmentFuFengList();
});