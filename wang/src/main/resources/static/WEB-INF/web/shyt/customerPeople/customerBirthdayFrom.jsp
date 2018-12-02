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
		<form class="table-wrap  m-20-auto" id="customerPeopleForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">客户生日祝福 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input  type="hidden"id="customerId" name="customerId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td class=" b-green-dark b-tc">
								<span class="required">*</span>公司名称</td>
							<td>
                                     <a   type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>         
							</td>
							<td >
								<span class="required">*</span>联系人</td>
							<td >
								<input type="text"  style="width:100%;"id="name" name="name" />
							</td>
							<td  class=" b-green-dark b-tc">职务</td>
							<td >
								<input type="text" name = "job" id="job" style="width:100%;"/>
							</td>
						</tr>
						<tr>
							<td ><span class="required">*</span>联系方式</td>
							<td >
								<input type="text"  name="tel" id="tel" style="width:100%;"/>
							</td>
							<td>身份证号</td>
							<td>
								<input id="idCard" type="text" style="width:100%;" name = "idCard"/>
							</td>
							<td>
								是否祝福
							</td>
							<td>
								<select id="extStr3" name="extStr3" style="width:100%;">
									<option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								祝福方式
							</td>
							<td>
								<select id="extStr4" name="extStr4" style="width:100%;">
									<option value="">请选择</option>
									<option value="电话祝福">电话祝福</option>
									<option value="邮件祝福">邮件祝福</option>
									<option value="见面祝福">见面祝福</option>
								</select>
							</td>
							<td >祝福时间</td>
							<td >
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  type="text" name = "extDate1" id="extDate1" style="width:100%;"/>
							</td>
							<td >产生费用(元)</td>
							<td>
								<input type="text"   id="extNum1" name="extNum1" style="width:100%;"/>
							</td>
						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="customerPeopleModule.saveOrModify(true)">提  交</a>
						</section>
					</section>
				</form>
			</section>
		</section>
<c:if test="${!empty oldId}">
<script >
$(document).ready(function(){
	var ids=(${oldId});
	if(ids!=null&&ids!=""){
		customerPeopleModule.get(ids);
	}
});
function clearTabale(){
	clearTable();
}
</script>
</c:if>		

<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/shyt/customerPeople/customerBirthdayModule.js" type="text/javascript"></script>
<%-- <script src="${sysPath}/js/com/shjfgl/customer/customer.validate.js"type="text/javascript"></script> --%>