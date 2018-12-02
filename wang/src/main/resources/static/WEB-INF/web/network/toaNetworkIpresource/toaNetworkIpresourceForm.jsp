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
		<form class="table-wrap  m-20-auto" id="toaNetworkIpresourceForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">IP总资源表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">IP地址</td>							<td>								<input type="text"  style="width:100%;"id="ipOne" name="ipOne" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">路由条目数</td>							<td>								<input type="text"  style="width:100%;"id="routeNumber" name="routeNumber" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">是否广播</td>							<td>								<select style="width:100%;"id="airingStatus" name="airingStatus">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">广播运营商名称</td>							<td>								<input type="text"  style="width:100%;"id="airingName" name="airingName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<input type="text"  style="width:100%;"id="remark" name="remark" />							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkIpresourceModule.saveOrModify(true)">提  交</a>
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
		toaNetworkIpresourceModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkIpresource/toaNetworkIpresourceForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkIpresource/toaNetworkIpresource.validate.js" type="text/javascript"></script>