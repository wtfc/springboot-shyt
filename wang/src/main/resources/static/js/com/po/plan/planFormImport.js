/**
 * 工作计划相关导入js
 * @author 刘锡来
 * @version  2014-06-30
 */

//获得计划计划table对象
var targetTable = document.getElementById('prePlan');

/**
 * 工作总结表格数据是否填写验证
 */
function sumTableDataValid(){
    var sumTable = document.getElementById('preSum');//获得工作总结table对象
    var sumTableLength = sumTable.rows.length;//获得工作总结table对象长度
    
    for(var i=1;i<sumTableLength;i++){
		if(sumTable.rows[i].cells[1].childNodes[0].innerHTML != ""){return false;}//主要完成事项为空验证
		if(sumTable.rows[i].cells[2].childNodes[0].innerHTML != ""){return false;}//完成标准是否为空验证
		if(sumTable.rows[i].cells[3].childNodes[0].childNodes[0].value != ""){return false;}//负责人是否为空验证
		if(sumTable.rows[i].cells[4].childNodes[0].childNodes[0].value != ""){return false;}//开始时间是否为空验证
		if(sumTable.rows[i].cells[5].childNodes[0].childNodes[0].value != ""){return false;}//完成时间是否为空验证
		if(sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value != ""){return false;}//完成比例是否为空验证
		if(sumTable.rows[i].cells[7].childNodes[0].innerHTML != ""){return false;}//完成比例是否为空验证
    }
    return true;
}

/**
 * 工作计划表格数据是否填写验证
 */
function planTableDataValid(){
    var planTable = document.getElementById('prePlan');//获得工作计划table对象
    var planTableLength = planTable.rows.length;//获得工作计划table对象长度
    
    for(var i=1;i<planTableLength;i++){
		if(planTable.rows[i].cells[1].childNodes[0].innerHTML != ""){return false;}//主要完成事项为空验证
		if(planTable.rows[i].cells[2].childNodes[0].innerHTML != ""){return false;}//完成标准是否为空验证
		if(planTable.rows[i].cells[3].childNodes[0].childNodes[0].value != ""){return false;}//负责人是否为空验证
		if(planTable.rows[i].cells[4].childNodes[0].childNodes[0].value != ""){return false;}//开始时间是否为空验证
		if(planTable.rows[i].cells[5].childNodes[0].childNodes[0].value != ""){return false;}//完成时间是否为空验证
		if(planTable.rows[i].cells[6].childNodes[0].childNodes[0].value != ""){return false;}//完成比例是否为空验证
    }
    return true;
}

/**
 * 导入计划
 */
