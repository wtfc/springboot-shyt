var equipmentLuGu = {};

//分页处理 start
//分页对象
equipmentLuGu.oTable = null;

equipmentLuGu.oTableAoColumns = [
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

equipmentLuGu.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentLuGu.oTable, aoData);
	
	var clientName = $("#equipmentLuGuQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentLuGuQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentLuGuQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentLuGuQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentLuGuQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentLuGuQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentLuGuQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentLuGuQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentLuGuQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentLuGu.queryReset = function(){
	$('#equipmentLuGuQueryForm')[0].reset();
};


//分页查询
equipmentLuGu.equipmentLuGuList = function () {
	
	if (equipmentLuGu.oTable == null) {
		equipmentLuGu.oTable = $('#equipmentLuGuTable').dataTable( {
			
			"iDisplayLength": equipmentLuGu.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentLuGu.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentLuGu.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentLuGu.oTable.fnDraw();
	}
};

equipmentLuGu.deleteEquipment = function (id) {
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
			equipmentLuGu.deleteTimeSet(ids);
		}
	});
};


equipmentLuGu.deleteTimeSet = function(ids) {
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
			equipmentLuGu.equipmentLuGuList();
		}
	});
};

//加载添加DIV
equipmentLuGu.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormLuGu.action",null,function(){
			equipmentLuGuModule.show();
		});
	}
	else{
		equipmentLuGuModule.show();
	}
};
		
//加载修改div
equipmentLuGu.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormLuGu.action",null,function(){
			equipmentLuGuModule.get(id);
		});
	}
	else{
		equipmentLuGuModule.get(id);
	}
};

//初始化方法
var equipmentLuGuSee = {};

//获取修改信息
equipmentLuGu.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentLuGuSee.clearForm();
				//$("#equipmentLuGuSeeForm").fill(data);
				$("#equipmentLuGuSeeForm #clientName").html(data.clientName);
				$("#equipmentLuGuSeeForm #type").html(data.type);
				$("#equipmentLuGuSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentLuGuSeeForm #contact").html(data.contact);
				$("#equipmentLuGuSeeForm #machina").html(data.machina);
				$("#equipmentLuGuSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentLuGuSeeForm #address").html(data.address);
				$("#equipmentLuGuSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentLuGuSeeForm #ip").html(data.ip);
				$("#equipmentLuGuSeeForm #uCount").html(data.uCount);
				$("#equipmentLuGuSeeForm #power").html(data.power);
				$("#equipmentLuGuSeeForm #plantPower").html(data.plantPower);
				$("#equipmentLuGuSeeForm #functionn").html(data.functionn);
				$("#equipmentLuGuSeeForm #port").html(data.port);
				$("#equipmentLuGuSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentLuGuSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentLuGuSeeForm #system").html(data.system);
				$("#equipmentLuGuSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentLuGuSeeForm #sn").html(data.sn);
				$("#equipmentLuGuSeeForm #device").html(data.device);
				$("#equipmentLuGuSeeForm #remark").html(data.remark);
				$("#equipmentLuGuSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentLuGuSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentLuGuSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentLuGuSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentLuGuSeeForm #extStr1").html(data.extStr1);
				$("#equipmentLuGuSeeForm #extStr2").html(data.extStr2);
				$("#equipmentLuGuSeeForm #extStr3").html(data.extStr3);
				$("#equipmentLuGuSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentLuGuSee.clearForm = function(){
	$('#equipmentLuGuSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentLuGu.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentLuGu.loadModule);
	$("#showAddDiv_t").click(equipmentLuGu.loadModule);
	
	$("#queryMachine").click(equipmentLuGu.equipmentLuGuList);
	$("#queryReset").click(equipmentLuGu.queryReset);
	$("#deleteMachines").click("click", function(){equipmentLuGu.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentLuGu.equipmentLuGuList();
});