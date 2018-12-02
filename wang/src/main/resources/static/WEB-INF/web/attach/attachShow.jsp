<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!-- <head> -->
<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
<meta charset="utf-8">
<!-- <meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support, progress bars, validation and preview images, audio and video for jQuery. Supports cross-domain, chunked and resumable file uploads and client-side image resizing. Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads."> -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->


<!-- Bootstrap styles -->
<!-- <link rel="stylesheet" href="<%=path %>/css/bootstrap.min.css"> -->
<!-- Generic page styles -->
<!-- <link rel="stylesheet" href="<%=path %>/css/style.css"> -->
<!-- blueimp Gallery styles -->
<link rel="stylesheet" href="<%=path %>/css/jQuery_File/blueimp-gallery.min.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="<%=path %>/css/jQuery_File/jquery.fileupload.css">
<link rel="stylesheet" href="<%=path %>/css/jQuery_File/jquery.fileupload-ui.css">
<!-- CSS adjustments for browsers with JavaScript disabled -->
<noscript><link rel="stylesheet" href="<%=path %>/css/jQuery_File/jquery.fileupload-noscript.css"></noscript>
<noscript><link rel="stylesheet" href="<%=path %>/css/jQuery_File/jquery.fileupload-ui-noscript.css"></noscript>
<!-- </head> -->

<div class="navbar navbar-default navbar-fixed-top">
</div>
<div class="container">
    <!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload"  method="POST" enctype="multipart/form-data">
        <!-- Redirect browsers with JavaScript disabled to the origin page -->
        <noscript><input type="hidden" name="redirect" value="http://blueimp.github.io/jQuery-File-Upload/"></noscript>
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
                <!-- The fileinput-button span is used to style the file input field as button -->
<!--                 <span class="btn btn-success fileinput-button"> -->
<!--                     <i class="glyphicon glyphicon-plus"></i> -->
<!--                     <span>添加文件</span> -->
<!--                     <input type="file" name="files[]"  id="files"  multiple> -->
<!--                 </span> -->
<!--                 <button type="submit" class="btn btn-primary start"> -->
<!--                     <i class="glyphicon glyphicon-upload"></i> -->
<!--                     <span>开始上传</span> -->
<!--                 </button> -->
<!--                 <button type="reset" class="btn btn-warning cancel"> -->
<!--                     <i class="glyphicon glyphicon-ban-circle"></i> -->
<!--                     <span>取消上传</span> -->
<!--                 </button> -->
<!--                 <button type="button" class="btn btn-danger delete"> -->
<!--                     <i class="glyphicon glyphicon-trash"></i> -->
<!--                     <span>删除</span> -->
<!--                 </button> -->
<!--                 <button type="button" class="btn btn-primary start" id=""> -->
<!--                     <i class="glyphicon glyphicon-trash"></i> -->
<!--                     <span>提取附件</span> -->
<!--                 </button> -->
<!--                 <input type="checkbox" class="toggle"> -->
                <!-- The global file processing state -->
<!--                 <span class="fileupload-process"></span> -->
            </div>
            <!-- The global progress state -->
            <div class="col-lg-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                </div>
                <!-- The extended global progress state -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <!-- 加上table id="attacthItem" 来判断上传附件数量 -->
        <table role="presentation" class="table table-striped" id="attacthItem"><tbody class="files"></tbody></table>
    </form>
    <br>
</div>
<!-- The blueimp Gallery widget -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">&lt;</a>
    <a class="next">&gt;</a>
    <a class="close">X</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>上传</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download  -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="${sysPath}{%=file.thumbnailUrl%}" title="{%=file.name%}" download="${sysPath}{%=file.name%}" data-gallery><img src="${sysPath}{%=file.thumbnailUrl%}"></a>
					<input type="hidden" name="fileid" value="{%=file.id%}"  data-gallery/>
					<input type="hidden" name="filename" value="{%=file.name%}"/>
					<input type="hidden" name="fileReourceName" value="{%=file.resourcesName%}"/>
					<input type="hidden" name="fileThumbnailUrl" value="{%=file.thumbnailUrl%}"/>
                {% } %}
            </span> 
        </td> 
        <td>
            <p class="name"> 
                {% if (file.url) { %}
                    <a href="${sysPath}{%=file.url%}?fileName={%=file.fileName%}&resourcesName={%=file.resourcesName%}" title="{%=file.name%}" download="${sysPath}{%=file.name%}"  >{%=file.name%}</a>
                {% } else { %} 
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div> 
            {% } %}
        </td>
        <td>
            <span class="size" > {%=o.formatFileSize(file.size)%}</span>
        </td>
        <td> 
			<a class="btn dark" href="${sysPath}{%=file.url%}?fileName={%=file.fileName%}&resourcesName={%=file.resourcesName%}" >下载</a>
        </td> 
    </tr>
{% } %}
</script>

<script src="<%=path %>/js/jQuery_File/vendor/jquery.ui.widget.js"></script>
<script src="<%=path %>/js/jQuery_File/tmpl.min.js"></script>
<script src="<%=path %>/js/jQuery_File/load-image.min.js"></script>
<script src="<%=path %>/js/jQuery_File/canvas-to-blob.min.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.blueimp-gallery.min.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.iframe-transport.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.fileupload.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.fileupload-process.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.fileupload-image.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.fileupload-audio.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.fileupload-video.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.fileupload-validate.js"></script>
<script src="<%=path %>/js/jQuery_File/jquery.fileupload-ui.js"></script>
<script src="<%=path %>/js/jQuery_File/main.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="<%=path %>/js/jQuery_File/cors/jquery.xdr-transport.js"></script>
<![endif]-->

