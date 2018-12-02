
//初始化方法
var specialdataShareModule = {};

//重复提交标识
specialdataShareModule.subState = false;

//添加修改公用方法
specialdataShareModule.saveOrModify = function (hide) {
	if(specialdataShareModule.subState)return;
		specialdataShareModule.subState = true;
	var token = $("#tokens").val();
	var url = getRootPath()+"/sys/specialdataShare/save.action";
	var formData = {"userids": $("#UserleftRight").val(),"deptids": $("#DeptleftRight-DeptleftRight").val(),"organids": $("#OrganleftRight-OrganleftRight").val(),"specialdataId":$("#specialdataId").val(),"token":token};
	jQuery.ajax({
		url : url,
		type : 'POST',
		cache: false,
		data : formData,
		dataType : "json",
		success : function(data) {
			specialdataShareModule.subState = false;
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				if (hide) {
					$('#myModal-share').modal('hide');
				} else {
					//specialdataShareModule.clearForm();
				}
				$("#token").val(data.token);
			} else {
				msgBox.info({
					type:"fail",
					content: data.errorMessage
				});
				$("#token").val(data.token);
			}
		},
		error : function() {
			specialdataShareModule.subState = false;
			msgBox.tip({
				type:"fail",
				content: $.i18n.prop("JC_SYS_002")
			});
		}
	});
};

$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	
	$("#saveAndClose").click(function(){specialdataShareModule.saveOrModify(true);});
	$("#formDivClose1").click(function(){$('#myModal-share').modal('hide');});

});