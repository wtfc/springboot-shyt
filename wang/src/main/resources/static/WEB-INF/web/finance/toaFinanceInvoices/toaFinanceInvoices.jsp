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
								<option value="0">未出</option>
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
<script >
//设置每行按钮
 function oTableSetButtones (source) {
	/* var read="";
	if(source.otherDeductions==1){
		return read;
	}else{
		read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceInvoices/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
		return read;
	} */
	/* del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaFinanceInvoices.deleteToaFinanceInvoices("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>"; */
	//取消权限
	read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceInvoices/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	return read;
}; 
</script>
<h2 class="panel-heading clearfix">当月收入表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaFinanceInvoicesTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>客户名称</th>
				<th>业务类型</th>				<th>应收月份</th>				<th>应收金额</th>				<th>发票金额</th>				<th>未开票金额</th>				<th>回款金额</th>				<th>未回款金额</th>				<th>代理费</th>
				<th>账单状态</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="createBill" data-toggle="modal">生成账单</a>
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/finance/toaFinanceInvoices/toaFinanceInvoices.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>