<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>代理费表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinanceAgencyQueryForm" >
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceAgency/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaFinanceAgency.deleteToaFinanceAgency("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">代理费</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaFinanceAgencyTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>代理编码</th>				<th>客户id</th>				<th>客户名称</th>				<th>业务拓展部</th>
				<th>拓展专员</th>
				<th>维护专员</th>				<th>计费周期起</th>				<th>计费周期止</th>				<th>合同编号</th>				<th>合同金额</th>				<th>合同周期起时间</th>				<th>合同周期止时间</th>				<th>机房</th>				<th>资源</th>				<th>支付方式</th>				<th>税前支付金额</th>				<th>税点</th>				<th>收款方</th>				<th>支付状态</th>				<th>支付时间</th>				<th>未支付金额</th>				<th>备注</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/finance/toaFinanceAgency/toaFinanceAgency.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>