<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<section class="scrollable jcGOA-section" id="scrollable">
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="customerPeopleTable">
		<thead>
			<tr>
				<th>联系人姓名</th>
				<th>职务</th>
				<th>联系电话</th>
				<th>身份证号</th>
				<th>邮箱</th>
				<th>微信</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${customers}" var="customers" >
			<tr>
				<td>${customers.name }</td>
				<td>${customers.job }</td>
				<td>${customers.tel }</td>
				<td>${customers.idCard }</td>
				<td>${customers.email }</td>
				<td>${customers.weixin }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%-- <script src='${sysPath}/js/com/shyt/customerPeople/customerPeople.js'></script> --%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>