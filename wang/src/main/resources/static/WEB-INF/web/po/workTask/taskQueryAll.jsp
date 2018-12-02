<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<%--<script src="${sysPath}/js/com/common/remind.js"></script> --%>
<script type="text/javascript">
	function oTableSetButtons(source) {
	   var queryButto="";
		if(source.status=='3'){
			queryButto="<a href=\"#\" onclick=\"taskInfoAll.getWorkTaskFiniInfo('"+source.id+"')\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
		}else{
			queryButto="<a href=\"#\" onclick=\"taskInfoAll.getWorkTaskInfo('"+source.id+"')\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
		}
		return queryButto;
};
</script>
  <section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
	     <div class="con-heading fl" id="navigationMenu">
		    	<h1></h1>
			    <div class="crumbs"></div>
		 </div>
	</header>
	<section class="tabs-wrap m-t-md">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#planListMsg" data-toggle="tab" data-id="planListMsg">发起人</a></li>
			<li><a href="#sendListMsg" data-toggle="tab" data-id="sendListMsg">负责人</a></li>
		</ul>
	</section>
	<section class="tab-content">
	     <section class="panel tab-content search-shrinkage">
	   		       <%--token 性能优化<input type="hidden" id="token" name="token" value="0" /> --%>
	   		       <input type="hidden" id="token" name="token" value="${token}" />
	               <%--原用于返回按钮 start--%>
	               <input type="hidden" id="querySponsorId" name="querySponsorId" value="${querySponsorId}" />
	               <input type="hidden" id="querySponsor" name="querySponsor" value="${querySponsor}" />
	               <%--原用于返回按钮 end--%>
	               <%--用于返回按钮 start--%>
	               <input type="hidden" id="returnURL" name="returnURL" value="${returnURL}"/>
	               <%--用于返回按钮 start--%>
	               <input type="hidden" id="currentUserId" value="${userId}"/>
	               <%--发起人  开始--%>   
	              <div id="planListMsg" class="tab-pane fade active in">
	                 <div class="search-line hide">
			                  <form class="table-wrap form-table" id="zdsz-fqr">
			                       <table class="table table-td-striped">
			                         <tbody>
			                             <tr>
			                                 <td class="w140">任务名称</td>
			                                 <td style="width:35%">
			                                 	<input type="text" id="taskName" name="taskName" value=""/>   
			                                 	<input type="hidden" id="taskType0" name="taskType" value="0"/>                     	
			                                 </td>
			                                 <td class="w140">计划开始时间</td>
			                                 <td>
			                                   <div class="input-group w-p100">
			                                   	   <input id="queryStartTime" name="startTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryEndTime lt" value="">
			                                       <div class="input-group-btn w30">-</div>
			                                       <input id="queryEndTime" name="endTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryStartTime gt" value="">
			                                   </div>
			                                 </td>  
			                             </tr>
			                             <tr>
			                                 <td class="w140">发起人</td>
			                                 <td style="width:35%">
			                                 	<div id="showSponsorTree" name="showSponsorTree"></div>                        	
			                                 </td>  
			                                 <td>任务状态</td>
			                                 <td>
			                                 	<label for='status0' class='checkbox inline'><input type="checkbox" id="status0" name="infoStatus" value="0"/>未接收</label>
			                                 	<label for='status1' class='checkbox inline'><input type="checkbox" id="status1" name="infoStatus" value="1"/>进行中</label>
			                                 	<label for='status2' class='checkbox inline'><input type="checkbox" id="status2" name="infoStatus" value="2"/>延期</label>
			                                 	<label for='status3' class='checkbox inline'><input type="checkbox" id="status3" name="infoStatus" value="3"/>完成</label>
			                                 	<label for='status4' class='checkbox inline'><input type="checkbox" id="status4" name="infoStatus" value="4"/>取消</label>
			                                 	<label for='status6' class='checkbox inline'><input type="checkbox" id="status6" name="infoStatus" value="6"/>超期</label>
			                                 	<label for='status7' class='checkbox inline'><input type="checkbox" id="status7" name="infoStatus" value="7"/>拒接收</label>
			                                 </td>                               
			                             </tr>
			                             <tr>
			                                 <td class="w140">任务类型</td>
			                                 <td style="width:35%">
					                              <dic:select name="taskWorkType" id="taskWorkType-fqr" dictName="taskWorkType" headName="-全部-" headValue="" defaultValue=""/>      	
			                                 </td>
			                                 <td></td><td></td>                     
			                             </tr>
			                       </tbody>
			                   </table>
			                   <section class="form-btn m-b-lg">
			                       <button id="queryInfo" class="btn dark" type="button">查 询</button>
			                       <button id="resetInfo" class="btn" type="reset">重 置</button>
			                   </section>
			                  </form>
	                 </div>
	                 <%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%> 
			           <section class="tab-content" id="insideIn-list">
						   <h2 class="panel-heading clearfix">查询结果</h2>
			               <div class="table-wrap">
			                   <table class="table table-striped tab_height" id="taskListTable">
			                       <thead>
			                           <tr>
			                               <th class="w46"></th>	
			                               <th class="w140">任务名称</th>
			                               <th class="w100">任务类型</th>
			                               <th class="w100">任务进度</th>
			                               <th class="w100">任务状态</th>
			                               <th class="w140">上级任务</th>
			                               <th class="w100">发起人</th>
			                               <th class="w100">负责人</th>
			                               <th class="w140">计划开始时间</th>
			                               <th class="w140">计划结束时间</th>
			                               <th class="w90">操作</th>
			                           </tr>
			                       </thead>
			                       <tbody>    
			                       </tbody>
			                   </table>
			               </div>
			           </section>
	              </div>
	              <%--发起人  结束--%>   
	              <%--负责人  开始--%>   
	              <div id="sendListMsg" class="tab-pane fade">
	              		<div class="search-line hide">
			                  <form class="table-wrap form-table" id="zdsz-fzr">
			                       <table class="table table-td-striped">
			                         <tbody>
			                             <tr>
			                                 <td class="w140">任务名称</td>
			                                 <td style="width:35%">
			                                 	<input type="text" id="taskName-fzr" name="taskName" value=""/>   
			                                 	<input type="hidden" id="taskType1" name="taskType" value="1"/>                             	
			                                 </td>
			                                 <td class="w140">计划开始时间</td>
			                                 <td>
			                                   <div class="input-group w-p100">
			                                   	   <input id="queryStartTime-fzr" name="startTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryEndTime-fzr lt" value="">
			                                       <div class="input-group-btn w30">-</div>
			                                       <input id="queryEndTime-fzr" name="endTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryStartTime-fzr gt" value="">
			                                   </div>
			                                 </td>  
			                             </tr>
			                             <tr>
			                                 <td class="w140">负责人</td>
			                                 <td style="width:35%">
			                                 	<div id="showSponsorTree-fzr" name="showSponsorTree"></div>                      	
			                                 </td>
			                                 <td>任务状态</td>
			                                 <td>
			                                 	<label for='status00' class='checkbox inline'><input type="checkbox" id="status00" name="infoStatus" value="0"/>未接收</label>
			                                 	<label for='status01' class='checkbox inline'><input type="checkbox" id="status01" name="infoStatus" value="1"/>进行中</label>
			                                 	<label for='status02' class='checkbox inline'><input type="checkbox" id="status02" name="infoStatus" value="2"/>延期</label>
			                                 	<label for='status03' class='checkbox inline'><input type="checkbox" id="status03" name="infoStatus" value="3"/>完成</label>
			                                 	<label for='status04' class='checkbox inline'><input type="checkbox" id="status04" name="infoStatus" value="4"/>取消</label>
			                                 	<label for='status06' class='checkbox inline'><input type="checkbox" id="status06" name="infoStatus" value="6"/>超期</label>
			                                 	<label for='status07' class='checkbox inline'><input type="checkbox" id="status07" name="infoStatus" value="7"/>拒接收</label>
			                                 </td>                             
			                             </tr>
			                             <tr>
			                                 <td class="w140">任务类型</td>
			                                 <td style="width:35%">
					                              <dic:select name="taskWorkType" id="taskWorkType-fzr" dictName="taskWorkType" headName="-全部-" headValue="" defaultValue=""/>  	
			                                 </td>
			                                 <td></td><td></td>                     
			                             </tr>
			                       </tbody>
			                   </table>
			                   <section class="form-btn m-b-lg">
			                       <button id="queryInfo-fzr" class="btn dark" type="button">查 询</button>
			                       <button id="resetInfo-fzr" class="btn" type="reset">重 置</button>
			                   </section>
			                </form>
			               </div>
			               <%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%> 
				           <section class="tab-content" id="insideIn-list-fzr">
							   <h2 class="panel-heading clearfix">查询结果</h2>
				               <div class="table-wrap">
				                   <table class="table table-striped tab_height" id="taskListTable-fzr">
				                       <thead>
				                           <tr>
				                               <th class="w46"></th>	
				                               <th class="w140">任务名称</th>
				                               <th class="w100">任务类型</th>
				                               <th class="w100">任务进度</th>
				                               <th class="w100">任务状态</th>
				                               <th class="w140">上级任务</th>
				                               <th class="w100">发起人</th>
				                               <th class="w100">负责人</th>
				                               <th class="w140">计划开始时间</th>
				                               <th class="w140">计划结束时间</th>
				                               <th class="w90">操作</th>
				                           </tr>
				                       </thead>
				                       <tbody>    
				                       </tbody>
				                   </table>
				               </div>
				           </section>
				           <section class="clearfix">
				               <section class="form-btn fl m-l">
				               		<a class="btn dark"  id="taskTatalLink">任务统计</a>
				               </section>                        
				          </section>
	              </div>
	                <%--负责人  结束--%>   
	       </section>
	</section>
  </section>
<!---------------------------------------- 附件部分-------------------------------------- --> 
<div class="modal fade panel" id="fileUpload-edit" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
				<h4 class="modal-title">添加上传文件</h4>
			</div>
			<div class="modal-body">
			<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
				<input type="hidden" name="businessId" id="businessId"/>
				<input type="hidden" name="businessTable" id="businessTable"  value="TTY_PO_TASK"/> 
				<!-- upload_type 1为单传  0为多传-->
	               <input type="hidden" id="upload_type" value="0"> 
				<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
				<button id="closeButtion" class="btn dark" type="button"  data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>
<script src="${sysPath}/js/com/po/workTask/taskQueryAll.js" type="text/javascript"></script>
<%@include file="/WEB-INF/web/include/foot.jsp"%>