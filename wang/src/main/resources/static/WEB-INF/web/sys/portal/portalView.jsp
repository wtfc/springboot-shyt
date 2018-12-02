<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<script >
if (!window.console || !console.firebug){
    var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];

    window.console = {};
    for (var i = 0; i < names.length; ++i)
        window.console[names[i]] = function() {};
}

window.onerror = function(msg,url,l)
{
	if(isIe8Browser){
		return true;
	}
};
function queryDoc(flag){
	 var action = "";
	 var menubarurl = "";
	 if(flag == 'send'){//发文查询
		 var title = $("#sendTitle").val();
		 var noValue =$("#sendNoValue").val();
		 if(title.length > 100 || noValue.length > 100){
			 msgBox.tip({
					type:"fail",
					content: "输入字符不能超出100个"
			 });
			 return;
		 }
		 //特殊字符校验
		 if(!$.hasSpecialChar(title,"~`!#$%^&*()=+{}[]|\\\":;'?/><") && !$.hasSpecialChar(noValue,"~`!#$%^&*()=+{}[]|\\\":;'?/><")){
			action = "/doc/search/manage.action?type=2&fromPortal=yes&title="+title+"&noValue="+noValue;
			menubarurl = "/doc/search/manage.action?type=2";//刷新左侧二级菜单
		 }else{
			 msgBox.tip({
					type:"fail",
					content: "含有特殊字符，请重新输入"
			 });
			 return;
		 }
	 }else{//收文查询
		 var title = $("#receiveTitle").val();
		 var seqValue = $("#seqValue").val();
		 var sendDept = $("#sendDept").val();
		 if(title.length > 100 || seqValue.length > 100 || sendDept.length > 100){
			 msgBox.tip({
					type:"fail",
					content: "输入字符不能超出100个"
			 });
			 return;
		 }
		//特殊字符校验
		 if(!$.hasSpecialChar(title,"~`!#$%^&*()=+{}[]|\\\":;'?/><") 
				 && !$.hasSpecialChar(seqValue,"~`!#$%^&*()=+{}[]|\\\":;'?/><") 
				 	&& !$.hasSpecialChar(sendDept,"~`!#$%^&*()=+{}[]|\\\":;'?/><")){
			 action = "/doc/search/manage.action?type=1&fromPortal=yes&title="+title+"&seqValue="+seqValue+"&sendDept="+sendDept;
			 menubarurl = "/doc/search/manage.action?type=1";//刷新左侧二级菜单
		 }else{
			 msgBox.tip({
					type:"fail",
					content: "含有特殊字符，请重新输入"
			 });
			 return;
		 }
	 }
	 window.parent.loadmenubarurl.value = menubarurl;
    loadrightmenu(action,"",window.parent.loadmenubarurl.value);
}
</script>

<style>
.ui-portlet-column {
	float: left;
	padding-bottom: 1px;
}

.ui-portlet-header {
	padding: .4em 0.7em
}

.ui-portlet-header .ui-icon {
	float: right;
}

.ui-portlet-content {
	overflow-y:auto;
}

.ui-portlet-header .ui-portlet-header-icon {
	float: left;
	margin: 0 0.2em 0 -0.5em;
}

.ui-sortable-placeholder {
	border: 1px dotted black;
	visibility: visible !important;
	height: 50px !important;
}

.ui-sortable-placeholder * {
	visibility: hidden;
}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="panel panel-index-wrap" id="shortcutShow">
	<ul id="controlshortcut">
    </ul>
</header>
<DIV id="loadLayout">
   <div id="portalsLayout1" class="ui-portlet1-1 clearfix"></div>
   <div id="portalsLayout2" class="ui-portlet2-1 clearfix"></div>
   <div id="portalsLayout3" class="clearfix"></div>
</DIV>

