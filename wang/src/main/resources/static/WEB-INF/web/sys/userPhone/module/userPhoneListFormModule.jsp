<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="actionTitle">OA移动用户绑定表表单</h4>
            </div>
            <div class="modal-body">
            	
<form class="table-wrap form-table" id="userPhoneForm">
		<input type="hidden" id="id" name = "id"  >
		<input type="hidden" id="token" name="token" value="${data.token}">
		<input type="hidden" id="modifyDate" name="modifyDate">
			<table class="table table-td-striped">
				<tbody>
					<tr>
						<td>用户ID</td>
						<td>
				       		<input type="text" id="userId" name = "userId" >
						</td>
						<td></td>
						<td>
				       		<input type="text" id="status" name = "status" >
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
				       		<input type="text" id="imeiNo" name = "imeiNo" >
						</td>
				</tbody>
			</table>
		</form>


            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" id="saveOrModify">保存继续</button><button class="btn" type="button" id="saveAndClose">保存退出</button><button class="btn" type="button" id="formDivClose">关 闭</button>
            </div>
        </div>
    </div>
</div>
<script src='${sysPath}/js/com/sys/userPhone/module/userPhoneListFormModule.js'></script>
<script src='${sysPath}/js/com/sys/userPhone/userPhoneList.validate.js'></script>
