
/**今日日志input id前缀*/
anno.todayLogIdPref = 'todayLog-';
/**明日提醒input id前缀*/
anno.tomorrowRemindIdPref = 'tomorrowRemind-';

/**日志详细今日日志及明日提醒动态添加行模版*/
anno.autoTrDetailStr = "<tr>"+
								"<td>第<span></span>条</td>"+
								"<td>" +
								"</td>"+
								"</tr>";
/**日志详细今日日程动态添加行模版*/
anno.autoTrDetailTodayDiaryStr = "<tr>"+
									"<td>第<span></span>条</td>"+
									"<td name=\"todayDiaryTitle\">" +
									"</td>"+
									"</tr>";


/**详细页待办任务动态添加行模版*/
anno.autoTrDetailWorkTaskStr =  "<tr>"+
										"<input type=\"hidden\" name=\"taskContent\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"startTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"endTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"actEndTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"userId\" value=\"\"/>"+
										"<td name=\"taskTitle\"></td>"+
										"<td name=\"taskProc\"></td>"+
										"<td name=\"sponsorId\"></td>"+
										"<td name=\"actStartTime\"></td>"+
										"</tr>";

/**
 * 动态行绑定事件
 */
anno.autoTrBindEvent = function(to,trStr,idPref){
	$('#'+to).on('blur','input[class^="addLine"]',function(){
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html();//取最后一行行号
		var currentNo = $(this).parent().parent().prev().find('span').html();//当前行号
		if(currentNo==lastNo){//判断是不是最后一行
			if($(this).val()!=''){//判断是不是为空
				anno.initTr(to,trStr,1,idPref);
			}
		};
	});
};

/**
 * 初始添加行公共方法to:目标容器id,trStr:添加内容,n:添加行数,idPref:id前缀
 */
anno.initTr = function(to,trStr,n,idPref){
	for(var i=1;i<=n;i++){
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		if(idPref){
			$('#'+to).find('tbody tr:last input').attr('id',idPref+currentNo);
		};
	}
	//绑定事件
	/*if(idPref){
		anno.autoTrBindEvent(to,trStr,idPref);
	};*/
};

/**
 * 详细页初始添加行公共方法to:目标容器id,trStr:添加内容,
 */
anno.initDetailTr = function(to,trStr,d){
	
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		$('#'+to).find('tbody tr:last td:last').html(d);
	
};


/**初始化详细页待办任务列表*/
anno.workTaskDetailTable = null;//分页对象
anno.initDetailWorkTask = function(currentDate,to){
	//显示列信息
	anno.workTaskTableColumns = [
	                                        //不需要排序的列直接用mData function方式
	                                        {mData: "taskName"},
	                                        {mData: "taskProc"},
	                                        {mData: "sponsor"},
	                                        {mData: "actStartTime"}
	                                        ];
		
	anno.aoColumnDefs = [{bSortable: false, aTargets: [0,1,2,3]}];
	
	//组装后台参数
	anno.workTaskTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(anno.workTaskDetailTable, aoData);
		
		aoData.push({ "name": "startDate", "value": currentDate});
		aoData.push({ "name": "directorId", "value":$('#worklogCreateUserId').val()});
	};
	
	//table对象为null时初始化
	if (anno.workTaskDetailTable == null) {
		anno.workTaskDetailTable = $('#'+to).dataTable( {
			sAjaxSource: getRootPath()+"/po/worklog/queryWorkTask.action",
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: anno.workTaskTableColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				anno.workTaskTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
			aoColumnDefs: anno.aoColumnDefs
		} );
		
	} else {
		//table不为null时重绘表格
		anno.workTaskDetailTable.fnDraw();
	}
	
};

