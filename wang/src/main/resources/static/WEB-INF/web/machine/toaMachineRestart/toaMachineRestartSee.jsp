<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaMachineRestartSee">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">重启操作 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td style="width: 15%;"><span class="required">*</span>工单编号</td>
							<td style="width: 35%;">
								<div id="equipmentNumber" style="font-size:14px;"></div>
							</td>
							<td style="width: 15%;"><span class="required">*</span>客户名称</td>
							<td style="width: 35%;">
								<div id="companyName" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>申请人</td>
							<td>
								<div id="applicant" style="font-size:14px;"></div>
							</td>
							<td><span class="required">*</span>申请时间</td>
							<td>
								<div id="billDate" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>是否前置操作</td>
							<td>
								<div id="chargeExamine" style="font-size:14px;"></div>
							</td>
							<td>前置操作内容</td>
							<td>
								<div id="chargeInformation" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">设备信息</td>
						</tr>
						<tr>
							<td><span class="required">*</span>机房</td>
							<td>
								<div id="machina" style="font-size:14px;"></div>
							</td>
							<td>机柜</td>
							<td>
								<div id="cabinet" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>IP</td>
							<td>
								<div id="ip" style="font-size:14px;"></div>
							</td>
							<td>SN</td>
							<td>
								<div id="sn" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>品牌型号</td>
							<td colspan="3">
								<div id="brand" style="font-size:14px;"></div>
							</td>
							<!-- <td>型号</td>
							<td>
								<div id="modelNumber" style="font-size:14px;"></div>
							</td> -->
						</tr>
						<tr>
							<td>备注</td>
							<td colspan="3">
								<div id="remark" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">操作过程</td>
						</tr>
						<tr>
							<td>到场时间</td>
							<td>
								<div id="warnDate" style="font-size:14px;"></div>
							</td>
							<td>是否重复重启（30分钟内多次重启）</td>
							<td>
								<div id="firstRestart" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>是否接显示器</td>
							<td>
								<div id="isMonitor" style="font-size:14px;"></div>
							</td>
							<td>上传附件</td>
							<td>
								<ul id="attachList"></ul>
							</td>
						</tr>
						<tr>
							<td>是否报错</td>
							<td>
								<div id="isWrong" style="font-size:14px;"></div>
							</td>
							<!-- <td>上传附件</td>
							<td></td> -->
						</tr>
						<tr>
							<td>开始操作时间</td>
							<td>
								<div id="operateDate" style="font-size:14px;"></div>
							</td>
							<td>操作完成时间</td>
							<td>
								<div id="endDate" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>有无后续操作</td>
							<td>
								<div id="regionExamine" style="font-size:14px;"></div>
							</td>
							<td>后续操作类型</td>
							<td>
								<div id="regionInformation" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>扫描码</td>
							<td>
								<div id="scanCode" style="font-size:14px;"></div>
							</td>
							<td>协助处理</td>
							<td>
								<div id="assist" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>操作工程师(技术处理人)</td>
							<td>
								<!-- <div id="caozgcs" style="font-size:14px;"></div> -->
								<div id="extStr4" style="font-size:14px;"></div>
							</td>
							<td>主管评分</td>
							<td>
								<div id="extStr2" style="font-size:14px;"></div>
							</td>
						</tr>
						
						<!-- 
						<tr>
							<td>是否连显示器</td>
							<td>
								<div id="isMonitor" style="font-size:14px;"></div>
							</td>
							<td>设备状态</td>
							<td>
								<div id="deviceStatus" style="font-size:14px;" ></div>
							</td>
						</tr>
						<tr>
							<td>过程中是否有报错</td>
							<td>
								<div id="isWrong" style="font-size:14px;"></div>
							</td>
							<td>错误信息</td>
							<td>
								<div id="errorMessage" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>报错是否修复</td>
							<td>
								<div id="errorRepair" style="font-size:14px;"></div>
							</td>
							<td>操作时间</td>
							<td>
								<div id="operateDate" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>是否超时(10分钟)</td>
							<td>
								<div id="isOvertime" style="font-size:14px;"></div>
							</td>
							<td>情况说明</td>
							<td>
								<div id="description" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>							<td>记录人</td>
							<td>
								<div id="recordPeople" style="font-size:14px;"></div>
							</td>
							<td>完成时间</td>
							<td>
								<div id="endDate" style="font-size:14px;"></div>							</td>
						</tr>						<tr>							<td>机房主管审批 </td>
							<td>
								<div id="chargeExamine" style="font-size:14px;"></div>
							</td>
							<td>问题说明</td>
							<td>
								<div id="chargeInformation" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>区域主管审批 </td>
							<td>
								<div id="regionExamine" style="font-size:14px;"></div>
							</td>
							<td>问题说明</td>
							<td>
								<div id="regionInformation" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>
							<td>扫描码</td>
							<td>
								<div id="scanCode" style="font-size:14px;"></div>
							</td>
							<td>协助处理</td>
							<td>
								<div id="assist" style="font-size:14px;"></div>
							</td>
						</tr>
						<tr>								<td>备注</td>
							<td colspan="3">
								<div id="remark" style="font-size:14px;"></div>							</td>						</tr>
						<tr>
							<td>
								<span><a class="btn dark" type="button" href="#file-edit" name="queryattach" id="attachs" role="button" data-toggle="modal" onclick="clearTabale()">上传文件</a></span>
							</td>
							<td>
							      <ul id="attachList"></ul>
							</td>
						</tr> -->						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<%-- <shiro:hasPermission name="user:saveMachineRestart"><a class="btn dark"  onclick="toaMachineRestartModule.saveOrModify(true)">提  交</a></shiro:hasPermission> --%>
						</section>
					</section>
				</form>
			</section>
			<section class="panel tab-content">
				<form id="toaRestartExchange">
					<div class="table-wrap form-table" id="exchange">
						<table class="table table-td-striped">
							<tr>
								<th style="width:20%;text-align: center;">留言人</th>
								<th style="width:40%;text-align: center;">内容</th>
								<th style="width:40%;text-align: center;">留言时间</th>
							</tr>
							<c:forEach items="${exchangeList}" var="m">
								<tr>
									<td style="text-align: center;font-weight:normal;">${m.name}</td>
									<td>${m.substance}</td>
									<td style="text-align: center;font-weight:normal;">${m.startDate}</td>
								</tr>
							</c:forEach>
						</table>
						<input type="hidden" id="token" name="token"value="${data.token}"> 
						<input type="hidden"id="modifyDate" name="modifyDate"> 
						<input type="hidden"id="name" name="name" value="${talkName}" /> 
						<input type="hidden" id="restartId" name="restartId" value="${idd}"/>
						<input type="hidden" id="serviceName" name="serviceName" value="0"/>
						<textarea id="substance" name="substance" rows="3" cols="3"></textarea>
						<button id="exchangeSubmit" class="btn" onclick=toaMachineRestartSee.restartExchange(true) type="button" class="btn">提 交</button>
					</div>
				</form>
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
				<input type="hidden" name="businessTable" id="businessTable"  value="toa_machine_restart"/> 
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
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaMachineRestartSee.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineRestart/toaMachineRestartSee.js" type="text/javascript"></script>