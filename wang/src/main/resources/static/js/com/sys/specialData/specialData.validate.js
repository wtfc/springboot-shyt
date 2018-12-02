$(document).ready(function(){
	
//初始化校验方法
	$("#specialDataForm").validate({
        rules: {
           tempinfoName: 
		   {
			    required: true,
			    maxlength: 20
		   },
		   infoType: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   userSex: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   specialData: 
		   {
			    required: true
		   },
		   beforeDay: 
		   {
			    required: false,
			    digits:true
		   },
		   showType: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   infoCirculate: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   openLevel: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   solarorlunar: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   summaryContent: 
		   {
			    required: false,
			    maxlength: 100
		   },
		   sendmailStatus: 
		   {
			    required: false,
			    maxlength: 20
		   },
		   sendpictureStatus: 
		   {
			    required: false,
			    maxlength: 20
		   },
	    }
	});


});