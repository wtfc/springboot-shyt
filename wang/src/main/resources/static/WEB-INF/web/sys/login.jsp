<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>

<html class="jcGOA">
<head>
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
	
	/* function downLoad(){
		//var url="http://113.10.155.131/CLodopPrint_Setup_for_Win32NT.zip";
		var url="E:\\tomcat\\apache-tomcat-7.0.65\\webapps\\shyt\\upload\\CLodopPrint_Setup_for_Win32NT.zip";
		//var url=getRootPath()+"\\upload\\CLodopPrint_Setup_for_Win32NT.zip";
		//var url="/shyt/upload/CLodopPrint_Setup_for_Win32NT.zip";
		window.location.href=url;
	}
	 */
</script>
<title>信息资源管理平台</title>
<link rel="shortcut icon" href="${sysPath }/favicon.ico" type="image/x-icon" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<meta charset="utf-8">
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<link href="${sysPath }/css/JcGoa_v2.0.css" rel="stylesheet" type="text/css" />
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
<body>
    <div class="login_box">
    	<div class="login_header">
    		<div class="logo">
				<img src="img/logo.png">
			</div>
<!--         <span class="fl m-r-md w200"><img width="200" height="80" src="${sysPath}/images/demoimg/logo_index.png"></span> -->
	        <div class="title">
				<h3>信息资源管理平台</h3>
				<p>SHYT office platform</p>
			</div>
		</div>
		<div class="login_content">
			<div class="login_form">
				<div class="form_content">
					<p class="form_titie">
					用户登录
					</p>
					 <form id="loginForm" class="form-signin" action="${sysPath}/login" method="post">
						<p class="form_user line">
							<span class="input_line username"></span>
							<input type="text" placeholder="请输入您的用户名" id="username" name="username" value="${username}"/>
						</p>
						<p class="form_pass line">
							<span class="input_line password"></span>
							<input type="password" id="password" name="password"  placeholder="请输入您的密码"/>
							<label for="password" class="error" id="loginLable">
								<c:set var="errorMessage" scope="page" value="${errorMessage}"/>
			                    <c:if test="${errorMessage!=null}">
			                        ${errorMessage}
			                    </c:if>
			                    <c:set var="kickout" scope="page" value="${kickout}"/>
			                    <c:if test="${kickout!=null}">
			                        	您的账号已在其他客户端登录，如非本人操作，请及时修改密码或联系管理员
			                    </c:if>
			                    <c:set var="timeout" scope="page" value="${timeout}"/>
			                    <c:if test="${timeout!=null}">
			                        	系统已超时，请重新登录
			                    </c:if>
						    </label>
						</p>
						<p class="login-error" id="loginError"></p>
						<p class="form_assist" >
							<label class="checkbox inline">
								<input type="checkbox" id="Remember">记住用户名
							</label>
							<!-- <a href="#"  class="fr" id="showForget">忘记密码？</a> -->
							<a href="http://113.10.155.131/CLodopPrint_Setup_for_Win32NT.zip"  class="fr">下载辅助安装工具</a>
							<!-- <a class="fr" onclick="downLoad()">下载辅助安装工具</a> -->
						</p>
						<p class="form_btn">
							<a class="btn" href="#" onClick="javascript:(loginFormSubmit())">登录平台</a>
						</p>
					</form>
				</div>
				<%-- <div class="form_footer">
					<a href="${sysPath }/system/getRegFile.action" class="fl m-l-lg">下载辅助安装工具</a>
				</div> --%>
			</div>
		</div>
		<div class="login_footer">
			<p>北京森华易腾通信技术有限公司  版权所有 客服电话：400-818-1123 </p>
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
					<a class="btn dark" type="button" onClick="javascript:(forgetSubmit())">确 定</a>
					<a class="btn" type="reset" data-dismiss="modal">取 消</a>
				</div>
			</div>
		</div>
	</div>
	<!--end 找回密码-->
	<!-- 校验插件 -->
	<!--登录自动加载插件 -->
	</div>
	<span class="hide">
	<object id='DWebSignSeal' style='position:absolute;width:0px;height:0px;left:0px;top:0px;' classid='clsid:77709A87-71F9-41AE-904F-886976F99E3E' codebase='${sysPath }/install/setup/WebSign.dll#version=4,5,5,0'>
	</OBJECT>
	<object id=WebOffice style='width:100%;LEFT: 0px; TOP: 0px;padding-right:5px' classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5' codebase='${sysPath }/plugin/WebOffice.cab#Version=7,0,1,0'>
		<param name='_ExtentX' value='6350'>
		<param name='_ExtentY' value='6350'>
	</object> 
	<object id='DES' style='width:0px;height:0px;left:0px;top:0px;' classid='clsid:E009118F-0E86-494D-B7FF-027F342B45CE' codebase='${sysPath }/plugin/DESSeal.dll#Version=4,2,9,0'>
	</OBJECT>
	<object id='HWPostil' style='width:100%;height:100%;left:0px;top:0px;' classid='clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561' codebase='${sysPath }/plugin/HWPostil.ocx#Version=3,1,2,0'>
	</OBJECT>
	</span>
</body>
<%-- <body class="loginBodyBg">
    <div class="loginBox">
        <h1>森华易腾系统 </h1>
    	<div class="loginImgBox">
    		<img src="${sysPath }/images/demoimg/login-imgLT.jpg" alt="竞争与超越">
            <img src="${sysPath }/images/demoimg/login-imgRT.jpg" alt="执行与结果">
            <img src="${sysPath }/images/demoimg/login-imgLB.jpg" alt="责任与付出">
            <img src="${sysPath }/images/demoimg/login-imgRB.jpg" alt="团结与协作">
    	</div>
        <div class="loginFormBox">
        	<h2>用户登录</h2>
            <form id="loginForm" class="form-signin" action="${sysPath}/login" method="post">
            	<p class="inputText">
            		<input type="text"  id="username" name="username"  placeholder="请输入您的用户名" value="${username}">
            	</p>
            	<p class="inputPassword">
            		<input type="password" id="password" name="password"  placeholder="请输入您的密码">
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
                    <a href="#" onClick="javascript:(loginFormSubmit())">登录平台</a>
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
</body> --%>
</html>