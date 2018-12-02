<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>IP封停记录表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkIpstopQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
							<input  type = "text" id="companyName" name="companyName" />
						</td>
						<td>机房</td>
						<td>
							<dic:select id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
						</td>
					</tr>
					<tr>
						<td>封停方式</td>
						<td>
							<select id="stopType" name="stopType">
								<option value="">请选择</option>
								<option value="ARP">ARP</option>
								<option value="NULL0">NULL0</option>
							</select>
						</td>
						<td>封停原因</td>
						<td>
							<select id="stopReason" name="stopReason">
								<option value="">请选择</option>
								<option value="攻击">攻击</option>
								<option value="网络安全组">网络安全组</option>
							</select>
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
//设置每行按钮
 function oTableSetButtones (source) {
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkIpstop/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkIpstop.deleteToaNetworkIpstop("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">IP封停记录表</h2>
<div class="table-wrap">
	<div style="overflow-x:auto;">
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkIpstopTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th style="width:10%;">客户名称</th>				<th style="width:5%;">机房</th>				<th style="width:10%;">封停IP所在设备</th>				<th style="width:10%;">IP地址/位数</th>				<th style="width:5%;">封停方式</th>				<th style="width:5%;">封停原因</th>				<th style="width:5%;">是否<br>请求<br>运营商协助</th>				<th style="width:5%;">运营商</th>				<th style="width:5%;">封停日期</th>				<th style="width:5%;">封停时间</th>				<th style="width:5%;">封停工程师</th>				<th style="width:5%;">最早可解封时间</th>				<th style="width:5%;">解封日期</th>				<th style="width:5%;">解封时间</th>				<th style="width:5%;">解封工程师</th>				<th style="width:10%;">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">下 单</a> -->
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/network/toaNetworkIpstop/toaNetworkIpstop.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>