<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>IDC人员入室审批表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="equipmentInOutQueryForm" name="equipmentInOutQueryForm">
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
					 		 <input type="text" id="companyName" name = "companyName"/> 
					 		 <input type="hidden" id="extStr1" name="extStr1" value="人员入室">
						</td>
						<td>机房</td>
						 <td>
						 	<select id="machina" name = "machina">
							  <option selected value ="">请选择</option>
							  <option value ="鲁谷机房">鲁谷机房</option>
							  <option value="兆维机房">兆维机房</option>
							  <option value="看丹桥机房">看丹桥机房</option>
							  <option value="洋桥机房">洋桥机房</option>
							  <option value="清华园机房">清华园机房</option>
							  <option value="沙河机房">沙河机房</option>
							  <option value="廊坊机房">廊坊机房</option>
							</select>
						 </td>
					</tr>
					<tr>
						<td>来源</td>
						<td>
							<select id="origin" name = "origin">
							  <option selected value ="">请选择</option>
							  <option value ="客户">客户</option>
							  <option value="销售">销售</option>
							  <option value="技术">技术</option>
							</select>
						</td>
						<td>类型</td>
						 <td>
						 	<select id="type" name = "type">
							  <option selected value ="">请选择</option>
							  <option value ="维护">维护</option>
							  <option value="参观">参观</option>
							</select>
						 </td>
					</tr>
					<tr>
						<td>处理状态</td>
						<td>
							<select id="status" name = "status">
							  <option selected value ="">请选择</option>
							  <option value ="0">待接单</option>
							  <option value="1">已接单</option>
							  <option value="2">操作完成</option>
							  <option value="3">作废</option>
							  <option value="4">工单完结</option>
							</select>
						</td>
						<td>入室时间</td>
						<td><input class="datepicker-input" type="text" id="intDate" name="intDate" data-pick-time="true" data-date-format="yyyy-MM-dd" /></td>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/machine/equipmentInOut/managePeople.action?id="+source.id+"&status=3\" onclick=\"\">查看详细</a>";
	return read ;
}; 
</script>
<h2 class="panel-heading clearfix">工单查询</h2>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="equipmentInOutTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>客户名称</th>
				<th>机 房</th>
				<th>来 源</th>
				<th>类型</th>
				<th>入室日期</th>
				<th>下单人</th>
				<th>处理阶段</th>
				<!--
				<th>设备数量</th>
				<th>实施情况</th>
				<th>实施人</th>
				<th>施工负责人</th>
				<th>客户满意度</th>
				<th>回访人</th> -->
				<th>操作</th>
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
<div id="formModuleDiv" ></div>
<script src='${sysPath}/js/com/shjfgl/equipmentInOutPeople/peopleInOutRead.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>