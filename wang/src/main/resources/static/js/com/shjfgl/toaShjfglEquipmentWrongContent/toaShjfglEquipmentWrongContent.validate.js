$("#toaShjfglEquipmentWrongContent#orm").validate({
    ignore:'ignore',
    rules: {
		wrongId:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});