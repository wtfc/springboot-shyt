$("#toaNetworkProcure#orm").validate({
    ignore:'ignore',
    rules: {
		machineNumber:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});