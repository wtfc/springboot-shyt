
//初始化方法
var equipmentLangFangModule = {};

//重复提交标识
equipmentLangFangModule.subState = false;

//显示表单弹出层
equipmentLangFangModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentLangFangModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentLangFangModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentLangFangModule.clearForm();
				$("#equipmentLangFangForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentLangFangModule.saveOrModify = function (hide) {
	if(equipmentLangFangModule.subState)return;
		equipmentLangFangModule.subState = true;
	if ($("#equipmentLangFangForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentLangFangForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentLangFangModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentLangFangModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentLangFang.equipmentLangFangList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentLangFangForm", data.labelErrorMessage);
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
				equipmentLangFangModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentLangFangModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentLangFangModule.clearForm = function(){
	$('#equipmentLangFangForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentLangFangModule.getWorkTask=function(){
	equipmentLangFang.backFnServerParams = function(aoData){
		 getTableParameters(equipmentLangFang.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentLangFang.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentLangFangModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentLangFang.backTable == null) {
		equipmentLangFang.backTable = $('#equipmentLangFangTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentLangFang.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentLangFang.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentLangFang.backTable.fnDraw();
	}
};

equipmentLangFangModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentLangFangModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentLangFangModule.getWorkTask();
};
equipmentLangFangModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentLangFangModule.initInfo();
	$("#saveAndClose").click(function(){equipmentLangFangModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentLangFangModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});