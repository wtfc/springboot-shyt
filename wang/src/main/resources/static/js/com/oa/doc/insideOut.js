var insideOut = {};
insideOut.pageRows = null;
//重复提交标识
insideOut.subState = false;

//分页处理 start
//分页对象
insideOut.oTable = null;
//显示列信息
insideOut.oTableAoColumns = [
	{ "mData": "title"},
	{ "mData": "docType", "mRender" : function(mData) {
		return mData == 0 ? "发文" : "收文";
	}},
	{ "mData": "receiveUserName", "mRender" : function(mData) {
//		if(mData.length>30){
//			return mData.substring(0,30)+"...";
//		}else{
//			return mData; 
//		}
		return "<a class=\"dark email-pucker\"href=\"#receiveUserName-list\" role=\"button\" data-toggle=\"modal\" id=\"email-aid\">"+
 		   "<i class=\"fa fa-users blue-dark\" data-toggle=\"tooltip\" data-placement=\"top\"" +
 		   "title=\"\" data-container=\"body\" data-original-title=\"点击查看接收人\" onclick=\"insideOut.tooltip('"+mData+"')\"></i></a>";
	}},
	{ "mData": "sendDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
insideOut.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(insideOut.oTable, aoData);
	//组装查询条件
	$.each($("#insideOutListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
insideOut.insideOutList = function () {
	$('#insideOut-list').fadeIn();
	if (insideOut.oTable == null) {
		insideOut.oTable = $('#insideOutTable').dataTable( {
			"iDisplayLength": insideOut.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/insideOut/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": insideOut.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				insideOut.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [2,4]}]
		} );
	} else {
		insideOut.oTable.fnDraw();
	}
};
//分页处理 end

insideOut.queryReset = function(){
	$('#insideOutListForm')[0].reset();
	selectControl.clearValue("receiveUserId-receiveUserId");
	//insideOut.insideOutList();
};

//分发公文
insideOut.showInsideOutAddDiv = function (id){
	$(document).showInsideOutAddDiv_(insideOut.insideOutList,id,2);
};

//文件跟踪分页对象
insideOut.replyTable = null;
//显示回复信息
insideOut.showInsideOutReplyDiv = function(insideOutId){
	$('#insideOut-reply').modal('show');
	insideOut.replyFnServerParams = function(aoData){
		getTableParameters(insideOut.replyTable, aoData);
		aoData.push({ "name": "insideOutId", "value": insideOutId});	
	};
	
	//显示回复列信息
	insideOut.replyColumns = [
		{ "mData": "reveiveUserName" },
		{ "mData": "reveiveOperate", "mRender" : function(mData) {
			if(mData=='1'){
				return '<font color="red">收回</font>';
			}else if(mData=='2'){
				return '<font color="red">拒收</font>';
			}else if(mData=='3'){
				return '<font color="green">已读</font>';
			}else{
				return "未读";
			}
		}},
		{ "mData": "replayDate"},
		{ "mData": "replayContent", "mRender" : function(mData, type, full) {
			var val = mData;
			if(mData==null){
				val = '';
			}
			if(full.reveiveOperate=='1'){
				return val+'<font color="red">【分发人主动收回】</font>';
			}else if(full.reveiveOperate=='2'){
				return val+'<font color="red">【已被接收人拒收】</font>';
			}else{
				return full.replayContent;
			}
		}}
	 ];
	if (insideOut.replyTable == null) {
		insideOut.replyTable = $('#insideOutReplyTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/insideIn/queryReplayList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": insideOut.replyColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				insideOut.replyFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,3]}]
		} );
	} else {
		insideOut.replyTable.fnDraw();
	}
};

//收回分页对象
insideOut.backTable = null;
//显示收回信息
insideOut.showInsideOutBackDiv = function(insideOutId){
	$('#insideOut-back').modal('show');
	$('#insideOutBackForm')[0].reset();
	insideOut.backFnServerParams = function(aoData){
		 getTableParameters(insideOut.backTable, aoData);
		 aoData.push({ "name": "insideOutId", "value": insideOutId});	
		 aoData.push({ "name": "reveiveOperate", "value": 0});
	};
	
	//显示收回列信息
	insideOut.backColumns = [
        {mData: function(source) {
    			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
    	}},
		{ "mData": "reveiveUserName" },
		{ "mData": "reveiveUserDeptName"}
	 ];
	if (insideOut.backTable == null) {
		insideOut.backTable = $('#insideOutBackTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/insideIn/queryBackList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": insideOut.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				insideOut.backFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		insideOut.backTable.fnDraw();
	}
};

//执行收回操作
insideOut.updateInsideOutBack = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		msgBox.info({
			content: $.i18n.prop("JC_SYS_029","要收回的发文")
		});
		return;
	}
	insideOut.updateReveiveOperates(ids);
	//$('#insideOut-back').modal('hide');
};
//收回发文回调方法
insideOut.updateReveiveOperates = function(ids) {
	$("#dataLoad").show();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/insideIn/updateReveiveOperates.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				$('#insideOut-back').modal('hide');
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				insideOut.insideOutList();
			} else {
				msgBox.tip({
					content: data.errorMessage
				});
			}
			$("#dataLoad").hide();
		},
		error : function() {
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_055")
			});
			$("#dataLoad").hide();
		}
	});
	
};

