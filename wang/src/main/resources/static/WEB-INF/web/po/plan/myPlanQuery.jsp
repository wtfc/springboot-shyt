<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>

<script type="text/javascript">
	function oTableSetButtons(mData,type,full) {
		var sendBtn = "";//转发按钮
		var readBtn = "";//阅读情况按钮
		if(full.flowStatus == 7 ){
			//sendBtn = "<shiro:hasPermission name='plan:send'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planSendAndRead.initSendUser("+full.id+ ")\" role=\"button\" data-toggle=\"modal\">转发</a></shiro:hasPermission>"
			sendBtn = "<shiro:hasPermission name='plan:send'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"myPlan.initSendUser("+full.id+ ",'0')\" role=\"button\" data-toggle=\"modal\">转发</a></shiro:hasPermission>";
			readBtn = "<shiro:hasPermission name='plan:readState'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planSendAndRead.initReadList("+full.id+ ")\">计划阅读情况</a></shiro:hasPermission>";
		}
		return sendBtn + readBtn ;
	};
	
	function oTableSendSetButtons(mData,type,full) {
		//李洪宇 于2014-07-23修改  开始
		//var deleteBtn = "<a class='a-icon m-r-xs i-remove' href='javascript:void(0)' onclick=\"myPlan.deleteBusiness("+full.planId+ ")\"><i class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' data-container='body' data-original-title='删除'></i></a>";//删除情况按钮
		var deleteBtn = "<a class='a-icon m-r-xs i-remove' href='javascript:void(0)' onclick=\"myPlan.deleteForward("+full.sendPkId+")\"><i class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' data-container='body' data-original-title='删除'></i></a>";//删除情况按钮
		//李洪宇 于2014-07-23修改  结束
		//var sendBtn = "<shiro:hasPermission name='plan:send'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planSendAndRead.initSendUser("+full.id+ ")\" role=\"button\" data-toggle=\"modal\">转发</a></shiro:hasPermission>";//转发按钮
		var sendBtn = "<shiro:hasPermission name='plan:send'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"myPlan.initSendUser("+full.id+ ",'1')\" role=\"button\" data-toggle=\"modal\">转发</a></shiro:hasPermission>";//转发按钮
		var readBtn = "<shiro:hasPermission name='plan:readState'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planSendAndRead.initReadList("+full.id+ ")\">计划阅读情况</a></shiro:hasPermission>";//阅读情况按钮
		
		return deleteBtn + sendBtn + readBtn;
	};
