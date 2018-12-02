//初始化方法
var toaFinancePercentageModule = {};
//重复提交标识
toaFinancePercentageModule.subState = false;
//获取修改信息
toaFinancePercentageModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/finance/toaFinancePercentage/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaFinancePercentageModule.clearForm();
				$("#toaFinancePercentageForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaFinancePercentageModule.saveOrModify = function (hide) {
	if(toaFinancePercentageModule.subState)return;
		toaFinancePercentageModule.subState = true;
	if ($("#toaFinancePercentageForm").valid()) {
		var url = getRootPath()+"/finance/toaFinancePercentage/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/finance/toaFinancePercentage/update.action";
		}
		var formData = $("#toaFinancePercentageForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinancePercentageModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/finance/toaFinancePercentage/manage.action","","/finance/toaFinancePercentage/manage.action");
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
				toaFinancePercentageModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinancePercentageModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaFinancePercentageModule.clearForm = function(){
	$('#toaFinancePercentageForm')[0].reset();
	hideErrorMessage();
};