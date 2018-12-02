<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="toaMachineExchangeModuleForm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="actionTitle">编 辑</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="id" name="id" value="0"> 
					<input type="hidden" id="token" name="token" value="${data.token}">
					<input type="hidden" id="modifyDate" name="modifyDate">
					<div class="table-wrap form-table">
						<table class="table table-td-striped">
							<tbody>
								<tr>									<td style="width:10%;" class=" b-green-dark b-tc">工单ID</td>									<td>										<input type="text"  style="width:100%;"id="restartId" name="restartId" />									</td>								</tr>								<tr>									<td style="width:10%;" class=" b-green-dark b-tc">姓名</td>									<td>										<input type="text"  style="width:100%;"id="name" name="name" />									</td>								</tr>								<tr>									<td style="width:10%;" class=" b-green-dark b-tc">留言时间</td>									<td>										<input type="text"  style="width:100%;"id="startDate" name="startDate" />									</td>								</tr>								<tr>									<td style="width:10%;" class=" b-green-dark b-tc">内容</td>									<td>										<input type="text"  style="width:100%;"id="substance" name="substance" />									</td>								</tr>								<tr>									<td style="width:10%;" class=" b-green-dark b-tc">业务表名</td>									<td>										<input type="text"  style="width:100%;"id="serviceName" name="serviceName" />									</td>								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="saveOrModify">保存继续</button>
					<button class="btn" type="button" id="saveAndClose">保存退出</button>
					<button class="btn" type="button" class="close"data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script src="${sysPath}/js/com/machine/toaMachineExchange/module/toaMachineExchangeModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineExchange/toaMachineExchange.validate.js" type="text/javascript"></script>