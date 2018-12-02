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
	<section class="panel clearfix">
		<form class="table-wrap  m-20-auto" id="toaFinanceMainForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">收入信息 </h3>
                <input type="hidden" id="id" name="id" value="0">
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId">
				<input type="hidden" id="startIntel" name="startIntel"/>
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td ><span class="required">*</span>收入编号</td>
							<td>
								<input type="text"  style="width:100%;"id="orderNo" name="orderNo" value="${applyNum}" readOnly/>
							</td>
							<td ><span class="required">*</span>客户名称</td>
							<td>
								<a  onclick="toaFinanceMainModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>
							</td>
							<td ><span class="required">*</span>业务类型</td>
							<td>
								<dic:select cssStyle="width:100%;"  id="companyType" name="companyType" dictName="customerType" headName="-请选择-" headValue="" defaultValue=""/>
							</td>
							<td><span class="required">*</span>变动日期</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="orderDate" name="orderDate" />
							</td>
						</tr>
						<tr>
							<td ><span class="required">*</span>业务拓展部</td>
							<td>
								<input type="text" readonly style="width:100%;"id="department" name="department" />
							</td>
							<td ><span class="required">*</span>拓展专员</td>
							<td>
								<input type="text" readonly style="width:100%;"id="sale" name="sale" />
							</td>
							<td >客户关系维护组</td>
							<td>
								<input type="text"  style="width:100%;"id="oldDepartment" name="oldDepartment" readonly value="客户关系维护组"/>
							</td>
							<td >维护专员</td>
							<td>
								<input type="text" readonly style="width:100%;"id="maintenanSale" name="maintenanSale" />
							</td>
						</tr>
						<tr>
							<td ><span class="required">*</span>登记月份</td>
							<td>
								<input type="text"  style="width:100%;"id="months" name="months" />
							</td>
							<td ><span class="required">*</span>资源变动类型</td>
							<td>
								<select style="width:100%;"id="resourceType" name="resourceType">
									<option value="">请选择</option>
									<option value="新增">新增</option>
									<option value="扩容">扩容</option>
									<option value="减容">减容</option>
									<option value="终止">终止</option>
									<option value="存量">存量</option>
									<option value="其他">其他</option>
								</select>
							</td>
							<td ><span class="required">*</span>机房</td>
							<td>
								<dic:select cssStyle="width:100%;" id="roomName" name="roomName" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
							</td>
							<td ><span class="required">*</span>付费方式</td>
							<td>
								<c:if test="${empty Id}">
									<select  style="width:100%;"id="payType" onchange="toaFinanceMainModule.date()"name="payType">
										<option value="">请选择</option>
										<option value="当月付" >当月付</option>
										<option value="后月付">后月付</option>
										<option value="季付">季付</option>
										<option value="后季度付">后季度付</option>
										<option value="半年付">半年付</option>
										<option value="年付">年付</option>
										<option value="超流量">超流量</option>
										<option value="临时扩容">临时扩容</option>
										<option value="一次性">一次性</option>
										<option value="其他">其他</option>
									</select>
								</c:if>
								<c:if test="${!empty Id}">
									<select  style="width:100%;"id="payType" name="payType">
										<option value="">请选择</option>
										<option value="当月付" >当月付</option>
										<option value="后月付">后月付</option>
										<option value="季付">季付</option>
										<option value="后季度付">后季度付</option>
										<option value="半年付">半年付</option>
										<option value="年付">年付</option>
										<option value="超流量">超流量</option>
										<option value="临时扩容">临时扩容</option>
										<option value="一次性">一次性</option>
										<option value="其他">其他</option>
									</select>
								</c:if>
							</td>
						</tr>
						<tr>
							<td ><span class="required">*</span>专线类型</td>
							<td>
								<select style="width:100%;"id="lineCategory" name="lineCategory">
									<option value="">请选择</option>
									<option value="单联通">单联通</option>
									<option value="单电信">单电信</option>
									<option value="多线">多线</option>
								</select>
							</td>
							<td ><span class="required">*</span>计费起始时间</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="cycleStart" name="cycleStart" />
							</td>
							<td ><span class="required">*</span>计费终止时间</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="cycleEnd" name="cycleEnd" />
							</td>
							<td >单天计算方式</td>
							<td>
								<select style="width:100%;" id="singleCharg" name="singleCharg">
									<option value="">请选择</option>
									<option value="365">365</option>
									<option value="当月">当月</option>
									<option value="30天">30天</option>
								</select>
							</td>
						</tr>
						<tr>
							<td >超流量取值方式</td>
							<td>
								<select style="width:100%;"id="overflowCategory" name="overflowCategory">
									<option value="">请选择</option>
									<option value="95值">95值</option>
									<option value="第一峰值">第一峰值</option>
									<option value="第二峰值">第二峰值</option>
									<option value="第三峰值">第三峰值</option>
									<option value="第八峰值">第八峰值</option>
								</select>
							</td>
							<td >合同编号</td>
							<td>
								<a  onclick="toaFinanceMainModule.closeWin1();" type="button" href="#new-agency1"  role="button" data-toggle="modal"><input type="text"  style="width:100%;"id="cardNo" readonly name="cardNo" /></a>
							</td>
							<td ><span class="required">*</span>合同金额</td>
							<td>
								<input type="text"  style="width:100%;"id="cardAmount" name="cardAmount" readonly/>
							</td>
							<td >带宽端口类型</td>
							<td>
								<input type="text"  style="width:100%;"id="performanceAmount" name="performanceAmount" />
							</td>
						</tr>
						<tr>
							<td>合同类别</td>
							<td>
								<input type="text" style="width:100%;" id="contractStatus" name="contractStatus" readonly="readonly"/>
							</td>
							<td>衍生合同编码</td>
							<td>
								<input type="text"  style="width:100%;"id="deriveNo" name="deriveNo" readonly="readonly"/>
							</td>
							<td>合同起始日期</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  id="startDate" name="startDate" style="width:100%;" disabled="disabled"/>
							</td>
							<td>合同终止日期</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  id="endDate" name="endDate" style="width:100%;" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td >折扣</td>
							<td>
								<input type="text"  style="width:100%;"id="discount" name="discount" />
							</td>
							<td >存量合同额</td>
							<td>
								<input type="text"  style="width:100%;"id="cardStockAmount" name="cardStockAmount" />
							</td>
							<td >预存金额</td>
							<td>
								<input type="text"  style="width:100%;"id="prestoreAmount" name="prestoreAmount" />
							</td>
							<td >备注</td>
							<td >
								<input type="text"  style="width:100%;"id="orderRemark" name="orderRemark" />
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
								<input  type="text"  style="width:100%;"id="minBandwidth" name="minBandwidth" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="portBandwidth" name="portBandwidth" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="overflowBandwidth" name="overflowBandwidth" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="cabinetNum" name="cabinetNum" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="serviceNum" name="serviceNum" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="ipNum" name="ipNum" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="switchNum" name="switchNum" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="odfNum" name="odfNum" />
					 		</td>
							<td>
								<input type="text"  style="width:100%;"id="portNum" name="portNum" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="memoryNum" name="memoryNum" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="cpuNum" name="cpuNum" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="diskNum" name="diskNum" />
							</td>
						</tr>
						<tr>
							<td>单价</td>
							<td>
								<input type="text"  style="width:100%;"id="minBandwidthPrice" name="minBandwidthPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="portBandwidthPrice" name="portBandwidthPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="overflowBandwidthPrice" name="overflowBandwidthPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="cabinetPrice" name="cabinetPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="servicePrice" name="servicePrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="ipPrice" name="ipPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="switchPrice" name="switchPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="odfPrice" name="odfPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="portPrice" name="portPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="memoryPrice" name="memoryPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="cpuPrice" name="cpuPrice" />
							</td>
							<td>
								<input type="text"  style="width:100%;"id="diskPrice" name="diskPrice" />
							</td>
						</tr>
						</tbody>
					</table>
					<c:if test="${!empty Id}">
						<h4 style="text-align:center">应收金额合计:<input type="text" id="extStr1" name="extStr1" readonly/></h4>
					</c:if>
					<table class="table" id="dictable">
					<thead>
						<tr>
							<td >应收月份</td>
							<td >应收金额</td>
							<td >应收起始日期</td>
							<td >应收终止日期</td>
							<td >代理费</td>
							<c:if test="${!empty Id}">
							<td >是否开票</td>
							<td >开票日期</td>
							<td >发票号码</td>
							<td >发票金额</td>
							<td >未开票金额</td>
							<td >是否回款</td>
							<td >回款日期</td>
							<td >回款金额</td>
							<td >未回款金额</td>
							</c:if>
						</tr>
					</thead>
					<tbody id="dicinfolist">
						<c:forEach items="${dicNodeInfo}" var="dicinfoVo" varStatus="status">
							<c:choose>
							<c:when test="${dicinfoVo.otherDeductions == 1}" >
								<tr>
									<td>
										${dicinfoVo.invoicesMonth }
									</td>
									<td>
										${dicinfoVo.monthAmount }
										<input type="hidden"  style="width:100%;"id="monthAmount${status.count}" name="monthAmount${status.count}" value="${dicinfoVo.monthAmount}"/>
									</td>
									<td>
										<fmt:formatDate value="${dicinfoVo.invoicesStartdate }" pattern="yyyy-MM-dd" />
									</td>
									<td>
										<fmt:formatDate value="${dicinfoVo.invoicesEnddate }" pattern="yyyy-MM-dd" />
									</td>
									<td>
										${dicinfoVo.commision}
									</td>
									<td>
										${dicinfoVo.invoicesState}
									</td>
									<td>
										<fmt:formatDate value="${dicinfoVo.vdateDate}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										${dicinfoVo.invoicesNo}
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
							</c:when>
							<c:otherwise>
								<tr id="${status.count}">
									<td>
										<input type="hidden" id="invoicesId${status.count}" name="invoicesId${status.count}" value="${dicinfoVo.id}"/>
										<input  type="text"  style="width:100%;"id="invoicesMonth${status.count }" name="invoicesMonth${status.count }" value="${dicinfoVo.invoicesMonth }"/>
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount${status.count }" name="monthAmount${status.count }" value="${dicinfoVo.monthAmount }"onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text" value="<fmt:formatDate value="${dicinfoVo.invoicesStartdate }" pattern="yyyy-MM-dd" />" style="width:100%;"id="invoicesStartdate${status.count }" name="invoicesStartdate${status.count }" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  value="<fmt:formatDate value="${dicinfoVo.invoicesEnddate }" pattern="yyyy-MM-dd" />"style="width:100%;"id="invoicesEnddate${status.count }" name="invoicesEnddate${status.count }" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision${status.count }" value="${dicinfoVo.commision}" name="commision${status.count }"/>
										<input type="hidden" id="idd${status.count }" name="idd${status.count }" value="${dicinfoVo.id}"/>
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
							</c:otherwise>
							</c:choose>
							</c:forEach>
							<c:if test="${!empty Id}">
								<c:if test="${list eq 1}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth2" name="invoicesMonth2" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount2" name="monthAmount2" onkeyup="checkInt2();" onpaste="checkInt2();" oncut="checkInt2();" ondrop="checkInt2();" onchange="checkInt2();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate2" name="invoicesStartdate2" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate2" name="invoicesEnddate2" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision2" name="commision2"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth3" name="invoicesMonth3" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount3" name="monthAmount3" onkeyup="checkInt3();"  ondrop="checkInt3();" onchange="checkInt3();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate3" name="invoicesStartdate3" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate3" name="invoicesEnddate3" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision3" name="commision3"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth4" name="invoicesMonth4" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount4" name="monthAmount4" onkeyup="checkInt4();"  ondrop="checkInt4();" onchange="checkInt4();" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate4" name="invoicesStartdate4" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate4" name="invoicesEnddate4" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision4" name="commision4"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth5" name="invoicesMonth5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount5" name="monthAmount5" onkeyup="checkInt5();"  ondrop="checkInt5();" onchange="checkInt5();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate5" name="invoicesStartdate5" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate5" name="invoicesEnddate5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision5" name="commision5"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth6" name="invoicesMonth6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount6" name="monthAmount6" onkeyup="checkInt6();"  ondrop="checkInt6();" onchange="checkInt6();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate6" name="invoicesStartdate6" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate6" name="invoicesEnddate6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision6" name="commision6"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth7" name="invoicesMonth7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount7" name="monthAmount7" onkeyup="checkInt7();"  ondrop="checkInt7();" onchange="checkInt7();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate7" name="invoicesStartdate7" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate7" name="invoicesEnddate7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision7" name="commision7"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 2}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth3" name="invoicesMonth3" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount3" name="monthAmount3" onkeyup="checkInt3();"  ondrop="checkInt3();" onchange="checkInt3();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate3" name="invoicesStartdate3" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate3" name="invoicesEnddate3" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision3" name="commision3"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth4" name="invoicesMonth4" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount4" name="monthAmount4" onkeyup="checkInt4();"  ondrop="checkInt4();" onchange="checkInt4();" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate4" name="invoicesStartdate4" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate4" name="invoicesEnddate4" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision4" name="commision4"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth5" name="invoicesMonth5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount5" name="monthAmount5" onkeyup="checkInt5();"  ondrop="checkInt5();" onchange="checkInt5();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate5" name="invoicesStartdate5" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate5" name="invoicesEnddate5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision5" name="commision5"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth6" name="invoicesMonth6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount6" name="monthAmount6" onkeyup="checkInt6();"  ondrop="checkInt6();" onchange="checkInt6();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate6" name="invoicesStartdate6" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate6" name="invoicesEnddate6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision6" name="commision6"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth7" name="invoicesMonth7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount7" name="monthAmount7" onkeyup="checkInt7();"  ondrop="checkInt7();" onchange="checkInt7();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate7" name="invoicesStartdate7" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate7" name="invoicesEnddate7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision7" name="commision7"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 3}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth4" name="invoicesMonth4" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount4" name="monthAmount4" onkeyup="checkInt4();"  ondrop="checkInt4();" onchange="checkInt4();" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate4" name="invoicesStartdate4" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate4" name="invoicesEnddate4" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision4" name="commision4"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth5" name="invoicesMonth5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount5" name="monthAmount5" onkeyup="checkInt5();"  ondrop="checkInt5();" onchange="checkInt5();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate5" name="invoicesStartdate5" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate5" name="invoicesEnddate5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision5" name="commision5"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth6" name="invoicesMonth6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount6" name="monthAmount6" onkeyup="checkInt6();"  ondrop="checkInt6();" onchange="checkInt6();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate6" name="invoicesStartdate6" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate6" name="invoicesEnddate6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision6" name="commision6"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth7" name="invoicesMonth7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount7" name="monthAmount7" onkeyup="checkInt7();"  ondrop="checkInt7();" onchange="checkInt7();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate7" name="invoicesStartdate7" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate7" name="invoicesEnddate7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision7" name="commision7"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 4}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth5" name="invoicesMonth5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount5" name="monthAmount5" onkeyup="checkInt5();"  ondrop="checkInt5();" onchange="checkInt5();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate5" name="invoicesStartdate5" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate5" name="invoicesEnddate5" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision5" name="commision5"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth6" name="invoicesMonth6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount6" name="monthAmount6" onkeyup="checkInt6();"  ondrop="checkInt6();" onchange="checkInt6();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate6" name="invoicesStartdate6" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate6" name="invoicesEnddate6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision6" name="commision6"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth7" name="invoicesMonth7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount7" name="monthAmount7" onkeyup="checkInt7();"  ondrop="checkInt7();" onchange="checkInt7();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate7" name="invoicesStartdate7" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate7" name="invoicesEnddate7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision7" name="commision7"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 5}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth6" name="invoicesMonth6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount6" name="monthAmount6" onkeyup="checkInt6();"  ondrop="checkInt6();" onchange="checkInt6();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate6" name="invoicesStartdate6" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate6" name="invoicesEnddate6" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision6" name="commision6"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth7" name="invoicesMonth7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount7" name="monthAmount7" onkeyup="checkInt7();"  ondrop="checkInt7();" onchange="checkInt7();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate7" name="invoicesStartdate7" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate7" name="invoicesEnddate7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision7" name="commision7"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 6}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth7" name="invoicesMonth7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount7" name="monthAmount7" onkeyup="checkInt7();"  ondrop="checkInt7();" onchange="checkInt7();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate7" name="invoicesStartdate7" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate7" name="invoicesEnddate7" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision7" name="commision7"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 7}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 8}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 9}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 10}">
									<tr>
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</c:if>
									<c:if test="${list eq 11}">
									<tr >
										<td>
											<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
										</td>
										<td>
											<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
										</td>
										<td>
											<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</c:if>
							</c:if>
							<c:if test="${empty Id}">
								<h4 style="text-align:center">应收金额合计:<span id="allMoneya" ></span></h4>
								<input type="hidden" id="extStr1" name="extStr1" />
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth1" name="invoicesMonth1" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount1" name="monthAmount1" onkeyup="checkInt1();" onpaste="checkInt1();" oncut="checkInt1();" ondrop="checkInt1();" onchange="checkInt1();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate1" name="invoicesStartdate1" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate1" name="invoicesEnddate1" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision1" name="commision1"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth2" name="invoicesMonth2" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount2" name="monthAmount2" onkeyup="checkInt2();" onpaste="checkInt2();" oncut="checkInt2();" ondrop="checkInt2();" onchange="checkInt2();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate2" name="invoicesStartdate2" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate2" name="invoicesEnddate2" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision2" name="commision2"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth3" name="invoicesMonth3" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount3" name="monthAmount3" onkeyup="checkInt3();"  ondrop="checkInt3();" onchange="checkInt3();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate3" name="invoicesStartdate3" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate3" name="invoicesEnddate3" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision3" name="commision3"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth4" name="invoicesMonth4" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount4" name="monthAmount4" onkeyup="checkInt4();"  ondrop="checkInt4();" onchange="checkInt4();" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate4" name="invoicesStartdate4" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate4" name="invoicesEnddate4" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision4" name="commision4"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth5" name="invoicesMonth5" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount5" name="monthAmount5" onkeyup="checkInt5();"  ondrop="checkInt5();" onchange="checkInt5();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate5" name="invoicesStartdate5" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate5" name="invoicesEnddate5" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision5" name="commision5"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth6" name="invoicesMonth6" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount6" name="monthAmount6" onkeyup="checkInt6();"  ondrop="checkInt6();" onchange="checkInt6();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate6" name="invoicesStartdate6" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate6" name="invoicesEnddate6" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision6" name="commision6"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth7" name="invoicesMonth7" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount7" name="monthAmount7" onkeyup="checkInt7();"  ondrop="checkInt7();" onchange="checkInt7();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate7" name="invoicesStartdate7" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate7" name="invoicesEnddate7" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision7" name="commision7"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth8" name="invoicesMonth8" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount8" name="monthAmount8" onkeyup="checkInt8();"  ondrop="checkInt8();" onchange="checkInt8();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate8" name="invoicesStartdate8" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate8" name="invoicesEnddate8" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision8" name="commision8"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth9" name="invoicesMonth9" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount9" name="monthAmount9" onkeyup="checkInt9();"  ondrop="checkInt9();" onchange="checkInt9();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate9" name="invoicesStartdate9" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate9" name="invoicesEnddate9" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision9" name="commision9"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth10" name="invoicesMonth10" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount10" name="monthAmount10" onkeyup="checkInt10();"  ondrop="checkInt10();" onchange="checkInt10();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate10" name="invoicesStartdate10" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate10" name="invoicesEnddate10" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision10" name="commision10"/>
									</td>
								</tr>
								<tr>
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth11" name="invoicesMonth11" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount11" name="monthAmount11" onkeyup="checkInt11();"  ondrop="checkInt11();" onchange="checkInt11();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate11" name="invoicesStartdate11" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate11" name="invoicesEnddate11" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision11" name="commision11"/>
									</td>
								</tr>
								<tr >
									<td>
										<input  type="text"  style="width:100%;"id="invoicesMonth12" name="invoicesMonth12" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="monthAmount12" name="monthAmount12" onkeyup="checkInt();"  ondrop="checkInt();" onchange="checkInt();"/>
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesStartdate12" name="invoicesStartdate12" />
									</td>
									<td>
										<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  style="width:100%;"id="invoicesEnddate12" name="invoicesEnddate12" />
									</td>
									<td>
										<input type="text"  style="width:100%;"id="commision12" name="commision12"/>
									</td>
								</tr>
							</c:if>
					</tbody>
				</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaFinanceMainModule.saveOrModify(true)">提  交</a>
						</section>
					</section>
				</form>
			</section>
		</section>
