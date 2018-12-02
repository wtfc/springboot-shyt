/**
 * 计划审批js
 */
var planRange = {};
planRange.subState = false;//重复提交标识,false时,允许提交表单
var para_week="";//周计划提醒参数
var para_month="";//月计划提醒参数
var para_year="";//年计划提醒参数

/**
 * 添加修改公用方法
 */
planRange.saveForm = function () {
	//校验是否重复提交
	if (planRange.subState)return;
	planRange.subState = true;
	//校验表单信息
//	if ($("#planRangeForm").valid()) {
	var url = null;
	url = getRootPath()+"/po/plan/setPlanRange.action?time=" + new Date(); 
	if ($("#updateHid").val() == 1) {
		url = getRootPath()+"/po/plan/updatePlanRange.action?time=" + new Date();
	}
	//将表单序列化成json格式
	var formData = $("#planRangeForm").serializeArray();
	formData.push({"name": "paraWeek", "value": para_week});
	formData.push({"name": "paraMonth", "value": para_month});
	formData.push({"name": "paraYear", "value": para_year});
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		success : function(data) {
			planRange.subState = false;//更新重复提交状态标识
			$("#updateHid").attr("value","1");
//			getToken();//更新token
			msgBox.tip({
				type:"success",
				content: $.i18n.prop("JC_SYS_001")
			});
		}
	});
//	}
};

/**
 * 周计划编写提醒设定方法
 */
planRange.showRemind_week = function(){
	$(document).showRemind_({tableName:'weekRangPlan',remind:para_week,callback:function(r) {para_week = r;}});
};

/**
 * 月计划编写提醒设定方法
 */
planRange.showRemind_month = function(){
	$(document).showRemind_({tableName:'monthRangPlan',remind:para_month,callback:function(r) {para_month = r;}});
};

/**
 * 年计划编写提醒设定方法
 */
planRange.showRemind_year = function(){
	$(document).showRemind_({tableName:'yearRangPlan',remind:para_year,callback:function(r) {para_year = r;}});
};

/**
 * 周工作计划范围初始化方法
 */
planRange.initWeekRangeUser = function(){
	var url = getRootPath()+"/po/plan/getWeekPlanRange.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			if(data.result!=""){
				//回显范围人员
				selectControl.init("showWeekTree","weekRangeId-weekRangeId", true, true,null,eval(data.result));//多选人员
				$("#updateHid").attr("value","1");
			}else{
				//周工作计划人员选择树初始化加载
				selectControl.init("showWeekTree","weekRangeId-weekRangeId", true, true);//多选人员
			}
		}
	});
};

/**
 * 月工作计划范围初始化方法
 */
planRange.initMonthRangeUser = function(){
	var url = getRootPath()+"/po/plan/getMonthPlanRange.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			if(data.result!=""){
				//回显范围人员
				selectControl.init("showMonthTree","monthRangeId-monthRangeId", true, true,null,eval(data.result));//多选人员
				$("#updateHid").attr("value","1");
			}else{
				//周工作计划人员选择树初始化加载
				selectControl.init("showMonthTree","monthRangeId-monthRangeId", true, true);//多选人员
			}
		}
	});
};

/**
 * 年工作计划范围初始化方法
 */
planRange.initYearRangeUser = function(){
	var url = getRootPath()+"/po/plan/getYearPlanRange.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			if(data.result!=""){
				//回显范围人员
				selectControl.init("showYearTree","yearRangeId-yearRangeId", true, true,null,eval(data.result));//多选人员
				$("#updateHid").attr("value","1");
			}else{
				//周工作计划人员选择树初始化加载
				selectControl.init("showYearTree","yearRangeId-yearRangeId", true, true);//多选人员
			}
		}
	});
};


/**
 * 周工作计划提醒初始化方法
 */
planRange.initWeekRemind = function(){
	var url = getRootPath()+"/po/plan/getPlanRangeRemind.action?dataId=1&time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			para_week = data.result;
		}
	});
};

/**
 * 月工作计划提醒初始化方法
 */
planRange.initMonthRemind = function(){
	var url = getRootPath()+"/po/plan/getPlanRangeRemind.action?dataId=2&time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			para_month = data.result;
		}
	});
};

/**
 * 年工作计划提醒初始化方法
 */
planRange.initYearRemind = function(){
	var url = getRootPath()+"/po/plan/getPlanRangeRemind.action?dataId=3&time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			para_year = data.result;
		}
	});
};

/**
 * 计划编写提醒验证方法
 */
function remindValidate(){
	if(para_week == ""){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_PO_020")
		});
		return false;
	}
	if(para_month == ""){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_PO_021")
		});
		return false;
	}
	if(para_year == ""){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_PO_022")
		});
		return false;
	}
	return true;
};

/**
 * 初始化方法
 */
jQuery(function($){
	//保存按钮事件绑定
	$("#saveRangeBtn").click(function(){planRange.saveForm();});
	//周计划提醒按钮事件绑定
	$("#weekRemind").click(function(){planRange.showRemind_week();});
	//月计划提醒按钮事件绑定
	$("#monthRemind").click(function(){planRange.showRemind_month();});
	//年计划提醒按钮事件绑定
	$("#yearRemind").click(function(){planRange.showRemind_year();});
	//周工作计划范围初始化方法
	planRange.initWeekRangeUser();
	//月工作计划范围初始化方法
	planRange.initMonthRangeUser();
	//年工作计划范围初始化方法
	planRange.initYearRangeUser();
	//周工作计划提醒初始化方法
	planRange.initWeekRemind();
	//月工作计划提醒初始化方法
	planRange.initMonthRemind();
	//年工作计划提醒初始化方法
	planRange.initYearRemind();
});