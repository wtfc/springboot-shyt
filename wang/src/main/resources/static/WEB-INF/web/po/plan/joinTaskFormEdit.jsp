<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%--<script src="${sysPath}/js/com/common/remind.js"></script> --%>
<script src="${sysPath}/js/com/po/plan/planFormEdit.js"></script>
<!--加入任务弹出层Start-->
<div class="modal fade panel" id="init" aria-hidden="false">
    <div class="modal-dialog w1100">
    <div class="modal-content">
	  	 <div class="modal-header">
         	<button type="button" class="close" data-dismiss="modal">×</button>
         	<h4 class="modal-title">任务布置</h4>
	     </div>
         <div class="modal-body">
		      <form class="form-table table-wrap" id="zdsz" name="zdsz">
		          <input type="hidden" id="currentUserId" value="${userId}"/> 
		          <input type="hidden" id="userName" value="${user.displayName}"/> 
		          <input type="hidden" id="userId" value="${user.id}"/>
		          <input type="hidden" id="taskOrigin" name="taskOrigin" value="1" />
		          <table class="table table-td-striped">
		              <tbody>
		                  <tr>
		                      <td class="w115"><span class="required">*</span>任务名称</td>
		                      <td colspan="3">
		                          <div class="input-group w-p100">
		                              <div class="btn-in-area">
		                                   <input type="text" id="taskName" name="taskName"/> 
		                                   <input type="hidden" id="status" name="status" value="0"/>
		                              </div>
		                          </div>
		                      </td>
		                  </tr>
		                  <tr>
		                      <td class="w105">任务类型</td>
		                      <td>
		                      		<dic:select name="taskWorkType" id="taskWorkType" dictName="taskWorkType" headName="-请选择-" headValue="" defaultValue=""/>  
		                      </td>
		                      <td class="w105"><span class="required">*</span>发起人</td>
		                      <td>
		                      		<shiro:principal property='displayName'/>
		                      		<input type="hidden" id="sponsorId" name="sponsorId" />
		                      </td>
		                  </tr>
		                  <tr>
		                      <td class="w140"><span class="required">*</span>负责人</td>
		                      <td><span id="directorUserName"></span>
		                        <input type="hidden" id="directorName" name="directorName" />
		                      	<input type="hidden" id="directorId" name="directorId" />
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
		                              <div class="btn-in-area">
		                                   <textarea rows="3" id="taskContent" name="content"></textarea>
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
		                           <input id="startTime" name="startTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#endTime lt" />
		                       </div>
		                   </td>
		                   <td><span class="required">*</span>结束时间</td>
		                   <td>
		                       <div class="input-group w-p100">
		                           <input id="endTime" name="endTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#startTime gt">
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
                        <td  colspan="3">
                            <label class="radio inline">
                                <input type="radio" name="remindType" value="0" checked="checked" /> 不提醒
                            </label>
                            <label class="radio inline m-l-md">
                                <input type="radio" name="remindType" value="2"/> 邮件
                            </label>
                           <%-- <a class="a-icon i-new m-r-xs" href="#" onclick="taskArra.showRemindSet();" role="button" data-toggle="modal">设置预览</a>  --%>
                            <a class="a-icon i-new m-r-xs" href="#" onclick="showRemindSet();" role="button" data-toggle="modal">设置预览</a> 
                        </td>                    
                    </tr>
		              </tbody>
		          </table>
		      </form>
		 </div>
     	 <div class="modal-footer form-btn">
             <button class="btn dark" type="button" id="addTaskOfPlan">保 存</button>
             <button class="btn" type="button" data-dismiss="modal" id="closeTask" id="closeSendTask()">关 闭</button>
         </div>
	</div>
    </div>
</div>
<!-- 上传附件 START -->
<div class="modal fade panel" id="file-edit" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
				<h4 class="modal-title">添加上传文件</h4>
			</div>
			<div class="modal-body">
			<!-- 业务关联表的属性值，新增附件时不需要填写或填写空字符串 -->
			<!-- islogicDel 1为逻辑删除 0为物理删除 -->
			<input type="hidden" name="showDelBtn" id="showDelBtn" value="1"/>
			<input type="hidden" id="islogicDel" value="1">
			<input type="hidden" name="businessId" id="businessId" value="0"/>
			<input type="hidden" name="businessSource" id="businessSource"/>
			<input type="hidden" name="businessTable" id="businessTable"  value="TTY_PO_TASK"/> 
			<!-- upload_type 1为单传  0为多传 -->
            <input type="hidden" id="upload_type" value="0"> 
            <input type="hidden" id="isshow" value="0"> 
            <input type="hidden" id="iscopy" value="0"> 
			<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>
<!-- 上传附件End -->

<!-- 设置预览 Start -->
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
<!--设置预览End-->
<!--加入任务弹出层End-->
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/workTask/taskArrangement.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/workTask/taskArrangement.validate.js" type="text/javascript"></script>