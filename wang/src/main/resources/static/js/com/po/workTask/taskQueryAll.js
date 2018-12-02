var taskInfoAll = {};
var currentUserId=document.getElementById("currentUserId").value;
//用于返回按钮 start
//var queryTaskName=document.getElementById("queryTaskName").value;
//var qStartTime=document.getElementById("qStartTime").value;
//var qEndTime=document.getElementById("qEndTime").value;
//用于返回按钮 end
var querySponsorId=document.getElementById("querySponsorId").value;//用于人员选择树初始化
var querySponsor=document.getElementById("querySponsor").value;//用于人员选择树初始化
taskInfoAll.pageRows = null;
var para_week=null;//提醒设置
//重复提交标识
taskInfoAll.subState = false;
//清空表单--发起人
taskInfoAll.clearForm = function () {
	selectControl.clearValue("sponsor-sponsorId");
	$("#taskName").val("");
	$("#queryStartTime").val("");
	$("#queryEndTime").val("");
};
//清空表单--负责人
taskInfoAll.clearForm_fzr= function () {
	selectControl.clearValue("sponsor1-sponsorId");
	$("#taskName-fzr").val("");
	$("#queryStartTime-fzr").val("");
	$("#queryEndTime-fzr").val("");
};
//查询子任务 start
var pageCount=10;

//用来拼table id 的全局变量
var tableId = "_";  	
/**
 * rowDetail的展开和关闭的方法
 * 参数id:对应数据唯一的key
 */
taskInfoAll.rowDetail = function(obj,id,qType){
	//绑定onClick事件.示例是绑定到每行第一个td上面.
	 //取得选中的row  
	var nTr = $(obj).parent().parent("tr")[0];
	//判断如果rowDetail是打开状态,那么则关闭.
    if ( $($(obj).closest("table")).DataTable().fnIsOpen(nTr) ){
    	$($(obj).closest("table")).DataTable().fnClose( nTr );
    	$(obj).removeClass("fa fa-minus");
    	$(obj).addClass("fa fa-plus2");
    }else{
    	//否则,未打开rowDetail时,则追加一个新row
    	//数据加载过程中,显示"数据加载中..."message.对应资源文件中的JC_SYS_068
    	var newRow = $($(obj).closest("table")).DataTable().fnOpen( nTr, "waiting....." ,  "pd");
    	//调用获得rowDetail数据的方法
    	taskInfoAll.getDetailData(id,newRow,obj,qType);
    	$(obj).removeClass("fa fa-plus2");
    	$(obj).addClass("fa fa-minus");
    }
};
/**
 * 调用获得rowDetail数据的方法
 * 参数1:id对应每行数据的唯一标示
 * 参数2:新创建的row对象
 */
taskInfoAll.getDetailData = function(id,newRow,obj,qType){
	var urlPath="";
	if(qType=='1'){//查询历史
		urlPath+=getRootPath()+"/po/taskfinish/getChildList.action?id="+id+"&time="+new Date();
	}else{
		urlPath+=getRootPath()+"/po/workTask/getChildListNew.action?id="+id+"&time="+new Date();
	}
	//向新创建的row中追加detail数据
	//调用拼装html语句格式的方法
	$('td', newRow).html(taskInfoAll.getDetailHtml(id));
    $("#"+tableId+id).dataTable( {
		"iDisplayLength": taskInfoAll.pageCount,//每页显示多少条记录
		"sAjaxSource": urlPath,
		"fnServerData": oTableRetrieveData,//查询数据回调函数
		"aoColumns": taskInfoAll.oTableAoColumns,//table显示列
        "bPaginate":false,
        "bSort":false,
		//默认不排序列
        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}]
	} );
	tableId += "_";
};
/**
 * 拼装html的方法
 * 参数1:data对应ajax返回的数据
 */
