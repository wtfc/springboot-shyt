<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>权责发生制表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaFinanceSplitQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/toaFinanceSplit/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaFinanceSplit.deleteToaFinanceSplit("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">权责发生制</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaFinanceSplitTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>编码</th>				<th>变动日期</th>				<th>月份</th>				<th>首次入网时间</th>				<th>客户id</th>				<th>客户名称</th>				<th>业务类型</th>				<th>资源变动类型</th>				<th>原拓展部门</th>				<th>部门</th>				<th>客户经理</th>				<th>维护经理</th>				<th>机房</th>				<th>付费方式</th>				<th>计费起始时间</th>				<th>计费终止时间</th>				<th>专线类型</th>				<th>单天计算方式</th>				<th>超流量取值方式</th>				<th>合同编号</th>				<th>合同金额</th>				<th>带宽端口类型</th>				<th>存量合同额</th>				<th>预存金额</th>				<th>折扣</th>				<th>备注</th>				<th>保底带宽</th>				<th>单价</th>				<th>端口带宽</th>				<th>单价</th>				<th>超流量带宽</th>				<th>单价</th>				<th>机柜</th>				<th>单价</th>				<th>服务器</th>				<th>单价</th>				<th>IP</th>				<th>单价</th>				<th>交换机</th>				<th>单价</th>				<th>链路</th>				<th>单价</th>				<th>端口</th>				<th>单价</th>				<th>内存</th>				<th>单价</th>				<th>CPU</th>				<th>单价</th>				<th>硬盘</th>				<th>单价</th>				<th>应收月份</th>				<th>应收金额</th>				<th>应收起始日期</th>				<th>应收终止日期</th>				<th>是否开票</th>				<th>开票日期</th>				<th>发票号码</th>				<th>发票金额</th>				<th>未开票金额</th>				<th>是否回款</th>				<th>回款日期</th>				<th>回款金额</th>				<th>未回款金额</th>				<th>代理费</th>				<th>删除标记</th>				<th>创建人员ID</th>				<th>创建人所在部门ID</th>				<th>创建时间</th>				<th>修改人ID</th>				<th>修改时间</th>				<th>预留字符字段1</th>				<th>预留字符字段2</th>				<th>预留字符字段3</th>				<th>预留字符字段4</th>				<th>预留字符字段5</th>				<th>预留时间字段1</th>				<th>预留时间字段2</th>				<th>预留数字字段1</th>				<th>预留数字字段2</th>				<th>预留数字字段3</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/shyt/toaFinanceSplit/toaFinanceSplit.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>