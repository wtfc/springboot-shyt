$("#toaNetworkPortForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{ 			required: true,		},		companyId:{ 			required: false,		},		companyType:{ 			required: false,		},		machine:{ 			required: false,		},		equipment:{ 			required: false,		},		board:{ 			required: false,		},		port:{ 			required: false,		},		vlan:{ 			required: false,		},		bandwidth:{ 			required: false,		},		using:{ 			required: false,		},		bandwidthNumber:{ 			required: true,		},		myselfNumber:{ 			required: true,		},		companyNumber:{ 			required: true,		},		myselfRate:{ 			required: true,		},		companyRate:{ 			required: true,		},		myselfIdleness:{ 			required: false,		},		companyIdleness:{ 			required: false,		},		remark:{ 			required: false,		},    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});