/**
 * 工作计划js
 * @author 刘锡来
 * @version  2014-06-30
 */
var plan = {};
var my = {};
var submitOn = "on";//计划提交标识,true时,允许提交
var diaryJson = "";//日程Json全局变量
var diaryJsonTemp ="";//日程Json全局临时变量
var planTableId = "_";//用来拼plan table id 的全局变量
var taskTableId = "_";//用来拼task table id 的全局变量
var targetTable = document.getElementById('prePlan');//获得计划计划table对象

plan.subState = false;//重复提交标识,false时,允许提交表单
plan.oTable = null;//计划oTable对象
plan.planPageRows = null;//定义计划分页变量
plan.taskPageRows = null;//定义任务分页变量

/****************************************************************
 * 工作计划时间计算 start
 * @author 刘锡来
 * @version  2014-08-12
*****************************************************************/
//年的时间控件（当前年，上一年，下一年）
function year(num) {
	hideErrorMessage();//隐藏错误校验域
//	flushYearValid();//重新加载校验方法
	$("#sumSpan").hide();
	$("#planSpan").hide();
	var yearVar = $('input[name=yearId]').val();
	var monthVar = $('input[name=monthId]').val();
	var dayVar = $('input[name=dayId]').val();
	
	if(monthVar<10){
		monthVar = "0"+monthVar;
	}
	if(dayVar<10){
		dayVar = "0"+dayVar;
	}
	var time = yearVar + "-"+monthVar+"-"+ dayVar;
	var year;
	if ($('input[name=planExtend]').val().indexOf("周") != -1 || 
		$('input[name=planExtend]').val().indexOf("月份") != -1) {
		if(num == 0){
			var dd = time.split("-");
			var date = new Date(dd[0],dd[1],dd[2]);
			year = date.getFullYear();
		}else if(num == -1){
			var dd = time.split("-");
			var date = new Date(dd[0],dd[1],dd[2]);
			year = date.getFullYear() - 1;
		}else{
			var dd = time.split("-");
			var date = new Date(dd[0],dd[1],dd[2]);
			year = date.getFullYear() + 1;
		}
	} else {
		if(num==0){
			var dd = time.split("-");
			var date = new Date(dd[0],dd[1],dd[2]);
			year = date.getFullYear();
		}else if(num==1){
//			year = eval($("#sumSubmitMW").val()) + 1;
			year = Number($("#sumSubmitMW").val()) + 1;
		}else{
//			year = eval($("#sumSubmitMW").val()) - 1;
			year = Number($("#sumSubmitMW").val()) - 1;
		}
	}
	if(num!=0){//初始化时不进行调用
		var url = getRootPath()+"/po/plan/dataEcho.action?planSubmit="+(Number(year))+"&year="+year+"&type="+2;
		dataLoadForLeftAndRight(url);
	}
	
	$("#planStartTime").val(year+"-01-01");
	$("#planEndTime").val(year+"-12-31");

	$("#sumSubmitYear").hide();
	$("#sumSubmitYear").val(year);
	$("#dateNowYear").html("");
	$("#sumSubmitMW").val(year);
	$("#dateNow").html("年工作总结");

	$("#planSubmitYear").hide();
	$("#planSubmitYear").val(Number(year) + 1);
	$("#dateNextYear").html("");
	$("#planSubmitMW").val(Number(year) + 1);
	$("#dateNext").html("年工作计划"); 

	$("#planTitle").val($("#applyDeptidValue").val()+$("#applyIdValue").val()+ year +"年工作总结与"+ (Number(year)+1)+"年工作计划");
	$("#planExtend").val(year + "年工作总结" + eval(Number(year) + 1) + "年工作计划");
	$("#planType").val("2");
}

