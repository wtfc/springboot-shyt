<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header>
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaFinanceMainForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">收入信息 </h3>
                <input type="hidden" id="id" name="id" value="${financeMain.id}">
				<input type="hidden" id="token" name="token" value="${data.token}">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td >收入编号</td>
							<td>
								${financeMain.orderNo }
							</td>
							<td >客户名称</td>
							<td>
								${financeMain.companyName }
							</td>
							<td >业务类型</td>
							<td>
								${financeMain.companyTypeValue }
							</td>
							<td >变动日期</td>
							<td>
								<fmt:formatDate value="${financeMain.orderDate }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
						<tr>
							<td >业务拓展部</td>
							<td>
								${financeMain.department }
							</td>
							<td >拓展专员</td>
							<td>
								${financeMain.sale }
							</td>
							<td >客户关系维护组</td>
							<td>
								${financeMain.oldDepartment }
							</td>
							<td >维护专员</td>
							<td>
								${financeMain.maintenanSale }
							</td>
						</tr>
						<tr>
							<td >登记月份</td>
							<td>
								${financeMain.months }
							</td>
							<td >资源变动类型</td>
							<td>
								${financeMain.resourceType }
							</td>
							<td >机房</td>
							<td>
								${financeMain.roomNameValue }
							</td>
							<td >付费方式</td>
							<td>
								${financeMain.payType }
							</td>
						</tr>
						<tr>
							<td >专线类型</td>
							<td>
								${financeMain.lineCategory }
							</td>
							<td >计费起始时间</td>
							<td>
								<fmt:formatDate value="${financeMain.cycleStart }" pattern="yyyy-MM-dd"/>
							</td>
							<td >计费终止时间</td>
							<td>
								<fmt:formatDate value="${financeMain.cycleEnd }" pattern="yyyy-MM-dd"/>
							</td>
							<td >单天计算方式</td>
							<td>
								${financeMain.singleCharg }
							</td>
						</tr>
						<tr>
							<td >超流量取值方式</td>
							<td>
								${financeMain.overflowCategory }
							</td>
							<td >合同编号</td>
							<td>
								${financeMain.cardNo }
							</td>
							<td >合同金额</td>
							<td>
								${financeMain.cardAmount }
							</td>
							<td >带宽端口类型</td>
							<td>
								${financeMain.performanceAmount }
							</td>
						</tr>
						<tr>
							<td>合同类别</td>
							<td>
								${financeMain.contractStatus }
							</td>
							<td>衍生合同编码</td>
							<td>
								${financeMain.deriveNo }
							</td>
							<td>合同起始日期</td>
							<td>
								<fmt:formatDate value="${financeMain.startDate }" pattern="yyyy-MM-dd"/>
							</td>
							<td>合同终止日期</td>
							<td>
								<fmt:formatDate value="${financeMain.endDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
						<tr>
							<td >折扣</td>
							<td>
								${financeMain.discount }
							</td>
							<td >存量合同额</td>
							<td>
								${financeMain.cardStockAmount }
							</td>
							<td >预存金额</td>
							<td>
								${financeMain.prestoreAmount }
							</td>
							<td >备注</td>
							<td >
								${financeMain.orderRemark }
							</td>
						</tr>
					</tbody>
				</table>
				<table class="table">
					<tbody>
						<tr>
							<td style="width:8%">资源名称</td>
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
						<tr>
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
						</tbody>
					</table>
					<h4 style="text-align:center">应收金额合计:${financeMain.extStr1}</h4>
					<table class="table" id="dictable">
					<thead>
						<tr>
							<td >应收月份</td>
							<td >应收金额</td>
							<td >应收起始日期</td>
							<td >应收终止日期</td>
							<td >代理费</td>
							<td >是否开票</td>
							<td >开票日期</td>
							<td >发票号码</td>
							<td >发票金额</td>
							<td >未开票金额</td>
							<td >是否回款</td>
							<td >回款日期</td>
							<td >回款金额</td>
							<td >未回款金额</td>
						</tr>
					</thead>
					<tbody id="dicinfolist">
						<c:forEach items="${dicNodeInfo}" var="dicinfoVo" varStatus="status">
							<tr id="${status.count }" >
								<td>
									${dicinfoVo.invoicesMonth }
								</td>
								<td>
									${dicinfoVo.monthAmount }
								</td>
								<td>
									<fmt:formatDate value="${dicinfoVo.invoicesStartdate }" pattern="yyyy-MM-dd" />
								</td>
								<td>
									<fmt:formatDate value="${dicinfoVo.invoicesEnddate }" pattern="yyyy-MM-dd" />
								</td>
								<td>
									${dicinfoVo.commision }
									<input type="hidden" id="idd" name="idd" value="${dicinfoVo.id}"/>
								</td>
								<td>
									${dicinfoVo.invoicesState }
								</td>
								<td>
									<fmt:formatDate value="${dicinfoVo.vdateDate }" pattern="yyyy-MM-dd"/>
								</td>
								<td>
									${dicinfoVo.invoicesNo }
								</td>
								<td>
									${dicinfoVo.invoicesAccount }
								</td>
								<td>
									${dicinfoVo.noinvoicesAccount }
								</td>
								<td>
									${dicinfoVo.receivedState }
								</td>
								<td>
									<fmt:formatDate value="${dicinfoVo.receivedDate }" pattern="yyyy-MM-dd"/>
								</td>
								<td>
									${dicinfoVo.receivedAccount }
								</td>
								<td>
									${dicinfoVo.arrearage }
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</form>
			</section>
		</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>