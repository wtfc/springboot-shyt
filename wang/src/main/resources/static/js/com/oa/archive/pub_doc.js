/**
 * 文档管理 zhanglg
 */
var base64 = new Base64();
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
var archive_doc = {
	permissionValue : "",
	permissionId : "",
	subState : false,
	documentType : 'doc',
	viewType : 1,// null只读，1编辑
	documentPath : '',
	folderSelector : null,
	model : 0,
	lockDate:null,
	modifyDate : null,
	currentSelectedRowId : null,
	currentDocumentId : null,
	folder : null
};
var currentUserId = 0;
archive_doc.pageRows = null;
archive_doc.oTable = null;// 分页对象
archive_doc.auditTable = null;// 分页对象

archive_doc.clearParam = function() {
	this.documentType = 'doc';
	this.viewType = 1;// null只读，1编辑
	this.documentPath = '';
	this.currentDMName = '';
	archive_doc.currentSelectedRowId = null;
};
var backFlag = -1;

archive_doc.option = {};

archive_doc.init = function() {
	archive_doc.loadData(null, null, null, null, true);
};

archive_doc.reloadData = function() {
	archive_doc.clearParam();
	archive_doc.loadData(archive_doc.getFolderId());
};
archive_doc.getFolderId = function() {
	return $('#folderId').val();
};
// WebOffice在线查看Office
archive_doc.viewFile = function(contentType, isEdit, path) {
	// word xls ppt 文件
	archive_doc.viewType = isEdit;// null 只读， 1 编辑
	archive_doc.documentPath = basePath_ + "/" + path;// null 只读， 1 编辑
	if (contentType == '1') {
		archive_doc.newWord();
	} else if (contentType == '2') {
		archive_doc.newExl();
	} else if (contentType == '3') {
		archive_doc.newPPT();
	}
	// archive_doc.viewType = 1;
};
// 异步加载数据
archive_doc.loadData = function(folderId, rowType, contentType, path, isClick) {
	$('#search').attr('value', '');
	$('#search').blur();
	if (folderId == 0) {
		return;
	}
	if (rowType == 1) {
		// 加载文档,
		// 需要先验证是否有权限
		// 先验证有没有操作权限
		if (folderId && path && path.length > 0) {
			path = base64.decode(path);
		}
		if (contentType == '0') {

			// 其他文档
		} else {
			archive_doc.viewFile(contentType, null, path);
		}
	} else {
		// 加载文件夹
		return $.ajax({
			type : "GET",
			url : getRootPath() + "/archive/folder/getPubDirDocs.action",
			data : {
				id : folderId,
				t : (new Date()).getTime()
			},
			dataType : "json",
			success : function(data) {
				$('#token').val(data.token);
				if (data.success == "true") {
					$('#doc_table > tbody').empty();
					$('#folderId').val(data.folder.id);
					archive_doc.folder = data.folder;
					backFlag += 1;
					archive_doc.render(data.folder);
					archive_doc.permission(data.folder);
					if (isClick) {
						archive_doc.pathBar.appendPath(data.folder.id,
								data.folder.folderName);
					}
					$('#btnNewFolder').removeAttr("disabled");
				} else {
					if (data.errorMessage) {
						msgBox.info({
							content : data.errorMessage
						});
					} else {
						msgBox.info({
							content : $.i18n.prop("JC_SYS_060")
						});
					}
				}
			}
		});
	}

};

// ---------表格操作开始---------------------------------------------------------------------------------------
// 界面渲染
archive_doc.render = function(folder) {
	currentUserId = folder.currentUserId;
	var tbody = $('#doc_table > tbody');
	var dirRow = "";
	var docRow = "";
	var self = this;

	// process subdirs
	$.each(folder.subdirs, function(i, o) {
		dirRow += ("<tr id=\"dir_" + o.id + "\" class=\"dir\">");
		dirRow += (self.createCheckCell(o));
		dirRow += (self.createDirNameCell(o));
		dirRow += (self.createDirOpCell(o));
		dirRow += (self.createSimpleCell(""));
		dirRow += (self.createSimpleCell("文件夹"));
		dirRow += (self.createSimpleCell(""));
		dirRow += (self.createSimpleCell(o.modifyDate));
		dirRow += (self.createSimpleCell(o.owner));
		dirRow += "</tr>";
	});
	tbody.append(dirRow);
	// process documents
	$.each(folder.documents, function(i, o) {
		docRow += ("<tr id=\"doc_" + o.id + "\" class=\"doc\">");
		docRow += (self.createCheckCell(o));
		docRow += (self.createDocNameCell(o));
		docRow += (self.createDocOpCell(o));
		docRow += (self.createSimpleCell(o.dmLockStatus == 1 ? "锁定" : '未锁定'));
		// docRow+=(self.createSimpleCell(o.contentType));
		docRow += (self.createSimpleCell(o.dmSuffix));
		// docRow+=(self.createSimpleCell(o.dmLockStatus));
		// docRow+=(self.createSimpleCell(o.contentType));
		docRow += (self.createSimpleCell(o.dmSize));
		docRow += (self.createSimpleCell(o.modifyDate));
		docRow += (self.createSimpleCell(o.owner));
		docRow += "</tr>";
	});
	tbody.append(docRow);
	// 如果没有记录，显示空白行
	archive_doc.processEmptyTable();
	$("i[data-toggle='tooltip']").tooltip();
	// root目录返回按钮不可用
	if (folder.parentFolderId == 0) {
		$('#btnReturnDisabled').show();
		$('#btnReturn').hide();
	} else {
		$('#btnReturnDisabled').hide();
		$('#btnReturn').show();
	}
	// IE8隔行变色
	ie8StylePatch();
	$("[data-toggle=dropdown]").dropdown();
	ListTable();
};
// 处理表格无数据的情况
archive_doc.processEmptyTable = function() {
	var tbody = $('#doc_table > tbody');
	if (tbody.children().length == 0) {
		tbody.append(archive_doc.createEmptyRow());
		return;
	}
	if (tbody.children().length > 1) {
		tbody.children('.empty_row').remove();
	}
};
// 生成空白行
archive_doc.createEmptyRow = function() {
	var row = '<tr class="empty_row"><td valign="top" colspan="8" class="dataTables_empty">没有匹配的记录</td></tr>';
	return row;
};
// 生成目录操作单元
archive_doc.createDirOpCell = function(folder) {
	return ininFolderPermission(folder);
	/*
	 * var option = "<td>"; option += "<div class=\"dropdown inline\"> ";
	 * option += "<a href=\"#\" type=\"button\" class=\"btn-uploading\"
	 * data-toggle=\"dropdown\"><i data-original-title=\"更多\"
	 * data-container=\"body\" title=\"\" data-placement=\"top\"
	 * data-toggle=\"tooltip\" class=\"fa fa-more-list\"></i></a> "; option += "
	 * <ul class=\"dropdown-menu animated fadeInRight\"> ";
	 * if(archive_doc.permissionValue.substring(0, 1)==1){
	 * if(archive_doc.permissionValue.substring(5, 6)==1){ option += " <li><a
	 * type=\"button\"
	 * href=\"javascript:archive_doc.clickCopy('#dir_"+folder.id+"')\"
	 * role=\"button\" >复制到...</a></li> "; option += " <li><a type=\"button\"
	 * href=\"javascript:archive_doc.clickCut('#dir_"+folder.id+"')\"
	 * role=\"button\" >剪切到...</a></li> "; }
	 * if(archive_doc.permissionValue.substring(6, 7)==1){ option += " <li><a
	 * type=\"button\" href=\"javascript:archive_doc.rename('#dir_" + folder.id +
	 * "');\" role=\"button\" >重命名</a></li> "; }
	 * if(archive_doc.permissionValue.substring(4, 5)==1){ option += " <li><a
	 * type=\"button\"
	 * href=\"javascript:archive_doc.rowDeleteClick('#dir_"+folder.id+"')\"
	 * role=\"button\" >删除</a></li> "; } } option += " <li><a type=\"button\"
	 * href=\"#document-information\" role=\"button\" data-toggle=\"modal\">授权</a></li> ";
	 * option += " </ul> "; option += " </div> </td>";
	 */
};

