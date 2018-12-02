var excuteExcel = {};
excuteExcel.exportExcel = function () {
	var contact=$("#contact").val();
	var clientName = $("#clientName").val();
	var address = $("#address").val();
    var url = getRootPath()+"/machine/equipment/exportExcel.action?contact="+contact+"&address="+address+"&clientName="+clientName+"";
    window.location.href=url;
};