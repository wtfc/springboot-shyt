<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>交接班记录表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkShiftQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            <td style="width:10%;" class=" b-green-dark b-tc">执行人</td>
						<td>
							<input type="text"  id="executor" name="executor" />
						</td>
						<td style="width:10%;" class=" b-green-dark b-tc">交接人</td>
						<td>
							<input type="text" style="width:100%;"id="connectPeople" name="connectPeople" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkShift/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>"; 
	var del="";
	if(source.extStr1!=1){
		 del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkShift.deleteToaNetworkShift("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	}
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">交接班记录表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkShiftTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>执行人</th>				<th>交接人</th>				<th>交接时间</th>				<th>已交接时间</th>				<th>客户名称</th>				<th>联系方式</th>				<th>状态</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/network/toaNetworkShift/toaNetworkShift.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>