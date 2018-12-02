infoApproval = {};
infoApproval.oTable = null;
infoApproval.pageRows = null;

infoApproval.oTableAoColumns = [
    {"mData":"infoTitile"},
    {"mData":"columnName"},
	{"mData":"sendName"},
	{"mData":"sendDepName"},
	{"mData":"sendTime"},
	{"mData":"processStatusMsg"},
	{"mData":function(source) {
		var opt = {};
		opt.workId = source.workId;
		opt.processStatus = source.processStatus;
		opt.flowStatus = source.flowStatus;
		opt.workflowId= source.workflowId;
		opt.entrance = $("#entrance").val();	
		opt.entranceType = $("#entranceType").val();
		opt.action="/im/info/infoView.action";
		//返回流程公共按钮
		return getWorkflowListButton(opt) + oTableSetButtons(source.id);
		}
	}
];

/**组装后台参数*/
infoApproval.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(infoApproval.oTable, aoData);
	//组装查询条件
	var columnId = $("#infoApprovalForm #columnId").val();//栏目
	if(columnId.length > 0 && columnId != -1){
		aoData.push({ "name": "columnId", "value": columnId});
	}
	
	var infoTitile = $("#infoApprovalForm #infoTitile").val();//标题
	if(infoTitile.length > 0){
		aoData.push({ "name": "infoTitile", "value": infoTitile});
	}
	
	var processStatus = $("#infoApprovalForm #processStatus").val();//流程状态
	if(processStatus.length > 0){
		aoData.push({ "name": "processStatus", "value": processStatus});
	}
	
	var sendId = $("#infoApprovalForm #sendId-sendId").val();//发布人
	if(sendId.length > 0){
		aoData.push({ "name": "sendId", "value": sendId});
	}
	
	var sendDepid = $("#infoApprovalForm #sendDepid-sendDepid").val();//发布部门
	if(sendDepid.length > 0){
		aoData.push({ "name": "sendDepid", "value": sendDepid});
	}
	
	var sendTimeBegin = $("#infoApprovalForm #sendTimeBegin").val();//发布时间开始
	if(sendTimeBegin.length > 0){
		aoData.push({ "name": "sendTimeBegin", "value": sendTimeBegin});
	}
	
	var sendTimeEnd = $("#infoApprovalForm #sendTimeEnd").val();//发布时间结束
	if(sendTimeEnd.length > 0){
		aoData.push({ "name": "sendTimeEnd", "value": sendTimeEnd});
	}
	
	//多个流程对应一个表单
	var flowId = $("#flowId").val();
	if(flowId.length > 0){
		aoData.push({ "name": "flowId", "value": flowId});
	}
};

infoApproval.infoApprovalList = function () {
	if (infoApproval.oTable == null) {
		infoApproval.oTable = $('#infoApprovalTable').dataTable( {
			"iDisplayLength": infoApproval.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/im/info/manageInfoApprovalList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": infoApproval.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				infoApproval.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,5,6]}]
		} );
	} else {
		infoApproval.oTable.fnDraw();
	}
};

/**临时添加的详细页跳转方法*/
infoApproval.infoDetail = function(infoId){
	// 删除验证
	$.ajax({
		type : "POST",
		url : getRootPath()+"/im/info/infoCount.action",
		data : {"id": infoId,
			t:(new Date()).getTime()},
		dataType : "json",
		success : function(data) {
			if (!data) {
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_012")
				});
			} else {
//				loadrightmenu("/im/info/infoDetail.action?id="+infoId+"&type=1&time=" + new Date(),"信息详细");
				window.open(encodeURI(getRootPath() + "/im/info/infoDetail.action?id="+infoId+"&type=5&menuFlag=2&time=" + new Date()));
			}
		}
	});
};

infoApproval.queryReset = function(){
	$('#infoApprovalForm')[0].reset();
	//发布人人员选择树清空
	selectControl.clearValue("sendId-sendId");
	//发布部门选择树清空
	selectControl.clearValue("sendDepid-sendDepid");
	//栏目清空
	clearTreeSelectControl("columnId");
};

infoApproval.initColumn = function(){
	var initValue = [];
	$("#column").treeSelectControl({
		controlId:"columnId",   //控件ID
		controlName:"columnId", //控件名称
		multiMode:false,   //必须
		containerId:"column",  //必须，外层容器ID
		url:getRootPath()+"/im/column/queryColumnTree.action", //必须
		//callbackFunction:infoDeliver.columnCallback,  //可选值变化回调函数
		mappings:{id:"id",name:"columnName",parentId:"columnParentId",title:"columnName",nodeType:"nodeType"},  //可选
		initValue:initValue  //可选
	});
};

/**
 * 初始化方法
 */
jQuery(function($){
	//流程初始化函数
	initWorkFlowStatus("infoApprovalForm");
	//初始化日期控件
	$(".datepicker-input").datepicker();
	selectControl.init("controlTree1", "sendId-sendId", true, true, null, null);//人员单选
	selectControl.init("controlTree2", "sendDepid-sendDepid", true, false, 0);//部门选择
	//计算分页显示条数
	infoApproval.pageRows = TabNub>0 ? TabNub : 10;
	
	//infoApproval.pageRows = 20;
	infoApproval.initColumn();
	infoApproval.infoApprovalList();
	$("#queryInfos").click(infoApproval.infoApprovalList);
	$("#queryReset").click(infoApproval.queryReset);
	/*$('#approvalInfoTable').on('click','a[name="toApprovalInfo"]',function(){
		loadrightmenu("/im/info/toApprovalInfo.action?id=12&time=" + new Date(),true);
	});*/
});