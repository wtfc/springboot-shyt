<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- -----------------------------------------------------------弹出层begin---------------------------------------------------------- -->
    <div class="modal fade panel" id="myModal-edit" aria-hidden="false">
        <div class="modal-dialog w900">
        <form class="cmxform" id="suggestTypeForm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" onclick="suggestTypeDiv.closeModel();">×</button>
                    <h4 class="modal-title" id="title"></h4>
                </div>
                <div class="modal-body">
                    <div class="form-table">
                    <input type="hidden" id="id" name="id"> 
					<input type="hidden" id="modifyDate" name="modifyDate">
					<input type="hidden" id="oldName" name="oldName">
                        <table class="table table-td-striped">
							<tbody>
								<tr>
									<td style="width:20%;"><span class="required">*</span>类型名称</td>
									<td style="width:30%;"><input type="text" id="typeName" name="typeName"></td>
									<td style="width:20%;">接收人状态</td>
									<td style="width:30%;">
									<label class="radio inline"> <input type="radio" onclick="suggestTypeDiv.radio(1)" value="1" id="isFixed" name="isFixed" checked="checked">固定</label> 
									<label class="radio inline m-l-md"> <input type="radio" onclick="suggestTypeDiv.radio(0)" value="0" id="isnoFixed" name="isFixed">不固定</label>
									</td>
								</tr>
								<tr>
									<td><span id="required" class="required">*</span>建议接收人</td>
									<td colspan="3">
										<div class="btn-in-area" id="userSelect" style="display: none;">
											<textarea id="userName" name="userName" readonly></textarea>
										</div>
										<div id="showUserTree"></div>
									</td>
								</tr>
							</tbody>
						</table>
                    </div>
                </div>
                <div class="modal-footer no-all form-btn">
					<button class="btn dark" type="button" id="savaOrModify">保存继续</button>
					<button class="btn" type="button" id="savaAndClose">保存退出</button>
					<button class="btn" type="button" id="addClose">关 闭</button>
				</div>
            </div>
            </form>
        </div>
    </div>
<script src="${sysPath}/js/com/ic/suggestType/suggestTypeInteractDiv.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/suggestType/suggestTypeInteract.validate.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/userDeptTree.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/deptFullTree.js" type="text/javascript"></script>
<!-- -----------------------------------------------------------弹出层end---------------------------------------------------------- -->
