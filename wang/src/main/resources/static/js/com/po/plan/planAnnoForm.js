/**
 * 工作计划js
 * @author 刘锡来
 * @version  2014-06-30
 */
var plan = {};
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
				plan.autoTrInit(dataPlanLength-5,0);	
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
		if (v.value == 100) {
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



//加载工作计划
function loadPlanDetial(data) {
	
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
				if(data.isLeader == "true" ||sourceUser == targetUser){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
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
//			afterInit();//领导批注
//			plan.formPriv();//表单权限
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


//下发任务弹出层关闭按钮
plan.closeSendTask = function() {
	//隐藏错误校验域
	hideErrorMessage();
	//清空附件
	clearTable();
	selectControl.clearValue("prticipantsName-prticipantsId");
	para_week = "";//清空提醒设置
	$("#remindPerName").val("");
	$("#remindContent").val("");
};

//下发工作任务弹出层隐藏方法
function hideDiv(){
	$("#init").modal("hide");//隐藏下发任务弹出层
	//成功提示
	msgBox.tip({
		type:"success",
		content:$.i18n.prop("JC_SYS_001")
	})
}


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
//					getToken();//更新token
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
						if (data.labelErrorMessage) {
							showErrors("leaderIdeaForm", data.labelErrorMessage);
						} else {
							msgBox.info({
								type : "fail",
								content : data.errorMessage
							});
						}
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
};
/*****************************************************************
 * 工作计划批注 end
/************** **************************************************/

//超期提醒设置预览
plan.showRemindSet=function(){
	var t=$('input[name="remindType"]:checked').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#optionsRadios2').val(s);//提醒方式
	var directorId=$('#directorName').val();
	var prticipantsId=returnValue('prticipantsName-prticipantsId');
	var dire=$('#directorId').val();
	var prti=$('#prticipantsName-prticipantsId').val();
	var perId='';
	if(directorId!=null && prticipantsId==null){
		perId+=directorId;
	}
	if(directorId==null && prticipantsId!=null){
		var prticipants= new Array(); 
		prticipants=prticipantsId.split(",");
		var participart='';
		var prticipantsLength = prticipants.length;
		for(var i=0;i<prticipantsLength;i++){
			if(i==(prticipantsLength-1)){
				participart+=prticipants[i].split(":")[1];
			}else{
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		perId+=participart;
	}
	if(directorId!=null && prticipantsId!=null){
		var prticipants= new Array();
		prticipants=prticipantsId.split(",");
		var participart='';
		var prticipantsLength = prticipants.length;
		for(var i=0;i<prticipantsLength;i++){
			if(prticipants[i].split(":")[1]!=directorId){
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		if(participart !=''){
			perId+=directorId+','+participart;
		}else{
			perId+=directorId;
		}
		
	}
	var per="";
	if(dire!=null && prti==null){
		per+=dire;
	}
	if(dire==null && prti!=null){
		per+=prti;
	}
	var partis='';
	if(dire!=null && prti!=null){
		var prt= new Array(); 
		prt=prti.split(",");
		var prtLength = prt.length;
		for(var i=0;i<prtLength;i++){
			if(i==(prtLength-1)){
				if(dire!=prt[i]){
					partis+=prt[i];
				}
			}else{
				if(dire!=prt[i]){
					partis+=prt[i]+',';
				}
			}
		}
		if(partis!=''){
			per+=dire+','+partis;
		}else{
			per+=dire;
		}
	}
	if(t!=0){
		$("#remindContent").attr("disabled",false);
		if(isSave==0){
			$('#remindContent').val($('#taskName').val());//提醒内容
		}else{
			if($('#remindContent').val()==null || $('#remindContent').val()==''){
				$('#remindContent').val($('#taskName').val());//提醒内容
				isSave=0;
			}
		}
		$('#remindPerId').val(per);
		$('#remindPerName').val(perId);
		$('#remindContentTemp').val($('#remindContent').val());
	}else{
		$('#remindContent').val("");
		$('#remindPerId').val("");
		$('#remindPerName').val("");
		$('#remindContentTemp').val("");
		$("#remindContent").attr("disabled",true);
		isSave=0;
	}
	$('#remindWindow').modal('show');
};