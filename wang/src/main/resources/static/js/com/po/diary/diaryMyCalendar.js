var diaryMyCalendar={};
diaryMyCalendar.subState = false;//重复提交标识

diaryMyCalendar.pageRows = null;
/**-----------------------日志汇总begin-------------------------------*/
//分页对象
diaryMyCalendar.collectTable = null;
//分页查询用户
diaryMyCalendar.collectSearch = function () {
	$('#startDateTemp').val($("#worklogDateBegin").val());//将查询条件赋值给临时变量 add by lihongyu at 2014-11-10
	$('#endDateTemp').val($("#worklogDateEnd").val());//将查询条件赋值给临时变量  add by lihongyu at 2014-11-10
	//显示列信息
	diaryMyCalendar.collectTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "worklogDate"},
		{mData: function(source) {
			var todayLogs = source.todayLogs;
			var contentStrs = '';
			if(todayLogs && todayLogs.length > 0) {
				for(var j=0;j<todayLogs.length;j++){
					var todayLog = todayLogs[j];
					contentStrs += ((j+1)+"."+todayLog.content+"</br>");
				}
			}
			return contentStrs;
		}},
		{mData: "sentimentRemark"},
		{mData: "annoCount",mRender : function(mData, type, full){
			var reVal="";
			if(mData >0){
				reVal="<a href=\"#\" onclick=\"diaryMyCalendar.showAnno("+full.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
			}
			return reVal;
		}}
	 ];
	
	//组装后台参数
	diaryMyCalendar.collectTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(diaryMyCalendar.collectTable, aoData);
		//组装查询条件
		var worklogDateBegin = $("#worklogDateBegin").val();
		var worklogDateEnd = $("#worklogDateEnd").val();
		
		aoData.push({ "name": "worklogDateBegin", "value": worklogDateBegin});
		aoData.push({ "name": "worklogDateEnd", "value": worklogDateEnd});
	};
	
	//table对象为null时初始化
	if (diaryMyCalendar.collectTable == null) {
		diaryMyCalendar.collectTable = $('#collectTable').dataTable( {
			iDisplayLength: 5,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/po/worklog/getMyworklogCollect.action?time=" + new Date(),
			//bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: diaryMyCalendar.collectTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				diaryMyCalendar.collectTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,3]}]
		} );
		setColumnVis(diaryMyCalendar.collectTable, 6);
	} else {
		//table不为null时重绘表格
		diaryMyCalendar.collectTable.fnDraw();
		//pageChange(user.oTable);
	}
};
/**
 * 日志汇总DIV
 */
diaryMyCalendar.showWorklogSummaryDiv=function(id){
	var url=getRootPath()+"/po/worklog/showMyworklogCollect.action?id="+id;
	$("#worklogSummary").load(
			url,null,function(){diaryMyCalendar.showWorklogSummaryDivCallBack(id)});
};
/**
 * 日志汇总DIV回调
 */
