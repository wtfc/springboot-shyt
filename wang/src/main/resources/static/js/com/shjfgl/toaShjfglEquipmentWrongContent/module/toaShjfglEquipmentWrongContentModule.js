//初始化方法
var toaShjfglEquipmentWrongContentModule = {};

//重复提交标识
toaShjfglEquipmentWrongContentModule.subState = false;

//显示表单弹出层
toaShjfglEquipmentWrongContentModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaShjfglEquipmentWrongContentModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaShjfglEquipmentWrongContentModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaShjfglEquipmentWrongContent/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaShjfglEquipmentWrongContentModule.clearForm();
				$("#toaShjfglEquipmentWrongContentModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaShjfglEquipmentWrongContentModule.saveOrModify = function (hide) {
	if(toaShjfglEquipmentWrongContentModule.subState)return;
		toaShjfglEquipmentWrongContentModule.subState = true;
	if ($("#toaShjfglEquipmentWrongContentModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaShjfglEquipmentWrongContent/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaShjfglEquipmentWrongContent/update.action";
		}
		var formData = $("#toaShjfglEquipmentWrongContentModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaShjfglEquipmentWrongContentModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaShjfglEquipmentWrongContentModule.clearForm();
					}
					$("#token").val(data.token);
					toaShjfglEquipmentWrongContent.toaShjfglEquipmentWrongContentList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaShjfglEquipmentWrongContentModuleForm", data.labelErrorMessage);
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
				toaShjfglEquipmentWrongContentModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaShjfglEquipmentWrongContentModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaShjfglEquipmentWrongContentModule.clearForm = function(){
	$('#toaShjfglEquipmentWrongContentModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaShjfglEquipmentWrongContentModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaShjfglEquipmentWrongContentModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});