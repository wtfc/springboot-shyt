$("#toaFinancePercentage#orm").validate({
    ignore:'ignore',
    rules: {
		perNumber:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});