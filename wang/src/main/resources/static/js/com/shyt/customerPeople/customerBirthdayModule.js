
//初始化方法
var customerPeopleModule = {};
var customerPeople = {};
//重复提交标识
customerPeopleModule.subState = false;
//获取修改信息
customerPeopleModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/customerPeople/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				customerPeopleModule.clearForm();
				$("#customerPeopleForm").fill(data);
//				$('#businessId').val(data.id);
//				$('#businessSource').val('0');
//				showAttachList(false);
//				echoAttachList(false);
			}
		}
	});
};

//添加修改公用方法
customerPeopleModule.saveOrModify = function (hide) {
	if(customerPeopleModule.subState)return;
		customerPeopleModule.subState = true;
	if ($("#customerPeopleForm").valid()) {
		var url = getRootPath()+"/shyt/customerPeople/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/customerPeople/update.action";
		}
		var formData = $("#customerPeopleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				customerPeopleModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/customerPeople/birthday.action","","/shyt/customerPeople/birthday.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("customerPeopleForm", data.labelErrorMessage);
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
				customerPeopleModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		customerPeopleModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
customerPeopleModule.clearForm = function(){
	$('#customerPeopleForm')[0].reset();
	hideErrorMessage();
};
//提取公司名称
customerPeopleModule.getWorkTask=function(){
	customerPeople.backFnServerParams = function(aoData){
		 getTableParameters(customerPeople.backTable, aoData);
		 var companyName = $("#customerForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	customerPeople.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"customerPeopleModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (customerPeople.backTable == null) {
		customerPeople.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": customerPeople.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				customerPeople.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		customerPeople.backTable.fnDraw();
	}
};
customerPeopleModule.assignment=function(t,s){
	$("#customerId").val(t);
	$("#companyName").val(s);
};
customerPeopleModule.closeWin=function(){
	$("#customerId").val("");
	$("#companyName").val("");
	customerPeopleModule.getWorkTask();
};
customerPeopleModule.queryReset = function(){
	$('#customerForm')[0].reset();
};