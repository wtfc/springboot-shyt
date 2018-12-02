<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/po/plan/planApp.js"></script>

<!-- 流程相关项 -->
<input type="hidden" name="entranceType" id="entranceType" value=${entranceType} >
<input type="hidden" name="entrance" id="entrance" value=${entrance}>
<!-- 多个流程对应一个表单 -->
<input type="hidden" name="flowId" id="flowId" value=${flowId}>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="navigationMenu"></header>
	
	<section class="panel clearfix search-box search-shrinkage">
	<div class="search-line hide">
	   <form id="planAppListForm" class="table-wrap form-table">
	       <table class="table table-td-striped">
	           <tbody>
	             <tr>
	                 <td class="w140">计划标题</td>
	                 <td><input id="planTitle" name="planTitle" type="text"></td>
	                 <td class="w140">计划类型</td>
	                 <td style="width:40%;">
	                     <label for='weekPlanType' class="checkbox inline">
	                         <input type="checkbox" id="weekPlanType" name="planType" value="0">周计划
	                     </label>
	                     <label for='monthPlanType' class="checkbox inline">
	                         <input type="checkbox" id="monthPlanType" name="planType" value="1">月计划
	                     </label>
	                     <label for='yearPlanType' class="checkbox inline">
	                         <input type="checkbox" id="yearPlanType" name="planType" value="2">年计划
	                     </label>
	                 </td>
	             </tr>	
	             <tr>
	                 <td>编制人</td>
	                 <td>
	                 	<div id="planAppUserTree" name="showUserTree"></div>
	                 </td>
	                 <td>编制部门</td>
	                 <td>
						 <div id="planAppDeptTree" name="planAppDeptTree"></div>
	                 </td>
	             </tr>
	             <tr>
	                 <td>开始时间</td>
	                 <td>
	                 	<div class="input-group w-p100">
	                      	<input id="planStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeEnd lt">
	                        <div class="input-group-btn w30">-</div>
	                        <input id="planStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeBegin gt">
	                  	</div>
	                 </td>
					 <td>审批状态</td>
					 <td>
						 <select name="processStatus" id="processStatus">
							<option value="1">待办</option>
							<option value="3">已办</option>
						 </select>
					 </td>
	             </tr>
	           </tbody>
	       </table>
	       <section class="form-btn m-b-lg">
	           <button id="planAppQueryBtn" name="planAppQueryBtn" class="btn dark query-jump" type="button">查 询</button>
	           <button id="planAppResetBtn" name="planAppResetBtn" class="btn" type="button">重 置</button>
	       </section>
	   </form>
   </div>
   <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
   </section>  
	
	<section class="panel clearfix">
	   <h2 class="panel-heading clearfix">查询结果</h2>
	   <div class="table-wrap">
	       <table id="planAppList" class="table table-striped" >
	           <thead>
	               <tr>
	                   <th style="width:200px;">计划标题</th>
	                   <th style="width:100px;">计划类型</th>
	                   <th style="width:100px;">审批状态</th>
	                   <th style="width:100px;">编制人</th>
	                   <th style="width:150px;">编制部门</th>
	                   <th style="width:120px;">开始时间</th>
	                   <th style="width:120px;">结束时间</th>
	                   <th class="w170">操作</th>
	               </tr>
	           </thead>
	           <tbody>
	           </tbody>
	       </table>
	   </div>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>