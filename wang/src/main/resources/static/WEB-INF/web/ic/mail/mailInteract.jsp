<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in" id="navigationMenu">
	<h1>
		<c:choose>
			<c:when test="${mfid eq 1}">收件箱</c:when>
			<c:when test="${mfid eq 2}">草稿箱</c:when>
			<c:when test="${mfid eq 3}">已发送</c:when>
			<c:when test="${mfid eq 4}">废件箱</c:when>
		</c:choose>
	</h1>
	<div class="crumbs">
		<c:choose>
			<c:when test="${mfid eq 1}">收件箱</c:when>
			<c:when test="${mfid eq 2}">草稿箱</c:when>
			<c:when test="${mfid eq 3}">已发送</c:when>
			<c:when test="${mfid eq 4}">废件箱</c:when>
		</c:choose>
	</div>
</header>
<input type="hidden" id="token" name="token" value="${token}">
<section class="panel email m-t-md dis-table">
	<section class="email-right">
		<!--start 邮件功能-->
		<section class="form-btn m-b-md clearfix" style="display:none">
			<div class=" fl m-r-sm m-t-sm">请选择邮箱</div>
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
			<!-- 隐藏域 文件夹ID做为查询条件-->
			<input type="hidden" id="url"  value="${url}"/>
			<input type="hidden" id="mailFolderId" name="mailFolderId"	value="${mfid}" />
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
			<input type="hidden" id="mailEasyTitle"  value="${mailEasyTitle }"/>
			<input type="hidden" id="senderUserIdNav"  value="${senderUserId }"/>
			<input type="hidden" id="senderMail"  value="${senderMail }"/>
			<input type="hidden" id="receiversReadFlag" value="${receiversReadFlag}"/>
			<input type="hidden" id="searchReceiveTimeBegin"  value="${searchReceiveTimeBegin }"/>
			<input type="hidden" id="searchReceiveTimeEnd"  value="${searchReceiveTimeEnd }"/>
			<!-- 跳转回来时带回的参数 end -->
			
			<!-- 按钮区域开始 按点击Menu时传参mfid来决定显示哪个按钮-->
			<!-- 收件箱、已发送、废件箱有已读、未读、转发-->
			<c:if test="${mfid eq 1  or mfid eq 3 or empty mfid or mfid gt 4}">
				<button class="btn" type="button" id="btnSetRead">标记已读</button>
				<button class="btn" type="button" id="btnSetUnread">标记未读</button>
			</c:if>
			<!-- 收件箱、已发送有已读、未读、批量删除-->
			<c:if test="${mfid eq 1 or mfid eq 3 or empty mfid or mfid gt 4}">
				<button class="btn" type="button" id="btnDelete">批量删除</button>
			</c:if>
			<!--草稿箱有删除草稿-->
			<c:if test="${mfid eq 2}">
				<button class="btn" type="button" id="btnDeleteDraft">彻底删除</button>
			</c:if>
			<!--废件箱有彻底删除-->
			<c:if test="${mfid eq 4}">
				<button class="btn" type="button" id="btnShiftDelete">彻底删除</button>
			</c:if>
			<!--所有文件夹都有“移动到”-->
			<c:if test="${mfid ne 2}">
				<c:if test="${moveShow ne 1}">
					<div class="dropdown inline">
						<button class="btn dropdown-toggle" data-toggle="dropdown"
							type="button">
							移动至<i class="fa fa-chevron-down"></i>
						</button>
						<ul class="dropdown-menu animated fadeInRight" id="moveTo">
							<c:choose>
									<c:when test="${mfid ne 1}">
										<li class="inBoxR"><a href="javascript:mail.moveTo(1);">收件箱</a></li>
									</c:when>
									<c:otherwise>
										<li class="inBoxR hide"><a href="javascript:mail.moveTo(1);" >收件箱</a></li>
									</c:otherwise>
							</c:choose>
							<%-- <c:if test="${mfid ne 2}">
								<li><a href="javascript:mail.moveTo(2);">草稿箱</a></li>
							</c:if> --%>
							<c:if test="${mfid ne 3}">
								<li><a href="javascript:mail.moveTo(3);">已发送</a></li>
							</c:if>
