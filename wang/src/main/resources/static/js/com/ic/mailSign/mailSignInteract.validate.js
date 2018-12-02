$(document).ready(function(){
	
//初始化校验方法
	$("#mailSignForm").validate({
		ignore: ".ignore",
        rules: {
		   signTitle: 
		   {
			    required: true,
			    maxlength: 12,
			    specialChar: true,
			    remote:{
			        url: getRootPath()+"/ic/mailSign/checkName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'signTitle': function(){return $("#mailSignForm #signTitle").val();},
			            'oldName': function(){return $("#mailSignForm #oldName").val();}
			        }
			    }
		   },
		   signContent: 
		   {
			    required: true
		   }
	    },
		messages: {
			signTitle: {remote: "签名名称已存在"}
		}
	});



});