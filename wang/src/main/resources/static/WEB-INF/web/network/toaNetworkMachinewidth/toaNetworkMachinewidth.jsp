<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>在网客户机房带宽统计表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkMachinewidthQueryForm" >
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkMachinewidth/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkMachinewidth.deleteToaNetworkMachinewidth("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">在网客户机房带宽统计表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkMachinewidthTable">
		<thead>
			<tr>
				<th class="w46" rowspan="2"><input type="checkbox" /></th>				<th style="text-align:center" rowspan="2">客户名称</th>				<th style="text-align:center" rowspan="2">总带宽(M)</th>
				<th style="text-align:center" colspan="7">三层环网</th>
				<th style="text-align:center" colspan="8">二层环网</th>
				<th style="text-align:center" colspan="2">外省机房</th>				<th style="text-align:center" rowspan="2">传输备注</th>				<th style="text-align:center" rowspan="2">操作</th>
			</tr>
			<tr>				<th>兆维(M)</th>				<th>洋桥(M)</th>				<th>看丹(M)</th>				<th>清华园(M)</th>				<th>鲁谷(M)</th>				<th>富丰桥(M)</th>				<th>沙河(M)</th>				<th>兆维(M)</th>				<th>洋桥(M)</th>				<th>看丹(M)</th>				<th>清华园(M)</th>				<th>鲁谷(M)</th>				<th>富丰桥(M)</th>				<th>沙河(M)</th>				<th>传输(M)</th>				<th>廊坊(M)</th>				<th>山东(M)</th>
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
<script src='${sysPath}/js/com/network/toaNetworkMachinewidth/toaNetworkMachinewidth.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>