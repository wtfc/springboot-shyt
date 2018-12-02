
//初始化方法
var equipmentKanDanModule = {};

//重复提交标识
equipmentKanDanModule.subState = false;

//显示表单弹出层
equipmentKanDanModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentKanDanModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentKanDanModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentKanDanModule.clearForm();
				$("#equipmentKanDanForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentKanDanModule.saveOrModify = function (hide) {
	if(equipmentKanDanModule.subState)return;
		equipmentKanDanModule.subState = true;
	if ($("#equipmentKanDanForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentKanDanForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentKanDanModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentKanDanModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentKanDan.equipmentKanDanList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentKanDanForm", data.labelErrorMessage);
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
				equipmentKanDanModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentKanDanModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentKanDanModule.clearForm = function(){
	$('#equipmentKanDanForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentKanDanModule.getWorkTask=function(){
	equipmentKanDan.backFnServerParams = function(aoData){
		 getTableParameters(equipmentKanDan.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentKanDan.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentKanDanModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentKanDan.backTable == null) {
		equipmentKanDan.backTable = $('#equipmentKanDanTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentKanDan.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentKanDan.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentKanDan.backTable.fnDraw();
	}
};

equipmentKanDanModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentKanDanModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentKanDanModule.getWorkTask();
};
equipmentKanDanModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentKanDanModule.initInfo();
	$("#saveAndClose").click(function(){equipmentKanDanModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentKanDanModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});