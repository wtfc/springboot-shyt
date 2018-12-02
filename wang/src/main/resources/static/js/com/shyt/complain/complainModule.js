
//初始化方法
var complainModule = {};
var complain = {};
var userEdit = {};
//重复提交标识
complainModule.subState = false;
//获取修改信息
complainModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/complain/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				complainModule.clearForm();
				$("#complainForm").fill(data);
//				$('#businessId').val(data.id);
//				$('#businessSource').val('0');
//				showAttachList(false);
//				echoAttachList(false);
			}
		}
	});
};
complainModule.showDeptTree = function (){
	$('#myModal').modal('show');
	//deptTree2 = new deptTree();
	//deptTree2.show("deptTreeDiv", "userForm #deptId", "userForm #deptName",null,false);
	userDeptTree.show("deptTreeDiv", "complainForm #deptId", "complainForm #partment",null,false);
};

userEdit.validateDeptName = function(){
	if($("#complainForm #partment").val() == ""){
		userEdit.subState = false;
		$("#deptError").html("此信息不能为空").show();
		return true;
	} else {
		$("#deptError").hide();
		return false;
	}
};
//添加修改公用方法
complainModule.saveOrModify = function (hide) {
	if(complainModule.subState)return;
		complainModule.subState = true;
	if ($("#complainForm").valid()) {
		var url = getRootPath()+"/shyt/complain/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/complain/update.action";
		}
		var formData = $("#complainForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				complainModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/complain/manage.action","","/shyt/complain/manage.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("complainForm", data.labelErrorMessage);
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
				complainModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		complainModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
complainModule.clearForm = function(){
	$('#complainForm')[0].reset();
	hideErrorMessage();
};
//提取公司名称
complainModule.getWorkTask=function(){
	complain.backFnServerParams = function(aoData){
		 getTableParameters(complain.backTable, aoData);
		 var companyName = $("#customerForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	complain.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"complainModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (complain.backTable == null) {
		complain.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": complain.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				complain.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		complain.backTable.fnDraw();
	}
};
complainModule.assignment=function(t,s){
	$("#customerId").val(t);
	$("#companyName").val(s);
};
complainModule.closeWin=function(){
	$("#customerId").val("");
	$("#companyName").val("");
	complainModule.getWorkTask();
};
complainModule.queryReset = function(){
	$('#customerForm')[0].reset();
};
jQuery(function($) 
		{
			$("#showDeptTree").click(complainModule.showDeptTree);
});