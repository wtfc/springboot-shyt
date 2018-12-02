<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/web/include/head.jsp"%>

<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script type="text/javascript">
	function userOTableSetButtons(source) {
		var download = "<a class=\"btn dark\" href=\""+getRootPath()+"/content/attach/download.action?fileName="+source.fileName+"&resourcesName="+source.resourcesName+"\" >下载</a>";
		return download;
};
</script>
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu">
		<h1></h1>
		<div class="crumbs"></div>
	</div> 
</header>
<section class="clearfix">
	<section class="form-btn fl m-l">
		<a class="btn dark" type="button" href="#myModal-edit"  role="button" data-toggle="modal" name="queryattach">附件</a>
<!-- 		<a class="btn dark" type="button" href="#myModal-edit"  role="button" data-toggle="modal" name="querycopyattach">复制附件</a> -->
<!-- 		<a class="btn dark" type="button"   role="button"  onclick="test()">test</a> -->
		<a class="btn dark" type="button"   role="button"  onclick="clearTable()">clearTable()清除附件列表</a>
		<a class="btn dark" type="button"   role="button"  onclick="clearAttachlist()">clearAttachlist()清除回显列表</a>
		<a class="btn dark" type="button"   role="button"  onclick="clearBunessVal()">clearBunessVal()清除业务属性值</a>
		<a class="btn dark" type="button"   role="button"  onclick="getLogicalAttachIds()">getLogicalAttachIds()获得逻辑删除ids</a>
		<a class="btn dark" type="button"   role="button"  onclick="clearDelAttachIds()">clearDelAttachIds()清除逻辑删除ids</a>
		
	</section>
	<section class="pagination m-r fr m-t-none">
	</section>
</section>

<!--管理附件弹出层 -->
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w820">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="closebtn" data-dismiss="modal" onclick="showAttachList(true)">×</button>
				<h4 class="modal-title">添加上传文件</h4>
			</div>
			<div class="modal-body">
			    <!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
				<input type="hidden" name="businessId" id="businessId" value="1"/>
				<input type="hidden" name="businessTable" id="businessTable"  value="1"/> 
				<!-- upload_type 1为单传  0为多传-->
                <input type="hidden" id="upload_type" value="0">
                <!-- islogicDel 1为逻辑删除  0为物理删除-->
                <input type="hidden" id="islogicDel" value="1">
                <!-- isshow 1为查看附件  0为管理附件-->
                <input type="hidden" id="isshow" value="0">
                 <!-- iscopy 1为复制附件列表 0为不复制  默认为0-->
                <input type="hidden" id="iscopy" value="0">
				<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
			<!--关闭弹出层时 如需清理表格内容可调用clearTable()-->
				<button class="btn dark" type="button" id="test3" data-dismiss="modal" onclick="showAttachList(true)">关闭</button>
			</div>
		</div>
	</div>
</div>
 <ul id="attachList"></ul>

<!-- dataTable回显 -->
<!-- <div class="modal fade panel" id="myModal-show" aria-hidden="false"> -->
<!-- 	<div class="modal-dialog"> -->
<!-- 		<div class="modal-content"> -->
<!-- 			<div class="modal-header"> -->
<!-- 				<button type="button" class="close" id="closebtn" data-dismiss="modal" >×</button> -->
<!-- 			</div> -->
<!-- 			<input type="hidden" name="businessId" id="businessId" value="1"/> -->
<!-- 			<input type="hidden" name="businessTable" id="businessTable"  value="1"/>  -->
<!-- 			<div class="modal-body"> -->
<!-- 				<table class="table table-td-striped" id="attachTable"> -->
<!-- 				    <thead> -->
<!--                          <tr> -->
<!--                              <th>文件名称</th> -->
<!--                              <th>文件大小</th> -->
<!--                              <th>上传时间</th> -->
<!--                              <th></th> -->
<!--                          </tr> -->
<!--                     </thead> -->
<!--                      <tbody> -->

<!--                      </tbody> -->
<!--                 </table> -->
<!-- 			</div> -->
<!-- 			<div class="modal-footer form-btn"> -->
<!-- 				<button class="btn dark" type="button"   data-dismiss="modal" >关闭</button> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->




