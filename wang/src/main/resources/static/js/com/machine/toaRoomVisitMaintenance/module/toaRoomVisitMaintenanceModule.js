//初始化方法
var toaRoomVisitMaintenanceModule = {};

//重复提交标识
toaRoomVisitMaintenanceModule.subState = false;

//显示表单弹出层
toaRoomVisitMaintenanceModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaRoomVisitMaintenanceModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaRoomVisitMaintenanceModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaRoomVisitMaintenance/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaRoomVisitMaintenanceModule.clearForm();
				$("#toaRoomVisitMaintenanceModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaRoomVisitMaintenanceModule.saveOrModify = function (hide) {
	if(toaRoomVisitMaintenanceModule.subState)return;
		toaRoomVisitMaintenanceModule.subState = true;
	if ($("#toaRoomVisitMaintenanceModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaRoomVisitMaintenance/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaRoomVisitMaintenance/update.action";
		}
		var formData = $("#toaRoomVisitMaintenance#oduleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaRoomVisitMaintenanceModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaRoomVisitMaintenanceModule.clearForm();
					}
					$("#token").val(data.token);
					toaRoomVisitMaintenance.toaRoomVisitMaintenanceList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaRoomVisitMaintenanceModuleForm", data.labelErrorMessage);
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
				toaRoomVisitMaintenanceModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $//.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaRoomVisitMaintenanceModule.subState = false;
		msgBox.info({
			content:$//.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaRoomVisitMaintenanceModule.clearForm = function(){
	$('#toaRoomVisitMaintenance#oduleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaRoomVisitMaintenanceModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaRoomVisitMaintenanceModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});