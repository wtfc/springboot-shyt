<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section class="scrollable padder jcGOA-section" id="scrollable"> 	
<header class="con-header pull-in">
	<h1>邮件内容</h1>
	<div class="crumbs">
		<a href="#" onclick="homeloadmenu()">首页</a><i></i>互动交流<i></i>电子邮件<i></i>邮件内容
	</div>
</header>
		<section class="panel m-t-md">
			<div class="table-wrap">
				<section class="email-btn form-btn">
				    <button class="btn" type="button" id="btnBack">返回</button>
					<button class="btn" type="button" id="btnReply">回复</button>
					<button class="btn" type="button" id="btnReplyAll">全部回复</button>
					<button class="btn" type="button" id="btnForwarding">转发</button>
                   <c:choose>
					<c:when test="${mfid eq 4}">
						<button class="btn" type="button" id="btnShiftDelete">彻底删除</button>
					</c:when>
					<c:otherwise>
					<c:if test="${mfid ne 2}">
						<button class="btn" type="button" id="btnDelete">删除</button>
						<button id="topStarButton" class="btn" type="button" onclick ="mailDetail.setStar(${mail.id},${mail.receiver.folderId},${mail.receiver.id})">星标</button>
					</c:if>
					</c:otherwise>
					</c:choose>
					<div class="dropdown inline">
						<button class="btn dropdown-toggle" data-toggle="dropdown"
							type="button">
							移动至<i class="fa fa-chevron-down"></i>
						</button>
						<ul class="dropdown-menu animated fadeInRight" id="moveTo">
							
							<c:if test="${mfid ne 1}">
								<li><a href="javascript:mailDetail.moveTo(1);">收件箱</a></li>
							</c:if>
<!-- 							<c:if test="${mfid ne 2 or mfid ne 3 }"> -->
<!-- 								<li><a href="javascript:mailDetail.moveTo(2);">草稿箱</a></li> -->
<!-- 							</c:if> -->
							<c:if test="${mfid ne 3}">
								<li><a href="javascript:mailDetail.moveTo(3);">已发送</a></li>
							</c:if>
