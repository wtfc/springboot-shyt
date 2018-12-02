
//初始化方法
var equipmentDaZuModule = {};

//重复提交标识
equipmentDaZuModule.subState = false;

//显示表单弹出层
equipmentDaZuModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentDaZuModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentDaZuModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentDaZuModule.clearForm();
				$("#equipmentDaZuForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentDaZuModule.saveOrModify = function (hide) {
	if(equipmentDaZuModule.subState)return;
		equipmentDaZuModule.subState = true;
	if ($("#equipmentDaZuForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentDaZuForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentDaZuModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentDaZuModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentDaZu.equipmentDaZuList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentDaZuForm", data.labelErrorMessage);
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
				equipmentDaZuModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentDaZuModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentDaZuModule.clearForm = function(){
	$('#equipmentDaZuForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentDaZuModule.getWorkTask=function(){
	equipmentDaZu.backFnServerParams = function(aoData){
		 getTableParameters(equipmentDaZu.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentDaZu.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentDaZuModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentDaZu.backTable == null) {
		equipmentDaZu.backTable = $('#equipmentDaZuTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentDaZu.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentDaZu.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentDaZu.backTable.fnDraw();
	}
};

equipmentDaZuModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentDaZuModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentDaZuModule.getWorkTask();
};
equipmentDaZuModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentDaZuModule.initInfo();
	$("#saveAndClose").click(function(){equipmentDaZuModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentDaZuModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});