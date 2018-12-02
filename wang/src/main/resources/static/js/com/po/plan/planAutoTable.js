var columnValSum=0;//默认总结行号
var columnValPlan=30;//默认计划行号
var sumNum=0;//默认行号
var codeVal=0;
var tablePreSumId;//table id
var tablePrePlanId;//table id
var isCode=false;//是否生成序号
var codeValSum=0;//总结显示行号
var codeValPlan=0;//计划显示行号
var tipNum=0;//计划提示次数 (李洪宇 于 2014-07-24 添加)
var tipNumZC=0;//总结提示次数 (李洪宇 于 2014-07-24 添加)
//总结动态添加方法
function autoCreateColumnCode(temp,ValueTemp){//首次进入页面后，自动创建行(序号)
	var targetTable = document.getElementById('preSum');
	if(temp>0){
		var rowHtml=[];
		for(var i=0;i<temp;i++){
			columnValSum +=1;
		    codeValSum +=1;
			rowHtml.push("<tr id='"+columnValSum+"'>");
			rowHtml.push("<td id='"+ columnValSum +"'>"+ columnValSum +"</td>");
			rowHtml.push(autoContentSum);
//			rowHtml +=autoCreateRowsSum(autoContentSum);
			rowHtml.push("</tr>");
		}
		var temp = rowHtml.join('');
		$("#"+tablePreSumId).append(temp);
		//参数1：绑定需要动态添加行的样式;参数2：创建事件;参数3：方法名称
		$('tbody').delegate('.autoSumDiv','blur',function cm(){
			var t=$(this).parent().parent().parent().attr("id");
			creColumnCode(t);//创建行(序号)
			if($("#preSum_0_"+t).html()=="" && returnValue("preSum_2_"+t+"-preSum_2_"+t) ==null){
				$("#preSum_2_"+t+"-preSum_2_"+t).select2("data",{"id":$("#loginUserId").val(),"text":$("#currentUser").val()});
				$("#preSum_10_"+t).val($("#loginUserId").val());	
			}
			$("[data-toggle=tooltip]").tooltip();//显示删除标签
			$(".datepicker-input-plan").each(function(){
				var $this = $(this);
				$this.on("click",function(){
					var data = $this.data('datetimepicker');
					if (!data) {
						$this.datepicker();
						data = $this.data('datetimepicker');
					}
					data.show();
				});
			});
			iePlaceholderPatch();//水印处理
		});
		
		flushSumRow(targetTable,targetTable.rows.length);//刷新行数据
	}
}

//计划动态添加方法
function autoCreateColumn(temp,ValueTemp){//首次进入页面后，自动创建行
    var targetTable = document.getElementById('prePlan');
	if(temp>0){
		var rowHtml=[];
		for(var i=0;i<temp;i++){
			codeValPlan +=1;
		    columnValPlan +=1;
			rowHtml.push("<tr id='"+columnValPlan+"'>");
			rowHtml.push("<td>"+codeValPlan+"</td>");
			rowHtml.push(autoContentPlan);
//			rowHtml +=autoCreateRowsPlan(autoContentPlan);
			rowHtml.push("</tr>");
		}
		var temp = rowHtml.join('');
		$("#"+tablePrePlanId).append(temp);
		$('tbody').delegate('.autoPlanDiv','blur',function cm(){
			var t=$(this).parent().parent().parent().attr("id");
			creColumn(t);//创建行(序号)
			if($("#prePlan_0_"+t).html()=="" && returnValue("prePlan_2_"+t+"-prePlan_2_"+t) ==null){
				$("#prePlan_2_"+t+"-prePlan_2_"+t).select2("data",{"id":$("#loginUserId").val(),"text":$("#currentUser").val()});
				$("#prePlan_9_"+t).val($("#loginUserId").val());
			}
			$("[data-toggle=tooltip]").tooltip();//显示删除标签
			$(".datepicker-input-plan").each(function(){
				var $this = $(this);
				$this.on("click",function(){
					var data = $this.data('datetimepicker');
					if (!data) {
						$this.datepicker();
						data = $this.data('datetimepicker');
					}
					data.show();
				});
			});
			iePlaceholderPatch();//水印处理
		});
		flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
	}
}

