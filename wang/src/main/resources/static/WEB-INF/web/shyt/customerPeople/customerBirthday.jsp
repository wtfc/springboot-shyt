<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>客户联系信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="customerPeopleQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>客户名称</td>
						<td>
					 		 <input type="text" id="companyName" name = "companyName"/>
					 		 <input type="hidden" id="extStr2" name = "extStr2" value="${month}"/>
					 		 <input type="hidden" id="extStr1" name = "extStr1" value="0"/>
						</td>
						<td>联系人</td>
						 <td>
						 	 <input type="text" id="name" name = "name"/>
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
	var read=null;
	if(source.extStr3==0){
	 read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/customerPeople/BirthdayForm.action?id="+source.id+"\" onclick=\"\">祝福</a>";
	}
	 return read ;
}; 
</script>
<h2 class="panel-heading clearfix">客户生日提醒</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="customerPeopleTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>月份</th>
				<th>客户名称</th>
				<th>联系人</th>
				<th>职务</th>
				<th>联系方式</th>
				<th>身份证号</th>
				<th>是否祝福</th>
				<th>祝福方式</th>
				<th>祝福时间</th>
				<th>产生费用</th>
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
<script src='${sysPath}/js/com/shyt/customerPeople/customerBirthday.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>