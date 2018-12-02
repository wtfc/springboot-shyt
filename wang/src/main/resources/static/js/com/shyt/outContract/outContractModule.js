
//初始化方法
var outContractModule = {};
var customer = {};
//重复提交标识
outContractModule.subState = false;
//获取修改信息
outContractModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/outContract/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				outContractModule.clearForm();
				$("#outContractForm").fill(data);
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(true);
				echoAttachList(false);
			}
		}
	});
};
outContractModule.spCall = function(data, controlId) {
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

outContractModule.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};
//添加修改公用方法
outContractModule.saveOrModify = function (hide) {
	if(outContractModule.subState)return;
		outContractModule.subState = true;
	if ($("#outContractForm").valid()) {
		var url = getRootPath()+"/shyt/outContract/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/outContract/update.action";
		}
		var formData = $("#outContractForm").serializeArray();
		outContractModule.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				outContractModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/outContract/manage.action","","/shyt/outContract/manage.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("outContractForm", data.labelErrorMessage);
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
				outContractModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		outContractModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//添加附件
//outContractModule.addFormParameters = function (formData){
//	var fileids = new Array();
//	$.each($("input[name='fileid']"), function(i, o) {
//		fileids.push(o.value);
//	});
//	formData.push({"name": "fileids", "value": fileids});
//	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
//};
//清空表单数据
outContractModule.clearForm = function(){
	$('#outContractForm')[0].reset();
	hideErrorMessage();
};

//提取公司名称
outContractModule.getWorkTask=function(){
	customer.backFnServerParams = function(aoData){
		 getTableParameters(customer.backTable, aoData);
		// aoData.push({ "name": "#customerQueryForm companyName", "value": companyName});//任务模板查询创建人为当前登录人
//		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	customer.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"outContractModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (customer.backTable == null) {
		customer.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/customer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": customer.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				customer.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		customer.backTable.fnDraw();
	}
};
outContractModule.assignment=function(t,s){
	$("#customerId").val(t);
	$("#companyName").val(s);
};
outContractModule.closeWin=function(){
	$("#customerId").val("");
	$("#companyName").val("");
	outContractModule.getWorkTask();
};