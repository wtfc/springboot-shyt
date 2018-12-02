var diaryShareList={};

//日程日历
diaryShareList.toCalendarView=function(){
	var userid=$("#userid").val();
	loadrightmenu("/po/diary/diaryShareCalendar.action?userid="+userid+"&time=" + new Date());
};
//关闭阅读情况
diaryShareList.closeReading=function (){
	$('#Reading').modal('hide');
};
diaryShareList.refreshTreeForList=function(){
	if($("#treeDemo")[0]){
    	var content = $("#content").height();
        var headerHeight_1 = $('#header_1').height() || 0;
        var headerHeight_2 = $("#header_2").height() || 0;
        $(".tree-right").css("padding-left","215px");
        $("#LeftHeight_box_rl").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight_box_rl").height();
	    var day_height=$("#datePicker").find("div").height()+32;
	    $("#LeftHeight_rl").height(content-80-headerHeight_1-headerHeight_2-day_height);
	    var lh_day = $("#LeftHeight_rl").height(); 
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight_box_rl").addClass("fixedNav");
	        $("#LeftHeight_rl").height(lh - day_height + 113);
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
	        		$("#LeftHeight_rl").height(lh_day + a)
	        //$("#LeftHeight").height()
	        $("#LeftHeight_box_rl").removeClass("fixedNav");
	    }
    }
};
//初始化
jQuery(function($){
	var initsysdate=schedule.getSysDate();//当前系统时间yyyy-MM-dd
	var initbegin=schedule.getSysDateBegin(initsysdate);//yyyy-MM-dd 00:00:00
	var initend=schedule.getSysDateEnd(initsysdate);//yyyy-MM-dd 23:59:59
	$("#startDateTime").val(initbegin);//列表页初次进入初始化时间
	$("#endDateTime").val(initend);//列表页初次进入初始化时间
	$("#calendarView").click(function(){diaryShareList.toCalendarView();});//进入日历页面
	$('#readingStatus').click(function(){
		var worklogId = $('#delId').val();
		schedule.readingStatus(worklogId);
	});//绑定阅读情况查询
	$("#closeReading").click(function(){diaryShareList.closeReading();});//关闭阅读情况
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
