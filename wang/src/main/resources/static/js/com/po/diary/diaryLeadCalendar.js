var diaryLeadCalendar={};

diaryLeadCalendar.subState = false;//重复提交标识
//查询当前用户是否已被人委托，有则赋值
diaryLeadCalendar.getDelegation=function(){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/diaryDelegation/getDelegationByMandataryId.action?time=" + new Date(),
		data : {},
		dataType : "json",
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
//清空表单
diaryLeadCalendar.clearForm = function () {
	$('#diaryForm').find("input[type=text]").val("");
	$('#diaryForm').find("textarea").val("");
//	$('#diaryForm')[0].reset();
	document.getElementById("periodStartdate").disabled=false;
	document.getElementById("periodEnddate").disabled=false;
	document.getElementById("starttime").disabled=false;
	document.getElementById("endtime").disabled=false;
//	var periodTypeTmp=document.getElementsByName("periodTypeTmp");
//	for(var i=0;i<periodTypeTmp.length;i++){
//		periodTypeTmp[i].disabled=false;
//	}
	hideErrorMessage();
};
//取消新增日程
diaryLeadCalendar.cancel=function(){
	diaryLeadCalendar.clearForm();
	$('#The-new-agenda').modal('hide');
};
//日程列表
diaryLeadCalendar.toListView=function(){
	loadrightmenu('/po/diary/diaryLeadList.action?possessorId='+$("#userid").val()+"&time=" + new Date());
};
//初始化编辑按键
diaryLeadCalendar.initModifyButton=function(){
	var buttons="<a class='btn dark' id='toModify'>编 辑</a>" +
			"<a class='btn' id='deleteDiary'>删 除</a>" +
			"<a type='button' class='btn' id='closeDetail' data-dismiss='modal'>关 闭</a>";
	$("#operationButtons").html(buttons);
	$("#deleteDiary").click("click", function(){schedule.deleteDiary($("#delId").val());});
	$("#toModify").click(function(){schedule.toModifyType()});
};
//关闭编辑方式
diaryLeadCalendar.closeModifyType=function(){
	$('#period-diary-modify').modal('hide');
};
//关闭详细信息
diaryLeadCalendar.closeDetail=function(){
	$('#Schedule-detail').modal('hide');
};
//新增修改X
diaryLeadCalendar.closeX=function(){
	diaryLeadCalendar.cancel();
	schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
};
//初始化
jQuery(function($){
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
    $('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio
    $("#search").click(function(){schedule.searchFullCalendar();});//日历页快速查询
	$("#savaOrModify").click(function(){//保存继续按键点击事件绑定
		schedule.savaOrModify(false);
	});
	$("#savaAndClose").click(function(){//保存退出按键点击事件绑定
		schedule.savaOrModify(true);
	});
	$("#cancel").click(function(){//取消按键点击事件绑定
		diaryLeadCalendar.cancel();
		schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
	});
	$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
		schedule.setPeriodDate();
		if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
			schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
			schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
		}
	});
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
	$("#listView").click(function(){diaryLeadCalendar.toListView();});//列表形式展示按键点击事件绑定
  	$("#deleteDiary").click("click", function(){//删除按键点击事件绑定
  		schedule.deleteDiary($("#delId").val());
  	});
  	$("#closeModifyType").click(function(){diaryLeadCalendar.closeModifyType();});//关闭编辑类型
  	$("#closeDetail").click(function(){diaryLeadCalendar.closeDetail();});//关闭详细页
  	$("#remindSet").click(function(){schedule.showRemind();});/**提醒按钮事件绑定*/
  	$(".datepicker-input").datepicker();//日历控件重新初始化
  	$(".tree-right").css("padding-left","215px");//左侧树空间初始化
//  	schedule.initLeftTreeDiv();/**初始化左侧的树的div宽高*/
  	
  	//绑定批注"回复"
	$('#comment').on('click','a[name="reply"]',function(){
		schedule.commentReply(this);
	});
	//绑定批注"发送"
	$('#comment').on('click','a[name="send"]',function(){
		schedule.commentSend(this);
	});
	//绑定批注内容清除
	$('#leaderIdeaForm').on('click','#clearleaderIdea',function(){$('#leaderIdeaContent').val('');});
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){schedule.saveLeaderIdeaForm();});
});