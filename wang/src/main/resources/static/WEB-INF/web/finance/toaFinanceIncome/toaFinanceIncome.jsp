<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>收入底表表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinanceIncomeQueryForm" >
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
 	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaFinanceIncome.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaFinanceIncome.deleteToaFinanceIncome("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit+del;
}; 
</script>
<h2 class="panel-heading clearfix">收入底表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaFinanceIncomeTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>编码</th>				<th>收入类别</th>				<th>客户id</th>				<th>客户名称</th>				<th>业务类型/产品组合</th>				<th>原拓展部门</th>				<th>部门</th>				<th>客户经理</th>				<th>维护经理</th>				<th>合同编号</th>				<th>年合同金额</th>				<th>机房</th>				<th>付费方式</th>				<th>计费起始时间</th>				<th>计费终止时间</th>				<th>变动日期</th>				<th>月份</th>				<th>首次入网时间</th>				<th>专线类型</th>				<th>单天计算方式</th>				<th>超流量取值方式</th>				<th>带宽端口类型</th>				<th>存量合同额</th>				<th>预存金额</th>				<th>折扣</th>				<th>备注</th>				<th>资源列表</th>				<th>保底带宽数量</th>				<th>保底带宽单价</th>				<th>端口带宽数量</th>				<th>端口带宽单价</th>				<th>超流量带宽数量</th>				<th>超流量带宽单价</th>				<th>机柜数量</th>				<th>机柜单价</th>				<th>服务器数量</th>				<th>服务器单价</th>				<th>IP数量</th>				<th>IP单价</th>				<th>交换机数量</th>				<th>交换机单价</th>				<th>链路数量</th>				<th>链路单价</th>				<th>端口数量</th>				<th>端口单价</th>				<th>内存数量</th>				<th>内存单价</th>				<th>CPU数量</th>				<th>CPU单价</th>				<th>硬盘数量</th>				<th>硬盘单价</th>				<th>计算情况(周期或补齐自然月)</th>				<th>状态</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addButton" data-toggle="modal">新 增</a>
			<button class="btn " id="deleteToaFinanceIncomes" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="toaFinanceIncomeModuleDiv"></div>
<script src='${sysPath}/js/com/finance/toaFinanceIncome/toaFinanceIncome.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>