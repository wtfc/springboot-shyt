$(document).ready(function(){
	
//初始化校验方法
	$("#smsStatisticForm").validate({
        rules: {
		   userId: 
		   {
			    required: false,
			    
		   },
		   statisticsMonth: 
		   {
			    required: false,
			    
		   },
		   sendNum: 
		   {
			    required: false,
			    
		   },
		   residueNum: 
		   {
			    required: false,
			    
		   }
	    }
	});



});