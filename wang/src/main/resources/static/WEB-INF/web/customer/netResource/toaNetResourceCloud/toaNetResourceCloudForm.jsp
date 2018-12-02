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
		<form class="table-wrap  m-20-auto" id="toaProductCloudForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">云资源信息表</h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="customersId" name="customersId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td  class=" b-green-dark b-tc">公司名称</td>							<td>
								<div style="width:100%;"id="customersName"></div>
																							</td>							<td  class=" b-green-dark b-tc">支持人员</td>							<td>
								<div style="width:100%;"id="supporter"></div>															</td>
							<td  class=" b-green-dark b-tc">资源模式</td>
							<td>
								<div style="width:100%;"id="resourceType"></div>
								
							</td>						</tr>
						<tr>
							<td  class=" b-green-dark b-tc">计费时间</td>
							<td>
								<div style="width:100%;"id="chargeTime"></div>
								
							</td>
							<td  class=" b-green-dark b-tc">计费开始时间</td>
							<td>
								<div style="width:100%;"id="startDate"></div>
								
							</td>
							<td  class=" b-green-dark b-tc">计费终止时间</td>
							<td>
								<div style="width:100%;"id="startEnd"></div>
								
							</td>
						</tr>						<tr>
							<td  class=" b-green-dark b-tc">金额(元)</td>
							<td>
								<div style="width:100%;"id="amount"></div>
								
							</td>							<td  class=" b-green-dark b-tc">CPU</td>							<td>
								<div style="width:100%;"id="cpu"></div>															</td>							<td  class=" b-green-dark b-tc">内存</td>							<td>
								<div style="width:100%;"id="ram"></div>															</td>						</tr>						<tr>							<td  class=" b-green-dark b-tc">容量盘</td>							<td>
								<div style="width:100%;"id="stick"></div>															</td>							<td  class=" b-green-dark b-tc">性能盘</td>							<td>
								<div style="width:100%;"id="performance"></div>															</td>							<td  class=" b-green-dark b-tc">云主机快照</td>							<td>
								<div style="width:100%;"id="cloudPhoto"></div>															</td>						</tr>						<tr>							<td  class=" b-green-dark b-tc">云硬盘快照</td>							<td>
								<div style="width:100%;"id="cloudDive"></div>															</td>							<td  class=" b-green-dark b-tc">公网IP</td>							<td>
								<div style="width:100%;"id="publicIp"></div>															</td>							<td  class=" b-green-dark b-tc">带宽</td>							<td>
								<div style="width:100%;"id="bandwidth"></div>															</td>						</tr>						<tr>							<td  class=" b-green-dark b-tc">路由器</td>							<td>
								<div style="width:100%;"id="router"></div>															</td>							<td  class=" b-green-dark b-tc">负载均衡</td>							<td>
								<div style="width:100%;"id="loadBalancer"></div>															</td>							<td  class=" b-green-dark b-tc">备注</td>							<td >
								<div style="width:100%;"id="remark"></div>															</td>						</tr>					</tbody>
				</table>
			</form>
		</section>
	</section>
	
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaProductCloudModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/customer/netResource/toaNetResourceCloud/toaNetResourceCloudForm.js" type="text/javascript"></script>