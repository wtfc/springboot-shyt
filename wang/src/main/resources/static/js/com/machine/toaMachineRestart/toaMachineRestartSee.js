//初始化方法
var toaMachineRestartSee = {};
//重复提交标识
toaMachineRestartSee.subState = false;
//获取修改信息
toaMachineRestartSee.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaMachineRestart/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$('#toaMachineRestartSee')[0].reset();
				$("#toaMachineRestartSee").fill(data);
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
				$("#regionExamine").html(data.regionExamine);
				$("#regionInformation").html(data.regionInformation);
				$("#scanCode").html(data.scanCode);
				//$("#caozgcs").html(data.caozgcs);
				$("#extStr4").html(data.extStr4);
				if(data.extStr2!=null){
					$("#extStr2").html(data.extStr2+"分");
				}else{
					$("#extStr2").html(data.extStr2);
				}

				if(data.firstRestart==1){
					$("#firstRestart").html("是");
				}else{
					$("#firstRestart").html("否");
				}
				$("#isOvertime").html(data.isOvertime);
				$("#isMonitor").html(data.isMonitor);
				$("#isWrong").html(data.isWrong);
				$("#assist").html(data.assist);
				
				
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(true);
				echoAttachList(false);
			}
		}
	});
};


//添加留言
toaMachineRestartSee.restartExchange = function (hide) {
	if(toaMachineRestartSee.subState)return;
	toaMachineRestartSee.subState = true;
	if ($("#toaRestartExchange").valid()) {
		var ids=$("#id").val();
		var url = getRootPath()+"/machine/toaMachineExchange/save.action";
		var formData = $("#toaRestartExchange").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineRestartSee.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/loadSee.action?id="+ids+"", "", "/machine/toaMachineRestart/loadSee.action?id="+ids+"");
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
				toaMachineRestartSee.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartSee.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
toaMachineRestartSee.clearForm = function(){
	$('#toaMachineRestartSee')[0].reset();
	hideErrorMessage();
};
