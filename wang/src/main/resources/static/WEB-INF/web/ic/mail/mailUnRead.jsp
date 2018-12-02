<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
 </div>
</header>
<section class="panel email m-t-md dis-table">
	<section class="email-right">
		<!--start 邮件功能-->
		<section class="email-btn form-btn" style="display:none">
			<span class="fl m-r-sm m-t-sm">请选择邮箱</span>
			<div class="dropdown fl">
				<button class="btn dropdown-toggle" data-toggle="dropdown"
					type="button">
					<span id="selected-mailbox">内部邮箱</span><i
						class="fa fa-chevron-down"></i>
				</button>
				<ul class="dropdown-menu animated fadeInRight" id="mailBoxList">
					<li><a href="javascript:mail.mailbox(0,'全部邮箱');">全部邮箱</a></li>
					<li><a href="javascript:mail.mailbox(1,'内部邮箱');">内部邮箱</a></li>
					<c:forEach items="${mailboxList}"  var="m">
						<li><a href="javascript:mail.mailbox(${m.id },'${m.address }');" id="">${m.address }</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="fl tc mail-loading hide">
					<img src="${sysPath}/images/demoimg/jbox-loading1.gif" width="16"
						height="16" alt="" class="fl" /> <span class="fl">邮件读取中...</span>
			</div>
		</section>
		<section class="email-btn form-btn">
			<div role="search" class="email-search fr">
				<div class="input-append m-b-none">
					<input type="text" class="form-control aside-md"
						placeholder="查询邮件主题" id="simple-search-title">
					<button type="button" class="btn w0" id="simple-search">
						<i class="fa fa-search"></i>
					</button>
				</div>
				<a class="btn dark" type="button" id="advancedSearch" href="#"
					role="button" data-toggle="modal">高级搜索</a>
			</div>
			<!--  -->
			<input type="hidden" id="allMailbox" value="${allMailbox }"/>
			<!-- 隐藏域 文件夹ID做为查询条件-->
			<input type="hidden" id="url"  value="${url}"/>
			<input type="hidden" id="mailFolderId" name="mailFolderId"	value="${mfid}" />
			<input type="hidden" id="mailEasyTitle"  value="${mailEasyTitle }"/>
			<input type="hidden" id="mailFolderName" name="mailFolderName"	value="${mailFolderName}" />
			<input type="hidden" id="starMail" name="starMail"	value="${starMail}" />
			<input type="hidden" id="excludeFolderId" name="excludeFolderId" value="${excludeFolderId}" />
			<input type="hidden" id="readFlag" name="readFlag" 	value="${readFlag}" />
			<input type="hidden" id="userId" name="userId"	value="${userId}" />
			<input type="hidden" id="address" name="address"	value="${address}" />
			<!-- 跳转回来时带回的参数 start -->
			<input type="hidden" id="returned" value="${returned }"/>
			<input type="hidden" id="mailboxId" value="${mailboxId }"/>
			<input type="hidden" id="mailboxAddress" value="${mailboxAddress }"/>
			<input type="hidden" id="mailTitle"  value="${mailTitle }"/>
			<input type="hidden" id="senderUserIdNav"  value="${senderUserId }"/>
			<input type="hidden" id="senderMail"  value="${senderMail }"/>
			<input type="hidden" id="receiversReadFlag" value="${receiversReadFlag}"/>
			<input type="hidden" id="searchReceiveTimeBegin"  value="${searchReceiveTimeBegin }"/>
			<input type="hidden" id="searchReceiveTimeEnd"  value="${searchReceiveTimeEnd }"/>
			<!-- 跳转回来时带回的参数 end -->
			<button class="btn" type="button" id="btnSetRead">标记已读</button>
			<!--批量删除-->
			<button class="btn" type="button" id="btnDelete">批量删除</button>
		</section>
		<!--end 邮件功能-->

		<div class="table-wrap tab-content">
			<!--h5>今天（222）</h5-->
			<!--start 邮箱列表1-->
			<table class="email-table m-b-md table-move first-td-tc list-table over-hide-wrap" id="mailTable">
				<thead>
					<tr>
						<th class="w46"><input type="checkbox"></th>
						<th style="width:50px"><b>&nbsp;</b><i
							class="fa m-r-xs green-dark"></i></th>
						<th class="w170">发件人</th>
						<th style="width:30px"></th>
						<th style="width:35px"></th>
						<th>主题</th>
						<th class="w60">附件</th>
						<th class="w66">收件人<i class="fa"></i></th>
						<th class="w170">时间</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
			<!--end 邮箱列表1-->
		</div>
	</section>
</section>
</section>
 <!--邮件收件人start-->
    <div class="modal fade panel" id="email-list" aria-hidden="false">
        <div class="modal-dialog w530">
            <div class="modal-content email-unfold">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">收件人</h4>
                </div>
                <div class="modal-body email-b-c" id="consignee">
                </div>
                <div class="modal-footer email-l-h" id="consigneeCount">
                                               共<span id="countNum">0</span>人<span class="m-l-20"><a href="#" class="email-f-c" >显示全部</a></span> 
                 <span class="m-l-20"><button class="btn" type="button" data-dismiss="modal">关&nbsp;闭</button></span>
                </div>
            </div>
        </div>
    </div>
    <!--邮件收件人end-->

<!--start 高级搜索 弹出层-->
<div class="modal fade panel" id="advanced-search" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">高级搜索</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
				   <form class="cmxform form-table" id="adSearchForm">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td class="w100">主题</td>
								<td><input  type="text" id="complex-search-title" /></td>
							</tr>
							<!--tr>
								<td>正文</td>
								<td><input type="text" id="complex-search-context" /></td>
							</tr-->
							<tr>
								<td>发件人</td>
								<td>
									<span class="internalText hide"> <input	class="internalText hide" type="hidden" id="senderUserId">
									<div id="controlTreeSender"></div>
									</span>
									<span class="externalText hide"> <input type="text" id="complex-search-sender" />
									</span>
								</td>	
								</tr>
							<!--tr>
								<td>收件人</td>
								<td><input type="text" /></td>
							</tr-->
							<tr>
								<td>时间</td>
								<td>
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" id="searchReceiveTimeBeginDiv" name="searchReceiveTimeBegin" value=""
											data-date-format="yyyy-MM-dd" data-ref-obj="#searchReceiveTimeEndDiv lt">
										<div class="input-group-btn w30">-</div>
										<input class="datepicker-input" type="text" id="searchReceiveTimeEndDiv" name="searchReceiveTimeEnd" value=""
											data-date-format="yyyy-MM-dd"  data-ref-obj="#searchReceiveTimeBeginDiv gt">
									</div>

								</td>
							</tr>
						</tbody>
					</table>
					</form>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="btnSearch">搜&nbsp;索</button>
				<button class="btn" type="button" id="btnSearchReset">重&nbsp;置</button>
				<button class="btn" type="button" data-dismiss="modal">关&nbsp;闭</button>
			</div>
		</div>
	</div>
</div>
<!--end 高级搜索 弹出层-->
<script  src="${sysPath}/js/com/ic/mail/mailUnRead.js"
	type="text/javascript"></script>
<script  src="${sysPath}/js/com/ic/mail/mailInteract.validate.js"
	type="text/javascript"></script>
	<script src="${sysPath}/js/com/ic/mail/mailUnReadDetail.js"
	type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
