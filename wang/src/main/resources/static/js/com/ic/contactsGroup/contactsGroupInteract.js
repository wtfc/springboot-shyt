var group = {};

//分页变量
group.pageRows = null;

//重复提交标识
group.subState = false;

//邮件联系人分组
group.oGroupTable = null;

//联系人分组添加修改公用方法
group.savaOrModifyGroup = function (hide) {
	if (group.subState) return;
	group.subState = true;
	if ($("#contactsGroupsForm").valid()) {
		var url = getRootPath()+"/ic/contactsGroup/save.action?time=" + new Date();
		if ($("#groupsId").val() != 0) {
			url = getRootPath()+"/ic/contactsGroup/update.action?time=" + new Date();
		}
		var formData = $("#contactsGroupsForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				group.subState = false;
				$('#token').val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if ($("#groupsId").val() == 0) {
						group.clearGroupForm();
						group.initGroupNameList();
					} else {
						$("#modifyDate").val(data.modifyDate);
					}
					if (hide) {
						$('#contactsGroups').modal('hide');
					}
					group.contactsGroupList();
					group.initGroupList();
					contacts.contactsExternalList();
					$("[name='groupChecked']").each(function() {
						$(this).attr("checked", false);
					});
				} else {
					if(data.labelErrorMessage){
						showErrors("contactsGroupsForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				group.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		group.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//清空表单
group.clearGroupForm = function () {
	$('#groupForm')[0].reset();
};
//删除邮件联系人分组
group.deleteContactsGroup = function (id) {
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
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			group.deleteGroupCallBack(ids);
		}
	});
};

group.deleteGroupCallBack = function(ids) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/contactsGroup/delete.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				group.clearGroupForm();
				group.contactsGroupList();
				group.initGroupList();
				contacts.contactsExternalList();
				group.initGroupNameList();
				$("[name='groupChecked']").each(function() {
					$(this).attr("checked", false);
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

//获取收件人组信息
group.getGroup = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/contactsGroup/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$("#mailContactsGroups").html("编辑");
				$("#saveAndCloseGroup").addClass("dark");
				$("#saveAndCloseGroup").html("保 存");
				group.clearGroupForm();
				$("#contactsGroupsForm").fill(data);
				$("#savaOrModifyGroup").hide();
				$("#groupNameOld").val(data.groupName);
				$('#contactsGroups').modal('show');
			}
		}
	});
};

//分页处理 start

//联系人分组信息
group.oTableGroupColumns = [
	{mData: function(source) {
		return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
	}
	},                        
  	{ "mData": "groupName" },
  	{ "mData": "description" },
	//设置权限按钮
    {mData: function(source) {
    return oTableSetGroupButtons(source);
    }}
 ];

group.oTableGroupFnServerParams = function(aoData){
	//排序条件
	getTableParameters(group.oGroupTable, aoData);
}

//分页查询联系人组
group.contactsGroupList = function () {
	if (group.oGroupTable == null) {
		group.oGroupTable = $('#contactsGroup').dataTable( {
			iDisplayLength: group.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/contactsGroup/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: group.oTableGroupColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				group.oTableGroupFnServerParams(aoData);
			},
			aaSorting:[],//设置默认排序列
			//默认不排序列
	        aoColumnDefs: [{"bSortable": false, "aTargets": [0,3]}]
		} );
	} else {
		group.oGroupTable.fnDraw();
	}
};
//分页处理 end

//清空联系人组表单
group.clearGroupForm = function () {
	$('#contactsGroupsForm').find("input[type=text]").val("");
};

//初始化查询条件联系人分组
group.initGroupList = function () {
	$.ajaxSetup({ 
	    async : false 
	});
	$.get(getRootPath()+"/ic/contactsGroup/groupList.action",null,function(data){
		$("#contactsGroupId").empty();
		$("#contactsGroupId").append($("<option>").val("").text("-全部-"));
		$.each(data, function(i, o) {
			var option = $("<option>").val(o.id).text(o.groupName);
			$("#contactsGroupId").append(option);
		});
	});
	$("#group").modal("setPaddingTop");
};

//初始化 增加、修改联系人分组
group.initGroupNameList = function () {
	$.ajaxSetup({ 
	    async : false 
	});
	$.get(getRootPath()+"/ic/contactsGroup/groupList.action",null,function(data){
		$("#groupId").empty();
		$("#groupId").append($("<option>").val("").text("-请选择-"));
		$.each(data, function(i, o) {
			var option = $("<option>").val(o.id).text(o.groupName);
			$("#groupId").append(option);
		});
	});
};


//显示添加联系人组弹出层
group.showGroupDiv = function (){
	//清除验证信息
	hideErrorMessage();
	group.clearGroupForm();
	$("#groupsId").val(0);
	$("#savaOrModifyGroup").show();
	$("#groupNameOld").val("");
	$("#mailContactsGroups").html("新增");
	$("#saveAndCloseGroup").removeClass("dark");
	$("#saveAndCloseGroup").html("保存退出");
	$('#contactsGroups').modal('show');
};
//初始化数据
jQuery(function($) 
{ 
	//计算分页显示条数
	group.pageRows = TabNub>0 ? TabNub : 10;
	$("#deleteContactsGroup").click("click", function(){group.deleteContactsGroup(0);});
	$("#saveAndCloseGroup").click(function(){group.savaOrModifyGroup(true);});
    $("#savaOrModifyGroup").click(function(){group.savaOrModifyGroup(false);});
	$("#addGroup").click(group.showGroupDiv);
	group.contactsGroupList();
	ie8StylePatch();
});
//@ sourceURL=contactsGroupInteract.js