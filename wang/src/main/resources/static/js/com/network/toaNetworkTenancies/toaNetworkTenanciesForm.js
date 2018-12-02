//初始化方法
var toaNetworkTenanciesModule = {};
//重复提交标识
toaNetworkTenanciesModule.subState = false;
//获取修改信息
toaNetworkTenanciesModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/network/toaNetworkTenancies/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaNetworkTenanciesModule.clearForm();
				$("#toaNetworkTenanciesForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaNetworkTenanciesModule.saveOrModify = function (hide) {
	if(toaNetworkTenanciesModule.subState)return;
		toaNetworkTenanciesModule.subState = true;
	if ($("#toaNetworkTenanciesForm").valid()) {
		var url = getRootPath()+"/network/toaNetworkTenancies/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/network/toaNetworkTenancies/update.action";
		}
		var formData = $("#toaNetworkTenanciesForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaNetworkTenanciesModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/network/toaNetworkTenancies/manage.action","","/network/toaNetworkTenancies/manage.action");
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
				toaNetworkTenanciesModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaNetworkTenanciesModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaNetworkTenanciesModule.clearForm = function(){
	$('#toaNetworkTenanciesForm')[0].reset();
	hideErrorMessage();
};