// 生成文件操作单元
archive_doc.createDocOpCell = function(doc) {
	var p = doc.physicalPath;
	if (p && p.length > 0) {
		p = base64.encode(p);
	}
	var allPermission = doc.createUser == currentUserId;
	var option = "<td>";
	if (archive_doc.folder.permView == true || allPermission) {
		if (archive_doc.folder.permNewUpDown == true || allPermission) {
			option += "<a href=\"javascript:javascript:archive_doc.download("
					+ doc.id
					+ ",'"
					+ doc.modifyDate
					+ "');\" ><i data-original-title=\"下载\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\"  class=\"fa fa-download\"></i></a>";
		}
		var office = (doc.contentType == 1 || doc.contentType == 2 || doc.contentType == 3);
		if (archive_doc.folder.permEdit == true || allPermission) {
			if (office) {
				option += "<a href=\"javascript:void(0)\"><i data-original-title=\"编辑\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" onclick=\"archive_doc.editDoc('"
						+ doc.contentType
						+ "','"
						+ p
						+ "','"
						+ doc.dmName
						+ "','"
						+ doc.id
						+ "','"
						+ doc.dmLockStatus
						+ "','"
						+ doc.modifyDate
						+ "')\"  class=\"fa fa-compose\"></i></a>";
			}
		}
	}
	option += "<div class=\"dropdown inline\"> ";
	option += "<a href=\"#\" type=\"button\" class=\"btn-uploading\" data-toggle=\"dropdown\"><i data-original-title=\"更多\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\"  class=\"fa fa-more-list\"></i></a> ";
	option += "        <ul class=\"dropdown-menu animated fadeInRight\">            ";
	if (archive_doc.folder.permView == true || allPermission) {
		if (archive_doc.folder.permCopyPaste == true || allPermission) {
			option += "			<li><a type=\"button\" href=\"javascript:archive_doc.clickCopy('#doc_"
					+ doc.id
					+ "',"
					+ doc.id
					+ ",'"
					+ doc.modifyDate
					+ "')\" role=\"button\" >复制到</a></li> ";
			option += "			 <li><a type=\"button\" href=\"javascript:archive_doc.clickCut('#doc_"
					+ doc.id
					+ "',"
					+ doc.id
					+ ",'"
					+ doc.modifyDate
					+ "')\" role=\"button\" >剪切到</a></li> ";
		}

		if (archive_doc.folder.permRename == true || allPermission) {
			option += "			 <li><a type=\"button\" href=\"javascript:archive_doc.rename('#doc_"
					+ doc.id
					+ "',"
					+ doc.id
					+ ",'"
					+ doc.modifyDate
					+ "')\" role=\"button\" >重命名</a></li> ";
		}

		if (archive_doc.folder.permDelete == true || allPermission) {
			option += "			 <li><a type=\"button\" href=\"javascript:archive_doc.rowDeleteClick('#doc_"
					+ doc.id
					+ "',"
					+ doc.id
					+ ",'"
					+ doc.modifyDate
					+ "')\" role=\"button\" >删除</a></li> ";
		}

		if (archive_doc.folder.permCollect == true || allPermission) {
			option += "			 <li><a type=\"button\" href=\"javascript:archive_doc.controller("
					+ doc.id
					+ ","
					+ doc.currentVersion
					+ ",'"
					+ doc.modifyDate
					+ "');\" role=\"button\" >收藏</a></li> ";
		}
	}

	/*
	 * option += " <li><a type=\"button\" href=\"#associated-Document\"
	 * role=\"button\" data-toggle=\"modal\">锁定</a></li> ";
	 */
	option += "			 <li><a type=\"button\" href=\"javascript:archive_doc.getDocInfo('"
			+ doc.id + "');\" role=\"button\">文档信息</a></li> ";
	if (archive_doc.folder.permView == true || allPermission) {
		var office = (doc.contentType == 1 || doc.contentType == 2 || doc.contentType == 3);
		if (archive_doc.folder.permVersion == true || allPermission) {
			if (office) {
				option += "          <li><a type=\"button\" href=\"javascript:archive_doc.version('"
						+ doc.id + "');\" role=\"button\">版本管理</a></li> ";
			}
		}

		if (archive_doc.folder.permHistory == true || allPermission) {
			option += "          <li><a type=\"button\" href=\"javascript:archive_doc.audithis('"
					+ doc.id + "');\" role=\"button\">文档审计</a></li> ";
		}

		if (archive_doc.folder.permRelate == true || allPermission) {
			option += "          <li><a type=\"button\" href=\"#associated-Document\" role=\"button\" data-toggle=\"modal\" onclick=\"archive_doc.showRelateDiv("
					+ doc.id + ")\">关联文档</a></li>  ";
		}
	}
	option += "        </ul> ";
	option += "    </div> </td>";
	return option;
};

// 生成简单显示单元
archive_doc.createSimpleCell = function(content) {
	if (content === undefined) {
		content = "";
	}
	return "<td>" + content + "</td>";
};

// 生成CheckBox单元
archive_doc.createCheckCell = function(bean) {
	// var id = null;
	// if (bean.dmName != undefined) {
	// id = "#doc_" + bean.id;
	// } else if (bean.folderName != undefined) {
	// id = "#dir_" + bean.id;
	// }
	// return "<td><input type=\"checkbox\" name=\"ids\" value=\"" + id
	// + "\"></td>";
	// 暂时去除Checkbox
	return "";
};

// 创建文件夹名称单元
archive_doc.createDirNameCell = function(bean) {
	var name = bean.folderName;
	var id = "#dir_" + bean.id;
	var cell = null;
	// 文件夹
	var rowType = 2;

	var label = "<label class=\"btn document-con\"";
	label += " onclick=\"archive_doc.loadData(\'";
	label += bean.id;
	label += "','";
	label += rowType;
	label += "','";
	label += "','";
	label += "',true);\">";
	label += '<img width=\"39\" height=\"44\" src=\"../../images/demoimg/document-file.png\"></label>';

	var nameSpan = "<span class=\"file_name\">";
	nameSpan += "<a href=\"javascript:void(0);\" onclick=\"archive_doc.loadData('";
	nameSpan += bean.id;
	nameSpan += "','";
	nameSpan += rowType;
	nameSpan += "','";
	nameSpan += "','";
	nameSpan += "',true);\">";
	nameSpan += name;
	nameSpan += "</a></span>";

	var nameInputSpan = "<span class=\"file_name_input hide\"><input style=\"position:relative;top:5px\" type=\"text\" name=\"folder_name\" onkeydown=\"if(event.keyCode==13){archive_doc.save('"
			+ id + "');}\" maxlength=\"64\"  value=\"";
	nameInputSpan += name;
	nameInputSpan += "\"/><a class=\"a-icon i-new m-l-xs m-r-xs\" type=\"button\" href=\"javascript:archive_doc.save('";
	nameInputSpan += id;
	nameInputSpan += "');\" role=\"button\" >确 定</a><a class=\"a-icon i-new m-r-xs\" type=\"button\" href=\"javascript:archive_doc.cancel('";
	nameInputSpan += id;
	nameInputSpan += "');\" role=\"button\" >取 消</a></span>";
	// <label class="btn i-word"><i class="fa">W99</i></label>

	cell = "<td>";
	cell += label;
	cell += nameSpan;
	cell += nameInputSpan;
	cell += "</td>";

	return cell;
};
archive_doc.alert = function(){
	msgBox.tip({
		content : "没有浏览权限"
	});
}
// 创建文档名称单元
archive_doc.createDocNameCell = function(bean) {
	var name = bean.dmName;
	var id = "#doc_" + bean.id;
	var cell = null;
	var allPermission = bean.createUser == currentUserId;
	var clickFunc = "onclick=\"archive_doc.alert()\"";
	if (archive_doc.folder.permView == true || allPermission) {
		clickFunc = " onclick=\"archive_doc.showDocContent(\'";
		clickFunc += bean.id;
		clickFunc += "');\"";
	}
	var label = "";
	if (bean.contentType == 1) {
		label = '<label class=\"btn document-con\" '+clickFunc+'><img width=\"39\" height=\"44\" src=\"../../images/demoimg/document-word.png\"></label>';
	} else if (bean.contentType == 2) {
		label = '<label class=\"btn document-con\" '+clickFunc+'><img width=\"39\" height=\"44\" src=\"../../images/demoimg/document-ex.png\"></label>';
	} else if (bean.contentType == 3) {
		label = '<label class=\"btn document-con\" '+clickFunc+'><img width=\"39\" height=\"44\" src=\"../../images/demoimg/document-ppt.png\"></label>';
	} else {
		label = '<label class=\"btn document-con\" '+clickFunc+'><img width=\"39\" height=\"44\" src=\"../../images/demoimg/document-text.png\"></label>';
	}

	var nameSpan = "<span class=\"file_name\">";
	if (archive_doc.folder.permView == true || allPermission) {
		nameSpan += "<a href=\"javascript:archive_doc.showDocContent('";
		nameSpan += bean.id;
		nameSpan += "');\">";
		nameSpan += name;
		nameSpan += "</a>";
	} else {
		nameSpan += "<a href=\"javascript:archive_doc.alert();\">";
		nameSpan += name;
		nameSpan += "</a>";
	}
	nameSpan += "</span>";

	var nameInputSpan = "<span class=\"file_name_input hide\"><input style=\"position:relative;top:5px\" type=\"text\" name=\"folder_name\" onkeydown=\"if(event.keyCode==13){archive_doc.save('"
			+ id + "');}\" maxlength=\"64\" value=\"";
	nameInputSpan += name;
	nameInputSpan += "\"/><a class=\"a-icon i-new m-l-xs m-r-xs\" type=\"button\" href=\"javascript:archive_doc.save('";
	nameInputSpan += id;
	nameInputSpan += "');\" role=\"button\" >确 定</a><a class=\"a-icon i-new m-r-xs\" type=\"button\" href=\"javascript:archive_doc.cancel('";
	nameInputSpan += id;
	nameInputSpan += "');\" role=\"button\" >取 消</a></span>";

	cell = "<td>";
	cell += label;
	cell += nameSpan;
	cell += nameInputSpan;
	cell += "</td>";

	return cell;
};
// 插入新目录行
archive_doc.insertDirRow = function(o, editable) {
	var dirRow = "";
	dirRow += ("<tr id=\"dir_" + o.id + "\"  class=\"dir\">");
	dirRow += (archive_doc.createCheckCell(o));
	dirRow += (archive_doc.createDirNameCell(o));
	// dirRow += (archive_doc.createDirOpCell(o));新建文件夹时不显示操作
	dirRow += (archive_doc.createSimpleCell(""));
	dirRow += (archive_doc.createSimpleCell(""));
	dirRow += (archive_doc.createSimpleCell("文件夹"));
	dirRow += (archive_doc.createSimpleCell(""));
	dirRow += (archive_doc.createSimpleCell(o.modifyDate));
	dirRow += (archive_doc.createSimpleCell(o.owner));
	dirRow += "</tr>";

	var firstRow = $('#doc_table > tbody .dir');
	if (firstRow.length > 0) {
		$(dirRow).insertBefore(firstRow[0]);
	} else {
		$('#doc_table > tbody').append(dirRow);
	}

	$('#btnNewFolder').attr("disabled", "disabled");

	if (editable) {
		$("#dir_" + o.id).children('td').children('.file_name').hide();
		$("#dir_" + o.id).children('td').children('.file_name_input').show();
		$("#dir_" + o.id).children('td').children('.file_name_input').children(
				'input').focus();
	} else {
		$("#dir_" + o.id).children('td').children('.file_name').show();
		$("#dir_" + o.id).children('td').children('.file_name_input').hide();
	}
	archive_doc.processEmptyTable();
	$("i[data-toggle='tooltip']").tooltip();
};
// 插入文件行
archive_doc.insertDocRow = function(o, editable) {
	docRow = "";
	docRow += ("<tr id=\"doc_" + o.id + "\" class=\"doc\">");
	docRow += (archive_doc.createCheckCell(o));
	docRow += (archive_doc.createDocNameCell(o));
	docRow += (archive_doc.createDocOpCell(o));
	docRow += (archive_doc.createSimpleCell(o.dmLockStatus == 1 ? "锁定" : '未锁定'));
	// docRow+=(self.createSimpleCell(o.contentType));
	docRow += (archive_doc.createSimpleCell(o.dmSuffix));
	docRow += (archive_doc.createSimpleCell(o.dmSize));
	docRow += (archive_doc.createSimpleCell(o.modifyDate));
	docRow += (archive_doc.createSimpleCell(o.createUser));
	docRow += "</tr>";

	var firstRow = $('#doc_table > tbody .doc').first();
	if (firstRow.length > 0) {
		$(docRow).insertBefore(firstRow);
	} else {
		$('#doc_table > tbody').append(docRow);
	}
	archive_doc.processEmptyTable();
	$("i[data-toggle='tooltip']").tooltip();
};

