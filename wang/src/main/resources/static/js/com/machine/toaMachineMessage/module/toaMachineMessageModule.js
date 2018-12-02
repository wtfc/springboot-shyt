//初始化方法
var toaMachineMessageModule = {};

//重复提交标识
toaMachineMessageModule.subState = false;

//显示表单弹出层
toaMachineMessageModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaMachineMessageModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaMachineMessageModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaMachineMessage/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaMachineMessageModule.clearForm();
				$("#toaMachineMessageModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaMachineMessageModule.saveOrModify = function (hide) {
	if(toaMachineMessageModule.subState)return;
		toaMachineMessageModule.subState = true;
	if ($("#toaMachineMessageModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaMachineMessage/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaMachineMessage/update.action";
		}
		var formData = $("#toaMachineMessageModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineMessageModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaMachineMessageModule.clearForm();
					}
					$("#token").val(data.token);
					toaMachineMessage.toaMachineMessageList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaMachineMessageModuleForm", data.labelErrorMessage);
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
				toaMachineMessageModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineMessageModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaMachineMessageModule.clearForm = function(){
	$('#toaMachineMessageModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaMachineMessageModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaMachineMessageModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});