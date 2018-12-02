var sendSimulation = {};
sendSimulation.pageRows = null;
//重复提交标识
sendSimulation.subState = false;

//分页处理 start
//分页对象
sendSimulation.oTable = null;
//显示列信息
sendSimulation.oTableAoColumns = [
	{ "mData": "flowName"},
	{ "mData": "createUserName"},
	{ "mData": "createDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
sendSimulation.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendSimulation.oTable, aoData);
};

//分页查询
sendSimulation.sendSimulationList = function () {
	$('#sendSimulation-list').fadeIn();
	if (sendSimulation.oTable == null) {
		sendSimulation.oTable = $('#sendSimulationTable').dataTable( {
			//"iDisplayLength": sendSimulation.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/send/getSendFLows.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendSimulation.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendSimulation.oTableFnServerParams(aoData);
			},
			bPaginate:false,
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3]}]
		} );
	} else {
		sendSimulation.oTable.fnDraw();
	}
};
//分页处理 end

//打开表单 entrance 进入页面类型
sendSimulation.openForm = function(id,processDefinitionKey,entrance,scanType){
	if(entrance == null){
		entrance = "business";
	}
	if(scanType==null){
		scanType = "create";
	}
	loadrightmenu("/workFlow/processDefinition/toStartProcess.action?scanType="+scanType+"&id_="+id+"&processDefinitionKey="+processDefinitionKey+"&entrance="+entrance,"unknown");
};

//跳转发文单页面
sendSimulation.showSendSimulationForm = function (id,flowId){
	sendSimulation.openForm(id,flowId);
};

//初始化
jQuery(function($){
	//计算分页显示条数
	sendSimulation.pageRows = TabNub>0 ? TabNub : 10;
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	sendSimulation.sendSimulationList();
});