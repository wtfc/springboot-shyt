<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable  jcGOA-section" id="scrollable">
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="visitTable">
		<thead>
			<tr>
				<th>回访类型</th>
				<th>回访日期</th>
				<th>回访费用(元)</th>
				<!-- <th>回访内容</th>
				<th>回访反馈</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${visits}" var="visits" >
			<tr>
				<td>${visits.visitStatus }</td>
				<td><fmt:formatDate value="${visits.visitDate }" pattern="yyyy-MM-dd" /></td>
				<td>${visits.visitPay }</td>
				<%-- <td>${visits.visit }</td>
				<td>${visits.remark }</td> --%>
			</tr>
			</c:forEach> 
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>