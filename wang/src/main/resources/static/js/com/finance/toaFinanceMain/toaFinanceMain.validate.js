$("#toaFinanceMainForm").validate({
    ignore:'ignore',
    rules: {
		orderNo:{
 			number: true
 			number: true
 			number: true
 			number: true,
		},
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
 			number: true
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