var diaryShareCalendar={};

//日程列表
diaryShareCalendar.toListView=function(){
	var userid=$("#userid").val();
	loadrightmenu('/po/diary/diaryShareList.action?possessorId='+userid+"&time=" + new Date());
};
//关闭详细页
diaryShareCalendar.closeDetail=function (){
	$('#Schedule-detail').modal('hide');
};
//关闭阅读情况
diaryShareCalendar.closeReading=function (){
	$('#Reading').modal('hide');
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
    $("#closeDetail").click(function(){diaryShareCalendar.closeDetail();});//关闭详细页
    $("#closeReading").click(function(){diaryShareCalendar.closeReading();});//关闭阅读情况
    $("#search").click(function(){schedule.searchFullCalendar();});//日历页快速查询
	$("#listView").click(function(){diaryShareCalendar.toListView();});//进入列表页
	$('#readingStatus').click(function(){
		var worklogId = $('#delId').val();
		schedule.readingStatus(worklogId);
	});//绑定阅读情况查询
  	$(".datepicker-input").datepicker();//日历控件重新初始化
  	$(".tree-right").css("padding-left","215px");//左侧树空间初始化
//  	schedule.initLeftTreeDiv();/**初始化左侧的树的div宽高*/
  	
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
});