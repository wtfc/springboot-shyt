<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<script type="text/javascript" src="${sysPath}/js/ueditor/ueditor.config.js"></script>
<script src="${sysPath}/js/ueditor/ueditor.all.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in">
	<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
	<a class="btn dark fr" href="#myModal-edit" role="button" id="showAddDiv_t" data-toggle="modal">新 增</a>
</header>
<!------------------------------------------------------ 显示的块 ---------------------------------------------------->
<input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/>
<input type="hidden" id="token" name="token" value=${token } />
<section class="panel m-t-md">
	<!--start 签名管理-->
	<h2 class="panel-heading clearfix ">邮件签名</h2>
	<br>
	<div id="list">

	</div>
	<!--end 签名管理-->
	<section class="clearfix">
		<!--start 按钮-->
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#myModal-edit" id="showAddDiv" role="button"
				data-toggle="modal">新 增</a>
		</section>
		<!--end 按钮-->
	</section>
	<!--start 设置默认签名-->
	<h2 class="panel-heading clearfix ">设置默认签名</h2>
	<section class="email-btn form-btn m-t-md m-l">
		<input type="hidden" id="mailboxVal" name="mailboxVal"> 
		<form id="setDefaultForm">
		选择设置的邮箱
		<input type="hidden" id="mailBoxModifyDate" name="mailBoxModifyDate">
		<select id="mailboxId" name="mailboxId" class="inline m-t-xs m-r">
             </select>
		新邮件
			 <select id="signId" name="signId" class="inline m-t-xs m-r">
			 
             </select>
		回复/转发
			 <select id="replySignId" name="replySignId" class="inline m-t-xs">
			 
             </select>
		</form>
	</section>
	<!--end 设置默认签名-->
	<!--start 按钮-->
	<section class="clearfix m-b-md">
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" id="save_setDefault" role="button">保 存</a>
		</section>
	</section>
	<!--end 按钮-->
</section>
<!-- -----------------------------------------------------------显示的块end---------------------------------------------------------- -->
</section>
<!-- -----------------------------------------------------------弹出层begin---------------------------------------------------------- -->
   <div class="modal fade panel" id="myModal-edit" aria-hidden="false">
       <div class="modal-dialog w900">
           <div class="modal-content">
               <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal">×</button>
                   <h4 class="modal-title" id="title"></h4>
               </div>
               <div class="modal-body">
                   <form class="form-table" id="mailSignForm">
                    <input type="hidden" id="id" name="id"> 
					<input type="hidden" id="modifyDate" name="modifyDate">
					<input type="hidden" id="oldName" name="oldName">
                       <table class="table table-td-striped">
                           <tbody>
                               <tr>  
                                   <td style="width: 14%"><span class="required">*</span>签名名称</td>
                                   <td style="width: 86%"><input type="text"  id="signTitle" name="signTitle"/></td>
                               </tr>
                               <tr>  
                                   <td ><span class="required">*</span>签名内容</td>
                                   <td >
                                   	<textarea rows="10" id="signContent" name="signContent"></textarea>
                                   </td>
                               </tr>
                           </tbody>
                       </table>
                   </form>
               </div>
                <div class="modal-footer no-all form-btn">
					<button class="btn dark" type="button" id="savaOrModify">保存继续</button>
					<button class="btn" type="button" id="savaAndClose">保存退出</button>
					<button class="btn" type="button" data-dismiss="modal">关 闭</button>
				</div>
           </div>
       </div>
   </div>
<!-- -----------------------------------------------------------弹出层end---------------------------------------------------------- -->
<script src="${sysPath}/js/com/ic/mailSign/mailSignInteract.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/mailSign/mailSignInteract.validate.js" type="text/javascript"></script>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>
