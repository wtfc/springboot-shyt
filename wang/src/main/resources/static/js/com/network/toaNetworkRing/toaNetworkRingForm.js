//初始化方法
var toaNetworkRingModule = {};
//重复提交标识
toaNetworkRingModule.subState = false;
//获取修改信息
toaNetworkRingModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/network/toaNetworkRing/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaNetworkRingModule.clearForm();
				$("#toaNetworkRingForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaNetworkRingModule.saveOrModify = function (hide) {
	if(toaNetworkRingModule.subState)return;
		toaNetworkRingModule.subState = true;
	if ($("#toaNetworkRingForm").valid()) {
		var url = getRootPath()+"/network/toaNetworkRing/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/network/toaNetworkRing/update.action";
		}
		var formData = $("#toaNetworkRingForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaNetworkRingModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/network/toaNetworkRing/manage.action","","/network/toaNetworkRing/manage.action");
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
				toaNetworkRingModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaNetworkRingModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaNetworkRingModule.clearForm = function(){
	$('#toaNetworkRingForm')[0].reset();
	hideErrorMessage();
};