function planBoxFillData() {
	var chkPlanValue = "";//未处理的计划内容字符串
	var insertNum = 0;//从哪行开始添加数值
	var planResult = "";//计划内容最终字符串
	var targetTable = document.getElementById('prePlan');//获得计划计划table对象
	var targetTableLength = targetTable.rows.length -1;
	var checkPlanIdLength = $("input[name^=checkPlanIds]:checked").length;
	
	if((targetTableLength + checkPlanIdLength) > 30){//导入计划记录到达上限
		msgBox.tip({
			content: $.i18n.prop("JC_OA_PO_006")
		});
	}else{
		//处理任务内容字符串方法
		$("input[name^=checkPlanIds]:checked").each(function(i,v){
			var checkPlanIds_1 = $("input[name="+v.name+"]").parent().parent().children("td").get(1).innerHTML;//主要完成事项
			var checkPlanIds_2 = $("input[name="+v.name+"]").parent().parent().children("td").get(2).innerHTML;//完成标准
			var checkPlanIds_3 = $("input[name="+v.name+"]").parent().parent().children("td").get(3).innerHTML;//负责人
			var checkPlanIds_4 = $("input[name="+v.name+"]").parent().parent().children("td").get(4).innerHTML;//开始时间
			var checkPlanIds_5 = $("input[name="+v.name+"]").parent().parent().children("td").get(5).innerHTML;//结束时间
			var checkPlanIds_6 = $("input[name="+v.name+"]").parent().parent().children("td").get(6).innerHTML;//比例
			var checkPlanIds_7 = $("input[name="+v.name+"]").parent().parent().children("td").get(7).innerHTML;//未完成原因说明
			var checkPlanIds_8 = v.value;
			chkPlanValue += checkPlanIds_1 +"|"+ checkPlanIds_2 +"|"+ checkPlanIds_3 +"|"+ checkPlanIds_4 +"|"+ 
							checkPlanIds_5 +"|"+ checkPlanIds_6 +"|"+checkPlanIds_7 +"|"+ checkPlanIds_8 +"|";
		});
		//处理任务最终内容
		planResult = chkPlanValue.substring(0, chkPlanValue.length-1).split("|");
		//计算存在数据的行数
		for(var i=60;i>30;i--){
			var prePlan = "#prePlan_0_" + i;
			if ($(prePlan).html()!=null && $(prePlan).html()!=""){
				insertNum = i;
				break;
			}
		}
		//如果没有计划内容为空,弹出提示
		if(planResult == ""){
			msgBox.info({
				content: $.i18n.prop("JC_OA_PO_007")
			});
		}else{
			if(insertNum == 0){//从第一行插入
				if((planResult.length/8) >= (targetTable.rows.length-1)){
					//插入动态添加行,总结不插入
					plan.autoTrInit(0, (planResult.length/8) - (targetTable.rows.length-1));
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				for(var i=0;i<planResult.length/8;i++){
					$("#prePlan_0_"+(i+31)).html(planResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+31)).html(planResult[n+1]);//完成标准
					$("#prePlan_2_"+(i+31)).val(planResult[n+2]);//负责人
					$("#prePlan_3_"+(i+31)).val(planResult[n+3]);//开始时间
					$("#prePlan_4_"+(i+31)).val(planResult[n+4]);//结束时间
					$("#prePlan_5_"+(i+31)).val(planResult[n+5]);//结束时间
					$("#prePlan_9_"+(i+31)).val(planResult[n+7]);//人员隐藏域ID
					n += 8;
				}
			}else{//不从第一行插入
				if((planResult.length/8 + insertNum-30) >= targetTable.rows.length){
					plan.autoTrInit(0,(planResult.length/8 + insertNum-30) - (targetTable.rows.length-1));
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				for(var i=0;i<planResult.length/8;i++){
					$("#prePlan_0_"+(i+1+insertNum)).html(planResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+1+insertNum)).html(planResult[n+1]);//完成标准
					$("#prePlan_2_"+(i+1+insertNum)).val(planResult[n+2]);//负责人
					$("#prePlan_3_"+(i+1+insertNum)).val(planResult[n+3]);//开始时间
					$("#prePlan_4_"+(i+1+insertNum)).val(planResult[n+4]);//结束时间
					$("#prePlan_5_"+(i+1+insertNum)).val(planResult[n+5]);//结束时间
					$("#prePlan_9_"+(i+1+insertNum)).val(planResult[n+7]);//人员隐藏域ID
					n += 8;
				}
			}
			$("#viewPlan").modal("hide");//弹出层隐藏
		}
	}
}


/**
 * 导入任务
 */
