$("#toaNetworkRing#orm").validate({
    ignore:'ignore',
    rules: {
		ringType:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});