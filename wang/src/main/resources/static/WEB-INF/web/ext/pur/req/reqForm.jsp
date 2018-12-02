<%@ page language="java" pageEncoding="UTF-8"
	import="com.jc.system.security.SystemSecurityUtils"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/WorkflowHead.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<input type="hidden" id="token" name="token" value="${token}">
	<!-- 工作流相关 -->
	<input type="hidden" id="condition" name="condition"> 
	<input type="hidden" id="myBusinessUrl" value="/pur/req/manageForApplyList.action"> 
	<input type="hidden" id="todoPage" value="/ext/pur/req/reqList">
	<input type="hidden" id="jsonData" value='${jsonData}'> 
	<!-- 面包屑 -->
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1 id="mianbaoxie">采购申请</h1>
			<div class="crumbs"></div>
		</div>
	</header>

	<section class="panel m-t-md clearfix search-box" id="PurchaseArchive">
		<!--start 表单-->
		<form class="table-wrap form-table" id="toPurchaseApplyForm">
			<input type="hidden" id="id" name="id"> 
			<input type="hidden" id="applier" name="applier" value="${user.id}"> 
			<input type="hidden" id="applyDept" name="applyDept" value="${user.deptId}"> 
			<input type="hidden" id="modifyDate" name="modifyDate"> 
			<input workFlowForm="hidden" flowVariable="applyType" type="hidden" id="applyType1" name="applyType1"> 
			<input workFlowForm="hidden" flowVariable="budget" type="hidden" id="budget1" name="budget1"> 
			<input workFlowForm="hidden" flowVariable="isGeneral"  type="hidden" id="isGeneral1" name="isGeneral1"> 
			<!--  采购申请头 start-->
			<table class="table table-td-striped">
				<tbody>
					<tr>
						<td class="w140">申请人</td>
						<td style="width:40%;" id="applierName">
							 ${user.displayName}
						</td>
						<td class="w140">申请时间</td>
						<td id="createDate">
							<fmt:formatDate value="${createDate}" type="both" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<td>申请部门</td>
						<td id="applyDeptName">
							${user.deptName}
						</td>
						<td>
							<span class="required" workFlowForm="button"
							itemName="applyType">*</span>申请类型
						</td>
						<td workFlowForm="select" itemName="applyType">
							<select id="applyType" name="applyType">
									<option value="">-请选择-</option>
									<option value="0">电子类教学设备</option>
		               				<option value="1">固定厂家</option>
		               				<option value="2">非固定厂家</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><span class="required" workFlowForm="button"
							itemName="isGeneral">*</span>是否总部</td>
						<td workFlowForm="select" itemName="isGeneral">
							<select id="isGeneral" name="isGeneral">
								<option value="">-请选择-</option>
								<option value="0">是</option>
								<option value="1">否</option>
							</select>
						</td>
						<td>购买属性</td>
						<td workFlowForm="select" itemName="buyType">
							<select id="buyType" name="buyType">
								<option value="">-请选择-</option>
								<option value="0">新增</option>
								<option value="1">替换</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><span class="required" workFlowForm="button"
							itemName="budget">*</span>预算总额</td>
						<td>
							<input workFlowForm="textinput" itemName="budget" type="text" id="budget" name="budget">
						</td>
						<td></td>
						<td></td>
						
					</tr>
				</tbody>
			</table>
			<!--  采购申请头 end-->
			
			<!-- 采购申请项目 start -->
			<section id="items">
			<h2 class=" panel-heading clearfix" workFlowForm="button" itemName="itemAutoTable">申请内容</h2>
			<div class="m-t-sm" workFlowForm="button" itemName="add">
							<a href="javascript:void(0);" class="a-icon i-new m-r-xs" onclick="pur_req.addItem();">添加</a> 
			</div>
			<table class="table table-striped first-td-tc" workFlowForm="autoRow" itemName="itemAutoTable" id="itemAutoTable">
				<thead>
					<tr>
						<th>名称</th>
						<th>规格</th>
						<th>数量</th>
						<th>用途</th>
						<th>备注</th>
						<th>最终供应商</th>
						<th>最终价格</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="itemBody">
					<!-- <tr>
						<td><input type="text" id="reqItem_0.itemName" name="reqItem[0].itemName"></td>
						<td><input type="text" id="reqItem_0.model" name="reqItem[0].model"></td>
						<td><input type="text" id="reqItem_0.quantity" name="reqItem[0].quantity"></td>
						<td><input type="text" id="reqItem_0.use" name="reqItem[0].use"></td>
						<td><input type="text" id="reqItem_0.remark" name="reqItem[0].remark"></td>
						<td><input type="text" id="reqItem_0.finalVendor" name="reqItem[0].finalVendor"></td>
						<td><input type="text" id="reqItem_0.finalPrice"name="reqItem[0].finalPrice"></td>
					</tr> -->
				</tbody>
			</table>
			</section>
			<!-- 采购申请项目 end -->
			
			<!-- 采购申请项目询价信息 start -->
			<section id="item_info_0">
				<!-- 询价信息 -->
				<h2 class=" panel-heading clearfix" workFlowForm="button" itemName="itemPriceAutoTable">询价信息</h2>
				<div class="m-t-sm" workFlowForm="button" itemName="add1">
							<a href="javascript:void(0);" class="a-icon i-new m-r-xs" onclick="pur_req.addItemPrice();">添加</a> 
				</div>
				<table class="table table-striped first-td-tc" workFlowForm="autoRow" itemName="itemPriceAutoTable" id="itemPriceAutoTable">
					<thead>
						<tr>
							<th>名称</th>
							<th>供应商</th>
							<th>单价</th>
							<th>联系人</th>
							<th>联系电话</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="itemPriceBody">
							<!-- <tr>
								<td></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].vendor"></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].price"></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].linkman"></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].phone"></td>
							</tr> -->
					</tbody>
				</table>
				<!-- 行政询价信息 -->
				<h2 class=" panel-heading clearfix" workFlowForm="button" itemName="itemPriceAutoTable2" id="itemPriceAutoTable2">行政询价信息</h2>
				<div class="m-t-sm" workFlowForm="button" itemName="add2">
							<a  href="javascript:void(0);" class="a-icon i-new m-r-xs" onclick="pur_req.addItemPrice2();">添加</a> 
				</div>
				<table class="table table-striped first-td-tc" workFlowForm="autoRow" itemName="itemPriceAutoTable2">
					<thead>
						<tr>
							<th>名称</th>
							<th>供应商</th>
							<th>单价</th>
							<th>联系人</th>
							<th>联系电话</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="itemPriceBody2">
							<!-- <tr>
								<td></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].vendor"></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].price"></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].linkman"></td>
								<td><input type="text" name="reqItem[0].reqPrice[0].phone"></td>
							</tr> -->
					</tbody>
				</table>
			</section>
			<!-- 采购申请项目报价信息 end -->
			<!--end 表单-->

			<h2 class=" panel-heading clearfix"  workFlowForm="button"
							itemName="leaderTextareaContent">负责人审核意见</h2>
			<section class="table-wrap">
				<workflow:suggest itemId="leaderTextareaContent"
					showLast="false" order="createTime" classStr="" style="" />
			</section>
			<h2 class=" panel-heading clearfix"  workFlowForm="button"
							itemName="programLeaderTextareaContent">项目牵头教学总监审核意见</h2>
			<section class="table-wrap">
				<workflow:suggest itemId="programLeaderTextareaContent"
					showLast="false" order="createTime" classStr="" style="" />
			</section>
			<h2 class=" panel-heading clearfix"  workFlowForm="button"
							itemName="aoLeaderTextareaContent">行政总监审核意见</h2>
			<section class="table-wrap">
				<workflow:suggest itemId="aoLeaderTextareaContent"
					showLast="false" order="createTime" classStr="" style="" />
			</section>
			<h2 class=" panel-heading clearfix"  workFlowForm="button"
							itemName="BossTextareaContent">总裁审核意见</h2>
			<section class="table-wrap">
				<workflow:suggest itemId="BossTextareaContent"
					showLast="false" order="createTime" classStr="" style="" />
			</section>
			<h2 class=" panel-heading clearfix"  workFlowForm="button"
							itemName="FADTextareaContent">财务审核意见</h2>
			<section class="table-wrap">
				<workflow:suggest itemId="FADTextareaContent"
					showLast="false" order="createTime" classStr="" style="" />
			</section>
		</form>

		<%@ include file="/WEB-INF/web/include/WorkflowButton.jsp"%>
	</section>
	<%@ include file="/WEB-INF/web/include/WorkflowPostscript.jsp"%>
