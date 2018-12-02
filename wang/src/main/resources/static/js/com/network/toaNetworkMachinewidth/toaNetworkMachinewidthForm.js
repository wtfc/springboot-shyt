//初始化方法
var toaNetworkMachinewidthModule = {};
var companyModule={};
var company={};
//重复提交标识
toaNetworkMachinewidthModule.subState = false;
//获取修改信息
toaNetworkMachinewidthModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/network/toaNetworkMachinewidth/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaNetworkMachinewidthModule.clearForm();
				$("#toaNetworkMachinewidthForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaNetworkMachinewidthModule.saveOrModify = function (hide) {
	if(toaNetworkMachinewidthModule.subState)return;
		toaNetworkMachinewidthModule.subState = true;
	if ($("#toaNetworkMachinewidthForm").valid()) {
		var url = getRootPath()+"/network/toaNetworkMachinewidth/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/network/toaNetworkMachinewidth/update.action";
		}
		var formData = $("#toaNetworkMachinewidthForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaNetworkMachinewidthModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/network/toaNetworkMachinewidth/manage.action","","/network/toaNetworkMachinewidth/manage.action");
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
				toaNetworkMachinewidthModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaNetworkMachinewidthModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaNetworkMachinewidthModule.clearForm = function(){
	$('#toaNetworkMachinewidthForm')[0].reset();
	hideErrorMessage();
};

//提取公司名称
companyModule.getWorkTask=function(){
	company.backFnServerParams = function(aoData){
		 getTableParameters(company.backTable, aoData);
		 var companyName = $("#companyForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	company.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"companyModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (company.backTable == null) {
		company.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": company.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				company.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		company.backTable.fnDraw();
	}
};
companyModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#companyName").val(s);
};
companyModule.closeWin=function(){
	$("#companyId").val("");
	$("#companyName").val("");
	companyModule.getWorkTask();
};
companyModule.queryReset = function(){
	$('#companyForm')[0].reset();
};