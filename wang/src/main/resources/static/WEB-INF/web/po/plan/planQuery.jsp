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
			sendBtn = "<shiro:hasPermission name='plan:send'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planQuery.initSendUser("+full.id+ ",'0')\" role=\"button\" data-toggle=\"modal\">转发</a></shiro:hasPermission>";
			readBtn = "<shiro:hasPermission name='plan:readState'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planSendAndRead.initReadList("+full.id+ ")\">计划阅读情况</a></shiro:hasPermission>";
		}
		return sendBtn + readBtn;
	};
	
	function oTableSendSetButtons(mData,type,full) {
		//var deleteBtn = "<a class='a-icon m-r-xs i-remove' href='javascript:void(0)' onclick=\"planQuery.deletePlanSend("+full.planId+ ")\"><i class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' data-container='body' data-original-title='删除'></i></a>";//删除情况按钮
		//var deleteBtn = "<a class='a-icon m-r-xs i-remove' href='javascript:void(0)' onclick=\"planQuery.deleteForward("+full.sendPkId+ ")\"><i class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' data-container='body' data-original-title='删除'></i></a>";//删除情况按钮
		//var sendBtn = "<shiro:hasPermission name='plan:send'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planSendAndRead.initSendUser("+full.id+ ")\" role=\"button\" data-toggle=\"modal\">转发</a></shiro:hasPermission>";//转发按钮
		var sendBtn = "<shiro:hasPermission name='plan:send'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planQuery.initSendUser("+full.id+ ",'1')\" role=\"button\" data-toggle=\"modal\">转发</a></shiro:hasPermission>";//转发按钮
		var readBtn = "<shiro:hasPermission name='plan:readState'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"planSendAndRead.initReadList("+full.id+ ")\">计划阅读情况</a></shiro:hasPermission>";//阅读情况按钮
		return sendBtn + readBtn;
	};
