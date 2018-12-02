var toaMachineRestartInfo = {};
var companyModule={};
var company={};

//分页处理 start
//分页对象
toaMachineRestartInfo.oTable = null;
//重复提交标识
toaMachineRestartInfo.subState = false;

toaMachineRestartInfo.oTableAoColumns = [
    { "mData":"companyName"},
	{ "mData":"equipmentNumber"},	{ "mData":"machinaValue"},	{ "mData":"ip"},	{ "mData":"extStr4"},	{ "mData":"operateDate"},	{ "mData":"endDate"},
	{ "mData":function(source){
		var status=source.status;
		if(status=="0"){
			return "排队等待";
		}else if(status=="1"){
			return "操作者接单";
		}else if(status=="2"){
			return "到达设备现场";
		}else if(status=="3"){
			return "正在重启";
		}else if(status=="4"){
			return "重启完成,待评分";
		}else{
			return "工单完成";
		}
	}},
	{ "mData":"operationTypeValue"},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineRestartInfo.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineRestartInfo.oTable, aoData);
	var companyId = $("#toaMachineRestartQueryForm #companyId").val();
	if(companyId.length > 0){
		aoData.push({ "name": "companyId", "value":companyId});
	}
	var equipmentNumber = $("#toaMachineRestartQueryForm #equipmentNumber").val();
	if(equipmentNumber.length > 0){
		aoData.push({ "name": "equipmentNumber", "value":equipmentNumber});
	}
	var machina = $("#toaMachineRestartQueryForm #machina").val();
	if(machina.length > 0){
		aoData.push({ "name": "machina", "value":machina});
	}
	var ip = $("#toaMachineRestartQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value":ip});
	}
	var caozgcs = $("#toaMachineRestartQueryForm #caozgcs").val();
	if(caozgcs.length > 0){
		aoData.push({ "name": "caozgcs", "value":caozgcs});
	}
	var status = $("#toaMachineRestartQueryForm #status").val();
	if(status.length > 0){
		aoData.push({ "name": "status", "value":status});
	}
};

//重置按钮功能
toaMachineRestartInfo.queryReset = function(){
	$('#toaMachineRestartQueryForm')[0].reset();
};

//分页查询
toaMachineRestartInfo.toaMachineRestartList = function () {
	if (toaMachineRestartInfo.oTable == null) {
		toaMachineRestartInfo.oTable = $('#toaMachineRestartTable').dataTable( {
			"iDisplayLength": toaMachineRestartInfo.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/customer/toaMachineRestartInfo/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineRestartInfo.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineRestartInfo.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8]}
	        ]
		} );
	} else {
		toaMachineRestartInfo.oTable.fnDraw();
	}
};

//添加修改公用方法
toaMachineRestartInfo.saveOrModify = function (id) {
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_057"),
		success: function(){
			toaMachineRestartInfo.save(id);
		}
	});
};

//添加修改公用方法
toaMachineRestartInfo.save = function (id) {
	if(toaMachineRestartInfo.subState)return;
	toaMachineRestartInfo.subState = true;
	if (id!=null||id!="") {
		var url = getRootPath()+"/customer/toaMachineRestartInfo/save.action";
		//var formData = $("#roomViewModuleForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : {"id":id},
			success : function(data) {
				toaMachineRestartInfo.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					/*if (hide) {
						$('#myModal-edit').modal('hide');
					}*/
					toaMachineRestartInfo.toaMachineRestartList();
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("toaMachineRestartTable", data.labelErrorMessage);
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
				toaMachineRestartInfo.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaMachineRestartInfo.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

//提取公司名称
companyModule.getWorkTask=function(){
	company.backFnServerParams = function(aoData){
		 getTableParameters(company.backTable, aoData);
		 var companyName = $("#companyForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	company.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"companyModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (company.backTable == null) {
		company.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": company.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				company.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		company.backTable.fnDraw();
	}
};
companyModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#companyName").val(s);
	toaMachineRestartModule.toaMachineRestartModuleChange();
};
companyModule.closeWin=function(){
	$("#companyId").val("");
	$("#companyName").val("");
	companyModule.getWorkTask();
};
companyModule.queryReset = function(){
	$('#companyForm')[0].reset();
};

$(document).ready(function(){
	//计算分页显示条数
	toaMachineRestartInfo.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaMachineRestartInfo.toaMachineRestartList);
	$("#queryReset").click(toaMachineRestartInfo.queryReset);
	//初始化列表方法
	toaMachineRestartInfo.toaMachineRestartList();
});