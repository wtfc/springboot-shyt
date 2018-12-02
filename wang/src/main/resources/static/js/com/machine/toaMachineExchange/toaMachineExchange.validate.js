$("#toaMachineExchange#orm").validate({
    ignore:'ignore',
    rules: {
		restartId:{ 			required: true,		},		name:{ 			required: true,		},		startDate:{ 			required: true,		},		substance:{ 			required: true,		},		serviceName:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});