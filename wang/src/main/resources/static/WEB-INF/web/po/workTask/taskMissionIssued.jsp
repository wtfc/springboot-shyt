<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/plan/planFormRowDetail.js"></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/workTask/taskMissionIssued.validate.js" type="text/javascript"></script>
   <section class="scrollable padder jcGOA-section" id="scrollable"> 
     <header class="con-header pull-in" id="header_1">
          <div class="con-heading fl" id="navigationMenu">
		    	<h1></h1>
			    <div class="crumbs"></div>
		  </div>
     </header>
     <section class="panel m-t-md">
         <form class="table-wrap form-table" id="receWorkTaskForm">
         	 <input type="hidden" id="rRemindType" name="rRemindType" value="${taskTemp.remindType}"/>
         	 <input type="hidden" id="rDirector" name="rDirector" value="${taskTemp.director}"/>
         	 <input type="hidden" id="rPrticipants" name="rPrticipants" value="${taskTemp.prticipants}"/>
         	 <input type="hidden" id="rTaskName" name="rTaskName" value="${taskTemp.taskName}"/>
         	 <input type="hidden" id="currentUserId" value="${userId}"/>
         	 <input type="hidden" id="userName" value="${user.displayName}"/> 
             <input type="hidden" id="userId" value="${user.id}"/>
             <input type="hidden" id="taskParentToSub" name="taskParentToSub" value='${taskParentToSub}' /><!--获取系统设置的参数，督办协办拆分任务时，新建下级任务，上级信息是否回显  lihongyu 20141126  -->
             <table class="table table-td-striped">
                 <tbody>	
                     <tr>
                          <td>任务名称</td>
                          <td colspan="3">
                         		${taskTemp.taskName}
                          </td>
                      </tr>
                      <tr>
                          <td>上级任务名称</td>
                          <td colspan="3">		
                         		<c:choose>
                                	<c:when test="${taskTemp.parentTaskName ne null}">${taskTemp.parentTaskName}</c:when>                          
                                	<c:otherwise>无</c:otherwise>
                                </c:choose>
                          </td>
                      </tr>
                      <tr>
                          <td>上级任务重要程度</td>
                          <td>
                                <c:choose>
                                	<c:when test="${taskTemp.parentTaskImpCode eq '8'}">重要紧急</c:when>
                                	<c:when test="${taskTemp.parentTaskImpCode eq '6'}">重要不紧急</c:when>
                                	<c:when test="${taskTemp.parentTaskImpCode eq '4'}">不重要紧急</c:when>
                                	<c:when test="${taskTemp.parentTaskImpCode eq '2'}">不重要不紧急</c:when>
                                	<c:otherwise>无</c:otherwise>
                                </c:choose>
                          </td>
                          <td>任务重要程度</td>
                          <td>
                                <c:choose>
                                	<c:when test="${taskTemp.taskImpCode eq '8'}">重要紧急</c:when>
                                	<c:when test="${taskTemp.taskImpCode eq '6'}">重要不紧急</c:when>
                                	<c:when test="${taskTemp.taskImpCode eq '4'}">不重要紧急</c:when>
                                	<c:when test="${taskTemp.taskImpCode eq '2'}">不重要不紧急</c:when>
                                	<c:otherwise>无</c:otherwise>
                                </c:choose>
                          </td>
                      </tr>
                      <tr>
                      	  <td>任务类型</td>
                          <td colspan="3">
                               ${taskTemp.taskTypeName}
                          </td>	
                          <%--
                          <td>任务来源</td>
                          <td>
                               <c:choose>
                                	<c:when test="${taskTemp.taskOrigin eq '0'}">新建表单</c:when>
                                	<c:when test="${taskTemp.taskOrigin eq '1'}">工作计划</c:when>
                                	<c:when test="${taskTemp.taskOrigin eq '2'}">会议纪要</c:when>
                                	<c:otherwise>无</c:otherwise>
                                </c:choose>
                          </td>
                           --%>
                      </tr>
                      <tr>
                          <td class="w170">发起人</td>
                          <td>
                          		${taskTemp.sponsor}
                          </td>
                          <td style="width:15%;">负责人</td>
                          <td>
                          		${taskTemp.director}
                          </td>
                      </tr>
                      <tr>
                          <td>参与人</td>
                          <td colspan="3">
                          		${taskTemp.prticipants}
                          </td>
                      </tr>
                      <tr>
                          <td>任务内容</td>
                          <td colspan="3">
                          		<input type="hidden" id="hiContent" name="content" value="${taskTemp.content}"/>
                              	<div id="showContent"></div>
                          </td>
                      </tr>
                      <tr>
                          <td>完成标准</td>
                          <td colspan="3">
                          		<input type="hidden" id="hiStandard" name="standard" value="${taskTemp.standard}"/>
	                            <div id="showStandard"></div>
                          </td>
                      </tr>
                      <tr>
                          <td>计划开始时间</td>
                          <td>	
                          		<fmt:formatDate value="${taskTemp.startTime}" pattern="yyyy-MM-dd"/>
                          </td>
                          <td>计划结束时间</td>
                          <td>
                                <fmt:formatDate value="${taskTemp.endTime}" pattern="yyyy-MM-dd"/>
                          </td>
                      </tr>
                      <tr>
                          <td>实际开始时间</td>
                          <td>
                                <fmt:formatDate value="${taskTemp.actStartTime}" pattern="yyyy-MM-dd"/>
                          </td>
                          <td>实际结束时间</td>
                          <td>
                                <fmt:formatDate value="${taskTemp.actEndTime}" pattern="yyyy-MM-dd"/>
                          </td>
                      </tr>
                      <tr>
                           <td>汇报时限(天)</td>
                           <td>
	                   			 ${taskTemp.reportDay}
                           </td>
                          <td>提醒</td>
                          <td>
	                        	<c:choose>
	                        		<c:when test="${taskTemp.reportType eq '1'}">是</c:when>
	                        		<c:otherwise>否</c:otherwise>
	                        	</c:choose>
                          </td>	
                      </tr>
                      <tr>
	                      <td>加入日程</td>
                          <td>
                                 <c:choose>
                                 	<c:when test="${taskTemp.isImportDiary eq '1'}">是</c:when>
                                 	<c:otherwise>否</c:otherwise>
                                 </c:choose>
                          </td>   
                          <td>超期提醒</td>
                          <td>
                          		 <c:choose>
                          		 	<c:when test="${taskTemp.remindType eq '0'}">不提醒</c:when>
                          		 	<c:when test="${taskTemp.remindType eq '2'}">邮件</c:when>
                          		 	<c:otherwise>无</c:otherwise>
                          		 </c:choose>
                          </td>
                      </tr>
                      <tr>
                          <td>任务进度(%)</td>
                          <td>
                          	${taskTemp.taskProc}
                          </td>
                          <td>附件</td>
                          <td>
                          	<ul id="attachList_0"></ul>
                          </td>
                      </tr>
                 </tbody>
             </table>
             <section class="form-btn m-b-lg">
             </section>
         </form>
     </section>
     <section class="panel clearfix">
     	<h2 class="panel-heading clearfix">下级任务列表</h2>
         <div class="table-wrap">
             <form class="table-wrap">
	             <table class="table table-striped tab_height first-td-tc" id="subTaskTable">
	                 <thead>
	                     <tr>
	                         <th class="w46">
		                         <input type="checkbox" id="parentCheck"/>
	                         </th>
	                         <th>任务名称</th>
	                         <th class="w100">任务进度</th>
	                         <th class="w100">任务状态</th>
	                         <th class="w100">发起人</th>
	                         <th class="w100">负责人</th>
	                         <th class="w140">计划开始时间</th>
	                         <th class="w140">计划结束时间</th>
	                     </tr>
	                 </thead>
	                 <tbody>
	                 	<c:choose>
	                        <c:when test="${taskListSize >0}">
	                        	<c:forEach items="${taskList}" var="vo" varStatus="c">
				                     <tr>
				                        <td>
				                        	<c:if test="${vo.status eq '0' || vo.status eq '8'}">
				                        		<input class="cBox" type="checkbox"  name="checkNum"  onClick="mission.subCheckboxControl()" value="${vo.id}"/>
				                        	</c:if>
				                        </td>
				                        <td>
				                       		 ${vo.taskName}
				                        </td>
				                        <td><div class="progress progress-striped active"><div class="progress-bar" style="width:${vo.taskProc}%"></div><span class="progress-text">${vo.taskProc}%</span></div></td>  
				                        <td>
				                        	<c:choose>
				                        		<c:when test="${vo.status eq '0'}">未接收</c:when>
				                        		<c:when test="${vo.status eq '1'}">进行中</c:when>
				                        		<c:when test="${vo.status eq '2'}">延期</c:when>
				                        		<c:when test="${vo.status eq '3'}">完成</c:when>
				                        		<c:when test="${vo.status eq '4'}">取消</c:when>
				                        		<c:when test="${vo.status eq '5'}">暂存</c:when>
				                        		<c:when test="${vo.status eq '6'}">超期</c:when>
				                        		<c:when test="${vo.status eq '7'}">拒接收</c:when>
				                        		<c:when test="${vo.status eq '8'}">暂存</c:when>
				                        		<c:otherwise>无</c:otherwise>
				                        	</c:choose>
				                        </td>
				                        <td>${vo.sponsor}</td>
				                        <td>${vo.director}</td>
				                        <td><fmt:formatDate value="${vo.startTime}" pattern="yyyy-MM-dd"/></td>
				                        <td><fmt:formatDate value="${vo.endTime}" pattern="yyyy-MM-dd"/></td>
				                    </tr>
			                     </c:forEach>
	                        </c:when>
	                        <c:otherwise>
	                        	<tr><td colspan="8">暂无数据</td></tr>
	                        </c:otherwise>
	                     </c:choose>
	                 </tbody>
	             </table>
	           </form>  
              <section class="form-btn m-b-lg fl">
                 <a href="#" class="btn dark" style="width:100px;" data-toggle="modal" id="addSubTaskId">新 增</a>
                 <button class="btn" type="button" id="batchDele">批量删除</button>
             </section>
             <section class="pagination m-r fr m-t-none">
                 
             </section>
         </div>
     </section>
   </section>  
    <!-- 新建子任务 -->
    <div class="modal fade panel" id="new-sub-task" aria-hidden="false">
        <div class="modal-dialog w900">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" onclick="mission.setBusinessIdNew();">×</button>
                    <h4 class="modal-title">新增</h4>
                </div>
                <div class="modal-body">
                   <form class=" table-wrap form-table" id="newTaskForm" >
                     <input type="hidden" id="parentId" name="parentTaskid" value="${parentId}"/>
                     <input type="hidden" id="subTotalCount" name="totalCount" value="${taskTemp.totalCount}"/>
                     <input type="hidden" id="subTotalCountNew" name="totalCountNew" value="${taskTemp.normalToCount}"/>
                     <input type="hidden" id="subTaskProc" name="taskProc" value="${taskTemp.taskProc}"/>
                     <input type="hidden" id="parentStartTime" name="parentStartTime" value="${taskTemp.startTime}"/>
                     <input type="hidden" id="parentEndTime" name="parentEndTime" value="${taskTemp.endTime}"/>
                     <input type="hidden" id="taskOrigin" name="taskOrigin" value="0"/>
                     <%--用于提取暂存任务时，保存原层级关系 start --%>
		              <input type="hidden" id="taskId" name="taskId"/>
		              <input type="hidden" id="statusArr" name="statusArr"/>
		             <%--用于提取暂存任务时，保存原层级关系 end --%>
                     <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td><span class="required">*</span>任务名称</td>
                                <td colspan="3">
                                    <div class="input-group w-p100">
                                        <div class="btn-in-area">
                                        	<textarea id="taskName" name="taskName"></textarea>
                                        	<input type="hidden" id="id" name="id" value="0"/>
                                       		<input type="hidden" id="token" name="token" value="${token}"/>
                                       		<input type="hidden" id="status" name="status" value="0"/>
                                        </div>
                                        <div class="input-group-btn p-l">
                                            <a  onclick="mission.closeWin();" class="a-icon i-new m-r-xs" type="button" href="#new-agency" role="button" data-toggle="modal">提取任务</a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                            	<td>上级任务名称</td>
                                <td colspan="3">${taskTemp.taskName}</td>
                            </tr>
                             <tr>
                                <td class="w170">上级任务重要程度</td>         	
                          		<td>	  
                          			    <c:choose>
                        					<c:when test="${taskTemp.taskImpCode eq '2'}">不重要不紧急</c:when>
                        					<c:when test="${taskTemp.taskImpCode eq '4'}">不重要紧急</c:when>
                        					<c:when test="${taskTemp.taskImpCode eq '6'}">重要不紧急</c:when>
                        					<c:when test="${taskTemp.taskImpCode eq '8'}">重要紧急</c:when>
                        					<c:otherwise>无</c:otherwise>
                        				</c:choose>	
                                </td>
                                <td style="width:15%;"><span class="required">*</span>重要程度</td>
                                <td>
                                    <select id="taskImpCode" name="taskImpCode">
                                    	<option value="">-请选择-</option>
                                        <option value="8">重要紧急</option>
                                        <option value="6">重要不紧急</option>
                                        <option value="4">不重要紧急</option>
                                        <option value="2">不重要不紧急</option>
                                    </select>
                                </td>
                            </tr>    
                            <tr>
                            	<td>任务类型</td>
		                        <td>
		                             <dic:select name="taskWorkType" id="taskWorkType" dictName="taskWorkType" headName="-请选择-" headValue="" defaultValue=""/>
		                        </td>
		                        <%--
		                        <td>任务来源</td>
		                        <td>
		                             <select class="remind" id="taskOrigin" name="taskOrigin">
		                                  <option value="">-请选择-</option>
		                                  <option value="0">新建表单</option>
		                                  <option value="1">工作计划</option>
		                                  <option value="2">会议纪要</option>
		                              </select>
		                        </td>
		                         --%>
		                        <td>发起人</td>
                                <td>
                                 <input type="hidden" id="subSponsorId" name="sponsorId" value="${taskTemp.directorId}"/>
                                 ${taskTemp.director}
                                </td>
		                    </tr>                    
                            <tr>
                                <td><span class="required">*</span>负责人</td>
                                <td>
                                      <div id="showDirectorIdTree"></div>   
                                </td>
                                <td>参与人</td>
                                <td>
                                     <div id="prticipantsTree"></div>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="required">*</span>任务内容</td>
                                <td colspan="3">
                                    <div class="input-group w-p100">
                                        <%--<div class="btn-in-area"> --%>
                                             <textarea id="taskContent" name="content"></textarea> 
                                        <%--</div> --%>
                                        <div class="input-group-btn p-l">
                                            <a id="selectPlan" class="a-icon i-new m-r-xs" type="button" href="#" role="button" data-toggle="modal">选择计划</a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>完成标准</td>
                                <td colspan="3"><textarea id="standard" name="standard"></textarea></td>
                            </tr>
                            <tr>
                                <td><span class="required">*</span>开始时间</td>
                                <td>
                                    <div class="input-group w-p100">
                                        <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="startTime" name="startTime" data-ref-obj="#endTime lt"/>
                                    </div>
                                </td>
                                <td><span class="required">*</span>结束时间</td>
                                <td>
                                    <div class="input-group w-p100">
                                        <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="endTime" name="endTime" data-ref-obj="#startTime gt"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
		                    	<td>汇报时限(天)</td>
		                        <td class="w276">
			                       <input type="text" class="input-mini m-l-none" id="reportDay" name="reportDay" value="0" onchange="mission.remindMessage();"/><br/>注：任务结束时间前几天以邮件方式提醒发起人和负责人
		                        </td>
		                        <td>完成提醒</td>
		                        <td>
		                        	<label class="radio inline">
		                                <input type="radio" id="isRemind" name="reportType" value="0" checked="checked" disabled="disabled"/> 否
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
		                                <input type="radio" id="isImport" name="isImportDiary" value="0" checked="checked" /> 否
		                            </label>
		                            <label class="radio inline m-l-md">
		                                <input type="radio" id="notImport" name="isImportDiary" value="1" /> 是
		                            </label> 
                                </td>
                                <td>附件</td>
                                <td>
                                     <input type="file" class="filestyle" data-icon="false" data-classbutton="btn btn-file input-group-btn" data-classinput="form-file" id="filestyle-0" style="position: fixed; left: -5000px;" />
									 <a class="btn dark" type="button" href="#file-edit"  name="queryattach" id="queryattach"  role="button" data-toggle="modal">附件</a>
                               		 <ul id="attachList"></ul>
                                </td>
                            </tr>
                            <tr>
                                <td>超期提醒</td>
                                <td colspan="3">
                                    <label class="radio inline">
		                                <input type="radio" name="remindType" value="0" checked="checked" /> 不提醒
		                            </label>
		                            <label class="radio inline m-l-md">
		                                <input type="radio" name="remindType" value="2"/> 邮件
		                            </label>
		                            <a class="a-icon i-new m-r-xs" href="#" onclick="mission.showRemindSetNew();" role="button" data-toggle="modal">设置预览</a> 
                                </td>
                            </tr>       
                        </tbody>
                       </table>
                   </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="addNewTask">保 存</button>
                    <button class="btn" type="button" id="addTempTask">暂 存</button>
                    <button class="btn" type="button" data-dismiss="modal" onclick="mission.setBusinessIdNew();">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 提取模板 -->
    <div class="modal fade panel" id="new-agency" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" onclick="mission.closeWin();">×</button>
                    <h4 class="modal-title">提取任务</h4>
                </div>
                <div class="modal-body">
                    <section class="panel-tab-con">
                        <!-- tabs -->
                        <section class="tabs-wrap tabs-wrap-in">
                            <!-- 同时添加 tabs-wrap-in 类-->
                            <ul class="nav nav-tabs">
                            	<li class="active">
                                    <a href="#messages0" data-toggle="tab" id="tempWorkTask" onclick="mission.getWorkTaskZCTask();">暂存任务</a>
                                </li>
                                <li>
                                    <a href="#messages1" data-toggle="tab" id="tempTask" onclick="mission.getWorkTaskZC();">任务模板</a>
                                </li>
                                <!-- 根据a标签href属性相对应的id，切换tab-pane -->
                                <li>
                                    <a href="#messages2" data-toggle="tab" id="issuedTask" onclick="mission.getWorkTask();">已发任务</a>
                                </li>
                            </ul>
                        </section>
                        <!-- tabs end -->
                        <!-- tabs模块下紧接panel模块 -->
                        <div class="tab-content">
                        	<div class="tab-pane fade active in " id="messages0">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                <form class="table-wrap">
                                    <table class="table table-striped frist-td-tc" id="taskTempTable">
                                    	<input type="hidden" id="taskNameVal" name="taskNameVal"/>
    	                                <input type="hidden" id="taskStatus" name="taskStatus"/>
                                        <thead>
                                            <th class="w46"></th>
                                            <th>任务名称</th>
                                            <th class="w66">操作</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                            <div class="tab-pane fade " id="messages1">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                <form class="table-wrap">
                                    <table class="table table-striped frist-td-tc" id="workTaskTable">
                                    	<input type="hidden" id="taskNameVal" name="taskNameVal"/>
    									<input type="hidden" id="taskStatus" name="taskStatus"/>
                                        <thead>
                                            <th class="w46"></th>
                                            <th>任务名称</th>
                                            <th class="w66">操作</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                            <div class="tab-pane fade" id="messages2">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                <form class="table-wrap">
                                    <table class="table table-striped" id="issuedTaskTable">
                                        <thead>
                                            <th class="w46"></th>
                                            <th>任务名称</th>
                                            <th class="w66">操作</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </section>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="extractButtion">提 取</button>
                    <button class="btn" type="button" data-dismiss="modal" onclick="mission.closeWin();">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 选择计划 -->
    <div class="modal fade panel" id="viewPlan" aria-hidden="false">
		<form id="planBoxForm" name="planBoxForm" class="table-wrap form-table">
	    <div class="modal-dialog" style="width:1200px;">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal">×</button>
	                <h4 class="modal-title">选择计划</h4>
	            </div>
	            <div class="modal-body">
	                <div class="table-wrap form-table">
	                    <table class="table table-td-striped">
	                        <tbody>
	                            <tr>
	                                <td>计划标题</td>
	                                <td colspan="3">
	                                	<input id="planTitleBox" name="planTitle" type="text">
	                                	<input type="hidden" id="applyId" name="applyId" value="${userId}"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td class="w140">计划类型</td>
	                                <td style="width:35%">
			                           <label class="checkbox inline">
			                               <input type="checkbox" id="weekPlanType" name="planType" value="0">周计划
			                           </label>
			                           <label class="checkbox inline">
			                               <input type="checkbox" id="monthPlanType" name="planType" value="1">月计划
			                           </label>
			                           <label class="checkbox inline">
			                               <input type="checkbox" id="yearPlanType" name="planType" value="2">年计划
			                           </label>
	                                </td>
	                                <td class="w140">开始时间</td>
	                                <td>
	                                    <div class="input-group w-p100">
	                                        <input id="planStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeEnd lt">
	                                        <div class="input-group-btn w30">-</div>
	                                        <input id="planStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeBegin gt">
	                                    </div>
	                                </td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                <section class=" form-btn m-b m-t">
	                    <button id="queryPlanBox" name="queryPlanBox" class="btn dark" type="button">查 询</button>
	                    <button id="resetPlanBox" name="resetPlanBox" class="btn" type="button">重 置</button>
	                </section>
	                <div class="table-wrap">
	                    <table id="queryPlanTable" name="queryPlanTable"class="table table-striped">
	                        <thead>
	                            <tr>
	                                <th class="w46"></th>
	                                <th class="w100">计划类型</th>
	                                <th>计划标题</th>
	                                <th class="w140">开始时间</th>
	                                <th class="w140">结束时间</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	
	                        </tbody>
	                    </table>
	                </div>
	            </div>
	            <div class="modal-footer  form-btn">
					<button class="btn dark" type="button" id="importContentBtn">导入计划</button>
					<button class="btn" type="button" data-dismiss="modal">关 闭</button>
	            </div>
	        </div>
	    </div>
	    </form>
   </div>
    <!--设置预览 -->
    <div class="modal fade panel" id="remindWindow" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">设置预览</h4>
                </div>
                <div class="modal-body dis-table">
	                <div class="form-table">
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
                </div>
                <div class="modal-footer no-all form-btn">
                    <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
     <!--设置预览 -->
    <div class="modal fade panel" id="remindWindowNew" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" onclick="mission.showRemindSetClose()">×</button>
                    <h4 class="modal-title">设置预览</h4>
                </div>
                <div class="modal-body dis-table">
	                <div class="form-table">
	                    <table class="table table-td-striped form-table table-bordered b-light">
	                       <tbody>
	                        <tr>
	                       		<td>被提醒人</td>
	                       		<td>
	                       			<input type="hidden" id="remindPerIdNew" name="remindPerId"/>
	                               	<textarea id="remindPerNameNew" name="remindPerName" readonly="readonly"></textarea>
	                             </td>
	                   		</tr>
	                   		<tr>
	                       		<td>提醒方式</td>
	                       		<td>                           		
	                           		<input type="text" id="optionsRadios" name="optionsRadios" readonly="readonly"/>
	                             </td>
	                   		</tr>
	                   		<tr> 
	                       		<td>提醒内容</td>
	                       		<td>
	                       			<input type="hidden" id="remindContentTemp" name="remindContentTemp"/>
	                       			<textarea id="remindContent" name="remindContent"></textarea>
	                       		</td>
	                   		</tr>
	                       </tbody>
	                   </table>
	                </div>
                </div>
                <div class="modal-footer no-all form-btn">
                    <button class="btn dark" type="button" data-dismiss="modal" id="remindSave">保 存</button>
                    <button class="btn " type="button" data-dismiss="modal" id="remindClose">关 闭</button>
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
	                <input type="hidden" id="upload_div_name" value="file-edit">
               		<input type="hidden" id="upload_close_callback" value="mission.fileupload">
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
    <script src="${sysPath}/js/com/po/workTask/taskMissionIssued.js" type="text/javascript"></script> 
	<%@ include file="/WEB-INF/web/include/foot.jsp"%>