<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<section class="panel">
<h2 class="panel-heading clearfix tc">优秀员工列表<span style="color:red;font-size:15px"></span></h2>
<div class="table-wrap">
<form action="" id="yxyg">
	<input type="hidden" id="id" name="id" value="0"> 
	<input type="hidden" id="token" name="token" value="${data.token}">
	<input type="hidden" id="modifyDate" name="modifyDate">
	<input type="hidden" id="type" name="type" value="优秀员工">
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
				<td style="text-align:center">何全</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E4%25BD%2595%25E5%2585%25A8.pptx&resourcesName=201512/bf0f8a64-3640-4a14-9c50-328b8809634a.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'何全')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '何全'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '何全'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">2</td>
				<td style="text-align:center">卞剑飞</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E5%258D%259E%25E5%2589%2591%25E9%25A3%259E.pptx&resourcesName=201512/df670a16-670d-4bf5-8f33-da9f449d5af7.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'卞剑飞')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '卞剑飞'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '卞剑飞'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">3</td>
				<td style="text-align:center">刘超</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E5%2588%2598%25E8%25B6%2585.pptx&resourcesName=201512/f302816e-9648-4942-a175-3f7661114f1b.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'刘超')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '刘超'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '刘超'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">4</td>
				<td style="text-align:center">潘继清</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E6%25BD%2598%25E7%25BB%25A7%25E6%25B8%2585.pptx&resourcesName=201512/b17369a9-21f6-445c-a4c5-2283d06501e0.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'潘继清')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '潘继清'}">
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '潘继清'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">5</td>
				<td style="text-align:center">张磊</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E5%25BC%25A0%25E7%25A3%258A.pptx&resourcesName=201512/70fceb2d-35b1-4b46-8018-2d3a8b405010.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'张磊')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '张磊'}">  
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '张磊'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">6</td>
				<td style="text-align:center">董铮</td>
				<td style="text-align:center">网络管理部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E7%25BD%2591%25E7%25BB%259C%25E7%25AE%25A1%25E7%2590%2586%25E9%2583%25A8%25E8%2591%25A3%25E9%2593%25AE.pptx&resourcesName=201512/f094739e-2b73-4013-96b6-4da6f897e0a3.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'董铮')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '董铮'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '董铮'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">7</td>
				<td style="text-align:center">韩煦</td>
				<td style="text-align:center">网络管理部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E7%25BD%2591%25E7%25BB%259C%25E7%25AE%25A1%25E7%2590%2586%25E9%2583%25A8%25E9%259F%25A9%25E7%2585%25A6.pptx&resourcesName=201512/70acbff7-5462-4888-878e-4b293d9083b2.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'韩喣')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '韩喣'}">
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '韩喣'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">8</td>
				<td style="text-align:center">曹艳飞</td>
				<td style="text-align:center">网络监控部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E7%25BD%2591%25E7%25BB%259C%25E7%259B%2591%25E6%258E%25A7%25E9%2583%25A8%25E6%259B%25B9%25E8%2589%25B3%25E9%25A3%259E.pptx&resourcesName=201512/1d1f40e3-f73f-4fe5-a0f0-d27999e94786.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'曹艳飞')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '曹艳飞'}">
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '曹艳飞'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">9</td>
				<td style="text-align:center">林腾飞</td>
				<td style="text-align:center">系统维护部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E7%25B3%25BB%25E7%25BB%259F%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E6%259E%2597%25E8%2585%25BE%25E9%25A3%259E.pptx&resourcesName=201512/3d2da3a3-b0a2-40f2-a20a-e0d9cf80b8c1.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'林腾飞')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '林腾飞'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '林腾飞'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">10</td>
				<td style="text-align:center">张立元</td>
				<td style="text-align:center">呼叫中心</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E5%2591%25BC%25E5%258F%25AB%25E4%25B8%25AD%25E5%25BF%2583%25E5%25BC%25A0%25E7%25AB%258B%25E5%2585%2583.ppt&resourcesName=201512/956f5b05-2dff-4168-be7e-642beaccbd52.ppt"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'张立元')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '张立元'}">
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '张立元'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">11</td>
				<td style="text-align:center">姚雪</td>
				<td style="text-align:center">人事行政部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E4%25BA%25BA%25E4%25BA%258B%25E8%25A1%258C%25E6%2594%25BF%25E9%2583%25A8%25E5%25A7%259A%25E9%259B%25AA.pptx&resourcesName=201512/f3e493e2-1de5-4f0a-9450-c0baf612d729.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'姚雪')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '姚雪'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '姚雪'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">12</td>
				<td style="text-align:center">王飞</td>
				<td style="text-align:center">战略合作部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E6%2588%2598%25E7%2595%25A5%25E5%2590%2588%25E4%25BD%259C%25E9%2583%25A8%25E7%258E%258B%25E9%25A3%259E.pptx&resourcesName=201512/807bc70f-6718-4c5d-b22d-b2a635b2b67c.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'王飞')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '王飞'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '王飞'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">13</td>
				<td style="text-align:center">刘燚</td>
				<td style="text-align:center">战略合作部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%2591%2598%25E5%25B7%25A5-%25E6%2588%2598%25E7%2595%25A5%25E5%2590%2588%25E4%25BD%259C%25E9%2583%25A8%25E5%2588%2598%25E7%2587%259A.ppt&resourcesName=201512/7c975615-f658-48fd-9458-70ea65fb2183.ppt"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'刘燚')"
					type="button"style="background-color:red;
				<c:if test="${number ne 'a'}">
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '刘燚'}"> 
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
				<c:forEach items="${yxygList}" var="m">
					<c:if test="${m.name eq '刘燚'}"> 
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
<script src="${sysPath}/js/com/shjfgl/onlineInfo/yxyg.js" type="text/javascript"></script>