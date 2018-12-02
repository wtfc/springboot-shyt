$(document).ready(function(){
	
	function test(value){
		if(value == 0)
			return false;
		return /^\d{1,6}$/.test(value);
	}
	
//初始化校验方法
	$("#viewForm").validate({
		ignore: ".ignore",
        rules: {
        	contact: 
		   {
			    required: true,
		   },
		   machina: 
		   {
			   required: true,
			    maxlength: 20
		   },
		   machinaNumber: 
		   {
			   required: true,
			    maxlength: 20
		   },
		   address: 
		   {
			   required: false,
			    maxlength: 20
		   },
		   isOpen: 
		   {
			    required: true,
			    maxlength: 20
		   },
		   type: 
		   {
			    required: true,
		   },
		   num: 
		   {
			    required: false,
			    isPositive: true
		   },
		   valume: 
		   {
			   required: false,
			   isPositive: true
		   }
        }
	});
});