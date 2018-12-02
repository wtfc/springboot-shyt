<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/po/plan/monthPlanCondition.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl">
			<h1>月计划提交情况</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
	<form class="clearfix" id="weemPlan">
		<input type="hidden" id="yearId" name="yearId" value=${sysYear } /> 
		<input type="hidden" id="monthId" name="monthId" value=${sysMonth } /> 
		<input type="hidden" id="year" name="year" /> 
		<input type="hidden" id="id" name="id" /> 
		<input type="hidden" id="name" name="name" /> 
		<input type="hidden" id="month" name="month" /> 
		<input type="hidden" id="week" name="week" />
		<input type="hidden" id="code" name="code" />
	
		<section class="tree-fluid m-t-md">
			<aside class="tree-wrap" id="LeftHeight_box">
				<section class="panel" id="LeftHeight" style="overflow-y: auto;">
					<div class="panel-heading clearfix" id="header_2">
		        		<h2>选择下属</h2>
		        	</div>
					<div id="treeDemo" class="ztree"></div>
				</section>
			</aside>
		</section>
		
		<section class="tree-right">
			<section class="tabs-wrap m-t-md">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#messages-2" data-toggle="tab">月计划提交情况</a></li>
				</ul>
			</section>
			<section class="panel tab-content">
				<div class="tab-pane fade active in" id="messages-2">
					<header class="report-header">
						<span id="datesNowMonth"></span>年月计划提交情况 
						<a href="#" onclick="nextMonthYear(-1);" class="btn">&laquo; 上年</a>
						<a href="#" onclick="nextMonthYear(1);" class="btn">下年&raquo;</a> <br /> <br />
						 空白：未提交月计划；-n：延迟n月后提交月计划；n：提前n月提交月计划；<font color="#44cc00"><b>√</b></font>：当月提交月计划；
					</header>
	
					<div class="table-wrap">
						<table id="monthPlan" class="table first-th-tc report">
							<thead id="monthPlanThead">
							</thead>
							<tbody id="monthPlanTbody">
							</tbody>
						</table>
					</div>
				</div>
			</section>
		</section>
	</form>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>