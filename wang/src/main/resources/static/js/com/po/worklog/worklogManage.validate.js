$(document).ready(function(){
//初始化校验方法
	$("#leaderIdeaForm").validate({
        rules: {
        	content: 
		   {
        		required: true,
			    maxlength: 2000
		   }
	    }
	});
	$("#commentForm").validate({
		rules: {
			replyContent: 
			{
				required: true,
				maxlength: 2000
			}
		}
	});
});