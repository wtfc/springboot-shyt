<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
     <header class="con-header pull-in" id="navigationMenu">
              <h1>项目管理</h1>
              <div class="crumbs">
                  <a href="#">首页</a><i></i>项目管理
              </div>
      </header>
      <section class="m-t-md panel personal-con">
      	<div class="personal-con-left">
          	<div class="personal-icon">
          		<b class="rounded">
                  	<i class="fa fa-flow"></i>
                      	项目管理
                  </b>
             	</div>
          </div>
      	<div class="personal-con-right">
          	<div class="m-b-lg personal-img"><img src="${sysPath }/images/demoimg/flow.jpg" width="1920" height="215"  alt=""/></div>
              <div class="m-l-lg personal-right-con">
              	<p><i class="fa fa-stop fl"></i>项目管理为组织内部协同业务提供技术支持，根据内部需求制定开发计划，完成相应的软件开发。</p>
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