<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable  jcGOA-section" id="scrollable">
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="complainTable">
		<thead>
			<tr>
				<th>被投诉部门</th>
				<th>投诉日期</th>
				<th>投诉类型</th>
				<th>是否解决</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${complains}" var="complains" >
			<tr>
				<td>${complains.partment }</td>
				<td><fmt:formatDate value="${complains.complainDate }" pattern="yyyy-MM-dd" /></td>
				<td>${complains.complainStatus }</td>
				<td>${complains.status }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>