$("#toaNetworkBandwidth#orm").validate({
    ignore:'ignore',
    rules: {
		companyId:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});