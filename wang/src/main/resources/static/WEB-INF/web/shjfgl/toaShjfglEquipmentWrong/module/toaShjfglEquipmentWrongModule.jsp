<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="toaShjfglEquipmentWrongModuleForm">
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
							<tr>							<td style="width:10%;" class=" b-green-dark b-tc">工单编号</td>							<td>								<input type="text"  style="width:100%;"id="equipmentNumber" name="equipmentNumber" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户ID</td>							<td>								<input type="text"  style="width:100%;"id="companyId" name="companyId" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户联系人</td>							<td>								<input type="text"  style="width:100%;"id="contact" name="contact" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户联系方式</td>							<td>								<input type="text"  style="width:100%;"id="tel" name="tel" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">下单日期</td>							<td>								<input type="text"  style="width:100%;"id="billDate" name="billDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">操作开始时间</td>							<td>								<input type="text"  style="width:100%;"id="startDate" name="startDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">操作完成时间</td>							<td>								<input type="text"  style="width:100%;"id="endDate" name="endDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">设备ID</td>							<td>								<input type="text"  style="width:100%;"id="equipmentId" name="equipmentId" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">设备信息核对结果</td>							<td>								<input type="text"  style="width:100%;"id="equipmentCheckResult" name="equipmentCheckResult" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">是否上报处理</td>							<td>								<input type="text"  style="width:100%;"id="isReport" name="isReport" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">是否存在后续操作</td>							<td>								<input type="text"  style="width:100%;"id="haveAfterOperate" name="haveAfterOperate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">工单类型（17.设备故障 18、网络故障）</td>							<td>								<input type="text"  style="width:100%;"id="operationType" name="operationType" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">工单类型图标名称（17.设备故障 18、网络故障）</td>							<td>								<input type="text"  style="width:100%;"id="operationTypeImg" name="operationTypeImg" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">机房</td>							<td>								<input type="text"  style="width:100%;"id="machina" name="machina" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">操作人员ID</td>							<td>								<input type="text"  style="width:100%;"id="caozgcs" name="caozgcs" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">操作人员姓名</td>							<td>								<input type="text"  style="width:100%;"id="caozgcsName" name="caozgcsName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">处理状态</td>							<td>								<input type="text"  style="width:100%;"id="status" name="status" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<input type="text"  style="width:100%;"id="remark" name="remark" />							</td>						</tr>
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
<script src="${sysPath}/js/com/machine/toaShjfglEquipmentWrong/module/toaShjfglEquipmentWrongModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaShjfglEquipmentWrong/toaShjfglEquipmentWrong.validate.js" type="text/javascript"></script>