function taskBoxFillData() {
	var chkTaskValue = "";//未处理的任务内容字符串
	var insertNum = 0;//从哪行开始添加数值
	var taskResult = "";//任务内容最终字符串
	var targetTable = document.getElementById('prePlan');//获得计划计划table对象
	var targetTableLength = targetTable.rows.length -1;
	var checkPlanIdLength = $("input[name^=checkTaskIds]:checked").length;
	
	if((targetTableLength + checkPlanIdLength) > 30){//记录到达上限
		msgBox.tip({
			content: $.i18n.prop("JC_OA_PO_006")
		});
	}else{
		//处理任务内容字符串方法
		$("input[name^=checkTaskIds]:checked").each(function(i, v){        
			var checkTaskIds_2 = $("input[name="+v.name+"]").parent().parent().children("td").get(2).innerHTML;
			var checkTaskIds_3 = $("input[name="+v.name+"]").parent().parent().children("td").get(3).innerHTML;
			var checkTaskIds_4 = $("input[name="+v.name+"]").parent().parent().children("td").get(4).innerHTML;
			var checkTaskIds_5 = $("input[name="+v.name+"]").parent().parent().children("td").get(5).innerHTML;
			var checkTaskIds_6 = $("input[name="+v.name+"]").parent().parent().children("td").get(6).innerHTML;
			var checkTaskIds_7 = $("input[name="+v.name+"]").parent().parent().children("td").get(7).innerHTML;
			var checkTaskIds_8 = $("input[name="+v.name+"]").parent().parent().children("td").get(8).innerHTML;
			var checkPlanIds_9 = v.value;
			chkTaskValue += checkTaskIds_2 +"|"+ checkTaskIds_3 +"|"+ checkTaskIds_4 +"|"+ checkTaskIds_5 +"|"+ 
						    checkTaskIds_6 +"|"+ checkTaskIds_7 +"|"+ checkTaskIds_8 +"|"+ checkPlanIds_9 +"|";
		});
		//处理任务最终内容
		taskResult = chkTaskValue.substring(0, chkTaskValue.length-1).split("|");
		//计算存在数据的行数
		for(var i=60;i>30;i--){
			var prePlan = "#prePlan_0_" + i;
			if ($(prePlan).html()!=null && $(prePlan).html()!=""){
				insertNum = i;
				break;
			}
		}
		//如果没有任务内容为空,弹出提示
		if(taskResult == ""){
			msgBox.info({
				content: $.i18n.prop("JC_OA_PO_007")
			});
		}else{
			if(insertNum == 0){
				if((taskResult.length/8) >= (targetTable.rows.length-1)){
					//插入动态添加行,总结不插入
					plan.autoTrInit(0, (taskResult.length/8) - (targetTable.rows.length-1));
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				for(var i=0;i<taskResult.length/8;i++){
					$("#prePlan_0_"+(i+31)).html(taskResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+31)).html(taskResult[n+1]);//完成标准
					$("#prePlan_2_"+(i+31)).val(taskResult[n+4]);//负责人
					$("#prePlan_3_"+(i+31)).val(taskResult[n+5]);//开始时间
					$("#prePlan_4_"+(i+31)).val(taskResult[n+6]);//结束时间
					$("#prePlan_9_"+(i+31)).val(taskResult[n+7]);//人员隐藏域ID
					n += 8;
				}
			}else{
				if((taskResult.length/8 + insertNum-30) >= targetTable.rows.length){
					plan.autoTrInit(0,(taskResult.length/8 + insertNum-30) - (targetTable.rows.length-1));
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				for(var i=0;i<taskResult.length/8;i++){
					$("#prePlan_0_"+(i+1+insertNum)).html(taskResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+1+insertNum)).html(taskResult[n+1]);//完成标准
					$("#prePlan_2_"+(i+1+insertNum)).val(taskResult[n+4]);//负责人
					$("#prePlan_3_"+(i+1+insertNum)).val(taskResult[n+5]);//开始时间
					$("#prePlan_4_"+(i+1+insertNum)).val(taskResult[n+6]);//结束时间
					$("#prePlan_9_"+(i+1+insertNum)).val(taskResult[n+7]);//人员隐藏域ID
					n += 8;
				}
			}
			$("#viewTask").modal("hide");//关闭任务弹出层
		}
	}
}

/**
 * 导入上期计划
 */
