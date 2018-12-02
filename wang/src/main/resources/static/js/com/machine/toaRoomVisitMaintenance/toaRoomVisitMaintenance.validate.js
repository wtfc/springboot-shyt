$("#toaRoomVisitMaintenance#orm").validate({
    ignore:'ignore',
    rules: {
		equipmentNumber:{ 			required: true,		},		companyId:{ 			required: true,		},		companyName:{ 			required: true,		},		industryType:{ 			required: true,		},		contact:{ 			required: true,		},		tel:{ 			required: true,		},		intDate:{ 			required: true,		},		type:{ 			required: true,		},		realityType:{ 			required: true,		},		preOperate:{ 			required: true,		},		intPeople:{ 			required: true,		},		intPeopleCard:{ 			required: true,		},		visitZone:{ 			required: true,		},		isInput:{ 			required: true,		},		caozgcs:{ 			required: true,		},		judge:{ 			required: true,		},		startDate:{ 			required: true,		},		endDate:{ 			required: true,		},		status:{ 			required: true,		},		remark:{ 			required: true,		},		preOperateUrl:{ 			required: true,		},		fileUrl:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: true,		},		extNum3:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});