$("#toaFinanceMainForm").validate({
    ignore:'ignore',
    rules: {
		orderNo:{ 			required: true,		},		orderDate:{ 			required: true,		},		month:{ 			required: true,		},		companyName:{ 			required: true,		},		companyType:{ 			required: true,		},		resourceType:{ 			required: true,		},		department:{ 			required: true,		},		sale:{ 			required: true,		},		maintenanSale:{ 			required: true,		},		roomName:{ 			required: true,		},		payType:{ 			required: true,		},		cycleStart:{ 			required: true,		},		cycleEnd:{ 			required: true,		},		lineCategory:{ 			required: true,		},		singleCharg:{ 			required: false,		},		overflowCategory:{ 			required: false,		},		cardNo:{ 			required: false,		},		cardAmount:{ 			required: true,		},		performanceAmount:{ 			required: false,
 			number: true		},		cardStockAmount:{ 			required: false,
 			number: true		},		prestoreAmount:{ 			required: false,
 			number: true		},		discount:{ 			required: false,		},		orderRemark:{ 			required: false,		},		minBandwidth:{ 			required: false,
 			number: true,
		},		minBandwidthPrice:{			required: false,
 			number: true		},		portBandwidth:{			required: false,
 			number: true		},		portBandwidthPrice:{			required: false,
 			number: true		},		overflowBandwidth:{			required: false,
 			number: true		},		overflowBandwidthPrice:{			required: false,
 			number: true		},		cabinetNum:{			required: false,
 			number: true		},		cabinetPrice:{			required: false,
 			number: true		},		serviceNum:{			required: false,
 			number: true		},		servicePrice:{			required: false,
 			number: true		},		ipNum:{			required: false,
 			number: true		},		ipPrice:{			required: false,
 			number: true		},		switchNum:{			required: false,
 			number: true		},		switchPrice:{			required: false,
 			number: true		},		odfNum:{			required: false,
 			number: true		},		odfPrice:{			required: false,
 			number: true		},		portNum:{			required: false,
 			number: true		},		portPrice:{			required: false,
 			number: true		},		memoryNum:{			required: false,
 			number: true		},		memoryPrice:{			required: false,
 			number: true		},		cpuNum:{			required: false,
 			number: true		},		cpuPrice:{			required: false,
 			number: true		},		diskNum:{			required: false,
 			number: true		},		diskPrice:{			required: false,
 			number: true		},
		monthAmount:{
			required: false,
 			number: true
		},
		monthAmount1:{
			required: false,
 			number: true
		},
		monthAmount2:{
			required: false,
 			number: true
		},
		monthAmount3:{
			required: false,
 			number: true
		},
		monthAmount4:{
			required: false,
 			number: true
		},
		monthAmount5:{
			required: false,
 			number: true
		},
		monthAmount6:{
			required: false,
 			number: true
		},
		monthAmount7:{
			required: false,
 			number: true
		},
		monthAmount8:{
			required: false,
 			number: true
		},
		monthAmount9:{
			required: false,
 			number: true
		},
		monthAmount10:{
			required: false,
 			number: true
		},
		monthAmount11:{
			required: false,
 			number: true
		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});