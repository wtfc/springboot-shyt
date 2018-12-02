var schedule = {};
var selectControlFlag=false;//为了应对列表页修改空人员选择树样式变形
/**-----------------------纯页面操作获取token bengin------------------------*/
schedule.getToken=function (){
	$.ajax({
		type : "POST",
		async : false,
		url : getRootPath() + "/po/diary/getToken.action?time=" + new Date(),
		data : {
		},
		dataType : "json",
		success : function(data) {
			$("#token").val(data.token);
		}
	});
};
/**-----------------------纯页面操作获取token end------------------------*/
/**-----------------------日程汇总begin-------------------------------*/
//分页对象
schedule.summaryTable = null;
//分页查询用户
schedule.summarySearch = function (start,end,pageRows) {
	//显示列信息
	schedule.summaryTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "possessorIdValue"},
		{mData: function(source) {
			var contentStrs="";
				contentStrs = source.starttime+" - "+source.endtime;
			return contentStrs;
		}},
		{mData: "content"},
//		{mData: "annoUserIdValue"},
//		{mData: function(source) {
//			contentStrs = source.annoContent==null?"":source.annoContent;
//			return contentStrs;
//		}},
		{mData: "annoCount",mRender : function(mData, type, full){
			var reVal="";
			if(mData >0){
				reVal="<a href=\"#\" onclick=\"schedule.showAnno("+full.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
			}
			return reVal;
		}}
	 ];
	
	//组装后台参数
	schedule.summaryTableFnServerParams = function(aoData){
		var userid=$("#userid").val();
		$("#starttimeBegin").val(start);
		$("#starttimeEnd").val(end);
		var startDate=schedule.getSysDateBegin(start);//系统时间 00:00:00
		var endDate=schedule.getSysDateEnd(end);//系统时间 23:59:59
		//排序条件
		getTableParameters(schedule.summaryTable, aoData);
		
		//组装查询条件
		aoData.push({ "name": "startDate", "value": startDate});
		aoData.push({ "name": "endDate", "value": endDate});
		aoData.push({ "name": "possessorId", "value": userid});
	};
	
	//table对象为null时初始化
	if (schedule.summaryTable == null) {
		schedule.summaryTable = $('#summaryTable').dataTable( {
			iDisplayLength: 5,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/po/diary/queryDiarySummary.action?time=" + new Date(),
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: schedule.summaryTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				schedule.summaryTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,3]}]
		} );
//		setColumnVis(schedule.collectTable, 6);
		
	} else {
		//table不为null时重绘表格
		schedule.summaryTable.fnDraw();
		//pageChange(user.oTable);
	}
};
//日程汇总重置
schedule.searchDiarySummaryReset=function(){
	var sysdate=schedule.getSysDate();
	$("#starttimeBegin").val(sysdate);
	$("#starttimeEnd").val(sysdate);
//	schedule.summarySearch(sysdate,sysdate);
};
//导出到Excel
schedule.exportExcel=function(start,end){
	var userid=$("#userid").val();
//	$("#starttimeBegin").val(start);
//	$("#starttimeEnd").val(end);
	var startDate=schedule.getSysDateBegin(start);//系统时间 00:00:00
	var endDate=schedule.getSysDateEnd(end);//系统时间 23:59:59
	
	var url = getRootPath()+"/po/diary/exportExcel.action?startDate="+startDate+"&endDate="+endDate+"&possessorId="+userid+"&time="+new Date();
	window.location.href=url;
};
/**查看领导批注*/
schedule.showAnno = function(id){
	var url=getRootPath()+"/po/diary/showSummaryAnnoDiv.action?id="+id;
	$("#summaryAnno").load(
			url,null,function(){schedule.showAnnoCallBack(id)});	
};

/**查看领导批注回调*/
schedule.showAnnoCallBack = function(dataid){
	var annoListJson=$("#annoListJson").val();
	var data=eval("("+annoListJson+")");
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
	$('#worklog-anno').modal('show');
};
/**-----------------------日志汇总end-------------------------------*/
/** ---------------------周期性提醒begin------------------------- */
var para_week = null;// 提醒设置
// 提醒设置
schedule.showRemind = function() {
	$(document).showRemind_({
		tableName : 'TTY_PO_DIARY',
		remind : $("#remind").val(),
		callback : function(r) {
			para_week = r;
			$("#remind").val(r);
		}
	});
};
//提醒设置(仅回显)
schedule.showRemindReadonly= function(){
	$(document).showRemind_({
		tableName:'TTY_PO_DIARY',
		readonly:true,
		remind:$("#tempRemind").val(),
		callback:function(r) {
			para_week = r;
			$("#detailRemindReadonly").val(r);
		}
	});
};
/** ---------------------周期性提醒end------------------------- */
schedule.subState = false;// 重复提交标识
/** -------------------列表页面begin----------------- */
schedule.refreshTreeForList=function(){
	if($("#treeDemo")[0]){
    	var content = $("#content").height();
        var headerHeight_1 = $('#header_1').height() || 0;
        var headerHeight_2 = $("#header_2").height() || 0;
        $("#LeftHeight_box_list").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight_box_list").height();
	    var day_height=$("#datePicker").find("div").height()+32;
	    $("#LeftHeight_list").height(content-80-headerHeight_1-headerHeight_2-day_height);
	    var lh_day = $("#LeftHeight_list").height(); 
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight_box_list").addClass("fixedNav");
	        $("#LeftHeight_list").height(lh - day_height + 113);
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
	        		$("#LeftHeight_list").height(lh_day + a)
	        //$("#LeftHeight").height()
	        $("#LeftHeight_box_list").removeClass("fixedNav");
	    }
    }
};
// 列表页面左侧日历点击回调方法
schedule.leftCallback = function(args) {
	if (typeof (args.pickerObject) == "object") {
		var pickerObject = args.pickerObject;
		var date = pickerObject.formatDate(pickerObject.getDate());
		var starttime = date + " 00:00:00";
		var endtime = date + " 23:59:59";
		$("#startDateTime").val(starttime);
		$("#endDateTime").val(endtime);
//		var userid=$("#userid").val();//在人员树选择的人员id
		schedule.getSelectDiary(starttime, endtime);// 列表页刷新选择日程下拉选
		schedule.detailTable();// 更新到详细界面
		schedule.clearDetailInfo();// 清空详细页各项内容
		schedule.clearModifyInfo();//清空修改页各项内容
		$('#comment').empty();// 清空批注信息
		$('#leaderIdeaTitle').show();// 显示领导批注字样
		schedule.initAnno(0);// 批注信息初始化
		var menuFlag=$("#diaryMenuFlag").val();//是哪个菜单 1日程安排 2下属日程 3共享查询 4领导日程
		if(menuFlag=="1"){//1日程安排
			$("#toModify").hide();
			$("#deleteDiary").hide();
			$("#calendarView").removeClass("btn");
			$("#calendarView").addClass("btn dark");
			$('#leaderIdeaTable').hide();//隐藏批注框
			var buttonSection="<a class='btn' href='#' id='diarySummary' data-toggle='modal'>日程汇总</a>";
			$("#buttonSection").append(buttonSection);
		}else if(menuFlag=="2"){//2下属日程
		}else if(menuFlag=="3"){//3共享查询
		}else if(menuFlag=="4"){//4领导日程
			$("#toModify").hide();
			$("#deleteDiary").hide();
			$("#calendarView").removeClass("btn");
			$("#calendarView").addClass("btn dark");
		}
	}
};
// 列表页刷新选择日程下拉选
schedule.getSelectDiary = function(starttime, endtime) {
	var userid = $("#userid").val();// 在人员树选择的人员id
	var menuFlag = $("#diaryMenuFlag").val();// 是哪个菜单 1日程安排 2下属日程 3共享查询 4领导日程
	var url = getRootPath() + "/po/diary/queryDiaryListPublic.action?time="
			+ new Date();
	if (menuFlag == "1") {// 1日程安排
	} else if (menuFlag == "2") {// 2下属日程
	} else if (menuFlag == "3") {// 3共享查询
	// url+="/po/diary/queryDiaryListPublic.action?possessorId="+userid+"&startDate="+starttime+"&endDate="+endtime+"&time="
	// + new Date();
	} else if (menuFlag == "4") {// 4领导日程
	}
	$.ajax({
		type : "POST",
		url : url,
		data : {
			"possessorId" : userid,
			"starttime" : starttime,
			"endtime" : endtime,
			"menuFlag" : menuFlag
		},
		dataType : "json",
		success : function(data) {
			var chooseDiary = "<select id='oneDayDiaryList' onchange='schedule.loadDiary()'>"
					+ "<option value=''>-请选择-</option>";
			if (data.length > 0) {
				var tmp = "";
				for (var i = 0; i < data.length; i++) {
					tmp += "<option value='" + data[i].id + "'>"
							+ data[i].title + "</option>";
				}
				chooseDiary += tmp;
			}
			chooseDiary += "</select>";
			$("#chooseDiary").html(chooseDiary);
		}
	});
};
// 清空详细页各项内容
schedule.clearDetailInfo = function() {
	$("#detailTitle").text("");
	$("#detailStartEndTime").text("");
	$("#detailPeriodTypeStartEndDate").text("");
	$("#detailShare").text("");
	$("#detailContent").text("");
	$("#detailRemind").text("");
	$("#detailStartTime").text("");
	$("#detailEndTime").text("");
};
//清空修改页各项内容
schedule.clearModifyInfo = function() {
	var menuFlag=$("#diaryMenuFlag").val();
	document.getElementById("delId").value = "";
	$("#id").val("");
//	$("#possessorId").val("");
	$("#title").val("");
	$("[name='content']").val("");
	if (menuFlag == "2") {// todo 在jquery.formfill.js问题没解决之前临时解决办法
		$("#leaderIdeaContent").val("");
	}
	$("#starttime").val("");
	$("#endtime").val("");
	$("#periodType").val("");//-------------------------------------------------------------------------
	$("#periodWay").val("");//-------------------------------------------------------------------------
	$("#periodStartdate").val("");
	$("#periodEnddate").val("");
//	$("#moduleFlag").val("");
//	$("#diaryType").val("");
	$("#isShare").val("");
	$("#remind").val("");
	$("#delId").val("");
	$("#modifyDate").val("");
	$("#millisecond").val("");
	$("#businessId").val("");
	$("#detailTitle").text("");
	$("#detailStartTime").text("");
	$("#detailEndTime").text("");
	$("#ptype").val("");
};
// 列表页下拉选择日程显示日程信息
schedule.loadDiary = function() {
	var menuFlag = $("#diaryMenuFlag").val();// 是哪个菜单 1日程安排 2下属日程 3共享查询 4领导日程
	$("#modifyFlag").val("");
	var id = $("#oneDayDiaryList").val();// 获取选中日程id select项
	var userid = $("#userid").val();
	schedule.detailTable();// 更新到详细界面
	schedule.clearDetailInfo();// 清空详细页各项内容
	schedule.getDetailData(id);// 列表页根据日程id查询并显示该日程详细信息
	if (id == null || id == "") {// 点击请选择的时候清空delId
		document.getElementById("delId").value = "";
		$("#toModify").hide();
		$("#deleteDiary").hide();
		$("#calendarView").removeClass("btn");
		$("#calendarView").addClass("btn dark");
	}
	$('#comment').empty();// 清空批注信息
	$('#leaderIdeaTitle').show();// 显示领导批注字样
	
	if (id != null && id != "") {
		if(menuFlag=="1" || menuFlag=="2"){
			$('#leaderIdeaTable').show();//显示批注框
		}
		schedule.initAnno(id);// 批注信息初始化
	}else{
		if(menuFlag=="1" || menuFlag=="2"){
			$('#leaderIdeaTable').hide();//隐藏批注框
		}
		schedule.initAnno(0);// 批注信息初始化
	}
	if(menuFlag=="1"){
		var buttonSection="<a class='btn' href='#' id='diarySummary' data-toggle='modal'>日程汇总</a>";
		$("#buttonSection").append(buttonSection);
	}
	schedule.getToken();
//	schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
//	schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
};
// 日程新建
schedule.toNewDiary = function() {
	var userid=$("#userid").val();
	var loginUser=$("#loginUser").val();
	var menuFlag = $("#diaryMenuFlag").val();
	if(menuFlag=="4"){
		if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
			$("#newOrModify").val("0");//标识为新建
			
			schedule.getToken();
			$("#oneDayDiaryList").val("");
			$("#id").val("0");
			$("#modifyDate").val("");
			$("#millisecond").val("");
			$("#businessId").val("");
			schedule.modifyTable();
			$("#savaOrModify").hide();
			$("#diarySummary").hide();
			$("#sava").removeClass("btn");
			$("#sava").addClass("btn dark");
			if (menuFlag == "1") {
				diaryMyList.clearForm();
			} else if (menuFlag == "4") {
				diaryLeadList.clearForm();
			}
			schedule.clearPeriodContent();//高级设置清空
			if ($("#id").val() == "0") {
				var starttime = $("#startDateTime").val();
				var endtime = $("#endDateTime").val();
				var sysdate = schedule.getSysDate();
				if (starttime != null && endtime != null && starttime != ""
						&& endtime != null) {
					
					$("#starttime").val(starttime);
					$("#endtime").val(endtime);
				} else {
					var start = schedule.getSysDateBegin(sysdate);
					var end = schedule.getSysDateEnd(sysdate);
					$("#starttime").val(start);
					$("#endtime").val(end);
				}
			}
			$('#comment').empty();// 清空批注信息
			$('#leaderIdeaTitle').hide();// 隐藏领导批注字样
			if (menuFlag == "1") {
				$('#leaderIdeaTable').hide();//隐藏批注框
			}
//			var periodStr = "<a class='a-icon i-new m-l' href='#' role='button' data-toggle='modal' id='advancedSettings'>" +
////					"<i class='fa fa-cog'></i>" +
//					"高级设置</a>";
//			$("#detailPeriodTypeStartEndDate").html(periodStr);
			schedule.clearPeriodContent2();//清空高级设置
			schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
//			heightOfTheLeftTree();//重新调整左侧部分高度
			var menuFlag = $("#diaryMenuFlag").val();// 是哪个菜单 1日程安排 2下属日程 3共享查询 4领导日程
			if (menuFlag == "1") {// 1日程安排
				schedule.refreshTreeForList();
//				var buttonSection="<a class='btn' href='#' id='diarySummary' data-toggle='modal'>日程汇总</a>";
//				$("#buttonSection").append(buttonSection);
			} else if (menuFlag == "2") {// 2下属日程
			} else if (menuFlag == "3") {// 3共享查询
			// url+="/po/diary/queryDiaryListPublic.action?possessorId="+userid+"&startDate="+starttime+"&endDate="+endtime+"&time="
			// + new Date();
			} else if (menuFlag == "4") {// 4领导日程
				queryForTree.refreshTreeForList();
			}
			schedule.refreshTreeForList();
		}else{
			msgBox.info({
				type : "fail",
				content : $.i18n.prop("JC_OA_PO_057"),
				callback : function() {
					loadrightmenu('/po/diary/diaryLeadList.action?possessorId='+$("#userid").val()+"&time=" + new Date());
				}
			});
		}
	}
	if(menuFlag=="1"){
		$("#newOrModify").val("0");//标识为新建
		schedule.getToken();
		$("#oneDayDiaryList").val("");
		$("#id").val("0");
		$("#modifyDate").val("");
		$("#millisecond").val("");
		$("#businessId").val("");
		schedule.modifyTable();
		$("#savaOrModify").hide();
		$("#diarySummary").hide();
		$("#sava").removeClass("btn");
		$("#sava").addClass("btn dark");
		if (menuFlag == "1") {
			diaryMyList.clearForm();
		} else if (menuFlag == "4") {
			diaryLeadList.clearForm();
		}
		schedule.clearPeriodContent();//高级设置清空
		if ($("#id").val() == "0") {
			var starttime = $("#startDateTime").val();
			var endtime = $("#endDateTime").val();
			var sysdate = schedule.getSysDate();
			if (starttime != null && endtime != null && starttime != ""
					&& endtime != null) {
				
				$("#starttime").val(starttime);
				$("#endtime").val(endtime);
			} else {
				var start = schedule.getSysDateBegin(sysdate);
				var end = schedule.getSysDateEnd(sysdate);
				$("#starttime").val(start);
				$("#endtime").val(end);
			}
		}
		$('#comment').empty();// 清空批注信息
		$('#leaderIdeaTitle').hide();// 隐藏领导批注字样
		if (menuFlag == "1") {
			$('#leaderIdeaTable').hide();//隐藏批注框
		}
//			var periodStr = "<a class='a-icon i-new m-l' href='#' role='button' data-toggle='modal' id='advancedSettings'>" +
////					"<i class='fa fa-cog'></i>" +
//					"高级设置</a>";
//			$("#detailPeriodTypeStartEndDate").html(periodStr);
		schedule.clearPeriodContent2();//清空高级设置
		schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
//			heightOfTheLeftTree();//重新调整左侧部分高度
//		var menuFlag = $("#diaryMenuFlag").val();// 是哪个菜单 1日程安排 2下属日程 3共享查询 4领导日程
//		if (menuFlag == "1") {// 1日程安排
//			schedule.refreshTreeForList();
//				var buttonSection="<a class='btn' href='#' id='diarySummary' data-toggle='modal'>日程汇总</a>";
//				$("#buttonSection").append(buttonSection);
//		} 
//		else if (menuFlag == "2") {// 2下属日程
//		} else if (menuFlag == "3") {// 3共享查询
		// url+="/po/diary/queryDiaryListPublic.action?possessorId="+userid+"&startDate="+starttime+"&endDate="+endtime+"&time="
		// + new Date();
//		} else if (menuFlag == "4") {// 4领导日程
//			queryForTree.refreshTreeForList();
//		}
		schedule.refreshTreeForList();
	}
};
schedule.refreshTreeForList=function(){
	var head1 = $("#diaryContentOne").height(),
		head2 = $('#diaryContentTwo').height(),
		temp = head1+head2-155;
	$("#LeftHeight_list").height(temp>305?temp:305);
};
// 列表页最右侧数据区刷新
schedule.refreshData = function() {
	var starttime = $("#startDateTime").val();
	var endtime = $("#endDateTime").val();
	schedule.getSelectDiary(starttime, endtime);// 列表页刷新选择日程下拉选
	schedule.clearDetailInfo();// 清空详细页各项内容
};
// 随操作更新按键 详细页面
schedule.changeButtonSectionToDetail = function() {
	var menuFlag = $("#diaryMenuFlag").val();// 是哪个菜单 1日程安排 2下属日程 3共享查询 4领导日程
	var buttonSection = "<a class='btn dark' id='toModify'>编 辑</a>"
			+ "<a class='btn' id='deleteDiary'>删 除</a>";
	buttonSection += "<a href='#' class='btn' id='calendarView'>日历形式展示</a>";
	$("#buttonSection").html(buttonSection);
	$("#toModify").click(function() {
		schedule.toModifyType();
	});
	$("#deleteDiary").click("click", function() {
		schedule.deleteDiary($("#delId").val());
	});
	if (menuFlag == "1") {
		$("#calendarView").click(function() {
			diaryMyList.toCalendarView()
		});
	} else if (menuFlag == "4") {
		$("#calendarView").click(function() {
			diaryLeadList.toCalendarView()
		});
	}

};
// 随操作更新按键 修改页面
schedule.changeButtonSectionToModify = function() {
	var menuFlag = $("#diaryMenuFlag").val();
	var buttonSection = "<a class='btn dark' id='savaOrModify'>保存继续</a>"
			+ "<a class='btn' id='sava'>保 存</a>" +
			// "<a class='btn' onclick='schedule.toNewDiary();'>重 置</a>" +
			// "<a class='btn' href='#Reading' data-toggle='modal'>阅读情况</a>" +
			// "<a class='btn' href='#Program-summary'
			// data-toggle='modal'>日程汇总</a>" +
			"<a href='#' class='btn' id='calendarView'>日历形式展示</a>";
	$("#buttonSection").html(buttonSection);
	$("#savaOrModify").click(function() {
		schedule.savaOrModify(false);
//		schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
	});
	$("#sava").click(function() {
		schedule.savaOrModify(true);
//		schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
	});
	if (menuFlag == "1") {
		$("#calendarView").click(function() {
			diaryMyList.toCalendarView()
		});
	} else if (menuFlag == "4") {
		$("#calendarView").click(function() {
			diaryLeadList.toCalendarView()
		});
	}
	if(menuFlag=="1"){
		buttonSection="<a class='btn' href='#' id='diarySummary' data-toggle='modal'>日程汇总</a>";
		$("#buttonSection").append(buttonSection);
	}
};
// 更新到修改界面
schedule.modifyTable = function() {
	var menuFlag = $("#diaryMenuFlag").val();
	var tableContent = "<tr>"
			+ "<td><span class='required'>*</span>日程标题</td>"
			+ "<td colspan='3'><input type='text' id='title' name='title'/></td>"
			+ "</tr><tr>"
			+ "<td style='width:15%;'><span class='required'>*</span>开始时间</td>"
			+ "<td style='width:35%;'>"
			+ "<div class='input-group w-p100'>"
			+ "<input class='datepicker-input' type='text' id='starttime' name='starttime' "
			+ "data-date-format='yyyy-MM-dd HH:mm:ss' data-pick-time='true' data-ref-obj='#endtime lt'>"
			+ "</div></td>" 
			+ "<td style='width:15%;'><span class='required'>*</span>结束时间</td>"
			+ "<td>"
			+ "<div class='input-group w-p100'>"
			+ "<input class='datepicker-input' type='text' id='endtime' name='endtime' "
			+ "data-date-format='yyyy-MM-dd HH:mm:ss' data-pick-time='true' data-ref-obj='#starttime gt'>"
			+ "</div></td>" 
			+ "</tr>"
			+ "<tr><td>周期模式</td>"
			+ "<td><a class='a-icon i-new m-r-xs' id='advancedSettings'"
			+ "href='#' role='button' data-toggle='modal'>"
//			+ "<i class='fa fa-cog'></i>" 
			+ "高级设置</a></td>"
			+ "<td>提醒设置</td><td>"
			+ "<input type='hidden' id='remind' name='remind'/>"
			+ "<a id='remindSet' name='remindSet' class='a-icon i-new m-r-xs' href='#' role='button' data-toggle='modal'>"
//			+ "<i class='fa fa-alarm'></i>" 
			+ "提醒设置</a></td></tr>"
			+ "<tr><td><span class='required'>*</span>内容</td>"
			+ "<td colspan=3><textarea id='content' name='content' wrap='hard'></textarea></td></tr>";
	if (menuFlag == "1") {
		tableContent += "<tr><td>共享范围</td>"
				+ "<td colspan='3'>"
				+ "<input id='shareScopeId' name='shareScopeId' type='hidden'/>"
				+ "<div id='shareScope'></div></td></tr>";
	}
	$('#Schedule-detail').html(tableContent);
	$(".datepicker-input").datepicker();// 日历控件重新初始化
	$("#advancedSettings").click(function() {
		schedule.setPeriodDate();
		if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
			schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
			schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
		}
	});
	$("#remindSet").click(function() {
		schedule.showRemind();
	});
	/** 提醒按钮事件绑定 */
	schedule.changeButtonSectionToModify();
	selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true,
			true);// 人员选择树 参数divid 设置的id-name 多选 人员
	
	if (menuFlag == "1") {// 1日程安排
		schedule.refreshTreeForList();
	} else if (menuFlag == "2") {// 2下属日程
	} else if (menuFlag == "3") {// 3共享查询
	// url+="/po/diary/queryDiaryListPublic.action?possessorId="+userid+"&startDate="+starttime+"&endDate="+endtime+"&time="
	// + new Date();
	} else if (menuFlag == "4") {// 4领导日程
		queryForTree.refreshTreeForList();
	}
	ie8StylePatch();
	iePlaceholderPatch();
};
// 更新到详细界面
schedule.detailTable = function() {
	var menuFlag = $("#diaryMenuFlag").val();
	var tableContent = "<tr>" + "<td>日程标题</td>"
			+ "<td colspan='3' id='detailTitle' style='word-break: break-all;word-wrap: break-word;'></td>" + "</tr><tr>"
			+ "<td style='width:15%;'>开始时间</td>"
			+ "<td id='detailStartTime' style='width:35%;'></td>" 
			+ "<td style='width:15%;'>结束时间</td>"
			+ "<td id='detailEndTime'></td>"
			+ "</tr><tr>"
			+ "<td>周期模式</td>" + "<td colspan='3' id='detailPeriodTypeStartEndDate'></td>"
			+ "</tr><tr>"
			+ "<td>提醒设置</td>"+"<td colspan='3' id='detailRemind'></td>"
			+ "</tr>";
	if (menuFlag == "1") {
		tableContent += "<tr><td>共享范围</td>" + "<td colspan='3' id='detailShare' style='word-break: break-all;word-wrap: break-word;'></td></tr>";
	}
	tableContent += "<tr><td>日程内容</td>" + "<td colspan='3' id='detailContent' style='word-break: break-all;word-wrap: break-word;'></td>"
			+ "</tr>";
	// "<tr>" +
	// "<td>提醒方式</td>" +
	// "<td id='detailRemind'></td>" +
	// "</tr>";
	$('#Schedule-detail').html(tableContent);
	var menuFlag = $("#diaryMenuFlag").val();
	if (menuFlag == "1") {// 日程安排
		schedule.changeButtonSectionToDetail();
	} else if (menuFlag == "4") {// 领导日程
//		var mandatorId = $("#mandatorId").val();
		var userid = $("#userid").val();
		var loginUser = $("#loginUser").val();
//		if (userid == mandatorId && userid != 0) {// 当前登陆人员是被委托人 单委托
		if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
			schedule.changeButtonSectionToDetail();
		}
	}
