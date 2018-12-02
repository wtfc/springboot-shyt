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
		<form class="table-wrap  m-20-auto" id="toaFinanceBillForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">账单信息 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">账单日期</td>							<td>								<input type="text"  style="width:100%;"id="billDate" name="billDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户id</td>							<td>								<input type="text"  style="width:100%;"id="companyId" name="companyId" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">纳税人识号</td>							<td>								<input type="text"  style="width:100%;"id="custmersTaxid" name="custmersTaxid" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">地址</td>							<td>								<input type="text"  style="width:100%;"id="address" name="address" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">电话</td>							<td>								<input type="text"  style="width:100%;"id="phone" name="phone" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">银行</td>							<td>								<input type="text"  style="width:100%;"id="bankName" name="bankName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">银行帐号</td>							<td>								<input type="text"  style="width:100%;"id="bankNo" name="bankNo" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">发票类型</td>							<td>								<input type="text"  style="width:100%;"id="ticket" name="ticket" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">审批状态</td>							<td>								<input type="text"  style="width:100%;"id="state" name="state" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合计金额</td>							<td>								<input type="text"  style="width:100%;"id="billMoney" name="billMoney" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合计金额大写</td>							<td>								<input type="text"  style="width:100%;"id="billMoneym" name="billMoneym" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">应支付日期</td>							<td>								<input type="text"  style="width:100%;"id="payDate" name="payDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">开户银行</td>							<td>								<input type="text"  style="width:100%;"id="obankname" name="obankname" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">帐 号</td>							<td>								<input type="text"  style="width:100%;"id="obankno" name="obankno" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">公司名称</td>							<td>								<input type="text"  style="width:100%;"id="ocompany" name="ocompany" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户经理</td>							<td>								<input type="text"  style="width:100%;"id="sale" name="sale" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">电话</td>							<td>								<input type="text"  style="width:100%;"id="salePhone" name="salePhone" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">所对应收入id</td>							<td>								<input type="text"  style="width:100%;"id="remarkId" name="remarkId" />							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaFinanceBillModule.saveOrModify(true)">提  交</a>
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
		toaFinanceBillModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/finance/toaFinanceBill/toaFinanceBillForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinanceBill/toaFinanceBill.validate.js" type="text/javascript"></script>