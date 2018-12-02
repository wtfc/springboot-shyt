<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="jcGOA">
<head>
<title>信息资源管理平台</title>
<meta charset="utf-8">
<link rel="shortcut icon" href="favicon.ico" />
<meta name="renderer" content="ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<link href="${sysPath}/css/JcGoa_v2.1.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src='${sysPath}/js/lib/jquery/jquery-1.10.2.js'></script>
<script type="text/javascript" src='${sysPath}/js/lib/jquery/jquery.cookie.js'></script>
<script type='text/javascript' src='${sysPath}/js/lib/common/common.all.js'></script>
<script type="text/javascript">setRootPath('${sysPath}');</script>
<script type="text/javascript">setTheme('${theme}');</script>
<script type="text/javascript" src='${sysPath}/js/lib/jquery-validation/jquery.validate.all.js'></script>
<script type="text/javascript" src="${sysPath}/js/jQuery_jbox_datepicker/jquery.box_datepicker.js"></script>
<script type='text/javascript' src='${sysPath}/js/lib/common/sessionValidate.js'></script>

<!-- Bootstrap -->
<!--[if lt IE 9]>
    <script src="${sysPath}/js/ie/html5_pie678.js"></script>
<![endif]-->
<script type="text/javascript">setRootPath('${sysPath}');</script>
<script type="text/javascript" src="${sysPath}/js/app.v2.js"></script>
<script src='${sysPath}/js/lib/common/showOnlinePerson.js' type='text/javascript'></script>
<script>
	window.onerror = function() {
		$("#dataLoad").hide();
	};
	function iFrameHeight() { 
		var ifm= document.getElementById("iframepage"); 
		var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument; 
		if(ifm != null && subWeb != null) { 
			ifm.height = subWeb.body.scrollHeight; 
		} 
	} 
</script>
</head>
<body ondragstart="return false" id="desktopBody">
     <section class="jcGOA-wrap">
        <header class="header navbar-fixed-top" id="desktop_header">
            <div class="navbar-topbar clearfix">
            	<!-- 桌面精灵使用勿动 开始 -->
					<!-- 页面读取完成标志-->
            		<input id="loadOver" type="hidden" value="0"/>
					<!-- 未读邮件-->
				 <a id="15e880df-f229-47a9-9a44-8f9eaa969f6e" name="15e880df-f229-47a9-9a44-8f9eaa969f6e" hidden="hidden" href="javascript:void(0)" onClick="loadrightmenu('/ic/mail/manageUnread.action','','/ic/mail/manageUnread.action');"></a>
					<!-- 待办发文-->
				 <a id="31e029e0-4599-4696-bd9c-f3290a8cf6eb" name="31e029e0-4599-4696-bd9c-f3290a8cf6eb" hidden="hidden"  href="javascript:void(0)" onClick="menuClick('2084','/workFlow/form/jumpToList.action?url=/doc/send/sendWaitTransact',2,'待办发文')"></a>
					<!-- 待办收文-->
				 <a id="384a1ce1-132f-4856-b3ff-885f1c3ee7d9" name="384a1ce1-132f-4856-b3ff-885f1c3ee7d9" hidden="hidden"  href="javascript:void(0)" onClick="menuClick('2089','/workFlow/form/jumpToList.action?url=/doc/receive/receiveToDoList',2,'待办收文')"></a>
					<!-- 待办流程-->
				 <a id="3d18eabf-3ee3-4cce-a932-57319c9e8d26" name="3d18eabf-3ee3-4cce-a932-57319c9e8d26" hidden="hidden"  href="javascript:void(0)" onClick="loadrightmenu('/workFlow/processInstance/getTodoList.action','','/workFlow/processInstance/getTodoList.action');"></a>
			     <!-- 桌面精灵使用勿动 结束-->