function querySumTable() {
	var targetSumTable = document.getElementById('preSum');//获得计划总结table对象
	//计算从哪行开始导入
	var insertNum = 0;
	var url = "";//ajax访问路径
	for (var i = 1; i <= 30; i++) {//行数
		for(var j = 0; j < 7; j++) {//列数
			var preSum = "#preSum_" + j + "_" + i;
			if (j==2||j==3||j==4||j==5){
				if ($(preSum).val() != null && $(preSum).val() != "") {
					insertNum = i;
					break;
				}				
			}else{
				if ($(preSum).html() != null && $(preSum).html() != "") {
					insertNum = i;
					break;
				}
			}
		}
	}
	
	if($('input[name=planExtend]').val().indexOf("周")>0){
		var year = $('input[name=planSubmitYear]').val();
		var planSubmit = $('input[name=sumSubmitMW]').val();
		if(planSubmit==1){
			year = Number(year) -1;
			planSubmit = 52;
		}
		url = getRootPath()+"/po/plan/beforePlanImp.action?planSubmit="+planSubmit+"&year="+year+"&type="+0;
	}else if($('input[name=planExtend]').val().indexOf("月")>0){
		var year = $('input[name=planSubmitYear]').val();
		var planSubmit = $('input[name=sumSubmitMW]').val();
		if(planSubmit==0){
			year = Number(year) -1;
			planSubmit = 12;
		}
		url = getRootPath()+"/po/plan/beforePlanImp.action?planSubmit="+planSubmit+"&year="+year+"&type="+1;
	}else{
		var year = $('input[name=planSubmitYear]').val();
		var planSubmit = $('input[name=sumSubmitMW]').val();
		url = getRootPath()+"/po/plan/beforePlanImp.action?planSubmit="+planSubmit+"&year="+year+"&type="+2;
	}
	
	$.ajax({
		type:"Post",
		url:url,
		dataType:"json",
		success:function(data){
			var dataPlan = data.plan.planDetails;
			if(dataPlan!=null){//上期存在数据
				var sumTableRow = $("#preSum").find("tr").length-1;//总结动态行数量
				var impPlanCount = dataPlan.length + insertNum - sumTableRow;//导入计划数据条数
				var sumTableTotil = sumTableRow + impPlanCount;//导入后的总数量
				
				if(sumTableTotil>30){//总行数超过30行/
					msgBox.info({
						content: $.i18n.prop("JC_OA_PO_006")
					});
				}else{
					plan.autoTrInit(impPlanCount,0);
				    var targetTableRowCount = targetSumTable.rows.length;
				    flushSumRow(targetSumTable,targetTableRowCount);
				    
					for(var i=0;i<dataPlan.length;i++){
						var o = data.plan.planDetails[i];
						$("#preSum_0_"+(i+1+insertNum)).html(o.content);
						$("#preSum_1_"+(i+1+insertNum)).html(o.standard);
						$("#preSum_2_"+(i+1+insertNum)).val(o.directorIdValue);
						$("#preSum_3_"+(i+1+insertNum)).val(o.startTime);
						$("#preSum_4_"+(i+1+insertNum)).val(o.endTime);
						$("#preSum_5_"+(i+1+insertNum)).val(o.compRate);
						$("#preSum_6_"+(i+1+insertNum)).html(o.description);
						if(o.compRate!="" && o.compRate!=null && o.compRate!=0 && o.compRate != 100){
							$("#preSum_7_"+(i+1+insertNum)).show();
						}
						$("#preSum_10_"+(i+1+insertNum)).val(o.directorId);
					}
				}
			}else{//上期不存在数据
				msgBox.info({
					content: $.i18n.prop("JC_OA_PO_004")//没有工作计划可导入
				});
			}
		}
	});
}

/**
 * 加入计划
 */
function importPlan(addPlan) {
	var targetTable = document.getElementById('prePlan');
	if(targetTable.rows.length -1 > 29){//导入计划记录到达上限
		msgBox.tip({
			content: $.i18n.prop("JC_OA_PO_006")
		});
	}else{
		//判断从哪行开始插入
		var insertNum = 0;
		for (var i = 31; i <= 60; i++) {//行数
			for(var j = 0; j < 6; j++) {//列数
				var prePlan = "#prePlan_" + j + "_" + i;
				if (j==2||j==3||j==4||j==5){
					if ($(prePlan).val() != null && $(prePlan).val() != "") {
						insertNum = i;
						break;
					}				
				}else{
					if ($(prePlan).html() != null && $(prePlan).html() != "") {
						insertNum = i;
						break;
					}
				}
			}
		}

		var source = null;
		for (var i = 0;i < 8;i++) {
			var firstPreSum = addPlan.id.split("_");
			var attrName = firstPreSum[0]+"_"+ i +"_"+firstPreSum[2];
			if(i == 0){
				source = $("#" + attrName).html();
			}else{
				if(i == 1 || i == 6){
					source = source + "|" + $("#" + attrName).html();
				}else{
					source = source + "|" + $("#" + attrName).val();
				}
			}
		}
		
		//拼接人员选择隐藏ID
		var preSum = addPlan.id.split("_");
		var directorId = preSum[0] + "_" + (eval(preSum[1])+3) + "_" + preSum[2];
		source = source + $("#" + directorId).val();
		var sum = source.split("|");
		if (insertNum == 0) {
			if ((sum.length / 8) >= $("#prePlan").find("tr").length) {
				plan.autoTrInit(0, (sum.length / 8) - $("#prePlan").find("tr").length + 1);
				flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
			}
			var n = 0;
			for (var i = 0; i < sum.length / 8; i++) {
				$("#prePlan_0_" + (i+1+30)).html(sum[n+0]);
				$("#prePlan_1_" + (i+1+30)).html(sum[n+1]);
				$("#prePlan_2_" + (i+1+30)).val(sum[n+2]);
				$("#prePlan_3_" + (i+1+30)).val(sum[n+3]);
				$("#prePlan_4_" + (i+1+30)).val(sum[n+4]);
				$("#prePlan_5_" + (i+1+30)).val(sum[n+5]);
				$("#prePlan_9_" + (i+1+30)).val(sum[n+7]);
				n += 8;
			}
		} else {
			if ((sum.length / 8 + insertNum - 30) >= $("#prePlan").find("tr").length) {
				plan.autoTrInit(0, (sum.length / 8 + insertNum - 30) - $("#prePlan").find("tr").length + 1);
				flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
			}
			var n = 0;
			for (var i = 0; i < sum.length / 8; i++) {
				$("#prePlan_0_" + (i+1+insertNum)).html(sum[n+0]);
				$("#prePlan_1_" + (i+1+insertNum)).html(sum[n+1]);
				$("#prePlan_2_" + (i+1+insertNum)).val(sum[n+2]);
				$("#prePlan_3_" + (i+1+insertNum)).val(sum[n+3]);
				$("#prePlan_4_" + (i+1+insertNum)).val(sum[n+4]);
				$("#prePlan_5_" + (i+1+insertNum)).val(sum[n+5]);
				$("#prePlan_9_" + (i+1+insertNum)).val(sum[n+7]);
				n += 8;
			}
		}
	}
}