//月的时间控件（当前月，上一月，下一月）
function month(num) {
	hideErrorMessage();//隐藏错误校验域
//	flushMonthValid();//重新加载校验方法
	$("#sumSpan").show();
	$("#planSpan").show();
	$("#sumSubmitYear").show();
	$("#planSubmitYear").show();
	var yearVar = $('input[name=yearId]').val();
	var monthVar = $('input[name=monthId]').val();
	var dayVar = $('input[name=dayId]').val();
	if(monthVar<10){
		monthVar = "0"+monthVar;
	}
	if(dayVar<10){
		dayVar = "0"+dayVar;
	}
	var time = yearVar + "-"+monthVar+"-"+ dayVar;
	var month;
	var date;
	var year;
	if ($('input[name=planExtend]').val().indexOf("年工作计划") != -1) {//年跳转情况
		var dd = time.split("-");
		date = new Date(dd[0],dd[1]-1,dd[2]);
		year = date.getFullYear();
		month = date.getMonth() + 1;
	} else if ($('input[name=planExtend]').val().indexOf("周") != -1) {//周跳转情况
		var dd = time.split("-");
		date = new Date(dd[0],dd[1]-1,dd[2]);
		year = date.getFullYear();
		month = date.getMonth() + 1;
	} else {//月跳转情况
		if(num==0){
			var dd = time.split("-");
			date = new Date(dd[0],dd[1]-1,dd[2]);
			year = date.getFullYear();
			month = date.getMonth() + 1;
		} else {
			date = $('input[name=planExtend]').val().substr(5);
			year = $('input[name=planExtend]').val().substr(0, 4);
			month = date.substr(0, date.indexOf("月"));
		}
	}
	
	if(num!=0){//初始化时不进行调用
		var url = getRootPath()+"/po/plan/dataEcho.action?planSubmit="+(Number(month) + Number(num))+"&year="+year+"&type="+1;
		dataLoadForLeftAndRight(url);
	}
	
	if (num == -1 && month == 1) {
		month = 12;
		year = Number(year) - 1;
	} else if (num == 1 && month == 12) {
		month = 1;
		year = Number(year) + 1;
	} else {
		month = Number(month) + num;
	}
	var dates;
	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
			|| month == 10 || month == 12) {
		dates = "(01日到31日)";
		if(month == 10 || month == 12){
			$("#planStartTime").val(year+"-"+month+"-01");
			$("#planEndTime").val(year+"-"+month+"-31");
		}else{
			$("#planStartTime").val(year+"-0"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-31");
		}
	} else if (month == 4 || month == 6 || month == 9 || month == 11) {
		dates = "(01日到30日)";
		if(month == 11){
			$("#planStartTime").val(year+"-"+month+"-01");
			$("#planEndTime").val(year+"-"+month+"-30");
		}else{
			$("#planStartTime").val(year+"-0"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-30");
		}
	} else if (month == 2) {
		//闰年判断方法：被4整除但不被100整除，或被400整除
//		if (year % 4 == 0) {
		if ((year % 4 == 0 && year % 100 !=0) || year % 400 == 0) {//add by lihongyu at 2015-03-11
			dates = "(01日到29日)";
			$("#planStartTime").val(year+"-"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-29");
		} else {
			dates = "(01日到28日)";
			$("#planStartTime").val(year+"-0"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-28");
		}
	}
	$("#planExtend").val(year + "年" + month + "月份" + dates);

	$("#sumSubmitYear").val(year);
	$("#dateNowYear").html("年");
	$("#sumSubmitMW").val(month);
	$("#dateNow").html("月份工作总结"); 

	if (month == 12) {
		$("#planSubmitYear").val(Number(year)+1);
		$("#dateNextYear").html("年");
		$("#planSubmitMW").val(1);
		$("#dateNext").html("月份工作计划"); 
	} else {
		$("#planSubmitYear").val(Number(year));
		$("#dateNextYear").html("年");
		$("#planSubmitMW").val(month+1);
		$("#dateNext").html("月份工作计划"); 
	}

	if(month == 12 ){
		$("#planTitle").val($("#applyDeptidValue").val()+$("#applyIdValue").val()+ month +"月份工作总结与1月份工作计划");
	}else{
		$("#planTitle").val($("#applyDeptidValue").val()+$("#applyIdValue").val()+ month +"月份工作总结与"+ (month+1)+"月份工作计划");
	}
	
	$("#planType").val("1");
}

//周的时间控件（当前周，上一周，下一周）
function week(num) {
	hideErrorMessage();//隐藏错误校验域
//	flushWeekValid();//重新加载校验方法
	$("#sumSpan").show();//工作总结年span显示
	$("#planSpan").show();//工作计划年span显示
	$("#sumSubmitYear").show();//工作总结提交年span显示
	$("#planSubmitYear").show();//工作计划提交年span显示	
	
	var week;
	var yearVar = $('input[name=yearId]').val();//系统年
	var monthVar = $('input[name=monthId]').val();//系统月
	var dayVar = $('input[name=dayId]').val();//系统天
	
	//处理月份"0"
//	if(monthVar<10){
//		monthVar = "0"+ monthVar;
//	}
	//处理天数"0"
	if(dayVar<10){
		dayVar = "0"+dayVar;
	}
	
	var time = yearVar + "-"+monthVar+"-"+ dayVar;//形式为"2014-08-08"
	var systemYear = $("#yearId").val();
	
	if($('input[name=planExtend]').val().indexOf("月份")!=-1 || $('input[name=planExtend]').val().indexOf("年工作总结")!=-1){
		week = $("#currentWeek").val();
		$("#planExtend").val(yearVar+"年第"+week+"周工作总结(" + getThisWeek(yearVar, monthVar, dayVar) + ")");
	}
	
	if ($('input[name=planExtend]').val().length < 11 && $('input[name=planExtend]').val().length > 5) {
		week = $("#currentWeek").val();
	} else if ($('input[name=planExtend]').val().length < 6) {
		week = $("#currentWeek").val();
	} else if ($('input[name=planExtend]').val().indexOf("月份") != -1) {
		week = $("#currentWeek").val();
	} else {			
		if(num==0){
			week = $("#currentWeek").val();
		} else {
			var length = $('input[name=planExtend]').val().indexOf("周");
			week = $('input[name=planExtend]').val().substr(6, length-6);
		}
	}

	var year;
	var month;
	var day;
	if (num == 0|| $('input[name=planExtend]').val().indexOf("月份") != -1|| $('input[name=planExtend]').val().length < 6) {
		year = $('input[name=yearId]').val();
		month = $('input[name=monthId]').val();
		day = $('input[name=dayId]').val();
		if(month<10){
			month = "0"+month;
		}
		if(day<10){
			day = "0"+day;
		}
	} else {
		var today;
		if ($('input[name=planExtend]').val().length < 11 && $('input[name=planExtend]').val().length > 5) {
			today = $('input[name=planExtend]').val().split("-");
		} else {
			if(week<10){
				var firstlength = $('input[name=planExtend]').val().lastIndexOf("/");
				var endlength = $('input[name=planExtend]').val().lastIndexOf(")");
				today = $('input[name=planExtend]').val().substring(Number(firstlength) + 1, endlength).split("-");
			} else if(week>9 && week<100){
				var firstlength = $('input[name=planExtend]').val().lastIndexOf("/");
				var endlength = $('input[name=planExtend]').val().lastIndexOf(")");
				today = $('input[name=planExtend]').val().substring(Number(firstlength) + 1, endlength).split("-");
			}
		}
		year = today[0];
		month = today[1];
		day = today[2];
	}
	if(num != 0){//初始化时不进行调用
		var url = getRootPath()+"/po/plan/dataEcho.action?planSubmit="+(Number(week) + Number(num))+"&year="+year+"&type="+0;
		dataLoadForLeftAndRight(url);
	}
	
	switch (num) {
	case 0: {
		$("#planExtend").val(year+"年第" + week + "周工作总结(" + getThisWeek(year, month, day) + ")");
		$("#sumSubmitYear").val(year);
		$("#dateNowYear").html("年第");
		$("#sumSubmitMW").val(week);
		$("#dateNow").html("周工作总结");
		
		if(week == 52){
			$("#planSubmitYear").val(Number(year) + 1);
			$("#dateNextYear").html("年第");
			$("#planSubmitMW").val("1");
			$("#dateNext").html("周工作计划");
		}else{
			$("#planSubmitYear").val(year);
			$("#dateNextYear").html("年第");
			$("#planSubmitMW").val(Number(week) + 1);
			$("#dateNext").html("周工作计划");
		}

		$("#planStartTime").val(getThisWeek(year, month, day).substring(0, 10));
		$("#planEndTime").val(getThisWeek(year, month, day).substring(11,22));
		break;
	}
	case 1: {
		if (week > 51) {
			week = Number(week) - 51;
			$("#planExtend").val(Number(year)+1+"年第" + week + "周工作总结(" + getNextWeek(year, month, day) + ")");
			$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
			$("#dateNowYear").html("年第");
			$("#sumSubmitMW").val(week);
			$("#dateNow").html("周工作总结");
			
			var nextweek = getNextWeek(year, month, day).substring(0, 10);
			$("#planSubmitYear").val($("#planExtend").val().substring(0,4));
			$("#dateNextYear").html("年第");
			$("#planSubmitMW").val(Number(week) + 1);
			$("#dateNext").html("周工作计划");
			$("#planStartTime").val(nextweek);
			$("#planEndTime").val(getThisWeek(nextweek.substring(0, 4), nextweek.substring(5, 7), nextweek.substring(8, 10)).substring(11,22));
		} else {
			week = Number(week) + 1;
			if(week==52){
				$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
				$("#planSubmitYear").val(Number($("#planExtend").val().substring(0,4))+1);
				
				$("#dateNextYear").html("年第");
				$("#planSubmitMW").val(1);
				$("#dateNext").html("周工作计划");
				$("#planExtend").val(year+"年第" + week + "周工作总结(" + getNextWeek(year, month, day) + ")");
				
				$("#dateNowYear").html("年第");
				$("#sumSubmitMW").val(week);
				$("#dateNow").html("周工作总结");
			} else if(week==2){
				$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
				$("#dateNowYear").html("年第");
				$("#sumSubmitMW").val(week);
				$("#dateNow").html("周工作总结");
				
				$("#planSubmitYear").val($("#planExtend").val().substring(0,4));
				$("#dateNextYear").html("年第");
				$("#planSubmitMW").val(Number(week)+1);
				$("#dateNext").html("周工作计划");
				$("#planExtend").val(year+"年第" + week + "周工作总结(" + getNextWeek(year, month, day) + ")");
			} else {
				$("#planSubmitYear").val($("#planExtend").val().substring(0,4));
				$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));

				$("#dateNextYear").html("年第");
				$("#planSubmitMW").val(Number(week) + 1);
				$("#dateNext").html("周工作计划");
				
				$("#dateNowYear").html("年第");
				$("#sumSubmitMW").val(week);
				$("#dateNow").html("周工作总结");
				$("#planExtend").val($("#planExtend").val().substring(0,4)+"年第" + week + "周工作总结(" + getNextWeek(year, month, day) + ")");
			}
			$("#planStartTime").val(getNextWeek(year, month, day).substring(0, 10));
			$("#planEndTime").val(getNextWeek(year, month, day).substring(11,22));
		}
		break;
	}
	case -1: {
		if (week == 1) {
			week = 52;
			$("#planExtend").val((Number($("#planExtend").val().substring(0,4))-1)+"年第" + week + "周工作总结(" + getPreviousWeek(year, month, day) + ")");
			
			$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
			$("#planSubmitYear").val(Number($("#planExtend").val().substring(0,4))+1);
			$("#dateNowYear").html("年第");
			$("#sumSubmitMW").val(52);
			$("#dateNow").html("周工作总结");

			$("#dateNextYear").html("年第");
			$("#planSubmitMW").val(1);
			$("#dateNext").html("周工作计划");
		}else if(week == 52){
			week = week - 1;
			$("#planExtend").val(year+"年第" + week + "周工作总结(" + getPreviousWeek(year, month, day) + ")");

			$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
			$("#planSubmitYear").val($("#planExtend").val().substring(0,4));	

			$("#dateNowYear").html("年第");
			$("#sumSubmitMW").val(week);
			$("#dateNow").html("周工作总结");
			
			$("#dateNextYear").html("年第");
			$("#planSubmitMW").val(Number(week) + 1);
			$("#dateNext").html("周工作计划");
		}else {
			week = week - 1;
			$("#planExtend").val(year+"年第" + week + "周工作总结(" + getPreviousWeek(year, month, day) + ")");
			$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
			$("#planSubmitYear").val($("#planExtend").val().substring(0,4));	

			$("#dateNowYear").html("年第");
			$("#sumSubmitMW").val(week);
			$("#dateNow").html("周工作总结");
		
			$("#dateNextYear").html("年第");
			$("#planSubmitMW").val(Number(week) + 1);
			$("#dateNext").html("周工作计划");
		}
		$("#planStartTime").val(getPreviousWeek(year, month, day).substring(0, 10));
		$("#planEndTime").val(getPreviousWeek(year, month, day).substring(11,22));
		break;
	}
	}

	if(week == 52){
		$("#planTitle").val($("#applyDeptidValue").val()+$("#applyIdValue").val()+"第"+week+"周工作总结与第1周工作计划");
	}else{
		$("#planTitle").val($("#applyDeptidValue").val()+$("#applyIdValue").val()+"第"+week+"周工作总结与第" + (Number(week)+1) + "周工作计划");
	}
	$("#planType").val("0");
}

//当前周
function getThisWeek(year, month, day) {
	var today = new Date(year, month - 1, day);
	var week = today.getDay();
	if (week == 0) {
		week = 7;
	}
	var monday = new Date(today.valueOf() - (week - 1) * 24 * 60 * 60 * 1000);
	var sunday = new Date(monday.valueOf() + 6 * 24 * 60 * 60 * 1000);
	return monday.getFullYear()
			+ "-"
			+ ((monday.getMonth() + 1).toString().length == 1 ? ("0" + (monday.getMonth() + 1)) : (monday.getMonth() + 1))
			+ "-"
			+ (monday.getDate().toString().length == 1 ? "0" + monday.getDate(): monday.getDate())
			+ "/"
			+ sunday.getFullYear()
			+ "-"
			+ ((sunday.getMonth() + 1).toString().length == 1 ? ("0" + (sunday.getMonth() + 1)) : (sunday.getMonth() + 1))
			+ "-"
			+ (sunday.getDate().toString().length == 1 ? "0" + sunday.getDate(): sunday.getDate());
}

//上一周
function getPreviousWeek(year, month, day) {
	var today = new Date(year, month - 1, day);
	var week = today.getDay();
	if (week == 0) {
		week = 7;
	}
	var monday = new Date(today.valueOf() - (week + 6) * 24 * 60 * 60 * 1000);
	var sunday = new Date(monday.valueOf() + 6 * 24 * 60 * 60 * 1000);
	return monday.getFullYear()
			+ "-"
			+ ((monday.getMonth() + 1).toString().length == 1 ? ("0" + (monday.getMonth() + 1)) : (monday.getMonth() + 1))
			+ "-"
			+ (monday.getDate().toString().length == 1 ? "0" + monday.getDate(): monday.getDate())
			+ "/"
			+ sunday.getFullYear()
			+ "-"
			+ ((sunday.getMonth() + 1).toString().length == 1 ? ("0" + (sunday.getMonth() + 1)) : (sunday.getMonth() + 1))
			+ "-"
			+ (sunday.getDate().toString().length == 1 ? "0" + sunday.getDate(): sunday.getDate());
}

//下一周
function getNextWeek(year, month, day) {
	var today = new Date(year, month - 1, day);
	var week = today.getDay();
	if (week == 0) {
		week = 7;
	}
	var monday = new Date(today.valueOf() - (week - 8) * 24 * 60 * 60 * 1000);
	var sunday = new Date(monday.valueOf() + 6 * 24 * 60 * 60 * 1000);
	return monday.getFullYear()
			+ "-"
			+ ((monday.getMonth() + 1).toString().length == 1 ? ("0" + (monday.getMonth() + 1)) : (monday.getMonth() + 1))
			+ "-"
			+ (monday.getDate().toString().length == 1 ? "0" + monday.getDate(): monday.getDate())
			+ "/"
			+ sunday.getFullYear()
			+ "-"
			+ ((sunday.getMonth() + 1).toString().length == 1 ? ("0" + (sunday.getMonth() + 1)) : (sunday.getMonth() + 1))
			+ "-"
			+ (sunday.getDate().toString().length == 1 ? "0" + sunday.getDate():sunday.getDate());
}

//刷新总结表格、计划表格
function flushTableRow(){
	var targetSumTable = document.getElementById('preSum');	
	var targetPlanTable = document.getElementById('prePlan');	
    flushSumRow(targetSumTable,targetSumTable.rows.length);
    flushPlanRow(targetPlanTable,targetPlanTable.rows.length);
}

//填充数据
function fillTableData(dataSum,dataPlan){
	var dataSumLength = dataSum.length;
	var dataPlanLength = dataPlan.length;
	//填充工作总结详细数据
	if(dataSumLength > 0) {
		for(var i=0;i<dataSumLength;i++){
			var o = dataSum[i];
			$("#preSum_0_"+(i+1)).html(o.content);
			$("#preSum_1_"+(i+1)).html(o.standard);
			$("#preSum_2_"+(i+1)+"-preSum_2_"+(i+1)).select2("data",{"id":o.directorId,"text":o.directorIdValue});
			$("#preSum_3_"+(i+1)).val(o.startTime);
			$("#preSum_4_"+(i+1)).val(o.endTime);
			$("#preSum_5_"+(i+1)).val(o.compRate);
			if(o.compRate!="" && o.compRate!=null && o.compRate!=0 && o.compRate != 100){
				$("#preSum_7_"+(i+1)).show();
			}
			$("#preSum_6_"+(i+1)).html(o.description);
			$("#preSum_10_"+(i+1)).val(o.directorId);
		}
	}
	//填充工作计划详细数据
	if(dataPlanLength > 0) {
		for(var i=0;i<dataPlanLength;i++){
			var o = dataPlan[i];
			$("#prePlan_0_"+(i+31)).html(o.content);
			$("#prePlan_1_"+(i+31)).html(o.standard);
			$("#prePlan_2_"+(i+31)+"-prePlan_2_"+(i+31)).select2("data",{"id":o.directorId,"text":o.directorIdValue});
			$("#prePlan_3_"+(i+31)).val(o.startTime);
			$("#prePlan_4_"+(i+31)).val(o.endTime);
			$("input#prePlan_5_"+(i+31)).val(o.compRate);
			$("#prePlan_9_"+(i+31)).val(o.directorId);
		}
	}
}

function dataLoadForLeftAndRight(url){
	$.ajax({
		type : "Post",
		url : url,
		dataType : "json",
		success : function(data) {
			if(data.plan!=null){
				if(data.plan.sumDetails!=null && data.plan.planDetails!=null){
					if(sumTableDataValid() && planTableDataValid()){
						var dataSum = data.plan.sumDetails;
						var dataPlan = data.plan.planDetails;
						var dataSumLength = dataSum.length;
						var dataPlanLength = dataPlan.length;
						if(dataSum!=null&&dataPlan!=null){
							$("#preSum").find("tbody").remove();
							if(dataSumLength < 6){
								plan.autoTrInit(6,0);
							}else{
								plan.autoTrInit(dataSumLength+1,0);
							}
							var targetSumTable = document.getElementById('preSum');	
							flushSumRow(targetSumTable,targetSumTable.rows.length);
							
							$("#prePlan").find("tbody").remove();
							if(dataPlanLength < 6){
								plan.autoTrInit(0,6);
							}else{
								plan.autoTrInit(0,dataPlanLength+1);
							}
							var targetPlanTable = document.getElementById('prePlan');	
							flushPlanRow(targetPlanTable,targetPlanTable.rows.length);
							fillTableData(dataSum,dataPlan);
						}
					}else{
						msgBox.confirm({
							content: $.i18n.prop("JC_OA_PO_037"),
							success: function(){
								$("#preSum").find("tbody").remove();
								$("#prePlan").find("tbody").remove();
								if(data.plan.sumDetails!=null&&data.plan.planDetails!=null){
									var dataSum = data.plan.sumDetails;
									var dataPlan = data.plan.planDetails;
									var sumAutoLength = null;
									var planAutoLength = null;
									
									if(dataSum!=null&&dataPlan!=null){
										if(dataSum.length < 6){
											sumAutoLength = 6;
										}else{
											sumAutoLength = dataSum.length+1;
										}
										if(dataPlan.length < 6){
											planAutoLength = 6;
										}else{
											planAutoLength = dataPlan.length+1;
										}
										plan.autoTrInit(sumAutoLength,planAutoLength);
										flushTableRow();
										fillTableData(dataSum,dataPlan);
									}
								}
							},
							cancel: function(){
							}
						});
					}
				}else{//跳转时,没有数据,重新加载
					if(sumTableDataValid() && planTableDataValid()){
						$("#preSum").find("tbody").remove();
						$("#prePlan").find("tbody").remove();
						plan.autoTrInit(6,6);
					    flushTableRow();
					}else{
						msgBox.confirm({
							content: $.i18n.prop("JC_OA_PO_037"),
							success: function(){
								$("#preSum").find("tbody").remove();
								$("#prePlan").find("tbody").remove();
								plan.autoTrInit(6,6);
							    flushTableRow();
							},
							cancel: function(){
							}
						});
					}
				}
			}
		}
	});
}

//重新加载年校验方法
function flushWeekValid(){
//    $("#sumSubmitYear").rules("remove");
//    $("#sumSubmitMW").rules("remove");
//    $("#planSubmitYear").rules("remove");
//    $("#planSubmitMW").rules("remove");
//
//    $("#sumSubmitYear").rules("add",{
//    	required:true,
//    	range:[2000,3000],
//    	minlength:4,
//    	maxlength:4,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
//	
//    $("#sumSubmitMW").rules("add",{
//    	required:true,
//    	range:[1,55],
//      	minlength:1,
//    	maxlength:2,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
//    
//    $("#planSubmitYear").rules("add",{
//    	required:true,
//    	range:[2000,3000],
//    	minlength:4,
//    	maxlength:4,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
//    
//    $("#planSubmitMW").rules("add",{
//    	required:true,
//    	range:[1,55],
//    	minlength:1,
//    	maxlength:2,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
}

//重新加载年校验方法
function flushMonthValid(){
//    $("#sumSubmitYear").rules("remove");
//    $("#sumSubmitMW").rules("remove");
//    $("#planSubmitYear").rules("remove");
//    $("#planSubmitMW").rules("remove");
//
//    $("#sumSubmitYear").rules("add",{
//    	required:true,
//    	range:[2000,3000],
//    	minlength:4,
//    	maxlength:4,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
//	
//    $("#sumSubmitMW").rules("add",{
//    	required:true,
//    	range:[1,12],
//      	minlength:1,
//    	maxlength:2,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
//    
//    $("#planSubmitYear").rules("add",{
//    	required:true,
//    	range:[2000,3000],
//    	minlength:4,
//    	maxlength:4,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
//    
//    $("#planSubmitMW").rules("add",{
//    	required:true,
//    	range:[1,12],
//    	minlength:1,
//    	maxlength:2,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
}

//重新加载年校验方法
function flushYearValid(){
//    $("#sumSubmitMW").rules("remove");
//    $("#planSubmitMW").rules("remove");
//    $("#sumSubmitMW").rules("add",{
//    	required:true,
//    	range:[2000,3000],
//      	minlength:4,
//    	maxlength:4,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
//    $("#planSubmitMW").rules("add",{
//    	required:true,
//    	range:[2000,3000],
//    	minlength:4,
//    	maxlength:4,
//    	messages:{
//    		required:'',
//    		range:'',
//    		minlength:'',
//    		maxlength:''
//    	}
//    });
}
/*****************************************************************
 * 工作计划时间计算 end
/************** **************************************************/






/****************************************************************
 * 工作计划表单 start
 * @author 刘锡来
 * @version  2014-08-12
*****************************************************************/
//动态添加行初始化方法
plan.autoTrInit = function(sumLength, planLength) {
	//初始化工作总结动态增加行
	var autoTrSumStr = "<td><span><div autoFlowForm='content' class='down-area input-div autoSumDiv' tabindex='-1' data-toggle='downarea' id='tempId' name='tempName'></div></span></td>"
			+ "<td><span><div autoFlowForm='content' class='down-area input-div' tabindex='-1' data-toggle='downarea' id='tempId' name='tempName'></div></span></td>"
//			+ "<td><span autoFlowForm='textinput' class='input-style'><input type='text' class='fzrOnfocus' maxlength='0' onclick='selectControl.singlePerson(this.id,false,plan.singlePersonSumCallBack)' id='tempId' name='tempName'></span></td>@"
			+ "<td><div itemId='itemIdTemp' autoFlowForm='userSelect' id='tempId' name='tempName'></div></td>"
			+ "<td><span autoFlowForm='textinput' class='input-style'><input type='text' data-date-format='yyyy-MM-dd' class='datepicker-input-plan' data-ref-obj='#sumDetailStartTime lt' id='tempId' name='tempName'></span></td>"
			+ "<td><span autoFlowForm='textinput' class='input-style'><input type='text' data-date-format='yyyy-MM-dd' class='datepicker-input-plan' data-ref-obj='#sumDetailEndTime gt' id='tempId' name='tempName'></span></td>"
			+ "<td><span autoFlowForm='textinput' class='input-style'><input type='text' placeholder='0-100' maxlength='3' class='tr' onblur='hiddenBtn(this)' id='tempId' name='tempName'></span></td>"
			+ "<td><span><div autoFlowForm='content' class='down-area input-div' tabindex='-1' data-toggle='downarea' id='tempId' name='tempName'></div></span></td>"
			+ "<td operate='true'><a id='impPlan' autoFlowForm='button' style='display:none;' onclick='importPlan(this);' class='a-icon i-new m-r-xs' href='#'>加入计划</a>"
			+ "<a autoFlowForm='button' class='a-icon i-remove' href='#'><i id='delSum' class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' "
			+ "onclick='deleteSumRow(event);' data-container='body' data-original-title='删除'></i></a>"
			+ "<input id='sumHid' name='planPart' type='hidden' value='0'>"
			+ "<input id='sumDirectorId' name='directorId' type='hidden'></td>";
	indexFun(true, "preSum", sumLength, 1, autoTrSumStr);
	$("[data-toggle=tooltip]").tooltip();//显示删除标签

	//初始化工作计划动态增加行
	var autoTrPlanStr = "<td><span><div autoFlowForm='content' class='down-area input-div autoPlanDiv' tabindex='-1' data-toggle='downarea' id='tempId' name='tempName'></div></span></td>"
			+ "<td><span><div autoFlowForm='content' class='down-area input-div' tabindex='-1' id='tempId' name='tempName'></div></span></td>"
//			+ "<td><span autoFlowForm='textinput' class='auto-style'><input type='text' class='fzrOnfocus' maxlength='0' onclick='selectControl.singlePerson(this.id,false,plan.singlePersonPlanCallBack)' id='tempId' name='tempName'></span></td>@"
			+ "<td><div itemId='itemIdTemp' autoFlowForm='userSelect' id='tempId' name='tempName'></div></td>"
			+ "<td><span autoFlowForm='textinput' class='auto-style'><input type='text' data-date-format='yyyy-MM-dd' class='datepicker-input-plan' data-ref-obj='#planDetailStartTime lt' id='tempId' name='tempName'></span></td>"
			+ "<td><span autoFlowForm='textinput' class='auto-style'><input type='text' data-date-format='yyyy-MM-dd' class='datepicker-input-plan' data-ref-obj='#planDetailEndTime gt' id='tempId' name='tempName'></span></td>"
			+ "<td><span autoFlowForm='textinput' class='auto-style'><input type='text' placeholder='0-100' maxlength='3' class='tr' id='tempId' name='tempName'></span></td>"
			+ "<td><a id='impDiary' workFlowForm='button' itemName='planDiaryItem' onclick='importSchedule(this);' class='a-icon i-new m-r-xs' href='#'>加入日程</a>"
			+ "<a workFlowForm='button' itemName='planDeleteItem' class='a-icon i-remove' href='#'><i id='delPlan' class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' "
			+ "onclick='deletePlanRow(event);' data-container='body' data-original-title='删除'></i></a>"
			+ "<a id='issuedWorkTask' workFlowForm='button' style='display:none;' itemName='planTaskItem' onclick='loadJoinTaskAddHtml(event);' class='a-icon i-new m-r-xs' href='#'>下发任务</a>"
			+ "<input id='planHid' name='planPart' type='hidden' value='1'>"
			+ "<input id='planDirectorId' name='directorId' type='hidden'></td>";
	indexFun(false, "prePlan", planLength, 1, autoTrPlanStr);
//	$(".datepicker-input").datepicker();
	
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
	
	$("[data-toggle=tooltip]").tooltip();//显示删除标签
	iePlaceholderPatch();				 //ie8下水印插件调用	
};



//工作日程周、月计划点击跳转初始化
plan.diaryJumpInit = function(condition) {//对应工作日程xtools跳转
	var url = getRootPath()+"/po/plan/getInitYMD.action";
	$.ajax({
		type : "GET",
		url : url,
		data : {"id":""},
		dataType : "json",
		async : true,
		success : function(data) {
			if (condition == "diaryWeekJump"){
				if(data.week != 0){
					$("#week").show();//周计划label显示
					$("#month").hide();//月计划label隐藏
					$("#year").hide();//年计划label隐藏
					$("#diaryCondition").val("diaryWeekJump");//跳转条件为周计划跳转
				}else{
					loadrightmenu("/po/plan/planNoPermission.action?time="+new Date());
				}
			}else{
				if(data.month != 0){
					$("#month").attr("class", "tabsTime btn active");
					$("#week").hide();//周计划label隐藏
					$("#month").show();//月计划label显示
					$("#year").hide();//年计划label隐藏
					$("#planType").val("1");//计划类型设置为月计划
					$("#diaryCondition").val("diaryMonthJump");//跳转条件为月计划跳转
					//调用月份初始化方法
					month(0);
				}else{
					loadrightmenu("/po/plan/planNoPermission.action?time="+new Date());	
				}
			}
		}
	});
};

//计划编制页面周、月、年label标签初始化
plan.showLabelInit = function() {
	var result = true;
	var weekPermission = $("#weekPermission").val();//周计划编制权限
	var monthPermission = $("#monthPermission").val();//月计划编制权限
	var yearPermission = $("#yearPermission").val();//年计划编制权限
	if($("#workFlowType").val() == 1){//起草状态
		if(weekPermission == 0 && monthPermission == 0 && yearPermission == 0) {//如果周、月、年都为0.则跳转工作计划无编制权限页面
			loadrightmenu("/po/plan/planNoPermission.action?time="+new Date());
			result = false;//处理工作流找不到表单,弹出提示问题
		}else{
			$("#planFormHeader").attr("style", "display:block");
			$("#planFormBox").attr("style", "display:block");
			if(weekPermission == 0) {//周为0,年label隐藏
				$("#week").hide();
			}
			if(monthPermission == 0) {//月为0,年label隐藏
				$("#month").hide();
			}
			if(yearPermission == 0) {//年为0,年label隐藏
				$("#year").hide();
			}
			
			//判断进入页面,默认显示label标签
			if(weekPermission != 0){
			   $("#planType").attr("value", 0);
			}else{
				if(monthPermission != 0){
				   $("#planType").attr("value", 1);
				}else{
					if(yearPermission != 0){
					  $("#planType").attr("value", 2);
					}
				}
			}
			plan.planDataInit();//工作计划初始数据
		}
	}else if($("#workFlowType").val() == 2){//暂存状态
		$("#planFormHeader").attr("style", "display:block");
		$("#planFormBox").attr("style", "display:block");
	}else{
		$("#planFormHeader").attr("style", "display:block");
		$("#planFormBox").attr("style", "display:block");
		plan.planDataInit();//工作计划初始数据
	}
	return result;
};

//初始化工作计划初始数据
plan.planDataInit = function() {
	var targetSumTable = document.getElementById('preSum');//获得计划总结table对象
	var targetTableRowCount = targetSumTable.rows.length;

	var beforePlanJsonStr = $("#beforePlan").val();
	var beforePlanObj = eval("("+beforePlanJsonStr+")");
	if(beforePlanObj!=null){
		var dataPlan = beforePlanObj.planDetails;
		
		if(dataPlan!=null){//上期存在数据
			var dataPlanLength = dataPlan.length;
			if(dataPlanLength >= 6){
				plan.autoTrInit(dataPlanLength+1,6);	
			}else{
				plan.autoTrInit(6,6);
			}
		    var targetTableRowCount = targetSumTable.rows.length;
		    flushSumRow(targetSumTable,targetTableRowCount);
			for(var i=0;i<dataPlanLength;i++){
				var o = dataPlan[i];
				$("#preSum_0_"+(i+1)).html(o.content);
				$("#preSum_1_"+(i+1)).html(o.standard);
				$("#preSum_2_"+(i+1)+"-preSum_2_"+(i+1)).select2("data",{"id":o.directorId,"text":o.directorIdValue});
				$("#preSum_3_"+(i+1)).val(o.startTime);
				$("#preSum_4_"+(i+1)).val(o.endTime);
				$("#preSum_5_"+(i+1)).val(o.compRate);
				$("#preSum_6_"+(i+1)).html(o.description);
				if(o.compRate!="" && o.compRate!=null && o.compRate!=0 && o.compRate != 100){
					$("#preSum_7_"+(i+1)).show();
				}
				$("#preSum_10_"+(i+1)).val(o.directorId);
			}
		}else{
			plan.autoTrInit(6,6);
		}
	}
	//初始化计划开始时间与计划结束时间
	plan.planFormDateInit($("#yearId").val(),$("#monthId").val(),$("#dayId").val());
	iePlaceholderPatch();//水印处理
};

//工作计划页面日期初始化
plan.planFormDateInit = function(year,month,day) {
	//工作计划跳转,计划类型值设置
	if($("#diaryCondition").val() == "diaryWeekJump"){
		$("#planType").val(0);
	}else if($("#diaryCondition").val() == "diaryMonthJump"){
		$("#planType").val(1);
	}else{
	}
	if ($("#planType").val() == 0) {
		week(0);//周计划,调用周计划初始化
	} else if ($("#planType").val() == 1) {//月计划
		$("#month").attr("class", "tabsTime btn active");//月计划label标签选中
		if(month.indexOf("0")==0){//处理带有"0"的月份数值
			month=month.replace(/^0/, '');
		}
		
		if (month == 12) {//默认当前月是12月份的处理
			$("#sumSubmitYear").val(year);//总结年是当前年
			$("#sumSubmitMW").val(month);//总结月是当前月
			$("#planSubmitYear").val(Number(year) + 1);//计划年是下一年
			$("#planSubmitMW").val("1");//计划月份是1月份
		} else {
			$("#sumSubmitYear").val(year);//总结年是当前年
			$("#sumSubmitMW").val(month);//总结月是当前月
			$("#planSubmitYear").val(year);//计划年是当前年
			$("#planSubmitMW").val(Number(month) + 1);//计划月份是下一月
		}

		$("#dateNowYear").text("年");
		$("#dateNow").text("月份工作总结");
		$("#dateNextYear").text("年");
		$("#dateNext").text("月份工作计划");

		//开始时间与结束时间默认值设置
		plan.dateShowInit(year,month,day);
		//计划标题默认值设置
		$("#planTitle").val($("#applyDeptidValue").val() + $("#applyIdValue").val()+ 
							$("#sumSubmitMW").val()+"月工作总结与"+$("#planSubmitMW").val() + "月工作计划");
		$("#planExtend").val(year + "年" + month + "月份(01日到31日)");
	} else {//年计划
		$("#year").attr("class", "tabsTime btn active");//年计划默认被选中
		$("#sumSubmitYear").hide();
		$("#planSubmitYear").hide();
		$("#sumSpan").hide();
		$("#planSpan").hide();		
		$("#dateNowYear").hide();
		$("#dateNextYear").hide();
		$("#sumSubmitYear").val(year);	
		$("#planSubmitYear").val(Number(month)+1);
		$("#sumSubmitMW").val(year);
		$("#planSubmitMW").val(Number(year)+1);
		$("#dateNow").text("年工作总结");
		$("#dateNext").text("年工作计划");

		$("#planStartTime").val(year+"-01-31");//开始时间初始数值
		$("#planEndTime").val(year+"-12-31");//结束时间初始数值
		
		// 计划标题默认值设置
		$("#planTitle").val($("#applyDeptidValue").val() + $("#applyIdValue").val()+
							$("#sumSubmitMW").val()+"年工作总结与"+$("#planSubmitMW").val() + "年工作计划");
	}
};

//开始时间与结束时间默认值设置(月份)
plan.dateShowInit =function(year,month,day){
	if (month == 1 || month == 3 || month == 5 || month == 7 || 
		month == 8 || month == 10 || month == 12) {
		if(month == 10 || month == 12){
			$("#planStartTime").val(year+"-"+month+"-01");
			$("#planEndTime").val(year+"-"+month+"-31");
		}else{
			$("#planStartTime").val(year+"-0"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-31");
		}
	} else if (month == 4 || month == 6 || month == 9 || month == 11) {
		if(month == 11){
			$("#planStartTime").val(year+"-"+month+"-01");
			$("#planEndTime").val(year+"-"+month+"-30");
		}else{
			$("#planStartTime").val(year+"-0"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-30");
		}
	} else if (month == 2) {
		if (year % 4 == 0) {
			$("#planStartTime").val(year+"-"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-29");
		} else {
			$("#planStartTime").val(year+"-0"+month+"-01");
			$("#planEndTime").val(year+"-0"+month+"-28");
		}
	}
};

//隐藏加入计划按钮函数
function hiddenBtn(v) {
	var isNum = /^[0-9]*[0-9][0-9]*$/;//是否为数字输入验证
	var sumPreName = v.id.split("_");
	var firName = Number(sumPreName[1]) + 2;
	var jName = sumPreName[0] + "_" + firName + "_" + sumPreName[2];
	if(v.value!=""){
		if (v.value >= 100) {
			$("#" + jName).hide();//按钮隐藏
		} else if (isNum.test(v.value) && v.value >= 0 && v.value < 100) {
			$("#" + jName).show();//按钮显示
		}
	}else{
		$("#" + jName).hide();//按钮隐藏
	}
	$(".input-textarea .table").hide();//UI重新渲染
	$(".input-textarea .table").show();//UI重新渲染
}

//总结人员选择树回调函数(暂时不需要)
plan.singlePersonSumCallBack = function(data, id) {
	document.getElementById(id).value = data[0].text;//点选人员名称
	var trRowNum = id.substring(9, id.length);//当前行号
	var sum_directorHid = 'preSum_10_' + trRowNum;//拼接计划负责人隐藏域ID
	document.getElementById(sum_directorHid).value = data[0].id;//总结负责人隐藏域赋值
};

//计划人员选择树回调函数
plan.singlePersonPlanCallBack = function(data, id) {
	document.getElementById(id).value = data[0].text;//点选人员名称
	var trRowNum = id.substring(10, id.length);//当前行号
	var plan_directorHid = 'prePlan_9_' + trRowNum;//拼接计划负责人隐藏域ID
	document.getElementById(plan_directorHid).value = data[0].id;//计划负责人隐藏域赋值
};


//工作计划插入方法
function insert(status,jumpFun) {
	var result = true; //流程提交返回值
	if (plan.subState)
		return;
	plan.subState = true;
	var url = getRootPath() + "/po/plan/save.action?time=" + new Date();
	var formData = $("#planForm").serializeArray();//将表单序列化成json格式
	plan.addFormParameters(formData,status);//添加其他表单信息
	jQuery.ajax({
		url : url,
		type : 'Post',//流程type类型(提交)
		async : false,
		contentType : "application/json;charset=UTF-8",//一对多关系必填否则去掉
		data : JSON.stringify(serializeJson(formData),replaceJsonNull),
//		data : JSON.stringify(serializeJson(formData)),
		success : function(data) {
			plan.subState = false;//更新重复提交状态标识
//			getToken();//更新token
			if (data.success == "true") {
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : jumpFun
					})
			} else {
				if (data.labelErrorMessage) {
					showErrors("planForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					if (data.errorMessage == "tokenError") {
						alertx(tokenMessage);
					} else if (data.errorMessage == "concurrentError") {
						alertx(concurrentMessage);
					} else {
						alertx(data.errorMessage);
					}
				} else {
					msgBox.tip({
						type : "fail",
						content : data.errorMessage
					});
					$("#token").val(data.token);
				}
			}
		},
		error : function() {
			plan.subState = false;
			msgBox.info({
				content : $.i18n.prop("JC_WORKFLOW_002")
			});
			$("#token").val(data.token);
		}
	});
	return result;
};

//添加工作计划详细表动态表单数据
plan.addFormParameters = function(formData,status) {
	var sumDetails = new Array();
	var planDetails = new Array();
	
	var sumContents = new Array();
	var sumStandard = new Array();
	var sumDirectorId = new Array();
	var sumStartTime = new Array();
	var sumEndTime = new Array();
	var sumCompRate = new Array();
	var sumDescription = new Array();
	var sumPlanPart = new Array();
	var sumDirectorIdValue = new Array();

	var planContents = new Array();
	var planStandard = new Array();
	var planDirectorId = new Array();
	var planStartTime = new Array();
	var planEndTime = new Array();
	var planCompRate = new Array();
	var planPart = new Array();
	var planDirectorIdValue = new Array();

	if(isFF()){
		$.each($("[id^=preSum_0_]"), function(i, o) {
			sumContents.push(o.textContent);
		});
		$.each($("[id^=preSum_1_]"), function(i, o) {
			sumStandard.push(o.textContent);
		});
	}else{
		$.each($("[id^=preSum_0_]"), function(i, o) {
			sumContents.push(o.innerText);
		});
		$.each($("[id^=preSum_1_]"), function(i, o) {
			sumStandard.push(o.innerText);
		});
	}
//	$.each($("[id^=preSum_10_]"), function(i, o) {
//		sumDirectorId.push(o.value);
//	});
	$.each($("input[id*='-preSum_2_']"), function(i, o) {
		//if(o.id.length>15 && o.id.indexOf("s2id")<0){
			if(returnValue(o.id)!=null){
				sumDirectorIdValue.push(returnValue(o.id).split(":")[1]);
				sumDirectorId.push(returnValue(o.id).split(":")[0]);
			}else{
				sumDirectorIdValue.push(null);
				sumDirectorId.push(null);
			}
		//}
	});
	$.each($("[id^=preSum_3_]"), function(i, o) {
		sumStartTime.push(o.value);
	});
	$.each($("[id^=preSum_4_]"), function(i, o) {
		sumEndTime.push(o.value);
	});
	$.each($("[id^=preSum_5_]"), function(i, o) {
		if(o.value == "0-100"){
			sumCompRate.push(null);
		}else{
			sumCompRate.push(o.value);
		}
	});
	if(isFF()){
		$.each($("[id^=preSum_6_]"), function(i, o) {
			sumDescription.push(o.textContent);
		});
	}else{
		$.each($("[id^=preSum_6_]"), function(i, o) {
			sumDescription.push(o.innerText);
		});
	}
	$.each($("[id^=preSum_9_]"), function(i, o) {
		sumPlanPart.push(o.value);
	});
	if(status == "Save"){
		$.each(sumContents, function(i, o) {
			sumDetails.push({
				"content" : sumContents[i],
				"standard" : sumStandard[i],
				"directorId" : sumDirectorId[i],
				"directorIdValue" : sumDirectorIdValue[i],
				"startTime" : sumStartTime[i],
				"endTime" : sumEndTime[i],
				"compRate" : sumCompRate[i],
				"description" : sumDescription[i],
				"planPart" : sumPlanPart[i]
			});
		});
	}else{
		$.each(sumContents, function(i, o) {
			if (sumContents[i] != "" && sumStandard[i] != "" 
				&& sumStartTime[i] !=="" && sumEndTime[i] != "" 
				&& sumCompRate[i] != "" ){
				if(sumDescription[i] == null ||sumDescription[i] == ""){
					sumDescription[i] = "";
				}
				sumDetails.push({
					"content" : sumContents[i],
					"standard" : sumStandard[i],
					"directorId" : sumDirectorId[i],
					"directorIdValue" : sumDirectorIdValue[i],
					"startTime" : sumStartTime[i],
					"endTime" : sumEndTime[i],
					"compRate" : sumCompRate[i],
					"description" : sumDescription[i],
					"planPart" : sumPlanPart[i]
				});
			}
		});
	}

	if(isFF()){
		$.each($("[id^=prePlan_0_]"), function(i, o) {
			planContents.push(o.textContent);
		});
		$.each($("[id^=prePlan_1_]"), function(i, o) {
			planStandard.push(o.textContent);
		});
	}else{
		$.each($("[id^=prePlan_0_]"), function(i, o) {
			planContents.push(o.innerText);
		});
		$.each($("[id^=prePlan_1_]"), function(i, o) {
			planStandard.push(o.innerText);
		});
	}
//	$.each($("[id^=prePlan_9_]"), function(i, o) {
//		planDirectorId.push(o.value);
//	});
	$.each($("div[id*='-prePlan_2_']"), function(i, o) {	
		//if(o.id.length>15 && o.id.indexOf("s2id")<0){
			if(returnValue(o.id)!=null){
				planDirectorIdValue.push(returnValue(o.id).split(":")[1]);
				planDirectorId.push(returnValue(o.id).split(":")[0]);
			}else{
				planDirectorIdValue.push(null);
				planDirectorId.push(null);
			}
		//}
	});
	$.each($("[id^=prePlan_3_]"), function(i, o) {
		planStartTime.push(o.value);
	});
	$.each($("[id^=prePlan_4_]"), function(i, o) {
		planEndTime.push(o.value);
	});
	$.each($("[id^=prePlan_5_]"), function(i, o) {
		if(o.value == "0-100"){
			planCompRate.push(null);
		}else{
			planCompRate.push(o.value);
		}
	});
	$.each($("[id^=prePlan_8_]"), function(i, o) {
		planPart.push(o.value);
	});
	
	if(status == "Save"){
		$.each(planContents, function(i, o) {
			planDetails.push({
				"content" : planContents[i],
				"standard" : planStandard[i],
				"directorId" : planDirectorId[i],
				"directorIdValue" : planDirectorIdValue[i],
				"startTime" : planStartTime[i],
				"endTime" : planEndTime[i],
				"compRate" : planCompRate[i],
				"planPart" : planPart[i]
			});
		});
	}else{
		$.each(planContents, function(i, o) {
			if (planContents[i] !="" && planStandard[i] != "" && planStartTime[i] != "" 
				&& planEndTime[i] != ""
				&& planCompRate[i] !=""){		
				planDetails.push({
					"content" : planContents[i],
					"standard" : planStandard[i],
					"directorId" : planDirectorId[i],
					"directorIdValue" : planDirectorIdValue[i],
					"startTime" : planStartTime[i],
					"endTime" : planEndTime[i],
					"compRate" : planCompRate[i],
					"planPart" : planPart[i]
				});
			}
		});
		
		
	}

	formData.push({
		"name" : "sumDetails",
		"value" : sumDetails
	});
	formData.push({
		"name" : "planDetails",
		"value" : planDetails
	});
};

//表单验证方法
function validateForm(type) {
	if(type=="Reject"){//退回按钮不验证
		return true;
	}else if(type=="Save"){//暂存不进行必填项校验
		if($("#planForm").valid()){
			if(compRateValid()){
				return true;
			}
		}
	}else if(type=="Submit"){//提交情况判断	
		if($("#flowStatus").val() == 3){//退回的提交或者暂存后的提交
			if($("#planForm").valid()){
				if(planBusinessValid()){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else if($("#flowStatus").val() == 0){//正常审批的提交
			if($("#planForm").valid()){
				if(planBusinessValid()){
					if(type=="Submit"){//审批时进行验证
						validateFormSubmitOne();//验证计划唯一性
						if(submitOn == "off"){
							return false;//计划不为一
						}else{
							return true;//允许提交
						}
					}else{
						return true;
					}
				}
			}
		}else{
			return true;
		}
	}else{
		if($("#planForm").valid()){
			if(planBusinessValid()){
				if(type=="Submit"){//审批时进行验证
					validateFormSubmitOne();//验证计划唯一性
					if(submitOn == "off"){
						return false;//计划不为一
					}else{
						return true;//允许提交
					}
				}else{
					return true;
				}
			}
		}
	}
}

//表单验证失败方法
function validateFormFail() {
}

//表单提交验证方法
function validateFormSubmitOne(){
	var sumSubmitYear = "";
	var sumSubmitMW = "";
	if($("#flowStatus").val() == 0 ||$("#flowStatus").val() == 3){//起草或驳回时取值
		sumSubmitYear = $("#sumSubmitYear").val();
		sumSubmitMW = $("#sumSubmitMW").val();
	}else{
		sumSubmitYear = $("#currentSubmitYear").val();
		sumSubmitMW = $("#currentSubmitMW").val();
	}

	var url = getRootPath() + "/po/plan/managePlanSubmitOneList.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		data : {"sumSubmitYear":sumSubmitYear,
			"sumSubmitMW":sumSubmitMW,
			"applyId":$("#applyId").val(),
			"applyDeptid":$("#applyDeptid").val(),
			"planType":$("#planType").val(),
			"planStartTime":$("#planStartTime").val(),
			"planEndTime":$("#planEndTime").val()
			},
		type : 'POST',
		async: false,
		success : function(data) {
			if(data.submitOn == "false"){
				submitOn = "off";
				msgBox.info({
					content : $.i18n.prop("JC_OA_PO_024")
				});
			}else{
				submitOn = "on";//提交的工作计划不重复
			}
		}
	});
}

//工作计划审批方法
function update(status, jumpFun) {
	var result = true;//流程提交返回值
	var url = getRootPath() + "/po/plan/update.action?time=" + new Date();
	if (plan.subState)
		return;
	plan.subState = true;

	var formData = $("#planForm").serializeArray();//将表单序列化成json格式
	plan.addFormParameters(formData,status);//添加其他表单信息
	jQuery.ajax({
		url : url,
		type : 'Post',//流程type类型（提交）
		async : false,
		contentType : "application/json;charset=UTF-8",//一对多关系必填否则去掉
		data : JSON.stringify(serializeJson(formData),replaceJsonNull),
		success : function(data) {
			plan.subState = false;
//			getToken();
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage,
					callback : jumpFun
				})
			} else {
				if (data.labelErrorMessage) {
					showErrors("planForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					if (data.errorMessage == "tokenError") {
						alertx(tokenMessage);
					} else if (data.errorMessage == "concurrentError") {
						alertx(concurrentMessage);
					} else {
						alertx(data.errorMessage);
					}
				} else {
					msgBox.tip({
						type : "fail",
						content : data.errorMessage
					});
				}
				$("#token").val(data.token);
			}
		}
	});
	return result;
}

