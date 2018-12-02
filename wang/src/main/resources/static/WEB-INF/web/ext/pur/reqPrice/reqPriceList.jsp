<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>采购项目报价表</h1>
			<div class="crumbs"></div>
		</div>
		
	</header>
	
<section class="panel search-shrinkage clearfix">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="reqPriceQueryForm">
	        <table class="table table-td-striped">
	            <tbody>
					<tr>
						<td>创建时间</td>
						<td>
					 		<input type="text" id="createDate" name = "createDate">
						</td>
						<td>审批状态</td>
						 <td>
							 <select name="processStatus" id="processStatus">
								<option value="1">待办</option>
								<option value="3">已办</option>
							 </select>
						 </td>
						 <td></td>
						 <td></td>
					</tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark" type="button" id="queryReqPrice">查 询</button>
	            <button class="btn" type="button" id="queryReset">重 置</button>
	        </section>
	    </form>
    </div>
    <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>


	<section class="panel m-t-md" id="body">
	    
			<div id="manageListTable" method="ManageList">

<script >
//设置每行按钮
function oTableSetButtones (source) {
	var buttonStr = "";
	
	var opt ={};
	opt.workId = source.workId;
	opt.flowStatus = source.flowStatus;
	opt.processStatus = source.processStatus;
	opt.workflowId = source.piId;
	opt.entrance = $("#entrance").val();
	opt.entranceType = $("#entranceType").val();
	opt.action="/pur/reqPrice/openReqPriceWorkflow.action";
	buttonStr = getWorkflowListButton(opt);
	return buttonStr ;
};
</script>
<h2 class="panel-heading clearfix">采购项目报价表</h2>
<div class="table-wrap">
	<input type="hidden" name="entranceType" id="entranceType" value=${entranceType}>
    <input type="hidden" name="entrance" id="entrance" value=${entrance}>
    <input type="hidden" name="flowId" id="flowId" value=${flowId}>
	<table class="table table-striped tab_height first-td-tc" id="reqPriceTable">
		<thead>
			<tr>
				
				<th class="w170">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>

<section class="clearfix" id="footer_height">

</section>

</div>
		
	</section>
</section>
 <script src='${sysPath}/js/com/pur/reqPrice/reqPriceList.js'></script>

 
<%@ include file="/WEB-INF/web/include/foot.jsp"%>