<!-- 							<c:if test="${mfid ne 4}">
								<li><a href="javascript:mailDetail.moveTo(4);">废件箱</a></li>
							</c:if>
 -->
						<c:forEach items="${mailFolderList }" var="folder">
						<c:choose>
							<c:when test="${folderId eq folder.id }">
								<li class="otherFolder hide"><a
									href="javascript:mailDetail.moveTo(${folder.id });">${folder.folderName }</a></li>
							</c:when>
							<c:otherwise>
								<li class="otherFolder"><a
									href="javascript:mailDetail.moveTo(${folder.id });">${folder.folderName }</a></li>
							</c:otherwise>
						</c:choose>
							</c:forEach>
						</ul>
					</div>
					<div class="inline fr">
						<c:choose>
						<c:when test="${mail.index eq 0 || mail.index eq -10 || mail.showPre eq false}">
							<button id="btnPre" class="btn" type="button" onclick="mailDetail.navigate(-1)" disabled>上一封</button>
						</c:when>
						<c:otherwise>
							<button id="btnPre" class="btn" type="button" onclick="mailDetail.navigate(-1)">上一封</button>
						</c:otherwise>
						</c:choose>
						<c:choose>
						<c:when test="${mail.index eq 999999 || mail.index eq -10 || mail.showNext eq false}">
							<button id="btnNext" class="btn m-r-none" type="button" onclick="mailDetail.navigate(1)" disabled>下一封</button>
						</c:when>
						<c:otherwise>
							<button id="btnNext" class="btn m-r-none" type="button" onclick="mailDetail.navigate(1)">下一封</button>
						</c:otherwise>
						</c:choose>
						
					</div>
				</section>
					</div>
			<header class="email-header table-wrap">
				<!--start 查看往来邮件-->
				<div class="panel-heading clearfix dropup">
				  <c:choose>
					<c:when test="${mfid eq 4}">
						<h2 id="detailStarMailForWaste">
							<c:if test="${mail.receiver.starMail eq 1}">
								<i class="fa fa-star yellow-dark fr m-t-mxs"></i>
							</c:if>
						</h2>
					</c:when>
					<c:otherwise>
					<c:if test="${mfid ne 2}">
						<h2 id="detailStarMail">
							<c:if test="${mail.receiver.starMail eq 0}">
								<a href="javascript:mailDetail.setStar(${mail.id},${mail.receiver.folderId},${mail.receiver.id});" class="fr"><i class="fa fa-star fr text-grey-999 m-t-mxs" id="lightstar${mail.receiver.id}"></i></a>
							</c:if>
							<c:if test="${mail.receiver.starMail eq 1}">
								<a href="javascript:mailDetail.setStar(${mail.id},${mail.receiver.folderId},${mail.receiver.id});" class="fr"><i class="fa fa-star yellow-dark fr m-t-mxs" id="lightstar${mail.receiver.id}"></i></a>
							</c:if>
						</h2>
					</c:if>
					</c:otherwise>
					</c:choose>
					<h2 id="mailTitle" style="width:85%;margin-left:5px">${mail.mailTitle }</h2>
					<div class="fr" id="outGoingDiv">
						<%-- <c:choose>
							<c:when test="${mail.mailboxId eq 1 }">
								<c:if test="${mail.senderUserId ne currentUserId}">
									
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${mailboxAddress ne mail.senderMail }">
									<a href="#" class="btn outgoing-mail-down" role="button"
									data-toggle="modal">查看往来邮件</a>
								</c:if>
							</c:otherwise>
						</c:choose> --%>
						<a href="#" class="btn outgoing-mail-down" role="button"
									data-toggle="modal">查看往来邮件</a>
					</div>
					<div class="outgoing-mail">
		
						<div class="outgoing-body">
							<div class="outgoing-h2">
								全部往来邮件<b class="badge bg-red m-l-xs" id="relationMailCount">0</b><a
									href="#" class="fr outgoing-mail-up"><i class="fa fa-angle-up"></i></a>
							</div>
							<div class="outgoing-email-content">
								<ul class="outgoing-list clearfix" id="relationMail">
								
								</ul>
							</div>
							<div class="outgoing-h2">
								全部往来附件<b class="badge bg-red m-l-xs" id="relationAttachCount">0</b> <a href="#"
									class="fr outgoing-acces-up"><i class="fa fa-angle-down"></i></a>
							</div>
							
							<div class="outgoing-acces-content">
								<ul class="outgoing-list clearfix" id="relationAttach">
									
								</ul>
		
							</div>
						</div>
		
					</div>
				</div>
				<!--end 查看往来邮件-->
				<!--start 详细邮件信息-->
				<div class="table-wrap corpus">
					<input type="hidden" id="id" value="${mail.id }" />
					<input type="hidden" id="mailboxId" value="${mail.mailboxId }" /> 
					<input type="hidden" id="isAllMail" value="${isAllMail}" /> 
					<input type="hidden" id="folder" value="${mail.receiver.folderId}" />
					<input type="hidden" id="mailTitleSearch"  value="${mailTitle }"/> 
					<input type="hidden" id="senderUserId" value="${senderUserId }" /> 
					<input type="hidden" id="senderMail" value="${senderMail }" /> 
					<input type="hidden" id="mailSenderUserId" value="${mail.senderUserId }" /> 
					<input type="hidden" id="mailSenderMail" value="${mail.senderMail }" /> 
					<input type="hidden" id="index" value="${mail.index }" /> 
					<input type="hidden" id="returnURL" value="${mail.returnURL }" />
					<input type="hidden" id="receiveMail" value="${mailboxAddress}" />
					<input type="hidden" id="folderId" value="${folderId}" />
					<input type="hidden" id="recId" value="${mail.receiver.id}" />
					<input type="hidden" id="showNext" value="${mail.showNext}" />
					<input type="hidden" id="showPre" value="${mail.showPre}" />
					<input type="hidden" id="searchReceiveTimeBegin"  value="${searchReceiveTimeBegin }"/>
					<input type="hidden" id="searchReceiveTimeEnd"  value="${searchReceiveTimeEnd }"/>
					<input type="hidden" id="encryption"  value="${mail.encryption}"/>
					<input type="hidden" id="displayName"  value="${user.displayName}"/>
					<input type="hidden" id="receiversReadFlag"  value="${readFlag}"/>
					<input type="hidden" id="wrongPassword"  value="${mail.wrongPassword}"/>
					<input type="hidden" id="currentUserId"  value="${currentUserId}"/>
					<input type="hidden" id="mailboxAddress"  value="${mailboxAddress}"/>
					
					<input type="hidden" id="mailto"  value="${mail.to}"/>
					<input type="hidden" id="mailcc"  value="${mail.cc}"/>
					<input type="hidden" id="mailbcc"  value="${mail.bcc}"/>
					<input type="hidden" id="mailShowSingle"  value="${mail.showSingle }"/>
					
					<dl class="email-horizontal1 clearfix">
						<dt class="control-label">发件人</dt>
						<dd class="controls" >
						<p class="fl p-hide" id="senderMailEcho">
							<c:choose>
								<c:when test="${mail.mailboxId eq 1 }">
								    <a style = "cursor :pointer" class="email-receivers" onclick="showOnlinePerson.showPersonInfo(${ mail.senderUserId})">
										${ mail.senderUserName}
									</a>	
								</c:when>
								<c:otherwise>
											${ mail.senderMail}
										</c:otherwise>
							</c:choose>
							</p>
						</dd>
					</dl>
					
					<dl class="email-horizontal1 clearfix <c:if test="${empty mail.showSingle}">hide</c:if>" id="dlShowSingle">
						<dt class="control-label">

							<c:choose>
								<c:when test="${mail.mailboxId eq 1 }">
									<c:choose>
										<c:when test="${mail.senderUserName eq user.displayName }">
													群发单显
										  </c:when>
										<c:otherwise>
													收件人
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${mailboxAddress eq mail.senderMail }">
													群发单显
										  </c:when>
										<c:otherwise>
													收件人
										</c:otherwise>
									</c:choose>
								</c:otherwise>
        		</c:choose>
						
				</dt>
						<dd class="controls" >
						<c:choose>
						<c:when test="${mail.mailboxId eq 1 }">
						<p class="fl p-hide" id="showSingle">
						    <span id="showSingleSpan">${mail.newSs }</span>
						 </p><p id="mailShowShowSingle" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
						</c:when>
					   <c:otherwise>
					   <p class="fl p-hide" id="showSingle">
						    <span id="showSingleSpan">${mail.showSingle }</span>
						 </p><p id="mailShowShowSingle" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
					   </c:otherwise>
					   </c:choose>
						</dd>
					</dl>
					<c:choose>
						<c:when test="${mail.mailboxId eq 1 }">
					<dl class="email-horizontal1 clearfix <c:if test="${empty mail.newTo}">hide</c:if>" id="dlTo">
						<dt class="control-label">
								收件人
						</dt>
						<dd class="controls" >
						<p class="fl p-hide" id="receiver">
						   <span id="receiverSpan"> ${mail.newTo } </span>
						 </p><p id="mailtoShow" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
						</dd>
					</dl>
					<dl class="email-horizontal1 clearfix <c:if test="${empty mail.newCc}">hide</c:if>" id="dlCc">
						<dt class="control-label">
								抄送人
						</dt>
						<dd class="controls" >
						<p class="fl p-hide" id="receiverCc">
						    <span id="receiverCcSpan"> ${mail.newCc }</span>
						 </p><p id="mailccShow" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
						</dd>
					</dl>
					<dl class="email-horizontal1 clearfix <c:if test="${empty mail.newBcc}">hide</c:if>" id="dlBcc">
						<dt class="control-label">
								密送人
						</dt>
						<dd class="controls" >
						<p class="fl p-hide" id="receiverBcc">
						   <span id="receiverBccSpan"> ${mail.newBcc }</span>
						 </p><p id="mailbccShow" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
						</dd>
					</dl>
					</c:when>
					 <c:otherwise>
						<dl class="email-horizontal1 clearfix <c:if test="${empty mail.to}">hide</c:if>" id="dlTo">
						<dt class="control-label">
								收件人
						</dt>
						<dd class="controls" >
						<p class="fl p-hide" id="receiver">
						   <span id="receiverSpan"> ${mail.to } </span>
						 </p><p id="mailtoShow" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
						</dd>
					</dl>
					<dl class="email-horizontal1 clearfix <c:if test="${empty mail.cc}">hide</c:if>" id="dlCc">
						<dt class="control-label">
								抄送人
						</dt>
						<dd class="controls" >
						<p class="fl p-hide" id="receiverCc">
						    <span id="receiverCcSpan"> ${mail.cc }</span>
						 </p><p id="mailccShow" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
						</dd>
					</dl>
					<dl class="email-horizontal1 clearfix <c:if test="${empty mail.bcc}">hide</c:if>" id="dlBcc">
						<dt class="control-label">
								密送人
						</dt>
						<dd class="controls" >
						<p class="fl p-hide" id="receiverBcc">
						   <span id="receiverBccSpan"> ${mail.bcc }</span>
						 </p><p id="mailbccShow" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
						</dd>
					</dl>							
					</c:otherwise>
					</c:choose>
					
					<dl class="email-horizontal1 clearfix">
						<dt class="control-label">时　间</dt>
						<dd class="controls" >
						<p class="fl" id="senderTime">
							<fmt:formatDate value="${mail.senderTime }" type="both"
								pattern="yyyy-MM-dd HH:mm:ss" /></p>
						</dd>
					</dl>
					<dl class="email-horizontal1 clearfix" id="attachList" style="display:none">
						<c:if test="${not empty mail.attachs }">
							<dt class="control-label">附　件</dt>
							<dd class="controls">
								<span id="attach" class="fl">
								<c:forEach var="attach"
										items="${mail.attachs }" varStatus="index">
										<c:choose>
											<c:when
												test="${mail.mailboxId eq 1 or mail.receiver.folderId eq 2 or mail.receiver.folderId eq 3}">
												<i class="fa fa-paper-clip"></i>
												<a
													href="javascript:mailDetail.download('${attach.fileName }','${attach.resourcesName }')"
													class="blue-dark m-r-xs">${attach.fileName }</a>&nbsp;
													<c:if test="${attach.fileSize/1000<1000}"><fmt:formatNumber value="${attach.fileSize/1000 }" pattern="#0.00" type="number"/>&nbsp;KB</c:if>
													<c:if test="${attach.fileSize/1000>1000 and attach.fileSize/1000<1000000}"><fmt:formatNumber value="${attach.fileSize/1000000 }" pattern="#0.00" type="number"/>&nbsp;MB</c:if>
													<c:if test="${attach.fileSize/1000>1000000}"><fmt:formatNumber value="${attach.fileSize/1000000000 }" pattern="#0.00" type="number"/>&nbsp;GB</c:if>
													</c:when>
											<c:otherwise>
											<i class="fa fa-paper-clip"></i>
												<a
													href="javascript:mailDetail.downloadExt('${attach.fileName }','${mail.id }')"
													class="blue-dark m-r-xs">${attach.fileName }</a>&nbsp;
													</c:otherwise>
										</c:choose>
									</c:forEach>
		
								</span>
		
							</dd>
						</c:if>
						
					</dl>
					<dl>
						<a href="#" class="fr f18 corpus-up"><i class="fa fa-angle-up"></i></a>
					</dl>
		</div>
				<!--end 详细邮件信息-->
				<!--start 缩略 邮件信息-->
				<div class="table-wrap simple padder-v">
					<c:if test="${mail.mailboxId eq 1 }"><b class="m-r-xs">发件人：</b> ${mail.senderUserName }</c:if>
					<c:if test="${mail.mailboxId ne 1 }"><b class="m-r-xs">发件人：</b> ${mail.senderMail }</c:if>
					 <b class="m-l m-r-xs">发送时间：</b> <fmt:formatDate value="${mail.senderTime }" type="both"
						pattern="yyyy-MM-dd HH:mm:ss" />
					<a href="#" class="fr f18 simple-down"><i
						class="fa fa-angle-down"></i></a>
				</div>
				<!--end 缩略 邮件信息-->
			</header>
		
			<c:choose>
				<c:when test="${empty mail.encryption or mail.encryption eq 0 }">
					<!--start 邮件内容-->
					<div class="email-compile table-wrap" id="mailContent">
						${mail.mailContent }
					</div>
					<div class="email-compile table-wrap hide" id="password">
						<section class="m-b-sm">这是一封由 <strong> ${mail.senderUserName }</strong> 发出的加密邮件，需要解密才能阅读。</section>
						<table class="email-table form-table">
						<tr>
							<td ><label
								class="inline m-t-n-xs m-r">密码</label> <input
								type="text" class="input-medium" id="encryptionPass"
								name="encryptionPass"> 
								<c:if test="${not empty mail.encryptionPass and mail.wrongPassword }">
									<label class="inline" style="color:red" id="errorMsg" id="errorMsg">密码错误！</label>
								</c:if>
								<a href="#" class="a-icon i-new m-l-xs"  type="button" id="btnPswOk">确定</a>
							</td>
						</tr>
						</table>						
						 
					</div>
					<!--end 邮件内容-->
				</c:when>
				<c:when test="${mail.encryption eq 1 and mail.wrongPassword eq false }">
					<!--start 邮件内容-->
					<div class="email-compile table-wrap" id="mailContent">
						${mail.mailContent }
					</div>
					<div class="email-compile table-wrap hide" id="password">
						<section class="m-b-sm">这是一封由<strong>${mail.senderUserName }</strong> 发出的加密邮件，需要解密才能阅读。</section>
						<table class="email-table form-table">
						<tr>
							<td ><label
								class="inline m-t-n-xs m-r">密码</label> <input
								type="text" class="input-medium" id="encryptionPass"
								name="encryptionPass"> 
								<c:if test="${not empty mail.encryptionPass and mail.wrongPassword }">
									<label class="inline" style="color:red" id="errorMsg">密码错误！</label>
								</c:if>
								<a href="#" class="a-icon i-new m-l-xs"  type="button" id="btnPswOk">确定</a>
							</td>
						</tr>
						</table>						
						 
					</div>
					<!--end 邮件内容-->
				</c:when>
				<c:otherwise>
					<c:if test="${empty mail.encryptionPass or mail.wrongPassword }">
					<div class="email-compile table-wrap hide" id="mailContent">
						
					</div>
					<div class="email-compile table-wrap" id="password">
						<section class="m-b-sm">这是一封由 <strong>${mail.senderUserName }</strong> 发出的加密邮件，需要解密才能阅读。</section>
						<table class="email-table form-table">
						<tr>
							<td ><label
								class="inline m-t-n-xs m-r">密码</label> <input
								type="text" class="input-medium" id="encryptionPass"
								name="encryptionPass"> 		
								<c:if test="${not empty mail.encryptionPass and mail.wrongPassword }">
									<label class="inline" style="color:red" id="errorMsg">密码错误！</label>
								</c:if>	
								<a href="#" class="a-icon i-new m-l-xs"  type="button" id="btnPswOk">确定</a>					
							</td>
						</tr>
						</table>						
						 
					</div>
					</c:if>
				</c:otherwise>
			</c:choose>
			
			<!--start 邮件详细回复-->
			<div class="fast-reply-box table-wrap hide">
				<section>
					<textarea rows="4"   id="fastContent" wrap="hard"></textarea>
				</section>
				<section class="form-btn m-t">
					<a href="javascript:mailDetail.fastreply()" class="btn dark m-r-md" id="btnSend">发送</a> <a href="javascript:void(0);"
						class="btn close-fast-reply" id="btnCancel">取消</a> <a href="javascript:mailDetail.replySwitchToWhole();" class="btn dark fr">切换至完整模式</a>
				</section>
			</div>
			<!--end 邮件详细回复-->
			<!--start 邮件快速细回复-->
			<div class="form-table table-wrap">
				<input type="text" id="fast_reply" placeholder="可快速回复：" class="fast-reply">
			</div>
			<!--end 邮件快速细回复-->
			
			<!-- 阅读往来邮件 -->
			<div class="modal fade panel" id="previewer" aria-hidden="false"></div>
			
			<!-- 查看加密往来附件的密码对话框 -->
			<div class="modal fade panel" id="inputPassword" aria-hidden="false">
				<div class="modal-dialog">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" id="cl" data-dismiss="modal">×</button>
		                    <h4 class="modal-title">请输入查看密码</h4>
		                </div>
		                <div class="modal-body">
			            <form class="form-table" id="suggestForm">
			            		<input type="hidden" id="attachId" value=""/>
			            		<input type="hidden" id="attachFileName" value=""/>
			            		<input type="hidden" id="attachResourcesName" value=""/>
		                        <table class="table table-td-striped">
									<tbody>
										<tr>
											<td class="w115"><span class="required">*</span>密码</td>
											<td>
												<input type="text" id="attachpassword" name="attachpassword" />
											</td>
										</tr>
									</tbody>
								</table>
								</form>
		                </div>
		                <div class="modal-footer no-all form-btn">
							<button class="btn dark" type="button" id="passwordOk">确定</button>
							 <button class="btn" id="passwordCancel" type="button">取消</button>
		                </div>
		            </div>
		        </div>
			</div>
		</section>
		</section>
<script src='${sysPath}/js/lib/common/showOnlinePerson.js' type='text/javascript'></script>			
<script src="${sysPath}/js/com/ic/mail/mailDetail.js"	type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>