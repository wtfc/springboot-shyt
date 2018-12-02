$(document).ready(function(){
	
//初始化校验方法
	$("#seqForm").validate({
		ignore: ".ignore",
        rules: {
        	seqName: 
			{
				required: true,
				maxlength: 30,
				fileName:true,
			    remote:{
			        url: getRootPath()+"/doc/seq/checkSeqName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'seqName': function(){return $("#seqForm #seqName").val();},
			            'seqNameOld': function(){return $("#seqForm #seqNameOld").val();}
			        }
			    }
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
			seqControlId: 
			{
				//required: true
				validSelect2:'seqControlTree'
			}
	    },
	    messages: {
	    	seqName: {remote: "流水号名称已存在"}
		}
	});
	$("#seqListForm").validate({
		ignore: ".ignore",
		rules: {
			seqName: 
			{
				maxlength: 50,
				fileName:true
			} 
		}
	});
});