diaryMyCalendar.showWorklogSummaryDivCallBack=function(id){
	$('#collectForm').find("input[type=text]").val("");
//	$('#collectForm')[0].reset();
	$('#collectTable tbody').empty();
	$('#worklogDateBegin').val(schedule.getSysDate());
	$('#worklogDateEnd').val(schedule.getSysDate());
	diaryMyCalendar.collectSearch();
	$('#worklog_summary').modal('show');
};
/**-----------------------日志汇总end-------------------------------*/
/**查看领导批注*/
diaryMyCalendar.showAnno = function(dataid){
	$.ajax({
		type:"GET",
		url : getRootPath()+"/po/worklog/queryAnno.action",
		data:{"businessId":dataid},
			dataType : "json",
			success : function(data){
				if(data){
					var liStr = '';
					if (data.length == 0) {
						liStr += "<li class=\"clearfix m-b input-group\">" + 
								 "<p class=\"dialog-text input-group\" id=\"rootAnno\" name=\"rootAnno\">没有批注内容</p></li>";
					}
					for (var i = 0; i < data.length; i++) {
						 var annoParent = data[i];
						 liStr += "<li class=\"clearfix m-b input-group\">" + 
								  "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\"" + 
								  annoParent.id + "\">" + 
								  "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
								  annoParent.annoUserIdValue + "：</strong>" + annoParent.content + 
								  "<span>" + annoParent.annoDate + "</span></p>";
						 // 批注回复
						 var replyList = annoParent.annoReplyList;
						 if(replyList){
							for(var j = 0; j < replyList.length; j++){
								var annoReply = replyList[j];
								liStr += "<div class=\"dis-row\">" + 
										 "<p class=\"dialog-text input-group\" data=\"" + 
										 annoReply.id + "\">" + "<input type=\"hidden\" name=\"\">" + 
										 "<i class=\"fa fa-comment input-group-btn  p-l\"></i><strong>" + 
										 annoReply.annoUserIdValue + "回复" + annoReply.parentUserName + 
										 "：</strong>" + annoReply.content + 
										 "<span>" + annoReply.annoDate + 
										 "</span></p>";
							}
						}
						liStr += "</li>";
					}
					$('#annoComment').append(liStr);
				}
			}
		});
	$('#worklog-anno').modal('show');
};
/**关闭领导批注*/
diaryMyCalendar.closeAnno = function(){
	$('#annoComment').empty();
};
//清空表单
diaryMyCalendar.clearForm = function () {
	$('#diaryForm').find("input[type=text]").val("");
	$('#diaryForm').find("textarea").val("");
//	$('#diaryForm')[0].reset();
	$('#shareScopeId').val("");
	selectControl.clearValue("shareScopeChoose-shareScopeChoose");
	document.getElementById("periodStartdate").disabled=false;
	document.getElementById("periodEnddate").disabled=false;
	document.getElementById("starttime").disabled=false;
	document.getElementById("endtime").disabled=false;
	$('#comment').empty();
	hideErrorMessage();
};
//取消新增日程
//diaryMyCalendar.cancel=function(){
//	diaryMyCalendar.clearForm();
//	$('#The-new-agenda').modal('hide');
//};
/**显示日程委托层*/
diaryMyCalendar.showAgentSetDiv=function(){
	var url=getRootPath()+"/po/diaryDelegation/showAgentSetDiv.action";
	$("#diaryDelegation").load(
			url,null,function(){diaryMyCalendar.toAgentSetView()});
};
/**显示日程委托层回调*/
diaryMyCalendar.toAgentSetView=function(){
	var diaryDelegationJson=$("#diaryDelegationJson").val();
	var data=eval("("+diaryDelegationJson+")");
	$("#delegationId").val(data.id);
	$("#mandataryId").val(data.mandataryId);
	$("#mandataryIdValue").val(data.mandataryIdValue);
	$("#ddModifyDate").val(data.modifyDate);
	
	var mid=$("#mandataryId").val();
	var mtext=$("#mandataryIdValue").val();
	var data=null;
	if(mid!=null&&mid!=""&&mtext!=null&&mtext!=""){
		data={id:mid,text:mtext};
	}
	if(mid==null||mid==""){
		$("#diaryAgentCancel").hide();
	}else{
		$("#diaryAgentCancel").show();
	}
	$("#shareScope").empty();
	selectControl.init("agentScope","agentScopeChoose-agentScopeChoose",false,true,null,data);//人员选择树 参数divid 设置的id-name 多选 人员
	$('#Schedule-client').modal('show');
	//日程委托确定按键点击绑定事件 
	$("#diaryAgentConfirm").click(function(){
		diaryMyCalendar.confirmAgentSet();
	});
	//日程委托取消委托按键点击绑定事件
  	$("#diaryAgentCancel").click(function(){
  		diaryMyCalendar.confirmAgentCancel($("#delegationId").val());
  	});
  	$("#closeAgent").click(function(){
  		diaryMyCalendar.closeAgent();
  	});////关闭日程委托层
  	ie8StylePatch();
//	getToken();
};
//查询当前用户是否已有委托人，有则赋值
diaryMyCalendar.getDelegation=function(){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/diaryDelegation/getDelegationByMandatorId.action?time=" + new Date(),
		data : {},
		dataType : "json",
		success : function(data) {
			if (data) {
				if(data.id!=null&&data.id!=""){
					$("#delegationId").val(data.id);
					$("#mandataryId").val(data.mandataryId); /*被委托人ID*/
					$("#mandataryIdValue").val(data.mandataryIdValue);
					$("#ddModifyDate").val(data.modifyDate);
				}
			}
		}
	});
};

