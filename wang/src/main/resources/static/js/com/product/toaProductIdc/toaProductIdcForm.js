//初始化方法
var toaProductIdcModule = {};
//重复提交标识
toaProductIdcModule.subState = false;
//获取修改信息
toaProductIdcModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/product/toaProductIdc/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaProductIdcModule.clearForm();
				$("#toaProductIdcForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaProductIdcModule.saveOrModify = function (hide) {
	if(toaProductIdcModule.subState)return;
		toaProductIdcModule.subState = true;
	if ($("#toaProductIdcForm").valid()) {
		var url = getRootPath()+"/shyt/toaProductIdc/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/toaProductIdc/update.action";
		}
		var formData = $("#toaProductIdcForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaProductIdcModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/product/toaProductIdc/manage.action","","/product/toaProductIdc/manage.action");
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
				toaProductIdcModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaProductIdcModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaProductIdcModule.clearForm = function(){
	$('#toaProductIdcForm')[0].reset();
	hideErrorMessage();
};