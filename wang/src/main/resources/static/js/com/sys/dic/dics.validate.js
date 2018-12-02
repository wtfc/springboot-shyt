$(document).ready(function(){
	
	//初始化校验方法 
	$.validator.addMethod("valueValider", function(value, element) {
			//return !this.optional(element);
			return true;
	}, "必填信息");

	$.validator.addMethod("codeValider", function(value, element) {
			//return !this.optional(element);
			return true;
	}, "必填信息");

	
	$("#dicsname").validate({
		ignore: ".ignore",
        rules: {code: 
		   {
		    required: false
		   }
        }
	});
});