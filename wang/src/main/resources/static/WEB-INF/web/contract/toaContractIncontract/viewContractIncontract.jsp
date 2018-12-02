<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- TODO 面包屑 -->
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="toaContractIncontractTable">
		<thead>
			<tr>
				<th>合同编码</th>				<th>发起人</th>				<th>合同类别</th>
				<th>协议类型</th>				<th>合同金额(元)</th>				<th>合同起始日期</th>				<th>合同终止日期</th>			</tr>
		</thead>
		<tbody>
			<c:forEach items="${inContract}" var="inContract">
			<tr>
				<td>${inContract.contractNumber }</td>
				<td>${inContract.leard }</td>
				<td>
					${inContract.contractStatus}
				</td>
				<td>${inContract.agreement}</td>
				<td>${inContract.contractMoney }</td>
				<td><fmt:formatDate value="${inContract.startDate }" pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${inContract.endDate }" pattern="yyyy-MM-dd" /></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>