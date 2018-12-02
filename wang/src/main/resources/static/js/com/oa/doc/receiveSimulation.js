var receiveSimulation = {};
receiveSimulation.pageRows = null;
//重复提交标识
receiveSimulation.subState = false;

//分页处理 start
//分页对象
receiveSimulation.oTable = null;
//显示列信息
receiveSimulation.oTableAoColumns = [
	{ "mData": "flowName"},
	{ "mData": "createUserName"},
	{ "mData": "createDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
receiveSimulation.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(receiveSimulation.oTable, aoData);
	//组装查询条件
	//aoData.push({ "name": "typeIds", "value": 31});
};

//分页查询
receiveSimulation.receiveSimulationList = function () {
	$('#receiveSimulation-list').fadeIn();
	if (receiveSimulation.oTable == null) {
		receiveSimulation.oTable = $('#receiveSimulationTable').dataTable( {
			//"iDisplayLength": receiveSimulation.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/receive/getReceiveFLows.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": receiveSimulation.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				receiveSimulation.oTableFnServerParams(aoData);
			},
			bPaginate:false,
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3]}]
		} );
	} else {
		receiveSimulation.oTable.fnDraw();
	}
};
//分页处理 end

//跳转发文单页面
receiveSimulation.showReceiveSimulationForm = function (id){
     var url = '/workFlow/processDefinition/toStartProcess.action?processDefinitionKey='+id;
     loadrightmenu(url,'unknown');
};
 
 
function insert(type, f) {
	
}
function update(type, f) {
	
}
//初始化
jQuery(function($){
	//计算分页显示条数
	receiveSimulation.pageRows = TabNub>0 ? TabNub : 10;
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	receiveSimulation.receiveSimulationList();
});