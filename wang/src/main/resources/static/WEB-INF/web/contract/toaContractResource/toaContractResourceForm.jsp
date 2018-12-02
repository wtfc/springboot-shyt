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
		<form class="table-wrap  m-20-auto" id="toaContractResourceForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">合同资源表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户id</td>							<td>								<input type="text"  style="width:100%;"id="customerId" name="customerId" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">保底带宽</td>							<td>								<input type="text"  style="width:100%;"id="minBandwidth" name="minBandwidth" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="minBandwidthPrice" name="minBandwidthPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">端口带宽</td>							<td>								<input type="text"  style="width:100%;"id="portBandwidth" name="portBandwidth" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="portBandwidthPrice" name="portBandwidthPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">超流量带宽</td>							<td>								<input type="text"  style="width:100%;"id="overflowBandwidth" name="overflowBandwidth" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="overflowBandwidthPrice" name="overflowBandwidthPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">机柜</td>							<td>								<input type="text"  style="width:100%;"id="cabinetNum" name="cabinetNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="cabinetPrice" name="cabinetPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">服务器</td>							<td>								<input type="text"  style="width:100%;"id="serviceNum" name="serviceNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="servicePrice" name="servicePrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">IP</td>							<td>								<input type="text"  style="width:100%;"id="ipNum" name="ipNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="ipPrice" name="ipPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">交换机</td>							<td>								<input type="text"  style="width:100%;"id="switchNum" name="switchNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="switchPrice" name="switchPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">链路</td>							<td>								<input type="text"  style="width:100%;"id="odfNum" name="odfNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="odfPrice" name="odfPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">端口</td>							<td>								<input type="text"  style="width:100%;"id="portNum" name="portNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="portPrice" name="portPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">内存</td>							<td>								<input type="text"  style="width:100%;"id="memoryNum" name="memoryNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="memoryPrice" name="memoryPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">CPU</td>							<td>								<input type="text"  style="width:100%;"id="cpuNum" name="cpuNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="cpuPrice" name="cpuPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">硬盘</td>							<td>								<input type="text"  style="width:100%;"id="diskNum" name="diskNum" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="diskPrice" name="diskPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">路由器</td>							<td>								<input type="text"  style="width:100%;"id="router" name="router" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="routerPrice" name="routerPrice" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">其他</td>							<td>								<input type="text"  style="width:100%;"id="otherss" name="otherss" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">单价</td>							<td>								<input type="text"  style="width:100%;"id="otherssPrice" name="otherssPrice" />							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaContractResourceModule.saveOrModify(true)">提  交</a>
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
		toaContractResourceModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/contract/toaContractResource/toaContractResourceForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/contract/toaContractResource/toaContractResource.validate.js" type="text/javascript"></script>