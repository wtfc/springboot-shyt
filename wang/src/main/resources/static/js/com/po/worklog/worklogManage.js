var common = {};
//导出到Excel
common.exportExcel=function(){
	//组装查询条件
	var userid = '';
	if($("#userId").length>0){
		userid=$("#userId").val();
	}
//	var worklogDateBegin = $("#worklogDateBegin").val();
//	var worklogDateEnd = $("#worklogDateEnd").val();
	
	var worklogDateBegin = $("#startDateTemp").val();
	var worklogDateEnd = $("#endDateTemp").val();
	
	var url;
	if(userid!='' && userid!=null && userid!='0'){
		url = getRootPath()+"/po/worklog/exportExcel.action?worklogDateBegin="+worklogDateBegin+"&worklogDateEnd="+worklogDateEnd+"&createUser="+userid+"&time="+new Date();
	}else{
		url = getRootPath()+"/po/worklog/exportExcel.action?worklogDateBegin="+worklogDateBegin+"&worklogDateEnd="+worklogDateEnd+"&time="+new Date();
	}
	window.location.href=url;
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

worklogManage = {};
/**日志详细今日日程动态添加行模版*/
worklogManage.autoTrDetailTodayDiaryStr = "<tr>"+
									"<td>第<span></span>条</td>"+
									"<td name=\"todayDiaryTitle\">" +
									"</td>"+
									"</tr>";

/**日志详细今日日志及明日提醒动态添加行模版*/
worklogManage.autoTrDetailStr = "<tr>"+
								"<td>第<span></span>条</td>"+
								"<td>" +
								"</td>"+
								"</tr>";
var currentObject = worklogManage;
/**
 * 重复提交标识
 */
worklogManage.subState = false;

var tree = null;

var worklogManageTreeToPage = {};

worklogManageTreeToPage.zNodes = null;
worklogManageTreeToPage.id = null;
worklogManageTreeToPage.name = null;
worklogManageTreeToPage.callback = null;
worklogManageTreeToPage.rootId = null;
worklogManageTreeToPage.rootName = null;

//下属人员树初始化
worklogManage.getZtree = function(){
	var url = getRootPath()+"/po/worklog/subWorkLogQueryTree.action";
	$.ajax({ 
		  url: url, 
		  dataType: 'json', 
		  //async:true, 
		  success: function(data){ 
			  if (data.length>0) {
					worklogManageTreeToPage.zNodes = [];
					var isFrist=false;
					var fristNum;
					$.each(data, function(i, o) {
						if(o.type==0 && isFrist==false){//type 0 人员 1部门
							isFrist=true;
							fristNum=i;
						}
					});
					$.each(data, function(i, o) {
						if(o.type==0 && i == fristNum){//type 0 人员 1部门
							worklogManageTreeToPage.rootId = o.id;
							worklogManageTreeToPage.rootName = o.name;
						}
						worklogManageTreeToPage.zNodes[i] = {
							id : o.id,
							pId : o.parentId,
							name : o.name + "",
							subPlanType : o.createUserDept,
							checkState : false,
							type: o.type
						};
					});
					tree = $.fn.zTree.init($("#treeDemo"), worklogManageTreeToPage.setting, worklogManageTreeToPage.zNodes);
					tree.expandAll(true);
					worklogManageTreeToPage.defaultCheck("treeDemo");
					$("#datePicker").empty();
					$("#datePicker").datepicker_worklog();
					//初始化左侧人员树div
					worklogManage.initLeftTreeDiv();
					//共享范围人员选择树初始化
					selectControl.init("shareUserNames1","shareUserIds-shareUserIds",true,true);//人员选择树 参数divid 设置的id-name 多选 人员
					
					//初始化当前日期和当前下属的日志
					worklogManage.initWorklog();
				}else{
					loadrightmenu('/po/worklog/worklogNoUnderling.action');
				}
		  } 
	});
};
worklogManageTreeToPage.setting = {
	check : {
		enable : false,
		nocheckInherit : true
	},
	view : {
		selectedMulti : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback:{
		beforeClick: function(id, node){
			if(node.type!=0){
				return false;
			}else{
				return true;
			}
		},
		onCheck: onCheck,
		onClick: onClick
	}
};
function onCheck(id, node){
}
function onClick(event, treeId, treeNode) {
    $("#userId").val(treeNode.id);//获取树节点ID
    $("#userName").val(treeNode.name);//获取树节点ID
    var dates = $('#datePickerInput').val();
    $('td[class*=day]').addClass('disabled');
	worklogManage.getWorklogStatus(dates);
    worklogManage.initWorklog();
};
//默认选中节点
worklogManageTreeToPage.defaultCheck = function(treeId){
	if(worklogManageTreeToPage.rootId != null && worklogManageTreeToPage.rootName != null){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var node = treeObj.getNodeByParam("id", worklogManageTreeToPage.rootId);
		treeObj.selectNode(node, true);
		node.checkState = true;
		$("#userId").val(worklogManageTreeToPage.rootId);
		$("#userName").val(worklogManageTreeToPage.rootName);
	}
};

worklogManage.initWorklog = function(){
	hideErrorMessage();//隐藏错误校验域
	worklogManage.clearleaderIdea();//领导批注内容清空
	var userId = $('#userId').val();
	if(typeof(userId) == 'undefined'||userId==''){
		return;
	} 
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
		data : {'createUser':userId,'worklogDateBegin':worklogDateBegin,'worklogDateEnd':worklogDateEnd},
		dataType : "json",
		success : function(data) {
			if (data) {
				worklogManage.initWorklogData(data);
			}
		},
		error:function() {
			msgBox.tip({
				content: date+'不存在日志',
				type:'fail'
			});
			worklogManage.clearWorklog();
		}
	});
};

