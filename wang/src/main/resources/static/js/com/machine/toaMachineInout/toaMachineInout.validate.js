$("#toaMachineInoutForm").validate({
    ignore:'ignore',
    rules: {
		equipmentNumber:{ 			required: true,		},		companyId:{ 			required: true,		},		companyName:{ 			required: true,		},		machina:{ 			required: true,		},		contact:{ 			required: true,		},		tel:{ 			required: true,		},		intDate:{ 			required: true,		},		type:{ 			required: true,		},		countt:{ 			required: true,		},		deviceInfo:{ 			required: true,		},		intPeople:{ 			required: true,		},		isInput:{ 			required: true,		},		caozgcs:{ 			required: true,		},		origin:{ 			required: true,		},		remark:{ 			required: false,		},		status:{ 			required: false,		},    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});