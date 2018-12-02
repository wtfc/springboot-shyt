infoAll = {};
/**分页变量*/
infoAll.pageRows = null;
infoAll.oTable = null;

infoAll.oTableAoColumns = [
    {mData: "infoTitile"},//信息标题
    {mData: "columnName"},//栏目
	{mData: "sendName"},//发布人
	{mData: "sendDepName"},//发布部门
	{mData: "sendTime"},//发布时间
	{mData: "flowStatusMsg", "mRender" : function(mData, type, full) {
		return full.flowStatusMsg;
	}},//流程状态	
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
		opt.deleteFun = "infoAll.deleteInfo(\'" + eval(full.id) + "\')";
		opt.action="/im/info/infoView.action";
		return  getWorkflowListButton(opt) + oTableSetButtons(mData,type,full);
	}}
 ];

/**信息查询组装后台参数*/
infoAll.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(infoAll.oTable, aoData);
	//组装查询条件
	var columnId = $("#infoAllForm #columnId").val();//栏目
	if(columnId.length > 0 && columnId != -1){
		aoData.push({ "name": "columnId", "value": columnId});
	}
	
	var infoTitile = $("#infoAllForm #infoTitile").val();//标题
	if(infoTitile.length > 0){
		aoData.push({ "name": "infoTitile", "value": infoTitile});
	}
	
	var sendId = $("#infoAllForm #sendId-sendId").val();//发布人
	if(sendId.length > 0){
		aoData.push({ "name": "sendId", "value": sendId});
	}
	
	var sendDepid = $("#infoAllForm #sendDepid-sendDepid").val();//发布部门
	if(sendDepid.length > 0){
		aoData.push({ "name": "sendDepid", "value": sendDepid});
	}
	
	var flowStatusForQuery = $("#infoAllForm #flowStatusForQuery").val();//流程状态
	if(flowStatusForQuery.length > 0){
		aoData.push({ "name": "flowStatusForQuery", "value": flowStatusForQuery});
	}
	
	var sendTimeBegin = $("#infoAllForm #sendTimeBegin").val();//发布时间开始
	if(sendTimeBegin.length > 0){
		aoData.push({ "name": "sendTimeBegin", "value": sendTimeBegin});
	}
	
	var sendTimeEnd = $("#infoAllForm #sendTimeEnd").val();//发布时间结束
	if(sendTimeEnd.length > 0){
		aoData.push({ "name": "sendTimeEnd", "value": sendTimeEnd});
	}
	if ($("#infoAllForm #isFailure_t").prop('checked')) {//是否有效-是
		aoData.push({ "name": "isFailure_t", "value": $("#infoAllForm #isFailure_t").val()});
	}
	if ($("#infoAllForm #isFailure_f").prop('checked')) {//是否有效-否
		aoData.push({ "name": "isFailure_f", "value": $("#infoAllForm #isFailure_f").val()});
	}
};

//信息查询分页查询方法
infoAll.initList = function () {
	if (infoAll.oTable == null) {
		infoAll.oTable = $('#infoAllTable').dataTable( {
			"iDisplayLength": infoAll.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/im/info/manageAllInfoList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": infoAll.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				infoAll.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,5,6,7]}]
		} );
	} else {
		//table不为null时重绘表格
		infoAll.oTable.fnDraw();
	}
};

//清空编制查询区域
infoAll.queryFormReset = function(){
	$('#infoAllForm')[0].reset();
	//发布人人员选择树清空
	selectControl.clearValue("sendId-sendId");
	//发布部门选择树清空
	selectControl.clearValue("sendDepid-sendDepid");
	//栏目清空
	clearTreeSelectControl("columnId");
};

/**
 * 删除信息
 */
infoAll.deleteInfo = function (id) {
	var ids = String(id);
	if (ids.length == 0) {
		alertx("请选择要删除的记录");
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			infoAll.deleteCallBack(ids)
		}
	});
};

/**
 * 删除信息回调方法
 */
infoAll.deleteCallBack = function(ids) {
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
					callback:infoAll.initList()
				});
			}else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

/**将信息置顶*/
infoAll.toTop = function(id,modifyDate){
	// 删除验证
	$.ajax({
		type : "POST",
		url : getRootPath()+"/im/info/infoCount.action",
		data : {"id": id,
			t:(new Date()).getTime()},
		dataType : "json",
		success : function(data) {
			if (!data) {
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_012")
				});
			} else {
				jQuery.ajax({
					url : getRootPath() + "/im/info/toTop.action",
					async : false,
					type : 'POST',
					data : {"id":id,"modifyDate":modifyDate},
					success : function(data) {
						
						if (data.success == "true") {
							msgBox.tip({
								type:"success",
								content:data.successMessage,
								callback:infoAll.initList()
							});
						} else {
							msgBox.info({
								type:"fail",
								content:jQuery.validator.format($.i18n.prop("JC_OA_IM_002"))
							});
						}
					},
					error : function() {
						msgBox.info({
							type:"fail",
							content:jQuery.validator.format($.i18n.prop("JC_OA_IM_002"))
						});
					}
				});
			}
		}
	});
};

