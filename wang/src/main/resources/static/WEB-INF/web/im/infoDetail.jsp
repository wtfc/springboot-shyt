<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%//@ include file="/WEB-INF/web/include/head.jsp"%>
<!DOCTYPE html>
<html class="jcGOA" id="content">
<head>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<meta name="renderer" content="ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>${info.infoTitile}</title>
<%//@ include file="/WEB-INF/web/include/base.jsp"%>
<link href="${sysPath}/css/JcGoa_v2.0.css" rel="stylesheet" type="text/css" />
<link href="${sysPath}/js/datepicker/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${sysPath}/css/datatable/dataTables.bootstra.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src='${sysPath}/js/lib/jquery/jquery-1.10.2.js'></script>
<script type='text/javascript' src='${sysPath}/js/lib/common/common.all.js'></script>
<script type='text/javascript' src='${sysPath}/js/lib/common/sessionValidate.js'></script>
<!-- Bootstrap -->
<!--[if lt IE 9]>
    <script src="${sysPath}/js/ie/html5shiv.js"></script>
    <script src="${sysPath}/js/ie/PIE_IE678.js"></script>
<![endif]-->
<script type="text/javascript" src='${sysPath}/js/lib/datatable/jquery.dataTables.all.js'></script>
<script type='text/javascript' src='${sysPath}/js/lib/common/jquery.plugin.js'></script>
<script type="text/javascript">setRootPath('${sysPath}');</script>
<script type="text/javascript" src='${sysPath}/js/lib/jquery-validation/jquery.validate.all.js'></script>
<script type="text/javascript" src="${sysPath}/js/jQuery_jbox_datepicker/jquery.box_datepicker.js"></script>
<script type="text/javascript" src="${sysPath}/js/app.v2.js"></script>

</head>
<body>
<div class="loading" id="dataLoad"></div>
<input type="hidden" id="detailToken" name="detailToken" value="${detailToken}">
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<!-- <header class="con-header pull-in">		
		<div class="con-heading fl" id="navigationMenu">	
			<h1>信息详细</h1>
			<div class="crumbs"></div>
		</div>	
	</header>	 -->
	<section class="panel m-t-md">
		<input type="hidden" id="infoId" value="${info.id}"/>
		<input type="hidden" id="type" value="${type}"/>
		<input type="hidden" id="menuFlag" value="${menuFlag}"/>
		<div id="infoheader">
	    	<h2 class="content-heading tc">${info.infoTitile}<small> 
	    		<c:if test="${not empty info.infoSubtitle}">—— </c:if>${info.infoSubtitle}</small></h2>
	    	<section class="tc text-blue m-b-xxl">发布人：${info.sendName}　　　　 发布部门：${info.sendDepName}　　　　作者：${info.author}　　　发布时间：${info.sendTimeFormat}</section>
	    </div>
	    <section class="b-b-d content-box m-r-85 m-l-40 m-b-xxl" id="infoconter">
	    	<iframe id="infoContent" src="" name="infoContent" frameborder="0" scrolling="no" width="100%">
	    	</iframe>
	    	<c:if test='${(type eq 1 || type eq 5)&&(menuFlag eq 0 || menuFlag eq 4)}'>
	        <section class="m-t-lg text-blue m-b">
	        	上一篇：<c:choose>
						<c:when test="${empty infoPre}">
							<a href="#" class="m-r-xl">无</a>
	   					</c:when>
	   					<c:otherwise>
	   						<a href="#" title="${infoPre.infoTitile}" onclick="infoDetail.infoDetail(${infoPre.id})" class="m-r-xl">
		   						<c:choose>  
								    <c:when test="${fn:length(infoPre.infoTitile) > 30}">  
								        <c:out value="${fn:substring(infoPre.infoTitile, 0, 20)}......" />  
								    </c:when>  
								   <c:otherwise>  
								      <c:out value="${infoPre.infoTitile}" />  
								    </c:otherwise>  
								</c:choose>  
	   						 </a> 
	   					</c:otherwise>
					  </c:choose>
	                                    下一篇：<c:choose>
						<c:when test="${empty infoNext}">
							<a href="#" class="m-r-xl">无</a>
	   					</c:when>
	   					<c:otherwise>
		   					<a href="#" title="${infoNext.infoTitile}" onclick="infoDetail.infoDetail(${infoNext.id})" class="m-r-xl">
		   						<c:choose>  
								    <c:when test="${fn:length(infoNext.infoTitile) > 30}">  
								        <c:out value="${fn:substring(infoNext.infoTitile, 0, 20)}......" />  
								    </c:when>  
								   <c:otherwise>  
								      <c:out value="${infoNext.infoTitile}" />  
								    </c:otherwise>  
								</c:choose>
							</a> 
	   					</c:otherwise>
					  </c:choose>
	        </section>
	        </c:if>
	    </section>
	    <div id="infofooter">
	    <h2 class="panel-heading clearfix m-l-20"><a name="comment"></a>附件</h2>
	    <ul class=" m-l-40 m-r-85 m-t" id="attachList">
	    </ul>
	    <c:if test='${info.allowReview eq 1 && type ne 5}'>
	    <h2 class="panel-heading clearfix m-l-20"><a name="comment"></a>评论</h2>
	    <ul class="dialog m-l-40 m-r-85 m-t" id="review">
	    </ul>
	    <section class="m-l-40 m-r-85">
		    <form id="reviewForm">
		   		<input type="hidden" id="infoId" name="infoId" value="${info.id}"/>
		   		<textarea rows="3" id="reviewContent" name="reviewContent" ></textarea>
			</form>
	    </section>
	    </c:if>
	    <section class="m-l-40 m-r-85 m-t-md m-b-md form-btn">
	   		<c:if test='${type eq 5}'><!-- 流程状态是审批中和暂存 -->
	   			<a href="javascript:window.close()"  class="btn dark" id="close">关 闭</a>
	   		</c:if>
	    	<c:if test='${info.allowReview eq 1 && type ne 5}'><!-- 允许有评论且流程状态不是审批中和暂存 -->
	   			<a href="#" class="btn dark" id="reviewSend">评 论</a>
	   			<a href="javascript:window.close()"  class="btn" id="close">关 闭</a>
	   		</c:if>
	    </section>
	    </div>
	</section>
	
	<!-- start 返回顶部-->
	<section class="sidebar">
	 	<%-- <c:if test="${info.allowPrint eq 1}">
			<a href="#" class="bg-blue"><i class="fa fa-print" data-original-title="打印" data-container="body" title="" data-placement="top" data-toggle="tooltip"></i></a>
		</c:if> --%>
		<c:if test='${info.allowReview eq 1 && type ne 5}'>
			<a href="#comment" class="bg-blue"><i class="fa fa-comment" data-original-title="评论" data-container="body" title="" data-placement="top" data-toggle="tooltip"></i></a>
		</c:if>
		<a href="#" id="backtotop" class="bg-blue"><i class="fa fa-upload" data-original-title="返回顶部" data-container="body" title="" data-placement="top" data-toggle="tooltip"></i></a>
	</section>
	<!-- end 返回顶部-->
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
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
</section>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<!-- <script src="${sysPath}/js/lib/common/selectControlNew.js" type="text/javascript"></script> -->
<script src="${sysPath}/js/com/im/infoDetail.js"></script>
<!-- <script src="${sysPath}/js/com/im/infoDetail.validate.js"></script> -->
<script type="text/javascript"> 
$(document).ready(function(){
	$(window).resize(function(e){	
		var ifm= document.getElementById("infoContent");
		var subWeb= window.frames["infoContent"].document;
		var height = getOs() == "Chrome"?subWeb.documentElement.scrollHeight+10:subWeb.body.scrollHeight+30;
		if(ifm != null && subWeb != null) {
			ifm.height = Math.max(height,getIframeHeight())+"px";//window.document.documentElement.scrollHeight;//
		}
    });
});
function getOs(){ 
	if(isIE = navigator.userAgent.indexOf("MSIE")!=-1) { 
        return "MSIE"; 
	} 
	if(isFirefox=navigator.userAgent.indexOf("Firefox")!=-1){ 
		return "Firefox"; 
	} 
	if(isChrome=navigator.userAgent.indexOf("Chrome")!=-1){ 
		return "Chrome"; 
	} 
	if(isSafari=navigator.userAgent.indexOf("Safari")!=-1) { 
		return "Safari"; 
	}  
	if(isOpera=navigator.userAgent.indexOf("Opera")!=-1){ 
		return "Opera"; 
	} 
} 

