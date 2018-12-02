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
		<form class="table-wrap  m-20-auto" id="toaFinancePercentageForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">绩效提成表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="mainId" name="mainId" />
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">编码</td>							<td>								<input type="text"  style="width:100%;"id="perNumber" name="perNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">年份</td>							<td>								<input type="text"  style="width:100%;"id="perYear" name="perYear" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">月份</td>							<td>								<input type="text"  style="width:100%;"id="perMonth" name="perMonth" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">类型</td>							<td>								<input type="text"  style="width:100%;"id="perType" name="perType" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">业务拓展部</td>							<td>								<input type="text"  style="width:100%;"id="department" name="department" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">拓展专员</td>							<td>								<input type="text"  style="width:100%;"id="sale" name="sale" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户关系维护组</td>							<td>								<input type="text"  style="width:100%;"id="tradeDepartment" name="tradeDepartment" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">维护专员</td>							<td>								<input type="text"  style="width:100%;"id="perEnSale" name="perEnSale" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">到账总金额</td>							<td>								<input type="text"  style="width:100%;"id="perAccount" name="perAccount" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">代理费</td>							<td>								<input type="text"  style="width:100%;"id="perAgentAccount" name="perAgentAccount" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">净收入（到账总额-代理费）</td>							<td>								<input type="text"  style="width:100%;"id="perPureAccount" name="perPureAccount" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">存量到账收入</td>							<td>								<input type="text"  style="width:100%;"id="billAccount" name="billAccount" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">新增到账收入</td>							<td>								<input type="text"  style="width:100%;"id="addBill" name="addBill" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">扩容到账收入</td>							<td>								<input type="text"  style="width:100%;"id="kuorong" name="kuorong" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">首次入网时间</td>							<td>								<input type="text"  style="width:100%;"id="perStart" name="perStart" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">回款日期</td>							<td>								<input type="text"  style="width:100%;"id="billDate" name="billDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">年限</td>							<td>								<input type="text"  style="width:100%;"id="perYers" name="perYers" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客维组计提比例</td>							<td>								<select style="width:100%;"id="keweiRatio" name="keweiRatio">
									<option value="0">0.00</option>
									<option value="0.01">0.01</option>
								</select>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">拓展组计提比例</td>							<td>								<select style="width:100%;"id="tuozhanRatio" name="tuozhanRatio">
									<option value="0">0.00</option>
									<option value="0.01">0.02</option>
									<option value="0.07">0.07</option>
								</select>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客维组计提金额</td>							<td>								<input type="text"  style="width:100%;"id="keweiMoney" name="keweiMoney" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">拓展组计提金额</td>							<td>								<input type="text"  style="width:100%;"id="tuozhanMoney" name="tuozhanMoney" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">发票号</td>							<td>								<input type="text"  style="width:100%;"id="billNumber" name="billNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<input type="text"  style="width:100%;"id="remark" name="remark" />							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaFinancePercentageModule.saveOrModify(true)">提  交</a>
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
		toaFinancePercentageModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/finance/toaFinancePercentage/toaFinancePercentageForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinancePercentage/toaFinancePercentage.validate.js" type="text/javascript"></script>