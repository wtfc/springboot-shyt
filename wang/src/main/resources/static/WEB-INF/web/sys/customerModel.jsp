<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
     <header class="con-header pull-in" id="navigationMenu">
              <h1>客户模块</h1>
              <div class="crumbs">
                  <a href="#">首页</a><i></i>客户模块
              </div>
      </header>
      <section class="m-t-md panel personal-con">
      	<div class="personal-con-left">
          	<div class="personal-icon">
          		<b class="rounded">
                  	<i class="fa fa-interaction"></i>
                      	客户模块
                  </b>
             	</div>
          </div>
      	<div class="personal-con-right">
          	<div class="m-b-lg personal-img"><img src="${sysPath }/images/demoimg/zygl.jpg" width="1920" height="215"  alt=""/></div>
              <div class="m-l-lg personal-right-con">
              	<p><i class="fa fa-stop fl"></i>客户模块用于客户登录后，对自身信息、设备的操作和网络资源信息的查看</p>
              </div>
        </div>
      </section>
</section>

<script>
  $(function(){
	ie8InRounded();
	var content_height=$("#content").height();
	$(".personal-con").height(content_height-$("#navigationMenu").height()-75)
   })
</script>