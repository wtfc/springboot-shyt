var diaryUnderlingList={};
/**关闭领导批注*/
diaryUnderlingList.closeAnno = function(){
	$('#annoComment').empty();
};
//领导批注框清空
diaryUnderlingList.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};
//日程汇总重置
diaryUnderlingList.searchDiarySummaryReset=function(){
	var sysdate=schedule.getSysDate();
	$("#starttimeBegin").val(sysdate);
	$("#starttimeEnd").val(sysdate);
//	schedule.collectSearch(sysdate,sysdate);
};
//日程汇总数据查询
//diaryUnderlingList.toDiarySummary=function (start,end){
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
//进入日程日历页面
diaryUnderlingList.toCalendarView=function(){
	var userid=$("#userid").val();
	loadrightmenu("/po/diary/diaryUnderlingCalendar.action?userid="+userid+"&time=" + new Date());
};
//关闭日程汇总
diaryUnderlingList.closeSummary=function (){
	$('#Program-summary').modal('hide');
};
//关闭阅读情况
diaryUnderlingList.closeReading=function (){
	$('#Reading').modal('hide');
};
//导出到Excel
diaryUnderlingList.exportExcel=function(start,end){
	var userid=$("#userid").val();
//	$("#starttimeBegin").val(start);
//	$("#starttimeEnd").val(end);
	var startDate=schedule.getSysDateBegin(start);//系统时间 00:00:00
	var endDate=schedule.getSysDateEnd(end);//系统时间 23:59:59
	
	var url = getRootPath()+"/po/diary/exportExcel.action?startDate="+startDate+"&endDate="+endDate+"&possessorId="+userid+"&time="+new Date();
	window.location.href=url;
};
diaryUnderlingList.refreshTreeForList=function(){
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
	$('#leaderIdeaTable').hide();//隐藏批注框
	var sysdate=schedule.getSysDate();//当前系统时间yyyy-MM-dd
	var initbegin=schedule.getSysDateBegin(sysdate);//yyyy-MM-dd 00:00:00
	var initend=schedule.getSysDateEnd(sysdate);//yyyy-MM-dd 23:59:59
	$("#startDateTime").val(initbegin);//列表页初次进入初始化时间
	$("#endDateTime").val(initend);//列表页初次进入初始化时间
	$("#calendarView").click(function(){diaryUnderlingList.toCalendarView();});//进入日历页面
	$("#diarySummary").click(function(){
		schedule.summarySearch(sysdate,sysdate);
		$('#startDateTemp').val(sysdate);
		$('#endDateTemp').val(sysdate);
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
	$("#closeSummary").click(function(){diaryUnderlingList.closeSummary();});//关闭日程汇总
	$("#clearleaderIdea").click(function(){diaryUnderlingList.clearleaderIdea();});//领导批注框清空
	$("#searchDiarySummaryReset").click(function(){diaryUnderlingList.searchDiarySummaryReset();});//日程汇总重置
    $("#closeReading").click(function(){diaryUnderlingList.closeReading();});//关闭阅读情况
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
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){schedule.saveLeaderIdeaForm();});
	//$("#exportExcel").click(function(){diaryUnderlingList.exportExcel($("#starttimeBegin").val(),$("#starttimeEnd").val());});//导出到Excel
	$("#exportExcel").click(function(){diaryUnderlingList.exportExcel($("#startDateTemp").val(),$("#endDateTemp").val());});//导出到Excel
	ie8StylePatch();
});