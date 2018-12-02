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

worklogShareList = {};

/**日志详细今日日志及明日提醒动态添加行模版*/
worklogShareList.autoTrDetailStr = "<tr>"+
								"<td>第<span></span>条</td>"+
								"<td>" +
								"</td>"+
								"</tr>";

worklogShareList.queryWorklogShare = function(){
	var formData = $('#queryWorklogShareForm').serializeArray();
};

//分页对象
worklogShareList.queryWorklogShareTable = null;

//分页查询用户
worklogShareList.queryWorklogShareSearch = function () {
	/*if(userId==''||userId==null){
		msgBox.info({
			content: "请选择人员！"
		});
		return ;
	}*/
	
	
	//显示列信息
	worklogShareList.queryWorklogShareAoColumns = [
	    //不需要排序的列直接用mData function方式
	    {mData: "title"},
		{mData: "worklogDate"},
		{mData: "shareWorklogUserId",mRender : function(mData, type, full){
				return full.shareWorklogUserName;
			}
		},
		{mData: function(source) {
			var contentStrs = '<a class="a-icon i-new m-r-xs" href="javascript:void(0)" role="button" data-toggle="modal" data="'+source.id+'" name="showDetail">查看详细</a>';
			return contentStrs;
		}}
	 ];
	
	//组装后台参数
	worklogShareList.queryWorklogShareFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogShareList.queryWorklogShareTable, aoData);
		//组装查询条件
		var worklogDateBegin = $("#worklogDateBegin").val();
		var worklogDateEnd = $("#worklogDateEnd").val();
		var shareWorklogUserId = $("#leftRight-leftRight").val();
		
		aoData.push({ "name": "worklogDateBegin", "value": worklogDateBegin});
		aoData.push({ "name": "worklogDateEnd", "value": worklogDateEnd});
		if(shareWorklogUserId!=null && shareWorklogUserId!=''){
			aoData.push({"name": "shareWorklogUserId", "value": shareWorklogUserId});
		}
	};
	
	//table对象为null时初始化
	if (worklogShareList.queryWorklogShareTable == null) {
		worklogShareList.queryWorklogShareTable = $('#queryWorklogShareTable').dataTable({
			iDisplayLength: worklogShareList.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/po/worklog/getShareWorklogPage.action?time=" + new Date(),
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogShareList.queryWorklogShareAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogShareList.queryWorklogShareFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,2,3]}]
		} );
		//setColumnVis(worklogShareList.queryWorklogShareTable, 6);
		
	} else {
		//table不为null时重绘表格
		worklogShareList.queryWorklogShareTable.fnDraw();
		//pageChange(user.oTable);
	}
};

/**添加没有匹配数据行*/
worklogShareList.noDate = function(to){
	var i = $('#'+to).find('thead th').size();
	var trStr = "<tr><td class=\"dataTables_empty\" colspan=\""+i+"\">没有匹配的记录</td></tr>";
	$('#'+to).find('tbody').append(trStr);
};


/**
 * 初始添加行公共方法to:目标容器id,trStr:添加内容,n:添加行数
 */
worklogShareList.initTr = function(to,trStr,n){
	for(var i=1;i<=n;i++){
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
	}
};

/**
 * 详细页初始添加行公共方法to:目标容器id,trStr:添加内容,
 */
worklogShareList.initDetailTr = function(to,trStr,d){
	
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		$('#'+to).find('tbody tr:last td:last').html(d);
	
};

/**初始化今日日程*/
worklogShareList.initDiary = function(currentDate,to,tr){
	var starttime = currentDate;
	var endtime = currentDate;
	var userId = $('#shareUserId').val();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryDiary.action",
		data : {"starttime":starttime,"endtime":endtime,"possessorId":userId},
		dataType : "json",
		success : function(data) {
			if (data) {
				if(data.length==0){
					worklogShareList.noDate(to);
				}
				for(var i=0;i<data.length;i++){
					var diary = data[i];
					worklogShareList.initTr(to,tr,1);
					$('#'+to).find('tbody tr:last td[name="todayDiary-title"]').html(diary.content);
				}
			}else{
				worklogShareList.noDate(to);
			}
		}
	});
};

