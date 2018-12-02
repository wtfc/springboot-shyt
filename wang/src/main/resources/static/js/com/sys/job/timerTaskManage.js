/**
 * @title GOA V2.0
 * @description 
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 都业广
 * @version  2014-05-22 13:33
 */

var timerTask = {}, pageCount=10;

//分页对象
timerTask.oTable = null;

//系统任务显示列信息
timerTask.oTableAoColumns = [
                             
	//不需要排序的列直接用mData function方式
	//checkbox框,绑定数据id
	{mData: function(source) {
				return "<input type=\"checkbox\" name=\"ids\" value=" + 
				source.id + ">";
			}
	},
	
	//任务类型
	{ "mData": "groupName", mRender : function(mData, type, full) {
		return full.groupName;
	}},
	
	//任务描述
	{ "mData": "description", mRender : function(mData, type, full) {
		return full.description;
	}},
	
	//任务开始时间
	{ "mData": "startAt", mRender : function(mData, type, full) {
		return full.startAt;
	}},
	
	//任务结束时间
	{ "mData": "endAt", mRender : function(mData, type, full) {
		return full.endAt;
	}},
	
	//周期类型
	{ "mData": "cycleType", mRender : function(mData, type, full) {
		
		//切换显示汉字
		if("1" == full.cycleType){
			return "固定间隔";
		}else if("2" == full.cycleType){
			return "周期循环";
		}else{
			return "一次性";
		}
		
		return full.cycleType;
	}},
	
	{ "mData": "state", mRender : function(mData, type, full) {
		var buttons="";
		if("1" == full.state){
			
			//状态值为1时,按钮显示暂停任务效果
			buttons+= "<a class=\"a-icon i-remove m-r-xs\" style=\"padding:0 6px;\" href=\"#\" onclick=\"timerTask.changeTimerTaskStatus('"+
					full.id + "','"+ full.state +"')\">暂停</a>";
		
		}else{
			
			//状态值为0时,按钮显示运行任务效果
			buttons+= "<a class=\"a-icon i-edit m-r-xs\" style=\"padding:0 6px;\" href=\"#\" onclick=\"timerTask.changeTimerTaskStatus('"+
					full.id + "','"+ full.state +"')\" role=\"button\" data-toggle=\"modal\">运行</a>";
		
		}
		
		//编辑按钮
		var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"timerTask.loadUpdateHtml("+
					full.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
		
		//删除按钮
		var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"timerTask.deleteTimerTask("+
					full.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
		
		//三个按钮同时显示在操作栏中
		return edit + buttons + del;
	}}
];

