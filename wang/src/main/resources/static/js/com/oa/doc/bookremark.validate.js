$(document).ready(function(){
	
//初始化校验方法
	$("#bookremarkUpdateForm").validate({
		ignore: ".ignore",
        rules: {
           name: 
 		   {
 			    required: true,
			    maxlength: 20,
			    minlength: 2,
			    fileName:true
 		   },
		   description: 
		   {
			    required: false,
			    maxlength: 50
		   }
		   
	    }
	});
});