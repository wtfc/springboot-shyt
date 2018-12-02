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
/**
 * 获得时间为星期几
 */
Date.prototype.getWeek = function()
{
	var week; 
	if(this.getDay()==0) week="星期日";
	if(this.getDay()==1) week="星期一";
	if(this.getDay()==2) week="星期二"; 
	if(this.getDay()==3) week="星期三";
	if(this.getDay()==4) week="星期四";
	if(this.getDay()==5) week="星期五";
	if(this.getDay()==6) week="星期六";
	return week;
}; 

worklogLeaderAnno = {};
/**日志详细今日日程动态添加行模版*/
worklogLeaderAnno.autoTrDetailTodayDiaryStr = "<tr>"+
									"<td>第<span></span>条</td>"+
									"<td name=\"todayDiaryTitle\">" +
									"</td>"+
									"</tr>";

/**日志详细今日日志及明日提醒动态添加行模版*/
worklogLeaderAnno.autoTrDetailStr = "<tr>"+
								"<td>第<span></span>条</td>"+
								"<td>" +
								"</td>"+
								"</tr>";
var currentObject = worklogLeaderAnno;
/**
 * 重复提交标识
 */
worklogLeaderAnno.subState = false;

worklogLeaderAnno.initWorklog = function(){
	var d = new Date();
	var date = d.format('yyyy-MM-dd');
	if($('#date').val()!=""){
		date = $('#date').val();
	}
	var worklogDateBegin = date+" 00:00:00";
	var worklogDateEnd = date+" 23:59:59";
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/get.action",
		data : {'worklogDateBegin':worklogDateBegin,'worklogDateEnd':worklogDateEnd},
		dataType : "json",
		success : function(data) {
			if (data) {
				$('#token').val(data.tokenTemp);
				worklogLeaderAnno.initWorklogData(data);
			}
		},
		error:function() {
			msgBox.tip({
				content: date+'不存在日志',
				type:'fail'
			});
			worklogLeaderAnno.clearWorklog();
		}
	});
};

