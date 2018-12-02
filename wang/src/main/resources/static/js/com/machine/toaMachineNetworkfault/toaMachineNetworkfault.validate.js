$("#toaMachineNetworkfaultForm").validate({
    ignore:'ignore',
    rules: {
		faultNumber:{ 			required: true,		},		companyName:{ 			required: true,		},		companyId:{ 			required: true,		},		operateDate:{ 			required: true,		},		contact:{ 			required: true,		},		tel:{ 			required: true,		},		typeOne:{ 			required: true,		},		typeTwo:{ 			required: true,		},		processDate:{ 			required: true,		},		finishDate:{ 			required: true,		},		faultReport:{ 			required: true,		},		processing:{ 			required: true,		},		faultReason:{ 			required: true,		},		caozgcs:{ 			required: true,		},		feedbacks:{ 			required: true,		},		isFinish:{ 			required: true,		},		search:{ 			required: true,		},		remark:{ 			required: false,		},		status:{ 			required: false,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});