<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>云资源信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaProductCloudQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>支持人员</td>
						<td>
							<input type="text" id="supporter" name="supporter" style="width:50%"/>
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
	 var reada = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/customer/toaNetResourceCloud/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	return reada;
};
</script>
<h2 class="panel-heading clearfix">云资源信息表</h2>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="toaProductCloudTable">
		<thead>
			<tr>
				<th>支持人员</th>				<th>CPU</th>				<th>内存</th>				<th>容量盘</th>				<th>性能盘</th>				<th>云主机快照</th>				<th>云硬盘快照</th>				<th>公网IP</th>				<th>带宽</th>				<th>路由器</th>				<th>负载均衡</th>				<th>金额(元)</th>				<th>资源模式</th>				<th>计费开始日期</th>				<th>计费终止日期</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/customer/netResource/toaNetResourceCloud/toaNetResourceCloud.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>