worklogLeaderAnno.initWorklogData = function(data){
	//清除页数据
	worklogLeaderAnno.clearDetail();
	//var d = new Date(data.worklogDate);
	//回显日志详细数据
	//$('#worklogTitle').html(data.worklogDate+"("+d.getWeek()+")日报"+$('#userName').val());
	$('#worklogId').val(data.id);
	$('#title').html(data.title);
	$('#createUserDept').val(data.createUserDept);
	$('#userId').val(data.createUser);
	$('#worklogDate').html(data.worklogDate);
	$('#detailSentimentRemark').html(data.sentimentRemark);
	//回显共享范围
	$('#detailShareUserNames').html(data.shareUserNames);
	
	/*//填充今日日程
	worklogLeaderAnno.initDiary(data.worklogDate);
	//填充待办任务
	worklogLeaderAnno.initWorkTask(data.worklogDate);
	//填充批注
	worklogLeaderAnno.initAnno(data.id);
	//填充今日日志
	var todayLogs = data.todayLogs;
	var todayLogStrs = "";
	if(todayLogs==null||todayLogs.length==0){
		todayLogStrs +='没有匹配的记录';
	}
	if(todayLogs && todayLogs.length > 0) {
		for(var i=0;i<todayLogs.length;i++){
			var todayLog = todayLogs[i];
			todayLogStrs += '第 '+(i+1)+' 条：'+todayLog.content+'</br>';
		}
	}
	$('#todayLog').html(todayLogStrs);
	//填充明日提醒
	var tomorrowReminds = data.tomorrowReminds;
	var tomorrowRemindStrs = "";
	if(tomorrowReminds==null||tomorrowReminds.length==0){
		tomorrowRemindStrs +='没有匹配的记录';
	}
	if(tomorrowReminds && tomorrowReminds.length > 0) {
		for(var i=0;i<tomorrowReminds.length;i++){
			var tomorrowRemind = tomorrowReminds[i];
			tomorrowRemindStrs += '第 '+(i+1)+' 条：'+tomorrowRemind.content+'</br>';
		}
	}
	$('#tomorrowRemind').html(tomorrowRemindStrs);*/
	
	//填充待办任务
	worklogLeaderAnno.initDetailWorkTask(data.worklogDate,'detailTask');
	//填充今日日程
	//worklogLeaderAnno.initDiary(data.worklogDate);
	worklogLeaderAnno.initDiary(data.worklogDate,'detailTodayDiary',worklogLeaderAnno.autoTrDetailTodayDiaryStr);
	//填充批注
	worklogLeaderAnno.initAnno(data.id);
	//填充今日日志
	var todayLogs = data.todayLogs;
	if(todayLogs==null||todayLogs.length==0){
		worklogLeaderAnno.noDate('detailTodayLog');
	}
	if(todayLogs && todayLogs.length > 0) {
		for(var i=0;i<todayLogs.length;i++){
			var todayLog = todayLogs[i];
			worklogLeaderAnno.initDetailTr('detailTodayLog',worklogLeaderAnno.autoTrDetailStr,todayLog.content);
		}
	}
	//填充明日提醒
	var tomorrowReminds = data.tomorrowReminds;
	if(tomorrowReminds==null||tomorrowReminds.length==0){
		worklogLeaderAnno.noDate('detailTomorrowRemind');
	}
	if(tomorrowReminds && tomorrowReminds.length > 0) {
		for(var i=0;i<tomorrowReminds.length;i++){
			var tomorrowRemind = tomorrowReminds[i];
			worklogLeaderAnno.initDetailTr('detailTomorrowRemind',worklogLeaderAnno.autoTrDetailStr,tomorrowRemind.content);
		}
	}
	//设置附件数据
	$('#businessId').val(data.id);
	$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
	showAttachList();//附件填出层数据回显方法
	echoAttachList();//附件列表数据回显方法
	
	//更新阅读情况
	/*$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/savaReadingStatus.action?time=" + new Date(),
		data : {'id':data.id,'createUser':data.createUser},
		dataType : "json",
		success : function(data) {
		}
	});*/
	
	worklogLeaderAnno.annoReading(data.id,data.status);
};

/**清空记录*/
worklogLeaderAnno.clearWorklog = function(){
	$('#worklogTitle').empty();
	$('#title').empty();
	$('#worklogDate').empty();
	$('#detailTask tbody').empty();
	$('#detailTodayDiary tbody').empty();
	$('#detailTodayLog tbody').empty();
	$('#detailSentimentRemark tbody').empty();
	$('#detailTomorrowRemind tbody').empty();
	$('#shareUserNames').empty();
	$('#comment').empty();
	$("#shareUserIds-shareUserIds").select2("data","");
};
/**初始化待办任务列表*/
worklogLeaderAnno.initDetailWorkTask = function(currentDate,to){
	/*var directorId = $('#userId').val();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryWorkTask.action?time=" + new Date(),
		data : {"startDate":currentDate,"directorId":directorId},
		dataType : "json",
		success : function(data) {
			if (data.data) {
				var workTaskStrs = "";
				if(data.data.length==0){
					workTaskStrs +='没有匹配的记录';
				}
				for(var i=0;i<data.data.length;i++){
					var workTask = data.data[i];
					workTaskStrs += '第 '+(i+1)+' 条：'+workTask.content+'</br>';
				}
				$('#task').html(workTaskStrs);
			}
		}
	});*/
	//显示列信息
	var directorId = $('#userId').val();
	worklogLeaderAnno.workTaskTableColumns = [
	                                        //不需要排序的列直接用mData function方式
	                                        {mData: "taskName"},
	                                        {mData: "taskProc"},
	                                        {mData: "sponsor"},
	                                        {mData: "actStartTime"}
	                                        ];
		
	worklogLeaderAnno.aoColumnDefs = [{bSortable: false, aTargets: [0,1,2,3]}];
	
	//组装后台参数
	worklogLeaderAnno.workTaskTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogLeaderAnno.workTaskDetailTable, aoData);
		
		aoData.push({ "name": "startDate", "value": currentDate});
		aoData.push({ "name": "directorId", "value": directorId});
	};
	
	//table对象为null时初始化
	if (worklogLeaderAnno.workTaskDetailTable == null) {
		worklogLeaderAnno.workTaskDetailTable = $('#'+to).dataTable( {
			sAjaxSource: getRootPath()+"/po/worklog/queryWorkTask.action",
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogLeaderAnno.workTaskTableColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogLeaderAnno.workTaskTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
			aoColumnDefs: worklogLeaderAnno.aoColumnDefs
		} );
		
	} else {
		//table不为null时重绘表格
		worklogLeaderAnno.workTaskDetailTable.fnDraw();
	}
};

