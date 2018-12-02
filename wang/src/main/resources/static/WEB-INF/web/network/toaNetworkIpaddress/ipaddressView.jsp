<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel clearfix search-box search-shrinkage">
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkIpaddressTable">
		<thead>
			<tr>
				<th>机房</th>
		</thead>
		<tbody>
			<c:forEach items="${listToaNetworkIpaddress}" var="Ipaddress">
			<tr>
				<td>${Ipaddress.machineValue }</td>
				<td>${Ipaddress.ipOne }</td>
				<td>${Ipaddress.ipTwo }</td>
				<td>${Ipaddress.ipNumber }</td>
				<td>${Ipaddress.bandwidthNumber }</td>
				<td>${Ipaddress.bandwidth }</td>
				<td>${Ipaddress.operationEnginner }</td>
				<td><fmt:formatDate value="${Ipaddress.operationDate }" pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${Ipaddress.dividerDate }" pattern="yyyy-MM-dd" /></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>