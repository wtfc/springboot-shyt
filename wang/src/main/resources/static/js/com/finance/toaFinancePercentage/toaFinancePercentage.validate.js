$("#toaFinancePercentage#orm").validate({
    ignore:'ignore',
    rules: {
		perNumber:{ 			required: true,		},		mainId:{ 			required: true,		},		perYear:{ 			required: true,		},		perMonth:{ 			required: true,		},		perType:{ 			required: true,		},		companyName:{ 			required: true,		},		companyId:{ 			required: true,		},		department:{ 			required: true,		},		sale:{ 			required: true,		},		tradeDepartment:{ 			required: true,		},		perEnSale:{ 			required: true,		},		perAccount:{ 			required: true,		},		perAgentAccount:{ 			required: true,		},		perPureAccount:{ 			required: true,		},		billAccount:{ 			required: true,		},		addBill:{ 			required: true,		},		kuorong:{ 			required: true,		},		perStart:{ 			required: true,		},		billDate:{ 			required: true,		},		perYers:{ 			required: true,		},		keweiRatio:{ 			required: true,		},		tuozhanRatio:{ 			required: true,		},		keweiMoney:{ 			required: true,		},		tuozhanMoney:{ 			required: true,		},		billNumber:{ 			required: true,		},		remark:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: true,		},		extNum3:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});