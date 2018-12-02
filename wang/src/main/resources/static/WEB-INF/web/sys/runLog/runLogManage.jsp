<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

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
					<td class="w170">开始时间</td>
					<td>
						<div class="input-group w-p100">
							<input class="datepicker-input" type="text" id="startAt" name="startAt" data-ref-obj="#operEndTime lt" data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss"><div class="input-group-btn w30"> -</div><input class="datepicker-input" type="text" id="endAt" name="endAt" data-ref-obj="#operStartTime gt" data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss">
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<section class="form-btn m-b-lg">
			<button class="btn dark query-jump" type="button" id="queryoperlog" >查 询</button>
			<button class="btn" type="reset" id="queryReset">重 置</button>
		</section>
		
	</form>
	</div>
	<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp" %>
</section>  
<section class="panel ">

		<h2 class="panel-heading clearfix">查询结果</h2>
	<div class="table-wrap">
		<table class="table table-striped tab_height" id="operlogTable">
			<thead>
				<tr>
					<th style="width:16%;">开始时间</th>
					<th style="width:16%;">执行结束时间</th>
					<th style="width:50%;">执行结果</th>
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
<script >
jQuery.getScript("${sysPath}/js/com/sys/runLog/runLogManage.js");
</script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
