$("#toaContractOutcontractForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{ 			required: false,		},		customer:{ 			required: false,		},		contractNumber:{ 			required: false,		},		leard:{ 			required: false,		},		leardDate:{ 			required: false,		},		agreement:{ 			required: false,		},		contractStatus:{ 			required: false,		},		contractMoney:{ 			required: false,		},		seal:{ 			required: false,		},		place:{ 			required: false,		},		startDate:{ 			required: false,		},		endDate:{ 			required: false,		},		resource:{ 			required: false,		},		contractFile:{ 			required: false,		},		remark:{ 			required: false,		},		deleteFlag:{ 			required: false,		},		createUser:{ 			required: false,		},		createUserDept:{ 			required: false,		},		createDate:{ 			required: false,		},		modifyUser:{ 			required: false,		},		modifyDate:{ 			required: false,		},		extStr1:{ 			required: false,		},		extStr2:{ 			required: false,		},		extStr3:{ 			required: false,		},		extStr4:{ 			required: false,		},		extStr5:{ 			required: false,		},		extDate1:{ 			required: false,		},		extDate2:{ 			required: false,		},		extNum1:{ 			required: false,		},		extNum2:{ 			required: false,		},		extNum3:{ 			required: false,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});