/**
 * 导出日程
 */
function importSchedule(plan) {
	var planSubYear = $("#planSubmitYear").val();//计划提交年
	var planSubMW = $("#planSubmitMW").val();//计划提交月、周
	var title = "";//日程标题
	diaryJson = "";//全局日程数据变量
	var isDiaryShow = true;
	var prePlanAttr = plan.id.split("_");
	var isNum = /^[1-9]*[1-9][0-9]*$/;//是否为数字输入验证
	var REGEX = /<br>/ig;
	
	for (var i = 0; i < 6; i++) {
		var attrName = prePlanAttr[0] + "_" + i + "_" + prePlanAttr[2];
		if (i == 0) {
			diaryJson = prePlanAttr[2] + "|" + $("#" + attrName).html();
		} else {
			if(i == 1){
				diaryJson = diaryJson + "|" + $("#" + attrName).html();
			}else{
				diaryJson = diaryJson + "|" + $("#" + attrName).val();
			}
		}
	}
	var diaryJsonArr = diaryJson.split("|");
	
	for(var i=1;i<diaryJsonArr.length;i++){
		if(i==diaryJsonArr.length-1){
			if(!isNum.test(diaryJsonArr[i]) || diaryJsonArr[i] > 100 || diaryJsonArr[i] < 0){
				isDiaryShow = false;
				msgBox.tip({
					content: $.i18n.prop("JC_OA_PO_023")
				});
				break;
			} 
		}
		if(diaryJsonArr[i] == ""||diaryJsonArr[i] == "undefined"){
			msgBox.tip({
				content: $.i18n.prop("JC_OA_PO_012")
			});
			isDiaryShow = false;
			break;
		}
	}

	if(isDiaryShow){
		diaryJson = diaryJson + "|" + $("#" + prePlanAttr[0] + "_9_" + prePlanAttr[2]).val() + "|" + title;
		var contentHaveBr = diaryJsonArr[1].indexOf("<BR>")>-1||diaryJsonArr[1].indexOf("<br>")>-1;
		var contentOutOfLength = $.trim(diaryJsonArr[1].replace(REGEX,"")).length > 125||$.trim(diaryJsonArr[2].replace(REGEX,"")).length > 2000;
		
		if(contentHaveBr && !contentOutOfLength){//标题换行,长度不越界
			msgBox.confirm({
				content: $.i18n.prop("JC_OA_PO_031"),
				success: function(){
						$('#diaryUser').html(diaryJsonArr[3]) +
						$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,""))) +
						$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]) +
						$('#diaryContent').html(diaryJsonArr[2]);
						$('#showDiaryBox').modal('show');	
				},
				cancel: function(){
				}
			});
			diaryJsonTemp = $.trim(diaryJsonArr[1].replace(REGEX,"")) + "|" +
						    diaryJsonArr[2] + "|" +
						    diaryJsonArr[4] + "|" +
						    diaryJsonArr[5] + "|" +
						    $("#" + prePlanAttr[0] + "_9_" + prePlanAttr[2]).val();
			diaryJson = diaryJsonTemp;
		}else if(!contentHaveBr && contentOutOfLength){//标题不换行,长度越界
			msgBox.confirm({
				content: $.i18n.prop("JC_OA_PO_032"),
				success: function(){
					$('#diaryUser').html(diaryJsonArr[3]) +
					$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,"")).substring(0,125)) +
					$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]) +
					$('#diaryContent').html($.trim(diaryJsonArr[2].replace(REGEX,"")).substring(0,2000));
					$('#showDiaryBox').modal('show');
				},
				cancel: function(){
				}
			});
			diaryJsonTemp = $.trim(diaryJsonArr[1].replace(REGEX,"")).substring(0,125) + "|" + 
							$.trim(diaryJsonArr[2]).substring(0,2000) + "|" + 
							diaryJsonArr[4] + "|" +
							diaryJsonArr[5] + "|" +
							$("#" + prePlanAttr[0] + "_9_" + prePlanAttr[2]).val();
			diaryJson = diaryJsonTemp;
		}else if(contentHaveBr && contentOutOfLength){//标题换行,长度越界
			msgBox.confirm({
				content: $.i18n.prop("JC_OA_PO_033"),
				success: function(){
					$('#diaryUser').html(diaryJsonArr[3]) +
					$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,"")).substring(0,125)) +
					$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]) +
					$('#diaryContent').html($.trim(diaryJsonArr[2].replace(REGEX,"")).substring(0,2000));
					$('#showDiaryBox').modal('show');
				},
				cancel: function(){
				}
			});
			diaryJsonTemp = $.trim(diaryJsonArr[1].replace(REGEX,"")).substring(0,125) + "|" + 
							$.trim(diaryJsonArr[2]).substring(0,2000) + "|" + 
							diaryJsonArr[4] + "|" +
							diaryJsonArr[5] + "|" +
							$("#" + prePlanAttr[0] + "_9_" + prePlanAttr[2]).val();
			diaryJson = diaryJsonTemp;
		}else{
			$('#diaryUser').html(diaryJsonArr[3]) +
			$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,""))) +
			$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]) +
			$('#diaryContent').html(diaryJsonArr[2]);
			$('#showDiaryBox').modal('show');
			diaryJsonTemp = $.trim(diaryJsonArr[1].replace(REGEX,"")) + "|" +
						    diaryJsonArr[2] + "|" +
						    diaryJsonArr[4] + "|" +
						    diaryJsonArr[5] + "|" +
						    $("#" + prePlanAttr[0] + "_9_" + prePlanAttr[2]).val();
			diaryJson = diaryJsonTemp;
		}
	}
}

/**
 * 导出日程确定方法
 */
function importShowDiary(diaryJson){
	var url = getRootPath()+"/po/plan/savePlanToDiary.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : {"diaryJson": diaryJson},
		success : function(data) {
			$('#showDiaryBox').modal('hide');
			if(data == 1){
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_PO_013")
				});
			}else{
				msgBox.tip({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_014")
				});
			}
		}
	});
}

/**
 * 加入任务弹出层
 */
function workTaskShow(e){
	$('#zdsz')[0].reset();
	var evt = e||event;
	var eventTarget = evt.srcElement?evt.srcElement:event.target;
    var targetTable = document.getElementById('prePlan');
    $("#taskContent").val(eventTarget.parentNode.parentNode.cells[1].innerHTML);//任务内容
    $("#standard").val(eventTarget.parentNode.parentNode.cells[2].innerHTML);//完成标准
    $("#directorUserName").text(eventTarget.parentNode.parentNode.cells[3].innerHTML);//负责人
    $("#directorId").val(eventTarget.parentNode.childNodes[4].value);//完成标准
    $("#sponsorId").val($("#applyId").val());//发起人
    
//	getToken("zdsz");
	$("#init").modal("show");
}