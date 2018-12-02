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

worklogCalendar = {};
/**
 * 重复提交标识
 */
worklogCalendar.subState = false;
worklogCalendar.pageRows = null;
/**今日日志input id前缀*/
worklogCalendar.todayLogIdPref = 'todayLog-';
/**明日提醒input id前缀*/
worklogCalendar.tomorrowRemindIdPref = 'tomorrowRemind-';
/**今日日志动态添加行模版*/
worklogCalendar.autoTrTodayLogStr = "<tr>"+
									"<td >第<span></span>条</td>"+
									"<td>" +
									"<span class=\"input-style\">" +
									"<input name=\"todayLogInput\" class=\"addLine\" type=\"text\" placeholder=\"请填写...\">" +
									"</span>" +
									"</td>"+
									"<td >" +
									"<a class='a-icon i-remove' href='javascript:void(0)' name='deleteTr'><i id='delSum' class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' " +
									"data-container='body' data-original-title='删除'></i></a>"+
									"</td>"+
									"</tr>";
/**明日提醒动态添加行模版*/
worklogCalendar.autoTrTomorrowRemindStr = "<tr>"+
										"<td >第<span></span>条</td>"+
										"<td>" +
										"<span class=\"input-style\">" +
										"<input name=\"tomorrowRemindInput\" class=\"addLine\" type=\"text\" placeholder=\"请填写...\">" +
										"</span>" +
										"</td>"+
										"<td class=\"w140\">"+
										"<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"worklogToDiary\">"+
										    "加入日程"+
									    "<a class='a-icon i-remove' href='javascript:void(0)' name='deleteTr'><i id='delSum' class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' " +
										"data-container='body' data-original-title='删除'></i></a>"+
										"</a>"+
										"</td>"+
										"</tr>";

/**日志详细今日日志及明日提醒动态添加行模版*/
worklogCalendar.autoTrDetailStr = "<tr>"+
								"<td>第<span></span>条</td>"+
								"<td>" +
								"</td>"+
								"</tr>";
/**日志详细今日日程动态添加行模版*/
worklogCalendar.autoTrDetailTodayDiaryStr = "<tr>"+
									"<td>第<span></span>条</td>"+
									"<td name=\"todayDiaryTitle\">" +
									"</td>"+
									"</tr>";

/**今日日程动态添加行模版*/
worklogCalendar.autoTrTodayDiaryStr =  "<tr>"+
										"<td>第<span></span>条</td>"+
										"<td name=\"todayDiaryTitle\"></td>"+
										"<td>"+
										    "<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"insertWorklog\">"+
										    	"插入日志"+
										    "</a>"+
										"</td>"+
										"</tr>";
/**添加修改页待办任务动态添加行模版*/
worklogCalendar.autoTrWorkTaskStr =  "<tr>"+
										"<input type=\"hidden\" name=\"taskId\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"taskTitle\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"taskContent\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"taskProc\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"startTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"endTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"actEndTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"userId\" value=\"\"/>"+
										"<td name=\"taskTitle\"></td>"+
										"<td name=\"taskProc\"></td>"+
										"<td name=\"sponsorId\"></td>"+
										"<td name=\"actStartTime\"></td>"+
										"<td>"+
										    "<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"report\">" +
										    	"汇报" +
										    "</a>"+
										    "<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"taskToDiary\">"+
										    	"加入日程"+
										    "</a>"+
										    "<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"insertWorklog\">"+
										    	"插入日志"+
										    "</a>"+
										"</td>"+
										"</tr>";

/**详细页待办任务动态添加行模版*/
worklogCalendar.autoTrDetailWorkTaskStr =  "<tr>"+
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
 * 动态添加行初始化方法
 */
worklogCalendar.autoTrInit = function(){
	//今日日志默认添加5行
	worklogCalendar.initTr('todayLog',worklogCalendar.autoTrTodayLogStr,5,worklogCalendar.todayLogIdPref);
	//明日提醒默认添加3行
	worklogCalendar.initTr('tomorrowRemind',worklogCalendar.autoTrTomorrowRemindStr,3,worklogCalendar.tomorrowRemindIdPref);
};
/**
 * 动态行绑定事件
 */
worklogCalendar.autoTrBindEvent = function(to,trStr,idPref){
	$('#'+to).on('focus','input[class^="addLine"]',function(){
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html();//取最后一行行号
		var currentNo = $(this).parent().parent().prev().find('span').html();//当前行号
		if(currentNo==lastNo){//判断是不是最后一行
			//if($(this).val()!=''){//判断是不是为空
				worklogCalendar.initTr(to,trStr,1,idPref);
			//}
		};
	});
};

/**
 * 初始添加行公共方法to:目标容器id,trStr:添加内容,n:添加行数,idPref:id前缀
 */
worklogCalendar.initTr = function(to,trStr,n,idPref){
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
		worklogCalendar.autoTrBindEvent(to,trStr,idPref);
	};*/
};

/**
 * 详细页初始添加行公共方法to:目标容器id,trStr:添加内容,
 */
worklogCalendar.initDetailTr = function(to,trStr,d){
	
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		$('#'+to).find('tbody tr:last td:last').html(d);
	
};

/**初始化待办任务列表*/
worklogCalendar.workTaskTable = null;//分页对象
worklogCalendar.initWorkTask = function(currentDate,to){
		//显示列信息
		worklogCalendar.workTaskTableColumns = [
		    //不需要排序的列直接用mData function方式
			{mData: "taskName"},
			{mData: "taskProc"},
			{mData: "sponsor"},
			{mData: "actStartTime"},
			//设置权限按钮
			{mData: function(source) {
				var returnStr = "<input type=\"hidden\" name=\"taskId\" value=\""+source.id+"\"/>"+
								"<input type=\"hidden\" name=\"taskTitle\" value=\""+source.taskName+"\"/>"+
								"<input type=\"hidden\" name=\"taskContent\" value=\""+source.content+"\"/>"+
								"<input type=\"hidden\" name=\"taskProc\" value=\""+source.taskProc+"\"/>"+
								"<input type=\"hidden\" name=\"startTime\" value=\""+source.startTime+"\"/>"+
								"<input type=\"hidden\" name=\"endTime\" value=\""+source.endTime+"\"/>"+
								"<input type=\"hidden\" name=\"actEndTime\" value=\""+source.actEndTime+"\"/>"+
								"<input type=\"hidden\" name=\"actStartTime\" value=\""+source.actStartTime+"\"/>";
				if(source.status != '3'){
					returnStr +="<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"report\" >" +
						    	"汇报" +
							    "</a>"+
							    "<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"taskToDiary\">" +
							    	"加入日程"+
							    "</a>";
				}
					returnStr +="<a class=\"a-icon i-new m-r-xs\" href=\"#\" name=\"insertWorklog\">" +
							    	"插入日志"+
							    "</a>";
				return returnStr;
			}}
		 ];
		worklogCalendar.aoColumnDefs = [{bSortable: false, aTargets: [0,1,2,3,4]}];
				
		
		//组装后台参数
		worklogCalendar.workTaskTableFnServerParams = function(aoData){
			//排序条件
			getTableParameters(worklogCalendar.workTaskTable, aoData);
			
			aoData.push({ "name": "startDate", "value": currentDate});
		};
		
		//table对象为null时初始化
		if (worklogCalendar.workTaskTable == null) {
			worklogCalendar.workTaskTable = $('#'+to).dataTable( {
				sAjaxSource: getRootPath()+"/po/worklog/queryWorkTask.action",
				bPaginate: false,
				fnServerData: oTableRetrieveData,//查询数据回调函数
				aoColumns: worklogCalendar.workTaskTableColumns,//table显示列
				fnServerParams: function ( aoData ) {//传参
					worklogCalendar.workTaskTableFnServerParams(aoData);
				},
				aaSorting:[],//设置表格默认排序列
				//fnDrawCallback: rememberPage,
				//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
		        aoColumnDefs: worklogCalendar.aoColumnDefs
			} );
			//setColumnVis(worklogCalendar.workTaskTable, 0);
			
		} else {
			//table不为null时重绘表格
			worklogCalendar.workTaskTable.fnDraw();
			//pageChange(worklogCalendar.oTable);
		}
	
};
/**初始化详细页待办任务列表*/
worklogCalendar.workTaskDetailTable = null;//分页对象
worklogCalendar.initDetailWorkTask = function(currentDate,to){
	//显示列信息
	worklogCalendar.workTaskTableColumns = [
	                                        //不需要排序的列直接用mData function方式
	                                        {mData: "taskName"},
	                                        {mData: "taskProc"},
	                                        {mData: "sponsor"},
	                                        {mData: "actStartTime"}
	                                        ];
		
	worklogCalendar.aoColumnDefs = [{bSortable: false, aTargets: [0,1,2,3]}];
	
	//组装后台参数
	worklogCalendar.workTaskTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogCalendar.workTaskDetailTable, aoData);
		
		aoData.push({ "name": "startDate", "value": currentDate});
	};
	
	//table对象为null时初始化
	if (worklogCalendar.workTaskDetailTable == null) {
		worklogCalendar.workTaskDetailTable = $('#'+to).dataTable( {
			sAjaxSource: getRootPath()+"/po/worklog/queryWorkTask.action",
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogCalendar.workTaskTableColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogCalendar.workTaskTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
			aoColumnDefs: worklogCalendar.aoColumnDefs
		} );
		
	} else {
		//table不为null时重绘表格
		worklogCalendar.workTaskDetailTable.fnDraw();
	}
	
};

