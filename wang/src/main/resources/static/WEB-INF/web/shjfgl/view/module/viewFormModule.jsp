<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="viewForm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="actionTitle">编 辑</h4>
				</div>
				<div class="modal-body">
					<section class="dis-table">
						<!-- tabs模块下紧接panel模块 -->
						<div class="tab-content" style="overflow:hidden;">
							<div class="tab-pane for fade active in" id="messages1">
								<!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
								<input type="hidden" id="id" name="id" value="0"> 
								<input type="hidden" id="token" name="token" value="${data.token}">
								<input type="hidden" id="modifyDate" name="modifyDate">
								<div class="table-wrap form-table">
									<table class="table table-td-striped">
										<tbody>
                                           <tr>
                                               <td><span class="required">*</span>机房名称</td>
                                               <td>
                                                   <select id="contact" name = "contact">
													  <option selected value ="">请选择</option>
													  <option value ="鲁谷机房">鲁谷机房</option>
													  <option value="兆维机房">兆维机房</option>
													  <option value="看丹桥机房">看丹桥机房</option>
													  <option value="洋桥机房">洋桥机房</option>
													  <option value="清华园机房">清华园机房</option>
													  <option value="沙河机房">沙河机房</option>
													  <option value="廊坊机房">廊坊机房</option>
													  <option value="富丰桥机房">富丰桥机房</option>
													  <option value="亦庄大族机房">亦庄大族机房</option>
													  <option value="小米代维">小米代维</option>
													  <option value="比目鱼机房">比目鱼机房</option>
													</select>
                                               </td>
                                               <td><span class="required">*</span>机房区域</td>
                                               <td>
                                                   <input type="text" id="machina" name="machina"/>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td class=" b-green-dark b-tc"><span class="required">*</span>机柜编号</td>
                                               <td>
                                               	<input type="text" id="machinaNumber" name = "machinaNumber"/>
                                               </td>
                                               <td>机柜位置</td>
                                               <td>
                                                   <input type="text" id="address" name = "address"/>
                                               </td>
                                           </tr>
                                           
                                           <tr>
                                               <td><span class="required">*</span>是否开通</td>
                                               <td>
                                               		<select id="isOpen" name="isOpen">
                                               			<option value="">请选择</option>
                                               			<option value="是">是</option>
                                               			<option value=否">否</option>
                                               		</select>
                                               </td>
                                               <td><span class="required">*</span>机柜类型</td>
                                               <td><select id="type" name="type">
                                               			<option value="">请选择</option>
                                               			<option value="#CD0000">公司自用</option>
                                               			<option value="#228B22">整包机柜</option>
                                               			<option value="#CDCD00">散户机柜</option>
                                               			<option value="#1C86EE">预留机柜</option>
                                               			<option value="black">空机柜</option>
                                               		</select>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td>机柜容量</td>
                                               <td><input type="text" id="valume" name = "valume"/></td>
                                               <td>现有设备数量</td>
                                               <td><input type="text" id="num" name = "num"/></td>
                                           </tr>
                                           <tr>
                                               <td>备 注</td>
                                               <td colspan="3"><textarea  id="remark" name = "remark"></textarea>
                                           </tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</section>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="saveOrModify">保存继续</button>
					<button class="btn" type="button" id="saveAndClose">保存退出</button>
					<button class="btn" type="button" class="close"
						data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script src="${sysPath}/js/com/shjfgl/view/module/viewModule.js"type="text/javascript"></script>
<script src="${sysPath}/js/com/shjfgl/view/view.validate.js"type="text/javascript"></script>