</script>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="navigationMenu"></header>
	
	<section class="tabs-wrap m-t-md">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#planListMsg" data-toggle="tab" data-id="planListMsg">编 制</a></li>
			<li><a href="#sendListMsg" data-toggle="tab" data-id="sendListMsg">转 发</a></li>
		</ul>
	</section>
	
	<section class="tab-content">
		<section class="panel tab-content search-shrinkage">
			<div id="planListMsg" class="tab-pane fade active in">
				<div class="search-line hide">
					<form id="myPlanForm" name="myPlanForm" class="table-wrap form-table">
						<table class="table table-td-striped">
							<tbody>
								<tr>
									<td>计划标题</td>
									<td>
										<input id="planTitle" name="planTitle" type="text">
									</td>
									<td class="w140">计划类型</td>
									<td style="width:40%;">
										<label for='weekPlanType' class="checkbox inline">
											<input type="checkbox" id="weekPlanType" name="planType" value="0">周计划
										</label>
										<label for='monthPlanType' class="checkbox inline">
											<input type="checkbox" id="monthPlanType" name="planType" value="1">月计划
										</label> 
										<label for='yearPlanType' class="checkbox inline"> <input
												type="checkbox" id="yearPlanType" name="planType" value="2">年计划
										</label>
									</td>
								</tr>
								<tr>
									<td>开始时间</td>
									<td>
										<div class="input-group w-p100">
											<input id="planStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text"
											   data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeEnd lt">
											<div class="input-group-btn w30">-</div>
											<input id="planStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text"
											   data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeBegin gt">
										</div>
									</td>
									<td>流程状态</td>
									<td>
										<%@ include file="/WEB-INF/web/include/workflowSearch.jsp"%>
									</td>
								</tr>
							</tbody>
						</table>
						<section class="form-btn">
							<button id="myPlanQuery" name="myPlanQuery" class="btn dark query-jump" type="button">查 询</button>
							<button id="myPlanReset" name="myPlanReset" class="btn" type="reset">重 置</button>
						</section>
					</form>
				</div>
				<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
	
				<section class="tab-content">
					<h2 class="panel-heading clearfix">查询结果</h2>
					<div class="table-wrap">
						<table id="myPlanTable" name="myPlanTable" class="table table-striped tab_height">
							<thead>
								<tr>
									<th style="width:25%">计划标题</th>
									<th>计划类型</th>
									<th>流程状态</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th class="w240">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</section>
			</div>
		
			<div id="sendListMsg" class="tab-pane fade">
				<div class="search-line hide">
					<form id="myPlanSendForm" name="myPlanSendForm" class="table-wrap form-table">
						<table class="table table-td-striped">
							<tbody>
								<tr>
									<td class="w140">计划标题</td>
									<td>
										<input id="planTitle" name="planTitle" type="text">
									</td>
									<td class="w140">计划类型</td>
									<td style="width:40%;">
										<label for='weekPlanType_send' class="checkbox inline">
										<input type="checkbox" id="weekPlanType_send" name="planType" value="0">周计划</label>
										<label for='monthPlanType_send' class="checkbox inline">
										<input type="checkbox" id="monthPlanType_send" name="planType" value="1">月计划</label>
										<label for='yearPlanType_send' class="checkbox inline">
										<input type="checkbox" id="yearPlanType_send" name="planType" value="2">年计划</label>
									</td>
								</tr>
								<tr>
									<td>开始时间</td>
									<td>
										<div class="input-group w-p100">
											<input id="sendPlanStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text"
											   data-date-format="yyyy-MM-dd" data-ref-obj="#sendPlanStartTimeEnd lt">
											<div class="input-group-btn w30">-</div>
											<input id="sendPlanStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text"
											   data-date-format="yyyy-MM-dd" data-ref-obj="#sendPlanStartTimeBegin gt">
										</div>
									</td>
									<td>方式</td>
									<td>
										<label for='sendState' class="checkbox inline">
											<input type="checkbox" id="sendState" name="sendPlanStatePrimaryKeys" value="0">转发
										</label>
										<label for='receiveState' class="checkbox inline">
											<input type="checkbox" id="receiveState" name="sendPlanStatePrimaryKeys" value="1">接收
										</label>
									</td>
								</tr>
								<tr>
									<td>转发人</td>
									<td><div id="senderShowUserTree" name="senderShowUserTree"></div></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<section class="form-btn m-b-lg">
							<button id="myPlanSendQuery" name="myPlanSendQuery" class="btn dark query-jump" type="button">查 询</button>
							<button id="myPlanSendReset" name="myPlanSendReset" class="btn" type="reset">重 置</button>
						</section>
					</form>
				</div>
				<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
	
				<section class="tab-content">
					<h2 class="panel-heading clearfix">查询结果</h2>
					<div class="table-wrap">
						<table id="sendTable" name="sendTable" class="table table-striped">
							<thead>
								<tr>
									<th style="width:25%">计划标题</th>
									<th>计划类型</th>
									<th>转发人</th>
									<th>接收人</th>
									<th>方式</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th class="w240">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</section>
			</div>
			<%@ include file="/WEB-INF/web/po/plan/planSendAndRead.jsp"%>
		</section>
	</section>
</section>

<!--接收人查看start-->
<div class="modal fade panel" id="receiveUserName-list"
	aria-hidden="false">
	<div class="modal-dialog w530">
		<div class="modal-content email-unfold">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">接收人</h4>
			</div>
			<div class="modal-body email-b-c" id="consignee"></div>
			<div class="modal-footer email-l-h" id="consigneeCount">
				共<span id="countNum">0</span>人<span class="m-l-20"><a
					href="#" class="email-f-c">显示全部</a></span> <span class="m-l-20"><button
						class="btn" type="button" data-dismiss="modal">关 闭</button></span>
			</div>
		</div>
	</div>
</div>
<!--接收人查看end-->

<script src="${sysPath}/js/com/po/plan/myPlanQuery.js"></script>
<script src="${sysPath}/js/com/po/plan/planSendAndRead.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>