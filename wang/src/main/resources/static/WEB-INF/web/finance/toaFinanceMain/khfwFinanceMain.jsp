<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>收入信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinanceMainQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            	<td >客户名称</td>
						<td>
							<input type = "text" id="companyName" name="companyName"/>
						</td>
						<td >维护专员</td>
						<td>
							<input type="text" id="maintenanSale" name="maintenanSale" readonly value="${user}"/>
						</td>
		            </tr>
		            <tr>
		            	<td>资源变动类型</td>
						<td>
							<select id="resourceType" name="resourceType">
								<option value="">请选择</option>
								<option value="新增">新增</option>
								<option value="扩容">扩容</option>
								<option value="减容">减容</option>
								<option value="终止">终止</option>
								<option value="存量">存量</option>
								<option value="其他">其他</option>
							</select>
						</td>
						<td>机房</td>
						<td>
							<dic:select  id="roomName" name="roomName" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceMain/loadFormRead.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	return read;
}; 
</script>
<h2 class="panel-heading clearfix">收入查询</h2>
<div class="table-wrap">
	<table class="table table-striped first-td-tc tab_height" id="toaFinanceMainTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/finance/toaFinanceMain/khfwFinanceMain.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>