/**校验短信提醒*/
diaryMyCalendar.validRemind = function(userIds){
	var reminder = $('input[name="remindWay"]:checked').val();
	if(reminder=='1'){//是短信
		jQuery.ajax({
			url : getRootPath()+"/po/diary/validRemind.action?time=" + new Date(),
			type : 'get',
			async: false,
			dataType : "json",
			data : {'agentId':userIds},
			success : function(data) {
				if(data.success=="success"){
					diaryMyCalendar.callbackConfirmAgentSet();
				}else{
					if(data.success){
						msgBox.confirm({
							content: data.successMessage,
							success: function(){
								diaryMyCalendar.callbackConfirmAgentSet();
							},
							cancel:function(){
							}
						});
						
					}else{
						msgBox.info({
							content: data.errorMessage
						});
					} 
				}
			},
			error : function() {
				msgBox.tip({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_015"),
					callback:function(){
					}
				});
			}
		});
	}else{//是邮件
		diaryMyCalendar.callbackConfirmAgentSet();
	}
};
//提交日程委托
diaryMyCalendar.confirmAgentSet=function(){
	//校验短信提醒
	diaryMyCalendar.validRemind($('#agentScopeChoose-agentScopeChoose').val());
};
//提交日程委托回调
diaryMyCalendar.callbackConfirmAgentSet=function(){
	$("#mandataryId").val($('#agentScopeChoose-agentScopeChoose').val());
	var delegationId=$("#delegationId").val();
	var mandataryId=$('#mandataryId').val();
	var remindWay=document.getElementsByName("remindWay");
	var remindWayValue="";
	var ddModifyDate=$("#ddModifyDate").val();
	for(var i=0;i<remindWay.length;i++){
		if(remindWay[i].checked == true){
			remindWayValue=remindWay[i].value;
		}
	}
	if (diaryMyCalendar.subState)return;
	if(mandataryId!=null&&mandataryId!=""){
		diaryMyCalendar.subState = true;
		var url = getRootPath()+"/po/diaryDelegation/save.action?time=" + new Date();
		if (delegationId != 0) {
			url = getRootPath()+"/po/diaryDelegation/update.action?time=" + new Date();
		}
		var formData = $("#diaryDelegationForm").serializeArray();
		formData.push({name:"token", value:$("#agentToken").val()});
		formData.push({name:"mandataryId", value:mandataryId});
		formData.push({name:"id", value:delegationId});
//		formData.push({name:"id", value:1});
		formData.push({name:"remindWay", value:remindWayValue});
		formData.push({name:"ddModifyDate", value:ddModifyDate});
		jQuery.ajax({
			url : url,
			type : 'POST',
			async:false,
			data : formData,
			success : function(data) {
				diaryMyCalendar.subState = false;
//				getToken();
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: $.i18n.prop("JC_SYS_111"),
						callback:function(){
							diaryMyCalendar.getDelegation();
							$('#Schedule-client').modal('hide');
						}
					});
				} else {
					if(data.labelErrorMessage){
						showErrors("diaryForm", data.labelErrorMessage);//todo
					} else if (data.errorMessage) {
						if (data.errorMessage == "tokenError") {
							alertx(tokenMessage);
						} else if(data.errorMessage == "concurrentError"){
							alertx(concurrentMessage);
						} else {
							alertx(data.errorMessage);
						}
					} else {
						msgBox.tip({
							type:"fail",
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				//diaryMyCalendar.subState = false;
				getToken();
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	}else{
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_OA_PO_016")
		});
		diaryMyCalendar.getDelegation();
	}
};
//取消委托
diaryMyCalendar.confirmAgentCancel=function(id){
	if (id == null || id == "") {
		msgBox.info({
			type:"fail",
			content: $.i18n.prop("JC_OA_PO_017")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_OA_PO_018"),
		success: function(){
			diaryMyCalendar.agentCancelCallBack(id);
		}
	});
};
//取消委托回调方法
diaryMyCalendar.agentCancelCallBack=function(id){
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/diaryDelegation/deleteByIds.action?time=" + new Date(),
		data : {"ids": id},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_SYS_110"),
					callback:function(){
						$("#delegationId").val(0);
						$("#mandataryId").val("");
						$("#mandataryIdValue").val("");
						$('#Schedule-client').modal('hide');
					}
				});
			}
		}
	});
};
//进入日程列表
diaryMyCalendar.toListView=function(){
	loadrightmenu('/po/diary/diaryMyList.action');
};
//关闭日程委托层
diaryMyCalendar.closeAgent=function (){
	$('#Schedule-client').modal('hide');
};
//关闭详细页
diaryMyCalendar.closeDetail=function (){
	$('#Schedule-detail').modal('hide');
};
//关闭编辑方式
//diaryMyCalendar.closeModifyType=function (){
//	$('#period-diary-modify').modal('hide');
//};
//关闭日志汇总
diaryMyCalendar.closeWorklogSummary=function (){
	$('#worklog_summary').modal('hide');
};
//写日志跳转方法
diaryMyCalendar.writeWorklog = function (){
	loadrightmenu("/po/worklog/myWorklogCalendarSkip.action?time=" + new Date(),"unknown");
};
//写周计划跳转方法
diaryMyCalendar.writeWeekPlan = function (){
	loadrightmenu("/workFlow/processDefinition/toStartProcess.action?flowIden=planKey&condition=diaryWeekJump&time=" + new Date(),"周计划编制");
};
//写月计划跳转方法
diaryMyCalendar.writeMonthPlan = function (){
	loadrightmenu("/workFlow/processDefinition/toStartProcess.action?flowIden=planKey&condition=diaryMonthJump&time=" + new Date(),"月计划编制");
};
//看下属日志跳转方法
diaryMyCalendar.underlingWorklog = function (){
	loadrightmenu("/po/worklog/worklogManageSkip.action?time=" + new Date(),"");
};
//看下属周计划方法
diaryMyCalendar.underlingWeekPlan = function (){
	loadrightmenu("/po/plan/subPlanQuery.action?jumpCondition=diaryWatchWeekJump&&time=" + new Date(),"下属周计划");
};
//看下属月计划方法
diaryMyCalendar.underlingMonthPlan = function (){
	loadrightmenu("/po/plan/subPlanQuery.action?jumpCondition=diaryWatchMonthJump&&time=" + new Date(),"下属月计划");
};

