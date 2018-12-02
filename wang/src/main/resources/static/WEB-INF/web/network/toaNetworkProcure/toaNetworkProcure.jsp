<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>网络设备采购表表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkProcureQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>序列号</td>
						<td>
							<input type="text"id="serialNumber" name="serialNumber" />
						</td>
						<td>采购申请时间</td>
						<td>
							<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"id="applicationDate" name="applicationDate" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkProcure/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkProcure.deleteToaNetworkProcure("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">网络设备采购表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkProcureTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>设备型号</th>				<th>描述</th>				<th>序列号</th>				<th>采购申请时间</th>				<th>采购到货时间</th>				<th>是否安装</th>				<th>设备位置</th>				<th>备注</th>				<th>操作</th>
			</tr>
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
<script src='${sysPath}/js/com/network/toaNetworkProcure/toaNetworkProcure.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>