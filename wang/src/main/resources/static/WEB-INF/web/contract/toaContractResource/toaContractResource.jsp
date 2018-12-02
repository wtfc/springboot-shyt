<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>合同资源表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaContractResourceQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/toaContractResource/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaContractResource.deleteToaContractResource("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">合同资源表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaContractResourceTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>客户id</th>				<th>保底带宽</th>				<th>单价</th>				<th>端口带宽</th>				<th>单价</th>				<th>超流量带宽</th>				<th>单价</th>				<th>机柜</th>				<th>单价</th>				<th>服务器</th>				<th>单价</th>				<th>IP</th>				<th>单价</th>				<th>交换机</th>				<th>单价</th>				<th>链路</th>				<th>单价</th>				<th>端口</th>				<th>单价</th>				<th>内存</th>				<th>单价</th>				<th>CPU</th>				<th>单价</th>				<th>硬盘</th>				<th>单价</th>				<th>路由器</th>				<th>单价</th>				<th>其他</th>				<th>单价</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/contract/toaContractResource/toaContractResource.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>