<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%--<link href="${sysPath}/css/JcGoa_v2.0.css" rel="stylesheet" type="text/css" /> --%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- <script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script> -->
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/plan/planFormRowDetail.js"></script>
<%--<script src="${sysPath}/js/com/common/remind.js"></script> --%>
<script src="${sysPath}/js/com/po/workTask/taskQuery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
	function userOTableSetButtons(source) {
		var download = "<a class=\"btn dark\" href=\""+getRootPath()+"/content/attach/download.action?fileName="+source.fileName+"&resourcesName="+source.resourcesName+"\" >下载</a>";
		return download;
};
</script>
<script type="text/javascript">
	var userId = '<%=request.getAttribute("userId")%>';
	function oTableSetButtons(source) {
	    var receTask="";
	    var notRece="";
	    var edit="";
	    var del="";
	    var reminde="";
	    var extensionApply="";
	    var extensionCancel="";
	    var extension="";
	    var cancel="";
	    var report="";
	    var issuedTask="";
	    var queryButto="";
		if(source.status=="0"){//未接收
			if(userId==source.sponsorId){//当前登录人为任务发起人
				edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"taskInfo.get("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
				del ="<a href=\"#\" onclick=\"taskInfo.deleteInfo("+ source.id+ ")\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a> ";
			}
		}
		if(source.status=="1" || source.status=="2"){//进行中||延期
			if(userId==source.sponsorId){//当前登录人为任务发起人
	        	reminde="<a href=\"#\" onclick=\"taskInfo.getReminders("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">催办</a>";
	         	cancel="<a href=\"#\" onclick=\"taskInfo.getCancel("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">取消</a>";
	         	if(source.delayStatus =='0'){
	         		extension="<a href=\"#\" onclick=\"taskInfo.getDelayCheckTask("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">延期审批</a>";
	         	}
			}
		}
		if(source.status=="6"){//超期
			if(userId==source.sponsorId){//当前登录人为任务发起人
	        	reminde="<a href=\"#\" onclick=\"taskInfo.getReminders("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">催办</a>";
	         	cancel="<a href=\"#\" onclick=\"taskInfo.getCancel("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">取消</a>";
	         	if(source.delayStatus =='0'){
	         		extension="<a href=\"#\" onclick=\"taskInfo.getDelayCheckTask("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">延期审批</a>";
	         	}
			}
		}
		if(source.status=='3'){//完成
			queryButto="<a href=\"#\" onclick=\"taskInfo.getWorkTaskFiniInfo('"+source.id+"')\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
		}else{
			queryButto="<a href=\"#\" onclick=\"taskInfo.getWorkTaskInfo('"+source.id+"')\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
		}
		if(source.status=='8'){//暂存
			if(userId==source.sponsorId){//当前登录人为任务发起人
				edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"taskInfo.get("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
				del ="<a href=\"#\" onclick=\"taskInfo.deleteInfo("+ source.id+ ")\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a> ";
			}
		}
		return receTask+notRece+reminde+extension+extensionApply+extensionCancel+report+issuedTask+cancel+edit+del+queryButto;
};
function oTableSetButtons_fzr(source) {
    var receTask="";
    var notRece="";
    var edit="";
    var del="";
    var reminde="";
    var extensionApply="";
    var extensionCancel="";
    var extension="";
    var cancel="";
    var report="";
    var issuedTask="";
    var queryButto="";
	if(source.status=="1" || source.status=="2"){//进行中||延期
		if(userId==source.directorId){//当前登录人为任务负责人
			 if(null==source.normalToCount || source.normalToCount==0){//如果存在子任务，则父任务没有汇报操作
		    	 report="<a href=\"#\" onclick=\"taskInfo.getReport("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">汇报</a>"; 
		     }
		     issuedTask="<a href=\"#\" onclick=\"taskInfo.toNextTask("+source.id+")\" class=\"a-icon i-new m-r-xs\">拆分任务</a>";
		     if(source.delayStatus =='0'){
		    	 extensionCancel="<a href=\"#\" onclick=\"taskInfo.getDelayCancelTask("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">延期取消</a>";
		     }else{
		    	 extensionApply="<a href=\"#\" onclick=\"taskInfo.getDelayTask("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">延期申请</a>";
		     }
		}
	}
	if(source.status=="6"){//超期
		if(userId==source.directorId){//当前登录人为任务负责人
			 if(null==source.normalToCount || source.normalToCount==0){//如果存在子任务，则父任务没有汇报操作
		    	 report="<a href=\"#\" onclick=\"taskInfo.getReport("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">汇报</a>"; 
		     }
		     issuedTask="<a href=\"#\" onclick=\"taskInfo.toNextTask("+source.id+")\" class=\"a-icon i-new m-r-xs\">拆分任务</a>";
		     if(source.delayStatus =='0'){
		    	 extensionCancel="<a href=\"#\" onclick=\"taskInfo.getDelayCancelTask("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">延期取消</a>";
		     }else{
		    	 extensionApply="<a href=\"#\" onclick=\"taskInfo.getDelayTask("+source.id+")\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">延期申请</a>";
		     }
		}
	}
	if(source.status=='3'){//完成
		queryButto="<a href=\"#\" onclick=\"taskInfo.getWorkTaskFiniInfo('"+source.id+"')\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
	}else{
		queryButto="<a href=\"#\" onclick=\"taskInfo.getWorkTaskInfo('"+source.id+"')\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
	}
	return receTask+notRece+reminde+extension+extensionApply+extensionCancel+report+issuedTask+cancel+edit+del+queryButto;
};
function oTableSetButtons_zc(source) {
    var receTask="";
    var notRece="";
    var edit="";
    var del="";
    var reminde="";
    var extensionApply="";
    var extensionCancel="";
    var extension="";
    var cancel="";
    var report="";
    var issuedTask="";
    var queryButto="";
	if(source.status=='8'){//暂存
		if(userId==source.sponsorId){//当前登录人为任务发起人
			edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"taskInfo.get("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
			del ="<a href=\"#\" onclick=\"taskInfo.deleteInfo("+ source.id+ ")\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a> ";
		}
	}
	queryButto="<a href=\"#\" onclick=\"taskInfo.getWorkTaskInfo('"+source.id+"')\" data-toggle=\"modal\" class=\"a-icon i-new m-r-xs\">查看</a>";
	return receTask+notRece+reminde+extension+extensionApply+extensionCancel+report+issuedTask+cancel+edit+del+queryButto;
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
	  	<h1>我的任务</h1>
		<div class="crumbs">
			<a href="#" onclick="homeloadmenu()">首页</a><i></i>个人办公<i></i>督办协办<i></i>我的任务
		</div>
	</header>
	<section class="tabs-wrap m-t-md">
		<ul class="nav nav-tabs">
		 <c:if test="${fromPortal eq 'yes'}">
		 	<li><a href="#planListMsg" data-toggle="tab" data-id="planListMsg">发起人</a></li>
			<li class="active"><a href="#sendListMsg" data-toggle="tab" data-id="sendListMsg">负责人</a></li>
			<li><a href="#tempListMsg" data-toggle="tab" data-id="tempListMsg">暂 存</a></li>
		 </c:if>
		 <c:if test="${fromPortal ne 'yes'}">
			<li class="active"><a href="#planListMsg" data-toggle="tab" data-id="planListMsg">发起人</a></li>
			<li><a href="#sendListMsg" data-toggle="tab" data-id="sendListMsg">负责人</a></li>
			<li><a href="#tempListMsg" data-toggle="tab" data-id="tempListMsg">暂 存</a></li>
		 </c:if>
		</ul>
	</section>
	<section class="tab-content">
	         <section class="panel tab-content search-shrinkage">
	         		<input type="hidden" id="token" name="token" value="${token}" />
	                 <%--用于返回按钮 start--%>
	                 <input type="hidden" id="queryTaskName" name="queryTaskName" value="${queryTaskName}" />
	                 <input type="hidden" id="qStartTime" name="qStartTime" value="${queryStartTime}" />
	                 <input type="hidden" id="qEndTime" name="qEndTime" value="${queryEndTime}" />
	                 <input type="hidden" id="querySponsorId" name="querySponsorId" value="${querySponsorId}" />
	                 <input type="hidden" id="querySponsor" name="querySponsor" value="${querySponsor}" />
	                 <input type="hidden" id="queryDirectorId" name="queryDirectorId" value="${queryDirectorId}" />
	                 <input type="hidden" id="queryDirector" name="queryDirector" value="${queryDirector}" />
	                 <input type="hidden" id="queryInfoStatus" name="queryInfoStatus" value="${queryInfoStatus}" />
	                 <input type="hidden" id="queryStatus0" name="status0" value="${status0}" />
	                 <input type="hidden" id="queryStatus1" name="status1" value="${status1}" />
	                 <input type="hidden" id="queryStatus2" name="status2" value="${status2}" />
	                 <input type="hidden" id="queryStatus3" name="status3" value="${status3}" />
	                 <input type="hidden" id="queryStatus4" name="status4" value="${status4}" />
	                 <input type="hidden" id="queryStatus6" name="status6" value="${status6}" />
	                 <input type="hidden" id="queryStatus7" name="status7" value="${status7}" />
	                 <input type="hidden" id="queryStatus8" name="status8" value="${status8}" />
	                 <!-- 如果是从首页的统计数直接进入页面，那么页面锁定在负责人tab页中，同时查询条件“未接收”框 被选中  xuweiping 2014.12.08 -->
	                  <input type="hidden" id="fromPortal"  value="${fromPortal}" />  
	                 <%--用于返回按钮 end--%>
	                 <input type="hidden" id="currentUserId" value="${userId}"/>
	                 <%--发起人  开始--%>
	                   <!-- 如果是从首页的统计数直接进入页面，那么页面锁定在负责人tab页中，同时查询条件“未接收”框 被选中  xuweiping 2014.12.08 -->
	                  <c:if test="${fromPortal eq 'yes'}">
					 	 <div id="planListMsg" class="tab-pane fade">
					 </c:if>
					 <c:if test="${fromPortal ne 'yes'}">
						<div id="planListMsg" class="tab-pane fade active in">
					 </c:if>
	                 
	                     <div class="search-line hide">
			                 <form class="table-wrap form-table" id="zdsz-fqr">
			                     <table class="table table-td-striped">
			                         <tbody>
			                              <tr>
			                                 <td class="w140">任务名称</td>
			                                 <td style="width:35%">
			                                 	<input type="text" id="taskName" name="taskName"/> 
			                                 	<input type="hidden" id="taskType0" name="taskType" value="0"/>                               	
			                                 </td>
			                                 <td class="w140">计划开始时间</td>
			                                 <td>
			                                   <div class="input-group w-p100">
			                                   	   <input id="queryStartTime" name="startTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryEndTime lt" />
			                                       <div class="input-group-btn w30">-</div>
			                                       <input id="queryEndTime" name="endTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryStartTime gt" />
			                                   </div>
			                                 </td>
			                             </tr>
			                             <tr>
			                                 <td class="w140">任务状态</td>
			                                 <td style="width:35%">
			                                 	<%--<label for='status8' class='checkbox inline'><input type="checkbox" id="status8" name="infoStatus" value="8"/>暂存</label> --%>
			                                 	<label for='status0' class='checkbox inline'><input type="checkbox" id="status0" name="infoStatus" value="0"/>未接收</label>
			                                 	<label for='status1' class='checkbox inline'><input type="checkbox" id="status1" name="infoStatus" value="1"/>进行中</label>
			                                 	<label for='status2' class='checkbox inline'><input type="checkbox" id="status2" name="infoStatus" value="2"/>延期</label>
			                                 	<label for='status3' class='checkbox inline'><input type="checkbox" id="status3" name="infoStatus" value="3"/>完成</label>
			                                 	<label for='status4' class='checkbox inline'><input type="checkbox" id="status4" name="infoStatus" value="4"/>取消</label>
			                                 	<label for='status6' class='checkbox inline'><input type="checkbox" id="status6" name="infoStatus" value="6"/>超期</label>
			                                 	<label for='status7' class='checkbox inline'><input type="checkbox" id="status7" name="infoStatus" value="7"/>拒接收</label>
			                                 </td>
			                                 <td class="w140">任务类型</td>
			                                 <td>
					                             <dic:select name="taskWorkType" id="taskWorkType-fqr" dictName="taskWorkType" headName="-全部-" headValue="" defaultValue=""/>       	
			                                 </td>                             
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
			             <section class="clearfix" id="insideIn-list">
						   <h2 class="panel-heading clearfix">查询结果</h2>
				               <div class="table-wrap ">
				                   <table class="table table-striped first-td-tc tab_height" id="taskListTable">
				                       <thead>
				                           <tr>
				                           	   <th class="w46"></th>	
				                               <th class="w140">任务名称</th>
				                               <th class="w100">任务类型</th>
				                               <th class="w100">任务进度</th>
				                               <th class="w100">任务状态</th>
				                               <th class="w140">上级任务</th>
				                               <th class="w90">发起人</th>
				                               <th class="w90">负责人</th>
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
	                 <!-- 如果是从首页的统计数直接进入页面，那么页面锁定在负责人tab页中，同时查询条件“未接收”框 被选中  xuweiping 2014.12.08 -->
					<c:if test="${fromPortal eq 'yes'}">
						 <div id="sendListMsg" class="tab-pane fade active in">
					</c:if>
					<c:if test="${fromPortal ne 'yes'}">
						<div id="sendListMsg" class="tab-pane fade">
					</c:if>
	                 	<div class="search-line hide">
			                 <form class="table-wrap form-table" id="zdsz-fzr">
			                     <table class="table table-td-striped">
			                         <tbody>
			                              <tr>
			                                 <td class="w140">任务名称</td>
			                                 <td style="width:35%">
			                                 	<input type="text" id="taskName-fzr" name="taskName"/>    
			                                 	<input type="hidden" id="taskType1" name="taskType" value="1"/>                             	
			                                 </td>
			                                 <td class="w140">计划开始时间</td>
			                                 <td>
			                                   <div class="input-group w-p100">
			                                   	   <input id="queryStartTime-fzr" name="startTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryEndTime-fzr lt" />
			                                       <div class="input-group-btn w30">-</div>
			                                       <input id="queryEndTime-fzr" name="endTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryStartTime-fzr gt" />
			                                   </div>
			                                 </td>
			                             </tr>
			                             <tr>
			                                 <td class="w140">任务状态</td>
			                                 <td  style="width:35%">
			                                 	<label for='status00' class='checkbox inline'><input type="checkbox" id="status00" name="infoStatus" value="0"/>未接收</label>
			                                 	<label for='status01' class='checkbox inline'><input type="checkbox" id="status01" name="infoStatus" value="1"/>进行中</label>
			                                 	<label for='status02' class='checkbox inline'><input type="checkbox" id="status02" name="infoStatus" value="2"/>延期</label>
			                                 	<label for='status03' class='checkbox inline'><input type="checkbox" id="status03" name="infoStatus" value="3"/>完成</label>
			                                 	<label for='status04' class='checkbox inline'><input type="checkbox" id="status04" name="infoStatus" value="4"/>取消</label>
			                                 	<label for='status06' class='checkbox inline'><input type="checkbox" id="status06" name="infoStatus" value="6"/>超期</label>
			                                 	<label for='status07' class='checkbox inline'><input type="checkbox" id="status07" name="infoStatus" value="7"/>拒接收</label>
			                                 </td>
			                                 <td class="w140">任务类型</td>
			                                 <td>
					                              <dic:select name="taskWorkType" id="taskWorkType-fzr" dictName="taskWorkType" headName="-全部-" headValue="" defaultValue=""/>
			                                 </td>                            
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
			            <section class="clearfix" id="insideIn-list-fzr">
							   <h2 class="panel-heading clearfix">查询结果</h2>
				               <div class="table-wrap ">
				                   <table class="table table-striped first-td-tc tab_height" id="taskListTable-fzr">
				                       <thead>
				                           <tr>
				                           	   <th class="w46"></th>	
				                               <th class="w140">任务名称</th>
				                               <th class="w100">任务类型</th>
				                               <th class="w100">任务进度</th>
				                               <th class="w100">任务状态</th>
				                               <th class="w140">上级任务</th>
				                               <th class="w90">发起人</th>
				                               <th class="w90">负责人</th>
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
	                 <%--暂存  开始--%>   
	                 <div id="tempListMsg" class="tab-pane fade">
	                 	<div class="search-line hide">
			                 <form class="table-wrap form-table" id="zdsz-zc">
			                     <table class="table table-td-striped">
			                         <tbody>
			                              <tr>
			                                 <td class="w140">任务名称</td>
			                                 <td style="width:35%">
			                                 	<input type="text" id="taskName-zc" name="taskName"/>              	
			                                 </td>
			                                 <td class="w140">计划开始时间</td>
			                                 <td>
			                                   <div class="input-group w-p100">
			                                   	   <input id="queryStartTime-zc" name="startTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryEndTime-zc lt" />
			                                       <div class="input-group-btn w30">-</div>
			                                       <input id="queryEndTime-zc" name="endTime" class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" data-ref-obj="#queryStartTime-zc gt" />
			                                   </div>
			                                 </td>
			                             </tr>
			                             <tr>
			                                 <td class="w140">任务类型</td>
			                                 <td>
					                              <dic:select name="taskWorkType" id="taskWorkType-zc" dictName="taskWorkType" headName="-全部-" headValue="" defaultValue=""/>
			                                 </td>
			                                 <td></td><td></td>               
			                             </tr>                        
			                       </tbody>
			                   </table>
			                   <section class="form-btn m-b-lg">
			                       <button id="queryInfo-zc" class="btn dark" type="button">查 询</button>
			                       <button id="resetInfo-zc" class="btn" type="reset">重 置</button>
			                   </section>
			               </form>
	                    </div>
	               		<%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%>
			            <section class="clearfix" id="insideIn-list-zc">
							   <h2 class="panel-heading clearfix">查询结果</h2>
				               <div class="table-wrap ">
				                   <table class="table table-striped  tab_height" id="taskListTable-zc">
				                       <thead>
				                           <tr>
				                               <th class="w140">任务名称</th>
				                               <th class="w100">任务类型</th>
<!-- 				                               <th class="w100">任务进度</th> -->
				                               <th class="w140">上级任务</th>
				                               <th class="w90">发起人</th>
				                               <th class="w90">负责人</th>
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
	                 <%--暂存  结束--%>
	    </section>
	</section>
  </section>
    <!--汇报弹窗-->
    <div class="modal fade panel" id="report" aria-hidden="false">
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
                                    <td class="w140"><span class="required">*</span>任务进度(%)</td>
                                    <td><input type="text" class="input-mini m-l-none" id="reportProc" name="reportProc"></td>
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
                                    <td class="w140">计划结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
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
                                    <td class="w140">计划结束时间</td>
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
                                    <td class="w140">计划结束时间</td>
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
                        <input type="hidden" id="cancelReceivePersonIds" name="cancelReceivePersonIds" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td class="w140">提醒方式</td>
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
                        <input type="hidden" id="receivePersonIds" name="receivePersonIds" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td class="w140">催办方式</td>
                                    <td>
                                        <label class="radio inline">
                                            <input type="radio" name="remindType" id="optionsRadios" checked="checked" value="2"/>邮件</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>催办内容</td>
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
                        <input type="hidden" id="noReContent" name="content" >
                        <input type="hidden" id="noReTaskEventType" name="taskEventType" value="4">
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td>任务名称</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="noRetaskName" name="taskName" readonly="readonly" ></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="w140">任务来源</td>
                                    <td style="width:35%">
                                        <input type="text" id="noTaskOriName" name="taskOriName" readonly="readonly" />
                                    </td>
                                    <td class="w140"></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>上级任务名称</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="noParentTaskName" name="parentTaskName" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>发起人</td>
                                    <td>
                                        <div class="input-group">
                                        	<input type="text" id="noSponsor" name="sponsor" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td>负责人</td>
                                    <td>
                                        <div class="input-group">
                                        	<input type="text" id="noDirector" name="director" readonly="readonly"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>参与人</td>
                                    <td colspan="3">
                                        <div class="btn-in-area">
                                        	<textarea rows="3" id="noPrticipants" name="prticipants" readonly="readonly"></textarea>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>任务内容</td>
                                    <td colspan="3">
                                        <%--<textarea rows="3" id="noContent" name="content" readonly="readonly"></textarea> --%>
                                        <textarea rows="3" id="noContent" name="noContent" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>完成标准</td>
                                    <td colspan="3">
                                        <textarea rows="3" id="noStandard" name="standard" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>开始时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="noReStartTime" name="startTime"  data-ref-obj="#reEndTime lt" disabled="disabled"/>
                                        </div>
                                    </td>
                                    <td>结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="noReEndTime" name="endTime" data-ref-obj="#reStartTime gt" disabled="disabled"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>超期提醒</td>
                                    <td colspan="3">
                                        <label class="radio inline">
			                                <input type="radio" name="remindType" value="0" disabled="disabled"/> 不提醒
			                            </label>
			                            <label class="radio inline m-l-md">
			                                <input type="radio" name="remindType" value="2" disabled="disabled"/> 邮件
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
                                    <td>汇报时限提醒</td>
                                    <td>
										<input type="hidden" id="notReRemind" name="remind"/>
				                        <a id="notReRemindSet" name="remindSet" class="a-icon i-new" href="#" role="button" data-toggle="modal">提醒设置</a>                                   
                                    </td>
                                </tr>
                                <tr>                                            
                                    <td>附件</td>
                                    <td colspan="3">
                                    	 <ul id="attachList_1"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>不接收原因</td>
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
    <!--编辑弹窗 -->
    <div class="modal fade panel" id="myModal-edit" aria-hidden="false">
        <div class="modal-dialog w900">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">编辑</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="updateWorkTask">
                    	<input type="hidden" id="id" name="id" value="0"/>
                        <input type="hidden" id="modifyDate" name="modifyDate"/>
                        <input type="hidden" id="status" name="status" value="0"/>
                        <input type="hidden" id="parentTaskid" name="parentTaskid"/>
                        <input type="hidden" id="parentStartTime" name="parentStartTime"/>
                        <input type="hidden" id="parentEndTime" name="parentEndTime"/>
                        <input type="hidden" id="taskOrigin" name="taskOrigin" value="0"/>
                        <table class="table table-td-striped">
                            <tbody>
                               <tr>
                                  <td><span class="required">*</span>任务名称</td>
                                          <td colspan="3">
                                              <div class="input-group w-p100">
                                                  <div class="btn-in-area">
                                                       <textarea rows="2" id="modifyTaskName" name="taskName"></textarea>
                                                  </div>
                                              </div>
                                          </td>
                                      </tr>
                                      <tr>
			                        <td>上级任务名称</td>
			                        <td colspan="3">
			                            <textarea rows="2" id="parentTaskName" name="parentTaskName" readonly="readonly"></textarea>
			                        </td>
			                    </tr>
			                    <tr>
			                        <td class="w170">上级任务重要程度</td>
			                        <td>
			                        	<input type="text" id="parentTaskImpName" name="parentTaskImpName" readonly="readonly"/>
			                        </td>
			                        <td  style="width:15%;">任务重要程度</td>
			                        <td>
			                           <div id="inpu">
			                           		<input  type="text" id="parentImplTemp" name="parentImplTemp" value="无" readonly="readonly"/>
			                           </div>
			                           <div id="sele" style="display: none">
			                           		<select id="taskImpCode" name="taskImpCode">
		                                        <option value="8">重要紧急</option>
		                                        <option value="6">重要不紧急</option>
		                                        <option value="4">不重要紧急</option>
		                                        <option value="2">不重要不紧急</option>
		                                    </select>
			                           </div>
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
	                                        	<input type="hidden" id="sponsorId" name="sponsorId"/>
	                                        	<input type="text" id="sponsor" name="sponsor" readonly="readonly"/>
	                                      </td>		                        
			                          </tr>
                                      <tr>
                                          <td><span class="required">*</span>负责人</td>  
                                          <td>
                                          	<div id="showDiTree"></div>
                                          </td> 
                                          <td>参与人</td>
                                          <td>
                                               <div id="prtTree"></div>
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
                                                  <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="updateStartTime" name="startTime" data-ref-obj="#updateEndTime lt"/>
                                              </div>
                                          </td>
                                          <td><span class="required">*</span>结束时间</td>
                                          <td>
                                              <div class="input-group w-p100">
                                                  <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="updateEndTime" name="endTime" data-ref-obj="#updateStartTime gt"/>
                                              </div>
                                          </td>
                                      </tr>
                                      <tr>
				                    	<td>汇报时限(天)</td>
				                        <td class="w276">
					                       <input type="text" class="input-mini m-l-none" id="reportDay" name="reportDay" value="0" onchange="taskInfo.remindMessage();"/><br/>注：任务结束时间前几天以邮件方式提醒发起人和负责人
				                        </td>
				                        <td>完成提醒</td>
				                        <td>
				                        	<label class="radio inline">
				                                <input type="radio" id="isRemind" name="reportType" value="0" checked="checked" /> 否
				                            </label>
				                            <label class="radio inline m-l-md">
				                                <input type="radio" id="notRemind" name="reportType" value="1" /> 是
				                            </label> 
				                        </td>
					                   </tr>
                                       <tr>
                                          <td>加入日程</td>
                                          <td>
                                              <label class="radio inline">
                                                  <input type="radio" name="isImportDiary" value="0" checked="checked" />否 
                                              </label>
                                              <label class="radio inline m-l-md">
                                                  <input type="radio" name="isImportDiary"  value="1"/>是
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
					                            <a class="a-icon i-new m-r-xs" href="#" onclick="taskInfo.showRemindSet();" role="button" data-toggle="modal">设置预览</a> 
                                          </td>
                                      </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="updateTaskInfo">保 存</button>
                    <button class="btn" type="button" id="updateTempTask" style="display: none;">暂 存 </button>
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
                        <input type="hidden" id="taskEventType" name="taskEventType" value="3" />
                        <input type="hidden" id="reTaskEventTitle" name="taskEventTitle" />
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td>任务名称</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="retaskName" name="taskName" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="w140">任务来源</td>
                                    <td style="width:35%">
                                        <input type="text" id="taskOriName" name="taskOriName" readonly="readonly" />
                                    </td>
                                    <td class="w140"></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>上级任务名称</td>
                                    <td colspan="3">
                                        <textarea rows="2" id="parentTaskName" name="parentTaskName" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>发起人</td>
                                    <td>
                                        <div class="input-group">
                                        	<input type="text" id="sponsor" name="sponsor" readonly="readonly"/>
                                        </div>
                                    </td>
                                    <td>负责人</td>
                                    <td>
                                        <div class="input-group">
                                        	<input type="text" id="director" name="director" readonly="readonly"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>参与人</td>
                                    <td colspan="3">
                                        <div class="btn-in-area">
                                        	<textarea rows="3" id="prticipants" name="prticipants" readonly="readonly"></textarea>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>任务内容</td>
                                    <td colspan="3">
                                        <textarea rows="3" id="content" name="content" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>完成标准</td>
                                    <td colspan="3">
                                        <textarea rows="3" id="standard" name="standard" readonly="readonly"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>计划开始时间</td>
                                    <td>
                                        <div class="input-group w-p100">            	
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="reStartTime" name="startTime"  disabled="disabled"/>
                                        </div>
                                    </td>
                                    <td>计划结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="reEndTime" name="endTime"  disabled="disabled"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>实际开始时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input type="hidden" id="nowDate" name="nowDate"/>
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="actStartTime" name="actStartTime" data-ref-obj="#nowDate lt" />
                                        </div>
                                    </td>
                                    <td>实际结束时间</td>
                                    <td>
                                        <div class="input-group w-p100">
                                            <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="actEndTime" name="actEndTime" disabled="disabled"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>超期提醒</td>
                                    <td colspan="3">
                                        <label class="radio inline">
			                                <input type="radio" name="remindType" value="0" disabled="disabled"/> 不提醒
			                            </label>
			                            <label class="radio inline m-l-md">
			                                <input type="radio" name="remindType" value="2" disabled="disabled"/> 邮件
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
                                     <td>汇报时限提醒</td>
                                    <td>
                                    	<input type="hidden" id="reRemind" name="remind"/>
				                        <a id="reRemindSet" name="remindSet" class="a-icon i-new" href="#" role="button" data-toggle="modal">提醒设置</a>
                                    </td>
                                </tr>
                                <tr>                              
                                    <td>附件</td>
                                    <td colspan="3">
                                    	 <ul id="attachList_0"></ul>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="receWorkTask">保 存</button>
                    <button class="btn" type="button" data-dismiss="modal" id="closeReceTask">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--任务模板弹窗-->
    <div class="modal fade panel" id="new-agency" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" onclick="taskInfo.closeWin();">×</button>
                    <h4 class="modal-title">任务模板</h4>
                </div>
                <div class="modal-body">
                    <section class="panel-tab-con">
                        <!-- tabs -->
                        <section class="tabs-wrap tabs-wrap-in">
                            <!-- 同时添加 tabs-wrap-in 类-->
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#messages1" data-toggle="tab" id="tempTask" onclick="taskInfo.getWorkTaskZC();">暂存任务</a>
                                </li>
                                <!-- 根据a标签href属性相对应的id，切换tab-pane -->
                                <li>
                                    <a href="#messages2" data-toggle="tab" id="issuedTask" onclick="taskInfo.getWorkTask();">已发任务</a>
                                </li>
                            </ul>
                        </section>
                        <!-- tabs end -->
                        <!-- tabs模块下紧接panel模块 -->
                        <div class="tab-content">
                            <div class="tab-pane fade active in " id="messages1">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                <form class="table-wrap">
                                    <table class="table table-striped" id="workTaskTable">
                                    	<input type="hidden" id="taskNameVal" name="taskNameVal"/>
    	                                <input type="hidden" id="taskStatus" name="taskStatus"/>
                                        <thead>
                                            <th style="width:10%;"></th>
                                            <th>任务名称</th>
                                            <th class="w60">操作</th>
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
                                            <th style="width:10%;"></th>
                                            <th>任务名称</th>
                                            <th class="w60">操作</th>
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
                    <button class="btn" type="button" data-dismiss="modal" onclick="taskInfo.closeWin();">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <%--选择计划弹窗---%>
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
                    <button type="button" class="close" data-dismiss="modal" onclick="taskInfo.showRemindSetClose()">×</button>
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
	                       			<textarea id="remindCon" name="remindContent"></textarea>
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
     <!--设置预览  接收任务-->
    <div class="modal fade panel" id="remindWindowForRe" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">设置预览</h4>
                </div>
                <div class="modal-body ">
                    <table class="table table-td-striped form-table">
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
                <div class="modal-body">
                    <table class="table table-td-striped form-table">
                       <tbody>
                        <tr>
                       		<td class="w140">被提醒人</td>
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
					<input type="hidden" name="businessType" id="businessType" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource"/>
					<input type="hidden" name="businessTable" id="businessTable"  value="TTY_PO_TASK"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="0"> 
	                <input type="hidden" id="iscopy" value="0"> 
	                <input type="hidden" id="upload_div_name" value="file-edit" />
                	<input type="hidden" id="upload_close_callback" value="taskInfo.fileupload">
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
<script src="${sysPath}/js/com/po/workTask/taskQuery.js" type="text/javascript"></script>
<%@include file="/WEB-INF/web/include/foot.jsp"%>