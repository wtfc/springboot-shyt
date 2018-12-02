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
		<form class="table-wrap  m-20-auto" id="toaProductCloudtestForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">测试机表 </h3>
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
							</td>							<td style="width:10%;">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>							<td style="width:10%;">客户类别</td>
							<td>
								<select style="width:100%;" id="cooperateType" name="cooperateType">
									<option value="新增">新增</option>
									<option value="在网">在网</option>
								</select>
							</td>
							<td style="width:10%;">销售</td>
							<td>
								<input type="text"  style="width:100%;"id="salePeople" name="salePeople" />
							</td>
						</tr>						<tr>
							<td style="width:10%;">测试机房</td>
							<td>
								<input type="text"  style="width:100%;"id="extStr2" name="extStr2" />
							</td>
							<td style="width:10%;">测试环境</td>
							<td>
								<input type="text"  style="width:100%;"id="extStr3" name="extStr3" />
							</td>
							<td style="width:10%;">用户名</td>
							<td>
								<input type="text"  style="width:100%;"id="extStr4" name="extStr4" />
							</td>
							<td style="width:10%;">密码</td>
							<td>
								<input type="text"  style="width:100%;"id="extStr5" name="extStr5" />
							</td>						</tr>						<tr>
							<td style="width:10%;">测试系统</td>
							<td>
								<input type="text"  style="width:100%;"id="systemMachine" name="systemMachine" />
							</td>							<td style="width:10%;">测试开始时间</td>
							<td>
								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="startDate" name="startDate" />
							</td>
							<td style="width:10%;">测试结束时间</td>
							<td>
								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="endDate" name="endDate" />
							</td>
							<td style="width:10%;">带宽（M）</td>
							<td>
								<input type="text"  style="width:100%;"id="bandwidth" name="bandwidth" />
							</td>
						</tr>
						<tr>	
							<td style="width:10%;">测试IP</td>
							<td>
								<textarea  style="width:100%;"id="publicIp" name="publicIp" ></textarea>
							</td>							<td style="width:10%;">CPU（核）</td>							<td>								<input type="text"  style="width:100%;"id="cpu" name="cpu" />							</td>							<td style="width:10%;">内存（G）</td>							<td>								<input type="text"  style="width:100%;"id="ram" name="ram" />							</td>							<td style="width:10%;">性能盘（G）</td>							<td>								<input type="text"  style="width:100%;"id="performance" name="performance" />							</td>
						</tr>
						<tr>							<td style="width:10%;">容量盘（G）</td>							<td>								<input type="text"  style="width:100%;"id="stick" name="stick" />							</td>							<td style="width:10%;">邮箱</td>							<td>								<input type="text"  style="width:100%;"id="email" name="email" />							</td>							<td style="width:10%;">延长时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="chargeTime" name="chargeTime" />							</td>							<td style="width:10%;">收回时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="finishDate" name="finishDate" />							</td>						</tr>						<tr>							<td style="width:10%;">其他</td>							<td colspan="7">								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<shiro:hasPermission name="saveProductCloud"><a class="btn dark"  onclick="toaProductCloudtestModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
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
		toaProductCloudtestModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/product/toaProductCloudtest/toaProductCloudtestForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/product/toaProductCloudtest/toaProductCloudtest.validate.js" type="text/javascript"></script>