$("#toaNetworkMachine#orm").validate({
    ignore:'ignore',
    rules: {
		machine:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});