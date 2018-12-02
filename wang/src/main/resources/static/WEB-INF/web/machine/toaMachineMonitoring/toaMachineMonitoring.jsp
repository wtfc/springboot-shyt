<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>监控报告通告表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaMachineMonitoringQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            	<td>客户名称</td>
						<td>
							<input type = "text" id="companyName" name="companyName" />
						</td>
						<td>所属机房</td>
						<td>
							<dic:select id="machina" name="machina" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
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
<script >
//设置每行按钮
 function oTableSetButtones (source) {
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/machine/toaMachineMonitoring/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>";
	var del = '<shiro:hasPermission name="user:deleteMachineMonitoring"><a class="a-icon i-remove" href="#" onclick="toaMachineMonitoring.deleteToaMachineMonitoring('+ source.id+')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">监控报告通告</h2>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="toaMachineMonitoringTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>工单编号</th>				<th>客户名称</th>				<th>所属机房</th>				<th>开始时间</th>				<th>恢复时间</th>				<th>故障类型</th>				<th>具体类型</th>				<th>故障原因</th>				<th>超流量</th>				<th>流入流量</th>				<th>流出流量</th>				<th>阀值</th>				<th>超出阀值</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/machine/toaMachineMonitoring/toaMachineMonitoring.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>