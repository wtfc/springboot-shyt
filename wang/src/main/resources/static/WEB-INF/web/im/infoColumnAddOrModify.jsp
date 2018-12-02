<%@ page language="java" pageEncoding="UTF-8"%>
<!-- ------------------------------------------------弹出层----------------------------------------------- -->
<div class="modal fade panel" id="add-column" aria-hidden="false">
	<div class="modal-dialog w900">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" >×</button>
				<h4 class="modal-title" id="title"></h4>
			</div>
			<div class="modal-body">
			<input type="hidden" id="columnJson" name="columnJson"  value='${columnJson}'/>
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="modifyDate" name="modifyDate">
			<input type="hidden" id="oldName" name="oldName">
			<input type="hidden" id="columnLevel" name="columnLevel">
			<input type="hidden" id="columnCompareId" name="columnCompareId">
			<input type="hidden" id="oldQuene" name="oldQuene">
			<input type="hidden" id="oldLevel" name="oldLevel">
			<input type="hidden" id="oldParentId" name="oldParentId">
		    <input type="hidden" id="token" name="token" value="${token}">
				<div class="table-wrap form-table">
					 <table class="table table-td-striped">
                  <tbody>
                      <tr>
                          <td class="w140"><span class="required">*</span>栏目名称</td>
                          <td colspan="3"><input type="text" id="columnName" name="columnName"></td>
                      </tr>
                      <tr>
						  <td><span class="required">*</span>所属栏目</td>
						  <td colspan="3">
						  	<select id="columnParentId" name="columnParentId" class="inline m-t-xs">
						  		<option>-请选择-</option>
						  	</select>
						  </td>
					  </tr>
					   <tr>
						  <td>排序</td>
						  <td colspan="3">
						  	
							  	<select id="quene" name="quene" class="inline m-t-xs">
							  		<option value="1">-请选择-</option>
							  	</select>
							  	<label class="radio inline"><input type="radio" name="columnPosition" id="before" value="0" />前</label>
                              	<label class="radio inline"><input type="radio" name="columnPosition" id="after" value="1" checked/>后</label>
						  </td>
					  </tr>
                      <tr>
                     	  <td><span class="required">*</span>关联流程</td>
                          <td>
                             <div id="selectPI"></div>
                          </td>
                          <td>是否公开</td>
                          <td>
                             <label class="radio inline"><input type="radio" name="isPublic" id="isPublic" value="1" checked>公开</label>
                              <label class="radio inline"><input type="radio" name="isPublic" id="isnoPublic" value="0">不公开</label>
                          </td>
                      </tr>
                      <!-- <tr>
                          <td>可发布范围</td>
                          <td colspan="3">
							<textarea id="publisherRange" name="publisherRange" readonly>
							
							</textarea>
                          </td>
                      </tr> -->
                      <tr>
                          <td>信息发布默认查看范围</td>
                          <td colspan="3">
                              <div id="checkerTree"></div>
                          </td>
                      </tr>
                 </tbody>
              </table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="saveColumn">保 存</button>
				<button class="btn" type="button" id="closeColumn">关 闭</button>
				<input type="reset" style="display:none;"/>
			</div>
		</div>
	</div>
</div>
<!-- ------------------------------------------------弹出层----------------------------------------------- -->