//	//详细页查看提醒设置
//	$('#detailRemind').on('click','a[name="detailRemindSet"]',function(){
//		schedule.showRemindReadonly();
//	});
	ie8StylePatch();
	iePlaceholderPatch();
};
// 将修改周期性日程置为不可用的控件全部恢复可用
schedule.enabledInput = function() {
	document.getElementById("periodStartdate").disabled = false;
	document.getElementById("periodEnddate").disabled = false;
	document.getElementById("starttime").disabled = false;
	document.getElementById("endtime").disabled = false;
	var periodTypeTmp = document.getElementsByName("periodTypeTmp");
	for (var i = 0; i < periodTypeTmp.length; i++) {
		periodTypeTmp[i].disabled = false;
	}
	var periodWayWeek = document.getElementsByName("periodWayWeek");
	for (var i = 0; i < periodWayWeek.length; i++) {
		periodWayWeek[i].disabled = false;
	}
	$("#periodWayDay").removeAttr("disabled");
	$("#periodWayMonth").removeAttr("disabled");
};

/** --------------------列表页面end------------------- */
/** ----------------日历&列表页面共用begin--------------- */
//高级设置点击x
schedule.closeX=function(){
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
};
/** 初始化左侧的树的div宽高 */
schedule.initLeftTreeDiv = function() {
	var content = $("#content").height();
	var headerHeight_1 = $('#header_1').height() || 0;
	var headerHeight_2 = $("#header_2").height() || 0;
	
	    $(".tree-right").css("padding-left","215px");
	//定义高度
		$("#LeftHeight_box_rl").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight_box_rl").height();
	    var day_height=$("#datePicker").find("div").height()+32;
	    $("#LeftHeight_rl").height(content-80-headerHeight_1-headerHeight_2-day_height);
	    var lh_day = $("#LeftHeight_rl").height();

	    
	    
	  $("#scrollable").scroll(function() {
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight_box_rl").addClass("fixedNav");
	        $("#LeftHeight_rl").height(lh - day_height + 113);
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
	        		$("#LeftHeight_rl").height(lh_day + a)
	        //$("#LeftHeight").height()
	        $("#LeftHeight_box_rl").removeClass("fixedNav");
	    } 
	
	  });
};

