$("#toaShytCustomerForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{ 			required: true,		},		companyId:{ 			required: false,		},		machine:{ 			required: true,		},		department:{ 			required: true,		},		sale:{ 			required: true,		},		tradeUser:{ 			required: true,		},		serviceType:{ 			required: true,		},		linkUser:{ 			required: false,		},		trade:{ 			required: true,		},		memberType:{ 			required: true,		},		address:{ 			required: false,		},		newAddress:{ 			required: false,		},		companyOld:{ 			required: false,		},		taxid:{ 			required: false,		},		bankName:{ 			required: false,		},		bankNo:{ 			required: false,		},		ticketFlag:{ 			required: true,		},		overflowCategory:{ 			required: false,		},		isDaili:{ 			required: true,		},		dailiName:{ 			required: false,		},		startIntel:{ 			required: false,		},		endIntel:{ 			required: false,		},		rating:{ 			required: false,		},		roomPrice:{ 			required: false,
 			number: true		},		machinePrice:{ 			required: false,
 			number: true		},		servicePrice:{ 			required: false,
 			number: true		},		ipPrice:{ 			required: false,
 			number: true		},		portPrice:{ 			required: false,
 			number: true		},		portBandwidthPrice:{ 			required: false,
 			number: true		},		minBandwidthPrice:{ 			required: false,
 			number: true		},		overflowBandwidthPrice:{ 			required: false,
 			number: true		},		switchPrice:{ 			required: false,
 			number: true		},		odfPrice:{ 			required: false,
 			number: true		},		memoryPrice:{ 			required: false,
 			number: true		},		cpuPrice:{ 			required: false,
 			number: true		},		diskPrice:{ 			required: false,
 			number: true		},		routerPrice:{ 			required: false,
 			number: true		},		otherssPrice:{ 			required: false,
 			number: true		},		remark:{ 			required: false,		},
		customerAccess:{
			required:true,
		},
		companyPlaced:{
			required:true,
		},
		customerWebsite:{
			required:true,
		},
		commonNumber:{
			required:true,
		},
		companyPurchasing:{
			required:true,
		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});