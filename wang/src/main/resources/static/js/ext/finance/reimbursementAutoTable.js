//动态增加行，增加费用

var columnValSum=0;//默认行号
var columnValPlan=30;//默认行号
var sumNum=0;//默认行号
var codeVal=0;
var tablePreSumId;//table id
var tablePrePlanId;//table id
var isCode=false;//是否生成序号
var codeValSum=0;
var codeValPlan=0;

//【会议决议】动态添加方法
function autoCreateColumnCode(temp,ValueTemp){//首次进入页面后，自动创建行(序号)
	if(temp>0){
		for(var i=0;i<temp;i++){
			columnValSum +=1;
		    codeValSum +=1;
			var rowHtml="";
			rowHtml +="<tr id='"+columnValSum+"'>";
			rowHtml +="<td id='"+ columnValSum +"'>"+ columnValSum +"</td>";
			rowHtml+=autoCreateRowsSum(autoContentSum);
			rowHtml +="</tr>";
			$("#"+tablePreSumId).append(rowHtml);	
		}
		//alert("来了");
		//参数1：绑定需要动态添加行的样式;参数2：创建事件;参数3：方法名称
		$('tbody').delegate('.input-style','focus',function cm(){
			var t=$(this).parent().parent().attr("id");
			//创建行(序号)
			creColumnCode(t);
		    var targetTable = document.getElementById('preSum');
 			flushSumRow(targetTable,targetTable.rows.length);
 			//初始化日期控件
			$(".datepicker-input").datepicker();
			$("[data-toggle=tooltip]").tooltip();//显示删除标签
		});
	}
}

//【会议决议】自动创建列
function autoCreateRowsSum(rowNames){
   	var roHtml="";
	   if(rowNames!=null && rowNames !=""){
	   		var arr=rowNames.split("@");
	   		if(arr !=null && arr.length >0){
	   			for(var m=0;m<arr.length;m++){
   					roHtml+=arr[m];
	   				if(roHtml.indexOf("addPlan") > 0 || roHtml.indexOf("delSum") > 0){
	   					
			   		} else {
			   			roHtml+=" id='preSum_"+m+"_"+ columnValSum +"' name='preSum_"+m+"_"+ columnValSum +"' /></span></td>";
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
	   			}
	   		}
	   }
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
 * 【会议决议】动态添加行
 */
function creColumnCode(temp){//创建行(序号)
    var id = $("#"+tablePreSumId).find("tr:last").attr("id");
	if(temp==id){
		for(var n=0;n<autoAddVal;n++){
			columnValSum +=1;//真实行号id和行号显示
			codeValSum = (eval(id)+1);//显示行号id和行号
			var rowHtml="";
			rowHtml +="<tr id='"+codeValSum+"'>";
			rowHtml +="<td id='"+ columnValSum +"'>"+codeValSum+"</td>";
			rowHtml +=autoCreateRowsSum(autoContentSum);
			rowHtml +="</tr>";
			$("#"+tablePreSumId).append(rowHtml);
		}
		
		$('tbody').delegate('.input-style','focus',function cm(){
		    var t=$(this).parent().parent().attr("id");
 			creColumnCode(t);
		});
	}
}

/**
 * 【会议决议】动态添加行删除方法
 */
function deleteSumRow(e){
	var evt = e||event;
	var eventTarget = evt.srcElement?evt.srcElement:event.target;
	$(eventTarget).tooltip("hide");//隐藏删除标签提示
    var targetTable = document.getElementById('preSum');
    var rowCount = targetTable.rows.length - 1;
    if(rowCount > 1){
        targetTable.deleteRow(eventTarget.parentNode.parentNode.parentNode.cells[0].innerHTML);
        flushSumRow(targetTable,targetTable.rows.length);
    }else{
		msgBox.info({
			content: $.i18n.prop("JC_OA_PO_005")
		});
    }
}

/**
 * 【会议决议】动态添加行刷新方法
 */
function flushSumRow(targetTable,rowLength){
    for (var i = 1; i < rowLength; i++) {
    	targetTable.rows[i].cells[0].innerHTML = i;
    	targetTable.rows[i].cells[0].id = i;
    	targetTable.rows[i].id = i;
    	targetTable.rows[i].cells[1].childNodes[0].childNodes[0].id = "preSum_0_"+ i;
    	targetTable.rows[i].cells[1].childNodes[0].childNodes[0].name = "preSum_0_"+ i;
    	targetTable.rows[i].cells[2].childNodes[0].childNodes[0].id = "preSum_1_"+ i;
    	targetTable.rows[i].cells[2].childNodes[0].childNodes[0].name = "preSum_1_"+ i;
    }
}