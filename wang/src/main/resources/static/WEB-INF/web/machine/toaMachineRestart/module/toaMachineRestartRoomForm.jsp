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
		<form class="table-wrap  m-20-auto" id="toaMachineRestartRoomForm">
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
								<div id="equipmentNumber"></div>
							</td>
							<td style="width: 15%;"><span class="required">*</span>客户名称</td>
							<td style="width: 35%;">
								<div id="companyName"></div>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>申请人</td>
							<td>
								<div id="applicant"></div>
							</td>
							<td><span class="required">*</span>申请时间</td>
							<td>
								<div id="billDate"></div>
							</td>
						</tr>
						<tr>
							<td>是否前置操作</td>
							<td>
								<div id="chargeExamine"></div>
							</td>
							<td>前置操作内容</td>
							<td>
								<div id="chargeInformation"></div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">设备信息</td>
						</tr>
						<tr>
							<td><span class="required">*</span>机房</td>
							<td>
								<div id="machina"></div>
							</td>
							<td>机柜</td>
							<td>
								<div id="cabinet"></div>
							</td>
						</tr>
						<tr>
							<td>IP</td>
							<td>
								<div id="ip"></div>
							</td>
							<td>SN</td>
							<td>
								<div id="sn"></div>
							</td>
						</tr>
						<tr>
							<td>品牌型号</td>
							<td colspan="3">
								<div id="brand"></div>
							</td>
							<!-- <td>型号</td>
							<td>
								<div id="modelNumber"></div>
							</td> -->
						</tr>
						<tr>
							<td>备注</td>
							<td colspan="3">
								<div id="remark"></div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">操作过程</td>
						</tr>
						<tr>
							<td>到场时间</td>
							<td></td>
							<td>是否重复重启（30分钟内多次重启）</td>
							<td></td>
						</tr>
						<tr>
							<td>是否接显示器</td>
							<td></td>
							<td>上传附件</td>
							<td></td>
						</tr>
						<tr>
							<td>是否报错</td>
							<td></td>
							<td>上传附件</td>
							<td></td>
						</tr>
						<tr>
							<td>开始操作时间</td>
							<td></td>
							<td>操作完成时间</td>
							<td></td>
						</tr>
						<tr>
							<td>扫描码</td>
							<td></td>
							<td>协助处理</td>
							<td></td>
						</tr>
						<tr>
							<td>有无后续操作</td>
							<td></td>
							<td>后续操作类型</td>
							<td></td>
						</tr>
						<tr>
							<td>操作工程师(技术处理人)</td>
							<td>
								<!-- <input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaMachineRestartRoomModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="caozgcs" name="caozgcs" /> -->
								<%-- <input type="text" style="width:100%;"id="caozgcs" name="caozgcs" value="${userName}"> --%>
								<input type="text" style="width:100%;"id="extStr4" name="extStr4" value="${userName}">
							</td>
							<td>主管评分</td>
							<td></td>
						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaMachineRestartRoomModule.saveOrModifyRoom(true)">接 单</a>
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
						<button id="exchangeSubmit" class="btn" onclick="toaMachineRestartRoomModule.restartExchange(true)" type="button" class="btn">提 交</button>
					</div>
				</form>
			</section>
		</section>
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaMachineRestartRoomModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/machine/toaMachineRestart/module/toaMachineRestartRoomForm.js" type="text/javascript"></script>
<%-- <script src="${sysPath}/js/com/machine/toaMachineRestart/toaMachineRestart.validate.js" type="text/javascript"></script> --%>