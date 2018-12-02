$("#toaMachineMonitoringForm").validate({
    ignore:'ignore',
    rules: {
		faultNumber:{ 			required: true,		},		companyName:{ 			required: true,		},		companyId:{ 			required: true,		},		machina:{ 			required: true,		},		startDate:{ 			required: true,		},		finishDate:{ 			required: true,		},		type:{ 			required: true,		},		typeTwo:{ 			required: true,		},		faultReason:{ 			required: true,		},		overflow:{ 			required: true,		},		inflow:{ 			required: true,		},		outflow:{ 			required: true,		},		fazhi:{ 			required: true,		},		overfazhi:{ 			required: true,		},		remark:{ 			required: true,		},		status:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: false,		},		extNum3:{ 			required: false,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});