var diaryLeadList={};
//查询当前用户是否已被人委托，有则赋值
diaryLeadList.getDelegation=function(){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/diaryDelegation/getDelegationByMandataryId.action?time=" + new Date(),
		data : {},
		dataType : "json",
		async:false,
		success : function(data) {
			if (data) {
//				if(data.id!=null&&data.id!=""){//单委托
//					$("#mandatorId").val(data.mandatorId);
//				}
				$("#mandatorId").val(data.mandatorIds);
			}
		}
	});
};
//初始化编辑按键
diaryLeadList.initModifyButton=function(){
	var buttons="<a class='btn dark' id='toModify'>编 辑</a>" +
			"<a class='btn' id='deleteDiary'>删 除</a>" +
			"<a type='button' class='btn' id='closeDetail' data-dismiss='modal'>关 闭</a>";
	$("#operationButtons").html(buttons);
	$("#deleteDiary").click("click", function(){schedule.deleteDiary($("#delId").val());});
	$("#toModify").click(function(){schedule.toModifyType()});
};
//显示新建按键
diaryLeadList.showNewAddButton=function(){
	var newAddButton="<div class='input-group-btn p-l'>" +
			"<a class='a-icon i-new m-r-xs' type='button' href='#'" +
			"role='button' data-toggle='modal' id='toNewDiary' onclick='schedule.toNewDiary();'>" +
//			"<i class='fa fa-file-add'></i>" +
			"新 增</a></div>";
	$("#toNewAddButton").html(newAddButton);
};
//清空表单
diaryLeadList.clearForm = function () {
	$('#diaryForm').find("input[type=text]").val("");
	$('#diaryForm').find("textarea").val("");
//	$('#diaryForm')[0].reset();
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
diaryLeadList.toCalendarView=function(){
	var userid=$("#userid").val();
	loadrightmenu("/po/diary/diaryLeadCalendar.action?userid="+userid+"&time=" + new Date());
};
//关闭修改方式
diaryLeadList.closeModifyType=function(){
	$('#period-diary-modify').modal('hide');
};
//初始化
jQuery(function($){
	diaryLeadList.getDelegation();//调用日历页的方法
	var sysdate=schedule.getSysDate();
	$("#startDateTime").val(schedule.getSysDateBegin(sysdate));
	$("#endDateTime").val(schedule.getSysDateEnd(sysdate));
//	var mandatorId=$("#mandatorId").val();//单委托
	var userid=$("#userid").val();
	var loginUser=$("#loginUser").val();
//	if(userid==mandatorId&&userid!=0){//当前用户是选中用户的被委托人 单委托
	if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
		diaryLeadList.showNewAddButton();
		schedule.changeButtonSectionToDetail();
		$("#toModify").hide();
		$("#deleteDiary").hide();
		$("#calendarView").removeClass("btn");
		$("#calendarView").addClass("btn dark");
	}
    $('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio
	$("#savaOrModify").click(function(){//保存继续按键点击事件绑定
		schedule.savaOrModify(false);
	});
	$("#sava").click(function(){//保存按键点击事件绑定
		schedule.savaOrModify(true);
	});
	$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
			schedule.setPeriodDate();
			if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
				schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
				schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
			}
		}
	);
	$("#toModify").click(function(){schedule.toModifyType()});//修改按键点击事件绑定
	$("#modifyDiv").click(function(){schedule.toModify()});//修改方式页面确定按键点击事件绑定
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
	$("#calendarView").click(function(){diaryLeadList.toCalendarView();});//日历形式展示按键点击事件绑定
	$("#closeModifyType").click(function(){diaryLeadList.closeModifyType();});//关闭修改类型
	$("#remindSet").click(function(){schedule.showRemind();});/**提醒按钮事件绑定*/
  	$(".datepicker-input").datepicker();//日历控件重新初始化
  	$(".tree-right").css("padding-left","215px");//左侧树空间初始化
  	schedule.initLeftTreeDiv();/**初始化左侧的树的div宽高*/
  	$('#comment').empty();//清空批注
	schedule.initAnno("0");//初始化批注--初始化时候默认查一个差不到的id用以显示空信息
  //绑定批注”回复“
	$('#comment').on('click','a[name="reply"]',function(){
		schedule.commentReply(this);
	});
	//绑定批注”发送“
	$('#comment').on('click','a[name="send"]',function(){
		schedule.commentSend(this);
	});
	//绑定批注内容清除
	$('#leaderIdeaForm').on('click','#clearleaderIdea',function(){$('#leaderIdeaContent').val('');});
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){schedule.saveLeaderIdeaForm();});
	ie8StylePatch();
});