<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>客户基本信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaShytCustomerQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            	<td>客户名称</td>
						<td>
					 		 <input type="text" id="companyName" name = "companyName"/>
					 		 <input type="hidden" id="extStr1" name = "extStr1" value="0"/>
						</td>
						<td>客户类型</td>
						 <td>
						 	<select id="memberType" name = "memberType">
							  	<option selected value ="">请选择</option>
							  	<option value="VVIP客户">VVIP客户</option>
								<option value="VIP客户">VIP客户</option>
								<option value="大客户">大客户</option>
								<option value="普通客户">普通客户</option>
							</select>
						 </td>
					</tr>
					<tr>
						<td>维护专员</td>
						<td>
							<input id="tradeUser" name="tradeUser" type="text"/>
						</td>
						<td>关联客户名称</td>
						 <td>
						 	<input id="linkUser" name = "linkUser" type="text"/>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/toaShytCustomer/viewPage.action?id="+source.id+"&company="+source.companyName+"&companyId="+source.companyId+"\" onclick=\"\">查看详细</a>"; 
	return read;
}; 
</script>
<h2 class="panel-heading clearfix">客户基本信息表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaShytCustomerTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>客户名称</th>				<th>签约拓展组</th>
				<th>签约拓展专员</th>
				<th>维护专员</th>				<th>关联客户名称</th>				<th>所属行业</th>				<th>客户类型</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">下 单</a> -->
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/shyt/customer/viewShytCustomer.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>