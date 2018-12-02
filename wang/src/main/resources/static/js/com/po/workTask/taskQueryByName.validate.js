$(document).ready(function(){
	//任务汇报校验方法
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
	//延期校验方法
	$("#delayForm").validate({
        rules: {
        	delayDays: 
		   {
			    required: true,
			    maxlength: 4,
			    min: 1,
			    digits:true
		   },
		   delayReason: 
		   {
			    required: true,
			    maxlength: 100,
			    minlength: 1,
			    specialChar: true
		   }	   
	    },
	    messages: {
	    	delayDays: {
	    		min:"请输入大于0的整数"
	    	   }
		}
	});
	//催办校验方法
	$("#remindersForm").validate({
        rules: {
        	delayReason: 
		   {
			    required: true,
			    maxlength: 100,
			    minlength: 1,
			    specialChar: true
		   }	   
	    }
	});
	//取消校验方法
	$("#cancelForm").validate({
        rules: {
        	delayReason: 
		   {
			    required: true,
			    maxlength: 100,
			    minlength: 1,
			    specialChar: true
		   }	   
	    }
	});
	//接收任务校验方法
	$("#receWorkTaskForm").validate({
        rules: {
        	actStartTime: 
		   {
			    required: true
		   }	   
	    }
	});
	//不接收任务校验方法
	$("#notRrce").validate({
        rules: {
        	delayReason: 
		   {
			    required: true,
			    maxlength: 100,
			    minlength: 1,
			    specialChar: true
		   }	   
	    }
	});
});