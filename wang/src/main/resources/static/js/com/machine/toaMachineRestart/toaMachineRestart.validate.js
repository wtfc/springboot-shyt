$("#toaMachineRestartForm").validate({
    ignore:'ignore',
    rules: {
		equipmentNumber:{
		/*billDate:{
			required:true,
		},*/
		warnDate:{
			required:true,
		},
		
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});