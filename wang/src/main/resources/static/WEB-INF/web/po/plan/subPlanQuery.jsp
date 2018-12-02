<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
	    <div class="con-heading fl" id="navigationMenu"></div>
	    <h1></h1>
	    <div class="crumbs"></div>
	</header>
	<%--李洪宇 20140728 修改查询条件折叠  开始 --%>
		<%--<section class="tree-fluid m-t-md">
		<aside class="tree-wrap" id="LeftHeight_box">--%>
		<section class="tree-fluid">
	    <aside class="tree-wrap m-t-md" id="LeftHeight_box">
	        <section class="panel" id="LeftHeight" style="overflow-y: auto;">
		        <div class="panel-heading clearfix" id="header_2">
		        	<h2>选择下属</h2>
		        </div>
		        <div id="treeDemo" class="ztree"></div>
	    	</section>
		</aside>
		<section class="tree-right">
		    <%--<section class="panel"> --%>
		    <!--start 查询条件-->
			<section class="panel clearfix search-box search-shrinkage">
			  <div class="search-line hide">
		        <form id="subPlanForm" name="subPlanForm" class="table-wrap form-table" >
					<input id="subUserId" name="subUserId" type="hidden">
					<input id="planType" name="planType" type="hidden">
					<input id="diaryJumpType" name="diaryJumpType" type="hidden" value=${diaryJumpType}>
		            <table class="table table-td-striped">
		                <tbody>
		                    <tr>
		                        <td>计划标题</td>
		                        <td colspan="3">
									<input id="planTitle" name="planTitle" type="text">
		                    	</td>
		                	</tr>
			                <tr>
			                	<td class="w140">计划类型</td>
			                    <td id="planTypeTd">
		                           <label id="weekPlanTypeLabel" class="checkbox inline">
		                               <input type="checkbox" id="weekPlanType" name="planType" value="0">周计划
		                           </label>
		                           <label id="monthPlanTypeLabel" class="checkbox inline">
		                               <input type="checkbox" id="monthPlanType" name="planType" value="1">月计划
		                           </label>
		                           <label id="yearPlanTypeLabel" class="checkbox inline">
		                               <input type="checkbox" id="yearPlanType" name="planType" value="2">年计划
		                           </label>
			                    </td>
			                    <td class="w140">开始时间</td>
			                    <td style="width:40%;">
			                        <div class="input-group w-p100">
			                            <input id="planStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeEnd lt">
			                            <div class="input-group-btn w30">-</div>
			                            <input id="planStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeBegin gt">
			                        </div>
			                    </td>
			                </tr>
		            	</tbody>
		        	</table>
			        <section class="form-btn m-b-lg">
			            <button id="subPlanQuery" name="subPlanQuery" class="btn dark" type="button">查 询</button>
			            <button id="subPlanReset" name="subPlanReset" class="btn" type="button">重 置</button>
			        </section>
		    	</form>
		    	</div>
				<%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%>
			</section>
			<%--<section class="panel m-t-md clearfix"> --%>
			<section class="panel clearfix">
			    <h2 class="panel-heading clearfix">查询结果</h2>
			    <div class="table-wrap">
			        <table id="subPlanTable" name="subPlanTable" class="table table-striped tab_height">
			            <thead>
			                <tr>
			                    <th style="width:40%">计划标题</th>
			                    <th>计划类型</th>
			                    <th>开始时间</th>
			                    <th>结束时间</th>
			                    <th class="w170">操作</th>
			                </tr>
			            </thead>
			            <tbody>
			            </tbody>
			        </table>
			    </div>
			</section>
		</section>
	</section>
</section>
<script src="${sysPath}/js/com/po/plan/subPlanQuery.js"></script>
<script src="${sysPath}/js/common-tree.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>