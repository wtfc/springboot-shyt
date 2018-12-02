$(document).ready(function(){
	
//初始化校验方法
	$("#exchangeReceiveListForm").validate({
		
		ignore: ".ignore",
        rules: {
			title: 
			{
				maxlength: 100,
				fileName:true
			}
	    }
	});
});