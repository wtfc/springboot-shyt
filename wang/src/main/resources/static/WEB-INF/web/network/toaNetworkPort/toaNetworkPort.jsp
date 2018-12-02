<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>端口使用情况统计表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkPortQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
							<input type = "text" id="companyName" name="companyName" />
						</td>
						<td>类型</td>
						<td>
							<input type="text" id="companyType" name="companyType" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkPort/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkPort.deleteToaNetworkPort("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">端口使用情况统计表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkPortTable">
		<thead>
			<tr>
				<th rowspan="2" class="w46"><input type="checkbox" /></th>				<th rowspan="2" style="text-align:center">客户名称</th>				<th rowspan="2" style="text-align:center">类型</th>				<th rowspan="2" style="text-align:center">机房</th>				<th rowspan="2" style="text-align:center">设备</th>				<th rowspan="2" style="text-align:center">板卡</th>				<th rowspan="2" style="text-align:center">端口</th>				<th rowspan="2" style="text-align:center">VLAN</th>				<th rowspan="2" style="text-align:center">带宽</th>				<th rowspan="2" style="text-align:center">使用状态</th>				<th rowspan="2" style="text-align:center">端口总数</th>				<th colspan="2" style="text-align:center">使用个数</th>				<th colspan="2" style="text-align:center">使用率</th>				<!-- <th colspan="2" style="text-align:center">空闲率</th> -->				<th rowspan="2" style="text-align:center">操作</th>
			</tr>
			<tr>
				<th>自用</th>
				<th>客户</th>
				<th>自用</th>
				<th>客户</th>
				<!-- <th>自用</th>
				<th>客户</th> -->
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
<script src='${sysPath}/js/com/network/toaNetworkPort/toaNetworkPort.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>