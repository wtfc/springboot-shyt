//初始化方法
var toaMachineExchangeModule = {};

//重复提交标识
toaMachineExchangeModule.subState = false;

//显示表单弹出层
toaMachineExchangeModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaMachineExchangeModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaMachineExchangeModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaMachineExchange/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaMachineExchangeModule.clearForm();
				$("#toaMachineExchangeModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaMachineExchangeModule.saveOrModify = function (hide) {
	if(toaMachineExchangeModule.subState)return;
		toaMachineExchangeModule.subState = true;
	if ($("#toaMachineExchangeModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaMachineExchange/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaMachineExchange/update.action";
		}
		var formData = $("#toaMachineExchangeModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineExchangeModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaMachineExchangeModule.clearForm();
					}
					$("#token").val(data.token);
					toaMachineExchange.toaMachineExchangeList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaMachineExchangeModuleForm", data.labelErrorMessage);
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
				toaMachineExchangeModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineExchangeModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaMachineExchangeModule.clearForm = function(){
	$('#toaMachineExchangeModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaMachineExchangeModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaMachineExchangeModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});