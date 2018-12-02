<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<section class="panel">
<h2 class="panel-heading clearfix tc">优秀团队列表<span style="color:red;font-size:15px"></span></h2>
<div class="table-wrap">
<form action="" id="yxtd">
	<input type="hidden" id="id" name="id" value="0"> 
	<input type="hidden" id="token" name="token" value="${data.token}">
	<input type="hidden" id="modifyDate" name="modifyDate">
	<input type="hidden" id="type" name="type" value="优秀团队">
	<table class="table tab_height" id="equipmentInOutTable">
		<thead>
			<tr>
				<th style="text-align:center">序号</th>
				<th style="text-align:center">部门</th>
				<th style="text-align:center">竞选报告PPT</th>
				<th style="text-align:center">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td style="text-align:center">1</td>
				<td style="text-align:center">网络安全组</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%259B%25A2%25E9%2598%259F-%25E7%25BD%2591%25E7%25BB%259C%25E5%25AE%2589%25E5%2585%25A8%25E7%25BB%2584.pptx&resourcesName=201601/794bfcc3-764f-4d1c-b5fb-559c9ca9e2b9.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'网络安全组')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '网络安全组'}"> 
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
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '网络安全组'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">2</td>
				<td style="text-align:center">运维五部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%259B%25A2%25E9%2598%259F-%25E8%25BF%2590%25E8%25A1%258C%25E7%25BB%25B4%25E6%258A%25A4%25E9%2583%25A8%25E8%25BF%2590%25E7%25BB%25B4%25E4%25BA%2594%25E9%2583%25A8.pptx&resourcesName=201512/85afc539-d06b-441b-aa5f-0f17cd8db52f.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'运行维护部-运维五部')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '运行维护部-运维五部'}">  
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
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '运行维护部-运维五部'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">3</td>
				<td style="text-align:center">网络监控部</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%259B%25A2%25E9%2598%259F-%25E7%25BD%2591%25E7%25BB%259C%25E7%259B%2591%25E6%258E%25A7%25E9%2583%25A8.pptx&resourcesName=201512/dcfc95ae-9058-47fc-8a48-4296bd7f1add.pptx"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'网络监控部')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '网络监控部'}">  
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
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '网络监控部'}"> 
					已投票 
					</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">4</td>
				<td style="text-align:center">销售三组</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				href="/projectMonitor/content/attach/download.action?fileName=%25E4%25BC%2598%25E7%25A7%2580%25E5%259B%25A2%25E9%2598%259F-%25E9%2594%2580%25E5%2594%25AE%25E9%2583%25A8%25E9%2594%2580%25E5%2594%25AE%25E4%25B8%2589%25E7%25BB%2584.ppt&resourcesName=201512/b9b5b3b1-680e-47d5-b86b-3768747cb319.ppt"
				>PPT下载</a>
				</td>
				<td style="text-align:center">
				<a class="btn " onclick="equipmentInOutModule.saveOrModify(true,'销售三组')"
					type="button"style="background-color:red;
					<c:if test="${number ne 'a'}">
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '销售三组'}"> 
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
				<c:forEach items="${yxtdList}" var="m">
					<c:if test="${m.name eq '销售三组'}"> 
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
<script src="${sysPath}/js/com/shjfgl/onlineInfo/yxtd.js" type="text/javascript"></script>