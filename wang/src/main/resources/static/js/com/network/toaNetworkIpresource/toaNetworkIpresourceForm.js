//初始化方法
var toaNetworkIpresourceModule = {};
//重复提交标识
toaNetworkIpresourceModule.subState = false;
//获取修改信息
toaNetworkIpresourceModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/network/toaNetworkIpresource/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaNetworkIpresourceModule.clearForm();
				$("#toaNetworkIpresourceForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaNetworkIpresourceModule.saveOrModify = function (hide) {
	if(toaNetworkIpresourceModule.subState)return;
		toaNetworkIpresourceModule.subState = true;
	if ($("#toaNetworkIpresourceForm").valid()) {
		var url = getRootPath()+"/network/toaNetworkIpresource/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/network/toaNetworkIpresource/update.action";
		}
		var formData = $("#toaNetworkIpresourceForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaNetworkIpresourceModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/network/toaNetworkIpresource/manage.action","","/network/toaNetworkIpresource/manage.action");
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
				toaNetworkIpresourceModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaNetworkIpresourceModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaNetworkIpresourceModule.clearForm = function(){
	$('#toaNetworkIpresourceForm')[0].reset();
	hideErrorMessage();
};