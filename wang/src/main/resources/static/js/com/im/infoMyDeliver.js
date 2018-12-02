infoMyDeliver = {};

/**分页变量*/
infoMyDeliver.pageRows = null;
/**我的发布分页对象*/
infoMyDeliver.oTable = null;

/**我的发布信息显示列信息*/
infoMyDeliver.oTableAoColumns = [
    {mData: "infoTitile"},//信息标题
    {mData: "columnName"},//栏目
	{mData: "flowStatusMsg", "mRender" : function(mData, type, full) {
		return full.flowStatusMsg;
	}},//流程状态	
	{mData: "sendTime"},//发布时间
	{mData: "isFailure",mRender:function(mData,type,full){
		return mData == 1 ? "否" : "是";
	}},//有效状态
	//设置权限按钮
	{mData:null,"mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.flowStatus = full.flowStatus;
		opt.processStatus = full.processStatus;
		opt.workflowId = full.workflowId;
		opt.showDeleteBtn = true;//是否显示删除按钮
		opt.entrance = "business";
		opt.entranceType = "myBusiness";
		opt.deleteFun = "infoMyDeliver.deleteInfo(\'" + eval(full.id) + "\')";
		opt.action="/im/info/infoView.action";
		return getWorkflowListButton(opt) + oTableSetButtons(mData,type,full);
	}}
 ];

/**我的发布组装后台参数*/
infoMyDeliver.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(infoMyDeliver.oTable, aoData);
	//组装查询条件
	aoData.push({ "name": "sendId", "value": 0});//sendId为0是后台查询当前用户的信息
	var columnId = $("#myInfoForm #columnId").val();//栏目
	if(columnId.length > 0 && columnId != -1){
		aoData.push({ "name": "columnId", "value": columnId});
	}
	
	var infoTitile = $("#myInfoForm #infoTitile").val();//标题
	if(infoTitile.length > 0){
		aoData.push({ "name": "infoTitile", "value": infoTitile});
	}
	
	var flowStatusForQuery = $("#myInfoForm #flowStatusForQuery").val();//流程状态
	if(flowStatusForQuery.length > 0){
		aoData.push({ "name": "flowStatusForQuery", "value": flowStatusForQuery});
	}
	
	var sendTimeBegin = $("#myInfoForm #sendTimeBegin").val();//发布时间开始
	if(sendTimeBegin.length > 0){
		aoData.push({ "name": "sendTimeBegin", "value": sendTimeBegin});
	}
	
	var sendTimeEnd = $("#myInfoForm #sendTimeEnd").val();//发布时间结束
	if(sendTimeEnd.length > 0){
		aoData.push({ "name": "sendTimeEnd", "value": sendTimeEnd});
	}
	if ($("#myInfoForm #isFailure_t").prop('checked')) {//是否有效-是
		aoData.push({ "name": "isFailure_t", "value": $("#myInfoForm #isFailure_t").val()});
	}
	if ($("#myInfoForm #isFailure_f").prop('checked')) {//是否有效-否
		aoData.push({ "name": "isFailure_f", "value": $("#myInfoForm #isFailure_f").val()});
	}
};

//我的发布分页查询方法
infoMyDeliver.initList = function () {
	if (infoMyDeliver.oTable == null) {
		infoMyDeliver.oTable = $('#myInfoTable').dataTable( {
			"iDisplayLength": infoMyDeliver.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/im/info/manageMyInfoList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": infoMyDeliver.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				infoMyDeliver.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,4,5]}]
		} );
	} else {
		//table不为null时重绘表格
		infoMyDeliver.oTable.fnDraw();
	}
};

//清空编制查询区域
infoMyDeliver.queryFormReset = function(){
	$('#myInfoForm')[0].reset();
	//栏目清空
	clearTreeSelectControl("columnId");
};

/**
 * 删除信息
 */
infoMyDeliver.deleteInfo = function (id) {
	var ids = String(id);
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			infoMyDeliver.deleteCallBack(ids)
		}
	});
};

/**
 * 删除信息回调方法
 */
infoMyDeliver.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/im/info/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback:infoMyDeliver.initList()
				});
			}else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

/**阅读情况分页对象*/
infoMyDeliver.readingStatusTable = null;
/**查看阅读情况*/
infoMyDeliver.initReadList = function(id){
	//显示列信息
	infoMyDeliver.readingStatusTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "readUserName"},
		{mData: "readTime"}
	 ];
	
	//组装后台参数
	infoMyDeliver.readingStatusTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(infoMyDeliver.readingStatusTable, aoData);
		//组装查询条件
		aoData.push({ "name": "infoId", "value": id});
	};
	
	//table对象为null时初始化
	if (infoMyDeliver.readingStatusTable == null) {
		infoMyDeliver.readingStatusTable = $('#readingStatusTable').dataTable( {
			sAjaxSource: getRootPath()+"/im/infoRead/manageList.action?time=" + new Date(),
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: infoMyDeliver.readingStatusTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				infoMyDeliver.readingStatusTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1]}]
		} );
		
	} else {
		//table不为null时重绘表格
		infoMyDeliver.readingStatusTable.fnDraw();
		//pageChange(user.oTable);
	}
	
	$('#Reading').modal('show');
};

///**临时添加的详细页跳转方法*/
//infoMyDeliver.infoDetail = function(infoId){
//	loadrightmenu("/im/info/infoDetail.action?id="+infoId+"&time=" + new Date(),true);
//};
/**临时添加的详细页跳转方法*/
infoMyDeliver.infoDetail = function(infoId,flowStatus){
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
				if(flowStatus=="1"||flowStatus=='0'){
					window.open(encodeURI(getRootPath() + "/im/info/infoDetail.action?id="+infoId+"&type=5&menuFlag=3&time=" + new Date()));
				}else{
					window.open(encodeURI(getRootPath() + "/im/info/infoDetail.action?id="+infoId+"&type=1&menuFlag=3&time=" + new Date()));
				}
			}
		}
	});
};

infoMyDeliver.initColumn = function(){
	var initValue = [];
	$("#column").treeSelectControl({
		controlId:"columnId",   //必须
		controlName:"columnId", //必须
		multiMode:false,   //必须
		containerId:"column",  //必须，外层容器ID
		url:getRootPath()+"/im/column/queryColumnTreeByUser.action", //必须
		//callbackFunction:infoDeliver.columnCallback,  //可选值变化回调函数
		mappings:{id:"id",name:"columnName",parentId:"columnParentId",title:"columnName",nodeType:"nodeType"},  //可选
		initValue:initValue  //可选
	});
};
/*infoDeliver.columnCallback = function(selectedData){
	 //if(selectedData){
		alert(selectedData); 
	 //}
}*/
/**
 * 初始化方法
 */
jQuery(function($){
	infoMyDeliver.pageRows = TabNub > 0 ? TabNub : 10;
	//我的发布查询按钮绑定事件
	$("#myInfoQuery").click(infoMyDeliver.initList);
	//我的发布重置按钮绑定事件
	$("#myInfoReset").click(infoMyDeliver.queryFormReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化栏目选择树
	infoMyDeliver.initColumn();
	//初始化列表
	infoMyDeliver.initList();
});