/** 改变系统任务的状态：
 * @param id:对应任务在数据库中的主键id
 * @param state:状态标示.1：运行状态，0：暂停状态
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTask.changeTimerTaskStatus = function (id,state) {

	//运行状态时弹出确认信息
	if("1" == state){
		//是否暂停任务？
		confirmx($.i18n.prop("JC_SYS_056"),
				function(){
					//进行后台数据提交
					timerTask.changeState(id,state);
				});
	
	//暂停状态时弹出确认信息
	}else{
		//是否运行任务？
		confirmx($.i18n.prop("JC_SYS_057"),
				function(){
					//进行后台数据提交
					timerTask.changeState(id,state);
				});
	}
};

/** 提交保存系统任务的运行状态：
 * @param id:对应任务在数据库中的主键id
 * @param state:状态标示.1：运行状态，0：暂停状态
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTask.changeState = function(id,state){
	
	//二次提交检测,为true时不提交
	if (timerTask.subState){
		return;
	}else{
		timerTask.subState = true;
	}
 	 
	var url;
	
	//更新运行状态
	if("1" == state){
		//暂停任务
		url = getRootPath()+"/sys/job/pause.action?time="+new Date();
	}else{
		//运行任务
		url = getRootPath()+"/sys/job/resume.action?time="+new Date();
	}
	jQuery.ajax({
		//提交请求的地址
		url: url,
		//提交请求的方式参数
		type: 'post',
		//前台传递的数据参数id:对应数据在数据库中的ID.state:对应数据库中的state运行状态一项
		data: {id:id, state:state},
		success: function(data, textStatus, xhr) {
			//提交通过后改变状态
			timerTask.subState = false;
			//getToken();
			//更新失败时的处理
			if(data.errorMessage!=null){
				//后台返回的错误信息
				msgBox.tip({content: data.errorMessage, type:'fail'});
			//更新成功时的处理
			}else{
				//任务状态更新成功
				msgBox.tip({content: $.i18n.prop("JC_SYS_058"), type:'success'});
				timerTask.timerTaskList();
			}
		},error:function(){
			  //提交通过后改变状态
			  timerTask.subState = false;
			  //任务状态更新失败
			  msgBox.tip({content: $.i18n.prop("JC_SYS_059"), type:'fail'});
		}
	});
};

/** 拼装datatable需要的参数
 * @param aoData:对应搜索条件数据
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTask.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(timerTask.oTable, aoData);
	
	//查询页面任务类型
	var groupName = $("#timerTaskListForm #groupNameMain").val();
	
	//查询页面任务详情
	var description = $("#timerTaskListForm #description").val();
	
	//任务类型不为空的情况加入查询条件
	if(groupName != ""){
		aoData.push({ "name": "groupName", "value": groupName});
	}
	
	//任务详情不为空的情况加入查询条件
	if(description != ""){
		aoData.push({ "name": "description", "value": $.trim(description)});
	}
};

/** 分页查询
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTask.timerTaskList = function () {
	if (timerTask.oTable == null) {
		timerTask.oTable = $('#timerTaskTable').dataTable( {
			"iDisplayLength": timerTask.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/job/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": timerTask.oTableAoColumns,//table显示列
			
			//传参
			"fnServerParams": function ( aoData ) {
				timerTask.oTableFnServerParams(aoData);
			}
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6]}
	        ]
		} );
	} else {
		timerTask.oTable.fnDraw();
	}
};


//加载添加DIV
timerTask.loadAddHtml = function (){
	if($.trim($("#timerTaskEdit").html()).length == 0){
		$("#timerTaskEdit").load(getRootPath()+"/sys/job/timerTaskEdit.action",null,function(){timerTaskEdit.createtimerTaskEdit();});
	}
	else{
		timerTaskEdit.createtimerTaskEdit();
	}
};


//加载修改DIV
timerTask.loadUpdateHtml = function (id){
	if($.trim($("#timerTaskEdit").html()).length == 0){
		$("#timerTaskEdit").load(getRootPath()+"/sys/job/timerTaskEdit.action",null,function(){timerTaskEdit.get(id);});
	}
	else{
		timerTaskEdit.get(id);
	}
};


/** 删除系统任务
 * @param id:数据与数据库中对应的id
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTask.deleteTimerTask = function (id) {
	var ids = String(id);

	//id为0时:批量删除
	if (id == 0) {
		var idsArr = [];
		
		//获得选中的数据
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		
		//将取得的id以逗号分隔
		ids = idsArr.join(",");
	}
	
	//没有选中任何数据情况的判断
	if (ids.length == 0) {
		//提示信息:请选择需要删除的数据
		msgBox.info({content: $.i18n.prop("JC_SYS_061"), type:'fail'});
		return;
	}
	
	//提交时弹出确认信息:是否删除该数据？
	confirmx($.i18n.prop("JC_SYS_034"),function(){timerTask.deleteCallBack(ids);});
};

/** 删除系统任务回调方法
 * @param ids:数据与数据库中对应的id串(格式:1,2,3)
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTask.deleteCallBack = function(ids) {
	//ajax提交
	$.ajax({
		//提交类型
		type : "POST",
		//请求地址
		url : getRootPath()+"/sys/job/deleteByIds.action?time=" + new Date(),
		//向后台传递的id参数(格式:1,2,3)
		data : {"ids": ids},
		//传递数据的格式
		dataType : "json",
		//成功后的处理
		success : function(data) {
			//删除数据大于0条时的处理
			if (data > 0) {
				//提示信息:删除成功
				msgBox.tip({content: $.i18n.prop("JC_SYS_005"), type:'success'});
				
				//重新刷新查询列表
				timerTask.timerTaskList();
			}
		}
	});
};

/** 重置查询页面查询条件的方法
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTask.queryReset = function(){
	$('#timerTaskListForm')[0].reset();
	$('#timerTaskListForm')[1].reset();
};



/** 页面初始化
 * @author 都业广
 * @version  2014-05-22 13:47
 */
jQuery(function($) 
{
	//计算分页显示条数
	timerTask.pageCount = TabNub>0 ? TabNub : 10;
	
	//绑定查询按钮
	$("#queryTimerTask").click(timerTask.timerTaskList);
	
	//绑定查询页面的重置按钮
	$("#queryReset").click(timerTask.queryReset);
	
	
	//绑定页面上方的新增按钮
	$("#timerTaskTop").click(timerTask.loadAddHtml);
	
	//绑定页面下方的新增按钮
	$("#timerTaskBottom").click(timerTask.loadAddHtml);
	
	//绑定批量删除按钮
	$("#deleteTimerTasks").click("click", 
			function(){
				timerTask.deleteTimerTask(0);
			});
	
	//初始化列表方法
	timerTask.timerTaskList();
	
});	


