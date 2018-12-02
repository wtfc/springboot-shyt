<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>机房机柜表</h1>
			<div class="crumbs"></div>
		</div>
		<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="viewQueryForm" name="viewQueryForm">
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>机房名称</td>
						<td>
		                    <select id="contact" name = "contact" >
							  <option value="比目鱼机房">比目鱼机房</option>
							</select>
                      </td>
                      <td>机房区域</td>
				     <td>
						 <input type="text" id="machina" name = "machina"/>
					</td>
					</tr>
					<tr>
						<td>机柜编号</td>
						<td>
					 		<input type="text" id="machinaNumber" name = "machinaNumber"/>
						</td>
						<td>机柜类型</td>
                        <td><select id="type" name="type">
                      			<option value="">请选择</option>
                      			<option value="#CD0000">公司自用</option>
                      			<option value="#228B22">整包机柜</option>
                      			<option value="#CDCD00">散户机柜</option>
                      			<option value="#1C86EE">预留机柜</option>
                      			<option value="black">空机柜</option>
                       		</select>
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
	var buttonStr = "";
	
	var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"view.loadModuleForUpdate("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"view.deleteView("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	buttonStr = edit + del;
	return buttonStr ;
}; 
</script>
<h2 class="panel-heading clearfix">机房机柜表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="viewTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>机房名称</th>
				<th>机房区域</th>
				<th>机柜编号</th>
				<th>是否开通</th>
				<th>机柜容量</th>
				<th>设备数量</th>
				<th>机柜类型</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">新 增</a><button class="btn " id="deleteMachines" type="button">批量删除</button>
		</section>
	</section>
</section>
</section>
<div id="formModuleDiv" ></div>
<script src='${sysPath}/js/com/shjfgl/view/view.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>