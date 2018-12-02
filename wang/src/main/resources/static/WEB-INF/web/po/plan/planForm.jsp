<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/WorkflowHead.jsp"%>
<script src="${sysPath}/js/com/po/plan/planAutoTable.js"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<!-- <script src="${sysPath}/js/com/po/plan/planFormDate.js"></script> -->
<script src="${sysPath}/js/com/po/plan/planForm.js"></script>
<script src="${sysPath}/js/com/po/plan/planFormEdit.js"></script>
<!-- <script src="${sysPath}/js/com/po/plan/planFormImport.js"></script> -->
<!-- <script src="${sysPath}/js/com/po/plan/planFormRowDetail.js"></script> -->
<!-- <script src="${sysPath}/js/com/po/plan/planAnno.js"></script> -->
<script src="${sysPath}/js/com/po/plan/plan.validate.js"></script>
<script src="${sysPath}/js/com/po/anno/anno.validate.js" type="text/javascript"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
<%@ include file="/WEB-INF/web/include/WorkflowSheetStart.jsp"%> 
<header class="con-header">
	<div id="planFormHeader" style="display:none;">
    <div class="con-heading fl" id="navigationMenu">
    	<h1>计划编制</h1>
	    <div class="crumbs"></div>
    </div>
    <div class="fr con-header-time btn-group" data-toggle="buttons">
       <label id="week" class="tabsTime btn active">
            <input type="radio" name="options" id="option1" style='display:none;'>
            <a id="leftWeek" href="#" onclick="week(-1);"><i class="fa fa-caret-left"></i></a>
            <span><a href="#" onclick="week(0);">周</a></span>
            <a id="rightWeek" href="#" onclick="week(1);"><i class="fa fa-caret-right"></i></a>
        </label>
        <label id="month" class="tabsTime btn">
            <input type="radio" name="options" id="option2" style='display:none;'>
            <a id="leftMonth" href="#" onclick="month(-1);"><i class="fa fa-caret-left"></i></a>
            <span><a href="#" onclick="month(0);">月</a></span>
            <a id="rightMonth" href="#" onclick="month(1);"><i class="fa fa-caret-right"></i></a>
        </label>
        <label id="year" class="tabsTime btn">
            <input type="radio" name="options" id="option3" style='display:none;'>
            <a id="leftYear" href="#" onclick="year(-1);"><i class="fa fa-caret-left"></i></a>
            <span><a href="#" onclick="year(0);">年</a></span>
            <a id="rightYear" href="#" onclick="year(1);"><i class="fa fa-caret-right"></i></a>
        </label>
        <div id="appYearLabel" class="fr con-header-time btn-group" data-toggle="buttons"><em>年</em></div>
        <div id="appMonthLabel" class="fr con-header-time btn-group" data-toggle="buttons"><em>月</em></div>
        <div id="appWeekLabel" class="fr con-header-time btn-group" data-toggle="buttons"><em>周</em></div>
    </div>
    </div>
</header>

