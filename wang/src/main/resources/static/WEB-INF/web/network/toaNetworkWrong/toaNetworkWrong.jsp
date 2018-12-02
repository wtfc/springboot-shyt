<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>网络故障处理表表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkWrongQueryForm" >
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
 	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaNetworkWrong.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkWrong.deleteToaNetworkWrong("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit+del;
}; 
</script>
<h2 class="panel-heading clearfix">网络故障处理表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkWrongTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>工单编号</th>				<th>客户ID</th>				<th>客户名称</th>				<th>客户联系人</th>				<th>客户联系方式</th>				<th>操作开始时间</th>				<th>操作完成时间</th>				<th>故障类型</th>				<th>源IP地址</th>				<th>源IP地址是否禁Ping（是 、否）</th>				<th>目的IP地址</th>				<th>目的IP地址是否禁Ping（是 、否）</th>				<th>对照组源IP地址</th>				<th>对照组目的IP地址</th>				<th>对端所属运营商</th>				<th>丢包率</th>				<th>路由跟踪图可看到最后一跳地址-出森华方向</th>				<th>路由跟踪图可看到最后一跳地址-入森华方向</th>				<th>异常接口所在IP段或网关地址</th>				<th>异常流量方向：（IDC方向（服务器到公网）、ISP方向（公网到服务器））</th>				<th>是否需要进行IP地址排名分析</th>				<th>IP地址排名分析起始时间</th>				<th>IP地址排名分析结束时间</th>				<th>流量突降IP地址范围</th>				<th>接口问题：（上联接口有闪断记录、上联接口没查到问题）</th>				<th>问题IP地址</th>				<th>是否备案</th>				<th>问题发生时间</th>				<th>实际故障类型</th>				<th>故障排查结果汇报</th>				<th>工单类型（17.设备故障 18、网络故障）</th>				<th>工单类型图标名称（17.设备故障 18、网络故障）</th>				<th>机房</th>				<th>操作人员ID</th>				<th>操作人员姓名</th>				<th>处理状态</th>				<th>备注</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addButton" data-toggle="modal">新 增</a>
			<button class="btn " id="deleteToaNetworkWrongs" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="toaNetworkWrongModuleDiv"></div>
<script src='${sysPath}/js/com/machine/toaNetworkWrong/toaNetworkWrong.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>