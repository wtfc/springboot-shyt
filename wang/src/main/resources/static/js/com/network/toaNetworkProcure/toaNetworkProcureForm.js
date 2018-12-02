//初始化方法
var toaNetworkProcureModule = {};
//重复提交标识
toaNetworkProcureModule.subState = false;
//获取修改信息
toaNetworkProcureModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/network/toaNetworkProcure/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaNetworkProcureModule.clearForm();
				$("#toaNetworkProcureForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaNetworkProcureModule.saveOrModify = function (hide) {
	if(toaNetworkProcureModule.subState)return;
		toaNetworkProcureModule.subState = true;
	if ($("#toaNetworkProcureForm").valid()) {
		var url = getRootPath()+"/network/toaNetworkProcure/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/network/toaNetworkProcure/update.action";
		}
		var formData = $("#toaNetworkProcureForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaNetworkProcureModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/network/toaNetworkProcure/manage.action","","/network/toaNetworkProcure/manage.action");
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
				toaNetworkProcureModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaNetworkProcureModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaNetworkProcureModule.clearForm = function(){
	$('#toaNetworkProcureForm')[0].reset();
	hideErrorMessage();
};