<div id="planFormBox" style="display:none;">
<input type="hidden" id="plan" name="plan" value='${planJson}'/>
<input type="hidden" id="beforePlan" name="beforePlan" value='${beforePlanJson}'/>
<form class="clearfix" id="planForm" style="background-color:#f3f3f3;">
 	<!-- 流程相关 -->
	<!-- 提交完毕跳转 -->
    <input type="hidden" id="myBusinessUrl" value="/po/plan/myPlanQuery.action">
    <!-- 待办/已办跳转  -->
    <input type="hidden" id="todoPage" value="/po/plan/planApproval">
    <input type="hidden" id="flowStatus" name="flowStatus">
	<input type="hidden" id="id" name="id">
    <input type="hidden" id="token" name="token" value='${token}'>
    <input type="hidden" id="modifyDate" name="modifyDate">
    <input type="hidden" id="applyId" name="applyId" value='${loginUserId}'>
    <input type="hidden" id="applyDeptid" name="applyDeptid" value='${loginUserDeptId}'>
	<input type="hidden" id="templateType" name="templateType" value="0">
	<input type="hidden" id="planState" name="planState" value="1">
	<input type="hidden" id="planType" name="planType">
	<input type="hidden" id="yearId" name="yearId" value='${sysYear}'>
	<input type="hidden" id="monthId" name="monthId" value='${sysMonth}'>
	<input type="hidden" id="dayId" name="dayId" value='${sysDay}'>
 	<input type="hidden" id="planExtend" name="planExtend">
 	<input type="hidden" id="currentUserId" name="currentUserId">
 	<input type="hidden" id="applyUserId" name="applyUserId"><!-- 流程申请人ID 	-->
 	<input type="hidden" id="currentWeek" name="currentWeek" value='${currentWeek}'><!-- 系统当前周 	-->
 	<input type="hidden" id="diaryCondition" name="diaryCondition" value='${diaryCondition}'><!-- 日程跳转隐藏域  -->
	<input type="hidden" id="currentSubmitYear" name="currentSubmitYear"><!-- 流程提交年  	-->
 	<input type="hidden" id="currentSubmitMW" name="currentSubmitMW"><!-- 流程提交周/月 	-->
	<input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/> <!-- 当前登录人姓名  -->
	<input id="loginUserId" type="hidden" value="<shiro:principal property='id'/>"/> <!-- 当前登录人ID 	-->
 	<input type="hidden" id="weekPermission" name="weekPermission" value='${weekPermission}'><!-- 周工作计划编制权限隐藏域  -->
 	<input type="hidden" id="monthPermission" name="monthPermission" value='${monthPermission}'><!-- 月工作计划编制权限隐藏域  -->	
 	<input type="hidden" id="yearPermission" name="yearPermission" value='${yearPermission}'><!-- 年工作计划编制权限隐藏域  -->
 	<input type="hidden" id="yearPermission" name="yearPermission" value='${yearPermission}'><!-- 系统时间年 -->
 	<input type="hidden" id="yearPermission" name="yearPermission" value='${yearPermission}'><!-- 系统时间月 -->
 	<input type="hidden" id="yearPermission" name="yearPermission" value='${yearPermission}'><!-- 系统时间日 -->		
 	
	<section class="row-fluid m-t-md" id="planTheme">
        <div class="span4 cbox bg-white">
        <div class="cbox-head">标题</div>
            <div class="cbox-con" workFlowForm="textinput" itemName="planTitle">
                <span class="input-style">
                    <input type="text" id="planTitle" name="planTitle">	
                </span>
            </div>
        </div>
        <div class="span2 cbox bg-white">
            <div class="cbox-head">开始时间</div>
            <div class="cbox-con" workFlowForm="textinput" itemName="planStartTime">
                <span class="input-style">
<!--                     <input class="datepicker-input" data-date-format="yyyy-MM-dd" type="text" id="planStartTime" name="planStartTime" data-ref-obj="#planEndTime lt"> -->
					<input type="text" id="planStartTime" name="planStartTime" readonly="readonly">
                </span>
            </div>
        </div>
        <div class="span2 cbox bg-white">
            <div class="cbox-head">结束时间</div>
            <div class="cbox-con" workFlowForm="textinput" itemName="planEndTime">
                <span class="input-style">
