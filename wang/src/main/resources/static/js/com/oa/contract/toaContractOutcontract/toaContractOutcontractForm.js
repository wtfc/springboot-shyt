//初始化方法
var toaContractOutcontractModule = {};
//重复提交标识
toaContractOutcontractModule.subState = false;
//获取修改信息
toaContractOutcontractModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/contract/toaContractOutcontract/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaContractOutcontractModule.clearForm();
				$("#toaContractOutcontractForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaContractOutcontractModule.saveOrModify = function (hide) {
	if(toaContractOutcontractModule.subState)return;
		toaContractOutcontractModule.subState = true;
	if ($("#toaContractOutcontractForm").valid()) {
		var url = getRootPath()+"/contract/toaContractOutcontract/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/contract/toaContractOutcontract/update.action";
		}
		var formData = $("#toaContractOutcontractForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaContractOutcontractModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/contract/toaContractOutcontract/manage.action","","/contract/toaContractOutcontract/manage.action");
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
				toaContractOutcontractModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaContractOutcontractModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaContractOutcontractModule.clearForm = function(){
	$('#toaContractOutcontractForm')[0].reset();
	hideErrorMessage();
};