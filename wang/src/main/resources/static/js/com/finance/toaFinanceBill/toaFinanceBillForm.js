//初始化方法
var toaFinanceBillModule = {};
//重复提交标识
toaFinanceBillModule.subState = false;

//获取修改信息
toaFinanceBillModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/finance/toaFinanceBill/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaFinanceBillModule.clearForm();
				$("#toaFinanceBillForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaFinanceBillModule.saveOrModify = function (hide) {
	if(toaFinanceBillModule.subState)return;
		toaFinanceBillModule.subState = true;
	if ($("#toaFinanceBillForm").valid()) {
		var url = getRootPath()+"/finance/toaFinanceBill/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/finance/toaFinanceBill/update.action";
		}
		var formData = $("#toaFinanceBillForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceBillModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/finance/toaFinanceBill/stateList.action","","/finance/toaFinanceBill/stateList.action");
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
				toaFinanceBillModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceBillModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//预审
toaFinanceBillModule.saveModify = function (hide) {
	if(toaFinanceBillModule.subState)return;
		toaFinanceBillModule.subState = true;
	if ($("#toaFinanceBillForm").valid()) {
		if ($("#id").val() != 0) {
			url = getRootPath()+"/finance/toaFinanceBill/updateForm.action";
		}
		var formData = $("#toaFinanceBillForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceBillModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/finance/toaFinanceBill/stateList.action","","/finance/toaFinanceBill/stateList.action");
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
				toaFinanceBillModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceBillModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//账单退回
toaFinanceBillModule.falseModify = function (hide) {
	if(toaFinanceBillModule.subState)return;
		toaFinanceBillModule.subState = true;
	if ($("#toaFinanceBillForm").valid()) {
		var url = getRootPath()+"/finance/toaFinanceBill/updateFalse.action";
		var formData = $("#toaFinanceBillForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceBillModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/finance/toaFinanceBill/stateList.action","","/finance/toaFinanceBill/stateList.action");
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
				toaFinanceBillModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceBillModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
toaFinanceBillModule.loadModule = function (id){
		$("#formModuleDiv").load(getRootPath()+"/finance/toaFinanceInvoices/invocesLoadForm.action",null,function(){
			toaFinanceInvoicesModule.show(id);
		});
};
//清空表单数据
toaFinanceBillModule.clearForm = function(){
	
	$('#toaFinanceBillForm')[0].reset();
	hideErrorMessage();
};
