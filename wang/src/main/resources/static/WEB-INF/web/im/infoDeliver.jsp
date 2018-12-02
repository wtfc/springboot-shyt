<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/WorkflowHead.jsp"%>

<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${sysPath}/js/ueditor/ueditor.all.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/im/infoDeliver.validate.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
<%@ include file="/WEB-INF/web/include/WorkflowSheetStart.jsp"%>
	<header class="con-header ">
	<div class="con-heading fl"  id="navigationMenu">
		<h1>信息发布</h1>
		<div class="crumbs"></div>
	</div>
	</header>
	
	<section class="panel m-t-md">
		<input type="hidden" id="sendDisplayName" name="sendDisplayName"  value='${sendDisplayName}'>
		<input type="hidden" id="sendDisplayDepName" name="sendDisplayDepName"  value='${sendDisplayDepName}'>
	   	<form class="form-table table-wrap" id="infoDeliverForm">
	   	<input type="hidden" id="id" name="id">
	   	<input type="hidden" id="piId" name="piId" value='${piId}'>
	   	<input type="hidden" id="columnId" name="columnId"  >
	   	<input type="hidden" name="scanType" id="scanType" value="${scanType}"/>
	   	<input type="hidden" id="myBusinessUrl" name="myBusinessUrl" value="/im/info/infoMyDeliver.action"/>
	   	<input type="hidden" id="todoPage" name="todoPage" value="/im/infoApproval"/>
	   	<input type="hidden" id="token" name="token" value="${token}">
	   	<textarea style="display:none" id="infoJson" name="infoJson" value='${infoJson}'></textarea>
	   	<input type="hidden" id="isOpen" name="isOpen">
	    <input type="hidden" id="modifyDate" name="modifyDate">
	    <table class="table table-td-striped">
	        <tbody>
	            <tr>
	                <td class="w140">所属栏目</td>
	                <td colspan="3" id="column"></td>
	            </tr>
	            <tr>
	                <td><span workFlowForm='button' itemName="infoTitile" class="required">*</span>标题</td>
	                <td colspan="3"><input type="text" workFlowForm="textinput" itemName="infoTitile" name="infoTitile" id="infoTitile"></td>
	            </tr>
	            <tr>
	                <td>副标题</td>
	                <td colspan="3"><input type="text" workFlowForm="textinput" itemName="infoSubtitle" name="infoSubtitle" id="infoSubtitle"></td>
	            </tr>
	<!--             <tr> -->
	<!--                 <td>文档编号</td> -->
	<!--                 <td><input type="text" workFlowForm="textinput" itemName="docuCode" name="docuCode" id="docuCode"></td> -->
	<!--                 <td>关键字</td> -->
	<!--                 <td><input type="text" workFlowForm="textinput" itemName="keyword" name="keyword" id="keyword"></td> -->
	<!--             </tr> -->
	            <tr>
	                <td style="width:15%">作者</td>
	                <td style="width:30%"><input type="text" workFlowForm="textinput" itemName="author" name="author" id="author" value="佚名"></td>
	                <td style="width:15%">作者单位</td>
	                <td><input type="text" workFlowForm="textinput" itemName="authorUnit" name="authorUnit" id="authorUnit"></td>
	            </tr>
	            <tr>
	                <td>首页显示图片</td>
	                <td colspan="3">
	                <section class="w105">
	                	<img src="${sysPath}/images/demoimg/iphoto.jpg" id="portalPicImg" width="105" height="105">
	                	<input type="hidden" id="portalPic" name="portalPic" />
	                	<div class="form-btn"><div class="form-btn">
	                	<button class="btn dark w105" workFlowForm="button" itemName="uploadPhoto" type="button" role="button" id="uploadPhoto" name="uploadPhoto">上传图片</button>
						</div>
					</section>                    
	               </td>
	           </tr>
	           <tr>
	               <td>附件</td>
	               <td colspan="3">
	                   <a class="btn dark" workFlowForm="button" itemName="attachs" type="button" href="#fileUpload-edit"  role="button" data-toggle="modal"  name="queryattach" id="attachs">附件</a>
	                   <ul id="attachList"></ul>  
	                </td>
	            </tr>
	            <tr>
	                <td><span workFlowForm='button' itemName="sendTime" class="required">*</span>发布时间</td>
	                <td><input class="datepicker-input" workFlowForm="textinput" itemName="sendTime" name="sendTime" id="sendTime"  type="text" data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss"></td>
	                <td>有效期限（天）</td>
	                <td >
	                	<span workFlowForm='checkbox' itemName="effectiveTime1" class="fl m-t-xs">
	                		<label class="checkbox inline"><input type="checkbox" label="永久" id="effectiveTime1"  name="effectiveTime1" value="-1" checked />永久</label>
	                	</span>
	                	<span workFlowForm="textinput" itemName="effectiveTime2" class="fl m-r-xs">
	                		<input type="text" class="input-mini" id="effectiveTime2" name="effectiveTime2" value="" readOnly/><!-- <span class="fr m-t-sm">天</span> -->
	                	</span> 
	                	
	                	
	                </td>
	            </tr>
	            <tr>
	                <td>发布人</td>
	                <td id="sendName">
	                </td>
	                <td>发布部门</td>
	                <td id="sendDepName">
	                </td>
	            </tr>
	            <tr>
	                <td>内容</td>
	                <td colspan="3">
	                   	<!--  <a class="a-icon i-new" href="#" role="button"><i class="fa fa-file-word"></i>切换到word</a> -->
	                   	<div class="email-compile" workFlowForm="editor" itemName="infoContent">
	                    	<textarea rows="3" name="infoContent" id="infoContent"></textarea>
	                	</div>
	                </td>
	            </tr>
	            <tr>
	                <td>摘要</td>
	                <td colspan="3">
	                    <textarea rows="3" workFlowForm="textinput" itemName="infoSumm" name="infoSumm" id="infoSumm"></textarea>
	                </td>
	            </tr>
	            <tr>
	                <td>允许评论</td>
	                <td workFlowForm="radio" itemName="allowReview" colspan="3">
	                    <label class="radio inline"><input type="radio" label="是" name="allowReview" value="1" checked>是</label>
	                    <label class="radio inline"><input type="radio" label="否" name="allowReview" value="0">否</label>
	                </td>
	                <!-- <td>允许打印</td>
	                <td workFlowForm="radio" itemName="allowPrint">
	                    <label class="radio inline"><input type="radio" label="是" name="allowPrint" value="1" checked>是</label>
	                    <label class="radio inline"><input type="radio" label="否" name="allowPrint" value="0">否</label>
	                </td> -->
	            </tr>
	<!--             <tr> -->
	<!--                 <td>是否公开</td> -->
	<!--                 <td colspan="3" workFlowForm='radio' itemName="isOpen"> -->
	<!--                     <label class="radio inline"><input type="radio" label="是" name="isOpen" value="1">是</label> -->
	<!--                     <label class="radio inline"><input type="radio" label="否" name="isOpen" value="0" checked>否</label> -->
	<!--                 </td> -->
	<!--             </tr> -->
	            <tr class="open_range">
	                <td>公开范围</td>
	                <td colspan="3">
	                    <div workFlowForm='userSelect' itemName="deptAndPerson!openDeptAndPersonIds" id="deptAndPerson"></div>
	                </td>
	            </tr>
	        </tbody>
	    </table>
