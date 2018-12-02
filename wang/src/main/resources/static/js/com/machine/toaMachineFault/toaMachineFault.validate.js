$("#toaMachineFaultForm").validate({
    ignore:'ignore',
    rules: {
		faultNumber:{ 			required: true,		},		companyName:{ 			required: true,		},		companyId:{ 			required: true,		},		intDate:{ 			required: true,		},		type:{ 			required: true,		},		operateDate:{ 			required: true,		},		restoreDate:{ 			required: true,		},		faultReason:{ 			required: true,		},		faultReport:{ 			required: true,		},		eazyName:{ 			required: true,		},		processing:{ 			required: true,		},		department:{ 			required: true,		},		network:{ 			required: true,		},		yunwei:{ 			required: true,		},		jiankong:{ 			required: true,		},		remarkOne:{ 			required: false,		},		status:{ 			required: false,		},    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});