<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>网络故障表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaMachineNetworkfaultQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            	<td>客户名称</td>
						<td>
							<input type = "text" id="companyName" name="companyName" />
						</td>
					</tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark" type="button" id="queryMachine">查 询</button>
	            <button class="btn" type="button" id="queryReset">重 置</button>
	        </section>
	    </form>
    </div>
    <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>

<section class="panel">
<script>
//设置每行按钮
 function oTableSetButtones (source) {
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/machine/toaMachineNetworkfault/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	var del = '<shiro:hasPermission name="user:deleteMachineNetwork"><a class="a-icon i-remove" href="#" onclick="toaMachineNetworkfault.deleteToaMachineNetworkfault('+ source.id+')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">网络故障</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaMachineNetworkfaultTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>工单编号</th>				<th>客户名称</th>				<th>报障时间</th>				<th>客户联系人</th>				<th>客户联系方式</th>				<th>故障类型</th>				<th>故障类型</th>				<th>处理时间</th>				<th>完成时间</th>				<th>故障原因</th>				<th>操作工程师</th>				<th>客户反馈</th>				<th>是否按<br>要求完成</th>				<th>客户满意度</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">下 单</a> -->
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/machine/toaMachineNetworkfault/toaMachineNetworkfault.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>