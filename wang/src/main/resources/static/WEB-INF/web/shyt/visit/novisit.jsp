<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>客户回访信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="visitQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
						<td>公司名称</td>
						<td>
					 		 <input type="text" id="companyName" name = "companyName"/>
					 		<!--  <input type="hidden" id="visitIsReturn" name = "visitIsReturn" value="否"/> -->
						</td>
						<td>回访类型</td>
						 <td>
						 	<select id="visitStatus" name="visitStatus" style="width:100%;">
									<option value="">请选择</option>
									<option value="新签约">新签约</option>
									<option value="目标客户">目标客户</option>
									<option value="客户联系人员变动">客户联系人员变动</option>
									<option value="故障">故障</option>
									<option value="满意度">满意度</option>
									<option value="投诉">投诉</option>
									<option value="日常关系维护">日常关系维护</option>
							</select>
						 </td>
					</tr>
					<tr>
						<td>回访日期</td>
						<td>
							<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  type="text" name = "visitDate" id="visitDate" />
						</td>
						<td>回访是否完成</td>
						<td>
						 	<select style="width:100%;"id="visitIsReturn" name ="visitIsReturn">
								<!-- <option value="是">是</option> -->
								<option selected value="否">否</option>
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
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/visit/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>";
	return read;
}; 
</script>
<h2 class="panel-heading clearfix">回访信息</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="visitTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>公司名称</th>
				<th>回访类型</th>
				<th>回访日期</th>
				<th>回访是否完成</th>
				<th>费用(元)</th>
				<!-- <th>回访内容</th>
				<th>回访反馈</th> -->
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
<script src='${sysPath}/js/com/shyt/visit/novisit.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>