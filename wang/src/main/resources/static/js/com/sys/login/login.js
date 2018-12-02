function loginFormSubmit(){
	if ($("#loginForm").valid()) {
		if($("#password").val().length == 0){
			$("#loginLable").html("此信息不能为空").show();
			return;
		}
		setCookie();
		$('#loginForm').submit();
	} else {
		if($("#password").val().length == 0){
			$("#loginLable").html("此信息不能为空").show();
			return;
		}
	}
}

function loginValidateRule(){
	$("#loginForm").validate({
        rules: {
           username: 
		   {
			    required: true,
			    username: true
		   },
		   password: 
		   {
			    required: true,
			    minlength: 4,
	 			maxlength: 20,
	 			pwd: true
		   }
	    }
	});
}

function getCookie(){
	var username = $.cookie("username");
	if(username){
		$("#username").val(username);
		$("input[id='Remember']").attr("checked","checked"); 
	}
}

function setCookie(){
	if($("#Remember").is(":checked")){
		$.cookie("username", $("#username").val(), { expires: 30 });
	} else {
		$.cookie("username", '');
	}
}

function loginInit(){
    var $p = $("#password");
    $p.bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
			loginFormSubmit();
        }
    });
    var $u = $("#username");
    $u.bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
			loginFormSubmit();
        }
    });
}


function forgetSubmit(){
	if ($("#forgetPwdForm").valid()) {
		var url = getRootPath()+"/sys/user/forgetPwd.action";
		var formData = $("#forgetPwdForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$('#myModal').modal('hide');
				} else {
					msgBox.info({
						content: data.errorMessage
					});
				}
			},
			error : function() {
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		$("#loginError").html("");
	}
}

function forgetValidateRule(){
	$("#forgetPwdForm").validate({
        rules: {
           loginName: 
		   {
			    required: true
		   },
		   question: 
		   {
			    required: true
		   },
		   answer: 
		   {
			    required: true
		   }
	    }
	});
}

function showForget(){
	forgetFormClear();
	hideErrorMessage();
	$('#myModal').modal('show');
}

function forgetFormClear(){
	$('#forgetPwdForm')[0].reset();
}

function linceseMes(){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/linceseMes.action",
		data : null,
		dataType : "json",
		success : function(data) {
			if (data) {
				if(data.state){
					if($.trim($("#loginError").html()).length == 0){
						$("#loginError").html(data.mes);
					}
				}
			}
		}
	});
}

function isIE(){
    return navigator.appName.indexOf("Microsoft Internet Explorer")!=-1 && document.all;
}

function hideErrorMessage(){
	$("label.error").remove();
	$(".error").removeClass("error");
}

/**
 * 校验是否安装active插件
 */
function validActivex(){
	if(isIE()){
		if(document.all.DWebSignSeal.object == null) {
			gotoDownloadActivexPage(1);
		}else if(document.all.HWPostil.object == null) {
			gotoDownloadActivexPage(2);
		}else if(document.all.WebOffice.object == null) {
			gotoDownloadActivexPage(3);
		}
	}
}

function gotoDownloadActivexPage(a){
	//alert("未安装插件")
}

$(function () {
	loginValidateRule();
	getCookie();
	loginInit();
	forgetValidateRule();
	$("#showForget").click(showForget);
	linceseMes();
	
	ie8StylePatch();
	iePlaceholderPatch();
});