//工作计划转发函数
plan.saveSend = function() {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/po/planSend/save.action",
		dataType : "json",
		success : function(data) {
			
		}
	});
};

//工作计划数据回显
function loadForm(piId) {
	if (piId.indexOf("HZ") == -1) {//加载页面时，是否是新建流程
		if (piId != null && piId.length > 0) {
			var planJsonStr = $("#plan").val();
			var planObj = eval("("+planJsonStr+")");
			loadPlanDetial(planObj);//加载计划编制表单数据
		}
	}
}

//加载工作计划
function loadPlanDetial(data) {
	var sumDetailsLength=0;
	var planDetailsLength=0;
	var sumAutoLength=0;
	var planAutoLength=0;
	$("#planForm").fill(data);
	if(data.success != "false"){
		//清除验证信息
		hideErrorMessage();
		//将后台绑定的数据回显到相应表单中
		$("#planForm").fill(data);
		if(data.sumDetails == null || data.sumDetails.length ==0){
			sumDetailsLength=6;
		}else{
			sumDetailsLength=data.sumDetails.length+1;
		}
		if(data.planDetails == null || data.planDetails.length==0){
			planDetailsLength=6;
		}else{
			planDetailsLength=data.planDetails.length+1;
		}
		//根据工作流状态判断
		if(data.flowStatus ==1 || data.flowStatus ==2 || data.flowStatus ==4 || data.flowStatus ==5){
			plan.autoTrInit(data.sumDetails.length,data.planDetails.length);//创建动态添加行
		}else if(data.flowStatus ==0){//暂存的情况
			if(data.sumDetails.length<6){
				sumAutoLength = 6;
			}else{
				sumAutoLength = data.sumDetails.length+1;
			}
			if(data.planDetails.length<6){
				planAutoLength = 6;
			}else{
				planAutoLength = data.planDetails.length+1;
			}
			plan.autoTrInit(sumAutoLength,planAutoLength);//创建动态添加行
		}else if(data.flowStatus ==3 && $("#scanType").val()=="todo"){//待办
			if(data.sumDetails.length<6){
				sumAutoLength = 6;
			}else{
				sumAutoLength = data.sumDetails.length+1;
			}
			if(data.planDetails.length<6){
				planAutoLength = 6;
			}else{
				planAutoLength = data.planDetails.length+1;
			}
			plan.autoTrInit(sumAutoLength,planAutoLength);//创建动态添加行
		}else if(data.flowStatus ==3 && $("#scanType").val()=="view"){//查看
			plan.autoTrInit(data.sumDetails.length,data.planDetails.length);//创建动态添加行
		}else{//d.flowStatus = '7' or d.flowStatus = '8'
			plan.autoTrInit(data.sumDetails.length,data.planDetails.length);//创建动态添加行
		}
		//填充工作总结详细数据
		if (data.sumDetails != null && data.sumDetails.length > 0) {
			var dataSumDetailsLength = data.sumDetails.length;
			for (var i = 0; i < dataSumDetailsLength; i++) {
				var o = data.sumDetails[i];
				$("#preSum_0_"+(i+1)).html(o.content);
				$("#preSum_1_"+(i+1)).html(o.standard);
				if(o.directorId != null && o.directorIdValue != null){
					$("#preSum_2_"+(i+1)+"-preSum_2_"+(i+1)).select2("data",{"id":o.directorId,"text":o.directorIdValue});	
				}
				$("#preSum_3_"+(i+1)).val(o.startTime);
				$("#preSum_4_"+(i+1)).val(o.endTime);
				$("#preSum_5_"+(i+1)).val(o.compRate);
				$("#preSum_6_"+(i+1)).html(o.description);
				$("#preSum_10_"+(i+1)).val(o.directorId);
				if(o.compRate!="" && o.compRate!=null && o.compRate!=0 && o.compRate != 100){
					$("#preSum_7_"+(i+1)).show();
				}
			}
		}
		//填充工作计划详细数据
		if (data.planDetails != null && data.planDetails.length > 0) {
			var dataPlanDetailsLength = data.planDetails.length;
			for (var i = 0; i < dataPlanDetailsLength; i++) {
				var o = data.planDetails[i];
				$("#prePlan_0_"+(i+31)).html(o.content);
				$("#prePlan_1_"+(i+31)).html(o.standard);
				if(o.directorId != null && o.directorIdValue != null){
					$("#prePlan_2_"+(i+31)+"-prePlan_2_"+(i+31)).select2("data",{"id":o.directorId,"text":o.directorIdValue});
				}
				$("#prePlan_3_"+(i+31)).val(o.startTime);
				$("#prePlan_4_"+(i+31)).val(o.endTime);
				$("#prePlan_5_"+(i+31)).val(o.compRate);
				$("#prePlan_9_"+(i+31)).val(o.directorId);
			}
		}
		//周.月.年计划标签设置
		$("#week").hide();
		$("#month").hide();
		$("#year").hide();
		//周计划调用初始化函数进行赋值
		if (data.planType == 0) {
			$("#appYearLabel").hide();
			$("#appMonthLabel").hide();
			$("#appWeekLabel").show();
			$("#dateNowYear").html("年第");
			$("#dateNow").html("周工作总结");
			$("#dateNextYear").html("年第");
			$("#dateNext").html("周工作计划");
		} else if (data.planType == 1) {//月计划回显处理
			$("#appYearLabel").hide();
			$("#appMonthLabel").show();
			$("#appWeekLabel").hide();
			$("#dateNowYear").html("年");
			$("#dateNow").html("月份工作总结");
			$("#dateNextYear").html("年");
			$("#dateNext").html("月份工作计划");
		} else {//年计划回显处理
			$("#appYearLabel").show();
			$("#appMonthLabel").hide();
			$("#appWeekLabel").hide();
			$("#sumSubmitYear").hide();
			$("#sumSpan").hide();
			$("[itemname=sumSubmitYear]").hide();
			$("#dateNow").html("年工作总结");
			$("#planSubmitYear").hide();
			$("#planSpan").hide();
			$("[itemname=planSubmitYear]").hide();
			$("#dateNext").html("年工作计划");
		}
		
		//简易模版.标准模版tab设置
		if (data.templateType == 0) {
			$("#jyPlanId").attr("class", "active");
			$(".planning-jyTbz").hide();//标准模版隐藏
		} else if (data.templateType == 1) {
			$("#jyPlanId").attr("class", "");
			$("#bzPlanId").attr("class", "active");
			$(".planning-jyTbz").show();//标准模版显示
		} else {
			$("#jyPlanId").attr("class", "active");
			$("#bzPlanId").hide();
		}
		
//		alert(data.flowStatus);
//		alert($("#scanType").val());
//		if(data.flowStatus == 1 && ($("#scanType").val()=="view" || $("#scanType").val()=="done" || $("#scanType").val()=="todo")){
//			if (data.templateType == 0) {
//				$("#bzPlanId").hide();//标准模版tab显示
//			} else if (data.templateType == 1) {
//				$("#jyPlanId").hide();
//			} else {
//				$("#bzPlanId").hide();
//			}
//		}else if(data.flowStatus == 3 && ($("#scanType").val()=="done"||$("#scanType").val()=="view" )){
//			if (data.templateType == 0) {
//				$("#bzPlanId").hide();//标准模版tab显示
//			} else if (data.templateType == 1) {
//				$("#jyPlanId").hide();
//			} else {
//				$("#bzPlanId").hide();
//			}
//		}else if(data.flowStatus == 7){
//			if (data.templateType == 0) {
//				$("#bzPlanId").hide();//标准模版tab显示
//			} else if (data.templateType == 1) {
//				$("#jyPlanId").hide();
//			} else {
//				$("#bzPlanId").hide();
//			}
//		}
		
		if((data.flowStatus == 1 && ($("#scanType").val()=="view" || $("#scanType").val()=="done" || $("#scanType").val()=="todo"))
		  ||(data.flowStatus == 3 && ($("#scanType").val()=="done"||$("#scanType").val()=="view" ))
		  ||(data.flowStatus == 7)
		){
			if (data.templateType == 0) {
				$("#bzPlanId").hide();//标准模版tab显示
			} else if (data.templateType == 1) {
				$("#jyPlanId").hide();
			} else {
				$("#bzPlanId").hide();
			}
		}
		
		$("#planForm #planType").val(data.planType);
		$("#currentUserId").val(data.applyIdValue);//当前登录人Id
		$("#currentSubmitYear").val(data.sumSubmitYear);//审批记录工作计划年
		$("#currentSubmitMW").val(data.sumSubmitMW);//审批记录工作计划周/月
	}else {
		if(data.labelErrorMessage){
			showErrors("planForm", data.labelErrorMessage);
		} else{
			msgBox.info({
				content: data.errorMessage
			});
		}
	}
}

