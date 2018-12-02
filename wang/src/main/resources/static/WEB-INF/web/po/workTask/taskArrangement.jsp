<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/po/plan/planFormRowDetail.js"></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script> 
<script src="${sysPath}/js/com/po/workTask/taskArrangement.validate.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
  <section class="scrollable padder jcGOA-section" id="scrollable">
    <header class="con-header pull-in" id="header_1">
    	<div class="con-heading fl" id="navigationMenu">
	    	<h1></h1>
		    <div class="crumbs"></div>
	    </div>
    </header>
    <!-- div 放入开始 -->
    <section class="panel m-t-md">
        <h2 class="panel-heading clearfix">任务布置</h2>
         <input type="hidden" id="token" name="token" value="${token}" />
        <form class="form-table table-wrap" id="zdsz" name="zdsz">
            <input type="hidden" id="currentUserId" value="${userId}"/> 
            <input type="hidden" id="userName" value="${user.displayName}"/> 
            <input type="hidden" id="userId" value="${user.id}"/>
            <input type="hidden" id="status" name="status" value="0"/>
            <input type="hidden" id="taskOrigin" name="taskOrigin" value="0"/>
            <%--用于提取暂存任务时，保存原层级关系 start --%>
              <input type="hidden" id="taskId" name="taskId"/>
              <input type="hidden" id="statusArr" name="statusArr"/>
            <%--用于提取暂存任务时，保存原层级关系 end --%>
            <table class="table table-td-striped">
                <tbody>
                    <tr>
                        <td class="w115"><span class="required">*</span>任务名称</td>
                        <td colspan="3">
                            <div class="input-group w-p100">
                                <div class="btn-in-area">
                                     <input type="text" id="taskName" name="taskName"/>         
                                </div>
                                <div class="input-group-btn p-l">
                                    <a class="a-icon i-new m-r-xs" onclick="taskArra.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal">提取任务</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="w140">任务类型</td>
                        <td style="width:40%;">
                              <dic:select name="taskWorkType" id="taskWorkType" dictName="taskWorkType" headName="-请选择-" headValue="" defaultValue=""/>  
                        </td>
                        <td class="w140"><span class="required">*</span>发起人</td>
                        <td>
                             <div id="showSponsorTree" ></div> 
                        </td>
                    </tr>
                    <tr>
                        <td class="w140"><span class="required">*</span>负责人</td>
                        <td>
                        	<div id="showDirectorIdTree"></div>
                        </td>
                        <td class="w140">参与人</td>
                        <td>
                        	<div id="prticipantsTree"></div>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="required">*</span>任务内容</td>
                        <td colspan="3">
                            <div class="input-group w-p100">
                                <textarea rows="3" id="taskContent" name="content"></textarea>
                                <div class="input-group-btn p-l">
                                    <a id="selectPlan" class="a-icon i-new m-r-xs" type="button" href="#" role="button" data-toggle="modal">选择计划</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>完成标准</td>
                        <td colspan="3">
                        	<textarea rows="3" id="standard" name="standard"></textarea>
                        </td>
                    </tr>
                    <tr>
	                    <td><span class="required">*</span>开始时间</td>
	                    <td>
	                        <div class="input-group w-p100">
	                            <input id="startTime" name="startTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#endTime lt"/>
	                        </div>
	                    </td>
	                    <td><span class="required">*</span>结束时间</td>
	                    <td>
	                        <div class="input-group w-p100">
	                            <input id="endTime" name="endTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#startTime gt" />
	                        </div>
	                    </td>
                    </tr> 
                    <tr>
                    	<td>汇报时限(天)</td>
                        <td>
	                       <input type="text" class="input-mini m-l-none" id="reportDay" name="reportDay" value="0" onchange="taskArra.remindMessage();"/><br/>注：任务结束时间前几天以邮件方式提醒发起人和负责人
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
							<span workFlowForm="button" itemName="queryattach"><a class="btn dark" type="button" href="#file-edit"  name="queryattach" id="queryattach"  role="button" data-toggle="modal">附件</a></span>
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
                            <a class="a-icon i-new m-r-xs" href="#" onclick="taskArra.showRemindSet();" role="button" data-toggle="modal">设置预览</a>  
                        </td> 
                    </tr>
                </tbody>
            </table>
        </form>
    </section>
    <section class="form-btn m-b-lg ">
         <button class="btn dark" type="button" id="addTask">保 存</button>
         <button class="btn" type="button" id="tempAddTask">暂 存</button>
         <button class="btn" type="button" id="addTaskModel">保存模板</button>
    </section>
  </section> 
    <%--选择计划弹窗    ---%>
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
    <!--  提取任务弹窗-------->
    <div class="modal fade panel" id="new-agency" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" onclick="taskArra.closeWin();">×</button>
                    <h4 class="modal-title">提取任务</h4>
                </div>
                <div class="modal-body">
                    <section class="panel-tab-con">
                        <!-- tabs -->
                        <section class="tabs-wrap tabs-wrap-in">
                            <!-- 同时添加 tabs-wrap-in 类-->
                            <ul class="nav nav-tabs">
                            	<li class="active">
                                    <a href="#messages0" data-toggle="tab" id="tempWorkTask" onclick="taskArra.getWorkTaskZCTask();">暂存任务</a>
                                </li>
                                <li>
                                    <a href="#messages1" data-toggle="tab" id="tempTask" onclick="taskArra.getWorkTaskZC();">任务模板</a>
                                </li>
                                <!-- 根据a标签href属性相对应的id，切换tab-pane -->
                                <li>
                                    <a href="#messages2" data-toggle="tab" id="issuedTask" onclick="taskArra.getWorkTask();">已发任务</a>
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
                            <div class="tab-pane fade" id="messages1">
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
                                    <table class="table table-striped frist-td-tc" id="issuedTaskTable">
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
                    <button class="btn" type="button" data-dismiss="modal" onclick="taskArra.closeWin();">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--设置预览 -->
    <div class="modal fade panel" id="remindWindow" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" onclick="taskArra.showRemindSetClose()">×</button>
                    <h4 class="modal-title">设置预览</h4>
                </div>
                <div class="modal-body dis-table">
	                <div class="form-table">
	                    <table class="table table-td-striped form-table table-bordered b-light">
	                       <tbody>
	                        <tr>
	                       		<td class="w140">被提醒人</td>
	                       		<td>
	                       			<input type="hidden" id="remindPerId" name="remindPerId"/>
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
					<input type="hidden" id="islogicDel" value="1" />
					<input type="hidden" name="businessId" id="businessId" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource"/>
					<input type="hidden" name="businessTable" id="businessTable"  value="TTY_PO_TASK"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0" /> 
	                <input type="hidden" id="isshow" value="0" /> 
	                <input type="hidden" id="iscopy" value="0" />
					<input type="hidden" id="upload_div_name" value="file-edit" />
                	<input type="hidden" id="upload_close_callback" value="taskArra.fileupload">
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
<!-- div 放入结束 -->
<script src="${sysPath}/js/com/po/workTask/taskArrangement.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>