



//初始化方法
var specialDataModule = {};

//重复提交标识
specialDataModule.subState = false;

//重复提交token
specialDataModule.subtoken = null;

//显示表单弹出层
specialDataModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	specialDataModule.clearForm();
	$('#myModal-edit').modal('show');
	if(specialDataModule.subtoken != null)
		$("#token").val(specialDataModule.subtoken);
	$("#actionTitle").html("新增");
	specialDataModule.changeInfo(1);
	selectControl.init("userTree","user-userId", false, true);
	if($("input[name='infoType']:checked").val() == 1){
		$("#summaryContent").attr('readonly',true);
	} else {
		$("#summaryContent").attr('readonly',false);
	}
};

//获取修改信息
specialDataModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/sys/specialData/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				specialDataModule.clearForm();
				$("#specialDataForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
				specialDataModule.changeInfo(data.infoType);
				if(data.infoType == 0){
					document.getElementById("infoType0").checked=true;
					$("#tempinfoName").val(data.infoName);
				}else{ 
					document.getElementById("infoType1").checked=true;
				}
				$("#actionTitle").html("编辑");
				selectControl.init("userTree","user-userId", false, true, null, {id:data.userId,text:data.infoName});
			}
		}
	});
};

specialDataModule.changeInfo = function (typesign){
	if(typesign == 0){
		$("#userTree").css("display", "none");
		$("#parameter").css("display", "none");
		$("#festival").css("display", "");
		$("#summaryContent").attr('readonly',false);
	}else {
		$("#userTree").css("display", "");
		$("#parameter").css("display", "");
		$("#festival").css("display", "none");
		$("#summaryContent").attr('readonly',true);
	}
};

//添加修改公用方法
specialDataModule.saveOrModify = function (hide) {
	if($("input[name='infoType']:checked").val() == 1){
		if($("#user-userId").val() == ''){
			msgBox.info({content: "请选择需要生日提醒的用户", type:'fail'});
			return;
		}else {
			var tempval = returnValue("user-userId").split(":");
			$("#specialDataForm #infoName").attr('value',tempval[1]);
			//$("#specialDataForm #infoName").html(tempval[1]);
			//alert($("#specialDataForm").serializeArray());
		}
	}else {
		$("#specialDataForm #infoName").val($("#tempinfoName").val());
	}
	if(specialDataModule.subState)return;
		specialDataModule.subState = true;
	if ($("#specialDataForm").valid()) {
		var url = getRootPath()+"/sys/specialData/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/sys/specialData/update.action";
		}
		var formData = $("#specialDataForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				specialDataModule.subState = false;
				$("#token").val(data.token);
				specialDataModule.subtoken = data.token;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						specialDataModule.clearForm();
					}
					
					specialData.specialDataList();
				} else {
					if(data.labelErrorMessage){
						showErrors("specialDataForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
					
				}
			},
			error : function() {
				specialDataModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		specialDataModule.subState = false;
	}
};

//清空表单数据
specialDataModule.clearForm = function(){
	$('#specialDataForm')[0].reset();
	hideErrorMessage();
};
  	



$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	
	
	$("#saveClose").click(function(){specialDataModule.saveOrModify(true);});
	//$("#saveOrModify").click(function(){specialDataModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});