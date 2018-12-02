var diaryMyList={};
/**-----------------------日志汇总begin-------------------------------*/
//分页对象
diaryMyList.collectTable = null;
//分页查询用户
diaryMyList.collectSearch = function () {
	$('#startDateTemp').val($("#worklogDateBegin").val());//将查询条件赋值给临时变量 add by lihongyu at 2014-11-10
	$('#endDateTemp').val($("#worklogDateEnd").val());//将查询条件赋值给临时变量  add by lihongyu at 2014-11-10
	//显示列信息
	diaryMyList.collectTableAoColumns = [
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
				reVal="<a href=\"#\" onclick=\"diaryMyList.showAnno("+full.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
			}
			return reVal;
		}}
	 ];
	
	//组装后台参数
	diaryMyList.collectTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(diaryMyList.collectTable, aoData);
		//组装查询条件
		var worklogDateBegin = $("#worklogDateBegin").val();
		var worklogDateEnd = $("#worklogDateEnd").val();
		
		aoData.push({ "name": "worklogDateBegin", "value": worklogDateBegin});
		aoData.push({ "name": "worklogDateEnd", "value": worklogDateEnd});
	};
	
	//table对象为null时初始化
	if (diaryMyList.collectTable == null) {
		diaryMyList.collectTable = $('#collectTable').dataTable( {
			sAjaxSource: getRootPath()+"/po/worklog/getMyworklogCollect.action?time=" + new Date(),
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: diaryMyList.collectTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				diaryMyList.collectTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,3]}]
		} );
		setColumnVis(diaryMyList.collectTable, 6);
		
	} else {
		//table不为null时重绘表格
		diaryMyList.collectTable.fnDraw();
		//pageChange(user.oTable);
	}
};
/**查看领导批注*/
diaryMyList.showAnno = function(dataid){
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
	$("#worklog-anno").modal("setPaddingTop");//UI调整，处理弹出层首次显示下方按钮遮挡问题
	$('#worklog-anno').modal('show');
};
/**关闭领导批注*/
diaryMyList.closeAnno = function(){
	$('#annoComment').empty();
};
//清空表单
diaryMyList.clearForm = function () {
	$('#diaryForm').find("input[type=text]").val("");
	$('#diaryForm').find("textarea").val("");
//	$('#diaryForm')[0].reset();
	$('#shareScopeId').val("");
	selectControl.clearValue("shareScopeChoose-shareScopeChoose");
	document.getElementById("periodStartdate").disabled=false;
	document.getElementById("periodEnddate").disabled=false;
	document.getElementById("starttime").disabled=false;
	document.getElementById("endtime").disabled=false;
	var periodTypeTmp=document.getElementsByName("periodTypeTmp");
	for(var i=0;i<periodTypeTmp.length;i++){
		periodTypeTmp[i].disabled=false;
	}
	hideErrorMessage();
};
//日程日历
diaryMyList.toCalendarView=function(){
	loadrightmenu('/po/diary/diaryMyCalendar.action');
};
//关闭编辑方式
diaryMyList.closeModifyType=function (){
	$('#period-diary-modify').modal('hide');
};
//关闭日志汇总
diaryMyList.closeWorklogSummary=function (){
	$('#worklog_summary').modal('hide');
};

//写日志跳转方法
diaryMyList.writeWorklog = function (){
	loadrightmenu("/po/worklog/myWorklogCalendarSkip.action?time=" + new Date(),"unknown");
};
//写周计划跳转方法
diaryMyList.writeWeekPlan = function (){
	loadrightmenu("/workFlow/processDefinition/toStartProcess.action?flowIden=planKey&condition=diaryWeekJump&time=" + new Date(),"周计划编制");
};
//写月计划跳转方法
diaryMyList.writeMonthPlan = function (){
	loadrightmenu("/workFlow/processDefinition/toStartProcess.action?flowIden=planKey&condition=diaryMonthJump&time=" + new Date(),"月计划编制");
};
//看下属日志跳转方法
diaryMyList.underlingWorklog = function (){
	loadrightmenu("/po/worklog/worklogManageSkip.action?time=" + new Date(),true);
};
//看下属周计划方法
diaryMyList.underlingWeekPlan = function (){
	loadrightmenu("/po/plan/subPlanQuery.action?jumpCondition=diaryWatchWeekJump&&time=" + new Date(),"下属周计划");
};
//看下属月计划方法
diaryMyList.underlingMonthPlan = function (){
	loadrightmenu("/po/plan/subPlanQuery.action?jumpCondition=diaryWatchMonthJump&&time=" + new Date(),"下属月计划");
};

