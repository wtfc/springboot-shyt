<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
     <header class="con-header pull-in" id="navigationMenu">
           <h1>客户信息</h1>
           <div class="crumbs">
               <a href="#">首页</a><i></i>客户信息
           </div>
   </header>
   <section class="m-t-md panel personal-con">
   	<div class="personal-con-left">
       	<div class="personal-icon">
       		<b class="rounded">
               	<i class="fa fa-official"></i>
                   	客户信息
               </b>
          	</div>
       </div>
   	<div class="personal-con-right">
       	<div class="m-b-lg personal-img"><img src="${sysPath}/images/demoimg/system.jpg" width="1920" height="215"  alt=""/></div>
           <div class="m-l-lg personal-right-con">
           	<p><i class="fa fa-stop fl"></i>客户信息提供了系统内所有客户信息；</p>
           	<p><i class="fa fa-stop fl"></i>可以根据客户名称来查询客户所有合同、计费信息、联系信息、回访信息、投诉信息、机房信息、云、CDN、网络等资源信息；</p>
           	<p><i class="fa fa-stop fl"></i>各级组织通过权限模型设计与分级、数据权限管理体系设计实现既共享又独立运行的垂直的服务。</p>
           </div>
     </div>
   </section>
</section>

<script>
  $(function(){
	ie8InRounded();
	var content_height=$("#content").height();
	$(".personal-con").height(content_height-$("#navigationMenu").height()-75);
   })
</script>