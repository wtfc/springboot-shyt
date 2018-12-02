
//初始化方法
var equipmentZhaoWeiModule = {};

//重复提交标识
equipmentZhaoWeiModule.subState = false;

//显示表单弹出层
equipmentZhaoWeiModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentZhaoWeiModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentZhaoWeiModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentZhaoWeiModule.clearForm();
				$("#equipmentZhaoWeiForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentZhaoWeiModule.saveOrModify = function (hide) {
	if(equipmentZhaoWeiModule.subState)return;
		equipmentZhaoWeiModule.subState = true;
	if ($("#equipmentZhaoWeiForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentZhaoWeiForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentZhaoWeiModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentZhaoWeiModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentZhaoWei.equipmentZhaoWeiList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentZhaoWeiForm", data.labelErrorMessage);
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
				equipmentZhaoWeiModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentZhaoWeiModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentZhaoWeiModule.clearForm = function(){
	$('#equipmentZhaoWeiForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentZhaoWeiModule.getWorkTask=function(){
	equipmentZhaoWei.backFnServerParams = function(aoData){
		 getTableParameters(equipmentZhaoWei.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentZhaoWei.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentZhaoWeiModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentZhaoWei.backTable == null) {
		equipmentZhaoWei.backTable = $('#equipmentZhaoWeiTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentZhaoWei.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentZhaoWei.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentZhaoWei.backTable.fnDraw();
	}
};

equipmentZhaoWeiModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentZhaoWeiModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentZhaoWeiModule.getWorkTask();
};
equipmentZhaoWeiModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentZhaoWeiModule.initInfo();
	$("#saveAndClose").click(function(){equipmentZhaoWeiModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentZhaoWeiModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});