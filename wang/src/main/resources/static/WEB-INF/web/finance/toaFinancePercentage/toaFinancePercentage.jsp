<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>绩效提成表</h1>
			<div class="crumbs"></div>
		</div>
			<a class="btn dark fr" onclick="hreff()" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinancePercentageQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>编码</td>
						<td>
							<input type="text"  id="perNumber" name="perNumber" />
						</td>
						<td>年份</td>
						<td>
							<input type="text"  id="perYear" name="perYear" />
						</td>
						<td>月份</td>
						<td>
							<input type="text"  id="perMonth" name="perMonth" />
						</td>
					</tr>
					<tr>
						<td>类型</td>
						<td>
							<select id="perType" name="perType">
								<option value="">请选择</option>
								<option value="新增">新增</option>
								<option value="存量">存量</option>
							</select>
						</td>
						<td>客户名称</td>
						<td>
							<input type="text"  id="companyName" name="companyName" />
						</td>
						<td>业务拓展部</td>
						<td>
							<input type="text"  id="department" name="department" />
						</td>
					</tr>
					<tr>
						<td>拓展专员</td>
						<td>
							<input type="text"  id="sale" name="sale" />
						</td>
						<td>客户关系维护组</td>
						<td>
							<input type="text"  id="tradeDepartment" name="tradeDepartment" />
						</td>
						<td>维护专员</td>
						<td>
							<input type="text"  id="perEnSale" name="perEnSale" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/finance/toaFinancePercentage/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaFinancePercentage.deleteToaFinancePercentage("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
};  
function hreff(){
	 location.href=""+getRootPath()+"/finance/toaFinancePercentage/loadForm.action";
}
</script>
<h2 class="panel-heading clearfix">绩效提成</h2>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="toaFinancePercentageTable">
		<thead>
			<tr>
				<th>编码</th>				<th>年份</th>				<th>月份</th>				<th>类型</th>				<th>客户名称</th>				<th>业务拓展部</th>
				<th>拓展专员</th>
				<th>客户关系维护组</th>				<th>维护专员</th>				<th>到账总金额</th>				<th>代理费</th>				<th>净收入</th>				<th>存量到<br>账收入</th>				<th>新增到<br>账收入</th>				<th>扩容到<br>账收入</th>				<th>首次入网时间</th>				<th>回款日期</th>				<th>年限</th>				<th>客维组<br>计提比例</th>				<th>拓展组<br>计提比例</th>				<th>客维组<br>计提金额</th>				<th>拓展组<br>计提金额</th>				<th>发票号</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark fr" onclick="hreff()" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/finance/toaFinancePercentage/toaFinancePercentage.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>