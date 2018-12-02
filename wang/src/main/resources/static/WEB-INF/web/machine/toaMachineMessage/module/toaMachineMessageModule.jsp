<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="toaMachineMessageModuleForm">
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
							<tr>							<td style="width:10%;" class=" b-green-dark b-tc">消息编号</td>							<td>								<input type="text"  style="width:100%;"id="messageNumber" name="messageNumber" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">消息标题</td>							<td>								<input type="text"  style="width:100%;"id="messageTitle" name="messageTitle" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">消息内容</td>							<td>								<input type="text"  style="width:100%;"id="messageContent" name="messageContent" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">接收部门ID</td>							<td>								<input type="text"  style="width:100%;"id="receivedDeptId" name="receivedDeptId" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">消息所属工单类型</td>							<td>								<input type="text"  style="width:100%;"id="messageType" name="messageType" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<input type="text"  style="width:100%;"id="remark" name="remark" />							</td>						</tr>
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
<script src="${sysPath}/js/com/machine/toaMachineMessage/module/toaMachineMessageModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineMessage/toaMachineMessage.validate.js" type="text/javascript"></script>