/**初始化批注列表*/
worklogCalendar.initAnno = function(worklogId){
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
					                        "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+annoReply.annoUserIdValue+"回复"+annoReply.parentUserName+"：</strong>"+annoReply.content+
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
				$('#comment').append(liStr);//.find('div[class*="hf-area"]').css("display","table-row");
			}
		}
	});
};

/**
 * 清空表单方法 
 */
worklogCalendar.clearForm = function(){
	$('#worklogCalendarForm')[0].reset();
	$('#reminderForm')[0].reset();
	$('#remindTitle').val('');
	$('#remindContentLable').html('内容');
	$('#userIds').val('');
	$('#userNames').text('');
	$('#fileupload table tbody').empty();
	selectControl.clearValue("shareUserIds-shareUserIds");
	hideErrorMessage();
};

/**打开新增页面初始化标题和日志时间*/
worklogCalendar.addInitData = function(currentDate){
	var d = currentDate.format('yyyy-MM-dd');
	var w = currentDate.getWeek();
	//设置日志标题
	$('#worklogTitle').html(d+'('+w+')');
	//设置日志时间
	$('#worklogDate').val(d);
	
};
//xuweiping 2014.11.25 求两个时间的天数差 日期格式为 YYYY-MM-dd  
worklogCalendar.daysBetween = function (DateOne,DateTwo)  
{   
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  
  
    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
  
    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
    return cha;  
}  
/**显示添加日志页面方法*/
worklogCalendar.showAddDiv = function(currentDate){
	var current = currentDate.format('yyyy-MM-dd');
	/**xuweiping  2014.11.25 添加判断，如果所选择的日期距离当前日期超过了系统参数设置的
	 * 长度（判断当前日期之前的日期，之后的不算如：当前日期为12号，系统参数为3，则9号之前的日期
	 * 不能再添加日志，而12号之后的不控制），则不允许添加日志信息（V2.3需求）
	 */
	var workLogDay = $("#workLogDay").val();
	if(workLogDay != 0 && workLogDay != null && workLogDay != ""){
		var d = new Date();
		var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//当前系统时间串
		var cha = worklogCalendar.daysBetween(str,current);//当前时间减去选择的时间，如果大于系统设置的参数值，则给出提示不允许新建日志
		if(cha >=workLogDay){
			msgBox.info({
				type: "fale",
				content: $.i18n.prop("JC_OA_PO_049")
			});
			return false;
		}
	}
	/** xuweiping 2014.11.25 end ---------------------*/
	$('#id').val('0');
	$('#modalTille').html('新增');
//	getToken();
	worklogCalendar.clearForm();
	worklogCalendar.clearDate();
	worklogCalendar.autoTrInit();
	if(currentDate){
		worklogCalendar.addInitData(currentDate);
	}
	worklogCalendar.initDiary(current,'todayDiary',worklogCalendar.autoTrTodayDiaryStr);
//	worklogCalendar.initWorkTask(current,'task',worklogCalendar.autoTrWorkTaskStr);
	worklogCalendar.initWorkTask(current,'task');
	worklogCalendar.pastFiveDays(current);
	$("#saveOrModify").show();
//	//李洪宇 于2014-7-10 修改 开始
//	//附件使用 start
//	$("#businessId").val('0');
//	//附件使用 end
//	//李洪宇 于2014-7-10 修改 结束
	//附件使用 start
	$("#businessId").val('0');
	$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
	clearDelAttachIds();//设置附件上传为逻辑删除
	$("#islogicDel").val("1");//附件使用 逻辑删除
	clearTable();
	echoAttachListDul(false);
	$('#isshow').val('0');//只允许附件下载
	//附件使用 end
	//日志标题自动添加当前日期 开始
	var year=current.substring(0,4);
	var month=current.substring(5,7);
	var day=current.substring(8,10);
	var title=year+'年'+month+'月'+day+'日工作日志';
	$("#title").val(title);
	//日志标题自动添加当前日期 结束
	$('#New-worklog').modal('show');
};



/**初始化今日日程*/
worklogCalendar.initDiary = function(currentDate,to,tr){
	var starttime = currentDate+" 00:00:00";
	var endtime = currentDate+" 23:59:59";
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/queryDiary.action",
		data : {"starttime":starttime,"endtime":endtime},
		dataType : "json",
		success : function(data) {
			if (data) {
				if(data.length==0){
					worklogCalendar.noDate(to);
					return;
				}
				for(var i=0;i<data.length;i++){
					var diary = data[i];
					worklogCalendar.initTr(to,tr,1);
					$('#'+to).find('tbody tr:last td[name="todayDiaryTitle"]').html(diary.content);
				}
			}else{
				worklogCalendar.noDate(to);
			}
		}
	});
};

worklogCalendar.noDate = function(to){
	var i = $('#'+to).find('thead th').size();
	var trStr = "<tr><td class=\"dataTables_empty\" colspan=\""+i+"\">没有匹配的记录</td></tr>";
	$('#'+to).find('tbody').append(trStr);
};