//总结自动创建列
function autoCreateRowsSum(rowNames){
   	var roHtml=[];
	   if(rowNames!=null && rowNames !=""){
	   		var arr=rowNames.split("@");
	   		var arrLength = arr.length;
	   		if(arr !=null && arrLength >0){
	   			for(var m = 0; m < arrLength; m++){
   					roHtml.push(arr[m]);
/*   				
	   				if(roHtml.indexOf("tempId") > 0){
			   		   roHtml = roHtml.replace("tempId","preSum_"+m+"_"+ columnValSum);
			   		   roHtml = roHtml.replace("tempName","preSum_"+m+"_"+ columnValSum);
			   		   roHtml = roHtml.replace("itemIdTemp","preSum_"+m+"_"+ columnValSum+"-preSum_"+m+"_"+ columnValSum);
			   		}

	   			    if(roHtml.indexOf("sumDetailStartTime") > 0){
			   		   roHtml = roHtml.replace("sumDetailStartTime","preSum_"+(m+1)+"_"+ columnValSum);
			   		}
	   			    if(roHtml.indexOf("sumDetailEndTime") > 0){
				   	   roHtml = roHtml.replace("sumDetailEndTime","preSum_"+(m-1)+"_"+ columnValSum);
				   	}	
	   			    if(roHtml.indexOf("impPlan") > 0){
			   		   roHtml = roHtml.replace("impPlan","preSum_"+m+"_"+ columnValSum);
			   		} 
	   			    if(roHtml.indexOf("delSum") > 0){
	   			       roHtml = roHtml.replace("delSum","preSum_"+(m+1)+"_"+ columnValSum);
	   			    }
	   			    if(roHtml.indexOf("sumHid") > 0){
			   		   roHtml = roHtml.replace("sumHid","preSum_"+(m+2)+"_"+ columnValSum);
			   		}
	   			    if(roHtml.indexOf("sumDirectorId") > 0){
				   	   roHtml = roHtml.replace("sumDirectorId","preSum_"+(m+3)+"_"+ columnValSum);
				   	}
*/				   	
	   			}
	   		}
	   }
	   roHtml = roHtml.join('');
	   return roHtml;
}

//计划自动创建列
function autoCreateRowsPlan(rowNames){
   	var roHtml=[];
	   if(rowNames!=null && rowNames !=""){
	   		var arr=rowNames.split("@");
	   		var arrLength = arr.length;
	   		if(arr !=null && arrLength >0){
	   			for(var m=0;m<arrLength;m++){
	   				roHtml.push(arr[m]);
/*	   				
//	   				if(roHtml.indexOf("impDiary") > 0 || roHtml.indexOf("delPlan") > 0){
//	   					
//			   		} else {
//			   			roHtml+=" id='prePlan_"+m+"_"+ columnValPlan +"' name='prePlan_"+m+"_"+ columnValPlan +"' /></span></td>";
//			   		}
	   				if(roHtml.indexOf("tempId") > 0){
			   		   roHtml = roHtml.replace("tempId","prePlan_"+m+"_"+ columnValPlan);
			   		   roHtml = roHtml.replace("tempName","prePlan_"+m+"_"+ columnValPlan);
			   		   roHtml = roHtml.replace("itemIdTemp","prePlan_"+m+"_"+ columnValPlan+"-prePlan_"+m+"_"+ columnValPlan);
				   	}
	   				
	   			    if(roHtml.indexOf("planDetailStartTime") > 0){
			   		   roHtml = roHtml.replace("planDetailStartTime","prePlan_"+(m+1)+"_"+ columnValPlan);
			   		}
	   			    if(roHtml.indexOf("planDetailEndTime") > 0){
				   	   roHtml = roHtml.replace("planDetailEndTime","prePlan_"+(m-1)+"_"+ columnValPlan);
				   	}	
	   			    if(roHtml.indexOf("impDiary") > 0){
			   			   roHtml = roHtml.replace("impDiary","prePlan_"+m+"_"+ columnValPlan);
			   		}
				    if(roHtml.indexOf("delPlan") > 0){
				       roHtml = roHtml.replace("delPlan","prePlan_"+(m+1)+"_"+ columnValPlan);
				    }
				    if(roHtml.indexOf("planHid") > 0){
			   		   roHtml = roHtml.replace("planHid","prePlan_"+(m+2)+"_"+ columnValPlan);
			   		}
	   			    if(roHtml.indexOf("planDirectorId") > 0){
			   		   roHtml = roHtml.replace("planDirectorId","prePlan_"+(m+3)+"_"+ columnValPlan);
				   	}
	   			    if(roHtml.indexOf("issuedWorkTask") > 0){
		   			   roHtml = roHtml.replace("issuedWorkTask","prePlan_"+(m+4)+"_"+ columnValPlan);
	   			    } 
*/	   			    	   			    
	   			}
			}
	   }
	   roHtml = roHtml.join('');
	return roHtml;
}



