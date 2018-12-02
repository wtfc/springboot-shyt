$("#toaProductCdnForm").validate({
    ignore:'ignore',
    rules: {
		customersName:{ 			required: true,		},		supporter:{ 			required: false,		},		cooperateType:{ 			required: false,		},		businessType:{ 			required: false,		},		userName:{ 			required: false,		},		speedName:{ 			required: false,		},		stick:{ 			required: false,		},		chargeMode:{ 			required: false,
		},		price:{ 			required: false,
 			number: true		},		flooredNumber:{ 			required: false,
 			number: true		},		chargeNumber:{ 			required: false,
 			number: true		},		amount:{ 			required: false,
 			number: true		},		chargeTime:{ 			required: true,		},		startDate:{ 			required: true,		},		startEnd:{ 			required: true,		},		remark:{ 			required: false,		},		extStr1:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});