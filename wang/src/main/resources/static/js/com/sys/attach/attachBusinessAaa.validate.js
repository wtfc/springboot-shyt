$(document).ready(function(){
	
//初始化校验方法
	$("#attachBusinessForm").validate({
        rules: {
		   attachId: 
		   {
			    required: false,
			    
		   },
		   businessId: 
		   {
			    required: false,
			    
		   },
		   businessTable: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   businessSource: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   extDate1: 
		   {
			    required: false,
			    
		   },
		   extDate2: 
		   {
			    required: false,
			    
		   },
		   extNum1: 
		   {
			    required: false,
			    
		   },
		   extNum2: 
		   {
			    required: false,
			    
		   },
		   extNum3: 
		   {
			    required: false,
			    
		   },
		   extStr1: 
		   {
			    required: false,
			    maxlength: 200
		   },
		   extStr2: 
		   {
			    required: false,
			    maxlength: 200
		   },
		   extStr3: 
		   {
			    required: false,
			    maxlength: 200
		   },
		   extStr4: 
		   {
			    required: false,
			    maxlength: 200
		   },
		   extStr5: 
		   {
			    required: false,
			    maxlength: 200
		   }
	    }
	});



});