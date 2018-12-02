//初始化方法
var toaContractIncontractModule = {};
//重复提交标识
toaContractIncontractModule.subState = false;
//获取修改信息
toaContractIncontractModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/contract/toaContractIncontract/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaContractIncontractModule.clearForm();
				$("#toaContractIncontractForm").fill(data);
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(true);
				echoAttachList(false);
			}
		}
	});
};

toaContractIncontractModule.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};

toaContractIncontractModule.spCall = function(data, controlId) {
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
//添加修改公用方法
toaContractIncontractModule.saveOrModify = function (hide) {
	if(toaContractIncontractModule.subState)return;
		toaContractIncontractModule.subState = true;
	if ($("#toaContractIncontractForm").valid()) {
		var url = getRootPath()+"/contract/toaContractIncontract/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/contract/toaContractIncontract/update.action";
		}
		var formData = $("#toaContractIncontractForm").serializeArray();
		toaContractIncontractModule.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data :formData,
			success : function(data) {
				toaContractIncontractModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/contract/toaContractIncontract/manage.action","","/contract/toaContractIncontract/manage.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("monitorForm", data.labelErrorMessage);
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
				toaContractIncontractModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaContractIncontractModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaContractIncontractModule.clearForm = function(){
	$('#toaContractIncontractForm')[0].reset();
	hideErrorMessage();
};