<%@ page language="java" pageEncoding="UTF-8"%>
<div class="modal fade panel" id="Schedule-client" aria-hidden="false">
<input type="hidden" id="agentToken" name="agentToken" value="${agentToken}">
<input type="hidden" id="diaryDelegationJson" name="diaryDelegationJson" value='${diaryDelegationJson}'>
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">日程委托<small class="m-l"><i class="fa fa-question-sign text-red m-r-xs"></i>本功能用于领导指定人员为其代为填写日程，仅领导可见。</small></h4>
			</div>
			<div class="modal-body">
				<form class="form-table"  id="diaryDelegationForm">
					<input name="ddModifyDate" id="ddModifyDate" type="hidden"/>
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td class="w140" class=" b-green-dark b-tc"><span class="required">*</span>选择人员</td>
								<td>
									<input type="hidden" id="delegationId" name="delegationId" value="0"/>
									<input type="hidden" id="mandataryId" name="mandataryId"/>
									<input type="hidden" id="mandataryIdValue" name="mandataryIdValue"/>
									<div id="agentScope"></div>
								</td>
							</tr>
							<tr>
								<td class="w140">提醒方式</td>
								<td>
									<label class="radio inline"> 
										<input type="radio" name="remindWay" id="remindWay1" value="0" checked /> 邮件
									</label> 
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="diaryAgentConfirm">确 定</a>
				<a class="btn" id="diaryAgentCancel">取消委托</a>
				<a class="btn" id="closeAgent">取 消</a>
			</div>
		</div>
	</div>
</div>