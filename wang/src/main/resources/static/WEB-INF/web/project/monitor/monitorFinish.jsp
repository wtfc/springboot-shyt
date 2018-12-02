<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>项目信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="monitorQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>项目名称</td>
						<td>
							<input hidden="hidden" id="status" name = "status" value="2"/>
					 		 <input type="text" id="name" name = "name"/>
						</td>
						<td>所属部门</td>
						 <td>
						 	<input id="extStr1" name = "extStr1" type="text"/>
						 </td>
					</tr>
					<tr>
						<td>负责人</td>
						<td>
							<input id="extStr2" name="extStr2" type="text"/>
						</td>
						<td>项目负责人</td>
						 <td>
						 	<input id="leared" name = "leared" type="text"/>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/project/monitor/manageForm.action?id="+source.id+"&status="+source.status+"\" onclick=\"\">查看</a>";
	return read;
}; 
</script>
<h2 class="panel-heading clearfix">项目信息</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="monitorTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>项目名称</th>
				<th>部门</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>项目负责人</th>
				<th>开发人</th>
				<th>操作</th>
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
<script src='${sysPath}/js/com/oa/project/monitor/monitorFinish.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>