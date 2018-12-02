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

worklogShare ={};
var tree = null;

var worklogShareTreeToPage = {};

worklogShareTreeToPage.zNodes = null;
worklogShareTreeToPage.id = null;
worklogShareTreeToPage.name = null;
worklogShareTreeToPage.callback = null;
worklogShareTreeToPage.rootId = null;
worklogShareTreeToPage.rootName = null;

/**日志详细今日日志及明日提醒动态添加行模版*/
worklogShare.autoTrDetailStr = "<tr>"+
								"<td>第<span></span>条</td>"+
								"<td>" +
								"</td>"+
								"</tr>";
/**日志详细今日日程动态添加行模版*/
worklogShare.autoTrDetailTodayDiaryStr = "<tr>"+
									"<td>第<span></span>条</td>"+
									"<td name=\"todayDiary-title\">" +
									"</td>"+
									"</tr>";

/**详细页待办任务动态添加行模版*/
worklogShare.autoTrDetailWorkTaskStr =  "<tr>"+
										"<input type=\"hidden\" name=\"content\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"startTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"endTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"actEndTime\" value=\"\"/>"+
										"<input type=\"hidden\" name=\"userId\" value=\"\"/>"+
										"<td name=\"task-title\"></td>"+
										"<td name=\"taskProc\"></td>"+
										"<td name=\"sponsorId\"></td>"+
										"<td name=\"actStartTime\"></td>"+
										"</tr>";

//下属人员树初始化
worklogShare.getZtree = function(){
	var url = getRootPath()+"/po/worklog/queryForShareTree.action?time=" + new Date();
	$.ajax({
		  url: url, 
		  dataType: 'json', 
		  //async:true, 
		  success: function(data){
			  if (data.length>0) {
					worklogShareTreeToPage.zNodes = [];
					$.each(data, function(i, o) {
						if(i == 0){
							worklogShareTreeToPage.rootId = o.id;
							worklogShareTreeToPage.rootName = o.name;
						}
						worklogShareTreeToPage.zNodes[i] = {
							id : o.id,
							pId : o.parentId,
							name : o.name + "",
							createUserDept: o.createUserDept,
							subPlanType : o.subPlanType,
							checkState : false
						};
					});
					tree = $.fn.zTree.init($("#treeDemo"), worklogShareTreeToPage.setting, worklogShareTreeToPage.zNodes);
					tree.expandAll(true);
					worklogShareTreeToPage.defaultCheck("treeDemo");
					//日历初始化
					worklogShare.initFullcalendar();
			  }else{
				  loadrightmenu('/po/worklog/diaryNoShareInfo.action');
			  }
		  },
		  error : function() {
			  loadrightmenu('/po/worklog/diaryNoShareInfo.action');
		  }
	});
//	var url = getRootPath()+"/po/worklog/queryForShareTree.action";
//	$.ajaxSetup({
//	    async : false 
//	});
//	$.getJSON(url, function(data) {
//		if (data) {
//			worklogShareTreeToPage.zNodes = [];
//			$.each(data, function(i, o) {
//				if(i == 0){
//					worklogShareTreeToPage.rootId = o.id;
//					worklogShareTreeToPage.rootName = o.name;
//				}
//				worklogShareTreeToPage.zNodes[i] = {
//					id : o.id,
//					pId : o.parentId,
//					name : o.name + "",
//					createUserDept: o.createUserDept,
//					subPlanType : o.subPlanType,
//					checkState : false
//				};
//			});
//			tree = $.fn.zTree.init($("#treeDemo"), worklogShareTreeToPage.setting, worklogShareTreeToPage.zNodes);
//			tree.expandAll(true);
//			worklogShareTreeToPage.defaultCheck("treeDemo");
//		}
//	});
//	//日历初始化
//	worklogShare.initFullcalendar();
};
worklogShareTreeToPage.setting = {
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
			if(node.createUserDept!=null){
				return false
			}else{
				return true;
			}
		},
		onClick:function(event, treeId, treeNode) {
		    $("#shareUserId").val(treeNode.id);//获取树节点ID
		    $('#shareCalendar').fullCalendar( 'refetchEvents' );
		}
	},
	
};

function onCheck(id, node){
};



