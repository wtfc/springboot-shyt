<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/web/include/head.jsp"%>--%>
 
 <!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
<meta charset="utf-8">

<link rel="stylesheet" href="${sysPath}/css/jQuery_File/blueimp-gallery.min.css">
<link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload.css">
<link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload-ui.css">
<noscript><link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload-noscript.css"></noscript>
<noscript><link rel="stylesheet" href="${sysPath}/css/jQuery_File/jquery.fileupload-ui-noscript.css"></noscript>

<div class="navbar navbar-default navbar-fixed-top">
</div>
<div class="container">
    <form id="fileupload"  method="POST" enctype="multipart/form-data">
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>添加文件</span>
                    <input type="file" name="files[]"  id="files"  multiple>
                    <input type="hidden"   value="" id="delattachIds">
                    <input type="hidden" id="category" name="category">
                    <input type="hidden" id="isClose" name="isClose">
                </span>
                <button type="button" class="btn btn-primary startAndClose">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>上传并关闭</span>
                </button>
                <button type="submit" class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>开始上传</span>
                </button>
                
                <!-- button type="reset" class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消上传</span>
                </button>
                <button type="button" class="btn btn-danger deletes"  >
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>批量删除</span>
                </button!-->
                <input type="checkbox" class="toggle" style="display:none">
                <!--span class="fileupload-process"></span-->
            </div>
            
        </div>
        <!-- 加上table id="attacthItem" 来判断上传附件数量 -->
        <table role="presentation" class="table table-striped" id="attacthItem">
        	<thead>
        	<tr>
        		<th>缩略图</th><th>名称</th><th>大小</th><th>操作</th>
        	</tr>
            </thead>
        	<tbody class="files"></tbody>
        </table>
    </form>
    <br>
</div>

<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">&lt;</a>
    <a class="next">&gt;</a>
    <a class="close">X</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>

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
            <p class="size">正在上传...</p>
            <div class="fileprogress">
  				<div class="fileprogress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:0">
  				</div>
			</div>

        </td>
        <td width="150px" >
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled style="display:none;">
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

<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="${sysPath}{%=file.thumbnailUrl%}" title="{%=file.name%}" download="${sysPath}{%=encodeURIComponent(encodeURIComponent(file.name))%}" data-gallery><img src="${sysPath}{%=file.thumbnailUrl%}"></a>
					<input type="hidden" name="fileid" value="{%=file.id%}"  data-gallery/>
					<input type="hidden" name="filename" value="{%=file.name%}"/>
					<input type="hidden" name="fileReourceName" value="{%=file.resourcesName%}"/>
					<input type="hidden" name="fileThumbnailUrl" value="{%=file.thumbnailUrl%}"/>
					<input type="hidden" name="fileSize" value="{%=o.formatFileSize(file.size)%}"/>
                {% } %}
            </span> 
        </td> 
        <td>
            <p class="name"> 
                {% if (file.url) { %}
                    <a href="${sysPath}{%=file.url%}?fileName={%=encodeURIComponent(encodeURIComponent(file.fileName))%}&resourcesName={%=file.resourcesName%}" title="{%=file.name%}" download="${sysPath}{%=file.name%}"  >{%=file.name%}</a>
					<input type="hidden" name="downloadItemUrl" value="${sysPath}{%=file.url%}?fileName={%=encodeURIComponent(encodeURIComponent(file.fileName))%}&resourcesName={%=file.resourcesName%}"/>
                {% } else { %} 
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { if(file.error=='Internal Server Error'){ %}
<div><strong class="error text-danger">文件格式错误</strong></div> 
 {% } else { %} 
                <div><strong class="error text-danger">{%=file.error%}</strong></div> 
            {% }} %}
        </td>
        <td>
            <span class="size" > {%=o.formatFileSize(file.size)%}</span>
        </td>
        <td width="150px"> 
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" name="deleteattach"    data-type="{%=file.deleteType%}" data-url="${sysPath}{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>删除</span>
					<input type="hidden" name="deleteItemUrl" value="${sysPath}{%=file.deleteUrl%}"/>
                </button>
				<input type="hidden" id="btn{%=file.id%}"  data-type="{%=file.deleteType%}" data-url="${sysPath}{%=file.deleteUrl%}" {% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %} class="deletes"/>
                <input type="checkbox"  name="delete" value="1" class="toggle"  style="display:none">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
			<a class="btn dark"  href="${sysPath}{%=file.url%}?fileName={%=file.fileName%}&resourcesName={%=file.resourcesName%}"  style="display:none">下载</a>
        </td> 
    </tr>
{% } %}
</script>


<%--<script src="${sysPath}/js/jQuery_File/tmpl.min.js,load-image.min.js,canvas-to-blob.min.js,jquery.blueimp-gallery.min.js,jquery.iframe-transport.js,fileupload.js,jquery.fileupload-process.js,jquery.fileupload-image.js,jquery.fileupload-audio.js,jquery.fileupload-video.js,jquery.fileupload-validate.js,jquery.fileupload-ui.js,main.js"></script>--%>
 <script src="${sysPath}/js/jQuery_File/jquery.fu.all.js"></script>
 <script src="${sysPath}/js/jQuery_File/main.js"></script>
 
<%-- <script src="${sysPath}/js/jQuery_File/tmpl.min.js"></script>
<script src="${sysPath}/js/jQuery_File/vendor/jquery.ui.widget.js"></script>
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
<script src="${sysPath}/js/jQuery_File/jquery.fileupload-ui.js"></script>
--%>
 <!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="${sysPath}/js/jQuery_File/cors/jquery.xdr-transport.js"></script>
<![endif]-->

