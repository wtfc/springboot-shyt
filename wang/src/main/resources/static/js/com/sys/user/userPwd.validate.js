$(document).ready(function(){

//初始化校验方法
	$("#userPwdForm").validate({
        rules: {
           password: 
 		   {
 			   required: true,
 			   minlength: 4,
 			   maxlength: 20,
 			   pwd: true,
 			   remote:{
                  url: getRootPath()+"/sys/user/checkUserPwd.action",
                  type: "get",
                  dataType: 'json',
                  data: {
                      'password': function(){return $("#password").val();}
                  }
			    }
 		   },
 		   newPassword: 
		   {
			   required: true,
			   minlength: 4,
			   maxlength: 20,
			   noEqualTo: "#password",
			   pwd: true
		   },
		   confirmPassword: 
 		   {
 			   required: true,
 			   minlength: 4,
 			   maxlength: 20,
 			   equalTo: "#newPassword",
 			   pwd: true
 		   },
	    },
		messages: {
			confirmPassword: {equalTo: $.i18n.prop("JC_SYS_021")},
			newPassword: {noEqualTo: "不能输入和旧密码相同新的密码"},
			password: {remote: "旧密码输入错误"}
		}
	});

});