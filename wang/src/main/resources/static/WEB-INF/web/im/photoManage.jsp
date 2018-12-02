<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/web/include/head.jsp"%> --%>
<!-- <head> -->
<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
<meta charset="utf-8">
<!-- <meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support, progress bars, validation and preview images, audio and video for jQuery. Supports cross-domain, chunked and resumable file uploads and client-side image resizing. Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads."> -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->


<!-- Bootstrap styles -->
<!-- <link rel="stylesheet" href="${sysPath}/css/bootstrap.min.css"> -->
<!-- Generic page styles -->
<!-- <link rel="stylesheet" href="${sysPath}/css/style.css"> -->
<!-- blueimp Gallery styles -->
<link rel="stylesheet" href="${sysPath}/css/jQuery_File/blueimp-gallery.min.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload.css">
<link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload-ui.css">
<!-- CSS adjustments for browsers with JavaScript disabled -->
<noscript><link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload-noscript.css"></noscript>
<noscript><link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload-ui-noscript.css"></noscript>
<!-- </head> -->
<div class="navbar navbar-default navbar-fixed-top">
</div>
<div class="container">
    <!-- The file upload form used as target for the file upload widget -->
    <form id="photoupload"  method="POST" enctype="multipart/form-data">
        <!-- Redirect browsers with JavaScript disabled to the origin page -->
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>添加文件</span>
                    <input type="file" name="photofiles[]"  id="photofiles"  >
                </span>
               
<!--                 <button type="button" class="btn btn-primary start" id=""> -->
<!--                     <i class="glyphicon glyphicon-trash"></i> -->
<!--                     <span>提取附件</span> -->
<!--                 </button> --><!-- The global file processing state -->
                <span class="fileupload-process"></span></div>
            <!-- The global progress state -->
            
        </div>
        <!-- The table listing the files available for upload/download -->
        <!-- 加上table id="attacthItem" 来判断上传附件数量 -->
        <table role="presentation" class="table table-striped" id="photoattacthItem">
        	<thead>
        	<tr>
        		<th width="15%">缩略图</th><th width="30%">名称</th><th width="25%">大小</th><th width="30%">操作</th>
        	</tr>
            </thead>
	        <tbody class="files">
	        </tbody>
        </table>
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
<script id="a-template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="a-template-upload fade">
        <td>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
       <td>
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
<script id="a-template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="a-template-download fade">
        <td width="120">
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <img src="${sysPath}/{%=file.resourcesName%}/{%=file.fileName%}" width="50" height="50">
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
                    {%=file.fileName%}
                {% } else { %} 
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">错误</span> {%=file.error%}</div> 
            {% } %}
        </td>
        <td>
            <span class="size" > {%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" {% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>删除</span>
                </button>
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td> 
    </tr>
{% } %}
</script>

<!-- <script src="${sysPath}/js/jQuery_File/vendor/jquery.ui.widget.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/tmpl.min.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/load-image.min.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/canvas-to-blob.min.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.blueimp-gallery.min.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.iframe-transport.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.fileupload.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.fileupload-process.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.fileupload-image.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.fileupload-audio.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.fileupload-video.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.fileupload-validate.js"></script> -->
<!-- <script src="${sysPath}/js/jQuery_File/jquery.fileupload-ui.js"></script> -->
<!-- <script src="${sysPath}/js/com/im/infoMain.js"></script> -->

<script src="${sysPath}/js/jQuery_File/jquery.fu.all.js"></script>
<!--script src="${sysPath}/js/jQuery_File/vendor/jquery.ui.widget.js"></script>
<script src="${sysPath}/js/jQuery_File/tmpl.min.js"></script>
<script src="${sysPath}/js/jQuery_File/load-image.min.js"></script>
<script src="${sysPath}/js/jQuery_File/canvas-to-blob.min.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.blueimp-gallery.min.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.iframe-transport.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.fileupload.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.fileupload-process.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.fileupload-image.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.fileupload-audio.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.fileupload-video.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.fileupload-validate.js"></script>
<script src="${sysPath}/js/jQuery_File/jquery.fileupload-ui.js"></script-->
<script src="${sysPath}/js/com/im/infoMain.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="${sysPath}/js/jQuery_File/cors/jquery.xdr-transport.js"></script>
<![endif]-->

