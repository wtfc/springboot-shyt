var userInfo = {};

//重复提交标识
userInfo.subState = false;

//获取修改信息
userInfo.get = function () {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/sys/user/getUserInfo.action",
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#userInfoForm").fill(data.user);
				if(data.user.photo){
					$("#userPhoto").attr("src", getRootPath()+"/"+data.user.photo);
				} else {
					$("#userPhoto").attr("src", getRootPath()+"/images/demoimg/iphoto.jpg");
				}
				$("#token").val(data.token);
			}
		}
	});
};

//添加修改公用方法
userInfo.modify = function () {
	if(userInfo.subState)return;
	userInfo.subState = true;
	if ($("#userInfoForm").valid()) {
		var url = getRootPath()+"/sys/user/userInfoUpdate.action";
		var formData = $("#userInfoForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : serializeJson(formData),
			success : function(data) {
				userInfo.subState = false;
				//getToken();
				$("#token").val(data.token);
				if(data.success == "true"){
					$("#userInfoForm #modifyDate").val(data.modifyDate);
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
				} else {
					if(data.labelErrorMessage){
						showErrors("userInfoForm", data.labelErrorMessage);
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
		userInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//获得已上传的附件
userInfo.getUserPhoto = function(){
	$('#fileupload table tbody').empty();
    $('#fileupload').addClass('fileupload-processing');
    $.ajax({
        url: getRootPath()+"/content/attach/userPhotoList.action",
        data : {"id": $("#id").val(), fileFolderName: $("#photo").val()},
        dataType: 'json',
        context: $('#fileupload')[0]
    }).always(function () {
        $(this).removeClass('fileupload-processing');
    }).done(function (result) {
        $(this).fileupload('option', 'done')
            .call(this, $.Event('done'), {result: result});
    });
    $('#myModal2').modal('show');
};

//初始化
jQuery(function($)
{
	$("#saveBtn").click(userInfo.modify);
	$("#uploadPhoto").click(userInfo.getUserPhoto);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	//getToken();
	userInfo.get();
});
