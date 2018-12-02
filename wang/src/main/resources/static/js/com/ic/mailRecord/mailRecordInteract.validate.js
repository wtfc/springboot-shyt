$(document).ready(function(){
	
//初始化校验方法
	$("#mailRecordForm").validate({
        rules: {
		   mailId: 
		   {
			    required: false,
			    
		   },
		   receiveUserId: 
		   {
			    required: false,
			    
		   },
		   receiveMail: 
		   {
			    required: false,
			    maxlength: 128
		   },
		   receiveType: 
		   {
			    required: false,
			    
		   },
		   deleteFlag: 
		   {
			    required: false,
			    
		   },
		   deleteUser: 
		   {
			    required: false,
			    
		   },
		   deleteDate: 
		   {
			    required: true,
			    
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