/**初始化今日日程*/
worklogLeaderAnno.initDiary = function(currentDate,to,tr){
	/*var userId=$('#userId').val();
	var starttime = currentDate;
	var endtime = currentDate;
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryDiary.action?time=" + new Date(),
		data : {"starttime":starttime,"endtime":endtime,"possessorId":userId},
		dataType : "json",
		success : function(data) {
			if (data) {
				var diaryStrs = "";
				if(data.length==0){
					diaryStrs +='没有匹配的记录';
				}
				for(var i=0;i<data.length;i++){
					var diary = data[i];
					diaryStrs += '第 '+(i+1)+' 条：'+diary.content+'</br>';
				}
				$('#todayDiary').html(diaryStrs);
			}
		}
	});*/
	var userId=$('#userId').val()
	var starttime = currentDate+" 00:00:00";
	var endtime = currentDate+" 23:59:59";
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryDiary.action",
		data : {"starttime":starttime,"endtime":endtime,"possessorId":userId},
		dataType : "json",
		success : function(data) {
			if (data) {
				if(data.length==0){
					worklogLeaderAnno.noDate(to);
					return;
				}
				for(var i=0;i<data.length;i++){
					var diary = data[i];
					worklogLeaderAnno.initTr(to,tr,1);
					$('#'+to).find('tbody tr:last td[name="todayDiaryTitle"]').html(diary.content);
				}
			}else{
				worklogLeaderAnno.noDate(to);
			}
		}
	});
};
/**
 * 初始添加行公共方法to:目标容器id,trStr:添加内容,n:添加行数,idPref:id前缀
 */
worklogLeaderAnno.initTr = function(to,trStr,n,idPref){
	for(var i=1;i<=n;i++){
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		if(idPref){
			$('#'+to).find('tbody tr:last input').attr('id',idPref+currentNo);
		};
		$("[data-toggle=tooltip]").tooltip();
	}
	//绑定事件
	/*if(idPref){
		worklogLeaderAnno.autoTrBindEvent(to,trStr,idPref);
	};*/
};
/**
 * 详细页初始添加行公共方法to:目标容器id,trStr:添加内容,
 */