//初始化方法
//isCodeTemp:是否生成序号,tableId:表id，autoNumValTemp:首次进行页面自动创建行数,autoAddValTemp:自动增加行数,autoContentTemp:自定义列内容
function indexFun(isCodeTemp,tableIdTemp,autoNumValTemp,autoAddValTemp,autoContentTemp){
	if(autoNumValTemp!=null || autoNumValTemp!=''){
		autoNumVal=autoNumValTemp;
	}
	if(autoAddValTemp!=null || autoAddValTemp!=''){
		autoAddVal=autoAddValTemp;
	}
	if(isCodeTemp!=null || isCodeTemp!=''){
		isCode=isCodeTemp;
	}
	if(isCode){
		tablePreSumId=tableIdTemp;
		autoContentSum=autoContentTemp;
		autoCreateColumnCode(autoNumVal,6);
	}else{
		tablePrePlanId = tableIdTemp;
		autoContentPlan=autoContentTemp;
		autoCreateColumn(autoNumVal,6);
	}
}

/**
 * 总结动态添加行
 */
function creColumnCode(temp){//创建行(序号)
	var targetTable = document.getElementById('preSum');
	if(targetTable.rows.length -1 > 29){//记录到达上限
		//李洪宇 于 2014-07-24 修改 开始	
		if(tipNumZC==0){
			tipNumZC=1;
//			msgBox.tip({
//				content: $.i18n.prop("JC_OA_PO_006")
//			});
			return false;
		}
		//李洪宇 于 2014-07-24 修改 结束
	}else{
	    var id = $("#"+tablePreSumId).find("tr:last").attr("id");
		if(temp==id){
			var preSumArray = [];
			var rowHtml="";
			for(var n=0;n<autoAddVal;n++){
				columnValSum += 1;//真实行号id和行号显示
				codeValSum = (eval(id)+1);//显示行号id和行号
				rowHtml ="";
				rowHtml +="<tr id='"+codeValSum+"'>";
				rowHtml +="<td id='"+ columnValSum +"'>"+codeValSum+"</td>";
				rowHtml +=autoCreateRowsSum(autoContentSum);
				rowHtml +="</tr>";
//				$("#"+tablePreSumId).append(rowHtml);
				preSumArray[n] = rowHtml;
			}
			$("#"+tablePreSumId).append(preSumArray.join(''));
			$('tbody').delegate('.autoSumDiv','blur',function cm(){
			    var t=$(this).parent().parent().attr("id");
	 			creColumnCode(t);
	 			iePlaceholderPatch();//水印处理
			});
		}
		flushSumRow(targetTable,targetTable.rows.length);
	}
}

/**
 * 计划动态添加行
 */
function creColumn(temp){//创建行
	var targetTable = document.getElementById('prePlan');
	if(targetTable.rows.length -1 > 29){//记录到达上限
	//李洪宇 于 2014-07-24 修改 开始	
		if(tipNum==0){
			tipNum=1;
//			msgBox.tip({
//				content: $.i18n.prop("JC_OA_PO_006")
//			});
			return false;
		}
		//李洪宇 于 2014-07-24 修改 结束
	}else{
	    var id = $("#"+tablePrePlanId).find("tr:last").attr("id");
		if(temp==id){
			var prePlanArray = [];
			var rowHtml="";
			for(var n=0;n<autoAddVal;n++){
				columnValPlan += 1;//真实行号id和行号显示
				codeValPlan = (eval(id)-29);//显示行号id和行号
				rowHtml ="";
				rowHtml +="<tr id='"+columnValPlan+"'>";
				rowHtml +="<td>"+codeValPlan+"</td>";
				rowHtml +=autoCreateRowsPlan(autoContentPlan);
				rowHtml +="</tr>";
//				$("#"+tablePrePlanId).append(rowHtml);
				prePlanArray[n] = rowHtml;
			}
			$("#"+tablePrePlanId).append(prePlanArray.join(''));
			$('tbody').delegate('.autoPlanDiv','blur',function cm(){
			    var t=$(this).parent().parent().attr("id");
			    creColumn(t);
			    iePlaceholderPatch();//水印处理
			});
		}
	    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
	}
}

/**
 * 总结动态添加行删除方法
 */
function deleteSumRow(e){
	var evt = e||event;
	if(isFF()){
		var eventTarget = evt.target;
	}else{
		var eventTarget = evt.srcElement?evt.srcElement:event.target;	
	}
	$(eventTarget).tooltip("hide");//隐藏删除标签提示
	
    var targetTable = document.getElementById('preSum');
    var rowCount = targetTable.rows.length - 1;
    if(rowCount > 1){
        targetTable.deleteRow(eventTarget.parentNode.parentNode.parentNode.cells[0].innerHTML);
        flushSumRowDelete(targetTable,targetTable.rows.length);
        tipNumZC=0;//李洪宇 于2014-07-24
    }else{
		msgBox.info({
			content: $.i18n.prop("JC_OA_PO_005")
		});
    }
}

