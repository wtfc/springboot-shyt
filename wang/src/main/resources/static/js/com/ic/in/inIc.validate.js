$(document).ready(function(){
	
//初始化校验方法
	$("#inForm").validate({
        rules: {
		   process: 
		   {
			    required: false,
			    
		   },
		   originator: 
		   {
			    required: false,
			    maxlength: 16
		   },
		   etype: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   encoding: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   messageDate: 
		   {
			    required: false,
			    
		   },
		   receiveDate: 
		   {
			    required: false,
			    
		   },
		   text: 
		   {
			    required: false,
			    maxlength: 1000
		   },
		   originalRefNo: 
		   {
			    required: false,
			    maxlength: 64
		   },
		   originalReceiveDate: 
		   {
			    required: false,
			    
		   },
		   gatewayId: 
		   {
			    required: false,
			    maxlength: 64
		   },
		   state: 
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