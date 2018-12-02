$("#toaNetworkBandwidth#orm").validate({
    ignore:'ignore',
    rules: {
		companyId:{ 			required: true,		},		companyName:{ 			required: true,		},		gbps:{ 			required: true,		},		line:{ 			required: true,		},		wanglin:{ 			required: true,		},		yzl:{ 			required: true,		},		langfang:{ 			required: true,		},		lugu:{ 			required: true,		},		dongsi:{ 			required: true,		},		huasi:{ 			required: true,		},		shandong:{ 			required: true,		},		yidong:{ 			required: true,		},		tietong:{ 			required: true,		},		jiaoyu:{ 			required: true,		},		keji:{ 			required: true,		},		cnisp:{ 			required: true,		},		guoji:{ 			required: true,		},		transfer:{ 			required: true,		},		remark:{ 			required: true,		},		deleteFlag:{ 			required: true,		},		createUser:{ 			required: true,		},		createUserDept:{ 			required: true,		},		createDate:{ 			required: true,		},		modifyUser:{ 			required: true,		},		modifyDate:{ 			required: true,		},		extStr1:{ 			required: true,		},		extStr2:{ 			required: true,		},		extStr3:{ 			required: true,		},		extStr4:{ 			required: true,		},		extStr5:{ 			required: true,		},		extDate1:{ 			required: true,		},		extDate2:{ 			required: true,		},		extNum1:{ 			required: true,		},		extNum2:{ 			required: true,		},		extNum3:{ 			required: true,		},
    },
	messages:{
		
	},
	errorPlacement: function(error, element) {  
		element.closest("td").append(error); 
	}
});