// ---------表格操作结束---------------------------------------------------------------------------------------------

// 检查是否为锁定文档
archive_doc.checkLock = function(id) {
	/*var lock = $('#doc_' + id).children("td").eq(2).html();
	if ("锁定" == lock) {
		msgBox.tip({
			content : $.i18n.prop("JC_OA_ARCHIVE_015")
		});
		return false;
	}
	return true;*/
	var bol=false;
	$.ajax({
		type : "post",
		cache : false,
		async:false,
		url : getRootPath() + "/archive/document/checkLock.action",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if (data) {
				bol= true;
			} else {
				msgBox.tip({
					content : $.i18n.prop("JC_OA_ARCHIVE_015")
				});
			}
		}
	});
	return bol;
};
// 返回上级目录
archive_doc.goParent = function() {
	$('#btnNewFolder').removeAttr("disabled");
	if (backFlag <= 0) {
		return;
	}

	archive_doc.pathBar.back();
	// $.ajax({
	// type : "GET",
	// url : getRootPath() + "/archive/folder/getParentPubDirDocs.action",
	// data : {
	// id : $('#folderId').val()
	// },
	// dataType : "json",
	// success : function(data) {
	// if (data.success == "true") {
	// $('#doc_table > tbody').empty();
	// $('#folderId').val(data.folder.id);
	// archive_doc.folder=data.folder;
	// backFlag -= 1;
	// archive_doc.render(data.folder);
	// archive_doc.permission(data.folder);
	// } else {
	// if (data.errorMessage) {
	// msgBox.tip({
	// content : data.errorMessage
	// });
	// }
	// }
	// }
	// });
	$('#btnReturn').blur();
};
// 显示上传组件
archive_doc.showUpload = function() {
	$('#fileUpload-edit').modal("show");
};
// 完成文件上传的回调
archive_doc.finishUpload = function() {
	// 取到上传的文件信息//循环插入文件信息行
	var files = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		files.push(o.value);
	});
	// 没有上传文件
	if (files.length == 0) {
		return;
	}

	$.ajax({
		type : "Post",
		url : getRootPath() + "/archive/folder/uploadDocs.action",
		data : {
			'fileids' : files.join(),
			model : 0,
			id : $('#folderId').val()
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage
				});
				$.each(data.documents, function(i, d) {
					archive_doc.insertDocRow(d, false);
				});
				clearTable();
				archive_doc.loadData(archive_doc.getFolderId());
			} else {
				if (data.errorMessage) {
					msgBox.tip({
						content : data.errorMessage
					});
				}
			}
		}
	});
};
// 取消或点关闭删除上传的文件
archive_doc.deleteAttach = function() {
	var ids = "";
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	if (fileids == null) {
		return;
	}
	ids = fileids.join(",");
	$.ajax({
		type : "POST",
		url : getRootPath() + "/content/attach/delete.action",
		data : {
			"ids" : ids
		},
		dataType : "json",
		success : function(data) {

		}
	});
};
// 新建文件夹插入新行
archive_doc.insertNewRow = function() {
	// getToken();
	var o = {
		folderName : '',
		id : 0,
		modifyDate : '',
		createUser : ''
	};
	archive_doc.insertDirRow(o, true);
};

// 重命名文件/文件夹名称
archive_doc.rename = function(rowId, id, modifyDate) {
	if (id && !archive_doc.checkLock(id)) {
		return;
	}
	archive_doc.modifyDate = modifyDate;
	archive_doc.checkDate();
	// 取消其它行的编辑状态
	var editing = $('tr >td > .file_name:hidden');
	if (editing != "undefined" && editing.length > 0) {
		$.each(editing, function(i, ele) {
			archive_doc.cancel('#' + $(ele).closest('tr').attr('id'));
		});
	}

	$(rowId).children('td').children('.file_name').hide();
	$(rowId).children('td').children('.file_name_input').show();
	$(rowId).children('td').children('.file_name_input').children('input')
			.select();
};

// 收藏
archive_doc.controller = function(id, currentVersion) {
	if (id && !archive_doc.checkLock(id)) {
		return;
	}
	$
			.ajax({
				type : "Post",
				url : getRootPath()
						+ "/archive/document/selectCollection.action",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox
								.confirm({
									content : $.i18n.prop("JC_OA_ARCHIVE_025"),
									success : function() {
										$
												.ajax({
													type : "Post",
													url : getRootPath()
															+ "/archive/document/updateCollection.action",
													data : {
														"id" : id,
														"currentVersion" : currentVersion
													},
													dataType : "json",
													success : function(data) {
														if (data.success == "true") {
															msgBox
																	.tip({
																		type : "success",
																		content : data.successMessage
																	});
														} else {
															msgBox
																	.info({
																		content : data.errorMessage
																	});
														}
													}
												});
									}
								});
					} else {
						if (data.success == "error") {
							msgBox.tip({
								content : data.successMessage
							});
						} else {
							$
									.ajax({
										type : "Post",
										url : getRootPath()
												+ "/archive/document/insertCollection.action",
										data : {
											"id" : id
										},
										dataType : "json",
										success : function(data) {
											if (data.success == "true") {
												msgBox
														.tip({
															type : "success",
															content : data.successMessage
														});
											} else {
													msgBox
															.info({
																content : data.errorMessage
															});
											}
										}
									});
						}
					}
				}
			});
};

