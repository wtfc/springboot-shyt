//初始化方法
var userEdit = {};
var customerInfo = {};
var shytCustomer = {};
//重复提交标识
customerInfo.subState = false;
//获取修改信息
customerInfo.getCustomer = function () {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/customer/customerInfo/getCustomer.action",
		data : null,
		dataType : "json",
		success : function(data) {
				if (data) {
					//清除验证信息
					hideErrorMessage();
					//customerInfo.clearForm();
					$("#toaShytCustomerForm").fill(data);
					$("#companyName").html(data.companyName);
					$("#memberType").html(data.memberType);
					//$("#serviceType").html(data.serviceType);
					$("#trade").html(data.trade);
					//$("#machine").html(data.machine);
					$("#address").html(data.address);
					$("#department").html(data.department);
					$("#sale").html(data.sale);
					
					$("#tradeUser").html(data.tradeUser);
					$("#linkUser").html(data.linkUser);
					$("#newAddress").html(data.newAddress);
					$("#taxid").html(data.taxid);
					$("#bankName").html(data.bankName);
					$("#bankNo").html(data.bankNo);
					$("#ticketFlag").html(data.ticketFlag);
					$("#overflowCategory").html(data.overflowCategory);
					$("#dailiName").html(data.dailiName);
					$("#startIntel").html(data.startIntel);
					$("#endIntel").html(data.endIntel);
					$("#rating").html(data.rating);
				}
		}
	});
};

customerInfo.getCustomerPeople = function () {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/customer/customerInfo/getCustomerPeople.action",
		data : null,
		dataType : "json",
		success : function(data) {
				if (data) {
					//清除验证信息
					hideErrorMessage();
					//customerInfo.clearForm();
					$("#toaShytCustomerForm").fill(data);
					
					$("#name").html(data.name);
					$("#job").html(data.job);
					$("#tel").html(data.tel);
					$("#idCard").html(data.idCard);
					$("#email").html(data.email);
					$("#weixin").html(data.weixin);
				}
		}
	});
};

$(document).ready(function(){
	
	customerInfo.getCustomer();
		
	customerInfo.getCustomerPeople();
	
});