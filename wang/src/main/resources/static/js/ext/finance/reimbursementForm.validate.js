$(document).ready(function(){
	
//初始化校验方法
	$("#reimbursementForm").validate({
        rules: {
		   rePeo: 
		   {
			    required: false,
			    
		   },
		   reDept: 
		   {
			    required: false,
			    
		   },
		   reDate: 
		   {
			    required: true,
			    
		   },
		   project: 
		   {
			    required: false,
			    maxlength: 255
		   },
		   gradeSum: 
		   {
			    required: false,
			    maxlength: 1000
		   },
		   reSunMoney: 
		   {
			    required: false,
			    
		   },
		   reSunMoneyBig: 
		   {
			    required: false,
			    maxlength: 255
		   },
		   reTeller: 
		   {
			    required: false,
			    
		   },
		   reHandle: 
		   {
			    required: false,
			    
		   },
		   reDraw: 
		   {
			    required: false,
			    
		   },
		   reStatus: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   reNumId: 
		   {
			    required: false,
			    
		   },
		   reNum: 
		   {
			    required: false,
			    maxlength: 255
		   },
		   flowId: 
		   {
			    required: false,
			    maxlength: 64
		   },
		   flowName: 
		   {
			    required: false,
			    maxlength: 255
		   },
		   piId: 
		   {
			    required: false,
			    maxlength: 64
		   },
		   status: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   weight: 
		   {
			    required: false,
			    
		   },
		   queue: 
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
		   },
		   money1: 
		   {
			   required: false,
			   number: true,
			   maxlength: 8
		   },
		   money2: 
		   {
			   required: false,
			   number: true,
			   maxlength: 8
		   },
		   money3: 
		   {
			   required: false,
			   number: true,
			   maxlength: 8
		   },
		   money4: 
		   {
			   required: false,
			   number: true,
			   maxlength: 8
		   },
		   money5: 
		   {
			   required: false,
			   number: true,
			   maxlength: 8
		   }
	    }
	});



});