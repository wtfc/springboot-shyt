//初始化方法
var toaMachineRestartEngineModule = {};
//重复提交标识
toaMachineRestartEngineModule.subState = false;
//获取修改信息
toaMachineRestartEngineModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaMachineRestart/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaMachineRestartEngineModule.clearForm();
				$("#toaMachineRestartEngineForm").fill(data);
				$("#equipmentNumber").html(data.equipmentNumber);
				$("#companyName").html(data.companyName);
				$("#applicant").html(data.applicant);
				$("#billDate").html(data.billDate);
				$("#chargeExamine").html(data.chargeExamine);
				$("#chargeInformation").html(data.chargeInformation);
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
				$("#cabinet").html(data.cabinet);
				$("#ip").html(data.ip);
				$("#sn").html(data.sn);
				$("#brand").html(data.brand);
				$("#modelNumber").html(data.modelNumber);
				$("#remark").html(data.remark);
				$("#warnDate").html(data.warnDate);
				$("#operateDate").html(data.operateDate);
				$("#endDate").html(data.endDate);
				$("#scanCode").html(data.scanCode);
				$("#caozgcs").html(data.caozgcs);
				$("#assist").html(data.assist);
				$("#isWrong").html(data.isWrong);
				$("#isMonitor").html(data.isMonitor);
				$("#isOvertime").html(data.isOvertime);
				if(data.firstRestart==1){
					$("#firstRestart").html("是");
				}else{
					$("#firstRestart").html("否");
				}
				
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(true);
				echoAttachList(false);
			}
		}
	});
};

//添加留言
toaMachineRestartEngineModule.restartExchange = function (hide) {
	if(toaMachineRestartEngineModule.subState)return;
		toaMachineRestartEngineModule.subState = true;
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
				toaMachineRestartEngineModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/loadEngine.action?id="+ids+"", "", "/machine/toaMachineRestart/loadEngine.action?id="+ids+"");
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
				toaMachineRestartEngineModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartEngineModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//添加修改公用方法(机房主管)
toaMachineRestartEngineModule.saveOrModifyEngine = function (hide) {
	if(toaMachineRestartEngineModule.subState)return;
		toaMachineRestartEngineModule.subState = true;
	if ($("#toaMachineRestartEngineForm").valid()) {
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaMachineRestart/updateEngine.action";
		}
		var formData = $("#toaMachineRestartEngineForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineRestartEngineModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/manageEngine.action","","/machine/toaMachineRestart/manageEngine.action");
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
				toaMachineRestartEngineModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartEngineModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
toaMachineRestartEngineModule.clearForm = function(){
	$('#toaMachineRestartEngineForm')[0].reset();
	hideErrorMessage();
};