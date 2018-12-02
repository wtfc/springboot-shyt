<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>在网客户带宽统计表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkBandwidthQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
							<input type = "text" id="companyName" name="companyName" />
						</td>
						<td>总带宽(M)</td>
						<td>
							<input type="text" id="gbps" name="gbps" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkBandwidth/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkBandwidth.deleteToaNetworkBandwidth("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">在网客户带宽统计表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkBandwidthTable">
		<thead>
			<tr>
				<th rowspan="3" class="w46"><input type="checkbox" /></th>				<th rowspan="3" style="text-align:center">客户名称</th>				<th rowspan="3" style="text-align:center">总带宽(M)</th>
				<th colspan="15" style="text-align:center">明细</th>
				<th rowspan="3" style="text-align:center">操作</th>
			</tr>
			<tr>				<th rowspan="2" style="text-align:center">多线(M)</th>
				<th colspan="3" style="text-align:center">单电信</th>
				<th colspan="3" style="text-align:center">单联通</th>
				<th>单移动</th>
				<th colspan="6" style="text-align:center">其他</th>
				<th rowspan="2" style="text-align:center">传输</th>
			</tr>
			<tr>				<th>网林(M)</th>				<th>云众林(M)</th>				<th>廊坊(M)</th>				<th>鲁谷(M)</th>				<th>东四(M)</th>				<th>华思(M)</th>				<th>山东(M)</th>				<th>移动(M)</th>				<th>铁通(M)</th>				<th>教育(M)</th>				<th>科技(M)</th>				<th>CNISP(M)</th>				<th>国际(M)</th>			</tr>
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
<script src='${sysPath}/js/com/network/toaNetworkBandwidth/toaNetworkBandwidth.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>