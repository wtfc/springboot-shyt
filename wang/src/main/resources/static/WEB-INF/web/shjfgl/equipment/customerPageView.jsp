<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<section class="scrollable jcGOA-section" id="scrollable"> 
<section class="panel">
	<table class="table table-striped tab_height first-td-tc" id="equipmentTable">
		<thead>
			<tr>
				<th>品牌型号</th>
				<th>机房</th>
				<th>机柜位置</th>
				<th>机柜编号</th>
				<th>上架时间</th>
				<th>管理网IP</th>
				<th>ETH2 IP/Netmask</th>
				<th>交换机对应端口</th>
				<th>电源（单/双电）</th>
				<th>设备功率</th>
				<th>U数</th>
				<th>A路电流</th>
				<th>B路电流</th>
				<th>操作系统</th>
				<th>设备配置</th>
				<th>资产编号</th>
				<th>SN</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listEquipment}" var="Equipment">
			<tr>
				<td>${Equipment.type }</td>
				<td>${Equipment.contact }</td>
				<td>${Equipment.address }</td>
				<td>${Equipment.machinaNumber }</td>
				<td><fmt:formatDate value="${Equipment.onlineDate }" pattern="yyyy-MM-dd" /></td>
				<td>${Equipment.ip }</td>
				<td>${Equipment.netmaskTwo }</td>
				<td>${Equipment.interchangerOne }</td>
				<td>${Equipment.power }</td>
				<td>${Equipment.plantPower }</td>
				<td>${Equipment.uCount }</td>
				<td>${Equipment.aCurrent }</td>
				<td>${Equipment.bCurrent }</td>
				<td>${Equipment.system }</td>
				<td>${Equipment.device }</td>
				<td>${Equipment.serialNumber }</td>
				<td>${Equipment.sn}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>