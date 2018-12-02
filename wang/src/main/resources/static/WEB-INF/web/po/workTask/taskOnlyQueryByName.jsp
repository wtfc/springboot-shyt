<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="jcGOA">
<head>
	<%@ include file="/WEB-INF/web/include/head.jsp"%>
	<%--性能优化
	<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
	<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
	<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
	 --%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
	<%--性能优化<script src="${sysPath}/js/com/common/remind.js"></script> --%>
	<script src="${sysPath}/js/com/po/anno/anno.manage.js" type="text/javascript"></script>
	<script src="${sysPath}/js/com/po/anno/anno.validate.js" type="text/javascript"></script>
	<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
</head>
<body>
   <section class="scrollable padder jcGOA-section" id="scrollable">
     <header class="con-header pull-in">
         <div class="con-heading fl" id="navigationMenu">
		    	<h1></h1>
			    <div class="crumbs"></div>
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
                 <a href="#messages2"  data-toggle="tab" onclick="queryOnlyTaskByName.queryTaskEvent();"  id="taskEventButton">任务事件</a>
             </li>
         </ul>
     </section>
     <!-- tabs end -->
     <section class="panel tab-content">
     		<%--领导批注权限限制 --%>
     		<input type="hidden" id="isLeader" name="isLeader" value="${isLeader}"/>
     		<%--领导批注权限限制 --%>
     		<%--返回路径 --%>
     		<input type="hidden" id="returnURL" name="returnURL" value="${url}"/>
     		<%--返回路径 --%>
     		<%--token 性能优化 <input type="hidden" id="token" name="token" value="0" /> --%>
     		<input type="hidden" id="token" name="token" value="${token}" />
     		<input type="hidden" id="queryTaskName" name="queryTaskName" value="${queryTaskName}" />
     		<input type="hidden" id="querySponsorId" name="querySponsorId" value="${querySponsorId}" />
     		<input type="hidden" id="queryDirectorId" name="queryDirectorId" value="${queryDirectorId}" />
     		<input type="hidden" id="queryStartTime" name="queryStartTime" value="${queryStartTime}" />
     		<input type="hidden" id="queryEndTime" name="queryEndTime" value="${queryEndTime}" />
     		<input type="hidden" id="queryInfoStatus" name="queryInfoStatus" value="${queryInfoStatus}" />
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
							 <td >参与人</td>
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
                               <%-- ${workTask.content} --%>
                               <input type="hidden" id="hiContent" name="content" value="${workTask.content}"/>
                               <div id="showContent"></div>
                             </td>
                         </tr>
                         <tr>
                             <td>完成标准</td>
                             <td colspan="3">
                                 <%-- ${workTask.standard}--%>
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
                             <%--<tr>
                                 <tr>
	                                 <th class="w46">序号</th>
	                                 <th class="w90">汇报人</th>
	                                 <th class="w170">汇报时间</th>
	                                 <th class="w90">任务进度</th>
	                                 <th>汇报内容</th>
	                             </tr>
                             </tr> --%>
                         </thead>
                         <tbody>
                         </tbody>
                     </table>
                 </div>
             </section>
             <!--领导批注 start -->
             <h2 class="modal-heading clearfix">领导批注</h2>
				<form id="leaderIdeaReplayForm"  class="table-wrap form-table">
				<%--
				<form id="leaderIdeaReplayForm"  class="table-wrap form-table padder-v">
				<ul class="dialog m-r m-l m-t" id="comment"> --%>
	             	<ul class="dialog" id="comment">
					</ul>
	            </form> 
				<c:if test="${isLeader eq true}">
				<%--<form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table padder-v">  --%>
					<form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table"> 
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
                 <%--暂时注销掉  <a class="btn dark" href="#"  onclick="queryOnlyTaskByName.returnWorkList();" >返回</a>--%>
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
	<script src="${sysPath}/js/com/po/workTask/taskOnlyQueryByName.js" type="text/javascript"></script>
	<%@ include file="/WEB-INF/web/include/foot.jsp"%>