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
		<form class="table-wrap  m-20-auto" id="toaMachineRestartDirectorForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">重启操作 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td>工单编号</td>
							<td>
								<div id="equipmentNumber"></div>
							</td>
							<td>客户名称</td>
							<td>
								<div id="companyName"></div>
							</td>
						</tr>
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
						<tr>
							<td>客户联系人</td>
							<td>
								<div id="contact"></div>
							</td>
							<td>客户联系方式</td>
							<td>
								<div id="tel" style="display: inline;"></div>&nbsp;&nbsp;
								<div id="extStr2" style="display: inline;"></div>
							</td>
						</tr>
						<tr>
							<td>IP地址（机柜）</td>
							<td>
								<div id="ip"></div>
							</td>
							<td>报障时间</td>
							<td>
								<div id="warnDate"></div>
							</td>
						</tr>
						<tr>
							<td>是否连显示器</td>
							<td>
								<div id="isMonitor"></div>
							</td>
							<td>设备状态</td>
							<td>
								<div id="deviceStatus"></div>
							</td>
						</tr>
						<tr>
							<td>过程中是否有报错</td>
							<td>
								<div id="isWrong"></div>
							</td>
							<td>错误信息</td>
							<td>
								<div id="errorMessage"></div>
							</td>
						</tr>
						<tr>
							<td>报错是否修复</td>
							<td>
								<div id="errorRepair"></div>
							</td>
							<td>操作时间</td>
							<td>
								<div id="operateDate"></div>
							</td>
						</tr>
						<tr>
							<td>是否超时(10分钟)</td>
							<td>
								<div id="isOvertime"></div>
							</td>
							<td>情况说明</td>
							<td>
								<div id="description"></div>
							</td>
						</tr>
						<tr>
							<td>技术处理人</td>
							<td>
								<div id="caozgcs"></div>
							</td>
							<td>完成时间</td>
							<td>
								<div id="endDate"></div>
							</td>
						</tr>
						<tr>
							<td>记录人</td>
							<td>
								<div id="recordPeople"></div>
							</td>
						</tr>
						<tr>
							<td>机房主管审批 </td>
							<td>
								<div id="chargeExamine"></div>
							</td>
							<td>问题说明</td>
							<td>
								<div id="chargeInformation"></div>
							</td>
						</tr>
						<tr>
							<td>区域主管审批 </td>
							<td>
								<select id="regionExamine" name="regionExamine" style="width:100%;" >
								 	<option value="0">不通过</option>
								 	<option value="1">通过</option>
								</select>
							</td>
							<td>问题说明</td>
							<td>
								<textarea id="regionInformation" name="regionInformation"  ></textarea>
							</td>
						</tr>
						<tr>	
							<td>备注</td>
							<td colspan="3">
								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>
							</td>
						</tr>

						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaMachineRestartDirectorModule.saveOrModifyDirector(true)">提  交</a>
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
						<button id="exchangeSubmit" class="btn" onclick="toaMachineRestartDirectorModule.restartExchange(true)" type="button" class="btn">提 交</button>
					</div>
				</form>
			</section>
		</section>
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaMachineRestartDirectorModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/machine/toaMachineRestart/module/toaMachineRestartDirectorForm.js" type="text/javascript"></script>
<%-- <script src="${sysPath}/js/com/machine/toaMachineRestart/toaMachineRestart.validate.js" type="text/javascript"></script> --%>