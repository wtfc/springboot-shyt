var bookremark = {};
bookremark.pageRows = null;
//重复提交标识
bookremark.subState = false;

//分页处理 start
//分页对象
bookremark.oTable = null;
//显示列信息
bookremark.oTableAoColumns = [
    {mData: function(source) {
	return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
	}},
	{ "mData": "columnName"},
	{ "mData": "name"},
//	{ "mData": "isUse", "mRender" : function(mData) {
//		return mData == 0 ? "禁用" : "启用";
//	}},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
bookremark.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(bookremark.oTable, aoData);
	//组装查询条件
	$.each($("#bookremarkListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
bookremark.bookremarkList = function () {
	$('#bookremark-list').fadeIn();
	if (bookremark.oTable == null) {
		bookremark.oTable = $('#bookremarkTable').dataTable( {
			"iDisplayLength": bookremark.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/bookremark/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": bookremark.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				bookremark.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,3]}]
		} );
	} else {
		bookremark.oTable.fnDraw();
	}
};
//分页处理 end

//元素添加对象
bookremark.addTable = null;
//元素添加列表页
bookremark.showAddDialog = function(){
	$('#bookremark-add').modal('show');
	
	//组装后台参数
	bookremark.addTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(bookremark.addTable, aoData);
	};
	
	//显示发文表字段信息
	bookremark.addColumns = [
        {mData: function(source) {
        	return "<input type=\"checkbox\" name=\"names\" sendColumnName="+source.columnName+" comment=" + source.columnCommentTemp + " value="+ source.columnNameTemp + ">";
      	}},
		{ "mData": "columnNameTemp" },
		{ "mData": "columnCommentTemp", "mRender" : function(mData, type, full) {
			return full.columnCommentTemp.replace('#','');
		}}
	 ];
	if (bookremark.addTable == null) {
		bookremark.addTable = $('#bookremarkAddTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/bookremark/querySendColumnsList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": bookremark.addColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				bookremark.addTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		bookremark.addTable.fnDraw();
	}
};

//保存添加元素
bookremark.saveBookremark = function (name) {
	var names = String(name);
	if (name == 0) {
		var namesArr = [];
		$("[name='names']:checked").each(function() {
			var mark = {};
			mark['name'] = $(this).attr('comment');
			mark['sendColumnName'] = $(this).attr('sendColumnName');
			mark['columnName'] = $(this).val();
			mark['isUse'] = '1';
			namesArr.push(mark);
			//namesArr.push($(this).val());
		});
	//	names = namesArr.join(",");
	}
	if (namesArr.length == 0) {
		msgBox.info({
			content: $.i18n.prop("JC_SYS_029","要添加的元素")
		});
		return;
	}
	bookremark.saveSendColumns(namesArr);
	$('#bookremark-add').modal('hide');
};
//添加元素回调方法
bookremark.saveSendColumns = function(names) {
	$("#dataLoad").show();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/bookremark/saveBatch.action",
		data : {"names": JSON.stringify(names,replaceJsonNull)},
		dataType : "json",
		success : function(data) {
			$("#token").val(data.token);
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				bookremark.bookremarkList();
			} else {
				msgBox.tip({
					content: data.errorMessage
				});
			};
			$("#dataLoad").hide();
		},
		error : function() {
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
			$("#dataLoad").hide();
		}
	});
};

bookremark.queryReset = function(){
	$('#bookremarkListForm')[0].reset();
	//bookremark.bookremarkList();
};

//添加成功清空表单数据
bookremark.initForm = function(){
	bookremark.clearForm();
};

//清空表单
bookremark.clearForm = function(){
	$('#bookremarkUpdateForm')[0].reset();
	hideErrorMessage();
};

//修改页面显示
bookremark.showBookremarkUpdateDiv = function (id){
	$("#dataLoad").show();
	bookremark.clearForm();
	
	var url = getRootPath()+"/doc/bookremark/get.action";
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
        		$("#bookremarkUpdateForm").fill(data);
        		$('#bookremark-update').modal('show');
        		$("#dataLoad").hide();
        	}
        }
    });
};
//修改保存方法
bookremark.updateBookremark = function(){
	$("#dataLoad").show();
	if(bookremark.subState)return;
	bookremark.subState = true;
	if ($("#bookremarkUpdateForm").valid()) {
		var url = getRootPath()+"/doc/bookremark/update.action?time=" + new Date();
		var formData = $("#bookremarkUpdateForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				bookremark.subState = false;
				$("#token").val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					bookremark.initForm();
					$('#bookremark-update').modal('hide');
					bookremark.bookremarkList();
				} else {
					if(data.labelErrorMessage){
						showErrors("bookremarkUpdateForm", data.labelErrorMessage);
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
				user.subState = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
			}
		});
	}else{
		$("#dataLoad").hide();
		bookremark.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//禁用启用操作
bookremark.updateBookremarkIsUse = function(id,isUse,modifyDate){
	var isUseCue;
	if(isUse==0){
		isUseCue = $.i18n.prop("JC_OA_DOC_007");
	}else{
		isUseCue = $.i18n.prop("JC_OA_DOC_008");
	}
	confirmx(isUseCue,function(){
		$("#dataLoad").show();
		jQuery.ajax({
			url : getRootPath()+"/doc/bookremark/update.action",
			type : 'POST',
			data : {"id": id,"isUse": isUse,"modifyDate": modifyDate},
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					bookremark.bookremarkList();
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
					content:$.i18n.prop("JC_SYS_004")
				});
			}
		});
	});	
};
//删除用户
bookremark.deleteBookremark = function (id) {
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
			content: $.i18n.prop("JC_SYS_061")
		});
		return;
	}
	confirmx($.i18n.prop("JC_SYS_034"),function(){
		bookremark.deleteCallBack(ids);
	});
};

//删除用户回调方法
bookremark.deleteCallBack = function(ids) {
	$("#dataLoad").show();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/bookremark/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				bookremark.bookremarkList();
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			$("#dataLoad").hide();
		}
	});
};
//初始化
jQuery(function($){
	//计算分页显示条数
	bookremark.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryBookremark").click(bookremark.bookremarkList);
	$("#queryReset").click(bookremark.queryReset);
	//元素添加页面
	$("#showAddDiv").click(bookremark.showAddDialog);
	$("#showAddDiv_t").click(bookremark.showAddDialog);
	//页面添加方法
	$("#saveBookremark").click(function(){bookremark.saveBookremark(0);});
	//页面修改方法
	$("#updateBookremark").click(bookremark.updateBookremark);
	//删除方法
	$("#deleteBookremarks").click("click", function(){bookremark.deleteBookremark(0);});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	bookremark.bookremarkList();
});