/**初始化详细页待办任务列表*/
worklogShareList.workTaskDetailTable = null;//分页对象
worklogShareList.initDetailWorkTask = function(currentDate,to){
	//显示列信息
	worklogShareList.workTaskTableColumns = [
	                                        //不需要排序的列直接用mData function方式
	                                        {mData: "taskName"},
	                                        {mData: "taskProc"},
	                                        {mData: "sponsor"},
	                                        {mData: "actStartTime"}
	                                        ];
		
	worklogShareList.aoColumnDefs = [{bSortable: false, aTargets: [0,1,2,3]}];
	
	
	//组装后台参数
	worklogShareList.workTaskTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogShareList.workTaskDetailTable, aoData);
		var userId = $('#shareUserId').val();
		aoData.push({ "name": "startDate", "value": currentDate});
		aoData.push({ "name": "directorId", "value": userId});
	};
	
	//table对象为null时初始化
	if (worklogShareList.workTaskDetailTable == null) {
		worklogShareList.workTaskDetailTable = $('#'+to).dataTable( {
			sAjaxSource: getRootPath()+"/po/worklog/queryWorkTask.action",
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogShareList.workTaskTableColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogShareList.workTaskTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
			aoColumnDefs: worklogShareList.aoColumnDefs
		} );
		//setColumnVis(worklogCalendar.workTaskDetailTable, 0);
		
	} else {
		//table不为null时重绘表格
		worklogShareList.workTaskDetailTable.fnDraw();
	}
	
};

/**初始化批注列表 只读*/
worklogShareList.initAnnoOnly = function(worklogId){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryAnno.action",
		data : {"businessId":worklogId},
		dataType : "json",
		success : function(data) {
			//console.log(data);
			if (data) {
				var liStr='';
				if(data.length==0){
					liStr +="<li class=\"clearfix m-b input-group\">"+
		                    "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有批注内容</p></li>";
				}
				for(var i=0;i<data.length;i++){
					
					var annoParent = data[i];
					liStr += "<li class=\"clearfix m-b input-group\">"+
				                    "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\""+annoParent.id+"\">"+
				                    "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+annoParent.annoUserIdValue+"：</strong>"+annoParent.content+
				                    "<span>"+annoParent.annoDate+"</span>"+
					                "</p>";
					               
					                
					
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
				                    "</div>";
						}
					}
					liStr += "</li>";
				}
				$('#comment').append(liStr);
			}
		}
	});
};
//worklogShareList.initAnno = function(worklogId){
//	$.ajax({
//		type : "GET",
//		url : getRootPath()+"/po/worklog/queryAnno.action",
//		data : {"businessId":worklogId},
//		dataType : "json",
//		success : function(data) {
//			//console.log(data);
//			if (data) {
//				var liStr='';
//				if(data.length==0){
//					liStr +="<li class=\"clearfix m-b input-group\">"+
//		                    "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有批注内容</p></li>";
//				}
//				for(var i=0;i<data.length;i++){
//					
//					var annoParent = data[i];
//					liStr += "<li class=\"clearfix m-b input-group\">"+
//				                    "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\""+annoParent.id+"\">"+
//				                    "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+annoParent.annoUserIdValue+"：</strong>"+annoParent.content+
//				                    "<span>"+annoParent.annoDate+"</span>"+
//					                "</p>";
//					               
//					                
//					
//					//批注回复
//					var replyList = annoParent.annoReplyList;
//					if(replyList){
//						for(var j=0;j<replyList.length;j++){
//							var annoReply = replyList[j];
//							liStr += "<div class=\"dis-row\">"+
//					                    "<p class=\"dialog-text input-group\" data=\""+annoReply.id+"\">"+
//					                    	"<input type=\"hidden\" name=\"\">"+
//					                        "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+annoReply.annoUserIdValue+"回复"+annoReply.parentUserName+"：</strong>"+annoReply.content+
//					                        "<span>"+annoReply.annoDate+"</span>"+
//					                   "</p>"+
//				                    "</div>";
//						}
//					}
//					liStr += "</li>";
//				}
//				$('#comment').append(liStr);
//			}
//		}
//	});
//};
/**初始化批注列表*/
worklogShareList.initAnno = function(worklogId){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryAnno.action",
		data : {"businessId":worklogId},
		dataType : "json",
		success : function(data) {
			if (data) {
				var liStr='';
				if(data.length==0){
					liStr +="<li class=\"clearfix m-b input-group\">"+
		                    "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有批注内容</p></li>";
				}
				var currentUser = $('#displayName').val();//当前登录人
				for(var i=0;i<data.length;i++){
					var annoParent = data[i];
					liStr += "<li class=\"clearfix m-b input-group\">"+
				                    "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\""+annoParent.id+"\">"+
				                    "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+annoParent.annoUserIdValue+"：</strong>"+annoParent.content+
				                    "<span>"+annoParent.annoDate+"</span>"+
					                "</p>";
					 liStr += "<div class=\"input-group-btn  p-l\">" + 
			          "<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"anno"+i+"\" name=\"reply\">回 复</a></div>";
					 liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
		                     "<textarea name=\"replayAnno\" rows=\"3\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":\"></textarea>"+
		                     "<div class=\"input-group-btn  p-l\">"+
		                     " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
		                     "</div></div>";
					//批注回复
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
							liStr += "<div class=\"input-group-btn  p-l\">" + 
							         	"<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"reply"+j+"\" name=\"reply\">回 复</a></div>" +
							         "</div>" +
							         "<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
							         "<textarea name=\"replayAnno\" rows=\"3\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+":\"></textarea>"+
							         "<div class=\"input-group-btn p-l\">"+
							         " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
							         "</div></div>";
						}
					}
					liStr += "</li>";
				}
				$('#comment').append(liStr);
			}
		}
	});
};
/**
 * 领导批注回复方法
 */