worklogLeaderAnno.initDetailTr = function(to,trStr,d){
	
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		$('#'+to).find('tbody tr:last td:last').html(d);
	
};
/**初始化批注列表*/
worklogLeaderAnno.initAnno = function(worklogId){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryAnno.action?time=" + new Date(),
		data : {"businessId":worklogId},
		dataType : "json",
		success : function(data) {
			if (data) {
				var liStr ='';
				if(data.length==0){
					liStr +="<li class=\"clearfix m-b input-group\">"+
		                    "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有批注内容</p></li>";
				}
				var currentUser = $('#currentUser').val();
				for(var i=0;i<data.length;i++){
					
					var annoParent = data[i];
					liStr += "<li class=\"clearfix m-b input-group\">"+
				                    "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\""+annoParent.id+"\">"+
				                    "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+annoParent.annoUserIdValue+"：</strong>"+annoParent.content+
				                    "<span>"+annoParent.annoDate+"</span>"+
					                "</p>"+
					                "<div class=\"input-group-btn\">"+
					                    "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"anno"+i+"\">回 复</a>"+
					                "</div>";
					liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
				                 "<textarea rows=\"3\" name=\"replayAnno\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":\"></textarea>"+
				                  "<div class=\"input-group-btn\">"+
				                   " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
				                  "</div>"+
				                "</div>";                
					                    
					                
					
					//批注回复
					var replyList = annoParent.annoReplyList;
					if(replyList){
						for(var j=0;j<replyList.length;j++){
							var annoReply = replyList[j];
							liStr += "<div class=\"dis-row\">"+
					                    "<p class=\"dialog-text input-group\" data=\""+annoReply.id+"\">"+
					                    	"<input type=\"hidden\" name=\"\">"+
					                        "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+annoReply.annoUserIdValue+"回复"+annoReply.parentUserName+"：</strong>"+annoReply.content+
					                        "<span>"+annoReply.annoDate+"</span>"+
					                   "</p>"+
					                    "<div class=\"input-group-btn\">"+
					                        "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"reply"+j+"\">回 复</a>"+
					                    "</div>"+
				                    "</div>"+
							
									"<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
					                 "<textarea rows=\"3\" name=\"replayAnno\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+":\"></textarea>"+
					                  "<div class=\"input-group-btn\">"+
					                   " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
					                  "</div>"+
					                "</div>";
									
						}
					}
					liStr += /*"<div class=\"hide hf-area\">"+
				                 "<textarea rows=\"3\"></textarea>"+
				                  "<div class=\"input-group-btn\">"+
				                   " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
				                  "</div>"+
				                "</div>"+*/
				            "</li>";
				}
				$('#comment').append(liStr);
			}
		}
	});
};

/**清除详情页数据*/
worklogLeaderAnno.clearDetail = function(){
	$('#detailTodayLog tbody').html('');
	$('#detailTomorrowRemind tbody').html('');
	$('#detailTodayDiary tbody').html('');
	$('#detailTask tbody').html('');
	$('#comment').empty();
	$("#shareUserIds-shareUserIds").select2("data","");
	//清除附件信息
	clearTable();
	clearAttachlist();
};

