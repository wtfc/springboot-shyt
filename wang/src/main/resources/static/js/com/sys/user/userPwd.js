var userPwd = {};

//重复提交标识
userPwd.subState = false;

userPwd.modify = function(){
	if(userPwd.subState)return;
	userPwd.subState = true;
	if ($("#userPwdForm").valid()) {
		var url = getRootPath()+"/sys/user/userPwdModify.action";
		var formData = $("#userPwdForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : serializeJson(formData),
			success : function(data) {
				userPwd.subState = false;
				$("#token").val(data.token);
				if(data.success == "true"){
					$("#password").val("");
					$("#newPassword").val("");
					$("#confirmPassword").val("");
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					//隐藏强制修改密码对话框
					$('#dPasswordModal').modal('hide');
				} else {
					if(data.labelErrorMessage){
						showErrors("userPwdForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				user.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		userPwd.subState = false;
	}
};

//初始化
jQuery(function($) 
{
	$("#saveBtn").click(userPwd.modify);
});
