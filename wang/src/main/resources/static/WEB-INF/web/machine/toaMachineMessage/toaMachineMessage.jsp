<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>工单消息表表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaMachineMessageQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            
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
 	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaMachineMessage.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaMachineMessage.deleteToaMachineMessage("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit+del;
}; 
</script>
<h2 class="panel-heading clearfix">工单消息表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaMachineMessageTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>消息编号</th>				<th>消息标题</th>				<th>消息内容</th>				<th>接收部门ID</th>				<th>消息所属工单类型</th>				<th>备注</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addButton" data-toggle="modal">新 增</a>
			<button class="btn " id="deleteToaMachineMessages" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="toaMachineMessageModuleDiv"></div>
<script src='${sysPath}/js/com/machine/toaMachineMessage/toaMachineMessage.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>