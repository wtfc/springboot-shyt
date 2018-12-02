<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>机房参观和入室维护表表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaRoomVisitMaintenanceQueryForm" >
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
 	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaRoomVisitMaintenance.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaRoomVisitMaintenance.deleteToaRoomVisitMaintenance("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit+del;
}; 
</script>
<h2 class="panel-heading clearfix">机房参观和入室维护表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaRoomVisitMaintenanceTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>工单编号</th>				<th>客户id</th>				<th>客户名称</th>				<th>行业类型</th>				<th>客户联系人</th>				<th>客户联系方式</th>				<th>入室日期</th>				<th>入室操作类型（维护、搬入、搬出、迁移）</th>				<th>实际操作内容（维护、搬入、搬出、迁移）</th>				<th>有无前置操作</th>				<th>入室人员</th>				<th>入室人员身份证号</th>				<th>参观区域</th>				<th>是否进场</th>				<th>陪同人员</th>				<th>评价</th>				<th>实际入室时间</th>				<th>实际出室时间</th>				<th>处理状态</th>				<th>备注</th>				<th>前置操作链接</th>				<th>附件</th>				<th>删除标记
            1 删除
            0 未删除</th>				<th>创建人员ID</th>				<th>创建人所在部门ID</th>				<th>创建时间</th>				<th>修改人ID</th>				<th>修改时间</th>				<th>预留字符字段1</th>				<th>预留字符字段2</th>				<th>预留字符字段3</th>				<th>预留字符字段4</th>				<th>预留字符字段5</th>				<th>预留时间字段1</th>				<th>预留时间字段2</th>				<th>预留数字字段1</th>				<th>预留数字字段2</th>				<th>预留数字字段3</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addButton" data-toggle="modal">新 增</a>
			<button class="btn " id="deleteToaRoomVisitMaintenances" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="toaRoomVisitMaintenanceModuleDiv"></div>
<script src='${sysPath}/js/com/machine/toaRoomVisitMaintenance/toaRoomVisitMaintenance.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>