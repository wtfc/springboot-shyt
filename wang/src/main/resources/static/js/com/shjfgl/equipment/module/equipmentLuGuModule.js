
//初始化方法
var equipmentLuGuModule = {};

//重复提交标识
equipmentLuGuModule.subState = false;

//显示表单弹出层
equipmentLuGuModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
	equipmentLuGuModule.clearForm();
	$('#myModal-edit').modal('show');
};

//获取修改信息
equipmentLuGuModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				equipmentLuGuModule.clearForm();
				$("#equipmentLuGuForm").fill(data);
				$("#saveOrModify").hide();
				$("#saveAndClose").attr("class","btn dark");
				$("#saveAndClose").html($.i18n.prop("JC_SYS_095"));
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//添加修改公用方法
equipmentLuGuModule.saveOrModify = function (hide) {
	if(equipmentLuGuModule.subState)return;
		equipmentLuGuModule.subState = true;
	if ($("#equipmentLuGuForm").valid()) {
		var url = getRootPath()+"/machine/equipment/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/equipment/update.action";
		}
		var formData = $("#equipmentLuGuForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentLuGuModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//equipmentLuGuModule.clearForm();
					}
					$("#token").val(data.token);
					equipmentLuGu.equipmentLuGuList();
				} else {
					if(data.labelErrorMessage){
						showErrors("equipmentLuGuForm", data.labelErrorMessage);
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
				equipmentLuGuModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		equipmentLuGuModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
equipmentLuGuModule.clearForm = function(){
	$('#equipmentLuGuForm')[0].reset();
	hideErrorMessage();
};
//公司名称提取
equipmentLuGuModule.getWorkTask=function(){
	equipmentLuGu.backFnServerParams = function(aoData){
		 getTableParameters(equipmentLuGu.backTable, aoData);
		 var companyName = $("#equipmentCustomerForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	equipmentLuGu.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"equipmentLuGuModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (equipmentLuGu.backTable == null) {
		equipmentLuGu.backTable = $('#equipmentLuGuTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipmentLuGu.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				equipmentLuGu.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0]}]
		} );
	} else {
		equipmentLuGu.backTable.fnDraw();
	}
};

equipmentLuGuModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#messages1 #clientName").val(s);
};

equipmentLuGuModule.closeWin=function(){
	$("#companyId").val("");
	$("#clientName").val("");
	equipmentLuGuModule.getWorkTask();
};
equipmentLuGuModule.queryReset = function(){
	$('#equipmentCustomerForm')[0].reset();
};


$(document).ready(function(){
	
	ie8StylePatch();
	$(".datepicker-input").each(function(){$(this).datepicker();});
//	equipmentLuGuModule.initInfo();
	$("#saveAndClose").click(function(){equipmentLuGuModule.saveOrModify(true);});
	$("#saveOrModify").click(function(){equipmentLuGuModule.saveOrModify(false);});
	$("#formDivClose").click(function(){$('#myModal-edit').modal('hide');});
	


});