//默认选中节点
worklogShareTreeToPage.defaultCheck = function(treeId){
	if(worklogShareTreeToPage.rootId != null && worklogShareTreeToPage.rootName != null){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var node = treeObj.getNodeByParam("id", worklogShareTreeToPage.rootId);
		treeObj.selectNode(node, true);
		//node.checkState = true;
		$("#shareUserId").val(worklogShareTreeToPage.rootId);
		$("#"+worklogShareTreeToPage.name).val(worklogShareTreeToPage.rootName);
	}
	
	
};
/**添加没有匹配数据行*/
worklogShare.noDate = function(to){
	var i = $('#'+to).find('thead th').size();
	var trStr = "<tr><td class=\"dataTables_empty\" colspan=\""+i+"\">没有匹配的记录</td></tr>";
	$('#'+to).find('tbody').append(trStr);
};

/**初始化日历组件方法*/
worklogShare.initFullcalendar = function(){
    $('#shareCalendar').fullCalendar({
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
		timeFormat:{//设置标题头时间格式
    		month:'H:mm{-H:mm} ',
           	agendaWeek:'H:mm{ - H:mm}',
           	agendaDay:'H:mm{ - H:mm}'
    	},
		eventClick:function( calEvent, jsEvent, view ) { //事件被点击
			worklogShare.showDetail(calEvent.id); 
		},
		events: function(start,end, callback) {//装载我的日志数据
				var shareWorklogUserId = $('#shareUserId').val();
				var startDate = $.fullCalendar.formatDate(start,"yyyy-MM-dd HH:mm:ss");  
				var endDate = $.fullCalendar.formatDate(end,"yyyy-MM-dd HH:mm:ss");
				var formData = [];
				formData.push({"name": "worklogDateBegin", "value": startDate});
				formData.push({"name": "worklogDateEnd", "value": endDate});
				if(shareWorklogUserId!=""){
					formData.push({"name": "shareWorklogUserId", "value": shareWorklogUserId});
				}
				jQuery.ajax({
				type:"POST",
				url:getRootPath()+"/po/worklog/getShareWorklogList.action?time="+new Date(),
				data:formData,
				async:false,
				success:function(data){
					var datas=[];
					for(i=0;i<data.length;i++){
						var checkAllDay=false;
						var id = data[i].id;
						var title = data[i].title+"";
						start =new Date( Date.parse(data[i].worklogDate.replace(/-/g,   "/")));
						datas.push({
							allDay:true,
							id: id,
							color:'#38B0DE',
							title: title,
							start: start
						});
					}
					callback(datas);
				}
			});

			
     	}
	});
};

/**
 * 初始添加行公共方法to:目标容器id,trStr:添加内容,n:添加行数
 */
worklogShare.initTr = function(to,trStr,n){
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
worklogShare.initDetailTr = function(to,trStr,d){
	
		var lastNo = $('#'+to).find('tbody tr:last td:first span').html()||0;//取最后一行行号
		var currentNo = Number(lastNo)+1;
		$('#'+to).find('tbody').append(trStr);
		$('#'+to).find('tbody tr:last td:first span').html(currentNo);
		$('#'+to).find('tbody tr:last td:last').html(d);
	
};

/**初始化今日日程*/
worklogShare.initDiary = function(currentDate,to,tr){
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
					worklogShare.noDate(to);
				}
				for(var i=0;i<data.length;i++){
					var diary = data[i];
					worklogShare.initTr(to,tr,1);
					$('#'+to).find('tbody tr:last td[name="todayDiary-title"]').html(diary.content);
				}
			}else{
				worklogShare.noDate(to);
			}
		}
	});
};

