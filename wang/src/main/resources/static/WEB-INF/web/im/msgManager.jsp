<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder" id="scrollable">
	<header class="con-header pull-in" id="navigationMenu">
		<h1>信息管理</h1>
		<div class="crumbs">
			<a href="#">首页</a><i></i>信息管理
		</div>
	</header>
	<section class="m-t-md panel personal-con">
		<div class="personal-con-left">
			<div class="personal-icon">
				<b class="rounded"> <i class="fa fa-message"></i> 信息管理
				</b>
			</div>
		</div>
		<div class="personal-con-right">
			<div class="m-b-lg personal-img">
				<img src="../../images/demoimg/message.jpg" width="1920" height="215"
					alt="" />
			</div>
			<div class="m-l-lg personal-right-con">
				<p>
					<i class="fa fa-stop fl"></i>信息管理提供了跨越组织、单一组织相结合的信息管理模式，为组织内部实现信息的发布、共享、积累、利用的全过程管理；
				</p>
				<p>
					<i class="fa fa-stop fl"></i>便于组织传递信息、积累知识、建设信息共享平台，为组织节省大量传递信息、查找信息、管理信息的成本。
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