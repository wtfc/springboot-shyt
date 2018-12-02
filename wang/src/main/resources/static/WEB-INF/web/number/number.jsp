<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<script src="${sysPath}/js/com/number/rule.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in">
		<div class="con-heading fl">
			<h1>编号设置</h1>
			<div class="crumbs">
				<a href="#">首页</a><i></i>编号设置
			</div>
		</div>
		<a class="btn dark fr" href="#" id="showCreate_t" role="button"
			data-toggle="modal">新 增</a>
	</header>
	<section class="panel m-t-md">
		<h2 class="panel-heading clearfix ">编号设置</h2>
		<div class="table-wrap">
			<table class="table table-striped tab_height first-td-tc">
				<thead>
					<tr>
						<th>规则名称</th>
						<th>日期格式</th>
						<th>日期格式<br />分隔符
						</th>
						<th>流水号<br />初始值
						</th>
						<th>流水号<br />位数
						</th>
						<th>当前<br />流水号
						</th>
						<th>流水号<br />上限值
						</th>
						<th>流水号<br />补位符
						</th>
						<th>重置<br />规则
						</th>
						<th>是否<br />连续
						</th>
						<th>参数</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="rules">

				</tbody>
			</table>
		</div>
		<section class="clearfix">
			<section class="form-btn fl m-l">
				<a class="btn dark" href="#" id="showCreate" role="button"
					data-toggle="modal">新 增</a>
				<button class="btn " type="submit">批量删除</button>
			</section>
		</section>
	</section>
</section>
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">编辑</h4>
			</div>
			<div class="modal-body">
				<form class="table-wrap form-table" id="ruleForm" name="ruleForm">
					<input type="hidden" name="actionType" id="actionType"> <input
						type="hidden" name="action" id="action">
					<table class="table table-td-striped">
						<tr>
							<td>规则名称</td>
							<td><input type="text" id="ruleName" name="ruleName"></td>
							<td>占位符</td>
							<td><input type="text" name="v1" id="v1"></td>
						</tr>
						<tr>
							<td>所属模块</td>
							<td><dic:select name="numberModule" id="numberModule"
									dictName="numberModule" headName="-请选择-" headValue=""
									defaultValue="" /></td>
							<td>显示名称</td>
							<td><input type="text" name="ruleDisplayname"
								id="ruleDisplayname"></td>
						</tr>
						<tr>
							<td>日期格式</td>
							<td>默认选项：<input type="radio" name="dateType" id="r1"
								value="1" checked="true">手动配置：<input type="radio"
								name="dateType" id="r2" value="2"></td>
							<td></td>
							<td></td>
						</tr>
						<tbody id="default">
							<tr>
								<td>格式</td>
								<td><select name="select" id="defaultDate">
										<option value="yyyy-MM-dd">yyyyMMdd</option>
										<option value="yy-MM-dd">yyMMdd</option>
										<option value="yy-M-d">yyMd</option>
										<option value="yyyy-M-d">yyyyMd</option>
										<option value="">无</option>
								</select></td>

								<td>日期分隔符</td>
								<td><select name="select" id="defaultSplit">
										<option value="">无</option>
										<option value="-">-</option>
										<option value="/">/</option>
										<option value=".">.</option>
										<option value="年-月-日">年月日</option>
								</select></td>
							</tr>
							<tr>
								<td>占位符</td>
								<td><input type="text" name="v2" id="v2"></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
						<tbody id="manual" style="display:none">
							<tr>
								<td>格式</td>
								<td><select name="year" id="year">
										<option value="yyyy">年&nbsp;&nbsp;&nbsp;&nbsp;</option>
										<option value="">无</option>
								</select></td>

								<td>占位符</td>
								<td><input type="text" name="v3" id="v3"></td>
							</tr>
							<tr>
								<td></td>
								<td><select name="month" id="month">
										<option value="MM">月&nbsp;&nbsp;&nbsp;&nbsp;</option>
										<option value="">无</option>
								</select></td>
								<td>占位符</td>
								<td><input type="text" name="v4" id="v4" /></td>

							</tr>
							<tr>
								<td></td>
								<td><select name="day" id="day">
										<option value="dd">日&nbsp;&nbsp;&nbsp;&nbsp;</option>
										<option value="">无</option>
								</select></td>
								<td>占位符</td>
								<td><input type="text" name="v5" id="v5" /></td>
							</tr>
							<tr>
								<td></td>
								<td><select name="hour" id="hour">
										<option value="HH">小时</option>
										<option value="">无</option>
								</select></td>
								<td>占位符</td>
								<td><input type="text" name="v6" id="v6" /></td>
							</tr>
							<tr>
								<td></td>
								<td><select name="minute" id="minute">
										<option value="mm">分钟</option>
										<option value="">无</option>
								</select></td>
								<td>占位符</td>
								<td><input type="text" name="v7" id="v7" /></td>
							</tr>
							<tr>
								<td></td>
								<td><select name="second" id="second">
										<option value="ss">秒&nbsp;&nbsp;&nbsp;&nbsp;</option>
										<option value="">无</option>
								</select></td>
								<td>占位符</td>
								<td><input type="text" name="v8" id="v8" /></td>
							</tr>
							<tr>
								<td></td>
								<td><select name="millisecond" id="millisecond">
										<option value="SSS">毫秒</option>
										<option value="">无</option>
								</select></td>
								<td>占位符</td>
								<td><input type="text" name="v9" id="v9" /></td>
							</tr>
							<tr>
								<td>日期分隔符</td>
								<td><select name="manualSplit" id="manualSplit">
										<option value="">无</option>
										<option value="-">-</option>
										<option value="/">/</option>
										<option value=".">.</option>
										<option value="年-月-日-小-时-分-钟-秒-毫秒">年月日小时分钟秒毫秒</option>
								</select></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
						<tr>
							<td>日期显示位置</td>
							<td><select name="dateIndex" id="dateIndex">
									<option value="1">1</option>
									<option value="2" selected>2</option>
									<option value="3">3</option>
									<option value="4">4</option>
							</select></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>流水号</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>类型</td>
							<td><select name="numberType" id="numberType">
									<option value="0">数字</option>
									<option value="1">字母</option>

							</select></td>
							<td>流水号初始值</td>
							<td><input type="text" name="numberStart" id="numberStart" /></td>
						</tr>
						<tr>
							<td>流水号位数</td>
							<td><select name="numberDigit" id="numberDigit">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
							</select></td>
							<td>流水号步长</td>
							<td><select name="numberStep" id="numberStep">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
							</select></td>
						</tr>
						<tr>
							<td>流水号上限值</td>
							<td><input type="text" name="numberCeiling"
								id="numberCeiling" /></td>
							<td>流水号补位符</td>
							<td><input type="text" name="numberPlaceholder"
								id="numberPlaceholder" /></td>
						</tr>
						<tr>
							<td>重置规则</td>
							<td><select name="numberResetRules" id="resetRule">
									<option value="2">按月</option>
									<option value="3">按年</option>
									<option value="1">按日</option>
									<option value="0">无</option>
							</select></td>
							<td>流水号是否连续</td>
							<td><select name="numberState" id="numberState">
									<option value="2">否&nbsp;&nbsp;&nbsp;&nbsp;</option>
									<option value="1">是&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</select></td>
						</tr>
						<tr>
							<td>占位符</td>
							<td><input type="text" name="v10" id="v10" /></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" onclick="rule.action()">保存</button>
				<button class="btn" type="reset">重 置</button>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>