<!--                 <a href="${sysPath}/help/政务协同办公平台V2.0操作手册.chm" class="m-t-xs fl icon-help" >帮助</a> -->
<!--                 <a onClick="window.open('${sysPath}/help/help.html','_blank','width=1000px,height=600px');return false;" href="#" class="m-t-xs fl icon-help" >帮助</a> -->
                <div class="m-t-xs fr dropdown" id="dropdownMenu">
                   <!-- onClick="skin()" -->
                   <!--  <a href="javascript:void(0)" class="icon-voice on" id="voiceSwitch" onclick="turnVoice(this);">声音</a> -->
                     <a href="javascript:void(0)" class="icon-skin" data-toggle="dropdown">换肤</a>
                    <a href="javascript:void(0)" onClick="exit()" class="icon-exit">退出</a>
                    <ul class="dropdown-menu animated fadeInRight" style="min-width: 125px;">
                        <li value="1"><a href="#">绿色主题</a></li>
                    </ul>
                </div>
            </div>
            <nav class="header-nav fl">
                <ul class="clearfix">                
                    <li class="padd">
                        <h1 style="min-width:207px;">
                           <img width="160" height="70" src="${sysPath}/images/demoimg/logo.png"/> 
                        </h1>
                    </li>
                    <c:forEach items="${menuList}" var="menu" >
                    <li onclick="reloadTopMenuClass(this)" name="menu${menu.id }">
                    	<c:choose>
                    		<c:when test="${menu.actionName != null && menu.actionName != ''}">
                    			<a onClick="historyUrl.length=0;loadleftMenu1('${menu.id}','${menu.actionName}','${menu.name}')" target="mainFrame"><%-- loadleftMenu(${menu.id}) --%>
                            		<i class="${menu.icon}"></i>${menu.name}
                       	 		</a>
                    		</c:when>
                    		<c:otherwise>
                    			<a onClick="loadleftMenu1('${menu.id}','','${menu.name}')"><%-- loadleftMenu(${menu.id}) --%>
                            		<i class="${menu.icon}"></i>${menu.name}
                       	 		</a>
                    		</c:otherwise>
                    	</c:choose>
                       
                    </li>
                    </c:forEach>
                    <li id="topnav-other_modules">
                        <a href="#">
                            <i class="topnav-other_modules"></i>其他模块
                        </a>
                    </li>
                </ul>
            </nav>
            <section class="userInfo m-r-sm fr clearfix">
                <div class="user-avatar fl tc">
                    <a href="#" onClick="loadrightmenu('/sys/user/userInfo.action','','/sys/user/userInfo.action')">
                        <img src="<shiro:principal property="photo"/>" height="74"/>
                    </a>
                </div>
                <div class="user-info fr">
					<div class="clearfix name">
						<p class="fl inter">
						<a href="#" style="cursor:pointer;"title="<shiro:principal property="displayName"/><shiro:principal property="dutyIdValue"/>">
							
								<i class="fa fa-user"></i><span><shiro:principal
										property="displayName" />
									<shiro:principal property="dutyIdValue" /></span>
							
						</a> 
						</p>
						<span class="fr"> 
							<a href="#" id="lineCount">在线：
								<span id="onlineCount">0</span>人
							</a>
						</span>
					</div>
					<div class="topbtn">
                        <ul>
                          <common:remindList> 
                          <shiro:hasPermission name="${button.id}">                                              
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" onClick="loadrightmenu('${button.onclickurl}','','${button.onclickurl}');">
                                    <i class="${button.iclass}" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="${button.dataoriginaltitle}"></i>
                                    <b class="badge bg-red pull-right rounded" id="${button.divid}b"><div id="${button.divid}"></div></b>
                                </a>                           
                            </li>
                            </shiro:hasPermission>
                            </common:remindList>                          
                        </ul>
                    </div>
                </div>
            </section>
        </header>
        <section>
            <section class="jcGOA-con">
                <aside class="aside-md bg-light" id="nav">
                    <section class="jcGOA-wrap jcGOA-menu">
                        <header class="menu-header tc clearfix">
                            <div class="clearfix">
                                <a href="#nav" data-toggle="class:nav-xs" class="fr menu-toggle" data-tip="menu">
                                	<!-- <i class="fa fa-align-justify"></i> -->
                                    <i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="隐藏菜单" class="fa fa-align-justify"></i>
                                </a>
                            </div>
                            <div class="sidebar-search clearfix">
                            	<section class="shortcut-link m-r m-t">
                                    	<a href="#" onClick="loadrightmenu('/sys/user/userInfo.action','','/sys/user/userInfo.action');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="修改信息" class="fa fa-Change-info"></i></a>
                                    	<a href="#" onClick="loadrightmenu('/sys/user/userPwd.action','','/sys/user/userPwd.action');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="修改密码" class="fa fa-key"></i></a>
                                    	<shiro:hasPermission name="group:select"><a href="#" onClick="loadrightmenu('/sys/group/manage.action?groupType=1','','/sys/group/manage.action?groupType=1');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="个人组别" class="fa fa-accessibility"></i></a></shiro:hasPermission>
                                    	<a href="#" onClick="loadrightmenu('/sys/portal/manageSet.action?portalType=ptype_user_only','','/sys/portal/manageSet.action?portalType=ptype_user_only');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="个人门户设置" class="fa fa-align-justi"></i></a>
                                    	<a href="#" onClick="loadrightmenu('/sys/noticeMsg/manage.action','','/sys/noticeMsg/manage.action');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="消息中心" class="fa fa-por-Settings"></i></a>
                                    	<!-- 
                                    	
                                    	<a href="#" onClick="loadrightmenu('/sys/user/userInfo.action','','/sys/user/userInfo.action');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="修改信息" class="men1"></i></a>
                                    	<a href="#" onClick="loadrightmenu('/sys/user/userPwd.action','','/sys/user/userPwd.action');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="修改密码" class="men1"></i></a>
                                    	<shiro:hasPermission name="group:select"><a href="#" onClick="loadrightmenu('/sys/group/manage.action?groupType=1','','/sys/group/manage.action?groupType=1');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="个人组别" class="men1"></i></a></shiro:hasPermission>
                                    	<a href="#" onClick="loadrightmenu('/sys/portal/manageSet.action?portalType=ptype_user_only','','/sys/portal/manageSet.action?portalType=ptype_user_only');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="个人门户设置" class="men1"></i></a>
                                    	<a href="#" onClick="loadrightmenu('/sys/noticeMsg/manage.action','','/sys/noticeMsg/manage.action');"><i data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="消息中心" class="men1"></i></a>
                                    	
                                    	 -->
                                    </section>
                            	
                                <div class="input-box dropdown clearfix hide">
                                    <a href="javascript:;" class="remove"></a>
                                    <input type="text" placeholder="快速查询..." />
                                    <button type="submit" class="submit"><i class="fa fa-search"></i></button>
                                    <button type="submit" class="submit nav-xs-submit dropdown-toggle" data-toggle="dropdown"><i class="fa fa-search"></i></button>
                                    <button type="submit" class="submit nav-xs-submit remove dropdown-toggle" data-toggle="dropdown"><i class="fa fa-remove"></i></button>
                                    <section class="dropdown-menu animated fadeInRight wrapper">
                                        <form role="search">
                                            <div class="input-append m-b-none">
                                                <input type="text" class="form-control" placeholder="快速查询...">
                                                <button type="submit" class="btn">
                                                    <i class="fa fa-search"></i>
                                                </button>
                                            </div>
                                        </form>
                                    </section>
                                </div>
                            </div>
                        </header>
                        <section class="menu">
                        	<div id="slim-scroll">
                                <nav class="nav-primary hidden-xs nav-back"><input type="hidden" id="loadmenubarid" name="loadmenubarid" value=""/><input type="hidden" id="loadmenubarurl" name="loadmenubarurl" value=""/><input type="hidden" id="urlParameter" name="urlParameter" value=""/>
                                <input type="hidden" id="newcrumbs" name="newcrumbs" value=""/><input type="hidden" id="oldcrumbs" name="oldcrumbs" value=""/><input type="hidden" id="copycrumbs" name="copycrumbs" value=""/>
       							<input type="hidden" id="loadmenubarName" name="loadmenubarName" value="${homemenu }"/><input type="hidden" id="loadmenubarName1" name="loadmenubarName1" value=""/>
       							<input type="hidden" id="loadmenubarName2" name="loadmenubarName2" value=""/><input type="hidden" id="loadmenubarName3" name="loadmenubarName3" value=""/>			
       							<input type="hidden" id="loadmenubarFloors" name="loadmenubarFloors" value=""/>
       							<input type="hidden" id="valPageSave" name="valPageSave" value="false"/>
       							<input type="hidden" id="valPageSaveMneuBar" name="valPageSaveMneuBar" value=""/>
       								<ul class="nav" id="leftmenucontent">
       								<c:forEach items="${leftmenuList}" var="leftmenu" varStatus="counts">
									      <li id="loadfirst" class="menus">
                                            <a href="javascript:void(0)" onClick="menuClick('${leftmenu.id}','${leftmenu.actionName}',1,'${leftmenu.name}')">
									             <c:if test="${ischildnode == '0' }">
									             	<i class="${leftmenu.icon}"><b class="b-bg-${counts.count%8}"></b></i>
									             	<c:if test="${leftmenu.isNextNode > 0 }">
									             		<span class="pull-right">
									              		<i class="fa fa-angle-down text"></i>
									              		<i class="fa fa-angle-up text-active"></i>
									              		</span>
									              	</c:if>
									             </c:if>
									             <c:if test="${ischildnode != '0' }">
									             	<i class="${leftmenu.icon}"></i>
									             	<c:if test="${leftmenu.isNextNode > 0 }">
									              		<i class="fa fa-angle-down text"></i>
									              		<i class="fa fa-angle-up text-active"></i>
									             	</c:if>
									             </c:if>
									             <span>${leftmenu.name}</span>
									         </a>
									         <c:if test="${leftmenu.isNextNode > 0 }">
									         <ul class="nav lt" id="divleft${leftmenu.id }">
											 </ul>
											 </c:if>
									       </li>
									   </c:forEach>
									   <c:forEach items="${menuList}" var="menu" >
									   		<c:forEach items="${menu.childmenus}" var="leftmenu" varStatus="counts">
									   			<li id="menu${menu.id }" name="menu${leftmenu.id }" class="menus" style="display: none;">
									   				<a id="floor${leftmenu.id }" class="liAMneu" href="javascript:void(0)" onClick="menuClick('${leftmenu.id}','${leftmenu.actionName}',1,'${leftmenu.name}')">
									             		<i class="${leftmenu.icon}"><b class="b-bg-${counts.count%8}"></b></i>
									             		<c:if test="${leftmenu.childmenussize > 0 }">
									             			<span class="pull-right">
									              			<i class="fa fa-angle-down text"></i>
									              			<i class="fa fa-angle-up text-active"></i>
									              			</span>
									             		</c:if>
									             	<span>${leftmenu.name}</span>
									         		</a>
									         		<c:if test="${leftmenu.childmenussize > 0 }">
									         			<ul class="nav lt ulmenus" id="ul${leftmenu.id }">
									         				<c:forEach items="${leftmenu.childmenus}" var="childmenu" >
									         					<li name="menu${childmenu.id }">
									         						<a id="floor${childmenu.id }" class="liAMneu" href="javascript:void(0)" onClick="menuClick('${childmenu.id}','${childmenu.actionName}',2,'${childmenu.name}')">
									         							<i class="${childmenu.icon}"></i>
									         							<c:if test="${childmenu.childmenussize > 0 }">
									         								<i class="fa fa-angle-down text"></i>
									              							<i class="fa fa-angle-up text-active"></i>
									         							</c:if>
									         						<span>${childmenu.name}</span>
									         						</a>
									         						<c:if test="${childmenu.childmenussize > 0 }">
									         							<ul class="nav lt ulmenus" id="ul${childmenu.id }">
									         								<c:forEach items="${childmenu.childmenus}" var="childvo" >
									         									<li name="menu${childvo.id }">
									         										<a id="floor${childvo.id }" class="liAMneu" href="javascript:void(0)" onClick="menuClick('${childvo.id}','${childvo.actionName}',3,'${childvo.name}')">
									         										<i class="${childvo.icon}"></i>
									         										<c:if test="${childvo.childmenussize > 0 }">
									         											<i class="fa fa-angle-down text"></i>
									              										<i class="fa fa-angle-up text-active"></i>
									         										</c:if>
									             									<span>${childvo.name}</span>
									         										</a>
									         										<c:if test="${childvo.childmenussize > 0 }">
									         											<ul class="nav lt">
									         											</ul>
									         										</c:if>
									         									</li>
									         								</c:forEach>
									         							</ul>
									         						</c:if>
									         					</li>
									         				</c:forEach>
									         			</ul>
											 		</c:if>
									   			</li>
									   		</c:forEach>
									   </c:forEach>     
									</ul>
      							</nav>
                            </div>
                        </section>
                    </section>
                </aside>
                
                <section style="width:100%;">
                         	<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;"
								scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
                </section>
                        
            </section>
             <div class="messageTip messageTip-1" style="z-index: 800;display:none">
                   <div class="messageHeader clearfix">
                       <h4 class="fl"><i class="fa fa-chat m-r-xs"></i>消息提醒</h4>
                      <button type="button" class="close m-t-sm m-r-sm messageTip-close">×</button>
                   </div>
                   <ul class="messageList">
                   </ul>
                   <div class="message-btm"><a href="javascript:void(0)" onClick="loadrightmenu('/sys/noticeMsg/manage.action','','/sys/noticeMsg/manage.action')" class="fr m-r"><i class="fa fa-caret-right m-r-xs"></i>更多</a></div>
               </div>
        </section>
    </section>
    <div class="nav-xs-dd bg-light" id="nav-menu-tie">
		<div id="nav-menu-con" class="nav-primary"></div>
	</div>