taskInfoAll.getDetailHtml = function(priId){
	var html = "";
	//指定table的格式和css
	html += "<table id=\""+ tableId + priId +"\" class=\"table table-striped tab_height first-td-tc\">";
	html += "<thead style=\"display:none\"></thead>";
	html += "<tbody>";

	html += "</tbody>";
	// table收尾
	html += "</table>"; 
	return html;
};
//查询子任务 end
//分页处理-发起人 start
taskInfoAll.oTable = null;//分页对象-发起人
//显示列信息-发起人
taskInfoAll.oTableAoColumns = [
	//点击+
	{mData: function(source) {
		if(source.totalCount >0){
			if(source.status=='3'){
				return "<a class=\"fa fa-plus2\" onClick=\"taskInfoAll.rowDetail(this," + source.taskId+",'1')\"></a>";
			}else{
				return "<a class=\"fa fa-plus2\" onClick=\"taskInfoAll.rowDetail(this,"+ source.id+",'0')\"></a>";
			}
		}else{
			return '';
		}
	}},
	{mData: "taskName", mRender : function(mData, type, full){
		return mData;
	}},
	{mData: "taskTypeName", mRender : function(mData, type, full){
		var reVal="无";
		if(mData !=null && mData !=''){
			reVal=mData;
		}
		return reVal;
	}},
	{mData: "taskProc", mRender : function(mData, type, full){
		return "<div class=\"progress progress-striped active\"><div class=\"progress-bar\" style=\"width:"+mData+"%\"></div><span class=\"progress-text\">"+mData+"%</span></div>";
	}},
	{mData: "status", mRender : function(mData, type, full){
		var reVal="无";
		if(mData=='0'){
			reVal="未接收";
		}else if(mData=='1'){
			reVal="进行中";
		}else if(mData=='2'){
			reVal="延期";
		}else if(mData=='3'){
			reVal="完成";
		}else if(mData=='4'){
			reVal="取消";
		}else if(mData=='5'){
			reVal="暂存";
		}else if(mData=='6'){
			reVal="超期";
		}else if(mData=='7'){
			reVal="拒接收";
		}else{//8
			reVal="暂存";
		}
		return reVal;
	}},
	{mData: "parentTaskName",mRender : function(mData){
			return taskInfoAll.getParentName(mData);
		}
	},
	{mData: "sponsor"},
	{mData: "director"},
	{mData: "startTime"},
	{mData: "endTime"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
 ];
//组装后台参数-发起人
taskInfoAll.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(taskInfoAll.oTable, aoData);
	//组装查询条件
	$.each($("#zdsz-fqr").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};
//查询-发起人
taskInfoAll.infoList = function () {
	$('#insideIn-list').fadeIn();
	if (taskInfoAll.oTable == null) {
		taskInfoAll.oTable = $('#taskListTable').dataTable( {
			"iDisplayLength": taskInfoAll.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageList.action?isQueryAll=yes&&time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskInfoAll.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				taskInfoAll.oTableFnServerParams(aoData);
			},
			aaSorting:[],
			//默认不排序列
			"aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,10]}]
		} );
	} else {
		//table不为null时重绘表格
		taskInfoAll.oTable.fnDraw();
	}
};
//分页处理-发起人  end
//分页处理-负责人 start
taskInfoAll.oTable_fzr = null;//分页对象-负责人
//显示列信息-负责人
taskInfoAll.oTableAoColumns_fzr = [
	//点击+
	{mData: function(source) {
		if(source.totalCount >0){
			if(source.status=='3'){
				return "<a class=\"fa fa-plus2\" onClick=\"taskInfoAll.rowDetail(this," + source.taskId+",'1')\"></a>";
			}else{
				return "<a class=\"fa fa-plus2\" onClick=\"taskInfoAll.rowDetail(this,"+ source.id+",'0')\"></a>";
			}
		}else{
			return '';
		}
	}},
	{mData: "taskName", mRender : function(mData, type, full){
		return mData;
	}},
	{mData: "taskTypeName", mRender : function(mData, type, full){
		var reVal="无";
		if(mData !=null && mData !=''){
			reVal=mData;
		}
		return reVal;
	}},
	{mData: "taskProc", mRender : function(mData, type, full){
		return "<div class=\"progress progress-striped active\"><div class=\"progress-bar\" style=\"width:"+mData+"%\"></div><span class=\"progress-text\">"+mData+"%</span></div>";
	}},
	{mData: "status", mRender : function(mData, type, full){
		var reVal="无";
		if(mData=='0'){
			reVal="未接收";
		}else if(mData=='1'){
			reVal="进行中";
		}else if(mData=='2'){
			reVal="延期";
		}else if(mData=='3'){
			reVal="完成";
		}else if(mData=='4'){
			reVal="取消";
		}else if(mData=='5'){
			reVal="暂存";
		}else if(mData=='6'){
			reVal="超期";
		}else if(mData=='7'){
			reVal="拒接收";
		}else{//8
			reVal="暂存";
		}
		return reVal;
	}},
	{mData: "parentTaskName",mRender : function(mData){
			return taskInfoAll.getParentName(mData);
		}
	},
	{mData: "sponsor"},
	{mData: "director"},
	{mData: "startTime"},
	{mData: "endTime"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
];
//组装后台参数-负责人
taskInfoAll.oTableFnServerParams_fzr = function(aoData){
	//排序条件
	getTableParameters(taskInfoAll.oTable_fzr, aoData);
	//组装查询条件
	$.each($("#zdsz-fzr").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};
//分页查询-负责人
taskInfoAll.infoList_fzr = function () {
	$('#insideIn-list-fzr').fadeIn();
	if (taskInfoAll.oTable_fzr == null) {
		taskInfoAll.oTable_fzr = $('#taskListTable-fzr').dataTable( {
			"iDisplayLength": taskInfoAll.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageList.action?isQueryAll=yes&&time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskInfoAll.oTableAoColumns_fzr,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				taskInfoAll.oTableFnServerParams_fzr(aoData);
			},
			aaSorting:[],
			//默认不排序列
			"aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,10]}]
		} );
	} else {
		//table不为null时重绘表格
		taskInfoAll.oTable_fzr.fnDraw();
	}
};
//分页处理-负责人 end
//json 日期格式化
taskInfoAll.customDateString = function (t) {
	var jDate="\/Date("+t+")\/";
    var r;
    var d = eval("new " + jDate.replace(/\//g, ""));
    //自定义格式
    if (d.getFullYear() > 1000) {
        month = d.getMonth() + 1;
        r = d.getFullYear() + "-" + month + "-" + d.getDate();
    }
    return r;
};
//查询上级任务名称
taskInfoAll.getParentName=function(t){
	 if(t==null){
		 t="无";
	 }
	 return t;
};
//任务统计
taskInfoAll.taskTatalLink=function () {
	loadrightmenu('/po/workTask/queryStatisticsNew.action','任务统计');
};
//根据任务名称查询任务(未完成)
taskInfoAll.getWorkTaskInfo=function (id) {
	if(taskInfoAll.operate(id,'0')==1){//操作校验
		loadrightmenu('/po/workTask/getOnlyWorkTaskInfo.action?id='+id+'&time=' + new Date());
	}else{
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_OA_COMMON_014")
		});
	}
};
//根据任务名称查询任务(完成)
taskInfoAll.getWorkTaskFiniInfo=function (id) {
//	if(taskInfoAll.operate(id,'0')==1){//操作校验（预留）
		loadrightmenu('/po/taskfinish/getOnlyWorkTaskInfo.action?id='+id+'&time=' + new Date());
//	}else{
//		msgBox.info({
//			type:"fail",
//			content:$.i18n.prop("JC_OA_COMMON_014")
//		});
//	}
};
//提醒设置
taskInfoAll.showRemind= function(){
	$(document).showRemind_({tableName:'1',remind:para_week,callback:function(r) {para_week = r;
		$("#remind").val(r);
	}});
};
//tab点击返回事件
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	var dataId = $(e.target).attr("data-id");
	//发起人
	if(dataId == "planListMsg"){
		//计算分页显示条数
		taskInfoAll.pageRows = TabNub>0 ? TabNub : 10;
		//进入页面后默认查询所有信息
		taskInfoAll.infoList();
		//清空编制查询区域
		taskInfoAll.clearForm();
	//负责人
	}else if(dataId == "sendListMsg"){
		//计算分页显示条数
		taskInfoAll.pageRows = TabNub>0 ? TabNub : 10;
		//进入页面后默认查询所有信息
		taskInfoAll.infoList_fzr();
		taskInfoAll.clearForm_fzr();
	}else {
		//进入页面后默认查询所有信息
		taskInfoAll.infoList();
	}
	$('#zdsz-fqr')[0].reset();
	$('#zdsz-fzr')[0].reset();
});
//操作事件校验
taskInfoAll.operate=function(taskId,operateType){
	var reVal=0;
	$.ajax({
		type : "POST",
		url : getRootPath()+"/po/workTask/checkOperation.action?time=" + new Date(),
		data : {"taskId": taskId,"operateType":operateType},
		dataType : "json",
        async : false,
		success : function(data) {
			taskInfoAll.subState=false;
			if(data>0){
				reVal=1;
			}
		},
		error : function() {
			taskInfoAll.subState = false;
		}
	});
	return reVal;
};
//初始化 
jQuery(function($)
{
//	getToken(); token 性能优化
	//计算分页显示条数
	taskInfoAll.pageRows = TabNub>0 ? TabNub : 10;
	//查询-发起人
	$("#queryInfo").click(function(){taskInfoAll.infoList();});
	//重置-发起人
	$("#resetInfo").click(function(){taskInfoAll.clearForm();});
	//查询-负责人
	$("#queryInfo-fzr").click(function(){taskInfoAll.infoList_fzr();});
	//重置-负责人
	$("#resetInfo-fzr").click(function(){taskInfoAll.clearForm_fzr();});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//进入页面后默认查询所有信息
	taskInfoAll.infoList();
	//任务统计
	$("#taskTatalLink").click(function(){taskInfoAll.taskTatalLink();});
	//提取任务模板中任务名称
	$("#extractButtion").click(function(){taskInfoAll.getTaskName();});
	//提醒按钮事件绑定
	$("#remindSet").click(function(){taskInfoAll.showRemind();});
	//人员选择树--发起人--查询
	if(querySponsorId!=null && querySponsorId!=''){
		selectControl.init("showSponsorTree","sponsor-sponsorId", false, true, null, {id:querySponsorId,text:querySponsor});
	}else{
		selectControl.init("showSponsorTree","sponsor-sponsorId", false, true);
	}
	//人员选择树--负责人--查询
	if(querySponsorId!=null && querySponsorId!=''){
		selectControl.init("showSponsorTree-fzr","sponsor1-sponsorId", false, true, null, {id:querySponsorId,text:querySponsor});
	}else{
		selectControl.init("showSponsorTree-fzr","sponsor1-sponsorId", false, true);
	}
});