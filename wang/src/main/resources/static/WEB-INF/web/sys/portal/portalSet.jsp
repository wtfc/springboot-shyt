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
</script>
<script >
//设置每行按钮
function oTableSetButtonsportal (source) {
	var share = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"portalset.setportal("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\">门户设置</a>";
	return share;
};
</script>

<link href="${sysPath}/css/portal/jquery-ui-1.10.2.custom.min.css" rel="stylesheet" type="text/css" />
<style>
.ui-portlet-column {
	float: left;
	padding-bottom: 20px;
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
<header class="con-header pull-in" id="navigationMenu">
	<h1></h1>
	<div class="crumbs">
	</div>
</header>
<section class="panel m-t-md clearfix">
	<h2 class="panel-heading clearfix">门户设置</h2>
	<div class="table-wrap">
		<input type="hidden" id="portalType" name="portalType" value="${portalType }">
		<table class="table table-striped tab_height" id="portalsetTable">
			<thead>
				<tr>
					<th >门户名称</th>
                    <th >门户状态</th>
                    <th >排序号</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</section>
</section>
<!--start 门户配置 弹出层-->
<div class="modal fade panel" id="check-meeting" aria-hidden="false">
    <div class="modal-dialog" style="width:80%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="portalset.reTextArea()">×</button>
                <h4 class="modal-title" id="titleportals">门户设置</h4>
            </div>
            <form class="table-wrap form-table" id="portaletLayoutForm">
				<input type="hidden" id="id" name="id">
				<input type="hidden" id="token" name="token" value="${token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="portalmenuId" name="portalmenuId" value="1">
				<input type="hidden" id="layoutSite" name="layoutSite">
				<input type="hidden" id="portalId" name="portalId">
				<input type="hidden" id="layoutType" name="layoutType">
            <div class="modal-body">
            	<section class="m-b clearfix" id="relayoutCode">
                    <span class="layout-title fl m-r-md">设置布局</span>
                    <section  class="layout-content fl layout-tabs">
                 	<!-- <a href="#" id="layoutCode5" onclick="portalset.setlayout(5)"><i class="fa fa-layout-121 m-r-md"></i></a> -->
                    <a href="#" id="layoutCode1" onclick="portalset.setlayout(1)"><i class="fa fa-layout-111 m-r-md"></i></a>
                    <a href="#" id="layoutCode7" onclick="portalset.setlayout(7)"><i class="fa fa-layout-311 m-r-md"></i></a>
                    <a href="#" id="layoutCode9" onclick="portalset.setlayout(9)"><i class="fa fa-layout-1 m-r-md"></i></a>
                    <a href="#" id="layoutCode8" onclick="portalset.setlayout(8)"><i class="fa fa-layout-13 m-r-md"></i></a>
                    <a href="#" id="layoutCode6" onclick="portalset.setlayout(6)"><i class="fa fa-layout-31 m-r-md"></i></a>
                    <a href="#" id="layoutCode2" onclick="portalset.setlayout(2)"><i class="fa fa-layout-211 m-r-md"></i></a>
                	</section>
                </section>
                <div id="portlets">
                </div>
                <header class="panel panel-index-wrap" >
                    <ul id="controlshortcut">
                       
                    </ul>
                </header>
                <DIV id="loadLayout">
                	<div id="portalsLayout1" class="ui-portlet1-1 clearfix"></div>
                	<div id="portalsLayout2" class="ui-portlet2-1 clearfix"></div>
                	<div id="portalsLayout3" class="clearfix"></div>
                </DIV>
            </div>
            </form>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" id="portalbtn">保 存</button>
                <button class="btn" type="button" onclick="portalset.reTextArea()">关 闭</button>
                <button class="btn" id="perview" type="button" onclick="portalset.viewlayout()">开启预览</button>
            </div>
        </div>
    </div>
</div>

<c:forEach items="${portletLists }" var="portalvo">
<c:set var="portalid" value=",${portalvo.id},"/>
<c:choose>
<c:when test="${fn:indexOf(rolePortalStr,portalid) != -1}">
	<c:if test="${portalvo.viewType == 'shortcut' }">
		<div id="fun_${portalvo.id }" style="display:none">
			
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'onetable' || portalvo.viewType == 'moretable'}">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel pos-rlt">
		<h2 class=m_title><span>${portalvo.letTitle}</span></h2>
		<div class="index-disabled"></div>
		<div class="table-wrap input-default" id="fun_${portalvo.id }">
	
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'textareaEdit' }">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel">
		<h2 class=m_title><span>${portalvo.letTitle}</span></h2>
		<div class="table-wrap input-default">
			<input type="hidden" id="id_${portalvo.id }" name="id" value="${portalvo.id }">
			<input type="hidden" id="modifyDate_${portalvo.id }" name="modifyDate" value="${portalvo.modifyDate }">
			<textarea name="letTextarea" rows="4" id="letTextarea_${portalvo.id }" onpause="portalset.checkLen(this,${portalvo.id })" onkeyup="portalset.checkLen(this,${portalvo.id })">
				${portalvo.letTextarea }
			</textarea>
			<div>您还可以输入<span id="count_${portalvo.id }"></span>个文字。 </div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" onclick="portalset.saveTextarea('${portalvo.id }','${portalvo.portalId }')">保 存</button>
			</div>
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'freeJsp' }">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel">
		<h2 class=m_title><span>${portalvo.letTitle}</span></h2>
		<div class="index-disabled"></div>
		<div class="table-wrap">
			<section class="tabs-wrap m-t-md">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#linkman-1" data-toggle="tab"
						data-id="internalContacts">发文查询</a></li>
					<li><a href="#linkman-2" data-toggle="tab"
						data-id="externalContacts">收文查询</a></li>
				</ul>
			</section>
			<!--end 公文 选项卡title-->
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
										<td style="width: 28%;">文件标题</td>
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
										<td style="width: 28%;">来文标题</td>
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
		</div>
		</div>
		</div>
	</c:if>
	<c:if test="${portalvo.viewType == 'printEdit' }">
		<div id="div_${portalvo.id }" style="display:none">
		<div class="index-inform panel">
		<h2 class=m_title><span>${portalvo.letTitle}</span> 
			<button class="btn dark index-btn" type="button" id="uploadPhoto" onclick="portalset.getPortletPhoto('${portalvo.id }')">上传图片
			</button>
		</h2>
		<div class="index-inform panel h320" style="overflow: hidden;">
			<input type="hidden" id="id_${portalvo.id }" name="id" value="${portalvo.id }">
			<input type="hidden" id="modifyDate_${portalvo.id }" name="modifyDate" value="${portalvo.modifyDate }">
			<div align="center" style="margin:0 10px 15px;" class="dbzx">
			<c:if test="${portalvo.letFile != null && portalvo.letFile != '' }"><img src="${sysPath}/${portalvo.letFile }/userPhoto.png" id="letFilePhoto_${portalvo.id }" ></c:if><c:if test="${portalvo.letFile == null || portalvo.letFile == '' }"><img src="${sysPath}/images/demoimg/iphoto.jpg" id="letFilePhoto_${portalvo.id }" ></c:if>
            <input type="hidden" id="letFile_${portalvo.id }" name="letFile" value="${portalvo.letFile }"/>
			</div>
			<%-- <div class="modal-footer form-btn">
				
				<button class="btn dark" type="button" onclick="portalset.saveTextarea('${portalvo.id }')">保 存</button>
			</div> --%>
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
    <div id="portalsLayout1" class="ui-portlet1-1 clearfix"></div><!--1.5:1.5:0  -->
    <div id="portalsLayout2" class="ui-portlet2-1 clearfix"></div><!--2:1:0  -->
    <div id="portalsLayout3" class="clearfix"></div><!--1:1:1  -->
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
<div class="modal fade panel" id="myModal2" aria-hidden="false">
      <div class="modal-dialog">
           <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body"> 
                    <!-- upload_type 1为单传  0为多传-->
                    <input type="hidden" id="upload_type" value="1"> 
                    <!-- 临时存储id -->
                    <input type="hidden" id="tempportletid" > 
                    <input type="hidden" id="tempportletfile" >
                    <input type="hidden" id="tempportletportalvoid" >
                    <%@ include file="/WEB-INF/web/sys/portal/portalAttach.jsp"%>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button"  data-dismiss="modal">关 闭</button>
                </div>
           </div>
      </div>
</div>
<!--end 门户配置 弹出层--> 
<script src="${sysPath}/js/com/sys/portal/jquery.superSlide.js"></script>
<script src="${sysPath}/js/lib/jqueryui/jquery-ui.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/portal/jquery.portlet.pack.js" type="text/javascript"></script>
<script src="${sysPath}/js/lib/echarts/echarts-plain.js" type="text/javascript"></script>
<script src="${sysPath}/js/lib/echarts/eccommon.js" type="text/javascript"></script>
<script>
jQuery.getScript("${sysPath}/js/com/sys/portal/portalSet.js");
</script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