<!--                     <input class="datepicker-input" data-date-format="yyyy-MM-dd" type="text" id="planEndTime" name="planEndTime" data-ref-obj="#planStartTime gt"> -->
					<input type="text" id="planEndTime" name="planEndTime" readonly="readonly">
                </span>
            </div>
        </div>
        <div class="span2 cbox bg-white">
            <div class="cbox-head">编制人</div>
            <div class="cbox-con" workFlowForm="textinput" itemName="applyIdValue">
                <span class="input-style">
                    <input type="text" id="applyIdValue" name="applyIdValue" value='${loginUserName}' readonly="readonly">
                </span>
            </div>
        </div>
        <div class="span2 cbox bg-white">
            <div class="cbox-head">编制部门</div>
            <div class="cbox-con" workFlowForm="textinput" itemName="applyDeptidValue">
                <span class="input-style">
                    <input type="text" id="applyDeptidValue" name="applyDeptidValue" value='${loginUserDeptName}' readonly="readonly">
                </span>
            </div>
        </div>
	</section>

    <section class="tabs-wrap m-t-md">
        <ul class="nav nav-tabs">
            <li id="jyPlanId" class="active">
                <a href="#" class="planning-jy abtn">简易模板</a>
            </li>
            <li id="bzPlanId" class="">
                <a href="#" class="planning-bz abtn">标准模板</a>
            </li>
        </ul>
    </section>

    <section class="m-b-none panel">
        <div class="panel-heading clearfix">
            <h2>
            	<span workFlowForm="textinput" itemName="sumSubmitYear"><span id="sumSpan" class="input-style" ><input type="text" id="sumSubmitYear" name="sumSubmitYear"></span></span>
            	<span><span id="dateNowYear"></span></span>
            	<span workFlowForm="textinput" itemName="sumSubmitMW"><span class="input-style" ><input type="text" id="sumSubmitMW" name="sumSubmitMW" ></span></span>
            	<span><span id="dateNow"></span></span>
            </h2>
            <div class="fr heading-btn">
                <a workFlowForm="button" itemName="importSum" id="importSum" name="importSum" class="btn" href="#" class="btn" role="button" >导入上期计划</a>
            </div>
        </div>
        <div class="table-wrap form-table-h input-textarea plan-table">
            <table id="preSum" workFlowForm="autoRow" itemName="autoSumTable" class="table table-striped first-td-tc"> 
                <thead>
                    <tr>
                        <th class="w46">序号</th>
                        <th style="width:14%;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>主要完成事项</th>
                        <th style="width:14%;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>完成标准</th>
                        <th style="width:13%;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>负责人</th>
                        <th style="width:120px;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>开始时间</th>
                        <th style="width:120px;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>完成时间</th>
                        <th style="width:100px;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>比例(%)</th>
                        <th>未完成原因说明</th>
                        <th operate="true" style="width:80px;">操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <section class="table-wrap form-table">
            <table class="table table-td-striped first-td-tc">
                <tbody>
                    <tr>
                        <td class="w140">备注</td>
                        <td><textarea rows="3" workFlowForm="textinput" itemName="remark" id="remark" name="remark" value="${plan.remark}"></textarea></td>
                    </tr>
                </tbody>
	        </table>
        </section>
        <div class="table-wrap form-table planning-jyTbz hide">
            <table class="table table-td-striped first-td-tc">
                <tbody>
                    <tr>
                        <td class="w170">管理及创新</td>
                        <td><textarea rows="3" workFlowForm="textinput" itemName="manaInno" id="manaInno" name="manaInno" value="${plan.manaInno}"></textarea></td>
                    </tr>
                    <tr>
                        <td>成本控制及节约</td>
                        <td><textarea rows="3" workFlowForm="textinput" itemName="costControl" id="costControl" name="costControl" value="${plan.costControl}"></textarea></td>
                    </tr>
                    <tr>
                        <td>培训总结</td>
                        <td><textarea rows="3" workFlowForm="textinput" itemName="trainingSum" id="trainingSum" name="trainingSum" value="${plan.trainingSum}"></textarea></td>
                    </tr>
                    <tr>
                        <td>存在问题及解决措施</td>
                        <td><textarea rows="3" workFlowForm="textinput" itemName="problemMeas" id="problemMeas" name="problemMeas" value="${plan.problemMeas}"></textarea></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="panel-heading clearfix">
        	<h2>
        		<span workFlowForm="textinput" itemName="planSubmitYear"><span id="planSpan" class="input-style" ><input type="text" id="planSubmitYear" name="planSubmitYear" value="${plan.planSubmitYear}"></span></span>
        		<span><span id="dateNextYear"></span></span>
        		<span workFlowForm="textinput" itemName="planSubmitMW"><span class="input-style"><input type="text" id="planSubmitMW" name="planSubmitMW" value="${plan.planSubmitMW}"></span></span>
        		<span><span id="dateNext"></span></span>
        	</h2>
            <div class="fr heading-btn">
                <a workFlowForm="button" itemName="showPlanQueryBox" id="showPlanQueryBox" name="showPlanQueryBox" href="#" class="btn" role="button" data-toggle="modal">查看计划</a>
                <a workFlowForm="button" itemName="showTaskQueryBox" id="showTaskQueryBox" name="showTaskQueryBox" href="#" class="btn" role="button" data-toggle="modal">查看任务</a>
            </div>
        </div>
        <div class="table-wrap form-table-h input-textarea">
            <table id="prePlan" workFlowForm="autoRow" itemName="autoPlanTable" class="table table-striped first-td-tc">
                <thead>
                    <tr>
                        <th class="w46">序号</th>
                        <th style="width:16%;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>主要完成事项</th>
                        <th style="width:16%;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>完成标准</th>
                        <th><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>负责人</th>
                        <th style="width:120px;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>开始时间</th>
                        <th style="width:120px;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>完成时间</th>
                        <th style="width:100px;"><span workFlowForm="button" itemName="requiredSpan" class="required">*</span>比例(%)</th>
                        <th class="w170">操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div>
        	<span workFlowForm="container" itemName="leaderContent"><h2 class="panel-heading clearfix">领导审批</h2></span>
        </div>
        <div class="table-wrap">
        	<workflow:suggest itemId="leaderContent" showLast="false" order="createTime" classStr="" style=""/>
        </div>
    	</section>
	</form>
