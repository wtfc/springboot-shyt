<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>重启操作表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaMachineRestartEngineQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            	<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>
						<td>
							<input type = "text" id="companyName" name="companyName" />
							<input type="hidden" id="status" name="status" value="4" />
						</td>
						<td style="width:10%;" class=" b-green-dark b-tc">所属机房</td>
						<td>
							<dic:select id="machina" name="machina" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
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
	 var read="";
	 var See="";
		if(source.status==4){
			read = '<shiro:hasPermission name="user:updateMachineRestart"><a class="a-icon i-new m-r-xs" href=\"#\" onclick="toaMachineRestartEngine.toaMachineRestartEngineUpdate('+ source.id+')">打分</a></shiro:hasPermission>'; 
		}else{
			See = "<a class=\"a-icon i-edit m-r-xs\" href=\""+getRootPath()+"/machine/toaMachineRestart/loadSee.action?id="+source.id+"\" onclick=\"\" style=\"padding:0px 5px;\">查看</a>"; 
		}
	return read+See;
}; 
</script>
<h2 class="panel-heading clearfix">重启操作</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaMachineRestartEngineTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>工单编号</th>				<th>客户名称</th>				<th>所属机房</th>				<!-- <th>客户联系人</th>				<th>客户联系方式</th> -->				<th>IP地址（机柜）</th>				<th>操作工程师</th>				<th>操作时间</th>				<th>完成时间</th>
				<th>工单状态</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/machine/toaMachineRestart/toaMachineRestartEngine.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>