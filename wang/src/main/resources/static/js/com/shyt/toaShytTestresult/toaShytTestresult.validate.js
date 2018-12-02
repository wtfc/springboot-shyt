$("#ToaShytTestresultForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{ 			required: true,		},		companyId:{ 			required: true,		},		machine:{ 			required: true,		},		tradeUser:{ 			required: true,		},		serviceDate:{ 			required: true,		},		linkUser:{ 			required: true,		},		companyOld:{ 			required: true,		},		remark:{ 			required: true,		}    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});