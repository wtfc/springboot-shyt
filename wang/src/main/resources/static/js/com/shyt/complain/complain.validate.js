$(document).ready(function(){
//初始化校验方法
	$("#complainForm").validate({
		ignore: ".ignore",
        rules: {
        	companyName: 
		   {
			    required: true,
		   },
		   partment: 
		   {
			    required: true,
		   },
		   complainDate: 
		   {
			    required: true,
		   },
		   complainStatus: 
		   {
			    required: true,
		   },
		   status: 
		   {
			    required: true,
		   },
	    },	
	    errorPlacement:function(error,element){//第一个参数是错误的提示文字，第二个参数是当前输入框
	    	element.closest("td").append(error);
	        //error.appendTo(element.siblings("span"));　　//用的是jQuery，这里设置的是，错误提示文本显示在当前文本框的兄弟span中
	    }
	});
});