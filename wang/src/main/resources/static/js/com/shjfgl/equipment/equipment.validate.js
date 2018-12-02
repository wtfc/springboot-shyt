$(document).ready(function(){
	
	function test(value){
		if(value == 0)
			return false;
		return /^\d{1,6}$/.test(value);
	}
	
//初始化校验方法
	$("#equipmentForm").validate({
		ignore: ".ignore",
        rules: {
        	clientName: 
		   {
			    required: true,
			    maxlength: 20,
			    minlength: 2,
			    specialChar: true
		   },
		   type: 
		   {
			    required: true,
			    maxlength: 20,
			    specialChar: true,
		   },
		   /*onlineDate: 
		   {
			    required: true
		   },*/
		   machina: 
		   {
			   required: true,
			   maxlength: 20,
		   },
		   machinaNumber: 
		   {
			   required: true,
			   maxlength: 20,
		   },
		   
		   address: 
		   {
			   required: true,
			   maxlength: 20,
		   },
		   netmaskOne: 
		   {
			   required: false,
			    maxlength: 20
		   },
		   interchangerOne: 
		   {
			   required: false,
			    maxlength: 20
		   },
		   netmaskTwo: 
		   {
			   required: false,
			    maxlength: 20
		   },
		   interchangerTwo: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   ip: 
		   {
			    required: false,
		   },
		   interchangerThree: 
		   {
			    required: false,
			    maxlength: 64,
			    specialChar: true
		   },
		   uCount: 
		   {
			    required: false,
			    isPositive: true
		   },
		   power: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   plantPower: 
		   {
			    required: false,
			    maxlength: 64,
			    specialChar: true
		   },
		   functionn: 
		   {
			    required: false,
			   // creditcard: true,
			    maxlength: 20
		   },
		   port: 
		   {
			    required: false,
			    maxlength: 100,
			    specialChar: true
		   },
		   aCurrent: 
		   {
			    required: false,
			    maxlength: 20,
			    specialChar: true
		   },
		   
		   bCurrent: 
		   {
			    required: false,
			    maxlength: 100,
			    specialChar: true
		   },
		   
		   system: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   serialNumber: 
		   {
			    required: false,
			    maxlength: 100,
			    specialChar: true
		   },
		   sn: 
		   {
			    required: false,
		   },
		   device: 
		   {
			    required: false,
			    maxlength: 64,
			    specialChar: true
		   },
		   contact: 
		   {
			    required: true,
		   }
	    },	
	    messages: {
			loginName: {remote: $.i18n.prop("JC_SYS_089")},
			adminSideV: {required: $.i18n.prop("JC_SYS_105")},
			officeTel: {simplePhone: $.i18n.prop("JC_SYS_106")},
			groupTel: {number: $.i18n.prop("JC_SYS_127")},
			payCardNo: {creditcard: $.i18n.prop("JC_SYS_107")},
			mobile:{remote: $.i18n.prop("JC_SYS_090")}
		}
	});

});