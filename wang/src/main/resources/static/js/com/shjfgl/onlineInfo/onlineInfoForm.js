
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

//获取修改信息
equipmentInOutModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/onlineInfo/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentInOutModule.clearForm();
				$("#equipmentInOutForm").fill(data);
			}
		}
	});
};

//添加修改公用方法
equipmentInOutModule.saveOrModify = function (hide) {
	if(equipmentInOutModule.subState)return;
		equipmentInOutModule.subState = true;
	if ($("#equipmentInOutForm").valid()) {
		var url = getRootPath()+"/machine/onlineInfo/save.action";
		var formData = $("#equipmentInOutForm").serializeArray();
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
						loadrightmenu("/machine/onlineInfo/viewList.action","","/machine/onlineInfo/viewList.action");
						}
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						equipmentInOutModule.clearForm();
					}
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
//清空表单数据
equipmentInOutModule.clearForm = function(){
	$('#equipmentInOutForm')[0].reset();
	hideErrorMessage();
};
$(document).ready(function(){
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
});