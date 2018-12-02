<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>客户IP地址统计表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkIpaddressQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
							<input style="width:100%;"  type = "text" id="companyName" name="companyName" />
						</td>
						<td>机房</td>
						<td>
							<dic:select cssStyle="width:100%;" id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
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
<script >
var toaNetworkIpaddress = {};

//分页处理 start
//分页对象
toaNetworkIpaddress.oTable = null;

toaNetworkIpaddress.oTableAoColumns = [
	{ "mData":"companyName"},
	{ "mData":"machineValue"},
	{ "mData":"ipOne"},
	{ "mData":"ipTwo"},
	{ "mData":"ipNumber"},
	{ "mData":"bandwidthNumber"},
	{ "mData":"bandwidth"},
	{ "mData":"operationEnginner"},
	{ "mData":"operationDate"},
	{ "mData":"dividerDate"}
];

toaNetworkIpaddress.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaNetworkIpaddress.oTable, aoData);
	
	var companyName = $("#toaNetworkIpaddressQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var machine = $("#toaNetworkIpaddressQueryForm #machine").val();
	if(machine.length > 0){
		aoData.push({ "name": "machine", "value":machine});
	}
	
};

//重置按钮功能
toaNetworkIpaddress.queryReset = function(){
	$('#toaNetworkIpaddressQueryForm')[0].reset();
};


//分页查询
toaNetworkIpaddress.toaNetworkIpaddressList = function () {
	if (toaNetworkIpaddress.oTable == null) {
		toaNetworkIpaddress.oTable = $('#toaNetworkIpaddressTable').dataTable( {
			"iDisplayLength": toaNetworkIpaddress.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/network/toaNetworkIpaddress/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaNetworkIpaddress.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaNetworkIpaddress.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}
	        ]
		} );
	} else {
		toaNetworkIpaddress.oTable.fnDraw();
	}
};
$(document).ready(function(){
	//计算分页显示条数
	toaNetworkIpaddress.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaNetworkIpaddress.toaNetworkIpaddressList);
	$("#queryReset").click(toaNetworkIpaddress.queryReset);
	//初始化列表方法
	toaNetworkIpaddress.toaNetworkIpaddressList();
});
</script>
<h2 class="panel-heading clearfix">客户IP地址统计表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkIpaddressTable">
		<thead>
			<tr>
				<th>客户名称</th>				<th>机房</th>				<th>IP地址<br>段开始</th>				<th>IP地址<br>段结束</th>				<th>IP<br>数量</th>				<th>设备端口</th>				<th>带宽</th>				<th>操作工程师</th>				<th>操作时间</th>				<th>分配时间</th>			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">下 单</a> -->
		</section>
	</section>
</section>
</section>
<%-- <script src='${sysPath}/js/com/network/toaNetworkIpaddress/toaNetworkIpaddress.js'></script> --%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>