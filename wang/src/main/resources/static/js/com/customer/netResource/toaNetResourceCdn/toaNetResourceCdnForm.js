//初始化方法
var toaProductCdnModule = {};
var toaProductCdn={};
//重复提交标识
toaProductCdnModule.subState = false;
//获取修改信息
toaProductCdnModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/customer/ToaNetResourceCdn/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaProductCdnModule.clearForm();
				$("#customersName").html(data.customersName);
				$("#extStr1").html(data.extStr1);
				$("#supporter").html(data.supporter);
				$("#userName").html(data.userName);
				$("#chargeMode").html(data.chargeMode);
				$("#chargeTime").html(data.chargeTime);
				$("#startDate").html(data.startDate);
				$("#startEnd").html(data.startEnd);
				$("#price").html(data.price);
				$("#flooredNumber").html(data.flooredNumber);
				$("#chargeNumber").html(data.chargeNumber);
				$("#amount").html(data.amount);
				$("#stick").html(data.stick);
				$("#remark").html(data.remark);
			}
		}
	});
};

//清空表单数据
toaProductCdnModule.clearForm = function(){
	$('#toaProductCdnForm')[0].reset();
	hideErrorMessage();
};

toaProductCdnModule.queryReset = function(){
	$('#customerForm')[0].reset();
};
toaProductCdnModule.spCall = function(data, controlId) {
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