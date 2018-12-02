//初始化方法
var toaFinanceInvoicesModule = {};
//重复提交标识
toaFinanceInvoicesModule.subState = false;

//获取修改信息
toaFinanceInvoicesModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/finance/toaFinanceInvoices/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaFinanceInvoicesModule.clearForm();
				$("#toaFinanceInvoicesForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaFinanceInvoicesModule.saveOrModify = function (hide) {
	if(toaFinanceInvoicesModule.subState)return;
		toaFinanceInvoicesModule.subState = true;
	if ($("#toaFinanceInvoicesForm").valid()) {
		var url = getRootPath()+"/finance/toaFinanceInvoices/update.action";
		var formData = $("#toaFinanceInvoicesForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceInvoicesModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/finance/toaFinanceInvoices/manage.action","","/finance/toaFinanceInvoices/manage.action");
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
				toaFinanceInvoicesModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceInvoicesModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
toaFinanceInvoicesModule.clearForm = function(){
	$('#toaFinanceInvoicesForm')[0].reset();
	hideErrorMessage();
};
function checkInt(){
	var money = $("#monthAmount").val();
	var fapiaoMoney = $("#invoicesAccount").val();
	$("#noinvoicesAccount").val(parseInt(money)-parseInt(fapiaoMoney));
}
function checkP(){
	var money = $("#monthAmount").val();
	var receiveMoney = $("#receivedAccount").val();
	$("#arrearage").val(parseInt(money)-parseInt(receiveMoney));
}
