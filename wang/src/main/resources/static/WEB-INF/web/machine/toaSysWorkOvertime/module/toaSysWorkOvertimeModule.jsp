<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="toaSysWorkOvertimeModuleForm">
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
							<tr>							<td style="width:10%;" class=" b-green-dark b-tc">姓名</td>							<td>								<input type="text"  style="width:100%;"id="name" name="name" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">加班开始时间</td>							<td>								<input data-date-format="yyyy-MM-dd HH:mm:ss" data-pick-time="true" class="datepicker-input"  type="text" name="startTime" id="startTime" style="width:100%;"/>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">加班结束时间</td>							<td>								<input data-date-format="yyyy-MM-dd HH:mm:ss" data-pick-time="true" class="datepicker-input"  type="text" name="endTime" id="endTime" style="width:100%;"/>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">加班小时数</td>							<td>								<input type="text"  style="width:100%;"id="workHour" name="workHour" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">加班内容</td>							<td>								<textarea id="workContent" name="workContent"></textarea>
							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>
								<textarea id="remark" name="remark"></textarea>							</td>						</tr>
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
<script src="${sysPath}/js/com/machine/toaSysWorkOvertime/module/toaSysWorkOvertimeModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaSysWorkOvertime/toaSysWorkOvertime.validate.js" type="text/javascript"></script>