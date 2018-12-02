<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>设备上下架和迁移表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaEquipmentUpDownQueryForm" >
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
 	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaEquipmentUpDown.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaEquipmentUpDown.deleteToaEquipmentUpDown("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit+del;
}; 
</script>
<h2 class="panel-heading clearfix">设备上下架和迁移</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaEquipmentUpDownTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>工单编号</th>				<th>客户ID</th>				<th>客户名称</th>				<th>客户联系人</th>				<th>客户联系方式</th>				<th>下单日期</th>				<th>操作开始时间</th>				<th>操作完成时间</th>				<th>设备ID</th>				<th>上架-设备放置位置</th>				<th>上架-网线连接要求</th>				<th>上架-网线使用类型</th>				<th>上架-是否安装系统</th>				<th>上架-是否配置IP</th>				<th>上架-机柜空间是否满足</th>				<th>上架-机柜电力是否满足</th>				<th>上架-网线实际连接情况</th>				<th>下架-操作时间</th>				<th>下架-是否远程关机</th>				<th>下架-下架后要求</th>				<th>下架-设备信息核对结果</th>				<th>下架-关机操作结果</th>				<th>下架-下架设备清单</th>				<th>是否存在后续操作</th>				<th>工单类型（3.设备上架 4、设备下架）</th>				<th>工单类型图标名称（3.设备上架 4、设备下架）</th>				<th>机房</th>				<th>操作人员ID</th>				<th>操作人员姓名</th>				<th>处理状态</th>				<th>备注</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addButton" data-toggle="modal">新 增</a>
			<button class="btn " id="deleteToaEquipmentUpDowns" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="toaEquipmentUpDownModuleDiv"></div>
<script src='${sysPath}/js/com/machine/toaEquipmentUpDown/toaEquipmentUpDown.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>