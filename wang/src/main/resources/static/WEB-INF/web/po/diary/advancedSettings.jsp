<%@ page language="java" pageEncoding="UTF-8"%>
<!--  高级设置弹窗-------->
<div class="modal fade panel" id="Advanced-settings" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onclick="schedule.closeX();">×</button>
				<h4 class="modal-title">高级设置</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td><span class="required">*</span>周期类型</td>
								<td>
									<label class="radio inline"> 
										<input type="radio" id="periodType0" name="periodTypeTmp" value="0" checked/>无定期
									</label> 
									<label class="radio inline">
										<input type="radio" id="periodType1" name="periodTypeTmp" value="1"/>按天
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="periodType2" name="periodTypeTmp" value="2"/>按周
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="periodType3" name="periodTypeTmp" value="3"/>按月
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="periodType4" name="periodTypeTmp" value="4"/>按年
									</label>
								</td>
							</tr>
							<tr id="periodTr" style="display:none" ></tr>
							<tr>
								<td nowrap><span class="required">*</span>周期开始时间</td>
								<td>
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" id="periodStartdate" name="periodStartdate"
											data-date-format="yyyy-MM-dd" data-ref-obj="#periodEnddate lt">
									</div>
								</td>
							</tr>
							<tr>
								<td nowrap><span class="required">*</span>周期结束时间</td>
								<td>
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" id="periodEnddate" name="periodEnddate"
											data-date-format="yyyy-MM-dd" data-ref-obj="#periodStartdate gt">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="periodConfirm">确 定</a>
				<a class="btn" id="periodCancel">取 消</a>
			</div>
		</div>
	</div>
</div>