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
						
						<td>机房</td>
						<td>
							<dic:select cssStyle="width:50%;" id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>
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

<h2 class="panel-heading clearfix">客户IP地址统计表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaNetworkIpaddressTable">
		<thead>
			<tr>				<th>机房</th>				<th>IP地址<br>段开始</th>				<th>IP地址<br>段结束</th>				<th>IP<br>数量</th>				<th>设备端口</th>				<th>带宽</th>				<th>操作工程师</th>				<th>操作时间</th>				<th>分配时间</th>			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			
		</section>
	</section>
</section>
</section>
<script src='${sysPath}/js/com/customer/netResource/toaNetResourceIpaddress/toaNetResourceIpaddress.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>