/**保存领导批注*/
worklogLeaderAnno.saveLeaderIdeaForm = function(){
	var worklogId = $('#worklogId').val();
	if(worklogId=='')return;
	//校验是否重复提交
	if (worklogLeaderAnno.subState)return;
	worklogLeaderAnno.subState = true;
	//校验表单信息
	if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogLeaderAnno.subState = false;		
		return;
	}else{
		if ($("#leaderIdeaForm").valid()) {
			var url = getRootPath()+"/po/anno/save.action?time=" + new Date();
			//将表单序列化成json格式
			var formData = $("#leaderIdeaForm").serializeArray();
			formData.push({"name": "businessId", "value": worklogId});
			formData.push({"name": "annoType", "value": 2});
			formData.push({"name": "byAnnoUserId", "value": $('#userId').val()});
			formData.push({"name":"byAnnoUserDepid","value":$('#createUserDept').val()});
			formData.push({"name":"annoName","value":$('#title').html()});
			formData.push({"name":"token","value":$('#token').val()});
			jQuery.ajax({
				url : url,
				type : 'POST',
				data : formData,
				success : function(data) {
					worklogLeaderAnno.subState = false;//更新重复提交状态标识
//					getToken();//更新token
					$('#token').val(data.token);
					if(data.success == "true"){
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
						$('#leaderIdeaContent').val('');
						$('#comment').empty();
						worklogLeaderAnno.initAnno(worklogId);
					} else {
						if(data.labelErrorMessage){
							showErrors("leaderIdeaForm", data.labelErrorMessage);
						} else{
							msgBox.info({
								content: data.errorMessage
							});
						} 
					}
				},
				error : function() {
					worklogLeaderAnno.subState = false;
					msgBox.tip({
						content: $.i18n.prop("JC_SYS_002")
					});
				}
			});
		} else {
			worklogLeaderAnno.subState = false;
			msgBox.info({
				content:"表单信息填写错误",
				success:function(){
					fnCall();
				}
			});
		}
	}
};
/**批注回复*/
worklogLeaderAnno.commentReply = function(e){
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

/**批注发送*/
worklogLeaderAnno.commentSend = function(e){
	var businessId = $('#worklogId').val();
	var annoName = $('#title').html();
	var annoParentId = $(e).attr('data');
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	var byAnnoUserId = $('#userId').val();
	var byAnnoUserDepid = $('#createUserDept').val();
	var content  = $(e).parent().prevAll('textarea').val();
	//校验表单信息
	if(!DeleteCascade.syncCheckExist("workLogAnno",businessId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogLeaderAnno.subState = false;//更新重复提交状态标识
		return;
	}else{
		if($('#commentForm').valid()){
			$.ajax({
				type : "POST",
				url : getRootPath()+"/po/worklog/annoReply.action?time=" + new Date(),
				data : {
						"annoName":annoName,
						"businessId":businessId,
					    "annoParentId":annoParentId,
					    "rootParentId":rootParentId,
					    "byAnnoUserId":byAnnoUserId,
						"byAnnoUserDepid":byAnnoUserDepid,
					    "content":content
					    },
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
						$('#comment').empty();
						worklogLeaderAnno.initAnno(businessId);
						/*$(e).parent().parent().toggle();
						$(e).parents('li').find('a[name="reply"]').show();*/
					}else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
			});
		}
	}
};

//分页对象
worklogLeaderAnno.collectTable = null;

//分页查询用户
worklogLeaderAnno.collectSearch = function () {
	var formData = $('#collectForm').serializeArray();
	$('#startDateTemp').val($("#worklogDateBegin").val());//将查询条件赋值给临时变量 add by lihongyu at 2014-11-14
	$('#endDateTemp').val($("#worklogDateEnd").val());//将查询条件赋值给临时变量  add by lihongyu at 2014-11-14
	//显示列信息
	worklogLeaderAnno.collectTableAoColumns = [
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
				reVal="<a href=\"#\" onclick=\"worklogLeaderAnno.showAnno("+full.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
			}
			return reVal;
		}}
	 ];
	
	//组装后台参数
	worklogLeaderAnno.collectTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogLeaderAnno.collectTable, aoData);
		//组装查询条件
		var worklogDateBegin = $("#worklogDateBegin").val();
		var worklogDateEnd = $("#worklogDateEnd").val();
		
		aoData.push({ "name": "worklogDateBegin", "value": worklogDateBegin});
		aoData.push({ "name": "worklogDateEnd", "value": worklogDateEnd});
	};
	
	//table对象为null时初始化
	if (worklogLeaderAnno.collectTable == null) {
		worklogLeaderAnno.collectTable = $('#collectTable').dataTable( {
			iDisplayLength: 5,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/po/worklog/getMyworklogCollect.action?time=" + new Date(),
			//bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogLeaderAnno.collectTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogLeaderAnno.collectTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,3]}]
		} );
		//setColumnVis(worklogLeaderAnno.collectTable, 6);
		
	} else {
		//table不为null时重绘表格
		worklogLeaderAnno.collectTable.fnDraw();
		//pageChange(user.oTable);
	}
};

/**初始化左侧的树的div宽高*/
worklogLeaderAnno.initLeftTreeDiv = function(){
	var content = $("#content").height();
	var headerHeight_1 = $('#header_1').height() || 0;
	var headerHeight_2 = $("#header_2").height() || 0;
	
	    $(".tree-right").css("margin-left","215px");
	//定义高度
		$("#LeftHeight").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight").height()
	
	  $("#scrollable").scroll(function() {
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight_box").addClass("fixedNav");
	        $("#LeftHeight_box").height(lh + 113)
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
	        $("#LeftHeight_box").height(lh + a)
	        //$("#LeftHeight").height()
	        $("#LeftHeight_box").removeClass("fixedNav");
	    } 
	
	  });
};
worklogLeaderAnno.noDate = function(to){
	var i = $('#'+to).find('thead th').size();
	var trStr = "<tr><td class=\"dataTables_empty\" colspan=\""+i+"\">没有匹配的记录</td></tr>";
	$('#'+to).find('tbody').append(trStr);
};


