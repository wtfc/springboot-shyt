//初始化方法
var roomViewModule = {};

//重复提交标识
roomViewModule.subState = false;
//显示表单弹出层
roomViewModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#id").val(0);
	$('#myModal-edit').modal('show');
};

//获取机房信息
roomViewModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/customer/equipmentInfo/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$("#roomViewModuleForm #clientName").html(data.clientName);
				$("#roomViewModuleForm #type").html(data.type);
				$("#roomViewModuleForm #onlineDate").html(data.onlineDate);
				$("#roomViewModuleForm #contact").html(data.contact);
				$("#roomViewModuleForm #machina").html(data.machina);
				$("#roomViewModuleForm #machinaNumber").html(data.machinaNumber);
				$("#roomViewModuleForm #address").html(data.address);
				$("#roomViewModuleForm #interchangerThree").html(data.interchangerThree);
				$("#roomViewModuleForm #ip").html(data.ip);
				$("#roomViewModuleForm #uCount").html(data.uCount);
				$("#roomViewModuleForm #power").html(data.power);
				$("#roomViewModuleForm #plantPower").html(data.plantPower);
				$("#roomViewModuleForm #functionn").html(data.functionn);
				$("#roomViewModuleForm #port").html(data.port);
				$("#roomViewModuleForm #aCurrent").html(data.aCurrent);
				$("#roomViewModuleForm #bCurrent").html(data.bCurrent);
				$("#roomViewModuleForm #system").html(data.system);
				$("#roomViewModuleForm #serialNumber").html(data.serialNumber);
				$("#roomViewModuleForm #sn").html(data.sn);
				$("#roomViewModuleForm #device").html(data.device);
				$("#roomViewModuleForm #remark").html(data.remark);
				$("#roomViewModuleForm #netmaskOne").html(data.netmaskOne);
				$("#roomViewModuleForm #interchangerOne").html(data.interchangerOne);
				$("#roomViewModuleForm #netmaskTwo").html(data.netmaskTwo);
				
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$('#myModal-edit').modal('show');
				$("#id").val(id);
			}
		}
	});
};

//添加修改公用方法
roomViewModule.saveOrModify = function (hide) {
	if(roomViewModule.subState)return;
	roomViewModule.subState = true;
	if ($("#roomViewModuleForm").valid()) {
		var url = getRootPath()+"/customer/equipmentInfo/save.action";
		var formData = $("#roomViewModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				roomViewModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/customer/toaMachineRestartInfo/manage.action", "", "/customer/toaMachineRestartInfo/manage.action");
						}
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					}
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("roomViewModuleForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
					$("#token").val(data.token);
				}
			},
			error : function() {
				roomViewModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		roomViewModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

roomViewModule.clearForm = function(){
	$('#roomViewModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){roomViewModule.saveOrModify(true);});
	
	$('id').hide();
});
