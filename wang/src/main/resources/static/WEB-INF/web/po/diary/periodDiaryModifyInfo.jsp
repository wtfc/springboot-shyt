<%@ page language="java" pageEncoding="UTF-8"%>
<div class="modal fade panel" id="period-diary-modify" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">编辑方式</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td class="w140">编辑方式</td>
								<td>
									<label class="radio inline"> 
										<input type="radio" id="periodModify1" name="periodModify" value="0" checked /> 编辑当前事件
									</label> 
									<label class="radio inline m-l-md"> 
										<input type="radio" id="periodModify2" name="periodModify" value="1"/> 编辑所有事件
									</label>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="modifyDiv">确 定</a>
				<a class="btn" id="closeModifyType" data-dismiss="modal">取 消</a> 
			</div>
		</div>
	</div>
</div>