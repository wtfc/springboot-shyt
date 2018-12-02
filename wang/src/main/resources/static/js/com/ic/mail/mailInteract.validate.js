
$(document).ready(function(){
	
//初始化校验方法
	$("#folderform").validate({
		ignore: ".ignore",
		rules: {
			folderName: 
		   {
			    required: true,
			    maxlength: 10
		   }
		}
	});



});