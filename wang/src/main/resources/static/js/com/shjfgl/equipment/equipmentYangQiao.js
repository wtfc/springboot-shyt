var equipmentYangQiao = {};

//分页处理 start
//分页对象
equipmentYangQiao.oTable = null;

equipmentYangQiao.oTableAoColumns = [
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

equipmentYangQiao.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentYangQiao.oTable, aoData);
	
	var clientName = $("#equipmentYangQiaoQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentYangQiaoQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentYangQiaoQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentYangQiaoQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentYangQiaoQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentYangQiaoQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentYangQiaoQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentYangQiaoQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentYangQiaoQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentYangQiao.queryReset = function(){
	$('#equipmentYangQiaoQueryForm')[0].reset();
};


//分页查询
equipmentYangQiao.equipmentYangQiaoList = function () {
	
	if (equipmentYangQiao.oTable == null) {
		equipmentYangQiao.oTable = $('#equipmentYangQiaoTable').dataTable( {
			
			"iDisplayLength": equipmentYangQiao.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentYangQiao.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentYangQiao.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentYangQiao.oTable.fnDraw();
	}
};

equipmentYangQiao.deleteEquipment = function (id) {
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
			equipmentYangQiao.deleteTimeSet(ids);
		}
	});
};


equipmentYangQiao.deleteTimeSet = function(ids) {
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
			equipmentYangQiao.equipmentYangQiaoList();
		}
	});
};

//加载添加DIV
equipmentYangQiao.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormYangQiao.action",null,function(){
			equipmentYangQiaoModule.show();
		});
	}
	else{
		equipmentYangQiaoModule.show();
	}
};
		
//加载修改div
equipmentYangQiao.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormYangQiao.action",null,function(){
			equipmentYangQiaoModule.get(id);
		});
	}
	else{
		equipmentYangQiaoModule.get(id);
	}
};

//初始化方法
var equipmentYangQiaoSee = {};

//获取修改信息
equipmentYangQiao.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentYangQiaoSee.clearForm();
				//$("#equipmentYangQiaoSeeForm").fill(data);
				$("#equipmentYangQiaoSeeForm #clientName").html(data.clientName);
				$("#equipmentYangQiaoSeeForm #type").html(data.type);
				$("#equipmentYangQiaoSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentYangQiaoSeeForm #contact").html(data.contact);
				$("#equipmentYangQiaoSeeForm #machina").html(data.machina);
				$("#equipmentYangQiaoSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentYangQiaoSeeForm #address").html(data.address);
				$("#equipmentYangQiaoSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentYangQiaoSeeForm #ip").html(data.ip);
				$("#equipmentYangQiaoSeeForm #uCount").html(data.uCount);
				$("#equipmentYangQiaoSeeForm #power").html(data.power);
				$("#equipmentYangQiaoSeeForm #plantPower").html(data.plantPower);
				$("#equipmentYangQiaoSeeForm #functionn").html(data.functionn);
				$("#equipmentYangQiaoSeeForm #port").html(data.port);
				$("#equipmentYangQiaoSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentYangQiaoSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentYangQiaoSeeForm #system").html(data.system);
				$("#equipmentYangQiaoSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentYangQiaoSeeForm #sn").html(data.sn);
				$("#equipmentYangQiaoSeeForm #device").html(data.device);
				$("#equipmentYangQiaoSeeForm #remark").html(data.remark);
				$("#equipmentYangQiaoSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentYangQiaoSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentYangQiaoSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentYangQiaoSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentYangQiaoSeeForm #extStr1").html(data.extStr1);
				$("#equipmentYangQiaoSeeForm #extStr2").html(data.extStr2);
				$("#equipmentYangQiaoSeeForm #extStr3").html(data.extStr3);
				$("#equipmentYangQiaoSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentYangQiaoSee.clearForm = function(){
	$('#equipmentYangQiaoSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentYangQiao.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentYangQiao.loadModule);
	$("#showAddDiv_t").click(equipmentYangQiao.loadModule);
	
	$("#queryMachine").click(equipmentYangQiao.equipmentYangQiaoList);
	$("#queryReset").click(equipmentYangQiao.queryReset);
	$("#deleteMachines").click("click", function(){equipmentYangQiao.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentYangQiao.equipmentYangQiaoList();
});