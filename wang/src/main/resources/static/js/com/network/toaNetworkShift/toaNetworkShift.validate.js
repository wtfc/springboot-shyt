$("#toaNetworkShiftForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{ 			required: false,		},		companyId:{ 			required: false,		},		phone:{ 			required: false,		},		executor:{ 			required: true,		},		connectPeople:{ 			required: true,		},		shiftDate:{ 			required: true,		},		finishDate:{ 			required: false,		},		remark:{ 			required: false,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});