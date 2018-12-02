var taskSta = {};
taskSta.directorIdVal="";
taskSta.pageRows = null;
//重复提交标识
taskSta.subState = false;
//人员选择树--负责人
selectControl.init("showDirectorTree","directorIdName-directorId", false, true);
//清空表单
taskSta.clearForm = function () {
	$('#taskTotal')[0].reset();
	selectControl.clearValue("directorIdName-directorId");
	hideErrorMessage();
};
//分页处理 start
//分页对象
taskSta.oTable = null;
//显示列信息
taskSta.oTableAoColumns = [
	{mData: "director", mRender : function(mData, type, full){
		taskSta.directorIdVal=full.directorId;
			return mData;
		}
	},
	{mData: "notReceiveTask",mRender : function(mData){
		return taskSta.getTaskLink(taskSta.directorIdVal,"0","0",mData);
		}
	},
	{mData: "receiveTask",mRender : function(mData){
		return taskSta.getTaskLink(taskSta.directorIdVal,"1","0",mData);
	}},
	{mData: "remindersTask",mRender : function(mData, type, full){
		if(mData>0){
			var temp="'"+full.taskIds+"'";
			return taskSta.getTaskLink(taskSta.directorIdVal,temp,"1",mData);
		}else{
			return 0;
		}
	}},
	{mData: "extensionTask",mRender : function(mData){
		return taskSta.getTaskLink(taskSta.directorIdVal,"2","0",mData);
	}},
	{mData: "extendedTask",mRender : function(mData){
		return taskSta.getTaskLink(taskSta.directorIdVal,"6","0",mData);
	}},
	{mData: "finishedTask",mRender : function(mData, type, full){
		var t="'"+full.finTaskIds+"'";
		if(mData>0){
			return '<a href="#" onclick="taskSta.getWorkTaskFi('+taskSta.directorIdVal+','+t+',2)">'+mData+'</a>';
		}else{
			return mData;
		}
	}}
];

//组装后台参数
taskSta.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(taskSta.oTable, aoData);
	//组装查询条件
	$.each($("#taskTotal").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};
//分页查询 
taskSta.infoList = function () {
	$('#insideIn-list').fadeIn();
	$('#startTimeTemp').val($('#startTime').val());
	$('#endTimeTemp').val($('#endTime').val());
	if (taskSta.oTable == null) {
		taskSta.oTable = $('#queryTaskTotal').dataTable( {
			"iDisplayLength": taskSta.pageRows,//每页显示多少条记录 
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfTask.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskSta.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				taskSta.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6]}]
		} );
	} else {
		//table不为null时重绘表格
		taskSta.oTable.fnDraw();
	}
};
//分页处理 end
//查询上级任务名称
taskSta.getParentName=function(t){
	 if(t==null){
		 t="无";
	 }
	 return t;
};
//链接--待接收、进行中、延期、超期、已完成任务、催办
taskSta.getTaskLink=function(director,t,v,n){
	var temp="0";
	if(n>0){
		temp='<a href="#" onclick="taskSta.getWorkTask('+director+','+t+','+v+')">'+n+'</a>';
	}else{
		temp=n;
	}
	return temp;
};
//查询任务
taskSta.backTable = null;
taskSta.getWorkTask=function(dire,t,v){
	$('#taskArrangement').modal('show');
	taskSta.backFnServerParams = function(aoData){
		 getTableParameters(taskSta.backTable, aoData);
		 aoData.push({ "name": "directorId", "value": dire});
		 if(v==1){
			 aoData.push({ "name": "taskIds", "value": t});
		 }else{
			 aoData.push({ "name": "status", "value": t});	
		 }
		 aoData.push({ "name": "startTime", "value":$('#startTimeTemp').val()});//添加开始时间
		 aoData.push({ "name": "endTime", "value":$('#endTimeTemp').val()});	//添加结束时间
	};
	//显示列信息
	taskSta.backColumns = [
       	{mData: "taskName",mRender : function(mData, type, full){
//       		var mDataTemp=taskSta.taskNameLength(mData);
//       		return '<a style=\'cursor: hand;cursor: pointer;\'  title='+mData+'>'+mDataTemp+'</a>';
       		return mData;
		}},
    	{mData: "taskProc",mRender : function(mData) {
			return "<div class=\"progress progress-striped active\"><div class=\"progress-bar\" style=\"width:"+mData+"%\"></div><span class=\"progress-text\">"+mData+"%</span></div>";
		}},
    	{mData: "parentTaskName",mRender : function(mData){
    		if(mData!=null){
//    			var mDataTemp=taskSta.taskNameLength(mData);
//           		return '<a  title='+mData+'>'+mDataTemp+'</a>';
    			return mData;
    		}else{
    			return '无';
    		}
		}},
    	{mData: "sponsor"},
    	{mData: "director"},
    	{mData: "startTime"},
    	{mData: "endTime"}
	 ];
	if (taskSta.backTable == null) {
		taskSta.backTable = $('#workTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/getWorkTaskList.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskSta.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				taskSta.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6]}]
		} );
	} else {
		taskSta.backTable.fnDraw();
	}
};
//查询任务--完成
taskSta.backTableFi = null;
taskSta.getWorkTaskFi=function(dire,t,v){
	$('#taskArrangementFi').modal('show');
	taskSta.backFnServerParamsFi = function(aoData){
		 getTableParameters(taskSta.backTableFi, aoData);
		 aoData.push({ "name": "directorId", "value": dire});
		 aoData.push({ "name": "finTaskIds", "value": t});
	};
	//显示列信息
	taskSta.backColumnsFi = [
       	{mData: "taskName",mRender : function(mData, type, full){
//       		var mDataTemp=taskSta.taskNameLength(mData);
//       		return '<a style=\'cursor: hand;cursor: pointer;\' title='+mData+' >'+mDataTemp+'</a>';
       		return mData;
		}},
    	{mData: "taskProc",mRender : function(mData) {
			return "<div class=\"progress progress-striped active\"><div class=\"progress-bar\" style=\"width:"+mData+"%\"></div><span class=\"progress-text\">"+mData+"%</span></div>";
		}},
    	{mData: "parentTaskName",mRender : function(mData){
    		if(mData!=null){
//    			var mDataTemp=taskSta.taskNameLength(mData);
//           		return '<a  title='+mData+'>'+mDataTemp+'</a>';
    			return mData;
    		}else{
    			return '无';
    		}
		}},
    	{mData: "sponsor"},
    	{mData: "director"},
    	{mData: "startTime"},
    	{mData: "endTime"}
	 ];
	if (taskSta.backTableFi == null) {
		taskSta.backTableFi = $('#workTaskTableFi').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/taskfinish/manageListOfFinish.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskSta.backColumnsFi,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				taskSta.backFnServerParamsFi(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6]}]
		} );
	} else {
		taskSta.backTableFi.fnDraw();
	}
};
//任务名称显示长度
taskSta.taskNameLength=function(tVal){
	var mDataTemp="";
	if(tVal !=null && tVal !=""){
		if(tVal.length>=8){
	    	mDataTemp=tVal.substr(0, 6)+"...";
	    }else{
	    	mDataTemp=tVal;
	    }
	}
    return mDataTemp;
};
//初始化 
jQuery(function($)
{
	//计算分页显示条数
	taskSta.pageRows = TabNub>0 ? TabNub : 10;
	//查询
	$("#queryTask").click(taskSta.infoList);
	//重置
	$("#queryReset").click(taskSta.clearForm);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//进入页面后默认查询所有信息
	taskSta.infoList();
});
