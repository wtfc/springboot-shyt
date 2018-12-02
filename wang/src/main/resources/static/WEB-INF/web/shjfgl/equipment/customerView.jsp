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
						<td>客户编号</td>
						 <td>
						 	<input type="text" id="extStr5" name = "extStr5"/>
						 </td>
					</tr>
					<tr>
						<td>资产编号</td>
						<td>
					 		<input type="text" id="serialNumber" name = "serialNumber"/>
						</td>
						<td>上架时间</td>
						 <td>
						 	<input class="datepicker-input" type="text" id="onlineDate" name="onlineDate" data-pick-time="true" data-date-format="yyyy-MM-dd" >
						 </td>
					</tr>
					<tr>
						<td>机房名称</td>
						<td>
		                    <select id="contact" name = "contact" >
							  <option value ="${deptName}">请选择</option>
							  <option value ="鲁谷机房">鲁谷机房</option>
							  <option value="兆维机房">兆维机房</option>
							  <option value="看丹桥机房">看丹桥机房</option>
							  <option value="洋桥机房">洋桥机房</option>
							  <option value="清华园机房">清华园机房</option>
							  <option value="沙河机房">沙河机房</option>
							  <option value="廊坊机房">廊坊机房</option>
							</select>
                      </td>
                      <td>管理网IP</td>
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
var equipment = {};

//分页处理 start
//分页对象
equipment.oTable = null;

equipment.oTableAoColumns = [
	{ "mData": "clientName"},
	{ "mData": "type" },
	{ "mData": "contact"},
	{ "mData": "address"},
	{ "mData": "machinaNumber"},
	{ "mData": "onlineDate" },
	{ "mData": "ip" },
	{ "mData": "netmaskTwo"},
	{ "mData": "interchangerOne"},
	{ "mData": "power"},	
	{ "mData": "plantPower"},
	{ "mData": "uCount"},
	{ "mData": "aCurrent"},
	{ "mData": "bCurrent"},
	{ "mData": "system"},
	{ "mData": "device"},
	{ "mData": "serialNumber"},
	{ "mData": "sn"}
];

equipment.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(equipment.oTable, aoData);
	
	var clientName = $("#equipmentQueryForm #clientName").val();
	if(clientName.length > 0){
		aoData.push({ "name": "clientName", "value":clientName});
	}
	var ip = $("#equipmentQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	var serialNumber = $("#equipmentQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}
	var onlineDate = $("#equipmentQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contact = $("#equipmentQueryForm #contact").val();
	if(contact.length > 0){
		aoData.push({ "name": "contact", "value": contact});
	}
	var extStr5 = $("#equipmentQueryForm #extStr5").val();
	if(extStr5.length > 0){
		aoData.push({ "name": "extStr5", "value": extStr5});
	}
};

//重置按钮功能
equipment.queryReset = function(){
	$('#equipmentQueryForm')[0].reset();
};


//分页查询
equipment.equipmentList = function () {
	
	if (equipment.oTable == null) {
		equipment.oTable = $('#equipmentTable').dataTable( {
			
			"iDisplayLength": equipment.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/equipment/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": equipment.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				equipment.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17]}
	        ]
		} );
	} else {
		equipment.oTable.fnDraw();
	}
};

$(document).ready(function(){
	
	//计算分页显示条数
	equipment.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(equipment.equipmentList);
	$("#queryReset").click(equipment.queryReset);
	//初始化列表方法
	equipment.equipmentList();
});
</script>
<h2 class="panel-heading clearfix">设备上架表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="equipmentTable">
		<thead>
			<tr>
				<th>客户名称</th>
				<th>品牌型号</th>
				<th>机房</th>
				<th>机柜位置</th>
				<th>机柜编号</th>
				<th>上架时间</th>
				<th>管理网IP</th>
				<th>ETH2 IP/Netmask</th>
				<th>交换机对应端口</th>
				<th>电源（单/双电）</th>
				<th>设备功率</th>
				<th>U数</th>
				<th>A路电流</th>
				<th>B路电流</th>
				<th>操作系统</th>
				<th>设备配置</th>
				<th>资产编号</th>
				<th>SN</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
		</section>
	</section>
</section>
</section>
<div id="formModuleDiv" ></div>
<%-- <script src='${sysPath}/js/com/shjfgl/equipment/equipment.js'></script> --%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>