<!--公司名称提取 开始  -->
	<div class="modal  panel" id="new-agency" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">公司名称</h4>
                </div>
                <form class="table-wrap  m-20-auto" id="customerForm">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td  >
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName1" name="companyName1" />
					            <button class="btn dark" type="button" onclick="toaFinanceMainModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="toaFinanceMainModule.queryReset()">重 置</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
                <div class="modal-body">
                    <section class="panel-tab-con">
                        <div class="tab-content">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                    <table class="table table-striped frist-td-tc" id="issuedTaskTable">
                                        <thead>
                                            <th class="w46">请选择</th>
                                            <th>公司名称</th>
                                            <th>销售部门</th>
                                            <th>客户经理</th>
                                            <th>维护经理</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                            </div>
                    </section>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn" type="button" data-dismiss="modal" >关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 选择公司结束 -->
    <!--公司名称提取 开始  -->
	<div class="modal  panel" id="new-agency1" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">合同信息</h4>
                </div>
               <!--  <form class="table-wrap  m-20-auto" id="customerForm1">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td>
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName2" name="companyName2" />
					            <button class="btn dark" type="button" onclick="toaFinanceMainModule.getWorkTask1()">查 询</button>
					            <button class="btn" type="button" onclick="toaFinanceMainModule.queryReset1()">重 置</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form> -->
                <div class="modal-body">
                    <section class="panel-tab-con">
                        <div class="tab-content">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                    <table class="table table-striped frist-td-tc" id="issuedTaskTable1">
                                        <thead>
                                            <th class="w46">请选择</th>
                                            <th>公司名称</th>
                                            <th>合同编号</th>
                                            <th>合同金额</th>
                                            <th>合同类别</th>
                                            <th>衍生合同编码</th>
                                            <th>合同起始日期</th>
                                            <th>合同终止日期</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                            </div>
                    </section>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn" type="button" data-dismiss="modal" >关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 选择公司结束 -->
<c:if test="${empty Id}">
<script >
$(document).ready(function(){
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth()+1;
	var day = myDate.getDate();
	var orderDate=null;
	var firstMonth=null;
	if(month<10){
		firstMonth = year+"-0"+month;
		if(day<10){
			firstMonth = year+"-0"+month;
			orderDate = year+"-0"+month+"-0"+day;
		}else{
			firstMonth = year+"-0"+month;
			orderDate = year+"-0"+month+"-"+day;
		}
	}else{
		if(day<10){
			firstMonth = year+"-"+month;
			orderDate = year+"-"+month+"-0"+day;
		}else{
			firstMonth = year+"-"+month;
			orderDate = year+"-"+month+"-"+day;
		}
	}
	$("#months").val(firstMonth);
	$("#orderDate").val(orderDate);
});
</script>
</c:if>
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaFinanceMainModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/finance/toaFinanceMain/toaFinanceMainForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinanceMain/toaFinanceMain.validate.js" type="text/javascript"></script>