var operlog = {}, pageCount=10;
  	
//重复提交标识
operlog.subState = false;
//分页处理 start
//分页对象
operlog.oTable = null;

//显示列信息
//操作日志
operlog.oTableAoColumns = [
	
	{ "mData": "startAt" },
	{ "mData": "endAt" },
	{ "mData": "jobData" }
];

operlog.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(operlog.oTable, aoData);
	var startAt = $("#operlogListForm #startAt").val();
	var endAt = $("#operlogListForm #endAt").val();
	
	/*if(groupName != ""){
		aoData.push({ "name": "groupName", "value": $.trim(groupName)});
	}*/
	
	if(startAt != ""){
		aoData.push({ "name": "startAt", "value": startAt });
	} /*else {
		aoData.push({ "name": "startAt", "value": operlog.dateReckon(-1)});
	}*/
	
	if(endAt != ""){
		aoData.push({ "name": "endAt", "value": endAt});
	} /*else {
		aoData.push({ "name": "endAt", "value": operlog.dateReckon(6)});
	}*/
	

	
};

operlog.dateReckon = function(dayadd){
	var nowdate = new Date();
	nowdate = nowdate.valueOf();
	nowdate = nowdate - dayadd * 24 * 60 * 60 * 1000;
	nowdate = new Date(nowdate);
	var str = '';
	str +=nowdate.getFullYear()+'-'; //获取当前年份 
	str +=nowdate.getMonth()+1+'-'; //获取当前月份（0——11） 
	str +=nowdate.getDate()+' '; 
	str +=nowdate.getHours()+'-'; 
	str +=nowdate.getMinutes()+'-'; 
	str +=nowdate.getSeconds();
	return str;
};

//分页查询
operlog.operlogList = function () {
	if (operlog.oTable == null) {
		operlog.oTable = $('#operlogTable').dataTable( {
			"iDisplayLength": operlog.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/runLog/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": operlog.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				operlog.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2]}
	        ]
		} );
	} else {
		operlog.oTable.fnDraw();
	}
};

//处理缓存问题 清空form表单中值
operlog.queryReset = function(){
	$('#operlogListForm')[0].reset();
};

//初始化
{
	//计算分页显示条数
	operlog.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryoperlog").click(operlog.operlogList);
	$("#queryReset").click(operlog.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化列表方法
	operlog.operlogList();
	    		

};	

