<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>收款方合同表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaContractIncontractQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>公司名称</td>
						<td>
					 		<input type="text"  id="companyName" name="companyName" />
					 		<input type="hidden"  id="derive" name="derive" value="否"/>
					 		<input type="hidden"  id="leard" name="leard" value="${userName }"/>
						</td>
						<td  class=" b-green-dark b-tc">合同终止日期</td>
						<td>
						<div class="input-group w-p100">
							<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text" data-ref-obj="#endDateEnd lt" name="endDateBegin" id="endDateBegin" />
							<div class="input-group-btn w30">-</div>
							<input data-date-format="yyyy-MM-dd"data-pick-time="false" data-ref-obj="#endDateBegin gt" class="datepicker-input" type="text"  name="endDateEnd" id="endDateEnd" />
							</div>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/contract/toaContractIncontract/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	return read;
}; 
</script>
<h2 class="panel-heading clearfix">收款方合同</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaContractIncontractTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>公司名称</th>				<th>合同编码</th>				<th>发起人</th>				<th>合同类别</th>				<th>合同金额(元)</th>				<th>盖章</th>				<th>归档时间</th>				<th>合同起始日期</th>				<th>合同终止日期</th>			</tr>
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
<script src='${sysPath}/js/com/oa/contract/toaContractIncontract/warContractIncontract.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>