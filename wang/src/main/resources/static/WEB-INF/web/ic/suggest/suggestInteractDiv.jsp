<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script> 

<!-- ------------------------弹出层 编辑begin-------------------------------------------- -->
   <div class="modal fade panel" id="myModal-edit" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" id="cl" data-dismiss="modal" onclick="suggest.closeModel(1)">×</button>
                    <h4 class="modal-title">新增</h4>
                </div>
                <div class="modal-body">
	            <form class="form-table" id="suggestForm">
							<!-- ---------------------------隐藏域---------------------- -->
						<input type="hidden" id="id" name="id"/>
						<input type="hidden" id="modifyDate" name="modifyDate"/>
						<input type="hidden" id="oldName" name="oldName"/>
                        <table class="table table-td-striped">
							<tbody>
								<tr>
									<td class="w115"><span class="required">*</span>建议标题</td>
									<td>
										<input type="text" id="suggestTitle" name="suggestTitle" />
									</td>
								</tr>
								<tr>
									<td><span class="required">*</span>建议类型</td>
									<td><select id="suggestTypeId" name="suggestTypeId"></select></td>
								</tr>
								<tr>
									<td>发起方式</td>
									<td>
									<label class="radio inline"> 
										<input type="radio" id="suggestWayT" name="suggestWay" checked value="0">实名
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="suggestWayF" name="suggestWay" value="1">匿名
									</label>
									</td>
								</tr>
								<tr>
									<td><span class="required">*</span>建议接收人</td>
									<td>
									<div class="btn-in-area" id="userSelect" style="display: none;">
											<textarea id="userName" name="userName" readonly></textarea>
									</div>
									<div id="controlTreeTo"></div>
									</td>
								</tr>
								<tr>
									<td>是否群发单显</td>
									<td>
										<label class="radio inline"> 
											<input type="radio" name="singleShow" value="1" checked>是
										</label> 
										<label class="radio inline"> 
											<input type="radio" name="singleShow" value="0">否
										</label>
									</td>
								</tr>
								<tr>
									<td>建议内容</td>
									<td>
										<textarea rows="3" id="suggestContent" name="suggestContent"></textarea>
										<div>您还可以输入 <span id="count">300</span>字。 </div>
									</td>
								</tr>
								<tr>
									<td>附件</td>
									<td>
									<%--<a class="btn dark" type="button" href="#"  role="button" data-toggle="modal" id="uploadFile" name="queryattach">附件</a> --%>
									<input type="file" class="filestyle" data-icon="false" data-classbutton="btn btn-file input-group-btn" data-classinput="form-file" id="filestyle-0" style="position: fixed; left: -5000px;" />
							        <a class="btn dark" type="button" href="#file-edit"  name="queryattach" id="queryattach"  role="button" data-toggle="modal">附件</a>
									<ul id="attachList"></ul>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
                </div>
                <div class="modal-footer no-all form-btn">
					<!-- <button class="btn dark" type="button" id="saveOrModify">保存继续</button> -->
					<button class="btn dark" type="button" id="saveAndClose">提 交</button>
					 <button class="btn" id="addClose" type="button">关 闭</button>
                </div>
              
            </div>
        </div>
    </div>
<!-- ------------------------------弹出层end---------------------------------------------->
 
<!--管理附件弹出层 -->
<div class="modal fade panel" id="file-edit" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close"  id="closebtn" data-dismiss="modal" onclick="showAttachList(false)">×</button>
				<h4 class="modal-title">附件</h4>
			</div>
			<div class="modal-body">
			    <!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
				<input type="hidden" name="businessId" id="businessId" value="0"/>
				<input type="hidden" name="businessTable" id="businessTable"  value="TOA_IC_SUGGEST"/> 
				<!-- upload_type 1为单传  0为多传-->
                <input type="hidden" id="upload_type" value="0">
                <!-- islogicDel 1为逻辑删除  0为物理删除-->
                <input type="hidden" id="islogicDel" value="1">
                <!-- isshow 1为查看附件  0为管理附件-->
                <input type="hidden" id="isshow" value="1"> 
                  <!-- iscopy 1为复制附件列表 0为不复制  默认为0-->
                <input type="hidden" id="iscopy" value="0">
				<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
			<!--关闭弹出层时 如需清理表格内容可调用clearTable()-->
				<button class="btn dark" type="button" id="clbtn" data-dismiss="modal" onclick="showAttachList(false)">关&nbsp;闭</button>
			</div>
		</div>
	</div>
</div>
<script src="${sysPath}/js/com/ic/suggest/suggestInteractDiv.js"></script>
<script src='${sysPath}/js/com/ic/suggest/suggestInteract.validate.js'></script>
<script src="${sysPath}/js/com/sys/user/userDeptTree.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/deptFullTree.js" type="text/javascript"></script>
