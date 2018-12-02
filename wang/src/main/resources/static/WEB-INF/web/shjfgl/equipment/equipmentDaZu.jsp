<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>设备上架表</h1>
			<div class="crumbs"></div>
		</div>
		<!-- <a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a> -->
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="equipmentDaZuQueryForm" name="equipmentDaZuQueryForm">
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
					 		<input type="text" id="clientName" name = "clientName"/>
						</td>
						<!-- <td>客户编号</td>
						 <td>
						 	<input type="text" id="extStr5" name = "extStr5"/>
						 </td> -->
						  <td>SN</td>
                          <td>
                              <input type="text" id="sn" name = "sn"/>
                          </td>
					</tr>
					<tr>
						<!-- <td>资产编号</td>
						<td>
					 		<input type="text" id="serialNumber" name = "serialNumber"/>
						</td> -->
						 <td>机柜位置</td>
				         <td>
				            <input type="text" id="address" name = "address"/>
				         </td>
						<td>上架时间</td>
						 <td>
						 	<input class="datepicker-input" type="text" id="onlineDate" name="onlineDate" data-pick-time="true" data-date-format="yyyy-MM-dd" >
						 </td>
					</tr>
					<tr>
						<td>机房名称</td>
						<td>
							<input type="text" id="contact" name="contact" value="亦庄大族机房" readonly/>
                      </td>
                      <td>管理网IP</td>
				     	<td>
						 	<input type="text" id="ip" name = "ip"/>
						</td>
					</tr>
					<tr>
						<td>ETH1 IP</td>
				     	<td>
						 	<input type="text" id="netmaskOne" name = "netmaskOne"/>
						</td>
					</tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark" type="button" id="queryMachine">查 询</button>
	            <button class="btn" type="button" id="queryReset">重 置</button>
	        </section>
	    </form>
    </div>
    <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>

<section class="panel">
<script>
//设置每行按钮
 function oTableSetButtones (source) {
	var buttonStr = "";
	var see = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"equipmentDaZu.get("+ source.id+")\" role=\"button\" data-toggle=\"modal\" style=\"padding:1px 4px;\">查看</a>";
	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"equipmentDaZu.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\" style=\"padding:1px 4px;\">编辑</a>";
	var del = '<shiro:hasPermission name="user:deleteEquipment"><a class="a-icon i-remove" href="#" onclick="equipmentDaZu.deleteEquipment('+ source.id+')" style="padding:1px 4px;">删除</a></shiro:hasPermission>';
	buttonStr = see+edit + del;
	return buttonStr ;
}; 
jQuery(function($) {
	$("#buttonExport").click(excuteExcel.exportExcel);
});
</script>
<h2 class="panel-heading clearfix">设备上架表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="equipmentDaZuTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th style="width:15%;">客户名称</th>
				<th>品牌型号</th>
				<th>机柜位置</th>
				<!-- <th>机柜编号</th> -->
				<th>SN</th>
				<th>上架时间</th>
				<th style="width:15%;">ETH1 IP</th>
				<th style="width:15%;">管理网IP</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<shiro:hasPermission name="user:saveEquipment"><a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">新 增</a></shiro:hasPermission>
			<shiro:hasPermission name="user:deleteEquipment"><button class="btn " id="deleteMachines" type="button">批量删除</button></shiro:hasPermission>
			<a class="btn dark" href="#" role="button" id="buttonExport" data-toggle="modal">导出Excel</a>
		</section>
	</section>
