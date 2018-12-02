var equipmentXiaoMiDaiWei = {};

//分页处理 start
//分页对象
equipmentXiaoMiDaiWei.oTable = null;

equipmentXiaoMiDaiWei.oTableAoColumns = [
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

equipmentXiaoMiDaiWei.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipmentXiaoMiDaiWei.oTable, aoData);
	
	var clientName = $("#equipmentXiaoMiDaiWeiQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentXiaoMiDaiWeiQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	/*var serialNumber = $("#equipmentXiaoMiDaiWeiQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}*/
	var onlineDate = $("#equipmentXiaoMiDaiWeiQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentXiaoMiDaiWeiQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	/*var extStr5 = $("#equipmentXiaoMiDaiWeiQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}*/
	var address = $("#equipmentXiaoMiDaiWeiQueryForm #address").val();
	if(address.length > 0){
		aoData.push({ "name": "address", "value": address});
	}
	var sn = $("#equipmentXiaoMiDaiWeiQueryForm #sn").val();
	if(sn.length > 0){
		aoData.push({ "name": "sn", "value": sn});
	}
	var netmaskOne = $("#equipmentXiaoMiDaiWeiQueryForm #netmaskOne").val();
	if(netmaskOne.length > 0){
		aoData.push({ "name": "netmaskOne", "value": netmaskOne});
	}
	
};

//重置按钮功能
equipmentXiaoMiDaiWei.queryReset = function(){
	$('#equipmentXiaoMiDaiWeiQueryForm')[0].reset();
};


//分页查询
equipmentXiaoMiDaiWei.equipmentXiaoMiDaiWeiList = function () {
	
	if (equipmentXiaoMiDaiWei.oTable == null) {
		equipmentXiaoMiDaiWei.oTable = $('#equipmentXiaoMiDaiWeiTable').dataTable( {
			
			"iDisplayLength": equipmentXiaoMiDaiWei.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentXiaoMiDaiWei.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipmentXiaoMiDaiWei.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		equipmentXiaoMiDaiWei.oTable.fnDraw();
	}
};

equipmentXiaoMiDaiWei.deleteEquipment = function (id) {
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
			equipmentXiaoMiDaiWei.deleteTimeSet(ids);
		}
	});
};


equipmentXiaoMiDaiWei.deleteTimeSet = function(ids) {
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
			equipmentXiaoMiDaiWei.equipmentXiaoMiDaiWeiList();
		}
	});
};

//加载添加DIV
equipmentXiaoMiDaiWei.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormXiaoMiDaiWei.action",null,function(){
			equipmentXiaoMiDaiWeiModule.show();
		});
	}
	else{
		equipmentXiaoMiDaiWeiModule.show();
	}
};
		
//加载修改div
equipmentXiaoMiDaiWei.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/machine/equipment/loadFormXiaoMiDaiWei.action",null,function(){
			equipmentXiaoMiDaiWeiModule.get(id);
		});
	}
	else{
		equipmentXiaoMiDaiWeiModule.get(id);
	}
};

//初始化方法
var equipmentXiaoMiDaiWeiSee = {};

//获取修改信息
equipmentXiaoMiDaiWei.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentXiaoMiDaiWeiSee.clearForm();
				//$("#equipmentXiaoMiDaiWeiSeeForm").fill(data);
				$("#equipmentXiaoMiDaiWeiSeeForm #clientName").html(data.clientName);
				$("#equipmentXiaoMiDaiWeiSeeForm #type").html(data.type);
				$("#equipmentXiaoMiDaiWeiSeeForm #onlineDate").html(data.onlineDate);
				$("#equipmentXiaoMiDaiWeiSeeForm #contact").html(data.contact);
				$("#equipmentXiaoMiDaiWeiSeeForm #machina").html(data.machina);
				$("#equipmentXiaoMiDaiWeiSeeForm #machinaNumber").html(data.machinaNumber);
				$("#equipmentXiaoMiDaiWeiSeeForm #address").html(data.address);
				$("#equipmentXiaoMiDaiWeiSeeForm #interchangerThree").html(data.interchangerThree);
				$("#equipmentXiaoMiDaiWeiSeeForm #ip").html(data.ip);
				$("#equipmentXiaoMiDaiWeiSeeForm #uCount").html(data.uCount);
				$("#equipmentXiaoMiDaiWeiSeeForm #power").html(data.power);
				$("#equipmentXiaoMiDaiWeiSeeForm #plantPower").html(data.plantPower);
				$("#equipmentXiaoMiDaiWeiSeeForm #functionn").html(data.functionn);
				$("#equipmentXiaoMiDaiWeiSeeForm #port").html(data.port);
				$("#equipmentXiaoMiDaiWeiSeeForm #aCurrent").html(data.aCurrent);
				$("#equipmentXiaoMiDaiWeiSeeForm #bCurrent").html(data.bCurrent);
				$("#equipmentXiaoMiDaiWeiSeeForm #system").html(data.system);
				$("#equipmentXiaoMiDaiWeiSeeForm #serialNumber").html(data.serialNumber);
				$("#equipmentXiaoMiDaiWeiSeeForm #sn").html(data.sn);
				$("#equipmentXiaoMiDaiWeiSeeForm #device").html(data.device);
				$("#equipmentXiaoMiDaiWeiSeeForm #remark").html(data.remark);
				$("#equipmentXiaoMiDaiWeiSeeForm #netmaskOne").html(data.netmaskOne);
				$("#equipmentXiaoMiDaiWeiSeeForm #interchangerOne").html(data.interchangerOne);
				$("#equipmentXiaoMiDaiWeiSeeForm #netmaskTwo").html(data.netmaskTwo);
				/*$("#equipmentXiaoMiDaiWeiSeeForm #interchangerTwo").html(data.interchangerTwo);
				$("#equipmentXiaoMiDaiWeiSeeForm #extStr1").html(data.extStr1);
				$("#equipmentXiaoMiDaiWeiSeeForm #extStr2").html(data.extStr2);
				$("#equipmentXiaoMiDaiWeiSeeForm #extStr3").html(data.extStr3);
				$("#equipmentXiaoMiDaiWeiSeeForm #extStr4").html(data.extStr4);*/
				$('#myModal').modal('show');
			}
		}
	});
};
//清空表单数据
equipmentXiaoMiDaiWeiSee.clearForm = function(){
	$('#equipmentXiaoMiDaiWeiSeeForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipmentXiaoMiDaiWei.pageCount = TabNub>0 ? TabNub : 10;
	
	
	$("#addmachineButton").click(equipmentXiaoMiDaiWei.loadModule);
	$("#showAddDiv_t").click(equipmentXiaoMiDaiWei.loadModule);
	
	$("#queryMachine").click(equipmentXiaoMiDaiWei.equipmentXiaoMiDaiWeiList);
	$("#queryReset").click(equipmentXiaoMiDaiWei.queryReset);
	$("#deleteMachines").click("click", function(){equipmentXiaoMiDaiWei.deleteEquipment(0);});
	$("#formDivClose").click(function(){$('#myModal').modal('hide');});
	//初始化列表方法
	equipmentXiaoMiDaiWei.equipmentXiaoMiDaiWeiList();
});