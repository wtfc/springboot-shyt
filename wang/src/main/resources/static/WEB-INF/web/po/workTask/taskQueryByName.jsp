<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="jcGOA">
<head>
	<%@ include file="/WEB-INF/web/include/head.jsp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
	<script src="${sysPath}/js/com/po/anno/anno.manage.js" type="text/javascript"></script>
	<script src="${sysPath}/js/com/po/anno/anno.validate.js" type="text/javascript"></script>
	<script src="${sysPath}/js/com/po/workTask/taskQueryByName.validate.js" type="text/javascript"></script>
	<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
</head>
<body>
  <section class="scrollable padder jcGOA-section" id="scrollable">
     <header class="con-header pull-in">
     	<h1>我的任务</h1>
		<div class="crumbs">
			<a href="#" onclick="homeloadmenu()">首页</a><i></i>个人办公<i></i>督办协办<i></i>我的任务
		</div>
     </header>
     <!-- tabs -->
     <section class="tabs-wrap m-t-md">
         <!-- 同时添加 tabs-wrap-in 类-->
         <ul class="nav nav-tabs">
             <li class="active">
                 <a href="#messages1" data-toggle="tab">任务详细</a>
             </li>
             <!-- 根据a标签href属性相对应的id，切换tab-pane -->
             <li>
                 <a href="#messages2"  data-toggle="tab" onclick="queryTaskByName.queryTaskEvent();"  id="taskEventButton">任务事件</a>
             </li>
         </ul>
     </section>
     <!-- tabs end -->
     <section class="panel tab-content">
     		<%--领导批注权限限制 --%>
     		<input type="hidden" id="isLeader" name="isLeader" value="${isLeader}"/>
     		<%--领导批注权限限制 --%>
     		<input type="hidden" id="token" name="token" value="${token}" />
     		<input type="hidden" id="queryTaskName" name="queryTaskName" value="${queryTaskName}" />
     		<input type="hidden" id="querySponsorId" name="querySponsorId" value="${querySponsorId}" />
     		<input type="hidden" id="queryDirectorId" name="queryDirectorId" value="${queryDirectorId}" />
     		<input type="hidden" id="queryStartTime" name="queryStartTime" value="${queryStartTime}" />
     		<input type="hidden" id="queryEndTime" name="queryEndTime" value="${queryEndTime}" />
     		<input type="hidden" id="queryInfoStatus" name="queryInfoStatus" value="${queryInfoStatus}" />
     		<input type="hidden" id="prticipants" name="prticipants" value="${workTask.prticipants}" />
     		<input type="hidden" id="director" name="director" value="${workTask.director}" />
     		<input type="hidden" id="taskName" name="taskName" value="${workTask.taskName}" />
	        <div class="tab-pane fade active in" id="messages1">
	         	 <form class="table-wrap form-table" id="updateworkTaskForm">
	         	 	<input type="hidden" id="modifyDate" name="modifyDate">
	         	 	<input type="hidden" id="tTaskName" name="taskName" value="${workTask.taskName}"/>
	             	<input type="hidden" id="tContent" name="content" value="${workTask.content}"/>
	         	 </form>
	             <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
	             <form class="table-wrap form-table" id="workTaskForm">
	             	<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
	             	<input type="hidden" id="userId" name="userId" value="${userId}"/>
	             	<input type="hidden" id="displayName" name="displayName" value="${displayName}"/>
	             	<input type="hidden" id="status" name="status"/>
	             	<input type="hidden" id="taskSponsorId" name="taskSponsorId" value="${workTask.sponsorId}"/>
	             	<input type="hidden" id="taskDirectorId" name="taskDirectorId" value="${workTask.directorId}"/>
	             	<input type="hidden" id="taskStatus" name="taskStatus" value="${workTask.status}"/>
	             	<input type="hidden" id="childSize" name="childSize" value="${childSize}"/>
	             	<input type="hidden" id="delayStatus" name="delayStatus" value="${workTask.delayStatus}"/>
	                 <table class="table table-td-striped">
	                     <tbody>
	                         <tr>
	                             <td>任务名称</td>
	                             <td colspan="3">
	                                 ${workTask.taskName}
	                             </td >
	                         </tr>
	                         <tr>
	                             <td>上级任务名称</td>
	                             <td colspan="3">
	                             	 ${workTask.parentTaskName}
	                             </td>
	                         </tr>
	                         <tr>
	                         	 <td class="w170">上级任务重要程度</td>
	                             <td>
	                             	${workTask.parentTaskImpName}
	                             </td>
	                             <td style="width:15%;">任务重要程度</td>
	                             <td>
	                             	${workTask.taskImpName}
	                             </td>
	                         </tr>
	                         <tr>
	                             <td>任务类型</td>
	                             <td colspan="3">
	                             	${workTask.taskTypeName}
	                             </td>
	                             <%--
	                             <td>任务来源</td>
	                             <td>
	                             	${workTask.taskOriName}
	                             </td>
	                              --%>
	                         </tr>
	                         <tr> 
	                         	 <td>发起人</td>
	                             <td>
	                             	${workTask.sponsor}
	                             </td>
	                             <td >负责人</td>
	                             <td>
									${workTask.director}
								 </td>
	                         </tr>
	                         <tr>
	                         	 <td>参与人</td>
	                             <td colspan="3">
	                             	${workTask.prticipants}
	                             </td>
	                         </tr>
	                         <tr>
	                             <td>计划开始时间</td>
	                             <td>
	                             	<fmt:formatDate value="${workTask.startTime}" pattern="yyyy-MM-dd"/>
	                             </td>
	                             <td>计划结束时间</td>
	                             <td>
	                           		  <fmt:formatDate value="${workTask.endTime}" pattern="yyyy-MM-dd"/>
	                             </td>
	                         </tr>
	                         <tr>
	                         	<td>实际开始时间</td>
	                             <td>
	                             	 <fmt:formatDate value="${workTask.actStartTime}" pattern="yyyy-MM-dd"/>
	                             </td>
	                             <td>实际结束时间</td>
	                             <td>
	                            	 <fmt:formatDate value="${workTask.actEndTime}" pattern="yyyy-MM-dd"/>
	                             </td>
	                         </tr>
	                         <tr>
	                             <td>任务内容</td>
	                             <td colspan="3">
	                             	<input type="hidden" id="hiContent" name="content" value="${workTask.content}"/>
                              	    <div id="showContent"></div>
	                             </td>
	                         </tr>
	                         <tr>
	                             <td>完成标准</td>
	                             <td colspan="3">
		                            <input type="hidden" id="hiStandard" name="standard" value="${workTask.standard}"/>
	                                <div id="showStandard"></div>
	                             </td>
	                         </tr>
	                         <tr>
	                         	 <td>加入日程</td>
	                             <td>
	                             	<c:choose>
	                             		<c:when test="${workTask.isImportDiary eq '1'}">是</c:when>
	                             		<c:otherwise>否</c:otherwise>
	                             	</c:choose>
	                             </td>
	                             <td>超期提醒</td>
	                             <td>
	                             	<c:choose>
	                             		<c:when test="${workTask.remindType eq '2'}">邮件</c:when>
	                             		<c:otherwise>不提醒</c:otherwise>
	                             	</c:choose>
	                             </td>
	                         </tr>
	                          <tr>
	                          	 <td>汇报时限(天)</td>
	                             <td>
				                    ${workTask.reportDay}
	                             </td>
	                          	 <td>提醒</td>
		                         <td>
		                        	<c:choose>
		                        		<c:when test="${workTask.reportType eq '1'}">是</c:when>
		                        		<c:otherwise>否</c:otherwise>
		                        	</c:choose>
		                         </td>		                            
	                         </tr>
	                         <tr>
	                         	 <td>附件</td>
	                             <td colspan="3">
	                             	<ul id="attachList"></ul>
	                             </td>
	                         </tr>
	                     </tbody>
	                 </table>
	             </form>
	             <section>
	                 <h2 class="panel-heading clearfix">汇报详细</h2>
	                 <div class="table-wrap">
	                     <table class="table table-striped tab_height first-td-tc"  id="workTaskTable">
	                         <thead>
	                             <tr>
	                                 <th class="w46">序号</th>
	                                 <th class="w90">汇报人</th>
	                                 <th class="w170">汇报时间</th>
	                                 <th class="w100">任务进度</th>
	                                 <th>汇报内容</th>
	                             </tr>
	                         </thead>
	                         <tbody>
	                         </tbody>
	                     </table>
	                 </div>
	             </section>
	             <!--领导批注 start -->
	             <h2 class="modal-heading clearfix">领导批注</h2>
		            <form id="leaderIdeaReplayForm"  class="table-wrap form-table">
		             	<ul class="dialog" id="comment">
						</ul>
		            </form>
					<c:if test="${isLeader eq true}">
						<form id="leaderIdeaForm"  class="table-wrap form-table"> 
				            <table class="table table-td-striped">
				                <tbody>
				                 	<tr>
				                    <td>批注内容</td>
				                    <td colspan="3">
				                        <div class="input-group w-p100">
				                            <div>
				                            	 <input type="hidden" id="byAnnoUserId" name="byAnnoUserId" value="${workTask.directorId}"/>
				                            	 <input type="hidden" id="annoCreateUserDept" name="annoCreateUserDept" value="${workTask.createUserDept}"/>
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
					</c:if>
		           <!--领导批注 end-->
	             <section class="form-btn m-b-lg m-l">
	             	 <a id="receTask" class="btn dark" href="#" onclick="queryTaskByName.getReceTask(${taskId})" role="button" data-toggle="modal" style="display: none">接收任务</a>
	             	 <a id="notReceTask" class="btn" href="#" onclick="queryTaskByName.getNotRrce(${taskId})" role="button" data-toggle="modal" style="display: none">不接收任务</a>	
	                 <a id="remindersTask" class="btn dark" href="#" onclick="queryTaskByName.getReminders(${taskId})" style="display: none">任务催办</a>
	                 <a id="reportTask" class="btn dark" href="#" onclick="queryTaskByName.getReport(${taskId})" role="button" data-toggle="modal" style="display: none">任务汇报</a>
	                 <a id="reportTaskNew" class="btn" href="#" onclick="queryTaskByName.getReport(${taskId})" role="button" data-toggle="modal" style="display: none">任务汇报</a>
	                 <a id="extensionTaskCheck" class="btn" href="#" onclick="queryTaskByName.getDelayCheckTask(${taskId})" role="button" data-toggle="modal" style="display: none">延期审批</a>
	                 <a id="extensionTaskApply" class="btn" href="#" onclick="queryTaskByName.getDelayTask(${taskId})" role="button" data-toggle="modal" style="display: none">延期申请</a>
	                 <a id="extensionTaskApplyNew" class="btn dark" href="#" onclick="queryTaskByName.getDelayTask(${taskId})" role="button" data-toggle="modal" style="display: none">延期申请</a>
	                 <a id="extensionTaskCancel" class="btn" href="#" onclick="queryTaskByName.getDelayCancelTask(${taskId})" role="button" data-toggle="modal" style="display: none">延期取消</a>
	                 <a id="extensionTaskCancelNew" class="btn dark" href="#" onclick="queryTaskByName.getDelayCancelTask(${taskId})" role="button" data-toggle="modal" style="display: none">延期取消</a>
	                 <a id="cancelTask" class="btn" href="#" onclick="queryTaskByName.getCancel(${taskId})" style="display: none">取消任务</a>
	                <%--暂时注销掉 <a id="returnTask" class="btn" href="#"  onclick="queryTaskByName.returnWorkList();">返回</a> --%>
	                 <a id="returnTemp" class="btn dark" href="#"  onclick="queryTaskByName.returnWorkList();" style="display: none">返回</a>
	             </section>
	         </div>
         <!-- 任务事件 -->
         <div class="tab-pane fade" id="messages2">
             <div class="table-wrap">
                 <table class="table table-striped tab_height" id="taskEventTable">
                     <thead>
                         <tr>
                             <th class="w90">操作人</th>
                             <th class="w170">操作时间</th>
                             <th class="w140">操作事件类型</th>  
                             <th>任务内容</th>
                             <th>事件内容</th>
                         </tr>
                     </thead>
                     <tbody>
                     </tbody>
                 </table>
             </div>
         </div>
     </section>
   </section>  
    <!--延期申请弹窗-->
    <div class="modal fade panel" id="delayApply" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">延期申请</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="delayForm">
                    	<input type="hidden" id="delayTaskId" name="taskId" />
                    	<input type="hidden" id="delayContent" name="content" />
                        <input type="hidden" id="delayTaskEventType" name="taskEventType" value="12" />
                        <input type="hidden" id="delayTaskEventTitle" name="taskEventTitle" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td>计划结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <%--<input  type="text" readonly="readonly"  data-date-format="yyyy-MM-dd" id="delayTtartTime"  name="startTime" data-ref-obj="#delayEndTime lt"/>
                                            <div class="input-group-btn w30">-</div> --%>
                                            <input  type="text" readonly="readonly"  data-date-format="yyyy-MM-dd" id="delayEndTime"  name="endTime" data-ref-obj="#delayTtartTime gt"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>延期天数(天)</td>
                                    <td>
                                        <input type="text" class="input-mini m-l-none" id="delayDays" name="delayDays" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>到期时间</td>
                                    <td>
                                        <input type="text" class="input-mini2 m-l-none"  readonly="readonly" id="delayEnddate" name="delayEnddate" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>延期理由</td>
                                    <td>
                                        <textarea id="delayReasons" name="delayReason"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="delaySure">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--延期审批弹窗-->
    <div class="modal fade panel" id="delay" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">延期审批</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="delayCheckForm">
                    	<input type="hidden" id="delayCheckTaskId" name="taskId" />
                    	<input type="hidden" id="delayCheckContent" name="content" />
                        <input type="hidden" id="delayCheckTaskEventType" name="taskEventType" />
                        <input type="hidden" id="delayCheckTaskEventTitle" name="taskEventTitle" />
                        <table class="table table-td-striped">
                            <tbody>
                                 <tr>
                                    <td>计划结束时间</td>
                                    <td>
                                         <input  type="text" readonly="readonly"  data-date-format="yyyy-MM-dd" id="delayCheckEndTime"  name="endTime"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>延期时间</td>
                                    <td>
                                        <input type="text" class="input-mini2 m-l-none"  readonly="readonly" id="delayCheckEnddate" name="delayEnddate" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>延期理由</td>
                                    <td>
                                        <textarea id="delayCheckReasons" name="delayReason"  readonly="readonly"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="delayPass">通 过</button>
                    <button class="btn" type="button" id="delayNotPass">不通过</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--延期取消弹窗-->
    <div class="modal fade panel" id="delayCancel" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">延期取消</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="delayCancelForm">
                    	<input type="hidden" id="delayCancelTaskId" name="taskId" />
                    	<input type="hidden" id="delayCancelContent" name="content" />
                        <input type="hidden" id="delayCancelTaskEventType" name="taskEventType" value="14" />
                        <input type="hidden" id="delayCancelTaskEventTitle" name="taskEventTitle" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td>计划结束时间</td>
                                    <td>
                                         <input  type="text" readonly="readonly"  data-date-format="yyyy-MM-dd" id="delayCancelEndTime"  name="endTime"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>延期时间</td>
                                    <td>
                                        <input type="text" class="input-mini2 m-l-none"  readonly="readonly" id="delayCancelEnddate" name="delayEnddate"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>延期理由</td>
                                    <td>
                                        <textarea id="delayCancelReasons" name="delayReason"  readonly="readonly"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="delayCanSure">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
	<!--汇报弹窗-->
    <div class="modal fade panel" id="reportWindow" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">汇报</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="reportForm">
                    	<input type="hidden" id="reportTaskId" name="taskId" />
                    	<input type="hidden" id="reEventTitle" name="taskEventTitle" />
                    	<input type="hidden" id="reContent" name="content" />
                        <input type="hidden" id="reportTaskEventType" name="taskEventType" value="5" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td style="width:30%;"><span class="required">*</span>任务进度(%)</td>
                                    <td><input type="text" class="input-mini m-l-none" id="reportProc" name="reportProc" /></td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>汇报内容</td>
                                    <td><textarea id="reportContent" name="reportContent"></textarea></td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="reportSure">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--催办弹窗-->
    <div class="modal fade panel" id="reminders" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">催办</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="remindersForm">
                        <input type="hidden" id="remindersTaskId" name="taskId" />
                        <input type="hidden" id="remindEventTitle" name="taskEventTitle" />
                        <input type="hidden" id="remindContent" name="content" />
                        <input type="hidden" id="remindersTaskEventType" name="taskEventType" value="6" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td style="width:30%;">催办方式</td>
                                    <td>
                                        <label class="radio inline">
                                            <input type="radio" name="remindType" checked="checked"  value="2"/>邮件</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:30%;"><span class="required">*</span>催办内容</td>
                                    <td>
                                        <textarea id="remindersCo" name="delayReason"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="remindersSure">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--取消弹窗-->
    <div class="modal fade panel" id="cancel" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">取消</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="cancelForm">
                        <input type="hidden" id="cancelTaskId" name="taskId" />
                        <input type="hidden" id="cancelEventTitle" name="taskEventTitle" />
                        <input type="hidden" id="cancelContent" name="content" />
                        <input type="hidden" id="cancelTaskEventType" name="taskEventType" value="8" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td>提醒方式</td>
                                    <td>
                                        <label class="radio inline">
                                            <input type="radio" name="remindType" checked="checked"  value="2"/>邮件</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>取消原因</td>
                                    <td>
                                        <textarea id="cancelReason" name="delayReason"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="cancelSure">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--接收任务弹窗 -->
    <div class="modal fade panel" id="recrivingTask" aria-hidden="false">
        <div class="modal-dialog w900">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">接收任务</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="receWorkTaskForm">
                    	<input type="hidden" id="taskId2" name="taskId" />
                        <input type="hidden" id="taskEventType" name="taskEventType" value="3"/>
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td>任务名称</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="reTaskName" name="taskName" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                	<td>任务类型</td>
                                    <td>
                                    	<input type="text" id="taskTypeName" name="taskTypeName" readonly="readonly" />
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <%--
                                    <td>任务来源</td>
                                    <td>
                                        <input type="text" id="taskOriName" name="taskOriName" readonly="readonly" />
                                    </td>
                                     --%>
                                </tr>
                                <tr>
                                	<td>上级任务</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="reParentTaskName" name="parentTaskName" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:18%;">发起人</td>
                                    <td style="width:34%;">
                                    	<input type="text" id="reSponsor" name="sponsor" readonly="readonly"/>
                                        <%--
                                        <div class="input-group">
                                        	<input type="text" id="reSponsor" name="sponsor" readonly="readonly"/>
                                        </div>
                                         --%>
                                    </td>
                                    <td style="width:18%;">负责人</td>
                                    <td>
                                    	<input type="text" id="reDirector" name="director" readonly="readonly"/>
                                        <%--
                                        <div class="input-group">
                                        	<input type="text" id="reDirector" name="director" readonly="readonly"/>
                                        </div>
                                         --%>
                                    </td>
                                </tr>
                                <tr>
                                    <td>参与人</td>
                                    <td colspan="3">
                                        <div class="btn-in-area">
                                        	<textarea rows="3" id="rePrticipants" name="prticipants" readonly="readonly"></textarea>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>任务内容</td>
                                    <td colspan="3">
                                        <textarea rows="3" id="receContent" name="content" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>完成标准</td>
                                    <td colspan="3">
                                        <textarea rows="3" id="reStandard" name="standard" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>计划开始时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="reStartTime" name="startTime" disabled="disabled" />
                                        </div>
                                    </td>
                                    <td>计划结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="reEndTime" name="endTime" disabled="disabled"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                	<td><span class="required">*</span>实际开始时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                       		 <input type="hidden" id="nowDate" name="nowDate"/>
                                             <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="reActStartTime" name="actStartTime" data-ref-obj="#nowDate lt" />
                                        </div>
                                    </td>
                                    <td>实际结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="reActEndTime" name="actEndTime" disabled="disabled"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
			                    	<td>汇报时限(天)</td>
			                        <td>
				                       <input type="text" class="input-mini m-l-none" id="reportDay" name="reportDay" readonly="readonly"/>
			                        </td>
			                        <td>完成提醒</td>
			                        <td>
			                        	<label class="radio inline">
			                                <input type="radio" id="isRemind" name="reportType" value="0" disabled="disabled"/> 否
			                            </label>
			                            <label class="radio inline m-l-md">
			                                <input type="radio" id="notRemind" name="reportType" value="1" disabled="disabled"/> 是
			                            </label> 
			                        </td>
			                    </tr>
                                <tr>
                                    <td>加入日程</td>
                                    <td>
                                        <label class="radio inline">
			                                <input type="radio" id="isImport" name="isImportDiary" value="0" checked="checked" disabled="disabled"/> 否
			                            </label>
			                            <label class="radio inline m-l-md">
			                                <input type="radio" id="notImport" name="isImportDiary" value="1" disabled="disabled"/> 是
			                            </label> 
                                    </td>
                                    <td>附件</td>
                                    <td>
                                    	<ul id="attachList_0"></ul>
                                    </td>
                                </tr>
                                <tr>
                                   <td>超期提醒</td>
                                    <td colspan="3">
                                        <label class="radio inline">
                                            <input type="radio" name="remindType" value="0"  disabled="disabled" /> 不提醒
                                        </label>
                                        <label class="radio inline m-l-md">
                                            <input type="radio" name="remindType" value="2"  disabled="disabled"/> 邮件
                                        </label>
                                    </td>                          
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="receWorkTask">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--不接收任务弹窗-->
    <div class="modal fade panel" id="no_task" aria-hidden="false">
        <div class="modal-dialog w900">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">不接收任务</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="notRrce">
                    	<input type="hidden" id="noReTaskId" name="taskId" >
                        <input type="hidden" id="noReEventTitle" name="taskEventTitle" >
                       <%-- <input type="hidden" id="noReContent" name="content" > --%>
                        <input type="hidden" id="noReTaskEventType" name="taskEventType" value="4">
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td>任务名称</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="noReTaskName" name="taskName" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                	<td>任务类型</td>
                                    <td>
                                    	<input type="text" id="taskTypeName" name="taskTypeName" readonly="readonly" />
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <%--
                                    <td>任务来源</td>
                                    <td>
                                        <input type="text" id="noTaskOriName" name="taskOriName" readonly="readonly"/>
                                    </td>
                                     --%>
                                </tr>
                                <tr>
                                	<td>上级任务</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="noReParentTaskName" name="parentTaskName" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:18%;">发起人</td>
                                    <td style="width:34%;">
                                    	<input type="text" id="noReSponsor" name="sponsor" readonly="readonly"/>
                                        <%--
                                        <div class="input-group">
                                        	<input type="text" id="noReSponsor" name="sponsor" readonly="readonly"/>
                                        </div>
                                         --%>
                                    </td>
                                    <td style="width:18%;">负责人</td>
                                    <td>
                                    	<input type="text" id="noReDirector" name="director" readonly="readonly"/>
                                        <%--
                                        <div class="input-group">
                                        	<input type="text" id="noReDirector" name="director" readonly="readonly"/>
                                        </div>
                                         --%>
                                    </td>
                                </tr>
                                <tr>
                                    <td>参与人</td>
                                    <td colspan="3">
                                        <div class="btn-in-area">
                                        	<textarea rows="3" id="noRePrticipants" name="prticipants" readonly="readonly"></textarea>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>任务内容</td>
                                    <td colspan="3">
                                        <textarea rows="3" id="noReceContent" name="content" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>完成标准</td>
                                    <td colspan="3">
                                        <textarea rows="3" id="noReStandard" name="standard" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>开始时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="noReStartTime" name="startTime" disabled="disabled" />
                                        </div>
                                    </td>
                                    <td>结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="noReEndTime" name="endTime" disabled="disabled"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                	<td>汇报时限(天)</td>
			                        <td>
				                       <input type="text" class="input-mini m-l-none" id="unReportDay" name="reportDay" readonly="readonly"/>
			                        </td>
			                        <td>完成提醒</td>
			                        <td>
			                        	<label class="radio inline">
			                                <input type="radio" id="unIsRemind" name="reportType" value="0" disabled="disabled"/> 否
			                            </label>
			                            <label class="radio inline m-l-md">
			                                <input type="radio" id="unNotRemind" name="reportType" value="1" disabled="disabled"/> 是
			                            </label> 
			                        </td>
                                </tr>
                                <tr>
                                    <td>加入日程</td>
                                    <td>
                                        <label class="radio inline">
			                                <input type="radio" id="isImport" name="isImportDiary" value="0" checked="checked" disabled="disabled"/> 否
			                            </label>
			                            <label class="radio inline m-l-md">
			                                <input type="radio" id="notImport" name="isImportDiary" value="1" disabled="disabled"/> 是
			                            </label> 
                                    </td>
                                    <td>附件</td>
                                    <td>
                                   		<ul id="attachList_1"></ul>
                                    </td>
                                </tr>
                                <tr>
                                   <td>超期提醒</td>
                                    <td colspan="3">
                                        <label class="radio inline">
                                            <input type="radio" name="remindType" value="0"  disabled="disabled"/> 不提醒
                                        </label>
                                        <label class="radio inline m-l-md">
                                            <input type="radio" name="remindType" value="2"  disabled="disabled"/> 邮件
                                        </label>
                                    </td>                          
                                </tr>
                                <tr>
                                    <td style="width:15%;"><span class="required">*</span>不接收原因</td>
                                    <td colspan="3">
                                        <textarea id="noReTaskReason" name="delayReason"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="notAccepted">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<!--设置预览 -->
    <div class="modal fade panel" id="remindWindow" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">设置预览</h4>
                </div>
                <div class="modal-body dis-table">
                    <table class="table table-td-striped form-table table-bordered b-light">
                       <tbody>
                        <tr>
                       		<td>被提醒人</td>
                       		<td>
                               	<textarea id="remindPerName" name="remindPerName" readonly="readonly"></textarea>
                             </td>
                   		</tr>
                   		<tr>
                       		<td>提醒方式</td>
                       		<td>                           		
                           		<input type="text" id="optionsRadios2" name="optionsRadios2" readonly="readonly"/>
                             </td>
                   		</tr>
                   		<tr> 
                       		<td>提醒内容</td>
                       		<td><textarea id="remindCon" name="remindContent" readonly="readonly"></textarea></td>
                   		</tr>
                       </tbody>
                   </table>
                </div>
                <div class="modal-footer no-all form-btn">
                    <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--设置预览  接收任务-->
    <div class="modal fade panel" id="remindWindowForRe" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">设置预览</h4>
                </div>
                <div class="modal-body dis-table">
                    <table class="table table-td-striped form-table table-bordered b-light">
                       <tbody>
                        <tr>
                       		<td>被提醒人</td>
                       		<td>
                               	<textarea id="jcRemindPerName" name="remindPerName" readonly="readonly"></textarea>
                             </td>
                   		</tr>
                   		<tr>
                       		<td>提醒方式</td>
                       		<td>                           		
                           		<input type="text" id="jcOptionsRadios2" name="optionsRadios2" readonly="readonly"/>
                             </td>
                   		</tr>
                   		<tr> 
                       		<td>提醒内容</td>
                       		<td><textarea id="jcRemindCon" name="remindContent" readonly="readonly"></textarea></td>
                   		</tr>
                       </tbody>
                   </table>
                </div>
                <div class="modal-footer no-all form-btn">
                     <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--设置预览  不接收任务-->
    <div class="modal fade panel" id="remindWindowForNR" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">设置预览</h4>
                </div>
                <div class="modal-body dis-table">
                    <table class="table table-td-striped form-table table-bordered b-light">
                       <tbody>
                        <tr>
                       		<td>被提醒人</td>
                       		<td>
                               	<textarea id="njRemindPerName" name="remindPerName" readonly="readonly"></textarea>
                             </td>
                   		</tr>
                   		<tr>
                       		<td>提醒方式</td>
                       		<td>                           		
                           		<input type="text" id="njOptionsRadios2" name="optionsRadios2" readonly="readonly"/>
                             </td>
                   		</tr>
                   		<tr> 
                       		<td>提醒内容</td>
                       		<td><textarea id="njRemindCon" name="remindContent" readonly="readonly"></textarea></td>
                   		</tr>
                       </tbody>
                   </table>
                </div>
                <div class="modal-footer no-all form-btn">
                     <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--start 上传附件  -->
	<div class="modal fade panel" id="file-edit" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
					<h4 class="modal-title">添加上传文件</h4>
				</div>
				<div class="modal-body">
				<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
					<!-- islogicDel 1为逻辑删除 0为物理删除-->
					<input type="hidden" name="showDelBtn" id="showDelBtn" value="1"/>
					<input type="hidden" id="islogicDel" value="1">
					<input type="hidden" name="businessId" id="businessId" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource"/>
					<input type="hidden" name="businessTable" id="businessTable"  value="TTY_PO_TASK"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="1"> 
	                <input type="hidden" id="iscopy" value="0"> 
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
<script src="${sysPath}/js/com/po/workTask/taskQueryByName.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>