//分页对象
worklogLeaderAnno.readingStatusTable = null;
/**阅读情况*/
worklogLeaderAnno.readingStatus = function(worklogId){
	//显示列信息
	worklogLeaderAnno.readingStatusTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "readerName"},
		{mData: "readingDate"}
	 ];
	
	//组装后台参数
	worklogLeaderAnno.readingStatusTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogLeaderAnno.readingStatusTable, aoData);
		//组装查询条件
		
		aoData.push({ "name": "worklogId", "value": worklogId});
		aoData.push({ "name": "businessType", "value": 2});
	};
	
	//table对象为null时初始化
	if (worklogLeaderAnno.readingStatusTable == null) {
		worklogLeaderAnno.readingStatusTable = $('#readingStatusTable').dataTable( {
			sAjaxSource: getRootPath()+"/po/readingStatus/queryAllByDataTable.action?time=" + new Date(),
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogLeaderAnno.readingStatusTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogLeaderAnno.readingStatusTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1]}]
		} );
		//setColumnVis(worklogLeaderAnno.readingStatusTable, 6);
		
	} else {
		//table不为null时重绘表格
		worklogLeaderAnno.readingStatusTable.fnDraw();
		//pageChange(user.oTable);
	}
};


worklogLeaderAnno.updateShareUser = function(){
	var worklogId = $('#worklogId').val();
	var userIds = $('#shareUserIds-shareUserIds').val();
	if(worklogId==''){return ;}
	jQuery.ajax({
		url : getRootPath()+"/po/worklog/updateShareUser.action?time=" + new Date(),
		type : 'POST',
		data : {'id':worklogId,'shareUserIds':userIds},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
			} 
		},
		error : function() {
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
		}
	});
};

worklogLeaderAnno.dateList = [];
/**标记日志状态*/
worklogLeaderAnno.showWorklogStatus = function(){
	$('td[class*="day"]').find('b').remove();
	$.each(worklogLeaderAnno.dateList,function(i,item){
		var currentDate = item.worklogDate;
		var status = item.status;
		if(status==null){
			$('#'+currentDate).removeClass('disabled').prepend('<b><i class="fa fa-corner-mark reserve"></i></b>');
		}
		if(status=='0'){
			$('#'+currentDate).removeClass('disabled').prepend('<b><i class="fa fa-corner-mark"></i></b>');
		}
		if(status=='1'){
			$('#'+currentDate).removeClass('disabled').prepend('<b><i class="fa fa-corner-mark day-green"></i></b>');
		}
	});
};
/**根据日历当前view中的起止时间查询日志*/
worklogLeaderAnno.getWorklogStatus = function(d){
	jQuery.ajax({
		url : getRootPath()+"/po/worklog/showWorklogStatus.action?time=" + new Date(),
		type : 'POST',
		data : {'dates':d},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				worklogLeaderAnno.dateList = data.worklogList;
				worklogLeaderAnno.showWorklogStatus();
			} 
		}
	});
};

worklogLeaderAnno.annoReading = function(id,status){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/annoReading.action?time=" + new Date(),
		data : {'businessId':id,'annoType':2,'status':status},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				var dates = $('#datePickerInput').val();
				worklogLeaderAnno.getWorklogStatus(dates);
			}
		}
	});
};

