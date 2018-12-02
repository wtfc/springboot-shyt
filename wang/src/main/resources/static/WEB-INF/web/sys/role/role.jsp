<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<script type="text/javascript" src="${sysPath}/js/com/sys/role/roleDeptTree.js"></script>
<script type="text/javascript" src="${sysPath}/js/com/sys/role/deptTree.js"></script>
<script type="text/javascript" src="${sysPath}/js/com/sys/role/menuTree.js"></script>
<script type="text/javascript" src="${sysPath}/js/com/sys/role/role.js"></script>
<script type="text/javascript" src="${sysPath}/js/com/sys/role/role.validate.js"></script>
<script type="text/javascript">
	function oTableSetButtons( source) {
		var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"roleButton.loadUpdateHtml('"+ source.id+ "')\"  data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
		var initPwd = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"roleButton.loadUpdateAuthorizeHtml("+ source.id +")\">授权</a>";
		var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"roleButton.deleteRole("+ source.id+ ","+source.personNum+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
		return edit + initPwd + del;
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" id="navigationMenu">
        <h1>角色设置</h1>
        <div class="crumbs"><a href="#">首页</a><i></i><a href="#">系统管理</a><i></i>角色设置</div>
    </div>
    <a class="btn dark fr" href="#" id="showAddDiv"  data-toggle="modal">新 增</a>
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
        <form class="table-wrap form-table" id="roleListForm">
                <input type="hidden" id="deptId" name="deptId">
                <input type="hidden" id="deptIds" name="deptIds">
                <input type="hidden" id="deptName" name="deptName" readonly/>
            </form>
        <section class="panel m-t-md">
            <h2 class="panel-heading clearfix">角色设置</h2>
            <div class="table-wrap">
                <table class="table table-striped tab_height" id="roleTable">
                    <thead>
                        <tr>
                            <th class="w200">角色名称</th>
                            <th class="w200">所属组织</th>
                            <th class="w200">人员数量</th>
                            <th class="w200">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                       
                    </tbody>
                </table>
            </div>
            <section class="clearfix" id="footer_height">
                <section class="form-btn fl m-l">
                    <shiro:hasPermission name="user:add"><a class="btn dark" href="#" id="showAddDivS"  data-toggle="modal">新 增</a></shiro:hasPermission>
                </section>
                <!-- 分页信息 -->
            </section>
 		</section>
	</section>
</section>   
</section>

    
    
    <div id="roleAuthorize">
	</div> 
    <div id="roleEdit">
	</div>  
    <script src="${sysPath}/js/common-tree.js" type="text/javascript"></script>
    <%@ include file="/WEB-INF/web/include/foot.jsp"%>
