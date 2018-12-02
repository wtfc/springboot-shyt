var mailbox = {};
//重复提交标识
mailbox.subState = false;
//添加修改公用方法
mailbox.savaOrModify = function (hide) {
	if (mailbox.subState)return;
	mailbox.subState = true;
	if ($("#mailboxForm").valid()) {
		var url = getRootPath()+"/ic/mailbox/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/ic/mailbox/update.action?time=" + new Date();
		}
		var formData = $("#mailboxForm").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				mailbox.subState = false;
				$('#token').val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						content: $.i18n.prop("JC_SYS_001"),
						type:"success"
					});
					if ($("#id").val() == 0) {
						mailbox.clearForm();
					} else {
						$("#modifyDate").val(data.modifyDate);
					}
					mailbox.mailboxList();
					if (hide) {
						$('#myModal-edit').modal('hide');
					}
				} else {
					if(data.labelErrorMessage){
						showErrors("mailboxForm", data.labelErrorMessage);
					} else if (data.errorMessage) {
						msgBox.tip({
							content: data.errorMessage,
							type:"fail"
						});
					} else {
						msgBox.tip({
							content: $.i18n.prop("JC_SYS_002"),
							type:"fail"
						});
					}
				}
				$("#dataLoad").fadeOut();
			},
			error : function() {
				mailbox.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	}else{
		$("#dataLoad").fadeOut();
		mailbox.subState = false;
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_067"),
			type:"fail"
		});
	}
};

//清空表单
mailbox.clearForm = function () {
	$('#mailboxForm').find("input[type=text]").val("");
	$('#mailboxForm').find("input[type=password]").val("");
	$('#mailboxForm').find("input[type=checkbox]").attr("checked",false);
	$('#list').find("input[type=checkbox]").attr("checked",false);
	$('#mailboxForm').find("textarea").val("");
};

//删除邮箱
mailbox.deleteMailbox = function (id) {
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
			content: $.i18n.prop("JC_SYS_061"),
			type:"fail"
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			mailbox.deleteCallBack(ids);
		}
	});
};

mailbox.deleteCallBack = function(ids) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/mailbox/delete.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				mailbox.clearForm();
				mailbox.mailboxList();
			}else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

//获取修改信息
mailbox.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/mailbox/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				mailbox.clearForm();
				$("#mailboxForm").fill(data);
				if(data.senderSSL == '1'){
					$('#senderSSL').prop("checked",true);
				}
				if(data.receiveSSL == '1'){
					$('#receiveSSL').prop("checked",true);
				}
				$("#oldName").val(data.username);
				$("#savaOrModify").hide();
				$("#title").html("编辑");
				$("#savaAndClose").addClass("dark");
				$("#savaAndClose").html("保 存");
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//分页处理 start
//分页对象
mailbox.oTable = null;
//显示列信息
mailbox.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{mData:"address"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
 ];


//组装后台参数
mailbox.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(mailbox.oTable, aoData);
	//组装查询条件
	if ($("#mailboxListForm #address").val()!="") {
		aoData.push({ "name": "address", "value": $("#mailboxListForm #address").val()});
	}
	
};

//分页查询邮箱
mailbox.mailboxList = function () {
	$('#IP-edit').fadeIn();
	if (mailbox.oTable == null) {
		mailbox.oTable = $('#mailboxTable').dataTable( {
			iDisplayLength: mailbox.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/mailbox/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: mailbox.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				mailbox.oTableFnServerParams(aoData);
			},
			aaSorting:[],
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,2]}]
		} );
	} else {
		mailbox.oTable.fnDraw();
	}
};
//分页处理 end

//显示添加邮箱弹出层
mailbox.showAddDiv = function (){
	//判断重复提交
	// getToken();
	//清除验证信息
	hideErrorMessage();
	mailbox.clearForm();
	$("#id").val(0);
	$("#title").html("新增");
	$("#savaAndClose").removeClass("dark");
	$("#savaAndClose").html("保存退出");
	$("#savaOrModify").show();
	$("#receivePort").attr("placeholder","143");
	$("#senderPort").attr("placeholder","25");
	$('#myModal-edit').modal('show');
};

//初始化
jQuery(function($) 
{
	//计算分页显示条数
	mailbox.pageRows = TabNub>0 ? TabNub : 10;
	$("#querymailbox").click(mailbox.mailboxList);
	$("#showAddDiv").click(mailbox.showAddDiv);
	$("#showAddDiv_t").click(mailbox.showAddDiv);
	$("#deleteMailboxs").click("click", function(){mailbox.deleteMailbox(0);});
	$("#savaAndClose").click(function(){$("#dataLoad").fadeIn();mailbox.savaOrModify(true);});//
	$("#savaOrModify").click(function(){$("#dataLoad").fadeIn();mailbox.savaOrModify(false);});
	mailbox.mailboxList();
	
	//选中ssl安全连接后，改变端口的默认值
	$("#receiveSSL").change(function() {
		if ($("#receiveSSL").prop("checked")) {
			$("#receivePort").attr("placeholder","993");
		} else {
			$("#receivePort").attr("placeholder","143");
		}
	});
	//选中ssl安全连接后，改变端口的默认值
	$("#senderSSL").change(function() {
		if ($("#senderSSL").prop("checked")) {
			$("#senderPort").attr("placeholder","465");
		} else {
			$("#senderPort").attr("placeholder","25");
		}
	});
});
//便于chrome中js调试
//@ sourceURL=mailboxInteract.js 