
//初始化方法
var equipmentYangQiaoModule = {};

//重复提交标识
equipmentYangQiaoModule.subState = false;

//显示表单弹出层
equipmentYangQiaoModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentYangQiaoModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentYangQiaoModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentYangQiaoModule.clearForm();
				$("#equipmentYangQiaoForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentYangQiaoModule.saveOrModify = function (hide) {
	if(equipmentYangQiaoModule.subState)return;
		equipmentYangQiaoModule.subState = true;
	if ($("#equipmentYangQiaoForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentYangQiaoForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentYangQiaoModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentYangQiaoModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentYangQiao.equipmentYangQiaoList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentYangQiaoForm", data.labelErrorMessage);
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
				equipmentYangQiaoModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentYangQiaoModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentYangQiaoModule.clearForm = function(){
	$('#equipmentYangQiaoForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentYangQiaoModule.getWorkTask=function(){
	equipmentYangQiao.backFnServerParams = function(aoData){
		 getTableParameters(equipmentYangQiao.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentYangQiao.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentYangQiaoModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentYangQiao.backTable == null) {
		equipmentYangQiao.backTable = $('#equipmentYangQiaoTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentYangQiao.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentYangQiao.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentYangQiao.backTable.fnDraw();
	}
};

equipmentYangQiaoModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentYangQiaoModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentYangQiaoModule.getWorkTask();
};
equipmentYangQiaoModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentYangQiaoModule.initInfo();
	$("#saveAndClose").click(function(){equipmentYangQiaoModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentYangQiaoModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});