//处理下发任务按钮方法
function afterInit() {
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#applyUserId").val();//工作计划申请人ID
	var planId = $("#id").val();
	
	if ($("#flowStatus").val() == 7){//流程审批通过状态
		jQuery.ajax({
			url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
			type : 'GET',
			data : {"sourceUser": sourceUser,"targetUser":targetUser},
			success : function(data) {
				if(data.isLeader == "true"||sourceUser == targetUser){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
					$("[itemname=planTaskItem]").show();
					//领导批注div显示
					$("#leaderIdeaDiv").show();
					//初始化领导批注
					plan.initAnno(planId);
					//绑定领导批注保存按钮方法
					$("#leaderIdea").click(function() {
						plan.saveLeaderIdeaForm(planId);
					});
					//绑定批注回复事件
					$('#comment').on('click', 'a[name="reply"]', function() {
						plan.commentReply(this);
					});
					//绑定批注发送按钮事件
					$('#comment').on('click', 'a[name="send"]', function() {
						plan.commentSend(this);
					});
					//绑定批注内容清除
					$('#leaderIdeaForm').on('click', '#clearleaderIdea', function() {
						$('#leaderIdeaContent').val("");
					});
				}else{
					$("#leaderIdeaDiv").show();//领导批注区域整体展示
					$("#leaderIdeaReplayForm").show();//批注回复内容展示
					plan.initAnnoReadOnly(planId);// 初始化领导批注(只读)
					$("#leaderIdeaForm").hide();//领导批注区域新增隐藏
				}
			}
		});
	}
}

