$("#toaShytCustomerForm").validate({
    ignore:'ignore',
    rules: {
		companyName:{
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