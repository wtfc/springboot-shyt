$(document).ready(function(){
	
//初始化校验方法
	$("#exchangeSendListForm").validate({
		ignore: ".ignore",
        rules: {
 		  title: 
			{
				maxlength: 100,
				fileName:true
			}
	    }
	});
	//初始化校验方法
	$("#exchangeSendAddForm").validate({
		ignore: ".ignore",
        rules: {
           receiveDeptIds:
 		   {
 			    //required: true
        	    validSelect2:'receiveDeptNames'
 		   }
	    }
	});
});