// 获取当前系统时间 返回yyyy-MM-dd
schedule.getSysDate = function() {
	var today = new Date();
	var year = today.getFullYear(); // 年
	var month = today.getMonth() + 1; // 月
	var m = "";
	if (month < 10) {
		m = "0" + month;
	} else {
		m = month;
	}
	var day = today.getDate(); // 日
	var d = "";
	if (day < 10) {
		d = "0" + day;
	} else {
		d = day;
	}
	var str = year + "-" + m + "-" + d;
	return str;
};
// 获取一天开始时间 传入date=yyyy-MM-dd 返回yyyy-MM-dd 00:00:00
schedule.getSysDateBegin = function(date) {
	return date + " 00:00:00";
};
// 获取一天结束时间 传入date=yyyy-MM-dd 返回yyyy-MM-dd 23:59:59
schedule.getSysDateEnd = function(date) {
	return date + " 23:59:59";
};
// 根据日程id查询并显示该日程详细信息 用于详细信息和修改返显
schedule.getDetailData = function(id) {
	var menuFlag = $("#diaryMenuFlag").val();// 1日程安排 2下属日程 3共享查询 4领导日程
	var pageFlag = $("#diaryPageFlag").val();
	var modifyFlag = $("#modifyFlag").val();
	if (id == null || id == "") {
		if(pageFlag == "1"){
			$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
		}
		if(pageFlag == "2"){
			schedule.refreshData();// 列表页最右侧数据区刷新
		}
		return false;
	}
	if (menuFlag == "1" || menuFlag == "4") {
		$('#shareScopeId').val("");
		selectControl.clearValue("shareScopeChoose-shareScopeChoose");
	}
	$.ajax({
		type : "GET",
		url : getRootPath() + "/po/diary/get.action?time=" + new Date(),
		data : {
			"id" : id
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data) {// 当data=null的时候不执行
				hideErrorMessage();// 清除验证信息
				// $("#diaryForm").fill(data);//todo jquery.formfill.js
				// 从后端调取json格式的数据动态填充表单 todo=ie下有问题 暂时屏蔽
				schedule.getPeriodWay(data.periodType, data.periodWay);
				if(modifyFlag=="0"&&pageFlag == "2" ){
					var periodWayWeek = document.getElementsByName("periodWayWeek");
					for (var i = 0; i < periodWayWeek.length; i++) {
						periodWayWeek[i].disabled = true;
					}
				}
				$("#leaderIdeaContent").val("");
				$("#id").val(data.id);
				$("#possessorId").val(data.possessorId);
				$("#title").val(data.title);
				// $("#content").val(data.content);
				var modifyContent = data.content.replace(/<br\/>/g, "\r\n");//用于处理计划导入日程的换行问题
				modifyContent = modifyContent.replace(/<BR\/>/g, "\r\n");//用于处理计划导入日程的换行问题
				modifyContent = modifyContent.replace(/<BR>/g, "\r\n");//用于处理计划导入日程的换行问题
				modifyContent = modifyContent.replace(/<br>/g, "\r\n");//用于处理计划导入日程的换行问题
				$("[name='content']").val(modifyContent);
				if (menuFlag == "2") {// todo 在jquery.formfill.js问题没解决之前临时解决办法
					$("#leaderIdeaContent").val("");
				}
				$("#starttime").val(data.starttime);
				$("#endtime").val(data.endtime);
				$("#periodType").val(data.periodType);
				$("#periodWay").val(data.periodWay);
				var periodStartdate=data.periodStartdate!=null&&data.periodStartdate!=""?data.periodStartdate.substring(0,10):"";
				var periodEnddate=data.periodEnddate!=null&&data.periodEnddate!=""?data.periodEnddate.substring(0,10):"";
				$("#periodStartdate").val(periodStartdate);
				$("#periodEnddate").val(periodEnddate);
				$("#moduleFlag").val(data.moduleFlag);
				$("#diaryType").val(data.diaryType);
				$("#isShare").val(data.isShare);
				$("#remind").val(data.remind);
				$("#tempRemind").val(data.remind);
				$("#delId").val(data.id);
				$("#modifyDate").val(data.modifyDate);
				$("#millisecond").val(data.millisecond);
				$("#businessId").val(data.businessId);
				//-----------------------------------------------------------用于处理修改时层回显等由于恶心的弹出层导致的恶心问题
				$("#tempPeriodStartdate").val($("#periodStartdate").val());
				$("#tempPeriodEnddate").val($("#periodEnddate").val());
				var periodTypeTmp = document.getElementsByName("periodTypeTmp");
				for (var i = 0; i < periodTypeTmp.length; i++) {
					if(periodTypeTmp[i].checked == true){
						$("#tempPeriodTypeTmp").val(periodTypeTmp[i].value);
					}
				}
				schedule.setPeriodWay();// periodWay内容的拼接
				$("#tempPeriodWay").val($("#periodWay").val());
//				$("#tempPeriodWayForModify").val($("#periodWay").val());//仅在此次赋值一次
				//-----------------------------------------------------------
				
				$("#detailTitle").text(data.title);
				$("#detailStartTime").text(data.starttime);
				$("#detailEndTime").text(data.endtime);
				$("#ptype").val(data.periodType);
				var period = "";
				var periodStart="";
				var periodEnd="";
				if(data.periodStartdate!=null&&data.periodStartdate!=""){
					periodStart=data.periodStartdate.substring(0,10);
				}
				if(data.periodEnddate!=null&&data.periodEnddate!=""){
					periodEnd=data.periodEnddate.substring(0,10);
				}
				switch (data.periodType) {
				case "0":
					period = "无定期";
					break;
				case "1":
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每一天";
					break;
				case "2":
					var str = data.periodWay.split(":")[1].split(",");
					var s = "";
					var weeks = "";
					for (var i = 0; i < str.length; i++) {
						switch (str[i]) {
						case "1":
							s = "周日";
							break;
						case "2":
							s = "周一";
							break;
						case "3":
							s = "周二";
							break;
						case "4":
							s = "周三";
							break;
						case "5":
							s = "周四";
							break;
						case "6":
							s = "周五";
							break;
						case "7":
							s = "周六";
							break;
						default:
							break;
						}
						weeks += s + ",";
					}
					weeks = weeks.substring(0, weeks.length - 1);
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每周" + weeks;
					break;
				case "3":
					var str = data.periodWay.split(":")[1];
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每月" + str + "日";
					break;
				case "4":
					var month = data.periodWay.split(":")[1];
					var m = parseInt(month) ;
					var day = data.periodWay.split(":")[2];
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每年" + m + "月" + day + "日";
					break;
				default:
					break;
				}
				$("#detailPeriodTypeStartEndDate").html(period);
				$("#detailShare").text(data.shareScopeIdValue);// 共享范围
				var content = data.content.replace(/\r\n/g, "<br/>");
				$("#detailContent").html(content);
				var detailRemind = "<input type='hidden' id='detailRemindReadonly' name='detailRemindReadonly'/>"
				+ "<a id='detailRemindSet' name='detailRemindSet' class='a-icon i-new m-r-xs' href='#' role='button' data-toggle='modal'>"
				+ "提醒设置</a>";
				$("#detailRemind").html(detailRemind);//提醒
				$("#savaOrModify").hide();
				if (pageFlag == "1") {
					$('#Schedule-detail').modal('show');
				}
				if (menuFlag == "1" || menuFlag == "4") {// 1日程安排 2下属日程 3共享查询
															// 4领导日程
					var shareIds = data.shareScopeId.split(",");
					var shareValues = data.shareScopeIdValue.split(",");
					if ((shareIds != null && shareIds != "") && (shareValues != null && shareValues != "")) {
						var data = [];
						for (var i = 0; i < shareIds.length; i++) {
							data.push({
								id : shareIds[i],
								text : shareValues[i]
							})
						}
						selectControlFlag=false;
//						console.log(data)//--------------------------------------------------------------------
						selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true, null, data);// 人员选择树 参数divid 设置的id-name 多选 人员
					}else{
						if(pageFlag=="1"){//为了应对列表页修改空人员选择树样式变形
							selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true);// 人员选择树 参数divid 设置的id-name 多选 人员
						}
						if(pageFlag=="2"){//为了应对列表页修改空人员选择树样式变形
							selectControlFlag=true;
						}
					}
				}
				//详细页查看提醒设置
				$('#detailRemind').on('click','a[name="detailRemindSet"]',function(){
					schedule.showRemindReadonly();
				});

				// 更新阅读情况
				$.ajax({
					type : "GET",
					url : getRootPath() + "/po/diary/savaReadingStatus.action",
					data : {
						'id' : data.id,
						'createUser' : data.createUser
					},
					dataType : "json",
					success : function(data) {
					}
				});
			}
		},error:function(data){
			msgBox.info({
				type : "fail",
				content : $.i18n.prop("JC_OA_PO_039"),
				callback : function() {
					if(pageFlag == "1"){
						$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
					}
					if(pageFlag == "2"){
						schedule.detailTable();// 更新到详细界面
						schedule.refreshData();// 列表页最右侧数据区刷新
					}
				}
			});
		}
	});
};
// 点击高级设置时给周期时间赋值
schedule.setPeriodDate = function() {
	if ($("#id").val == "0") {
		$("#periodStartdate").val($("#starttime").val().substring(0, 10));
		$("#periodEnddate").val($("#endtime").val().substring(0, 10));
	}
	if($("#periodTr").text()!=""){//给标识赋值，用于判断进入修改页时高级设置是否值
		$("#modifyExistValue").val("1");//修改有值
	}else{
		$("#modifyExistValue").val("0");//修改没有值
	}
	$('#Advanced-settings').modal('show');
};
//周期性类型选择切换控制周期开始结束时间可选
schedule.periodTypeAssignForDate = function() {
	if($('input:radio[name="periodTypeTmp"]:checked').attr("id")=="periodType0"){//无定期
		$("#periodStartdate").attr("disabled","disabled");
		$("#periodEnddate").attr("disabled","disabled");
	}else{
		$("#periodStartdate").removeAttr("disabled");
		$("#periodEnddate").removeAttr("disabled");
	}
};
// 周期性类型选择切换
schedule.periodTypeAssign = function() {// 0-无定期;1-按天;2-按周;3-按月;4-按年
	switch ($('input:radio[name="periodTypeTmp"]:checked').attr("id")) {
	case "periodType0":
		$("#periodType").val("0");
		$("#periodWay").val("0");
		$("#periodTr").html("");
		$("#periodTr").hide();
		break;
	case "periodType1":
		$("#periodType").val("1");
		$("#periodWay").val("1");
		var periodContent = "<td><span class='required'>*</span>周期</td><td>每一天</td>";
		$("#periodTr").html(periodContent);
		$("#periodTr").show();
		break;
	case "periodType2":
		$("#periodType").val("2");
		$("#periodWay").val("2:");
		var periodContent = "<td><span class='required'>*</span>周期</td><td>"
				+ "<label for='periodWayWeek0' class='checkbox inline'><input type='checkbox' id='periodWayWeek0' name='periodWayWeek' value='1'/>星期日</label>"
				+ "<label for='periodWayWeek1' class='checkbox inline'><input type='checkbox' id='periodWayWeek1' name='periodWayWeek' value='2'/>星期一</label>"
				+ "<label for='periodWayWeek2' class='checkbox inline'><input type='checkbox' id='periodWayWeek2' name='periodWayWeek' value='3'/>星期二</label>"
				+ "<label for='periodWayWeek3' class='checkbox inline'><input type='checkbox' id='periodWayWeek3' name='periodWayWeek' value='4'/>星期三</label>"
				+ "<label for='periodWayWeek4' class='checkbox inline'><input type='checkbox' id='periodWayWeek4' name='periodWayWeek' value='5'/>星期四</label>"
				+ "<label for='periodWayWeek5' class='checkbox inline'><input type='checkbox' id='periodWayWeek5' name='periodWayWeek' value='6'/>星期五</label>"
				+ "<label for='periodWayWeek6' class='checkbox inline'><input type='checkbox' id='periodWayWeek6' name='periodWayWeek' value='7'/>星期六</label>"
				+ "</td>";
		$("#periodTr").html(periodContent);
		$("#periodTr").show();
		break;
	case "periodType3":
		$("#periodType").val("3");
		$("#periodWay").val("3:");
		var options = "";
		for (var i = 1; i <= 31; i++) {
			options += "<option value=" + i + ">" + i + "</option>"
		}
		var periodContent = "<td><span class='required'>*</span>周期</td><td>每月第&nbsp;&nbsp;"
				+ "<select id='periodWayDay' name='periodWayDay' style='width:65px'>"
				+ options + "</select>" + "&nbsp;&nbsp;天</td>";
		$("#periodTr").html(periodContent);
		$("#periodTr").show();
		break;
	case "periodType4":
		$("#periodType").val("4");
		$("#periodWay").val("4:");
		var optionsDay = "";
		var optionsMonth = ""
		for (var i = 1; i <= 31; i++) {
			optionsDay += "<option value=" + i + ">" + i + "</option>"
		}
		for (var i = 1; i <= 12; i++) {
			optionsMonth += "<option value=" + i + ">" + i + "</option>"
		}
		var periodContent = "<td><span class='required'>*</span>周期</td><td>每年第&nbsp;&nbsp;"
				+ "<select id='periodWayMonth' name='periodWayMonth' style='width:65px' onchange='schedule.monthDay($(this).val())'>"
				+ optionsMonth
				+ "</select>&nbsp;&nbsp;月的第&nbsp;&nbsp;"
				+ "<select id='periodWayDay' name='periodWayDay' style='width:65px'>"
				+ optionsDay + "</select>&nbsp;&nbsp;天</td>";
		$("#periodTr").html(periodContent);
		$("#periodTr").show();
		break;
	default:
		break;
	}
	schedule.periodTypeAssignForDate();
	ie8StylePatch();
	iePlaceholderPatch();
};
// 周期性年选月日的处理
schedule.monthDay = function(value) {
	var optionsDay = "";
	var periodStartdate = $("#periodStartdate").val();
//	if (periodStartdate == null || periodStartdate == "") {
//		// alertx("请选择周期开始及结束时间");
//		msgBox.info({
//			type : "fail",
//			content : "请选择周期开始及结束时间",
//		});
//		$("#periodWayMonth option[value='0']").attr("selected", true);
//		for (var i = 1; i <= 29; i++) {
//			optionsDay += "<option value=" + i + ">" + i + "</option>"
//		}
//		return optionsDay;
//	}
	var year = periodStartdate.substring(0, 4);
	if (value == '2') {
		if ((year % 4 == 0 && !(year % 100 == 0)) || year % 400 == 0) { // 闰年
			for (var i = 1; i <= 29; i++) {
				optionsDay += "<option value=" + i + ">" + i + "</option>"
			}
		} else {// 平年
			for (var i = 1; i <= 28; i++) {
				optionsDay += "<option value=" + i + ">" + i + "</option>"
			}
		}
	} else if (value == '1' || value == '3' || value == '5' || value == '7'
			|| value == '8' || value == '10' || value == '12') {// 31天的月份
		for (var i = 1; i <= 31; i++) {
			optionsDay += "<option value=" + i + ">" + i + "</option>"
		}
	} else {// 30天的月份
		for (var i = 1; i <= 30; i++) {
			optionsDay += "<option value=" + i + ">" + i + "</option>"
		}
	}
	var optionsMonth = ""
	for (var i = 1; i <= 12; i++) {
		optionsMonth += "<option value=" + i + ">" + i + "</option>"
	}
	var periodContent = "<td><span class='required'>*</span>周期</td><td>每年第&nbsp;&nbsp;"
			+ "<select id='periodWayMonth' name='periodWayMonth' style='width:65px' onchange='schedule.monthDay($(this).val())'>"
			+ optionsMonth
			+ "</select>&nbsp;&nbsp;月的第&nbsp;&nbsp;"
			+ "<select id='periodWayDay' name='periodWayDay' style='width:65px'>"
			+ optionsDay + "</select>&nbsp;&nbsp;天</td>";
	$("#periodTr").html(periodContent);
	$("#periodWayMonth option[value='" + value + "']").attr("selected", true);
	$("#periodTr").show();
	ie8StylePatch();
	iePlaceholderPatch();
};
// 高级设置确定 包含周期选择的验证
schedule.periodConfirm = function() {
	if ($("#periodType").val() != '0') {
		if ($("#periodStartdate").val() == null
				|| $("#periodStartdate").val() == "") {
			// alertx("请选择周期开始时间");
			msgBox.info({
				type : "fail",
				content : "请选择周期开始时间",
			});
			return false;
		} else if ($("#periodEnddate").val() == null
				|| $("#periodEnddate").val() == "") {
			// alertx("请选择周期结束时间");
			msgBox.info({
				type : "fail",
				content : "请选择周期结束时间",
			});
			return false;
		}
		if ($("#periodType").val() == '2') {
			var tmp = "";
			var week = document.getElementsByName("periodWayWeek");
			for (var i = 0; i < week.length; i++) {
				if (week[i].checked == true) {
					tmp += week[i].value;
				}
			}
			if (tmp == null || tmp == "") {
				// alertx("请选择周期");
				msgBox.info({
					type : "fail",
					content : "请选择周期",
				});
				return false;
			}
		}
	}
	if($("#periodTr").text()!=""){//给标识赋值，用于判断是否点击过高级设置确定按键
		$("#newOk").val("1");//点击过
	}else{
		$("#newOk").val("0");//没点过
	}
	if($("#periodTr").text()!=""){//给标识赋值，用于判断是否点击过高级设置确定按键
		$("#modifyExistValue").val("1");//点击过
	}else{
		$("#modifyExistValue").val("0");//没点过
	}
	$('#Advanced-settings').modal('hide');
};
// 高级设置取消
schedule.periodCancel = function() {
	$('#Advanced-settings').modal('hide');
};
//高级设置清空
schedule.clearPeriodContent=function(){
	$("#periodStartdate").val("");
	$("#periodEnddate").val("");
	var periodTypeTmp = document.getElementsByName("periodTypeTmp");
	for (var i = 0; i < periodTypeTmp.length; i++) {
		if(periodTypeTmp[i].value=="0"){
			periodTypeTmp[i].checked = true;
		}else{
			periodTypeTmp[i].checked = false;
		}
	}
	document.getElementById("periodStartdate").disabled=false;
	document.getElementById("periodEnddate").disabled=false;
	document.getElementById("starttime").disabled=false;
	document.getElementById("endtime").disabled=false;
	var periodTypeTmp=document.getElementsByName("periodTypeTmp");
	for(var i=0;i<periodTypeTmp.length;i++){
		periodTypeTmp[i].disabled=false;
	}
	$("#periodTr").html("");
	$("#periodType").val("0");
	$("#periodWay").val("0");
//	var periodWayWeek = document.getElementsByName("periodWayWeek");
//	for (var i = 0; i < periodWayWeek.length; i++) {
//		periodWayWeek[i].checked = true;
//	}
};
//高级设置清空 仅清空内容
schedule.clearPeriodContent2=function(){
	$("#periodStartdate").val("");
	$("#periodEnddate").val("");
	var periodTypeTmp = document.getElementsByName("periodTypeTmp");
	for (var i = 0; i < periodTypeTmp.length; i++) {
		if(periodTypeTmp[i].value=="0"){
			periodTypeTmp[i].checked = true;
		}else{
			periodTypeTmp[i].checked = false;
		}
	}
	$("#periodTr").html("");
	$("#periodType").val("0");
	$("#periodWay").val("0");
//	var periodWayWeek = document.getElementsByName("periodWayWeek");
//	for (var i = 0; i < periodWayWeek.length; i++) {
//		periodWayWeek[i].checked = true;
//	}
};
//临时存储高级设置层中的内容
schedule.periodContentTemp=function(){
	$("#tempPeriodStartdate").val($("#periodStartdate").val());
	$("#tempPeriodEnddate").val($("#periodEnddate").val());
	var periodTypeTmp = document.getElementsByName("periodTypeTmp");
	for (var i = 0; i < periodTypeTmp.length; i++) {
		if(periodTypeTmp[i].checked == true){
			$("#tempPeriodTypeTmp").val(periodTypeTmp[i].value);
		}
	}
	schedule.setPeriodWay();// periodWay内容的拼接
	$("#tempPeriodWay").val($("#periodWay").val());
};
//从临时存储的内容中给高级设置赋值
schedule.assignPeriodContent=function(){
	$("#periodStartdate").val($("#tempPeriodStartdate").val());
	$("#periodEnddate").val($("#tempPeriodEnddate").val());
	document.getElementById("periodStartdate").disabled=false;
	document.getElementById("periodEnddate").disabled=false;
	var periodTypeTmp = document.getElementsByName("periodTypeTmp");
	for (var i = 0; i < periodTypeTmp.length; i++) {
		if(periodTypeTmp[i].id==$("#tempPeriodTypeTmp").val()){
			periodTypeTmp[i].checked = true;
		}
	}
	schedule.getPeriodWay($("#tempPeriodTypeTmp").val(),$("#tempPeriodWay").val());//periodWay内容的反向解析，并选中
};
//清空临时存储的高级设置层内容
schedule.clearPeriodContentTemp=function(){
	$("#tempPeriodStartdate").val("");
	$("#tempPeriodEnddate").val("");
	$("#tempPeriodTypeTmp").val("");
	$("#tempPeriodWay").val("");
};
//保存关闭等操作后的清空标识操作
schedule.clearPeriodFlag=function(){
	schedule.clearPeriodContent2();//清空高级设置
	schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
	$("#newOrModify").val("");
	$("#newOk").val("");
	$("#modifyExistValue").val("");
	$("#modifyFlag").val("");
};
// periodWay内容的拼接
schedule.setPeriodWay = function() {
	if ($("#periodType").val() == "2") {// 周
		var periodWayWeek = $("#periodWay").val();
		if(periodWayWeek=="2:"){// 新建 ; 非周期性日程选择高级设置后 （非周期性日程编辑成周期性日程）; 
			var week = document.getElementsByName("periodWayWeek");
			for (var i = 0; i < week.length; i++) {
				if (week[i].checked == true) {
					periodWayWeek += week[i].value + ",";
				}
			}
			periodWayWeek = periodWayWeek.substring(0, periodWayWeek.length - 1);
			$("#periodWay").val(periodWayWeek);
		}else{
			if(periodWayWeek!=null&&periodWayWeek!=""){//周期性日程
				var week = document.getElementsByName("periodWayWeek");
				var tmpPeriodWayWeek="2:";
				for (var i = 0; i < week.length; i++) {
					if (week[i].checked == true) {
						tmpPeriodWayWeek += week[i].value + ",";
					}
				}
				tmpPeriodWayWeek = tmpPeriodWayWeek.substring(0, tmpPeriodWayWeek.length - 1);
				$("#periodWay").val(tmpPeriodWayWeek);
			}
		}
	} else if ($("#periodType").val() == "3") {
		var periodWayMonthDay = $("#periodWay").val();
		var monthDay = $("#periodWayDay").val();
		if(periodWayMonthDay=="3:"){
			$("#periodWay").val(periodWayMonthDay + monthDay);
		}else{
			if(periodWayMonthDay!=null&&periodWayMonthDay!=""){
				var tmpPeriodWayMonthDay = "3:";
				$("#periodWay").val(tmpPeriodWayMonthDay + monthDay);
			}
		}
	} else if ($("#periodType").val() == "4") {
		var periodWayYearMonthDay = $("#periodWay").val();
		var yearMonth = $("#periodWayMonth").val();
		var yearMonthDay = $("#periodWayDay").val();
		if(periodWayYearMonthDay=="4:"){
			$("#periodWay").val(periodWayYearMonthDay + yearMonth + ":" + yearMonthDay);
		}else{
			if(periodWayYearMonthDay!=null&&periodWayYearMonthDay!=""){
				var tmpPeriodWayYearMonthDay="4:";
				$("#periodWay").val(tmpPeriodWayYearMonthDay + yearMonth + ":" + yearMonthDay);
			}
		}
	}
};
// periodWay内容的反向解析，并选中
schedule.getPeriodWay = function(periodType, periodWay) {
	var periodTypeTmp = document.getElementsByName("periodTypeTmp");
	switch (periodType) {
	case "0":
		for (var i = 0; i < periodTypeTmp.length; i++) {
			if (periodTypeTmp[i].value == '0') {
				periodTypeTmp[i].checked = true;
				$("#periodTr").html();
				$("#periodTr").hide();
			}
		}
		break;
	case "1":
		for (var i = 0; i < periodTypeTmp.length; i++) {
			if (periodTypeTmp[i].value == '1') {
				periodTypeTmp[i].checked = true;
				var periodContent = "<td><span class='required'>*</span>周期</td><td>每一天</td>";
				$("#periodTr").html(periodContent);
				$("#periodTr").show();
			}
		}
		break;
	case "2":
		for (var i = 0; i < periodTypeTmp.length; i++) {
			if (periodTypeTmp[i].value == '2') {
				periodTypeTmp[i].checked = true;
				var str = periodWay.split(":")[1].split(",");
				var chk1 = "";
				var chk2 = "";
				var chk3 = "";
				var chk4 = "";
				var chk5 = "";
				var chk6 = "";
				var chk7 = "";
				for (var j = 0; j < str.length; j++) {
					var tmp = str[j];
					switch (tmp) {
					case "1":
						chk1 = "checked"
						break;
					case "2":
						chk2 = "checked"
						break;
					case "3":
						chk3 = "checked"
						break;
					case "4":
						chk4 = "checked"
						break;
					case "5":
						chk5 = "checked"
						break;
					case "6":
						chk6 = "checked"
						break;
					case "7":
						chk7 = "checked"
						break;
					default:
						break;
					}
				}
				var periodContent = "<td><span class='required'>*</span>周期</td><td>"
						+ "<label for='periodWayWeek0' class='checkbox inline'><input type='checkbox' id='periodWayWeek0' name='periodWayWeek' value='1' "
						+ chk1
						+ "/>星期日</label>"
						+ "<label for='periodWayWeek1' class='checkbox inline'><input type='checkbox' id='periodWayWeek1' name='periodWayWeek' value='2' "
						+ chk2
						+ "/>星期一</label>"
						+ "<label for='periodWayWeek2' class='checkbox inline'><input type='checkbox' id='periodWayWeek2' name='periodWayWeek' value='3' "
						+ chk3
						+ "/>星期二</label>"
						+ "<label for='periodWayWeek3' class='checkbox inline'><input type='checkbox' id='periodWayWeek3' name='periodWayWeek' value='4' "
						+ chk4
						+ "/>星期三</label>"
						+ "<label for='periodWayWeek4' class='checkbox inline'><input type='checkbox' id='periodWayWeek4' name='periodWayWeek' value='5' "
						+ chk5
						+ "/>星期四</label>"
						+ "<label for='periodWayWeek5' class='checkbox inline'><input type='checkbox' id='periodWayWeek5' name='periodWayWeek' value='6' "
						+ chk6
						+ "/>星期五</label>"
						+ "<label for='periodWayWeek6' class='checkbox inline'><input type='checkbox' id='periodWayWeek6' name='periodWayWeek' value='7' "
						+ chk7 + "/>星期六</label>" + "</td>";
				$("#periodTr").html(periodContent);
				$("#periodTr").show();
			}
		}
		break;
	case "3":
		for (var i = 0; i < periodTypeTmp.length; i++) {
			if (periodTypeTmp[i].value == '3') {
				periodTypeTmp[i].checked = true;
				var str = periodWay.split(":")[1];
				var options = "";
				for (var i = 1; i <= 31; i++) {
					options += "<option value=" + i + ">" + i + "</option>"
				}
				var periodContent = "<td><span class='required'>*</span>周期</td><td>每月第&nbsp;&nbsp;"
						+ "<select id='periodWayDay' name='periodWayDay' style='width:65px'>"
						+ options + "</select>" + "&nbsp;&nbsp;天</td>";
				$("#periodTr").html(periodContent);
				$("#periodWayDay").val(str);
				$("#periodTr").show();
			}
		}
		break;
	case "4":
		for (var i = 0; i < periodTypeTmp.length; i++) {
			if (periodTypeTmp[i].value == '4') {
				periodTypeTmp[i].checked = true;
				var str = periodWay.split(":");
				var optionsDay = "";
				var optionsMonth = ""
				for (var i = 1; i <= 31; i++) {
					optionsDay += "<option value=" + i + ">" + i + "</option>"
				}
				for (var i = 1; i <= 12; i++) {
					optionsMonth += "<option value=" + i + ">" + i + "</option>"
				}
				var periodContent = "<td><span class='required'>*</span>周期</td><td>每年第&nbsp;&nbsp;"
						+ "<select id='periodWayMonth' name='periodWayMonth' style='width:65px' onchange='schedule.monthDay($(this).val())'>"
						+ optionsMonth
						+ "</select>&nbsp;&nbsp;月的第&nbsp;&nbsp;"
						+ "<select id='periodWayDay' name='periodWayDay' style='width:65px'>"
						+ optionsDay + "</select>&nbsp;&nbsp;天</td>";
				$("#periodTr").html(periodContent);
				$("#periodWayMonth").val(str[1]);
				$("#periodWayDay").val(str[2]);
				$("#periodTr").show();
			}
		}
		break;
	default:
		break;
	}
	
	ie8StylePatch();
	iePlaceholderPatch();
};
// 添加修改公用方法
schedule.savaOrModify = function(hide) {
	var menuFlag = $("#diaryMenuFlag").val();
	var pageFlag = $("#diaryPageFlag").val();
	if (schedule.subState)
		return;
	if ($("#diaryForm").valid()) {
		var url = getRootPath() + "/po/diary/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath() + "/po/diary/update.action?time=" + new Date();
		}
		schedule.setPeriodWay();
		$("#shareScopeId").val($('#shareScopeChoose-shareScopeChoose').val());// 共享范围
		if($("#modifyFlag").val()=="2"){//当进入修改页面时是普通日程
			//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性
			var periodTypeTmp = document.getElementsByName("periodTypeTmp");
			for (var i = 0; i < periodTypeTmp.length; i++) {
				if(periodTypeTmp[i].checked==true&&periodTypeTmp[i].value!="0"){
					$("#modifyFlag").val("3");
				}
			}
		}
		if($("#modifyFlag").val()=="1"){//修改全部周期时
			//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性
			var periodTypeTmp = document.getElementsByName("periodTypeTmp");
			for (var i = 0; i < periodTypeTmp.length; i++) {
				if(periodTypeTmp[i].checked==true&&periodTypeTmp[i].value=="0"){
					$("#modifyFlag").val("4");
				}
			}
		}
		var formData = $("#diaryForm").serializeArray();
//		for(var i=0;i<formData.length;i++){
//			if(formData[i].name=="periodType"){
//				alert(formData[i].name+" "+formData[i].value);
//			}
//		}
		if($("#modifyFlag").val()=="3"){
			msgBox.confirm({
				content : "编辑普通日程为周期性日程<br>会删除该日程所属的批注信息,<br>同时对开始/结束时间的变更无效,<br>是否继续？",
				success : function() {
					schedule.subState = true;
					schedule.savaOrModifyCallback(url,formData,pageFlag,menuFlag,hide);
				},
				cancel : function() {
					schedule.subState = false;
				}
			});
		}else{
			schedule.savaOrModifyCallback(url,formData,pageFlag,menuFlag,hide);
		}
	} else {
		schedule.subState = false;
		msgBox.info({
			type : "fail",
			content : $.i18n.prop("JC_SYS_067")
		});
	}
};
schedule.savaOrModifyCallback=function(url,formData,pageFlag,menuFlag,hide){
	jQuery.ajax({
		url : url,
		type : 'POST',
		async : false,
		data : formData,
		success : function(data) {
			schedule.subState = false;
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage,
					callback : function() {
						var start=$("#starttime").val();
						var end=$("#endtime").val();
						var id = data.diaryVo.id;
						if (pageFlag == "1") {
							if (menuFlag == "1") {
								schedule.clearAddForm();
								if($.trim($("#periodDiv").html()) != ""){
									schedule.clearPeriodContent();//清空高级设置
								}
							} else if (menuFlag == "4") {
								schedule.clearAddForm();
								if($.trim($("#periodDiv").html()) != ""){
									schedule.clearPeriodContent();//清空高级设置
								}
							}
							if (hide) {
								$('#The-new-agenda').modal('hide');
							}
							$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
						} else if (pageFlag == "2") {
							var starttime = $("#startDateTime").val();
							var endtime = $("#endDateTime").val();
							schedule.getSelectDiary(starttime, endtime);// 列表页刷新选择日程下拉选
//							$("#oneDayDiaryList option[value='"+id+"']").attr("selected", true);
							schedule.detailTable();// 更新到详细界面
							schedule.getDetailData(id);// 列表页根据日程id查询并显示该日程详细信息
						}
						$("#remind").val('');//清空提醒设置内容
						if (hide) {
							schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
						}else{
							schedule.clearPeriodContent2();//清空高级设置
							schedule.clearPeriodContentTemp();//清空临时存储的高级设置层内容
							$("#token").val(data.diaryVo.token);
							if (menuFlag == "1") {
								$('#shareScopeId').val("");
								selectControl.clearValue("shareScopeChoose-shareScopeChoose");
							}
						}
//						getToken();
					}
				});
			} else {
				if (data.labelErrorMessage) {
					showErrors("diaryForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					if (data.errorMessage == "tokenError") {
						alertx(tokenMessage);
					} else if (data.errorMessage == "concurrentError") {
						alertx(concurrentMessage);
					} else {
						alertx(data.errorMessage);
					}
				} else {
					msgBox.tip({
						type : "fail",
						content : data.successMessage
					});
				}
//				getToken();
			}
		},
		error : function() {
			schedule.subState = false;
			msgBox.tip({
				type : "fail",
				content : $.i18n.prop("JC_SYS_002")
			});
		}
	});
};
// 删除日程
schedule.deleteDiary = function(id) {
	if (id == null || id == "") {
		msgBox.info({
			type : "fail",
			content : $.i18n.prop("JC_SYS_061")
		});
		return;
	}
	
	if(!DeleteCascade.syncCheckExist("diaryAnno",id)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		msgBox.confirm({
			content : $.i18n.prop("JC_SYS_034"),
			success : function() {
				var menuFlag = $("#diaryMenuFlag").val();// 1日程安排 2下属日程 3共享查询 4领导日程
				var loginUser=$("#loginUser").val();
				var userid=$("#userid").val();
				if(menuFlag=="4"){
					if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
						schedule.deleteCallBack(id);
					}else{
						msgBox.info({
							type : "fail",
							content : $.i18n.prop("JC_OA_PO_057"),
							callback : function() {
								var pageFlag = $("#diaryPageFlag").val();// 1日历 2列表
								if(pageFlag=="1"){
									loadrightmenu("/po/diary/diaryLeadCalendar.action?userid="+$("#userid").val()+"&time=" + new Date());
								}
								if(pageFlag=="2"){
									loadrightmenu('/po/diary/diaryLeadList.action?possessorId='+$("#userid").val()+"&time=" + new Date());
								}
							}
						});
					}
				}else{
					schedule.deleteCallBack(id);
				}
			}
		});
	}
};
// 删除用户回调方法
schedule.deleteCallBack = function(id) {
	var pageFlag = $("#diaryPageFlag").val();
	$
			.ajax({
				type : "POST",
				url : getRootPath() + "/po/diary/deleteByIds.action?time="
						+ new Date(),
				data : {
					"ids" : id
				},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								if (pageFlag == "1") {
									$('#Schedule-detail').modal('hide');
									$("#calendar")
											.fullCalendar('refetchEvents');// 重新加载此页面
								} else if (pageFlag == "2") {
									schedule.refreshData();// 列表页最右侧数据区刷新
									$('#comment').empty();// 清空批注信息
									$('#leaderIdeaTitle').show();// 显示领导批注字样
									schedule.initAnno(0);// 批注信息初始化
									$("#toModify").hide();
									$("#deleteDiary").hide();
									$("#calendarView").removeClass("btn");
									$("#calendarView").addClass("btn dark");
									
								}
								schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
							}
						});
					} else {
						if (data.labelErrorMessage) {
							showErrors("diaryForm", data.labelErrorMessage);
						} else if (data.errorMessage) {
							msgBox.info({
								type : "fail",
								content : data.errorMessage
							});
						} else {
							msgBox.tip({
								type : "fail",
								content : data.successMessage,
								callback : function() {
								}
							});
						}
					}
				},
				error : function() {
					schedule.subState = false;
					msgBox.tip({
						type : "fail",
						content : $.i18n.prop("JC_SYS_002")
					});
				}
			});
};
// 进入日程修改方式
schedule.toModifyType = function() {
	$("#newOrModify").val("1");//标识为修改
	var pageFlag = $("#diaryPageFlag").val();// 1日历 2列表
	var menuFlag = $("#diaryMenuFlag").val();
//	selectControl.init("shareScope","shareScopeChoose-shareScopeChoose",true,true);//人员选择树 参数divid 设置的id-name 多选 人员
	if (pageFlag == "1") {
		if ($("#ptype").val() == "0") {// periodType=0 无周期
			$('#Schedule-detail').modal('hide');
			$('#formTitle').text("编辑");
			$("#modifyFlag").val("2");//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性
			$("#savaOrModify").hide();
			$("#savaAndClose").removeClass("btn");
			$("#savaAndClose").addClass("btn dark");
			$("#savaAndClose").text("保 存");
			$('#The-new-agenda').modal('show');
			schedule.enabledInput();// 将修改周期性日程置为不可用的控件全部恢复可用
		} else {
			msgBox.confirm({
				content : "编辑所有事件<br>会删除该周期性日程<br>所属的批注信息，<br>是否继续？",
				success : function() {
					if($('input:radio[name="periodModify"]:checked').attr("id")=="periodModify2"){
						document.getElementById("periodModify1").checked=true;
					}
					$('#period-diary-modify').modal('show');
				}
			});
		}
	} else if (pageFlag == "2") {
		var id = $("#id").val();
		if (id != "0") {
			if(menuFlag=="4"){
				var userid=$("#userid").val();
				var loginUser=$("#loginUser").val();
				if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
				}else{
					msgBox.info({
						type : "fail",
						content : $.i18n.prop("JC_OA_PO_057"),
						callback : function() {
							loadrightmenu('/po/diary/diaryLeadList.action?possessorId='+$("#userid").val()+"&time=" + new Date());
						}
					});
				}
			}
			
			if ($("#ptype").val() == "0") {// periodType=0 无周期
				$('#comment').empty();// 清空批注信息
				$('#leaderIdeaTitle').hide();// 隐藏领导批注字样
				if (menuFlag == "1") {
					$('#leaderIdeaTable').hide();//隐藏批注框
				}
				schedule.modifyTable();
				schedule.enabledInput();// 将修改周期性日程置为不可用的控件全部恢复可用
				$("#modifyFlag").val("2");//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性
				schedule.changeButtonSectionToModify();
				schedule.refreshData();// 列表页最右侧数据区刷新
				schedule.getDetailData(id);
				$("#savaOrModify").hide();
				$("#sava").removeClass("btn");
				$("#sava").addClass("btn dark");
			} else {
				msgBox.confirm({
					content : "编辑所有事件<br>会删除该周期性日程<br>所属的批注信息，<br>是否继续？",
					success : function() {
						if($('input:radio[name="periodModify"]:checked').attr("id")=="periodModify2"){
							document.getElementById("periodModify1").checked=true;
						}
						$('#period-diary-modify').modal('show');
					}
				});
			}
		} else {
			msgBox.info({
				type : "fail",
				content : "请选择一个要编辑的数据"
			});
		}
	}
};
// 进入日程修改
schedule.toModify = function() {
	$("#newOrModify").val("1");//标识为修改
	var menuFlag = $("#diaryMenuFlag").val();
	var pageFlag = $("#diaryPageFlag").val();// 1日历 2列表
	$('#comment').empty();// 清空批注信息
	$('#leaderIdeaTitle').hide();// 隐藏领导批注字样
	if (menuFlag == "1") {
		$('#leaderIdeaTable').hide();//隐藏批注框
	}
	var flag = document.getElementsByName("periodModify");
	var modifyFlag = "";
	for (var i = 0; i < flag.length; i++) {
		if (flag[i].checked == true) {
			modifyFlag = flag[i].value;
			$("#modifyFlag").val(modifyFlag);
		}
	}
	var id = $("#id").val();
	if (pageFlag == "1") {
		$('#Schedule-detail').modal('hide');
		$('#formTitle').text("编辑");
		$("#savaOrModify").hide();
		$("#savaAndClose").removeClass("btn");
		$("#savaAndClose").addClass("btn dark");
		$("#savaAndClose").text("保 存");
		$('#The-new-agenda').modal('show');
	} else if (pageFlag == "2") {
		schedule.modifyTable();
		schedule.enabledInput();// 将修改周期性日程置为不可用的控件全部恢复可用
		schedule.changeButtonSectionToModify();
		schedule.getDetailData(id);
		$("#savaOrModify").hide();
		$("#sava").removeClass("btn");
		$("#sava").addClass("btn dark");
	}
	if (modifyFlag == "0") {// 修改当前事件
		document.getElementById("periodStartdate").disabled = true;
		document.getElementById("periodEnddate").disabled = true;
		var periodTypeTmp = document.getElementsByName("periodTypeTmp");
		for (var i = 0; i < periodTypeTmp.length; i++) {
			periodTypeTmp[i].disabled = true;
		}
		var periodWayWeek = document.getElementsByName("periodWayWeek");
		for (var i = 0; i < periodWayWeek.length; i++) {
			periodWayWeek[i].disabled = true;
		}
		$("#periodWayDay").attr("disabled", "disabled");
		$("#periodWayMonth").attr("disabled", "disabled");
		document.getElementById("starttime").disabled = false;
		document.getElementById("endtime").disabled = false;
	} else if (modifyFlag == "1") {// 修改所有事件
		document.getElementById("starttime").disabled = true;
		document.getElementById("endtime").disabled = true;
		document.getElementById("periodStartdate").disabled = false;
		document.getElementById("periodEnddate").disabled = false;
		var periodTypeTmp = document.getElementsByName("periodTypeTmp");
		for (var i = 0; i < periodTypeTmp.length; i++) {
			periodTypeTmp[i].disabled = false;
		}
		var periodWayWeek = document.getElementsByName("periodWayWeek");
		for (var i = 0; i < periodWayWeek.length; i++) {
			periodWayWeek[i].disabled = false;
		}
		$("#periodWayDay").removeAttr("disabled");
		$("#periodWayMonth").removeAttr("disabled");
	}
	var starttime = $("#startDateTime").val();
	var endtime = $("#endDateTime").val();
	schedule.getSelectDiary(starttime, endtime);// 列表页刷新选择日程下拉选
	$('#period-diary-modify').modal('hide');
	if(pageFlag=="2"&&selectControlFlag==true){//为了应对列表页修改空人员选择树样式变形
		selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true);// 人员选择树 
	}
};
schedule.collectTable = null;//阅读情况分页对象
// 阅读情况列表
schedule.readingStatus = function(dataId) {
	if (dataId == null || dataId == "") {
		var notContent = "<tr><td colspan='2' style='text-align:center' >没有匹配的记录</td></tr>";
		$('#readingInnerTable').html(notContent);
		return false;
	}
	//显示列信息
	schedule.collectTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "readerName"},
		{mData: "readingDate"}
	 ];
	
	//组装后台参数
	schedule.collectTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(schedule.collectTable, aoData);
		//组装查询条件
		aoData.push({ "name": "worklogId", "value": dataId});
		aoData.push({ "name": "businessType", "value": 1});
	};
	
	//table对象为null时初始化
	if (schedule.collectTable == null) {
		schedule.collectTable = $('#readingStatusTable').dataTable( {
			sAjaxSource: getRootPath()+"/po/readingStatus/queryAllByDataTable.action?time=" + new Date(),
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: schedule.collectTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				schedule.collectTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1]}]
		} );