<!-- 强制修改密码DIV -->
<div id="duressPasswordDiv"></div>
<div id="voiceDiv"></div>
<!-- 生日祝福 -->
<div id="birthdayDiv" class="birthday">
	<div>
		<button class="close" id="birthdayBtn" onclick='$("#birthdayDiv").fadeOut(1000);'>x</button>
	</div>
	<div>
		<img alt="生日提醒" width="400" height="300"  src="${sysPath}/images/3108677.gif" >
	</div>
</div>
<script type="text/javascript">
var menuswrite = {};
menuswrite.statue = false;//启动自定义页面跳转清空方法标识
menuswrite.formstatue = false; //启动表单修改后保存前离开提示标识
</script>
<script src="${sysPath}/js/com/sys/menu/navigationbar.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/workFlow/workFlowUtils.js" type="text/javascript"></script>
<script src="${sysPath}/js/lib/common/msgTip.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/duressPassword.js"></script>
<script>
$(function() {
    $("#topnav-other_modules").other();
    if ('${portalurl}' != null && '${portalurl}' != '')
        mainFrame.location.href = getRootPath() + '${portalurl}';

    $('#loadOver').attr('value', '1');
    loadbirthday();
    var mydropdown = new customDropDown($("#dropdownMenu"));
});

function exit() {
    msgBox.confirm({
        content: "是否退出吗？",
        success: function() {
            promptOnUnLoad();
            location.href = getRootPath() + "/logout";
        }
    });
}

