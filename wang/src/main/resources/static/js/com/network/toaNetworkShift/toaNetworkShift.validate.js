$("#toaNetworkShiftForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});