<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<section class="panel">
<h2 class="panel-heading clearfix tc">特殊贡献列表<span style="color:red;font-size:15px"></span></h2>
<div class="table-wrap">
<form action="" id="tsgx">
	<input type="hidden" id="id" name="id" value="0"> 
	<input type="hidden" id="token" name="token" value="${data.token}">
	<input type="hidden" id="modifyDate" name="modifyDate">
	<input type="hidden" id="type" name="type" value="特殊贡献">
	<table class="table tab_height" id="equipmentInOutTable">
		<thead>
			<tr>
				<th style="text-align:center">序号</th>
				<th style="text-align:center">姓名</th>
				<th style="text-align:center">部门</th>
				<th style="text-align:center">职务</th>
				<th style="text-align:center">PPT</th>
				<th style="text-align:center">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td style="text-align:center">1</td>
				<td style="text-align:center">石青松</td>
				<td style="text-align:center">产品部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E6%259F%25A5%25E8%25AF%25A2sql%25E8%25AF%25AD%25E5%258F%25A5.sql&resourcesName=201512/d1ab07a9-f90e-4801-a8ce-1931bac3abe7.sql"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'石青松')"
					type="button"role="button"style="background-color:red;
				<c:forEach items="${tsgxList}" var="m">
					<c:if test="${!empty m.name}"> 
					display:none 
					</c:if>
				</c:forEach>
				"
				>投票
				</a>
				<c:forEach items="${tsgxList}" var="m">
					<c:if test="${m.name eq '石青松'}"> 
					已投票
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">1</td>
				<td style="text-align:center">李岩</td>
				<td style="text-align:center">产品部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E6%259F%25A5%25E8%25AF%25A2sql%25E8%25AF%25AD%25E5%258F%25A5.sql&resourcesName=201512/d1ab07a9-f90e-4801-a8ce-1931bac3abe7.sql"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'李岩')"
					type="button"role="button"style="background-color:red;
				<c:forEach items="${tsgxList}" var="m">
					<c:if test="${!empty m.name}"> 
					display:none 
					</c:if>
				</c:forEach>
				"
				>投票
				</a>
				<c:forEach items="${tsgxList}" var="m">
					<c:if test="${m.name eq '李岩'}">
					已投票
					</c:if>
				</c:forEach>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/shjfgl/onlineInfo/tsgx.js" type="text/javascript"></script>