/**初始化详细页待办任务列表*/
worklogShare.workTaskDetailTable = null;//分页对象
worklogShare.initDetailWorkTask = function(currentDate,to){
	//显示列信息
	worklogShare.workTaskTableColumns = [
	                                        //不需要排序的列直接用mData function方式
	                                        {mData: "taskName"},
	                                        {mData: "taskProc"},
	                                        {mData: "sponsor"},
	                                        {mData: "actStartTime"}
	                                        ];
		
	worklogShare.aoColumnDefs = [{bSortable: false, aTargets: [0,1,2,3]}];
	
	
	//组装后台参数
	worklogShare.workTaskTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(worklogShare.workTaskDetailTable, aoData);
		var userId = $('#shareUserId').val();
		aoData.push({ "name": "startDate", "value": currentDate});
		aoData.push({ "name": "directorId", "value": userId});
	};
	
	//table对象为null时初始化
	if (worklogShare.workTaskDetailTable == null) {
		worklogShare.workTaskDetailTable = $('#'+to).dataTable( {
			sAjaxSource: getRootPath()+"/po/worklog/queryWorkTask.action",
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: worklogShare.workTaskTableColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				worklogShare.workTaskTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
			aoColumnDefs: worklogShare.aoColumnDefs
		} );
		//setColumnVis(worklogCalendar.workTaskDetailTable, 0);
		
	} else {
		//table不为null时重绘表格
		worklogShare.workTaskDetailTable.fnDraw();
	}
	
};

