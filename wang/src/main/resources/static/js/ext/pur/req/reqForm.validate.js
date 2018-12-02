$(document).ready(function(){
	
//初始化校验方法
	$("#toPurchaseApplyForm").validate({
        rules: {
		   applyType: 
		   {
			    required: true
		   },
		   budget: 
		   {
			    required: true,
			    customNumber: [7,2,false]
			    
		   },
		   isGeneral: 
		   {
			    required: true
		   }
	    }
	});


});