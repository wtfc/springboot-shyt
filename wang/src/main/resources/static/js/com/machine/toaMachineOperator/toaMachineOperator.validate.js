$("#toaMachineOperatorForm").validate({
    ignore:'ignore',
    rules: {
		equipmentNumber:{
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});