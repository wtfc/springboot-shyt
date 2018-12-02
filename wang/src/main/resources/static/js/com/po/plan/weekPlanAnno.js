/**
 * 周计划上级批注查询js
 */
var weekPlanAnno = {};
weekPlanAnno.subState = false;//重复提交标识,false时,允许提交表单
var columnValSum=0;//默认总结行号
var columnValPlan=30;//默认计划行号
var sumNum=0;//默认行号
var codeVal=0;
var tablePreSumId;//table id
var tablePrePlanId;//table id
var isCode=false;//是否生成序号
var codeValSum=0;//总结显示行号
var codeValPlan=0;//计划显示行号
/**
 * 初始化数据加载
 */
weekPlanAnno.initWeekPlanAnno = function(piId) {
	var url = getRootPath() + "/po/plan/planWorkFlowInit.action?planType=0&time="+ new Date();
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		success : function(data) {
			$("#showYearSpan").text(data.showYearSpan);//span年显示
			$("#piId").val(data.lastPiId);//流程ID
			var weeksStr = "";
			if(data.spanOn =="true"){
				for (var i=0;i< data.planList.length;i++) {
					if(i == 0){
						weeksStr += "<tr>";
						weeksStr += "<td id='"+ i +"' style='background-color:#ffc334' onclick=showOtherWeekPlan('"+data.planList[i].id+"',"+i+")><span>"
								 + data.showSpanList[i]
								 + "</span>周</td>";
						weeksStr += "</tr>";
					}else{
						weeksStr += "<tr>";
						weeksStr += "<td id='"+ i +"' onclick=showOtherWeekPlan('"+data.planList[i].id+"',"+i+")><span>"
								 + data.showSpanList[i]
								 + "</span>周</td>";
						weeksStr += "</tr>";
					}
				}
			}
			$("#weeksTbody").append(weeksStr);
			
			var dataDetailsSum = data.detailPlan.sumDetails;
			var dataDetailsPlan = data.detailPlan.planDetails;
			weekPlanAnno.autoTrInit(dataDetailsSum.length,dataDetailsPlan.length);
			//填充工作总结详细数据
			if (dataDetailsSum != null && dataDetailsSum.length >0) {
				var dataSumDetailsLength = dataDetailsSum.length;
				for (var i = 0; i < dataSumDetailsLength; i++) {
					var o = dataDetailsSum[i];
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
			if (dataDetailsPlan != null && dataDetailsPlan.length > 0) {
				var dataPlanDetailsLength = dataDetailsPlan.length;
				for (var i = 0; i < dataPlanDetailsLength; i++) {
					var o = dataDetailsPlan[i];
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
			$("#planTitle").val(data.planList[0].planTitle);
			$("#planStartTime").val(data.planList[0].planStartTime);
			$("#planEndTime").val(data.planList[0].planEndTime);
			$("#applyIdValue").val(data.planList[0].applyIdValue);
			$("#applyDeptidValue").val(data.planList[0].applyDeptidValue);
			$("#applyUserId").val(data.planList[0].applyUserId);
			$("#id").val(data.planList[0].id);
			$("#applyId").val(data.planList[0].applyId);
			$("#applyDeptid").val(data.planList[0].applyDeptid);
			$("#remark").val(data.planList[0].remark);
			$("#manaInno").val(data.planList[0].manaInno);
			$("#costControl").val(data.planList[0].costControl);
			$("#trainingSum").val(data.planList[0].trainingSum);
			$("#problemMeas").val(data.planList[0].problemMeas);
			$("#token").val(data.token);
			$("#otherSpanSumDiv").html(data.planList[0].sumSubmitYear+"年第"+data.planList[0].sumSubmitMW+"周工作总结");
			$("#otherSpanPlanDiv").html(data.planList[0].planSubmitYear+"年第"+data.planList[0].planSubmitMW+"周工作计划");
			//简易模版.标准模版tab设置
			if (data.planList[0].templateType == 0) {
				$("#jyPlanId").attr("class", "active");
				$("#bzPlanId").hide();//标准模版tab显示
				$(".planning-jyTbz").hide();//标准模版隐藏
			} else if (data.planList[0].templateType == 1) {
				$("#jyPlanId").attr("class", "");
				$("#bzPlanId").attr("class", "active");
				$("#jyPlanId").hide();
				$(".planning-jyTbz").show();//标准模版显示
			} else {
				$("#jyPlanId").attr("class", "active");
				$("#bzPlanId").hide();
			}
			afterInit();//领导批注
			formDetail.initForm();
		}
	});
};

//处理下发任务按钮方法
function afterInit() {
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#applyUserId").val();//工作计划申请人ID
	var planId = $("#id").val();
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
				weekPlanAnno.initAnno(planId);
				//绑定领导批注保存按钮方法
				$("#leaderIdea").click(function() {
					weekPlanAnno.saveLeaderIdeaForm(planId);
				});
				//绑定批注回复事件
				$('#comment').on('click', 'a[name="reply"]', function() {
					weekPlanAnno.commentReply(this);
				});
				//绑定批注发送按钮事件
				$('#comment').on('click', 'a[name="send"]', function() {
					weekPlanAnno.commentSend(this);
				});
				//绑定批注内容清除
				$('#leaderIdeaForm').on('click', '#clearleaderIdea', function() {
					$('#leaderIdeaContent').val("");
				});
			}else{
				$("#leaderIdeaDiv").show();//领导批注区域整体展示
				$("#leaderIdeaReplayForm").show();//批注回复内容展示
				weekPlanAnno.initAnnoReadOnly(planId);// 初始化领导批注(只读)
				$("#leaderIdeaForm").hide();//领导批注区域新增隐藏
			}
		}
	});
}

