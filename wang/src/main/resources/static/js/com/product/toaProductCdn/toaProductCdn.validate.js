$("#toaProductCdnForm").validate({
    ignore:'ignore',
    rules: {
		customersName:{
		},
 			number: true
 			number: true
 			number: true
 			number: true
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});