$("#toaFinanceBill#orm").validate({
    ignore:'ignore',
    rules: {
		billDate:{ 			required: true,		},		custmersName:{ 			required: true,		},		customersId:{ 			required: true,		},		custmersTaxid:{ 			required: true,		},		address:{ 			required: true,		},		phone:{ 			required: true,		},		bankName:{ 			required: true,		},		bankNo:{ 			required: true,		},		ticket:{ 			required: true,		},		state:{ 			required: true,		},		billMoney:{ 			required: true,		},		billMoneym:{ 			required: true,		},		payDate:{ 			required: true,		},		obankname:{ 			required: true,		},		obankno:{ 			required: true,		},		ocompany:{ 			required: true,		},		sale:{ 			required: true,		},		salePhone:{ 			required: true,		},		remarkId:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: true,		},		extNum3:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});