worklogCalendar.echoShowAttachList = function () {
	echoAttachList(true);
};
/**获取修改信息*/
worklogCalendar.showUpdateDiv = function (id) {
	/**xuweiping  2014.11.25 添加判断，如果所选择的日期距离当前日期超过了系统参数设置的
	 * 长度（判断当前日期之前的日期，之后的不算如：当前日期为12号，系统参数为3，则9号之前的日期
	 * 不能再添加日志，而12号之后的不控制），则不允许添加日志信息（V2.3需求）
	 */
//	echoAttachList(true);
	var authority = worklogCalendar.hasAuthority();
	if(authority == "true"){
		$('#worklog-detail').modal('hide');
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/worklog/get.action",
			data : {"id": id},
			dataType : "json",
			success : function(data) {
				if (data) {
					worklogCalendar.clearForm();
					$('#modalTille').html('编辑');
//					getToken();
					$('#token').val(data.tokenTemp);//获取token
					//清除验证信息
					worklogCalendar.initForm();
					hideErrorMessage();
					$("#worklogCalendarForm").fill(data);
					worklogCalendar.addInitData(new Date( Date.parse(data.worklogDate.replace(/-/g,"/"))));
					//填充待办任务
					//worklogCalendar.initWorkTask(data.worklogDate,'task',worklogCalendar.autoTrWorkTaskStr);
					worklogCalendar.initWorkTask(data.worklogDate,'task');
					//获取过去五天日志状态
					worklogCalendar.pastFiveDays(data.worklogDate);
					//填充今日日程
					worklogCalendar.initDiary(data.worklogDate,'todayDiary',worklogCalendar.autoTrTodayDiaryStr);
					//填充今日日志
					var todayLogs = data.todayLogs;
					if(todayLogs && todayLogs.length > 0) {
						for(var i=0;i<todayLogs.length;i++){
							var todayLog = todayLogs[i];
							worklogCalendar.initTr('todayLog',worklogCalendar.autoTrTodayLogStr,1,worklogCalendar.todayLogIdPref);
							$('#todayLog').find('tbody tr:last input').val(todayLog.content);
						}
					}
					//今日日志补行
					var todayLogLength = todayLogs!=null?todayLogs.length:0;
					if(todayLogLength < 5){
						worklogCalendar.initTr('todayLog',worklogCalendar.autoTrTodayLogStr,(5-todayLogLength),worklogCalendar.todayLogIdPref);
					}
					//填充明日提醒
					var tomorrowReminds = data.tomorrowReminds;
					if(tomorrowReminds && tomorrowReminds.length > 0) {
						for(var i=0;i<tomorrowReminds.length;i++){
							var tomorrowRemind = tomorrowReminds[i];
							worklogCalendar.initTr('tomorrowRemind',worklogCalendar.autoTrTomorrowRemindStr,1,worklogCalendar.tomorrowRemindIdPref);
							$('#tomorrowRemind').find('tbody tr:last input').val(tomorrowRemind.content);
						}
					}
					//明日提醒补行
					var tomorrowRemindLength = tomorrowReminds!=null?tomorrowReminds.length:0;
					if(tomorrowRemindLength < 3){
						worklogCalendar.initTr('tomorrowRemind',worklogCalendar.autoTrTomorrowRemindStr,(3-tomorrowRemindLength),worklogCalendar.tomorrowRemindIdPref);
					}
					//回显共享范围
					if(data.shareUserNames.length!=0){
						$("#shareUserIds-shareUserIds").select2("data", eval(data.shareUserIds));
					}
					
					//$('#shareUserNames').text(data.shareUserNames);
//					//设置附件数据
//					$('#businessId').val(data.id);
//					$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
//					showAttachList();//附件弹出层数据回显方法
					//附件使用 start
					$("#businessId").val(data.id);
					$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
					clearDelAttachIds();//设置附件上传为逻辑删除
					$("#islogicDel").val("1");//附件使用 逻辑删除
					clearTable();
					echoAttachListDul(true);
					clearTable();
					$('#isshow').val('0');//只允许附件下载
					//附件使用 end
					$("#saveOrModify").hide();
					$('#New-worklog').modal('show');
				}
			}
		});
	}else{
		msgBox.info({
			type: "fale",
			content: "您已超出操作时限！",
			callback: function(){
				$('#worklog-detail').modal('hide');
				$('#New-worklog').modal('hide');
				worklogCalendar.initForm();
				loadrightmenu('/po/worklog/myWorklogCalendarSkip.action','我的日志');
			}
		});
	}
	/** xuweiping 2014.11.25 end ---------------------*/
	
};

/**删除日志*/
worklogCalendar.deleteWorklog = function (id) {
	/**xuweiping  2014.11.25 添加判断，如果所选择的日期距离当前日期超过了系统参数设置的
	 * 长度（判断当前日期之前的日期，之后的不算如：当前日期为12号，系统参数为3，则9号之前的日期
	 * 不能再添加日志，而12号之后的不控制），则不允许添加日志信息（V2.3需求）
	 */
	var authority = worklogCalendar.hasAuthority();
	
	if(authority == "true"){
		msgBox.confirm({
			content: $.i18n.prop("JC_SYS_034"),
			success: function(){
				$.ajax({
					type : "POST",
					url : getRootPath()+"/po/worklog/deleteByIds.action?time=" + new Date(),
					data : {"id": id,"ids":id},
					dataType : "json",
					success : function(data) {
						if (data.success == "true") {
							msgBox.tip({
								type:"success",
								content: data.successMessage
							});
							$('#worklog-detail').modal('hide');
							$("#calendar").fullCalendar('refetchEvents');//重新加载此页面
						}else{
							msgBox.info({
								content: data.errorMessage
							});
						}
					}
				});
			}
		});
	}else{
		msgBox.info({
			type: "fale",
			content: "您已超出操作时限！",
			callback: function(){
				$('#worklog-detail').modal('hide');
				$('#New-worklog').modal('hide');
				worklogCalendar.initForm();
				loadrightmenu('/po/worklog/myWorklogCalendarSkip.action','我的日志');
			}
		});
	}
	/** xuweiping 2014.11.25 end ---------------------*/
};


