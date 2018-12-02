
$(document).ready(function(){
	
//初始化校验方法
	$("#mailboxForm").validate({
		ignore: ".ignore",
		rules: {
		   address: 
		   {
			    required: true,
			    maxlength: 40,
			    email: true
		   },
		   receiveService: 
		   {
			    required: true,
			    maxlength: 100
		   },
		   receivePort: 
		   {
			    required: false,
			    maxlength: 10,
			    number: true,
			    digits:true
		   },
		   senderService: 
		   {
			    required: true,
			    maxlength: 100
		   },
		   senderPort: 
		   {
			    required: false,
			    maxlength: 10,
			    number: true,
			    digits:true
		   },
		   username: 
		   {
			    required: true,
			    maxlength: 40,
			    remote:{
			        url: getRootPath()+"/ic/mailbox/checkName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'username': function(){return $("#mailboxForm #username").val();},
			            'oldName': function(){return $("#mailboxForm #oldName").val();}
			        }
			    }
		   },
		   mailPassword: 
		   {
			    required: true,
			    maxlength: 20
		   }
		   
	    },
		messages: {
			username: {remote: "账户名称已存在"}
		}
	});



});