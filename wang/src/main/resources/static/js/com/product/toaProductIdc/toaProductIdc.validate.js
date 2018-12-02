$("#toaProductIdcForm").validate({
    ignore:'ignore',
    rules: {
		customersId:{ 			required: true,		},		customersName:{ 			required: true,		},		phone:{ 			required: true,		},		linkman:{ 			required: true,		},		address:{ 			required: true,		},		taxid:{ 			required: true,		},		expandPeople:{ 			required: true,		},		vask:{ 			required: true,		},		supporter:{ 			required: true,		},		cooperateType:{ 			required: true,		},		businessType:{ 			required: true,		},		machine:{ 			required: true,		},		equipment:{ 			required: true,		},		bandwidth:{ 			required: true,		},		port:{ 			required: true,		},		ipAddress:{ 			required: true,		},		remark:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: true,		},		extNum3:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});