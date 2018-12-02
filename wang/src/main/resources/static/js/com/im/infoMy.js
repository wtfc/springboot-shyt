infoMy = {};
/**分页变量*/
infoMy.pageRows = null;
/**我的发布分页对象*/
infoMy.oTable = null;

/**我的发布信息显示列信息*/
infoMy.oTableAoColumns = [
//	{mData: "infoTitile", "mRender" : function(mData, type, full) {
//	  var resultStr = "";
//	  if(full.portalPic!=""&&full.portalPic!=null){
//		  resultStr += "<i class='fa fa-photo m-r-xs'></i>";
//	  }
//		resultStr += full.infoTitile;
//		return resultStr;
//	}},//信息标题
	{mData: "infoTitile"},//标题
	{mData: "columnName"},//栏目
	{mData: "sendName"},//发布人
	{mData: "sendDepName"},//发布部门
	{mData: "sendTime"},//发布时间
	{mData: "isFailure",mRender:function(mData,type,full){
		return mData == 1 ? "否" : "是";
	}},//有效状态
	//设置权限按钮
	{mData:null,"mRender":function(mData,type,full) {
		return oTableSetButtons(mData,type,full);
	}}
 ];

/**我的发布组装后台参数*/
infoMy.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(infoMy.oTable, aoData);
	//组装查询条件
	var columnId = $("#infoMyForm #columnId").val();//栏目
	if(columnId.length > 0 && columnId != -1){
		aoData.push({ "name": "columnId", "value": columnId});
	}
	
	var infoTitile = $("#infoMyForm #infoTitile").val();//标题
	if(infoTitile.length > 0){
		aoData.push({ "name": "infoTitile", "value": infoTitile});
	}
	
	var sendId = $("#infoMyForm #sendId-sendId").val();//发布人
	if(sendId.length > 0){
		aoData.push({ "name": "sendId", "value": sendId});
	}
	
	var sendDepid = $("#infoMyForm #sendDepid-sendDepid").val();//发布部门
	if(sendDepid.length > 0){
		aoData.push({ "name": "sendDepid", "value": sendDepid});
	}
	
	var sendTimeBegin = $("#infoMyForm #sendTimeBegin").val();//发布时间开始
	if(sendTimeBegin.length > 0){
		aoData.push({ "name": "sendTimeBegin", "value": sendTimeBegin});
	}
	
	var sendTimeEnd = $("#infoMyForm #sendTimeEnd").val();//发布时间结束
	if(sendTimeEnd.length > 0){
		aoData.push({ "name": "sendTimeEnd", "value": sendTimeEnd});
	}
};

//我的发布分页查询方法
infoMy.initList = function () {
	if (infoMy.oTable == null) {
		infoMy.oTable = $('#infoMyTable').dataTable( {
			"iDisplayLength": infoMy.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/im/info/manageMyList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": infoMy.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				infoMy.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,5,6]}]
		} );
	} else {
		//table不为null时重绘表格
		infoMy.oTable.fnDraw();
	}
};

//清空编制查询区域
infoMy.queryFormReset = function(){
	$('#infoMyForm')[0].reset();
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
infoMy.deleteInfo = function (id) {
	var ids = String(id);
	if (ids.length == 0) {
		alertx("请选择要删除的记录");
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			infoMy.deleteCallBack(ids)
		}
	});
};

/**
 * 删除信息回调方法
 */
infoMy.deleteCallBack = function(ids) {
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
					callback:infoMy.initList()
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
infoMy.toTop = function(id,modifyDate){
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
					callback:infoMy.initList()
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
};

/**阅读情况分页对象*/
infoMy.readingStatusTable = null;
/**查看阅读情况*/
infoMy.initReadList = function(id){
	//显示列信息
	infoMy.readingStatusTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: "readUserName"},
		{mData: "readTime"}
	 ];
	
	//组装后台参数
	infoMy.readingStatusTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(infoMy.readingStatusTable, aoData);
		//组装查询条件
		aoData.push({ "name": "infoId", "value": id});
	};
	
	//table对象为null时初始化
	if (infoMy.readingStatusTable == null) {
		infoMy.readingStatusTable = $('#readingStatusTable').dataTable( {
			sAjaxSource: getRootPath()+"/im/infoRead/manageList.action?time=" + new Date(),
			bPaginate: false,
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: infoMy.readingStatusTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				infoMy.readingStatusTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1]}]
		} );
		
	} else {
		//table不为null时重绘表格
		infoMy.readingStatusTable.fnDraw();
		//pageChange(user.oTable);
	}
	
	$('#Reading').modal('show');
};

/**临时添加的详细页跳转方法*/
infoMy.infoDetail = function(infoId){
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
//				loadrightmenu("/im/info/infoDetail.action?id="+infoId+"&type=1&time=" + new Date(),"unknown");
				window.open(encodeURI(getRootPath() + "/im/info/infoDetail.action?id="+infoId+"&type=1&menuFlag=4&time=" + new Date()));
			}
		}
	});
};

/**
 * 初始化方法
 */
jQuery(function($){
	infoMy.pageRows = TabNub > 0 ? TabNub : 10;
	//我的发布查询按钮绑定事件
	$("#infoMyQuery").click(infoMy.initList);
	//我的发布重置按钮绑定事件
	$("#infoMyReset").click(infoMy.queryFormReset);
	selectControl.init("controlTree1", "sendId-sendId", true, true);//人员单选
	selectControl.init("controlTree2", "sendDepid-sendDepid", true, false, 0);//部门选择
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	$("#column").treeSelectControl({
		controlId:"columnId",   //必须
		controlName:"columnId", //必须
		multiMode:false,   //必须
		containerId:"column",  //必须，外层容器ID
		url:getRootPath()+"/im/column/queryColumnTreeByUser.action", //必须
		//callbackFunction:infoDeliver.columnCallback,  //可选值变化回调函数
		mappings:{id:"id",name:"columnName",parentId:"columnParentId",title:"piId",nodeType:"nodeType"}  //可选
		//initValue:initValue  //可选
	});
	//初始化列表
	infoMy.initList();
});