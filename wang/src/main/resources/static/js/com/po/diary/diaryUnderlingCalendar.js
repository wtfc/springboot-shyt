var diaryUnderlingCalendar={};
/**关闭领导批注*/
diaryUnderlingCalendar.closeAnno = function(){
	$('#annoComment').empty();
};
//领导批注框清空
diaryUnderlingCalendar.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};
//进入下属日程列表
diaryUnderlingCalendar.toListView=function(){
	var userid=$("#userid").val();
	loadrightmenu('/po/diary/diaryUnderlingList.action?possessorId='+userid+"&time=" + new Date());
};
//日程汇总重置
diaryUnderlingCalendar.searchDiarySummaryReset=function(){
	var sysdate=schedule.getSysDate();
	$("#starttimeBegin").val(sysdate);
	$("#starttimeEnd").val(sysdate);
//	schedule.collectSearch(sysdate,sysdate);
};
//日程汇总数据查询
//diaryUnderlingCalendar.toDiarySummary=function (start,end){
//	var userid=$("#userid").val();
//	$("#starttimeBegin").val(start);
//	$("#starttimeEnd").val(end);
//	var startDate=schedule.getSysDateBegin(start);//系统时间 00:00:00
//	var endDate=schedule.getSysDateEnd(end);//系统时间 23:59:59
//	$.ajax({
//		type : "POST",
//		url : getRootPath()+"/po/diary/queryDiarySummary.action?time=" + new Date(),
//		data:{"startDate":startDate,"endDate":endDate,"possessorId":userid},
//		dataType : "json",
//		success : function(data) {
//			var tableContent="";
//			for(var i=0;i<data.length;i++){
//				var annoContent = data[i].annoContent==null?"":data[i].annoContent;
//				tableContent+="<tr><td>"+data[i].possessorIdValue+"</td>" +
////						"<td>"+data[i].createDate+"</td>" +
//						"<td>"+data[i].starttime+" - "+data[i].endtime+"</td>" +
//						"<td>"+data[i].content+"</td>" +
//						"<td>"+annoContent+"</td></tr>";
//			}
//			if(tableContent==""){
//				tableContent="<tr><td colspan='4'>没有批注内容</td></tr>";
//			}
//			$("#innerTable").html(tableContent);
//		}
//	});
//	$('#Program-summary').modal('show');
//};
//关闭详细页
diaryUnderlingCalendar.closeDetail=function (){
	$('#Schedule-detail').modal('hide');
};
//关闭日程汇总
diaryUnderlingCalendar.closeSummary=function (){
	$('#Program-summary').modal('hide');
};
//关闭阅读情况
diaryUnderlingCalendar.closeReading=function (){
	$('#Reading').modal('hide');
};
//导出到Excel
diaryUnderlingCalendar.exportExcel=function(start,end){
	var userid=$("#userid").val();
//	$("#starttimeBegin").val(start);
//	$("#starttimeEnd").val(end);
	var startDate=schedule.getSysDateBegin(start);//系统时间 00:00:00
	var endDate=schedule.getSysDateEnd(end);//系统时间 23:59:59
	
	var url = getRootPath()+"/po/diary/exportExcel.action?startDate="+startDate+"&endDate="+endDate+"&possessorId="+userid+"&time="+new Date();
	window.location.href=url;
};
//初始化
jQuery(function($){
	var sysdate=schedule.getSysDate();
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
    	$('.calendar').fullCalendar('today');
	});
    $("#closeDetail").click(function(){diaryUnderlingCalendar.closeDetail();});//关闭详细页
    $("#closeSummary").click(function(){diaryUnderlingCalendar.closeSummary();});//关闭日程汇总
    $("#searchDiarySummaryReset").click(function(){diaryUnderlingCalendar.searchDiarySummaryReset();});//日程汇总重置
    $("#closeReading").click(function(){diaryUnderlingCalendar.closeReading();});//关闭阅读情况
    $("#search").click(function(){schedule.searchFullCalendar();});//日程日历形式快速查询
	$("#listView").click(function(){diaryUnderlingCalendar.toListView();});//进入下属日程列表
	$("#diarySummary").click(function(){
		schedule.summarySearch(sysdate,sysdate);
		$('#startDateTemp').val(sysdate);
		$('#endDateTemp').val(sysdate);
		$('#Schedule-detail').modal('hide');
		$('#Program-summary').modal('show');
	});//日程汇总按键点击事件
	$("#searchDiarySummary").click(function(){
		$('#startDateTemp').val($("#starttimeBegin").val());
		$('#endDateTemp').val($("#starttimeEnd").val());
		schedule.summarySearch($("#starttimeBegin").val(),$("#starttimeEnd").val());
	});//日程汇总查询按键点击事件
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
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){schedule.saveLeaderIdeaForm();});
	$("#clearleaderIdea").click(function(){diaryUnderlingCalendar.clearleaderIdea();});//领导批注框清空
	$("#exportExcel").click(function(){diaryUnderlingCalendar.exportExcel($("#startDateTemp").val(),$("#endDateTemp").val());});//导出到Excel
	//$("#exportExcel").click(function(){diaryUnderlingCalendar.exportExcel($("#starttimeBegin").val(),$("#starttimeEnd").val());});//导出到Excel
});