/**前一篇和后一篇按钮*/
worklogManage.prefAndNext = function(isNext){
	worklogManage.getWorklogStatus($('#datePickerInput').val());
	//----xuweiping 2014.11.13 判断上下级领导关系是否存在---begin
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#userId").val();//工作计划申请人ID
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "false"){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
				msgBox.info({
					content: $.i18n.prop("JC_OA_PO_047"),
					type:'fail'
				});
				return;
			}
			//判断数据是否被删除
//			if(!DeleteCascade.syncCheckExist("workLogAnno",$("#worklogId").val())){
//				msgBox.info({
//					content: $.i18n.prop("JC_OA_COMMON_015")
//				});
//				return;
//			}
			//----xuweiping 2014.11.13 判断上下级领导关系是否存在---end
			hideErrorMessage();//隐藏错误校验域
			worklogManage.clearleaderIdea();//领导批注内容清空
			var userId = $('#userId').val();
			if(userId=='undefined'||userId==''){
				return;
			} 
			var currentDate = $('#date').val();
			var d = new Date();
			if(currentDate!=""){
				var tempDate =  currentDate.replace(/-/g,"/");
				d = new Date(tempDate);
			}else{
				currentDate = d.format('yyyy-MM-dd');
			}
			var tempData;
			var data;
			d = d.valueOf();
			var tipContent = "";
			if(isNext=='true'){
				d = d + (1 * 24 * 60 * 60 * 1000);
				tempData = new Date(d).format('yyyy-MM-dd');
				data =  {'createUser':userId,'worklogDateBegin':tempData+' 00:00:00'};
				tipContent += currentDate+"后不存在日志";
			}else{
				d = d - (1 * 24 * 60 * 60 * 1000);
				tempData = new Date(d).format('yyyy-MM-dd');
				data =  {'createUser':userId,'worklogDateEnd':tempData+' 00:00:00'};
				tipContent += currentDate+"前不存在日志";
			}
			$.ajax({
				type : "GET",
				url : getRootPath()+"/po/worklog/getPrefAndNext.action",
				data : data,
				dataType : "json",
				success : function(data) {
					if (data.success=='true') {
						worklog = data.worklogList[0];
						worklogManage.initWorklogData(worklog);
						$('#date').val(worklog.worklogDate);
						$("#datePicker").datepicker_worklog('setDate',worklog.worklogDate);
					}else{
						msgBox.tip({
							content: tipContent,
							type:'fail'
						});
						$('#worklogId').val('');
//						worklogManage.getWorklogStatus($('#datePickerInput').val());
					}
				},
				error:function() {
					msgBox.tip({
						content: tipContent,
						type:'fail'
					});
					worklogManage.clearWorklog();
				}
			});
		}
	});
};

