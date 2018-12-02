<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/po/plan/planCollectQuery.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in">
	   <h1></h1>
	   <div class="crumbs"></div>
	</header>
	
	<section class="tabs-wrap m-t-md">
	   <ul class="nav nav-tabs">
	       <li class="active"><a href="#collectListMsg" data-toggle="tab" data-id="collectListMsg">汇总</a></li>
	   </ul>
	</section>
	
	<section class="tab-content">
	<section class="panel tab-content search-shrinkage">
	     <div id="collectListMsg" class="tab-pane fade active in">
	     <div class="search-line hide">
	        <form id="collectListForm" class="table-wrap form-table">
	        	<input type="hidden" id="diaryJumpType" name="diaryJumpType" value="${diaryJumpType}">
	            <table class="table table-td-striped">
	                <tbody>
	                    <tr>
		                    <td class="w140">计划标题</td>
		                    <td><input id="planTitle" name="planTitle" type="text"></td>
	                        <td class="w140">计划类型</td>
	                        <td style="width:40%;" id="planTypeTd">
	                           <label for='weekPlanType_collect' class="checkbox inline">
	                               <input type="checkbox" id="weekPlanType_collect" name="planType" value="0">周计划
	                           </label>
	                           <label for='monthPlanType_collect' class="checkbox inline">
	                               <input type="checkbox" id="monthPlanType_collect" name="planType" value="1">月计划
	                           </label>
	                           <label for='yearPlanType_collect' class="checkbox inline">
	                               <input type="checkbox" id="yearPlanType_collect" name="planType" value="2">年计划
	                           </label>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>编制人</td>
	                        <td><div id="collectShowUserTree" name="collectShowUserTree"></div></td>
	                        <td>编制部门</td>
	                        <td><div id="collectDeptTree" name="collectDeptTree"></div></td>
	                    </tr>
	                    <tr>
	                    	<td>开始时间</td>
	                        <td>
	                            <div class="input-group w-p100">
	                                <input id="collectPlanStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#collectPlanStartTimeEnd lt">
	                                <div class="input-group-btn w30"> -</div>
	                                <input id="collectPlanStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#collectPlanStartTimeBegin gt">
	                            </div>
	                        </td>
	                        <td></td>
	                        <td id="planTypeHid"></td>
	                    </tr>
	                </tbody>
	            </table>
	            
	            <section class="form-btn m-b-lg">
	                <button id="queryCollect" name="queryCollect" class="btn dark query-jump" type="button">查 询</button>
	                <button id="resetCollect" name="resetCollect" class="btn" type="button">重 置</button>
	            </section>
	        </form>
	        </div>
			<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
			
	        <section class="tab-content">
	            <h2 class="panel-heading clearfix">查询结果<small class="m-l">
	            	<i class="fa fa-question-sign text-red m-r-xs"></i>本期总结指本周、本月、本年的工作总结；下期计划指下一周、下一月、下一年的工作计划</small>
	            </h2>
	            <div class="table-wrap">
	                <table id="collectListTable" class="table table-striped">
	                    <thead>
	                        <tr>
								<th style="width:20%">计划标题</th>
								<th class="w100">计划类型</th>
								<th class="w100">编制人</th>
								<th class="w115">编制部门</th>
								<th class="w115">开始时间</th>
								<th class="w115">结束时间</th>
								<th>本期总结及<br>实际完成百分比</th>
								<th>下期计划</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	                </table>
	            </div>
	        </section>
	    </div>
	</section>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/WorkflowHead.jsp"%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>