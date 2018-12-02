<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
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
    	<form class="table-wrap form-table" id="equipmentQueryForm" name="equipmentQueryForm">
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
		                    <%-- <select id="contact" name = "contact" >
							  <option value ="${deptName}">请选择</option>
							  <option value ="鲁谷机房">鲁谷机房</option>
							  <option value="兆维机房">兆维机房</option>
							  <option value="看丹桥机房">看丹桥机房</option>
							  <option value="洋桥机房">洋桥机房</option>
							  <option value="清华园机房">清华园机房</option>
							  <option value="沙河机房">沙河机房</option>
							  <option value="廊坊机房">廊坊机房</option>
							</select> --%>
							<input type="text" id="contact" name="contact" value="${deptName}" readonly/>
                      </td>
                      <!-- <td>管理网IP</td> -->
                      <td>Eth1 IP</td>
				     <td>
						 	<input type="text" id="ip" name = "ip"/>
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
	
	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"equipment.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = '<shiro:hasPermission name="user:deleteEquipment"><a class="a-icon i-remove" href="#" onclick="equipment.deleteEquipment('+ source.id+')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
	buttonStr = edit + del;
	return buttonStr ;
}; 
/**
 *  导出Excel
 */
var excuteExcel = {};
excuteExcel.exportExcel = function () {
    var url = getRootPath()+"/machine/equipment/exportExcel.action";
    window.location.href=url;
};
jQuery(function($) {
	$("#buttonExport").click(excuteExcel.exportExcel);
});
</script>
<h2 class="panel-heading clearfix">设备上架表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="equipmentTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>客户名称</th>
				<th>品牌型号</th>
				<th>机房名称</th>
				<th>机柜位置</th>
				<!-- <th>机柜编号</th> -->
				<th>SN</th>
				<th>上架时间</th>
				<!-- <th>管理网IP</th> -->
				<th>Eth1 IP</th>
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
			<a class="btn dark" href="#" role="button" id="buttonExport" data-toggle="modal">导出Excel测试</a>
		</section>
	</section>
</section>
</section>
<div id="formModuleDiv" ></div>
<script src='${sysPath}/js/com/shjfgl/equipment/equipment.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>