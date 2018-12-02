//初始化方法
var toaProductCloudModule = {};
var toaProductCloud={};
//重复提交标识
toaProductCloudModule.subState = false;
//获取修改信息
toaProductCloudModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/product/toaProductCloud/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaProductCloudModule.clearForm();
				$("#toaProductCloudForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaProductCloudModule.saveOrModify = function (hide) {
	if(toaProductCloudModule.subState)return;
		toaProductCloudModule.subState = true;
	if ($("#toaProductCloudForm").valid()) {
		var url = getRootPath()+"/product/toaProductCloud/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/product/toaProductCloud/update.action";
		}
		var formData = $("#toaProductCloudForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaProductCloudModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/product/toaProductCloud/manage.action","","/product/toaProductCloud/manage.action");
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
				toaProductCloudModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaProductCloudModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaProductCloudModule.clearForm = function(){
	$('#toaProductCloudForm')[0].reset();
	hideErrorMessage();
};
//提取公司名称
toaProductCloudModule.getWorkTask=function(){
	toaProductCloud.backFnServerParams = function(aoData){
		 getTableParameters(toaProductCloud.backTable, aoData);
		 var companyName = $("#customerForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	toaProductCloud.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"toaProductCloudModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (toaProductCloud.backTable == null) {
		toaProductCloud.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaProductCloud.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				toaProductCloud.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		toaProductCloud.backTable.fnDraw();
	}
};
toaProductCloudModule.assignment=function(t,s){
	$("#customersId").val(t);
	$("#customersName").val(s);
};
toaProductCloudModule.closeWin=function(){
	$("#customersId").val("");
	$("#customersName").val("");
	toaProductCloudModule.getWorkTask();
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