</script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="navigationMenu"></header>
	
	<section class="tabs-wrap m-t-md">
	   <ul class="nav nav-tabs">
	       <li class="active"><a href="#planListMsg" data-toggle="tab" data-id="planListMsg">编 制</a></li>
	       <li><a href="#sendListMsg" data-toggle="tab" data-id="sendListMsg">转 发</a></li>
	       <li><a href="#collectListMsg" data-toggle="tab" data-id="collectListMsg">汇 总</a></li>
	       <li><a href="#manageListMsg" data-toggle="tab" data-id="manageListMsg">管理创新</a></li>
	   </ul>
	</section>
	
	<section class="tab-content">
		<section class="panel tab-content search-shrinkage">
			<div id="planListMsg" class="tab-pane fade active in">
			<div class="search-line hide">
				<form id="planListForm" class="form-table table-wrap">
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
								   <label for='yearPlanType' class="checkbox inline">
									   <input type="checkbox" id="yearPlanType" name="planType" value="2">年计划
								   </label>
							   </td>
						   </tr>
						   <tr>
							   <td class="w140">编制人</td>
							   <td>
								   <div id="planShowUserTree" name="planShowUserTree"></div>
							   </td>
							   <td>编制部门</td>
							   <td>
					   				<div id="planDeptTree" name="planDeptTree"></div>
							   </td>
						   </tr>
						   <tr>
							   <td>开始时间</td>
							   <td>
								   <div class="input-group w-p100">
									   <input id="planStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeEnd lt">
									   <div class="input-group-btn w30">-</div>
									   <input id="planStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeBegin gt">
								   </div>
							   </td>
							   <td>流程状态</td>
							<td>
								<%@ include file="/WEB-INF/web/include/workflowSearchNoSave.jsp"%>
							</td>
						   </tr>
					   </tbody>
				   </table>
				   <section class="form-btn">
					   <button id="queryPlan" name="queryPlan" class="btn dark query-jump" type="button">查 询</button>
					   <button id="resetPlan" name="resetPlan" class="btn" type="button">重 置</button>
				   </section>
				</form>
			</div>
			<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
			
			<section class="tab-content">
				<h2 class="panel-heading clearfix">查询结果</h2>
				<div class="table-wrap">
					<table id="planQueryList" class="table table-striped tab_height">
						<thead>
							<tr>
								<th style="width:20%">计划标题</th>
								<th class="w100">计划类型</th>
								<th class="w100">编制人</th>
								<th class="w100">编制部门</th>
								<th class="w100">流程状态</th>
								<th class="w100">开始时间</th>
								<th class="w100">结束时间</th>
								<th class="w200">操作</th>
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
				<form id="sendListForm" class="table-wrap form-table">
				   <table class="table table-td-striped">
					   <tbody>
						  <tr>
						   <td class="w140">计划标题</td>
						   <td><input id="planTitle" name="planTitle" type="text"></td>
						   <td class="w140">计划类型</td>
						   <td style="width:40%;">
								  <label for='weekPlanType_send' class="checkbox inline">
									  <input type="checkbox" id="weekPlanType_send" name="planType" value="0">周计划
								  </label>
								  <label for='monthPlanType_send' class="checkbox inline">
									  <input type="checkbox" id="monthPlanType_send" name="planType" value="1">月计划
								  </label>
								  <label for='yearPlanType_send' class="checkbox inline">
									  <input type="checkbox" id="yearPlanType_send" name="planType" value="2">年计划
								  </label>
						   </td>
						  </tr>
						  <tr>
							  <td>编制人</td>
							  <td>
								  <div id="sendShowUserTree" name="sendShowUserTree"></div>
							  </td>
							  <td>编制部门</td>
							  <td>
								<div id="sendDeptTree" name="sendDeptTree"></div>
							  </td>
						  </tr>
						  <tr>
							  <td>开始时间</td>
							  <td>
								  <div class="input-group w-p100">
									  <input id="sendPlanStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#sendPlanStartTimeEnd lt">
									  <div class="input-group-btn w30">-</div>
									  <input id="sendPlanStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#sendPlanStartTimeBegin gt">
								  </div>
							  </td>
			<!--                       <td>方式</td> -->
			<!--                       <td> -->
			<!--                           <label for='sendState' class="checkbox inline"> -->
			<!--                                <input type="checkbox" id="sendState" name="sendPlanStatePrimaryKeys" value="0">转发 -->
			<!--                           </label> -->
			<!--                           <label for='receiveState' class="checkbox inline"> -->
			<!--                                <input type="checkbox" id="receiveState" name="sendPlanStatePrimaryKeys" value="1">接收 -->
			<!--                           </label> -->
			<!--                       </td> -->
							  <td>转发人</td>
							  <td><div id="senderShowUserTree" name="senderShowUserTree"></div></td>
						  </tr>
						  <tr>
							  <td>接收人</td>
							  <td><div id="receiveShowUserTree" name="receiveShowUserTree"></div></td>
							  <td></td>
							  <td></td>
						  </tr>
					   </tbody>
				   </table>
	
				   <section class="form-btn m-b-lg">
					   <button id="querySend" name="querySend" class="btn dark query-jump" type="button" >查 询</button>
					   <button id="resetSend" name="resetSend" class="btn" type="button">重 置</button>
				   </section>
				</form>  
		    </div>
			<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
			
			<section class="tab-content">
				<h2 class="panel-heading clearfix">查询结果</h2>
				<div class="table-wrap">
				   <table id="sendListTable" class="table table-striped">
					   <thead>
						   <tr>
							   <th style="width:20%">计划标题</th>
							   <th class="w100">计划类型</th>
							   <th class="w100">编制人</th>
							   <th class="w100">编制部门</th>
						  <!-- <th class="w60">方式</th> -->
							   <th class="w100">转发人</th>
							   <th class="w100">接收人</th>
							   <th class="w115">开始时间</th>
							   <th class="w115">结束时间</th>
							   <th class="w200">操作</th>
						   </tr>
					   </thead>
					   <tbody>
					   </tbody>
				   </table>
				</div>
			</section>
			</div>
	
			<div id="collectListMsg" class="tab-pane fade">
			<div class="search-line hide">
				<form id="collectListForm" class="table-wrap form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td class="w140">计划标题</td>
								<td><input id="planTitle" name="planTitle" type="text"></td>
								<td class="w140">计划类型</td>
								<td style="width:40%;">
								   <label for='weekPlanType_collect' class="checkbox inline">
									   <input type="checkbox" id="weekPlanType_collect" name="planType" value="0">周计划
								   </label>
								   <label for='monthPlanType_collect' class="checkbox inline">
									   <input type="checkbox" id="monthPlanType_collect" name="planType" value="1">月计划
								   </label>
								   <label for='yearPlanType_collect' class="checkbox inline">
									   <input type="checkbox" id="yearPlanType_collect" name="planType" value="2">年计划
								   </label>
								</td>
							</tr>
							<tr>
								<td>编制人</td>
								<td><div id="collectShowUserTree" name="collectShowUserTree"></div></td>
								<td>编制部门</td>
								<td><div id="collectDeptTree" name="collectDeptTree"></div></td>
							</tr>
							<tr>
								<td>开始时间</td>
								<td>
									<div class="input-group w-p100">
										<input id="collectPlanStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#collectPlanStartTimeEnd lt">
										<div class="input-group-btn w30"> -</div>
										<input id="collectPlanStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#collectPlanStartTimeBegin gt">
									</div>
								</td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
					<section class="form-btn m-b-lg">
						<button id="queryCollect" name="queryCollect" class="btn dark query-jump" type="button">查 询</button>
						<button id="resetCollect" name="resetCollect" class="btn" type="button">重 置</button>
					</section>
				</form>
		    </div>
			<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
	
			<section class="tab-content">
				<h2 class="panel-heading clearfix">查询结果<small class="m-l">
					<i class="fa fa-question-sign text-red m-r-xs"></i>本期总结指本周、本月、本年的工作总结；下期计划指下一周、下一月、下一年的工作计划</small>
				</h2>
				<div class="table-wrap">
					<table id="collectListTable" class="table table-striped">
						<thead>
							<tr>
								<th style="width:20%">计划标题</th>
								<th class="w100">计划类型</th>
								<th class="w100">编制人</th>
								<th class="w115">编制部门</th>
								<th class="w115">开始时间</th>
								<th class="w115">结束时间</th>
								<th>本期总结及<br>实际完成百分比</th>
								<th>下期计划</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</section>
			</div>
	
			<div id="manageListMsg" class="tab-pane fade">
		    <div class="search-line hide">
			   <form id="manageListForm" class="table-wrap form-table">
				  <table class="table table-td-striped">
					  <tbody>
						  <tr>
							  <td class="w140">计划标题</td>
							  <td><input id="planTitle" name="planTitle" type="text"></td>
							  <td class="w140">计划类型</td>
							  <td style="width:40%;">
								   <label for='weekPlanType_manage' class="checkbox inline">
									   <input type="checkbox" id="weekPlanType_manage" name="planType" value="0">周计划
								   </label>
								   <label for='monthPlanType_manage' class="checkbox inline">
									   <input type="checkbox" id="monthPlanType_manage" name="planType" value="1">月计划
								   </label>
								   <label for='yearPlanType_manage' class="checkbox inline">
									   <input type="checkbox" id="yearPlanType_manage" name="planType" value="2">年计划
								   </label>
							  </td>
						  </tr>
						  <tr>
							  <td>编制人</td>
							  <td><div id="manageShowUserTree" name="manageShowUserTree"></div></td>
							  <td>编制部门</td>
							  <td><div id="manageDeptTree" name="manageDeptTree"></div></td>
						  </tr>
					  </tbody>
				  </table>
				  <section class="form-btn m-b-lg">
					 <button id="queryManage" name="queryManage" class="btn dark query-jump" type="button">查 询</button>
					 <button id="resetManage" name="resetManage" class="btn" type="button">重 置</button>
				  </section>
			   </form>
			</div>
			<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
				
			<section class="tab-content">
			   <h2 class="panel-heading clearfix">查询结果</h2>
				   <div class="table-wrap">
					   <table id="manageListTable" class="table table-striped">
						   <thead>
							   <tr>
								   <th style="width:25%">计划标题</th>
								   <th class="w100">计划类型</th>
								   <th class="w100">编制人</th>
								   <th class="w115">编制部门</th>
								   <th>管理及创新</th>
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
<div class="modal fade panel" id="receiveUserName-list" aria-hidden="false">
    <div class="modal-dialog w530">
        <div class="modal-content email-unfold">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">接收人</h4>
            </div>
            <div class="modal-body email-b-c" id="consignee">
            </div>
            <div class="modal-footer email-l-h" id="consigneeCount">
                                           共<span id="countNum">0</span>人<span class="m-l-20"><a href="#" class="email-f-c" >显示全部</a></span> 
             <span class="m-l-20"><button class="btn" type="button" data-dismiss="modal">关 闭</button></span>
            </div>
        </div>
    </div>
</div>
<!--接收人查看end-->
<script src="${sysPath}/js/com/po/plan/planQuery.js"></script>
<script src="${sysPath}/js/com/po/plan/planSendAndRead.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>