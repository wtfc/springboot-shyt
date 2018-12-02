//初始化方法
var toaMachineRestartRoomShowModule = {};
//重复提交标识
toaMachineRestartRoomShowModule.subState = false;
//获取修改信息
toaMachineRestartRoomShowModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/toaMachineRestart/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaMachineRestartRoomShowModule.clearForm();
				$("#toaMachineRestartRoomShowForm").fill(data);
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
				$("#scanCode").html(data.scanCode);
				//$("#caozgcs").html(data.caozgcs);
				$("#extStr4").html(data.extStr4);
				  
				//是重复重启状态下，设置   是否重复重启为：是    是否接显示器：是      add-wangtf
				$("#firstRestart").html("是");
				$("#isMonitor").html("是");
				
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(true);
				echoAttachList(false);
			}
		}
	});
};


toaMachineRestartRoomShowModule.spCall = function(data, controlId) {
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
toaMachineRestartRoomShowModule.restartExchange = function (hide) {
	if(toaMachineRestartRoomShowModule.subState)return;
		toaMachineRestartRoomShowModule.subState = true;
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
				toaMachineRestartRoomShowModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/machine/toaMachineRestart/loadRoomShow.action?id="+ids+"", "", "/machine/toaMachineRestart/loadRoomShow.action?id="+ids+"");
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
				toaMachineRestartRoomShowModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartRoomShowModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//添加附件
toaMachineRestartRoomShowModule.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};

//添加修改公用方法(机房)
toaMachineRestartRoomShowModule.saveOrModifyRoomShow = function (hide) {
	if(toaMachineRestartRoomShowModule.subState)return;
		toaMachineRestartRoomShowModule.subState = true;
	if ($("#toaMachineRestartRoomShowForm").valid()) {
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/toaMachineRestart/updateRoomShow.action";
		}
		var formData = $("#toaMachineRestartRoomShowForm").serializeArray();
		toaMachineRestartRoomShowModule.addFormParameters(formData);
		formData.push({"name":"extStr3","value":$(".template-download").find("input[name='downloadItemUrl']").val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaMachineRestartRoomShowModule.subState = false;
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
				toaMachineRestartRoomShowModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartRoomShowModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
toaMachineRestartRoomShowModule.clearForm = function(){
	$('#toaMachineRestartRoomShowForm')[0].reset();
	clearTabale();
	hideErrorMessage();
};

//清空重复加载的附件名称
function clearTabale(){
	clearTable();
}

$(document).ready(function(){
	//页面初始化，默认隐藏上传附件和是否报错部分
	
	$("#attachs").show();
	$("#attachList").show();
	$("#labelIsWrong").show();
	$("#isWrong").show();
});