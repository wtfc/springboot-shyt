<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script type="text/javascript">
function oTableSetButtons(mData, type, full) {
	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"department.showDeptEditHtml("+ full.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" title=\"编辑\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"javascript:;\" onclick=\"department.deleteDepartment("+ full.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" title=\"删除\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	if(full.parentId == 0){
		return edit;
	}else{
		return edit + del;
	}
}
</script>
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>机构设置</h1>
			<div class="crumbs">
				<a href="#">首页</a><i></i>系统管理<i></i>机构设置
			</div>
		</div>
		<a class="btn dark button fr" type="button" role="button" data-toggle="modal" id="newDept">新 增</a>
	</header>
	<section class="tree-fluid m-t-md">
	<aside class="tree-wrap" id="LeftHeight_box">
		<section class="panel" id="LeftHeight" style="overflow-y: auto;">
			<div class="panel-heading clearfix" id="header_2">
				<h2>选择组织</h2>
			</div>
			<div id="treeDemo" class="ztree"></div>
		</section>
	</aside>
	<section class="tree-right">
		<section class="panel">
			<form id="deptForm">
				<!-- tree,nodeInfo -->
				<input id="deptId" name="deptId" type="hidden"/>
				<div class="table-wrap form-table">
					<table class="table table-td-striped tab_height">
						<tbody>
							<tr>
								<!-- class="w140" style="width:36%" -->
								<td class="w140">名称</td>
								<td id="deptName" class="w140"></td>
								<td class="w140">负责人</td>
								<td id="leaderName" class="w140"></td>
								<td class="w140">组织类型</td>
								<td id="deptType" class="w140"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<section class="form-btn m-b-lg m-l">
					<button id="editDept" class="btn dark" type="button" role="button" data-toggle="modal" onclick='department.showDeptEditHtml($("#deptId").val())'>编 辑</button>
					<button id="delDept" class="btn" type="button" onclick='department.deleteDepartment($("#deptId").val())'>删 除</button>
				</section>
			</form>
		</section>
		<!-- tabs -->
		<section class="tabs-wrap m-t-md">
			<ul class="nav nav-tabs">
				<li class="active"><a id="tabOrg" href="#messages1" data-toggle="tab">组 织</a></li>
				<li><a id="tabUser" href="#messages2" data-toggle="tab">人 员</a></li>
			</ul>
		</section>
		<!-- tabs end -->
		<section class="panel tab-content">
			<div class="tab-pane fade active in" id="messages1">
				<div class="table-wrap">
					<table class="table table-striped tab_height " id="departmentTable">
						<thead>
							<tr>
								<th>名称</th>
								<th>负责人</th>
								<th>组织类型</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
				<section class="clearfix">
					<section class="form-btn fl m-l">
						<button class="btn dark button fr" type="button" href="#" role="button" data-toggle="modal" id="newDept-foot">新 增</button>
					</section>
				</section>
			</div>
			<div class="tab-pane fade" id="messages2">
				<div class="table-wrap">
					<table class="table table-striped" id="uTable">
						<thead>
							<tr>
								<th style="width:40%;">姓名</th>
								<th>职务</th>
								<th style="width:13%;">级别</th>
								<th style="width:13%;">性别</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
				<section class="pagination m-r fr m-t-none"></section>
			</div>
		</section>
	</section>
</section>
</section>
<input id="deptIds" name="deptIds" type="hidden"/>
<input id="userDeptId" name="userDeptId" type="hidden"/><!-- 用户列表中用到的部门ID -->
<div id="deptInsertHtml"></div>
<div id="deptEditHtml"></div>
<script src="${sysPath}/js/com/sys/department/departmentUser.js" type="text/javascript"></script>
<script src="${sysPath}/js/common-tree.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>