<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="actionTitle">新增</h4>
            </div>
            <div class="modal-body">
            	
	<form class="table-wrap form-table" id="specialDataForm">
		<input type="hidden" id="id" name = "id"  >
		<input type="hidden" id="token" name="token" value="${token}">
		<input type="hidden" id="modifyDate" name="modifyDate">
		<input type="hidden" id="sendmailStatus" name = "sendmailStatus" value="2">
		<input type="hidden" id="sendpictureStatus" name = "sendpictureStatus" value="2">
		<input type="hidden" id="beforeDay" name = "beforeDay" value="0">
		<input type="hidden" id="jsonData" name="jsonData" value='${jsonData}'>
		<input type="hidden" id="infoName" name = "infoName" value="">
			<table class="table table-td-striped">
				<tbody>
					<tr>
						<td style="width: 20%">信息类型</td>
						<td style="width: 30%">
							<label class="radio inline">
				       			<input type="radio" name = "infoType" id="infoType0" value="0" onclick="specialDataModule.changeInfo(0)">节日
				       		</label>
				       		<label class="radio inline">
				       			<input type="radio" name = "infoType" id="infoType1" value="1" checked onclick="specialDataModule.changeInfo(1)">生日
							</label>
						</td>
						<td style="width: 20%"><span class="required">*</span>名称</td>
						<td style="width: 30%">
							<div id="userTree"></div>
							<div id="festival" style="display: none;">
				       		<input type="text" id="tempinfoName" name = "tempinfoName" >
				       		</div>
						</td>
					</tr>
					<tr>
						<td>日期计算方式</td>
						<td>
							<label class="radio inline">
								<input type="radio" name = "solarorlunar" value="1" checked>阳历
							</label>
							<label class="radio inline">
				       			<input type="radio" name = "solarorlunar" value="2" >阴历
				       		</label>
						</td>
						<td><span class="required">*</span>提示日期</td>
						<td>
				       		<input class="datepicker-input" data-date-format="yyyy-MM-dd" type="text" id="specialData" name = "specialData" >
						</td>
					</tr>
					<tr>
						<td>公开程度</td>
						<td>
							<label class="radio inline">
								<input type="radio" name = "openLevel" value="1" checked>完全公开
							</label>
							<label class="radio inline">
				       			<input type="radio" name = "openLevel" value="2">部分公开
				       		</label>
						</td>
						<td>信息是否循环使用</td>
						<td>
							<label class="radio inline">
								<input type="radio" name = "infoCirculate" value="1" checked>是
							</label>
							<label class="radio inline">
				       			<input type="radio" name = "infoCirculate" value="2" >否
				       		</label>
						</td>
					</tr>
					<tr>
						<td><span id="dateName"><span class="required">*</span><span id="infoText">生日祝福</span></span></td>
						<td colspan="3">
							<textarea rows="3" cols="100" name="summaryContent" id="summaryContent"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		

            </div>
            <div class="modal-footer form-btn">
                <!-- <button class="btn dark" type="button" id="saveOrModify">保存继续</button> -->
                <button class="btn dark" type="button" id="saveClose">保 存</button><button class="btn" type="button" id="formDivClose">关 闭</button>
            </div>
        </div>
    </div>
</div>
<script src='${sysPath}/js/com/sys/specialData/specialDataEdit.js'></script>
<script src='${sysPath}/js/com/sys/specialData/specialData.validate.js'></script>