//工作日志批注数量统计方法
diaryMyCalendar.setWorklogAnnoCount = function (type){
	var url = getRootPath()+"/po/worklog/workLogAnnoCountForDiary.action?time="+ new Date();
	$.ajax({
		type : "post",
		url : url,
		dataType : "json",
		success : function(data) {
			$("#worklogAnnoBtn").text(data.annoCount);
		}
	});	
};
//计划批注数量统计方法
diaryMyCalendar.setPlanAnnoCount = function (type){
	var url = getRootPath()+"/po/plan/planAnnoCountForDiary.action?time="+ new Date();
	$.ajax({
		type : "post",
		url : url,
		data : {"planType": type},
		dataType : "json",
		success : function(data) {
			if(type == 0){
				$("#weekPlanAnnoBtn").text(data.annoCount);
			}else{
				$("#monthPlanAnnoBtn").text(data.annoCount);
			}

		}
	});
};

//查看日志领导批注
diaryMyCalendar.worklogAnno = function (){
	var worklogAnnoText = $("#worklogAnnoBtn").text();
	if(worklogAnnoText == 0){
		msgBox.info({
			content:$.i18n.prop("JC_OA_PO_019")
		});
	}else{
		loadrightmenu("/po/worklog/worklogLeaderAnno.action?time=" + new Date(),"工作日志");
	}
};
//查看周计划领导批注
diaryMyCalendar.weekPlanAnno = function (){
	var weekPlanAnnoText = $("#weekPlanAnnoBtn").text();
	if(weekPlanAnnoText == 0){
		msgBox.info({
			content:$.i18n.prop("JC_OA_PO_019")
		});
	}else{
		loadrightmenu("/po/plan/weekPlanAnnoSkip.action?time=" + new Date(),"unknown");
	}
};
//查看月计划领导批注
diaryMyCalendar.monthPlanAnno = function (){
	var monthPlanAnnoText = $("#monthPlanAnnoBtn").text();
	if(monthPlanAnnoText == 0){
		msgBox.info({
			content:$.i18n.prop("JC_OA_PO_019")
		});
	}else{
		loadrightmenu("/po/plan/monthPlanAnnoSkip.action?time=" + new Date(),"unknown");
	}
};
//日志提交情况
diaryMyCalendar.worklogCondition = function (){
	loadrightmenu("/po/plan/dayPlanConditionSkip.action?time=" + new Date(),"unknown");
};
//周计划提交情况
diaryMyCalendar.weekPlanCondition = function (){
	loadrightmenu("/po/plan/weekPlanConditionSkip.action?time=" + new Date(),"unknown");
};
//月计划提交情况
diaryMyCalendar.monthPlanCondition = function (){
	loadrightmenu("/po/plan/monthPlanConditionSkip.action?time=" + new Date(),"unknown");
}; 


