$(document).ready(function(){
	
	function test(value){
		if(value == 0)
			return false;
		return /^\d{1,6}$/.test(value);
	}
	
//初始化校验方法
	$("#equipmentInOutForm").validate({
		ignore: ".ignore",
        rules: {
        	companyName: 
		   {
			    required: true,
			    maxlength: 20,
			    specialChar: true
		   },
		   machina: 
		   {
			    required: true,
		   },
		   origin: 
		   {
			    required: true
		   },
		   type: 
		   {
			   required: true,
		   },
		   contact: 
		   {
			   required: false,
			   maxlength: 20,
		   },
		   
		   tel: 
		   {
			   required: false,
			   maxlength: 15,
		   },
		   intDate: 
		   {
			   required: true,
		   },
		   noter: 
		   {
			   required: false,
		   },
		   intPeople: 
		   {
			   required: false,
			    maxlength: 20
		   },
		   deviceInfo: 
		   {
			    required: false,
			    maxlength: 20,
		   },
		   operate: 
		   {
			    required: false,
			    maxlength: 100,
		   },
		   remarkOne: 
		   {
			    required: false,
			    maxlength: 100,
			    specialChar: true
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
	$("#equipmentInOutForm2").validate({
		ignore: ".ignore",
        rules: {
//		   daozhele 
		   countt: 
		   {
			    required: false,
			    isPositive: true
		   },
		   isInput: 
		   {
			    required: true,
		   },
		   implemtation: 
		   {
			    required: true,
		   },
		   implemtationName: 
		   {
			    required: true,
		   },
		   caozgcs: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   workLeader: 
		   {
			    required: true,
			    maxlength: 100,
			    specialChar: true
		   },
		   remark: 
		   {
			    required: false,
			    maxlength: 100,
			    specialChar: true
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
//daozhe
   $("#equipmentInOutForm4").validate({
		ignore: ".ignore",
        rules: {
        	isFinish: 
		   {
			    required: true,
		   },
		   rebackName: 
		   {
			    required: true,
			    maxlength: 100
		   },
		   search: 
		   {
			    required: true,
		   },
		   feedback:
		   {
			    required: false,
			    maxlength: 100
		   },
		   remarkTwo: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   extStr2: 
		   {
			    required: true,
			    maxlength: 100
		   },
		   extStr3: 
		   {
			    required: true,
			    maxlength: 100
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