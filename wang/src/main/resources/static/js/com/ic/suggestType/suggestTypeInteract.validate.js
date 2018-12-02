$(document).ready(function(){
	
//初始化校验方法
	$("#suggestTypeForm").validate({
		ignore: ".ignore",
        rules: {
		   typeName: 
		   {
			    required: true,
			    maxlength: 20,
			    specialChar: true,
			    remote:{
			        url: getRootPath()+"/ic/suggestType/checkName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'typeName': function(){return $("#suggestTypeForm #typeName").val();},
			            'oldName': function(){return $("#suggestTypeForm #oldName").val();}
			        }
			    }
		   }
	    },
		messages: {
			typeName: {remote: "类型名称已存在"}
		}
	});



});