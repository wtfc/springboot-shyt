<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table">
		<thead>
			<tr>
				<td>合同信息</td>
				<td>资源信息</td>
				<td>保底带宽</td>
				<td>端口带宽</td>
				<td>超流量带宽</td>
				<td>机柜</td>
				<td>服务器</td>
				<td>IP</td>
				<td>交换机</td>
				<td>链路</td>
				<td>端口</td>
				<td>内存</td>
				<td>CPU</td>
				<td>硬盘</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${toaFinanceMains}" var="financeMain" >
			<tr>
				<td>${financeMain.cardAmount}</td>
				<td>数量</td>
				<td>
					${financeMain.minBandwidth }
				</td>
				<td>
					${financeMain.portBandwidth }
				</td>
				<td>
					${financeMain.overflowBandwidth }
				</td>
				<td>
					${financeMain.cabinetNum }
				</td>
				<td>
					${financeMain.serviceNum }
				</td>
				<td>
					${financeMain.ipNum }
				</td>
				<td>
					${financeMain.switchNum }
				</td>
				<td>
					${financeMain.odfNum }
		 		</td>
				<td>
					${financeMain.portNum }
				</td>
				<td>
					${financeMain.memoryNum }
				</td>
				<td>
					${financeMain.cpuNum }
				</td>
				<td>
					${financeMain.diskNum }
				</td>
			</tr>
			<tr>
				<td>${financeMain.cardNo }</td>
				<td>单价</td>
				<td>
					${financeMain.minBandwidthPrice }
				</td>
				<td>
					${financeMain.portBandwidthPrice }
				</td>
				<td>
					${financeMain.overflowBandwidthPrice }
				</td>
				<td>
					${financeMain.cabinetPrice }
				</td>
				<td>
					${financeMain.servicePrice }
				</td>
				<td>
					${financeMain.ipPrice }
				</td>
				<td>
					${financeMain.switchPrice }
				</td>
				<td>
					${financeMain.odfPrice }
				</td>
				<td>
					${financeMain.portPrice }
				</td>
				<td>
					${financeMain.memoryPrice }
				</td>
				<td>
					${financeMain.cpuPrice }
				</td>
				<td>
					${financeMain.diskPrice }
				</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>