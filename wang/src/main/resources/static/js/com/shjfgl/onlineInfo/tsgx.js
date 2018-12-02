
//初始化方法
var equipmentInOutModule = {};

//重复提交标识
equipmentInOutModule.subState = false;

//显示表单弹出层
equipmentInOutModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentInOutModule.clearForm();
	$('#myModal-edit').modal('show');
};

//添加修改公用方法
equipmentInOutModule.saveOrModify = function (hide,names) {
	var vall = names;
	if(equipmentInOutModule.subState)return;
		equipmentInOutModule.subState = true;
	if ($("#tsgx").valid()) {
		var url = getRootPath()+"/information/people/save.action";
		var formData = $("#tsgx").serializeArray();
		formData.push({"name": "name", "value": vall});
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentInOutModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
						loadrightmenu("/machine/onlineInfo/tsgx.action","","/machine/onlineInfo/tsgx.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentInOutForm", data.labelErrorMessage);
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
				equipmentInOutModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentInOutModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
$(document).ready(function(){
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
});