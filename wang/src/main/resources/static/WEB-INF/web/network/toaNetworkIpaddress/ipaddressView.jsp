<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel clearfix search-box search-shrinkage">
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkIpaddressTable">
		<thead>
			<tr>
				<th>机房</th>				<th>IP地址<br>段开始</th>				<th>IP地址<br>段结束</th>				<th>IP<br>数量</th>				<th>设备端口</th>				<th>带宽</th>				<th>操作工程师</th>				<th>操作时间</th>				<th>分配时间</th>			</tr>
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