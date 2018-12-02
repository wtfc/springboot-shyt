var fromDept = {};
fromDept.pageRows = null;
//重复提交标识
fromDept.subState = false;

//分页处理 start
//分页对象
fromDept.oTable = null;
//显示列信息
fromDept.oTableAoColumns = [
    {mData: function(source) {
    	return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
  	}},
	{ "mData": "name"},
	{ "mData": "offcial"},
	{ "mData": "createDate", "mRender" : function(mData, type, full) {
		return full.createDateTemp;
	}},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
fromDept.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(fromDept.oTable, aoData);
	//组装查询条件
	$.each($("#fromDeptListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
fromDept.fromDeptList = function () {
	$('#fromDept-list').fadeIn();
	if (fromDept.oTable == null) {
		fromDept.oTable = $('#fromDeptTable').dataTable( {
			"iDisplayLength": fromDept.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/fromDept/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": fromDept.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				fromDept.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,4]}]
		} );
	} else {
		fromDept.oTable.fnDraw();
	}
};
//分页处理 end

fromDept.queryReset = function(){
	$('#fromDeptListForm')[0].reset();
	//fromDept.fromDeptList();
};

//添加成功清空表单数据
fromDept.initForm = function(){
	fromDept.clearForm();
};

//清空表单
fromDept.clearForm = function(){
	$('#fromDeptForm')[0].reset();
	$("#fromDeptForm").find("input[type=hidden]").val('');
	hideErrorMessage();
};

//显示添加单位弹出层
fromDept.showAddDiv = function (){
	//getToken();
	fromDept.clearForm();
	$("#id").val(0);
	$("#saveOrModify").show();
	$("#saveAndClose").removeClass("dark");
	$("#saveAndClose").html("保存退出");
	$("#Modal_Titile").text("新增");
	$('#fromDept-edit').modal('show');
};

//获取修改信息
fromDept.get = function (id) {
	$("#dataLoad").show();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/doc/fromDept/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$("#fromDeptForm").fill(data);
				$("#id").val(data.id);
				$("#nameOld").val(data.name);
				$("#saveOrModify").hide();
				$("#saveAndClose").addClass("dark");
				$("#saveAndClose").html("保 存");
				$("#Modal_Titile").text("编辑");
				$('#fromDept-edit').modal('show');
			}
			$("#dataLoad").hide();
		}
	});
};

//添加修改公用方法
fromDept.saveOrModify = function (hide) {
	$("#dataLoad").show();
	if (fromDept.subState)return;
	fromDept.subState = true;
	if ($("#fromDeptForm").valid()) {
		var url = getRootPath()+"/doc/fromDept/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/doc/fromDept/update.action?time=" + new Date();
		}
		var formData = $("#fromDeptForm").serializeArray();
		//alert($('#token').val());
		formData.push({"name": "token", "value": $('#token').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				fromDept.subState = false;
				//getToken();
				$("#token").val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if ($("#id").val() == 0) {
						fromDept.initForm();
					}
					if (hide) {
						$('#fromDept-edit').modal('hide');
					}
					fromDept.fromDeptList();
				} else {
					if(data.labelErrorMessage){
						showErrors("fromDeptForm", data.labelErrorMessage);
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
				fromDept.subState = false;
				content: jQuery.validator.format($.i18n.prop("JC_SYS_002"));
			}
		});
	} else {
		$("#dataLoad").hide();
		fromDept.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//删除用户
fromDept.deleteFromDept = function (id) {
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
		fromDept.deleteCallBack(ids);
	});
};

//删除用户回调方法
fromDept.deleteCallBack = function(ids) {
	$("#dataLoad").show();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/fromDept/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				fromDept.fromDeptList();
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
	fromDept.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryDept").click(fromDept.fromDeptList);
	$("#queryReset").click(fromDept.queryReset);
	//添加页面
	$("#showAddDiv").click(fromDept.showAddDiv);
	$("#showAddDiv_t").click(fromDept.showAddDiv);
	//添加修改方法
	$("#saveAndClose").click(function(){fromDept.saveOrModify(true);});
	$("#saveOrModify").click(function(){fromDept.saveOrModify(false);});
	//删除方法
	$("#deleteFromDepts").click("click", function(){fromDept.deleteFromDept(0);});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	fromDept.fromDeptList();
});