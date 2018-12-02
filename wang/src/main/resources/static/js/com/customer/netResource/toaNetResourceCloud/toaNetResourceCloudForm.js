//初始化方法
var toaProductCloudModule = {};
var toaProductCloud={};
//重复提交标识
toaProductCloudModule.subState = false;
//获取修改信息
toaProductCloudModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/customer/toaNetResourceCloud/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaProductCloudModule.clearForm();
				$("#customersName").html(data.customersName);
				$("#supporter").html(data.supporter);
				$("#resourceType").html(data.resourceType);
				$("#chargeTime").html(data.chargeTime);
				$("#startDate").html(data.startDate);
				$("#startEnd").html(data.startEnd);
				$("#amount").html(data.amount);
				$("#cpu").html(data.cpu);
				$("#ram").html(data.ram);
				$("#stick").html(data.stick);
				$("#performance").html(data.performance);
				$("#cloudPhoto").html(data.cloudPhoto);
				$("#cloudDive").html(data.cloudDive);
				$("#publicIp").html(data.publicIp);
				$("#bandwidth").html(data.bandwidth);
				$("#router").html(data.router);
				$("#loadBalancer").html(data.loadBalancer);
				$("#remark").html(data.remark);
			}
		}
	});
};

//清空表单数据
toaProductCloudModule.clearForm = function(){
	$('#toaProductCloudForm')[0].reset();
	hideErrorMessage();
};

toaProductCloudModule.queryReset = function(){
	$('#customerForm')[0].reset();
};
toaProductCloudModule.spCall = function(data, controlId) {
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
