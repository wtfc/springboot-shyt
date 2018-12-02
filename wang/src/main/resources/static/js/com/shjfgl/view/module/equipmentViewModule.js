
//初始化方法
var viewModule = {};


//显示表单弹出层
viewModule.show = function (){
	$("#saveAndClose").attr("class","btn");
	$("#saveAndClose").html($.i18n.prop("JC_SYS_097"));
	$("#saveOrModify").show();
	$("#id").val(0);
//	viewModule.clearForm();
	$('#myModal-edit').modal('show');
};

//分页处理 start
//分页对象
viewModule.oTable = null;

viewModule.oTableAoColumns = [
//	{mData: function(source) {
//			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
//		}
//	},
	{ "mData": "clientName"},
	{ "mData": "type" },
//	{ "mData": "machinaNumber"},
	{ "mData": "address" },
	{ "mData": "onlineDate" },
	{ "mData": "ip" },
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

viewModule.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(viewModule.oTable, aoData);
	
//	var clientName = $("#viewModuleQueryForm #clientName").val();
//	if(clientName.length > 0){
//		aoData.push({ "name": "clientName", "value":clientName});
//	}
//	var ip = $("#viewModuleQueryForm #ip").val();
//	if(ip.length > 0){
//		aoData.push({ "name": "ip", "value": ip});
//	}
//	var serialNumber = $("#viewModuleQueryForm #serialNumber").val();
//	if(serialNumber.length > 0){
//		aoData.push({ "name": "serialNumber", "value": serialNumber});
//	}
//	var onlineDate = $("#viewModuleQueryForm #onlineDate").val();
//	if(onlineDate.length > 0){
//		aoData.push({ "name": "onlineDate", "value": onlineDate});
//	}
	var machinaId = $("#machinaId").val();
	if(machinaId.length > 0){
		aoData.push({ "name": "machinaId", "value": machinaId});
	}
	var machinaNumber = $("#machinaNumber").val();
	if(machinaNumber.length > 0){
		aoData.push({ "name": "machinaNumber", "value": machinaNumber});
	}
};
//分页查询
viewModule.viewModuleList = function () {
	
	if (viewModule.oTable == null) {
		viewModule.oTable = $('#viewModuleTable').dataTable( {
			
			"iDisplayLength": viewModule.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": viewModule.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				viewModule.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4]}
	        ]
		} );
	} else {
		viewModule.oTable.fnDraw();
	}
};

$(document).ready(function(){
	
	//计算分页显示条数
	viewModule.pageCount = TabNub>0 ? TabNub : 10;
	//初始化列表方法
	viewModule.viewModuleList();
});
//清空表单数据
viewModule.clearForm = function(){
	$('#viewForm')[0].reset();
	hideErrorMessage();
};