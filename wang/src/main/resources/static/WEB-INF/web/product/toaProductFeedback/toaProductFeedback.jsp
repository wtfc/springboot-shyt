<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>测试反馈表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaProductFeedbackQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            	<td>测试类型</td>
						<td>
							<select style="width:100%;" id="extStr1" name="extStr1">
								<option value="">全部</option>
								<option value="云主机测试">云主机测试</option>
								<option value="物理机测试">物理机测试</option>
							</select>
						</td>
						<td>客户名称</td>
						<td>
							<input type="text"id="companyName" name="companyName" />
						</td>
						<td>销售</td>
						<td>
							<input type="text"id="salePeople" name="salePeople" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/product/toaProductFeedback/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaProductFeedback.deleteToaProductFeedback("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">测试反馈</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaProductFeedbackTable">
		<thead>
			<tr>
				<th>测试类型</th>
				<th>客户名称</th>				<th>销售</th>				<th>测试平台</th>				<th>收回时间</th>				<th>客户评价</th>				<th>备注</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/product/toaProductFeedback/toaProductFeedback.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>