//工作日志批注数量统计方法
diaryMyList.setWorklogAnnoCount = function (type){
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
diaryMyList.setPlanAnnoCount = function (type){
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
diaryMyList.worklogAnno = function (){
	var worklogAnnoText = $("#worklogAnnoBtn").text();
	if(worklogAnnoText == 0){
		msgBox.info({
			content:$.i18n.prop("JC_OA_PO_019")
		});
	}else{
		loadrightmenu("/po/worklog/worklogLeaderAnno.action?time=" + new Date(),"unknown");
	}
};
//查看周计划领导批注
diaryMyList.weekPlanAnno = function (){
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
diaryMyList.monthPlanAnno = function (){
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
diaryMyList.worklogCondition = function (){
	loadrightmenu("/po/plan/dayPlanConditionSkip.action?time=" + new Date(),"unknown");
};

//周计划提交情况
diaryMyList.weekPlanCondition = function (){
	loadrightmenu("/po/plan/weekPlanConditionSkip.action?time=" + new Date(),"unknown");
};

//月计划提交情况
diaryMyList.monthPlanCondition = function (){
	loadrightmenu("/po/plan/monthPlanConditionSkip.action?time=" + new Date(),"unknown");
}; 


//周计划汇总
diaryMyList.weekPlanCollect = function (){
	if(loadSpecifyMenu(96)){
		loadrightmenu("/po/plan/manageCollectPlanListForDiarySkip.action?jumpCondition=weekCollectJump&&time=" + new Date(),"周计划汇总");
	}
};
//月计划汇总
diaryMyList.monthPlanCollect = function (){
	if(loadSpecifyMenu(96)){
		loadrightmenu("/po/plan/manageCollectPlanListForDiarySkip.action?jumpCondition=monthCollectJump&&time=" + new Date(),"月计划汇总");
	}
};

diaryMyList.refreshTreeForList=function(){
	var head1 = $("#diaryContentOne").height(),
		head2 = $('#diaryContentTwo').height(),
		temp = head1+head2-155;
	$("#LeftHeight_list").height(temp>305?temp:305);
};
//初始化
jQuery(function($){
	var sysdate=schedule.getSysDate();
	$('#leaderIdeaTable').hide();//隐藏批注框
	diaryMyList.setWorklogAnnoCount();//初始化设置工作日志批注数量
	diaryMyList.setPlanAnnoCount(0);//初始化设置周计划批注数量
	diaryMyList.setPlanAnnoCount(1);//初始化设置月计划批注数量
	$("#toModify").hide();
	$("#deleteDiary").hide();
	$("#calendarView").removeClass("btn");
	$("#calendarView").addClass("btn dark");
	var initsysdate=schedule.getSysDate();//当前系统时间yyyy-MM-dd
	var initbegin=schedule.getSysDateBegin(initsysdate);//yyyy-MM-dd 00:00:00
	var initend=schedule.getSysDateEnd(initsysdate);//yyyy-MM-dd 23:59:59
	$("#startDateTime").val(initbegin);//列表页初次进入初始化时间
	$("#endDateTime").val(initend);//列表页初次进入初始化时间
    $('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio
	$("#savaOrModify").click(function(){//保存继续按键点击事件绑定
		schedule.savaOrModify(false);
	});
	$("#sava").click(function(){//保存按键点击事件绑定
		schedule.savaOrModify(true);
	});
	$('#diaryForm').on('click','#advancedSettings',function(){
		schedule.setPeriodDate();
		if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
			schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
			schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
		}
	});
//	$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
//		schedule.setPeriodDate();
//		if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
//			schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
//			schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
//		}
//	});
	$("#toModify").click(function(){schedule.toModifyType()});//编辑按键点击事件绑定
	$("#modifyDiv").click(function(){schedule.toModify()});//编辑方式页面确定按键点击事件绑定
	$("#periodConfirm").click(function(){//高级设置页面确定按键点击事件绑定
		schedule.periodConfirm();
		schedule.periodContentTemp();//临时存储高级设置层中的内容
	});
	$("#periodCancel").click(function(){
		if($("#newOrModify").val()=="0"){//新建页
			if($("#newOk").val()!=1){
				schedule.clearPeriodContent2();//清空高级设置
				schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
			}
		}
		if($("#newOrModify").val()=="1"){//修改页
			if($("#modifyExistValue").val()!=1){
				schedule.clearPeriodContent2();//清空高级设置
				schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
			}
		}
		schedule.periodCancel();
	});//高级设置页面取消按键点击事件绑定
	$("#toNewDiary").click(function(){schedule.toNewDiary();});//新建日程
	$("#calendarView").click(function(){diaryMyList.toCalendarView();});//日历形式展示按键点击事件绑定
  	$("#deleteDiary").click("click", function(){//删除日程
  		schedule.deleteDiary($("#delId").val());
  	});
  	$("#remindSet").click(function(){schedule.showRemind();});/**提醒按钮事件绑定*/
  	$("#closeModifyType").click(function(){diaryMyList.closeModifyType();});//关闭编辑类型
  	$("#closeWorklogSummary").click(function(){diaryMyList.closeWorklogSummary();});//关闭日志汇总
  	
  	/**-----------------------------------------------日程汇总begin------------------------------------------------------**/
  	$('#diaryForm').on('click','#diarySummary',function(){
  		schedule.summarySearch(sysdate,sysdate);
		$('#Program-summary').modal('show');
  	});//日程汇总按键点击事件
//	$("#diarySummary").click(function(){
//		schedule.summarySearch(sysdate,sysdate);
//		$('#Program-summary').modal('show');
//	});//日程汇总按键点击事件
  	//点击日程汇总时将临时起止时间赋值为当前时间
  	$("#diarySummary").click(function(){
  		$('#startDateTemp').val(sysdate);
  		$('#endDateTemp').val(sysdate);
  	});		
	$("#searchDiarySummary").click(function(){
		$('#startDateTemp').val($("#starttimeBegin").val());
		$('#endDateTemp').val($("#starttimeEnd").val());
		schedule.summarySearch($("#starttimeBegin").val(),$("#starttimeEnd").val());
	});//日程汇总查询按键点击事件
	$("#searchDiarySummaryReset").click(function(){schedule.searchDiarySummaryReset();});//日程汇总重置
//	$("#exportExcelDiary").click(function(){schedule.exportExcel($("#starttimeBegin").val(),$("#starttimeEnd").val());});//导出到Excel
	$("#exportExcelDiary").click(function(){schedule.exportExcel($("#startDateTemp").val(),$("#endDateTemp").val());});//导出到Excel
	/**-----------------------------------------------日程汇总end------------------------------------------------------**/
  	
  	//日历控件重新初始化
  	$(".datepicker-input").datepicker();
  	//日志按钮事件绑定
  	$("#underlingWorklogBtn").click(function(){diaryMyList.underlingWorklog();});

  	//写日志按钮事件绑定
  	$("#writeWorklogBtn").click(function(){diaryMyList.writeWorklog();});
  	//写周计划按钮事件绑定
  	$("#writeWeekPlanBtn").click(function(){diaryMyList.writeWeekPlan();});
  	//写月计划按钮事件绑定
  	$("#writeMonthPlanBtn").click(function(){diaryMyList.writeMonthPlan();});
  	//看下级日志按钮事件绑定
  	$("#underlingWorklogBtn").click(function(){diaryMyList.underlingWorklog();});
  	//看下级周按钮事件绑定
  	$("#underlingWeekPlanBtn").click(function(){diaryMyList.underlingWeekPlan();});
  	//看下级月按钮事件绑定
  	$("#underlingMonthPlanBtn").click(function(){diaryMyList.underlingMonthPlan();});
  	//日志上级领导批注按钮事件绑定
  	$("#worklogAnnoBtn").click(function(){diaryMyList.worklogAnno();});
  	//周计划上级领导批注按钮事件绑定
  	$("#weekPlanAnnoBtn").click(function(){diaryMyList.weekPlanAnno();});
  	//月计划上级领导批注按钮事件绑定
  	$("#monthPlanAnnoBtn").click(function(){diaryMyList.monthPlanAnno();});
  	//日志提交情况按钮事件绑定
  	$("#worklogConditionBtn").click(function(){diaryMyList.worklogCondition();});
  	//周计划提交情况按钮事件绑定
  	$("#weekPlanConditionBtn").click(function(){diaryMyList.weekPlanCondition();});
  	//月计划提交情况按钮事件绑定
  	$("#monthPlanConditionBtn").click(function(){diaryMyList.monthPlanCondition();});
  	
  	//周计划汇总
  	$("#weekPlanCollectBtn").click(function(){diaryMyList.weekPlanCollect();});
  	//月计划汇总
  	$("#monthPlanCollectBtn").click(function(){diaryMyList.monthPlanCollect();});
  	
  	
  	$('#comment').empty();//清空批注
	schedule.initAnno("0");//初始化批注--初始化时候默认查一个差不到的id用以显示空信息
    //绑定批注"回复"
	$('#comment').on('click','a[name="reply"]',function(){
		schedule.commentReply(this);
	});
	//绑定批注"发送"
	$('#comment').on('click','a[name="send"]',function(){
		schedule.commentSend(this);
	});
	//绑定批注内容清除
	$('#diaryForm').on('click','#clearleaderIdea',function(){$('#leaderIdeaContent').val('');});
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){schedule.saveLeaderIdeaForm();});
	
	//绑定汇总查询
	$('#collectSearch').click(function(){
		diaryMyList.collectSearch();
	});
	//绑定日志汇总
	$('#collect').click(function(){
		$('#collectForm')[0].reset();
		$('#collectTable tbody').empty();
		$('#worklogDateBegin').val(schedule.getSysDate());
		$('#worklogDateEnd').val(schedule.getSysDate());
		diaryMyList.collectSearch();
		$('#worklog_summary').modal('show');
	});
	
	//绑定日志汇总重置
	$('#collectReset').click(function(){
		$('#worklogDateBegin').val(schedule.getSysDate());
		$('#worklogDateEnd').val(schedule.getSysDate());
	});
	//绑定日志汇总导出Excel
	$('#exportExcel').click(function(){
		common.exportExcel();
	});
	ie8StylePatch();
	diaryMyList.refreshTreeForList();
});
//$(function(){
//	var content = $("#gadfhjahdfh").outerHeight(true);
//    var head_1 = $('#ajash5a6dfhgadfg').outerHeight(true);
//    //树
//    //if($("#LeftHeight_list")[0]){
//        //$(".tree-right").css("margin-left","218px");
//    	//定义高度
//		//$("#LeftHeight_box_list").height(content-100-headerHeight_1);
//	    //var lh = content-100-headerHeight_1;//$("#LeftHeight_box_list").height();
//	    //var dateh=$("#datePicker").find("div").outerHeight(true);
//	    var heig = content+head_1-222;//222为日期插件高度
//	    $("#LeftHeight_list").height(heig>305?heig:305);
//	    //var lh_day = $("#LeftHeight_list").height();
////      $("#scrollable").scroll(function() {
//        //var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
////        if($("#scrollable").scrollTop()>=113){
////            $("#LeftHeight_box_list").addClass("fixedNav");
////            //$("#LeftHeight_list").height(lh - day_height + 113);
////        }else{
////            //$("#LeftHeight_list").height(lh_day + a)
////            //$("#LeftHeight").height()
////            $("#LeftHeight_box_list").removeClass("fixedNav");
////        } 
//    
//      //});
//      
//    //}
//    //树end
//});