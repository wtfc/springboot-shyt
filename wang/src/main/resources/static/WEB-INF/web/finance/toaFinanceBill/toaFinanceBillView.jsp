<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<script type="text/javascript" src='${sysPath}/js/echarts.min.js'></script>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable" style="width:100%"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>个人信息表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinanceBillQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>统计年</td>
						<td>
							<input type="text" data-date-format="yyyy-MM-dd" data-pick-time="false" class="datepicker-input" style="width:100%;" id="billDate" name="billDate" />
						</td>
						<td>客户名称</td>
						<td>
							<input type="text"  id="companyName" name="companyName" />
						</td>
					</tr>
					<tr>
						<td>客户经理</td>
						<td>
							<input type="text"  id="sale" name="sale" />
						</td>
						<td>业务类型</td>
						<td>
							<dic:select id="serviceType" name="serviceType" dictName="customerType" headName="-请选择-" headValue="" defaultValue=""/>
						</td>
					</tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark" type="button" id="queryBill">查 询</button>
	            <button class="btn" type="button" id="queryReset">重 置</button>
	        </section>
	    </form>
    </div>
    <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>

<section class="panel" style="width:100%">

<h2 class="panel-heading clearfix">财务信息视图统计</h2>
	
	<!-- 视图显示区域 -->
    <div class="main">
		 <div class="content">
			<div id="billViewByMonth" class="diagram"></div>
			<div id="billViewByYear" class="diagram"></div>
			<div id="billViewByRoom" class="diagram"></div>
		</div>
	</div>
	
</section>
</section>
<style>
.main {
	margin: 0 auto;
	width: 100%;
	/* min-height: 600px; */
	height: auto;
}

.content {
	background:;
	width: 100%;
	height: 100%;
	/* margin: 10%; */
	text-align: center;
}

.diagram {
	width: 100%;
	height: 350px;
	display: inline-block;
}

#select2_filter_user_div {
	margin-right: 20px;
}

</style>
<script src='${sysPath}/js/com/finance/toaFinanceBill/toaFinanceBillView.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>