var statisticsIc={};
//分页处理 start
statisticsIc.pageRows = null;
//分页对象
statisticsIc.oTable = null;

//显示列信息
statisticsIc.oTableAoColumns = [
//不需要排序的列直接用mData function方式
	{mData: "userName"},
	{mData: "sendNum"},
	{mData: "residueNum"}
];

//组装后台参数
statisticsIc.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(statisticsIc.oTable, aoData);
	//组装查询条件
	var userId = $("#statisticsForm #createUser-createUser").val();
	var statisticsMonth = $("#statisticsForm #statisticsMonth").val();
	
	if(userId != ""){
		aoData.push({ "name": "userId", "value": userId});
	}
	if(statisticsMonth != ""){
		aoData.push({ "name": "statisticsMonth", "value": statisticsMonth});
	}
};

//分页查询用户
statisticsIc.statisticsIcList = function () {
	//table对象为null时初始化
	if (statisticsIc.oTable == null) {
		statisticsIc.oTable = $('#statisticsTable').dataTable( {
			iDisplayLength: statisticsIc.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/smsStatistic/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: statisticsIc.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				statisticsIc.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,2]}]
		} );
	} else {
		//table不为null时重绘表格
		statisticsIc.oTable.fnDraw();
	}
};

//查询条件重置
statisticsIc.resetSearch = function(){
	$('#statisticsForm')[0].reset();
	//清空人员选择树
	selectControl.clearValue("createUser-createUser");	
};

//初始化方法按如下结构编写
jQuery(function($){
	//计算分页显示条数
	statisticsIc.pageRows = TabNub>0 ? TabNub : 10;
    //初始化内容
	$("#querystatistics").click(statisticsIc.statisticsIcList);
	$("#resetSearch").click(statisticsIc.resetSearch);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	selectControl.init("controlTree","createUser-createUser", false, true);//单选人员
	statisticsIc.statisticsIcList();
	
});

//@ sourceURL=statisticsIc.js
