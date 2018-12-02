//初始化方法
var toaFinanceSplitModule = {};
//重复提交标识
toaFinanceSplitModule.subState = false;
//获取修改信息
toaFinanceSplitModule.get = function (id) {
	$//.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/toaFinanceSplit/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaFinanceSplitModule.clearForm();
				$("#toaFinanceSplit#orm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaFinanceSplitModule.saveOrModify = function (hide) {
	if(toaFinanceSplitModule.subState)return;
		toaFinanceSplitModule.subState = true;
	if ($("#toaFinanceSplit#orm").valid()) {
		var url = getRootPath()+"/shyt/toaFinanceSplit/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/toaFinanceSplit/update.action";
		}
		var formData = $("#toaFinanceSplit#orm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceSplitModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/toaFinanceSplit/manage.action","","/shyt/toaFinanceSplit/manage.action");
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
				toaFinanceSplitModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $//.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceSplitModule.subState = false;
		msgBox.info({
			content:$//.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaFinanceSplitModule.clearForm = function(){
	$('#toaFinanceSplit#orm')[0].reset();
	hideErrorMessage();
};