
//初始化方法
var viewModule = {};

//重复提交标识
viewModule.subState = false;

//显示表单弹出层
viewModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	viewModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
viewModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/view/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				viewModule.clearForm();
				$("#viewForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
viewModule.saveOrModify = function (hide) {
	if(viewModule.subState)return;
		viewModule.subState = true;
	if ($("#viewForm").valid()) {
		var url = getRootPath()+"/machine/view/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/view/update.action";
		}
		var formData = $("#viewForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				viewModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//viewModule.clearForm();
					}
					$("#token").val(data.token);
					view.viewList();
				} else {
					if(data.labelErrorMessage){
						showErrors("viewForm", data.labelErrorMessage);
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
				viewModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		viewModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
viewModule.clearForm = function(){
	$('#viewForm')[0].reset();
	hideErrorMessage();
};
$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	viewModule.initInfo();
	$("#saveAndClose").click(function(){viewModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){viewModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});