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
    $('#photoupload').fileupload({
        //xhrFields: {withCredentials: true},
        url: getRootPath()+'/im/info/portalPicUpload.action',
        maxNumberOfFiles : 1,
        acceptFileTypes:  /(\.|\/)(gif|jpe?g|png|bmp)$/i,
        uploadTemplateId: 'a-template-upload',
        downloadTemplateId: 'a-template-download',
        destroy : function(e,data){
        	$.ajax({
    			url : getRootPath()+'/im/info/deleteInfoPortalPic.action',
    			type : 'POST',
    			data : {id: $("#id").val(), fileFolderName: $("#portalPic").val()},
    			success : function(result) {
    				if(result.state){
    					data.context.fadeOut(function () {
                            $(this).remove();
                        });
    					$("#portalPicImg").attr("src", getRootPath()+"/images/demoimg/iphoto.jpg");
    					$("#portalPic").val("");
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
    
    $('#photoupload').bind('fileuploadsubmit', function (e, data) {
        data.formData = {id: $("#id").val()};
    });
    
    $('#photoupload').bind('fileuploaddone', function (e, data) {
    	$("#portalPicImg").attr("src", getRootPath()+"/"+data.result.files[0].resourcesName+"/"+data.result.files[0].fileName);
    	$("#portalPic").val(data.result.files[0].resourcesName);
    });
   
    $("#photofiles").click(isMultipleAddPhoto);
});

function isMultipleAddPhoto(){
	var upload_type = $("#photo_upload_type").val();
	var itemcount= $("table[id$='photoattacthItem']>tbody").children("tr").length;
	if(upload_type=="1" && itemcount>=1){//单传控制
		msgBox.tip({
			content: "请删除现有图片"
		});
		return false;
	} else {
		return true;
	}
};