//查看公文
insideOut.showDocView = function(docId,docType){
	$("#dataLoad").show();
	var url;
	if(docType==0){
		url = getRootPath()+"/doc/send/getSendByWorkflowInstance.action";
	}else{
		url = getRootPath()+"/doc/receive/getReceiveByWorkflowInstance.action";
	}
	var params = {
		time: new Date(),
		id: docId
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
        	$("#dataLoad").hide();
        	if(data){
        		openformNoPerson(data.workId,data.piId,"business","view");
        	//	openform(data.workId,data.piId,"business","view");
        		//接收公文查看修改已读
        		//window.open(getRootPath()+"/workFlow/form/openform.action?entrance=business&scanType=view&workId="+data.workId);
        		//loadrightmenu("/workFlow/form/openform.action?entrance="+entrance+"&scanType="+type+"&workId="+workid);
        	}else{
    			msgBox.tip({
    				content: $.i18n.prop("JC_SYS_060")
    			});        		
        	}
        },
		error : function() {
			$("#dataLoad").hide();
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_060")
			});			
		}
    });
};

//字典项初始化
insideOut.initDic = function(){
	//dic.fillDics("insideOutListForm #docType", "doc_type");
};

/*
 * 弹出查看正文页面
 * */
var Doc = {
		id:'',
};
insideOut.viewDoc = function(id){
	Doc['id'] = id;
	window.open(getRootPath()+"/doc/send/viewDoc.action");
	
}

//公文抓取页面显示方法
insideOut.showDocViewTest = function(id){
	loadrightmenu('/doc/insideOut/manageDocFormView.action?id='+id+'&time='+new Date());
};
//列表中查看接收人
insideOut.tooltip = function(receiveUserNames){
	var usersView = "";
	var users = receiveUserNames.split(",");
	if(users.length>0){
		for(var i=0;i<users.length;i++){
			usersView+= "<span class=\"email-f-r\">"+users[i]+"</span>";
		}
		$('#consignee').html(usersView);
	}else{
		$('#consignee').html("<span class=\"email-f-r first-td-tc\">无接收人</span>");
	}
	$('#countNum').html(users.length);
};

//初始化
jQuery(function($){
	//计算分页显示条数
	insideOut.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryInsideOut").click(insideOut.insideOutList);
	$("#queryReset").click(insideOut.queryReset);
	//收回分发公文
	$("#updateInsideOutBack").click(function(){insideOut.updateInsideOutBack(0);});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//查询人员选择树
	selectControl.init("queryReceiveUserTree","receiveUserId-receiveUserId", false, true);
	insideOut.insideOutList();
	insideOut.initDic();
});