<!-- 							<c:if test="${mfid ne 4}"> -->
<!-- 								<li><a href="javascript:mail.moveTo(4);">废件箱</a></li> -->
<!-- 							</c:if> -->
							<c:if test="${!empty mailFolderList}">
								<c:forEach items="${mailFolderList }" var="folder">
									<li class="otherFolder" value="${folder.id }"><a href="javascript:mail.moveTo(${folder.id });">${folder.folderName }</a></li>
								</c:forEach>
							</c:if>
							<!--li><a href="javascript:mail.setStarByIds();">星标邮件</a></li-->
						</ul>
					</div>
				</c:if>
			</c:if>
			<!--收件箱有其他文件夹-->
			<c:if test="${mfid eq 1  or mfid gt 4}">
				<div class="dropdown inline">
					<button class="btn dropdown-toggle" data-toggle="dropdown"
						type="button">
						<span id="selected-fid">&nbsp;其他文件夹</span><i class="fa fa-chevron-down"></i>
					</button>
					<ul class="dropdown-menu animated fadeInRight" id="mailFolder">
					<c:if test="${empty mailFolderList}">
						<li id="noFolder"><em class="m-l">还没有其他文件夹</em></li>
					</c:if>
					    <li><a href="javascript:mail.fid(1,'其他文件夹')">返回收件箱</a></li>
						<c:forEach items="${mailFolderList }" var="folder">
							<li><a href="javascript:mail.fid(${folder.id },'${folder.folderName }');">${folder.folderName }</a></li>
						</c:forEach>
					</ul>
				</div>
				<a class="btn new-folder" href="#" role="button"
					data-toggle="modal" id="show-new-folder">新建文件夹</a>
				<a class="btn w0" href="#cog-folder" role="button" data-toggle="modal"><i
					class="fa fa-cog" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="设置"></i></a>
			</c:if>
			<!-- 按钮区域结束-->
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
						<c:if test="${mfid eq 3}">
							<th class="w170">收件人<i class="fa"></i></th>
						</c:if>
						<c:if test="${mfid ne 3}">
							<th class="w170">发件人</th>
						</c:if>
						<th style="width:30px"></th>
						<th style="width:30px"></th>
						<th style="width:35px"></th>
						<th>主题</th>
						<th class="w60">附件</th>
						<c:if test="${mfid eq 3}">
							<th style="width:1px"></th>
						</c:if>
						<c:if test="${mfid ne 3}">
							<th class="w66">收件人<i class="fa"></i></th>
						</c:if>
						<th class="w170">时间</th>
						<c:choose>
						<c:when test="${mfid eq 3}">
						<th class="w66">操作</th>
						</c:when>
						<c:otherwise>
						<th class="w46"></th>
						</c:otherwise>
						</c:choose>
						
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
								<td><input type="text" id="complex-search-title" /></td>
							</tr>
							<!--tr>
								<td>正文</td>
								<td><input type="text" id="complex-search-context" /></td>
							</tr-->
							<tr>
								<td id="sendUserTd">发件人</td>
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
							<!--tr>
								<td>星标</td>
								<td><select id="complex-search-starMail">
										<option></option>
										<option value="1">是</option>
										<option value="0">否</option>
								</select></td>
							</tr-->
							<c:choose>
										<c:when test="${mfid ne 3}">
										<c:if test="${empty readFlag}">
											<tr>
												<td>状态</td>
												<td><select id="complex-search-mailStatus">
														<option value="">-全部-</option>
														<option value="1">已读</option>
														<option value="0">未读</option>
												</select></td>
											</tr>
										</c:if>
									</c:when>
							</c:choose>
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
<!--start 其他文件夹 弹出层-->
<div class="modal fade panel" id="cog-folder" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">其他文件夹</h4>
			</div>
			<div class="modal-body clearfix">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>文件夹名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="mailFolderTable">
					<c:forEach items="${mailFolderList }" var="folder">
						<tr>
							<td>${folder.folderName }</td>
							<td class="w100"><a class="a-icon i-edit m-r-xs"
								href="#" role="button" data-toggle="modal" onclick="mail.get(${folder.id })"> 
								<i class="fa fa-edit2" data-toggle="tooltip" data-placement="top"
									title="" data-container="body" data-original-title="编辑"></i>
							</a> <a class="a-icon i-remove" href="javascript:mail.deleteFolder('${folder.id }');"> <i class="fa fa-remove"
									data-toggle="tooltip" data-placement="top" title=""
									data-container="body" data-original-title="删除"></i>
							</a></td>
						</tr>
						</c:forEach>
						
					</tbody>
				</table>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" href="#" role="button" id="show_folder"
					data-toggle="modal">新建文件夹</a>
				<button class="btn" type="button" data-dismiss="modal">关&nbsp;闭</button>
			</div>
		</div>
	</div>
</div>
<!--end 其他文件夹 弹出层-->
<!--start 新建文件夹 弹出层-->
<div class="modal fade panel" id="new-folder" aria-hidden="false">
	<div class="modal-dialog">
		<form class="cmxform" id="folderform">
			<div class="modal-content">
				<input type="hidden" id="id" name="id">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="newFolders">新建文件夹</h4>
				</div>
				<div class="modal-body">
					<div class="input-group input-default">
						<label class="input-group-btn p-r"><span class="required">*</span>文件夹名称</label>
						<input type="text" id="folderName" name="folderName" />
					</div>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="saveForder">保&nbsp;存</button>
					<button class="btn" type="button" data-dismiss="modal" id="cancel">关&nbsp;闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<!--end 新建文件夹 弹出层-->
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/mail/mailInteract.js"
	type="text/javascript"></script>
<script async  src="${sysPath}/js/com/ic/mail/mailInteract.validate.js"
	type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
