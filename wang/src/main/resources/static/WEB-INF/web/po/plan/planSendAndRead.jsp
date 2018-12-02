<%@ page language="java" pageEncoding="UTF-8"%>
<!--start 阅读情况 弹出层-->
<div id="read" class="modal fade panel" aria-hidden="false">
	<form id="readForm" name="readForm" class="table-wrap form-table">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">计划阅读情况</h4>
            </div>
            <div class="modal-body">
                <div class="table-wrap">
                    <table id="readTable" name="readTable" class="table table-striped">
                        <thead>
                            <tr>
                                <th>阅读人</th>
                                <th>阅读时间</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer  form-btn">
                <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
    </form>
</div>
<!--end 阅读情况 弹出层-->

<!--start 转发人员 弹出层-->
<div id="showSendUserTree" class="modal fade panel" aria-hidden="false">
    <form id="sendUserForm" name="sendUserForm" class="table-wrap form-table">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">转发人员选择</h4>
            </div>
			<div class="modal-body">
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td class="w115"><span class="required">*</span>转发人员</td>
								<td><div id="sendUserTree"></div></td>
							</tr>
						</tbody>
					</table>
                </div>
            </div>
            <div class="modal-footer  form-btn">
                <button id="sendPlan" name="sendPlan" class="btn dark" type="button">保 存</button>
                <button id="closeSend" name="closeSend" class="btn" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
    </form>
</div>
<!--end 转发人员  弹出层-->