worklogShareList.commentReply = function(e) {
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

/**
 * 领导批注发送方法
 */
worklogShareList.commentSend = function(e) {
	//校验是否重复提交
	if (worklogShareList.subState)return;
	worklogShareList.subState = true;
	var businessId = $('#detailId').val();
	var annoParentId = $(e).attr('data');
	var byAnnoUserId = $('#shareUserId').val();
	var annoName = $('#detailTitle').text();
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	var content = $(e).parent().parent().find("textarea").val();
	if(!DeleteCascade.syncCheckExist("workLogAnno",businessId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogShareList.subState =false;
		return;
	}else{
		if ($("#leaderIdeaReplayForm").valid()) {
			$.ajax({
				type : "POST",
				url : getRootPath()+"/po/workTask/annoReply.action?time=" + new Date(),
				data : {
					"businessId" : businessId,
					"annoName" : annoName,
					"annoParentId" : annoParentId,
					"rootParentId" : rootParentId,
					"byAnnoUserId" : byAnnoUserId,
					"content" : content,
					"annoType":2
				},
				dataType : "json",
				success : function(data) {
					worklogShareList.subState = false;//更新重复提交状态标识
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								$('#comment').empty();
								worklogShareList.initAnno(businessId);
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
			worklogShareList.subState = false;//更新重复提交状态标识
			msgBox.info({
				content:$.i18n.prop("JC_SYS_067"),
				success:function(){
					fnCall();
				}
			});
		}
	}
};
/**保存领导批注*/
worklogShareList.saveLeaderIdeaForm = function(){
	var worklogId = $('#detailId').val();
	if(worklogId=='')return;
	//校验是否重复提交
	if (worklogShareList.subState)return;
	worklogShareList.subState = true;
	if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogShareList.subState = false;//更新重复提交状态标识
		return;
	}else{
	//校验表单信息
	if ($("#leaderIdeaForm").valid()) {
		var url = getRootPath()+"/po/anno/save.action?time=" + new Date();
		//将表单序列化成json格式
		var formData = $("#leaderIdeaForm").serializeArray();
		formData.push({"name": "businessId", "value": worklogId});
		formData.push({"name": "annoType", "value": 2});
		formData.push({"name": "annoName", "value": $('#detailTitle').text()});
		formData.push({"name":"byAnnoUserId","value":$('#shareUserId').val()});
//		formData.push({"name":"byAnnoUserDepid","value":$('#annoCreateUserDept').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				worklogShareList.subState = false;//更新重复提交状态标识
//				getToken();//更新token
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$('#leaderIdeaContent').val('');
					$('#comment').empty();
					worklogShareList.initAnno(worklogId);
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
				worklogShareList.subState = false;//更新重复提交状态标识
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
			}
		});
	} else {
		worklogShareList.subState = false;//更新重复提交状态标识
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
   }
};
//领导批注框清空
worklogShareList.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};
//领导批注修改 李洪宇 2014-08-29 结束
/**清除详情页数据*/
worklogShareList.clearDetail = function(){
	$('#detailTodayLog tbody').empty();
	$('#detailTomorrowRemind tbody').empty();
	$('#detailTodayDiary tbody').empty();
	$('#detailTask tbody').empty();
	$('#comment').empty();
	$('#shareUserNames').empty();
	//清除附件信息
	clearTable();
	clearAttachlist();
};

/**查看日志详细*/
worklogShareList.showDetail = function(id){
	worklogShareList.getIsLeaderNew();
	hideErrorMessage();
	$("#leaderIdeaContent").val("");
	d = {"id": id};
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/get.action",
		data : d,
		dataType : "json",
		async:false,
		success : function(data) {
			if (data) {
				//清除详情页数据
				worklogShareList.clearDetail();
				$('#detailId').val(data.id);
				//回显日志详细数据
				var currentDate = new Date(Date.parse(data.worklogDate.replace(/-/g,"/")));
				var d = currentDate.format('yyyy-MM-dd');
				var w = currentDate.getWeek();
				//设置日志标题
				$('#detailWorklogTitle').html(d+'('+w+')');
				$('#detailTitle').html(data.title);
				$('#detailWorklogDate').html(data.worklogDate);
				$('#detailSentimentRemark').html(data.sentimentRemark);
				$('#shareUserNames').html(data.shareUserNames);
				//填充今日日程
				//worklogShareList.initDiary(data.worklogDate,'detailTodayDiary',worklogShareList.autoTrDetailTodayDiaryStr);
				//填充待办任务
				//worklogShareList.initDetailWorkTask(data.worklogDate,'detailTask');
				//填充批注
//				worklogShareList.initAnno(data.id);
//				var reV=$('#isLeaderTemp').val();
//				if(reV=='1'){
//					worklogShareList.initAnno(data.id);
//					$("#leaderIdeaForm").css('display', 'block'); //显示  
//				}else{
					worklogShareList.initAnnoOnly(data.id);
					$("#leaderIdeaForm").css('display','none'); //隐藏  
//				}
				//填充今日日志
				var todayLogs = data.todayLogs;
				if(todayLogs.length==0){
					worklogShareList.noDate('detailTodayLog');
				}
				if(todayLogs && todayLogs.length > 0) {
					for(var i=0;i<todayLogs.length;i++){
						var todayLog = todayLogs[i];
						worklogShareList.initDetailTr('detailTodayLog',worklogShareList.autoTrDetailStr,todayLog.content);
					}
				}
				
				//填充明日提醒
				var tomorrowReminds = data.tomorrowReminds;
				if(tomorrowReminds.length==0){
					worklogShareList.noDate('detailTomorrowRemind');
				}
				if(tomorrowReminds && tomorrowReminds.length > 0) {
					for(var i=0;i<tomorrowReminds.length;i++){
						var tomorrowRemind = tomorrowReminds[i];
						worklogShareList.initDetailTr('detailTomorrowRemind',worklogShareList.autoTrDetailStr,tomorrowRemind.content);
					}
				}
				
				//设置附件数据
				$('#businessId').val(data.id);
				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
				showAttachList();//附件填出层数据回显方法
				echoAttachList();//附件列表数据回显方法				
				//更新阅读情况
				$.ajax({
					type : "GET",
					url : getRootPath()+"/po/worklog/savaReadingStatus.action",
					data : {'id':data.id,'createUser':data.createUser},
					dataType : "json",
					success : function(data) {
						
					}
				});
				
			}
		}
	});
	$('#worklog-detail').modal('show');
};