/**初始化批注列表*/
anno.initAnno = function(worklogId){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryAnno.action",
		data : {"businessId":worklogId},
		async : false,
		dataType : "json",
		success : function(data) {
			if (data) {
				var liStr='';
				if(data.length==0){
					liStr +="<li class=\"clearfix m-b input-group\">"+
		                    "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有批注内容</p></li>";
				}
				var currentUser = $('#currentUser').val();
				for(var i=0;i<data.length;i++){
					
					var annoParent = data[i];
					liStr += "<li class=\"clearfix m-b input-group\">"+
				                    "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\""+annoParent.id+"\">"+
				                    "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>"+annoParent.annoUserIdValue+"：</strong>"+annoParent.content+
				                    "<span>"+annoParent.annoDate+"</span>"+
					                "</p>"+
					                "<div class=\"input-group-btn\">"+
					                    "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"anno"+i+"\">回 复</a>"+
					                "</div>";
					
					liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
				                 "<textarea rows=\"3\" name=\"replyContent\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":\"></textarea>"+
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
					                        "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>"+annoReply.annoUserIdValue+"回复"+annoReply.parentUserName+"：</strong>"+annoReply.content+
					                        "<span>"+annoReply.annoDate+"</span>"+
					                   "</p>"+
					                    "<div class=\"input-group-btn\">"+
					                        "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"reply"+j+"\">回 复</a>"+
					                    "</div>"+
				                    "</div>"+
				                    
				                    "<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
					                 "<textarea rows=\"3\" name=\"replyContent\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+":\"></textarea>"+
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



/**
 * 清空表单方法 
 */
anno.clearForm = function(){
	$('#annoForm')[0].reset();
	$('#reminderForm')[0].reset();
	$('#userIds').val('');
	$('#userNames').text('');
	$('#fileupload table tbody').empty();
	$("#shareUserIds-shareUserIds").select2("data","");
	hideErrorMessage();
};

/**初始化今日日程*/
anno.initDiary = function(currentDate,to,tr){
	var starttime = currentDate+" 00:00:00";
	var endtime = currentDate+" 23:59:59";
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryDiary.action",
		data : {"starttime":starttime,"endtime":endtime,"possessorId":$('#worklogCreateUserId').val()},
		dataType : "json",
		success : function(data) {
			if (data) {
				if(data.length==0){
					anno.noDate(to);
					return;
				}
				for(var i=0;i<data.length;i++){
					var diary = data[i];
					anno.initTr(to,tr,1);
					$('#'+to).find('tbody tr:last td[name="todayDiaryTitle"]').html(diary.content);
				}
			}else{
				anno.noDate(to);
			}
		}
	});
};

anno.noDate = function(to){
	var i = $('#'+to).find('thead th').size();
	var trStr = "<tr><td class=\"dataTables_empty\" colspan=\""+i+"\">没有匹配的记录</td></tr>";
	$('#'+to).find('tbody').append(trStr);
};






/**添加成功清空表单数据*/
anno.initForm = function(){
	anno.clearForm();
	anno.clearDate();
};

/**清除详情页数据*/
anno.clearDetail = function(){
	$('#detailTodayLog tbody').empty();
	$('#detailTomorrowRemind tbody').empty();
	$('#detailTodayDiary tbody').empty();
	$('#detailTask tbody').empty();
	$('#comment').empty();
	//清除附件信息
	clearTable();
	clearAttachlist();
};

/**查看日志详细*/
anno.showDetail = function(id){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if(data.id != null){
				//清除详情页数据
				anno.clearDetail();
				$('#detailId').val(data.id);
				$('#worklogCreateUserId').val(data.createUser);
				$('#worklogCreateUserDept').val(data.createUserDept);
				//回显日志详细数据
				$('#detailTitle').html(data.title);
				$('#detailWorklogDate').html(data.worklogDate);
				$('#detailSentimentRemark').html(data.sentimentRemark);
				$('#detailShareUserNames').html(data.shareUserNames);
				//填充今日日程
				anno.initDiary(data.worklogDate,'detailTodayDiary',anno.autoTrDetailTodayDiaryStr);
				//填充待办任务
				//anno.initWorkTask(data.worklogDate,'detailTask',anno.autoTrDetailWorkTaskStr);
				anno.initDetailWorkTask(data.worklogDate,'detailTask');
				//填充批注
				var targetUser = $('#currentUserId').val();//当前登录人
				var sourceUser = data.createUser;
				anno.initAnno(data.id);
				anno.isLeader(sourceUser,targetUser,data.id);
				
				
				//填充今日日志
				var todayLogs = data.todayLogs;
				if(todayLogs==null||todayLogs.length==0){
					anno.noDate('detailTodayLog');
				}
				if(todayLogs && todayLogs.length > 0) {
					for(var i=0;i<todayLogs.length;i++){
						var todayLog = todayLogs[i];
						anno.initDetailTr('detailTodayLog',anno.autoTrDetailStr,todayLog.content);
					}
				}
				
				//填充明日提醒
				var tomorrowReminds = data.tomorrowReminds;
				if(tomorrowReminds==null||tomorrowReminds.length==0){
					anno.noDate('detailTomorrowRemind');
				}
				if(tomorrowReminds && tomorrowReminds.length > 0) {
					for(var i=0;i<tomorrowReminds.length;i++){
						var tomorrowRemind = tomorrowReminds[i];
						anno.initDetailTr('detailTomorrowRemind',anno.autoTrDetailStr,tomorrowRemind.content);
					}
				}
				
				//设置附件数据
				$('#businessId').val(data.id);
				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
				showAttachList();//附件填出层数据回显方法
				echoAttachList();//附件列表数据回显方法
				anno.annoReading(data.id,data.status);
				if(id!=null){
					$('#worklog-detail').modal('show');
				}else{
					$('#New-worklog').modal('hide');
					$('#worklog-detail').modal('show');
				}
			}else{
				msgBox.tip({
					type : "fail",
					content : $.i18n.prop("JC_SYS_128")
				});
			}
		},error:function(data){
			msgBox.tip({
				type : "fail",
				content : $.i18n.prop("JC_SYS_128")
			});
		}
	});
};

