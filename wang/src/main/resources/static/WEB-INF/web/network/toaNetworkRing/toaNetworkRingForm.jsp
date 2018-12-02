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
		<form class="table-wrap  m-20-auto" id="toaNetworkRingForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">骨干网链路带宽统计表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">环网类型</td>							<td>
								<select style="width:100%;"id="ringType" name="ringType">
									<option value="">请选择</option>
									<option value="二层环网">二层环网</option>
									<option value="三层环网">三层环网</option>
								</select>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">机房名称</td>							<td>								<dic:select cssStyle="width:100%;" id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">对端机房名称</td>							<td>
								<dic:select cssStyle="width:100%;" id="oppositeMachine" name="oppositeMachine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">链路带宽（G）</td>							<td>								<input type="text"  style="width:100%;"id="lineGbps" name="lineGbps" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">聚合链路数量</td>							<td>								<input type="text"  style="width:100%;"id="lineNumber" name="lineNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">用户带宽总和</td>							<td>								<input type="text"  style="width:100%;"id="gbps" name="gbps" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">链路带宽复用比</td>							<td>								<input type="text" readonly style="width:100%;"id="withFuyongbi" name="withFuyongbi" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkRingModule.saveOrModify(true)">提  交</a>
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
		toaNetworkRingModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkRing/toaNetworkRingForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkRing/toaNetworkRing.validate.js" type="text/javascript"></script>