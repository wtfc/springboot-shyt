//初始化方法
var toaProductFeedbackModule = {};
//重复提交标识
toaProductFeedbackModule.subState = false;
//获取修改信息
toaProductFeedbackModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/product/toaProductFeedback/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaProductFeedbackModule.clearForm();
				$("#toaProductFeedbackForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaProductFeedbackModule.saveOrModify = function (hide) {
	if(toaProductFeedbackModule.subState)return;
		toaProductFeedbackModule.subState = true;
	if ($("#toaProductFeedbackForm").valid()) {
		var url = getRootPath()+"/product/toaProductFeedback/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/product/toaProductFeedback/update.action";
		}
		var formData = $("#toaProductFeedbackForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaProductFeedbackModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/product/toaProductFeedback/manage.action","","/product/toaProductFeedback/manage.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("monitorForm", data.labelErrorMessage);
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
				toaProductFeedbackModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaProductFeedbackModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaProductFeedbackModule.clearForm = function(){
	$('#toaProductFeedbackForm')[0].reset();
	hideErrorMessage();
};