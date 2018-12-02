var contactsDiv = {};



//添加修改公用方法
contactsDiv.savaOrModify = function (hide) {
	if (contactsDiv.subState)return;
	contactsDiv.subState = true;
	if ($("#contactsForm").valid()) {
		var url = getRootPath()+"/ic/contacts/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/ic/contacts/update.action?time=" + new Date();
		}
		var formData = $("#contactsForm").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				contactsDiv.subState = false;
				$('#token').val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if ($("#id").val() == 0) {
						contactsDiv.clearForm();
					} 
					if (hide) {
						$('#editContacts').modal('hide');
					}
					contacts.contactsExternalList();
					contactsDiv.initGroupNameList();
					$("[name='contactsChecked']").each(function() {
						$(this).attr("checked", false);
					});
					$("#dataLoad").fadeOut();
				} else {
					if(data.labelErrorMessage){
						showErrors("contactsForm", data.labelErrorMessage);
					}else{
						msgBox.info({
							content: data.errorMessage
						});
					  } 
					$("#dataLoad").fadeOut();
				}
			},
			error : function() {
				contactsDiv.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		contactsDiv.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//初始化 增加、修改联系人分组
contactsDiv.initGroupNameList = function () {
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

//获取修改信息
contactsDiv.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/contacts/getContacts.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				contactsDiv.initGroupNameList();
				$("#mailContacts").html("编辑");
				$("#saveAndClose").addClass("dark");
				contactsDiv.clearForm();
				$("#contactsForm").fill(data);
				$("#savaOrModify").hide();
				$("#saveAndClose").html("保 存");
				$("#userNameOld").val(data.userName);
				$("#mailOld").val(data.mail);
				$("#phoneOld").val(data.phone);
				$("#groupRId").val(data.groupRId);
				$('#editContacts').modal('show');
			}
		}
	});
};

//清空表单
contactsDiv.clearForm = function () {
	$('#contactsForm')[0].reset();
	contactsDiv.initGroupNameList();
};

//初始化数据
jQuery(function($) 
{ 
	$("#saveAndClose").click(function(){contactsDiv.savaOrModify(true);});
	$("#savaOrModify").click(function(){contactsDiv.savaOrModify(false);});
	contactsDiv.initGroupNameList();
	ie8StylePatch();
});

//@ sourceURL=contactsInteract.js