/**初始化批注列表*/
//领导批注修改 李洪宇 2014-08-19 开始
//worklogShare.initAnno = function(worklogId){
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
worklogShare.initAnno = function(worklogId){
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
 * 领导批注初始化方法(只读)
 */
worklogShare.initAnnoOnly = function(worklogId){
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
/**
 * 领导批注回复方法
 */
worklogShare.commentReply = function(e) {
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
worklogShare.commentSend = function(e) {
	//校验是否重复提交
	if (worklogShare.subState)return;
	worklogShare.subState = true;
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
		worklogShare.subState =false;
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
					worklogShare.subState = false;//更新重复提交状态标识
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								$('#comment').empty();
								worklogShare.initAnno(businessId);
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
			worklogShare.subState = false;//更新重复提交状态标识
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
worklogShare.saveLeaderIdeaForm = function(){
	var worklogId = $('#detailId').val();
	if(worklogId=='')return;
	//校验是否重复提交
	if (worklogShare.subState)return;
	worklogShare.subState = true;
	if(!DeleteCascade.syncCheckExist("workLogAnno",worklogId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		worklogShare.subState = false;//更新重复提交状态标识
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
				worklogShare.subState = false;//更新重复提交状态标识
//				getToken();//更新token
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$('#leaderIdeaContent').val('');
					$('#comment').empty();
					worklogShare.initAnno(worklogId);
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
				worklogShare.subState = false;//更新重复提交状态标识
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
			}
		});
	} else {
		worklogShare.subState = false;//更新重复提交状态标识
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
worklogShare.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};
//领导批注修改 李洪宇 2014-08-19 结束
/**清除详情页数据*/
worklogShare.clearDetail = function(){
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
worklogShare.showDetail = function(id){
	hideErrorMessage();
	$("#leaderIdeaContent").val("");
	d = {"id": id};
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/worklog/get.action",
		data : d,
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除详情页数据
				worklogShare.clearDetail();
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
				//worklogShare.initDiary(data.worklogDate,'detailTodayDiary',worklogShare.autoTrDetailTodayDiaryStr);
				//填充待办任务
				//worklogShare.initWorkTask(data.worklogDate,'detailTask',worklogShare.autoTrDetailWorkTaskStr);
				//worklogShare.initDetailWorkTask(data.worklogDate,'detailTask');
				//批注修改 李洪宇 2014-08-19 开始
				//填充批注
//				worklogShare.getIsLeader(data.createUser);
//				var reV=$('#isLeaderTemp').val();
//				if(reV=='1'){
//					worklogShare.initAnno(data.id);
//					$("#leaderIdeaForm").css('display', 'block'); //显示  
//				}else{
					worklogShare.initAnnoOnly(data.id);
					$("#leaderIdeaForm").css('display','none'); //隐藏  
//				}
				//批注修改 李洪宇 2014-08-19 结束
				//填充今日日志
				var todayLogs = data.todayLogs;
				if(todayLogs.length==0){
					worklogShare.noDate('detailTodayLog');
				}
				if(todayLogs && todayLogs.length > 0) {
					for(var i=0;i<todayLogs.length;i++){
						var todayLog = todayLogs[i];
						worklogShare.initDetailTr('detailTodayLog',worklogShare.autoTrDetailStr,todayLog.content);
					}
				}
				
				//填充明日提醒
				var tomorrowReminds = data.tomorrowReminds;
				if(tomorrowReminds.length==0){
					worklogShare.noDate('detailTomorrowRemind');
				}
				if(tomorrowReminds && tomorrowReminds.length > 0) {
					for(var i=0;i<tomorrowReminds.length;i++){
						var tomorrowRemind = tomorrowReminds[i];
						worklogShare.initDetailTr('detailTomorrowRemind',worklogShare.autoTrDetailStr,tomorrowRemind.content);
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

/**日志日历形式查询*/
worklogShare.searchFullCalendar = function(){
	var searchtime=$('#searchtime').val();
	if(searchtime==null||searchtime=='')return false;
	var searchdate = new Date(Date.parse(searchtime.replace(/-/g,"/")));
	$('#shareCalendar').fullCalendar( 'gotoDate', searchdate );
	$('#shareCalendar').fullCalendar( 'select', searchdate, searchdate, true);
};

/**初始化左侧的树的div宽高*/
worklogShare.initLeftTreeDiv = function(){
	var content = $("#content").height();
	var headerHeight_1 = $('#header_1').height() || 0;
	var headerHeight_2 = $("#header_2").height() || 0;
	
	if($("#treeDemo")[0]){
	    $(".tree-right").css("margin-left","215px");
	//定义高度
		$("#LeftHeight").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight").height()
	
	  $("#scrollable").scroll(function() {
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight").addClass("fixedNav");
	        $("#LeftHeight").height(lh + 113)
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
	        $("#LeftHeight").height(lh + a)
	        //$("#LeftHeight").height()
	        $("#LeftHeight").removeClass("fixedNav");
	    } 
	
	  });
	}
};
/**判断当前登录用户与被查看日志所属人员是否为领导关系*/
worklogShare.getIsLeader=function(t){
	$.ajax({
		type : "GET",
		cache: false,
		async:false,
		url : getRootPath()+"/po/worklog/getIsLeader.action?time=" + new Date(),
		data : {"id": $('#shareUserId').val(),"createUserId":t},
		dataType : "json",
		success : function(data) {
			$('#isLeaderTemp').val(data);
		}
	});
};
$('#today').on('click', function() {//清除快速查询条件
	$('#searchtime').val("");
    $('.calendar').fullCalendar('today')
});
/**
 * 初始化方法
 */
jQuery(function($){
	worklogShare.initLeftTreeDiv();
	//绑定”快速查找“
	$('#search').click(function(){worklogShare.searchFullCalendar();});
	//初始化下属人员树
	worklogShare.getZtree();
	
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	/*$('#dayview').on('click', function() {//agendaDay 一页显示一天, 显示详细的24小时安排
      $('.calendar').fullCalendar('changeView', 'agendaDay');
    });*/

    $('#weekview').on('click', function() {//agendaWeek 一页显示一周, 显示详细的24小时安排(也就是议事日程)
      $('.calendar').fullCalendar('changeView', 'basicWeek');
      worklogShare.searchFullCalendar();
    });

    $('#monthview').on('click', function() {//month 一页显示一月, 日历样式
      $('.calendar').fullCalendar('changeView', 'month');
      worklogShare.searchFullCalendar();
    });
    
    $('#today').on('click', function() {//显示今天
        $('.calendar').fullCalendar('today');
    });
    
   /* $('#allShare').on('click', function() {//显示所有
    	$('#shareUserId').val('');
    	tree.cancelSelectedNode();
    	$('.calendar').fullCalendar('refetchEvents');
    });*/
    
  //绑定详情页展开收起
    $('a[name="detailShowAndHide"]').on('click',function(){
    	$('a[name="detailShowAndHide"]').toggle();
    	$(this).parent().parent().next().toggle();
    });
    
    $("#toList").click(function(){loadrightmenu('/po/worklog/worklogShareListSkip.action?time='+new Date());});
    //绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){worklogShare.saveLeaderIdeaForm();});
	//绑定批注"回复"
	$('#comment').on('click','a[name="reply"]',function(){
		worklogShare.commentReply(this);
	});
	//绑定批注"发送"
	$('#comment').on('click','a[name="send"]',function(){
		worklogShare.commentSend(this);
	});
	//绑定批注内容清除
	$("#clearleaderIdea").click(function(){
		worklogShare.clearleaderIdea();
	});
	//隐藏错误信息
	hideErrorMessage();
});
//@ sourceURL=worklogShare.js