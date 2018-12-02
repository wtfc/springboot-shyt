var attach = {};

// var businessId_old;

// var isrepeat = false;

attach.pageRows = null;
// 重复提交标识
attach.subState = false;

// 分页处理 start
// 分页对象
attach.oTable = null;

// 分页查询用户
attach.attachList = function() {
	// 显示列信息
	attach.oTableAoColumns = [ {
		mData : "fileName"
	}, {
		mData : "fileSize"
	}, {
		mData : "uploadTime"
	}, {
		mData : function(source) {
			return userOTableSetButtons(source);
		}
	} ];

	// 组装后台参数
	attach.oTableFnServerParams = function(aoData) {
		// 排序条件
		getTableParameters(attach.oTable, aoData);
		// 组装查询条件
		var businessId = $("#businessId").val();
		var businessTable = $("#businessTable").val();
		aoData.push({
			"name" : "isPaged",
			"value" : "1"
		});
		aoData.push({
			"name" : "businessId",
			"value" : businessId
		});
		aoData.push({
			"name" : "businessTable",
			"value" : businessTable
		});
	};

	// table对象为null时初始化
	if (attach.oTable == null) {
		attach.oTable = $('#attachTable').dataTable({
			bPaginate : false,
			sAjaxSource : getRootPath() + "/content/attach/manageList.action",
			fnServerData : oTableRetrieveData,// 查询数据回调函数
			aoColumns : attach.oTableAoColumns,// table显示列
			fnServerParams : function(aoData) {// 传参
				attach.oTableFnServerParams(aoData);
			},
			aaSorting : []
		// 设置表格默认排序列
		});
	} else {
		attach.oTable.fnDraw();
	}
};

// 分页处理 end

// 附件下载
attach.download = function(fileName, resourcesName) {
	jQuery.ajax({
		url : getRootPath() + "/content/attach/download.action",
		type : 'POST',
		data : {
			"fileName" : fileName,
			"resourcesName" : resourcesName
		},
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : ""
				});
			} else {
				msgBox.tip({
					content : data.errorMessage
				});
			}
		},
		error : function() {
			msgBox.tip({
				content : "下载失败"
			});
		}
	});
};

// 根据上传类型，控制是否可以多文件上传
attach.isMultipleAdd = function() {
	var upload_type = $("#upload_type").val();
	// var itemcount= $("table[id$='attacthItem']>tbody").children("tr").length;
	var itemcount = $(document).find('.files').find('tr').size();
	if (upload_type == "1" && itemcount >= 1) {// 单传控制
		msgBox.tip({
			content : "已达到上传文件上限"
		});
		return false;
	}
	return true;
};

attach.modify = function() {
	var businessId = $("#businessId").val();
	var businessTable = $("#businessTable").val();
	jQuery.ajax({
		url : getRootPath() + "/content/attach/attachlist.action",
		type : 'POST',
		data : {
			"businessId" : businessId,
			"businessTable" : businessTable,
			"isPaged" : "1"
		}
	});
};

// 获得已上传的附件
attach.getList = function() {
	var isshow = $("#isshow").val();
	var businessId = $("#businessId").val();
	var businessTable = $("#businessTable").val();
	var category = $("#category").val();
	var iscopy = $("#iscopy").val();
	$('#fileupload').addClass('fileupload-processing');
	var url = getRootPath() + "/content/attach/attachlist.action";
	if (iscopy == "1") {//转发文复制附件
		url = getRootPath() + "/content/attach/copyAttachlist.action";
	}
	if (iscopy == "2") {//转发文复制附件和正文转成附件
		url = getRootPath() + "/content/attach/copyAttachAndTextList.action";
	}
	$.ajax({
		url : url,
		data : {
			"businessId" : businessId,
			"businessTable" : businessTable,
			"isPaged" : "1",
			"category" : category
		},
		dataType : 'json',
		async : false,
		context : $('#fileupload')[0]
	}).always(function() {
		$(this).removeClass('fileupload-processing');
	}).done(function(result) {
		//clearTable();
		$(this).fileupload('option', 'done').call(this, $.Event('done'), {
			result : result
		});
		if (isshow == "1") {
			$('#fileupload').find(".fileupload-buttonbar").hide();
			$('#attacthItem').find(".delete").hide();
			$('#attacthItem').find(".toggle").hide();
			$('#attacthItem').find(".dark").show();
		} else {
			$('#fileupload').find(".fileupload-buttonbar").show();
			$('#attacthItem').find(".delete").show();
			$('#attacthItem').find(".toggle").hide();
			$('#attacthItem').find(".dark").hide();
		}
	});
};
// 设置以前的复选框同时操作全选中复选框
setOldCheck = function(item) {
	var flag = true;
	$(item).closest(".fade").find('.toggle').trigger("click");
	if ($(item).is(":checked") == false) {
		$(document).find('.allnewtoggle').prop('checked', false);
	} else {
		$(document).find('.newtoggle').each(function() {
			if ($(this).is(":checked") == false)
				flag = false;
		});
		$(document).find('.allnewtoggle').prop('checked', flag);
	}
};

// 清除表格 需手工调用
clearTable = function() {
	$(document).find('.files').empty();
};

// 清除隐藏域被删除的附件id值
clearDelAttachIds = function() {
	$("#delattachIds").val("");
};

