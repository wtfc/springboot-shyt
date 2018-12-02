var common = {};
//导出到Excel
common.exportExcel=function(){
	//组装查询条件
	var userid = '';
	if($("#userId").length>0){
		userid=$("#userId").val();
	}
//	var worklogDateBegin = $("#worklogDateBegin").val();
//	var worklogDateEnd = $("#worklogDateEnd").val();
	var worklogDateBegin = $("#startDateTemp").val();
	var worklogDateEnd = $("#endDateTemp").val();
	var url;
	if(userid!='' && userid!=null && userid!='0'){
		url = getRootPath()+"/po/worklog/exportExcel.action?worklogDateBegin="+worklogDateBegin+"&worklogDateEnd="+worklogDateEnd+"&createUser="+userid+"&time="+new Date();
	}else{
		url = getRootPath()+"/po/worklog/exportExcel.action?worklogDateBegin="+worklogDateBegin+"&worklogDateEnd="+worklogDateEnd+"&time="+new Date();
	}
	window.location.href=url;
};