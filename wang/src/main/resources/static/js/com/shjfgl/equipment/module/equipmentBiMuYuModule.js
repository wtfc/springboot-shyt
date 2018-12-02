
//初始化方法
var equipmentBiMuYuModule = {};

//重复提交标识
equipmentBiMuYuModule.subState = false;

//显示表单弹出层
equipmentBiMuYuModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentBiMuYuModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentBiMuYuModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentBiMuYuModule.clearForm();
				$("#equipmentBiMuYuForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentBiMuYuModule.saveOrModify = function (hide) {
	if(equipmentBiMuYuModule.subState)return;
		equipmentBiMuYuModule.subState = true;
	if ($("#equipmentBiMuYuForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentBiMuYuForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentBiMuYuModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentBiMuYuModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentBiMuYu.equipmentBiMuYuList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentBiMuYuForm", data.labelErrorMessage);
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
				equipmentBiMuYuModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentBiMuYuModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentBiMuYuModule.clearForm = function(){
	$('#equipmentBiMuYuForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentBiMuYuModule.getWorkTask=function(){
	equipmentBiMuYu.backFnServerParams = function(aoData){
		 getTableParameters(equipmentBiMuYu.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentBiMuYu.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentBiMuYuModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentBiMuYu.backTable == null) {
		equipmentBiMuYu.backTable = $('#equipmentBiMuYuTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentBiMuYu.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentBiMuYu.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentBiMuYu.backTable.fnDraw();
	}
};

equipmentBiMuYuModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentBiMuYuModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentBiMuYuModule.getWorkTask();
};
equipmentBiMuYuModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentBiMuYuModule.initInfo();
	$("#saveAndClose").click(function(){equipmentBiMuYuModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentBiMuYuModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});