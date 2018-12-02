<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaFinanceAgencyForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">代理费 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">代理编码</td>							<td>								<input type="text"  style="width:100%;"id="orderNo" name="orderNo" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户id</td>							<td>								<input type="text"  style="width:100%;"id="customerId" name="customerId" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="customerName" name="customerName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">业务拓展部</td>							<td>								<input type="text"  style="width:100%;"id="department" name="department" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">拓展专员</td>							<td>								<input type="text"  style="width:100%;"id="sale" name="sale" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">维护专员</td>							<td>								<input type="text"  style="width:100%;"id="maintenanSale" name="maintenanSale" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">计费周期起</td>							<td>								<input type="text"  style="width:100%;"id="cycleStart" name="cycleStart" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">计费周期止</td>							<td>								<input type="text"  style="width:100%;"id="cycleEnd" name="cycleEnd" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">合同编号</td>							<td>								<input type="text"  style="width:100%;"id="cardNo" name="cardNo" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同金额</td>							<td>								<input type="text"  style="width:100%;"id="cardAmount" name="cardAmount" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">合同周期起时间</td>							<td>								<input type="text"  style="width:100%;"id="incontractStart" name="incontractStart" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">合同周期止时间</td>							<td>								<input type="text"  style="width:100%;"id="incontractEnd" name="incontractEnd" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">机房</td>							<td>								<input type="text"  style="width:100%;"id="roomName" name="roomName" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">资源</td>							<td>								<input type="text"  style="width:100%;"id="resources" name="resources" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">支付方式</td>							<td>								<input type="text"  style="width:100%;"id="payType" name="payType" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">税前支付金额</td>							<td>								<input type="text"  style="width:100%;"id="beforeTax" name="beforeTax" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">税点</td>							<td>								<input type="text"  style="width:100%;"id="beforeLittle" name="beforeLittle" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">收款方</td>							<td>								<input type="text"  style="width:100%;"id="paee" name="paee" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">支付状态</td>							<td>								<input type="text"  style="width:100%;"id="payState" name="payState" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">支付时间</td>							<td>								<input type="text"  style="width:100%;"id="payDate" name="payDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">未支付金额</td>							<td>								<input type="text"  style="width:100%;"id="notPay" name="notPay" />							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaFinanceAgencyModule.saveOrModify(true)">提  交</a>
						</section>
					</section>
				</form>
			</section>
		</section>
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaFinanceAgencyModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/finance/toaFinanceAgency/toaFinanceAgencyForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinanceAgency/toaFinanceAgency.validate.js" type="text/javascript"></script>