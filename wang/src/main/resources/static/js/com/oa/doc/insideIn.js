var insideIn = {};
insideIn.pageRows = null;
//重复提交标识
insideIn.subState = false;
//分页处理 start
//分页对象
insideIn.oTable = null;
//显示列信息
insideIn.oTableAoColumns = [
	{ "mData": "title"},
	{ "mData": "docType", "mRender" : function(mData) {
		return mData == 0 ? "发文" : "收文";
	}},
	{ "mData": "sendUserName"},
	{ "mData": "receiveDate"},
	{ "mData": "validDate", "mRender" : function(mData) {
		return mData == '9999-02-01 00:00:00' ? "永久" : mData;
	}},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
insideIn.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(insideIn.oTable, aoData);
	//组装查询条件
	$.each($("#insideInListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	aoData.push({ "name": "reveiveOperate", "value": "0,3"});
};

//分页查询
insideIn.insideInList = function () {
	$('#insideIn-list').fadeIn();
	if (insideIn.oTable == null) {
		insideIn.oTable = $('#insideInTable').dataTable( {
			"iDisplayLength": insideIn.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/insideIn/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": insideIn.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				insideIn.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [5]}]
		} );
	} else {
		insideIn.oTable.fnDraw();
	}
};
//分页处理 end

insideIn.queryReset = function(){
	$('#insideInListForm')[0].reset();
	selectControl.clearValue("sendUserId-sendUserId");
	//insideIn.insideInList();
};

//清空回复表单
insideIn.clearForm = function(){
	$('#insideInReplyForm')[0].reset();
	$('#insideInReplyForm').find("input[type=hidden]").val("");
	hideErrorMessage();
};

//分发公文
insideIn.showInsideInAddDiv = function (id,modifyDate,insideOutId){
	$(document).showInsideOutAddDiv_(insideIn.insideInList,id,3,modifyDate,insideOutId);
};

//回复接收公文页面显示
insideIn.showInsideInReplyDiv = function (id, usable){
	if("0" == usable) {
		msgBox.tip({
			content: '对不起，您已超出查看时限'
		});
		return;
	}
	$("#dataLoad").show();
	insideIn.clearForm();
	var url = getRootPath()+"/doc/insideIn/get.action";
	var params = {
		time: new Date(),
		id: id
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
			if (data) {
	        	$("#insideInReplyForm").fill(data);
	        	$('#insideIn-reply').modal('show');
			}
			$("#dataLoad").hide();
        }
    });
};

//回复短信验证
insideIn.validRemind = function(){
	if ($("#insideInReplyForm").valid()) {
		var sendUserId = $('#insideInReplyForm #sendUserId').val();
		var reminder = $('#insideInReplyForm input[name="remindMode"]:checked').val();
		if(reminder=='1'){//是短信
			jQuery.ajax({
				url : getRootPath()+"/doc/insideIn/validRemind.action?time=" + new Date(),
				type : 'get',
				async: false,
				dataType : "json",
				data : {'sendUserId':sendUserId},
				success : function(data) {
					if(data.success=="success"){
						insideIn.updateInsideInReply();
					}else{
						if(data.success){
							msgBox.confirm({
								content: data.successMessage,
								success: function(){
									insideIn.updateInsideInReply();
								},
								cancel:function(){
								}
							});
							
						}else{
							msgBox.info({
								content: data.errorMessage
							});
						} 
					}
				},
				error : function() {
					msgBox.tip({
						type:"fail",
						content:$.i18n.prop("JC_OA_PO_015"),
						callback:function(){
						}
					});
				}
			});
		}else{//是邮件
			insideIn.updateInsideInReply();
		}
	}else{
		$("#dataLoad").hide();
		subState_ = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//回复接收公文修改
insideIn.updateInsideInReply = function(){
	$("#dataLoad").show();
	if(insideIn.subState)return;
	insideIn.subState = true;
	if ($("#insideInReplyForm").valid()) {
		var url = getRootPath()+"/doc/insideIn/updateReplay.action?time=" + new Date();
		var formData = $("#insideInReplyForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				insideIn.subState = false;
				if(data.success == "true"){
					insideIn.clearForm();
					$('#insideIn-reply').modal('hide');
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					insideIn.insideInList();
				} else {
					if(data.labelErrorMessage){
						showErrors("insideInReplyForm", data.labelErrorMessage);
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
				insideIn.subState = false;
				content: jQuery.validator.format($.i18n.prop("JC_SYS_002"));
			}
		});
	} else {
		$("#dataLoad").hide();
		insideIn.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//拒收操作
insideIn.updateInsideInRejection = function(id,modifyDate, usable){
	if("0" == usable) {
		msgBox.tip({
			content: '对不起，您已超出查看时限'
		});
		return;
	}
	confirmx($.i18n.prop("JC_OA_DOC_009"),function(){
		jQuery.ajax({
			url : getRootPath()+"/doc/insideIn/updateReveiveOperate.action",
			type : 'POST',
			data : {"id": id,"modifyDate": modifyDate,"reveiveOperate": 2},
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					insideIn.insideInList();
				} else {
					msgBox.tip({
						content: data.errorMessage
					});
				}
			},
			error : function() {
				msgBox.tip({
					content:$.i18n.prop("JC_SYS_055")
				});
			}
		});
	});	
};

//查看公文
insideIn.showDocView = function(id,usable){
	if("0" == usable) {
		msgBox.tip({
			content: '对不起，您已超出查看时限'
		});
		return;
	}
	$("#dataLoad").show();
	var url = getRootPath()+"/doc/insideIn/getSendOrReceiveView.action";
	var params = {
		time: new Date(),
		id: id
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

//字典项初始化
insideIn.initDic = function(){
	//dic.fillDics("insideInListForm #docType", "doc_type");
};
/*
 * 弹出查看正文页面
 * */
var Doc = {
		id:'',
};
insideIn.viewDoc = function(id, usable){
	if("0" == usable) {
		msgBox.tip({
			content: '对不起，您已超出查看时限'
		});
		return;
	}
	var url = getRootPath()+"/doc/insideIn/getSendOrReceiveView.action";
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
        	window.open(getRootPath()+"/doc/send/viewDoc.action");
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
	insideIn.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryInsideIn").click(insideIn.insideInList);
	$("#queryReset").click(insideIn.queryReset);
	//回复接收公文
	$("#updateInsideInReply").click(insideIn.validRemind);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//查询人员选择树
	selectControl.init("querySendUserTree","sendUserId-sendUserId", false, true);
	insideIn.insideInList();
	insideIn.initDic();
});

