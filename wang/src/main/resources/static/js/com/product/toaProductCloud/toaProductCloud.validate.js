$("#toaProductCloudForm").validate({
    ignore:'ignore',
    rules: {
		customersName:{
		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});