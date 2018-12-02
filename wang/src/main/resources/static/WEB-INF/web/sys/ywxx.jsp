<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
     <header class="con-header pull-in" id="navigationMenu">
              <h1>业务信息</h1>
              <div class="crumbs">
                  <a href="#">首页</a><i></i>业务信息
              </div>
      </header>
      <section class="m-t-md panel personal-con">
      	<div class="personal-con-left">
          	<div class="personal-icon">
          		<b class="rounded">
                  	<i class="fa fa-flow"></i>
                      	业务信息
                  </b>
             	</div>
          </div>
      	<div class="personal-con-right">
          	<div class="m-b-lg personal-img"><img src="${sysPath }/images/demoimg/flow.jpg" width="1920" height="215"  alt=""/></div>
              <div class="m-l-lg personal-right-con">
              	<p><i class="fa fa-stop fl"></i>业务信息模块主要记录查询客户回访、投诉、机房、网络资源、云资源、CDN资源等信息</p>
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