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
		<form class="table-wrap  m-20-auto" id="toaContractIncontractForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">收款合同信息 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" ><span class="required">*</span>公司名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>							<td style="width:10%;" >合同编码</td>							<td>								<input type="text"  style="width:100%;"id="contractNumber" name="contractNumber" />							</td>
							<td style="width:10%;" ><span class="required">*</span>合同类别</td>
							<td>
								<input type="text" style="width:100%;" id="contractStatus" name="contractStatus"/>
								<!-- <select style="width:100%;" id="contractStatus" name="contractStatus">
									<option value="">请选择</option>
									<option value="0">新签</option>
									<option value="1">续签</option>
									<option value="2">扩容</option>
									<option value="3">减容</option>
									<option value="4">终止</option>
									<option value="5">变更</option>
									<option value="6">其他</option>
								</select> -->
							</td>
						</tr>
						<tr>
							<td style="width:10%;" ><span class="required">*</span>发起人</td>
							<td>
								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaContractIncontractModule.spCall', eval($('#userJson').val()));"  style="width:100%;"id="leard" name="leard" />
							</td>
							<td style="width:10%;" ><span class="required">*</span>发起日期</td>							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  name="leardDate" id="leardDate" style="width:100%;"/>							</td>
							<td style="width:10%;" ><span class="required">*</span>业务类型</td>
							<td>
								<dic:select cssStyle="width:100%;"  id="customerType" name="customerType" dictName="customerType" headName="-请选择-" headValue="" defaultValue=""/>
							</td>
						</tr>
						<tr>							<td style="width:10%;" ><span class="required">*</span>协议类型</td>							<td>
								<input type="text" style="width:100%;" id="agreement" name="agreement"/>
								<!-- <select style="width:100%;" id="agreement" name="agreement">
									<option value="">请选择</option>
									<option value="0">主合同</option>
									<option value="1">确认单</option>
									<option value="2">补充</option>
									<option value="3">续签</option>
									<option value="4">三方</option>
									<option value="5">终止</option>
									<option value="6">其他</option>
								</select> -->							</td>							<td style="width:10%;" ><span class="required">*</span>合同金额(元)</td>							<td>								<input type="text"  style="width:100%;"id="contractMoney" name="contractMoney" />							</td>							<td style="width:10%;" >盖章情况</td>							<td>
								<select name="seal" style="width:100%;" id="seal">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>							</td>
						</tr>
						<tr>							<td style="width:10%;" >归档时间</td>							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  id="place" name="place"  style="width:100%;"/>							</td>							<td style="width:10%;" ><span class="required">*</span>合同起始日期</td>							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  id="startDate" name="startDate" style="width:100%;"/>							</td>							<td style="width:10%;" ><span class="required">*</span>合同终止日期</td>							<td>
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" type="text"  id="endDate" name="endDate" style="width:100%;"/>							</td>						</tr>
						<tr>
							<td style="width:10%;" >是否衍生其他合同</td>							<td>
								<select  style="width:100%;" id="derive" name="derive">
									<option value="否">否</option>
									<option value="是">是</option>
								</select>							</td>							<td style="width:10%;" >衍生合同编号</td>							<td>								<input type="text"  style="width:100%;"id="deriveNo" name="deriveNo" />							</td>							<td>
								<span><a class="btn dark" type="button"
										href="#file-edit" name="queryattach" id="attachs"
										role="button" data-toggle="modal" onclick="clearTabale()">合同扫描件</a></span>
							</td>
							<td>
								<ul id="attachList"></ul>
							</td>
						</tr>
						<tr >							<td  style="width:10%;" >备注</td>							<td colspan="5">								<textarea   style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<shiro:hasPermission name="user:saveIncontract"><a class="btn dark"  onclick="toaContractIncontractModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
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
					<input type="hidden" name="businessTable" id="businessTable"  value="toa_contract_inContract"/> 
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
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaContractIncontractModule.get(ids);
	}
});
function clearTabale(){
	clearTable();
}
</script>
</c:if>
<%-- <c:if test="${empty oldId}">
<script >

function number(){
	$("#contractNumber").val("");
	
	var number = $("#Number").val();
	var types = $("#extStr1").val();
	if(types=="IDC"){
		$("#contractNumber").val("IDC"+number);
	}else if(types=="ISP"){
		$("#contractNumber").val("ISP"+number);
	}else if(types=="云"){
		$("#contractNumber").val("Y"+number);
	}else if(types=="CDN"){
		$("#contractNumber").val("CDN"+number);
	}else if(types=="流量清洗"){
		$("#contractNumber").val("DDOS"+number);
	}
}
</script>
</c:if> --%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/oa/contract/toaContractIncontract/toaContractIncontractForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/oa/contract/toaContractIncontract/toaContractIncontract.validate.js" type="text/javascript"></script>