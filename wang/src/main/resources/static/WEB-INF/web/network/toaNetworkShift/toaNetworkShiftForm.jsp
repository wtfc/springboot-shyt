<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<script type="text/javascript" src="${sysPath}/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${sysPath}/js/ueditor/ueditor.all.min.js"> </script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaNetworkShiftForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">交接班记录表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<input type="hidden" id="extStr1" name="extStr1" value="0"/>
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<input type="text"  style="width:100%;"id="companyName" name="companyName" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">联系方式</td>							<td>								<input type="text"  style="width:100%;"id="phone" name="phone" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc"><span class="required">*</span>执行人</td>							<td>								<input type="text"  style="width:100%;"id="executor" name="executor" value="${name }"/>							</td>							<td style="width:10%;" class=" b-green-dark b-tc"><span class="required">*</span>交接人</td>							<td>
							<c:if test="${!empty Id}">
								<input type="text" style="width:100%;"id="connectPeople" name="connectPeople" />
							</c:if>
							<c:if test="${empty Id}">								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaNetworkShiftModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="connectPeople" name="connectPeople" />							</c:if>
							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc"><span class="required">*</span>交接时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="ture" class="datepicker-input" style="width:100%;"id="shiftDate" name="shiftDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">已交接时间</td>							<td>								<input type="text" readonly style="width:100%;"id="finishDate" name="finishDate" />							</td>						</tr>
						<tr>
							<td>
								<span><a class="btn dark" type="button"
										href="#file-edit" name="queryattach" id="attachs"
										role="button" data-toggle="modal" onclick="clearTabale()">交接附件</a></span>
							</td>
							<td>
								<ul id="attachList"></ul>
							</td>
						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">交接内容</td>
							<td colspan="3">
							<div class="email-compile">
							<textarea id="remark" name="remark" ></textarea>
							</div>
							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
						<c:if test="${empty Id}">
							<a class="btn dark"  onclick="toaNetworkShiftModule.saveOrModify(true)">提  交</a>
						</c:if>
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
					<input type="hidden" name="businessTable" id="businessTable"  value="toa_network_shift"/> 
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
		toaNetworkShiftModule.get(ids);
	}
});
</script>
</c:if>
<c:if test="${empty Id}">
<script >
$(document).ready(function(){
	getUE();
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkShift/toaNetworkShiftForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkShift/toaNetworkShift.validate.js" type="text/javascript"></script>