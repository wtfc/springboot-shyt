$(document).ready(function(){
//初始化校验方法
	$("#worklogCalendarForm").validate({
        rules: {
		   title: 
		   {
			    required: true,
			    maxlength: 255
		   },
		   worklogDate: 
		   {
			    required: true,
		   },
		   sentimentRemark: 
		   {
			   maxlength: 500
		   },
		   todayLogInput: 
		   {
			   maxlength: 500
		   },
		   tomorrowRemindInput:
		   {
			   maxlength: 500
		   }
	    }
	});
	$("#reportForm").validate({
        rules: {
        	reportProc: 
		   {
        		required: true,
 			    range:[1,100],
 			    digits:true
		   },
		   reportContent: 
		   {
			    required: true,
			    maxlength: 100,
			    minlength: 1,
			    specialChar: true
		   }
	    },
	    messages: {
	    	reportProc: {
	    		range: "请输入大于0且小于等于100的整数",
	    		digits:"请输入整数"
	    	   }
		}
	});
	$("#commentForm").validate({
		rules: {
			replyContent: 
			{
//				required: true,
//				maxlength: 2000
				 required: true,
				 maxlength: 150,
				 specialChar: true
			}
		}
	});
	//初始化校验方法  保存
	$("#leaderIdeaForm").validate({
		 rules: {
			 content: 
			   {
				    required: true,
				    maxlength: 150,
				    specialChar: true
			   }
		 }
	});	
});