//		setColumnVis(schedule.collectTable, 6);
		
	} else {
		//table不为null时重绘表格
		schedule.collectTable.fnDraw();
		//pageChange(user.oTable);
	}
//	if (dataId == null || dataId == "") {
//		var notContent = "<tr><td colspan='2'>没有批注内容</td></tr>";
//		$('#readingInnerTable').html(notContent);
//		return false;
//	}
//	$
//			.ajax({
//				type : "POST",
//				url : getRootPath() + "/po/readingStatus/queryAll.action?time="
//						+ new Date(),
//				data : {
//					'worklogId' : dataId,
//					'businessType' : 1
//				},
//				dataType : "json",
//				success : function(data) {
//					$('#readingInnerTable').empty();
//					if (data) {
//						var readingStatusStrs = "";
//						for (var i = 0; i < data.length; i++) {
//							var readingStatus = data[i];
//							readingStatusStrs += "<tr>" + "<td>"
//									+ readingStatus.readerName + "</td>"
//									+ "<td>" + readingStatus.readingDate
//									+ "</td>" + "</tr>";
//						}
//						if (readingStatusStrs == "") {
//							readingStatusStrs = "<tr><td colspan='2'>没有批注内容</td></tr>";
//						}
//						$('#readingInnerTable').html(readingStatusStrs);
//					} else {
//						msgBox.info({
//							type : "fail",
//							content : data.errorMessage
//						});
//					}
//				}
//			});
};
//比较判断当前传入用户是否是当前登录用户的委托人
schedule.compareMandatorIdUserid=function(userid,loginUserId){
	var is=false;//判断当前选中用户是否为委托人
	var flag=false;//判断曾经的委托人是否还是领导状态
	var mandatorIds=$("#mandatorId").val();
	if(mandatorIds!=null&&mandatorIds!=""){
		var ids=mandatorIds.split(",");
		for(var i=0;i<ids.length;i++){
			if(userid == ids[i]){
				is=true;
			} 
		}
	}else{
	}
	if(is){//当前选中用户是委托人 当前委托人曾经是领导 判断当前委托人是否还是领导状态
		$.ajax({
			type : "GET",
			url : getRootPath()+"/sys/user/get.action?time=" + new Date(),
			data : {id:userid},
			async : false,
			dataType : "json",
			success : function(data) {
				if (data) {
					if(data.isLeader=="1"){
						is=true;
					}else{
						is=false;
					}
				}
			}
		});
	}
	return is;
};
//当用户在使用中委托人被取消领导资格时 删除委托关系
schedule.mandatorIdUseridDel=function(userid,loginUser){
	var data = {
			mandatorId : userid,
			mandataryId : loginUser
		};
	var url=getRootPath()+"/po/diaryDelegation/deleteByMandatorIdAndMandataryId.action?time=" + new Date();
	$.ajax({
		type : "GET",
		url : url,
		data : data,
		async : false,
		dataType : "json",
		success : function(data) {
		}
	});
};
schedule.compareMandatorIdUseridOld=function(userid){
	var is=false;
	var mandatorIds=$("#mandatorId").val();
	if(mandatorIds!=null&&mandatorIds!=""){
		var ids=mandatorIds.split(",");
		for(var i=0;i<ids.length;i++){
			if(userid == ids[i]){
				is=true;
			} 
		}
		return is;
	}else{
		return is;
	}
};
// ------------------------批注Begin-----------------------------------
/** 初始化批注列表 */
schedule.initAnno = function(dataid) {
	var menuFlag = $("#diaryMenuFlag").val();// 1日程安排 2下属日程 3共享查询 4领导日程
	$
			.ajax({
				type : "GET",
				url : getRootPath() + "/po/diary/queryAnno.action",
				data : {
					"businessId" : dataid
				},
				dataType : "json",
				success : function(data) {
					if (data) {
						var liStr = '';
						if (data.length == 0) {
							if(dataid=="0"){
								liStr += "<li class=\"clearfix m-b input-group\">"
									+ "<p class=\"dialog-text input-group\" name=\"rootAnno\"></p></li>";
							}else{
								liStr += "<li class=\"clearfix m-b input-group\">"
									+ "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有批注内容</p></li>";
							}
						}
						var currentUser = $('#currentUser').val();
						for (var i = 0; i < data.length; i++) {
							var annoParent = data[i];
							liStr += "<li class=\"clearfix m-b input-group\">"
									+ "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\""
									+ annoParent.id
									+ "\">"
									+ "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"
									+ annoParent.annoUserIdValue + "：</strong>"
									+ annoParent.content + "<span>"
									+ annoParent.annoDate + "</span>" + "</p>";
							if (menuFlag == "1" || menuFlag == "2") {
								liStr += "<div class=\"input-group-btn p-l\">"
										+ "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"anno"+i+"\">回 复</a>"
										+ "</div>";
								liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
					                 		"<textarea rows=\"3\" name=\"replayAnno\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":\"></textarea>"+
					                 		"<div class=\"input-group-btn p-l\">"+
					                 		" <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
					                 		"</div>"+
					                 	"</div>";
							}

							// 批注回复
							var replyList = annoParent.annoReplyList;
							if (replyList) {
								for (var j = 0; j < replyList.length; j++) {
									var annoReply = replyList[j];
									liStr += "<div class=\"dis-row\">"
												+ "<p class=\"dialog-text input-group\" data=\"" + annoReply.id + "\">"
												+ "<input type=\"hidden\" name=\"\">"
												+ "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"
												+ annoReply.annoUserIdValue + "回复"
												+ annoReply.parentUserName
												+ "：</strong>" + annoReply.content
												+ "<span>" + annoReply.annoDate
												+ "</span>" + "</p>";
									if (menuFlag == "1" || menuFlag == "2") {
										liStr += "<div class=\"input-group-btn p-l\">"
												+ "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"reply"+j+"\">回 复</a>"
												+ "</div>" 
										 + "</div>"+
												"<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
								                 	"<textarea name=\"replayAnno\" rows=\"3\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+":\"></textarea>"+
								                 	"<div class=\"input-group-btn p-l\">"+
								                 		" <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
								                 	"</div>"+
								                "</div>";
									}
//									liStr += "</div>";
								}
							}
							if (menuFlag == "1" || menuFlag == "2") {
								liStr += 
//									"<div class=\"hide hf-area\">"
//										+ "<textarea rows=\"3\"></textarea>"
//										+ "<div class=\"input-group-btn\">"
//										+ " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"
//										+ "</div>" + "</div>" + 
										"</li>";
							}
						}
						$('#comment').append(liStr);
					}
				}
			});
};
/** 保存领导批注 */
schedule.saveLeaderIdeaForm = function() {
	var dataId = $('#delId').val();
	if (dataId == ''){
		msgBox.info({
			type : "fail",
			content : "请选择一个日程"
		});
		return false;
	}
	// 校验是否重复提交
	if (schedule.subState)
		return;
	schedule.subState = true;
	$("textarea[name='replayAnno']").val("立牌坊"); //用于处理一个表单中俩个批注框都不能为空，提交回复无法通过验证的问题。
	
	if(!DeleteCascade.syncCheckExist("diaryAnno",dataId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		// 校验表单信息
		if ($("#diaryForm").valid()) {
			var url = getRootPath() + "/po/anno/save.action?time=" + new Date();
//			var url = getRootPath() + "/po/diary/saveAnno.action?time=" + new Date();
			// 将表单序列化成json格式
//			var formData = $("#leaderIdeaForm").serializeArray();
			var formData = [];
			formData.push({
				"name" : "content",
				"value" : $("#leaderIdeaContent").val()
			});
			formData.push({
				"name" : "businessId",
				"value" : dataId
			});
			formData.push({
				"name" : "annoType",
				"value" : 1
			});
			formData.push({
				"name" : "annoName",
				"value" : $("#detailTitle").text()
			});
			formData.push({
				"name" : "byAnnoUserId",
				"value" : $("#userid").val()
			});
			var diaryPageFlag = $("#diaryPageFlag").val();
			if(diaryPageFlag=="1"){//-------------------------------------------------------------------------------------------------------
				formData.push({
					"name" : "token",
					"value" : $("#annoToken").val()
				});
			}
			if(diaryPageFlag=="2"){
				formData.push({
					"name" : "token",
					"value" : $("#token").val()
				});
			}
			jQuery.ajax({
				url : url,
				type : 'POST',
				data : formData,
				success : function(data) {
					schedule.subState = false;// 更新重复提交状态标识
//					getToken();// 更新token
					if(diaryPageFlag=="1"){
						$("#annoToken").val(data.token);
					}
					if(diaryPageFlag=="2"){
						$("#token").val(data.token);
					}
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								$('#leaderIdeaContent').val('');
								$('#comment').empty();
								schedule.initAnno(dataId);
							}
						});
					} else {
						if (data.labelErrorMessage) {
							showErrors("leaderIdeaForm", data.labelErrorMessage);
						} else {
							msgBox.info({
								type : "fail",
								content : data.errorMessage
							});
						}
					}
				},
				error : function() {
					schedule.subState = false;
					msgBox.tip({
						type : "fail",
						content : $.i18n.prop("JC_SYS_002")
					});
				}
			});
		} else {
			schedule.subState = false;
			msgBox.info({
				type : "fail",
				content : $.i18n.prop("JC_SYS_067")
			});
		}
	}
	$("textarea[name='replayAnno']").val(""); //用于处理一个表单中俩个批注框都不能为空，提交回复无法通过验证的问题。
};
/** 批注回复 */
schedule.commentReply = function(e) {
	var parentId = $(e).parent().prev().attr('data');
	var id = $(e).attr('id');
	$(e).toggle();//点击的回复按钮隐藏
	var $showArea = $(e).parents('li').find('div[class*="hf-area"][for="'+id+'"]');
	$showArea.find('a[name="send"]').attr('data',parentId);
	$showArea.find('textarea').val('');
	$showArea.toggle();//显示当前批注的回复文本域
	$('a[name="reply"]').not($(e)).show();//显示其他回复按钮
	$showArea.css("display","table-row");//添加样式
	$(e).parents('ul').find('div[class*="hf-area"]').not($showArea).hide();
	iePlaceholderPatch();
};
/** 批注发送 */
schedule.commentSend = function(e) {
	var businessId = $('#delId').val();
	var annoParentId = $(e).attr('data');
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr(
			'data');
//	var content = $(e).parent().prev('textarea').val();//导致验证后提交undefined
	var content = $(e).parent().parent().find("textarea").val();
	$("#leaderIdeaContent").val("立牌坊");//用于处理一个表单中俩个批注框都不能为空，提交回复无法通过验证的问题。
	
	if(!DeleteCascade.syncCheckExist("diaryAnno",businessId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		if ($("#diaryForm").valid()) {
			$.ajax({
				type : "POST",
				url : getRootPath() + "/po/diary/annoReply.action?time=" + new Date(),
				data : {
					"businessId" : businessId,
					"annoParentId" : annoParentId,
					"rootParentId" : rootParentId,
					"content" : content,
					"byAnnoUserId" : $("#userid").val(),
					"annoName":$("#detailTitle").text(),
					"annoToken":$("#annoToken").val()
				},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								$('#comment').empty();
								schedule.initAnno(businessId);
							}
						});
					} else {
						msgBox.info({
							type : "fail",
							content : data.errorMessage
						});
					}
				}
			});
		}else {
			schedule.subState = false;//更新重复提交状态标识
			msgBox.info({
				content:$.i18n.prop("JC_SYS_067"),
				success:function(){
//					fnCall();
				}
			});
		}
	}
	$("#leaderIdeaContent").val("");//用于处理一个表单中俩个批注框都不能为空，提交回复无法通过验证的问题。
};
// ------------------------------------批注End--------------------------------------------
/** ------------------日历&列表页面共用end--------------- */
/** ------------------日历页面begin------------------- */
// fullcalendar初始化
schedule.initFullCalendar = function(userid) {
	var loginUser=$("#loginUser").val();
	var menuFlag = $("#diaryMenuFlag").val();// 是哪个菜单 1日程安排 2下属日程 3共享查询 4领导日程
//	var mandatorId = $("#mandatorId").val();//单委托
//	if (userid == mandatorId && userid != 0) {//单委托
	if(schedule.compareMandatorIdUseridOld(userid)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
		diaryLeadCalendar.initModifyButton();
	}
	var editableFlag = false;
	if (menuFlag == "1") {
		editableFlag = true;
	} else if (menuFlag == "4") {
//		if (userid == mandatorId && userid != 0) {//单委托
		if(schedule.compareMandatorIdUseridOld(userid)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
			editableFlag = true;
		}
	}
	$('#calendar').fullCalendar(
			{
				header : {
					left : 'prev',
					center : 'title',
					right : 'next ',
				},
				firstDay : 0,// 开始的第一天
				weekends : true,// 是否显示周末
				defaultView : 'month',// 默认显示的视图
				weekMode : 'fixed',

				unselectAuto : false,// 是否自动取消选中框
				weekends : true,// 布尔类型, 默认为true, 标识是否显示周六和周日的列.
				allDayDefault : false,
				allDaySlot : false,//是否显示全天日程
				allDayText:'全日', 
				editable : editableFlag,// Boolean类型, 默认值false,
										// 用来设置日历中的日程是否可以编辑. 可编辑是指可以移动, 改变大小等.
				disableDragging : false, // Boolean类型, 默认false,
											// 为false时所有的event可以拖动, 必须editable =
											// true
				diableResizing : true, // Boolean, 默认false, 所有的event可以改变大小,
										// 必须editable = true
				selectable : true,
				selectHelper : true,
				select : function(startDate, endDate, allDay, jsEvent, view) {// 日期选择后
					if (jsEvent) {// 鼠标点击
						schedule.showAddDiaryDiv(userid,startDate,endDate);
					} else {// 快速查询定位

					}
				},
				eventClick : function(calEvent, jsEvent, view) { // 事件被点击
					schedule.showDiaryDetailDiv(calEvent.id,userid);
//					schedule.getDetailData(calEvent.id);
				},
				timeFormat : {
					month : 'H:mm{-H:mm} ',
					agendaWeek : 'H:mm{ - H:mm}',
					agendaDay : 'H:mm{ - H:mm}'

				},
				titleFormat:{//日历标题格式化
				    month: 'yyyy年MMMM',
				    week:"yyyy年MMMd日'&#8212;'{yyyy年MMMd日}", 
			        day: 'yyyy年MMMMd日 dddd'
				},
				eventDrop : function(calEvent, dayDelta, minuteDelta, allDay,
						revertFunc, jsEvent, ui, view) {
					// 跨天拖拽日程,在一个日程事件被移动, 并成功移动到另外一个日期/时间调用;
					// CalEvent对象: 标准的当前使用的日程事件源(请注意区分这里的日程事件和计算机领域的事件之间的区别,
					// 这里的事件是业务上的, 表明在某个时间做某事的一个记录, 而计算机领域的事件则是用户对计算机的一个动作)
					// dayDelta: 保存了这次拖动导致该日程事件移动了多少天, 向前为正数, 向后为负数.
					// minuteDelta: 保存了这次拖动导致该日程事件移动了多少分钟, 向前为正数, 向后为负数.
					// 该值只有在agenda view下有效.
					// allDay: 如果在month view下移动, 或在agenda view下移动到all-day区域,
					// 则allDay为true, 移动到agenda view的其他区域则为false.
					// revertFunc: 调用该方法, 可以将刚才的拖动恢复到原状. 这个方法多用于ajax的提交, 移动之后,
					// 提交数据到后台, 如果后台更新失败, 根据返回消息, 调用这个方法, 可以使页面回复原状.
					// jsEvent就是一个普通的javascript事件, 包含的是click事件的基础信息.
					// UI保存的是一个JQuery的UI对象. 可以从该对象中获取位移, 位置等数据.
					schedule.get(calEvent.id);
					var tmpMaster=$("#tmpMaster").val();
					var tmpPeriodType=$("#tmpPeriodType").val();
					var possessorId=calEvent.possessorId;
					var title=calEvent.title;
					var isShare=calEvent.isShare;
					if(tmpMaster=="0"&&tmpPeriodType=="0"){//不包含周期性日程主日程
						if (menuFlag == "1") {// 1日程安排 4领导日程
							var start = $.fullCalendar.formatDate(calEvent.start,
									"yyyy-MM-dd HH:mm:ss");
							var end = $.fullCalendar.formatDate(calEvent.end,
									"yyyy-MM-dd HH:mm:ss");
							schedule.modify(start, end, calEvent.id, revertFunc,
									"editable",$("#modifyDate").val(),possessorId,title,isShare);
						} else if (menuFlag == "4") {
//							if (userid == mandatorId && userid != 0) {//单委托
							if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
								var start = $.fullCalendar.formatDate(
										calEvent.start, "yyyy-MM-dd HH:mm:ss");
								var end = $.fullCalendar.formatDate(calEvent.end,
										"yyyy-MM-dd HH:mm:ss");
								schedule.modify(start, end, calEvent.id,
										revertFunc, "editable",$("#modifyDate").val(),possessorId,title,isShare);
							}else{
								revertFunc();
							}
						}
					}else if(tmpPeriodType=="0"&&tmpMaster!="0"){
						if (menuFlag == "1") {// 1日程安排 4领导日程
							var start = $.fullCalendar.formatDate(calEvent.start,
									"yyyy-MM-dd HH:mm:ss");
							var end = $.fullCalendar.formatDate(calEvent.end,
									"yyyy-MM-dd HH:mm:ss");
							schedule.modify(start, end, calEvent.id, revertFunc,
									"editable",$("#modifyDate").val(),possessorId,title,isShare);
						} else if (menuFlag == "4") {
//							if (userid == mandatorId && userid != 0) {//单委托
							if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
								var start = $.fullCalendar.formatDate(
										calEvent.start, "yyyy-MM-dd HH:mm:ss");
								var end = $.fullCalendar.formatDate(calEvent.end,
										"yyyy-MM-dd HH:mm:ss");
								schedule.modify(start, end, calEvent.id,
										revertFunc, "editable",$("#modifyDate").val(),possessorId,title,isShare);
							}else{
								revertFunc();
							}
						}
					}else if((tmpMaster==undefined||tmpMaster=="")&&(tmpPeriodType==undefined||tmpPeriodType=="")){
						revertFunc();
						msgBox.info({
							type : "fail",
							content : $.i18n.prop("JC_OA_PO_039"),
							callback : function() {
								$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
							}
						});
					}else{
						revertFunc();
						msgBox.info({
							type : "fail",
							content : $.i18n.prop("JC_OA_PO_041"),
							callback : function() {
							}
						});
					}
					$("#tmpMaster").val("");
					$("#modifyDate").val("");
					$("#tmpPeriodType").val("0");
				},
				eventResize : function(calEvent, dayDelta, minuteDelta,
						revertFunc, jsEvent, ui, view) {// 横着拖拽日程,在日程事件改变大小并成功后调用
					schedule.get(calEvent.id);
					var tmpMaster=$("#tmpMaster").val();
					var tmpPeriodType=$("#tmpPeriodType").val();
					var possessorId=calEvent.possessorId;
					var title=calEvent.title;
					var isShare=calEvent.isShare;
					if(tmpMaster=="0"&&tmpPeriodType=="0"){//不包含周期性日程主日程
						if (menuFlag == "1") {// 1日程安排 4领导日程
							var start = $.fullCalendar.formatDate(calEvent.start,
									"yyyy-MM-dd HH:mm:ss");
							var end = $.fullCalendar.formatDate(calEvent.end,
									"yyyy-MM-dd HH:mm:ss");
							schedule.modify(start, end, calEvent.id, revertFunc,
									"editable",$("#modifyDate").val(),possessorId,title,isShare);
						} else if (menuFlag == "4") {
//							if (userid == mandatorId && userid != 0) {//单委托
							if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人	
								var start = $.fullCalendar.formatDate(
										calEvent.start, "yyyy-MM-dd HH:mm:ss");
								var end = $.fullCalendar.formatDate(calEvent.end,
										"yyyy-MM-dd HH:mm:ss");
								schedule.modify(start, end, calEvent.id,
										revertFunc, "editable",$("#modifyDate").val(),possessorId,title,isShare);
							}else{
								revertFunc();
							}
						}
					}else if(tmpPeriodType=="0"&&tmpMaster!="0"){
						if (menuFlag == "1") {// 1日程安排 4领导日程
							var start = $.fullCalendar.formatDate(calEvent.start,
									"yyyy-MM-dd HH:mm:ss");
							var end = $.fullCalendar.formatDate(calEvent.end,
									"yyyy-MM-dd HH:mm:ss");
							schedule.modify(start, end, calEvent.id, revertFunc,
									"editable",$("#modifyDate").val(),possessorId,title,isShare);
						} else if (menuFlag == "4") {
//							if (userid == mandatorId && userid != 0) {//单委托
							if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人	
								var start = $.fullCalendar.formatDate(
										calEvent.start, "yyyy-MM-dd HH:mm:ss");
								var end = $.fullCalendar.formatDate(calEvent.end,
										"yyyy-MM-dd HH:mm:ss");
								schedule.modify(start, end, calEvent.id,
										revertFunc, "editable",$("#modifyDate").val(),possessorId,title,isShare);
							}else{
								revertFunc();
							}
						}
					}else if((tmpMaster==undefined||tmpMaster=="")&&(tmpPeriodType==undefined||tmpPeriodType=="")){
						revertFunc();
						msgBox.info({
							type : "fail",
							content : $.i18n.prop("JC_OA_PO_039"),
							callback : function() {
								$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
							}
						});
					}else{
						revertFunc();
						msgBox.info({
							type : "fail",
							content : $.i18n.prop("JC_OA_PO_041"),
							callback : function() {
							}
						});
					}
					$("#tmpMaster").val("");
					$("#modifyDate").val("");
					$("#tmpPeriodType").val("0");
				},
				events : function(start, end, callback) {
					var startDate = $.fullCalendar.formatDate(start,
							"yyyy-MM-dd HH:mm:ss");
					var endDate = $.fullCalendar.formatDate(end,
							"yyyy-MM-dd HH:mm:ss");
					var paramPossessorId = userid;// 非日程安排时传入从树获取的用户ID
					var url = getRootPath()
							+ "/po/diary/queryDiaryList.action?time="
							+ new Date();
					if (menuFlag == "3") {
						url = getRootPath()
								+ "/po/diary/queryShareDiaryList.action?time="
								+ new Date();
					}
					jQuery.ajax({
						type : "POST",
						url : url,
						data : {
							"startDate" : startDate,
							"endDate" : endDate,
							"possessorId" : paramPossessorId
						},
						async : false,
						success : function(data) {
							var datas = [];
							for (i = 0; i < data.length; i++) {
								var checkAllDay = false;
								var loginUserId=data[i].loginUserId;
								var id = data[i].id;
								var periodType = data[i].periodType;
								var periodWay = data[i].periodWay;
								var possessorId = data[i].possessorId;
								var title = data[i].title + " ";
								var content = data[i].content;
								var moduleFlag = data[i].moduleFlag;
								var isShare = data[i].isShare;
								var modifyDate = data[i].modifyDate;
								start = new Date(Date.parse(data[i].starttime
										.replace(/-/g, "/")));
								end = new Date(Date.parse(data[i].endtime
										.replace(/-/g, "/")));
								var strDate = new Date(Date
										.parse(data[i].starttime.replace(/-/g,
												"/")));
								var endDate = new Date(Date
										.parse(data[i].endtime.replace(/-/g,
												"/")));
								var color = "#60aae9";// 默认手写日程
								// switch(moduleFlag){//模块来源标记[0-工作日程;1-日志导入;2-记事本导入;3-待办任务导入;4-工作计划导入]
								// case "0":
								// color="#60aae9";//class bg-blue 手写日程
								// break;
								// case "1":
								// color="#ffc333";//class bg-yellow 日志导入
								// break;
								// case "2":
								// color="#00c0ef";//class bg-blue-lt 记事本导入
								// break;
								// case "3":
								// color="#00a65a";//class bg-green-dark 待办任务导入
								// break;
								// case "4":
								// color="#aece4e";// class bg-green-lt 工作计划导入
								// break;
								// default:
								// break;
								// }
								// var textColor="";
								if (menuFlag == "1") {// 1日程安排
									if (isShare == "1") {
										color = "#ffc333";// class
															// bg-yellow-dark
															// 已共享日程
										// title="【共享】 "+title;
										// textColor="#000000";
									}
								} else if (menuFlag == "2") {// 2下属日程
								} else if (menuFlag == "3") {// 3共享查询
									color = "#60aae9";
								} else if (menuFlag == "4") {// 4领导日程
								}
								datas.push({
									allDay : false,
									id : id,
									color : color,
									title : title,
									content : content,
									start : start,
									end : end,
									possessorId : possessorId,
									isShare : isShare,
									loginUserId : loginUserId
								});
							}
							callback(datas);
						}
					});
				}
			});
};
// 日程日历形式快速查询
schedule.searchFullCalendar = function() {
	var searchtime = $('#searchtime').val();
	if (searchtime == null || searchtime == ''){
		return false;
	}
//	var searchdate = new Date(searchtime);
	var searchdate = new Date(Date.parse(searchtime.replace(/-/g,"/")));//解决IE8兼容性问题
	
	$('#calendar').fullCalendar('gotoDate', searchdate);
	$('#calendar').fullCalendar('select', searchdate, searchdate, true);
};
// 销毁当前日历
schedule.destroyFullCalendar = function() {
	$("#calendar").html("");
};
// 修改公用方法(仅用于拖拽和拉伸)
schedule.modify = function(starttime, endtime, id, revertFunc, editable, modifyDate,possessorId,title,isShare) {
	var menuFlag = $("#diaryMenuFlag").val();
	if (schedule.subState)
		return;
	schedule.subState = true;
	if ($("#diaryForm").valid()) {
		var url = getRootPath() + "/po/diary/update.action?time=" + new Date();
		var data = {
			starttime : starttime,
			endtime : endtime,
			id : id,
			title : title,
			possessorId:possessorId,
//			modifyFlag : "0", //5拖拽拉伸（包含周期性编辑当前事件）
			modifyFlag : "5", 
			modifyDate : modifyDate, 
			editable : editable,
			isShare : isShare
		};
		jQuery.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : data,
			success : function(data) {
				schedule.subState = false;
//				getToken();
				$("#token").val(data.diaryVo.token);
				if (data.success == "true") {
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : function() {
							var id = data.diaryVo.id;
//							if (menuFlag == "1") {
//								schedule.clearAddForm();
//							} else if (menuFlag == "4") {
//								schedule.clearAddForm();
//							}
//							$('#The-new-agenda').modal('hide');
							$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
						}
					});
				} else {
					if (data.labelErrorMessage) {
						showErrors("diaryForm", data.labelErrorMessage);
					} else if (data.errorMessage) {
						if (data.errorMessage == "tokenError") {
							alertx(tokenMessage);
						} else if (data.errorMessage == "concurrentError") {
							alertx(concurrentMessage);
						} else {
							alertx(data.errorMessage);
						}
					} else {
						msgBox.tip({
							type : "fail",
							content : data.successMessage,
							callback : function() {
								revertFunc();
							}
						});
					}
				}
			},
			error : function() {
				schedule.subState = false;
				msgBox.tip({
					type : "fail",
					content : $.i18n.prop("JC_SYS_002"),
					callback : function() {
						revertFunc();
					}
				});
			}
		});
	} else {
		schedule.subState = false;
		msgBox.info({
			type : "fail",
			content : $.i18n.prop("JC_SYS_067"),
			callback : function() {
				revertFunc();
			}
		});
	}
};
/** -------------------日历页面end-------------------- */
//仅供拖拽拉伸回显master和modifydate用
schedule.get = function(id) {
//	var menuFlag = $("#diaryMenuFlag").val();// 1日程安排 2下属日程 3共享查询 4领导日程
//	var pageFlag = $("#diaryPageFlag").val();
//	var modifyFlag = $("#modifyFlag").val();
	if (id == null || id == "") {
		return false;
	}
//	if (menuFlag == "1" || menuFlag == "4") {
//		$('#shareScopeId').val("");
//		selectControl.clearValue("shareScopeChoose-shareScopeChoose");
//	}
	$.ajax({
		type : "GET",
		url : getRootPath() + "/po/diary/get.action?time=" + new Date(),
		data : {
			"id" : id
		},
		dataType : "json",
		async : false,
		success : function(data) {
			$("#modifyDate").val(data.modifyDate);
			$("#tmpMaster").val(data.master);
			$("#tmpPeriodType").val(data.periodType);
		}/*,error:function(data){
			msgBox.info({
				type : "fail",
				content : $.i18n.prop("JC_OA_PO_039"),
				callback : function() {
				}
			});
		}*/
	});
};
/*****************************************弹出层操作***********************************************/
//清空新建表单
schedule.clearAddForm = function (menuFlag) {
	$('#diaryForm').find("input[type=text]").val("");
	$('#diaryForm').find("textarea").val("");
	if(menuFlag=="1"){
		$('#shareScopeId').val("");
		selectControl.clearValue("shareScopeChoose-shareScopeChoose");
	}
	document.getElementById("starttime").disabled=false;
	document.getElementById("endtime").disabled=false;
//	$('#comment').empty();
	hideErrorMessage();
};

