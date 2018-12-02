//初始化方法
var toaShjfglEquipmentWrongModule = {};

//重复提交标识
toaShjfglEquipmentWrongModule.subState = false;

//显示表单弹出层
toaShjfglEquipmentWrongModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaShjfglEquipmentWrongModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaShjfglEquipmentWrongModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaShjfglEquipmentWrong/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaShjfglEquipmentWrongModule.clearForm();
				$("#toaShjfglEquipmentWrongModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaShjfglEquipmentWrongModule.saveOrModify = function (hide) {
	if(toaShjfglEquipmentWrongModule.subState)return;
		toaShjfglEquipmentWrongModule.subState = true;
	if ($("#toaShjfglEquipmentWrongModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaShjfglEquipmentWrong/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaShjfglEquipmentWrong/update.action";
		}
		var formData = $("#toaShjfglEquipmentWrongModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaShjfglEquipmentWrongModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaShjfglEquipmentWrongModule.clearForm();
					}
					$("#token").val(data.token);
					toaShjfglEquipmentWrong.toaShjfglEquipmentWrongList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaShjfglEquipmentWrongModuleForm", data.labelErrorMessage);
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
				toaShjfglEquipmentWrongModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaShjfglEquipmentWrongModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaShjfglEquipmentWrongModule.clearForm = function(){
	$('#toaShjfglEquipmentWrongModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaShjfglEquipmentWrongModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaShjfglEquipmentWrongModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});