worklogManage.initWorklogData = function(data){
	//清除页数据
	worklogManage.clearDetail();
	//var d = new Date(data.worklogDate);
	//回显日志详细数据
	//$('#worklogTitle').html(data.worklogDate+"("+d.getWeek()+")日报"+$('#userName').val());
	$('#worklogId').val(data.id);
	$('#createUserDept').val(data.createUserDept);
	$('#title').html(data.title);
	$('#worklogDate').html(data.worklogDate);
	$('#detailSentimentRemark').html(data.sentimentRemark);
	//回显共享范围
	if(data.shareUserNames!=null&&data.shareUserNames.length!=0){
		$("#shareUserIds-shareUserIds").select2("data", eval(data.shareUserIds));
	}
	if(data.remindType!=null && data.remindType!='0' && data.remindMan!=null && data.remindMan!=''){
		$('#oldRemaidMan').val(data.remindMan);
	}else{
		$('#oldRemaidMan').val(data.remindMan);//当点击提醒设置按钮时，不进行其他操作时，历史共享人员赋值。  李洪宇  2014-9-3
	}
	worklogManage.clearRemindForm();
	//填充待办任务
	worklogManage.initDetailWorkTask(data.worklogDate,'detailTask');
	//填充今日日程
	//worklogManage.initDiary(data.worklogDate);
	worklogManage.initDiary(data.worklogDate,'detailTodayDiary',worklogManage.autoTrDetailTodayDiaryStr);
	//填充批注
	worklogManage.initAnno(data.id);
	//填充今日日志
	/*var todayLogs = data.todayLogs;
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
	$('#todayLog').html(todayLogStrs);*/
	
	var todayLogs = data.todayLogs;
	if(todayLogs==null||todayLogs.length==0){
		worklogManage.noDate('detailTodayLog');
	}
	if(todayLogs && todayLogs.length > 0) {
		for(var i=0;i<todayLogs.length;i++){
			var todayLog = todayLogs[i];
			worklogManage.initDetailTr('detailTodayLog',worklogManage.autoTrDetailStr,todayLog.content);
		}
	}
	//填充明日提醒
	/*var tomorrowReminds = data.tomorrowReminds;
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
	var tomorrowReminds = data.tomorrowReminds;
	if(tomorrowReminds==null||tomorrowReminds.length==0){
		worklogManage.noDate('detailTomorrowRemind');
	}
	if(tomorrowReminds && tomorrowReminds.length > 0) {
		for(var i=0;i<tomorrowReminds.length;i++){
			var tomorrowRemind = tomorrowReminds[i];
			worklogManage.initDetailTr('detailTomorrowRemind',worklogManage.autoTrDetailStr,tomorrowRemind.content);
		}
	}
	//设置附件数据
	$('#businessId').val(data.id);
	$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
	showAttachList();//附件弹出层数据回显方法
	echoAttachList();//附件列表数据回显方法
	
	$('#readingStatus').show();
	//更新阅读情况
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/savaReadingStatus.action",
		data : {'id':data.id,'createUser':data.createUser},
		dataType : "json",
		success : function(data) {
			
		}
	});
};
/**
 * 详细页初始添加行公共方法to:目标容器id,trStr:添加内容,
 */
worklogManage.initDetailTr = function(to,trStr,d){
	
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		$('#'+to).find('tbody tr:last td:last').html(d);
	
};
/**清空记录*/
worklogManage.clearWorklog = function(){
	$('#worklogId').val('');
	$('#worklogTitle').empty();
	$('#title').empty();
	$('#worklogDate').empty();
	$('#detailTask tbody').empty();
	$('#detailTodayDiary tbody').empty();
	$('#detailTodayLog tbody').empty();
	$('#detailSentimentRemark').empty();
	$('#detailTomorrowRemind tbody').empty();
	$('#shareUserNames').empty();
	$('#comment').empty();
	selectControl.clearValue("shareUserIds-shareUserIds");
	//清除附件信息
	clearTable();
	clearAttachlist();
};
/**初始化详细页待办任务列表*/
worklogManage.workTaskDetailTable = null;//分页对象
worklogManage.initDetailWorkTask = function(currentDate,to){
	/*var directorId = $('#userId').val();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryWorkTask.action",
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
	worklogManage.workTaskTableColumns = [
	                                        //不需要排序的列直接用mData function方式
	                                        {mData: "taskName"},
	                                        {mData: "taskProc"},
	                                        {mData: "sponsor"},
	                                        {mData: "actStartTime"}
	                                        ];
		
	worklogManage.aoColumnDefs = [{bSortable: false, aTargets: [0,1,2,3]}];
	
	//组装后台参数
	worklogManage.workTaskTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogManage.workTaskDetailTable, aoData);
		
		aoData.push({ "name": "startDate", "value": currentDate});
		aoData.push({ "name": "directorId", "value": directorId});
	};
	
	//table对象为null时初始化
	if (worklogManage.workTaskDetailTable == null) {
		worklogManage.workTaskDetailTable = $('#'+to).dataTable( {
			sAjaxSource: getRootPath()+"/po/worklog/queryWorkTask.action",
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogManage.workTaskTableColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogManage.workTaskTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
			aoColumnDefs: worklogManage.aoColumnDefs
		} );
		
	} else {
		//table不为null时重绘表格
		worklogManage.workTaskDetailTable.fnDraw();
	}
	
};

/**初始化今日日程*/
worklogManage.initDiary = function(currentDate,to,tr){
	/*var userId=$('#userId').val();
	var starttime = currentDate;
	var endtime = currentDate;
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryDiary.action",
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
	var userId=$('#userId').val();
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
					worklogManage.noDate(to);
					return;
				}
				for(var i=0;i<data.length;i++){
					var diary = data[i];
					worklogManage.initTr(to,tr,1);
					$('#'+to).find('tbody tr:last td[name="todayDiaryTitle"]').html(diary.content);
				}
			}else{
				worklogManage.noDate(to);
			}
		}
	});
};
/**
 * 初始添加行公共方法to:目标容器id,trStr:添加内容,n:添加行数,idPref:id前缀
 */
worklogManage.initTr = function(to,trStr,n,idPref){
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
		worklogManage.autoTrBindEvent(to,trStr,idPref);
	};*/
};

/**清除详情页数据*/
worklogManage.clearDetail = function(){
	$('#detailTodayLog tbody').html('');
	$('#detailTomorrowRemind tbody').html('');
	$('#detailTodayDiary tbody').html('');
	$('#detailTask tbody').html('');
	$('#comment').empty();
	$('#reminderForm')[0].reset();
	$('#readingStatus').hide();
	selectControl.clearValue("shareUserIds-shareUserIds");
	//清除附件信息
	clearTable();
	clearAttachlist();
};
// 李洪宇 于2014-7-10 对批注进行修改 开始
/**
 * 领导批注初始化方法
 */
worklogManage.initAnno = function(dataid) {
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
					var currentUser = $('#currentUser').val();
					for (var i = 0; i < data.length; i++) {
						 var annoParent = data[i];
						 liStr += "<li class=\"clearfix m-b input-group\">" + 
								  "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\"" + 
								  annoParent.id + "\">" + 
								  "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
								  annoParent.annoUserIdValue + "：</strong>" + annoParent.content + 
								  "<span>" + annoParent.annoDate + "</span></p>";
								 
						 liStr += "<div class=\"input-group-btn p-l\">" + 
						          "<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"anno"+i+"\" name=\"reply\">回 复</a></div>";
						 liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
		                          "<textarea name=\"replayAnno\" rows=\"3\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":\"></textarea>"+
		                          "<div class=\"input-group-btn p-l\">"+
		                          	" <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
		                          "</div></div>";
						 // 批注回复
						 var replyList = annoParent.annoReplyList;
						 if(replyList){
							for(var j = 0; j < replyList.length; j++){
								var annoReply = replyList[j];
								liStr += "<div class=\"dis-row\">" + 
										 "<p class=\"dialog-text input-group\" data=\"" + 
										 annoReply.id + "\">" + "<input type=\"hidden\" name=\"\">" + 
										 "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
										 annoReply.annoUserIdValue + "回复" + annoReply.parentUserName + 
										 "：</strong>" + annoReply.content + 
										 "<span>" + annoReply.annoDate + 
										 "</span></p>";
								liStr += "<div class=\"input-group-btn p-l\">" + 
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
worklogManage.commentReply = function(e) {
	iePlaceholderPatch();//手动调用水印
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
};

/**
 * 领导批注发送方法
 */
worklogManage.commentSend = function(e) {
	//校验是否重复提交
	if (worklogManage.subState)return;
	worklogManage.subState = true;
	var businessId = $('#worklogId').val();
	var annoName = $('#title').html();
	var annoParentId = $(e).attr('data');;
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	var byAnnoUserId = $('#userId').val();
	var byAnnoUserDepid = $('#createUserDept').val();
	var content  = $(e).parent().prevAll('textarea').val();
	if(!DeleteCascade.syncCheckExist("workLogAnno",businessId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogManage.subState = false;
		return;
	}else{
		if ($("#leaderIdeaReplayForm").valid()) {
			$.ajax({
				type : "POST",
				url : getRootPath()+"/po/worklog/annoReply.action?time=" + new Date(),
				data : {"annoName":annoName,
					"businessId":businessId,
					"annoParentId":annoParentId,
					"rootParentId":rootParentId,
					"byAnnoUserId":byAnnoUserId,
					"byAnnoUserDepid":byAnnoUserDepid,
					"content":content
					},
				dataType : "json",
				success : function(data) {
					worklogManage.subState = false;//更新重复提交状态标识
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								$('#comment').empty();
								worklogManage.initAnno(businessId);
								var d = $('#datePickerInput').val();
								worklogManage.getWorklogStatus(d);
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
			worklogManage.subState = false;//更新重复提交状态标识
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
worklogManage.saveLeaderIdeaForm = function(){
	var worklogId = $('#worklogId').val();
	if(worklogId==''){
		msgBox.info({
			content: '日志不存在'
		});
		return;
	}
	//校验是否重复提交
	if (worklogManage.subState)return;
	worklogManage.subState = true;
	if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogManage.subState = false;
		return;
	}else{
		//校验表单信息
		if ($("#leaderIdeaForm").valid()) {
			var url = getRootPath()+"/po/worklog/saveAnno.action?time=" + new Date();
			//将表单序列化成json格式
			var formData = $("#leaderIdeaForm").serializeArray();
			var annoName = $('#title').html();
			var userId = $('#userId').val();
			var createUserDept = $('#createUserDept').val();
			formData.push({"name": "annoName", "value": annoName});
			formData.push({"name": "businessId", "value": worklogId});
			formData.push({"name": "annoType", "value": 2});
			formData.push({"name" : "byAnnoUserId","value" :userId});
			formData.push({"name" : "byAnnoUserDepid","value" :createUserDept});
			jQuery.ajax({
				url : url,
				type : 'POST',
				data : formData,
				success : function(data) {
					worklogManage.subState = false;//更新重复提交状态标识
//					getToken();//更新token
					if(data.success == "true"){
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
						$('#leaderIdeaContent').val('');
						$('#comment').empty();
						worklogManage.initAnno(worklogId);
						var d = $('#datePickerInput').val();
						worklogManage.getWorklogStatus(d);
						$("#token").val(data.token);
					} else {
						worklogManage.subState = false;//更新重复提交状态标识
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
					worklogManage.subState = false;//更新重复提交状态标识
					msgBox.tip({
						content: $.i18n.prop("JC_SYS_002")
					});
				}
			});
		} else {
			worklogManage.subState = false;//更新重复提交状态标识
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
worklogManage.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};
//李洪宇 于2014-7-10 对批注进行修改 结束

//分页对象
worklogManage.collectTable = null;

//不分页查询汇总
worklogManage.collectSearch = function () {
	$('#startDateTemp').val($("#worklogDateBegin").val());//将查询条件赋值给临时变量 add by lihongyu at 2014-11-10
	$('#endDateTemp').val($("#worklogDateEnd").val());//将查询条件赋值给临时变量  add by lihongyu at 2014-11-10
	var userId = $('#userId').val();
	
	if(userId==''||userId==null){
		msgBox.info({
			content: "请选择人员！"
		});
		return ;
	}
	//显示列信息
	worklogManage.collectTableAoColumns = [
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
				reVal="<a href=\"#\" onclick=\"worklogManage.showAnno("+full.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
			}
			return reVal;
		}}
	 ];
	
	//组装后台参数
	worklogManage.collectTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogManage.collectTable, aoData);
		//组装查询条件
		var worklogDateBegin = $("#worklogDateBegin").val();
		var worklogDateEnd = $("#worklogDateEnd").val();
		
		aoData.push({ "name": "worklogDateBegin", "value": worklogDateBegin});
		aoData.push({ "name": "worklogDateEnd", "value": worklogDateEnd});
		aoData.push({"name": "createUser", "value": userId});
	};
	
	//table对象为null时初始化
	if (worklogManage.collectTable == null) {
		worklogManage.collectTable = $('#collectTable').dataTable( {
			iDisplayLength: 5,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/po/worklog/getMyworklogCollect.action?time=" + new Date(),
			//bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogManage.collectTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogManage.collectTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,3]}]
		} );
	} else {
		//table不为null时重绘表格
		worklogManage.collectTable.fnDraw();
		//pageChange(user.oTable);
	}
	$('#worklog_summary').modal('show');
};
/**初始化左侧的树的div宽高*/
worklogManage.initLeftTreeDiv = function(){
	var content = $("#content").height();
	var headerHeight_1 = $('#header_1').height() || 0;
	var headerHeight_2 = $("#header_2").height() || 0;
	
	    $(".tree-right").css("margin-left","215px");
	//定义高度
		$("#LeftHeight_box_rl").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight_box_rl").height();
	    var day_height=$("#datePicker").find("div").height()+15;
	    $("#LeftHeight_rl").height(content-80-headerHeight_1-headerHeight_2-day_height);
	    var lh_day = $("#LeftHeight_rl").height(); 
	  $("#scrollable").scroll(function() {
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight_box_rl").addClass("fixedNav");  
	        $("#LeftHeight_box_rl").height(lh + 113);
	        $("#LeftHeight_rl").height(lh_day + 113);
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop();
	        		$("#LeftHeight_rl").height(lh_day + a);
	        //$("#LeftHeight").height()
	        $("#LeftHeight_box_rl").removeClass("fixedNav");
	    } 
	
	  });
};
worklogManage.noDate = function(to){
	var i = $('#'+to).find('thead th').size();
	var trStr = "<tr class=\"odd\"><td class=\"dataTables_empty\" colspan=\""+i+"\">没有匹配的记录</td></tr>";
	$('#'+to).find('tbody').append(trStr);
};
//分页对象
worklogManage.readingStatusTable = null;
/**阅读情况*/
worklogManage.readingStatus = function(worklogId){
//	//增加日志校验
//	if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
//		msgBox.info({
//			content: $.i18n.prop("JC_OA_COMMON_015")
//		});
//		return;
//	}
	//显示列信息
	worklogManage.readingStatusTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "readerName"},
		{mData: "readingDate"}
	 ];
	
	//组装后台参数
	worklogManage.readingStatusTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogManage.readingStatusTable, aoData);
		//组装查询条件
		
		aoData.push({ "name": "worklogId", "value": worklogId});
		aoData.push({ "name": "businessType", "value": 2});
	};
	
	//table对象为null时初始化
	if (worklogManage.readingStatusTable == null) {
		worklogManage.readingStatusTable = $('#readingStatusTable').dataTable( {
			sAjaxSource: getRootPath()+"/po/readingStatus/queryAllByDataTable.action?time=" + new Date(),
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogManage.readingStatusTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogManage.readingStatusTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1]}]
		} );
		//setColumnVis(worklogManage.readingStatusTable, 6);
		
	} else {
		//table不为null时重绘表格
		worklogManage.readingStatusTable.fnDraw();
		//pageChange(user.oTable);
	}
	$("#Reading").modal("setPaddingTop");//UI调整，处理弹出层首次显示下方按钮遮挡问题
};

/**校验短信提醒*/
worklogManage.validRemind = function(){
	var worklogId = $('#worklogId').val();
	if(worklogId==''){return ;}
	var reminder = $('input[name="remind"]:checked').val();
	if(reminder=='2'){
		var remainIds = $('#leftRight-leftRight').val();
		var oldRemaidMan = $('#oldRemaidMan').val();
		var remaidMan = betw(oldRemaidMan,remainIds).join(",");
		//将共享范围人员去重 李洪宇 start 
		var shareUserIds = $('#shareUserIds-shareUserIds').val();
		var r = [];
		if(shareUserIds!=null || shareUserIds!=''){
			var s = shareUserIds.split(',');
	        var dic = {};
	        for (var i = s.length; i--; ) {
	            dic[s[i]]=s[i];
	        }
	        for (var v in dic) {
	        r.push(dic[v]);
	        }
		}
		var remainManTemp='';
		if(r!=null){
			remainManTemp= r.join(",");
		}
		//将共享范围人员去重 李洪宇 end
		jQuery.ajax({
			url : getRootPath()+"/po/worklog/validRemindNew.action?time=" + new Date(),
			type : 'get',
			async:false,
			dataType : "json",
			data : {'shareUserIds':remaidMan,'createUser':$('#userId').val()},
			success : function(data) {
				if(data.success=="success"){
					worklogManage.updateShareUser();
				}else{
					if(data.success){
						msgBox.confirm({
							content: data.successMessage,
							success: function(){
								worklogManage.updateShareUser();
							},
							cancel:function(){
							}
						});
					}else{
						msgBox.tip({
							content: data.errorMessage
						});
					} 
				}
			},
			error : function() {
				msgBox.tip({
					content: '短信提醒验证失败，请联系管理员'
				});
				
			}
		});
	}else{
		worklogManage.updateShareUser();
	}
};

/**保存共享范围*/
worklogManage.updateShareUser = function(){
	//李洪宇 于2014-7-11 修改  添加 共享范围 为空校验  开始
	/*var shareUserIds = $('#shareUserIds-shareUserIds').val();
	if(shareUserIds==''||shareUserIds==null){
		msgBox.info({
			content:'请先选择共享人',
			type:'fail'
		});
		return;
	}*/
	//----xuweiping 2014.11.13 判断上下级领导关系是否存在---begin
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#userId").val();//工作计划申请人ID
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "false"){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
				msgBox.info({
					content: $.i18n.prop("JC_OA_PO_047"),
					type:'fail'
				});
				return;
			}///----------------xuweiping 2014.11.13 判断上下级领导关系是否存在---end
			//判断数据是否被删除
			if(!DeleteCascade.syncCheckExist("workLogAnno",$("#worklogId").val())){
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_015")
				});
				return;
			}
			//李洪宇 于2014-7-11 修改  添加 共享范围 为空校验 结束
			var worklogId = $('#worklogId').val();
			var userIds = $('#shareUserIds-shareUserIds').val();
			var remainIds = $('#leftRight-leftRight').val()||'';
			var oldRemaidMan = $('#oldRemaidMan').val();
			//共享提醒
			var remindType = $('input[name="remind"]:checked').val();
			//提醒内容
			var remindTitle = $('#remindTitle').val();
			//提醒内容
			var remindContent = $('#remindContent').val();
			//增加的共享人
			var remaidMan = betw(oldRemaidMan,remainIds).join(",");
			var userId = $('#userId').val();
			if(worklogId==''){return ;}
			//将共享范围人员去重 李洪宇 start 
			var r = [];
			if(userIds!=null || userIds!=''){
				var s = userIds.split(',');
				var dic = {};
				for (var i = s.length; i--; ) {
					dic[s[i]]=s[i];
				}
				for (var v in dic) {
					r.push(dic[v]);
				}
			}
			var remainManTemp='';
			if(r!=null){
				remainManTemp= r.join(",");
			}
			//将共享范围人员去重 李洪宇 end
			jQuery.ajax({
				url : getRootPath()+"/po/worklog/updateShareUser.action?time=" + new Date(),
				type : 'POST',
				data : {'id':worklogId,'shareUserIds':remainManTemp,'remindMan':remaidMan,'remindType':remindType,'remindTitle':remindTitle,'remindContent':remindContent,'createUser':userId,'beforeRemindMan':remainIds},
				dataType : "json",
				success : function(data) {
					if(data.success == "true"){
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
						worklogManage.clearRemindForm();
					}else{
						if(data.labelErrorMessage){
							showErrors("notRrce", data.labelErrorMessage);
						}else{
							msgBox.info({
								type:"fail",
								content: data.errorMessage
							});
						}
					}
				},
				error : function() {
					msgBox.tip({
						content: $.i18n.prop("JC_SYS_002")
					});
				}
			});
		}
	});	
};