/**
 * 显示日程添加DIV
 */
schedule.showAddDiaryDiv = function(userid,startDate,endDate){
	var mandatorIds=$("#mandatorId").val();//委托人
	var menuFlag=$("#diaryMenuFlag").val();
	var url=getRootPath()+"/po/diary/showAddDiaryDiv.action";
	$("#diaryDiv").load(url,null,function(){schedule.showAddDiaryDivCallBack(userid,startDate,endDate)});
};
/**
 * 新建页面回调方法
 */
schedule.showAddDiaryDivCallBack=function(userid,startDate,endDate){
	var menuFlag=$("#diaryMenuFlag").val();
	$("#remind").val('');//清空提醒设置内容
	schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
	$("#newOrModify").val("0");//标识为新建
	if (menuFlag == "1") {// 1日程安排
//		schedule.clearPeriodContent();//高级设置清空
		selectControl.init("shareScope","shareScopeChoose-shareScopeChoose",true,true);//人员选择树 参数divid 设置的id-name 多选 人员
		schedule.clearAddForm(menuFlag);
		var start = $.fullCalendar.formatDate(startDate,
				"yyyy-MM-dd HH:mm:ss");
		var end = $.fullCalendar.formatDate(endDate,
				"yyyy-MM-dd HH:mm:ss");
		if(end.substring(11,end.length)=="00:00:00"){//月视图用
			end = schedule.getSysDateEnd(end.substring(0,10)); 
		}
		$("#id").val(0);
		$("#modifyDate").val("");
		$("#millisecond").val("");
		$("#businessId").val("");
		$("#moduleFlag").val("0");
		$("#savaOrModify").show();
		$("#starttime").val(start);
		$("#endtime").val(end);
		$('#formTitle').text("新增");
		$("#savaAndClose").removeClass("btn dark");
		$("#savaAndClose").addClass("btn");
		$("#savaAndClose").text("保存退出");
		$('#The-new-agenda').modal('show');
		
		
		var startDate=$("#starttime").val().substring(0, 10);
		var endDate=$("#endtime").val().substring(0, 10);
		if($("#periodTr").text()!=""){//给标识赋值，用于判断进入修改页时高级设置是否值
			$("#modifyExistValue").val("1");//修改有值
		}else{
			$("#modifyExistValue").val("0");//修改没有值
		}
		var modifyFlag = $("#modifyFlag").val();
		$("#periodStartdate").val(startDate);
		$("#periodEnddate").val(endDate);
		$('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio 
		$(".datepicker-input").datepicker();//日历控件重新初始化
//		 if(modifyFlag==null||modifyFlag==""){
//			 schedule.enabledInput(); 
//		 }
		if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
			schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
			schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
		}
		$("#periodConfirm").click(function(){//高级设置页面确定按键点击事件绑定
			schedule.periodConfirm();
			schedule.periodContentTemp();//临时存储高级设置层中的内容
		});
		$("#periodCancel").click(function(){//高级设置页面取消按键点击事件绑定
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
		});
		
		$("#savaOrModify").click(function(){//保存继续按键点击事件绑定
			schedule.savaOrModify(false);
		});
		$("#savaAndClose").click(function(){//保存退出按键点击事件绑定
			schedule.savaOrModify(true);
		});
		$("#cancel").click(function(){//取消按键点击事件绑定 
			schedule.cancel(menuFlag);
			schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
		});
		$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
			$('#Advanced-settings').modal('show');
		});
		/**提醒按钮事件绑定*/
		$("#remindSet").click(function(){
			schedule.showRemind();
		});
	} else if (menuFlag == "2") {// 2下属日程
	} else if (menuFlag == "3") {// 3共享查询
	} else if (menuFlag == "4") {// 4领导日程
		$("#share").hide();
		schedule.clearPeriodContent();//高级设置清空
//		if (userid == mandatorId && userid != 0) {//单委托
		var loginUser=$("#loginUser").val();
		if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
			schedule.clearAddForm(menuFlag);
			var start = $.fullCalendar.formatDate(
					startDate, "yyyy-MM-dd HH:mm:ss");
			var end = $.fullCalendar.formatDate(endDate,
			"yyyy-MM-dd HH:mm:ss");
			if(end.substring(11,end.length)=="00:00:00"){//月视图用
				end = schedule.getSysDateEnd(end.substring(0,10)); 
			}
			$("#id").val(0);
			$("#modifyDate").val("");
			$("#millisecond").val("");
			$("#businessId").val("");
			$("#moduleFlag").val("0");
			$("#savaOrModify").show();
			$("#starttime").val(start);
			$("#endtime").val(end);
			$('#formTitle').text("新增");
			$("#savaAndClose").removeClass("btn dark");
			$("#savaAndClose").addClass("btn");
			$("#savaAndClose").text("保存退出");
			$('#The-new-agenda').modal('show');
			
			var startDate=$("#starttime").val().substring(0, 10);
			var endDate=$("#endtime").val().substring(0, 10);
			if($("#periodTr").text()!=""){//给标识赋值，用于判断进入修改页时高级设置是否值
				$("#modifyExistValue").val("1");//修改有值
			}else{
				$("#modifyExistValue").val("0");//修改没有值
			}
			var modifyFlag = $("#modifyFlag").val();
			$("#periodStartdate").val(startDate);
			$("#periodEnddate").val(endDate);
			$('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio 
			$(".datepicker-input").datepicker();//日历控件重新初始化
			
			if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
				schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
				schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
			}
			
			$("#periodConfirm").click(function(){//高级设置页面确定按键点击事件绑定
				schedule.periodConfirm();
				schedule.periodContentTemp();//临时存储高级设置层中的内容
			});
			$("#periodCancel").click(function(){//高级设置页面取消按键点击事件绑定
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
			});
			
			$("#savaOrModify").click(function(){//保存继续按键点击事件绑定
				schedule.savaOrModify(false);
			});
			$("#savaAndClose").click(function(){//保存退出按键点击事件绑定
				schedule.savaOrModify(true);
			});
			$("#cancel").click(function(){//取消按键点击事件绑定 
				schedule.cancel(menuFlag);
				schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
			});
			$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
				$('#Advanced-settings').modal('show');
			});
			/**提醒按钮事件绑定*/
			$("#remindSet").click(function(){
				schedule.showRemind();
			});
		}
	}
	ie8StylePatch();
};
//取消新增日程
schedule.cancel=function(menuFlag){
	schedule.clearAddForm(menuFlag);
	$('#The-new-agenda').modal('hide');
};
//新增修改点击x
schedule.closeAddX=function(){
	schedule.cancel();
	schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
};
/**
 * 显示日程详细DIV
 */