worklogShareList.leftRightSelect = function(){
	
	leftRightSelect.init({
		containerId:"leftAndRightSelectDiv",
		moduleId:"leftRight-leftRight",
		isCheck:true,
		url:getRootPath()+"/po/worklog/queryForShareLeftRight.action",
		title:"共享人"
	});
};
/**判断当前登录用户与被查看日志所属人员是否为领导关系*/
worklogShareList.getIsLeaderNew=function(){
	$.ajax({
		type : "GET",
		cache: false,
		async:false,
		url : getRootPath()+"/po/worklog/getIsLeader.action?time=" + new Date(),
		data : {"id":$('#shareUserId').val(),"createUserId":$('#shareUserId').val()},
		dataType : "json",
		success : function(data) {
			$('#isLeaderTemp').val(data);
		}
	});
};
/**查看日志详细*/
worklogShareList.showDetailBefore = function(id){
	d = {"id": id};
	$.ajax({
		type : "GET",
		cache: false,
		async:false,
		url : getRootPath()+"/po/worklog/get.action?time=" + new Date(),
		data : d,
		dataType : "json",
		success : function(data) {
			if (data) {
				//将日志创建人赋值
				$('#shareUserId').val(data.createUser);
			}
		}
	});
};
//初始化
jQuery(function($) 
{
	worklogShareList.leftRightSelect();
	//计算分页显示条数
	worklogShareList.pageRows = TabNub>0 ? TabNub : 10;
	$('#worklogDateBegin').val(new Date().format('yyyy-MM-dd'));
	$('#worklogDateEnd').val(new Date().format('yyyy-MM-dd'));
	
	worklogShareList.queryWorklogShareSearch();
	//绑定查询
	$('#queryInfo').click(function(){
		worklogShareList.queryWorklogShareSearch();
	});
	//绑定重置
	$('#resetInfo').click(function(){
		$('#worklogDateBegin').val(new Date().format('yyyy-MM-dd'));
		$('#worklogDateEnd').val(new Date().format('yyyy-MM-dd'));
		$("#leftRight-leftRight").select2("data","");
	});
	 //绑定详情页展开收起
    $('a[name="detailShowAndHide"]').on('click',function(){
    	$('a[name="detailShowAndHide"]').toggle();
    	$(this).parent().parent().next().toggle();
    });
    //绑定添加页展开收起
    $('a[name="showAndHide"]').on('click',function(){
    	$('a[name="showAndHide"]').toggle();
    	$(this).parent().parent().next().toggle();
    });
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//日历形式跳转
	$('#showCalendar').click(function(){
		loadrightmenu("/po/worklog/worklogShareSkip.action?time="+new Date());
	});
	
	//查看日志详细
	$('#queryWorklogShareTable').on('click','a[name="showDetail"]',function(){
		var id = $(this).attr('data');
		worklogShareList.showDetailBefore(id);
		worklogShareList.showDetail(id);
	});
	
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){worklogShareList.saveLeaderIdeaForm();});
	//绑定批注"回复"
	$('#comment').on('click','a[name="reply"]',function(){
		worklogShareList.commentReply(this);
	});
	//绑定批注"发送"
	$('#comment').on('click','a[name="send"]',function(){
		worklogShareList.commentSend(this);
	});
	//绑定批注内容清除
	$("#clearleaderIdea").click(function(){
		worklogShareList.clearleaderIdea();
	});
});