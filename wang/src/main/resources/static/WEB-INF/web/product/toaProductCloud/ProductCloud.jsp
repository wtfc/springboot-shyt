<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="toaProductCloudTable">
		<thead>
			<tr>
				<th>CPU</th>
		</thead>
		<tbody>
			<c:forEach items="${listProductCloud}" var="listProductCloud">
			<tr>
				<td>${listProductCloud.cpu }</td>
				<td>${listProductCloud.ram }</td>
				<td>${listProductCloud.stick }</td>
				<td>${listProductCloud.performance }</td>
				<td>${listProductCloud.cloudPhoto }</td>
				<td>${listProductCloud.cloudDive }</td>
				<td>${listProductCloud.publicIp }</td>
				<td>${listProductCloud.bandwidth }</td>
				<td>${listProductCloud.router }</td>
				<td>${listProductCloud.loadBalancer }</td>
				<td>${listProductCloud.amount }</td>
				<td>${listProductCloud.resourceType }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>