<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>骨干网链路带宽统计表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkRingQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>环网类型</td>
						<td>
							<select id="ringType" name="ringType">
								<option value="">请选择</option>
								<option value="二层环网">二层环网</option>
								<option value="三层环网">三层环网</option>
							</select>
						</td>
						<td>机房名称</td>
						<td>
							<dic:select id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkRing/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkRing.deleteToaNetworkRing("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">骨干网链路带宽统计表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkRingTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>环网类型</th>				<th>机房名称</th>				<th>对端机房名称</th>				<th>链路带宽</th>				<th>聚合链路数量</th>				<th>用户带宽总和</th>				<th>链路带宽复用比</th>				<th>备注</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/network/toaNetworkRing/toaNetworkRing.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>