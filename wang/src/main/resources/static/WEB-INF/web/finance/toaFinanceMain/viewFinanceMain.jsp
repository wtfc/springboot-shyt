<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table table-striped first-td-tc tab_height" id="toaFinanceMainTable">
		<thead>
			<tr>
				<th>编码</th>				<th>变动日期</th>				<th>客户名称</th>				<th>业务<br>类型</th>				<th>资源<br>变动<br>类型</th>				<th>机房</th>				<th>付费方式</th>				<th>计费起<br>始时间</th>				<th>计费终<br>止时间</th>				<th>专线类型</th>				<th>单天计<br>算方式</th>				<th>超流量<br>取值方式</th>				<th>计费金额</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${toaFinanceMains}" var="toaFinanceMains" >
			<tr>
				<td>${toaFinanceMains.orderNo }</td>
				<td><fmt:formatDate value="${toaFinanceMains.orderDate }" pattern="yyyy-MM-dd" /></td>
				<td>${toaFinanceMains.companyName }</td>
				<td>${toaFinanceMains.companyTypeValue }</td>
				<td>${toaFinanceMains.resourceType }</td>
				<td>${toaFinanceMains.roomNameValue }</td>
				<td>${toaFinanceMains.payType }</td>
				<td><fmt:formatDate value="${toaFinanceMains.cycleStart }" pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${toaFinanceMains.cycleEnd }" pattern="yyyy-MM-dd" /></td>
				<td>${toaFinanceMains.lineCategory }</td>
				<td>${toaFinanceMains.singleCharg }</td>
				<td>${toaFinanceMains.overflowCategory }</td>
				<td>${toaFinanceMains.cardAmount }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>