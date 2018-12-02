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
		<form class="table-wrap  m-20-auto" id="outContractForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">付款合同信息 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<%-- <input type="hidden" name="Number" id="Number" value="${applyNum}"/> --%>
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td  class=" b-green-dark b-tc">
								<span class="required">*</span>甲方</td>
							<td >
								<select style="width:100%;"id="companyName" name="companyName">
									<option value="">请选择</option>
									<option value="北京森华易腾通信技术有限公司">北京森华易腾通信技术有限公司</option>
									<option value="北京网林未名信息技术有限公司">北京网林未名信息技术有限公司</option>
									<option value="北京云众林网络技术有限公司">北京云众林网络技术有限公司</option>
									<option value="廊坊森华易腾通信技术有限公司">廊坊森华易腾通信技术有限公司</option>
								</select>
							</td>
							<td >
								<span class="required">*</span>乙方</td>
							<td >
								<input type="text"  style="width:100%;"id="customer" name="customer" />
							</td>
							<td>
								<span class="required">*</span>合同编码
							</td>
							<td>
								<input type="text"  name="contractNumber" id="contractNumber" style="width:100%;" />
							</td>
						</tr>
						<tr>
							<td ><span class="required">*</span>发起人</td>
							<td >
								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'outContractModule.spCall', eval($('#userJson').val()));" name = "leard" id="leard" style="width:100%;"/>
							</td>
							<td ><span class="required">*</span>发起日期</td>
							<td >
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  name="leardDate" id="leardDate" style="width:100%;"/>
							</td>
							<td><span class="required">*</span>合同类型</td>
							<td>
								<select id="agreement" name="agreement" style="width:100%;">
									<option value="">请选择</option>
									<option value="采购">采购</option>
									<option value="基础资源">基础资源</option>
									<option value="代理 DL">代理 DL</option>
									<option value="其他">其他</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<span class="required">*</span>合同金额
							</td>
							<td>
								<input type="text"  name="contractMoney" id="contractMoney" style="width:100%;"/>
							</td>
							<td>
								<span class="required">*</span>合同起始日期
							</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  name="startDate" id="startDate" style="width:100%;"/>
							</td>
							<td>
								<span class="required">*</span>合同终止日期
							</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text" style="width:100%;" id="endDate" name = "endDate"/>
							</td>
						</tr>
						<tr>
							<td>
								盖章情况
							</td>
							<td>
								<select id="seal" name = "seal" style="width:100%;">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</td>
							<td>
								归档时间
							</td>
							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  type="text"  name="place" id="place" style="width:100%;"/>
							</td>
							<td>
								<span><a class="btn dark" type="button"
										href="#file-edit" name="queryattach" id="attachs"
										role="button" data-toggle="modal" onclick="clearTabale()">合同扫描件</a></span>
							</td>
							<td>
								<ul id="attachList"></ul>
							</td>
						</tr>
						<tr>
							<td>
								合同内容
							</td>
							<td colspan="5">
								<textarea  name="resource" id="resource" style="width:100%;"></textarea>
							</td>
						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark" role="button" onclick="outContractModule.saveOrModify(true)">提  交</a>
						</section>
					</section>
				</form>
			</section>
		</section>
<!--start 上传附件  -->
	<div class="modal fade panel" id="file-edit" aria-hidden="false">
		<div class="modal-dialog w820">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
					<h4 class="modal-title">添加上传文件</h4>
				</div>
				<div class="modal-body">
				<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
					<!-- islogicDel 1为逻辑删除 0为物理删除-->
					<input type="hidden" id="islogicDel" value="1">
					<input type="hidden" id="iscopy"  value="0">
					<input type="hidden" name="businessId" id="businessId" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource" />
					<input type="hidden" name="businessTable" id="businessTable"  value="toa_shyt_outContract"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="0">
	                <input type="hidden" id="upload_div_name" value="file-edit">
					<!-- <input type="hidden" id="upload_close_callback" value="equipmentInOutModule.echoAttachList">  --> 
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button"  id="closeModalBtn" onclick="showAttachList(true)" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
<c:if test="${!empty oldId}">
<script >
$(document).ready(function(){
	var ids=(${oldId});
	if(ids!=null&&ids!=""){
		outContractModule.get(ids);
	}
});
function clearTabale(){
	clearTable();
}
</script>
</c:if>
<!-- <script >

function number(){
	$("#contractNumber").val("");
	var number = $("#Number").val();
	var types = $("#agreement").val();
	if(types=="采购"){
		$("#contractNumber").val("CG"+number);
	}else if(types=="基础资源"){
		$("#contractNumber").val("JC"+number);
	}else if(types=="其他"){
		$("#contractNumber").val("QT"+number);
	}
}
</script> -->
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shyt/outContract/outContractModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shyt/outContract/toaContractOutcontract.validate.js" type="text/javascript"></script>