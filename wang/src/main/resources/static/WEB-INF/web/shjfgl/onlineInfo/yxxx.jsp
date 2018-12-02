<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<section class="panel">
<h2 class="panel-heading clearfix tc">优秀新星列表<span style="color:red;font-size:15px"></span></h2>
<div class="table-wrap">
<form action="" id="yxxx">
	<input type="hidden" id="id" name="id" value="0"> 
	<input type="hidden" id="token" name="token" value="${data.token}">
	<input type="hidden" id="modifyDate" name="modifyDate">
	<input type="hidden" id="type" name="type" value="优秀新星">
	<table class="table tab_height" id="equipmentInOutTable">
		<thead>
			<tr>
				<th style="text-align:center">序号</th>
				<th style="text-align:center">姓名</th>
				<th style="text-align:center">部门</th>
				<th style="text-align:center">竞选报告PPT</th>
				<th style="text-align:center">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td style="text-align:center">1</td>
				<td style="text-align:center">杨凯</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E6%2596%25B0%25E6%2598%259F-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E6%259D%25A8%25E5%2587%25AF.ppt&resourcesName=201512/714c0298-0feb-4b1f-982a-6e859ed4a85d.ppt"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'杨凯')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '杨凯'}">
					display:none 
					</c:if>
				</c:forEach>
				</c:if>
				<c:if test="${number eq 'a'}">
					display:none
				</c:if>
				"
				>投票
				</a>
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '杨凯'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">2</td>
				<td style="text-align:center">张子祥</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E6%2596%25B0%25E6%2598%259F-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E5%25BC%25A0%25E5%25AD%2590%25E7%25A5%25A5.pptx&resourcesName=201512/1165dc02-ef06-41c5-8bac-e26914e3c2e5.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'张子祥')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '张子祥'}">
					display:none 
					</c:if>
				</c:forEach>
				</c:if>
				<c:if test="${number eq 'a'}">
					display:none
				</c:if>
				"
				>投票
				</a>
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '张子祥'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">3</td>
				<td style="text-align:center">郭永</td>
				<td style="text-align:center">网络监控部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E6%2596%25B0%25E6%2598%259F-%25E7%25BD%2591%25E7%25BB%259C%25E7%259B%2591%25E6%258E%25A7%25E9%2583%25A8%25E9%2583%25AD%25E6%25B0%25B8.pptx&resourcesName=201512/183f159c-5859-4a1d-b3c6-da6a2ff04fa7.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'郭永')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '郭永'}"> 
					display:none 
					</c:if>
				</c:forEach> 
				</c:if>
				<c:if test="${number eq 'a'}">
					display:none
				</c:if>
				"
				>投票
				</a>
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '郭永'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">4</td>
				<td style="text-align:center">王超</td>
				<td style="text-align:center">网络监控部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E6%2596%25B0%25E6%2598%259F-%25E7%25BD%2591%25E7%25BB%259C%25E7%259B%2591%25E6%258E%25A7%25E9%2583%25A8%25E7%258E%258B%25E8%25B6%2585.pptx&resourcesName=201512/2f2d2ece-e749-4ae6-b08e-a64c5d6067d5.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'王超')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '王超'}"> 
					display:none 
					</c:if>
				</c:forEach>
				</c:if>
				<c:if test="${number eq 'a'}">
					display:none
				</c:if>
				"
				>投票
				</a>
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '王超'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">5</td>
				<td style="text-align:center">徐伟利</td>
				<td style="text-align:center">销售四部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E6%2596%25B0%25E6%2598%259F-%25E9%2594%2580%25E5%2594%25AE%25E9%2583%25A8%25E5%25BE%2590%25E4%25BC%259F%25E5%2588%25A9.ppt&resourcesName=201512/054a46fa-eefa-4ff1-958c-31f9af2ea44f.ppt"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'徐伟俐')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '徐伟俐'}"> 
					display:none 
					</c:if>
				</c:forEach>
				</c:if>
				<c:if test="${number eq 'a'}">
					display:none
				</c:if>
				"
				>投票
				</a>
				<c:forEach items="${yxxxList}" var="m">
					<c:if test="${m.name eq '徐伟俐'}"> 
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
<script src="${sysPath}/js/com/shjfgl/onlineInfo/yxxx.js" type="text/javascript"></script>