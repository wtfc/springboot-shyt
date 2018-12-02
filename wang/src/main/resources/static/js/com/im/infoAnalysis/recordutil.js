
function init_time_tools(begin,end){
	$.ajax({
		url : getRootPath()+"/im/infoAnalysis/getTime.action",
		success : function(data){
			$("#"+begin).val(data.begin_time);
			$("#"+end).val(data.end_time);
		}
	});
}