schedule.showDiaryDetailDiv = function(id,userid){
	var url=getRootPath()+"/po/diary/showDiaryDetailDiv.action?id="+id;
	$("#detailDiv").load(url,null,function(){schedule.showDiaryDetailDivCallBack(id,userid)});
};
/**
 * 详细页面回调方法
 */
schedule.showDiaryDetailDivCallBack=function(id,userid){
	var menuFlag = $("#diaryMenuFlag").val();// 1日程安排 2下属日程 3共享查询 4领导日程
	var modifyFlag = $("#modifyFlag").val();
//	if (menuFlag == "1" ) {
//		$('#shareScopeId').val("");
//		selectControl.clearValue("shareScopeChoose-shareScopeChoose");
//	}
	var diaryJson=$("#diaryJsonDetail").val();
	
	var data=eval("("+diaryJson+")");
	hideErrorMessage();// 清除验证信息
	if (diaryJson == null||diaryJson=="null") {

		msgBox.info({
			type : "fail",
			content : $.i18n.prop("JC_OA_PO_039"),
			callback : function() {
				$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
				return false;
			}
		});
		return false;
	}

	$('#delId').val(data.id);
	$("#remind").val(data.remind);
	$("#tempRemind").val(data.remind);
	var periodStartdate=data.periodStartdate!=null&&data.periodStartdate!=""?data.periodStartdate.substring(0,10):"";
	var periodEnddate=data.periodEnddate!=null&&data.periodEnddate!=""?data.periodEnddate.substring(0,10):"";
	//-----------------------------------------------------------用于处理修改时层回显等由于恶心的弹出层导致的恶心问题
	$("#tempPeriodStartdate").val($("#periodStartdate").val());
	$("#tempPeriodEnddate").val($("#periodEnddate").val());
	var periodTypeTmp = document.getElementsByName("periodTypeTmp");
	for (var i = 0; i < periodTypeTmp.length; i++) {
		if(periodTypeTmp[i].checked == true){
			$("#tempPeriodTypeTmp").val(periodTypeTmp[i].value);
		}
	}
	schedule.setPeriodWay();// periodWay内容的拼接
	$("#tempPeriodWay").val($("#periodWay").val());
	//-----------------------------------------------------------
	$("#detailTitle").text(data.title);
	$("#detailStartTime").text(data.starttime);
	$("#detailEndTime").text(data.endtime);
	$("#ptype").val(data.periodType);
	var period = "";
	var periodStart="";
	var periodEnd="";
	if(data.periodStartdate!=null&&data.periodStartdate!=""){
		periodStart=data.periodStartdate.substring(0,10);
	}
	if(data.periodEnddate!=null&&data.periodEnddate!=""){
		periodEnd=data.periodEnddate.substring(0,10);
	}
	switch (data.periodType) {
	case "0":
		period = "无定期";
		break;
	case "1":
		period = "从" + periodStart + " 至 "
				+ periodEnd + "的每一天";
		break;
	case "2":
		var str = data.periodWay.split(":")[1].split(",");
		var s = "";
		var weeks = "";
		for (var i = 0; i < str.length; i++) {
			switch (str[i]) {
			case "1":
				s = "周日";
				break;
			case "2":
				s = "周一";
				break;
			case "3":
				s = "周二";
				break;
			case "4":
				s = "周三";
				break;
			case "5":
				s = "周四";
				break;
			case "6":
				s = "周五";
				break;
			case "7":
				s = "周六";
				break;
			default:
				break;
			}
			weeks += s + ",";
		}
		weeks = weeks.substring(0, weeks.length - 1);
		period = "从" + periodStart + " 至 "
				+ periodEnd + "的每周" + weeks;
		break;
	case "3":
		var str = data.periodWay.split(":")[1];
		period = "从" + periodStart + " 至 "
				+ periodEnd + "的每月" + str + "日";
		break;
	case "4":
		var month = data.periodWay.split(":")[1];
		var m = parseInt(month) ;
		var day = data.periodWay.split(":")[2];
		period = "从" + periodStart + " 至 "
				+ periodEnd + "的每年" + m + "月" + day + "日";
		break;
	default:
		break;
	}
	$("#detailPeriodTypeStartEndDate").html(period);
	if(menuFlag=="3"){
		$("#annoInput").hide();
	}
	if(menuFlag=="4"){
		$("#shareDetail").hide();
		$("#annoInput").hide();
	}else{
		$("#detailShare").text(data.shareScopeIdValue);// 共享范围
	}
	var content = data.content.replace(/\r\n/g, "<br/>");
	$("#detailContent").html(content);
	var detailRemind = "<input type='hidden' id='detailRemindReadonly' name='detailRemindReadonly'/>"
	+ "<a id='detailRemindSet' name='detailRemindSet' class='a-icon i-new m-r-xs' href='#' role='button' data-toggle='modal'>"
	+ "提醒设置</a>";
	$("#detailRemind").html(detailRemind);//提醒
	$('#Schedule-detail').modal('show');
	$('#comment').empty();// 清空批注信息
	if (id != null && id != "") {
		schedule.initAnno(id);// 批注信息初始化
	}
	if(menuFlag=="1"){
		$("#readingStatus").hide();
	}else if(menuFlag=="2"||menuFlag=="3"){
		$("#toModify").hide();
		$("#deleteDiary").hide();
		$("#readingStatus").removeClass("btn");
		$("#readingStatus").addClass("btn dark");
		$('#readingStatus').click(function(){
			var worklogId = $('#delId').val();
			schedule.readingStatus(worklogId);
		});//绑定阅读情况查询
	}else if(menuFlag=="4"){
		var loginUser=$("#loginUser").val();
		if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
			$("#readingStatus").hide();
		}else{
			$("#toModify").hide();
			$("#deleteDiary").hide();
			$("#readingStatus").hide();
			$("#closeDetail").removeClass("btn");
			$("#closeDetail").addClass("btn dark");
		}
	}