var historyUrl = new Array();

function menuClick(menuId, url, floors, menuName) {
    setMenuId(menuId, url, floors, menuName);
    if (url != null && url.length > 0) {
        historyUrl.length = 0;
        historyUrl.push({
            url: url
        });

        if (typeof(window.valPageSave) != 'undefined' && window.valPageSave.value != null && window.valPageSave.value == 'true') {
            var opt = {
                content: $.i18n.prop("JC_OA_IC_096"),
                success: function() {
                    if (document.getElementById('mainFrame') != null) {
                        var mainWindow = document.getElementById('mainFrame').contentWindow;
                        mainWindow.pageMethon(); //名称统一 ，方法内具体实现使用人自行开发
                    }
                    window.valPageSave.value = "false";
                    mainFrame.location.href = getRootPath() + "/" + url;
                },
                noback: function() {
                    window.valPageSave.value = "false";
                    mainFrame.location.href = getRootPath() + "/" + url;
                },
                cancel: function() {
                    anchormenu(window.loadmenubarurl.value);
                },
                buttons: {
                    "离开并存草稿": "yes",
                    "取消": "no",
                    "离开": "cancel"
                }
            };
            msgBox.confirm(opt);
        } else {
        	isConcealMenu?loadrightmenu(url,"",url):(mainFrame.location.href = getRootPath() + "/" + url);
        }
    }
}
$(document).on("keydown", function(e) {
    if (e.keyCode == 8 && e.target.tagName != "INPUT" && e.target.tagName != "TEXTAREA")
        return false;
});

$("#onlineCount").on("click", function() {
    showOnlinePerson.init();
});

var i18nData = {}; //全局资源文件数据
//换肤下拉菜单点击事件
function customDropDown(ele) {
    this.dropdown = ele;
    this.options = this.dropdown.find("ul.dropdown-menu > li");
    this.val = '';
    this.initEvents();
}

customDropDown.prototype = {
    initEvents: function() {
        var obj = this;
        //点击下拉列表的选项
        obj.options.on("click", function() {
            var opt = $(this).attr("value");
            skin(opt);
        });
    }
};
</script>
</body>
</html>