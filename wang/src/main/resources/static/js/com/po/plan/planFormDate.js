/**
 * 工作计划时间js
 */

/**
 * 年的时间控件（当前年，上一年，下一年）
 */
function year(num) {
	hideErrorMessage();//隐藏错误校验域
	flushYearValid();//重新加载校验方法
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
		$('input[name=planExtend]').val().indexOf("月份") != -1
		) {
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
			year = eval($("#sumSubmitMW").val()) + 1;
		}else{
			year = eval($("#sumSubmitMW").val()) - 1;
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
	$("#planExtend").val(year + "年工作总结" + eval(Number(year) + 1) + "年工作计划") ;
	$("#planType").val("2");
}

/**
 * 月的时间控件（当前月，上一月，下一月）
 */
function month(num) {
	hideErrorMessage();//隐藏错误校验域
	flushMonthValid();//重新加载校验方法
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
		if (year % 4 == 0) {
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

/**
 * 周的时间控件（当前周，上一周，下一周）
 */
function week(num) {
	hideErrorMessage();//隐藏错误校验域
	flushWeekValid();//重新加载校验方法
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
		
		$("#planSubmitYear").val(year);
		$("#dateNextYear").html("年第");
		$("#planSubmitMW").val(Number(week) + 1);
		$("#dateNext").html("周工作计划"); 

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
			
			$("#planStartTime").val(getNextWeek(nextweek.substring(0, 4), nextweek.substring(5, 7), nextweek.substring(8, 10)).substring(0, 10));
			$("#planEndTime").val(getNextWeek(nextweek.substring(0, 4), nextweek.substring(5, 7), nextweek.substring(8, 10)).substring(11,22));
		} else {
			week = Number(week) + 1;
			if(week==52){
				$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
				$("#planSubmitYear").val(eval($("#planExtend").val().substring(0,4))+1);
				
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
			$("#planExtend").val((eval($("#planExtend").val().substring(0,4))-1)+"年第" + week + "周工作总结(" + getPreviousWeek(year, month, day) + ")");
			
			$("#sumSubmitYear").val($("#planExtend").val().substring(0,4));
			$("#planSubmitYear").val(eval($("#planExtend").val().substring(0,4))+1);
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
		$("#planTitle").val($("#applyDeptidValue").val()+$("#applyIdValue").val()+"第"+week+"周工作总结与第" + (eval(week)+1) + "周工作计划");
	}
	$("#planType").val("0");
}

/**
 * 当前周
 */
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

/**
 * 上一周
 */
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

/**
 * 下一周
 */
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

function flushTableRow(){
	var targetSumTable = document.getElementById('preSum');	
	var targetPlanTable = document.getElementById('prePlan');	
    flushSumRow(targetSumTable,targetSumTable.rows.length);
    flushPlanRow(targetPlanTable,targetPlanTable.rows.length);
}

function fillTableData(dataSum,dataPlan){
	//填充工作总结详细数据
	if(dataSum.length > 0) {
		for(var i=0;i<dataSum.length;i++){
			var o = dataSum[i];
			$("#preSum_0_"+(i+1)).html(o.content);
			$("#preSum_1_"+(i+1)).html(o.standard);
			$("#preSum_2_"+(i+1)).val(o.directorIdValue);
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
	if(dataPlan.length > 0) {
		for(var i=0;i<dataPlan.length;i++){
			var o = dataPlan[i];
			$("#prePlan_0_"+(i+31)).html(o.content);
			$("#prePlan_1_"+(i+31)).html(o.standard);
			$("#prePlan_2_"+(i+31)).val(o.directorIdValue);
			$("#prePlan_3_"+(i+31)).val(o.startTime);
			$("#prePlan_4_"+(i+31)).val(o.endTime);
			$("#prePlan_5_"+(i+31)).val(o.compRate);
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
						if(dataSum!=null&&dataPlan!=null){
							$("#preSum").find("tbody").remove();
							$("#prePlan").find("tbody").remove();
							if(dataSum.length > 6 || dataPlan.length > 6){
								plan.autoTrInit(dataSum.length,dataPlan.length);
							    flushTableRow();
							}
							if(dataSum.length <= 6){
								$("#preSum").find("tbody").remove();
								plan.autoTrInit(dataSum.length,0);
								var targetSumTable = document.getElementById('preSum');	
								flushSumRow(targetSumTable,targetSumTable.rows.length);
							}	
							if(dataPlan.length <= 6){
								$("#prePlan").find("tbody").remove();
								plan.autoTrInit(0,dataPlan.length);
								var targetPlanTable = document.getElementById('prePlan');	
								flushPlanRow(targetPlanTable,targetPlanTable.rows.length);
							}
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
									
									if(dataSum!=null&&dataPlan!=null){
										plan.autoTrInit(data.plan.sumDetails.length,data.plan.planDetails.length);
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
    $("#sumSubmitYear").rules("remove");
    $("#sumSubmitMW").rules("remove");
    $("#planSubmitYear").rules("remove");
    $("#planSubmitMW").rules("remove");

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
}

//重新加载年校验方法
function flushMonthValid(){
    $("#sumSubmitYear").rules("remove");
    $("#sumSubmitMW").rules("remove");
    $("#planSubmitYear").rules("remove");
    $("#planSubmitMW").rules("remove");

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
}


//重新加载年校验方法
function flushYearValid(){
    $("#sumSubmitMW").rules("remove");
    $("#planSubmitMW").rules("remove");
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