<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script type="text/javascript" src="${sysPath}/js/ueditor/ueditor.config.js"></script>
<script async  src="${sysPath}/js/com/ic/mail/mail.validate.js" type="text/javascript"></script>
<!--外部人员选择树-->
<script src="${sysPath}/js/lib/common/outSideUserForMail.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/contacts/contactsInteract.validate.js" type="text/javascript"></script>
<script type="text/javascript" src="${sysPath}/js/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
//根据权限判断是否显示外部人员选择树
	function getOutSideUserButtons(itemId) {
		var add = "<shiro:hasPermission name='contacts:add'><a class='btn btn-file no-all input-group-btn' id='"+itemId+"Div' role='button' data-toggle='modal'><i class='fa fa-users'></i></a></shiro:hasPermission>";
		return add;
};
   function getOutSideUserAdd(mail,nameIndex) {
	var add = "<shiro:hasPermission name='contacts:add'><img style='cursor:hand;vertical-align: middle;margin-top:-1px' src="+getRootPath()+"/img/add.png width=11 height=11 onclick=outSideUser.showOutSideLayer(0,'"+mail+"','"+nameIndex+""+mail+"') ></shiro:hasPermission>";
	return add;
};
//邮件回复是否显示人员选择树
function getOutSideUserReply(mail) {
	var add = "<shiro:hasPermission name='contacts:add'><img style='cursor:hand' src="+getRootPath()+"/img/edit.png onclick=outSideUser.showOutSideLayer(1,'"+mail+"') ></shiro:hasPermission>";
	return add;
};

function getOutSideUserReplyO(mail,nameIndex) {
	var add = "<shiro:hasPermission name='contacts:add'><img style='cursor:hand' src="+getRootPath()+"/img/add.png width=11 height=11 onclick=outSideUser.showOutSideLayer(0,'"+mail+"','"+nameIndex+""+mail+"') ></shiro:hasPermission>";
	return add;
};

</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
  </div>
