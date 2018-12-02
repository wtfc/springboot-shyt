$(document).ready(function(){
	
	//初始化校验方法
	var deptAdd = $("#departmentForm").validate({
		debug: true,
		ignore: ".ignore",
        rules: {
		   code: 
		   {
			    required: false,
			    maxlength: 192
		   },
		   name: //部门名称
		   {
			    required: true,
			    specialChar: true,
			    maxlength: 16
		   },
		   deptDesc: //部门详细信息
		   {
			    required: false,
			    maxlength: 255
		   },
		   leaderId: //负责人	 //控件的name属性
		   {
			   	//validSelect2 : "userInsertDivId"	//验证名称 ："控件divID"
		   },
		   chargeLeaderId: 
		   {
			    required: false,
			    
		   },
		   parentId: 
		   {
			    required: false,
			    
		   },
		   managerDept: 
		   {
			    required: false,
			    
		   },
		   deptType: 
		   {
			    required: false,
			    
		   },
		   organizationId: 
		   {
			    required: false,
			    
		   },
		   queue: 
		   {
			    required: false,
			    digits: true,
			    maxlength: 5
		   },
		   createUser: 
		   {
			    required: false,
			    
		   },
		   createDate: 
		   {
			    required: false,
			    
		   },
		   createUserOrg: 
		   {
			    required: false,
			    
		   },
		   createUserDep: 
		   {
			    required: false,
			    
		   },
		   modifyUser: 
		   {
			    required: false,
			    
		   },
		   modifyDate: 
		   {
			    required: false,
			    
		   },
		   deleteFlag: 
		   {
			    required: false,
			    
		   },
		   remark1: 
		   {
			    required: false,
			    maxlength: 765
		   },
		   remark2: 
		   {
			    required: false,
			    maxlength: 65535
		   }
	    }
	});

	var deptUpdate = $("#departmentUpdateForm").validate({
		debug: true,
        rules: {
		   code: 
		   {
			    required: false,
			    maxlength: 192
		   },
		   name: //部门名称
		   {
			    required: true,
			    specialChar: true,
			    maxlength: 16
		   },
		   deptDesc: //部门详细信息
		   {
			    required: false,
			    maxlength: 255
		   },
		   leaderId: 
		   {
			    //required: false,
			    
		   },
		   chargeLeaderId: 
		   {
			    required: false,
			    
		   },
		   parentId: 
		   {
			    required: false,
			    
		   },
		   managerDept: 
		   {
			    required: false,
			    
		   },
		   deptType: 
		   {
			    required: false,
			    
		   },
		   organizationId: 
		   {
			    required: false,
			    
		   },
		   queue: 
		   {
			    required: false,
			    digits : true,
			    maxlength: 5
		   },
		   createUser: 
		   {
			    required: false,
			    
		   },
		   createDate: 
		   {
			    required: false,
			    
		   },
		   createUserOrg: 
		   {
			    required: false,
			    
		   },
		   createUserDep: 
		   {
			    required: false,
			    
		   },
		   modifyUser: 
		   {
			    required: false,
			    
		   },
		   modifyDate: 
		   {
			    required: false,
			    
		   },
		   deleteFlag: 
		   {
			    required: false,
			    
		   },
		   remark1: 
		   {
			    required: false,
			    maxlength: 765
		   },
		   remark2: 
		   {
			    required: false,
			    maxlength: 65535
		   }
	    }
	});

});