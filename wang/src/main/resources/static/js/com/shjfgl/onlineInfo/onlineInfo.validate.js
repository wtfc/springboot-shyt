$("#equipmentInOutForm").validate({
    ignore:'ignore',
    rules: {
		num1:{
		    required: true,
		},
		num2:{
		    required: true,
		},
		num3:{
		    required: true,
		},
		num4:{
		    required: true,
		},
		num5:{
		    required: true,
		},
		num6:{
		    required: true,
		},
		num7:{
		    required: true,
		},
		num8:{
		    required: true,
		},
		num9:{
		    required: true,
		},
		num10:{
		    required: true,
		},
		num11:{
		    required: true,
		},
		num12:{
		    required: true,
		},
		num13:{
		    required: true,
		},
		num14:{
		    required: true,
		},
		num15:{
		    required: true,
		},
		num16:{
		    required: true,
		},
		num17:{
		    required: true,
		},
		num18:{
		    required: true,
		},
		num19:{
		    required: true,
		},
		num44:{
		    required: true,
		},
		num43:{
		    required: true,
		},
		num42:{
		    required: true,
		},
		num41:{
		    required: true,
		},
		num40:{
		    required: true,
		},
		num39:{
		    required: true,
		},
		    },
    messages: {
    	num1:{
    		required: "选项不能为空",
		},
		num2:{
		    required: "选项不能为空",
		},
		num3:{
		    required: "选项不能为空",
		},
		num4:{
		    required: "选项不能为空",
		},
		num5:{
		    required: "选项不能为空",
		},
		num6:{
		    required: "选项不能为空",
		},
		num7:{
		    required: "选项不能为空",
		},
		num8:{
		    required: "选项不能为空",
		},
		num9:{
		    required: "选项不能为空",
		},
		num10:{
		    required: "选项不能为空",
		},
		num11:{
		    required: "选项不能为空",
		},
		num12:{
		    required: "选项不能为空",
		},
		num13:{
		    required: "选项不能为空",
		},
		num14:{
		    required: "选项不能为空",
		},
		num15:{
		    required: "选项不能为空",
		},
		num16:{
		    required: "选项不能为空",
		},
		num17:{
		    required: "选项不能为空",
		},
		num18:{
		    required: "选项不能为空",
		},
		num19:{
		    required: "选项不能为空",
		},
		num44:{
		    required: "选项不能为空",
		},
		num43:{
		    required: "选项不能为空",
		},
		num42:{
		    required: "选项不能为空",
		},
		num41:{
		    required: "选项不能为空",
		},
		num40:{
		    required: "选项不能为空",
		},
		num39:{
		    required: "选项不能为空",
		},
    },
	errorPlacement:function(error,element){//第一个参数是错误的提示文字，第二个参数是当前输入框
	element.closest("td").append(error);
    //error.appendTo(element.siblings("span"));　　//用的是jQuery，这里设置的是，错误提示文本显示在当前文本框的兄弟span中
	}
		});