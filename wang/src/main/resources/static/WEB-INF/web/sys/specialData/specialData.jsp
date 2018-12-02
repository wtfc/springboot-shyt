<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js"
	type="text/javascript"></script>
<script>
	$.ajaxSetup({
		cache : false
	//设置成false将不会从浏览器缓存读取信息
	});
</script>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel search-shrinkage clearfix">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="specialDataQueryForm">
	        <table class="table table-td-striped">
	            <tbody>
					<tr>
						<td>名称</td>
						<td>
					 		<input type="text" id="infoName" name = "infoName" >
						</td>
						<td>信息类型</td>
						<td>
							<select name="infoType" id="infoType">
								<option value="">-全部-</option>
                                <option value="0">节日</option>
                                <option value="1">生日</option>
                            </select>
						</td>
					</tr>
					
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark" type="button" id="querySpecialData">查 询</button>
	            <button class="btn" type="button" id="queryReset">重 置</button>
	        </section>
	    </form>
    </div>
    <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>


	<section class="panel" id="body">
	    
			<div id="manageListTable" method="ManageList">

<script >
//设置每行按钮
function oTableSetButtones (source) {
	var buttonStr = "";
	var share = "<a class=\"a-icon i-new m-r-xs\" href=\"#myModal-share\" onclick=\"specialData.loadshareHtml("
		+ source.id + ")\" data-toggle=\"modal\">共享范围</a>";
	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"specialData.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"specialData.deleteSpecialData("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	buttonStr = share + edit + del;
	return buttonStr ;
};
</script>
<h2 class="panel-heading clearfix"></h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="specialDataTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>名称</th>
				<th>信息类型 </th>
				<th>提示日期</th>
				<th>公开程度</th>
				<th class="w170">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>

<section class="clearfix" id="footer_height">

	<section class="form-btn fl m-l">
		<a class="btn dark" href="#myModal-edit" role="button" id="addSpecialDataButton" data-toggle="modal">新 增</a><button class="btn " id="deleteSpecialDatas" type="button">批量删除</button>
	</section>

</section>

</div>
			<div id="formModuleDiv" method="FormMethod"></div>
		
	</section>
</section>
<div id="specialDatashareEdit">
</div>

<script src='${sysPath}/js/com/sys/specialData/specialData.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>