// 新建、修改名称之后的保存操作
archive_doc.save = function(id) {

	var file_name_node = $(id).children('td').children('.file_name_input')
			.children('input');
	$(id).children('td').children('.file_name_input').children('label[for]')
			.remove();
	var formId = "form";
	file_name_node.wrap("<form id=\"form\"></form>");
	archive_doc.bindingFolderValidator();
	// 验证文件/文件夹名通过后，提交保存
	if ($('#' + formId).valid()) {
		file_name_node.unwrap();
		var url = getRootPath() + "/archive";
		var data = {};
		// 保存文件夹名
		if (id.indexOf('dir') > -1) {
			url += "/folder";
			data = {
				"folderName" : $(id).children('td')
						.children('.file_name_input').children('input').val(),
				"parentFolderId" : $('#folderId').val(),
				"folderType" : 0,
				"kmAppFlag" : 0,
				"modifyDate": archive_doc.modifyDate,
				"token" : $('#token').val()
			};
			if (id.substr(5) == '0') {
				url += "/save.action";
			} else {
				url += "/update.action";

				data.id = id.substr(5);
			}

		}
		// 保存文件名
		else if (id.indexOf('doc') > -1) {
			url += "/document";
			data = {
				"dmName" : $(id).children('td').children('.file_name_input')
						.children('input').val(),
				"folderId" : $('#folderId').val(),
				"fileType" : 0,
				"token" : $('#token').val()
			};

			url += "/update.action";
			data.id = id.substr(5);

		} else {
			// error
		}
		data.model = 0;
		$("#dataLoad").show();
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			dataType : "json",
			success : function(data) {
				$('#token').val(data.token);
				if (data.success == "true") {
					msgBox.tip({
						type : "success",
						content : data.successMessage
					});

					// 如果是新建操作的保存，应该取消新建文件夹按钮的禁用状态，并且重设RowId
					if (id == '#dir_0') {
						$('#btnNewFolder').removeAttr("disabled");
						// $(id).remove();
						// archive_doc.insertDirRow(data.folder);
					} else {
						$(id).children('td').children('.file_name').children(
								'a').html(
								$(id).children('td').children(
										'.file_name_input').children('input')
										.val());
						$(id).children('td').children('.file_name').show();
						$(id).children('td').children('.file_name_input')
								.hide();
					}

					$('#btnNewFolder').removeAttr("disabled");
					archive_doc.reloadData();
					archive_doc.processEmptyTable();
				} else {
					if (data.success == "error") {
						msgBox.tip({
							content : data.successMessage
						});
					}
					if (data.labelErrorMessage) {
						msgBox.tip({
							content : data.labelErrorMessage
						});
					}
					if (data.errorMessage) {
						msgBox.tip({
							content : data.errorMessage
						});
					}

				}
				// alert($('#token').val());
				$("#dataLoad").hide();
				// getToken();
			},
			error : function() {
				msgBox.tip({
					content : $.i18n.prop("JC_SYS_002")
				});
				$("#dataLoad").hide();
			}
		});
	} else {
		file_name_node.unwrap();
	}
};
// 取消修改、取消新建
archive_doc.cancel = function(id) {
	$(id).children('td').children('.file_name').show();
	$(id).children('td').children('.file_name_input').hide();
	$(id).children('td').children('.file_name_input').children('input').val(
			$(id).children('td').children('.file_name').children("a").html());
	// 如果是新建操作的取消，应该取消新建文件夹按钮的禁用状态，并且Remove当前行
	if (id == '#dir_0') {
		$('#btnNewFolder').removeAttr("disabled");
		$(id).remove();
	}
	archive_doc.reloadData();
};

// 显示授权窗口
archive_doc.auth = function(folerId) {
	$.ajax({
		type : "post",
		cache : false,
		url : getRootPath() + "/archive/folder/selectDir.action",
		data : {
			"id" : folerId
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				$('#folder').val(folerId);
				// archive_doc.oTable = null;
				$('#authority-management').modal('show');
				// 分页查询方法
				archive_doc.initList();
			} else {
				msgBox.info({
					content : data.errorMessage
				});
			}
		}
	});
};

// 显示授权窗口
archive_doc.authTop = function() {
	archive_doc.auth($('#folderId').val());
};

// 修改组织人员权限列表
archive_doc.get = function(id, permissionValue, num) {
	if (archive_doc.subState)
		return;
	if (num != 1 && $('#' + id + 1).attr('mark') == 0) {
		msgBox.tip({
			content : "请先设置浏览权限"
		});
		return;
	}
	archive_doc.subState = true;
	var mark = $('#' + id + num).attr('mark');
	/*
	 * if(mark == 1) { permissionValue= 0; }else { permissionValue= 1; }
	 */
	permissionValue = mark;
	$.ajax({
		type : "post",
		cache : false,
		url : getRootPath() + "/archive/permission/batchUpdate.action",
		data : {
			"id" : id,
			"permissionValue" : permissionValue,
			"num" : num,
			"forderId" : $('#folder').val()
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				// 修改权限回调方法
				// archive_doc.initList();
				// archive_doc.permission();
				if (num == 1) {
					if ($('#' + id + num).attr('mark') == 1) {
						for (var i = 1; i <= 10; i++) {
							$('#' + id + i).attr('mark', 0);
							$('#' + id + i).html(
									"<i class='fa fa-remove text-red'></i>");
						}
					} else {
						for (var j = 1; j <= 4; j++) {
							$('#' + id + j).html(
									"<i class='fa fa-ok text-green'></i>");
							$('#' + id + j).attr('mark', 1);
						}
					}
				} else {
					if ($('#' + id + num).attr('mark') == 1) {
						$('#' + id + num).attr('mark', 0);
						$('#' + id + num).html(
								"<i class='fa fa-remove text-red'></i>");
					} else {
						$('#' + id + num).html(
								"<i class='fa fa-ok text-green'></i>");
						$('#' + id + num).attr('mark', 1);
					}
				}
				archive_doc.reloadData();
			} else {
				if (data.success == "error") {
					msgBox.tip({
						content : data.successMessage
					});
				} else {
					msgBox.tip({
						content : data.errorMessage
					});
				}
			}
			archive_doc.subState = false;
		}
	});
};

// 显示列信息
archive_doc.oTableAoColumns = [
		{
			mData : "userName"
		},// 组织人员名
		{
			mData : function(source) {
				var upPermission = source.permView == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permView + "' id='" + source.id
						+ "1' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permView + ",1);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permNewUpDown == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permNewUpDown + "' id='"
						+ source.id + "2' herf='#' onclick='archive_doc.get("
						+ source.id + "," + source.permNewUpDown + ",2);'>"
						+ upPermission + "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permEdit == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permEdit + "' id='" + source.id
						+ "3' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permEdit + ",3);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permDelete == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permDelete + "' id='" + source.id
						+ "4' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permDelete + ",4);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permCopyPaste == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permCopyPaste + "' id='"
						+ source.id + "5' herf='#' onclick='archive_doc.get("
						+ source.id + "," + source.permCopyPaste + ",5);'>"
						+ upPermission + "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permRename == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permRename + "' id='" + source.id
						+ "6' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permRename + ",6);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permCollect == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permCollect + "' id='" + source.id
						+ "7' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permCollect + ",7);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permVersion == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permVersion + "' id='" + source.id
						+ "8' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permVersion + ",8);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permHistory == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permHistory + "' id='" + source.id
						+ "9' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permHistory + ",9);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				var upPermission = source.permRelate == 1 ? "<i class='fa fa-ok text-green'></i>"
						: "<i class='fa fa-remove text-red'></i>";
				return "<a mark='" + source.permRelate + "' id='" + source.id
						+ "10' herf='#' onclick='archive_doc.get(" + source.id
						+ "," + source.permRelate + ",10);'>" + upPermission
						+ "</a>";
			}
		},
		{
			mData : function(source) {
				return "<a class='a-icon i-edit m-r-xs' href='javascript:void(0)' onclick='archive_doc.updatePermission("
						+ source.id
						+ ")' role='button' data-toggle='modal'><i class='fa fa-edit2' data-toggle='tooltip' data-placement='top' title='' data-container='body' data-original-title='编辑'></i></a>"
						+ "<a class='a-icon i-remove m-r-xs' href='javascript:void(0)' onclick='archive_doc.batchDelete("
						+ source.id
						+ ")' role='button' data-toggle='modal'><i class='fa fa-remove' data-toggle='tooltip' data-placement='top' data-container='body' data-original-title='删除'></i></a>";
			}
		} // 操作
];

// 组装后台参数
archive_doc.oTableFnServerParams = function(aoData) {
	// 排序条件
	getTableParameters(archive_doc.oTable, aoData);
	aoData.push({
		"name" : "folderId",
		"value" : $('#folder').val()
	});
};

