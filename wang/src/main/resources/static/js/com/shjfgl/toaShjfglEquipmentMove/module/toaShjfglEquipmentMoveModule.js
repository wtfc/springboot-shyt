//初始化方法
var toaShjfglEquipmentMoveModule = {};

//重复提交标识
toaShjfglEquipmentMoveModule.subState = false;

//显示表单弹出层
toaShjfglEquipmentMoveModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaShjfglEquipmentMoveModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaShjfglEquipmentMoveModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaShjfglEquipmentMove/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaShjfglEquipmentMoveModule.clearForm();
				$("#toaShjfglEquipmentMoveModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaShjfglEquipmentMoveModule.saveOrModify = function (hide) {
	if(toaShjfglEquipmentMoveModule.subState)return;
		toaShjfglEquipmentMoveModule.subState = true;
	if ($("#toaShjfglEquipmentMoveModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaShjfglEquipmentMove/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaShjfglEquipmentMove/update.action";
		}
		var formData = $("#toaShjfglEquipmentMoveModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaShjfglEquipmentMoveModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaShjfglEquipmentMoveModule.clearForm();
					}
					$("#token").val(data.token);
					toaShjfglEquipmentMove.toaShjfglEquipmentMoveList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaShjfglEquipmentMoveModuleForm", data.labelErrorMessage);
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
				toaShjfglEquipmentMoveModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaShjfglEquipmentMoveModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaShjfglEquipmentMoveModule.clearForm = function(){
	$('#toaShjfglEquipmentMoveModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaShjfglEquipmentMoveModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaShjfglEquipmentMoveModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});