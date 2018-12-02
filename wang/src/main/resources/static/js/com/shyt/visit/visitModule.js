
//初始化方法
var visitModule = {};

var visit = {};
//重复提交标识
visitModule.subState = false;
//获取修改信息
visitModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/visit/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				visitModule.clearForm();
				//selectControl.init("controlTree", "status-status", true, false, 0);	//组织多选
				$("#visitForm").fill(data);
//				if(data.deptName && data.deptNameValue){
//					$("#deptNameForm-deptName").select2("data", {id:data.deptName,text:data.deptNameValue});
//				}
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(true);
				echoAttachList(false);
			}
		}
	});
};
visitModule.spCall = function(data, controlId) {
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

visitModule.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};
//添加修改公用方法
visitModule.saveOrModify = function (hide) {
	if(visitModule.subState)return;
		visitModule.subState = true;
	if ($("#visitForm").valid()) {
		var url = getRootPath()+"/shyt/visit/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/visit/update.action";
		}
		var formData = $("#visitForm").serializeArray();
		visitModule.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				visitModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/visit/manage.action","","/shyt/visit/manage.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("visitForm", data.labelErrorMessage);
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
				visitModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		visitModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//清空表单数据
visitModule.clearForm = function(){
	$('#visitForm')[0].reset();
	hideErrorMessage();
};

//提取公司名称
visitModule.getWorkTask=function(){
	visit.backFnServerParams = function(aoData){
		 getTableParameters(visit.backTable, aoData);
		 var companyName = $("#customerForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	visit.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"visitModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (visit.backTable == null) {
		visit.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": visit.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				visit.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		visit.backTable.fnDraw();
	}
};
visitModule.assignment=function(t,s){
	$("#customerId").val(t);
	$("#companyName").val(s);
};
visitModule.closeWin=function(){
	$("#customerId").val("");
	$("#companyName").val("");
	visitModule.getWorkTask();
};
visitModule.queryReset = function(){
	$('#customerForm')[0].reset();
};
$(document).ready(function($) {
	selectControl.init("controlTree", "status-statuss", true, false, null);	//组织多选
});
