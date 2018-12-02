<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<script type="text/javascript" src="${sysPath}/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${sysPath}/js/ueditor/ueditor.all.min.js"> </script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<section class="panel m-t-md" id="body">
			<h3 class="tc" style="margin:0;border:0;">项目信息 </h3>
			<section class="dis-table">
             <section class="panel-tab-con dis-table-cell">
                <form class="table-wrap  " id="monitorForm">
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="status" name="status">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td style="width:10%;" class=" b-green-dark b-tc"><span
								class="required">*</span>项目名称</td>
							<td ><input  type="text" style="width:100%;"
								id="name" name="name" /></td>
							<td style="width:10%;"><span class="required">*</span>所属部门</td>
							<td style="width:45%">
								<input type="text"  style="width:100%;"
								id="extStr1" name="extStr1" value="${dept}"/>
							</td>
						</tr>
						<tr>
							<td style="width:10%;" class=" b-green-dark b-tc"><span class="required">*</span>负责人</td>
							<td ><input type="text" style="width:100%;"
								id="extStr2" name="extStr2" value="${name}"/></td>
							<td>
								<span><a class="btn dark" type="button"
										href="#file-edit" name="queryattach" id="attachs"
										role="button" data-toggle="modal" onclick="clearTabale()">需求文档</a></span>
							</td>
							<td>
								<ul id="attachList"></ul>
							</td>
						</tr>
						<tr>
							<td style="width:10%;"><span class="required">*</span>立项时间</td>
							<td >
							<input class="datepicker-input"style="width:100%;" type="text" id="startDate" name = "startDate" data-pick-time="true" data-date-format="yyyy-MM-dd" />
							</td>
							<td><span class="required">*</span>期望完成时间</td>
							<td>
								<input class="datepicker-input"style="width:100%;" type="text" id="endDate" name = "endDate" data-pick-time="true" data-date-format="yyyy-MM-dd" />
							</td>
						</tr>
						<c:if test="${!empty status}">
						<tr>
							<td><span class="required">*</span>项目开发负责人</td>
							<td>
								<input type="text"  name="leared" onFocus="selectControl.singlePerson(this.id, true, 'monitorModule.spCall', eval($('#userJson').val()));"style="width:100%;" id="leared"/>
							</td>
							<td><span class="required">*</span>所需开发人员</td>
							<td>
							<textarea  name="people" id="people" 
							onFocus="selectControl.singlePerson(this.id, true, 'monitorModule.spCall', eval($('#userJson').val()));"
							></textarea>
							</td>
						</tr>
						</c:if>
						<tr>
							<td>需求说明</td>
							<td colspan="3">
							<div class="email-compile">
							<textarea id="operate" name="operate" ></textarea>
							</div>
							</td>
						</tr>
						</tbody>
					</table>
					<c:if test="${status eq '0'}">
					<div class=" form-btn">
					<button class="btn dark" type="button" onclick=monitorModule.saveOrModify(true) >提 交</button>
					</div>
					</c:if>
					<c:if test="${empty id}">
					<div class=" form-btn">
					<button class="btn dark" type="button" onclick=monitorModule.saveOrModify(true) >提 交</button>
					</div>
					</c:if>
					</form>
					<form class="table-wrap  " id="monitorForm1">
					<c:if test="${status eq '1'}">
					<div class="table-wrap form-table" id="exchange">
						<c:forEach items="${exchangeList}" var="m">
						<div>
							<span id="neirong"><span style="font-size:18px">${m.name }:</span>${m.content}&nbsp&nbsp&nbsp&nbsp${m.startDate}<br/>
							</span>
						</div>
						</c:forEach>
						<input type="hidden" id="token" name="token"value="${data.token}"> 
						<input type="hidden"id="modifyDate" name="modifyDate"> 
						<input type="hidden"id="name" name="name" value="${talkName}" /> 
						<input type="hidden" id="equipmentId" name="equipmentId" value="${idd}"/>
						<input type="hidden" id="extStr1" name="extStr1" value="toa_project_monitor"/>
						<textarea id="content" name="content" rows="3" cols="3"></textarea>
						<%-- <shiro:hasPermission name="user:add4"> --%>
							<button id="exchangeSubmit" class="btn"
								onclick=monitorModule.equipmentInOutForm3(true)
								type="button" class="btn">提 交</button>
						<%-- </shiro:hasPermission> --%>
					</div>
					</c:if>
					<c:if test="${status eq '2'}">
					<div class="table-wrap form-table" id="exchange">
						<c:forEach items="${exchangeList}" var="m">
						<div>
							<span id="neirong"><span style="font-size:18px">${m.name }:</span>${m.content}&nbsp&nbsp&nbsp&nbsp${m.startDate}<br/>
							</span>
						</div>
						</c:forEach>
					</div>
					</c:if>
				</form>
			</section>
		</section>
	</section>
	<!--start 上传附件  -->
	<div class="modal fade panel" id="file-edit" aria-hidden="false">
		<div class="modal-dialog w820">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
					<h4 class="modal-title">添加上传文件</h4>
				</div>
				<div class="modal-body">
					<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
					<!-- islogicDel 1为逻辑删除 0为物理删除-->
					<input type="hidden" id="islogicDel" value="1">
					<input type="hidden" id="iscopy"  value="0">
					<input type="hidden" name="businessId" id="businessId" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource" />
					<input type="hidden" name="businessTable" id="businessTable"  value="toa_shjfgl_customer"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="0">
	                <input type="hidden" id="upload_div_name" value="file-edit">
					<!-- <input type="hidden" id="upload_close_callback" value="equipmentInOutModule.echoAttachList">  --> 
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button"  id="closeModalBtn" onclick="showAttachList(true)" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
</section>

<c:if test="${!empty id}">
<script >
$(document).ready(function(){
	var ids=(${id});
	if(ids!=null&&ids!=""){
	monitorModule.get(ids);
	}
});
function clearTabale(){
	clearTable();
}
</script>
</c:if>
<c:if test="${empty id}">
<script >
$(document).ready(function(){
	getUE();
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/oa/project/monitor/module/monitorModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/oa/project/monitor/monitor.validate.js"type="text/javascript"></script>