//初始化方法
var toaEquipmentUpDownModule = {};

//重复提交标识
toaEquipmentUpDownModule.subState = false;

//显示表单弹出层
toaEquipmentUpDownModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaEquipmentUpDownModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaEquipmentUpDownModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaEquipmentUpDown/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaEquipmentUpDownModule.clearForm();
				$("#toaEquipmentUpDownModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaEquipmentUpDownModule.saveOrModify = function (hide) {
	if(toaEquipmentUpDownModule.subState)return;
		toaEquipmentUpDownModule.subState = true;
	if ($("#toaEquipmentUpDownModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaEquipmentUpDown/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaEquipmentUpDown/update.action";
		}
		var formData = $("#toaEquipmentUpDownModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaEquipmentUpDownModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaEquipmentUpDownModule.clearForm();
					}
					$("#token").val(data.token);
					toaEquipmentUpDown.toaEquipmentUpDownList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaEquipmentUpDownModuleForm", data.labelErrorMessage);
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
				toaEquipmentUpDownModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaEquipmentUpDownModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaEquipmentUpDownModule.clearForm = function(){
	$('#toaEquipmentUpDownModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaEquipmentUpDownModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaEquipmentUpDownModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});