<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>资产信息表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaShytAssetQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
	            	<tr>
			            <td>资产名称</td>
						<td><input type="text" style="width:100%;" id="assetsName"
							name="assetsName" /></td>
						<td>类别</td>
						<td><select style="width:100%;" id="type"name="type">
								<option value="">请选择</option>
								<option value="0">办公家具</option>
								<option value="1">办公设备</option>
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
 	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaShytAsset.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaShytAsset.deleteToaShytAsset("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit+del;
}; 
</script>
<h2 class="panel-heading clearfix">资产信息表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaShytAssetTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>资产名称</th>				<th>类别</th>				<th>品牌</th>				<th>资产编号</th>				<th>设备参数</th>				<th>单位</th>				<th>数量</th>				<th>单价</th>				<th>入库日期</th>				<th>使用部门</th>				<th>存放地点</th>				<th>调拨内容</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addButton" data-toggle="modal">新 增</a>
			<button class="btn " id="deleteToaShytAssets" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="toaShytAssetModuleDiv"></div>
<script src='${sysPath}/js/com/shyt/toaShytAsset/toaShytAsset.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>