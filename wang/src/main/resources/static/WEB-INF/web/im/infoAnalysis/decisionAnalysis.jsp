<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder" id="scrollable">
    <header class="con-header pull-in" id="navigationMenu">
            <h1>决策分析</h1>
            <div class="crumbs">
                <a href="#">首页</a><i></i>决策分析
            </div>
    </header>
    <section class="m-t-md panel personal-con">
    	<div class="personal-con-left">
        	<div class="personal-icon">
        		<b class="rounded">
                	<i class="fa fa-decision"></i>
                    决策分析
                </b>
           	</div>
        </div>
    	<div class="personal-con-right">
        	<div class="m-b-lg personal-img"><img src="../../images/demoimg/decision.jpg" width="1920" height="215"  alt=""/></div>
            <div class="m-l-lg personal-right-con">
            	<p><i class="fa fa-stop fl"></i>决策分析通过各种图表展示（饼状图、柱状图、仪表盘等），为组织高层领导提供决策参考和依据，帮助强化领导的监控管理，增强对组织的控制力，及时有效监控各部门、各个人员的各项业务的进度情况。</p>
            </div>
      </div>
    </section>
    
</section>
<script>
	$(function() {
		ie8InRounded();
		var content_height = $("#content").height();
		$(".personal-con")
				.height(content_height - $("#navigationMenu").height() - 75)
	})
</script>