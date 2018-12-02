<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>收入主体表表</h1>
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
						<td >拓展专员</td>
						<td>
							<input type="text" id="sale" name="sale" />
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
	var read1 = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinanceMain/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaFinanceMain.deleteToaFinanceMain("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+read1+del;
}; 
function hreff(){
	 location.href=""+getRootPath()+"/finance/toaFinanceMain/loadForm.action";
}
/**
 *  导出Excel
 */
var excuteExcel = {};
excuteExcel.exportExcel = function () {
    var url = getRootPath()+"/finance/toaFinanceMain/exportExcel.action";
    window.location.href=url;
};
jQuery(function($) {
	$("#buttonExport").click(excuteExcel.exportExcel);
});
</script>
<h2 class="panel-heading clearfix">收入查询</h2>
<div class="table-wrap">
	<table class="table table-striped first-td-tc tab_height" id="toaFinanceMainTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>编码</th>				<th>变动日期</th>				<th>客户名称</th>				<th>业务<br>类型</th>				<th>资源<br>变动<br>类型</th>				<th>业务拓展部</th>
				<th>拓展专员</th>
				<th>维护专员</th>				<th>机房</th>				<th>付费方式</th>				<th>计费起<br>始时间</th>				<th>计费终<br>止时间</th>				<th>专线类型</th>				<th>单天计<br>算方式</th>				<th>超流量<br>取值方式</th>				<th>合同金额</th>				<th>应收合计</th>				<!-- <th>绩效合同额</th>				<th>存量合同额</th>				<th>预存金额</th>				<th>折扣</th> -->				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark " onclick="hreff()" id="showAddDiv_t"  data-toggle="modal">录 入</a>
			<a class="btn dark" href="#"  id="buttonExport" data-toggle="modal">导出Excel</a>
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/finance/toaFinanceMain/toaFinanceMain.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>