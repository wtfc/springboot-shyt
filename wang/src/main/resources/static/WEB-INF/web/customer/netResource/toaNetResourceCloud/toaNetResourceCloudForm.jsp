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
						<tr>
								<div style="width:100%;"id="customersName"></div>
								
								<div style="width:100%;"id="supporter"></div>
							<td  class=" b-green-dark b-tc">资源模式</td>
							<td>
								<div style="width:100%;"id="resourceType"></div>
								
							</td>
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
						</tr>
							<td  class=" b-green-dark b-tc">金额(元)</td>
							<td>
								<div style="width:100%;"id="amount"></div>
								
							</td>
								<div style="width:100%;"id="cpu"></div>
								<div style="width:100%;"id="ram"></div>
								<div style="width:100%;"id="stick"></div>
								<div style="width:100%;"id="performance"></div>
								<div style="width:100%;"id="cloudPhoto"></div>
								<div style="width:100%;"id="cloudDive"></div>
								<div style="width:100%;"id="publicIp"></div>
								<div style="width:100%;"id="bandwidth"></div>
								<div style="width:100%;"id="router"></div>
								<div style="width:100%;"id="loadBalancer"></div>
								<div style="width:100%;"id="remark"></div>
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