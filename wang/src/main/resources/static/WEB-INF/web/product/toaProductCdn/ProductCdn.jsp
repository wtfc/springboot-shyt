<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="toaProductCdnTable">
		<thead>
			<tr>
				<th>用户名</th>				<th>加速域名</th>				<th>回源IP/回源域名</th>				<th>计费方式</th>				<th>单价(元)</th>				<th>保底数值</th>				<th>计费数值</th>				<th>金额(元)</th>				<th>计费开始日期</th>				<th>计费终止日期</th>			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listProduct}" var="listProduct">
			<tr>
				<td>${listProduct.userName }</td>
				<td>${listProduct.speedName }</td>
				<td>${listProduct.stick }</td>
				<td>${listProduct.chargeMode }</td>
				<td>${listProduct.price }</td>
				<td>${listProduct.flooredNumber }</td>
				<td>${listProduct.chargeNumber }</td>
				<td>${listProduct.amount }</td>
				<td><fmt:formatDate value="${listProduct.startDate }" pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${listProduct.startEnd }" pattern="yyyy-MM-dd" /></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>