anno.isLeader = function(sourceUser,targetUser,dataId){
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "true"||sourceUser == targetUser){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
				$('div[class="input-group-btn"]').show();//领导批注区域整体展示
				$("#worklogLeaderIdeaForm").show();//批注回复内容展示
			}else{
				$('div[class="input-group-btn"]').hide();//领导批注区域整体展示
				$("#worklogLeaderIdeaForm").hide();//批注回复内容展示
			}
		}
	});
}
/**更新批注的阅读状态*/
anno.annoReading = function(id,status){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/annoReading.action",
		data : {'businessId':id,'annoType':2,'status':status},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				//alert('批注阅读状态更新成功');
			}
		}
	});
};

/**保存领导批注*/
anno.saveWorklogLeaderIdeaForm = function(){
	var worklogId = $('#detailId').val();
	if(worklogId=='')return;
	//校验是否重复提交
	if (anno.subState)return;
	anno.subState = true;
	//是否删除校验 add by lihongyu at 2014-11-5 start
	if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		anno.subState = false;
		return;
	}
	//是否删除校验 add by lihongyu at 2014-11-5 end
	//校验表单信息
	if ($("#worklogLeaderIdeaForm").valid()) {
		var url = getRootPath()+"/po/worklog/saveAnno.action?time=" + new Date();
		//将表单序列化成json格式
		var formData = $("#worklogLeaderIdeaForm").serializeArray();
		var annoName = $('#detailTitle').html();
		var createUserId = $('#worklogCreateUserId').val();
		var createUserDept = $('#worklogCreateUserDept').val();
		formData.push({"name": "annoName", "value": annoName});
		formData.push({"name": "businessId", "value": worklogId});
		formData.push({"name": "annoType", "value": 2});
		formData.push({"name" : "byAnnoUserId","value" :createUserId});
		formData.push({"name" : "byAnnoUserDepid","value" :createUserDept});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				anno.subState = false;//更新重复提交状态标识
//				getToken();//更新token
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$('#worklogLeaderIdeaContent').val('');
					$('#comment').empty();
					$("#token").val(data.token);
					anno.initAnno(worklogId);
				} else {
					anno.subState = false;//更新重复提交状态标识
					if(data.labelErrorMessage){
						showErrors("worklogLeaderIdeaForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					} 
				}
			},
			error : function() {
				anno.subState = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
			}
		});
	} else {
		anno.subState = false;
		msgBox.info({
			content:"表单信息填写错误",
			success:function(){
				fnCall();
			}
		});
	}
};

/**批注回复*/
anno.commentReply = function(e){
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
anno.commentSend = function(e){
	var businessId = $('#detailId').val();
	var annoName = $('#detailTitle').html();
	var annoParentId = $(e).attr('data');
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	var byAnnoUserId = $('#worklogCreateUserId').val();
	var byAnnoUserDepid = $('#worklogCreateUserDept').val();
	var content  = $(e).parent().prevAll('textarea').val();
	//var content = $(e).parent().parent().find("textarea").val();
	//是否删除校验 add by lihongyu at 2014-11-5 start
	if(!DeleteCascade.syncCheckExist("workLogAnno",businessId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		anno.subState = false;
		return;
	}
	//是否删除校验 add by lihongyu at 2014-11-5 end
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
					anno.initAnno(businessId);
				}else{
					msgBox.info({
						content: data.errorMessage
					});
				}
			}
		});
	}else {
		anno.subState = false;//更新重复提交状态标识
		msgBox.info({
			content:"表单信息填写错误",
			success:function(){
//				fnCall();
			}
		});
	}
};



/**初始化待办任务汇报数据*/
anno.initTaskReport = function(e){
	$('#reportForm #reportTaskId').val($(e).parents('tr').find('input[name="taskId"]').val());
	$('#reportForm #reEventTitle').val($(e).parents('tr').find('input[name="taskTitle"]').val());
	$('#reportForm #reContent').val($(e).parents('tr').find('input[name="taskContent"]').val());
	var taskProc =  $(e).parents('tr').find('input[name="taskProc"]').val();
	$('#reportForm #reportProc').val(taskProc);
	if(taskProc >=100){
		$('#reportProc').attr("readonly","readonly");
		$('#reportContent').attr("readonly","readonly");
	}else{
		$('#reportProc').removeAttr("readonly");
		$('#reportContent').removeAttr("readonly");
	}
	hideErrorMessage();
	$('#reportForm #reportContent').val('');
};

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
//@ sourceURL=myWorklogCalendar.js