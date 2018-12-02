<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script >
$.ajaxSetup ({
	 cache: false //设置成false将不会从浏览器缓存读取信息
	});
</script>
<script >
//设置每行按钮
function oTableSetButtonsUserIp (source) {
	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"userIp.loadUpdateHtml("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"userIp.deleteuserIp("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit + del ;
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu">
		<h1></h1>
		<div class="crumbs"></div>
	</div>
	<a class="btn dark fr" type="button" href="#myModal-edit" id="userIpTop" role="button" data-toggle="modal">新 增</a>
</header>
<section class="panel clearfix search-box search-shrinkage">
<div class="search-line hide">
	 <form class="table-wrap form-table" id="userIpListForm">
		<table class="table table-td-striped">
			<tbody>
				<tr>
					<td class="w140">绑定用户</td>
					<td ><div id="userTree"></div></td>
				</tr>
			</tbody>
		</table>
		<section class="form-btn m-b-lg">
			<button class="btn dark" type="button" id="queryuserIp">查 询</button><button class="btn" id="queryReset" type="reset">重 置</button>
		</section>
	</form>
</div>
<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
</section>  
<section class="panel">
	<h2 class="panel-heading clearfix">查询结果</h2>
	<div class="table-wrap">
		<table class="table table-striped tab_height first-td-tc" id="userIpTable">
			<thead>
				<tr>
					<th class="w46"><input type="checkbox" /></th>
					<th class="w115">绑定用户</th>
                    <th style="width:16%;">用户部门</th>
                    <th>绑定IP</th>
                    <th style="width:16%;">修改时间</th>
					<th class="w170">操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<section class="clearfix">
		<section class="form-btn fl m-l">
			<a class="btn dark" type="button" href="#myModal-edit" id="userIpBottom" role="button" data-toggle="modal">新 增</a>
			<button class="btn" type="button" id="deleteuserIps">批量删除</button>
		</section>
		<section class="pagination m-r fr m-t-none">
		</section>
	</section>
</section>
</section>
<div id="userIpEdit">
</div>
<script src="${sysPath}/js/com/sys/userIp/userIp.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>