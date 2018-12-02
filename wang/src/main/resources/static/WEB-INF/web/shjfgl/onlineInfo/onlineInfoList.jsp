<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<!-- <header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>机房设备进出表</h1>
			<div class="crumbs"></div>
		</div>
	</header> -->
	

<section class="panel">
<h2 class="panel-heading clearfix tc">森华易腾中层管理人员列表<span style="color:red;font-size:15px">(请您至少评价5人，其中1人须为直属上级)</span></h2>
<div class="table-wrap">
	<table class="table tab_height" id="equipmentInOutTable">
		<thead>
			<tr>
				<th style="text-align:center">序号</th>
				<th style="text-align:center">姓名</th>
				<th style="text-align:center">部门</th>
				<th style="text-align:center">职务</th>
				<th style="text-align:center">是否完成</th>
				<th style="text-align:center">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr >
				<td style="text-align:center">1</td>
				<td style="text-align:center">石青松</td>
				<td style="text-align:center">产品部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '石青松'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '石青松'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=石青松"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">2</td>
				<td style="text-align:center">葛薇薇</td>
				<td style="text-align:center">公关部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '葛薇薇'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '葛薇薇'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=葛薇薇"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">3</td>
				<td style="text-align:center">张微</td>
				<td style="text-align:center">人事行政部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '张微'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '张微'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=张微"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">4</td>
				<td style="text-align:center">王蕾</td>
				<td style="text-align:center">经营财务部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '王蕾'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '王蕾'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=王蕾"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">5</td>
				<td style="text-align:center">王蕊</td>
				<td style="text-align:center">呼叫中心</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '王蕊'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '王蕊'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=王蕊"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">6</td>
				<td style="text-align:center">郭涛</td>
				<td style="text-align:center">网络管理部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '郭涛'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '郭涛'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=郭涛"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">7</td>
				<td style="text-align:center">张强</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">经理</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '张强'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '张强'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=张强"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">8</td>
				<td style="text-align:center">陈洁</td>
				<td style="text-align:center">人事行政部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '陈洁'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '陈洁'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=陈洁"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">9</td>
				<td style="text-align:center">刘佳佳</td>
				<td style="text-align:center">经营财务部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '刘佳佳'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '刘佳佳'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=刘佳佳"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">10</td>
				<td style="text-align:center">陆锦云</td>
				<td style="text-align:center">销售部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '陆锦云'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '陆锦云'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=陆锦云"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">11</td>
				<td style="text-align:center">高卫东</td>
				<td style="text-align:center">销售部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '高卫东'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '高卫东'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=高卫东"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">12</td>
				<td style="text-align:center">赵存生</td>
				<td style="text-align:center">销售部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '赵存生'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '赵存生'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=赵存生"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">13</td>
				<td style="text-align:center">靳海静</td>
				<td style="text-align:center">销售部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '靳海静'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '靳海静'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=靳海静"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">14</td>
				<td style="text-align:center">郝晓夏</td>
				<td style="text-align:center">网络监控部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '郝晓夏'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '郝晓夏'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=郝晓夏"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">15</td>
				<td style="text-align:center">程浩</td>
				<td style="text-align:center">系统维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '程浩'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '程浩'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=程浩"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">16</td>
				<td style="text-align:center">董文杰</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '董文杰'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '董文杰'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=董文杰"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">17</td>
				<td style="text-align:center">赵伟</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '赵伟'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '赵伟'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=赵伟"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">18</td>
				<td style="text-align:center">王忠宝</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '王忠宝'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '王忠宝'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=王忠宝"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">19</td>
				<td style="text-align:center">李晗</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '李晗'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '李晗'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=李晗"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">20</td>
				<td style="text-align:center">张浩</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '张浩'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '张浩'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=张浩"
				>参与调研</a>
				</td>
			</tr>
			<tr>
				<td style="text-align:center">21</td>
				<td style="text-align:center">胡斌</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '胡斌'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '胡斌'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=胡斌"
				>参与调研</a>
				</td>
			</tr><tr>
				<td style="text-align:center">22</td>
				<td style="text-align:center">郭琪</td>
				<td style="text-align:center">运行维护部</td>
				<td style="text-align:center">主管</td>
				<td style="text-align:center">
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '郭琪'}"> 
						已完成
					</c:if>
				</c:forEach>
				</td>
				<td style="text-align:center">
				<a class="a-icon i-new m-r-xs"
				<c:forEach items="${dlist}" var="m">
					<c:if test="${m.extStr1 eq '郭琪'}">
					  style="display:none" 
					</c:if>
				</c:forEach>
				href="/projectMonitor/machine/onlineInfo/manage.action?name=郭琪"
				>参与调研</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>