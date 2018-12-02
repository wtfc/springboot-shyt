
//初始化方法
var timeSetModule = {};

//重复提交标识
timeSetModule.subState = false;

//显示表单弹出层
timeSetModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	timeSetModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
timeSetModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/hr/timeSet/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				timeSetModule.clearForm();
				$("#timeSetForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
timeSetModule.saveOrModify = function (hide) {
	if(timeSetModule.subState)return;
		timeSetModule.subState = true;
	var chk_value="";
	$("input[type=checkbox][name=publicHolidayStatusShow]:checked").each(function(){
		chk_value+=$(this).val()+',';
	});
	$("#publicHolidayStatus").val(chk_value);
	chk_value="";
	if($("#beLateTime").val()>$("#leaveEarlyTime").val()){
		timeSetModule.subState = false;
		msgBox.info({
			content:"迟到时间必须早于早退时间"
		});
		return;
	}
	if($("#beLateTime").val()<$("#officeHours").val()){
		timeSetModule.subState = false;
		msgBox.info({
			content:"迟到时间必须晚于上班时间"
		});
		return;
	}
	if($("#leaveEarlyTime").val()>$("#closingTime").val()){
		timeSetModule.subState = false;
		msgBox.info({
			content:"早退时间必须早于下班时间"
		});
		return;
	}
	if ($("#timeSetForm").valid()) {
		var url = getRootPath()+"/hr/timeSet/save.action";
		var urlStatus="save";
		if ($("#id").val() != 0&&$("#isFailure").val()==1) {
			url = getRootPath()+"/hr/timeSet/update.action";
			urlStatus="update"
		}
		if(urlStatus=="save"){
			$("#isFailure").val("1");
		}
		var formData = $("#timeSetForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				timeSetModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					/*if (hide) {
						$('#myModal-edit').modal('hide');
					} else {*/
						timeSetModule.clearForm();
					//}
					$("#token").val(data.token);
					loadrightmenu("/hr/timeSet/manage.action");
					//timeSet.timeSetList();
				} else {
					if(data.labelErrorMessage){
						showErrors("timeSetForm", data.labelErrorMessage);
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
				timeSetModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		timeSetModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
timeSetModule.clearForm = function(){
	$('#timeSetForm')[0].reset();
	hideErrorMessage();
};
//时间空间去除提示文字
function blurInput(e){
	this.parent().find("label").remove();
	this.removeClass("error");
}
//初始化数据
timeSetModule.initInfo = function(){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/hr/timeSet/loadForm.action",
		dataType : "json",
		success : function(data) {
			if (data) {
				console.log(data);
				$("#timeSetForm").fill(data.timeSet);
				$("#token").val(data.token);
				$("#nowDate").val(data.nowDate);;
				var phsValue = $("#publicHolidayStatus").val();
				if(phsValue.length>0){
					phsValue=phsValue.split(",");
					for(var i=0;i<phsValue.length-1;i++){
						$("[name=publicHolidayStatusShow][value="+phsValue[i]+"]").attr("checked",true);
					}
				}
				/*$("#saveOrModify").hide();*/
				/*$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));*/
				/*$('#myModal-edit').modal('show');*/
			}
		}
	});
}


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
	timeSetModule.initInfo();
	$("#saveAndClose").click(function(){timeSetModule.saveOrModify(true);});
	//$("#saveOrModify").click(function(){timeSetModule.saveOrModify(false);});
	//$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});