$("#toaMachineExchange#orm").validate({
    ignore:'ignore',
    rules: {
		restartId:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});