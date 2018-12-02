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
		<form class="table-wrap  m-20-auto" id="toaNetworkTenanciesForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">退租客户记录表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">机房</td>							<td>								<dic:select cssStyle="width:100%;" id="room" name="room" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">退租时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="tenanciesDate" name="tenanciesDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">带宽</td>							<td>								<input type="text"  style="width:100%;"id="bandwidth" name="bandwidth" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">IP地址</td>							<td>								<input type="text"  style="width:100%;"id="ipAddress" name="ipAddress" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">设备</td>							<td>								<input type="text"  style="width:100%;"id="machine" name="machine" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">端口</td>							<td>								<input type="text"  style="width:100%;"id="port" name="port" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<textarea style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkTenanciesModule.saveOrModify(true)">提  交</a>
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
		toaNetworkTenanciesModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkTenancies/toaNetworkTenanciesForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkTenancies/toaNetworkTenancies.validate.js" type="text/javascript"></script>