/**
 * 计划动态添加行删除方法
 */
function deletePlanRow(e){
	var evt = e||event;
	if(isFF()){
		var eventTarget = evt.target;
	}else{
		var eventTarget = evt.srcElement?evt.srcElement:event.target;	
	}
	$(eventTarget).tooltip("hide");//隐藏删除标签提示
	
    var targetTable = document.getElementById('prePlan');
    var rowCount = targetTable.rows.length - 1;
    if(rowCount > 1){
        targetTable.deleteRow(eventTarget.parentNode.parentNode.parentNode.cells[0].innerHTML);
        flushPlanRowDelete(targetTable,targetTable.rows.length);
        tipNum=0;//李洪宇 于2014-07-24
    }else{
		msgBox.info({
			content: $.i18n.prop("JC_OA_PO_005")
		});
    }
}

/**
 * 总结动态添加行刷新方法
 */
function flushSumRow(targetTable,rowLength){
    for (var i = 1; i < rowLength; i++) {
    	$("#preSum tr:eq("+ i +")").get(0).id = i;
    	$("#preSum tr:eq("+ i +") td:eq(0)").get(0).innerHTML = i;
    	$("#preSum tr:eq("+ i +") td:eq(0)").get(0).id = i;
    	$("#preSum tr:eq("+ i +") td:eq(1) div").get(0).id = "preSum_0_"+ i;//主要完成事项ID
    	$("#preSum tr:eq("+ i +") td:eq(1) div").attr("name","preSum_0_"+ i);//主要完成事项NAME
    	$("#preSum tr:eq("+ i +") td:eq(2) div").get(0).id = "preSum_1_"+ i;//完成标准ID
    	$("#preSum tr:eq("+ i +") td:eq(2) div").attr("name","preSum_1_"+ i);//完成标准NAME
    	$("#preSum tr:eq("+ i +") td:eq(3) div").get(0).id = "preSum_2_"+ i;//负责人ID
    	$("#preSum tr:eq("+ i +") td:eq(3) div").attr("name","preSum_2_"+ i);//负责人NAME
    	$("#preSum tr:eq("+ i +") td:eq(3) div").attr("itemId","preSum_2_"+ i + "-preSum_2_" + i);//负责人TEMPID
    	if(returnValue("preSum_2_"+ i +"-preSum_2_"+ i)==null){
    		selectControl.init("preSum_2_"+ i,"preSum_2_"+ i +"-preSum_2_"+ i, false, true, null, null);    	
    	}    	
    	$("#preSum tr:eq("+ i +") td:eq(4) input").get(0).id = "preSum_3_"+ i;//开始时间ID
    	$("#preSum tr:eq("+ i +") td:eq(4) input").attr("name","preSum_3_"+ i);//开始时间NAME
    	$("#preSum tr:eq("+ i +") td:eq(4) input").get(0).setAttribute("data-ref-obj","#preSum_4_" + i + " lt");//开始时间对应的结束时间   	
    	$("#preSum tr:eq("+ i +") td:eq(5) input").get(0).id = "preSum_4_"+ i;//结束时间ID
    	$("#preSum tr:eq("+ i +") td:eq(5) input").attr("name","preSum_4_"+ i);//结束时间NAME
    	$("#preSum tr:eq("+ i +") td:eq(5) input").get(0).setAttribute("data-ref-obj","#preSum_3_" + i + " gt");//开始时间对应的结束时间
    	$("#preSum tr:eq("+ i +") td:eq(6) input").get(0).id = "preSum_5_"+ i;//完成比例ID
    	$("#preSum tr:eq("+ i +") td:eq(6) input").attr("name","preSum_5_"+ i);//完成比例NAME
        $("#preSum tr:eq("+ i +") td:eq(7) div").get(0).id = "preSum_6_"+ i;
    	$("#preSum tr:eq("+ i +") td:eq(7) div").attr("name","preSum_6_"+ i);
    	$("#preSum tr:eq("+ i +") td:eq(8) a").get(0).id = "preSum_7_"+ i;
    	$("#preSum tr:eq("+ i +") td:eq(8) i").get(0).id = "preSum_8_"+ i;
        $("#preSum tr:eq("+ i +") td:eq(8) input").get(0).id = "preSum_9_"+ i;
    	$("#preSum tr:eq("+ i +") td:eq(8) input").get(1).id = "preSum_10_"+ i;
//    	console.log($("#preSum tr:eq("+ i +") td:eq(8) input").get(0))
    	
//    	var targetTableRows = targetTable.rows[i];
//    	targetTableRows.cells[0].innerHTML = i;
//    	targetTableRows.cells[0].id = i;
//    	targetTableRows.id = i;
//    	targetTableRows.cells[1].childNodes[0].childNodes[0].id = "preSum_0_"+ i;//主要完成事项ID
//    	targetTableRows.cells[1].childNodes[0].childNodes[0].name = "preSum_0_"+ i;//主要完成事项NAME
//    	targetTableRows.cells[2].childNodes[0].childNodes[0].id = "preSum_1_"+ i;//完成标准ID
//    	targetTableRows.cells[2].childNodes[0].childNodes[0].name = "preSum_1_"+ i;//完成标准NAME
//    	targetTableRows.cells[3].childNodes[0].id = "preSum_2_"+ i;//负责人ID
//    	targetTableRows.cells[3].childNodes[0].name = "preSum_2_"+ i;//负责人NAME
//    	if(returnValue("preSum_2_"+ i +"-preSum_2_"+ i)==null){
//    		selectControl.init("preSum_2_"+ i,"preSum_2_"+ i +"-preSum_2_"+ i, false, true, null, null);    	
//    	}
//    	targetTableRows.cells[4].childNodes[0].childNodes[0].id = "preSum_3_"+ i;//开始时间ID
//    	targetTableRows.cells[4].childNodes[0].childNodes[0].name = "preSum_3_"+ i;//开始时间NAME
//    	targetTableRows.cells[4].childNodes[0].childNodes[0].setAttribute("data-ref-obj","#preSum_4_" + i + " lt");//开始时间对应的结束时间
//    	targetTableRows.cells[5].childNodes[0].childNodes[0].id = "preSum_4_"+ i;//结束时间ID
//    	targetTableRows.cells[5].childNodes[0].childNodes[0].name = "preSum_4_"+ i;//结束时间NAME
//    	targetTableRows.cells[5].childNodes[0].childNodes[0].setAttribute("data-ref-obj","#preSum_3_" + i + " gt");//开始时间对应的结束时间
//      targetTableRows.cells[6].childNodes[0].childNodes[0].id = "preSum_5_"+ i;//完成比例ID
//      targetTableRows.cells[6].childNodes[0].childNodes[0].name = "preSum_5_"+ i;//完成比例NAME
//    	targetTableRows.cells[7].childNodes[0].childNodes[0].id = "preSum_6_"+ i;
//    	targetTableRows.cells[7].childNodes[0].childNodes[0].name = "preSum_6_"+ i;
//    	targetTableRows.cells[8].childNodes[0].id = "preSum_7_"+ i;
//    	targetTableRows.cells[8].childNodes[1].childNodes[0].id = "preSum_8_"+ i;
//    	targetTableRows.cells[8].childNodes[2].id = "preSum_9_"+ i;
//    	targetTableRows.cells[8].childNodes[3].id = "preSum_10_"+ i;
//    	($(targetTable.rows[i])[0]).children[9].id = "preSum_10_" + i;
    }
}

