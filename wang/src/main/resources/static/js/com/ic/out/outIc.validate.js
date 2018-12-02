$(document).ready(function(){
	
	$.validator.addMethod("outLinkman", function(value) {
		var flag = true;
		if(value!=null&&value!=""){
			var rr = new RegExp(/^[1][3-8]\d{9}$/);
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
	$("#outForm").validate({
		ignore: ".ignore",
		rules: {
        	
		   text: 
		   {
			    required: true,
			    maxlength: 1000
		   },
		   sentDate: 
		   {
			   required:function(element) {
 			        if($("#s-dsfs").is(":checked")){
 			        	if($("#dsfs").val()==""){
 			        		return true;
 			        	}
 			        }
 			        return false;
 			   }
		   },
		   sendType:
		   {
			    required: true
		   }
	    }
	});



});