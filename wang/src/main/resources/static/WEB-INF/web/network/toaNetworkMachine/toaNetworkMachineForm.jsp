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
		<form class="table-wrap  m-20-auto" id="toaNetworkMachineForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">网络设备统计表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">设备品牌</td>							<td>								<input type="text"  style="width:100%;"id="machine" name="machine" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">设备名称</td>							<td>								<input type="text"  style="width:100%;"id="machineName" name="machineName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">设备类型</td>							<td>								<input type="text"  style="width:100%;"id="machineType" name="machineType" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">登录IP</td>							<td>								<input type="text"  style="width:100%;"id="loginIp" name="loginIp" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">设备型号</td>							<td>								<input type="text"  style="width:100%;"id="machineNumber" name="machineNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">设备软件版本</td>							<td>								<input type="text"  style="width:100%;"id="describes" name="describes" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">序列号SN</td>							<td>								<input type="text"  style="width:100%;"id="serialNumber" name="serialNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">所在位置</td>							<td>								<input type="text"  style="width:100%;"id="address" name="address" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td colspan="3">								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkMachineModule.saveOrModify(true)">提  交</a>
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
		toaNetworkMachineModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkMachine/toaNetworkMachineForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkMachine/toaNetworkMachine.validate.js" type="text/javascript"></script>