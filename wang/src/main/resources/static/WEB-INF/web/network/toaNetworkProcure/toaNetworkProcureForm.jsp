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
		<form class="table-wrap  m-20-auto" id="toaNetworkProcureForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">网络设备采购表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">设备型号</td>							<td>								<input type="text"  style="width:100%;"id="machineNumber" name="machineNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">描述</td>							<td>								<textarea  style="width:100%;"id="describes" name="describes" ></textarea>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">序列号</td>							<td>								<input type="text"  style="width:100%;"id="serialNumber" name="serialNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">采购申请时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="applicationDate" name="applicationDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">采购到货时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="arrivalDate" name="arrivalDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">是否安装</td>							<td>
								<select style="width:100%;"id="installs" name="installs">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">设备位置</td>							<td>								<textarea  style="width:100%;"id="address" name="address" ></textarea>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkProcureModule.saveOrModify(true)">提  交</a>
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
		toaNetworkProcureModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkProcure/toaNetworkProcureForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkProcure/toaNetworkProcure.validate.js" type="text/javascript"></script>