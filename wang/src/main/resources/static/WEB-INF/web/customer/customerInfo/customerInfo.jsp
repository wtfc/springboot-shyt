<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaShytCustomerForm">
			<h5 class=" tc" style="margin:0;border:0;">客户基本信息 </h5>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td>
								<div style="width:100%;"id="memberType"></div>
								
							</td>
							<td style="width:10%;">业务类型</td>
							<td style="width:23%;">
								
								<dic:checkbox id="serviceType" name="serviceType" dictName="customerType" />
							</td>
						</tr>
						<tr>
							<td style="width:10%;">所属行业</td>
							<td>
								<div style="width:100%;"id="trade"></div>
							</td>
							<td style="width:10%;">所属机房</td>
								<dic:checkbox  id="machine" name="machine" dictName="room" />
							<td>
								<div style="width:100%;" id="address"></div>
							</td>
						</tr>
								<div id="department" style="width:100%;"></div>
								<div style="width:100%;"id="sale"></div>
								<div style="width:100%;"id="tradeUser"></div>
							<td style="width:10%;">关联客户名称</td>
							<td>
								<div style="width:100%;"id="linkUser"></div>
							</td>
							<td style="width:10%;">注册地址</td>
								<div style="width:100%;"id="newAddress"></div>
								<div style="width:100%;"id="taxid"></div>
								<div style="width:100%;"id="bankName"></div>
								<div style="width:100%;"id="bankNo"></div>
								<div style="width:100%;"id="ticketFlag"></div>
								<div style="width:100%;"id="overflowCategory"></div>
								<div style="width:100%;"id="dailiName"></div>
								<div data-date-format="yyyy-MM-dd" style="width:100%;"id="startIntel"></div>
								<div data-date-format="yyyy-MM-dd" style="width:100%;"id="endIntel"></div>
								<div style="width:100%;"id="rating"></div>
					</tbody>
					</table>
					<c:if test="${empty Id}">
					
					<h5 class=" tc" style="margin:0;border:0;">客户联系信息 </h5>
					<table id="customerTable" class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:10%;">联系人</td>
								<td >
									<div style="width:100%;"id="name"></div>
								</td>
								<td style="width:10%;">职务</td>
								<td >
									<div id="job" style="width:100%;"></div>
								</td>
								<td style="width:10%;">联系方式</td>
								<td >
									<div id="tel" style="width:100%;"></div>
								</td>
							</tr>
							<tr>
								<td style="width:10%;">身份证号</td>
								<td>
									<div id="idCard" style="width:100%;"></div>
								</td>
								<td style="width:10%;">邮箱</td>
								<td>
									<div id="email" style="width:100%;"></div>
								</td>
								<td style="width:10%;">微信/QQ
								</td>
								<td>
									<div id="weixin" style="width:100%;"></div>
								</td>
							</tr>
						</tbody>
					</table>
					
					</c:if>

				</form>
			</section>
		</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/customer/customerInfo/customerInfo.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/userDeptTree.js" type="text/javascript"></script>