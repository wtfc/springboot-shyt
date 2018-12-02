$(document).ready(function(){
	
//初始化校验方法
	$("#reqPriceForm").validate({
        rules: {
		   reqId: 
		   {
			    required: false,
			    
		   },
		   itemId: 
		   {
			    required: false,
			    
		   },
		   type: 
		   {
			    required: false,
			    maxlength: 32
		   },
		   vendor: 
		   {
			    required: false,
			    maxlength: 128
		   },
		   linkman: 
		   {
			    required: false,
			    maxlength: 30
		   },
		   phone: 
		   {
			    required: false,
			    maxlength: 30
		   },
		   price: 
		   {
			    required: false,
			    
		   },
		   weight: 
		   {
			    required: false,
			    
		   },
	    }
	});


});