</header>
<section class="tree-fluid m-t-md">
	<section class="panel">
		<div class="table-wrap">
			
			<section class="email-btn form-btn">
				<button class="btn dark" type="button" id="btnSend">发送</button>
				<button class="btn" type="button" id="btnSave">存草稿箱</button>
				<button class="btn" type="button" id="btnPreview">预览</button>
				<button class="btn" type="button" id="btnCancel">取消</button>
		   </section>
			<form class="cmxform" id="mailForm">
				<!--start 邮件-->
				<dl class="email-horizontal padder-v">
					<dt class="control-label">发件人</dt>
					
					<dd class="controls clearfix">
						<input type="hidden" id="returnURL" value="${mail.returnURL }"/>
						<input type="hidden" id="autoSaveInterval" name="autoSaveInteval" value="${autoSaveInterval }"/>
						<input type="hidden" id="id" name="id" value="${mail.id}"/>
						<input type="hidden" id="mailboxId" name="mailboxId" value="${mail.mailboxId }" />
						<input type="hidden" id="senderMail" name="senderMail" value="${mail.senderMail }" />
						<input type="hidden" id="senderUserId" name="senderUserId" value="${mail.senderUserId }" />
						<input type="hidden" id="messageId" value="${mail.messageId }"/>
						<input type="hidden" id="isNew"  name="isNew" value="${isNew }">
						<input type="hidden" id="displayName"  name="displayName" value="${user.displayName  }">
						<input type="hidden" id="files"  name="files" value="${files}">
						<input type="hidden" id="mailReplyId" name="mailReplyId" value="${mail.mailReplyId}">
						<input type="hidden" id="folderId" name="folderId" value="${folderId}">
						<c:choose>
						  <c:when test="${folderId eq 2}">
						  <c:if test="${mail.mailboxId eq 1}">
										${user.displayName  }
									</c:if>
									<c:if test="${mail.mailboxId ne 1}">
										${mail.senderMail }
						  </c:if>
						  </c:when>
						<c:otherwise>
						<section class="form-btn inline">
						<div class="dropdown inline">
							<button class="btn dropdown-toggle" data-toggle="dropdown"
								type="button" id="btn-selected-mailbox">
								<span id="selected-mailbox">选择邮箱</span><i
									class="fa fa-chevron-down m-l-sm"></i>
							</button>
							<ul class="dropdown-menu animated fadeInRight" id="mailBoxList">
								<li><a href="javascript:newmail.mailbox(1,'${user.displayName  }');">${user.displayName  }</a></li>
								<c:forEach items="${mailboxList}" var="m">
									<li><a
										href="javascript:newmail.mailbox(${m.id },'${m.address }');">${m.address }</a></li>
								</c:forEach>
							</ul>
						</div>
					</section>
						</c:otherwise>
						</c:choose>
						<div class="fr m-t-n-xs">
							<div class="email-btn-group">
								<a href="#" class="btn m-r-xs email-btn-csr">添加抄送</a> <a
									href="#" class="btn m-r-xs email-btn-msr">添加密送</a>
							</div>
							<a href="#" class="btn email-btn-qfdx">使用群发单显</a>
						</div>
					</dd>
				</dl>
				<dl class="email-horizontal email-sjr" >
					<dt class="control-label">收件人</dt>
					<dd class="controls">
						<span class="internalText">
							<input class="internalText" type="hidden" id="echoTo"  value='${mail.innerTo }'/> 
							<div id="controlTreeTo"  onfocusout ="newmail.compareAll()"></div>
							<input type="hidden" id="mailTo">
						</span>
						<span class="externalText">
							<input type="hidden" id="externalto" name="externalto" value='${mail.to}'>
							<span outSideUser="true" name="externalTo" onfocusout ="newmail.compareAll()">
						</span>	
						</span>
						<input type="hidden" id="toValid" name="toValid"></input>
						<input type="hidden" id="mailToOut">
					</dd> 
				</dl>
				<dl class="email-horizontal email-qfdx hide">
					<dt class="control-label">群发单显</dt>
					<dd class="controls">
						<span class="internalText hide">
							<input type="hidden" id="echoSs"  value='${mail.innerSs }'> 
							<div id="controlTreeSs" onfocusout ="newmail.compareAll()"></div>
							<input type="hidden" id="mailTo">
						</span>
						<span class="externalText hide">
						<input type="hidden" id="externalss" name="externalss" value='${mail.showSingle}'>
								<span outSideUser="true" name="externalShowSingle" onfocusout ="newmail.compareAll()">
							</span>
						</span>
						<input type="hidden" id="showSingleValid" name="showSingleValid"></input>
						<input type="hidden" id="mailToOut">
					</dd>
				</dl>
				<div class="email-cs-ms">
					<dl class="email-horizontal email-csr hide">
						<dt class="control-label">抄送人</dt>
						<dd class="controls">
							<span class=" internalText hide">
								<input type="hidden" id="echoCc"  value='${mail.innerCc }'>
								<div id="controlTreeCc" onfocusout ="newmail.compareAll()"></div>
							</span>
							<span class="externalText hide">
								<input type="hidden" id="externalcc" name="externalcc" value='${mail.cc}'/>
								<span outSideUser="true" name="externalCc" onfocusout ="newmail.compareAll()">
								</span>
							</span>
						</dd>
					</dl>
					<dl class="email-horizontal email-msr hide">
						<dt class="control-label">密送人</dt>
						<dd class="controls">
							<span class="internalText hide">
								<input type="hidden" id="echoBcc"  value='${mail.innerBcc }'>
								<div id="controlTreeBcc" onfocusout ="newmail.compareAll()"></div>
							</span>
							<span  class="externalText hide">
								<input type="hidden" id="externalbcc" name="externalbcc" value='${mail.bcc}'/>
                             <span outSideUser="true" name="externalBcc" onfocusout ="newmail.compareAll()">
                             </span>
							</span>
						</dd>
					</dl>
				</div>
				<dl class="email-horizontal">
					<dt class="control-label">主 题</dt>
					<dd class="controls">
						<input type="text" maxlength="80" id="mailTitle" name="mailTitle" value="${mail.mailTitle }" oninput="newmail.OnInput(event)" onpropertychange="newmail.OnPropChanged(event)"></input>
					</dd>
				</dl>
				<dl class="email-horizontal email-nocol">
					<dt class="control-label">附 件</dt>
					<dd class="controls">
						<input type="file" class="filestyle" data-icon="false"
							data-classbutton="btn btn-file input-group-btn"
							data-classinput="form-file" id="filestyle-0"
							style="position: fixed; left: -500000px;" name="attachs">
						<div class="bootstrap-filestyle input-group">
								<a class="btn dark" type="button" href="#fileUpload-edit"  role="button" data-toggle="modal" id="queryattach" name="queryattach">附件</a>
						</div>
						 <ul id="attachList"></ul>
					</dd>
				</dl>
				<!--end 邮件-->
				<!--start 编辑器-->
				<div class="email-compile" onfocusout ="newmail.compareAll()">
					<textarea rows="10" id="mailContent" name="mailContent" >${mail.mailContent }</textarea>
				</div>
				<!--end 编辑器-->
				<!--start 更多选项-->
				<div class="table-wrap hide" id="mail-options">
					<table class="form-table" >
						<tr style="height:50px;">
							<!-- <td class="w140"><button  class="btn" 
									type="button" id="signature" >插入签名</button></td> -->
							<td class="w140"><label class="checkbox inline"><input
									type="checkbox" id="pressing" name="pressing" value="1"  <c:if test="${mail.pressing eq 1 }">checked</c:if>>紧急</label></td>
							<td class="w140"><label class="checkbox inline" ><input
									type="checkbox" id="smsAlert" name="smsAlert" value="1" <c:if test="${mail.smsAlert eq 1 }">checked</c:if>>提醒</label></td>
							<td class="w100"><label class="checkbox inline innermail">
							<input type="checkbox" class="encryption" id="encryption"
									name="encryption" value="1" <c:if test="${mail.encryption eq 1 }">checked</c:if>>加密</label></td>
							<td class="encryption-td" <c:choose><c:when test="${mail.encryption eq 1 }">style="display: table-cell;"</c:when>
						      <c:otherwise>style="display: none;"</c:otherwise></c:choose>>
						   <label
								class="checkbox inline m-t-n-xs m-r">密码</label> <input
								type="text" class="input-medium" id="encryptionPass"
								name="encryptionPass" value="${mail.encryptionPass }"> <label
								class="checkbox inline m-t-n-xs m-r m-l">确认密码</label> <input
								type="text" class="input-medium" id="confirmPass"
								name="confirmPass"></td>
						</tr>
						<%-- <tr style="background:none;">
							<td><label class="checkbox inline innermail"><input
									type="checkbox" id="replyTexting" name="replyTexting" value="1" <c:if test="${mail.replyTexting eq 1 }">checked</c:if>>回复邮件提醒</label></td>
							<td><select class="w100" id="replyTextingTime" name="replyTextingTime" disabled="disabled">
									<option value="0">-请选择-</option>
									<option value="1" <c:if test="${mail.replyTextingTime eq 1 }">selected</c:if>>1小时</option>
									<option value="2" <c:if test="${mail.replyTextingTime eq 2 }">selected</c:if>>2小时</option>
									<option value="3" <c:if test="${mail.replyTextingTime eq 3 }">selected</c:if>>3小时</option>
									<option value="4" <c:if test="${mail.replyTextingTime eq 4 }">selected</c:if>>4小时</option>
									<option value="5" <c:if test="${mail.replyTextingTime eq 5 }">selected</c:if>>5小时</option>
									<option value="6" <c:if test="${mail.replyTextingTime eq 6 }">selected</c:if>>6小时</option>
							</select></td>
							<td></td>
							<td></td>
							<td></td>
						</tr> --%>
					</table>
				</div>
			</form>
			<div id="outSideUserTreeDiv"></div>
			<div class="email-btn form-btn m-t-md clearfix">
				<a href="javascript:newmail.send();" class="btn dark fl"
					role="button">发送</a> <a href="javascript:newmail.save();" class="btn fl" role="button" >存草稿箱</a>
				<a href="javascript:newmail.preview();" class="btn fl" role="button">预览</a>
				<a href="javascript:newmail.backToInbox();" class="btn fl" role="button">取消</a>
				 <a href="#"
					class="btn dark fr mail-options-btn">更多选项 <i
					class="fa fa-chevron-down"></i></a>
			</div>
			<!--end 更多选项-->
		</div>
	</section>
