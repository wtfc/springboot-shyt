var equipmentZhaoWei = {};

//分页处理 start
//分页对象
equipmentZhaoWei.oTable = null;

equipmentZhaoWei.oTableAoColumns = [
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

equipmentZhaoWei.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentZhaoWei.oTable, aoData);
	
	var clientName = $("#equipmentZhaoWeiQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentZhaoWeiQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentZhaoWeiQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentZhaoWeiQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentZhaoWeiQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentZhaoWeiQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentZhaoWeiQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentZhaoWeiQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentZhaoWeiQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
};

//重置按钮功能
equipmentZhaoWei.queryReset = function(){
	$('#equipmentZhaoWeiQueryForm')[0].reset();
};


//分页查询
equipmentZhaoWei.equipmentZhaoWeiList = function () {
	
	if (equipmentZhaoWei.oTable == null) {
		equipmentZhaoWei.oTable = $('#equipmentZhaoWeiTable').dataTable( {
			
			"iDisplayLength": equipmentZhaoWei.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentZhaoWei.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentZhaoWei.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentZhaoWei.oTable.fnDraw();
	}
};

equipmentZhaoWei.deleteEquipment = function (id) {
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
			equipmentZhaoWei.deleteTimeSet(ids);
		}
	});
};


equipmentZhaoWei.deleteTimeSet = function(ids) {
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
			equipmentZhaoWei.equipmentZhaoWeiList();
		}
	});
};

//加载添加DIV
equipmentZhaoWei.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormZhaoWei.action",null,function(){
			equipmentZhaoWeiModule.show();
		});
	}
	else{
		equipmentZhaoWeiModule.show();
	}
};
		
//加载修改div
equipmentZhaoWei.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormZhaoWei.action",null,function(){
			equipmentZhaoWeiModule.get(id);
		});
	}
	else{
		equipmentZhaoWeiModule.get(id);
	}
};

//初始化方法
var equipmentZhaoWeiSee = {};

//获取修改信息
equipmentZhaoWei.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentZhaoWeiSee.clearForm();
				//$("#equipmentZhaoWeiSeeForm").fill(data);
				$("#equipmentZhaoWeiSeeForm #clientName").html(data.clientName);
				$("#equipmentZhaoWeiSeeForm #type").html(data.type);
				$("#equipmentZhaoWeiSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentZhaoWeiSeeForm #contact").html(data.contact);
				$("#equipmentZhaoWeiSeeForm #machina").html(data.machina);
				$("#equipmentZhaoWeiSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentZhaoWeiSeeForm #address").html(data.address);
				$("#equipmentZhaoWeiSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentZhaoWeiSeeForm #ip").html(data.ip);
				$("#equipmentZhaoWeiSeeForm #uCount").html(data.uCount);
				$("#equipmentZhaoWeiSeeForm #power").html(data.power);
				$("#equipmentZhaoWeiSeeForm #plantPower").html(data.plantPower);
				$("#equipmentZhaoWeiSeeForm #functionn").html(data.functionn);
				$("#equipmentZhaoWeiSeeForm #port").html(data.port);
				$("#equipmentZhaoWeiSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentZhaoWeiSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentZhaoWeiSeeForm #system").html(data.system);
				$("#equipmentZhaoWeiSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentZhaoWeiSeeForm #sn").html(data.sn);
				$("#equipmentZhaoWeiSeeForm #device").html(data.device);
				$("#equipmentZhaoWeiSeeForm #remark").html(data.remark);
				$("#equipmentZhaoWeiSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentZhaoWeiSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentZhaoWeiSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentZhaoWeiSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentZhaoWeiSeeForm #extStr1").html(data.extStr1);
				$("#equipmentZhaoWeiSeeForm #extStr2").html(data.extStr2);
				$("#equipmentZhaoWeiSeeForm #extStr3").html(data.extStr3);
				$("#equipmentZhaoWeiSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentZhaoWeiSee.clearForm = function(){
	$('#equipmentZhaoWeiSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentZhaoWei.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentZhaoWei.loadModule);
	$("#showAddDiv_t").click(equipmentZhaoWei.loadModule);
	
	$("#queryMachine").click(equipmentZhaoWei.equipmentZhaoWeiList);
	$("#queryReset").click(equipmentZhaoWei.queryReset);
	$("#deleteMachines").click("click", function(){equipmentZhaoWei.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentZhaoWei.equipmentZhaoWeiList();
});