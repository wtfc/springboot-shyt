$(document).ready(function(){
	
//初始化校验方法
	$("#insideInReplyForm").validate({
		ignore: ".ignore",
        rules: {
           replayContent: 
 		   {
 			    required: true,
			    maxlength: 200
 		   }
	    }
	});
});