/**
 * 总结动态添加行刷新方法
 */
function flushSumRowDelete(targetTable,rowLength){
    for (var i = 1; i < rowLength; i++) {
    	$("#preSum tr:eq("+ i +")").get(0).id = i;
    	$("#preSum tr:eq("+ i +") td:eq(0)").get(0).innerHTML = i;
    	$("#preSum tr:eq("+ i +") td:eq(0)").get(0).id = i;
    	$("#preSum tr:eq("+ i +") td:eq(1) div").get(0).id = "preSum_0_"+ i;//主要完成事项ID
    	$("#preSum tr:eq("+ i +") td:eq(1) div").attr("name","preSum_0_"+ i);//主要完成事项NAME
    	$("#preSum tr:eq("+ i +") td:eq(2) div").get(0).id = "preSum_1_"+ i;//完成标准ID
    	$("#preSum tr:eq("+ i +") td:eq(2) div").attr("name","preSum_1_"+ i);//完成标准NAME
    	$("#preSum tr:eq("+ i +") td:eq(3) div").get(0).id = "preSum_2_"+ i;//负责人ID
    	$("#preSum tr:eq("+ i +") td:eq(3) div").attr("name","preSum_2_"+ i);//负责人NAME
    	$("#preSum tr:eq("+ i +") td:eq(3) div").attr("itemId","preSum_2_"+ i + "-preSum_2_" + i);//负责人TEMPID
    	$("#preSum tr:eq("+ i +") td:eq(3) input[type=hidden]").get(0).id = "preSum_2_"+ i +"-"+"preSum_2_"+ i;//负责人隐藏域ID
    	$("#preSum tr:eq("+ i +") td:eq(3) input[type=hidden]").attr("name","preSum_2_"+ i);
    	var userIdTemp = returnValue($("#preSum tr:eq("+ i +") td:eq(3) div").get(0).id +"-"+$("#preSum tr:eq("+ i +") td:eq(3) div").get(0).id);
    	if(userIdTemp!=null){
        	var userId = userIdTemp.split(":");
        	$("#preSum_2_"+i+"-preSum_2_"+i).select2("data",{"id":userId[0],"text":userId[1]});
    	}else{
    		$("#preSum_2_"+i+"-preSum_2_"+i).select2("data",null);
    	} 	
    	$("#preSum tr:eq("+ i +") td:eq(4) input").get(0).id = "preSum_3_"+ i;//开始时间ID
    	$("#preSum tr:eq("+ i +") td:eq(4) input").attr("name","preSum_3_"+ i);//开始时间NAME
    	$("#preSum tr:eq("+ i +") td:eq(4) input").get(0).setAttribute("data-ref-obj","#preSum_4_" + i + " lt");//开始时间对应的结束时间   	
    	$("#preSum tr:eq("+ i +") td:eq(5) input").get(0).id = "preSum_4_"+ i;//结束时间ID
    	$("#preSum tr:eq("+ i +") td:eq(5) input").attr("name","preSum_4_"+ i);//结束时间NAME
    	$("#preSum tr:eq("+ i +") td:eq(5) input").get(0).setAttribute("data-ref-obj","#preSum_3_" + i + " gt");//开始时间对应的结束时间
    	$("#preSum tr:eq("+ i +") td:eq(6) input").get(0).id = "preSum_5_"+ i;//完成比例ID
    	$("#preSum tr:eq("+ i +") td:eq(6) input").attr("name","preSum_5_"+ i);//完成比例NAME
        $("#preSum tr:eq("+ i +") td:eq(7) div").get(0).id = "preSum_6_"+ i;
    	$("#preSum tr:eq("+ i +") td:eq(7) div").attr("name","preSum_6_"+ i);
    	$("#preSum tr:eq("+ i +") td:eq(8) a").get(0).id = "preSum_7_"+ i;
    	$("#preSum tr:eq("+ i +") td:eq(8) i").get(0).id = "preSum_8_"+ i;
        $("#preSum tr:eq("+ i +") td:eq(8) input").get(0).id = "preSum_9_"+ i;
    	$("#preSum tr:eq("+ i +") td:eq(8) input").get(1).id = "preSum_10_"+ i;
    }
}

