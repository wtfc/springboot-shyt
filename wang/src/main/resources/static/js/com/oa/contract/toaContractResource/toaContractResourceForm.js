//初始化方法
var toaContractResourceModule = {};
//重复提交标识
toaContractResourceModule.subState = false;
//获取修改信息
toaContractResourceModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/contract/toaContractResource/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaContractResourceModule.clearForm();
				$("#toaContractResourceForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaContractResourceModule.saveOrModify = function (hide) {
	if(toaContractResourceModule.subState)return;
		toaContractResourceModule.subState = true;
	if ($("#toaContractResourceForm").valid()) {
		var url = getRootPath()+"/contract/toaContractResource/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/contract/toaContractResource/update.action";
		}
		var formData = $("#toaContractResourceForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaContractResourceModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/contract/toaContractResource/manage.action","","/contract/toaContractResource/manage.action");
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
				toaContractResourceModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaContractResourceModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaContractResourceModule.clearForm = function(){
	$('#toaContractResourceForm')[0].reset();
	hideErrorMessage();
};