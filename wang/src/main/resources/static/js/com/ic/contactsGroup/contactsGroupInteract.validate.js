$(document).ready(function(){
	
//初始化校验方法
	$("#contactsGroupsForm").validate({
        rules: {
		   groupName: 
		   {
			    required: true,
			    specialChar: true,
			    maxlength: 10,
			    remote:{
                    url: getRootPath()+"/ic/contactsGroup/checkGroupName.action",
                    type: "get",
                    dataType: 'json',
                    data: {
                        'groupNameOld': function(){return $("#groupNameOld").val();}
                    }
			    }
		   },
		   description: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   deleteFlag: 
		   {
			    required: false
			    
		   },
		   createUser: 
		   {
			    required: false
			    
		   },
		   createUserDept: 
		   {
			    required: false
			    
		   },
		   createUserOrg: 
		   {
			    required: false
			    
		   },
		   createDate: 
		   {
			    required: false
			    
		   },
		   modifyUser: 
		   {
			    required: false
			    
		   },
		   modifyDate: 
		   {
			    required: false
			    
		   },
		   extDate1: 
		   {
			    required: false
			    
		   },
		   extDate2: 
		   {
			    required: false
			    
		   },
		   extNum1: 
		   {
			    required: false
			    
		   },
		   extNum2: 
		   {
			    required: false
			    
		   },
		   extNum3: 
		   {
			    required: false
			    
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
	    },
	    messages: {
	    	groupName: {remote: $.i18n.prop("JC_OA_IC_018")}
		}
	});



});