/**
 * 计划动态添加行刷新方法
 */
function flushPlanRow(targetTable,rowLength){
    for (var i = 1; i < rowLength; i++) {
    	$("#prePlan tr:eq("+ i +")").get(0).id = i + 30;
    	$("#prePlan tr:eq("+ i +") td:eq(0)").get(0).innerHTML = i;
    	$("#prePlan tr:eq("+ i +") td:eq(0)").get(0).id = i + 30;
    	$("#prePlan tr:eq("+ i +") td:eq(1) div").get(0).id = "prePlan_0_"+ (i + 30);//主要完成事项ID
    	$("#prePlan tr:eq("+ i +") td:eq(1) div").attr("name","prePlan_0_"+ (i + 30));//主要完成事项NAME
    	$("#prePlan tr:eq("+ i +") td:eq(2) div").get(0).id = "prePlan_1_"+ (i + 30);//完成标准ID
    	$("#prePlan tr:eq("+ i +") td:eq(2) div").attr("name","prePlan_1_"+ (i + 30));//完成标准NAME
    	$("#prePlan tr:eq("+ i +") td:eq(3) div").get(0).id = "prePlan_2_"+ (i + 30);//负责人ID
    	$("#prePlan tr:eq("+ i +") td:eq(3) div").attr("name","prePlan_2_"+ (i + 30));//负责人NAME
    	$("#prePlan tr:eq("+ i +") td:eq(3) div").attr("itemId","prePlan_2_"+ (i + 30) + "-prePlan_2_" + (i + 30));//负责人TEMPID
	    if(returnValue("prePlan_2_"+ (i + 30) +"-prePlan_2_"+ (i + 30))==null){
			selectControl.init("prePlan_2_"+ (i + 30),"prePlan_2_"+ (i + 30) +"-prePlan_2_"+ (i + 30), false, true, null, null);    	
		}else{
	    	var userIdTemp = returnValue($("#prePlan tr:eq("+ i +") td:eq(3) div").get(0).id +"-"+$("#prePlan tr:eq("+ i +") td:eq(3) div").get(0).id);
	    	if(userIdTemp!=null){
	        	var userId = userIdTemp.split(":");
	        	$("#prePlan_2_"+(i + 30)+"-prePlan_2_"+(i + 30)).select2("data",{"id":userId[0],"text":userId[1]});
	    	}
		} 	
    	$("#prePlan tr:eq("+ i +") td:eq(4) input").get(0).id = "prePlan_3_"+ (i + 30);//开始时间ID
    	$("#prePlan tr:eq("+ i +") td:eq(4) input").attr("name","prePlan_3_"+ (i + 30));//开始时间NAME
    	$("#prePlan tr:eq("+ i +") td:eq(4) input").get(0).setAttribute("data-ref-obj","#prePlan_4_" + (i + 30) + " lt");//开始时间对应的结束时间   	
    	$("#prePlan tr:eq("+ i +") td:eq(5) input").get(0).id = "prePlan_4_"+ (i + 30);//结束时间ID
    	$("#prePlan tr:eq("+ i +") td:eq(5) input").attr("name","prePlan_4_"+ (i + 30));//结束时间NAME
    	$("#prePlan tr:eq("+ i +") td:eq(5) input").get(0).setAttribute("data-ref-obj","#prePlan_3_" + (i + 30) + " gt");//开始时间对应的结束时间
    	$("#prePlan tr:eq("+ i +") td:eq(6) input").get(0).id = "prePlan_5_"+ (i + 30);//完成比例ID
    	$("#prePlan tr:eq("+ i +") td:eq(6) input").attr("name","prePlan_5_"+ (i + 30));//完成比例NAME
        $("#prePlan tr:eq("+ i +") td:eq(7) a").get(0).id = "prePlan_6_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) i").get(0).id = "prePlan_7_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) a").get(2).id = "prePlan_10_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) input").get(0).id = "prePlan_8_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) input").get(1).id = "prePlan_9_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) input").attr("name","prePlan_9_"+ (i + 30));
    	
