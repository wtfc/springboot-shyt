$(document).ready(function(){
	
//初始化校验方法
	$("#fromDeptForm").validate({
		ignore: ".ignore",
        rules: {
        	name: 
			{
 			    required: true,
			    maxlength: 80,
			    minlength: 2,
			    fileName:true,
			    remote:{
			        url: getRootPath()+"/doc/fromDept/checkName.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'name': function(){return $("#fromDeptForm #name").val();},
			            'nameOld': function(){return $("#fromDeptForm #nameOld").val();}
			        }
			    }
			},
 		   offcial: 
		   {
			    required: true,
			    maxlength: 10,
			    fileName:true
		   },
		   tel: 
		   {
			    required: false,
			    maxlength: 11,
			    mobile: true
		   },
		   description: 
		   {
			    required: false,
			    maxlength: 100
		   }
	    },
	    messages: {
	    	name: {remote: "单位名称已存在"}
		}
	});
	$("#fromDeptListForm").validate({
		ignore: ".ignore",
		rules: {
			name: 
			{
				maxlength: 50,
				fileName:true
			}
			
		}
	});
});