// 清除回显列表
clearAttachlist = function() {
	var attachList = $("#attachList");
	attachList.empty();
};
// 清除逻辑属性值
clearBunessVal = function() {
	$("#businessId").val("");
	$("#businessTable").val("");
};
// 获得逻辑删除的id
getLogicalAttachIds = function() {
	
};
// 显示回显列表 flag true 显示删除按钮 id 为回显容器的id默认为attachList
showAttachList = function(flag, id, callback) {
	var str = "";
	var attachList = $("#attachList");
	
	
	if (typeof (id) != "undefined") {
		if (id != null || id != "")
			attachList = $("#" + id);
	}

	if ($(document).find(".template-download").length == 0) {
		attachList.html("");
	} else {
		$(document).find(".template-download").each(
			function() {
				var downloadItemUrl = $(this).find("input[name='downloadItemUrl']").val();
				//var deleteItemUrl = $(this).find("input[name='deleteItemUrl']").val();
				var filename = $(this).find("input[name='filename']").val();
				var fileid = $(this).find("input[name='fileid']").val();
				var fileSize = $(this).find("input[name='fileSize']").val();
				if(filename){
					if (flag == true)
						str += "<li id=\"l"+fileid+"\"><a href='" + downloadItemUrl
								+ "'><i class='fa fa-paper-clip m-r-sm'></i>"
								+ filename + "</a>&nbsp;&nbsp;"+fileSize
								+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=asynDel("+fileid+","+callback+")>x</a></li>";
					else
						str += "<li><a href='" + downloadItemUrl
								+ "'><i class='fa fa-paper-clip m-r-sm'></i>"
								+ filename + "</a>&nbsp;&nbsp;"+fileSize+"</li>";
					attachList.html(str);
				}
			});
	}
	
};

asynDel = function(fileid, callback) {
	var btnid = "btn" + fileid;
	var btn = $("#" + btnid);
	msgBox.confirm({
		content : $.i18n.prop("JC_SYS_034"),
		success : function() {
			//var flag = $("#islogicDel").val();
			//if (flag == "1"||flag == "2") {
				btn.trigger("click");
				$("#l"+fileid).remove();
				if(callback){
					callback();
				}
				return;
			//}
		}
	});
	
};


// 异步删除
asynDel2 = function(fileid) {
	this.event.preventDefault();// 取消浏览器默认行为
	var url = this.event.currentTarget.href;
	var obj = $(this.event.currentTarget);
	var btnid = "btn" + fileid;
	var btn = $("#" + btnid);
	msgBox.confirm({
		content : $.i18n.prop("JC_SYS_034"),
		success : function() {
			var flag = $("#islogicDel").val();
			if (flag == "1") {
				btn.trigger("click");
				obj.closest("li").remove();
				return;
			}
			jQuery.ajax({
				url : url,
				type : 'get',
				async : false,
				success : function() {
					btn.trigger("click");
					obj.closest("li").remove();
				}
			});
		}
	});
};

// 无需弹出显示列表
echoAttachList = function(flag) {
	attach.getList();
	showAttachList(flag);
};

//无需弹出显示列表
echoAttachListDul = function(flag,id) {
	attach.getList();
	showAttachList(flag,id);
};

// 设置隐藏域里的值分别传入附件id 附件名称 逻辑删除id 传入值以逗号分隔   
attach.setAttachHiddenVal = function(ids, names, delIds) {
	var attachIds="";
	var attachNames="";
	var attachDelIds="";
	if (typeof (ids) != "undefined") {
		if (ids != null || ids != "") {
			attachIds = $("#" + ids);
			attachIds.val("");
		}
	}
	if (typeof (names) != "undefined") {
		if (names != null || names != "") {
			attachNames = $("#" + names);
			attachNames.val("");
		}
	}
	if (typeof (delIds) != "undefined") {
		if (delIds != null || delIds != "") {
			attachDelIds = $("#" + delIds);
			attachDelIds.val("");
		}
	}

	$(document).find(".template-download").each(function() {
		var filename = $(this).find("input[name='filename']").val();
		var fileid = $(this).find("input[name='fileid']").val();
		if(typeof (attachIds) == "object")
			if (attachIds.val() == "" || attachIds.val() == null) {
				attachIds.val(fileid);
			} else {
				attachIds.val(attachIds.val() + "," + fileid);
			}
		if(typeof (attachNames) == "object")
			if (attachNames.val() == "" || attachNames.val() == null) {
				attachNames.val(filename);
			} else {
				attachNames.val(attachNames.val() + "," + filename);
			}
	});
	if(typeof (attachDelIds) == "object")
		attachDelIds.val($("#delattachIds").val());
};

// 初始化
jQuery(function($) {
	// 去除属性multiple upload_type=1时为单传
	if ($("#upload_type").val() == "1") {
		if (document.getElementById("files").hasAttribute("multiple"))
			document.getElementById("files").removeAttribute("multiple");
	}
	$("#files").click(attach.isMultipleAdd);
	attach.pageRows = 10;
	var attachButton = document.getElementsByName("queryattach");// 多个表单click按钮的实现
	for (var i = 0; i < attachButton.length; i++) {
		$(attachButton[i]).click(attach.getList);
	}
	$(this).find('.allnewtoggle')
			.on(
					{
						click : function(e) {/*
							if ($("table[id$='attacthItem']>tbody").children(
									"tr").length == 1) {
								$(document).find('.allnewtoggle').prop(
										'checked', true);
							}
							var flag = $(document).find('.allnewtoggle').is(
									":checked");
							$(document).find('.newtoggle')
									.prop('checked', flag);
							if (flag == false) {
								if ($(document).find(
										'.fileupload-buttonbar .toggle').is(
										":checked") == false)
									$(document).find(
											'.fileupload-buttonbar .toggle')
											.trigger("click");
							} else {
								if ($(document).find(
										'.fileupload-buttonbar .toggle').is(
										":checked") == true)
									$(document).find(
											'.fileupload-buttonbar .toggle')
											.trigger("click");
							}
						*/}
					});
});