<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<c:set var="sysPath" value="${pageContext.request.contextPath}"/>

<c:if test="${sysPath=='/'}">
	<c:set var="sysPath" value="" />
</c:if>
<!DOCTYPE html>

<html class="jcGOA">
<head>
<title>森华易腾CRM系统</title>
<link rel="shortcut icon" href="${sysPath }/favicon.ico" type="image/x-icon" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<meta charset="utf-8">
<link href="${sysPath }/css/JcGoa_v2.0.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	// 如果在框架中，则跳转刷新上级页面
	if(self.frameElement && self.frameElement.tagName=="IFRAME"){
		parent.location.reload();
	}
	if(typeof(rootPath) == "undefined"){
		var rootPath = "";
	}
	
	function setRootPath(path){
		rootPath = path;
	}
	
	function getRootPath() {
	    return rootPath;
	}
	if(typeof(sysTheme) == "undefined"){
		var sysTheme = "0";
	}

	function setTheme(theme){
		sysTheme = theme;
	}

	function getTheme(){
		return sysTheme;
	}
</script>

<script type="text/javascript" src='${sysPath}/js/jquery.min.js'></script>
<script type="text/javascript" src='${sysPath}/js/lib/jquery/jquery.cookie.js'></script>
<script type="text/javascript">setRootPath('${sysPath}');</script>
<script type="text/javascript" src='${sysPath}/js/lib/jquery-validation/jquery.validate.all.js'></script>
<script type="text/javascript" src="${sysPath}/js/jQuery_jbox/jquery.jBox-2.3.js"></script>
<script type="text/javascript" src='${sysPath}/js/app.v2.js'></script>
<script src="${sysPath }/js/com/sys/login/login.js"></script>
<!--[if lt IE 9]>
    <script src="${sysPath }/js/ie/html5shiv.js"></script>
<![endif]-->
</head>
<body class="loginBodyBg">
    <div class="loginBox">
        <h1>森华易腾CRM系统</h1>
    	<div class="loginImgBox">
    		<img src="${sysPath }/images/demoimg/login-imgLT.jpg" alt="以人为本的核心设计理念">
            <img src="${sysPath }/images/demoimg/login-imgRT.jpg" alt="多端同步">
            <img src="${sysPath }/images/demoimg/login-imgLB.jpg" alt="以精确管理为中心思想">
            <img src="${sysPath }/images/demoimg/login-imgRB.jpg" alt="简单易用">
    	</div>
        <div class="loginFormBox">
        	<h2>用户登录</h2>
            <form id="loginForm" class="form-signin" action="${sysPath}/login" method="post">
            	<p class="inputText">
            		<input type="text"  id="username" name="username" >
            	</p>
            	<p class="inputPassword">
            		<input type="password" id="password" name="password"  >
            	</p>
                <p class="login-error" id="loginError">
                    <c:set var="errorMessage" scope="page" value="${errorMessage}"/>
                    <c:if test="${errorMessage!=null}">
                        ${errorMessage}
                    </c:if>
                    <c:set var="kickout" scope="page" value="${kickout}"/>
                    <c:if test="${kickout!=null}">
                        您被踢出
                    </c:if>
                    <c:set var="timeout" scope="page" value="${timeout}"/>
                    <c:if test="${timeout!=null}">
                        系统已超时，请重新登录
                    </c:if>
                   
                </p>
                 
                <p class="Buttons">
                	<input type="checkbox" id="Remember"><label for="Remember">记住用户名</label>
                    <a href="#" id="sub" onClick="javascript:(loginFormSubmit())">登录平台</a>
                </p>
            </form>
            <div class="ForgotPassword"><a href="#" id="showForget" class="fr m-r-lg">忘记密码?</a>
            		<!--[if IE]>
						<a href="${sysPath }/system/getRegFile.action" class="fl m-l-lg">下载辅助安装工具</a>
					<![endif]-->
			</div>
  
        </div>
    </div>
    
    <!--start 找回密码-->
	<div class="modal fade panel" id="myModal" aria-hidden="false">
		<div class="modal-dialog" style="width:500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title">密码找回</h4>
				</div>
				<div class="modal-body">
                <form id="forgetPwdForm" class="form-table">
					<table class="loginModal table table-td-striped">
						<tr>
							<td><span class="required">*</span>登录名</td>
							<td><input type="text" name="loginName" id="loginName"></td>
						</tr>
						<tr>
							<td><span class="required">*</span>密码提示问题</td>
							<td>
								<dic:select name="question" id="question" dictName="question" headName="-请选择-" headValue="" defaultValue=""/>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>密码提示答案</td>
							<td><input type="text" name="answer" id="answer"></td>
						</tr>
					</table>
                 </form>
                 <div></div>
				</div>
                
				<div class="modal-footer form-btn">
					<a class="btn dark" type="button" onClick="javascript:(forgetSubmit())">提 交</a>
					<a class="btn" type="reset" data-dismiss="modal">关 闭</a>
				</div>
			</div>
		</div>
	</div>
	<!--end 找回密码-->
	<!-- 校验插件 -->
</body>
</html>