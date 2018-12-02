<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>云资源信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaProductCloudQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td style="width:10%;" class=" b-green-dark b-tc">公司名称</td>
						<td>
							<input type="text" id="customersName" name="customersName" />
						</td>
						<td class=" b-green-dark b-tc">支持人员</td>
						<td>
							<input type="text" id="supporter" name="supporter" />
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
	 var reada = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/product/toaProductCloud/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	var read = '<shiro:hasPermission name="user:updateProductCloud"><a class=\"a-icon i-new m-r-xs\" href=\"'+getRootPath()+'/product/toaProductCloud/loadForm.action?id='+source.id+'\" onclick=\"\">编辑</a></shiro:hasPermission>'; 
	var del = '<shiro:hasPermission name="user:deleteProductCloud"><a class="a-icon i-remove" href="#" onclick="toaProductCloud.deleteToaProductCloud('+ source.id+')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
	return reada+read+del;
};
</script>
<h2 class="panel-heading clearfix">云资源信息表</h2>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="toaProductCloudTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>公司名称</th>
				<th>支持人员</th>				<th>CPU</th>				<th>内存</th>				<th>容量盘</th>				<th>性能盘</th>				<th>云主机快照</th>				<th>云硬盘快照</th>				<th>公网IP</th>				<th>带宽</th>				<th>路由器</th>				<th>负载均衡</th>				<th>金额(元)</th>				<th>资源模式</th>				<th>计费开始日期</th>				<th>计费终止日期</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/product/toaProductCloud/toaProductCloud.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>