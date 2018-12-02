<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>客户IP地址统计表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaNetworkIpaddressQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
							<input type = "text" id="companyName" name="companyName" />
						</td>
						<td>地址类型</td>
						<td>
							<select style="width:100%;"id="addressType" name="addressType" >
								<option value="">请选择</option>
								<option value="0">单线</option>
								<option value="1">多线</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>机房</td>
						<td>
							<dic:select  id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
						</td>
						<td>设备</td>
						<td>
							<input type="text"id="equipment" name="equipment" />
						</td>
					</tr>
					<tr>
						<td>IP地址段开始</td>
						<td>
							<input type="text"id="ipOne" name="ipOne" />
						</td>
						<td>IP地址段结束</td>
						<td>
							<input type="text"id="ipTwo" name="ipTwo" />
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/network/toaNetworkIpaddress/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"toaNetworkIpaddress.deleteToaNetworkIpaddress("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del;
}; 
</script>
<h2 class="panel-heading clearfix">客户IP地址统计表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkIpaddressTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>客户名称</th>				<th>地址<br>类型</th>				<th>机房</th>				<th>设备</th>				<th>IP地址<br>段开始</th>				<th>IP地址<br>段结束</th>				<th>IP<br>数量</th>
				<th>端口属性</th>				<th>设备端口</th>				<th>带宽</th>				<th>操作工程师</th>				<th>操作时间</th>				<th>分配时间</th>				<th>地址<br>总数</th>				<th>使用地<br>址总数</th>				<th>空闲地<br>址总数</th>				<th>使用率</th>				<th>空闲率</th>				<th>操作</th>
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
<script src='${sysPath}/js/com/network/toaNetworkIpaddress/toaNetworkIpaddress.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>