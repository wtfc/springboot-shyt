$("#toaMachineRestartForm").validate({
    ignore:'ignore',
    rules: {
		equipmentNumber:{ 			required: true,		},		companyId:{ 			required: true,		},		companyName:{ 			required: true,		},
		/*billDate:{
			required:true,
		},*/		machina:{ 			required: true,		},		ip:{ 			required: false,		},
		warnDate:{
			required:true,
		},
		    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});