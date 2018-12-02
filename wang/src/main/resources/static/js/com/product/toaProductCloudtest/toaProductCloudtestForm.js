//初始化方法
var toaProductCloudtestModule = {};
//重复提交标识
toaProductCloudtestModule.subState = false;
//获取修改信息
toaProductCloudtestModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/product/toaProductCloudtest/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaProductCloudtestModule.clearForm();
				$("#toaProductCloudtestForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaProductCloudtestModule.saveOrModify = function (hide) {
	if(toaProductCloudtestModule.subState)return;
		toaProductCloudtestModule.subState = true;
	if ($("#toaProductCloudtestForm").valid()) {
		var url = getRootPath()+"/product/toaProductCloudtest/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/product/toaProductCloudtest/update.action";
		}
		var formData = $("#toaProductCloudtestForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaProductCloudtestModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/product/toaProductCloudtest/manage.action","","/product/toaProductCloudtest/manage.action");
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
				toaProductCloudtestModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaProductCloudtestModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaProductCloudtestModule.clearForm = function(){
	$('#toaProductCloudtestForm')[0].reset();
	hideErrorMessage();
};