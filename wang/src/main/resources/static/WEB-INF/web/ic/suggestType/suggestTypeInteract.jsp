<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script async  src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script type="text/javascript">
//显示没有行数据后面的操作按钮
	function oTableSetButtons( source) {
		var edit = "<shiro:hasPermission name='suggestType:update'><a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"suggestType.loadHtml("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a></shiro:hasPermission>";
		var del = "<shiro:hasPermission name='suggestType:delete'><a class=\"a-icon i-remove\" href=\"#\" onclick=\"suggestType.deleteSuggestType("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"/></i></a></shiro:hasPermission>";
		return edit +  del;
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in">
	<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
	 <a class="btn dark fr" href="#myModal-edit" role="button" id="showAddDiv_t" data-toggle="modal">新 增</a>
</header>
<!------------------------------------------------------ 显示的块 ---------------------------------------------------->
<!-- ---------------------------------------------------查询条件begin--------------------------------------- -->
<input type="hidden" id="token" name="token" value=${token } />
<section class="panel clearfix search-box search-shrinkage">
	<div class="search-line hide">
		<form class="table-wrap form-table" id="suggestTypeListForm">
			<input id="suggestTypeIds" name="suggestTypeIds" type="hidden">
			<table class="table table-td-striped">
				<tbody>
					<tr>
						<td class="w140">类型名称</td>
						<td><input type="text" id="typeName" name="typeName" /></td>

						<td class="w140">接收人状态</td>
						<td><label class="checkbox inline"> <input
								type="checkbox" id="isFixed_t" name="isFixed_t" value="1">固定
						</label> <label class="checkbox inline m-l-md"> <input
								type="checkbox" id="isFixed_f" name="isFixed_f" value="0">不固定
						</label></td>
					</tr>
				</tbody>
			</table>
			<section class="form-btn m-b-lg">
				<button class="btn dark query-jump" type="button"
					id="querysuggestType">查 询</button>
				<button class="btn" type="reset">重 置</button>
			</section>
		</form>
	</div>
	<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
</section>
<!-- ---------------------------------------------------查询条件end---------------------------------------------------------- -->
<!-- ----------------------------------------------------数据列表begin-------------------------------------------------------- -->
<section class="panel" id="IP-edit">
	<div class="panel-heading clearfix btn-wrap">
		<h2>查询结果</h2>
	</div>
	<div class="table-wrap input-default" id="list">
		 <table class="table table-striped tab_height first-td-tc" id="suggestTypeTable">
			<thead>
				<tr>
					<th class="w46 sorting_disabled"><input type="checkbox" id='all_selected'/></th>
					<th>类型名称</th>
					<th>接收人状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<!-- 此处必须留有一个空的<tbody>以便js像列表中添加数据 -->
			<tbody>

			</tbody>
		</table>
	</div>
	<section class="bp-inline clearfix" id="footer_height">
		<section class="form-btn fl m-l">
			<shiro:hasPermission name="suggestType:save"><a class="btn dark query-jump" href="#" id="showAddDiv" role="button" data-toggle="modal">新 增</a></shiro:hasPermission>
			<button class="btn" type="submit" id="deleteSuggestTypes">批量删除</button>
		</section>
	</section>
</section>
<!-- ----------------------------------------------------数据列表end---------------------------------------------------------->
</section>
<!-- -----------------------------------------------------------显示的块end---------------------------------------------------------- -->

<!-- -----------------------------------------------------------弹出层begin---------------------------------------------------------- -->
    <div id="myModal-editDiv">
       
    </div>
    <script src="${sysPath}/js/com/ic/suggestType/suggestTypeInteract.js" type="text/javascript"></script>
    <script src="${sysPath}/js/com/ic/suggestType/suggestTypeInteract.validate.js" type="text/javascript"></script>
    <%@ include file="/WEB-INF/web/include/foot.jsp"%>
<!-- -----------------------------------------------------------弹出层end---------------------------------------------------------- -->