//    	var targetTableRows = targetTable.rows[i];
//    	targetTableRows.cells[0].innerHTML = i;
//    	targetTableRows.cells[0].id = i + 30;
//    	targetTableRows.id = i + 30;
//    	targetTableRows.cells[1].childNodes[0].childNodes[0].id = "prePlan_0_"+ (i + 30);
//    	targetTableRows.cells[1].childNodes[0].childNodes[0].name = "prePlan_0_"+ (i + 30);
//    	targetTableRows.cells[2].childNodes[0].childNodes[0].id = "prePlan_1_"+ (i + 30);
//    	targetTableRows.cells[2].childNodes[0].childNodes[0].name = "prePlan_1_"+ (i + 30);
//    	targetTableRows.cells[3].childNodes[0].id = "prePlan_2_"+ (i + 30);
//    	targetTableRows.cells[3].childNodes[0].name = "prePlan_2_"+ (i + 30);
//    	if(returnValue("prePlan_2_"+ (i + 30) +"-prePlan_2_"+ (i + 30))==null){
//    		selectControl.init("prePlan_2_"+ (i + 30),"prePlan_2_"+ (i + 30) +"-prePlan_2_"+ (i + 30), false, true, null, null);    	
//    	}
//    	targetTableRows.cells[4].childNodes[0].childNodes[0].id = "prePlan_3_"+ (i + 30);
//    	targetTableRows.cells[4].childNodes[0].childNodes[0].name = "prePlan_3_"+ (i + 30);
//    	targetTableRows.cells[4].childNodes[0].childNodes[0].setAttribute("data-ref-obj","#prePlan_4_" + (i + 30) + " lt");//开始时间对应的结束时间
//    	targetTableRows.cells[5].childNodes[0].childNodes[0].id = "prePlan_4_"+ (i + 30);
//    	targetTableRows.cells[5].childNodes[0].childNodes[0].name = "prePlan_4_"+ (i + 30);
//    	targetTableRows.cells[5].childNodes[0].childNodes[0].setAttribute("data-ref-obj","#prePlan_3_" + (i + 30) + " gt");//开始时间对应的结束时间
//    	targetTableRows.cells[6].childNodes[0].childNodes[0].id = "prePlan_5_"+ (i + 30);//完成比例ID
//    	targetTableRows.cells[6].childNodes[0].childNodes[0].name = "prePlan_5_"+ (i + 30);//完成比例NAME
//    	targetTableRows.cells[7].childNodes[0].id = "prePlan_6_"+ (i + 30);
//    	targetTableRows.cells[7].childNodes[1].childNodes[0].id = "prePlan_7_"+ (i + 30);
//    	targetTableRows.cells[7].childNodes[2].id = "prePlan_10_"+ (i + 30);
//    	targetTableRows.cells[7].childNodes[3].id = "prePlan_8_"+ (i + 30);
//    	targetTableRows.cells[7].childNodes[4].id = "prePlan_9_"+ (i + 30);
//    	targetTableRows.cells[7].childNodes[4].name = "prePlan_9_"+ (i + 30);
    }
}

