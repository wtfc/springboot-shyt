//初始化方法
var toaProductCdnModule = {};
var toaProductCdn={};
//重复提交标识
toaProductCdnModule.subState = false;
//获取修改信息
toaProductCdnModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/product/toaProductCdn/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaProductCdnModule.clearForm();
				$("#toaProductCdnForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaProductCdnModule.saveOrModify = function (hide) {
	if(toaProductCdnModule.subState)return;
		toaProductCdnModule.subState = true;
	if ($("#toaProductCdnForm").valid()) {
		var url = getRootPath()+"/product/toaProductCdn/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/product/toaProductCdn/update.action";
		}
		var formData = $("#toaProductCdnForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaProductCdnModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/product/toaProductCdn/manage.action","","/product/toaProductCdn/manage.action");
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
				toaProductCdnModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaProductCdnModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaProductCdnModule.clearForm = function(){
	$('#toaProductCdnForm')[0].reset();
	hideErrorMessage();
};
//提取公司名称
toaProductCdnModule.getWorkTask=function(){
	toaProductCdn.backFnServerParams = function(aoData){
		 getTableParameters(toaProductCdn.backTable, aoData);
		 var companyName = $("#customerForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	toaProductCdn.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"toaProductCdnModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (toaProductCdn.backTable == null) {
		toaProductCdn.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaProductCdn.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				toaProductCdn.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		toaProductCdn.backTable.fnDraw();
	}
};
toaProductCdnModule.assignment=function(t,s){
	$("#customersId").val(t);
	$("#customersName").val(s);
};
toaProductCdnModule.closeWin=function(){
	$("#customersId").val("");
	$("#customersName").val("");
	toaProductCdnModule.getWorkTask();
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