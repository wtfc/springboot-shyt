<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>CDN资源表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaProductCdnQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td style="width:10%;" class=" b-green-dark b-tc">公司名称</td>
						<td>
							<input type = "text" id="customersName" name="customersName" />
						</td>
						<td style="width:10%;" class=" b-green-dark b-tc">支持人员</td>
						<td>
							<input type="text"  style="width:100%;"id="supporter" name="supporter" />
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
	var reada = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/product/toaProductCdn/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	var read = '<shiro:hasPermission name="user:updateProductCdn"><a class=\"a-icon i-new m-r-xs\" href=\"'+getRootPath()+'/product/toaProductCdn/loadForm.action?id='+source.id+'\" onclick=\"\">编辑</a></shiro:hasPermission>'; 
	var del = '<shiro:hasPermission name="user:deleteProductCdn"><a class="a-icon i-remove" href="#" onclick="toaProductCdn.deleteToaProductCdn('+ source.id+')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
	return reada+read+del;
}; 
</script>
<h2 class="panel-heading clearfix">CDN资源表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaProductCdnTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>公司名称</th>
				<th>支持人员</th>				<th>用户名</th>				<th>加速域名</th>				<th>回源IP/回源域名</th>				<th>计费方式</th>				<th>单价(元)</th>				<th>保底数值</th>				<th>计费数值</th>				<th>金额(元)</th>				<th>计费开始日期</th>				<th>计费终止日期</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/product/toaProductCdn/toaProductCdn.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>