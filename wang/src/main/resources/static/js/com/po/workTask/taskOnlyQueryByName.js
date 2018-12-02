var queryOnlyTaskByName={};
var taskId=document.getElementById("taskId").value;
var userId=document.getElementById("userId").value;
var taskSponsorId=document.getElementById("taskSponsorId").value;
var taskDirectorId=document.getElementById("taskDirectorId").value;
var taskStatus=document.getElementById("taskStatus").value;
var childSize=document.getElementById("childSize").value;
var queryTaskName=document.getElementById("queryTaskName").value;
var querySponsorId=document.getElementById("querySponsorId").value;
var queryDirectorId=document.getElementById("queryDirectorId").value;
var queryStartTime=document.getElementById("queryStartTime").value;
var queryEndTime=document.getElementById("queryEndTime").value;
var queryInfoStatus=document.getElementById("queryInfoStatus").value;
var tTaskName=document.getElementById("tTaskName").value;
var isLeader=document.getElementById("isLeader").value;//领导批注权限限制
/**重复提交标识*/
queryOnlyTaskByName.subState=false;
/**获取单条信息*/
queryOnlyTaskByName.get = function (t) {
	if(t !=null && t!=""){
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/workTask/getTask.action?time=" + new Date(),
			data : {"id": t},
			dataType : "json",
			success : function(data) {
				if (data) {
					//清除验证信息
					hideErrorMessage();
					$("#workTaskForm").fill(data);
					//附件使用 start
					$("#businessId").val(data.id);
					$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
					clearDelAttachIds();//设置附件上传为逻辑删除
					$("#islogicDel").val("1");//附件使用 逻辑删除
					echoAttachList(false);
					clearTable();
					//附件使用 end
				}
			}
		});
	}
};
/**汇报详细列表*/
queryOnlyTaskByName.backTable = null;
queryOnlyTaskByName.getWorkTask=function(dire,t){
	queryOnlyTaskByName.backFnServerParams = function(aoData){
		 getTableParameters(queryOnlyTaskByName.backTable, aoData);
		 aoData.push({ "name": "taskId", "value": dire});	
		 aoData.push({ "name": "taskEventType", "value": t});	
	};
	//显示列信息
	queryOnlyTaskByName.backColumns = [
       	{mData: "rowNum"},
    	{mData: "userName"},
    	{mData: "createDate"},
    	{mData: "reportProc", mRender : function(mData, type, full){
			return mData+"%";
		}},
    	{mData: "reportContent"}
	 ];
	if (queryOnlyTaskByName.backTable == null) {
		queryOnlyTaskByName.backTable = $('#workTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTaskHistory/getTaskHistListByTaskId.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": queryOnlyTaskByName.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				queryOnlyTaskByName.backFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4]}]
		} );
	} else {
		queryOnlyTaskByName.backTable.fnDraw();
	}
};
/**任务事件查询*/
queryOnlyTaskByName.backTableForTE = null;
queryOnlyTaskByName.queryTaskEvent=function(){
	queryOnlyTaskByName.backFnServerParamsForTE = function(aoData){
		 getTableParameters(queryOnlyTaskByName.backTableForTE, aoData);
		 aoData.push({ "name": "taskId", "value": taskId});
	};
	//显示列信息
	queryOnlyTaskByName.backColumnsForTE = [
	   	{mData: "userName"},
	   	{mData: "createDate"},
	   	{mData: "taskEventType", mRender : function(mData, type, full){
	   		var taskEventName="";
	   		if(mData=='0'){
	   			taskEventName="布置任务";
	   		}else if(mData=='1'){
	   			taskEventName="修改任务";
	   		}else if(mData=='2'){
	   			taskEventName="删除任务";
	   		}else if(mData=='3'){
	   			taskEventName="接收任务";
	   		}else if(mData=='4'){
	   			taskEventName="不接收任务";
	   		}else if(mData=='5'){
	   			taskEventName="汇报任务";
	   		}else if(mData=='6'){
	   			taskEventName="催办任务";
	   		}else if(mData=='7'){
	   			taskEventName="延期审批通过";
	   		}else if(mData=='8'){
	   			taskEventName="取消任务";
	   		}else if(mData=='9'){
	   			taskEventName="完成任务";
	   		}else if(mData=='10'){
	   			taskEventName="批注任务";
	   		}else if(mData=='11'){
	   			taskEventName="任务超期";
	   		}else if(mData=='12'){
	   			taskEventName="延期申请";
	   		}else if(mData=='13'){
	   			taskEventName="延期审批未通过";
	   		}else if(mData=='14'){
	   			taskEventName="延期取消";
	   		}else if(mData=='15'){
	   			taskEventName="暂存";
	   		}
			return taskEventName;
		}},
	   	{mData: "content"},
	   	{mData: "content", mRender : function(mData, type, full){
	   		var returnVal="";
	   		if(full.taskEventType=='5'){//汇报
	   			returnVal=full.reportContent;
	   		}else{//其他
	   			returnVal=full.delayReason;
	   		}
	   		return returnVal;
	   	}}
	    ];
	if (queryOnlyTaskByName.backTableForTE == null) {
		queryOnlyTaskByName.backTableForTE = $('#taskEventTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTaskHistory/getTaskHistListByTaskId.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": queryOnlyTaskByName.backColumnsForTE,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				queryOnlyTaskByName.backFnServerParamsForTE(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4]}]
		} );
	} else {
		queryOnlyTaskByName.backTableForTE.fnDraw();
	}
};
//返回功能
queryOnlyTaskByName.returnWorkList=function(){
	var url=$('#returnURL').val();
	var strArray=unescape(url).split("?");
	$.ajax({
		url : getRootPath() +strArray[0],
		type : 'POST',
		data:strArray[1],
		dataType : "html",
		success : function(data) {
			loadrightmenu(unescape(url));
		}
	});
};
//提醒设置
var para_week='';
queryOnlyTaskByName.showRemind= function(){
	$(document).showRemind_({tableName:'1',readonly:true,remind:$('#remind').val(),callback:function(r) {para_week = r;
		$("#remind").val(r);
	}});
};
//点击关闭附件弹出层是清空内容
queryOnlyTaskByName.fileupload = function (){
	$("#businessId").val("0");
	showAttachList(false);
};
//批注按钮显示
queryOnlyTaskByName.isLeader = function (){
//	if(isLeader=='false'){
//		$('#leaderIdeaForm').hide();
//	}
};
//内容、标准样式
queryOnlyTaskByName.autoValue=function(){
    var co=$('#hiContent').val();
    co=queryOnlyTaskByName.enter2Br(co);
    co=queryOnlyTaskByName.br2Enter(co);
	$('#showContent').html(co);
	var st=$('#hiStandard').val();
	st=queryOnlyTaskByName.enter2Br(st);
	st=queryOnlyTaskByName.br2Enter(st);
	$('#showStandard').html(st);
};
//折行
queryOnlyTaskByName.enter2Br = function (str){
	return str.replace(/\n/g,'<br/>').replace(/\s/g,"&nbsp;");
};
//回车
queryOnlyTaskByName.br2Enter = function (str){
    return str.replace(/<br>/ig,"\r\n").replace(/(&nbsp;)/g," ");
};
//用于批注保存时，区分任务状态
queryOnlyTaskByName.checkAnnoSave=function(){
	annoManage.saveLeaderIdeaForm(taskStatus);
};
//用于批注回复时，区分任务状态
queryOnlyTaskByName.checkAnnoReply=function(p){
	annoManage.commentSend(p,taskStatus);
};
/**初始化*/
jQuery(function($)
{
	//获取单条信息
	queryOnlyTaskByName.get(taskId);
	//汇报详细列表
	queryOnlyTaskByName.getWorkTask(taskId,'5');
	//查询批注
//	annoManage.initAnno(taskId);
	if(isLeader=='true'){
		annoManage.initAnno(taskId);
	}else{
		annoManage.initAnnoReadOnly(taskId);
	}
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){
		queryOnlyTaskByName.checkAnnoSave();
	});
	//绑定批注"回复"
	$('#comment').on('click','a[name="reply"]',function(){
		annoManage.commentReply(this);
	});
	//绑定批注"发送"
	$('#comment').on('click','a[name="send"]',function(){
		queryOnlyTaskByName.checkAnnoReply(this);
	});
	//绑定批注内容清除
	$("#clearleaderIdea").click(function(){
		annoManage.clearleaderIdea();
	});
	//提醒按钮事件绑定
	$("#remindSet").click(function(){queryOnlyTaskByName.showRemind();});
	//附件使用
	$("#closebtn").click(queryOnlyTaskByName.fileupload);
	$("#closeModalBtn").click(queryOnlyTaskByName.fileupload);
	$("#queryattach").click(queryOnlyTaskByName.fileupload);
	//批注按钮显示
	queryOnlyTaskByName.isLeader();
	//内容、标准样式
	queryOnlyTaskByName.autoValue();
});