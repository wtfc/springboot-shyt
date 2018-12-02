$("#toaMachineOperatorForm").validate({
    ignore:'ignore',
    rules: {
		equipmentNumber:{ 			required: true,		},		companyId:{ 			required: true,		},		companyName:{ 			required: true,		},		machina:{ 			required: true,		},		contact:{ 			required: true,		},		tel:{ 			required: true,		},		operateType:{ 			required: true,		},		operateText:{ 			required: true,		},		intDate:{ 			required: true,		},		caozgcs:{ 			required: true,		},		operateDate:{ 			required: true,		},		endDate:{ 			required: true,		},		isFinish:{ 			required: true,		},		countt:{ 			required: true,		},		search:{ 			required: true,		},		remark:{ 			required: false,		},		status:{ 			required: false,		},    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});