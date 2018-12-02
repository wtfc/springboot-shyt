<%@ page language="java" pageEncoding="UTF-8"%>
<div class="modal fade panel" id="The-new-agenda" aria-hidden="false" ><!-- data-back="diaryMyCalendar.closeX();" -->
<input type="hidden" id="token" name="token" value="${token}">
<input type="hidden" id="diaryJsonModify"  name="diaryJsonModify"  value='${diaryJsonModify}'/>
	<div class="modal-dialog w900">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onclick="schedule.closeAddX();">×</button>
				<h4 class="modal-title" id="formTitle">新增</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td><span class="required">*</span>日程标题</td>
								<td colspan="3"><input type="text" id="title" name="title" ></td>
							</tr>
							<tr>
								<td style="width:15%;"><span class="required">*</span>开始时间</td>
								<td style="width:35%;">
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" id="starttime" name="starttime"
											data-date-format="yyyy-MM-dd HH:mm:ss" data-pick-time="true" data-ref-obj="#endtime lt">
									</div>
								</td>
								<td style="width:15%;"><span class="required">*</span>结束时间</td>
								<td>
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" id="endtime" name="endtime"
											data-date-format="yyyy-MM-dd HH:mm:ss" data-pick-time="true" data-ref-obj="#starttime gt">
									</div>
								</td>
							</tr>
							<tr>
								<td>周期模式</td>
								<td><a class="a-icon i-new m-r-xs" id="advancedSettings" 
									href="#" role="button" data-toggle="modal"><!-- <i
										class="fa fa-cog"></i> -->高级设置</a></td>
								<td>提醒设置</td>
								<td>
									<input type="hidden" id="remind" name="remind"/>
	                       			<a id="remindSet" name="remindSet" class="a-icon i-new m-r-xs" href="#" role="button" data-toggle="modal">
	                    				<!--<i class="fa fa-alarm"></i>-->提醒设置
	                       			</a>
								</td>
							</tr>
							<tr>
								<td><span class="required">*</span>内容</td>
								<td colspan=3><textarea id="content" name="content" wrap="hard"></textarea></td>
							</tr>
							<tr id="share">
								<td>共享范围</td>
								<td colspan="3">
									<!-- <div class="input-group w-p100">
										<div class="btn-in-area">
											<input id="shareScopeId" name="shareScopeId" type="hidden"/>
	                                		<textarea id="shareScope" name="shareScope" rows="3"></textarea>
	                                		<a id="showShareScopeTree" class="btn btn-file input-group-btn" href="#">
	                                			<i class="fa fa-user"></i> 
	                                		</a>
										</div>
									</div>-->
									<input id="shareScopeId" name="shareScopeId" type="hidden"/>
									<div id="shareScope"></div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="savaOrModify">保存继续</a>
				<a class="btn" id="savaAndClose">保存退出</a>
				<a class="btn" id="cancel">关 闭</a>
			</div>
		</div>
	</div>
</div>
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