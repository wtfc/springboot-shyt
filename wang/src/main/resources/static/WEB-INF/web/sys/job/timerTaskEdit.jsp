<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title" id="subTitle">新增</h4>
			</div>
			<div class="modal-body">
				<form class="table-wrap form-table" id="timerTaskForm">
				<input type="hidden" id="id" name="id">
				<input type="hidden" id="token" name="token" value="${token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td><span class="required">*</span>任务类型</td>
								<td><div><input type="text" name="groupName" id="groupName"/></div></td>
							</tr>
							<tr>
								<td><span class="required">*</span>任务类名称</td>
								<td><div><input type="text" name="jobClassName" id="jobClassName"/></div></td>
							</tr>
							<tr>  
								<td>规则
								</td>
								<td>
								<select id="cycleType" name="cycleType">
									<option value='1' selected>固定间隔</option>
									<option value='2'>周期循环</option>
									<option value='3'>一次性</option>
								</select>
								</td>
							</tr>
							<tr id="intervalTime">  
								<td><span class="required">*</span>间隔时间</td>
								<td>
									<div style="float:left"><label><input type="text"  style="width:80px;" id="intervalHours" name="intervalHours">小时</label></div>
									<div style="float:left"><label><input type="text"  style="width:80px;" id="intervalMinutes" name="intervalMinutes">分</label></div>
								</td>
							</tr>
							<tr id="taskCount">  
								<td>任务次数</td>
								<td>
									<div style="float:left"><label><input type="text" style="width:80px;" id="taskCounts" name="taskCounts">次</label></div>
								</td>
							</tr>
							<tr id="cycle">  
								<td>提醒周期</td>
								<td>
									<input type="radio" class="cycleSelect" name="cycleSelect" checked value="day">天
									<input type="radio" class="cycleSelect" name="cycleSelect" value="week">周
									<input type="radio" class="cycleSelect" name="cycleSelect" value="month">月
									<input type="radio" class="cycleSelect" name="cycleSelect" value="year">年
								</td>
							</tr>
							<tr id="week">  
								<td><span class="required">*</span>周期</td>
								<td>
									<div style="float:left"><label>每个<br>
										<input type="checkbox" class="weekly" name="weekly" value="1"/>星期日
										<input type="checkbox" class="weekly" name="weekly" value="2"/>星期一
										<input type="checkbox" class="weekly" name="weekly" value="3"/>星期二
										<input type="checkbox" class="weekly" name="weekly" value="4"/>星期三<br>
										<input type="checkbox" class="weekly" name="weekly" value="5"/>星期四
										<input type="checkbox" class="weekly" name="weekly" value="6"/>星期五
										<input type="checkbox" class="weekly" name="weekly" value="7"/>星期六</label>
									</div>
								</td>
							</tr>
							<tr id="month">  
								<td>周期</td>
								<td>
								    <input type="radio" name="monthly" checked value="monthlyDay"/>每月
								    <select id="monthlyDay" name="monthlyDay" style="width:80px;">
									</select>日<br>
									<input type="radio" name="monthly" value="monthlyLastDay"/>每月最后一天<br>
									<input type="radio" name="monthly" value="monthlyDayWeek"/>每月
									<select id="monthlyLast" name="monthlyLast" style="width:80px;">
										<option value='1' selected>第一个</option>
										<option value='2'>第二个</option>
										<option value='3'>第三个</option>
										<option value='L'>最后一个</option>
									</select>
									<select id="monthlyLastWeek" name="monthlyLastWeek" style="width:80px;">
										<option value='1' selected>星期日</option>
										<option value='2'>星期一</option>
										<option value='3'>星期二</option>
										<option value='4'>星期三</option>
										<option value='5'>星期四</option>
										<option value='6'>星期五</option>
										<option value='7'>星期六</option>
									</select>
								</td>
							</tr>
							<tr id="year">  
								<td>周期</td>
								<td>
								    <input type="radio" name="yearly" checked value="yearlyMD"/>每年
								    <select id="yearlyMonthForDay" name="yearlyMonthForDay" style="width:80px;">
									</select>月
									<select id="yearlyDay" name="yearlyDay" style="width:80px;">
									</select>日<br>
									<input type="radio" name="yearly" value="yearlyMonthL"/>每年
								    <select id="yearlyMonthForLast" name="yearlyMonthForLast" style="width:80px;">
									</select>月 最后一天<br>
									<input type="radio" name="yearly" value="yearlyLW"/>每年
									<select id="yearlyMonthForWeek" name="yearlyMonthForWeek" style="width:80px;">
									</select>月
									<select id="yearlyLast" name="yearlyLast" style="width:80px;">
										<option value='1' selected>第一个</option>
										<option value='2'>第二个</option>
										<option value='3'>第三个</option>
										<option value='L'>最后一个</option>
									</select>
									<select id="yearlyLastWeek" name="yearlyLastWeek" style="width:80px;">
										<option value='1' selected>星期日</option>
										<option value='2'>星期一</option>
										<option value='3'>星期二</option>
										<option value='4'>星期三</option>
										<option value='5'>星期四</option>
										<option value='6'>星期五</option>
										<option value='7'>星期六</option>
									</select>
								</td>
							</tr>
							<tr id="perTime">  
								<td><span class="required">*</span>每天</td>
								<td>
									<div style="float:left;"><label><input type="text" style="width:80px;" id="perHours" name="perHours">时</label></div>
									<div style="float:left"><label><input type="text" style="width:80px;" id="perMinutes" name="perMinutes">分</label></div>
								</td>
							</tr>
							<tr>
								<td><span class="required">*</span>开始时间</td>
								<td><div class="input-group"><label><input class="datepicker-input" type="text" id="startAt" name="startAt" data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss" data-ref-obj="#endAt lt"></label></div></td>
							</tr>
							<tr id="endTime">
								<td>结束时间</td>
								<td><div class="input-group"><label><input class="datepicker-input" type="text" id="endAt" name="endAt" data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss" data-ref-obj="#startAt gt"></label></div></td>
							</tr>
							<tr>  
								<td>任务详情</td>
								<td><label><textarea rows="2" name="description" placeholder="详情..."></textarea></label></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="timerTaskbtn">保&nbsp;存</button>
			  <button type="button" class="btn" data-dismiss="modal">关&nbsp;闭</button>
			</div>
		</div>
	</div>
</div>
<script src="${sysPath}/js/com/sys/job/timerTaskEdit.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/job/timerTaskManage.validate.js" type="text/javascript"></script>