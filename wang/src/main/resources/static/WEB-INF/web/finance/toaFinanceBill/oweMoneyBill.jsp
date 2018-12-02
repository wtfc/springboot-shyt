<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>账单信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinanceBillQueryForm" >
			<input  type="hidden"id="oweMoney" name="oweMoney" value="1"/>
			<input  type="hidden"id="state" name="state" value="4"/>
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
							<input  type="text"id="companyName" name="companyName" />
						</td>
						<td>账单日期</td>
						<td>
							<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text" id="billDate" name="billDate" />
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
	var print = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceBill/print.action?id="+source.id+"&companyType="+source.serviceTypeValue+"\" onclick=\"\">查看</a>"; 
	return print;
}; 
</script>
<h2 class="panel-heading clearfix">欠费信息查询</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaFinanceBillTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>账单日期</th>				<th>客户名称</th>
				<th>业务类型</th>				<th>发票类型</th>				<th>应收金额</th>				<th>应收日期</th>				<th>客户经理</th>				<th>电话</th>
				<th>欠费金额</th>				<th>审核状态</th>
				<th>操作</th>			</tr>
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
<script src='${sysPath}/js/com/finance/toaFinanceBill/oweMoneyBill.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>