$("#outContractForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{
 			number: true,
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});