function restoreViewState(){
	if(document.readyState=="complete"){
		setIFrameHeight();
	}else if(document.readyState!="loading"){//待页面加载完成后调整高度
		setIFrameHeight();
	}else{
 		setTimeOut("restoreViewState()",100);
	}
}

function getIframeHeight(){
	var head = $("#infoheader").outerHeight(true);
	var foot = $("#infofooter").outerHeight(true);
	var wins = $("#scrollable").outerHeight(true);
	return wins - head - foot - 180;
}

function setIFrameHeight() { 
	var ifm= document.getElementById("infoContent");
	var subWeb= window.frames["infoContent"].document;
	var scrollHeight = Math.max(subWeb.documentElement.scrollHeight, subWeb.body.scrollHeight);
	if(ifm != null && subWeb != null) {
		ifm.height = Math.max(scrollHeight,getIframeHeight())+"px";//window.document.documentElement.scrollHeight;//
	}
/* 	if(browser=="MSIE"){
		if(ifm != null && subWeb != null) {
			ifm.height = scrollHeight+15+"px";
		}
	}else if(browser=="Firefox"){
		if(ifm != null && subWeb != null) {
			ifm.height = scrollHeight+15+"px";
		}
	}else if(browser=="Chrome"){
		if(ifm != null && subWeb != null) {
			ifm.height = scrollHeight+"px";//window.document.documentElement.scrollHeight;//
		}
	} */
// 	var ifm= document.getElementById("infoContent"); 
//	var subWeb = document.frames ? document.frames["infoContent"].document : ifm.contentDocument; 
//	if(ifm != null && subWeb != null) { 
//		var heightValue = subWeb.body.scrollHeight<150?150:subWeb.body.scrollHeight+10;
//		ifm.height = heightValue; 
//	}  
}

</script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>