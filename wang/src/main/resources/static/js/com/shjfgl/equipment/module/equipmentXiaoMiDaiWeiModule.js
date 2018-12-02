
//初始化方法
var equipmentXiaoMiDaiWeiModule = {};

//重复提交标识
equipmentXiaoMiDaiWeiModule.subState = false;

//显示表单弹出层
equipmentXiaoMiDaiWeiModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentXiaoMiDaiWeiModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentXiaoMiDaiWeiModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentXiaoMiDaiWeiModule.clearForm();
				$("#equipmentXiaoMiDaiWeiForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentXiaoMiDaiWeiModule.saveOrModify = function (hide) {
	if(equipmentXiaoMiDaiWeiModule.subState)return;
		equipmentXiaoMiDaiWeiModule.subState = true;
	if ($("#equipmentXiaoMiDaiWeiForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentXiaoMiDaiWeiForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentXiaoMiDaiWeiModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentXiaoMiDaiWeiModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentXiaoMiDaiWei.equipmentXiaoMiDaiWeiList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentXiaoMiDaiWeiForm", data.labelErrorMessage);
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
				equipmentXiaoMiDaiWeiModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentXiaoMiDaiWeiModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentXiaoMiDaiWeiModule.clearForm = function(){
	$('#equipmentXiaoMiDaiWeiForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentXiaoMiDaiWeiModule.getWorkTask=function(){
	equipmentXiaoMiDaiWei.backFnServerParams = function(aoData){
		 getTableParameters(equipmentXiaoMiDaiWei.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentXiaoMiDaiWei.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentXiaoMiDaiWeiModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentXiaoMiDaiWei.backTable == null) {
		equipmentXiaoMiDaiWei.backTable = $('#equipmentXiaoMiDaiWeiTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentXiaoMiDaiWei.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentXiaoMiDaiWei.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentXiaoMiDaiWei.backTable.fnDraw();
	}
};

equipmentXiaoMiDaiWeiModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentXiaoMiDaiWeiModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentXiaoMiDaiWeiModule.getWorkTask();
};
equipmentXiaoMiDaiWeiModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentXiaoMiDaiWeiModule.initInfo();
	$("#saveAndClose").click(function(){equipmentXiaoMiDaiWeiModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentXiaoMiDaiWeiModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});