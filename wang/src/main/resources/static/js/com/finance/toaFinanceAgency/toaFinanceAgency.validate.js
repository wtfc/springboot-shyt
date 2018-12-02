$("#toaFinanceAgencyForm").validate({
    ignore:'ignore',
    rules: {
		orderNo:{ 			required: false,		},		customerId:{ 			required: false,		},		customerName:{ 			required: false,		},		department:{ 			required: false,		},		sale:{ 			required: false,		},		maintenanSale:{ 			required: false,		},		cycleStart:{ 			required: false,		},		cycleEnd:{ 			required: false,		},		cardNo:{ 			required: false,		},		cardAmount:{ 			required: false,		},		incontractStart:{ 			required: false,		},		incontractEnd:{ 			required: false,		},		roomName:{ 			required: false,		},		resources:{ 			required: false,		},		payType:{ 			required: false,		},		beforeTax:{ 			required: false,		},		beforeLittle:{ 			required: false,		},		paee:{ 			required: false,		},		payState:{ 			required: false,		},		payDate:{ 			required: false,		},		notPay:{ 			required: false,		},		orderRemark:{ 			required: false,		},		deleteFlag:{ 			required: false,		},		createUser:{ 			required: false,		},		createUserDept:{ 			required: false,		},		createDate:{ 			required: false,		},		modifyUser:{ 			required: false,		},		modifyDate:{ 			required: false,		},		extStr1:{ 			required: false,		},		extStr2:{ 			required: false,		},		extStr3:{ 			required: false,		},		extStr4:{ 			required: false,		},		extStr5:{ 			required: false,		},		extDate1:{ 			required: false,		},		extDate2:{ 			required: false,		},		extNum1:{ 			required: false,		},		extNum2:{ 			required: false,		},		extNum3:{ 			required: false,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});