/**初始化日历组件方法*/
worklogCalendar.initFullcalendar = function(){
    $('#calendar').fullCalendar({
		/********普通属性******/
    	header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
		selectable: true,
		selectHelper: true,
		firstDay:0,//开始的第一天
		weekends:true,//是否显示周末
		defaultView:'month',//默认显示的视图
		weekMode:'fixed',
		titleFormat:{
		    week:"yyyy 年 MMM d日' &#8212;'{ d日}", // Sep 13 2009
		},
		
		unselectAuto:false,//是否自动取消选中框
		weekends:true,//布尔类型, 默认为true, 标识是否显示周六和周日的列.
        allDayDefault:false, 
        allDayText:'全日',  
        //axisFormat:'HH(:mm)tt',
        
		/********日程编辑属性******/
		editable:false, //Boolean类型, 默认值false, 用来设置日历中的日程是否可以编辑.  可编辑是指可以移动, 改变大小等.	
		disableDragging : false, // Boolean类型, 默认false,为false时所有的event可以拖动, 必须editable =	true
		diableResizing : true, // Boolean, 默认false, 所有的event可以改变大小,必须editable = true
		/*timeFormat:{//设置标题头时间格式
    		month:'H:mm{-H:mm} ',
           	agendaWeek:'H:mm{ - H:mm}',
           	agendaDay:'H:mm{ - H:mm}'
    	},*/
		eventClick:function( calEvent, jsEvent, view ) { //事件被点击
			$("#currentDate").val(calEvent.start);
			worklogCalendar.showDetail(calEvent.id);
			 
		},
		select: function( startDate, endDate, allDay, jsEvent, view ){//日期选择后
			if(jsEvent){//鼠标点击
				$("#currentDate").val(startDate);
				var d = startDate.format('yyyy-MM-dd');
				var worklogDateBegin = d+" 00:00:00";
				var worklogDateEnd = d+" 23:59:59";
				$.ajax({
					type : "POST",
					url : getRootPath()+"/po/worklog/worklogAllowInsert.action?time=" + new Date(),
					data : {"worklogDateBegin": worklogDateBegin,'worklogDateEnd':worklogDateEnd},
					dataType : "json",
					success : function(data) {
						if (data.success) {
							worklogCalendar.showAddDiv(startDate);
							$("#token").val(data.token);
						}else{
							worklogCalendar.showDetail(null,d);
						}
					}
				});
			}else{//快速定位
				
			}
		},
		events: function(start,end, callback) {//装载我的日志数据
 			var startDate = $.fullCalendar.formatDate(start,"yyyy-MM-dd HH:mm:ss");  
            var endDate = $.fullCalendar.formatDate(end,"yyyy-MM-dd HH:mm:ss");
			jQuery.ajax({
				type:"POST",	
				url:getRootPath()+"/po/worklog/getMyworklogList.action?time="+new Date(),
				data:{"worklogDateBegin":startDate,"worklogDateEnd":endDate},
				async:false,
				success:function(data){
					var datas=[];
					for(i=0;i<data.length;i++){
						var checkAllDay=false;
						var id = data[i].id;
						var title = data[i].title+" ";
						start =new Date( Date.parse(data[i].worklogDate.replace(/-/g,   "/")));
						var color = '#60aae9';
						if(data[i].isShare=='1'){
							color = '#ffc333';
						}
						datas.push({
							id: id,
							color:color,
							title: title,
							start: start,
							allDay:true
						});
					}
					callback(datas);
				}
			});
     	}
	});
}; 

/*worklogCalendar.worklogAllowInsert = function(currentDate){
	var d = currentDate.format('yyyy-MM-dd');
	var worklogDateBegin = d+" 00:00:00";
	var worklogDateEnd = d+" 23:59:59";
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/worklog/worklogAllowInsert.action?time=" + new Date(),
		data : {"worklogDateBegin": worklogDateBegin,'worklogDateEnd':worklogDateEnd},
		dataType : "json",
		success : function(data) {
			if (data.success == "false") {
				msgBox.info({
					content: data.errorMessage
				});
			}
			worklogCalendar.allowInsert = data.success;
		}
	});
};*/

/**校验短信提醒*/
worklogCalendar.validRemind = function(hide){
	var userIds = $('#shareUserIds-shareUserIds').val();
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
	$("#shareUserIds-shareUserIds").val(remainManTemp);
	//将共享范围人员去重 李洪宇 end
	var reminder = $('input[name="remind"]:checked').val();
	if(reminder=='2'){
		jQuery.ajax({
			url : getRootPath()+"/po/worklog/validRemind.action?time=" + new Date(),
			type : 'get',
			async:false,
			dataType : "json",
			data : {'shareUserIds':remainManTemp},
//			data : {'shareUserIds':userIds},
			success : function(data) {
				if(data.success=="success"){
					worklogCalendar.saveOrModifySubmit(hide);
				}else{
					if(data.success){
						msgBox.confirm({
							content: data.successMessage,
							success: function(){
								worklogCalendar.saveOrModifySubmit(hide);
							},
							cancel:function(){
								//$('input[type="radio"][name="remind"]').get(0).checked = true;
								//worklogCalendar.saveOrModifySubmit(hide);
							}
						});
						
					}else{
						msgBox.info({
							content: data.errorMessage
						});
					} 
				}
				
				/*if(data.success){
					msgBox.confirm({
						content: data.successMessage,
						success: function(){
							worklogCalendar.saveOrModifySubmit(hide);
						},
						cancel:function(){
							 //$('input[type="radio"][name="remind"][value="0"]').attr('checked','checked');
							 $('input[type="radio"][name="remind"]').get(0).checked = true;
							 worklogCalendar.saveOrModifySubmit(hide);
						}
					});
				} */
			},
			error : function() {
				msgBox.tip({
					content: $.i18n.prop("JC_OA_PO_015")
				});
				
			}
		});
	}else{
		worklogCalendar.saveOrModifySubmit(hide);
	}
};
worklogCalendar.saveOrModify = function (hide) {
	// xuweiping 2014.11.28 添加判断保存时是否具备操作权限，此处添加是为了解决并发操作引起的错误
	var authority = worklogCalendar.hasAuthority();
	
	if(authority == "true"){
		worklogCalendar.validRemind(hide);
	}
	else{
		msgBox.info({
			type: "fale",
			content: "您已超出操作时限！",
			callback: function(){
				$('#New-worklog').modal('hide');
				worklogCalendar.initForm();
				loadrightmenu('/po/worklog/myWorklogCalendarSkip.action','我的日志');
			}
		});
	}
};
//获取系统参数的值  xuweiping 2014.11.28  添加判断保存时是否具备操作权限，此处添加是为了解决并发操作引起的错误
worklogCalendar.hasAuthority = function(){
	var str = "";
	//获取系统参数的值
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/getWorkLogDay.action?time=" + new Date(),
		dataType : "json",
		cache:false,  
	    async:false, //同步处理 
		success : function(data) {
			if(data.workLogDay == 0 || data.workLogDay == null || data.workLogDay == ""){
				str = "true";
			}else{
				var currentDate = $("#currentDate").val();
				var current = new Date(Date.parse(currentDate.replace(/-/g,"/"))).format('yyyy-MM-dd');
				var d = new Date();
				var s = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//当前系统时间串
				var cha = worklogCalendar.daysBetween(s,current);//当前时间减去选择的时间，如果大于系统设置的参数值，则给出提示不允许新建日志
				if(cha <data.workLogDay){
					str = "true";
				}else{
					str = "false";
				}
			}
		}
	});
	return str;
}

/**点击关闭附件弹出层是清空内容*/
worklogCalendar.fileupload = function (){
	$("#businessId").val("0");
	//showAttachList(true);
	echoAttachList(true);
};


/**点击关闭时删除一上传文件*/
worklogCalendar.deleteAttach = function() {
	if($("#fileids").val().length > 0 && $("#fileid_old").val() != $("#fileids").val()){
		var ids = $("#fileids").val();
		$.ajax({
			type : "POST",
			url : getRootPath()+"/content/attach/delete.action",
			data : {"ids": ids},
			dataType : "json",
			success : function(data) {
				carInfo.carInfoList();
			}
		});
	}
};

