var duressPassword = {};

duressPassword.isFirstLogin = function (){
	jQuery.ajax({
		url : getRootPath()+"/sys/user/isFirstLogin.action",
		type : 'GET',
		success : function(data) {
			if(data){
				$("#duressPasswordDiv").load(getRootPath()+"/sys/user/duressPassword.action",null,function(){duressPassword.showPasswordModal();});
			}
		}
	});
};

duressPassword.showPasswordModal = function () {
	$('#dPasswordModal').modal('show');
};

//初始化
jQuery(function($) 
{
	duressPassword.isFirstLogin();
});