//周计划汇总
diaryMyCalendar.weekPlanCollect = function (){
	if(loadSpecifyMenu(96)){
		loadrightmenu("/po/plan/manageCollectPlanListForDiarySkip.action?jumpCondition=weekCollectJump&&time=" + new Date(),"周计划汇总");
	}
};
//月计划汇总
diaryMyCalendar.monthPlanCollect = function (){
	if(loadSpecifyMenu(96)){
		loadrightmenu("/po/plan/manageCollectPlanListForDiarySkip.action?jumpCondition=monthCollectJump&&time=" + new Date(),"月计划汇总");
	}
};

//新增修改点击x
//diaryMyCalendar.closeX=function(){
//	diaryMyCalendar.cancel();
//	schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
//};
//初始化
jQuery(function($){
	//计算分页显示条数
	diaryMyCalendar.pageRows = TabNub>0 ? TabNub : 10;
	
	var sysdate=schedule.getSysDate();
//	diaryMyCalendar.getDelegation();
	diaryMyCalendar.setWorklogAnnoCount();//初始化设置工作日志批注数量
	diaryMyCalendar.setPlanAnnoCount(0);//初始化设置周计划批注数量
	diaryMyCalendar.setPlanAnnoCount(1);//初始化设置月计划批注数量
	schedule.initFullCalendar(0);// fullcalendar 初始化
	$('#dayview').on('click', function() {//agendaDay 一页显示一天, 显示详细的24小时安排
		$('.calendar').fullCalendar('changeView', 'agendaDay');
		schedule.searchFullCalendar();
    });
    $('#weekview').on('click', function() {//agendaWeek 一页显示一周, 显示详细的24小时安排(也就是议事日程)
    	$('.calendar').fullCalendar('changeView', 'agendaWeek');
    	schedule.searchFullCalendar();
    });
    $('#monthview').on('click', function() {//month 一页显示一月, 日历样式
    	$('.calendar').fullCalendar('changeView', 'month');
    	schedule.searchFullCalendar();
    });
    $('#today').on('click', function() {//显示今天
    	$('#searchtime').val("");
        $('.calendar').fullCalendar('today')
	});
//    $('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio 
    $("#search").click(function(){schedule.searchFullCalendar();});//日历页快速查询
//	$("#savaOrModify").click(function(){//保存继续按键点击事件绑定
//		schedule.savaOrModify(false);
//	});
//	$("#savaAndClose").click(function(){//保存退出按键点击事件绑定
//		schedule.savaOrModify(true);
//	});
//	$("#cancel").click(function(){//取消按键点击事件绑定 
//		diaryMyCalendar.cancel();
//		schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
//	});
//	$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
//		schedule.setPeriodDate();
//		if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
//			schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
//			schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
//		}
//	});
//	$("#toModify").click(function(){schedule.toModifyType()});//编辑按键点击事件绑定
//	$("#modifyDiv").click(function(){schedule.toModify()});//编辑方式页面确定按键点击事件绑定
//	$("#periodConfirm").click(function(){//高级设置页面确定按键点击事件绑定
//		schedule.periodConfirm();
//		schedule.periodContentTemp();//临时存储高级设置层中的内容
//	});
//	$("#periodCancel").click(function(){//高级设置页面取消按键点击事件绑定
//		if($("#newOrModify").val()=="0"){//新建页
//			if($("#newOk").val()!=1){
//				schedule.clearPeriodContent2();//清空高级设置
//				schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
//			}
//		}
//		if($("#newOrModify").val()=="1"){//修改页
//			if($("#modifyExistValue").val()!=1){
//				schedule.clearPeriodContent2();//清空高级设置
//				schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
//			}
//		}
//		schedule.periodCancel();
//	});
	$("#listView").click(function(){diaryMyCalendar.toListView();});//列表形式展示按键点击事件绑定
//  	$("#deleteDiary").click("click", function(){
//  		schedule.deleteDiary($("#delId").val());
//  	});//删除按键点击事件绑定
//  	$("#showShareScopeTree").click(function(){//共享范围人员选择树初始化加载
//  		shareScopeTree = structure.init(shareScopeTreeInit);
//  		shareScopeTree.open();
//  	});
  	$("#diaryAgent").click(function(){
//  		diaryMyCalendar.toAgentSetView();
  		diaryMyCalendar.showAgentSetDiv();
  	});//日程委托按键点击绑定事件
//  	$("#closeModifyType").click(function(){diaryMyCalendar.closeModifyType();});//关闭编辑类型
//  	$("#closeDetail").click(function(){diaryMyCalendar.closeDetail();});//关闭详细页
  	$("#closeWorklogSummary").click(function(){diaryMyCalendar.closeWorklogSummary();});//关闭日志汇总
//	$("#remindSet").click(function(){
//		schedule.showRemind();
//	});/**提醒按钮事件绑定*/
	/**-----------------------------------------------日程汇总begin------------------------------------------------------**/
	$("#diarySummary").click(function(){
		schedule.summarySearch(sysdate,sysdate);
//		$('#Schedule-detail').modal('hide');
		$('#startDateTemp').val(sysdate);//将查询条件赋值给临时变量 add by lihongyu at 2014-11-10
		$('#endDateTemp').val(sysdate);//将查询条件赋值给临时变量  add by lihongyu at 2014-11-10
		$('#Program-summary').modal('show');
	});//日程汇总按键点击事件
	$("#searchDiarySummary").click(function(){
		$('#startDateTemp').val($("#starttimeBegin").val());//将查询条件赋值给临时变量 add by lihongyu at 2014-11-10
		$('#endDateTemp').val($("#starttimeEnd").val());//将查询条件赋值给临时变量  add by lihongyu at 2014-11-10
		schedule.summarySearch($("#starttimeBegin").val(),$("#starttimeEnd").val());
	});//日程汇总查询按键点击事件
	$("#searchDiarySummaryReset").click(function(){schedule.searchDiarySummaryReset();});//日程汇总重置
//	$("#exportExcelDiary").click(function(){schedule.exportExcel($("#starttimeBegin").val(),$("#starttimeEnd").val());});//导出到Excel
	$("#exportExcelDiary").click(function(){schedule.exportExcel($("#startDateTemp").val(),$("#endDateTemp").val());});//导出到Excel
	/**-----------------------------------------------日程汇总end------------------------------------------------------**/
  	$(".datepicker-input").datepicker();//日历控件重新初始化
  	//写日志按钮事件绑定
  	$("#writeWorklogBtn").click(function(){diaryMyCalendar.writeWorklog();});
  	//写周计划按钮事件绑定
  	$("#writeWeekPlanBtn").click(function(){diaryMyCalendar.writeWeekPlan();});
  	//写月计划按钮事件绑定
  	$("#writeMonthPlanBtn").click(function(){diaryMyCalendar.writeMonthPlan();});
  	//看下级日志按钮事件绑定
  	$("#underlingWorklogBtn").click(function(){diaryMyCalendar.underlingWorklog();});
  	//看下级周按钮事件绑定
  	$("#underlingWeekPlanBtn").click(function(){diaryMyCalendar.underlingWeekPlan();});
  	//看下级月按钮事件绑定
  	$("#underlingMonthPlanBtn").click(function(){diaryMyCalendar.underlingMonthPlan();});
  	//日志上级领导批注按钮事件绑定
  	$("#worklogAnnoBtn").click(function(){diaryMyCalendar.worklogAnno();});
  	//周计划上级领导批注按钮事件绑定
  	$("#weekPlanAnnoBtn").click(function(){diaryMyCalendar.weekPlanAnno();});
  	//月计划上级领导批注按钮事件绑定
  	$("#monthPlanAnnoBtn").click(function(){diaryMyCalendar.monthPlanAnno();});
  	//日志提交情况按钮事件绑定
  	$("#worklogConditionBtn").click(function(){diaryMyCalendar.worklogCondition();});
  	//周计划提交情况按钮事件绑定
  	$("#weekPlanConditionBtn").click(function(){diaryMyCalendar.weekPlanCondition();});
  	//月计划提交情况按钮事件绑定
  	$("#monthPlanConditionBtn").click(function(){diaryMyCalendar.monthPlanCondition();});
  	
  	//周计划汇总
  	$("#weekPlanCollectBtn").click(function(){diaryMyCalendar.weekPlanCollect();});
  	//月计划汇总
  	$("#monthPlanCollectBtn").click(function(){diaryMyCalendar.monthPlanCollect();}); 	
  	//绑定批注”回复“
	$('#comment').on('click','a[name="reply"]',function(){
		schedule.commentReply(this);
	});
	//绑定批注”发送“
	$('#comment').on('click','a[name="send"]',function(){
		schedule.commentSend(this);
	});
	//绑定批注内容清除
	$('#diaryForm').on('click','#clearleaderIdea',function(){$('#leaderIdeaContent').val('');});
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){schedule.saveLeaderIdeaForm();});
	
	//绑定汇总查询
	$('#collectSearch').click(function(){
		diaryMyCalendar.collectSearch();
	});
	//绑定日志汇总
	$('#collect').click(function(){
		$('#collectForm').find("input[type=text]").val("");
//		$('#collectForm')[0].reset();
		$('#collectTable tbody').empty();
		$('#worklogDateBegin').val(schedule.getSysDate());
		$('#worklogDateEnd').val(schedule.getSysDate());
		diaryMyCalendar.collectSearch();
		$('#worklog_summary').modal('show');
	});
	//绑定日志汇总重置
	$('#collectReset').click(function(){
		$('#worklogDateBegin').val(schedule.getSysDate());
		$('#worklogDateEnd').val(schedule.getSysDate());
	});
});