</section>

<!-- 动态添加行模板 start -->
	<textarea style="display:none" id="itemTemplate">
		<tr>
			
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='required' maxlength='20' id="reqItem_{0}.itemName" name="reqItems[{0}].itemName" value="{1}" onblur="pur_req.procSelection()"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='required' maxlength='20' id="reqItem_{0}.model" name="reqItems[{0}].model" value="{2}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='required digits' maxlength='7' id="reqItem_{0}.quantity" name="reqItems[{0}].quantity" value="{3}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" id="reqItem_{0}.use" name="reqItems[{0}].use" value="{4}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" id="reqItem_{0}.remark" name="reqItems[{0}].remark" value="{5}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" id="reqItem_{0}.finalVendor" name="reqItems[{0}].finalVendor" value="{6}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='digits' id="reqItem_{0}.finalPrice"name="reqItems[{0}].finalPrice" value="{7}"></span></td>
			<td>
				<span operate="true">
					<a class="a-icon i-remove" href="javascript:void(0);">
					<i id="prePlan_1_31" class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" onclick="pur_req.deleteItem();" data-container="body" data-original-title="删除">
					</i></a>
					</span>
				<input type="hidden" name="reqItems[{0}].id" value="{8}">
				<input type="hidden" name="reqItems[{0}].modifyDate" value="{9}">
			</td>
		</tr>
	</textarea>
	
	<textarea style="display:none" id="quatationTemplate">
		<tr>
			
			<td><span autoFlowForm='select' class='input-style'><select class='required' onchange="pur_req.selectChange()">{2}</select></span>
				<input type="hidden"  id="reqItem_{0}.reqPrice_{1}.id"  name="reqItems[{0}].reqPrices[{1}].id" value="{7}">
			</td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='required' maxlength='20'  id="reqItem_{0}.reqPrice_{1}.vendor"  name="reqItems[{0}].reqPrices[{1}].vendor" value="{3}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='required digits' maxlength='7'  id="reqItem_{0}.reqPrice_{1}.price"  name="reqItems[{0}].reqPrices[{1}].price" value="{4}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text"  id="reqItem_{0}.reqPrice_{1}.linkman"  name="reqItems[{0}].reqPrices[{1}].linkman" value="{5}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text"  id="reqItem_{0}.reqPrice_{1}.phone"  name="reqItems[{0}].reqPrices[{1}].phone" value="{6}"></span></td>
			<td>
				<span operate="true">
					<a class="a-icon i-remove" href="javascript:void(0);">
					<i id="prePlan_1_31" class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" onclick="pur_req.deleteItem();" data-container="body" data-original-title="删除">
					</i></a>
					<a class="a-icon i-new m-r-xs" href="javascript:void(0);" onclick="pur_req.select()">选中</a>
					<input type="hidden" id="reqItem_{0}.reqPrice_{1}.type" name="reqItems[{0}].reqPrices[{1}].type" value="0">
				</span>
				
			</td>
		</tr>
	</textarea>
	
	<textarea style="display:none" id="quatationTemplate2">
		<tr>
			<td><span autoFlowForm='select' class='input-style'><select class='required'  onchange="pur_req.selectChange()">{2}</select></span>
				<input type="hidden"  id="reqItem_{0}.reqPrice_{1}.id"  name="reqItems[{0}].reqPrices[{1}].id" value="{7}">
			</td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='required' maxlength='20'  id="reqItem_{0}.reqPrice_{1}.vendor"  name="reqItems[{0}].reqPrices[{1}].vendor" value="{3}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text" class='required digits' maxlength='7'  id="reqItem_{0}.reqPrice_{1}.price"  name="reqItems[{0}].reqPrices[{1}].price" value="{4}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text"  id="reqItem_{0}.reqPrice_{1}.linkman"  name="reqItems[{0}].reqPrices[{1}].linkman" value="{5}"></span></td>
			<td><span autoFlowForm='textinput' class='input-style'><input type="text"  id="reqItem_{0}.reqPrice_{1}.phone"  name="reqItems[{0}].reqPrices[{1}].phone" value="{6}"></span></td>
			<td >
				<span operate="true">
					<a class="a-icon i-remove" href="javascript:void(0);">
					<i id="prePlan_1_31" class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" onclick="pur_req.deleteItem();" data-container="body" data-original-title="删除">
					</i></a>
					<a class="a-icon i-new m-r-xs" href="javascript:void(0);" onclick="pur_req.select()">选中</a>
					<input type="hidden" id="reqItem_{0}.reqPrice_{1}.type" name="reqItems[{0}].reqPrices[{1}].type" value="1">
				</span>
				
			</td>
		</tr>
	</textarea>
<!-- 动态添加行模板 end -->
<script src="${sysPath}/js/ext/pur/req/reqForm.js"
	type="text/javascript"></script>
<script
	src="${sysPath}/js/ext/pur/req/reqForm.validate.js"
	type="text/javascript"></script>
<script src="${sysPath}/js/com/common/dynamicTableValid.js"
	type="text/javascript"></script>

<%@ include file="/WEB-INF/web/include/WorkflowFoot.jsp"%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>