</div>

<!-- 领导批注 start -->
<div id="leaderIdeaDiv" name="leaderIdeaDiv" style="display:none;">
	<section class="panel">
		<h4 class="modal-heading clearfix">领导批注</h4>
	    <form id="leaderIdeaReplayForm"  class="table-wrap form-table">
            <ul class="dialog m-r" id="comment"></ul>
        </form> 
 		<form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table" style="padding-right:15px;">   
            <table class="table table-td-striped">
                <tbody>
                 	<tr>
                    <td class="w140">批注内容</td>
                    <td colspan="4">
                        <div class="input-group w-p100">
                            <div>
                                 <textarea id="leaderIdeaContent" name="content" rows="3"></textarea>
                            </div>
                            <div class="input-group-btn icon p-l">
                                <a id="leaderIdea" id="leaderIdea" href="#" class="a-icon i-new m-b-xs fr">保 存</a>
                                <a class="a-icon i-trash fr m-b-xs" href="#" id="clearleaderIdea">清 空</a>
                            </div>
                        </div>
                    </td>
                </tr>   
                </tbody>
            </table>
        </form> 
	</section>
</div>
<!-- 领导批注 end -->
<%@ include file="/WEB-INF/web/include/WorkflowButton.jsp"%>
<%@ include file="/WEB-INF/web/include/WorkflowSheetEnd.jsp"%>
<%@ include file="/WEB-INF/web/include/WorkflowPostscript.jsp"%>
</section>

<!--加入日程弹出层Start-->
<div id="showDiaryBox" name="showDiaryBox" class="modal fade panel" aria-hidden="false">
	<div class="modal-dialog w900">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">加入日程详细</h4>
			</div>
			<div class="modal-body">
				<div class="table-wrap form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:20%;">日程所有人</td>
								<td id="diaryUser"></td>
							</tr>
							<tr>
								<td style="width:20%;">日程标题</td>
								<td id="diaryTitle"></td>
							</tr>
							<tr>
								<td style="width:20%;">起始时间</td>
								<td id="diaryStartEndTime"></td>
							</tr>
							<tr>
								<td>日程内容</td>
								<td id="diaryContent"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="toModify">保 存</a>
				<button class="btn" type="button" data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>
<!--加入日程弹出层 End-->

<!--计划弹出层预留div位置-->
<div id="planFormEdit">
</div>
<!--任务弹出层预留div位置-->
<div id="taskFormEdit">
</div>
<!--加入任务弹出层预留div位置-->
<div id="joinTaskFormEdit">
</div>


<%@ include file="/WEB-INF/web/include/WorkflowFoot.jsp"%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>