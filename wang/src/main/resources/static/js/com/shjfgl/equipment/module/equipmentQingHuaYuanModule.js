
//初始化方法
var equipmentQingHuaYuanModule = {};

//重复提交标识
equipmentQingHuaYuanModule.subState = false;

//显示表单弹出层
equipmentQingHuaYuanModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentQingHuaYuanModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentQingHuaYuanModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentQingHuaYuanModule.clearForm();
				$("#equipmentQingHuaYuanForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentQingHuaYuanModule.saveOrModify = function (hide) {
	if(equipmentQingHuaYuanModule.subState)return;
		equipmentQingHuaYuanModule.subState = true;
	if ($("#equipmentQingHuaYuanForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentQingHuaYuanForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentQingHuaYuanModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentQingHuaYuanModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentQingHuaYuan.equipmentQingHuaYuanList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentQingHuaYuanForm", data.labelErrorMessage);
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
				equipmentQingHuaYuanModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentQingHuaYuanModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentQingHuaYuanModule.clearForm = function(){
	$('#equipmentQingHuaYuanForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentQingHuaYuanModule.getWorkTask=function(){
	equipmentQingHuaYuan.backFnServerParams = function(aoData){
		 getTableParameters(equipmentQingHuaYuan.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentQingHuaYuan.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentQingHuaYuanModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentQingHuaYuan.backTable == null) {
		equipmentQingHuaYuan.backTable = $('#equipmentQingHuaYuanTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentQingHuaYuan.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentQingHuaYuan.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentQingHuaYuan.backTable.fnDraw();
	}
};

equipmentQingHuaYuanModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentQingHuaYuanModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentQingHuaYuanModule.getWorkTask();
};
equipmentQingHuaYuanModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentQingHuaYuanModule.initInfo();
	$("#saveAndClose").click(function(){equipmentQingHuaYuanModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentQingHuaYuanModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});