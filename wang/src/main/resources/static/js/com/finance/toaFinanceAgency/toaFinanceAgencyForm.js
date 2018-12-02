//初始化方法
var toaFinanceAgencyModule = {};
//重复提交标识
toaFinanceAgencyModule.subState = false;
//获取修改信息
toaFinanceAgencyModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/finance/toaFinanceAgency/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaFinanceAgencyModule.clearForm();
				$("#toaFinanceAgencyForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaFinanceAgencyModule.saveOrModify = function (hide) {
	if(toaFinanceAgencyModule.subState)return;
		toaFinanceAgencyModule.subState = true;
	if ($("#toaFinanceAgencyForm").valid()) {
		var url = getRootPath()+"/finance/toaFinanceAgency/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/finance/toaFinanceAgency/update.action";
		}
		var formData = $("#toaFinanceAgencyForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceAgencyModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/finance/toaFinanceAgency/manage.action","","/finance/toaFinanceAgency/manage.action");
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
				toaFinanceAgencyModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceAgencyModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaFinanceAgencyModule.clearForm = function(){
	$('#toaFinanceAgencyForm')[0].reset();
	hideErrorMessage();
};