/**添加修改公用方法*/
worklogCalendar.saveOrModifySubmit = function (hide) {
	if (worklogCalendar.subState)return;
	worklogCalendar.subState = true;
	if ($("#worklogCalendarForm").valid()) {
		var url = getRootPath()+"/po/worklog/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/po/worklog/update.action?time=" + new Date();
		}
		var formData = $("#worklogCalendarForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
		//添加其他表单信息
		worklogCalendar.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			contentType: "application/json;charset=UTF-8",//一对多关系必填否则去掉
//			data : JSON.stringify(serializeJson(formData)),//Ajax提交方法的代码在WinXP+IE8的环境下会出现提交的数据值为 "null"的情况，可能会造成服务端类型转换异常
			data : JSON.stringify(serializeJson(formData),replaceJsonNull),
			success : function(data) {
				worklogCalendar.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$('#calendar').fullCalendar( 'refetchEvents' );//重新加载日志页面
					var currentDate = $('#worklogDate').val();
					//if ($("#id").val() == 0) {
						worklogCalendar.initForm();
					//}
					if (hide) {
						$('#New-worklog').modal('hide');
					}else{
						if(currentDate){
							worklogCalendar.addInitData(new Date(Date.parse(currentDate.replace(/-/g,"/"))));
						}
						$('#worklogDate').val(currentDate);
						worklogCalendar.autoTrInit();
						
					}
//					getToken();
					//worklogCalendar.userList();
				} else {
					if(data.labelErrorMessage){
						showErrors("worklogCalendarForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
//					getToken();
				}
			},
			error : function() {
				worklogCalendar.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		worklogCalendar.subState = false;
		msgBox.info({
//			content:"表单信息填写错误",
			content: $.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

/**添加今日日志，明日提醒表单数据*/
worklogCalendar.addFormParameters = function (formData){
	//根据实际业务组装json对象
	var todayLogs = new Array(), tomorrowReminds = new Array(), todayLogValue = new Array(), tomorrowRemindValue = new Array();
	//组织今日日志
	$.each($("[id^="+worklogCalendar.todayLogIdPref+"]"), function(i, o) {
		if($.trim(o.value)!=""){
			todayLogValue.push(o.value);
		}
	});
	$.each(todayLogValue, function(i, o) {
		todayLogs.push({"content":todayLogValue[i],"contentType":0});
	});
	formData.push({"name": "todayLogs", "value": todayLogs});
	//组织明日提醒
	$.each($("[id^="+worklogCalendar.tomorrowRemindIdPref+"]"), function(i, o) {
		if($.trim(o.value)!=""){
			tomorrowRemindValue.push(o.value);
		}
	});
	
	$.each(tomorrowRemindValue, function(i, o) {
		tomorrowReminds.push({"content":tomorrowRemindValue[i],"contentType":1});
	});
	formData.push({"name": "tomorrowReminds", "value": tomorrowReminds});
	//共享提醒
	var reminder = $('input[name="remind"]:checked').val();
	formData.push({"name": "remindType", "value": reminder});
	if(reminder!='0'){
		//提醒人
		var remindMan = $('#leftRight-leftRight').val();
		formData.push({"name":"remindMan","value":remindMan});
		//提醒内容
		var remindTitle = $('#remindTitle').val();
		formData.push({"name":"remindTitle","value":remindTitle});
		//提醒内容
		var remindContent = $('#remindContent').val();
		if($.trim(remindContent)!=''){
			formData.push({"name":"remindContent","value":remindContent});
		}
	}
	
	//附件
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};

/**日志日历形式查询*/
worklogCalendar.searchFullCalendar = function(){
	var searchtime=$('#searchtime').val();
	if(searchtime==null||searchtime=='')return false;
	var searchdate = new Date( Date.parse(searchtime.replace(/-/g,"/")));
	$('#calendar').fullCalendar( 'gotoDate', searchdate );
	$('#calendar').fullCalendar( 'select', searchdate, searchdate, true);
};

/**添加成功清空表单数据*/
worklogCalendar.initForm = function(){
	worklogCalendar.clearForm();
	worklogCalendar.clearDate();
};

/**清除详情页数据*/
worklogCalendar.clearDetail = function(){
	$('#detailTodayLog tbody').empty();
	$('#detailTomorrowRemind tbody').empty();
	$('#detailTodayDiary tbody').empty();
	$('#detailTask tbody').empty();
	$('#comment').empty();
	//清除附件信息
	clearTable();
	clearAttachlist();
};
/**清除添加修改页数据*/
worklogCalendar.clearDate = function(){
	$('#todayLog tbody').empty();
	$('#tomorrowRemind tbody').empty();
	$('#todayDiary tbody').empty();
	$('#task tbody').empty();
	//清除附件信息
	clearTable();
	clearAttachlist();
};
/**查看日志详细*/
worklogCalendar.showDetail = function(id,currentDate){
	var d ;
	if(id!=null){
		d = {"id": id};
	}else{
		var worklogDateBegin = currentDate+" 00:00:00";
		var worklogDateEnd = currentDate+" 23:59:59";
		d = {"worklogDateBegin": worklogDateBegin,"worklogDateEnd":worklogDateEnd};
	}
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/get.action?time=" + new Date(),
		data : d,
		dataType : "json",
		success : function(data) {
			if (data) {
				$('#token').val(data.tokenTemp);//获取token
				//清除详情页数据
				worklogCalendar.clearDetail();
				$('#detailId').val(data.id);
				$('#createUser').val(data.createUser);
				$('#createUserDept').val(data.createUserDept);
				//回显日志详细数据
				var currentDate = new Date(Date.parse(data.worklogDate.replace(/-/g,"/")));
				var d = currentDate.format('yyyy-MM-dd');
				var w = currentDate.getWeek();
				//设置日志标题
				$('#detailWorklogTitle').html(d+'('+w+')');
				$('#detailTitle').html(data.title);
				$('#detailWorklogDate').html(data.worklogDate);
				$('#detailSentimentRemark').html(data.sentimentRemark);
				$('#detailShareUserNames').html(data.shareUserNames);
				//填充今日日程
				worklogCalendar.initDiary(data.worklogDate,'detailTodayDiary',worklogCalendar.autoTrDetailTodayDiaryStr);
				//填充待办任务
				//worklogCalendar.initWorkTask(data.worklogDate,'detailTask',worklogCalendar.autoTrDetailWorkTaskStr);
				worklogCalendar.initDetailWorkTask(data.worklogDate,'detailTask');
				//填充批注
				worklogCalendar.initAnno(data.id);
				//填充今日日志
				var todayLogs = data.todayLogs;
				if(todayLogs==null||todayLogs.length==0){
					worklogCalendar.noDate('detailTodayLog');
				}
				if(todayLogs && todayLogs.length > 0) {
					for(var i=0;i<todayLogs.length;i++){
						var todayLog = todayLogs[i];
						worklogCalendar.initDetailTr('detailTodayLog',worklogCalendar.autoTrDetailStr,todayLog.content);
					}
				}
				
				//填充明日提醒
				var tomorrowReminds = data.tomorrowReminds;
				if(tomorrowReminds==null||tomorrowReminds.length==0){
					worklogCalendar.noDate('detailTomorrowRemind');
				}
				if(tomorrowReminds && tomorrowReminds.length > 0) {
					for(var i=0;i<tomorrowReminds.length;i++){
						var tomorrowRemind = tomorrowReminds[i];
						worklogCalendar.initDetailTr('detailTomorrowRemind',worklogCalendar.autoTrDetailStr,tomorrowRemind.content);
					}
				}
//				//设置附件数据
//				$('#businessId').val(data.id);
//				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
//				showAttachList();//附件填出层数据回显方法
//				echoAttachList();//附件列表数据回显方法	
				//附件使用 start
				$("#businessId").val(data.id);
				$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				clearTable();
				echoAttachListDul(false,'attachList_1');
				$('#isshow').val('1');//只允许附件下载
				//附件使用 end
				worklogCalendar.annoReading(data.id,data.status);
				//更新阅读情况  李洪宇 2014-9-22
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
	if(id!=null){
		$('#worklog-detail').modal('show');
	}else{
		$('#New-worklog').modal('hide');
		$('#worklog-detail').modal('show');
	}
};

/**更新批注的阅读状态*/
worklogCalendar.annoReading = function(id,status){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/annoReading.action?time=" + new Date(),
		data : {'businessId':id,'annoType':2,'status':status},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				//alert('批注阅读状态更新成功');
			}
		}
	});
};

/**插入日志方法*/
worklogCalendar.insertWorklog = function(e){
	var title = $(e).parents('td').prevAll('[name$="Title"]').html()||$(e).parents('td').find('input[name$="Title"]').val();
	var isAddTr = true; 
	$('#todayLog input').each(function(){
		if($(this).val()==''){
			$(this).val(title);
			isAddTr = false;
			return false;
		}
	});
	if(isAddTr){
		worklogCalendar.initTr('todayLog',worklogCalendar.autoTrTodayLogStr,1,worklogCalendar.todayLogIdPref);
		$('#todayLog').find('tbody tr:last input').val(title);
	}
};


worklogCalendar.taskToDiary = function(e){
	//日程标题
	var title = $(e).parents('tr').find('input[name="taskTitle"]').val();
	//日程内容
	var content = $(e).parents('tr').find('input[name="taskContent"]').val();
	//实际开始时间
	var actStartTime = $(e).parents('tr').find('input[name="actStartTime"]').val() + ' 00:00:00';
	//计划结束时间
	var endTime = $(e).parents('tr').find('input[name="endTime"]').val() + ' 23:59:59';
	
	$('#showDiaryBox').find('input[name="taskTitle"]').val(title);
	$('#showDiaryBox').find('input[name="taskContent"]').val(content);
	$('#showDiaryBox').find('input[name="actStartTime"]').val(actStartTime);
	$('#showDiaryBox').find('input[name="endTime"]').val(endTime);
	$('#showDiaryBox').find('input[name="moduleFlag"]').val('3');
	
	$('#diaryUser').html($('#currentUser').val());
	$('#diaryTitle').html(title);
	$('#diaryStartTime').html(actStartTime);
	$('#diaryEndTime').html(endTime);
	$('#diaryContent').html(content);
	$('#showDiaryBox').modal('show');
}

/**待办任务插入日程方法*/
worklogCalendar.toDiary = function(e){
	//日程标题
	var title = $(e).parents('div[id="showDiaryBox"]').find('input[name="taskTitle"]').val();
	//日程内容
	var content = $(e).parents('div[id="showDiaryBox"]').find('input[name="taskContent"]').val();
	//实际开始时间
	var actStartTime = $(e).parents('div[id="showDiaryBox"]').find('input[name="actStartTime"]').val();
	//计划结束时间
	var endTime = $(e).parents('div[id="showDiaryBox"]').find('input[name="endTime"]').val();
	var moduleFlag = $(e).parents('div[id="showDiaryBox"]').find('input[name="moduleFlag"]').val();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/worklog/taskToDiary.action?time=" + new Date(),
		data : {"title": title,"content":content,"starttime":actStartTime,"endtime":endTime,"moduleFlag":moduleFlag},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				
				$('#showDiaryBox').modal('hide');
				$('#showDiaryBox').find('input[name="taskTitle"]').val('');
				$('#showDiaryBox').find('input[name="taskContent"]').val('');
				$('#showDiaryBox').find('input[name="actStartTime"]').val('');
				$('#showDiaryBox').find('input[name="endTime"]').val('');
				$('#diaryUser').html('');
				$('#diaryTitle').html('');
				$('#diaryStartTime').html('');
				$('#diaryEndTime').html('');
				$('#diaryContent').html('');
			}else{
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};
/**明日提醒插入日程方法*/
worklogCalendar.worklogToDiary = function(e){
	
	//日程内容
	var content =$.trim($(e).parents('tr').find('input[class*="addLine"]').val());
	if(content==''){
		msgBox.info({
			content: "提醒为空，不能加入日程"
		});
		return ;
	}
	
	//日程标题直接取日志标题
	var title = $.trim($('#title').val());
	if(title==''){
		msgBox.info({
			content: "日志标题为空，不能加入日程"
		});
		return ;
	}
	//实际开始时间：当前时间加一天
	var d = new Date( Date.parse($('#worklogDate').val().replace(/-/g,"/")));  
	d = d.valueOf();
	d = d + 1 * 24 * 60 * 60 * 1000;
	d = (new Date(d)).format('yyyy-MM-dd'); 
	var actStartTime = d+' 00:00:00';
	var endTime = d+' 23:59:59';
	
	
	$('#showDiaryBox').find('input[name="taskTitle"]').val(title);
	$('#showDiaryBox').find('input[name="taskContent"]').val(content);
	$('#showDiaryBox').find('input[name="actStartTime"]').val(actStartTime);
	$('#showDiaryBox').find('input[name="endTime"]').val(endTime);
	$('#showDiaryBox').find('input[name="moduleFlag"]').val('2');
	
	$('#diaryUser').html($('#currentUser').val());
	$('#diaryTitle').html(title);
	$('#diaryStartTime').html(actStartTime);
	$('#diaryEndTime').html(endTime);
	$('#diaryContent').html(content);
	$('#showDiaryBox').modal('show');
};

/**查询过去五天的日志情况*/
worklogCalendar.pastFiveDays = function(currentDate){
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/worklog/queryMyWorklogPastFiveDays.action?time=" + new Date(),
		data : {"worklogDate": currentDate},
		dataType : "json",
		success : function(data) {
			$('#pastFiveDays').empty();
			if (data) {
				var pastFiveDays = "过去五天：";
				for(var i=0;i<data.length;i++){
					var worklog = data[i];
					var date = new Date( Date.parse(worklog.worklogDate.replace(/-/g,   "/")));
					var d = date.format('dd');
					var w = date.getWeek().substring(2,3);
					pastFiveDays +=d+"("+w+")" ;
					if(worklog.counts>0){
						pastFiveDays +="<a href=\"javascript:void(0)\" class=\"btn-link m-l-xs m-r-xs\" name=\"read\" data=\""+worklog.worklogDate+"\" >看</a>";
					}else{
						pastFiveDays +="<a href=\"javascript:void(0)\" class=\"btn-link m-l-xs m-r-xs\" name=\"write\" data=\""+worklog.worklogDate+"\">写</a>";
					}
				}
				$('#pastFiveDays').append(pastFiveDays);
			}
		}
	});
};

/**批注回复*/
worklogCalendar.commentReply = function(e){
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
worklogCalendar.commentSend = function(e){
	var businessId = $('#detailId').val();
	var annoParentId = $(e).attr('data');
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	var content  = $(e).parent().prevAll('textarea').val();
	var byAnnoUserId = $('#createUser').val();
	var byAnnoUserDepid = $('#createUserDept').val();
	var annoName = $('#detailTitle').html();
	if(!DeleteCascade.syncCheckExist("workLogAnno",businessId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogCalendar.subState = false;//更新重复提交状态标识
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
						worklogCalendar.initAnno(businessId);
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
/**保存领导批注*/
worklogCalendar.saveLeaderIdeaForm = function(){
	var worklogId = $('#detailId').val();
	if(worklogId=='')return;
	//校验是否重复提交
	if (worklogCalendar.subState)return;
	worklogCalendar.subState = true;
	//校验表单信息
	if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogCalendar.subState = false;//更新重复提交状态标识
		return;
	}else{
		if ($("#leaderIdeaForm").valid()) {
			var url = getRootPath()+"/po/anno/save.action?time=" + new Date();
			//将表单序列化成json格式
			var formData = $("#leaderIdeaForm").serializeArray();
			formData.push({"name": "businessId", "value": worklogId});
			formData.push({"name": "annoType", "value": 2});
			formData.push({"name": "annoName", "value": $('#detailTitle').text()});
			formData.push({"name":"byAnnoUserId","value":$('#createUser').val()});
//			formData.push({"name":"byAnnoUserDepid","value":$('#annoCreateUserDept').val()});
			formData.push({"name":"token","value":$('#token').val()});
			jQuery.ajax({
				url : url,
				type : 'POST',
				data : formData,
				success : function(data) {
					worklogCalendar.subState = false;//更新重复提交状态标识
					$('#token').val(data.token);
					if(data.success == "true"){
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
						$('#leaderIdeaContent').val('');
						$('#comment').empty();
						worklogCalendar.initAnno(worklogId);
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
					worklogCalendar.subState = false;//更新重复提交状态标识
					msgBox.tip({
						content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
					});
				}
			});
		} else {
			worklogCalendar.subState = false;//更新重复提交状态标识
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
worklogCalendar.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};

//分页对象
worklogCalendar.collectTable = null;

//分页查询用户
worklogCalendar.collectSearch = function () {
	$('#startDateTemp').val($("#worklogDateBegin").val());//将查询条件赋值给临时变量 add by lihongyu at 2014-11-10
	$('#endDateTemp').val($("#worklogDateEnd").val());//将查询条件赋值给临时变量  add by lihongyu at 2014-11-10
	//显示列信息
	worklogCalendar.collectTableAoColumns = [
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
				reVal="<a href=\"#\" onclick=\"worklogCalendar.showAnno("+full.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
			}
			return reVal;
		}}
	 ];
	
	//组装后台参数
	worklogCalendar.collectTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogCalendar.collectTable, aoData);
		//组装查询条件
		var worklogDateBegin = $("#worklogDateBegin").val();
		var worklogDateEnd = $("#worklogDateEnd").val();
		
		aoData.push({ "name": "worklogDateBegin", "value": worklogDateBegin});
		aoData.push({ "name": "worklogDateEnd", "value": worklogDateEnd});
	};
	
	//table对象为null时初始化
	if (worklogCalendar.collectTable == null) {
		worklogCalendar.collectTable = $('#collectTable').dataTable( {
			iDisplayLength: 5,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/po/worklog/getMyworklogCollect.action?time=" + new Date(),
			//bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogCalendar.collectTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogCalendar.collectTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,3]}]
		} );
		//setColumnVis(worklogCalendar.collectTable, 6);
		
	} else {
		//table不为null时重绘表格
		worklogCalendar.collectTable.fnDraw();
		//pageChange(user.oTable);
	}
};

/**初始化待办任务汇报数据*/
worklogCalendar.initTaskReport = function(e){
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



/**待办任务汇报*/
worklogCalendar.taskReport = function(){
		if (worklogCalendar.subState)return;
		worklogCalendar.subState = true;
		if ($("#reportForm").valid()) {
			var url = getRootPath()+"/po/worklog/taskReport.action";
			var formData = $("#reportForm").serializeArray();
			formData.push({"name": "title", "value": $("#reportForm input[name='taskTitle']").val()});
			formData.push({"name": "token", "value": $("#token").val()});//当同一页面存在多个token时，将token提取到主页面并且保留一个token。
			$.ajax({
				url : url,
				type : 'post',
//				data : JSON.stringify(serializeJson(formData)),//Ajax提交方法的代码在WinXP+IE8的环境下会出现提交的数据值为 "null"的情况，可能会造成服务端类型转换异常
				data : JSON.stringify(serializeJson(formData),replaceJsonNull),
				contentType: "application/json;charset=UTF-8",
				success : function(data) {
					worklogCalendar.subState = false;
//					getToken();
					$('#token').val(data.token);
					if(data.success == "true"){
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
						worklogCalendar.initWorkTask($('#worklogDate').val(),'task');
						$('#report-modal').modal('hide');
					} else {
						if(data.labelErrorMessage){
							showErrors("reportForm", data.labelErrorMessage);
						} else{
							msgBox.info({
								content: data.errorMessage
							});
						}
					}
				},
				error : function() {
					worklogCalendar.subState = false;
					msgBox.tip({
						content: $.i18n.prop("JC_SYS_002")
					});
				}
			});
		} else {
			worklogCalendar.subState = false;
			msgBox.info({
				content:"表单信息填写错误",
				success:function(){
					fnCall();
				}
			});
		}
};

/**删除行方法e:事件源；num:最少保留多少行*/
worklogCalendar.deleteTr =function(e,num){
	if($(e).parents('table tbody').find('tr').size()>num){
		var no = $(e).parents('tr').find('td:first span').html();
		$(e).parents('tr').nextAll().each(function(index){
			$(this).find('td:first span').html(Number(no)+index);
		});
		$(e).parents('tr').detach();
		$('.table').hide();
		$('.table').show();
		$(".tooltip").hide();
		}else{
		msgBox.info({
			content: $.i18n.prop("JC_OA_PO_005")
		});
		//$(e).parents('tr').find('input').val('');
	}
};

/**提醒设置*/
worklogCalendar.reminder = function(){
	
	
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
//	if(shareUserIds==''||shareUserIds==null){
	if(remainManTemp==''||remainManTemp==null){
		msgBox.info({
			content:'请先选择共享人',
			type:'fail'
		});
		return;}
	
	$('#leftAndRightSelectDiv').empty();
	leftRightSelect.init({
		containerId:"leftAndRightSelectDiv",
		moduleId:"leftRight-leftRight",
		isCheck:true,
//		url:getRootPath()+"/po/worklog/getRemaidManData.action?ids="+shareUserIds,
		url:getRootPath()+"/po/worklog/getRemaidManData.action?ids="+remainManTemp,
		title:"被提醒人"
	});
	$('#reminderForm')[0].reset();
//	leftRightSelect.setData("leftRight-leftRight",shareUserIds);
	leftRightSelect.setData("leftRight-leftRight",remainManTemp);
	$('#remindContentLable').html('内容');
	$('#remindTitleTr').val('').hide();
	$('#reminderModal').modal('show');
};

/**校验提醒内容*/
worklogCalendar.reminderValid = function(){
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

worklogCalendar.importYesterdayRemind = function(){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/getYesterdayRemind.action",
		data : {"worklogDate":$('#worklogDate').val()},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				var worklogContents = data.worklogContents;
				if(worklogContents==undefined||worklogContents.length==0){
					msgBox.tip({
						content: "昨日日志不存在提醒"
					});
					return;
				}
				for(var i=0;i<worklogContents.length;i++){
					var isAddTr= true;
					var worklogContent = worklogContents[i];
					$('#todayLog input').each(function(){
						if($(this).val()==''){
							$(this).val(worklogContent.content);
							isAddTr = false;
							return false;
						}
					});
					if(isAddTr){
						worklogCalendar.initTr('todayLog',worklogCalendar.autoTrTodayLogStr,1,worklogCalendar.todayLogIdPref);
						$('#todayLog').find('tbody tr:last input').val(worklogContent.content);
					}
				}
			}else{
				msgBox.tip({
					content:data.errorMessage
				});
			}
		}
	});
};
/**查看领导批注*/
worklogCalendar.showAnno = function(dataid){
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
worklogCalendar.closeAnno = function(){
	$('#annoComment').empty();
};
//阅读显示列信息
worklogCalendar.oTableReadAoColumns = [
	{mData: "readerName"},//阅读人
	{mData: "readingDate"}//阅读时间
 ];
/**
 * 初始化阅读情况列表
 */
worklogCalendar.initReadList = function (){
	var planId=$('#detailId').val();
	if(!DeleteCascade.syncCheckExist("workLogAnno",planId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		$('#read').modal('show');
		//阅读情况分页显示条数
		worklogCalendar.readPageRows = TabNub > 0 ? TabNub : 10;
		worklogCalendar.oTableReadFnServerParams = function(aoData){
			 getTableParameters(worklogCalendar.oTableRead, aoData);
			 aoData.push({ "name": "worklogId", "value": planId});
		     aoData.push({ "name": "businessType", "value": 2});
		};
		if (worklogCalendar.oTableRead == null) {
			worklogCalendar.oTableRead = $('#readTable').dataTable( {
				"iDisplayLength": worklogCalendar.readPageRows,//每页显示多少条记录
				"sAjaxSource": getRootPath()+"/po/readingStatus/queryAllByDataTable.action?time=" + new Date(),//后台分页查询地址url
				"fnServerData": oTableRetrieveData,//查询数据回调函数
				"aoColumns": worklogCalendar.oTableReadAoColumns,//table显示列
				//传参
				"fnServerParams": function ( aoData ) {
					worklogCalendar.oTableReadFnServerParams(aoData);
				},
				bPaginate:false,
				//默认不排序列
		        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
			} );
		} else {
			//table不为null时重绘表格
			worklogCalendar.oTableRead.fnDraw();
		}
	}
};
$('#today').on('click', function() {//清除快速查询条件
	$('#searchtime').val("");
    $('.calendar').fullCalendar('today');
});
//初始化
jQuery(function($) 
{
	//计算分页显示条数
	worklogCalendar.pageRows = TabNub>0 ? TabNub : 10;
	/**提醒设置相关开始*/
	$('#reminder').click(function(){
		worklogCalendar.reminder();
	});
	
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
	
	$('#remindOk').click(function(){
		if(worklogCalendar.reminderValid())
			$('#reminderModal').modal('hide');
	});
	/**提醒设置相关结束*/
	//附件使用
	$("#closebtn").click(worklogCalendar.fileupload);
	$("#closeModalBtn").click(worklogCalendar.fileupload);
	$("#queryattach").click(worklogCalendar.fileupload);
	//绑定汇总查询
	$('#collectSearch').click(function(){
		worklogCalendar.collectSearch();
	});
	//绑定日志汇总
	$('#collect').click(function(){
		$('#collectForm')[0].reset();
		$('#collectTable tbody').empty();
		$('#worklogDateBegin').val(new Date().format('yyyy-MM-dd'));
		$('#worklogDateEnd').val(new Date().format('yyyy-MM-dd'));
		worklogCalendar.collectSearch();
		$('#worklog_summary').modal('show');
	});
	
	//绑定日志汇总重置
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
		worklogCalendar.commentReply(this);
	});
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){worklogCalendar.saveLeaderIdeaForm();});
	//绑定批注”发送“
	$('#comment').on('click','a[name="send"]',function(){
		worklogCalendar.commentSend(this);
	});
	//绑定批注内容清除
	$("#clearleaderIdea").click(function(){
		worklogCalendar.clearleaderIdea();
	});
	//绑定”看“日志
	$('#pastFiveDays').on('click','a[name="read"]',function(){
		var currentDate = $(this).attr('data');
		worklogCalendar.showDetail(null,currentDate);
	});
	//绑定”写“日志
	$('#pastFiveDays').on('click','a[name="write"]',function(){
		var currentDate = $(this).attr('data');
		worklogCalendar.showAddDiv(new Date(Date.parse(currentDate.replace(/-/g,"/"))));
	});
	//绑定”快速查找“
	$('#search').click(function(){worklogCalendar.searchFullCalendar();});
	//绑定”修改按钮“
	$('#modify').click(function(){
		worklogCalendar.showUpdateDiv($('#detailId').val());
	});
	$('#delete').click(function(){
		worklogCalendar.deleteWorklog($('#detailId').val());
	});
	//绑定导入昨天提醒
	$('#importYesterdayRemind').click(function(){
		//$('#worklog-detail').modal('hide');
		worklogCalendar.importYesterdayRemind();
	});
	//绑定“保存关闭”事件
	$("#saveAndClose").click(function(){worklogCalendar.saveOrModify(true);});
	//绑定“保存继续”事件
	//$("#saveOrModify").click(function(){worklogCalendar.saveOrModify(false);});
	//绑定“列表形式展示”事件
	$('#showList').click(function(){
		worklogCalendar.loadrightmenu("/po/worklog/myWorklogListSkip.action");
	});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化日历组件
	worklogCalendar.initFullcalendar();
	//绑定日视图
	/*$('#dayview').on('click', function() {
      $('.calendar').fullCalendar('changeView', 'agendaDay');
    });*/
	//绑定周视图
    $('#weekview').on('click', function() {
      $('.calendar').fullCalendar('changeView', 'basicWeek');
      worklogCalendar.searchFullCalendar();
    });
    //绑定月视图
    $('#monthview').on('click', function() {
      $('.calendar').fullCalendar('changeView', 'month');
      worklogCalendar.searchFullCalendar();
    });
    //绑定今日
    $('#today').on('click', function() {
    	$('.calendar').fullCalendar('today');
    });
    //绑定待办任务和今日日程的“插入日志”
    $('#todayDiary,#task').on('click','a[name="insertWorklog"]',function(){
    	worklogCalendar.insertWorklog(this);
    });
    //绑定待办任务的“插入日程”
    $('#task').on('click','a[name="taskToDiary"]',function(){
    	worklogCalendar.taskToDiary(this);
    });
    
    //绑定“插入日程”
    $('#showDiaryBox').on('click','#toModify',function(){
    	worklogCalendar.toDiary(this);
    });
    
    //绑定明日提醒的“插入日程”
    $('#tomorrowRemind').on('click','a[name="worklogToDiary"]',function(){
    	worklogCalendar.worklogToDiary(this);
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
    //绑定代办任务的汇报
    $('#task tbody').on('click','a[name="report"]',function(){
    	worklogCalendar.initTaskReport(this);
    	$('#report-modal').modal('show');
    });
    //绑定代办任务的汇报提交
    $('#report-modal').on('click','#reportSure',function(){
    	worklogCalendar.taskReport();
    	//$('#report-modal').modal('show');
    });
    //绑定今日日志删除行按钮
    $('#todayLog tbody').on('click','a[name="deleteTr"]',function(){
    	worklogCalendar.deleteTr(this,1);
    });
    
    //绑定明日提醒删除行按钮
    $('#tomorrowRemind tbody').on('click','a[name="deleteTr"]',function(){
    	worklogCalendar.deleteTr(this,1);
    });
    
    selectControl.init("shareUserNames","shareUserIds-shareUserIds",true,true);//人员选择树 参数divid 设置的id-name 多选 人员
	worklogCalendar.autoTrBindEvent('todayLog',worklogCalendar.autoTrTodayLogStr,worklogCalendar.todayLogIdPref);
	worklogCalendar.autoTrBindEvent('tomorrowRemind',worklogCalendar.autoTrTomorrowRemindStr,worklogCalendar.tomorrowRemindIdPref);
	
	//alert(betw('1,4,5','1,2,3'));
});
  	
    
//@ sourceURL=myWorklogCalendar.js