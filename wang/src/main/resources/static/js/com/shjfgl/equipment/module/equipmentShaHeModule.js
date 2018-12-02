
//初始化方法
var equipmentShaHeModule = {};

//重复提交标识
equipmentShaHeModule.subState = false;

//显示表单弹出层
equipmentShaHeModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentShaHeModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentShaHeModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentShaHeModule.clearForm();
				$("#equipmentShaHeForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentShaHeModule.saveOrModify = function (hide) {
	if(equipmentShaHeModule.subState)return;
		equipmentShaHeModule.subState = true;
	if ($("#equipmentShaHeForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentShaHeForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentShaHeModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentShaHeModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentShaHe.equipmentShaHeList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentShaHeForm", data.labelErrorMessage);
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
				equipmentShaHeModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentShaHeModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentShaHeModule.clearForm = function(){
	$('#equipmentShaHeForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentShaHeModule.getWorkTask=function(){
	equipmentShaHe.backFnServerParams = function(aoData){
		 getTableParameters(equipmentShaHe.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentShaHe.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentShaHeModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentShaHe.backTable == null) {
		equipmentShaHe.backTable = $('#equipmentShaHeTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentShaHe.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentShaHe.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentShaHe.backTable.fnDraw();
	}
};

equipmentShaHeModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentShaHeModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentShaHeModule.getWorkTask();
};
equipmentShaHeModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentShaHeModule.initInfo();
	$("#saveAndClose").click(function(){equipmentShaHeModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentShaHeModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});