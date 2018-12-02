$("#toaContractResource#orm").validate({
    ignore:'ignore',
    rules: {
		customerId:{ 			required: false,		},		minBandwidth:{ 			required: false,		},		minBandwidthPrice:{ 			required: false,		},		portBandwidth:{ 			required: false,		},		portBandwidthPrice:{ 			required: false,		},		overflowBandwidth:{ 			required: false,		},		overflowBandwidthPrice:{ 			required: false,		},		cabinetNum:{ 			required: false,		},		cabinetPrice:{ 			required: false,		},		serviceNum:{ 			required: false,		},		servicePrice:{ 			required: false,		},		ipNum:{ 			required: false,		},		ipPrice:{ 			required: false,		},		switchNum:{ 			required: false,		},		switchPrice:{ 			required: false,		},		odfNum:{ 			required: false,		},		odfPrice:{ 			required: false,		},		portNum:{ 			required: false,		},		portPrice:{ 			required: false,		},		memoryNum:{ 			required: false,		},		memoryPrice:{ 			required: false,		},		cpuNum:{ 			required: false,		},		cpuPrice:{ 			required: false,		},		diskNum:{ 			required: false,		},		diskPrice:{ 			required: false,		},		router:{ 			required: false,		},		routerPrice:{ 			required: false,		},		otherss:{ 			required: false,		},		otherssPrice:{ 			required: false,		},		deleteFlag:{ 			required: false,		},		createUser:{ 			required: false,		},		createUserDept:{ 			required: false,		},		createDate:{ 			required: false,		},		modifyUser:{ 			required: false,		},		modifyDate:{ 			required: false,		},		extStr1:{ 			required: false,		},		extStr2:{ 			required: false,		},		extStr3:{ 			required: false,		},		extStr4:{ 			required: false,		},		extStr5:{ 			required: false,		},		extDate1:{ 			required: false,		},		extNum2:{ 			required: false,		},		extDate2:{ 			required: false,		},		extNum1:{ 			required: false,		},		extNum3:{ 			required: false,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});