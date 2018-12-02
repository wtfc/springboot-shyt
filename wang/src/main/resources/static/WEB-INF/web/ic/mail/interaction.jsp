<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
    <header class="con-header pull-in" id="navigationMenu">
            <h1>互动交流</h1>
            <div class="crumbs">
                <a href="#">首页</a><i></i>互动交流
            </div>
    </header>

    <section class="m-t-md panel personal-con">
   	<div class="personal-con-left">
       	<div class="personal-icon">
       		<b class="rounded">
               	<i class="fa fa-interaction"></i>互动交流</b>
        </div>
    </div>
   	<div class="personal-con-right">
       	<div class="m-b-lg personal-img"><img src="../../images/demoimg/interaction.jpg" width="1920" height="215" alt=""/></div>
           <div class="m-l-lg personal-right-con">
           	<p><i class="fa fa-stop fl"></i>互动交流为组织提供了丰富的打破组织边界与地理位置限制的沟通方式，弥补传统沟通方式的不足，提高组织的沟通能力、沟通效率，沟通成本大幅降低；</p>
            <p><i class="fa fa-stop fl"></i>包括内邮件、意见建议等业务服务，不仅作为独立的业务应用，还给系统提供丰富的信息提醒手段，其中短信及邮件与系统的各模块结合实现新信息即时的到达时提醒。</p>
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