$(document).ready(function(){
	
	 
	/*
	 * var username = $("#username").val();

if (!username.match( /^[\u4E00-\u9FA5a-zA-Z0-9_]{3,20}$/)) {
var msg = "汉字 英文字母 数字 下划线组成，3-20位";
$("#usertips").html(msg);
} else {
 $("#usertips").html('');
}
*/
//初始化校验方法
	$("#sendWaitTransactListForm").validate({
		
		ignore: ".ignore",
        rules: {
			title: 
			{
				maxlength: 100,
				fileName:true
			},
			noValue: 
			{
				maxlength: 100,
				fileName:true
			}
	    }
	});
});