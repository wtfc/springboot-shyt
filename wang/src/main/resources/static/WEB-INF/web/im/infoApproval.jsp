<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/treeSelectControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/im/infoApproval.js"></script>
<script type="text/javascript">
function oTableSetButtons(id) {
	readBtn = "<a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"infoApproval.infoDetail("+id+ ")\">查看详细</a>";
	return readBtn;
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in"><div class="con-heading fl" id="navigationMenu"></div>
	</header>
	
	<!--start 查询条件-->
	<section class="panel clearfix search-box search-shrinkage">
		<div class="search-line hide">
			<form id="infoApprovalForm" class="table-wrap form-table">
				<!-- 需要增加，流程入口判断查询类型 -->
				<input type="hidden" name="entranceType" id="entranceType" value=${entranceType}>
				<input type="hidden" name="entrance" id="entrance" value=${entrance}>
				<input type="hidden" name="flowId" id="flowId" value=${flowId}>
				
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td class="w140">标题</td>
							<td style="width:40%;"><input type="text" name="infoTitile"
								id="infoTitile"></td>
							<td class="w140">栏目</td>
							<td><div id="column"></div></td>
						</tr>
						<tr>
							<td>发布人</td>
							<td><div id="controlTree1"></div></td>
							<td>发布部门</td>
							<td><div id="controlTree2"></div></td>
						</tr>
						<tr>
							<td>发布时间</td>
							<td>
								<div class="input-group w-p100">
									<input class="datepicker-input" type="text" name="sendTimeBegin"
										id="sendTimeBegin" data-ref-obj="#sendTimeEnd lt">
									<div class="input-group-btn w30">-</div>
									<input class="datepicker-input" type="text" name="sendTimeEnd"
										id="sendTimeEnd" data-ref-obj="#sendTimeBegin gt">
								</div>
							</td>
							<td>审批状态</td>
							<td><select name="processStatus" id="processStatus">
									<option value="1">待办</option>
									<option value="3">已办</option>
							</select></td>
						</tr>
					</tbody>
				</table>
				<section class="form-btn m-b-lg">
					<button class="btn dark query-jump" type="button" id="queryInfos">查
						询</button>
					<button class="btn" type="reset" id="queryReset">重 置</button>
				</section>
			</form>
		</div>
		<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
	</section>
	<!--end 查询条件-->
	<!--start 查询结果-->
	<section class="panel clearfix">
		<h2 class="panel-heading clearfix">查询结果</h2>
	    <div class="table-wrap">
	        <table class="table table-striped tab_height" id="infoApprovalTable" >
	            <thead>
	                <tr>
	                <th style="width:150px;">标题</th>
	                <th style="width:150px;">栏目</th>
	                <th style="width:90px;">发布人</th>
	                <th style="width:110px;">发布部门</th>
	                <th style="width:140px;">发布时间</th>
	                <th style="width:90px;">审批状态</th>
	                <th style="width:150px;">操作</th>
	                </tr>
	            </thead>
	            <tbody>
	            </tbody>
	        </table>
	    </div>
	</section>
	<!--end 查询结果-->
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>