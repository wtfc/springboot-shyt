//初始化方法
var toaNetworkWrongModule = {};

//重复提交标识
toaNetworkWrongModule.subState = false;

//显示表单弹出层
toaNetworkWrongModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaNetworkWrongModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaNetworkWrongModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaNetworkWrong/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaNetworkWrongModule.clearForm();
				$("#toaNetworkWrongModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaNetworkWrongModule.saveOrModify = function (hide) {
	if(toaNetworkWrongModule.subState)return;
		toaNetworkWrongModule.subState = true;
	if ($("#toaNetworkWrongModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaNetworkWrong/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaNetworkWrong/update.action";
		}
		var formData = $("#toaNetworkWrongModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaNetworkWrongModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaNetworkWrongModule.clearForm();
					}
					$("#token").val(data.token);
					toaNetworkWrong.toaNetworkWrongList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaNetworkWrongModuleForm", data.labelErrorMessage);
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
				toaNetworkWrongModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaNetworkWrongModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaNetworkWrongModule.clearForm = function(){
	$('#toaNetworkWrongModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaNetworkWrongModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaNetworkWrongModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});