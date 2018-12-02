<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="equipmentYangQiaoForm">
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
								<input type="hidden" id="companyId" name="companyId">
								<div class="table-wrap form-table">
									<table class="table table-td-striped">
										<tbody>
											<tr>
                                               <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>客户名称</td>
                                               <td colspan="3">
                                               		<a onclick="equipmentYangQiaoModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input type="text" id="clientName" name="clientName" style="width: 100%;" readonly/></a>
                                               		<!-- <input type="text" id="clientName" name = "clientName"/> -->
                                               </td>
                                               <!-- <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>客户编号</td>
                                               <td style="width:35%;">
                                               	<input type="text" id="extStr5" name = "extStr5"/>
                                               </td> -->
                                           </tr>
                                           <tr>
                                           		<td style="width:15%;"><span class="required">*</span>品牌型号</td>
                                               <td style="width:35%;">
                                                   <input type="text" id="type" name="type"/>
                                               </td>
                                               <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>上架时间</td>
                                               <td style="width:35%;">
                                                   <input class="datepicker-input" type="text" id="onlineDate" name="onlineDate" data-date-format="yyyy-MM-dd" >
                                               </td>
                                           </tr>
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
                                               <td><span class="required">*</span>机柜位置</td>
                                               <td>
                                                   <input type="text" id="address" name = "address"/>
                                               </td>
                                           </tr>
                                           
                                           <tr>
                                               <td>管理网IP</td>
                                               <td><input type="text" id="ip" name = "ip"/></td>
                                               <td>交换机对应端口</td>
                                               <td><input type="text" id="interchangerThree" name = "interchangerThree"/></td>
                                           </tr>
                                           <tr>
                                               <td>U数</td>
                                               <td><input type="text" id="uCount" name = "uCount"/></td>
                                               <td>U位</td>
                                               <td><input type="text" id="netmaskTwo" name = "netmaskTwo"/></td>
                                           </tr>
                                           <tr>
                                               <td>设备功率</td>
                                               <td><input type="text" id="plantPower" name = "plantPower"/></td>
                                               <td>设备功能</td>
                                               <td>

                                                   <input type="text" id="functionn" name = "functionn"/>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td>上联端口</td>
                                               <td><input type="text" id="port" name = "port"/></td>
                                               <td>A路电流</td>
                                             	<td><input type="text" id="aCurrent" name = "aCurrent"/></td>
                                           </tr>
                                           <tr>
                                               <td>B路电流</td>
                                               <td>

                                                   <input type="text" id="bCurrent" name = "bCurrent"/>
                                               </td>
                                               <td>操作系统</td>
                                               <td><input type="text" id="system" name = "system"/></td>
                                           </tr>
                                           <tr>
                                               <td>资产编号</td>
                                             	<td><input type="text" id="serialNumber" name = "serialNumber"/></td>
                                           
                                               <td>SN</td>
                                               <td>
                                                   <input type="text" id="sn" name = "sn"/>
                                               </td>
                                           </tr>
                                           <tr>
                                           		<td>电源（单/双电）</td>
                                               <td><input type="text" id="power" name = "power"/></td>
                                               <td>设备配置</td>
                                               <td><textarea  id="device" name = "device"></textarea></td>
                                           </tr>
                                           <tr>
                                               <td>备 注</td>
                                               <td colspan="3"><textarea  id="remark" name = "remark"></textarea></td>
                                           </tr>
                                           <tr>
                                               <td class=" b-green-dark b-tc">ETH1 IP/Netmask</td>
                                               <td>
                                               		<textarea id="netmaskOne" name = "netmaskOne"></textarea>
                                               		<!-- <input type="text" id="netmaskOne" name = "netmaskOne"/> -->
                                               	</td>
                                               <!--<td><input id="password" name="password" type="text" data-trigger="manual" data-placement="top" data-animation="false" /></td>-->
                                               <td>交换机对应端口</td>
                                               <td>
													<textarea id="interchangerOne" name = "interchangerOne"></textarea>
                                                  <!--  <input type="text" id="interchangerOne" name = "interchangerOne"/> -->
                                               </td>
                                           </tr>
                                           <!-- <tr>
                                               <td>ETH2 IP/Netmask</td>
                                               <td>

                                                   <input type="text" id="netmaskTwo" name = "netmaskTwo"/>
                                               </td>
                                               <td>交换机对应端口</td>
                                               <td>

                                                   <input type="text" id="interchangerTwo" name = "interchangerTwo"/>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td class=" b-green-dark b-tc">ETH3 IP/Netmask</td>
                                               <td><input type="text" id="extStr1" name = "extStr1"/></td>
                                               <td><input id="password" name="password" type="text" data-trigger="manual" data-placement="top" data-animation="false" /></td>
                                               <td>交换机对应端口</td>
                                               <td>

                                                   <input type="text" id="extStr2" name = "extStr2"/>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td>ETH4 IP/Netmask</td>
                                               <td>

                                                   <input type="text" id="extStr3" name = "extStr3"/>
                                               </td>
                                               <td>交换机对应端口</td>
                                               <td>

                                                   <input type="text" id="extStr4" name = "extStr4"/>
                                               </td>
                                           </tr> -->
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</section>
				</div>
				<div class="modal-footer form-btn">
					<shiro:hasPermission name="user:saveEquipment"><button class="btn dark" type="button" id="saveOrModify">保存继续</button></shiro:hasPermission>
					<shiro:hasPermission name="user:saveEquipment"><button class="btn" type="button" id="saveAndClose">保存退出</button></shiro:hasPermission>
					<button class="btn" type="button" class="close" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<!--公司名称提取开始  -->
<div class="modal  panel" id="new-agency" aria-hidden="false">
	<div class="modal-dialog w820">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">客户名称</h4>
			</div>
			<div class="modal-body">
				<form class="table-wrap  m-20-auto" id="equipmentCustomerForm">
					<table class="table table-td-striped"> 
						<tbody>
							<tr>
								<td>客户名称</td>
								<td>
									<input type = "text" id="companyName" name="companyName" />
				            		<button class="btn dark" type="button" onclick="equipmentYangQiaoModule.getWorkTask()">查 询</button>
				           		 	<button class="btn" type="button" onclick="equipmentYangQiaoModule.queryReset()">重 置</button>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				 <section class="panel-tab-con">
                     <div class="tab-content">
                             <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                             <form class="table-wrap">
                                 <table class="table table-striped frist-td-tc" id="equipmentYangQiaoTaskTable">
                                     <thead>
                                         <th class="w46">请选择</th>
                                         <th>公司名称</th>
                                     </thead>
                                     <tbody>
                                     </tbody>
                                 </table>
                             </form>
                         </div>
                 </section>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn" type="button" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
<!--公司名称提取结束  -->

<script src="${sysPath}/js/com/shjfgl/equipment/module/equipmentYangQiaoModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shjfgl/equipment/equipmentYangQiao.validate.js" type="text/javascript"></script>