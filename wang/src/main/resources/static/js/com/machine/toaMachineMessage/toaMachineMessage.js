var toaMachineMessage = {};

//分页处理 start
//分页对象
toaMachineMessage.oTable = null;

toaMachineMessage.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"messageNumber"},	{ "mData":"messageTitle"},	{ "mData":"messageContent"},	{ "mData":"receivedDeptId"},	{ "mData":"messageType"},	{ "mData":"remark"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaMachineMessage.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaMachineMessage.oTable, aoData);
	
	//var companyName = $("#toaMachineMessageQueryForm #companyName").val();
	//if(companyName.length > 0){
	//	aoData.push({ "name": "companyName", "value":companyName});
	//}
	
};

//重置按钮功能
toaMachineMessage.queryReset = function(){
	$('#toaMachineMessageQueryForm')[0].reset();
};


//分页查询
toaMachineMessage.toaMachineMessageList = function () {
	if (toaMachineMessage.oTable == null) {
		toaMachineMessage.oTable = $('#toaMachineMessageTable').dataTable( {
			"iDisplayLength": toaMachineMessage.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/toaMachineMessage/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaMachineMessage.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaMachineMessage.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1]}
	        ]
		} );
	} else {
		toaMachineMessage.oTable.fnDraw();
	}
};

toaMachineMessage.deleteToaMachineMessage = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		msgBox.info({
			content: $.i18n.prop("JC_SYS_061")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			toaMachineMessage.deleteTimeSet(ids);
		}
	});
};


toaMachineMessage.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/machine/toaMachineMessage/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			toaMachineMessage.toaMachineMessageList();
		}
	});
};

//加载添加DIV
toaMachineMessage.loadModule = function (){
	if($.trim($("#toaMachineMessageModuleDiv").html()).length == 0){
		$("#toaMachineMessageModuleDiv").load(getRootPath()+"/machine/toaMachineMessage/loadForm.action",null,function(){
			toaMachineMessageModule.show();
		});
	}
	else{
		toaMachineMessageModule.show();
	}
};
		
//加载修改div
toaMachineMessage.loadModuleForUpdate = function (id){
	if($.trim($("#toaMachineMessageModuleDiv").html()).length == 0){
		$("#toaMachineMessageModuleDiv").load(getRootPath()+"/machine/toaMachineMessage/loadForm.action",null,function(){
			toaMachineMessageModule.get(id);
		});
	}
	else{
		toaMachineMessageModule.get(id);
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaMachineMessage.pageCount = TabNub>0 ? TabNub : 10;
	
	$("#addButton").click(toaMachineMessage.loadModule);
	$("#showAddDiv_t").click(toaMachineMessage.loadModule);
	$("#queryMachine").click(toaMachineMessage.toaMachineMessageList);
	$("#queryReset").click(toaMachineMessage.queryReset);
	$("#deleteToaMachineMessages").click("click", function(){toaMachineMessage.deleteToaMachineMessage(0);});
	//初始化列表方法
	toaMachineMessage.toaMachineMessageList();
});