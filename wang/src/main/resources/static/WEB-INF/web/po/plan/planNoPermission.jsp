<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="navigationMenu">
		<h1>计划编制</h1>
		<div class="crumbs">
			<a href="#">首页</a><i></i>个人办公<i></i>工作计划<i></i>计划编制
		</div>
	</header>
	<section class="panel m-t-md" id="prompt_sub">
				<div class="h500">
					<div class="g-z-content img-rounded">
						<img src="../../images/demoimg/no-staff.png" width="70px;">
						<div class="g-z-text">
							<span class="g-z-span">抱歉！</br>暂无编制权限
							</span>
						</div>
					</div>
				</div>
	</section>
</section>
<script type="text/javascript">
	$(document).ready(function(){
		$('#prompt_sub').css('height',$('#scrollable').height() - $('#navigationMenu').height())
	})
</script>
<%@ include file="/WEB-INF/web/include/WorkflowHead.jsp"%>