/**
* 时间对象的格式化
*/
Date.prototype.format = function(format)
{
var o = {
"M+" : this.getMonth() + 1,
"d+" : this.getDate(),
"h+" : this.getHours(),
"m+" : this.getMinutes(),
"s+" : this.getSeconds(),
"q+" : Math.floor((this.getMonth() + 3) / 3),
"S" : this.getMilliseconds()
};

if (/(y+)/.test(format))
{
format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
- RegExp.$1.length));
}

for (var k in o)
{
if (new RegExp("(" + k + ")").test(format))
{
format = format.replace(RegExp.$1, RegExp.$1.length == 1
? o[k]
: ("00" + o[k]).substr(("" + o[k]).length));
}
}
return format;
};
$(function(){
	
	//绑定日志汇总导出Excel
	$('#exportExcel').click(function(){
		common.exportExcel();
	});
	
	var content = $("#content").height();
    var headerHeight_1 = $('#header_1').height() || 0;
    //树
    if($("#LeftHeight_rc")[0]){
        //$(".tree-right").css("margin-left","218px");
//定义高度
		$("#LeftHeight_rc").height(content-100-headerHeight_1);
        var lh = $("#LeftHeight_rc").height()

      $("#scrollable").scroll(function() {
        if($("#scrollable").scrollTop()>=113){
            $("#LeftHeight_rc").addClass("fixedNav");
            $("#LeftHeight_rc").height(lh + 113)
        }else{
            var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
            $("#LeftHeight_rc").height(lh + a)
            //$("#LeftHeight").height()
            $("#LeftHeight_rc").removeClass("fixedNav");
        } 
    
      });
    }
    //树end
	})