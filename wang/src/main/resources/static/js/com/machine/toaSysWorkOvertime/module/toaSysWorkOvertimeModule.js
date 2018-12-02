//初始化方法
var toaSysWorkOvertimeModule = {};

//重复提交标识
toaSysWorkOvertimeModule.subState = false;

//显示表单弹出层
toaSysWorkOvertimeModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaSysWorkOvertimeModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaSysWorkOvertimeModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaSysWorkOvertime/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaSysWorkOvertimeModule.clearForm();
				$("#toaSysWorkOvertimeModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaSysWorkOvertimeModule.saveOrModify = function (hide) {
	if(toaSysWorkOvertimeModule.subState)return;
		toaSysWorkOvertimeModule.subState = true;
	if ($("#toaSysWorkOvertimeModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaSysWorkOvertime/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaSysWorkOvertime/update.action";
		}
		var formData = $("#toaSysWorkOvertimeModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaSysWorkOvertimeModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaSysWorkOvertimeModule.clearForm();
					}
					$("#token").val(data.token);
					toaSysWorkOvertime.toaSysWorkOvertimeList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaSysWorkOvertimeModuleForm", data.labelErrorMessage);
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
				toaSysWorkOvertimeModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaSysWorkOvertimeModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaSysWorkOvertimeModule.clearForm = function(){
	$('#toaSysWorkOvertimeModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaSysWorkOvertimeModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaSysWorkOvertimeModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});