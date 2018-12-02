<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<!--start 联系人组别 弹出层-->
<div class="modal fade panel " id="group" aria-hidden="false">
	<div class="modal-dialog w820">
		<form class="cmxform" id="groupForm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">联系人组别</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped first-td-tc tab-content" id="contactsGroup">
					<thead>
						<tr>
						    <th class="w46"><input type="checkbox" name="groupChecked"></th>
							<th style="width:20%;">组别</th>
							<th>描述</th>
							<th class="w100">操作</th>
						</tr>
					</thead>
					<tbody>
						
				    </tbody>
				</table>
			</div>
			<div class="modal-footer form-btn">
				<shiro:hasPermission name="groups:add"><a class="btn dark" type="button" id="addGroup">新 增</a></shiro:hasPermission>
				<shiro:hasPermission name="groups:delete"><a class="btn query-jump" role="button" id="deleteContactsGroup">批量删除</a></shiro:hasPermission>
			    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
			</div>
		</div>
		</form>
	</div>
</div>
<!--end 选择组别 弹出层-->
<!--start 联系人组别 弹出层-->
<div class="modal fade panel " id="contactsGroups" aria-hidden="false">
	<div class="modal-dialog">
	<form class="cmxform" id="contactsGroupsForm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title" id="mailContactsGroups">邮件联系人组</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
				<input type="hidden" id="groupsId" name="id"> 
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:30%;"><span class="required">*</span>组别</td>
								<td><input type="text" id="groupName" name="groupName">
								<input type="hidden" id="groupNameOld" name="groupNameOld"/>
								</td>
							</tr>
							<tr>
								<td>描述</td>
								<td><input type="text" id="description" name="description"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="savaOrModifyGroup">保存继续</button>
			    <button class="btn" type="button" id="saveAndCloseGroup">保存退出</button>
			    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
			</div>
		</div>
		</form>
	</div>
</div>
<!--end 选择组别 弹出层-->
<script src="${sysPath}/js/com/ic/contactsGroup/contactsGroupInteract.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/contactsGroup/contactsGroupInteract.validate.js" type="text/javascript"></script>