/**将信息取消置顶*/
infoAll.toDown = function(id,modifyDate){
	// 删除验证
	$.ajax({
		type : "POST",
		url : getRootPath()+"/im/info/infoCount.action",
		data : {"id": id,
			t:(new Date()).getTime()},
		dataType : "json",
		success : function(data) {
			if (!data) {
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_012")
				});
			} else {
				jQuery.ajax({
					url : getRootPath() + "/im/info/toDown.action",
					async : false,
					type : 'POST',
					data : {"id":id,"modifyDate":modifyDate},
					success : function(data) {
						if (data.success == "true") {
							msgBox.tip({
								type:"success",
								content:data.successMessage,
								callback:infoAll.initList()
							});
						} else {
							msgBox.info({
								type:"fail",
								content:$.i18n.prop("JC_OA_IM_007")
							});
						}
					},
					error : function() {
						msgBox.info({
							type:"fail",
							content:$.i18n.prop("JC_OA_IM_007")
						});
					}
				});
			}
		}
	});
};

/**阅读情况分页对象*/
infoAll.readingStatusTable = null;
/**查看阅读情况*/
infoAll.initReadList = function(id){
	// 删除验证
	$.ajax({
		type : "POST",
		url : getRootPath()+"/im/info/infoCount.action",
		data : {"id": id,
			t:(new Date()).getTime()},
		dataType : "json",
		success : function(data) {
			if (!data) {
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_012")
				});
			} else {
				$('#Reading').modal('show');
			}
		}
	});
	//显示列信息
	infoAll.readingStatusTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "readUserName"},
		{mData: "readTime"}
	 ];
	
	//组装后台参数
	infoAll.readingStatusTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(infoAll.readingStatusTable, aoData);
		//组装查询条件
		aoData.push({ "name": "infoId", "value": id});
	};
	
	//table对象为null时初始化
	if (infoAll.readingStatusTable == null) {
		infoAll.readingStatusTable = $('#readingStatusTable').dataTable( {
			sAjaxSource: getRootPath()+"/im/infoRead/manageList.action?time=" + new Date(),
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: infoAll.readingStatusTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				infoAll.readingStatusTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1]}]
		} );
		
	} else {
		//table不为null时重绘表格
		infoAll.readingStatusTable.fnDraw();
		//pageChange(user.oTable);
	}
	
	$('#Reading').modal('show');
};

/**初始化栏目选择树*/
infoAll.initColumn = function(){
	var initValue = [];
	$("#column").treeSelectControl({
		controlId:"columnId",   //必须
		controlName:"columnId", //必须
		multiMode:false,   //必须
		containerId:"column",  //必须，外层容器ID
		url:getRootPath()+"/im/column/queryColumnTree.action", //必须
		//callbackFunction:infoDeliver.columnCallback,  //可选值变化回调函数
		mappings:{id:"id",name:"columnName",parentId:"columnParentId",title:"columnName",nodeType:"nodeType"},  //可选
		initValue:initValue  //可选
	});
};

/**详细页跳转方法*/
infoAll.infoDetail = function(infoId,flowStatus){
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
				if(flowStatus=='1'||flowStatus=='0'){
					window.open(encodeURI(getRootPath() + "/im/info/infoAllDetail.action?id="+infoId+"&type=5&menuFlag=5&time=" + new Date()));
				}else{
					window.open(encodeURI(getRootPath() + "/im/info/infoAllDetail.action?id="+infoId+"&type=1&menuFlag=5&time=" + new Date()));
				}
				
			}
		}
	});
};

/**
 * 初始化方法
 */
jQuery(function($){
	infoAll.pageRows = TabNub > 0 ? TabNub : 10;
	//我的发布查询按钮绑定事件
	$("#infoAllQuery").click(infoAll.initList);
	//我的发布重置按钮绑定事件
	$("#infoAllReset").click(infoAll.queryFormReset);
	selectControl.init("controlTree1", "sendId-sendId", true, true);//人员单选
	selectControl.init("controlTree2", "sendDepid-sendDepid", true, false, 0);//部门选择
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化栏目树
	infoAll.initColumn();
	//初始化列表
	infoAll.initList();
});
//@ sourceURL=infoAll.js