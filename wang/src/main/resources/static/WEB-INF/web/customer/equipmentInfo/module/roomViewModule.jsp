<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<div class="modal fade panel" id="myModal" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="equipmentDaZuSeeForm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="actionTitle">编 辑</h4>
				</div>
				<div class="modal-body">
					
				</div>
				<div class="modal-footer form-btn">
					<button class="btn" type="button" class="close" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="roomViewModuleForm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="actionTitle">操作</h4>
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
								<input type="hidden" id="companyId" name="companyId">
								<div class="table-wrap form-table">
									<table class="table table-td-striped">
										<tbody>
											<tr>
                                               <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>客户名称</td>
                                               <td style="width:18%;">
                                               		<div id="clientName"></div>
                                               </td>
                                               <td style="width:15%;"><span class="required">*</span>机房名称</td>
                                               <td style="width:18%;">
                                               		<div id="contact"></div>
                                               </td>
                                               <td style="width:15%;"><span class="required">*</span>机房区域</td>
                                               <td style="width:18%;">
                                               		<div id="machina"></div>
                                               </td>
                                               
                                           </tr>
                                           <tr>
                                           		<td class=" b-green-dark b-tc"><span class="required">*</span>机柜编号</td>
                                               <td>	
                                               		<div id="machinaNumber"></div>
                                               </td>
                                               <td><span class="required">*</span>机柜位置</td>
                                               <td>
                                               		<div id="address"></div>
                                               </td>
                                               <td><span class="required">*</span>品牌型号</td>
                                               <td>
                                               		<div id="type"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                           		<td class=" b-green-dark b-tc"><span class="required">*</span>IP</td>
                                               <td>
                                               		<div id="ip"></div>
                                               </td>
                                               <td class=" b-green-dark b-tc"><span class="required">*</span>上架时间</td>
                                               <td>
                                               		<div id="onlineDate" data-date-format="yyyy-MM-dd"></div>
                                               </td>
                                               <td><span class="required">*</span>SN</td>
                                               <td>
                                                   <div id="sn"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                           		<td>交换机对应端口</td>
                                               <td>
                                               		<div id="interchangerThree"></div>
                                               </td>
                                               <td>管理网IP</td>
                                               <td>
                                               		<div id="ip"></div>
                                               </td>
                                               <td>U数</td>
                                               <td>
                                               		<div id="uCount"></div>
                                               	</td>
                                               
                                           </tr>
                                           <tr>
                                               <td>U位</td>
                                               <td>
                                               		<div id="netmaskTwo"></div>
                                               	</td>
                                               	<td>设备功率</td>
                                               <td>
                                                	<div id="plantPower"></div>
                                                </td>
                                                <td>设备功能</td>
                                               <td>
													<div id="functionn"></div>
                                               </td>
                                           </tr>
                                           
                                           <tr>
                                               <td>上联端口</td>
                                               <td>
                                               		<div id="port"></div>
                                               </td>
                                               <td>A路电流</td>
                                             	<td>
                                             	    <div id="aCurrent"></div>
                                            	 </td>
                                            	 <td>B路电流</td>
                                               <td>
													<div id="bCurrent"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                               
                                               <td>操作系统</td>
                                               <td>
                                               		<div id="system"></div>
                                               </td>
                                               <td>资产编号</td>
                                             	<td>
                                             		<div id="serialNumber"></div>
                                             	</td>
                                             	<td>电源（单/双电）</td>
                                               <td>
                                               		<div id="power"></div>
                                               	</td>
                                           </tr>
                                           <tr>
                                               <td>设备配置</td>
                                               <td>
                                                  	<div id="device"></div>
                                                </td>
                                           		 <td>交换机对应端口</td>
                                               <td>
													<div id="interchangerOne"></div>
                                               </td>
                                               <td class=" b-green-dark b-tc">ETH1 IP/Netmask</td>
                                               <td>
                                               		<div id="netmaskOne"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                              
                                               <td>备 注</td>
                                               <td colspan="5">
                                               		<div id="remark"></div>
                                               </td>
                                           </tr>
                                           
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</section>
					<section>
					<input type="hidden" id="id" name="id" value="0"> 
					<input type="hidden" id="token" name="token" value="${data.token}">
					<input type="hidden" id="modifyDate" name="modifyDate">
					<input type="hidden" id="equipmentNumber" name="equipmentNumber" readonly value="${applyNum}"/>
					
					</section>
				</div>

				<div class="modal-footer form-btn">
					<div style="width:70%;float:left;">
						<div style="width:20%;float:left;">
							<input type="radio" id="operate1" name="operate" value="1"checked="checked">重启操作
						</div>
						<div style="width:20%;float:left;">
							<input type="radio" id="operate2" name="operate" value="机房操作">机房操作
						</div>
						<div style="width:20%;float:left;">
							<input type="radio" id="operate3" name="operate" value="机房进出">机房进出
						</div>
					</div>
					<div style="width:30%;float:left;">
						<button class="btn" type="button" id="saveAndClose">发起操作</button>
						<button class="btn" type="button" class="close" data-dismiss="modal">关 闭</button>
					</div>
					
				</div>
			</div>
		</form>
	</div>
</div>
<script src="${sysPath}/js/com/customer/equipmentInfo/module/roomViewModule.js" type="text/javascript"></script>