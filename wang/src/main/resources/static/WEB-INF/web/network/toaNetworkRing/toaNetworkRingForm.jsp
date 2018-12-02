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
						<tr>
								<select style="width:100%;"id="ringType" name="ringType">
									<option value="">请选择</option>
									<option value="二层环网">二层环网</option>
									<option value="三层环网">三层环网</option>
								</select>
								<dic:select cssStyle="width:100%;" id="oppositeMachine" name="oppositeMachine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
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