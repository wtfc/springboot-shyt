<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script type="text/javascript">
//显示没有行数据后面的操作按钮
	function oTableSetButtons(source) {
		var edit = '<shiro:hasPermission name="contacts:update"><a class="a-icon i-edit m-r-xs" href="#" onclick="contacts.getAddHtml('+ source.id+ ')" role="button" data-toggle="modal"><i class="fa fa-edit2" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="编辑"></i></a></shiro:hasPermission>';
		var del = '<shiro:hasPermission name="contacts:delete"><a class="a-icon i-remove" href="#" onclick="contacts.deleteContacts('+ source.id+ ')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
		return edit +  del;
};

//显示没有行数据后面的操作按钮
function oTableSetGroupButtons(source) {
	var edit ='<shiro:hasPermission name="groups:update"><a class="a-icon i-edit m-r-xs" href="#" onclick="group.getGroup('+ source.id+ ')" role="button" data-toggle="modal"><i class="fa fa-edit2" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="编辑"></i></a></shiro:hasPermission>';
	var del = '<shiro:hasPermission name="groups:delete"><a class="a-icon i-remove" href="#" onclick="group.deleteContactsGroup('+ source.id+ ')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
	return edit +  del;
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in">
<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
 </div>
	 <shiro:hasPermission name="contacts:add"><a class="btn dark fr hide" role="button" data-toggle="modal" id="showAddDiv_1">新 增</a></shiro:hasPermission>
</header>
<input type="hidden" id="token" name="token" value="${token}"> 
<!--start 联系人 选项卡title-->
<section class="tabs-wrap m-t-md">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#linkman-1" data-toggle="tab" data-id="internalContacts">内部联系人</a></li>
		<li><a href="#linkman-2" onclick="contacts.contactsExternalList();" data-toggle="tab"  data-id="externalContacts">外部联系人</a></li>
	</ul>
</section>
<!--end 联系人 选项卡title-->
<!--start 联系人 选项卡content-->
		<section class="tab-content">
			<section class="panel tab-content search-shrinkage">		
			<div class="tab-pane active fade in" id="linkman-1">
			<!-- 查询条件start -->
			  <div class="search-line hide">
					<form  class="table-wrap form-table" id="internalListForm">
						<input type="hidden" id="userNameTemp" name="userNameTemp"/>
					    <input type="hidden" id="sexTemp" name="sexTemp"/>
					    <input type="hidden" id="name4-nameTemp" name="name4-nameTemp"/>
						<input type="hidden" id="officeTelTemp" name="officeTelTemp"/>
						<input type="hidden" id="phoneTemp" name="phoneTemp"/>
						<input type="hidden" id="groupTelTemp" name="groupTelTemp"/>
						<input type="hidden" id="mailTemp" name="mailTemp"/>
						<table class="table table-td-striped">
							<tbody>
								<tr>
									<td class="w140">姓名</td>
									<td>
				<!-- 						<div class="input-group"> -->
											<input type="text" id="userName">
				<!-- 							<a class="btn btn-file input-group-btn" href="#name" id="structureItem" role="button" -->
				<!-- 								data-toggle="modal"><i class="fa fa-user"></i></a> -->
				<!-- 						</div> -->
									</td>
									<td class="w140">性别</td>
									<td>
									     <dic:select name="sex" id="sex" dictName="sex" headName="-全部-" headValue="" defaultValue="" />
									</td>
								</tr>
								<tr>
								    <td class="w140">部门</td>
									<td style="width:40%;"><div id="controlTree"></div></td>
									<td>办公电话</td>
									<td>
										<input type="text" id="officeTel">
									</td>
								</tr>
								<tr>
									<td>移动电话</td>
									<td>
										<input type="text" id="phone">
									</td>		
									<td>集团号码</td>
									<td><input type="text" id="groupTel"></td>
								</tr>
								<tr>		
									<td>邮箱地址</td>
									<td><input type="text" id="mail"></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<section class="form-btn m-b-lg">
							<button class="btn dark query-jump" type="button" id="queryInternal">查 询</button>
							<button class="btn" type="button" id="queryResetInternal">重 置</button>
						</section>
						</form>
					</div>
				<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
				<!-- 查询条件end -->	
				<div class="table-wrap">
					<table class="table table-striped" id="internal">
						<thead>
							<tr>
								<th style="width:10%;">姓名</th>
								<th style="width:10%;">性别</th>
								<th style="width:10%;">部门</th>
								<th style="width:10%;">办公电话</th>
								<th style="width:10%;">移动电话</th>
								<th style="width:10%;">集团号码</th>
								<th style="width:10%;">邮箱地址</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<section class="bp-inline clearfix">
					<section class="form-btn fl m-l">
 						<a class="btn dark query-jump" id="exportExcelContacts" onclick="contacts.exportExcel(1)" role="button">导出联系人</a>
					</section>
				</section>
			</div>
			<div class="tab-pane fade in" id="linkman-2">
			<!-- 外部联系人查询条件start -->
			  <div class="search-line hide">
					<form  class="table-wrap form-table" id="externalListForm">
						<input type="hidden" id="userNameTemp" name="userNameTemp"/>
					    <input type="hidden" id="sexTemp" name="sexTemp"/>
					    <input type="hidden" id="simpleNameTemp" name="simpleNameTemp"/>
						<input type="hidden" id="groupIdTemp" name="groupIdTemp"/>
						<input type="hidden" id="mailTemp" name="mailTemp"/>
						<input type="hidden" id="phoneTemp" name="phoneTemp"/>
						<table class="table table-td-striped">
							<tbody>
								<tr>
									<td class="w140">姓名</td>
									<td>
				<!-- 						<div class="input-group"> -->
											<input type="text" id="userName">
				<!-- 							<a class="btn btn-file input-group-btn" href="#name" id="structureItem" role="button" -->
				<!-- 								data-toggle="modal"><i class="fa fa-user"></i></a> -->
				<!-- 						</div> -->
									</td>
									<td class="w140">性别</td>
									<td>
									     <dic:select name="sex" id="sex" dictName="sex" headName="-全部-" headValue="" defaultValue="" />
									</td>
								</tr>
								<tr>
								    <td class="w140">简称</td>
									<td style="width:40%;"><input type="text" id="simpleName"></td>				
									<td>组别</td>
									<td>
									<select  id="contactsGroupId" name="contactsGroupId">
									<option value="">-全部-</option>
				                    <c:forEach items="${groupList }" var="groupList">
				                    <option value="${groupList.id }">${groupList.groupName }</option>
									</c:forEach>
									</select>
									</td>
								</tr>
								<tr>
								    <td>邮箱地址</td>
									<td><input type="text" id="mail"></td>			
									<td>移动电话</td>
									<td><input type="text" id="phone"></td>
								</tr>
							</tbody>
						</table>
						<section class="form-btn m-b-lg">
							<button class="btn dark query-jump" type="button" id="queryExternal">查 询</button>
							<button class="btn"  type="button" id="queryResetExternal">重 置</button>
						</section>
						</form>
					</div>
					<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
				<!-- 外部联系人查询条件end -->
				<div class="table-wrap">
					<table class="table table-striped  first-td-tc" id="external">
						<thead>
							<tr>
								<th class="w46"><input type="checkbox" name="contactsChecked"></th>
								<th>组别</th>
								<th>姓名</th>
								<th>简称</th>
								<th>性别</th>
								<th>移动电话</th>
								<th>邮箱地址</th>
								<th class="w200">操作</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<section class="bp-inline clearfix">
					<section class="form-btn fl m-l">
						 <shiro:hasPermission name="contacts:add"><a class="btn dark query-jump" role="button" data-toggle="modal" id="showAddDiv">新 增</a></shiro:hasPermission>
							 <shiro:hasPermission name="groups:add"><a class="btn query-jump" role="button"  data-toggle="modal" id="conGroups">联系人组别</a></shiro:hasPermission>	
							  <shiro:hasPermission name="contacts:delete"><a class="btn query-jump" role="button" id="deleteContacts">批量删除</a></shiro:hasPermission>
							  <a class="btn query-jump" id="exportExcelContacts" onclick="contacts.exportExcel(2)" role="button">导出联系人</a>
<!-- 							class="btn query-jump" role="button">导 入</a> -->
					</section>
				</section>
			</div>
			</section>
		</section>

<!--end 联系人 选项卡content-->
  </section>
<!--start 新增 弹出层-->
  <div id="contactsDiv">
  </div>
<!--end 新增 弹出层-->
 
<!--start 联系人组别 弹出层-->
<div id="groupDiv">
  </div>
<!--end 选择组别 弹出层-->
<script src="${sysPath}/js/com/ic/contacts/contactsInteract.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/contacts/contactsInteract.validate.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
