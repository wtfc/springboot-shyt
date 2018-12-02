<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="toaProductCloudTable">
		<thead>
			<tr>
				<th>CPU</th>				<th>内存</th>				<th>容量盘</th>				<th>性能盘</th>				<th>云主机快照</th>				<th>云硬盘快照</th>				<th>公网IP</th>				<th>带宽</th>				<th>路由器</th>				<th>负载均衡</th>				<th>金额(元)</th>				<th>资源模式</th>			</tr>
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