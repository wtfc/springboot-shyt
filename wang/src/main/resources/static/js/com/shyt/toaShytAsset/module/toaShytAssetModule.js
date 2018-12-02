//初始化方法
var toaShytAssetModule = {};

//重复提交标识
toaShytAssetModule.subState = false;

//显示表单弹出层
toaShytAssetModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	toaShytAssetModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
toaShytAssetModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaShytAsset/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaShytAssetModule.clearForm();
				$("#toaShytAssetModuleForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
toaShytAssetModule.saveOrModify = function (hide) {
	if(toaShytAssetModule.subState)return;
		toaShytAssetModule.subState = true;
	if ($("#toaShytAssetModuleForm").valid()) {
		var url = getRootPath()+"/machine/toaShytAsset/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaShytAsset/update.action";
		}
		var formData = $("#toaShytAssetModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaShytAssetModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaShytAssetModule.clearForm();
					}
					$("#token").val(data.token);
					toaShytAsset.toaShytAssetList();
				} else {
					if(data.labelErrorMessage){
						showErrors("toaShytAssetModuleForm", data.labelErrorMessage);
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
				toaShytAssetModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaShytAssetModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaShytAssetModule.clearForm = function(){
	$('#toaShytAssetModuleForm')[0].reset();
	hideErrorMessage();
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	$("#saveAndClose").click(function(){toaShytAssetModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){toaShytAssetModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
});