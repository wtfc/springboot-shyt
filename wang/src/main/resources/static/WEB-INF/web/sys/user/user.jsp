<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<script src="${sysPath}/js/com/sys/user/deptTreeToPage.js" type="text/javascript"></script>

<script type="text/javascript">
	function userOTableSetButtons( source) {
		var edit = '<shiro:hasPermission name="user:update"><a class="a-icon i-edit m-r-xs" href="#" onclick="user.loadUpdateHtml('+ source.id+')" role="button" data-toggle="modal"><i class="fa fa-edit2" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="编辑"></i></a></shiro:hasPermission>';
		var initPwd = '<shiro:hasPermission name="user:initPwd"><a class="a-icon i-new m-r-xs" href="#" onclick="user.initPassword('+ source.id +')">初始化密码</a></shiro:hasPermission>';
		var del = '<shiro:hasPermission name="user:delete"><a class="a-icon i-remove" href="#" onclick="user.deleteUser('+ source.id+ ')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
		if(source.isSystemAdmin || source.status == "status_3"){
			return null;
		} else {
			return edit + initPwd + del;
		}
};
</script>
<script src="${sysPath}/js/com/sys/user/user.js" type="text/javascript"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
    <shiro:hasPermission name="user:add"><a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a></shiro:hasPermission>
</header>
<section class="tree-fluid">
    <aside class="tree-wrap m-t-md" id="LeftHeight_box">
        <section class="panel" id="LeftHeight" style="overflow-y: auto;">
            <div class="panel-heading clearfix" id="header_2">
				<h2>选择部门</h2>
			</div>
            <div id="treeDemo" class="ztree"></div>
        </section>
    </aside>
    <section class="tree-right">
        <section class="panel search-shrinkage clearfix">
            <div class="search-line hide">
            <form class="table-wrap form-table" id="userListForm">
                <input type="hidden" id="deptId" name="deptId">
                <input type="hidden" id="deptIds" name="deptIds">
                <table class="table table-td-striped">
                    <tbody>
                        <tr>
                            <td class="w140">部门</td>
                            <td style="width:40%;">
                                <input type="text" id="deptName" name="deptName" readonly/>
                            </td>
                            <td class="w140">性别</td>
                            <td>
                             	<dic:select name="sex" id="sex" dictName="sex" headName="-全部-" headValue="" defaultValue=""/>
                               
                            </td>
                        </tr>
                        <tr>
                            <td style="width:10%;">状态</td>
                            <td style="width:40%;">
                                <select id="status" name="status">
                                    <option value="status_0">启用</option>
                                    <option value="status_2">锁定</option>
                                    <option value="status_3">删除</option>
                                    <option value="">-全部-</option>
                                </select>
                            </td>
                            <td style="width:10%;">姓名</td>
                            <td>
                                <input type="text" id="displayName" name="displayName"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <section class="form-btn m-b-lg">
                    <button class="btn dark" type="button" id="queryUser">查 询</button>
                    <button class="btn" type="button" id="queryReset">重 置</button>
                </section>
            </form>
            </div>
            <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
        </section>
        <section class="panel">
            <h2 class="panel-heading clearfix">查询结果</h2>
            <div class="table-wrap">
                <table class="table table-striped first-td-tc tab_height" id="userTable">
                    <thead>
                        <tr>
                            <th class="w46">
                                <input type="checkbox" />
                            </th>
                            <th class="w100">姓名</th>
                            <th class="w60">性别</th>
                            <th>部门</th>
                            <th>职务</th>
                            <th class="w100">状态</th>
                            <shiro:hasPermission name="user:update or user:delete or user:initPwd">
                            <th class="w210">操作</th>
                            </shiro:hasPermission>
                        </tr>
                    </thead>
                    <tbody>
                       
                    </tbody>
                </table>
            </div>
            <section class="clearfix" id="footer_height">
                <section class="form-btn fl m-l">
                    <shiro:hasPermission name="user:add"><a class="btn dark" href="#" id="showAddDiv" role="button" data-toggle="modal">新 增</a></shiro:hasPermission>
                    <shiro:hasPermission name="user:delete"><button class="btn" type="submit" id="deleteUsers">批量删除</button></shiro:hasPermission>
                </section>
                <!-- 分页信息 -->
            </section>
 		</section>
	</section>
</section> 
</section>
<div id="userEdit">
</div>        
<script src="${sysPath}/js/common-tree.js" type="text/javascript"></script>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>