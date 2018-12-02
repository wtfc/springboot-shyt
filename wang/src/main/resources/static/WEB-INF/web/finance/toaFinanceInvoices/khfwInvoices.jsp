<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>当月收入表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinanceInvoicesQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td >客户名称</td>
						<td>
							<input type="text" id="companyName" name="companyName" />
							<input type="hidden" id="maintenanSale" name="maintenanSale" value="${user}"/>
						</td>
						<td >应收月份</td>
						<td>
							<input type="text" id="invoicesMonth" name="invoicesMonth" />
						</td>
					</tr>
					<tr>
						<td >业务类型</td>
						<td>
							<dic:select id="companyType" name="companyType" dictName="customerType" headName="-请选择-" headValue="" defaultValue=""/>
						</td>
						<td >账单状态</td>
						<td>
							<select id="otherDeductions" name="otherDeductions" >
								<option value="1">已出</option>
							</select>
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
<h2 class="panel-heading clearfix">到账信息表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height" id="toaFinanceInvoicesTable">
		<thead>
			<tr>
				<th>客户名称</th>
				<th>业务类型</th>				<th>应收起始日期</th>
				<th>应收终止日期</th>				<th>应收金额</th>				<th>是否开票</th>
				<th>发票号码</th>
				<th>开票日期</th>
				<th>发票金额</th>				<th>未开票金额</th>
				<th>是否回款</th>				<th>回款金额</th>
				<th>回款日期</th>				<th>未回款金额</th>				<th>代理费</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="createBill" data-toggle="modal">生成账单</a> -->
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/finance/toaFinanceInvoices/khfwInvoices.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>