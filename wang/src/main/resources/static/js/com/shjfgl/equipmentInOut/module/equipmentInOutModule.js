
//初始化方法
var equipmentInOutModule = {};

//重复提交标识
equipmentInOutModule.subState = false;

//获取修改信息
equipmentInOutModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipmentInOut/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentInOutModule.clearForm();
				$("#equipmentInOutForm").fill(data);
				$("#equipmentInOutForm2").fill(data);
				$("#equipmentInOutForm4").fill(data);
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(false);
				echoAttachList(false);
			}
		}
	});
};

//添加修改公用方法
equipmentInOutModule.saveOrModify = function (hide) {
	if(equipmentInOutModule.subState)return;
		equipmentInOutModule.subState = true;
	if ($("#equipmentInOutForm").valid()) {
		var url = getRootPath()+"/machine/equipmentInOut/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipmentInOut/update.action";
		}
		var formData = $("#equipmentInOutForm").serializeArray();
		equipmentInOutModule.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentInOutModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
						loadrightmenu("/machine/equipmentInOut/managejsfk.action","","/machine/equipmentInOut/managejsfk.action");
						}
					});
					equipmentInOutModule.clearForm();
					$("#token").val(data.token);
					
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentInOutForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
					$("#token").val(data.token);
				}
			},
			error : function() {
				equipmentInOutModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_WORKFLOW_002")
				});
			}
		});
	} else {
		equipmentInOutModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//工单处理update
equipmentInOutModule.equipmentInOutForm2 = function (hide) {
	if(equipmentInOutModule.subState)return;
		equipmentInOutModule.subState = true;
		var	url="";
	if ($("#equipmentInOutForm2").valid()) {
		url = getRootPath()+"/machine/equipmentInOut/updateGdcl.action";
		var formData = $("#equipmentInOutForm2").serializeArray();
		equipmentInOutModule.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentInOutModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
						loadrightmenu("/machine/equipmentInOut/manageRead.action","","/machine/equipmentInOut/manageRead.action");
						}
					});
					equipmentInOutModule.clearForm();
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentInOutForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage,
						});
					}
					$("#token").val(data.token);
				};
			},
			error : function() {
				equipmentInOutModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_WORKFLOW_002")
				});
			}
		});
	} else {
		equipmentInOutModule.subState = false;
			msgBox.info({
				content:$.i18n.prop("JC_SYS_067")
			});
	}
};
//客户回访
equipmentInOutModule.equipmentInOutForm4 = function (hide) {
	if(equipmentInOutModule.subState)return;
		equipmentInOutModule.subState = true;
		var	url="";
	if ($("#equipmentInOutForm4").valid()) {
		url = getRootPath()+"/machine/equipmentInOut/updateKfhf.action";
		var formData = $("#equipmentInOutForm4").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentInOutModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
						loadrightmenu("/machine/equipmentInOut/manageRead.action","","/machine/equipmentInOut/manageRead.action");
						}
					});
					clearTable();
					equipmentInOutModule.clearForm();
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentInOutForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage,
						});
					}
					$("#token").val(data.token);
				};
			},
			error : function() {
				equipmentInOutModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_WORKFLOW_002")
				});
			}
	});
	} else {
		equipmentInOutModule.subState = false;
			msgBox.info({
				content:$.i18n.prop("JC_SYS_067")
			});
	}
};
//附言
equipmentInOutModule.equipmentInOutForm3 = function (hide) {
	if(equipmentInOutModule.subState)return;
		equipmentInOutModule.subState = true;
		var	url="";
	var id = $("#equipmentInOutForm #id").val();
	if ($("#equipmentInOutForm").valid()) {
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/exchange/save.action";
		}
		var formData = $("#equipmentInOutForm3").serializeArray();
		var stat = $("#equipmentInOutForm3 #stat").val();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentInOutModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/equipmentInOut/manage.action?id="+id+"&status="+stat+"","","/machine/equipmentInOut/manage.action?id="+id+"&status="+stat+"");
							}
					});
					equipmentInOutModule.clearForm();
					$("#token").val(data.token);
				} 
			},
		});
	} else {
		equipmentInOutModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//添加附件
equipmentInOutModule.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};
//清空表单数据
equipmentInOutModule.clearForm = function(){
	$('#equipmentInOutForm')[0].reset();
	$('#equipmentInOutForm2')[0].reset();
	$('#equipmentInOutForm3')[0].reset();
	$('#equipmentInOutForm4')[0].reset();
	hideErrorMessage();
};

//人员选择方法
equipmentInOutModule.spCall = function(data, controlId) {
	//id
	var id = "";
	//姓名
	var name = "";
	//dataJson
	var userJson = "";
	
	//遍历数据
	if(data != null || data.length > 0){
		$.each(data, function(i, val){
			id += val.id + ",";
			name += val.text + ",";
			userJson += "{id:"+val.id+", text:'"+val.text+"'},";
		});
	}
	$('#userJson').val("[" + userJson.substring(0, userJson.length-1) + "]");  //用于数据回显
	$("#"+controlId).val(name.substring(0, name.length-1));
	$("#membersId").val(id.substring(0, id.length-1));
	//清空验证信息
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
//	selectControl.init("controlTree1", "name1-name", true, true, null, null,null);
//	$(".datepicker-input").each(function(){$(this).datepicker();});
//	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});