/**
 * 计划动态添加行刷新方法
 */
function flushPlanRowDelete(targetTable,rowLength){
    for (var i = 1; i < rowLength; i++) {
    	$("#prePlan tr:eq("+ i +")").get(0).id = i + 30;
    	$("#prePlan tr:eq("+ i +") td:eq(0)").get(0).innerHTML = i;
    	$("#prePlan tr:eq("+ i +") td:eq(0)").get(0).id = i + 30;
    	$("#prePlan tr:eq("+ i +") td:eq(1) div").get(0).id = "prePlan_0_"+ (i + 30);//主要完成事项ID
    	$("#prePlan tr:eq("+ i +") td:eq(1) div").attr("name","prePlan_0_"+ (i + 30));//主要完成事项NAME
    	$("#prePlan tr:eq("+ i +") td:eq(2) div").get(0).id = "prePlan_1_"+ (i + 30);//完成标准ID
    	$("#prePlan tr:eq("+ i +") td:eq(2) div").attr("name","prePlan_1_"+ (i + 30));//完成标准NAME
    	$("#prePlan tr:eq("+ i +") td:eq(3) div").get(0).id = "prePlan_2_"+ (i + 30);//负责人ID
    	$("#prePlan tr:eq("+ i +") td:eq(3) div").attr("name","prePlan_2_"+ (i + 30));//负责人NAME
    	$("#prePlan tr:eq("+ i +") td:eq(3) div").attr("itemId","prePlan_2_"+ (i + 30) + "-prePlan_2_" + (i + 30));//负责人TEMPID
    	$("#prePlan tr:eq("+ i +") td:eq(3) input[type=hidden]").get(0).id = "prePlan_2_"+ (i + 30) +"-"+"prePlan_2_"+ (i + 30);//负责人隐藏域ID
    	$("#prePlan tr:eq("+ i +") td:eq(3) input[type=hidden]").attr("name","prePlan_2_"+ (i + 30));
    	var userIdTemp = returnValue($("#prePlan tr:eq("+ i +") td:eq(3) div").get(0).id +"-"+$("#prePlan tr:eq("+ i +") td:eq(3) div").get(0).id);
    	if(userIdTemp!=null){
        	var userId = userIdTemp.split(":");
        	$("#prePlan_2_"+(i + 30)+"-prePlan_2_"+(i + 30)).select2("data",{"id":userId[0],"text":userId[1]});
//        	selectControl.init("prePlan_2_"+ (i + 30),"prePlan_2_"+ (i + 30) +"-prePlan_2_"+ (i + 30), false, true, null,{"id":userId[0],"text":userId[1]});
    	}else{
    		$("#prePlan_2_"+(i + 30)+"-prePlan_2_"+(i + 30)).select2("data",null);
//    		selectControl.init("prePlan_2_"+ (i + 30),"prePlan_2_"+ (i + 30) +"-prePlan_2_"+ (i + 30), false, true, null,null);
    	}
    	
    	$("#prePlan tr:eq("+ i +") td:eq(4) input").get(0).id = "prePlan_3_"+ (i + 30);//开始时间ID
    	$("#prePlan tr:eq("+ i +") td:eq(4) input").attr("name","prePlan_3_"+ (i + 30));//开始时间NAME
    	$("#prePlan tr:eq("+ i +") td:eq(4) input").get(0).setAttribute("data-ref-obj","#prePlan_4_" + (i + 30) + " lt");//开始时间对应的结束时间   	
    	$("#prePlan tr:eq("+ i +") td:eq(5) input").get(0).id = "prePlan_4_"+ (i + 30);//结束时间ID
    	$("#prePlan tr:eq("+ i +") td:eq(5) input").attr("name","prePlan_4_"+ (i + 30));//结束时间NAME
    	$("#prePlan tr:eq("+ i +") td:eq(5) input").get(0).setAttribute("data-ref-obj","#prePlan_3_" + (i + 30) + " gt");//开始时间对应的结束时间
    	$("#prePlan tr:eq("+ i +") td:eq(6) input").get(0).id = "prePlan_5_"+ (i + 30);//完成比例ID
    	$("#prePlan tr:eq("+ i +") td:eq(6) input").attr("name","prePlan_5_"+ (i + 30));//完成比例NAME
        $("#prePlan tr:eq("+ i +") td:eq(7) a").get(0).id = "prePlan_6_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) i").get(0).id = "prePlan_7_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) a").get(2).id = "prePlan_10_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) input").get(0).id = "prePlan_8_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) input").get(1).id = "prePlan_9_"+ (i + 30);
        $("#prePlan tr:eq("+ i +") td:eq(7) input").attr("name","prePlan_9_"+ (i + 30));
    }
}