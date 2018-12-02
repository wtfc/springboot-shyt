$(document).ready(function(){
	
//初始化校验方法
	$("#noListForm").validate({
		ignore: ".ignore",
		rules: {
			noName: 
			{
			    maxlength: 30,
			    fileName:true
			}
		}});
	$("#noForm").validate({
		ignore: ".ignore",
        rules: {
        	noName: 
			{
			    required: true,
			    maxlength: 30,
			    fileName:true,
			    remote:{
			        url: getRootPath()+"/doc/no/checkNoName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'noName': function(){return $("#noForm #noName").val();},
			            'noNameOld': function(){return $("#noForm #noNameOld").val();}
			        }
			    }
			},
			parameter1: 
			{
			    required: true,
			    maxlength: 20,
			    fileName:true
			},
			parameter2: 
			{
				required: function(){
					if($("#isYear").is(":checked")){
						return true;
					}else{
						return false;
					}
				},
				maxlength: 5
			},
			parameter3: 
			{
				required: function(){
					if($("#isYear").is(":checked")){
						return true;
					}else{
						return false;
					}
				},
				maxlength: 5
			},
			parameter4: 
			{
				required: true,
				maxlength: 5
			},
			numberStart: 
			{
				required: true,
				digits:true,
				maxlength: 8,
				max: function(){
					var numberCeiling = $("#numberCeiling").val();
					if(numberCeiling != '' && !isNaN(parseInt(numberCeiling))){
						return parseInt(numberCeiling);
					}else{
						return 99999999;
					}
				}
			},
			numberCeiling: 
			{
				digits:true,
				maxlength: 8,
				min: function(){
					var numberStart = $("#numberStart").val();
					if(numberStart != '' && !isNaN(parseInt(numberStart))){
						return parseInt(numberStart);
					}else{
						return 0;
					}
				}
			},
			noControlId: 
			{
				//required: true
				validSelect2:'noControlTree'
			}
	    },
	    messages: {
	    	noName: {remote: "文号名称已存在"}
		}
	});
});

