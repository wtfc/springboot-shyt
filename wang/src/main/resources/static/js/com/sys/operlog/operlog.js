var operlog = {}, pageCount=10;
  	
//重复提交标识
operlog.subState = false;
//分页处理 start
//分页对象
operlog.oTable = null;

//显示列信息
//操作日志
operlog.oTableAoColumns = [
	{ "mData": "loginName" },
	{ "mData": "displayName" },
	{ "mData": "deptName" },
	{ "mData": "ip" },
	{ "mData": "operType" },
	{ "mData": "operDesc" },
	{ "mData": "operTime" }
];

operlog.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(operlog.oTable, aoData);
	
	var loginName = $("#operlogListForm #loginName").val();
	var displayName = $("#operlogListForm #displayName").val();
	var operType = $("#operlogListForm #operType").val();
	var operStartTime = $("#operlogListForm #operStartTime").val();
	var operEndTime = $("#operlogListForm #operEndTime").val();
	
	if(loginName != ""){
		aoData.push({ "name": "loginName", "value": $.trim(loginName)});
	}
	
	if(displayName != ""){
		aoData.push({ "name": "displayName", "value": $.trim(displayName)});
	}
	
	if(operType != ""){
		aoData.push({ "name": "operType", "value": $.trim(operType)});
	}
	
	if(operStartTime != ""){
		aoData.push({ "name": "operStartTime", "value": operStartTime});
	} else {
		aoData.push({ "name": "operStartTime", "value": operlog.dateReckon(6)});
	}
	
	if(operEndTime != ""){
		aoData.push({ "name": "operEndTime", "value": operEndTime });
	} else {
		aoData.push({ "name": "operEndTime", "value": operlog.dateReckon(-1)});
	}
	
};

operlog.dateReckon = function(dayadd){
	var nowdate = new Date();
	nowdate = nowdate.valueOf();
	nowdate = nowdate - dayadd * 24 * 60 * 60 * 1000;
	nowdate = new Date(nowdate);
	var dict = {   
		"yyyy": nowdate.getFullYear(),   
		"M": nowdate.getMonth() + 1,   
		"d": nowdate.getDate(),   
		"H": nowdate.getHours(),   
		"m": nowdate.getMinutes(),   
		"s": nowdate.getSeconds(),   
		"MM": ("" + (nowdate.getMonth() + 101)).substr(1),   
		"dd": ("" + (nowdate.getDate() + 100)).substr(1),   
		"HH": ("" + (nowdate.getHours() + 100)).substr(1),   
		"mm": ("" + (nowdate.getMinutes() + 100)).substr(1),   
		"ss": ("" + (nowdate.getSeconds() + 100)).substr(1)   
	}; 
	var format = "yyyy-MM-dd HH:mm:ss";
	return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
	   return dict[arguments[0]];   
	});
};

//分页查询
operlog.operlogList = function () {
	if (operlog.oTable == null) {
		operlog.oTable = $('#operlogTable').dataTable( {
			"iDisplayLength": operlog.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/operlog/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": operlog.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				operlog.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5]}
	        ]
		} );
	} else {
		operlog.oTable.fnDraw();
	}
};

//处理缓存问题 清空form表单中值
operlog.queryReset = function(){
	$('#operlogListForm')[0].reset();
	$("#operlogListForm #operStartTime").val(operlog.dateReckon(6));
	$("#operlogListForm #operEndTime").val(operlog.dateReckon(-1));
};

//初始化
jQuery(function($) 
{
	//计算分页显示条数
	operlog.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryoperlog").click(operlog.operlogList);
	$("#queryReset").click(operlog.queryReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	$("#operlogListForm #operStartTime").val(operlog.dateReckon(6));
	$("#operlogListForm #operEndTime").val(operlog.dateReckon(-1));
	//初始化列表方法
	operlog.operlogList();
	    		

});	

