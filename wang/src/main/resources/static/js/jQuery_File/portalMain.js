/*
 * jQuery File Upload Plugin JS Example 8.9.1
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global $, window */


$(function () {
    $('#fileupload').fileupload({
        //xhrFields: {withCredentials: true},
        url: getRootPath()+'/content/attach/portletPhotoUpload.action',
        maxNumberOfFiles : 1,
        acceptFileTypes:  /(\.|\/)(gif|jpe?g|png|bmp)$/i,
  
        destroy : function(e,data){
        	$.ajax({
    			url : getRootPath()+'/content/attach/deletePortletPhoto.action',
    			type : 'POST',
    			data : {id: $("#tempportletid").val(), fileFolderName: $("#tempportletfile").val()},
    			success : function(result) {
    				if(result.state){
    					data.context.fadeOut(function () {
                            $(this).remove();
                        });
    					$("#letFilePhoto_"+$("#tempportletportalvoid").val()).attr("src", getRootPath()+"/images/demoimg/iphoto.jpg");
    					$("#letFile_"+$("#tempportletportalvoid").val()).val("");
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
        data.formData = {id: $("#id_"+$("#tempportletportalvoid").val()).val()};
    });
    
    $('#fileupload').bind('fileuploaddone', function (e, data) {
    	$("#letFilePhoto_"+$("#tempportletportalvoid").val()).attr("src", getRootPath()+"/"+data.result.files[0].resourcesName+"/"+data.result.files[0].fileName);
    	$("#letFile_"+$("#tempportletportalvoid").val()).val(data.result.files[0].resourcesName);
    });
   
    $("#files").click(isMultipleAdd);
});

function isMultipleAdd(){
	var upload_type = $("#upload_type").val();
	var itemcount= $("table[id$='attacthItem']>tbody").children("tr").length;
	if(upload_type=="1" && itemcount>=1){//单传控制
		msgBox.tip({
			content: "请删除现有图片"
		});
		return false;
	} else {
		return true;
	}
};

