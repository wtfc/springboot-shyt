<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/treeSelectControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/im/infoColumnSelect.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="infoHeader"><div class="con-heading fl" id="navigationMenu"></div>
	</header>
	<div class="divinfo">
	<div class="nav-primary nav-infomation nav-tree m-t-md" id="column">
		<ul class="nav">
			<li class="active">
				<a href="#">
					<i class="fa"></i>
					<span id="menuall" >
						主栏目
					</span>
				</a>
				<ul class="nav lt" id="firstColumn">
					
				</ul>
			</li>
		</ul>
		</div>
	</div>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%> 
<!-- <section class="panel m-t-md clearfix" id="column">
	<div class="h500">
		<section class="panel m-t-md" id="prompt_sub">
			<div class="h500">
				<div class="g-z-content img-rounded">
					<img src="./images/demoimg/no-staff.png" width="70px;">
					<div class="g-z-text">
						<span class="g-z-span">抱歉！</br>您暂无可操作栏目
						</span><br>
					</div>
				</div>
			</div>
		</section>
	</div>
</section> -->