//表单权限控制方法
plan.formPriv = function() {
	$("#planForm").find("[workFlowForm]").each(function() {
		var item = $(this);
		var itemId = $(this).attr("itemName");
		var itemType = $(this).attr("workFlowForm");
		formPriv.type[itemType].read(item);
	});
};

//加载计划表单数据(存在数据)
plan.planFormLoadData = function(piId, planType) {
	var ajaxData = {
			piId : piId,
			time : new Date()
	};
	var url = getRootPath() + "/po/plan/planAnnoDataLoad.action?time="+ new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : {"piId": piId},
		success : function(data) {
			$("#planFormDiv").html(data);
			$("#planFormDiv").html();//刷新的div
			if (planType == 0) {
				$("#submitYearSpan").text("年第");
				$("#submitMWSpan").text("周工作总结");
			} else if (planType == 1) {
				$("#submitYearSpan").text("年");
				$("#submitMWSpan").text("月份工作总结");
			} else {
				$("#submitYearSpan").text("年第");
				$("#submitMWSpan").text("周工作总结");
			}
			columnValSum = 0;//总结默认序号
			columnValPlan = 30;//计划内容默认序号
			codeValPlan = 0;//计划序号默认序号
			loadPlanDetial(ajaxData);
			afterInit();//领导批注
			plan.formPriv();//表单权限
		}
	});
};

