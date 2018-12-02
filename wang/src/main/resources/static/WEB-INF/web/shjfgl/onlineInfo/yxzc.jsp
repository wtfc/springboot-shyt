<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<section class="panel">
<h2 class="panel-heading clearfix tc">优秀中层列表<span style="color:red;font-size:15px"></span></h2>
<div class="table-wrap">
<form action="" id="yxzc">
	<input type="hidden" id="id" name="id" value="0"> 
	<input type="hidden" id="token" name="token" value="${data.token}">
	<input type="hidden" id="modifyDate" name="modifyDate">
	<input type="hidden" id="type" name="type" value="优秀中层">
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
				<td style="text-align:center">胡斌</td>
				<td style="text-align:center">运维五部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E4%25B8%25AD%25E5%25B1%2582-%25E8%25BF%2590%25E7%25BB%25B4%25E4%25BA%2594%25E9%2583%25A8%25E8%2583%25A1%25E6%2596%258C.pptx&resourcesName=201512/1729283c-eb35-4f88-9a69-5633809a648e.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'胡斌')"
					type="button"style="background-color:red;
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${!empty m.name}"> 
					display:none 
					</c:if>
				</c:forEach>
				"
				>投票
				</a>
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${m.name eq '胡斌'}"> 
					已投票
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">2</td>
				<td style="text-align:center">陈洁</td>
				<td style="text-align:center">网络安全组</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E4%25B8%25AD%25E5%25B1%2582-%25E7%25BD%2591%25E7%25BB%259C%25E5%25AE%2589%25E5%2585%25A8%25E7%25BB%2584%25E9%2599%2588%25E6%25B4%2581.pptx&resourcesName=201601/339ed3de-c1cb-4fb5-b6a5-27e68247b7b1.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'陈洁')"
					type="button"style="background-color:red;
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${!empty m.name}"> 
					display:none 
					</c:if>
				</c:forEach>
				"
				>投票
				</a>
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${m.name eq '陈洁'}"> 
					已投票
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">3</td>
				<td style="text-align:center">郝晓夏</td>
				<td style="text-align:center">网络监控部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E4%25B8%25AD%25E5%25B1%2582-%25E7%25BD%2591%25E7%25BB%259C%25E7%259B%2591%25E6%258E%25A7%25E9%2583%25A8%25E9%2583%259D%25E6%2599%2593%25E5%25A4%258F.pptx&resourcesName=201512/3bbfb8be-3237-4c1b-9809-b8c1c8bb4c5c.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'郝晓夏')"
					type="button"style="background-color:red;
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${!empty m.name}"> 
					display:none 
					</c:if>
				</c:forEach>
				"
				>投票
				</a>
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${m.name eq '郝晓夏'}"> 
					已投票
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">4</td>
				<td style="text-align:center">高卫东</td>
				<td style="text-align:center">销售部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E4%25B8%25AD%25E5%25B1%2582-%25E9%2594%2580%25E5%2594%25AE%25E9%2583%25A8%25E9%25AB%2598%25E5%258D%25AB%25E4%25B8%259C.ppt&resourcesName=201512/ba3aecdf-acc2-46bf-9ea3-8e8cd3e70503.ppt"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'高卫东')"
					type="button"style="background-color:red;
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${!empty m.name}"> 
					display:none 
					</c:if>
				</c:forEach>
				"
				>投票
				</a>
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${m.name eq '高卫东'}"> 
					已投票
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">5</td>
				<td style="text-align:center">张强</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E4%25B8%25AD%25E5%25B1%2582-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E5%25BC%25A0%25E5%25BC%25BA.pptx&resourcesName=201512/8cf1d772-13b3-4541-b68a-5a3468c2c723.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'张强')"
					type="button"style="background-color:red;
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${!empty m.name}"> 
					display:none 
					</c:if>
				</c:forEach>
				"
				>投票
				</a>
				<c:forEach items="${yxzcList}" var="m">
					<c:if test="${m.name eq '张强'}"> 
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
<script src="${sysPath}/js/com/shjfgl/onlineInfo/yxzc.js" type="text/javascript"></script>