<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
	<header class="con-header pull-in" id="navigationMenu">
		<h1>个人办公</h1>
		<div class="crumbs">
			<a href="#">首页</a><i></i>个人办公
		</div>
	</header>
	<section class="m-t-md panel personal-con">
		<div class="personal-con-left">
			<div class="personal-icon">
				<b class="rounded"> <i class="fa fa-personage"></i> 个人办公
				</b>
			</div>
		</div>
		<div class="personal-con-right">
			<div class="m-b-lg personal-img">
				<img src="../../images/demoimg/personal-img.jpg" width="1920"
					height="215" alt="" />
			</div>
			<div class="m-l-lg personal-right-con">
				<p>
					<i class="fa fa-stop fl"></i>个人办公为组织内部上、下级日常工作的沟通汇报、领导批示，个人工作内容的跟踪、提醒、共享提供管理解决方案；
				</p>
				<p>
					<i class="fa fa-stop fl"></i>通过周、月、年的工作计划，任务的督办协办提高组织内部上、下级工作汇报，重点工作任务下发、执行、跟踪的办事效率
				</p>
				<p>
					<i class="fa fa-stop fl"></i>工作日志、工作日程为组织内部用户日常工作的记录、跟踪、提醒、共享提供服务。
				</p>
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