<input type="hidden" id="portalId" name="portalId" value="${portalId }"/>
<input type="hidden" id="portalType" name="portalType" value="${portalType }"/>
</section>
<c:forEach items="${portletLists }" var="portalvo">
<c:set var="portalid" value=",${portalvo.id},"/>
<c:choose>
<c:when test="${fn:indexOf(rolePortalStr,portalid) != -1}">
	<c:if test="${portalvo.viewType == 'shortcut' }">
		<div id="fun_${portalvo.id }" style="display:none">
			
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'onetable'}">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel" >
		<h2 class=m_title><span>${portalvo.letTitle}</span><em id="href_${portalvo.id }" style="cursor: pointer;" onclick="portalview.loadMore('${portalvo.id }')">更多</em></h2>
		<div class="table-wrap input-default super-slide" id="fun_${portalvo.id }">
	
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'moretable'}">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel" >
		<h2 class=m_title><span>${portalvo.letTitle}</span></h2>
		<div class="table-wrap input-default" id="fun_${portalvo.id }">
	
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'textareaEdit' }">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel">
		<h2 class=m_title><span>${portalvo.letTitle}</span></h2>
		<div class="index-inform panel h320">
		<div class="table-wrap">
            <p class="index-p-text" style="text-indent: 0" id="letTextarea_${portalvo.id }">${portalvo.letTextarea }</p>
        </div>
     	</div>
		</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'freeJsp' }">
		<div id="div_${portalvo.id }" style="display:none">
			<div class="index-inform panel">
				<h2 class=m_title>
					<span>${portalvo.letTitle}</span>
				</h2>
				<div class="index-inform panel h320">
					<div class="table-wrap">
						<p class="index-p-text" style="text-indent: 0" id="letFreeJsp_${portalvo.id }">
							<section class="tabs-wrap m-t-md">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#linkman-1" data-toggle="tab"
										data-id="internalContacts">发文查询</a></li>
									<li><a href="#linkman-2" data-toggle="tab"
										data-id="externalContacts">收文查询</a></li>
								</ul>
							</section>
							<!--end 公文 选项卡title-->
							<input id="morelink_${portletId }" type="hidden"
								value="${funUrlmore }" />
							<!-- 更多链接 -->
							<input id="morelink_${portletId }_${functionId}" type="hidden"
								value="${funUrlmore }" />
							<!-- 更多链接  -->
							<!--start 公文 选项卡content-->
							<section class="tab-content">
								<section class="panel tab-content search-shrinkage">
									<br>
									<div class="tab-pane active fade in" id="linkman-1">
										<!-- 查询条件start -->
										<form class="table-wrap form-table" id="sendForm">
											<table class="table table-td-striped">
												<tbody>
													<tr>
														<td style="width: 25%;">文件标题</td>
														<td><input type="text" id="sendTitle"
															name="sendTitle"></td>
													</tr>
													<tr>
														<td>编号</td>
														<td><input type="text" id="sendNoValue"
															name="sendNoValue"></td>
													</tr>
												</tbody>
											</table>
											<br>
											<section class="form-btn m-b-lg">
												<button class="btn dark query-jump" type="button"
													onclick="queryDoc('send')">查 询</button>
											</section>
										</form>
										<!-- 查询条件end -->
									</div>
									<div class="tab-pane fade in" id="linkman-2">
										<!-- 外部联系人查询条件start -->
										<form class="table-wrap form-table" id="receiveForm">
											<table class="table table-td-striped">
												<tbody>
													<tr>
														<td style="width: 25%;">来文标题</td>
														<td><input type="text" id="receiveTitle"
															name="receiveTitle"></td>
													</tr>
													<tr>
														<td>来文机关</td>
														<td><input type="text" id="sendDept" name="sendDept">
														</td>
													</tr>
													<tr>
														<td>收文编号</td>
														<td><input type="text" id="seqValue" name="seqValue">
														</td>
													</tr>
												</tbody>
											</table>
											<section class="form-btn m-b-lg">
												<button class="btn dark query-jump" type="button"
													onclick="queryDoc('receive')">查 询</button>
											</section>
										</form>
										<!-- 外部联系人查询条件end -->
									</div>
								</section>
							</section>
						</p>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'printEdit' }">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel">
		<h2 class=m_title><span>${portalvo.letTitle}</span></h2>
		<div class="index-inform panel h320" style="overflow: hidden;">
		<div align="center" style="margin:0 10px 15px;" class="dbzx"><c:if test="${portalvo.letFile != null && portalvo.letFile != '' }"><img src="${sysPath}/${portalvo.letFile }/userPhoto.png"></c:if><c:if test="${portalvo.letFile == null || portalvo.letFile == '' }"><img src="${sysPath}/images/demoimg/iphoto.jpg"></c:if></div>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'friendlyLink' }">
		<div id="div_${portalvo.id }" style="display:none">
			<div class="index-inform panel">
			<h2 class=m_title><span>${portalvo.letTitle}</span></h2>
			<div class="table-wrap input-default" id="fun_${portalvo.id }">
				
			</div>
			</div>
		</div>
	</c:if>
