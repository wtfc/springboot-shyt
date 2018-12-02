<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaProductFeedbackForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">测试机反馈 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td style="width:10%;">测试类型</td>
							<td>
								<select style="width:100%;" id="extStr1" name="extStr1">
									<option value="云主机测试">云主机测试</option>
									<option value="物理机测试">物理机测试</option>
								</select>
							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">销售</td>							<td>								<input type="text"  style="width:100%;"id="salePeople" name="salePeople" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">测试平台</td>							<td>								<input type="text"  style="width:100%;"id="cooperateType" name="cooperateType" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">收回时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="finishDate" name="finishDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户评价</td>							<td>								<textarea style="width:100%;"id="appraisal" name="appraisal" ></textarea>							</td>						</tr>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td colspan="3">								<textarea style="width:100%;"id="remark" name="remark" ></textarea>							</td>
						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaProductFeedbackModule.saveOrModify(true)">提  交</a>
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
		toaProductFeedbackModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/product/toaProductFeedback/toaProductFeedbackForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/product/toaProductFeedback/toaProductFeedback.validate.js" type="text/javascript"></script>