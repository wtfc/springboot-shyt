/**
 * 工作计划表单验证js
 * @author 刘锡来
 */
$(document).ready(function(){
	$.validator.addMethod("sumSubmitYearValid", function(value, element) {
		if($("#planType").val() == 0||$("#planType").val() == 1){
            $("#sumSubmitYear").rules("remove");
            $("#sumSubmitYear").rules("add",{
            	required:true,
            	range:[2000,3000],
            	minlength:4,
            	maxlength:4,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}
		return true;
	}, "");
	
	$.validator.addMethod("sumSubmitMWValid", function(value, element) {
		if($("#planType").val() == 0){
            $("#sumSubmitMW").rules("remove");
            $("#sumSubmitMW").rules("add",{
            	required:true,
            	range:[1,55],
            	minlength:1,
            	maxlength:2,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}else if($("#planType").val() == 1){
            $("#sumSubmitMW").rules("remove");
            $("#sumSubmitMW").rules("add",{
            	required:true,
            	range:[1,12],
            	minlength:1,
            	maxlength:2,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}else{
            $("#sumSubmitMW").rules("remove");
            $("#sumSubmitMW").rules("add",{
            	required:true,
            	range:[2000,3000],
            	minlength:4,
            	maxlength:4,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}
		return true;
	}, "");
	
	$.validator.addMethod("planSubmitYearValid", function(value, element) {
		if($("#planType").val() == 0||$("#planType").val() == 1){
            $("#planSubmitYear").rules("remove");
            $("#planSubmitYear").rules("add",{
            	required:true,
            	range:[2000,3000],
            	minlength:4,
            	maxlength:4,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}
		return true;
	}, "");
	
	
	$.validator.addMethod("planSubmitMWValid", function(value, element) {
		if($("#planType").val() == 0){
            $("#planSubmitMW").rules("remove");
            $("#planSubmitMW").rules("add",{
            	required:true,
            	range:[1,55],
            	minlength:1,
            	maxlength:2,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}else if($("#planType").val() == 1){
            $("#planSubmitMW").rules("remove");
            $("#planSubmitMW").rules("add",{
            	required:true,
            	range:[1,12],
            	minlength:1,
            	maxlength:2,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}else{
            $("#planSubmitMW").rules("remove");
            $("#planSubmitMW").rules("add",{
            	required:true,
            	range:[2000,3000],
            	minlength:4,
            	maxlength:4,
            	messages:{
            		required:'',
            		range:'',
            		minlength:'',
            		maxlength:''
            	}
            });
		}
		return true;
	}, "");
	
	$("#planForm").validate({
        rules: {
        	planTitle:{//计划标题
        		required: true,
        		specialChar: true,
        		maxlength: 50
		    },
		    planStartTime:{//计划开始时间
				required: true
		    },
		    planEndTime:{//计划结束时间
				required: true
		    },
		    remark:{//备注
//        		specialChar: true,
		    	maxlength: 1000
		    },
		    manaInno:{//管理及创新
//        		specialChar: true,
		    	maxlength: 1000
		    },
		    costControl:{//成本控制及节约
//        		specialChar: true,
		    	maxlength: 1000
		    },
		    trainingSum:{//培训总结
//        		specialChar: true,
		    	maxlength: 1000
		    },
		    problemMeas:{//存在问题及解决措施
//        		specialChar: true,
		    	maxlength: 1000
		    },
		    sumSubmitYear:{
		    	sumSubmitYearValid:true,
		    	required: true
		    },
		    sumSubmitMW:{
		    	sumSubmitMWValid:true,
		    	required: true
		    },
		    planSubmitYear:{
		    	planSubmitYearValid:true,
		    	required: true
		    },
		    planSubmitMW:{
		    	planSubmitMWValid:true,
		    	required: true
		    }
	    }
	});	
});
	
function planBusinessValid(){
    var sumTable = document.getElementById('preSum');//获得工作总结table对象
    var planTable = document.getElementById('prePlan');//获得工作计划table对象
    var sumTableLength = sumTable.rows.length;//获得工作总结table对象长度
    var planTableLength = planTable.rows.length;//获得工作计划table对象长度
    var noDataSumRows = 0;//工作总结没有数据行数
    var noDataPlanRows = 0;//工作计划没有数据行数
	var isNum = /^[0-9]*[0-9][0-9]*$/;//是否为数字输入验证
	
//  总结详细信息验证	
//	if(typeof(sumTable.rows[1].cells[1].childNodes[0].innerHTML) != 'undefined' && 
//	   typeof(planTable.rows[1].cells[1].childNodes[0].innerHTML) != 'undefined'){
	if(sumTable.rows[1].cells[1].childNodes[0].outerHTML.indexOf("downarea") > 0 && 
	   planTable.rows[1].cells[1].childNodes[0].outerHTML.indexOf("downarea") > 0){
	    for(var i=1;i<sumTableLength;i++){
	    	var sumNoDataNum = 0;//空值计数
	    	//主要完成事项不为空,其他填写项必须不为空
	    	if(sumTable.rows[i].cells[1].childNodes[0].childNodes[0].innerHTML != ""){
	    		
	    		//主要完成事项特殊字符校验  add by lihongyu at 2014-10-17 start
	    		if(checkStr(sumTable.rows[i].cells[1].childNodes[0].childNodes[0].innerHTML)==true){
					msgBox.tip({
						type : "fail",
						content : "工作总结表单第 "+ i + " 条<br>主要完成事项中含有特殊<br>字符'|'。"
					});
					return false;
				}
	    		//主要完成事项特殊字符校验  add by lihongyu at 2014-10-17 end
	    		
				if(sumTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML == ""){
					msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条完成标准"
					});
					return false;
				}//完成标准是否为空验证
				
				//完成标准特殊字符校验  add by lihongyu at 2014-10-17 start
				else{
					if(checkStr(sumTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML)==true){
						msgBox.tip({
							type : "fail",
							content : "工作总结表单第 "+ i + " 条<br>完成标准中含有特殊字符<br>'|'。"
						});
						return false;
					}
				}
				//完成标准特殊字符校验  add by lihongyu at 2014-10-17  end
				
//				if(sumTable.rows[i].cells[3].childNodes[0].childNodes[0].value == ""){
				if(returnValue(sumTable.rows[i].cells[3].childNodes[0].id+"-"+sumTable.rows[i].cells[3].childNodes[0].id) == null){//验证负责人
					msgBox.tip({
						type : "fail",
						content : "请选择工作总结表单<br>第 "+ i + " 条负责人"
					});
					return false;
				}//负责人是否为空验证
				
				if(sumTable.rows[i].cells[4].childNodes[0].childNodes[0].value == ""){
					msgBox.tip({
						type : "fail",
						content : "请选择工作总结表单<br>第 "+ i + " 条开始时间"
					});
					return false;
				}//开始时间是否为空验证
				if(sumTable.rows[i].cells[5].childNodes[0].childNodes[0].value == ""){
					msgBox.tip({
						type : "fail",
						content : "请选择工作总结表单<br>第 "+ i + " 条完成时间"
					});	
					return false;
				}//完成时间是否为空验证
				if(sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value == "" ||
				   sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value == "0-100"){
					msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条比例"
					});
					return false;
				}//完成比例是否为空验证
				if(!isNum.test(sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value)||
						sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value > 100 ||
						sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value < 0 
					  ){
						msgBox.tip({
							type : "fail",
							content : "工作总结表单<br>第 "+ i + " 条比例填写错误"
						});
						return false;
				}//完成比例长度验证,0至100验证
				
				//未完成原因说明特殊字符校验  add by lihongyu at 2014-10-17 start
				if(sumTable.rows[i].cells[7].childNodes[0].childNodes[0].value != ""){//未完成原因说明
					if(checkStr(sumTable.rows[i].cells[7].childNodes[0].childNodes[0].innerHTML)==true){
						msgBox.tip({
							type : "fail",
							content : "工作总结表单第 "+ i + " 条<br>未完成原因说明中含有特殊<br>字符'|'。"
						});
						return false;
					}
				}
				//未完成原因说明特殊字符校验  add by lihongyu at 2014-10-17 end
				
	    	}else{
	    		//主要完成事项为空，其他不为空的情况验证
	    		if(sumTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML != ""){//验证完成标准
					msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			sumNoDataNum +=1;//完成标准列数据为空
	    		}
//	    		if(sumTable.rows[i].cells[3].childNodes[0].childNodes[0].value != ""){//验证负责人
	    		if(returnValue(sumTable.rows[i].cells[3].childNodes[0].id+"-"+sumTable.rows[i].cells[3].childNodes[0].id) != null){//验证负责人
	    			msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			sumNoDataNum +=1;//负责人列数据为空
	    		}
	    		if(sumTable.rows[i].cells[4].childNodes[0].childNodes[0].value != ""){//验证开始时间
					msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			sumNoDataNum +=1;//开始时间列数据为空
	    		}
	    		if(sumTable.rows[i].cells[5].childNodes[0].childNodes[0].value != ""){//验证完成时间
					msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			sumNoDataNum +=1;//完成时间列数据为空
	    		}
	    		if(sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value != "" && 
	    		   sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value != "0-100"){//验证完成比例
					msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			sumNoDataNum +=1;//完成比例列数据为空
	    		}
	    		if(sumTable.rows[i].cells[7].childNodes[0].childNodes[0].innerHTML != ""){//验证未完成原因
					msgBox.tip({
						type : "fail",
						content : "请填写工作总结表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			sumNoDataNum +=1;//未完成原因数据列为空
	    		}
	    		
	    		if(sumNoDataNum == 6){//如果当前行每一列数据都为空，则标记改行为空行,数值6为每行的列数
	    			noDataSumRows +=1;
	    		}
	    	}
	    }
	    
	    if(noDataSumRows == sumTableLength-1){//如果空行数数值等于当前table对象的行数
			msgBox.tip({
				type : "fail",
				content : "请填写工作总结表单数据"
			});
	    	return false;
	    }
	    
	    //计划详细信息验证
	    for(var i=1;i<planTableLength;i++){
	    	var planNoDataNum = 0;//空值计数
	    	//主要完成事项不为空,其他填写项必须不为空
	    	if(planTable.rows[i].cells[1].childNodes[0].childNodes[0].innerHTML != ""){
	    		
	    		//主要完成事项特殊字符校验  add by lihongyu at 2014-10-17 start
	    		if(checkStr(planTable.rows[i].cells[1].childNodes[0].childNodes[0].innerHTML)==true){
					msgBox.tip({
						type : "fail",
						content : "工作计划表单第 "+ i + " 条<br>主要完成事项中含有特殊<br>字符'|'。"
					});
					return false;
				}
	    		//主要完成事项特殊字符校验  add by lihongyu at 2014-10-17 end
	    		
	    		if(planTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML == ""){
					msgBox.tip({
						type : "fail",
						content : "请填写工作计划表单<br>第 "+ i + " 条完成标准"
					});
	    			return false;
	    		}//完成标准是否为空验证
	    		
	    		//完成标准特殊字符校验  add by lihongyu at 2014-10-17 start
				else{
					if(checkStr(planTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML)==true){
						msgBox.tip({
							type : "fail",
							content : "工作计划表单第 "+ i + " 条<br>完成标准中含有特殊字符<br>'|'。"
						});
						return false;
					}
				}
				//完成标准特殊字符校验  add by lihongyu at 2014-10-17  end
	    		
//				if(planTable.rows[i].cells[3].childNodes[0].childNodes[0].value == ""){
	    		if(returnValue(planTable.rows[i].cells[3].childNodes[0].id+"-"+planTable.rows[i].cells[3].childNodes[0].id) == null){//验证负责人
					msgBox.tip({
						type : "fail",
						content : "请选择工作计划表单<br>第 "+ i + " 条负责人"
					});
					return false;
				}//负责人是否为空验证
				if(planTable.rows[i].cells[4].childNodes[0].childNodes[0].value == ""){
					msgBox.tip({
						type : "fail",
						content : "请选择工作计划表单<br>第 "+ i + " 条开始时间"
					});	
					return false;
				}//计划开始时间是否为空验证
				if(planTable.rows[i].cells[5].childNodes[0].childNodes[0].value == ""){
					msgBox.tip({
						type : "fail",
						content : "请选择工作计划表单<br>第 "+ i + " 条完成时间"
					});
					return false;
				}//计划完成时间是否为空验证
				if(planTable.rows[i].cells[6].childNodes[0].childNodes[0].value == "" ||
				   planTable.rows[i].cells[6].childNodes[0].childNodes[0].value == "0-100"){
					msgBox.tip({
						type : "fail",
						content : "请填写工作计划表单<br>第 "+ i + " 条比例"
					});
					return false;
				}//完成比例是否为空验证
				
				if(!isNum.test(planTable.rows[i].cells[6].childNodes[0].childNodes[0].value)||
						planTable.rows[i].cells[6].childNodes[0].childNodes[0].value > 100 ||
						planTable.rows[i].cells[6].childNodes[0].childNodes[0].value < 0 
					  ){
					msgBox.tip({
						type : "fail",
						content : "工作计划表单<br>第 "+ i + " 条比例填写错误"
					});
					return false;
				}//完成比例长度验证	
	    	}else{
	    		if(planTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML != ""){
					msgBox.tip({
						type : "fail",
						content : "请填写工作计划表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			planNoDataNum +=1;//完成标准列数据为空
	    		}//完成标准验证
//	    		if(planTable.rows[i].cells[3].childNodes[0].childNodes[0].value != ""){
	    		if(returnValue(planTable.rows[i].cells[3].childNodes[0].id+"-"+planTable.rows[i].cells[3].childNodes[0].id) != null){//验证负责人
					msgBox.tip({
						type : "fail",
						content : "请填写工作计划表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			planNoDataNum +=1;//负责人列数据为空
	    		}//负责人验证
	    		if(planTable.rows[i].cells[4].childNodes[0].childNodes[0].value != ""){
					msgBox.tip({
						type : "fail",
						content : "请填写工作计划表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			planNoDataNum +=1;//开始时间列数据为空
	    		}//计划开始时间验证
	    		if(planTable.rows[i].cells[5].childNodes[0].childNodes[0].value != ""){
					msgBox.tip({
						type : "fail",
						content : "请填写工作计划表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			planNoDataNum +=1;//完成时间列数据为空
	    		}//计划完成时间验证
	    		if(planTable.rows[i].cells[6].childNodes[0].childNodes[0].value != "" &&
	    		   planTable.rows[i].cells[6].childNodes[0].childNodes[0].value != "0-100"){
					msgBox.tip({
						type : "fail",
						content : "请填写工作计划表单<br>第 "+ i + " 条主要完成事项"
					});
	    			return false;
	    		}else{
	    			planNoDataNum +=1;//完成比例列数据为空
	    		}//完成比例验证
	    		if(planNoDataNum == 5){//如果当前行每一列数据都为空，则标记改行为空行,数值5为每行的列数
	    			noDataPlanRows +=1;
	    		}
	    	}
    	}
	    if(noDataPlanRows == planTableLength-1){//如果空行数数值等于当前table对象的行数
			msgBox.tip({
				type : "fail",
				content : "请填写工作计划表单数据"
			});
	    	return false;
	    }   
    }
	
    return true;
}

function compRateValid(){//比例验证
    var sumTable = document.getElementById('preSum');//获得工作总结table对象
    var planTable = document.getElementById('prePlan');//获得工作计划table对象
    var sumTableLength = sumTable.rows.length;//获得工作总结table对象长度
    var planTableLength = planTable.rows.length;//获得工作计划table对象长度
	var isNum = /^[0-9]*[0-9][0-9]*$/;//是否为数字输入验证
    for(var i=1;i<sumTableLength;i++){
    	if(typeof(sumTable.rows[i].cells[6].childNodes[0].childNodes[0])!= 'undefined'){
    		if(sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value !="0-100"){
    			if(!isNum.test(sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value)||
    					sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value > 100 ||
    					sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value < 0 
    				  ){
    					msgBox.tip({
    						type : "fail",
    						content : "工作总结表单<br>第 "+ i + " 条比例填写错误"
    					});
    					return false;
    			}//完成比例长度验证,0至100验证
    		}
    	}else{
    		return true;
    	}
    }

    for(var i=1;i<planTableLength;i++){
    	if(typeof(planTable.rows[i].cells[6].childNodes[0].childNodes[0])!= 'undefined'){
			if(planTable.rows[i].cells[6].childNodes[0].childNodes[0].value !="0-100"){
				if(!isNum.test(planTable.rows[i].cells[6].childNodes[0].childNodes[0].value)||
						planTable.rows[i].cells[6].childNodes[0].childNodes[0].value > 100 ||
						planTable.rows[i].cells[6].childNodes[0].childNodes[0].value < 0 
					  ){
					msgBox.tip({
						type : "fail",
						content : "工作计划表单<br>第 "+ i + " 条比例填写错误"
					});
					return false;
				}//完成比例长度验证
			}
    	}else{
    		return true;
    	}
    }
	return true;
}

//自定义校验  add by lihongyu at 2014-10-27 11:32
function checkStr(s){ 
	var rs =false;
	if(s.indexOf('|')!=-1){
		rs=true;
	}else{
		rs=false;
	}
	return rs; 
};