</c:when>
<c:otherwise>
	<c:if test="${portalvo.viewType == 'shortcut' }">
		<div id="div_${portalvo.id }" style="display:none">
			
		</div>
	</c:if>
	<c:if test="${portalvo.viewType != 'shortcut' }">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel" >
		<h2 class=m_title><span>${portalvo.letTitle}</span></h2> 
		<div class="table-wrap input-default">
		<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
		<thead>
			<tr><TD>无访问权限</TD></tr>
		</thead>
		</table>
		</div>
		</div>
		</div>
	</c:if>
</c:otherwise>
</c:choose>
</c:forEach>


<DIV id="loadLayout1" style="display: none">
    <div id="portalsLayout1" class="clearfix"></div>
    <div id="portalsLayout2" class="clearfix"></div>
    <div id="portalsLayout3" class="clearfix"></div>
</DIV>
<DIV id="loadLayout2" style="display: none">
	<div id="portalsLayout1" class="ui-portlet1-1 clearfix"></div>
    <div id="portalsLayout2" class="clearfix"></div>
    <div id="portalsLayout3" class="clearfix"></div>
</DIV>
<DIV id="loadLayout3" style="display: none">
    <div id="portalsLayout1" class="ui-portlet1-1 clearfix"></div>
    <div id="portalsLayout2" class="ui-portlet2-1 clearfix"></div>
    <div id="portalsLayout3" class="clearfix"></div>
</DIV>
<DIV id="loadLayout4" style="display: none">
    <div id="portalsLayout1" class="ui-portlet1-1 clearfix"></div><!--1.5:1.5:0  -->
    <div id="portalsLayout2" class="ui-portlet1-1 clearfix"></div><!--1.5:1.5:0  -->
    <div id="portalsLayout3" class="ui-portlet1-1 clearfix"></div><!--1.5:1.5:0  -->
</DIV>
<DIV id="loadLayout5" style="display: none">
    <div id="portalsLayout1" class="ui-portlet1-2-1 clearfix"></div>
    <div id="portalsLayout2" class="ui-portlet1-2-1 clearfix"></div>
    <div id="portalsLayout3" class="ui-portlet1-2-1 clearfix"></div>
</DIV>
<DIV id="loadLayout6" style="display: none">
    <div id="portalsLayout1" class="ui-portlet2-1 clearfix"></div><!--2:1:0  -->
    <div id="portalsLayout2" class="ui-portlet2-1 clearfix"></div><!--2:1:0  -->
    <div id="portalsLayout3" class="ui-portlet2-1 clearfix"></div><!--2:1:0  -->
</DIV> 
<DIV id="loadLayout7" style="display: none">
    <div id="portalsLayout1" class="clearfix"></div><!--1:1:1  -->
    <div id="portalsLayout2" class="ui-portlet-1 clearfix"></div><!--1:0:0  -->
    <div id="portalsLayout3" class="ui-portlet-1 clearfix"></div><!--1:0:0  -->
</DIV>  
<DIV id="loadLayout8" style="display: none">
    <div id="portalsLayout1" class="ui-portlet1-2 clearfix"></div><!--1:2:0  -->
    <div id="portalsLayout2" class="ui-portlet1-2 clearfix"></div><!--1:2:0  -->
    <div id="portalsLayout3" class="ui-portlet1-2 clearfix"></div><!--1:2:0  -->
</DIV> 
<DIV id="loadLayout9" style="display: none">
    <div id="portalsLayout1" class="ui-portlet-1 clearfix"></div><!--1:0:0  -->
    <div id="portalsLayout2" class="ui-portlet-1 clearfix"></div><!--1:0:0  -->
    <div id="portalsLayout3" class="ui-portlet-1 clearfix"></div><!--1:0:0  -->
</DIV>

<script src="${sysPath}/js/lib/jqueryui/jquery-ui.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/portal/jquery.portlet.pack.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/portal/jquery.superSlide.js"></script>
<script src="${sysPath}/js/lib/echarts/echarts-plain.js" type="text/javascript"></script>
<script src="${sysPath}/js/lib/echarts/eccommon.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/portal/portalView.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>