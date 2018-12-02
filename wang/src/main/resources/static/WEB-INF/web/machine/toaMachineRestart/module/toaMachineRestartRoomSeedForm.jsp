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
		<form class="table-wrap  m-20-auto" id="toaMachineRestartRoomSeedForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">重启操作 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td>工单编号</td>							<td>								<div id="equipmentNumber"></div>							</td>							<td>客户名称</td>							<td>								<div id="companyName"></div>							</td>						</tr>
						<tr>
							<td>发单时间</td>
							<td>
								<div id="billDate"></div>
							</td>
							<td>所属机房</td>
							<td>
								<div id="machina"></div>
							</td>
						</tr>
						<tr>							<td>客户联系人</td>							<td>								<div id="contact"></div>							</td>
							<td>客户联系方式</td>
							<td>
								<div id="tel" style="display: inline;"></div>&nbsp;&nbsp;
								<div id="contactWay" style="display: inline;"></div>
							</td>						</tr>						<tr>							<td>IP地址（机柜）</td>							<td>								<div id="ip"></div>							</td>
							<td>SN</td>
							<td>
								<div id="sn"></div>
							</td>
						</tr>
						<tr>							<td>报障时间</td>
							<td>
								<div id="warnDate"></div>
							</td>
							<td>技术处理人</td>
							<td>
								<div id="caozgcs"></div>
								<!-- <input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaMachineRestartRoomModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="caozgcs" name="caozgcs" /> -->
							</td>						</tr>
						<tr>
							<td>是否连显示器</td>
							<td>
								<!-- <select id="isMonitor" name="isMonitor" style="width:100%;"  >
								 	<option value="0">否</option>
								 	<option value="1">是</option>
								</select> -->
								<div id="isMonitor"></div>
							</td>
							<td>设备状态</td>
							<td>
								<!-- <input type="text"  style="width:100%;"id="deviceStatus" name="deviceStatus"  /> -->
								<div id="deviceStatus"></div>
							</td>
						</tr>
						<tr>
							<td>过程中是否有报错</td>
							<td>
								<!-- <select id="isWrong" name="isWrong" style="width:100%;"  >
								 	<option value="0">否</option>
								 	<option value="1">是</option>
								</select> -->
								<div id="isWrong"></div>
							</td>
							<td>错误信息</td>
							<td>
								<!-- <input type="text"  style="width:100%;"id="errorMessage" name="errorMessage"  /> -->
								<div id="errorMessage"></div>
							</td>
						</tr>
						<tr>
							<td>报错是否修复</td>
							<td>
								<!-- <select id="errorRepair" name="errorRepair" style="width:100%;" >
								 	<option value="0">否</option>
								 	<option value="1">是</option>
								</select> -->
								<div id="errorRepair"></div>
							</td>
							<td>操作时间</td>
							<td>
								<div id="operateDate"></div>
								<!-- <input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="true" class="datepicker-input" style="width:100%;"id="operateDate" name="operateDate" /> -->
							</td>
						</tr>
						<tr>
							<td>是否超时(10分钟)</td>
							<td>
								<!-- <select id="isOvertime" name="isOvertime" style="width:100%;" >
								 	<option value="0">否</option>
								 	<option value="1">是</option>
								</select> -->
								<div id="isOvertime"></div>
							</td>
							<td>情况说明</td>
							<td>
								<!-- <input type="text"  style="width:100%;"id="description" name="description"  /> -->
								<div id="description"></div>
							</td>
						</tr>
						<tr>							<td>记录人</td>
							<td>
								<!-- <input type="text"  style="width:100%;"id="recordPeople" name="recordPeople" /> -->
								<div id="recordPeople"></div>
							</td>
							<td>完成时间</td>
							<td>
								<!-- <input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="true" class="datepicker-input" style="width:100%;"id="endDate" name="endDate" /> -->
								<div id="endDate"></div>
							</td>						</tr>
						<tr>
							<td>扫描码</td>
							<td>
								<div id="scanCode"></div>
							</td>
							<td>协助处理</td>
							<td>
								<select id="assist" name="assist">
									<option value="">请选择</option>
									<option value="否">否</option>
									<option value="是">是</option>
								</select>
							</td>
						</tr>						<!-- <tr>							<td>机房主管审批 </td>
							<td></td>
							<td>问题说明</td>
							<td></td>
						</tr>
						<tr>
							<td>区域主管审批 </td>
							<td></td>
							<td>问题说明</td>
							<td></td>
						</tr> -->
						<tr>								<td>备注</td>
							<td colspan="3">								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>
						<tr>
							<td>
								<!-- <span><a class="btn dark" type="button" href="#file-edit" name="queryattach" id="attachs" role="button" data-toggle="modal" onclick="clearTabale()">上传文件</a></span> -->
							</td>
							<td>
							      <ul id="attachList"></ul>
							</td>
						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaMachineRestartRoomSeedModule.saveOrModifyRoomSeed(true)">提  交</a>
						</section>
					</section>
				</form>
			</section>
			<section class="panel tab-content">
				<form id="toaMachineRestartExchange">
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
						<button id="exchangeSubmit" class="btn" onclick="toaMachineRestartRoomSeedModule.restartExchange(true)" type="button" class="btn">提 交</button>
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
		toaMachineRestartRoomSeedModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineRestart/module/toaMachineRestartRoomSeedForm.js" type="text/javascript"></script>
<%-- <script src="${sysPath}/js/com/machine/toaMachineRestart/toaMachineRestart.validate.js" type="text/javascript"></script> --%>