//加载计划表单数据(没有数据)
plan.planFormLoadNoData = function() {
	var url = getRootPath() + "/po/plan/planAnnoNoDataLoad.action?time="+ new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		success : function(data) {
			$("#planFormDiv").html(data);//刷新的div
		}
	});
};

//简易模版/标准模版tab点击处理
$(document).on('click','.abtn',function(e){
	e.preventDefault();
	$(".abtn").parent().removeClass();
	$(e.target).parent().addClass("active");
	if ($(e.target).hasClass("planning-jy")) {
		$(".planning-jyTbz").hide();
		$("#templateType").attr("value", '0');//简易模版隐藏域赋值
	} else {
		$(".planning-jyTbz").show();
		$("#templateType").attr("value", '1');//标准模版隐藏域赋值
	}
});

/*****************************************************************
 * 工作计划表单 end
/************** **************************************************/





/****************************************************************
 * 工作计划相关导入 start
 * @author 刘锡来
 * @version  2014-08-12
*****************************************************************/
//工作总结表格数据是否填写验证
function sumTableDataValid(){
    var sumTable = document.getElementById('preSum');//获得工作总结table对象
    var sumTableLength = sumTable.rows.length;//获得工作总结table对象长度
    for(var i=1;i<sumTableLength;i++){
		if(sumTable.rows[i].cells[1].childNodes[0].childNodes[0].innerHTML != ""){return false;}//主要完成事项为空验证
		if(sumTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML != ""){return false;}//完成标准是否为空验证
//		if(sumTable.rows[i].cells[3].childNodes[0].childNodes[0].value != ""){return false;}//负责人是否为空验证
		if(returnValue(sumTable.rows[i].cells[3].childNodes[0].childNodes[0].id+"-"+sumTable.rows[i].cells[3].childNodes[0].childNodes[0].id) != null){return false;}//负责人是否为空验证
		if(sumTable.rows[i].cells[4].childNodes[0].childNodes[0].value != ""){return false;}//开始时间是否为空验证
		if(sumTable.rows[i].cells[5].childNodes[0].childNodes[0].value != ""){return false;}//完成时间是否为空验证
//		if(isIE()){
//			if(sumTable.rows[i].cells[6].childNodes[0].childNodes[1].value != ""){return false;}//完成比例是否为空验证
//		}else{
		if(sumTable.rows[i].cells[6].childNodes[0].childNodes[0].value != "0-100"){return false;}//完成比例是否为空验证	
//		}
		if(sumTable.rows[i].cells[7].childNodes[0].childNodes[0].innerHTML != ""){return false;}//未完成原因说明
    }
    return true;
}

//工作计划表格数据是否填写验证
function planTableDataValid(){
    var planTable = document.getElementById('prePlan');//获得工作计划table对象
    var planTableLength = planTable.rows.length;//获得工作计划table对象长度
    for(var i=1;i<planTableLength;i++){
		if(planTable.rows[i].cells[1].childNodes[0].childNodes[0].innerHTML != ""){return false;}//主要完成事项为空验证
		if(planTable.rows[i].cells[2].childNodes[0].childNodes[0].innerHTML != ""){return false;}//完成标准是否为空验证
//		if(planTable.rows[i].cells[3].childNodes[0].childNodes[0].value != ""){return false;}//负责人是否为空验证
		if(returnValue(planTable.rows[i].cells[3].childNodes[0].childNodes[0].id+"-"+planTable.rows[i].cells[3].childNodes[0].childNodes[0].id) != null){return false;}//负责人是否为空验证
		if(planTable.rows[i].cells[4].childNodes[0].childNodes[0].value != ""){return false;}//开始时间是否为空验证
		if(planTable.rows[i].cells[5].childNodes[0].childNodes[0].value != ""){return false;}//完成时间是否为空验证
//		if(isIE()){
//			if(planTable.rows[i].cells[6].childNodes[0].childNodes[1].value != ""){return false;}//完成比例是否为空验证
//		}else{
		if(planTable.rows[i].cells[6].childNodes[0].childNodes[0].value != "0-100"){return false;}//完成比例是否为空验证	
//		}
    }
    return true;
}

//导入计划
function planBoxFillData() {
	var chkPlanValue = "";//未处理的计划内容字符串
	var insertNum = 0;//从哪行开始添加数值
	var planResult = "";//计划内容最终字符串
	var targetTable = document.getElementById('prePlan');//获得计划计划table对象
	var targetTableLength = targetTable.rows.length -1;
	var checkPlanIdLength = $("input[name^=checkPlanIds]:checked").length;
	//用于存放导入计划的主表id
	var planIdList = new Array();
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
			planIdList.push(plan.getPlanId(v.id));
		});
		//工作计划是否删除校验 start
		if(planIdList!=null){
			for(var i=0;i<planIdList.length;i++){
				if(!DeleteCascade.syncCheckExist("planAnno",planIdList[i])){
					msgBox.info({
						content: $.i18n.prop("JC_OA_COMMON_015")
					});
					return;
				}
			}
		}
		//处理任务最终内容
		planResult = chkPlanValue.substring(0, chkPlanValue.length-1).split("|");
		var planResultLength = planResult.length;
		
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
				if((planResultLength/8) >= targetTableLength){
					//插入动态添加行,总结不插入
					plan.autoTrInit(0, (planResultLength/8) - targetTableLength);
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				
				for(var i=0;i<planResultLength/8;i++){
					$("#prePlan_0_"+(i+31)).html(planResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+31)).html(planResult[n+1]);//完成标准
//					$("#prePlan_2_"+(i+31)).val(planResult[n+2]);//负责人
					$("#prePlan_2_"+(i+31)+"-prePlan_2_"+(i+31)).select2("data",{"id":planResult[n+7],"text":planResult[n+2]});
					$("#prePlan_3_"+(i+31)).val(planResult[n+3]);//开始时间
					$("#prePlan_4_"+(i+31)).val(planResult[n+4]);//结束时间
					$("#prePlan_5_"+(i+31)).val(planResult[n+5]);//完成比例
					$("#prePlan_9_"+(i+31)).val(planResult[n+7]);//人员隐藏域ID
					n += 8;
				}
			}else{//不从第一行插入
				if((planResultLength/8 + insertNum-30) >= targetTable.rows.length){
					plan.autoTrInit(0,(planResultLength/8 + insertNum-30) - targetTableLength);
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				for(var i=0;i<planResultLength/8;i++){
					$("#prePlan_0_"+(i+1+insertNum)).html(planResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+1+insertNum)).html(planResult[n+1]);//完成标准
//					$("#prePlan_2_"+(i+1+insertNum)).val(planResult[n+2]);//负责人
					$("#prePlan_2_"+(i+1+insertNum)+"-prePlan_2_"+(i+1+insertNum)).select2("data",{"id":planResult[n+7],"text":planResult[n+2]});
					$("#prePlan_3_"+(i+1+insertNum)).val(planResult[n+3]);//开始时间
					$("#prePlan_4_"+(i+1+insertNum)).val(planResult[n+4]);//结束时间
					$("#prePlan_5_"+(i+1+insertNum)).val(planResult[n+5]);//完成比例
					$("#prePlan_9_"+(i+1+insertNum)).val(planResult[n+7]);//人员隐藏域ID
					n += 8;
				}
			}
			$("#viewPlan").modal("hide");//弹出层隐藏
		}
	}
}
/**
 * 过滤
 */
plan.getPlanId=function(pId){
	var planId='';
	if(pId !=null && pId !=''){
		planId=pId.split('_');
		if(planId!=null){
			planId=planId[1];
		}
	}
	return planId;
};
//导入任务
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
			var checkPlanIds_9 = $("input[name="+v.name+"]").parent().parent().children("td").get(9).innerHTML;
			var checkPlanIds_10 = v.value;
			chkTaskValue += checkTaskIds_2 +"|"+ checkTaskIds_3 +"|"+ checkTaskIds_4 +"|"+ checkTaskIds_5 +"|"+ 
						    checkTaskIds_6 +"|"+ checkTaskIds_7 +"|"+ checkTaskIds_8 +"|"+ checkPlanIds_9 +"|"+checkPlanIds_10+"|";
		});
		//处理任务最终内容
		taskResult = chkTaskValue.substring(0, chkTaskValue.length-1).split("|");
		var taskResultLength = taskResult.length;
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
				if((taskResultLength/9) >= targetTableLength){
					//插入动态添加行,总结不插入
					plan.autoTrInit(0, (taskResultLength/9) - targetTableLength);
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				for(var i=0;i<taskResultLength/9;i++){
					$("#prePlan_0_"+(i+31)).html(taskResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+31)).html(taskResult[n+3]);//完成标准
					$("#prePlan_2_"+(i+31)+"-prePlan_2_"+(i+31)).select2("data",{"id":taskResult[n+8],"text":taskResult[n+5]});//负责人
					$("#prePlan_3_"+(i+31)).val(taskResult[n+6]);//开始时间
					$("#prePlan_4_"+(i+31)).val(taskResult[n+7]);//结束时间
					$("#prePlan_9_"+(i+31)).val(taskResult[n+8]);//人员隐藏域ID
					n += 9;
				}
			}else{
				if((taskResultLength/9 + insertNum-30) >= targetTable.rows.length){
					plan.autoTrInit(0,(taskResultLength/9 + insertNum-30) - targetTableLength);
				    flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
				}
				var n = 0;
				for(var i=0;i<taskResultLength/9;i++){
					$("#prePlan_0_"+(i+1+insertNum)).html(taskResult[n]);//主要完成事项
					$("#prePlan_1_"+(i+1+insertNum)).html(taskResult[n+3]);//完成标准
					$("#prePlan_2_"+(i+1+insertNum)+"-prePlan_2_"+(i+1+insertNum)).select2("data",{"id":taskResult[n+8],"text":taskResult[n+5]});//负责人
					$("#prePlan_3_"+(i+1+insertNum)).val(taskResult[n+6]);//开始时间
					$("#prePlan_4_"+(i+1+insertNum)).val(taskResult[n+7]);//结束时间
					$("#prePlan_9_"+(i+1+insertNum)).val(taskResult[n+8]);//人员隐藏域ID
					n += 9;
				}
			}
			$("#viewTask").modal("hide");//关闭任务弹出层
		}
	}
}

