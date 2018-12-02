<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>OA移动用户绑定表</h1>
			<div class="crumbs"></div>
		</div>
		<!-- <a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a> -->
	</header>
	<section class="panel search-box search-shrinkage clearfix">
                            <div class="search-line hide">
								<form class="table-wrap form-table" id="userPhoneQueryForm" name="userPhoneQueryForm">
									<table class="table table-td-striped">
										<tbody>
											<tr>
												<td class="w140">姓名</td>
												<td>
													<input type="text" id="displayName" name="displayName" >
												</td>
												<td class="w140">性别</td>
												<td>
													<dic:select name="sex" id="sex" dictName="sex" headName="-全部-" headValue="" defaultValue=""/>
												</td>
											</tr>
											<tr>
												<td class="w140">状态</td>
												<td>
													<select id="status" name="status">
					                                    <option value="1" selected="selected">启用</option>
					                                    <option value="0">锁定</option>
					                                    <option value="2">待审核</option>
					                                    <option value="">-全部-</option>
					                                </select>
												</td>
												<td class="w140"></td>
												<td>
												</td>
											</tr>
										</tbody>
									</table>
									<section class="form-btn m-b-lg">
										<button class="btn dark query-jump" type="button" id="queryType">查 询</button>
										<button class="btn" type="reset" id="queryReset">重 置</button>
									</section>
								</form>
							</div>
							<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
							</section>
	<section class="panel m-t-md" id="body">
	    <div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div id="formModuleDiv" method="FormMethod"></div>
			<div id="manageListTable" method="ManageList">

<script >
//设置每行按钮
function oTableSetButtones (source) {
	var initPwd = "";
	if(source.status == "0"){
	}else if(source.status == "2"){
		initPwd = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"userPhone.changeStatus("+ source.id +",'1')\">审核</a>";
	}
	else{
		initPwd = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"userPhone.changeStatus("+ source.id +",'0')\">锁定</a>";
	}
	
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"userPhone.deleteUserPhone("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return initPwd + del ;
};
</script>
<h2 class="panel-heading clearfix">查询结果</h2>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="userPhoneTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>姓名</th>
				<th>型号</th>
				<th class="w60">性别</th>
				<th>部门</th>
				<th>用户名</th>
				<th>串号</th>
				<th class="w60">状态</th>
				<th class="w170">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
<section class="clearfix">    
	<section class="form-btn fl m-l">
		<!-- <a class="btn dark" href="#myModal-edit" role="button" id="addUserPhoneButton" data-toggle="modal">新 增</a> -->
		<button class="btn " id="deleteUserPhones" type="button">批量删除</button>
	</section>
	<section class="pagination m-r fr m-t-none">
	</section>
</section>
    </div>
		</div>
	</div>
</div>
	</section>
</section>
 <script src='${sysPath}/js/com/sys/userPhone/userPhoneList.js'></script>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>