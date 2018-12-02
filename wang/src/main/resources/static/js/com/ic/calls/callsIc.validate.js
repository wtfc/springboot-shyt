$(document).ready(function(){
	
//初始化校验方法
	$("#callsForm").validate({
        rules: {
		   callDate: 
		   {
			    required: false,
			    
		   },
		   gatewayId: 
		   {
			    required: false,
			    maxlength: 64
		   },
		   callerId: 
		   {
			    required: false,
			    
		   },
		   deleteFlag: 
		   {
			    required: false,
			    
		   },
		   createUser: 
		   {
			    required: false,
			    
		   },
		   createUserDept: 
		   {
			    required: false,
			    
		   },
		   createUserOrg: 
		   {
			    required: false,
			    
		   },
		   createDate: 
		   {
			    required: false,
			    
		   },
		   modifyUser: 
		   {
			    required: false,
			    
		   },
		   modifyDate: 
		   {
			    required: false,
			    
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