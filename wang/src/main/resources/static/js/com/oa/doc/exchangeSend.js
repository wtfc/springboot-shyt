var exchangeSend = {};
exchangeSend.pageRows = null;
//重复提交标识
exchangeSend.subState = false;

//分页处理 start
//分页对象
exchangeSend.oTable = null;
//显示列信息
exchangeSend.oTableAoColumns = [
	{ "mData": "status", "mRender" : function(mData) {
		return mData == 0 ? "未发送" : "已发送";
	}},
	{ "mData": "title"},
	{ "mData": "sendDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
exchangeSend.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(exchangeSend.oTable, aoData);
	//组装查询条件
	$.each($("#exchangeSendListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
exchangeSend.exchangeSendList = function () {
	$('#exchangeSend-list').fadeIn();
	if (exchangeSend.oTable == null) {
		exchangeSend.oTable = $('#exchangeSendTable').dataTable( {
			"iDisplayLength": exchangeSend.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/exchangeSend/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": exchangeSend.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				exchangeSend.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [3]}]
		} );
	} else {
		exchangeSend.oTable.fnDraw();
	}
};
//分页处理 end

exchangeSend.queryReset = function(){
	$('#exchangeSendListForm')[0].reset();
	//exchangeSend.exchangeSendList();
};

//显示发送公文弹出层
exchangeSend.showExchangeSendAddDiv = function (id, docType){
	$("#dataLoad").show();
	//getToken();
	exchangeSend.clearForm();
	$("#docType").val(docType);
	var url = getRootPath()+"/doc/exchangeSend/get.action";
	var params = {
		time: new Date(),
		id: id
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
        	if(data){
        		//$("#exchangeSendAddForm").fill(data);
        		$("#exchangeSendAddForm #id").val(data.id);
        		$("#exchangeSendAddForm #modifyDate").val(data.modifyDate);
        		$('#exchangeSend-add').modal('show');
        	}
        	$("#dataLoad").hide();
        }
    });
};

//保存公文发送信息
exchangeSend.saveExchangeSend = function(){
	$("#dataLoad").show();
	$("#receiveDeptNames").val(returnValue("receiveDeptIds-receiveDeptIds"));//获取选择机构的名称和ID集合
	if(exchangeSend.subState)return;
	exchangeSend.subState = true;
	if ($("#exchangeSendAddForm").valid()) {
		var url = getRootPath()+"/doc/exchangeSend/update.action?time=" + new Date();
		var formData = $("#exchangeSendAddForm").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				exchangeSend.subState = false;
				//getToken();
				$("#token").val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$('#exchangeSend-add').modal('hide');
					exchangeSend.exchangeSendList();
				} else {
					if(data.labelErrorMessage){
						showErrors("exchangeSendAddForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
				$("#dataLoad").hide();
			},
			error : function() {
				$("#dataLoad").hide();
				exchangeSend.subState = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
			}
		});
	} else {
		$("#dataLoad").hide();
		exchangeSend.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//添加成功清空表单数据
exchangeSend.initForm = function(){
	exchangeSend.clearForm();
};

//清空表单
exchangeSend.clearForm = function(){
	$('#exchangeSendAddForm')[0].reset();
	$('#exchangeSendAddForm').find("input[type=hidden]").val("");
	$('#exchangeSendAddForm').find("textarea").text("");
	//清空机构控件值
	selectControl.clearValue("receiveDeptIds-receiveDeptIds");
	hideErrorMessage();
};


//签收单位分页对象
exchangeSend.receiveDeptsTable = null;
//显示签收信息
exchangeSend.showExchangeSendReceiveDeptsDiv = function(exchangeSendId){
	$('#exchangeSend-receiveDepts').modal('show');
	
	exchangeSend.receiveDeptsFnServerParams = function(aoData){
		 getTableParameters(exchangeSend.receiveDeptsTable, aoData);
		 aoData.push({ "name": "exchangeSendId", "value": exchangeSendId});
	};
	
	//显示签收单位列信息
	exchangeSend.receiveDeptsColumns = [
		{ "mData": "receiveDeptName" },
		{ "mData": "isUrgent", "mRender" : function(mData) {
			return mData == 0 ? "否" : "是";
		}},
		{ "mData": "status", "mRender" : function(mData, type, full) {
			if(mData=='0'){
				return '未签收';
			}else if(mData=='1'){
				return '<font color="red">发送人主动收回</font>';
			}else if(mData=='2'){
				return '<font color="red">已被接收单位拒收</font>';
			}else{
				return '<font color="green">已签收</font>';
			}
		}},
		{ "mData": "userName" },
		{ "mData": "receiveDate" }
	 ];
	if (exchangeSend.receiveDeptsTable == null) {
		exchangeSend.receiveDeptsTable = $('#exchangeSendReceiveDeptsTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/exchangeReceive/manageAllList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": exchangeSend.receiveDeptsColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				exchangeSend.receiveDeptsFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,3]}]
		} );
	} else {
		exchangeSend.receiveDeptsTable.fnDraw();
	}
};

//收回分页对象
exchangeSend.backTable = null;
//显示收回信息
exchangeSend.showExchangeSendBackDiv = function(exchangeSendId){
	$('#exchangeSend-back').modal('show');
	
	exchangeSend.backFnServerParams = function(aoData){
		 getTableParameters(exchangeSend.backTable, aoData);
		 aoData.push({ "name": "exchangeSendId", "value": exchangeSendId});	
		 aoData.push({ "name": "status", "value": "0"});
	};
	
	//显示收回列信息
	exchangeSend.backColumns = [
        {mData: function(source) {
    			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
    	}},
		{ "mData": "receiveDeptName" },
		{ "mData": "sendUserName" },
    	{ "mData": "sendDate" }
	 ];
	if (exchangeSend.backTable == null) {
		exchangeSend.backTable = $('#exchangeSendBackTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/exchangeReceive/manageAllList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": exchangeSend.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				exchangeSend.backFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		exchangeSend.backTable.fnDraw();
	}
};

//执行收回操作
exchangeSend.updateExchangeSendBack = function (id) {
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
			content: $.i18n.prop("JC_SYS_029","要收回的公文")
		});
		return;
	}
	exchangeSend.updateReveiveStatus(ids);
	//$('#exchangeSend-back').modal('hide');
};
//收回发文回调方法
exchangeSend.updateReveiveStatus = function(ids) {
	$("#dataLoad").show();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/exchangeReceive/updateReveiveStatus.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				$('#exchangeSend-back').modal('hide');
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
			} else {
				msgBox.tip({
					content: data.errorMessage
				});
			}
			$("#dataLoad").hide();
		},
		error : function() {
			$("#dataLoad").hide();
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_055")
			});
		}
	});
};
var Doc = {
		id:'',
		viewFile:''
};
exchangeSend.viewDoc = function(id, docType){
	var url;
	if(docType==0){
		url = getRootPath()+"/doc/send/getSendByWorkflowInstance.action";
	}else{
		url = getRootPath()+"/doc/receive/getReceiveByWorkflowInstance.action";
	}
	var params = {
		time: new Date(),
		id: id
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
        	Doc['id'] = data.id;
        	Doc['viewFile'] = data.viewFile;
        	window.open(getRootPath()+"/doc/send/viewDoc.action");
        },
		error : function() {
			$("#dataLoad").hide();
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_060")
			});			
		}
    });
	
}


//查看公文
exchangeSend.showDocView = function(docId,docType){
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
        		//openform(data.workId,data.piId,"business","view");
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

//初始化
jQuery(function($){
	//计算分页显示条数
	exchangeSend.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryExchangeSend").click(exchangeSend.exchangeSendList);
	$("#queryReset").click(exchangeSend.queryReset);
	//保存分发内容
	$("#saveExchangeSend").click(exchangeSend.saveExchangeSend);
	//收回分发公文
	$("#updateExchangeSendBack").click(function(){exchangeSend.updateExchangeSendBack(0);});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//选择机构
//	selectControl.init("controlTreeSendDept","receiveDeptIds-receiveDeptIds", true, false,1);
	selectOrg.init("controlTreeSendDept","receiveDeptIds-receiveDeptIds", true);
	exchangeSend.exchangeSendList();
});