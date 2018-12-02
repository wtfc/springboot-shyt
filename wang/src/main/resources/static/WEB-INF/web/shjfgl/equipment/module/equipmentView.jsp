<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<section class="panel m-t-md" id="body">
		<form class="table-wrap form-table" id="equipmentView">
		<h3 class="tc" style="margin:0;border:0;">设备详细信息 </h3>
					<section class="dis-table">
						<div class="tab-content" style="overflow:hidden;">
							<div class="tab-pane for fade active in" id="messages1">
								<div class="table-wrap form-table">
									<table class="table table-td-striped">
										<tbody>
											<tr>
                                               <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>客户名称</td>
                                               <td style="width:35%;">
                                               	<input type="text" id="clientName" name = "clientName"/>
                                               </td>
                                               <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>客户编号</td>
                                               <td style="width:35%;">
                                               	<input type="text" id="extStr5" name = "extStr5"/>
                                               </td>
                                           </tr>
                                           <tr>
                                           		<td ><span class="required">*</span>品牌型号</td>
                                               <td >
                                                   <input type="text" id="type" name="type"/>
                                               </td>
                                               <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>上架时间</td>
                                               <td>
                                                   <input class="datepicker-input" type="text" id="onlineDate" name="onlineDate" data-date-format="yyyy-MM-dd" >
                                               </td>
                                           </tr>
                                           <tr>
                                               <td><span class="required">*</span>机房名称</td>
                                               <td>
                                                   <input type="text" id="contact" name = "contact" />
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
                                               <td>电源（单/双电）</td>
                                               <td><input type="text" id="power" name = "power"/></td>
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
                                               <td>设备配置</td>
                                               <td colspan="3"><textarea  id="device" name = "device"></textarea>
                                           </tr>
                                           <tr>
                                               <td>备 注</td>
                                               <td colspan="3"><textarea  id="remark" name = "remark"></textarea>
                                           </tr>
                                           <tr>
                                               <td class=" b-green-dark b-tc">ETH1 IP/Netmask</td>
                                               <td><input type="text" id="netmaskOne" name = "netmaskOne"/></td>
                                               <!--<td><input id="password" name="password" type="text" data-trigger="manual" data-placement="top" data-animation="false" /></td>-->
                                               <td>交换机对应端口</td>
                                               <td>

                                                   <input type="text" id="interchangerOne" name = "interchangerOne"/>
                                               </td>
                                           </tr>
                                           <tr>
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
                                               <!--<td><input id="password" name="password" type="text" data-trigger="manual" data-placement="top" data-animation="false" /></td>-->
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
                                           </tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</section>
			</form>
	</section>
</section>
<c:if test="${!empty id}">
<script >
$(document).ready(function(){
	var ids=(${id});
	if(ids!=null&&ids!=""){
		view(ids);
	}
});
 function view(id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/machine/equipment/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$('#equipmentView')[0].reset();
				$("#equipmentView").fill(data);
			}
		}
	});
};
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>