//	if (menuFlag == "1" ) {// 1日程安排 2下属日程 3共享查询  4领导日程
//		selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true);// 人员选择树 参数divid 设置的id-name 多选 人员
//	}
	if (menuFlag == "1" || menuFlag == "4") {// 1日程安排 2下属日程 3共享查询  4领导日程
		//编辑按键点击事件绑定
		$("#toModify").click(function(){
			schedule.showDiaryModifyDiv(id);
		});
		//删除按键点击事件绑定
		$("#deleteDiary").click("click", function(){
	  		schedule.deleteDiary(id);
	  	});
	}
	//详细页查看提醒设置
	$('#detailRemind').on('click','a[name="detailRemindSet"]',function(){
		schedule.showRemindReadonly();
	});

	//关闭详细页点击事件绑定
	$("#closeDetail").click(function(){
		$('#Schedule-detail').modal('hide');
	});
	if(menuFlag == "1" || menuFlag == "2"){
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
	}
	// 更新阅读情况
	$.ajax({
		type : "GET",
		url : getRootPath() + "/po/diary/savaReadingStatus.action",
		data : {
			'id' : data.id,
			'createUser' : data.createUser
		},
		dataType : "json",
		success : function(data) {
		}
	});
	ie8StylePatch();
	
};
/**
 * 显示日程修改DIV
 */
