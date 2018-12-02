<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>收入信息综合查询表</h1>
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
							<dic:select   id="companyType" name="companyType" dictName="customerType" headName="-请选择-" headValue="" defaultValue=""/>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceInvoices/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaFinanceInvoices.deleteToaFinanceInvoices("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
/**
 *  导出Excel
 */
var excuteExcel = {};
excuteExcel.exportExcel = function () {
    var url = getRootPath()+"/finance/toaFinanceInvoices/exportExcel.action";
    window.location.href=url;
};
jQuery(function($) {
	$("#buttonExport").click(excuteExcel.exportExcel);
});
</script>
<h2 class="panel-heading clearfix">当月收入表</h2>
<div class="table-wrap">
	<div style="overflow-x:auto;">
	<table class="table table-striped tab_height first-td-tc" id="toaFinanceInvoicesTable">
		<thead>
			<tr>
				<th>变动 日期</th>
				<th>客户名称</th>
				<th>业务<br>类型</th>
				<th>资源变<br>动类型</th>
				<th>业务拓展部</th>
				<th>拓展专员</th>
				<th>维护专员</th>
				<th>机房</th>
				<th>付费方式</th>
				<th>计费起始时间</th>
				<th>计费终止时间</th>
				<th>专线类型</th>
				<th>单天计<br>算方式</th>
				<th>超流量<br>取值方式</th>
				<th>合同金额</th>
				<th>绩效合同额</th>
				<th>存量合同额</th>
				<th>预存金额</th>
				<th>折扣</th>
				<th>保底带宽</th>
				<th>单价</th>
				<th>端口带宽</th>
				<th>单价</th>
				<th>超流量带宽</th>
				<th>单价</th>
				<th>机柜</th>
				<th>单价</th>
				<th>服务器</th>
				<th>单价</th>
				<th>IP</th>
				<th>单价</th>
				<th>交换机</th>
				<th>单价</th>
				<th>链路</th>
				<th>单价</th>
				<th>端口</th>
				<th>单价</th>
				<th>内存</th>
				<th>单价</th>
				<th>CPU</th>
				<th>单价</th>
				<th>硬盘</th>
				<th>单价</th>
				<th>应收 月份</th>				<th>应收金额</th>				<th>发票金额</th>				<th>未开票金额</th>				<th>回款金额</th>				<th>未回款金额</th>				<th>代理费</th>
				<!-- <th>账单状态</th> -->			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="buttonExport" data-toggle="modal">导出Excel</a>
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/finance/toaFinanceInvoices/toaFinanceMainInvoices.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>