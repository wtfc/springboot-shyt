
//初始化方法
var equipmentFuFengModule = {};

//重复提交标识
equipmentFuFengModule.subState = false;

//显示表单弹出层
equipmentFuFengModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentFuFengModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentFuFengModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentFuFengModule.clearForm();
				$("#equipmentFuFengForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentFuFengModule.saveOrModify = function (hide) {
	if(equipmentFuFengModule.subState)return;
		equipmentFuFengModule.subState = true;
	if ($("#equipmentFuFengForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentFuFengForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentFuFengModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentFuFengModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentFuFeng.equipmentFuFengList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentFuFengForm", data.labelErrorMessage);
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
				equipmentFuFengModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentFuFengModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentFuFengModule.clearForm = function(){
	$('#equipmentFuFengForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentFuFengModule.getWorkTask=function(){
	equipmentFuFeng.backFnServerParams = function(aoData){
		 getTableParameters(equipmentFuFeng.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentFuFeng.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentFuFengModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentFuFeng.backTable == null) {
		equipmentFuFeng.backTable = $('#equipmentFuFengTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentFuFeng.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentFuFeng.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentFuFeng.backTable.fnDraw();
	}
};

equipmentFuFengModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentFuFengModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentFuFengModule.getWorkTask();
};
equipmentFuFengModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentFuFengModule.initInfo();
	$("#saveAndClose").click(function(){equipmentFuFengModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentFuFengModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});