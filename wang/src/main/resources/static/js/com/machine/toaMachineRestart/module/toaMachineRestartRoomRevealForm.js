//初始化方法
var toaMachineRestartRoomRevealModule = {};
//重复提交标识
toaMachineRestartRoomRevealModule.subState = false;
//获取修改信息
toaMachineRestartRoomRevealModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaMachineRestart/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaMachineRestartRoomRevealModule.clearForm();
				$("#toaMachineRestartRoomRevealForm").fill(data);
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
				//$("#extStr2").html(data.extStr2);
				$("#contactWay").html(data.contactWay);
				$("#ip").html(data.ip);
				$("#warnDate").html(data.warnDate);
				
				//修改
				//$("#extStr3").html(data.extStr3);
				$("#sn").html(data.sn);
				$("#caozgcs").html(data.caozgcs);
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
				$("#recordPeople").html(data.recordPeople);
				$("#scanCode").html(data.scanCode);
				$("#assist").html(data.assist);
				
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(true);
				echoAttachList(false);
			}
		}
	});
};


toaMachineRestartRoomRevealModule.spCall = function(data, controlId) {
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

//添加留言
toaMachineRestartRoomRevealModule.restartExchange = function (hide) {
	if(toaMachineRestartRoomRevealModule.subState)return;
		toaMachineRestartRoomRevealModule.subState = true;
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
				toaMachineRestartRoomRevealModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/loadRoomReveal.action?id="+ids+"", "", "/machine/toaMachineRestart/loadRoomReveal.action?id="+ids+"");
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
				toaMachineRestartRoomRevealModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartRoomRevealModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//添加附件
toaMachineRestartRoomRevealModule.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};

//添加修改公用方法(机房)
toaMachineRestartRoomRevealModule.saveOrModifyRoomReveal = function (hide) {
	if(toaMachineRestartRoomRevealModule.subState)return;
		toaMachineRestartRoomRevealModule.subState = true;
	if ($("#toaMachineRestartRoomRevealForm").valid()) {
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaMachineRestart/updateRoomReveal.action";
		}
		var formData = $("#toaMachineRestartRoomRevealForm").serializeArray();
		toaMachineRestartRoomRevealModule.addFormParameters(formData);
		formData.push({"name":"extStr2","value":$(".template-download").find("input[name='downloadItemUrl']").val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineRestartRoomRevealModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/manageRoom.action","","/machine/toaMachineRestart/manageRoom.action");
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
				toaMachineRestartRoomRevealModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartRoomRevealModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
toaMachineRestartRoomRevealModule.clearForm = function(){
	$('#toaMachineRestartRoomRevealForm')[0].reset();
	clearTable();
	hideErrorMessage();
};

//清空重复加载的附件名称
function clearTabale(){
	clearTable();
}