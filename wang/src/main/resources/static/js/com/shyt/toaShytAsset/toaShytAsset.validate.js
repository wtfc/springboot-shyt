$("#toaShytAsset#orm").validate({
    ignore:'ignore',
    rules: {
		assetsName:{
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});