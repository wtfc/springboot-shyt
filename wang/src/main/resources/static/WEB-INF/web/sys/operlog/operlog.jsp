<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="navigationMenu">
	<h1></h1>
	<div class="crumbs"></div>
</header>

<section class="panel clearfix search-box search-shrinkage">
<div class="search-line hide">
	<form class="table-wrap form-table" id="operlogListForm" name="operlogListForm" >
		<table class="table table-td-striped">
			<tbody>
				<tr>
					<td class="w140">登录名</td>
					<td ><input type="text" id="loginName" name="loginName" /></td>
					<td class="w140">操作类型</td>
					<td>
						<dic:select name="operType" id="operType" dictName="operType" headType="1" headValue="" defaultValue="" />
					</td>
				</tr>
				<tr>
					<td class="w140">显示名</td>
					<td ><input type="text" id="displayName" name="displayName" /></td>
					<td class="w140">操作时间</td>
					<td>
						<div class="input-group w-p100">
							<input class="datepicker-input" type="text" id="operStartTime" name="operStartTime" data-ref-obj="#operEndTime lt" data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss"><div class="input-group-btn w30"> -</div><input class="datepicker-input" type="text" id="operEndTime" name="operEndTime" data-ref-obj="#operStartTime gt" data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss">
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<section class="form-btn m-b-lg">
			<button class="btn dark query-jump" type="button" id="queryoperlog" >查 询</button>
			<button class="btn" type="button" id="queryReset">重 置</button>
		</section>
		
	</form>
	</div>
		<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>						
</section>  
<section class="panel ">

		<h2 class="panel-heading clearfix">查询结果</h2>
	<div class="table-wrap">
		<table class="table table-striped tab_height" id="operlogTable">
			<thead>
				<tr>
					<th class="w115">登录名</th>
					<th class="w115">显示名</th>
					<th style="width:16%;">部门</th>
					<th style="width:10%;">访问IP</th>
					<th style="width:10%;">操作类型</th>
					<th>操作描述</th>
					<th style="width:20%;">操作时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<section class="clearfix">
		<section class="form-btn fl m-l">
		</section>
		<section class="pagination m-r fr m-t-none">
		</section>
	</section>
</section>
</section>
<script src="${sysPath}/js/com/sys/operlog/operlog.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
