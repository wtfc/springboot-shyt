$("#toaContractResource#orm").validate({
    ignore:'ignore',
    rules: {
		customerId:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});