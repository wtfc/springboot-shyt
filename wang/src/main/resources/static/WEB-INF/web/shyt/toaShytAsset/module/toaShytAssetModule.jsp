<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="toaShytAssetModuleForm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="actionTitle">编 辑</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="id" name="id" value="0"> <input
						type="hidden" id="token" name="token" value="${data.token}">
					<input type="hidden" id="modifyDate" name="modifyDate">
					<div class="table-wrap form-table">
						<table class="table table-td-striped">
							<tbody>
								<tr>
									<td style="width:10%;" class=" b-green-dark b-tc">资产名称</td>
									<td><input type="text" style="width:100%;" id="assetsName"
										name="assetsName" /></td>
									<td style="width:10%;" class=" b-green-dark b-tc">类别</td>
									<td><select style="width:100%;" id="type"name="type">
											<option value="0">办公家具</option>
											<option value="1">办公设备</option>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:10%;" class=" b-green-dark b-tc">品牌</td>
									<td><input type="text" style="width:100%;" id="bard"
										name="bard" /></td>
									<td style="width:10%;" class=" b-green-dark b-tc">资产编号</td>
									<td><input type="text" style="width:100%;" id="assetsNum"
										name="assetsNum" /></td>
								</tr>
								<tr>
									<td style="width:10%;" class=" b-green-dark b-tc">设备参数</td>
									<td><input type="text" style="width:100%;" id="machineNum"
										name="machineNum" /></td>
									<td style="width:10%;" class=" b-green-dark b-tc">单位</td>
									<td><input type="text" style="width:100%;" id="unit"
										name="unit" /></td>
								</tr>
								<tr>
									<td style="width:10%;" class=" b-green-dark b-tc">数量</td>
									<td><input type="text" style="width:100%;" id="number"
										name="number" /></td>
									<td style="width:10%;" class=" b-green-dark b-tc">单价</td>
									<td><input type="text" style="width:100%;" id="price"
										name="price" /></td>
								</tr>
								<tr>
									<td style="width:10%;" class=" b-green-dark b-tc">入库日期</td>
									<td><input type="text" style="width:100%;" id="inDate"
										name="inDate" /></td>
									<td style="width:10%;" class=" b-green-dark b-tc">使用部门</td>
									<td><input type="text" style="width:100%;" id="useDept"
										name="useDept" /></td>
								</tr>
								<tr>
									<td style="width:10%;" class=" b-green-dark b-tc">存放地点</td>
									<td><input type="text" style="width:100%;" id="address"name="address" /></td>
									<td style="width:10%;" class=" b-green-dark b-tc">调拨内容</td>
									<td><textarea  style="width:100%;"id="tiaoboneirong" name="tiaoboneirong" ></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="saveOrModify">保存继续</button>
					<button class="btn" type="button" id="saveAndClose">保存退出</button>
					<button class="btn" type="button" class="close"
						data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script
	src="${sysPath}/js/com/shyt/toaShytAsset/module/toaShytAssetModule.js"
	type="text/javascript"></script>
<script
	src="${sysPath}/js/com/shyt/toaShytAsset/toaShytAsset.validate.js"
	type="text/javascript"></script>