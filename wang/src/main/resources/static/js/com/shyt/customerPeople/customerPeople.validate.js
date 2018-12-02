$(document).ready(function(){
//初始化校验方法
	$("#customerPeopleForm").validate({
		ignore: ".ignore",
        rules: {
        	companyName: 
		   {
			    required: true,
		   },
		   name: 
		   {
			    required: true,
		   },
		   tel: 
		   {
			    required: true,
			    maxlength: 11,
			    mobile: true,
		   },
		   idCard: 
		   {
			    required: true,
			    maxlength:18
		   },
		   email: 
		   {
			    required: true,
			    maxlength: 64,
			    email: true,
			    specialChar: true
		   },
		   placeOrigin:{
			   required: true, 
		   },
		   marriagePeople:{
			   required: true,  
		   },
		   childrenSituation:{
			   required: true,
		   },
		   departmentPeople:{
			   required: true, 
		   },
		   customerYear:{
			  required: true,  
		   },
		   decisionMaking:{
			  required: true, 
		   },
		   job:{
			  required: true,   
		   },
	    },	
	    errorPlacement:function(error,element){//第一个参数是错误的提示文字，第二个参数是当前输入框
	    	element.closest("td").append(error);
	        //error.appendTo(element.siblings("span"));　　//用的是jQuery，这里设置的是，错误提示文本显示在当前文本框的兄弟span中
	    }
	});
});
