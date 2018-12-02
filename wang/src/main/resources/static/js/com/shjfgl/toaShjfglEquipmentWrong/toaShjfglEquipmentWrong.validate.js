$("#toaShjfglEquipmentWrong#orm").validate({
    ignore:'ignore',
    rules: {
		equipmentNumber:{ 			required: true,		},		companyId:{ 			required: true,		},		companyName:{ 			required: true,		},		contact:{ 			required: true,		},		tel:{ 			required: true,		},		billDate:{ 			required: true,		},		startDate:{ 			required: true,		},		endDate:{ 			required: true,		},		equipmentId:{ 			required: true,		},		equipmentCheckResult:{ 			required: true,		},		isReport:{ 			required: true,		},		haveAfterOperate:{ 			required: true,		},		operationType:{ 			required: true,		},		operationTypeImg:{ 			required: true,		},		machina:{ 			required: true,		},		caozgcs:{ 			required: true,		},		caozgcsName:{ 			required: true,		},		status:{ 			required: true,		},		remark:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extDate3:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: true,		},		extNum3:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});