<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/workflow.js" type="text/javascript"></script>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js"type="text/javascript"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>


<script type="text/javascript">
//设置每行按钮
 function oTableSetButtons(source) {
	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"column.updateColumnShow("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"column.deleteColumn("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"/></a>";
	
	if(source.columnParentId == 0){	
				return null;
	}else{
		if(source.isParent != "0"){//如果是父节点，只显示编辑
				return edit;
		}else{
			return edit + del ;
		}
		
	}
};
</script>

<script src="${sysPath}/js/com/im/infoColumn.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/im/infoColumn.validate.js" type="text/javascript"></script>
	
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1"><div class="con-heading fl" id="navigationMenu"></div>
		<a class="btn dark button fr" type="button" href="#" role="button" data-toggle="modal" id="newColumn">新 增</a>
	</header>
	<section class="tree-fluid m-t-md">
		<aside class="tree-wrap" id="LeftHeight_box">
			<section class="panel" id="LeftHeight" style="overflow-y: auto;">
				<h2 class="panel-heading clearfix" id="header_2">选择栏目</h2>
				<div id="treeDemo" class="ztree"></div>
			</section>
		</aside>
		<!-- ------------------------------------------------显示单条树的节点信息----------------------------------------------- -->
		<section class="tree-right">
			<section class="panel">
				<form id="columnShowForm">
					<!-- tree,nodeInfo -->
					<input id="columnId" name="columnId" type="hidden" />
					<div class="table-wrap form-table">
						<table class="table table-td-striped tab_height">
							<tbody>
								<tr>
									<!-- class="w140" style="width:36%" -->
									<td class="w140">栏目名称</td>
									<td id="columnName" class="w140"></td>
									<td class="w140">是否公开</td>
									<td id="isPublic" class="w140"></td>
								</tr>
							</tbody>
						</table>
					</div>
					<section class="form-btn m-b-lg m-l">
						<button id="editColumn" class="btn dark" type="button" href="#" role="button" data-toggle="modal" onclick='column.updateColumnShow($("#columnId").val())'>编 辑</button>
						<button id="delColumn" class="btn" type="button" onclick='column.deleteColumn($("#columnId").val())'>删 除</button>
					</section>
				</form>
			</section>
	<!-- ------------------------------------------------查询列表----------------------------------------------- -->
			<section class="panel m-t-md" id="IP-edit">
				<div class="panel-heading clearfix btn-wrap">
					<h2>查询结果</h2>
				</div>
				<div class="table-wrap input-default" id="list">
					<table class="table table-striped tab_height" id="columnTable">
						<thead>
								<tr>
									<!-- <th class="w46"><input type="checkbox"/></th> -->
									<th>栏目名称</th>
									<th>是否公开</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
	
							</tbody>
						</table>
					</div>
					<section class="clearfix">
						<section class="form-btn fl m-l">
							<a class="btn dark button fr" type="button" href="#" role="button" data-toggle="modal" id="newColumn-foot">新 增</a>
							<!-- <button class="btn " type="submit">批量删除</button> -->
						</section>
					</section>
			</section>
		</section>
	</section>
</section>
<input id="parentId" name="parentId" type="hidden" />
<input type="hidden" id="modifyFlag" name="modifyFlag">
<input type="hidden" id="columnNameTmp" name="columnNameTmp">
<form id="columnForm" name="columnForm">
<div id="infoColumnAddOrModify"></div>
</form>
<script src="${sysPath}/js/common-tree.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>