/**前一篇和后一篇按钮*/
worklogLeaderAnno.prefAndNext = function(isNext){
	var d = new Date();
	var currentDate = $('#date').val();
	if(currentDate!=""){
		currentDate =  currentDate.replace(/-/g,"/");
		d = new Date(currentDate);
	}
	currentDate = d.format('yyyy-MM-dd');
	
	var tempData;
	var data;
	d = d.valueOf();
	var tipContent = "";
	if(isNext=='true'){
		d = d + (1 * 24 * 60 * 60 * 1000);
		tempData = new Date(d).format('yyyy-MM-dd');
		data =  {'worklogDateBegin':tempData+' 00:00:00'};
		tipContent += currentDate+"后不存在日志";
	}else{
		d = d - (1 * 24 * 60 * 60 * 1000);
		tempData = new Date(d).format('yyyy-MM-dd');
		data =  {'worklogDateEnd':tempData+' 00:00:00'};
		tipContent += currentDate+"前不存在日志";
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/getPrefAndNext.action?time=" + new Date(),
		data : data,
		dataType : "json",
		success : function(data) {
			if (data.success=='true') {
				worklog = data.worklogList[0];
				worklogLeaderAnno.initWorklogData(worklog);
				$('#date').val(worklog.worklogDate);
				$("#datePicker").datepicker_worklog('setDate',worklog.worklogDate);
			}else{
				msgBox.tip({
					content: tipContent,
					type:'fail'
				});
			}
		},
		error:function() {
			msgBox.tip({
				content: tipContent
			});
			worklogLeaderAnno.clearWorklog();
		}
	});
};

/**查看领导批注*/
worklogLeaderAnno.showAnno = function(dataid){
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
worklogLeaderAnno.closeAnno = function(){
	$('#annoComment').empty();
};
/**
 * 初始化方法
 */
jQuery(function($){
	$('#datePickerInput').change(function(){
		var dates = $(this).val();
		worklogLeaderAnno.getWorklogStatus(dates);
	});
	
	selectControl.init("shareUserNames1","shareUserIds-shareUserIds",true,true);//人员选择树 参数divid 设置的id-name 多选 人员
	
	$("#worklogDateBegin,#worklogDateEnd").datepicker();
	$("#datePicker").datepicker_worklog();
	worklogLeaderAnno.initLeftTreeDiv();
	//绑定汇总查询
	$('#collectSearch').click(function(){
		worklogLeaderAnno.collectSearch();
	});
	//绑定阅读情况查询
	$('#readingStatus').click(function(){
		var worklogId = $('#worklogId').val();
		if(worklogId!='' && worklogId!=null){
			worklogLeaderAnno.readingStatus(worklogId);
		}
	});
	//绑定上一篇和下一篇
	$('#pref,#next').click(function(){
		worklogLeaderAnno.prefAndNext($(this).attr('isNext'));
	});
	//绑定日志汇总
	$('#collect').click(function(){
		$('#collectForm')[0].reset();
		$('#collectTable tbody').empty();
		$('#worklogDateBegin').val(new Date().format('yyyy-MM-dd'));
		$('#worklogDateEnd').val(new Date().format('yyyy-MM-dd'));
		worklogLeaderAnno.collectSearch();
		$('#worklog_summary').modal('show');
	});
	//绑定日志汇总
	$('#collectReset').click(function(){
		$('#worklogDateBegin').val(new Date().format('yyyy-MM-dd'));
		$('#worklogDateEnd').val(new Date().format('yyyy-MM-dd'));
	});
	
	//绑定日志汇总导出Excel
	$('#exportExcel').click(function(){
		common.exportExcel();
	});
	
	//绑定批注”回复“
	$('#comment').on('click','a[name="reply"]',function(){
		worklogLeaderAnno.commentReply(this);
	});
	//绑定批注”发送“
	$('#comment').on('click','a[name="send"]',function(){
		worklogLeaderAnno.commentSend(this);
	});
	//绑定批注内容清除
	$('#leaderIdeaForm').on('click','#clearleaderIdea',function(){$('#leaderIdeaContent').text('');});
	
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){worklogLeaderAnno.saveLeaderIdeaForm();});
	//绑定共享范围保存方法
	$("#updateShareUser").click(function(){worklogLeaderAnno.updateShareUser();});
	
	worklogLeaderAnno.initWorklog();
	
	window.testCallback = function(args){
		if(typeof(args.pickerObject)=="object"){
			var pickerObject = args.pickerObject;
			var selectedDate = pickerObject.getDate();
			var year = selectedDate.getFullYear();
			var month = selectedDate.getMonth();
			var date = selectedDate.getDate();
			//todo:add your code below
			$('#date').val(pickerObject.formatDate(pickerObject.getDate()));
			worklogLeaderAnno.initWorklog();
		} 
		
	}
});
