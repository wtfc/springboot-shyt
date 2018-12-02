$("#toaMachineExpressForm").validate({
    ignore:'ignore',
    rules: {
		expressNumber:{ 			required: true,		},		companyName:{ 			required: true,		},		companyId:{ 			required: true,		},		machina:{ 			required: true,		},		contact:{ 			required: true,		},		tel:{ 			required: true,		},		type:{ 			required: true,		},		deviceDs:{ 			required: true,		},		payType:{ 			required: true,		},		deviceDf:{ 			required: true,		},		moneyType:{ 			required: true,		},		expressPeople:{ 			required: true,		},		expressAddress:{ 			required: true,		},		daifuPay:{ 			required: true,		},		expressDate:{ 			required: true,		},		caozgcs:{ 			required: true,		},		remark:{ 			required: false,		},		status:{ 			required: false,		},    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});