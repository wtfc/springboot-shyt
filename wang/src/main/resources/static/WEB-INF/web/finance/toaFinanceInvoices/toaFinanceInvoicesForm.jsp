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
		<form class="table-wrap  m-20-auto" id="toaFinanceInvoicesForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">当月收入表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="mainId" name="mainId">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td >客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>							<td >应收月份</td>							<td>								<input type="text"  style="width:100%;"id="invoicesMonth" name="invoicesMonth" />							</td>							<td >应收金额</td>							<td>								<input type="text"  style="width:100%;"id="monthAmount" name="monthAmount" />							</td>						</tr>						<tr>							<td >应收起始日期</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="invoicesStartdate" name="invoicesStartdate" />							</td>							<td >应收终止日期</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="invoicesEnddate" name="invoicesEnddate" />							</td>							<td >是否开票</td>							<td>
								<select id="invoicesState" name="invoicesState" style="width:100%;">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>							</td>						</tr>						<tr>							<td >开票日期</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="vdateDate" name="vdateDate" />							</td>							<td >发票号码</td>							<td>								<input type="text"  style="width:100%;"id="invoicesNo" name="invoicesNo" />							</td>							<td >发票金额</td>							<td>								<input type="text" onkeyup="checkInt();" onpaste="checkInt();" oncut="checkInt();" ondrop="checkInt();" onchange="checkInt();" style="width:100%;"id="invoicesAccount" name="invoicesAccount" />							</td>						</tr>						<tr>							<td >未开票金额</td>							<td>								<input type="text"  style="width:100%;"id="noinvoicesAccount" name="noinvoicesAccount" />							</td>							<td >是否回款</td>							<td>
								<select style="width:100%;"id="receivedState" name="receivedState" >
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>							</td>							<td >回款日期</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="receivedDate" name="receivedDate" />							</td>						</tr>						<tr>							<td >回款金额</td>							<td>								<input type="text" onkeyup="checkP();" onpaste="checkP();" oncut="checkP();" ondrop="checkP();" onchange="checkP();" style="width:100%;"id="receivedAccount" name="receivedAccount" />							</td>							<td >未回款金额</td>							<td>								<input type="text"  style="width:100%;"id="arrearage" name="arrearage" />							</td>							<td >代理费</td>							<td>								<input type="text"  style="width:100%;"id="commision" name="commision" />							</td>						</tr>
						<tr>
							<td>备注</td>
							<td colspan="5">
								<textarea id="extStr1" name="extStr1"></textarea>
							</td>
						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaFinanceInvoicesModule.saveOrModify(true)">提  交</a>
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
		toaFinanceInvoicesModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/finance/toaFinanceInvoices/financeInvoicesForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinanceInvoices/toaFinanceInvoices.validate.js" type="text/javascript"></script>