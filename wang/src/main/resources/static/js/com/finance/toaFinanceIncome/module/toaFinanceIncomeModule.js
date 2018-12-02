//初始化方法
var toaFinanceIncomeModule = {};

//重复提交标识
toaFinanceIncomeModule.subState = false;

//显示表单弹出层
toaFinanceIncomeModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaFinanceIncomeModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaFinanceIncomeModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/finance/toaFinanceIncome/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaFinanceIncomeModule.clearForm();
				$("#toaFinanceIncomeModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaFinanceIncomeModule.saveOrModify = function (hide) {
	if(toaFinanceIncomeModule.subState)return;
		toaFinanceIncomeModule.subState = true;
	if ($("#toaFinanceIncomeModuleForm").valid()) {
		var url = getRootPath()+"/finance/toaFinanceIncome/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/finance/toaFinanceIncome/update.action";
		}
		var formData = $("#toaFinanceIncomeModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceIncomeModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaFinanceIncomeModule.clearForm();
					}
					$("#token").val(data.token);
					toaFinanceIncome.toaFinanceIncomeList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaFinanceIncomeModuleForm", data.labelErrorMessage);
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
				toaFinanceIncomeModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceIncomeModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaFinanceIncomeModule.clearForm = function(){
	$('#toaFinanceIncomeModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaFinanceIncomeModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaFinanceIncomeModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});