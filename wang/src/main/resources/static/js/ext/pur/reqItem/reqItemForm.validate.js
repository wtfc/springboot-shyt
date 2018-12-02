$(document).ready(function(){
	
//初始化校验方法
	$("#reqItemForm").validate({
        rules: {
		   reqId: 
		   {
			    required: false,
			    
		   },
		   itemName: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   brand: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   model: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   unit: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   quantity: 
		   {
			    required: false,
			    
		   },
		   use: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   remark: 
		   {
			    required: false,
			    maxlength: 256
		   },
		   finalVendor: 
		   {
			    required: false,
			    maxlength: 128
		   },
		   finalPrice: 
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