var suggestTypeDiv = {};
//重复提交标识
suggestTypeDiv.subState = false;

//添加修改公用方法
suggestTypeDiv.savaOrModify = function (hide) {
	if (suggestTypeDiv.subState)return;
	if ($("#suggestTypeForm").valid()) {
		if($("#suggestTypeForm #recipientIds-recipientIds").val() == "" && $("input[name='isFixed']:checked").val()=='1'){
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_069"),
				type:"fail"
			});
			return;
		}
		suggestTypeDiv.subState = true;
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
				suggestTypeDiv.subState = false;
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
					//设置是否固定默认为固定
					$('#suggestTypeForm').find("input[type=radio]").get(0).checked = 'checked';
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
				suggestTypeDiv.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	}else{
		suggestTypeDiv.subState = false;
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_067"),
			type:"fail"
		});
	}
};

suggestTypeDiv.closeModel = function(){
	$("#suggestTypeForm")[0].reset();
	$("#myModal-edit").modal('hide');
};

suggestTypeDiv.radio = function(num){
	if(num==0){
		$('#required').html("");
	} else {
		$('#required').html("*");
	}
};

//初始化
jQuery(function($) 
{
	$("#savaAndClose").click(function(){suggestTypeDiv.savaOrModify(true);});
	$("#savaOrModify").click(function(){suggestTypeDiv.savaOrModify(false);});
	selectControl.init("showUserTree","recipientIds-recipientIds", true, true,null,null);//多选人员
	$("#addClose").click(function(){suggestTypeDiv.closeModel();});//关闭
	ie8StylePatch();
});

//@ sourceURL=suggestTypeInteract.js