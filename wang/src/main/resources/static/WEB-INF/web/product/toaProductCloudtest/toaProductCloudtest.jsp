<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>测试机表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaProductCloudtestQueryForm" >
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
					<tr>
						<td>测试机房</td>
						<td>
							<input type="text"  style="width:100%;"id="extStr2" name="extStr2" />
						</td>
						<td>测试IP</td>
						<td>
							<textarea  style="width:100%;"id="publicIp" name="publicIp" ></textarea>
						</td>
						<td>开始时间</td>
						<td>
							<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="startDate" name="startDate" />
						</td>
					</tr>
					<tr>
						<td>结束时间</td>
						<td>
							<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="endDate" name="endDate" />
						</td>
						<td>延长时间</td>
						<td>
							<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="chargeTime" name="chargeTime" />
						</td>
						<td>收回时间</td>
						<td>
							<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="finishDate" name="finishDate" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/product/toaProductCloudtest/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	var del = '<shiro:hasPermission name="deleteProductCloud"><a class="a-icon i-remove" href="#" onclick="toaProductCloudtest.deleteToaProductCloudtest('+ source.id+')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" data-container="body" data-original-title="删除"></i></a></shiro:hasPermission>';
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">测试机表</h2>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="toaProductCloudtestTable">
		<thead>
			<tr>
				<th>测试类型</th>
				<th>客户名称</th>				<th>客户类别</th>				<th>开始时间</th>				<th>结束时间</th>
				<!-- <th style="text-align: center"colspan="6">测试需求</th> -->				<th>销售</th>				<th>邮箱</th>				<th>测试IP</th>				<th>延长至</th>				<th>收回时间</th>				<!-- <th>其他</th> -->				<th>操作</th>
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
<script src='${sysPath}/js/com/product/toaProductCloudtest/toaProductCloudtest.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>