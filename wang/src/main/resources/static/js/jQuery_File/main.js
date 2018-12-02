$(function() {
	$('#fileupload').fileupload({
		url : getRootPath() + '/content/attach/upload.action',
		maxNumberOfFiles : 100,
		maxFileSize:524288000,  
        maxAllFileSize:524288000
	});
	
	$('#fileupload').bind('fileuploadsubmit', function (e, data) {
		showProgressBar(data.context.find(".fileprogress-bar"));
		var progressSize = 0;
	    function showProgressBar(obj){
	    	progressSize = progressSize + 5;
	    	$(obj).css("width", progressSize+"%");
	    	setTimeout(function(){showProgressBar(obj);}, 1000);
	    }
    });
	
    $('#fileupload').bind('fileuploaddone', function (e, data) {
    	$(data.context.find(".fileprogress-bar")).css("width", "100%");
    });
    
	initUpload();
});



function initUpload(){
	var businessId = $("#businessId").val();
	var businessTable = $("#businessTable").val();
	$.ajax({
		url : $('#fileupload').fileupload('option', 'url'),
		data : {
			"businessId" : businessId,
			"businessTable" : businessTable,
			"isPaged" : "1"
		},
		dataType : 'json',
		context : $('#fileupload')[0]
	}).done(function(result) {
		$(this).fileupload('option', 'done').call(this, $.Event('done'), {
			result : result
		});
	});
}
