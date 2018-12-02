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
		<form class="table-wrap  m-20-auto" id="toaProductCdnForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">CDN资源表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden"   id="customersId" name="customersId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td >公司名称</td>							<td>
								<div style="width:100%;" id="customersName"></div>
															</td>							<td >分类</td>
							<td>
								<div style="width:100%;"id="extStr1"></div>
								
							</td>							<td >支持人员</td>
							<td>
								<div style="width:100%;"id="supporter"></div>
								
							</td>
						</tr>						<tr>							<td >用户名</td>							<td>
								<div style="width:100%;"id="userName"></div>															</td>
							<td >计费方式</td>							<td>
								<div style="width:100%;"id="chargeMode"></div>															</td>
							<td >计费时间</td>
							<td>
								<div style="width:100%;"id="chargeTime"></div>
								
							</td>						</tr>
						<tr>
							<td >计费开始日期</td>
							<td>
								<div style="width:100%;"id="startDate"></div>
								
							</td>
							<td >计费终止日期</td>
							<td>
								<div style="width:100%;"id="startEnd"></div>
								
							</td>
							<td >单价</td>
							<td>
								<div style="width:100%;"id="price"></div>
								
							</td>
						</tr>						<tr>							<td >保底数值</td>							<td>
								<div style="width:100%;"id="flooredNumber"></div>															</td>							<td >计费数值</td>							<td>
								<div style="width:100%;"id="chargeNumber"></div>															</td>							<td >金额(元)</td>							<td>
								<div style="width:100%;"id="amount"></div>															</td>						</tr>						<tr>
							<td >加速域名<br>回源IP/回源域名</td>
							<td>
								<div style="width:100%;"id="stick"></div>
								
							</td>							<td >备注</td>							<td colspan="3">
								<div style="width:100%;"id="remark"></div>															</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							
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
		toaProductCdnModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com//customer/netResource/toaNetResourceCdn/toaNetResourceCdnForm.js" type="text/javascript"></script>