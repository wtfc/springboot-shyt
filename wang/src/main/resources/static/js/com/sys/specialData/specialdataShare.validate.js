$(document).ready(function(){
	
//初始化校验方法
	$("#specialdataShareForm").validate({
        rules: {
		   specialdataId: 
		   {
			    required: false,
			    
		   },
		   shareDept: 
		   {
			    required: false,
			    
		   },
		   shareUsers: 
		   {
			    required: false,
			    
		   },
		   shareOrg: 
		   {
			    required: false,
			    
		   },
	    }
	});


});