<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page trimDirectiveWhitespaces="true" %> 
<script src="${sysPath}/js/com/ic/mail/mailView.js"
	type="text/javascript"></script>
</head>
<body>
<!--start 详细邮件信息-->
<div class="modal-dialog w900">
	<div class="modal-content">
		<div class="modal-header ">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h4 class="modal-title">
			<c:choose>
				<c:when test="${isFromTo eq true }">邮件内容</c:when>
				<c:otherwise>邮件预览</c:otherwise>
			</c:choose>
			&nbsp;${mail.mailTitle }</h4>
		</div>
		<div class="modal-body form-btn clearfix">
			<!--  button  type="button" class="btn" data-dismiss="modal">关闭</button>-->
			
				<c:if test="${isFromTo eq true }">
					<button class="btn dark" type="button"  onclick="javascript:window.open('${sysPath }/ic/mail/manageReplyMail.action?id=${mail.id}&reply=0');">回复</button>
					<button class="btn" type="button" onclick="javascript:window.open('${sysPath }/ic/mail/manageReplyMail.action?id=${mail.id}&reply=1');">全部回复</button>
					<button class="btn" type="button" onclick="javascript:window.open('${sysPath }/ic/mail/manageForwarding.action?ids=${mail.id}');">转发</button>
				</c:if>
				
			<input type="hidden" id="id" value="${mail.id }" /> <input
				type="hidden" id="folder" value="${mail.mailFolderId }" />
			<input type="hidden" id="mailtoview"  value="${mail.to}"/>
			<input type="hidden" id="mailccview"  value="${mail.cc}"/>
			<input type="hidden" id="mailbccview"  value="${mail.bcc}"/>
			<input type="hidden" id="mailShowSingleview"  value="${mail.showSingle}"/>
			<dl class="email-horizontal1">
				<dt class="control-label">发件人</dt>
				<dd class="controls" id="senderMailEcho">
				<p class="p-hide">
					<c:choose>
						<c:when test="${mail.mailboxId eq 1 }">
									${ mail.senderUserName}
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
						<c:when test="${ not empty mail.showSingle}">
									群发单显
						  </c:when>
						<c:otherwise>
									收件人
						</c:otherwise>
					</c:choose>
					<%-- <c:choose>
								<c:when test="${mail.mailboxId eq 1 }">
									<c:choose>
										<c:when test="${ not empty mail.showSingle}">
													群发单显
										  </c:when>
										<c:otherwise>
													收件人
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty mail.showSingle}">
													群发单显
										  </c:when>
										<c:otherwise>
													收件人
										</c:otherwise>
									</c:choose>
								</c:otherwise>
        		</c:choose> --%>
				</dt>
				<dd class="controls" >
				<p class="fl p-hide" id="showSingle">
				    <span id="showSingleSpanView">${mail.showSingle }</span>
				 </p><p id="mailShowShowSingleView" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
				</dd>
			</dl>
			<dl class="email-horizontal1 clearfix <c:if test="${empty mail.to}">hide</c:if>" id="dlTo">
				<dt class="control-label">
						收件人
				</dt>
				<dd class="controls" >
				<p class="fl p-hide" id="receiver">
				   <span id="receiverSpanView" > ${mail.to }</span>
				 </p><p id="mailtoShowView" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
				</dd>
			</dl>
			<dl class="email-horizontal1 clearfix <c:if test="${empty mail.cc}">hide</c:if>" id="dlCc">
				<dt class="control-label">
						抄送人
				</dt>
				<dd class="controls" >
				<p class="fl p-hide" id="receiverCc">
				    <span id="receiverCcSpanView">${mail.cc }</span>
				 </p><p id="mailccShowView" class="fl w-p1"><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
				</dd>
			</dl>
			<dl class="email-horizontal1 clearfix <c:if test="${empty mail.bcc}">hide</c:if>" id="dlBcc">
				<dt class="control-label">
						密送人
				</dt>
				<dd class="controls" >
				<p class="fl p-hide" id="receiverBcc">
				    <span id="receiverBccSpanView">${mail.bcc }</span>
				 </p><p id="mailbccShowView" class="fl w-p1" ><a href="#" class="email-receiver">更多<i class="fa fa-angle-down"></i></a></p>
				</dd>
			</dl>

			<dl class="email-horizontal1 clearfix">
				<dt class="control-label">时　间</dt>
				<dd class="controls" id="senderTime">
					<p><fmt:formatDate value="${mail.senderTime }" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></p>
				</dd>
			</dl>
			<c:if test="${not empty mail.attachs }">
				<dl class="email-horizontal1 clearfix">
					<dt class="control-label">附　件</dt>
					<dd class="controls">
								<span id="attach"> <c:forEach var="attach"
										items="${mail.attachs }" varStatus="index">
												<i class="fa fa-paper-clip"></i>
												<a href="javascript:mailView.download('${attach.fileName }','${attach.resourcesName }')"
													class="blue-dark m-r-xs">${attach.fileName }</a>&nbsp;
													<c:if test="${attach.fileSize/1000<1000}"><fmt:formatNumber value="${attach.fileSize/1000 }" pattern="#0.00" type="number"/>&nbsp;KB</c:if>
													<c:if test="${attach.fileSize/1000>1000 and attach.fileSize/1000<1000000}"><fmt:formatNumber value="${attach.fileSize/1000000 }" pattern="#0.00" type="number"/>&nbsp;MB</c:if>
													<c:if test="${attach.fileSize/1000>1000000}"><fmt:formatNumber value="${attach.fileSize/1000000000 }" pattern="#0.00" type="number"/>&nbsp;GB</c:if>
												
									</c:forEach>
								</span>
		
					</dd>
				</dl>
			</c:if>
			
			<div class="email-compile table-wrap" id="mailContent">
			${mail.mailContent }</div>
		</div>
		<!--end 详细邮件信息-->

		<!--start 邮件内容-->
		
		<div class="modal-footer form-btn">
			<button type="button" class=" btn dark" data-dismiss="modal">关&nbsp;闭</button>
		</div>
		<!--end 邮件内容-->
	</div>
</div>