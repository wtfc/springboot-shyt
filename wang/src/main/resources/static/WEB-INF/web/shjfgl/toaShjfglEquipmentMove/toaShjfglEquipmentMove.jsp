<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>设备搬迁表表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaShjfglEquipmentMoveQueryForm" >
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
 	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaShjfglEquipmentMove.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaShjfglEquipmentMove.deleteToaShjfglEquipmentMove("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return edit+del;
}; 
</script>
<h2 class="panel-heading clearfix">设备搬迁表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaShjfglEquipmentMoveTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>工单编号</th>				<th>设备ID</th>				<th>客户ID</th>				<th>客户名称</th>				<th>客户联系人</th>				<th>客户联系方式</th>				<th>机柜</th>				<th>IP</th>				<th>SN</th>				<th>品牌型号</th>				<th>机房</th>				<th>操作人员ID</th>				<th>操作人员姓名</th>				<th>处理状态</th>				<th>到达设备现场时间</th>				<th>操作开始时间</th>				<th>操作完成时间</th>				<th>下架到达设备时间</th>				<th>下架完成时间</th>				<th>上架到达设备时间</th>				<th>上架完成时间</th>				<th>操作时间</th>				<th>设备存放位置</th>				<th>网线连接要求</th>				<th>网线使用类型</th>				<th>是否安装系统</th>				<th>是否配置IP</th>				<th>IP配置要求</th>				<th>是否远程关机</th>				<th>下架后要求（暂存，代发）</th>				<th>机柜空间是否满足</th>				<th>机柜电力是否满足</th>				<th>设备信息核对结果</th>				<th>关机操作结果</th>				<th>下架设备清单</th>				<th>设备正常运行</th>				<th>是否存在后续操作</th>				<th>后置操作内容</th>				<th>工单类型（17.设备故障 18、网络故障）</th>				<th>工单类型图标名称（17.设备故障 18、网络故障）</th>				<th>备注</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addButton" data-toggle="modal">新 增</a>
			<button class="btn " id="deleteToaShjfglEquipmentMoves" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="toaShjfglEquipmentMoveModuleDiv"></div>
<script src='${sysPath}/js/com/machine/toaShjfglEquipmentMove/toaShjfglEquipmentMove.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>