//导入上期计划
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
		if(planSubmit==0||planSubmit==12){
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
		contentType: "application/json;charset=UTF-8",
		success:function(data){
			if(data!=null){
				var dataPlan = data.plan.planDetails;
				if(dataPlan!=null){//上期存在数据
					var dataPlanLength = dataPlan.length;
					var sumTableRow = $("#preSum").find("tr").length-1;//总结动态行数量
//					var impPlanCount = dataPlan.length + insertNum - sumTableRow;//导入计划数据条数
					var sumTableTotal = sumTableRow + dataPlanLength;//导入后的总数量
					
					if(sumTableTotal>30){//总行数超过30行/
						msgBox.info({
							content: $.i18n.prop("JC_OA_PO_006")
						});
					}else{
//						$("#preSum").find("tbody").remove();
						if(insertNum < 6){
							if(dataPlanLength>sumTableRow){//导入的行数大于表格行数
								plan.autoTrInit(dataPlanLength-(sumTableRow-insertNum),0);
							}else if(dataPlanLength>(sumTableRow-insertNum)){
								plan.autoTrInit(dataPlanLength,0);
							}else{
								
							}
//							else{
//								plan.autoTrInit(dataPlanLength,0);
//							}
						}else{
							plan.autoTrInit(dataPlanLength,0);
						}
					    var targetTableRowCount = targetSumTable.rows.length;
					    flushSumRow(targetSumTable,targetTableRowCount);
						for(var i=0;i<dataPlanLength;i++){
							var o = data.plan.planDetails[i];
							$("#preSum_0_"+(i+1+insertNum)).html(o.content);
							$("#preSum_1_"+(i+1+insertNum)).html(o.standard);
//							$("#preSum_2_"+(i+1+insertNum)).val(o.directorIdValue);
							$("#preSum_2_"+(i+1+insertNum)+"-preSum_2_"+(i+1+insertNum)).select2("data",{"id":o.directorId,"text":o.directorIdValue});
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
			}else{
				msgBox.info({
					content: $.i18n.prop("JC_OA_PO_004")//没有工作计划可导入
				});
			}
		}
	});
}

//加入计划
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
				if(plan.checkStr($("#" + attrName).html())==true){
					msgBox.tip({
						type : "fail",
						content : "主要完成事项中含有特殊字符'|'"
					});
					return false;
				}
				source = $("#" + attrName).html();
			}else{
				if(i == 1 || i == 6){
					if(i==1 && plan.checkStr($("#" + attrName).html())==true){
						msgBox.tip({
							type : "fail",
							content : "完成标准中含有特殊字符'|'"
						});
						return false;
					}
					if(i==6 && plan.checkStr($("#" + attrName).html())==true){
						msgBox.tip({
							type : "fail",
							content : "未完成原因说明中含有特殊字符'|'"
						});
						return false;
					}
					source = source + "|" + $("#" + attrName).html();
				}else if(i == 2){
					if(returnValue(attrName + "-" + attrName) != null){
						source = source + "|" + returnValue(attrName + "-" + attrName);
					}else{
						source = source + "|" + "";
					}
				}else{
					source = source + "|" + $("#" + attrName).val();
				}
			}
		}
		
		//拼接人员选择隐藏ID
		var preSum = addPlan.id.split("_");
//		var directorId = preSum[0] + "_" + (eval(preSum[1])+3) + "_" + preSum[2];
//		source = source + $("#" + directorId).val();
		var directorId = preSum[0] + "_2" + "_" + preSum[2];
		if(returnValue(directorId + "-" + directorId) != null){
			source = source + returnValue(directorId + "-" + directorId).split(":")[0];
		}else{
			source = source + "";
		}
		var sum = source.split("|");
		var sumLength = sum.length;
		flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
		if (insertNum == 0) {
			if ((sumLength / 8) >= $("#prePlan").find("tr").length) {
				plan.autoTrInit(0, (sumLength / 8) - $("#prePlan").find("tr").length + 1);
			}
			var n = 0;
			for (var i = 0; i < sumLength / 8; i++) {
				$("#prePlan_0_" + (i+1+30)).html(sum[n+0]);
				$("#prePlan_1_" + (i+1+30)).html(sum[n+1]);
//				$("#prePlan_2_" + (i+1+30)).val(sum[n+2]);
				if(sum[n+2] != null && sum[n+2] != ""){
//					$("#prePlan_2_"+(i+1+30)+"-prePlan_2_"+(i+1+30)).select2("data",{"id":sum[n+2].split(":")[0],"text":sum[n+2].split(":")[1]});
					selectControl.init("prePlan_2_"+(i+1+30),"prePlan_2_"+(i+1+30) +"-prePlan_2_"+(i+1+30), false, true, null,{"id":sum[n+2].split(":")[0],"text":sum[n+2].split(":")[1]});
				}
				$("#prePlan_3_" + (i+1+30)).val(sum[n+3]);
				$("#prePlan_4_" + (i+1+30)).val(sum[n+4]);
				$("#prePlan_5_" + (i+1+30)).val(sum[n+5]);
				$("#prePlan_9_" + (i+1+30)).val(sum[n+2].split(":")[0]);
				n += 8;
			}
		} else {
			if ((sumLength / 8 + insertNum - 30) >= $("#prePlan").find("tr").length) {
				plan.autoTrInit(0, (sumLength / 8 + insertNum - 30) - $("#prePlan").find("tr").length + 1);
			}
			var n = 0;
			for (var i = 0; i < sumLength / 8; i++) {
				$("#prePlan_0_" + (i+1+insertNum)).html(sum[n+0]);
				$("#prePlan_1_" + (i+1+insertNum)).html(sum[n+1]);
//				$("#prePlan_2_" + (i+1+insertNum)).val(sum[n+2]);
				if(sum[n+2] != null && sum[n+2] != ""){
//					$("#prePlan_2_"+(i+1+insertNum)+"-prePlan_2_"+(i+1+insertNum)).select2("data",{"id":sum[n+2].split(":")[0],"text":sum[n+2].split(":")[1]});
					selectControl.init("prePlan_2_"+(i+1+insertNum),"prePlan_2_"+(i+1+insertNum) +"-prePlan_2_"+(i+1+insertNum), false, true, null,{"id":sum[n+2].split(":")[0],"text":sum[n+2].split(":")[1]});
				}
				$("#prePlan_3_" + (i+1+insertNum)).val(sum[n+3]);
				$("#prePlan_4_" + (i+1+insertNum)).val(sum[n+4]);
				$("#prePlan_5_" + (i+1+insertNum)).val(sum[n+5]);
				$("#prePlan_9_" + (i+1+insertNum)).val(sum[n+2].split(":")[0]);
				n += 8;
			}
		}
	}
}

//加入日程
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
			if(my.checkStr($("#" + attrName).html())==true){
				msgBox.tip({
					type : "fail",
					content : "主要完成事项中含有特殊字符'|'"
				});
				return false;
			}
			diaryJson = prePlanAttr[2] + "|" + $("#" + attrName).html();
		} else {
			if(i == 1){
				if(my.checkStr($("#" + attrName).html())==true){
					msgBox.tip({
						type : "fail",
						content : "完成标准中含有特殊字符'|'"
					});
					return false;
				}
				diaryJson = diaryJson + "|" + $("#" + attrName).html();
			}else if(i == 2){
				if(returnValue(attrName+"-"+attrName)!=null){
					diaryJson = diaryJson + "|" + returnValue(attrName+"-"+attrName).split(":")[1];
				}else{
					diaryJson = diaryJson + "";
				}
			}else{
				diaryJson = diaryJson + "|" + $("input#" + attrName).val();
			}
		}
	}
	var diaryJsonArr = diaryJson.split("|");
	var diaryJsonArrLength = diaryJsonArr.length;
	var userIdValue = returnValue(prePlanAttr[0]+"_2_"+prePlanAttr[2]+"-"+prePlanAttr[0]+"_2_"+prePlanAttr[2]); 
	for(var i=1;i<diaryJsonArrLength;i++){
		if(i==diaryJsonArrLength-1){
			if(!isNum.test(diaryJsonArr[i]) || diaryJsonArr[i] > 100 || diaryJsonArr[i] < 0){
				isDiaryShow = false;
				msgBox.tip({
					content: $.i18n.prop("JC_OA_PO_023")
				});
				break;
			} 
		}
		if(diaryJsonArr[i] == ""||diaryJsonArr[i] == "undefined"||userIdValue==null){
			msgBox.tip({
				content: $.i18n.prop("JC_OA_PO_012")
			});
			isDiaryShow = false;
			break;
		}
	}

	if(isDiaryShow){
		var userIdHid = $("#" + prePlanAttr[0] + "_9_" + prePlanAttr[2]).val();
		if(userIdValue!=null){
			userIdHid = userIdValue.split(":")[0];
		}
		diaryJson = diaryJson + "|" + userIdHid + "|" + title;
		var contentHaveBr = diaryJsonArr[1].indexOf("<BR>")>-1||diaryJsonArr[1].indexOf("<br>")>-1;//判断是否有回车
		var contentOutOfLength = $.trim(diaryJsonArr[1].replace(REGEX,"")).length > 125||$.trim(diaryJsonArr[2].replace(REGEX,"")).length > 2000;
		
		if(contentHaveBr && !contentOutOfLength){//标题换行,长度不越界
			msgBox.confirm({
				content: $.i18n.prop("JC_OA_PO_031"),
				success: function(){
						$('#diaryUser').html(diaryJsonArr[3]);
						$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,"")));
						$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]);
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
						    userIdHid;
			diaryJson = diaryJsonTemp;
		}else if(!contentHaveBr && contentOutOfLength){//标题不换行,长度越界
			msgBox.confirm({
				content: $.i18n.prop("JC_OA_PO_032"),
				success: function(){
					$('#diaryUser').html(diaryJsonArr[3]);
					$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,"")).substring(0,125));
					$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]);
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
							userIdHid;
			diaryJson = diaryJsonTemp;
		}else if(contentHaveBr && contentOutOfLength){//标题换行,长度越界
			msgBox.confirm({
				content: $.i18n.prop("JC_OA_PO_033"),
				success: function(){
					$('#diaryUser').html(diaryJsonArr[3]);
					$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,"")).substring(0,125));
					$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]);
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
							userIdHid;
			diaryJson = diaryJsonTemp;
		}else{
			$('#diaryUser').html(diaryJsonArr[3]);
			$('#diaryTitle').html($.trim(diaryJsonArr[1].replace(REGEX,"")));
			$('#diaryStartEndTime').html(diaryJsonArr[4] + " 至 " + diaryJsonArr[5]);
			$('#diaryContent').html(diaryJsonArr[2]);
			$('#showDiaryBox').modal('show');
			diaryJsonTemp = $.trim(diaryJsonArr[1].replace(REGEX,"")) + "|" +
						    diaryJsonArr[2] + "|" +
						    diaryJsonArr[4] + "|" +
						    diaryJsonArr[5] + "|" +
						    userIdHid;
			diaryJson = diaryJsonTemp;
		}
	}
}

//加入日程确定方法
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



//加载加入任务弹出层
function loadJoinTaskAddHtml(e){
	hideErrorMessage(); //add by lihongyu at 2015-3-3
	//add by xuwp at 2014-11-11 start
	var planId = $("#id").val();//当前计划ID
	if(!DeleteCascade.syncCheckExist("planAnno",planId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}
	//add by xuwp at 2014-11-11 end
	var evt = e||event;
	if(isFF()){
		var eventTarget = evt.target;
	}else{
		var eventTarget = evt.srcElement?evt.srcElement:evt.target;	
	}

	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#applyUserId").val();//工作计划申请人ID
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "true"||sourceUser == targetUser){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
				if($.trim($("#joinTaskFormEdit").html()) == ""){
					$("#joinTaskFormEdit").load(getRootPath()+"/po/plan/joinTaskFormEdit.action",null,function(){workTaskShow(eventTarget)});
				}else{
					workTaskShow(eventTarget);
				}
			}else{
				msgBox.info({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_047")
				});
			}
		}
	});
};

//加入任务弹出层
function workTaskShow(eventTarget){
	$("#init").modal("show");
	plan.clearForm();
	ie8StylePatch();
	$('#zdsz')[0].reset();
    var targetTable = document.getElementById('prePlan');
    $("#taskContent").val(eventTarget.parentNode.parentNode.cells[1].childNodes[0].innerHTML);//任务内容
    $("#standard").val(eventTarget.parentNode.parentNode.cells[2].childNodes[0].innerHTML);//完成标准
    $("#directorUserName").text(eventTarget.parentNode.parentNode.cells[3].childNodes[0].innerHTML);//负责人
    $("#directorName").val(eventTarget.parentNode.parentNode.cells[3].childNodes[0].innerHTML);//负责人
    $("#directorId").val(eventTarget.parentNode.childNodes[4].value);//完成标准
//  $("#sponsorId").val($("#applyId").val());//发起人
    $("#sponsorId").val($('#loginUserId').val());//发起人赋值为 当前登录人  李洪宇 2014-9-22
//	getToken("zdsz");
    
    $('#remindContent').val('');//手动清空超期提醒内容
}
//清空表单
plan.clearForm = function () {
	$('#zdsz')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("prticipantsName-prticipantsId");//参与人
	$("#businessId").val("0");//附件清空
	clearTable();
	echoAttachListDul(false,'attachList');
//	showAttachList(false);//附件清空
};
/*****************************************************************
 * 工作计划相关导入 end
/************** **************************************************/

