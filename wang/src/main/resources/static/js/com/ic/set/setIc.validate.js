$(document).ready(function(){
	//绑定人员选择树的class
	$.validator.addMethod("rankIdValidate", function(value,element) {
		//获得人员选择树的value
		var per = $("#rankId-rankId").val();
		if($("input[name='type-1']:checked").val() == 1){
			if(per==null||per==""){
				return true;
			}else{
				return checkSaveOrUpdate(per);
			}
		}else{
			return true;
		}
	}, $.i18n.prop("JC_OA_IC_015","人员"));
	
	$.validator.addMethod("rankIdV", function(value,element) {
		if($("input[name='type-1']:checked").val() == 0){
			if(value==null||value==""){
				return true;
			}else{
				return checkSaveOrUpdate(value);
			}
		}else{
			return true;
		}
	},  $.i18n.prop("JC_OA_IC_015","级别"));
	
	function checkSaveOrUpdate(param){
		var flag = false;
		//$("#id").val() != 0修改方法
		if ($("#id").val() != 0) {
			$.ajax({
				url: getRootPath()+"/ic/set/get.action",
		        type: "POST",
		        async:false,
		        data:"id="+$("#id").val(),
		        success : function(data) {
		        	if(data.rankId==param){
		        		flag=true;
		        	}else{
		        		flag = checkName(param,flag);
		        	}
		        }
			});
			return flag;
		}else{
			return checkName(param);
		}
	}
	
	function checkName(param){
		var flag = false;
		//新增方法
		if(param!=null&&param!=""){
			
			$.ajax({
				url: getRootPath()+"/ic/set/checkName.action",
		        type: "POST",
		        async:false,
		        data:"rankId="+param,
		        success : function(data) {
		        	//如果不存在返回“true”
		        	if(data=="true"){
		        		flag=true;
		        	}
		        }
			});
		}
		return flag;
	}
	
	jQuery.validator.addMethod("validSelectForPhone", function(value, element, params){
		if($("input[name='type-1']:checked").val() == 1){
			if(value.length == 0){
				$("#"+params+" .select2-container").addClass("error"); 
				$("#"+params+" .help-block").removeClass("hide");
				$("#"+params+" .help-block").html("<font color=red>"+$.i18n.prop("JC_SYS_010")+"</font>");
				return false;
			}else{
				$("#"+params+" .select2-container").removeClass("error"); 
				$("#"+params+" .help-block").addClass("hide");
				$("#"+params+" .help-block").html("");
				return true;
			}
		}else{
			return true;
		}
		
	}, "");
	
//初始化校验方法
	$("#setForm").validate({
		ignore: ".ignore",
        rules: { 
        	rankId:{
  			   /*required:function(element) {
  			        return $("input[name='type-1']:checked").val() == 1;
  			   }*/
        		validSelectForPhone: "controlTree"
  			},
  			rankIdSelect:{
   			   required:function(element) {
 			        return $("input[name='type-1']:checked").val() == 0;
 			   }
 			},
		   maxnum: 
		   {
			    required: true,
			    maxlength: 6,
			    digits: {digits: true}
		   }
		  
	    }
	});
	
	$("#setSearchForm").validate({
        rules: {
		   num: 
		   {
			    maxlength: 6,
			    digits: {digits: true}
	
		   }
		  
	    }
	});

});