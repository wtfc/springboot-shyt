<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<!--start 新增 弹出层-->
<div class="modal fade panel " id="editContacts" aria-hidden="false">
	<div class="modal-dialog">
	<form class="cmxform" id="contactsForm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title" id="mailContacts">邮件联系人</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
                <input type="hidden" id="groupRId" name="groupRId"><!-- 联系人分组关系主键 -->
						<input type="hidden" id="id" name="id" value="0"> 
						<input type="hidden" id="modifyDate" name="modifyDate">
						<input type="hidden" id="groupRId" name="groupRId">
						<table class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:30%;"><span class="required">*</span>姓名</td>
								<td><input type="text" id="userName" name="userName">
								<input type="hidden" id="userNameOld" name="userNameOld"/>
								</td>
							</tr>
							<tr>
								<td>简称</td>
								<td><input type="text" id="simpleName" name="simpleName"></td>
							</tr>
							<tr>
							     <td>性别</td>
								  <td>
                                     <dic:select name="sex" id="sex" dictName="sex" headName="-请选择-" headValue="" defaultValue=""/>                                                     
                                  </td>
							</tr>
							<tr>
								<td><span class="required">*</span>邮箱地址</td>
								<td><input type="text" id="mail" name="mail">
								<input type="hidden" id="mailOld" name="mailOld"/>
								</td>
							</tr>
							<tr>
								<td><span class="required">*</span>移动电话</td>
								<td><input type="text" id="phone" name="phone" class="phone"><input type="hidden" id="phoneOld" name="phoneOld" ></td>
							</tr>
							<tr>
								<td>组别</td>
								<td>
									<div>
										<select id="groupId" name="groupId">

					                   </select>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="savaOrModify">保存继续</button>
			    <button class="btn" type="button" id="saveAndClose">保存退出</button>
			    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
			</div>
		</div>
		</form>
	</div>
</div>
<!--end 新增 弹出层-->
<script src="${sysPath}/js/com/ic/contacts/contactsInteractDiv.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/contacts/contactsInteract.validate.js" type="text/javascript"></script>
