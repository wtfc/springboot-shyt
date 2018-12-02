//初始化方法
var toaShytTestresultModule = {};
//重复提交标识
toaShytTestresultModule.subState = false;
//获取修改信息
toaShytTestresultModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/toaShytTestresult/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaShytTestresultModule.clearForm();
				$("#toaShytTestresultForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaShytTestresultModule.saveOrModify = function (hide) {
	if(toaShytTestresultModule.subState)return;
		toaShytTestresultModule.subState = true;
	if ($("#toaShytTestresultForm").valid()) {
		var url = getRootPath()+"/shyt/toaShytTestresult/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/toaShytTestresult/update.action";
		}
		var formData = $("#toaShytTestresultForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaShytTestresultModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/toaShytTestresult/manage.action","","/shyt/toaShytTestresult/manage.action");
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
				toaShytTestresultModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaShytTestresultModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaShytTestresultModule.clearForm = function(){
	$('#toaShytTestresultForm')[0].reset();
	hideErrorMessage();
};