$(document).ready(function(){
	
//初始化校验方法
	$("#reviewForm").validate({
        rules: {
		   infoId: 
		   {
			    required: false,
			    
		   },
		   reviewContent: 
		   {
			    required: true,
			    maxlength: 200
		   }
	    }
	});
});