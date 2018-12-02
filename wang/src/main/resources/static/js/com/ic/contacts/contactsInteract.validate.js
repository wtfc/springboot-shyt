$(document).ready(function(){
	$.validator.addMethod("phone", function(value) {
		var flag = true;
		if(value!=null&&value!=""){
			var rr = new RegExp(/^[1][3-8]+\d{9}$/);
			code=value.split(",");
			for(var i=0;i<code.length;i++){
				if(!rr.test(code[i])){
					flag =  false;
				}
			}
		}
		return flag;
	}, $.i18n.prop("JC_SYS_022"));
//初始化校验方法
	$("#contactsForm").validate({
		ignore: ".ignore",
        rules: {
          userName: 
		   {
			    required: true,
			    specialChar: true,
			    maxlength: 10,
			    remote:{
                    url: getRootPath()+"/ic/contacts/checkUserName.action",
                    type: "get",
                    async:false,
                    dataType: 'json',
                    data: {
                        'userNameOld': function(){return $("#userNameOld").val();}
                    }
			    }

		   },
		   mail: 
		   {
			    required: true,
			    email:true,
			    maxlength: 30,
			    remote:{
                    url: getRootPath()+"/ic/contacts/checkMail.action",
                    type: "get",
                    async:false,
                    dataType: 'json',
                    data: {
                        'mailOld': function(){return $("#mailOld").val(); },
                        'phone': function(){return $("#contactsForm #phone").val(); },
                        'id': function(){return $("#contactsForm #id").val(); }
                    }
			    }
		   },
		   contactsType: 
		   {
			    required: false,
			    maxlength: 32
		   },
		   simpleName: 
		   {
			    required: false,
			    specialChar: true,
			    maxlength: 10
		   },
		   phone: 
		   {
			    required: true,
			    mobile: true,
			    maxlength: 11,
			    remote:{
                    url: getRootPath()+"/ic/contacts/checkMobile.action",
                    type: "get",
                    async:false,
                    dataType: 'json',
                    data: {
                        'phoneOld': function(){return $("#phoneOld").val();}
                    }
			    }
		   },
		   groupId: 
		   {
			    required: false,
			    maxlength: 64
		   },
		   sex: 
		   {
			    required: false
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
	    	userName: { remote: $.i18n.prop("JC_OA_IC_019")},
	        mail:{ remote: $.i18n.prop("JC_OA_IC_074")},
	    	phone:{remote: $.i18n.prop("JC_SYS_090")}
		}
	});

});