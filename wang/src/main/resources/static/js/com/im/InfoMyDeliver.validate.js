$(document).ready(function(){
	
//初始化校验方法
	$("#infoForm").validate({
        rules: {
		   columnId: 
		   {
			    required: false,
			    
		   },
		   piId: 
		   {
			    required: false,
			    maxlength: 64
		   },
		   portalPic: 
		   {
			    required: false,
			    
		   },
		   infoTitile: 
		   {
			    required: false,
			    maxlength: 1000
		   },
		   infoSubtitle: 
		   {
			    required: false,
			    maxlength: 1000
		   },
		   docuCode: 
		   {
			    required: false,
			    maxlength: 1024
		   },
		   keyword: 
		   {
			    required: false,
			    maxlength: 1024
		   },
		   author: 
		   {
			    required: false,
			    maxlength: 255
		   },
		   authorUnit: 
		   {
			    required: false,
			    maxlength: 1024
		   },
		   sendId: 
		   {
			    required: false,
			    
		   },
		   sendDepid: 
		   {
			    required: false,
			    
		   },
		   sendTime: 
		   {
			    required: false,
			    
		   },
		   effectiveTime: 
		   {
			    required: false,
			    
		   },
		   infoSumm: 
		   {
			    required: false,
			    maxlength: 2000
		   },
		   allowReview: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   allowPrint: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   isTop: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   weight: 
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