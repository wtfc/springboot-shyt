//初始化方法
var toaMachineRestartDirectorModule = {};
//重复提交标识
toaMachineRestartDirectorModule.subState = false;
//获取修改信息
toaMachineRestartDirectorModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaMachineRestart/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaMachineRestartDirectorModule.clearForm();
				$("#toaMachineRestartDirectorForm").fill(data);
				$("#equipmentNumber").html(data.equipmentNumber);
				$("#companyName").html(data.companyName);
				$("#billDate").html(data.billDate);
				if(data.machina=="room_0"){
					$("#machina").html("鲁谷机房");
				}else if(data.machina=="room_1"){
					$("#machina").html("清华园机房");
				}else if(data.machina=="room_2"){
					$("#machina").html("兆维机房");
				}else if(data.machina=="room_3"){
					$("#machina").html("沙河机房");
				}else if(data.machina=="room_4"){
					$("#machina").html("看丹桥机房");
				}else if(data.machina=="room_5"){
					$("#machina").html("廊坊机房");
				}else if(data.machina=="room_6"){
					$("#machina").html("富丰桥机房");
				}else if(data.machina=="room_7"){
					$("#machina").html("大族机房");
				}else if(data.machina=="room_8"){
					$("#machina").html("洋桥机房");
				}else if(data.machina=="room_9"){
					$("#machina").html("东四机房");
				}else if(data.machina=="room_10"){
					$("#machina").html("山东机房");
				}else if(data.machina=="room_11"){
					$("#machina").html("揽信机房");
				}else{
					$("#machina").html("专线");
				}
				$("#contact").html(data.contact);
				$("#tel").html(data.tel);
				$("#extStr2").html(data.extStr2);
				$("#ip").html(data.ip);
				$("#warnDate").html(data.warnDate);
				
				if(data.isMonitor=="0"){
					$("#isMonitor").html("否");
				}else{
					$("#isMonitor").html("是");
				}
				$("#deviceStatus").html(data.deviceStatus);
				if(data.isWrong=="0"){
					$("#isWrong").html("否");
				}else{
					$("#isWrong").html("是");
				}
				$("#errorMessage").html(data.errorMessage);
				if(data.errorRepair=="0"){
					$("#errorRepair").html("否");
				}else{
					$("#errorRepair").html("是");
				}
				$("#operateDate").html(data.operateDate);
				if(data.isOvertime=="0"){
					$("#isOvertime").html("否");
				}else{
					$("#isOvertime").html("是");
				}
				$("#description").html(data.description);
				$("#caozgcs").html(data.caozgcs);
				$("#endDate").html(data.endDate);
				$("#recordPeople").html(data.recordPeople);
				if(data.chargeExamine=="0"){
					$("#chargeExamine").html("不通过");
				}else{
					$("#chargeExamine").html("通过");
				}
				$("#chargeInformation").html(data.chargeInformation);
			}
		}
	});
};

//添加留言
toaMachineRestartDirectorModule.restartExchange = function (hide) {
	if(toaMachineRestartDirectorModule.subState)return;
		toaMachineRestartDirectorModule.subState = true;
	if ($("#toaMachineRestartExchange").valid()) {
		var ids=$("#id").val();
		var url = getRootPath()+"/machine/toaMachineExchange/save.action";
		var formData = $("#toaMachineRestartExchange").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineRestartDirectorModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/loadDirector.action?id="+ids+"", "", "/machine/toaMachineRestart/loadDirector.action?id="+ids+"");
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
				toaMachineRestartDirectorModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartDirectorModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//添加修改公用方法(区域主管)
toaMachineRestartDirectorModule.saveOrModifyDirector = function (hide) {
	if(toaMachineRestartDirectorModule.subState)return;
		toaMachineRestartDirectorModule.subState = true;
	if ($("#toaMachineRestartDirectorForm").valid()) {
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaMachineRestart/updateDirector.action";
		}
		var formData = $("#toaMachineRestartDirectorForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineRestartDirectorModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/manageDirector.action","","/machine/toaMachineRestart/manageDirector.action");
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
				toaMachineRestartDirectorModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartDirectorModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
toaMachineRestartDirectorModule.clearForm = function(){
	$('#toaMachineRestartDirectorForm')[0].reset();
	hideErrorMessage();
};