// 分页查询方法
archive_doc.initList = function() {
	if (archive_doc.oTable == null) {
		archive_doc.oTable = $('#permission').dataTable(
				{
					bDestroy : true,
					"iDisplayLength" : 5,// 每页显示多少条记录
					"sAjaxSource" : getRootPath()
							+ "/archive/permission/manageList.action",// 后台分页查询地址url
					"fnServerData" : oTableRetrieveData,// 查询数据回调函数
					"aoColumns" : archive_doc.oTableAoColumns,// table显示列
					// 传参
					"fnServerParams" : function(aoData) {						
						archive_doc.oTableFnServerParams(aoData);
					},
					"fnDrawCallback" :function(){
						$('#authority-management').modal('setPaddingTop');
					},
					// 默认不排序列
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ]
					} ]
				});
	} else {
		// table不为null时重绘表格
		archive_doc.oTable.fnDraw();
	}
};

// 删除组织人员权限
archive_doc.batchDelete = function(id) {
	msgBox.confirm({
		content : $.i18n.prop("JC_SYS_034"),
		success : function() {
			$.ajax({
				type : "post",
				url : getRootPath() + "/archive/permission/batchDelete.action",
				data : {
					"id" : id,
					"forderId" : $('#folder').val()
				},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage
						});
						// 修改权限回调方法
						archive_doc.initList();
						// archive_doc.permission();
						archive_doc.reloadData();
					} else {
						if (data.success == "error") {
							msgBox.tip({
								content : data.successMessage
							});
						} else {
							msgBox.tip({
								content : data.errorMessage
							});
						}
					}
				}
			});
		}
	});
};

// 修改组织人员权限
archive_doc.updatePermission = function(id) {
	archive_doc.permissionId = id;
	$.ajax({
		type : "post",
		url : getRootPath() + "/archive/permission/updatePermission.action",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				var aa = "[";
				for (var i = 0; i < data.subPermission.length; i++) {
					aa += "{id:" + data.subPermission[i].controlId + ",text:'"
							+ data.subPermission[i].controlName + "',type:"
							+ data.subPermission[i].dataType + "},";
				}
				aa = aa.substring(0, aa.length - 1) + "]";
				var subPermission = aa;
				var opts5 = {
					echoData : eval(subPermission), // 回显数据,没有回显数据可以不写
					callbackFunction : updatePersonCall
				// 回调函数
				};
				$.fn.deptAndPersonControl.openDeptAndPerson(opts5);
			} else {
				if(data.success == "error"){
					msgBox.tip({
						content : data.successMessage
					});
				}else{
				msgBox.tip({
					content : data.errorMessage
				});
			}
			}
		}
	});
};

// 添加权限
archive_doc.addBtn = function() {
	var opts4 = {
		callbackFunction : deptPersonCall
	// 回调函数
	};
	$.fn.deptAndPersonControl.openDeptAndPerson(opts4);
};

// 修改组织人员权限
function updatePersonCall(data) {
	var permissionId = archive_doc.permissionId;
	var id = "";
	var text = "";
	var type = "";
	$.each(data, function(i, val) {
		id += val.id + ",";
		text += val.text + ",";
		type += val.type + ",";
	});
	if (id == null || id.length < 1) {
		$.ajax({
			type : "post",
			url : getRootPath() + "/archive/permission/batchDelete.action",
			data : {
				"id" : permissionId,
				"forderId" : $('#folder').val()
			},
			dataType : "json",
			success : function(data) {
				if (data.success == "true") {
					// 修改权限回调方法
					archive_doc.initList();
					// archive_doc.permission();
					archive_doc.reloadData();
				} else {
				}
			}
		});
		return;
	}
	$.ajax({
		type : "post",
		url : getRootPath() + "/archive/permission/batchPermission.action",
		data : {
			"permissionId" : permissionId,
			"id" : id,
			"text" : text,
			"type" : type,
			"forderId" : $('#folder').val()
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage
				});
				// 修改权限回调方法
				archive_doc.initList();
				// archive_doc.permission();
				archive_doc.reloadData();
			} else {
				if (data.success == "error") {
					msgBox.tip({
						content : data.successMessage
					});
				} else {
					msgBox.tip({
						content : data.errorMessage
					});
				}
			}
		}
	});
}

// 添加组织人员权限
function deptPersonCall(data) {
	var id = "";
	var text = "";
	var type = "";
	$.each(data, function(i, val) {
		id += val.id + ",";
		text += val.text + ",";
		type += val.type + ",";
	});
	if (id == null || id.length < 1) {
		return;
	}
	$.ajax({
		type : "post",
		url : getRootPath() + "/archive/permission/batchInsert.action",
		data : {
			"folderId" : $('#folder').val(),
			"id" : id,
			"text" : text,
			"type" : type,
			"forderId" : $('#folder').val()
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage
				});
				// 修改权限回调方法
				archive_doc.initList();
				// archive_doc.permission();
				archive_doc.reloadData();
			} else {
				if (data.success == "error")
					msgBox.tip({
						content : data.successMessage
					});
				else {
					msgBox.tip({
						content : data.errorMessage
					});
				}
			}
		}
	});
}

// 删除方法
archive_doc.deleteClick = function() {
	var idsArr = [];
	$("[name='ids']:checked").each(function() {
		idsArr.push($(this).val());
	});
	var ids = idsArr.join(",");

	if (ids.length == 0) {
		msgBox.tip({
			content : $.i18n.prop("JC_SYS_061"),
			type : "fail"
		});
		return;
	}

	msgBox.confirm({
		content : $.i18n.prop("JC_SYS_034"),
		success : function() {
			archive_doc.deleteDirDoc(idsArr);
		}
	});
};
archive_doc.rowDeleteClick = function(rowId, id) {
	if (id && !archive_doc.checkLock(id)) {
		return;
	}
	var idsArr = [];
	idsArr.push(rowId);
	var ids = idsArr.join(",");

	if (ids.length == 0) {
		msgBox.tip({
			content : $.i18n.prop("JC_SYS_061"),
			type : "fail"
		});
		return;
	}

	msgBox.confirm({
		content : $.i18n.prop("JC_SYS_034"),
		success : function() {
			archive_doc.deleteDirDoc(idsArr);
		}
	});
};
archive_doc.deleteDirDoc = function(idsArr) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/archive/folder/delDirDocs.action",
		data : {
			'ids' : idsArr.join(",")
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					content : data.successMessage,
					type : "success"
				});
				$.each(idsArr, function(i, rowid) {
					$(rowid).remove();
				});
				archive_doc.processEmptyTable();
				archive_doc.reloadData();
			} else {
				if (data.labelErrorMessage) {
					msgBox.tip({
						content : data.labelErrorMessage
					});
				}
				if (data.errorMessage) {
					msgBox.tip({
						content : data.errorMessage
					});
				}

			}
		},
		error : function() {
			msgBox.tip({
				content : $.i18n.prop("JC_SYS_002")
			});
		}
	});
};

archive_doc.currentDMName = '';
archive_doc.editDoc = function(contentType, path, name, id, dmLockStatus) {
	if (!archive_doc.checkLock(id)) {
		return;
	}
	$.ajax({
		type : "post",
		url : getRootPath() + "/archive/folder/checkDocFileExist.action",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if (data == true) {
				archive_doc.currentDMName = name;
				archive_doc.currentDocumentId = id;
				/*
				 * if("1" == dmLockStatus) { msgBox.tip({ content :
				 * $.i18n.prop("JC_OA_ARCHIVE_015") }); return; }
				 */
				if (contentType == 1 || contentType == 2 || contentType == 3) {
					archive_doc.viewFile(contentType, 1, base64.decode(path));
					// word
					// ppt
					// exl
				} else {
					// 其他
				}
			} else {
				msgBox.tip({
					content : $.i18n.prop("JC_OA_ARCHIVE_035")
				});
			}
		}
	});
};
// 下载文档
archive_doc.download = function(id) {
	if (archive_doc.checkLock(id)) {
		$.ajax({
			type : "post",
			url : getRootPath() + "/archive/folder/checkDocFileExist.action",
			data : {
				"id" : id
			},
			dataType : "json",
			success : function(data) {
				if (data == true) {
					window.open(getRootPath()
							+ "/archive/folder/download.action?id=" + id,
							"下载文档");
				} else {
					msgBox.tip({
						content : $.i18n.prop("JC_OA_ARCHIVE_035")
					});
				}
			}
		});
	}

};
// 显示文档
archive_doc.showDocContent = function(id) {
	if (archive_doc.checkLock(id)) {
		$.ajax({
			type : "post",
			url : getRootPath() + "/archive/folder/checkDocFileExist.action",
			data : {
				"id" : id
			},
			dataType : "json",
			success : function(data) {
				if (data == true) {
					window.open(getRootPath()
							+ "/archive/folder/showDocContent.action?id=" + id,
							"查看文档");
				} else {
					msgBox.tip({
						content : $.i18n.prop("JC_OA_ARCHIVE_035")
					});
				}
			}
		});
	}
};

