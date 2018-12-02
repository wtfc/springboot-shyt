var equipmentDaZu = {};

//分页处理 start
//分页对象
equipmentDaZu.oTable = null;

equipmentDaZu.oTableAoColumns = [
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

equipmentDaZu.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentDaZu.oTable, aoData);
	
	var clientName = $("#equipmentDaZuQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentDaZuQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentDaZuQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentDaZuQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentDaZuQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentDaZuQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentDaZuQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentDaZuQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentDaZuQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
	
};

//重置按钮功能
equipmentDaZu.queryReset = function(){
	$('#equipmentDaZuQueryForm')[0].reset();
};


//分页查询
equipmentDaZu.equipmentDaZuList = function () {
	
	if (equipmentDaZu.oTable == null) {
		equipmentDaZu.oTable = $('#equipmentDaZuTable').dataTable( {
			
			"iDisplayLength": equipmentDaZu.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentDaZu.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentDaZu.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentDaZu.oTable.fnDraw();
	}
};

equipmentDaZu.deleteEquipment = function (id) {
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
			equipmentDaZu.deleteTimeSet(ids);
		}
	});
};


equipmentDaZu.deleteTimeSet = function(ids) {
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
			equipmentDaZu.equipmentDaZuList();
		}
	});
};

//加载添加DIV
equipmentDaZu.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormDaZu.action",null,function(){
			equipmentDaZuModule.show();
		});
	}
	else{
		equipmentDaZuModule.show();
	}
};
		
//加载修改div
equipmentDaZu.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormDaZu.action",null,function(){
			equipmentDaZuModule.get(id);
		});
	}
	else{
		equipmentDaZuModule.get(id);
	}
};

//初始化方法
var equipmentDaZuSee = {};

//获取修改信息
equipmentDaZu.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentDaZuSee.clearForm();
				//$("#equipmentDaZuSeeForm").fill(data);
				$("#equipmentDaZuSeeForm #clientName").html(data.clientName);
				$("#equipmentDaZuSeeForm #type").html(data.type);
				$("#equipmentDaZuSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentDaZuSeeForm #contact").html(data.contact);
				$("#equipmentDaZuSeeForm #machina").html(data.machina);
				$("#equipmentDaZuSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentDaZuSeeForm #address").html(data.address);
				$("#equipmentDaZuSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentDaZuSeeForm #ip").html(data.ip);
				$("#equipmentDaZuSeeForm #uCount").html(data.uCount);
				$("#equipmentDaZuSeeForm #power").html(data.power);
				$("#equipmentDaZuSeeForm #plantPower").html(data.plantPower);
				$("#equipmentDaZuSeeForm #functionn").html(data.functionn);
				$("#equipmentDaZuSeeForm #port").html(data.port);
				$("#equipmentDaZuSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentDaZuSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentDaZuSeeForm #system").html(data.system);
				$("#equipmentDaZuSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentDaZuSeeForm #sn").html(data.sn);
				$("#equipmentDaZuSeeForm #device").html(data.device);
				$("#equipmentDaZuSeeForm #remark").html(data.remark);
				$("#equipmentDaZuSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentDaZuSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentDaZuSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentDaZuSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentDaZuSeeForm #extStr1").html(data.extStr1);
				$("#equipmentDaZuSeeForm #extStr2").html(data.extStr2);
				$("#equipmentDaZuSeeForm #extStr3").html(data.extStr3);
				$("#equipmentDaZuSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentDaZuSee.clearForm = function(){
	$('#equipmentDaZuSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentDaZu.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentDaZu.loadModule);
	$("#showAddDiv_t").click(equipmentDaZu.loadModule);
	
	$("#queryMachine").click(equipmentDaZu.equipmentDaZuList);
	$("#queryReset").click(equipmentDaZu.queryReset);
	$("#deleteMachines").click("click", function(){equipmentDaZu.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentDaZu.equipmentDaZuList();
});