$("#toaProductCloudForm").validate({
    ignore:'ignore',
    rules: {
		customersName:{ 			required: true,		},		supporter:{ 			required: false,		},		cooperateType:{ 			required: false,		},		businessType:{ 			required: false,		},		cpu:{ 			required: false,		},		ram:{ 			required: false,		},		stick:{ 			required: false,		},		performance:{ 			required: false,		},		cloudPhoto:{ 			required: false,		},		cloudDive:{ 			required: false,		},		publicIp:{ 			required: false,		},		bandwidth:{ 			required: false,		},		router:{ 			required: false,		},		loadBalancer:{ 			required: false,		},		amount:{ 			required: false,
		},		resourceType:{ 			required: false,		},		chargeTime:{ 			required: false,		},		startDate:{ 			required: false,		},		startEnd:{ 			required: false,		},		remark:{ 			required: false,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});