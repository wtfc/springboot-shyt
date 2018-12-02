$(document).ready(function(){
	//绑定人员选择树的class
	$.validator.addMethod("input-medium", function(value,element) {
		return this.optional(element) || /^[a-zA-Z0-9][a-zA-Z0-9]{0,7}$/.test(value);
	}, $.i18n.prop("1-8位，允许字母数字"));
	
	//初始化校验方法
	$("#mailForm").validate({
		ignore: ".ignore",
        rules: {
        	confirmPass: 
		   {
        		specialChar:true,
        		equalTo:'#encryptionPass',
			    maxlength: 8
		   },
		   encryptionPass:
		   {
			   specialChar:true,
			   required:function(element) {
				   if($("input[name='encryption']:checked").val() == 1){
					   if($.hasSpecialChar($('#encryptionPass').val())){
						   return true;
					   }
					   if($('#encryptionPass').val().trim() == ""){
						   return true;
					   }
				   }
				   return false;
			   },
			   maxlength: 8
		   },
		   replyTextingTime:{
			   min:function(element) {
				   if($("input[name='replyTexting']:checked").val() == 1){
					  return $('#replyTextingTime').val()<1;
				   }
				   return false;
			   }
		   },
		   innerTo:
			{
			   required:function(element) {
			        return $("#mailboxId").val() == 1 && $('#innerCc-innerCc').val()=="" && $('#innerBcc-innerBcc').val()=="" && $('#innerSs-innerSs').val()=="";
			   }
			},
			toValid:
			{
			   required:function(element){
				   return $("#mailboxId").val()>1 && $('#showSingleValid').val()=="";
			   }
			},
			
			innerSs:
			{
				   required:function(element) {
				        return $("#mailboxId").val() == 1 && $('#innerCc-innerCc').val()=="" && $('#innerBcc-innerBcc').val()=="" && $('#innerTo-innerTo').val()=="";
				 }
			},
			showSingleValid:
				{
				   required:function(element){
					   return $("#mailboxId").val()>1 && $('#toValid').val()=="";
				   }
			},
			mailTitle:{
				 maxlength: 80
			}
			   
	    },	
	    messages: {
	    	confirmPass: {equalTo: $.i18n.prop("JC_OA_IC_020")},
	    	replyTextingTime: {min: $.i18n.prop("JC_SYS_010")}
		}
	});
});

//@ sourceURL=mail.validate.js