$("#toaMachineMonitoringForm").validate({
    ignore:'ignore',
    rules: {
		faultNumber:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});