schedule.showDiaryModifyDiv = function(id){
	var menuFlag = $("#diaryMenuFlag").val();// 1日程安排 2下属日程 3共享查询 4领导日程
	if(menuFlag=="4"){
		var userid=$("#userid").val();
		var loginUser=$("#loginUser").val();
		if(schedule.compareMandatorIdUserid(userid,loginUser)&& userid != 0){//比较判断当前传入用户是否是当前登录用户的委托人
			var url=getRootPath()+"/po/diary/showDiaryModifyDiv.action?id="+id;
			$("#diaryDiv").load(
					url,null,function(){schedule.showDiaryModifyDivCallBack(id)});
		}else{
			msgBox.info({
				type : "fail",
				content : $.i18n.prop("JC_OA_PO_057"),
				callback : function() {
					loadrightmenu("/po/diary/diaryLeadCalendar.action?userid="+$("#userid").val()+"&time=" + new Date());
				}
			});
		}
	}else{
		var url=getRootPath()+"/po/diary/showDiaryModifyDiv.action?id="+id;
		$("#diaryDiv").load(
				url,null,function(){schedule.showDiaryModifyDivCallBack(id)});
	}
};
/**
 * 显示日程修改DIV回调
 */
schedule.showDiaryModifyDivCallBack = function(id){
	$("#newOrModify").val("1");//标识为修改
	
	var menuFlag = $("#diaryMenuFlag").val();// 1日程安排 2下属日程 3共享查询 4领导日程
	var modifyFlag = $("#modifyFlag").val();
//	if (id == null || id == "") {
//		$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
//		return false;
//	}
	if (menuFlag == "1") {
		$('#shareScopeId').val("");
		selectControl.clearValue("shareScopeChoose-shareScopeChoose");
	}
	if (menuFlag == "4") {
		$('#share').hide();
	}
	var diaryJson=$("#diaryJsonModify").val();
	var data=eval("("+diaryJson+")");
	hideErrorMessage();// 清除验证信息
	if (diaryJson == null||diaryJson=="null") {
		msgBox.info({
			type : "fail",
			content : $.i18n.prop("JC_OA_PO_039"),
			callback : function() {
				$('#Schedule-detail').modal('hide');
				$("#calendar").fullCalendar('refetchEvents');// 重新加载此页面
				return false;
			}
		});
		return false;
	}
	var startDate=$("#starttime").val().substring(0, 10);
	var endDate=$("#endtime").val().substring(0, 10);
//	if($("#periodTr").text()!=""){//给标识赋值，用于判断进入修改页时高级设置是否值
//		$("#modifyExistValue").val("1");//修改有值
//	}else{
//		$("#modifyExistValue").val("0");//修改没有值
//	}
	var modifyFlag = $("#modifyFlag").val();
	$("#periodStartdate").val(startDate);
	$("#periodEnddate").val(endDate);
	$('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio 
//	 if(modifyFlag==null||modifyFlag==""){
//		 schedule.enabledInput(); 
//	 }
	if($("#modifyFlag").val()!="0"){//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
		schedule.assignPeriodContent();//从临时存储的内容中给高级设置赋值
		schedule.periodTypeAssignForDate();//默认选中无定期时 周期时间不可用
	}
	$("#periodConfirm").click(function(){//高级设置页面确定按键点击事件绑定
		schedule.periodConfirm();
		schedule.periodContentTemp();//临时存储高级设置层中的内容
	});
	$("#periodCancel").click(function(){//高级设置页面取消按键点击事件绑定
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
	});
	

	$("#id").val(data.id);
	
	$("#possessorId").val(data.possessorId);
	$("#title").val(data.title);
	// $("#content").val(data.content);
	var modifyContent = data.content.replace(/<br\/>/g, "\r\n");//用于处理计划导入日程的换行问题
	modifyContent = modifyContent.replace(/<BR\/>/g, "\r\n");//用于处理计划导入日程的换行问题
	modifyContent = modifyContent.replace(/<BR>/g, "\r\n");//用于处理计划导入日程的换行问题
	modifyContent = modifyContent.replace(/<br>/g, "\r\n");//用于处理计划导入日程的换行问题
	$("[name='content']").val(modifyContent);
	if (menuFlag == "2") {// todo 在jquery.formfill.js问题没解决之前临时解决办法
		$("#leaderIdeaContent").val("");
	}
	$("#starttime").val(data.starttime);
	$("#endtime").val(data.endtime);
	$("#periodType").val(data.periodType);
	$("#periodWay").val(data.periodWay);
	var periodStartdate=data.periodStartdate!=null&&data.periodStartdate!=""?data.periodStartdate.substring(0,10):"";
	var periodEnddate=data.periodEnddate!=null&&data.periodEnddate!=""?data.periodEnddate.substring(0,10):"";
	$("#periodStartdate").val(periodStartdate);
	$("#periodEnddate").val(periodEnddate);
	$("#moduleFlag").val(data.moduleFlag);
	$("#diaryType").val(data.diaryType);
	$("#isShare").val(data.isShare);
	$("#remind").val(data.remind);
	$("#tempRemind").val(data.remind);
	$("#delId").val(data.id);
	$("#ptype").val(data.periodType);
	$("#modifyDate").val(data.modifyDate);
	$("#millisecond").val(data.millisecond);
	$("#businessId").val(data.businessId);
	
	if (menuFlag == "1") {// 1日程安排 2下属日程 3共享查询  4领导日程
		var shareIds = data.shareScopeId.split(",");
		var shareValues = data.shareScopeIdValue.split(",");
		if ((shareIds != null && shareIds != "") && (shareValues != null && shareValues != "")) {
			var data = [];
			for (var i = 0; i < shareIds.length; i++) {
				data.push({
					id : shareIds[i],
					text : shareValues[i]
				})
			}
			selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true, null, data);// 人员选择树 参数divid 设置的id-name 多选 人员
		}else{
			//为了应对列表页修改空人员选择树样式变形
			selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true);// 人员选择树 参数divid 设置的id-name 多选 人员
		}
	}
	
	
	if ($("#ptype").val() == "0") {// periodType=0 无周期
		$('#Schedule-detail').modal('hide');
		$('#formTitle').text("编辑");
		$("#modifyFlag").val("2");//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性
		$("#savaOrModify").hide();
		$("#savaAndClose").removeClass("btn");
		$("#savaAndClose").addClass("btn dark");
		$("#savaAndClose").text("保 存");
		$('#The-new-agenda').modal('show');
		
		$("#savaAndClose").click(function(){//保存退出按键点击事件绑定
			schedule.savaOrModify(true);
		});
		$("#cancel").click(function(){//取消按键点击事件绑定 
			schedule.cancel(menuFlag);
			schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
		});
		$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
			$('#Advanced-settings').modal('show');
		});
		/**提醒按钮事件绑定*/
		$("#remindSet").click(function(){
			schedule.showRemind();
		});
		
//		schedule.enabledInput();// 将修改周期性日程置为不可用的控件全部恢复可用
	} else {
		msgBox.confirm({
			content : "编辑所有事件<br>会删除该周期性日程<br>所属的批注信息，<br>是否继续？",
			success : function() {
				schedule.periodModifyInfoDiv(id);
			},
			cancel : function(){
			}
		});
	}
	$(".datepicker-input").datepicker();//日历控件重新初始化
	ie8StylePatch();
};
/**
 * 显示周期性日程修改提示信息DIV
 */
schedule.periodModifyInfoDiv = function(id){
	var url=getRootPath()+"/po/diary/periodDiaryModifyInfo.action";
	$("#periodModifyInfoDiv").load(
			url,null,function(){schedule.periodModifyInfoDivCallBack(id)});
};
/**
 * 显示周期性日程修改提示信息DIV回调
 */
schedule.periodModifyInfoDivCallBack = function(id){
	if($('input:radio[name="periodModify"]:checked').attr("id")=="periodModify2"){
		document.getElementById("periodModify1").checked=true;
	}
	$('#period-diary-modify').modal('show');
	$("#modifyDiv").click(function(){
		schedule.showPeriodDiaryModifyDiv(id);
	});//编辑方式页面确定按键点击事件绑定
	$("#closeModifyType").click(function(){
		$('#period-diary-modify').modal('hide');
	});//关闭编辑类型
	ie8StylePatch();
};
/**
 * 显示周期性日程修改DIV
 */
schedule.showPeriodDiaryModifyDiv=function(id){
	var url=getRootPath()+"/po/diary/showDiaryModifyDiv.action?id="+id;
	$("#diaryDiv").load(
			url,null,function(){schedule.showPeriodDiaryModifyDivCallBack(id)});
};
/**
 * 显示周期性日程修改DIV回调
 */
schedule.showPeriodDiaryModifyDivCallBack=function(id){
	$("#newOrModify").val("1");//标识为修改
	var menuFlag = $("#diaryMenuFlag").val();
//	var pageFlag = $("#diaryPageFlag").val();// 1日历 2列表
//	$('#comment').empty();// 清空批注信息
//	$('#leaderIdeaTitle').hide();// 隐藏领导批注字样
	if (menuFlag == "1") {
		$('#leaderIdeaTable').hide();//隐藏批注框
	}
	if (menuFlag == "4") {
		$('#share').hide();
	}
	var flag = document.getElementsByName("periodModify");
	var modifyFlag = "";
	for (var i = 0; i < flag.length; i++) {
		if (flag[i].checked == true) {
			modifyFlag = flag[i].value;
			$("#modifyFlag").val(modifyFlag);
		}
	}
	$('input:radio[name="periodTypeTmp"]').click(schedule.periodTypeAssign);//处理周期radio 
	if (modifyFlag == "1") {// 修改所有事件
		document.getElementById("starttime").disabled = true;
		document.getElementById("endtime").disabled = true;
	}
	var diaryJson=$("#diaryJsonModify").val();
	var data=eval("("+diaryJson+")");
	hideErrorMessage();// 清除验证信息
	
	schedule.getPeriodWay(data.periodType, data.periodWay);
	$("#id").val(data.id);
	$("#possessorId").val(data.possessorId);
	$("#title").val(data.title);
	// $("#content").val(data.content);
	var modifyContent = data.content.replace(/<br\/>/g, "\r\n");//用于处理计划导入日程的换行问题
	modifyContent = modifyContent.replace(/<BR\/>/g, "\r\n");//用于处理计划导入日程的换行问题
	modifyContent = modifyContent.replace(/<BR>/g, "\r\n");//用于处理计划导入日程的换行问题
	modifyContent = modifyContent.replace(/<br>/g, "\r\n");//用于处理计划导入日程的换行问题
	$("[name='content']").val(modifyContent);
	if (menuFlag == "2") {// todo 在jquery.formfill.js问题没解决之前临时解决办法
		$("#leaderIdeaContent").val("");
	}
	$("#starttime").val(data.starttime);
	$("#endtime").val(data.endtime);
	$("#periodType").val(data.periodType);
	$("#periodWay").val(data.periodWay);
	var periodStartdate=data.periodStartdate!=null&&data.periodStartdate!=""?data.periodStartdate.substring(0,10):"";
	var periodEnddate=data.periodEnddate!=null&&data.periodEnddate!=""?data.periodEnddate.substring(0,10):"";
	$("#periodStartdate").val(periodStartdate);
	$("#periodEnddate").val(periodEnddate);
	$("#moduleFlag").val(data.moduleFlag);
	$("#diaryType").val(data.diaryType);
	$("#isShare").val(data.isShare);
	$("#remind").val(data.remind);
	$("#tempRemind").val(data.remind);
	$("#delId").val(data.id);
	$("#ptype").val(data.periodType);
	$("#modifyDate").val(data.modifyDate);
	$("#millisecond").val(data.millisecond);
	$("#businessId").val(data.businessId);
	
	if (menuFlag == "1") {// 1日程安排 2下属日程 3共享查询  4领导日程
		var shareIds = data.shareScopeId.split(",");
		var shareValues = data.shareScopeIdValue.split(",");
		if ((shareIds != null && shareIds != "") && (shareValues != null && shareValues != "")) {
			var data = [];
			for (var i = 0; i < shareIds.length; i++) {
				data.push({
					id : shareIds[i],
					text : shareValues[i]
				})
			}
			selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true, null, data);// 人员选择树 参数divid 设置的id-name 多选 人员
		}else{
			//为了应对列表页修改空人员选择树样式变形
			selectControl.init("shareScope", "shareScopeChoose-shareScopeChoose", true, true);// 人员选择树 参数divid 设置的id-name 多选 人员
		}
	}
	
	if (modifyFlag == "0") {// 修改当前事件
		document.getElementById("periodStartdate").disabled = true;
		document.getElementById("periodEnddate").disabled = true;
		var periodTypeTmp = document.getElementsByName("periodTypeTmp");
		for (var i = 0; i < periodTypeTmp.length; i++) {
			periodTypeTmp[i].disabled = true;
		}
		var periodWayWeek = document.getElementsByName("periodWayWeek");
		for (var i = 0; i < periodWayWeek.length; i++) {
			periodWayWeek[i].disabled = true;
		}
		$("#periodWayDay").attr("disabled", "disabled");
		$("#periodWayMonth").attr("disabled", "disabled");
		document.getElementById("starttime").disabled = false;
		document.getElementById("endtime").disabled = false;
	} else if (modifyFlag == "1") {// 修改所有事件
		document.getElementById("periodStartdate").disabled = false;
		document.getElementById("periodEnddate").disabled = false;
		var periodTypeTmp = document.getElementsByName("periodTypeTmp");
		for (var i = 0; i < periodTypeTmp.length; i++) {
			periodTypeTmp[i].disabled = false;
		}
		var periodWayWeek = document.getElementsByName("periodWayWeek");
		for (var i = 0; i < periodWayWeek.length; i++) {
			periodWayWeek[i].disabled = false;
		}
		$("#periodWayDay").removeAttr("disabled");
		$("#periodWayMonth").removeAttr("disabled");
	}
	
	$('#Schedule-detail').modal('hide');
	$('#formTitle').text("编辑");
	$("#savaOrModify").hide();
	$("#savaAndClose").removeClass("btn");
	$("#savaAndClose").addClass("btn dark");
	$("#savaAndClose").text("保 存");
	$('#The-new-agenda').modal('show');
	$('#period-diary-modify').modal('hide');
	$("#periodConfirm").click(function(){//高级设置页面确定按键点击事件绑定
		schedule.periodConfirm();
		schedule.periodContentTemp();//临时存储高级设置层中的内容
	});
	$("#periodCancel").click(function(){//高级设置页面取消按键点击事件绑定
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
	});
	$("#savaAndClose").click(function(){//保存退出按键点击事件绑定
		schedule.savaOrModify(true);
	});
	$("#cancel").click(function(){//取消按键点击事件绑定 
		schedule.cancel(menuFlag);
		schedule.clearPeriodFlag();//保存关闭等操作后的清空标识操作
	});
	$("#advancedSettings").click(function(){//高级设置按键点击事件绑定
		schedule.setPeriodDate();
		$('#Advanced-settings').modal('show');
	});
	/**提醒按钮事件绑定*/
	$("#remindSet").click(function(){
		schedule.showRemind();
	});
	$(".datepicker-input").datepicker();//日历控件重新初始化
	ie8StylePatch();
};
schedule.closeAnno = function(){
	$('#annoComment').empty();
};
/*****************************************弹出层操作***********************************************/