</section>
</section>
<div id="formModuleDiv" ></div>
<!-- 查看 -->
<div class="modal fade panel" id="myModal" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="equipmentDaZuSeeForm">
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
                                               		<div id="clientName"></div>
                                               </td>
                                               <!-- <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>客户编号</td>
                                               <td style="width:35%;">
                                               	<input type="text" id="extStr5" name = "extStr5"/>
                                               </td> -->
                                           </tr>
                                           <tr>
                                           		<td style="width:15%;"><span class="required">*</span>品牌型号</td>
                                               <td style="width:35%;">
                                               		<div id="type"></div>
                                               </td>
                                               <td style="width:15%;" class=" b-green-dark b-tc"><span class="required">*</span>上架时间</td>
                                               <td style="width:35%;">
                                               		<div id="onlineDate" data-date-format="yyyy-MM-dd"></div>
                                                  <!--  <input class="datepicker-input" type="text" id="onlineDate" name="onlineDate" data-date-format="yyyy-MM-dd" > -->
                                               </td>
                                           </tr>
                                           <tr>
                                               <td><span class="required">*</span>机房名称</td>
                                               <td>
                                               		<div id="contact"></div>
                                               </td>
                                               <td><span class="required">*</span>机房区域</td>
                                               <td>
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
                                           </tr>
                                           
                                           <tr>
                                               <td>管理网IP</td>
                                               <td>
                                               		<div id="ip"></div>
                                               </td>
                                               <td>交换机对应端口</td>
                                               <td>
                                               		<div id="interchangerThree"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td>U数</td>
                                               <td>
                                               		<div id="uCount"></div>
                                               	</td>
                                               <td>U位</td>
                                               <td>
                                               		<div id="netmaskTwo"></div>
                                               	</td>
                                           </tr>
                                           <tr>
                                               <td>设备功率</td>
                                               <td>
                                                	<div id="plantPower"></div>
                                                </td>
                                               <td>设备功能</td>
                                               <td>
													<div id="functionn"></div>
                                                   <!-- <input type="text" id="functionn" name = "function"/> -->
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
                                           </tr>
                                           <tr>
                                               <td>B路电流</td>
                                               <td>
													<div id="bCurrent"></div>
                                               </td>
                                               <td>操作系统</td>
                                               <td>
                                               		<div id="system"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td>资产编号</td>
                                             	<td>
                                             		<div id="serialNumber"></div>
                                             	</td>
                                           
                                               <td>SN</td>
                                               <td>
                                                   <div id="sn"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                           	    <td>电源（单/双电）</td>
                                               <td>
                                               		<div id="power"></div>
                                               	</td>
                                               <td>设备配置</td>
                                               <td>
                                                  	<div id="device"></div>
                                                </td>
                                           </tr>
                                           <tr>
                                               <td>备 注</td>
                                               <td colspan="3">
                                               		<div id="remark"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td class=" b-green-dark b-tc">ETH1 IP/Netmask</td>
                                               <td>
                                               		<div id="netmaskOne"></div>
                                               </td>
                                               <!--<td><input id="password" name="password" type="text" data-trigger="manual" data-placement="top" data-animation="false" /></td>-->
                                               <td>交换机对应端口</td>
                                               <td>
													<div id="interchangerOne"></div>
                                               </td>
                                           </tr>
                                           <!-- <tr>
                                               <td>ETH2 IP/Netmask</td>
                                               <td>
													<div id="netmaskTwo"></div>
                                               </td>
                                               <td>交换机对应端口</td>
                                               <td>
													<div id="interchangerTwo"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td class=" b-green-dark b-tc">ETH3 IP/Netmask</td>
                                               <td>
                                               			<div id="extStr1"></div>
                                               </td>
                                               
                                               <td><input id="password" name="password" type="text" data-trigger="manual" data-placement="top" data-animation="false" /></td>
                                               <td>交换机对应端口</td>
                                               <td>
												 <div id="extStr2"></div>
                                               </td>
                                           </tr>
                                           <tr>
                                               <td>ETH4 IP/Netmask</td>
                                               <td>
													<div id="extStr3"></div>
                                               </td>
                                               <td>交换机对应端口</td>
                                               <td>
                                                   <div id="extStr4"></div>
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
					<button class="btn" type="button" class="close" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script src='${sysPath}/js/com/shjfgl/equipment/exportExcel.js'></script>
<script src='${sysPath}/js/com/shjfgl/equipment/equipmentDaZu.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>