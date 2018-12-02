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
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
							<input  type="text"id="companyName" name="companyName" />
							<input type="hidden" id="sale" name="sale" value="${user}"/>
						</td>
						<td>审批状态</td>
						<td>
							<select  id="state" name="state">
								<option value="">请选择</option>
								<option value="0">业务审核</option>
								<option value="1">开票中(回款中)</option>
								<option value="2">已开票，已回款</option>
								<option  value="3">账单退回</option>
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
<script >
//设置每行按钮
 function oTableSetButtones (source) {
	var See = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceBill/print.action?id="+source.id+"&companyType="+source.serviceTypeValue+"\" onclick=\"\">查看</a>";
	var print="";
	if(source.state!=0){
		print = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceBill/print.action?id="+source.id+"&companyType="+source.serviceTypeValue+"\" onclick=\"\">打印</a>";
	}
	return See+print;
}; 
</script>
<h2 class="panel-heading clearfix">账单信息查询</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaFinanceBillTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>账单日期</th>				<th>客户名称</th>
				<th>业务类型</th>				<th>发票类型</th>				<th>合计金额</th>				<th>应支付日期</th>				<th>客户经理</th>				<th>电话</th>				<th>审核状态</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="createBill" data-toggle="modal">提交审核</a> -->
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/finance/toaFinanceBill/tongguoBill.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>