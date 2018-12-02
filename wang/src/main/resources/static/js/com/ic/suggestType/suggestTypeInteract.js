var suggestType = {};
//重复提交标识
suggestType.subState = false;

//添加修改公用方法
suggestType.savaOrModify = function (hide) {
	if (suggestType.subState)return;
	if ($("#suggestTypeForm").valid()) {
		if($("#suggestTypeForm #recipientIds-recipientIds").val() == "" && $("input[name='isFixed']:checked").val()=='1'){
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_069"),
				type:"fail"
			});
			return;
		}
		suggestType.subState = true;
		var url = getRootPath()+"/ic/suggestType/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/ic/suggestType/update.action?time=" + new Date();
		}
		var formData = $("#suggestTypeForm").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				suggestType.subState = false;
				$('#token').val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						content: data.successMessage,
						type:"success"
					});
					if ($("#id").val() == 0) {
						suggestType.clearForm();
					} else {
						$("#modifyDate").val(data.modifyDate);
					}
					suggestType.suggestTypeList();
					if (hide) {
						$('#myModal-edit').modal('hide');
					}else{
						$('#required').html("*");
					}
				} else {
					if(data.labelErrorMessage){
						msgBox.tip({
							content: data.labelErrorMessage,
							type:"fail"
						});
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
			},
			error : function() {
				suggestType.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	}else{
		suggestType.subState = false;
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_067"),
			type:"fail"
		});
	}
};

//清空表单
suggestType.clearForm = function () {
	// $('#suggestTypeForm')[0].reset();
	$('#suggestTypeForm').find("input[type=text]").val("");
	$('#list').find("input[type=checkbox]").attr("checked",false);
	$('#suggestTypeForm').find("textarea").val("");
	select2InitEchoToPerson("suggestTypeForm #recipientIds-recipientIds",null);
};

//删除建议类型
suggestType.deleteSuggestType = function (id) {
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
	if(!DeleteCascade.syncCheckCanBatchDelete("suggestTypeDelete",ids)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_013")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			suggestType.deleteCallBack(ids);
		}
	});
};

suggestType.deleteCallBack = function(ids) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/suggestType/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				suggestType.clearForm();
				suggestType.suggestTypeList();
			}else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

//获取修改信息
suggestType.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/suggestType/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				suggestType.clearForm();
				$("#suggestTypeForm").fill(data);
				if(data.userName != "["){
					select2InitEchoToPerson("suggestTypeForm #recipientIds-recipientIds",eval(data.userName));
				}
				$("#savaOrModify").hide();
				$("#oldName").val(data.typeName);
				$("#title").html("编辑");
				$("#savaAndClose").addClass("dark");
				$("#savaAndClose").html("保 存");
				$('#myModal-edit').modal('show');
				/** 此处添加备注 由于fill方法对radio和checkbox的处理有差异，fill.js中使用的.attr()方法不能准确的赋值，所以强行使用.prop（）方法重新给radio赋值回显**/
				if(data.isFixed == '1') {
					$('#isFixed').prop('checked', true);
					$('#required').html("*");//如果是固定的，建议接收人要显示* 为必填，否则去掉* 不必填
				} else {
					$('#isnoFixed').prop('checked', true);
					$('#required').empty();
				}
			}
		},
		error : function() {
			msgBox.tip({
				content:$.i18n.prop("JC_OA_COMMON_014")
			});
		}
	});
};

//分页处理 start
//分页对象
suggestType.oTable = null;
//显示列信息
suggestType.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{ "mData": "typeName" },
	{mData: "isFixed", mRender : function(mData) {
		return mData == 1 ? "固定" : "不固定";
	}
	},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
 ];


//组装后台参数
suggestType.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(suggestType.oTable, aoData);
	//组装查询条件
	if ($("#suggestTypeListForm #typeName").val()!="") {
		aoData.push({ "name": "typeName", "value": $("#suggestTypeListForm #typeName").val()});
	}
	if ($("#suggestTypeListForm #isFixed_t").prop('checked')) {
		aoData.push({ "name": "isFixed_t", "value": $("#suggestTypeListForm #isFixed_t").val()});
	}
	if ($("#suggestTypeListForm #isFixed_f").prop('checked')) {
		aoData.push({ "name": "isFixed_f", "value": $("#suggestTypeListForm #isFixed_f").val()});
	}
};

//分页查询建议类型
suggestType.suggestTypeList = function () {
	$('#IP-edit').fadeIn();
	if (suggestType.oTable == null) {
		suggestType.oTable = $('#suggestTypeTable').dataTable( {
			iDisplayLength: suggestType.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/suggestType/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: suggestType.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				suggestType.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,3]}]
		} );
	} else {
		suggestType.oTable.fnDraw();
	}
};
//分页处理 end

//显示添加建议类型弹出层
suggestType.showAddDiv = function (){
	//判断重复提交
	suggestType.clearForm();
	hideErrorMessage();
	// $('#suggestTypeForm').find("input[type=radio]").eq(1).attr("checked",'checked');
	$("#id").val(0);
	$("#title").html("新增");
	$('#required').html("*");
	$("#savaAndClose").removeClass("dark");
	$("#savaAndClose").html("保存退出");
	$("#savaOrModify").show();
	$('#myModal-edit').modal('show');
};

//加载添加DIV
suggestType.loadAddHtml = function (){
	if($.trim($("#myModal-editDiv").html()) == ""){
		$("#myModal-editDiv").load(getRootPath()+"/ic/suggestType/suggestTypeDiv.action",suggestType.showAddDiv);
	}
	else{
		suggestType.showAddDiv();
	}
};

//加载添加DIV
suggestType.loadHtml = function (id){
	if($.trim($("#myModal-editDiv").html()) == ""){
		$("#myModal-editDiv").load(getRootPath()+"/ic/suggestType/suggestTypeDiv.action",null,function(){suggestType.get(id);});
	}
	else{
		suggestType.get(id);
	}
};

//初始化
jQuery(function($) 
{
	//计算分页显示条数
	suggestType.pageRows = TabNub>0 ? TabNub : 10;
	$("#querysuggestType").click(suggestType.suggestTypeList);
	$("#showAddDiv").click(suggestType.loadAddHtml);
	$("#showAddDiv_t").click(suggestType.loadAddHtml);
	$("#deleteSuggestTypes").click("click", function(){suggestType.deleteSuggestType(0);});
	$("#savaAndClose").click(function(){suggestType.savaOrModify(true);});
	$("#savaOrModify").click(function(){suggestType.savaOrModify(false);});
	selectControl.init("showUserTree","recipientIds-recipientIds", true, true,null,null);//多选人员
	
	suggestType.suggestTypeList();
	

	$("input[name='isFixed']").change(function() {
		if ($("input[name='isFixed']:checked").val() == 0) {
			$('#required').empty();
		} else {
			$('#required').html("*");
		}
	});
});

//@ sourceURL=suggestTypeInteract.js