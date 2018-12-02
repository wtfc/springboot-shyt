$(document).ready(function(){
	
//初始化校验方法
	$("#columnForm").validate({
		ignore: ".ignore",
        rules: {
        	columnName: 
        	{
        		required: true,
        		maxlength: 20,
        		specialChar: true,
	    		remote:{
			        url: getRootPath()+"/im/column/checkName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'columnName': function(){return $("#columnForm #columnName").val();},
			            'oldName': function(){return $("#columnForm #oldName").val();}
			        }
			    }
        	},
        	workflows: 
		   {
			    required: true
		   },
		   columnParentId: 
		   {
			    required: true
			    
		   },
		   quene: 
		   {
			    required: false
			    
		   },
		   isApp: 
		   {
			    required: false,
			    maxlength: 1
		   },
		   weight: 
		   {
			    required: false
			    
		   },
		   
		   createUser: 
		   {
			    required: false
			    
		   },
		   createUserDept: 
		   {
			    required: false
			    
		   },
		   createUserOrg: 
		   {
			    required: false
			    
		   },
		   createDate: 
		   {
			    required: false
			    
		   },
		   modifyUser: 
		   {
			    required: false
			    
		   },
		   modifyDate: 
		   {
			    required: false
			    
		   }
	    },
		messages: {
			columnName: {remote: "栏目名称已存在"}
		}
	});
});