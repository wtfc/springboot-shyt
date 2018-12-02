<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
     <header class="con-header pull-in" id="header_1">
           <h1>资源管理</h1>
           <div class="crumbs">
               <a href="#">首页</a><i></i>资源管理
           </div>
   </header>
   <section class="m-t-md panel personal-con">
   	<div class="personal-con-left">
       	<div class="personal-icon">
       		<b class="rounded">
               	<i class="fa fa-message"></i>
                   	资源管理
               </b>
          	</div>
       </div>
   	<div class="personal-con-right">
       	<div class="m-b-lg personal-img"><img src="${sysPath}/images/demoimg/zygl.jpg" width="1920" height="215"  alt=""/></div>
           <div class="m-l-lg personal-right-con">
           	<p><i class="fa fa-stop fl"></i>资源管理提供了机房机柜、设备、平面图等信息；</p>
           	<p><i class="fa fa-stop fl"></i>可以对机柜中的设备进行设备、查询、更改等业务功能；</p>
           	<p><i class="fa fa-stop fl"></i>各级组织通过完善的数据查询体系，可以准确的定位到具体设备信息及位置为管理人员提供垂直管理服务。</p>
           </div>
     </div>
   </section>
</section>

<script>
  $(function(){
	ie8InRounded();
	var content_height=$("#content").height();
	$(".personal-con").height(content_height-$("#header_1").height()-75)
   })
</script>