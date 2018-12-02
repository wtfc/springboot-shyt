<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/WorkflowHead.jsp"%>
<script src="${sysPath}/js/com/common/remind.js"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/plan/planAutoTable.js"></script>
<script src="${sysPath}/js/com/po/plan/monthPlanAnno.js"></script>
<script src="${sysPath}/js/com/po/plan/planFormEdit.js"></script>
<script src="${sysPath}/js/com/po/anno/anno.validate.js" type="text/javascript"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<form class="clearfix" id="planForm"> 
		<input type="hidden" id="piId" name="piId">
	    <input type="hidden" id="flowStatus" name="flowStatus">
		<input type="hidden" id="id" name="id">
	    <input type="hidden" id="token" name="token" value='${token}'>
	    <input type="hidden" id="modifyDate" name="modifyDate">
	    <input type="hidden" id="applyId" name="applyId">
	    <input type="hidden" id="applyDeptid" name="applyDeptid">
		<input type="hidden" id="templateType" name="templateType" value="0">
		<input type="hidden" id="planState" name="planState" value="1">
		<input type="hidden" id="planType" name="planType">
		<input type="hidden" id="yearId" name="yearId">
		<input type="hidden" id="monthId" name="monthId">
		<input type="hidden" id="dayId" name="dayId">
	 	<input type="hidden" id="planExtend" name="planExtend">
	 	<input type="hidden" id="currentUserId" name="currentUserId">
	 	<input type="hidden" id="applyUserId" name="applyUserId"><!-- 流程申请人ID 	-->
	 	<input type="hidden" id="currentWeek" name="currentWeek"><!-- 系统当前周 	-->
	 	<input type="hidden" id="diaryCondition" name="diaryCondition"><!-- 日程跳转隐藏域  -->
		<input type="hidden" id="currentSubmitYear" name="currentSubmitYear"><!-- 流程提交年  	-->
	 	<input type="hidden" id="currentSubmitMW" name="currentSubmitMW"><!-- 流程提交周/月 	-->
		<input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/> <!-- 当前登录人姓名  -->
		<input id="loginUserId" type="hidden" value="<shiro:principal property='id'/>"/> <!-- 当前登录人ID 	-->
		
		<section class="scrollable" id="scrollable">
		    <header class="con-header pull-in" id="header_1">
			    <div class="con-heading fl">
			       <h1>计划编制</h1>
			       <div class="crumbs">
			           <a href="#">首页</a><i></i><a href="#">个人办公</a><i></i><a href="#">工作日程</a><i></i>月计划上级批注
			       </div>
			    </div>
			</header>
		
	    	<section class="tree-fluid m-t-md">
		        <aside class="tree-wrap" id="LeftHeight_box" style="width:140px;">
		           <section class="panel" id="LeftHeight" style="width:140px;">
		               <div id="monthsSpanDiv" class="table-wrap notation-btn">
		                   <table id="monthsSpanTable" class="table first-td-tc">
		                       <thead id="header_2">
		                           <tr>
		                               <th>
		                               	   <a id="leftYear" href="#" class="fl"><i class="fa fa-chevron-left"></i></a>
		                               	      <span id="showYearSpan" name="showYearSpan"></span>
		                               	   <a id="rightYear" href="#" class="fr"><i class="fa fa-chevron-right"></i></a>
		                               </th>
		                           </tr>
		                       </thead>
		                       <tbody id="monthsTbody">
		                       </tbody>
		                   </table>
		               </div>
		           </section>
		        </aside>
	    	</section>
	        <section class="tree-right">
		   		<div id="planFormDiv">
		   			<%@ include file="/WEB-INF/web/po/plan/planAnnoForm.jsp"%>
		    	</div>
		    </section>
	    </section>
	</form>
</section>
<!--加入任务弹出层预留div位置-->
<div id="joinTaskFormEdit">
</div>
<script src="${sysPath}/js/com/workFlow/form/formPrivilege.js"></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/workTask/taskArrangement.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/workTask/taskArrangement.validate.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/WorkflowFoot.jsp"%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>