<!-- 	     <h2 class="panel-heading clearfix">领导审批</h2> -->
<!-- 	        <section class="input-table-group table-wrap pzhf w-p100"> -->
<!-- 		       	<section workflowSuggest="true" showLast="false" order="createTime" id="leaderApproval"> -->
<!-- 				</section> -->
<!-- 			</section> -->
		<div class="m-l-n-md m-b">
        	<span workFlowForm="container" itemName="zhuguanjuzhangApproval"><h2 class="panel-heading clearfix">主管局长审批</h2></span>
        </div>
        <div class="m-l-n-md m-b">
        	<span workFlowForm="container" itemName="shujiApproval"><h2 class="panel-heading clearfix">书记审批</h2></span>
        </div>
        <div class="m-l-n-md m-b">
        	<span workFlowForm="container" itemName="juzhangApproval"><h2 class="panel-heading clearfix">局长审批</h2></span>
        </div>
        <section>
        	<workflow:suggest itemId="leaderApproval" showLast="false" order="createTime" classStr="" style=""/>
        </section>
	</form>
	</section>
	<%@ include file="/WEB-INF/web/include/WorkflowButton.jsp"%>
	<%@ include file="/WEB-INF/web/include/WorkflowSheetEnd.jsp"%>
	<%@ include file="/WEB-INF/web/include/WorkflowPostscript.jsp"%>
</section>

<!--start 上传附件  -->
 <div class="modal fade panel" id="file-edit" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
					<h4 class="modal-title">添加上传文件</h4>
				</div>
				<div class="modal-body">
				<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
					<!-- islogicDel 1为逻辑删除 0为物理删除-->
					<input type="hidden" name="showDelBtn" id="showDelBtn" value="1"/>
					<input type="hidden" id="islogicDel" value="1">
					<input type="hidden" name="businessId" id="businessId" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource"/>
					<input type="hidden" name="businessTable" id="businessTable"  value="toa_im_info"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="0"> 
	                <input type="hidden" id="iscopy" value="0"> 
	                <input type="hidden" id="upload_div_name" value="file-edit">
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
<!--end 上传附件 -->

<div class="modal fade panel" id="portalPicUpload" aria-hidden="false">
   <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" class="close" id="closephotobtn" data-dismiss="modal">×</button>
               <h4 class="modal-title">上传首页图片</h4>
           </div>
           <div class="modal-body"> 
               <!-- upload_type 1为单传  0为多传-->
               <input type="hidden" id="photo_upload_type" value="1"> 
               <%@ include file="/WEB-INF/web/im/photoManage.jsp"%>
           </div>
           <div class="modal-footer form-btn">
               <button class="btn dark" type="button"  data-dismiss="modal">关 闭</button>
           </div>
       </div>
   </div>
</div>
<script src="${sysPath}/js/com/im/infoDeliver.js"></script>
<%@ include file="/WEB-INF/web/include/WorkflowFoot.jsp"%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>