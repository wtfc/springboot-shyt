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
		<form class="table-wrap  m-20-auto" id="toaContractOutcontractForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">付款方合同 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">甲方公司名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">乙方公司名称</td>							<td>								<input type="text"  style="width:100%;"id="customer" name="customer" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同编码</td>							<td>								<input type="text"  style="width:100%;"id="contractNumber" name="contractNumber" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">发起人</td>							<td>								<input type="text"  style="width:100%;"id="leard" name="leard" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">发起日期</td>							<td>								<input type="text"  style="width:100%;"id="leardDate" name="leardDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同类型</td>							<td>								<input type="text"  style="width:100%;"id="agreement" name="agreement" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同类别</td>							<td>								<input type="text"  style="width:100%;"id="contractStatus" name="contractStatus" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同金额（元）</td>							<td>								<input type="text"  style="width:100%;"id="contractMoney" name="contractMoney" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">盖章</td>							<td>								<input type="text"  style="width:100%;"id="seal" name="seal" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">归档时间</td>							<td>								<input type="text"  style="width:100%;"id="place" name="place" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同起始日期</td>							<td>								<input type="text"  style="width:100%;"id="startDate" name="startDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同终止日期</td>							<td>								<input type="text"  style="width:100%;"id="endDate" name="endDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同资源</td>							<td>								<input type="text"  style="width:100%;"id="resource" name="resource" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">合同扫描件</td>							<td>								<input type="text"  style="width:100%;"id="contractFile" name="contractFile" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<input type="text"  style="width:100%;"id="remark" name="remark" />							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaContractOutcontractModule.saveOrModify(true)">提  交</a>
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
		toaContractOutcontractModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/contract/toaContractOutcontract/toaContractOutcontractForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/contract/toaContractOutcontract/toaContractOutcontract.validate.js" type="text/javascript"></script>