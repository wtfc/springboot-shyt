//初始化方法
var toaNetworkMachineModule = {};
//重复提交标识
toaNetworkMachineModule.subState = false;
//获取修改信息
toaNetworkMachineModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/network/toaNetworkMachine/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaNetworkMachineModule.clearForm();
				$("#toaNetworkMachineForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaNetworkMachineModule.saveOrModify = function (hide) {
	if(toaNetworkMachineModule.subState)return;
		toaNetworkMachineModule.subState = true;
	if ($("#toaNetworkMachineForm").valid()) {
		var url = getRootPath()+"/network/toaNetworkMachine/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/network/toaNetworkMachine/update.action";
		}
		var formData = $("#toaNetworkMachineForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaNetworkMachineModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/network/toaNetworkMachine/manage.action","","/network/toaNetworkMachine/manage.action");
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
				toaNetworkMachineModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaNetworkMachineModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaNetworkMachineModule.clearForm = function(){
	$('#toaNetworkMachineForm')[0].reset();
	hideErrorMessage();
};