// 页面搜索
archive_doc.onFilter = function(ev) {
	var tbody = $('#doc_table > tbody');
	tbody.children('.empty_row').remove();
	var filterField = jQuery(this);
	var contacts = $('#doc_table');
	var input = filterField.val();

	var names = contacts.find("tr .file_name");
	var bool = 0;
	names.each(function(i, node) {
		var el = jQuery(node);
		var name = el.text();

		var match = name.indexOf(input) >= 0;
		if (match == true) {
			bool = 1;
		}
		var contact = el.closest("tr");
		if (match) {
			contact.show();
		} else {
			contact.hide();
		}
	});
	if (bool == 0) {
		tbody.append(archive_doc.createEmptyRow());
	}
};

/**
 * 绑定文件夹行的验证器 文件夹名称：不能为空，最大长度为64
 */
archive_doc.bindingFolderValidator = function() {
	$('#form').validate({
		ignore : ".ignore",
		rules : {
			folder_name : {
				required : true,
				maxlength : 64,
				fileName : true
			}
		}
	});
};

archive_doc.newWord = function(flag) {
	archive_doc.documentType = 'doc';
	var url = getRootPath()
			+ "/archive/folder/newDocument.action?documentType=doc";
	url += ("&documentPath=" + archive_doc.documentPath);
	url += ("&folderId=" + archive_doc.getFolderId());
	url += ("&currentDocumentId=" + archive_doc.currentDocumentId);
	url += ("&model=" + archive_doc.model);
	window.open(encodeURI(url));
};
archive_doc.newExl = function() {
	archive_doc.documentType = 'xls';
	var url = getRootPath()
			+ "/archive/folder/newDocument.action?documentType=xls";
	url += ("&documentPath=" + archive_doc.documentPath);
	url += ("&folderId=" + archive_doc.getFolderId());
	url += ("&currentDocumentId=" + archive_doc.currentDocumentId);
	url += ("&model=" + archive_doc.model);
	window.open(encodeURI(url));
};
archive_doc.newPPT = function() {
	archive_doc.documentType = 'ppt';
	var url = getRootPath()
			+ "/archive/folder/newDocument.action?documentType=ppt";
	url += ("&documentPath=" + archive_doc.documentPath);
	url += ("&folderId=" + archive_doc.getFolderId());
	url += ("&currentDocumentId=" + archive_doc.currentDocumentId);
	url += ("&model=" + archive_doc.model);
	window.open(encodeURI(url));
};

// 权限控制
archive_doc.permission = function(folder) {
	if (folder.permView == 0) {
		$("#upload").addClass("hide");
		$("#btnNew").addClass("hide");
	} else if (folder.permNewUpDown == 0) {
		$("#upload").addClass("hide");
		$("#btnNew").addClass("hide");
	} else {
		$("#upload").removeClass("hide");
		$("#btnNew").removeClass("hide");
	}
};
archive_doc.checkDate = function(){
	if(archive_doc.lockDate) {
		archive_doc.modifyDate = archive_doc.lockDate; 
		archive_doc.lockDate = null;
	}
}
/**
 * 复制点击事件
 */
archive_doc.clickCopy = function(rowId, id,modifyDate) {
	// 复制的是文档
	/*alert(rowId);
	alert(id);
	alert(modifyDate);*/
	archive_doc.modifyDate = modifyDate;
	archive_doc.checkDate();
	archive_doc.currentSelectedRowId = rowId;
	if (rowId.indexOf('dir') > 0) {
		archive_doc.folderSelector.setCallback(archive_doc.copyDirTo);
	} else {
		if (id && !archive_doc.checkLock(id)) {
			return;
		}
		archive_doc.folderSelector.setCallback(archive_doc.copyDocTo);
	}
	archive_doc.folderSelector.show();
};

/**
 * 文件夹：复制到 文件夹选择树回调函数
 */
archive_doc.copyDirTo = function(id) {
	if (archive_doc.currentSelectedRowId == null) {
		msgBox.tip({
			content : $.i18n.prop("JC_SYS_060")
		});
	}
	$.ajax({
		type : "GET",
		url : getRootPath() + "/archive/folder/copyDirTo.action",
		data : {
			id : archive_doc.currentSelectedRowId.substr(5),
			modifyDate: archive_doc.modifyDate,
			parentFolderId : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage,
					callback : archive_doc.reloadData()
				});
			} else {
				if (data.success == "error") {
					msgBox.info({
						content : data.errorMessage
					});
				} else if (data.errorMessage) {
					msgBox.info({
						content : data.errorMessage,
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				} else {
					msgBox.info({
						content : $.i18n.prop("JC_SYS_060"),
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				}
			}
		}
	});
};

/**
 * 文档：复制到 文件夹选择树回调函数
 */
archive_doc.copyDocTo = function(id) {
	if (archive_doc.currentSelectedRowId == null) {
		msgBox.tip({
			content : $.i18n.prop("JC_SYS_060")
		});
	}
	$.ajax({
		type : "GET",
		cache:false,
		url : getRootPath() + "/archive/folder/copyDocTo.action",
		data : {
			id : archive_doc.currentSelectedRowId.substr(5),
			modifyDate: archive_doc.modifyDate,
			folderId : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage,
					callback : archive_doc.reloadData()
				});

			} else {
				if(data.success == "error"){
					msgBox.info({
						content : data.errorMessage,
					});
				}else
				if (data.errorMessage) {
					msgBox.info({
						content : data.errorMessage,
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				} else {
					msgBox.info({
						content : $.i18n.prop("JC_SYS_060"),
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				}
			}
		}
	});
};

/**
 * 剪切点击事件
 */
archive_doc.clickCut = function(rowId, id, modifyDate) {
	archive_doc.modifyDate = modifyDate;
	archive_doc.checkDate();
	archive_doc.currentSelectedRowId = rowId;
	if (rowId.indexOf('dir') > 0) {
		archive_doc.folderSelector.setCallback(archive_doc.cutDirTo);
	} else {
		if (id && !archive_doc.checkLock(id)) {
			return;
		}
		archive_doc.folderSelector.setCallback(archive_doc.cutDocTo);
	}
	archive_doc.folderSelector.show();
};

/**
 * 文件夹：剪切到 文件夹选择树回调函数
 */
archive_doc.cutDirTo = function(id) {
	if (archive_doc.currentSelectedRowId == null) {
		msgBox.tip({
			content : $.i18n.prop("JC_SYS_060")
		});
	}
	$.ajax({
		type : "GET",
		url : getRootPath() + "/archive/folder/cutDirTo.action",
		data : {
			id : archive_doc.currentSelectedRowId.substr(5),
			folderType:0,
			modifyDate: archive_doc.modifyDate,
			parentFolderId : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage,
					callback : archive_doc.reloadData()
				});
				;
			} else {
				if (data.success == "error") {
					msgBox.info({
						content : data.successMessage
					});
				} else if (data.errorMessage) {
					msgBox.info({
						content : data.errorMessage,
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				} else {
					msgBox.info({
						content : $.i18n.prop("JC_SYS_060"),
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				}
			}
		}
	});
};

/**
 * 文档：剪切到 文件夹选择树回调函数
 */
archive_doc.cutDocTo = function(id) {
	if (archive_doc.currentSelectedRowId == null) {
		msgBox.tip({
			content : $.i18n.prop("JC_SYS_060")
		});
	}
	$.ajax({
		type : "GET",
		url : getRootPath() + "/archive/folder/cutDocTo.action",
		data : {
			modifyDate: archive_doc.modifyDate,
			id : archive_doc.currentSelectedRowId.substr(5),
			folderId : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				// $(archive_doc.currentSelectedRowId).remove();
				msgBox.tip({
					type : "success",
					content : data.successMessage,
					callback : archive_doc.reloadData()
				});
				;
			} else {
				if (data.success == "error") {
					msgBox.info({
						content : data.successMessage
					});
				} else
				if (data.errorMessage) {
					msgBox.info({
						content : data.errorMessage,
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				} else {
					msgBox.info({
						content : $.i18n.prop("JC_SYS_060"),
						callback : function() {
							archive_doc.currentSelectedRowId = null;
						}
					});
				}
			}
		}
	});
};

/**
 * 文档信息
 */
archive_doc.getDocInfo = function(id) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/archive/document/selectDoc.action",
		data : {
			id : id,
			folderId :$("#folderId").val(),
			t : (new Date()).getTime()
		},
		dataType : "json",
		success : function(data) {
			if(data.success =="true"){
			//if (data != null) {
				var selectDoc=data.document;
				var lock = '';
				if (selectDoc.dmLockStatus == 1) {
					lock = "锁定" + lockButton(selectDoc);
				} else {
					lock = "未锁定" + lockButton(selectDoc);
				}

				$('#info_dmName').html(selectDoc.dmName);
				$('#info_dmSize').html(selectDoc.dmSize);
				$('#info_createDate').html(selectDoc.createDate);
				$('#info_dmDir').html(selectDoc.dmDir);
				$('#info_dmName').html(selectDoc.dmName);
				$('#info_currentVersion').html(selectDoc.maxVersion);
				$('#info_modifyDate').html(selectDoc.modifyDate);
				$('#info_seq').html(selectDoc.seq);
				$('#info_createUser').html(selectDoc.owner);
				$('#info_dmLockStatus').html(lock);
				$('#info_keyWords').html(selectDoc.keyWords);
				$('#info_dmType').html(selectDoc.dmType);
				$('#document-information').modal('show');
			} else {
				if(data.success == "error"){
					msgBox.tip({
						content : data.successMessage
					});
				}else
				if (data.errorMessage) {
					msgBox.tip({
						content : data.errorMessage
					});
				} else {
					msgBox.tip({
						content : $.i18n.prop("JC_SYS_060")
					});
				}
			}
		}
	});
};

/**
 * 文档锁定
 */
archive_doc.lockDoc = function(id) {
	$
			.ajax({
				type : "GET",
				url : getRootPath() + "/archive/document/lock.action",
				data : {
					id : id,
					dmLockStatus : 1
				},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						archive_doc.lockDate = data.modifyDate;
						var unlock = '锁定<a href="javascript:archive_doc.unlockDoc('
								+ id
								+ ');" class="a-icon i-new fr"><i class=""';
						unlock += '	 data-original-title="锁定" data-container="body" title=""';
						unlock += ' data-placement="top" data-toggle="tooltip"></i>解除锁定</a>';

						$('#info_dmLockStatus').html(unlock);
						$('#doc_' + id).children("td").eq(2).html("锁定");
						msgBox.tip({
							type : "success",
							content : data.successMessage
						});
					} else {
						if (data.errorMessage) {
							msgBox.tip({
								content : data.errorMessage
							});
						} else {
							msgBox.tip({
								content : $.i18n.prop("JC_SYS_060")
							});
						}
					}
				}
			});
};

