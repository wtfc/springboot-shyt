<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<script src="${sysPath}/js/com/sys/setting/setting.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/setting/setting.validate.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="navigationMenu">
	<h1></h1>
	<div class="crumbs"></div>
</header>
<section class="panel m-t-md search-box clearfix">
	<h2 class="panel-heading clearfix">系统参数</h2>
	<form class="table-wrap form-table" id="settingForm">
		<table class="table table-td-striped">
			<tbody>
				<!-- <tr> -->
					<!-- <td class="w240">短信服务</td>
					<td>
						<input type="hidden" id="id" name = "id"  >
						<input type="hidden" id="token" name="token" value="0">
						<input type="hidden" id="docSuggestionHistory" name="docSuggestionHistory" value="1">
						<input type="hidden" id="showIdentifyingCode" name="showIdentifyingCode" value="1"/>
						<input type="hidden" id="photoPatn" name = "photoPatn"  />
						<input type="hidden" id="filePath" name = "filePath" />
						<label class="radio inline">
								<input type="radio" name="isMsgService" id="SMS" value="1" />开启
						</label>
						<label class="radio inline">
								<input type="radio" name="isMsgService" checked value="0" />不开启
						</label>
					</td>
				</tr>
				<tr>
					<td>短信前缀</td>
					<td>
						<input type="text" id="msgPrefix" name = "msgPrefix" placeholder="20字以内" />
					</td>
				</tr>
				<tr>
					<td>意见域签批</td>
					<td>
						<label class="radio inline">
							<input type="radio" name="suggestionType" checked value="1" />手写签批
						</label>
						<label class="radio inline">
							<input type="radio" name="suggestionType" value="2" />签名
						</label>
					</td>
				</tr>
				<tr>
					<td>网络签章</td>
					<td>
						<label class="radio inline">
							<input type="radio" name="netKey" checked value="1" />使用
						</label>
						<label class="radio inline">
							<input type="radio" name="netKey" value="0" />不使用
						</label>
					</td>
				</tr> -->
				<tr>
				<td>IP绑定</td>
		    	<td>
		    		<label class="radio inline">
						<input type="radio" name="useIpBanding" checked value="1" />启用
					</label>
					<label class="radio inline">
						<input type="radio" name="useIpBanding" value="0" />不启用
					</label>
		    	</td>
		    	</tr>
				<tr>
				<td>同一账号不同客户端同时在线</td>
				<td>
					<label class="radio inline">
						<input type="radio" name="loginType" value="1" />允许
					</label>
					<label class="radio inline">
						<input type="radio" name="loginType" checked value="0" />不允许
					</label>
				</td>
				</tr>
				<!-- <tr>
				<td>插件类型</td>
				<td>
					<label class="radio inline">
						<input type="radio" name="signType" value="0" />点聚
					</label>
					<label class="radio inline">
						<input type="radio" name="signType" checked value="1" />金格
					</label>
				</td>
				</tr>
				<tr>
				<td>是否控制公文正文章的颜色</td>
				<td>
					<label class="radio inline">
						<input type="radio" name="controlPrint" value="1" />控制
					</label>
					<label class="radio inline">
						<input type="radio" name="controlPrint" checked value="0" />不控制
					</label>
				</td>
				</tr>
				<tr>
				<td>督办协办拆分任务时，上级信息是否回显</td>
				<td>
					<label class="radio inline">
						<input type="radio" name="taskParentToSub" value="1" />回显
					</label>
					<label class="radio inline">
						<input type="radio" name="taskParentToSub" checked value="0" />不回显
					</label>
				</td>
				</tr> -->
				<tr>
					<td>最多错误登录次数(次)</td>
					<td>
						<input type="text" id="maxErrorCount" name = "maxErrorCount" maxlength="2" class="input-mini m-l-none" />（到达错误登录次数后用户被锁定，可联系管理员解锁）</td>
				</tr>
				<tr>
					<td>锁定时间(分钟)</td>
					<td>
						<input type="text" id="lockTime" maxlength="5" name = "lockTime" class="input-mini m-l-none" />（锁定时间内不能登录）
					</td>
				</tr>
				<tr>
					<td>邮件自动保存时间</td>
					<td>
						<input type="text" id="emailSaveTime" name = "emailSaveTime" maxlength="2" class="input-mini m-l-none" />分</td>
				</tr>
				<tr>
					<td>日志编辑天数控制</td>
					<td>
						<input type="text" id="worklogDay" value="0" name = "worklogDay" maxlength="2" class="input-mini m-l-none" />天</td>
				</tr>
			</tbody>
		</table>
		<section class="form-btn m-b-lg">
			<button class="btn dark ok" type="button" id="settingbtn">保 存</button>
		</section>
	</form>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>