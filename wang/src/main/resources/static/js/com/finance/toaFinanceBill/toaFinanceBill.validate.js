$("#toaFinanceBill#orm").validate({
    ignore:'ignore',
    rules: {
		billDate:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});