/**
 * 文档解锁
 */
archive_doc.unlockDoc = function(id) {
	$
			.ajax({
				type : "GET",
				url : getRootPath() + "/archive/document/lock.action",
				data : {
					id : id,
					dmLockStatus : 0,
					t : (new Date()).getTime()
				},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						archive_doc.lockDate = data.modifyDate;
						var lock = '未锁定<a href="javascript:archive_doc.lockDoc('
								+ id
								+ ');" class="a-icon i-new fr"><i class="" data-original-title="锁定"';
						lock += '	data-container="body" title="" data-placement="top"';
						lock += ' data-toggle="tooltip"></i>锁定</a>';
						$('#info_dmLockStatus').html(lock);
						$('#doc_' + id).children("td").eq(2).html("未锁定");
						msgBox.tip({
							type : "success",
							content : data.successMessage
						});
					} else {
						if (data.errorMessage) {
							msgBox.tip({
								content : data.errorMessage
							});
						} else {
							msgBox.tip({
								content : $.i18n.prop("JC_SYS_060")
							});
						}
					}
				}
			});
};

// 关联关系 start
// 关联文档页对象
archive_doc.relateTable = null;
archive_doc.relateFnServerParams = function(aoData) {
	getTableParameters(archive_doc.relateTable, aoData);
	aoData.push({
		"name" : "documentId",
		"value" : $('#pubDocRelateForm #id').val()
	});
};
archive_doc.check = function(dmId) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/archive/document/check.action",
		data : {
			id : dmId
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				archive_doc.showDocContent(dmId);
			} else {
				if (data.errorMessage) {
					msgBox.tip({
						content : data.errorMessage
					});
				} else {
					msgBox.tip({
						content : $.i18n.prop("JC_SYS_060")
					});
				}
			}
		}
	});
};
// 显示列信息
archive_doc.relateColumns = [
		{
			"mData" : function(source) {
				return "<a type=\"button\" href=\"javascript:archive_doc.check('"
						+ source.dmId
						+ "')\" role=\"button\" >"
						+ source.dmName + "." + source.dmSuffix + "</a>";
			}
		},
		{
			"mData" : function(source) {
				return source.dmDir;
			}
		},
		{
			"mData" : null,
			"mRender" : function(mData, type, full) {
				return "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"archive_doc.deleteRelate("
						+ full.id
						+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
			}
		} ];
archive_doc.relateList = function() {
	if (archive_doc.relateTable == null) {
		archive_doc.relateTable = $('#pubDocRelateTable').dataTable(
				{
					"iDisplayLength" : 5,// 每页显示多少条记录
					"sAjaxSource" : getRootPath()
							+ "/archive/relate/manageList.action",
					"fnServerData" : oTableRetrieveData,// 查询数据回调函数
					"aoColumns" : archive_doc.relateColumns,// table显示列
					"fnServerParams" : function(aoData) {// 传参
						archive_doc.relateFnServerParams(aoData);
					},
					aaSorting : [],// 设置表格默认排序列
					// 默认不排序列
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0, 1, 2 ]
					} ]
				});
	} else {
		archive_doc.relateTable.fnDraw();
	}
};
// 显示信息
archive_doc.showRelateDiv = function(id) {
	if (id && !archive_doc.checkLock(id)) {
		return;
	}
	$.ajax({
		type : "post",
		cache : false,
		async:false,
		url : getRootPath() + "/archive/document/checkDocExist.action",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if (data) {
				msgBox.tip({
					content : $.i18n.prop("JC_OA_ARCHIVE_035")
				});
			} else {
				$('#pubDoc-relate').modal('show');
				$('#pubDocRelateForm #id').val(id);
				archive_doc.relateList();
			}
		}
	});
	
};

// 选择关联文档页对象
archive_doc.relateForDocTable = null;
archive_doc.relateForDocFnServerParams = function(aoData) {
	getTableParameters(archive_doc.relateForDocTable, aoData);
	$.each($("#queryRelateForDocForm").serializeArray(), function(i, o) {
		if (o.value != "") {
			aoData.push({
				"name" : o.name,
				"value" : o.value
			});
		}
	});
	aoData.push({
		"name" : "model",
		"value" : 0
	});// 公共文档
	aoData.push({
		"name" : "id",
		"value" : $('#pubDocRelateForm #id').val()
	});
};

// 显示列信息
archive_doc.relateForDocColumns = [
		{
			mData : function(source) {
				return "<input type=\"checkbox\" name=\"documentIds\" value="
						+ source.id + ">";
			}
		}, {
			"mData" : "dmName"
		}, {
			"mData" : "seq"
		}, {
			"mData" : "owner"
		}, {
			"mData" : "createDate"
		} ];
archive_doc.relateForDocList = function() {
	if (archive_doc.relateForDocTable == null) {
		archive_doc.relateForDocTable = $('#relateForDocTable').dataTable(
				{
					"iDisplayLength" : 5,// 每页显示多少条记录
					"sAjaxSource" : getRootPath()
							+ "/archive/document/manageByRelateList.action",// 不包含已经关联的文档
					"fnServerData" : oTableRetrieveData,// 查询数据回调函数
					"aoColumns" : archive_doc.relateForDocColumns,// table显示列
					"fnServerParams" : function(aoData) {// 传参
						archive_doc.relateForDocFnServerParams(aoData);
					},
					aaSorting : [],// 设置表格默认排序列
					// 默认不排序列
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0, 1, 2, 3 ]
					} ]
				});
	} else {
		archive_doc.relateForDocTable.fnDraw();
	}
};

archive_doc.resetRelateForDoc = function() {
	$('#queryRelateForDocForm')[0].reset();
	selectControl.clearValue("createUser-createUser");
};

// 显示信息
archive_doc.showRelateForDocDiv = function(id) {
	$('#relate-doc').modal('show');
	archive_doc.resetRelateForDoc();
	archive_doc.relateForDocList();
};