</section>
</section>
<div class="modal fade panel" id="previewer" aria-hidden="false">
</div>

   <!---------------------------------------- 附件部分-------------------------------------- --> 
<div class="modal fade panel" id="fileUpload-edit" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="closebtn" data-dismiss="modal" onclick="newmail.clearAttach()">×</button>
				<h4 class="modal-title">添加上传文件</h4>
			</div>
			<div class="modal-body">
			<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
				<input type="hidden" name="businessId" id="businessId" value="${mail.id }"/>
				<input type="hidden" name="businessTable" id="businessTable"  value="TTY_IC_MAIL"/> 
				<!-- upload_type 1为单传  0为多传-->
                <input type="hidden" id="upload_type" value="0"> 
                <!-- isshow 1为查看附件  0为管理附件-->
                <input type="hidden" id="isshow" value="0"> 
                <!-- islogicDel 1为逻辑删除  0为物理删除-->
                <input type="hidden" id="islogicDel" value="1">
                <input type="hidden" id="upload_div_name" value="fileUpload-edit">
                <input type="hidden" id="upload_close_callback" value="newmail.clearAttach">
			 <%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button"  data-dismiss="modal" onclick="newmail.clearAttach()">关&nbsp;闭</button>
			</div>
		</div>
	</div>
</div>
<div class="loading" id="dataLoad_mail"></div>	
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script> 
<script async  src="${sysPath}/js/com/ic/mail/newMail.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>