worklogManage.clearRemindForm = function(){
	$('#reminderForm')[0].reset();
	$('#remindTitle').val('');
	$('#remindTitleTr').hide();
	$('#remindContentLable').html('内容');
};
worklogManage.dateList = [];
/**标记日志状态*/
worklogManage.showWorklogStatus = function(){
	$('td[class*="day"]').find('b').remove();
	$.each(worklogManage.dateList,function(i,item){
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
worklogManage.getWorklogStatus = function(d){
	var createUser = $('#userId').val();
	if(createUser=='')return ;
	jQuery.ajax({
		url : getRootPath()+"/po/worklog/showWorklogStatus.action?time=" + new Date(),
		type : 'POST',
		data : {'dates':d,'createUser':createUser},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				worklogManage.dateList = data.worklogList;
				worklogManage.showWorklogStatus();
			} 
		}
	});
};

/**提醒设置*/
worklogManage.reminder = function(){
	//----xuweiping 2014.11.13 判断上下级领导关系是否存在---begin
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#userId").val();//工作计划申请人ID
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "false"){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
				msgBox.info({
					content: $.i18n.prop("JC_OA_PO_047"),
					type:'fail'
				});
				return;
			}
			//判断数据是否被删除
			if(!DeleteCascade.syncCheckExist("workLogAnno",$("#worklogId").val())){
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_015")
				});
				return;
			}
			//----xuweiping 2014.11.13 判断上下级领导关系是否存在---end
			var worklogId = $('#worklogId').val();
			if(worklogId=='')return;
			
			var shareUserIds = $('#shareUserIds-shareUserIds').val();
			//将共享范围人员去重 李洪宇 start 
			var r = [];
			if(shareUserIds!=null || shareUserIds!=''){
				var s = shareUserIds.split(',');
				var dic = {};
				for (var i = s.length; i--; ) {
					dic[s[i]]=s[i];
				}
				for (var v in dic) {
					r.push(dic[v]);
				}
			}
			var remainManTemp='';
			if(r!=null){
				remainManTemp= r.join(",");
			}
			//将共享范围人员去重 李洪宇 end
			if(remainManTemp==''||remainManTemp==null){
				msgBox.info({
					content:'请先选择共享人',
					type:'fail'
				});
				return;
			}
			$('#leftAndRightSelectDiv').empty();
			leftRightSelect.init({
				containerId:"leftAndRightSelectDiv",
				moduleId:"leftRight-leftRight",
				isCheck:true,
				url:getRootPath()+"/po/worklog/getRemaidManData.action?ids="+remainManTemp,
				title:"被提醒人"
			});
			leftRightSelect.setData("leftRight-leftRight",remainManTemp);
			$('#reminderModal').modal('show');
		}
	});
};
/**校验提醒内容*/
worklogManage.reminderValid = function(){
	var reminder = $('input[name="remind"]:checked').val();
	var remindTitle = $('#remindTitle').val();
	var remindContent = $('#remindContent').val();
	var reminders = $('#leftRight-leftRight').val();
	if(reminder=='1'){//邮件提醒
		//校验提醒人不能为空
		if(reminders==''){
			msgBox.info({
				content:"请选择被提醒人",
				success:function(){
					fnCall();
				}
			});
			return false;
		}
		if(remindTitle.length>=80){
			msgBox.info({
				content:"邮件标题不能大于80个字符",
				success:function(){
					fnCall();
				}
			});
			return false;
		}
	}else if(reminder=='2'){//短信提醒
		//校验提醒人不能为空
		if(reminders==''){
			msgBox.info({
				content:"请选择被提醒人",
				success:function(){
					fnCall();
				}
			});
			return false;
		}
		if(remindContent.length>=300){
			msgBox.info({
				content:"短信内容不能大于300个字符",
				success:function(){
					fnCall();
				}
			});
			return false;
		}
	}
	return true;
};
//日志提交情况
worklogManage.worklogCondition = function (){
	loadrightmenu("/po/plan/dayPlanConditionSkip.action?time=" + new Date(),"工作日志提交情况");
};
/**查看领导批注*/
worklogManage.showAnno = function(dataid){
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
worklogManage.closeAnno = function(){
	$('#annoComment').empty();
};
/**
 * 初始化方法
 */
jQuery(function($){
	//日历视图改变时调用
	$('#datePickerInput').change(function(){
		var dates = $(this).val();
		worklogManage.getWorklogStatus(dates);
	});
	//提醒设置页面动态效果
	$('#reminderForm').on('click','input[name="remind"]',function(){
		var reminder = $('input[name="remind"]:checked').val();
		if(reminder=='1'){
			$('#remindContentLable').html('邮件内容');
			$('#remindTitleTr').show();
		}else if(reminder=='2'){
			$('#remindContentLable').html('短信内容');
			$('#remindTitleTr').val('').hide();
		}else{
			$('#remindContentLable').html('内容');
			$('#remindTitleTr').val('').hide();
		}
	});
	//绑定提醒设置
	$('#reminder').click(function(){
		worklogManage.reminder();
	});
	
	$('#remindOk').click(function(){
		if(worklogManage.reminderValid())
		$('#reminderModal').modal('hide');
	});
	//日志提交情况按钮事件绑定
  	$("#worklogConditionBtn").click(function(){worklogManage.worklogCondition();});
	//绑定汇总查询
	$('#collectSearch').click(function(){
		worklogManage.collectSearch();
	});
	//绑定上一篇和下一篇
	$('#pref,#next').click(function(){
		worklogManage.prefAndNext($(this).attr('isNext'));
	});
	//绑定阅读情况查询
	$('#readingStatus').click(function(){
		var worklogId = $('#worklogId').val();
		if(worklogId!='' && worklogId!=null){
			//增加日志校验
			if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_015")
				});
				return;
			}
			worklogManage.readingStatus(worklogId);
			$('#Reading').modal('show');
		}
		else{
			msgBox.tip({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_043")
			});
		}
	});
	//绑定日志汇总
	$('#collect').click(function(){
		$('#collectForm')[0].reset();
		$('#collectTable tbody').empty();
		$('#worklogDateBegin').val(new Date().format('yyyy-MM-dd'));
		$('#worklogDateEnd').val(new Date().format('yyyy-MM-dd'));
		worklogManage.collectSearch();
		
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
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){worklogManage.saveLeaderIdeaForm();});
	//绑定批注"回复"
	$('#comment').on('click','a[name="reply"]',function(){
		worklogManage.commentReply(this);
	});
	//绑定批注"发送"
	$('#comment').on('click','a[name="send"]',function(){
		worklogManage.commentSend(this);
	});
	//绑定批注内容清除
	$("#clearleaderIdea").click(function(){
		worklogManage.clearleaderIdea();
	});
	//初始化下属人员树
	worklogManage.getZtree();
	//绑定共享范围保存方法
	$("#updateShareUser").click(function(){worklogManage.validRemind();});
	
	//日历控件重新初始化
	$("#worklogDateBegin,#worklogDateEnd").datepicker();
	//初始化悬停的日历组件	
	/*//初始化左侧人员树div
	worklogManage.initLeftTreeDiv();
	//共享范围人员选择树初始化
	selectControl.init("shareUserNames1","shareUserIds-shareUserIds",true,true);//人员选择树 参数divid 设置的id-name 多选 人员
*/	//悬停日历的日期点击回调方法
	window.testCallback = function(args){
		if(typeof(args.pickerObject)=="object"){
			var pickerObject = args.pickerObject;
			//var selectedDate = pickerObject.getDate();
			//todo:add your code below
			$('#date').val(pickerObject.formatDate(pickerObject.getDate()));
			//$("#datePicker").datepicker_worklog('setDate','2014-02-02');
			worklogManage.showWorklogStatus();
			worklogManage.initWorklog();
		} 
		
	};
});
//@ sourceURL=worklogManager.js