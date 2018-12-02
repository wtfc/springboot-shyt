$(document).ready(function(){
	
//初始化校验方法
	$("#suggestForm").validate({
		ignore: ".ignore",
        rules: {
		   suggestTypeId: 
		   {
			    required: true
			    
		   },
		   suggestTitle: 
		   {
			    required: true,
			    maxlength: 30,
			    specialChar: true
			   /* remote:{
			        url: getRootPath()+"/ic/suggest/checkName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'suggestTitle': function(){return $("#suggestForm #suggestTitle").val();},
			            'oldName': function(){return $("#suggestForm #oldName").val();}
			        }
			    }*/
		   },
		   suggestContent: 
		   {
			    required: false,
			    maxlength: 300,
			    specialChar: true
		   },
		   recipientIds: 
		   {
			    required: true
		   },
		   suggestWay: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   singleShow: 
		   {
			    required: false,
			    maxlength: 2
		   },
		   repStatus: 
		   {
			    required: false,
			    maxlength: 1
		   }
		  
	    },
		messages: {
			suggestTitle: {remote: "建议标题已存在"}
		}
	});
	$("#replyForm").validate({
		ignore: ".ignore",
        rules: {
        	repContent: 
		   {
			    required: true,
			    maxlength: 300,
			    specialChar: true
		   }
	    }
	});



});