/**
 * Span左右按钮操作方法
 */
function optWeekYear(optYear) {
	var showYearSpanValue = "";

	if (optYear == -1) {
		showYearSpanValue = eval($("#showYearSpan").text()) - 1;
	} else if (optYear == 1) {
		showYearSpanValue = eval($("#showYearSpan").text()) + 1;
	} else {
		showYearSpanValue = $("#showYearSpan").text();
	}

	var ajaxData = {
		showYear:showYearSpanValue,
		planType:"0",
		time:new Date()
	};

	var loadUrl = getRootPath()+"/po/plan/planAnnoDataLoad.action?time="+new Date();
	$.ajax({
		type : "post",
		url : loadUrl,
		success : function(data) {
			$("#planFormDiv").html("");
			$("#planFormDiv").html(data);
			planDataLoad(ajaxData,showYearSpanValue);
		}
	});
}

function planDataLoad(ajaxData,showYearSpanValue){
	var url = getRootPath() + "/po/plan/planWorkFlowInit.action";
	$.ajax({
		type : "post",
		url : url,
		data : ajaxData,
		dataType : "json",
		success : function(data) {
			$("#weeksTbody").html("");
			$("#showYearSpan").text(showYearSpanValue);
			var weeksStr = "";
			if (data.spanOn == "true") {
				for (var i = 0; i < data.planList.length; i++) {
					if(i == 0){
						weeksStr += "<tr>";
						weeksStr += "<td id='"+ i +"' style='background-color:#ffc334' onclick=showOtherWeekPlan('"+data.planList[i].id+"',"+i+")><span>"
								 + data.showSpanList[i]
								 + "</span>周</td>";
						weeksStr += "</tr>";
					}else{
						weeksStr += "<tr>";
						weeksStr += "<td id='"+ i +"' onclick=showOtherWeekPlan('"+data.planList[i].id+"',"+i+")><span>"
								 + data.showSpanList[i]
								 + "</span>周</td>";
						weeksStr += "</tr>";
					}
				}
				$("#weeksTbody").append(weeksStr);
				
				var dataDetailsSum = data.detailPlan.sumDetails;
				var dataDetailsPlan = data.detailPlan.planDetails;
				
//				$("#planFormDiv").load(getRootPath()+"/po/plan/planAnnoDataLoad.action",null,null);//绘制表格
				weekPlanAnno.autoTrInit(dataDetailsSum.length,dataDetailsPlan.length);
				//填充工作总结详细数据
				if (dataDetailsSum != null && dataDetailsSum.length >0) {
					var dataSumDetailsLength = dataDetailsSum.length;
					for (var i = 0; i < dataSumDetailsLength; i++) {
						var o = dataDetailsSum[i];
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
				if (dataDetailsPlan != null && dataDetailsPlan.length > 0) {
					var dataPlanDetailsLength = dataDetailsPlan.length;
					for (var i = 0; i < dataPlanDetailsLength; i++) {
						var o = dataDetailsPlan[i];
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
				$("#planTitle").val(data.planList[0].planTitle);
				$("#planStartTime").val(data.planList[0].planStartTime);
				$("#planEndTime").val(data.planList[0].planEndTime);
				$("#applyIdValue").val(data.planList[0].applyIdValue);
				$("#applyDeptidValue").val(data.planList[0].applyDeptidValue);
				$("#applyUserId").val(data.planList[0].applyUserId);
				$("#id").val(data.planList[0].id);
				$("#applyId").val(data.planList[0].applyId);
				$("#applyDeptid").val(data.planList[0].applyDeptid);
				$("#remark").val(data.planList[0].remark);
				$("#manaInno").val(data.planList[0].manaInno);
				$("#costControl").val(data.planList[0].costControl);
				$("#trainingSum").val(data.planList[0].trainingSum);
				$("#problemMeas").val(data.planList[0].problemMeas);
				$("#token").val(data.token);
				$("#otherSpanSumDiv").html(data.planList[0].sumSubmitYear+"年第"+data.planList[0].sumSubmitMW+"周工作总结");
				$("#otherSpanPlanDiv").html(data.planList[0].planSubmitYear+"年第"+data.planList[0].planSubmitMW+"周工作计划");
				
				//简易模版.标准模版tab设置
				if (data.planList[0].templateType == 0) {
					$("#jyPlanId").attr("class", "active");
					$("#bzPlanId").hide();//标准模版tab显示
					$(".planning-jyTbz").hide();//标准模版隐藏
				} else if (data.planList[0].templateType == 1) {
					$("#jyPlanId").attr("class", "");
					$("#bzPlanId").attr("class", "active");
					$("#jyPlanId").hide();
					$(".planning-jyTbz").show();//标准模版显示
				} else {
					$("#jyPlanId").attr("class", "active");
					$("#bzPlanId").hide();
				}
				afterInit();//领导批注
				formDetail.initForm();
			}else{
				weekPlanAnno.planFormLoadNoData();
			}
		}
	});
}

//加载计划表单数据(没有数据)
weekPlanAnno.planFormLoadNoData = function() {
	var url = getRootPath() + "/po/plan/planAnnoNoDataLoad.action?time="+ new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		success : function(data) {
			$("#planFormDiv").html("");
			$("#planFormDiv").html(data);//刷新的div
		}
	});
};

//初始化充满底部背景
function weeks_tree(){
	var content = $("#content").height();
    var headerHeight_1 = $('#header_1').height() || 0;
    var headerHeight_2 = $("#header_2").height() || 0;
    //树
    if($("#weeksSpanDiv")[0]){
    	//定义高度
		$("#LeftHeight").height(content-35-headerHeight_1-headerHeight_2);
        var lh = $("#LeftHeight").height()

      $("#scrollable").scroll(function() {
        if($("#scrollable").scrollTop()>=113){
            $("#LeftHeight").addClass("fixedNav");
            $("#LeftHeight").height(lh + 113)
        }else{
            var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
            $("#LeftHeight").height(lh + a)
            $("#LeftHeight").removeClass("fixedNav");
        } 
      });
    }
    //树end
}

/**
 * 显示另外计划
 */
function showOtherWeekPlan(id,spanId){
	$("#dataLoad").fadeIn();
	$("#weeksSpanTable td").each(function(i,v) {
		v.style.cssText = "background-color:#fff";
	});
	$("#"+spanId).attr("style","background-color:#ffc334");
	$("#"+spanId).attr("name","weeksSpanColor"); 
	var flushFlag = true;//默认刷新
	var flushValue = $("#"+spanId).attr("name");
	if(flushValue!= null && flushValue!= ""){//当点击后,不刷新
		flushFlag = false;
	}
//	if(typeof($("#"+spanId).attr("name")) == "undefined"){
//		$("#planFormDiv").html("");
//		$("#planFormDiv").load(getRootPath()+"/po/plan/planAnnoDataLoad.action",null,null);//绘制表格
	var loadUrl = getRootPath()+"/po/plan/planAnnoDataLoad.action?time="+new Date();
	$.ajax({
		type : "post",
		url : loadUrl,
		success : function(data) {
			$("#planFormDiv").html("");
			$("#planFormDiv").html(data);
			planDetailLoad(id,spanId,flushFlag);
			$("#dataLoad").fadeOut();
		}
	});
//	}
}

function planDetailLoad(id,spanId,flushFlag){
	var url = getRootPath() + "/po/plan/planWorkFlowInit.action?planType=0&time="+ new Date();
	$.ajax({
		type : "POST",
		url : url,
		data : {"id":id,"showYear":$("#showYearSpan").val()},
		dataType : "json",
		success : function(data) {
			$("#showYearSpan").text(data.showYearSpan);//span年显示
			$("#piId").val(data.lastPiId);//流程ID
			var dataDetailsSum = data.detailPlan.sumDetails;
			var dataDetailsPlan = data.detailPlan.planDetails;
			weekPlanAnno.autoTrInit(dataDetailsSum.length,dataDetailsPlan.length,flushFlag);
			
			//填充工作总结详细数据
			if (dataDetailsSum != null && dataDetailsSum.length >0) {
				var dataSumDetailsLength = dataDetailsSum.length;
				for (var i = 0; i < dataSumDetailsLength; i++) {
					var o = dataDetailsSum[i];
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
			if (dataDetailsPlan != null && dataDetailsPlan.length > 0) {
				var dataPlanDetailsLength = dataDetailsPlan.length;
				for (var i = 0; i < dataPlanDetailsLength; i++) {
					var o = dataDetailsPlan[i];
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
			$("#planTitle").val(data.planList[0].planTitle);
			$("#planStartTime").val(data.planList[0].planStartTime);
			$("#planEndTime").val(data.planList[0].planEndTime);
			$("#applyIdValue").val(data.planList[0].applyIdValue);
			$("#applyDeptidValue").val(data.planList[0].applyDeptidValue);
			$("#applyUserId").val(data.planList[0].applyUserId);
			$("#id").val(data.planList[0].id);
			$("#applyId").val(data.planList[0].applyId);
			$("#applyDeptid").val(data.planList[0].applyDeptid);
			$("#remark").val(data.planList[0].remark);
			$("#manaInno").val(data.planList[0].manaInno);
			$("#costControl").val(data.planList[0].costControl);
			$("#trainingSum").val(data.planList[0].trainingSum);
			$("#problemMeas").val(data.planList[0].problemMeas);
			$("#token").val(data.token);			
			$("#otherSpanSumDiv").html(data.planList[0].sumSubmitYear+"年"+data.planList[0].sumSubmitMW+"周工作总结");
			$("#otherSpanPlanDiv").html(data.planList[0].planSubmitYear+"年"+data.planList[0].planSubmitMW+"周工作计划");
			
			//简易模版.标准模版tab设置
			if (data.planList[0].templateType == 0) {
				$("#jyPlanId").attr("class", "active");
				$("#bzPlanId").hide();//标准模版tab显示
				$(".planning-jyTbz").hide();//标准模版隐藏
			} else if (data.planList[0].templateType == 1) {
				$("#jyPlanId").attr("class", "");
				$("#bzPlanId").attr("class", "active");
				$("#jyPlanId").hide();
				$(".planning-jyTbz").show();//标准模版显示
			} else {
				$("#jyPlanId").attr("class", "active");
				$("#bzPlanId").hide();
			}
			afterInit();
			formDetail.initForm();
		}
	});
}

//动态添加行初始化方法
weekPlanAnno.autoTrInit = function(sumLength, planLength) {
	//初始化工作总结动态增加行
	var autoTrSumStr = "<td><span><div autoFlowForm='content' class='down-area input-div autoSumDiv' tabindex='-1' data-toggle='downarea' id='tempId' name='tempName'></div></span></td>"
			+ "<td><span><div autoFlowForm='content' class='down-area input-div' tabindex='-1' data-toggle='downarea' id='tempId' name='tempName'></div></span></td>"
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
};

//领导批注初始化方法
weekPlanAnno.initAnno = function(dataid) {
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
weekPlanAnno.initAnnoReadOnly = function(dataid) {
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
weekPlanAnno.saveLeaderIdeaForm = function(planId) {
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
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								$('#leaderIdeaContent').val('');
								$('#comment').empty();
								weekPlanAnno.initAnno(planId);
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
					weekPlanAnno.subState = false;
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
weekPlanAnno.commentReply = function(e) {
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
weekPlanAnno.commentSend = function(e) {
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
//		if($("#leaderIdeaReplayForm").valid()){
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
								weekPlanAnno.initAnno(businessId);
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
//		}
	}
};

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
			rowHtml.push("</tr>");
		}
		var temp = rowHtml.join('');
		$("#"+tablePreSumId).append(temp);
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
			rowHtml.push("</tr>");
		}
		var temp = rowHtml.join('');
		$("#"+tablePrePlanId).append(temp);
		flushPlanRow(targetTable,targetTable.rows.length);//刷新行数据
	}
}

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
    var id = $("#"+tablePreSumId).find("tr:last").attr("id");
	if(temp==id){
		var rowHtml=[];
		for(var n=0;n<autoAddVal;n++){
			columnValSum += 1;//真实行号id和行号显示
			codeValSum = (eval(id)+1);//显示行号id和行号
			rowHtml.push("<tr id='"+codeValSum+"'>");
			rowHtml.push("<td id='"+ columnValSum +"'>"+codeValSum+"</td>");
			rowHtml.push(autoContentSum);
			rowHtml.push("</tr>");
		}
		var temp = rowHtml.join('');
		$("#"+tablePreSumId).append(temp);
	}
}

/**
 * 计划动态添加行
 */
function creColumn(temp){//创建行
	var targetTable = document.getElementById('prePlan');
    var id = $("#"+tablePrePlanId).find("tr:last").attr("id");
	if(temp==id){
		var rowHtml=[];
		for(var n=0;n<autoAddVal;n++){
			columnValPlan += 1;//真实行号id和行号显示
			codeValPlan = (eval(id)-29);//显示行号id和行号
			rowHtml.push("<tr id='"+columnValPlan+"'>");
			rowHtml.push("<td>"+codeValPlan+"</td>");
			rowHtml.push(autoContentPlan);
			rowHtml.push("</tr>");
		}
		var temp = rowHtml.join('');
		$("#"+tablePrePlanId).append(temp);
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


//加载加入任务弹出层
function loadJoinTaskAddHtml(e){
	//删除校验 add by lihongyu at 2014-11-13 start
	if(!DeleteCascade.syncCheckExist("planAnno",$('#id').val())){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}
	//删除校验 add by lihongyu at 2014-11-13 end
	var evt = e||event;
	if(isFF()){
		var eventTarget = evt.target;
	}else{
		var eventTarget = evt.srcElement?evt.srcElement:evt.target;	
	}
	if($.trim($("#joinTaskFormEdit").html()) == ""){
		$("#joinTaskFormEdit").load(getRootPath()+"/po/plan/joinTaskFormEdit.action",null,function(){workTaskShow(eventTarget)});
	}else{
		workTaskShow(eventTarget);
	}
};

//加入任务弹出层
function workTaskShow(eventTarget){
	$("#init").modal("show");
	weekPlanAnno.clearForm();
	ie8StylePatch();
	$('#zdsz')[0].reset();
    var targetTable = document.getElementById('prePlan');
    $("#taskContent").val(eventTarget.parentNode.parentNode.cells[1].childNodes[0].innerHTML);//任务内容
    $("#standard").val(eventTarget.parentNode.parentNode.cells[2].childNodes[0].innerHTML);//完成标准
    $("#directorUserName").text(eventTarget.parentNode.parentNode.cells[3].childNodes[0].innerHTML);//负责人
    $("#directorName").val(eventTarget.parentNode.parentNode.cells[3].childNodes[0].innerHTML);//负责人
    $("#directorId").val(eventTarget.parentNode.childNodes[4].value);//完成标准
    $("#sponsorId").val($('#loginUserId').val());//发起人赋值为 当前登录人  李洪宇 2014-9-22
}

//清空表单
weekPlanAnno.clearForm = function () {
	$('#zdsz')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("prticipantsName-prticipantsId");//参与人
	$("#businessId").val("0");//附件清空
	clearTable();
	echoAttachListDul(false,'attachList');
};

function toUpdatePlanAnno(){
	//删除校验 add by lihongyu at 2014-11-13 start
	if(!DeleteCascade.syncCheckExist("planAnno",$('#id').val())){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}
	//删除校验 add by lihongyu at 2014-11-13 end
	var url = getRootPath() + "/po/plan/updatePlanAnno.action?time="+ new Date();
	$.ajax({
		type : "POST",
		url : url,
		data:{"planId":$("#id").val()},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				var url = getRootPath() + "/po/plan/planWorkFlowInit.action?planType=0&time="+ new Date();
				$.ajax({
					type : "POST",
					url : url,
					dataType : "json",
					success : function(data) {
						$("#showYearSpan").text(data.showYearSpan);//span年显示
						$("#piId").val(data.lastPiId);//流程ID
						var weeksStr = "";
						if(data.spanOn =="true"){
							for (var i=0;i< data.planList.length;i++) {
								if(i == 0){
									weeksStr += "<tr>";
									weeksStr += "<td id='"+ i +"' style='background-color:#ffc334' onclick=showOtherWeekPlan('"+data.planList[i].id+"',"+i+")><span>"
											 + data.showSpanList[i]
											 + "</span>周</td>";
									weeksStr += "</tr>";
								}else{
									weeksStr += "<tr>";
									weeksStr += "<td id='"+ i +"' onclick=showOtherWeekPlan('"+data.planList[i].id+"',"+i+")><span>"
											 + data.showSpanList[i]
											 + "</span>周</td>";
									weeksStr += "</tr>";
								}
							}
						}
						$("#weeksTbody").html(weeksStr);
						
						if(weeksStr!=""){
							$("#preSum").find("tbody").remove();
							$("#prePlan").find("tbody").remove();
							
							var dataDetailsSum = data.detailPlan.sumDetails;
							var dataDetailsPlan = data.detailPlan.planDetails;
							weekPlanAnno.autoTrInit(dataDetailsSum.length,dataDetailsPlan.length);
							//填充工作总结详细数据
							if (dataDetailsSum != null && dataDetailsSum.length >0) {
								var dataSumDetailsLength = dataDetailsSum.length;
								for (var i = 0; i < dataSumDetailsLength; i++) {
									var o = dataDetailsSum[i];
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
							if (dataDetailsPlan != null && dataDetailsPlan.length > 0) {
								var dataPlanDetailsLength = dataDetailsPlan.length;
								for (var i = 0; i < dataPlanDetailsLength; i++) {
									var o = dataDetailsPlan[i];
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
							$("#planTitleOther").html(data.planList[0].planTitle);
							$("#planStartTimeOther").html(data.planList[0].planStartTime);
							$("#planEndTimeOther").html(data.planList[0].planEndTime);
							$("#applyIdValueOther").html(data.planList[0].applyIdValue);
							$("#applyDeptidValueValueOther").html(data.planList[0].applyDeptidValue);
							$("#applyUserId").val(data.planList[0].applyUserId);
							$("#id").val(data.planList[0].id);
							$("#applyId").val(data.planList[0].applyId);
							$("#applyDeptid").val(data.planList[0].applyDeptid);
							$("#remark").val(data.planList[0].remark);
							$("#manaInno").val(data.planList[0].manaInno);
							$("#costControl").val(data.planList[0].costControl);
							$("#trainingSum").val(data.planList[0].trainingSum);
							$("#problemMeas").val(data.planList[0].problemMeas);
							$("#token").val(data.token);
							$("#otherSpanSumDiv").html(data.planList[0].sumSubmitYear+"年第"+data.planList[0].sumSubmitMW+"周工作总结");
							$("#otherSpanPlanDiv").html(data.planList[0].planSubmitYear+"年第"+data.planList[0].planSubmitMW+"周工作计划");
							$("#comment").empty();
							
							//简易模版.标准模版tab设置
							if (data.planList[0].templateType == 0) {
								$("#jyPlanId").attr("class", "active");
								$("#bzPlanId").hide();//标准模版tab显示
								$(".planning-jyTbz").hide();//标准模版隐藏
							} else if (data.planList[0].templateType == 1) {
								$("#jyPlanId").attr("class", "");
								$("#bzPlanId").attr("class", "active");
								$("#jyPlanId").hide();
								$(".planning-jyTbz").show();//标准模版显示
							} else {
								$("#jyPlanId").attr("class", "active");
								$("#bzPlanId").hide();
							}
							
							var targetUser = $("#loginUserId").val();//当前登录人ID
							var sourceUser = $("#applyUserId").val();//工作计划申请人ID
							var planId = $("#id").val();
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
										monthPlanAnno.initAnno(planId);
									}else{
										$("#leaderIdeaDiv").show();//领导批注区域整体展示
										$("#leaderIdeaReplayForm").show();//批注回复内容展示
										monthPlanAnno.initAnnoReadOnly(planId);// 初始化领导批注(只读)
										$("#leaderIdeaForm").hide();//领导批注区域新增隐藏
									}
								}
							});
							formDetail.initForm();
						}else{
							weekPlanAnno.planFormLoadNoData();
						}
					}
				});
			}
		}
	});
}

/**
 * 初始化方法
 */
jQuery(function($) {
	weekPlanAnno.initWeekPlanAnno();
	$("#leftYear").click(function(){optWeekYear(-1);});
	$("#rightYear").click(function(){optWeekYear(1);});
	weeks_tree();
});