/****************************************************************
 * 工作计划批注 start
 * @author 刘锡来
 * @version  2014-08-12
*****************************************************************/
//领导批注初始化方法
plan.initAnno = function(dataid) {
	$.ajax({
		type:"GET",
		url:getRootPath() + "/po/plan/queryLeaderIdea.action",
		data:{"businessId":dataid},
			dataType : "json",
			success : function(data){
				if(data){
					var liStr = '';
					var dataLength = data.length;
					if (dataLength == 0) {
						liStr += "<li class=\"clearfix m-b input-group\">" + 
								 "<p class=\"dialog-text input-group\" id=\"rootAnno\" name=\"rootAnno\">没有批注内容</p></li>";
					}
					var currentUser = $('#currentUser').val();//当前登录人
					for (var i = 0; i < dataLength; i++) {
						 var annoParent = data[i];
						 liStr += "<li class=\"clearfix m-b input-group\">" + 
								  "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\"" + 
								  annoParent.id + "\">" + 
								  "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
								  annoParent.annoUserIdValue + "：</strong>" + annoParent.content + 
								  "<span>" + annoParent.annoDate + "</span></p>";
								 
						 liStr += "<div class=\"input-group-btn p-l\">" + 
						          "<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"anno"+i+"\" name=\"reply\">回 复</a></div>";
						 liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
		                          "<textarea name='replayAnno' rows=\"3\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+"："+"\"></textarea>"+
		                          "<div class=\"input-group-btn p-l\">"+
				                  "<input type=\"hidden\" name=\"byAnnoUserId\" value=\""+annoParent.annoUserId+"\">"+
				                  "<input type=\"hidden\" name=\"byAnnoUserDepid\" value=\""+annoParent.annoUserDepid+"\">"+
		                          " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
		                          "</div></div>";

						 //批注回复
						 var replyList = annoParent.annoReplyList;
						 var replyListLength = replyList.length;
						 if(replyList){
							for(var j = 0; j < replyListLength; j++){
								var annoReply = replyList[j];
								liStr += "<div class=\"dis-row\">" + 
										 "<p class=\"dialog-text input-group\" data=\"" + 
										 annoReply.id + "\">" + "<input type=\"hidden\" name=\"\">" + 
										 "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
										 annoReply.annoUserIdValue + "回复" + annoReply.parentUserName + 
										 "：</strong>" + annoReply.content + 
										 "<span>" + annoReply.annoDate + 
										 "</span></p>";
								liStr += "<div class=\"input-group-btn p-l\">" + 
								         	"<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"reply"+j+"\" name=\"reply\">回 复</a></div>" +
								         "</div>" +
								         "<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
								         "<textarea name='replayAnno' rows=\"3\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+"："+"\"></textarea>"+
								         "<div class=\"input-group-btn p-l\">"+
						                 "<input type=\"hidden\" name=\"byAnnoUserId\" value=\""+annoParent.annoUserId+"\">"+
						                 "<input type=\"hidden\" name=\"byAnnoUserDepid\" value=\""+annoParent.annoUserDepid+"\">"+
								         " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
								         "</div></div>";
							}
						}
						liStr += "</li>";
					}
					$('#comment').append(liStr);
				}
			}
		});
};

//领导批注初始化方法
plan.initAnnoReadOnly = function(dataid) {
	$.ajax({
		type:"GET",
		url:getRootPath() + "/po/plan/queryLeaderIdea.action",
		data:{"businessId":dataid},
			dataType : "json",
			success : function(data){
				if(data){
					var liStr = '';
					var dataLength = data.length;
					if (dataLength == 0) {
						liStr += "<li class=\"clearfix m-b input-group\">" + 
								 "<p class=\"dialog-text input-group\" id=\"rootAnno\" name=\"rootAnno\">没有批注内容</p></li>";
					}
					var currentUser = $('#currentUser').val();//当前登录人
					for (var i = 0; i < dataLength; i++) {
						 var annoParent = data[i];
						 liStr += "<li class=\"clearfix m-b input-group\">" + 
								  "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\"" + 
								  annoParent.id + "\">" + 
								  "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
								  annoParent.annoUserIdValue + "：</strong>" + annoParent.content + 
								  "<span>" + annoParent.annoDate + "</span></p>";
						 
						 //批注回复
						 var replyList = annoParent.annoReplyList;
						 var replyListLength = replyList.length;
						 if(replyList){
							for(var j = 0; j < replyListLength; j++){
								var annoReply = replyList[j];
								liStr += "<div class=\"dis-row\">" + 
										 "<p class=\"dialog-text input-group\" data=\"" + 
										 annoReply.id + "\">" + "<input type=\"hidden\" name=\"\">" + 
										 "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
										 annoReply.annoUserIdValue + "回复" + annoReply.parentUserName + 
										 "：</strong>" + annoReply.content + 
										 "<span>" + annoReply.annoDate + 
										 "</span></p>";
							}
						}
						liStr += "</li>";
					}
					$('#comment').append(liStr);
				}
			}
		});
};

//领导批注保存方法
plan.saveLeaderIdeaForm = function(planId) {
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#applyUserId").val();//工作计划申请人ID
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(!DeleteCascade.syncCheckExist("planAnno",planId)){
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_015")
				});
				return;
			}else{
				//校验表单信息
				if ($("#leaderIdeaForm").valid()) {
					var url = getRootPath() + "/po/anno/save.action?time=" + new Date();
					//将表单序列化成json格式
					var formData = $("#leaderIdeaForm").serializeArray();
					formData.push({"name":"businessId","value":planId});
					formData.push({"name":"annoType","value":0});
					formData.push({"name":"annoName","value":$("[itemname='planTitle']").html()});
					formData.push({"name":"byAnnoUserId","value":$('#applyId').val()});
					formData.push({"name":"byAnnoUserDepid","value":$('#applyDeptid').val()});
					formData.push({"name":"token","value":$('#token').val()});
					jQuery.ajax({
						url : url,
						type : 'POST',
						data : formData,
						success : function(data){
//							getToken();//更新token
							if (data.success == "true") {
								msgBox.tip({
									type : "success",
									content : data.successMessage,
									callback : function() {
										$('#leaderIdeaContent').val('');
										$('#comment').empty();
										plan.initAnno(planId);
										$("#token").val(data.token);
									}
								});
							} else {
								if(data.labelErrorMessage) {
									showErrors("leaderIdeaForm", data.labelErrorMessage);
								}else{
									msgBox.info({
										type : "fail",
										content : data.errorMessage
									});
								}
								$("#token").val(data.token);
							}
						},
						error : function() {
							plan.subState = false;
							msgBox.tip({
								type : "fail",
								content : $.i18n.prop("JC_SYS_002")
							});
						}
					});
				}
			}
		}
	});
};


//领导批注回复方法
plan.commentReply = function(e) {
	var parentId = $(e).parent().prev().attr('data');
	var id = $(e).attr('id');
	$(e).toggle();//点击的回复按钮隐藏
	var $showArea = $(e).parents('li').find('div[class*="hf-area"][for="'+id+'"]');
	$showArea.find('a[name="send"]').attr('data',parentId);
	$showArea.find('textarea').val('');
	$showArea.toggle();//显示当前批注的回复文本域
	$('a[name="reply"]').not($(e)).show();//显示其他回复按钮
	$showArea.css("display","table-row");//添加样式
	$(e).parents('ul').find('div[class*="hf-area"]').not($showArea).hide();
	iePlaceholderPatch();//ie8下水印插件调用
};

//领导批注发送方法
plan.commentSend = function(e) {
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#applyUserId").val();//工作计划申请人ID
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "true"||sourceUser == targetUser){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
				var businessId = $('#id').val();
				var annoParentId = $(e).attr('data');
				var byAnnoUserId = $(e).prevAll('input[name="byAnnoUserId"]').val();
				var byAnnoUserDepid = $(e).prevAll('input[name="byAnnoUserDepid"]').val();
				var annoName = $("[itemname=planTitle]").text();
				var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
				var content = $(e).parent().parent().find("textarea").val();
				
				if(!DeleteCascade.syncCheckExist("planAnno",businessId)){
					msgBox.info({
						content: $.i18n.prop("JC_OA_COMMON_015")
					});
					return;
				}else{
					if($("#leaderIdeaReplayForm").valid()){
						$.ajax({
							type : "POST",
							url : getRootPath() + "/po/plan/annoReply.action?time=" + new Date(),
							data : {
								"businessId" : businessId,
								"annoName" : annoName,
								"annoParentId" : annoParentId,
								"rootParentId" : rootParentId,
								"byAnnoUserId" : $('#applyId').val(),
								"byAnnoUserDepid" : $('#applyDeptid').val(),
								"content" : content
							},
							dataType : "json",
							success : function(data) {
								if (data.success == "true") {
									msgBox.tip({
										type : "success",
										content : data.successMessage,
										callback : function() {
											$('#comment').empty();
											plan.initAnno(businessId);
										}
									});
								} else {
									msgBox.info({
										type : "fail",
										content : data.errorMessage
									});
								}
							}
						});
					}
				}
			}else{
				msgBox.info({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_047")
				});
			}
		}
	});
};
/*****************************************************************
 * 工作计划批注 end
/************** **************************************************/


/*****************************************************************
 * 工作计划表单初始化 start
 * @author 刘锡来
 * @version  2014-08-12
/************** **************************************************/


//加载添加DIV
plan.loadPlanAddHtml = function (){
	if($.trim($("#planFormEdit").html()) == ""){
		$("#planFormEdit").load(getRootPath()+"/po/plan/planFormEdit.action",null,plan.initPlanBoxList);
	}else{
		plan.initPlanBoxList();
	}
};

//加载添加DIV
plan.loadTaskAddHtml = function (){
	if($.trim($("#taskFormEdit").html()) == ""){
		$("#taskFormEdit").load(getRootPath()+"/po/plan/taskFormEdit.action",null,plan.initTaskBoxList);
	}else{
		plan.initTaskBoxList();
	}
};

//初始化查询计划弹出层方法
plan.initPlanBoxList = function(){
	planRowDetail.resetPlanBoxList();
	$(".datepicker-input").datepicker();
	ie8StylePatch();
	//显示计划弹出层内容
	$('#viewPlan').modal('show');
	//初始化查看任务弹出层列表
	planRowDetail.planRowDetailInit();
};

//初始化查询任务弹出层方法
plan.initTaskBoxList = function(){
	taskRowDetail.resetTaskBoxList();
	$(".datepicker-input").datepicker();
	ie8StylePatch();
	//显示任务弹出层内容
	$('#viewTask').modal('show');
	//初始化查看任务弹出层列表
	taskRowDetail.taskRowDetailInit();
};


function initFun(){
	//工作计划类型初始化赋值
	$("#planType").attr("value", 0);//初始化工作计划类型
	var result = plan.showLabelInit();
	if(result == false){
		return false;
	}
	var condition = $("#diaryCondition").val();
	if(typeof(condition) != "undefined" && condition != ""){
	   //日程快捷方式跳转初始化
	   plan.diaryJumpInit(condition);
	}
	//计划编制页面周、月、年label标签初始化
	//计划分页显示条数
	plan.planPageRows = TabNub > 0 ? TabNub : 10;
	//任务分页显示条数
	plan.taskPageRows = TabNub > 0 ? TabNub : 10;
	//审批(年)div隐藏
	$("#appYearLabel").hide();
	//审批(月)div隐藏
	$("#appMonthLabel").hide();
	//审批(周)div隐藏
	$("#appWeekLabel").hide();
	//领导批注div隐藏
	$("#leaderIdeaDiv").hide();
	//绑定导入上期计划按钮事件(计划编制)
	$("#importSum").click(function(){querySumTable();});
	//绑定查看计划按钮事件(计划编制)
	$("#showPlanQueryBox").click(plan.loadPlanAddHtml);
	//绑定查看任务按钮事件(计划编制)
	$("#showTaskQueryBox").click(plan.loadTaskAddHtml);
	//绑定计划加入日程确定按钮事件
	$("#toModify").click(function(){importShowDiary(diaryJson);});
	var piId = $("#dataId").val();
	loadForm(piId);
	formDetail.initForm();
	afterInit();
}
/*****************************************************************
 * 工作计划表单初始化 end
/************** **************************************************/

//自定义校验  add by lihongyu at 2014-10-27 11:32
plan.checkStr=function(s){
	var rs =false;
	if(s.indexOf('|')!=-1){
		rs=true;
	}else{
		rs=false;
	}
	return rs; 
};
//自定义校验  add by lihongyu at 2014-10-27 11:32
my.checkStr=function(s){
	var rs =false;
	if(s.indexOf('|')!=-1){
		rs=true;
	}else{
		rs=false;
	}
	return rs; 
};
$(document).ready(function(){
	initFun();
	var plat = $("#planTheme");
	switch (getTheme()) {
	case "1":
		plat.removeClass("m-t-md").css("background-color","#fff");
		break;
	case "2":
		plat.removeClass("m-t-md").css({
			"background-color":"#fff",
			"padding-top"     : "20px"
		});
		break;
	}
})