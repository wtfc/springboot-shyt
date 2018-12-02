$("#toaProductCloudtest#orm").validate({
    ignore:'ignore',
    rules: {
		companyId:{ 			required: true,		},		companyName:{ 			required: true,		},		startDate:{ 			required: true,		},		endDate:{ 			required: true,		},		salePeople:{ 			required: true,		},		cooperateType:{ 			required: true,		},		cpu:{ 			required: true,		},		ram:{ 			required: true,		},		performance:{ 			required: true,		},		stick:{ 			required: true,		},		bandwidth:{ 			required: true,		},		systemMachine:{ 			required: true,		},		email:{ 			required: true,		},		publicIp:{ 			required: true,		},		chargeTime:{ 			required: true,		},		finishDate:{ 			required: true,		},		remark:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: true,		},		extNum3:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});