// 确认选择的关联文档
archive_doc.saveRelateForDoc = function() {
	var idsArr = [];
	var id = $('#pubDocRelateForm #id').val();
	$("[name='documentIds']:checked").each(function() {
		idsArr.push($(this).val());
	});
	ids = idsArr.join(",");
	if (ids.length == 0) {
		msgBox.tip({
			content : $.i18n.prop("JC_SYS_029", "要关联的文档")
		});
		return;
	}
	if (id && !archive_doc.checkLock(id)) {
		return;
	}
	$.ajax({
		type : "POST",
		url : getRootPath() + "/archive/relate/saveByDocumentIds.action",
		data : {
			"documentIds" : ids,
			"documentId" : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage
				});
				$('#relate-doc').modal('hide');
				archive_doc.relateList();
			} else {
				msgBox.tip({
					content : data.errorMessage
				});
			}
		}
	});
};
// 删除选择的关联文档
archive_doc.deleteRelate = function(id) {
	msgBox.confirm({
		content : $.i18n.prop("JC_SYS_034"),
		success : function() {
			$.ajax({
				type : "POST",
				url : getRootPath() + "/archive/relate/deleteByIds.action",
				data : {
					"ids" : id
				},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage
						});
						archive_doc.relateList();
					} else {
						msgBox.tip({
							content : data.errorMessage
						});
					}
				}
			});
		}
	});
};
// 关联关系 end

// 版本管理 Start---------------------------------------------------------
archive_doc.version = function(id) {
	if (id && !archive_doc.checkLock(id)) {
		return;
	}
	$.ajax({
		type : "post",
		cache : false,
		url : getRootPath() + "/archive/document/selectDoc.action",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				$('#version-management').modal('show');
				archive_doc.initVersionList(id);
			} else {
				msgBox.tip({
					content : data.successMessage
				});
			}
		}
	});
};

archive_doc.versionTable = null;
// 显示列信息
archive_doc.versionTableAoColumns = [
		{
			mData : "currentVersion"
		},// 版本号
		{
			mData : "versionDesc"
		},// 版本说明
		{
			mData : "owner"
		},// 更新人
		{
			mData : "modifyDate"
		},// 操作时间
		{
			mData : function(source) {
				if (source.isCurrentUsed == 0) {
					return "<a class='a-icon i-new m-l-xs m-r-xs' type=\"button\" id=\"switch_"
							+ source.id
							+ "\" href=\"javascript:archive_doc.switchVersion('"
							+ source.id + "')\" role=\"button\" >启用此版本</a>";
				} else {
					return "<a class='a-icon i-new m-l-xs m-r-xs' type=\"button\" id=\"switch_"
							+ source.id
							+ "\" href=\"javascript:archive_doc.switchVersion('"
							+ source.id
							+ "')\" role=\"button\" style=\"display: none;\">启用此版本</a>";
				}
			}
		} ];

// 分页查询方法
archive_doc.initVersionList = function(id) {

	// 组装后台参数
	archive_doc.versionTableFnServerParams = function(aoData) {
		// 排序条件
		getTableParameters(archive_doc.versionTable, aoData);
		aoData.push({
			"name" : "backUpId",
			"value" : id
		});
	};
	if (archive_doc.versionTable == null) {
		archive_doc.versionTable = $('#versionTable').dataTable(
				{
					"iDisplayLength" : archive_doc.pageRows,// 每页显示多少条记录
					"sAjaxSource" : getRootPath()
							+ "/archive/document/manageVersionList.action",// 后台分页查询地址url
					"fnServerData" : oTableRetrieveData,// 查询数据回调函数
					"aoColumns" : archive_doc.versionTableAoColumns,// table显示列
					"fnServerParams" : function(aoData) {
						archive_doc.versionTableFnServerParams(aoData);
					},
					aaSorting : [],// 设置表格默认排序列
					// 默认不排序列
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 4 ]
					} ]
				});
	} else {
		// table不为null时重绘表格
		archive_doc.versionTable.fnDraw();
	}
};

/**
 * 启用选中版本
 */
archive_doc.switchVersion = function(id, version) {
	$.ajax({
		type : "POST",
		url : getRootPath() + "/archive/document/switchVersion.action",
		data : {
			"id" : id,
			t : (new Date()).getTime()
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type : "success",
					content : data.successMessage
				});
				// $('#versionTable a').toggleClass("hide");
				// $("#versionTable a[class='hide']").removeClass("hide");
				// $("#switch_"+id).addClass("hide");
				$("#switch_" + id).attr("style", "display: none;");
				archive_doc.versionTable.fnDraw();
			} else {
				msgBox.tip({
					content : data.errorMessage
				});
			}
			archive_doc.reloadData();
		}
	});
};
// 版本管理 end---------------------------------------------------------------

// 审计信息 start--------------------------------------------------------------
// 显示列信息
archive_doc.oTableAudithis = [ {
	mData : "userName"
},// 操作人员
{
	mData : "operDesc"
},// 事件描述
{
	mData : "ip"
},// 操作IP
{
	mData : "operTime"
} // 操作时间
];
var auditId = null;
// 组装后台参数
archive_doc.oTableFnServerParamsAudithis = function(aoData) {
	// 排序条件
	getTableParameters(archive_doc.auditTable, aoData);
	aoData.push({
		"name" : "dataId",
		"value" : auditId
	});
};

// 文档审计
archive_doc.audithis = function(id) {
	$.ajax({
		type : "post",
		cache : false,
		url : getRootPath() + "/archive/document/selectDoc.action",
		data : {
			id : id,
			folderId:$("#folderId").val()
		},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				auditId = id;
				$('#document-the-audit').modal('show');
				archive_doc.audithisList(id);
			} else {
				msgBox.tip({
					content : data.successMessage
				});
			}
		}
	});
	/*// archive_doc.oTable = null;
	auditId = id;
	$('#document-the-audit').modal('show');
	archive_doc.audithisList(id);*/
};

archive_doc.audithisList = function(id) {
	if (archive_doc.auditTable == null) {
		archive_doc.auditTable = $('#audithisList').dataTable(
				{
					bDestroy : true,
					"iDisplayLength" : archive_doc.pageRows,// 每页显示多少条记录
					"sAjaxSource" : getRootPath()
							+ "/archive/audithis/manageList.action",// 后台分页查询地址url
					"fnServerData" : oTableRetrieveData,// 查询数据回调函数
					"aoColumns" : archive_doc.oTableAudithis,// table显示列
					"fnServerParams" : function(aoData) {
						archive_doc.oTableFnServerParamsAudithis(aoData, id);
					},
					aaSorting : [],// 设置表格默认排序列
					// 默认不排序列
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : []
					} ]
				});
	} else {
		// table不为null时重绘表格
		archive_doc.auditTable.fnDraw();
	}
};
// 审计信息
// End----------------------------------------------------------------------------------------------

// 退出本页面前回调
function pageRedirecting() {
	archive_doc = undefined;
};
// 初始化
jQuery(function($) {
	// 计算分页显示条数
	archive_doc.pageRows = TabNub > 0 ? TabNub : 10;
	// $('#dSection').css('height',$(window).height() - 270);
	// 关联关系使用 start
	$('#chooseRelate').click(archive_doc.showRelateForDocDiv);
	selectControl.init("queryCreateUserTree", "createUser-createUser", false,
			true);
	$("#queryRelateForDoc").click(archive_doc.relateForDocList);
	$("#resetRelateForDoc").click(archive_doc.resetRelateForDoc);
	$("#saveRelate").click(archive_doc.saveRelateForDoc);
	// 关联关系使用 end
	$('#btnNewFolder').click(archive_doc.insertNewRow);
	$('#btnAuth').click(archive_doc.authTop);
	$('#btnReturn').click(archive_doc.goParent);
	$('#btnReturnDisabled').click(archive_doc.goParent);
	$('#btnUpload').click(archive_doc.showUpload);
	$('#btnFinishUpload').click(archive_doc.finishUpload);
	$('#btnCloseUpload').click(archive_doc.deleteAttach);
	$('#btnDelete').click(archive_doc.deleteClick);
	$('#newPPT').click(archive_doc.newPPT);
	$('#newWord').click(archive_doc.newWord);
	$('#newExl').click(archive_doc.newExl);
	// 添加按钮绑定事件
	$("#addBtn").click(archive_doc.addBtn);
	$('#search').on("input", archive_doc.onFilter);
	$('#search').on("keyup", archive_doc.onFilter);
	$(".tree-list .tree-btn").on(
			'click',
			function(e) {
				e.preventDefault();
				e.stopPropagation();
				$(this).find("i").toggleClass("fa-chevron-down").end().closest(
						".tree-list").next().slideToggle();
			});
	$(".btn-new").mouseenter(function() {
		$(".document-new").show();
		setTimeout('$(".document-new").hide()', 5000);
	});
	$(".document-new").mouseleave(function() {
		$(this).hide();
	});

	$('#businessSource').val('0');// 默认的值为0 表示来源于OA，1表示来源于CRM
	// 权限控制加载
	// archive_doc.permission();

	archive_doc.folderSelector = new FolderSelectTree($('#folderSelector'),
			null, 0);
	// 日历控件重新初始化
	$(".datepicker-input").datepicker();

	// menuswrite.statue = true;

	archive_doc.pathBar = new FolderPathBar($('#folderTable'),
			archive_doc.loadData);
	archive_doc.init();
});

// @ sourceURL=archive_document.js
