//动态增加行的初始化form，包括：会议决议、会议遗留问题

/**
 * 会议决议和会议遗留问题
 */
var plan = {};
plan.subState = false;//重复提交标识,false时,允许提交表单

//计划对象
plan.oTable = null;
//任务对象
plan.oTableTask = null;
//定义计划分页变量
plan.planPageRows = null;
//定义任务分页变量
plan.taskPageRows = null;

/**
 * 动态添加行初始化方法
 */
plan.autoTrInit = function(sumLength){
	$.validator.addClassRules({
		downArea: {
			maxlength: 500
		  }
		});
	//初始化 会议决议    动态添加行
	var autoTrSumStr="<td><span autoFlowForm='textarea' class='input-style'><textarea class='downArea' @" +
	  "<td operate='true'>" +
	  "<a autoFlowForm='button' class='a-icon i-remove' href='#'><i id='delSum' class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' " +
	  "onclick='deleteSumRow(event);' data-container='body' data-original-title='删除'></i></a></td> ";
	               
	
	indexFun(true,"preSum",sumLength,1,autoTrSumStr);
	$("[data-toggle=tooltip]").tooltip();//显示删除标签
	
};



/**
 * 添加【会议决议】和【会议遗留问题】,经过这个JS函数，会配合后台controller变成List
 */
plan.addFormParameters = function (formData){
	
	//会议决议
	var decision = new Array();
	//会议决议数组
	var decisionArr = new Array();
	
	//会议遗留问题    【但是，会议决议和会议遗留问题都是存在一个字段 decision】
	var remain = new Array();
	//会议遗留问题数组
	var remainArr = new Array();
	
    //填充会议决议
	$.each($("[id^=preSum_0_]"), function(i, o) {
		decision.push(o.value);
	});
	//填充会议决议数组
	$.each(decision, function(i, o) {
		decisionArr.push({"decision":decision[i],"type":0});
	});
	
	
	//填充会议遗留问题
	$.each($("[id^=prePlan_0]"), function(i, o) {
		remain.push(o.value);
	});
	//填充会议遗留问题数组
	$.each(remain, function(i, o) {
		decisionArr.push({"decision":remain[i],"type":1});
	});
	
	formData.push({"name": "msd", "value": decisionArr});
	
	
};



/**
 * 序号生成方法
 */
plan.orderNumber = function () {
	var length = $("#preSum").find("tr").length;
	for(var i=0;i<length-1;i++){
		$("#preSum_"+(i+1)).html((i+1));
	}
};

/**
 * 加载【会议决议】和【会议遗留问题】
 */
function loadPlanDetial(ajaxData) {
	var url = getRootPath() + "/po/plan/get.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'GET',
		data : ajaxData,
		async : false,
		success : function(data) {
			if (data) {
				// 清除验证信息
				hideErrorMessage();
				// 将后台绑定的数据回显到相应表单中
				$("#planForm").fill(data);
				plan.autoTrInit(data.sumDetails.length,data.planDetails.length);//创建动态添加行
				// 填充工作总结详细数据
				if (data.sumDetails != null && data.sumDetails.length > 0) {
					for (var i = 0; i < data.sumDetails.length; i++) {
						var o = data.sumDetails[i];
						$("#preSum_0_"+(i+1)).html(o.content);
						$("#preSum_1_"+(i+1)).html(o.standard);
						$("#preSum_2_"+(i+1)).val(o.directorIdValue);
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
				// 填充工作计划详细数据
				if (data.planDetails != null && data.planDetails.length > 0) {
					for (var i = 0; i < data.planDetails.length; i++) {
						var o = data.planDetails[i];
						$("#prePlan_0_"+(i+31)).html(o.content);
						$("#prePlan_1_"+(i+31)).html(o.standard);
						$("#prePlan_2_"+(i+31)).val(o.directorIdValue);
						$("#prePlan_3_"+(i+31)).val(o.startTime);
						$("#prePlan_4_"+(i+31)).val(o.endTime);
						$("#prePlan_5_"+(i+31)).val(o.compRate);
						$("#prePlan_9_"+(i+31)).val(o.directorId);
					}
				}
				// 周.月.年计划标签设置
				$("#week").hide();
				$("#month").hide();
				$("#year").hide();
				// 周计划调用初始化函数进行赋值
				if (data.planType == 0) {
					$("#appYearLabel").hide();
					$("#appMonthLabel").hide();
					$("#appWeekLabel").show();
					$("#dateNowYear").html("年第");
					$("#dateNow").html("周工作总结");
					$("#dateNextYear").html("年第");
					$("#dateNext").html("周工作计划");
				} else if (data.planType == 1) {// 月计划回显处理
					$("#appYearLabel").hide();
					$("#appMonthLabel").show();
					$("#appWeekLabel").hide();
					$("#dateNowYear").html("年");
					$("#dateNow").html("月份工作总结");
					$("#dateNextYear").html("年");
					$("#dateNext").html("月份工作计划");
				} else {// 年计划回显处理
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

				// 简易模版.标准模版tab设置
				if (data.templateType == 0) {
					$("#jyPlanId").attr("class", "active");
					$("#bzPlanId").hide();// 标准模版tab隐藏
					$(".planning-jyTbz").hide();// 标准模版隐藏
				} else if (data.templateType == 1) {
					$("#bzPlanId").attr("class", "active");
					$("#jyPlanId").hide();
					$(".planning-jyTbz").show();// 标准模版显示
				} else {
					$("#jyPlanId").attr("class", "active");
					$("#bzPlanId").hide();
				}
				$("#planForm #planType").val(data.planType);
				$("#currentUserId").val(data.applyIdValue);//当前登录人Id
				$("#currentSubmitYear").val(data.sumSubmitYear);//审批记录工作计划年
				$("#currentSubmitMW").val(data.sumSubmitMW);//审批记录工作计划周/月
			}
		}
	});
}
