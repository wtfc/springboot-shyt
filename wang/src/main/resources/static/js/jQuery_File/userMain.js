
$(function () {
    $('#fileupload').fileupload({
        url: getRootPath()+'/content/attach/userPhotoUpload.action',
        maxNumberOfFiles : 1,
        maxFileSize: 1000000, 
        acceptFileTypes:  /(\.|\/)(gif|jpe?g|png|bmp)$/i,
  
        destroy : function(e,data){
        	$.ajax({
    			url : getRootPath()+'/content/attach/deleteUserPhoto.action',
    			type : 'POST',
    			data : {id: $("#id").val(), fileFolderName: $("#photo").val()},
    			success : function(result) {
    				if(result.state){
    					data.context.fadeOut(function () {
                            $(this).remove();
                        });
    					$("#userPhoto").attr("src", getRootPath()+"/images/demoimg/iphoto.jpg");
    					$("#photo").val("");
    					msgBox.tip({
    						type:"success",
    						content: "删除成功"
    					});
    				} else {
    					msgBox.tip({
    						content: "删除失败"
    					});
    				}
    			}
        	  });
        },
        redirect :
	        window.location.href.replace(
	            /\/[^\/]*$/,
	            '/cors/result.html?%s'
	        )
    });
    
    $('#fileupload').bind('fileuploadsubmit', function (e, data) {
        data.formData = {id: $("#id").val()};
    });
    
    $('#fileupload').bind('fileuploaddone', function (e, data) {
    	$("#userPhoto").attr("src", getRootPath()+"/"+data.result.files[0].resourcesName);
    	$("#photo").val(data.result.files[0].resourcesName);
    });
   
    $("#files").click(isMultipleAdd);
});

function isMultipleAdd(){
	var upload_type = $("#upload_type").val();
	var itemcount= $("table[id$='attacthItem']>tbody").children("tr").length;
	if(upload_type=="1" && itemcount>=1){//单传控制
		msgBox.tip({
			content: "请删除现有头像"
		});
		return false;
	} else {
		return true;
	}
};

