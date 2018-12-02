<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table table-striped first-td-tc tab_height" id="toaFinanceMainTable">
		<thead>
			<tr>
				<th>编码</th>
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