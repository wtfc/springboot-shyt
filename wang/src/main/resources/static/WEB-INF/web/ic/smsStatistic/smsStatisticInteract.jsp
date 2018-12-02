<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in">
	    <div class="con-heading fl" id="navigationMenu">
	       <h1></h1>
	       <div class="crumbs"></div>
	    </div>
	</header>
	<!--start 查询条件 -->
	<section class="panel clearfix search-box search-shrinkage">
		<div class="search-line hide">
	    <form class="table-wrap form-table" id="statisticsForm">
	        <table class="table table-td-striped">
	            <tbody>
	                <tr>
	                    <td class="w140">发信人</td>
	                    <td style="width:40%;">
	                    	<div id="controlTree"></div>
	                    </td>
	                    <td class="w140">月份</td>
	                    <td>
	                        <div class="input-group w-p100">
	                            <input  class="datepicker-input" type="text" name="statisticsMonth" id="statisticsMonth" data-date-minviewmode="1" data-date-viewMode="1" data-date-format="yyyy-MM">
	                        </div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark query-jump" type="button" id="querystatistics">查 询</button>
	            <button class="btn" type="button" id="resetSearch">重 置</button>
	        </section>
	    </form>
	    </div>
	<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
	</section>  
	<!--end 查询条件 -->
	<!-- start 查询结果 -->
	<section class="panel clearfix">
	    <h2 class="panel-heading clearfix">
	        查询结果
	    </h2>
	    <div class="table-wrap">
	        <table class="table table-striped tab_height" id="statisticsTable">
	            <thead>
	                <tr>
	                    <th>发信人</th>
	                    <th>发送数量</th>
	                    <th>剩余数量</th>
	                </tr>
	            </thead>
	            <tbody>
	                
	            </tbody>
	        </table>
	    </div>
	</section>
</section>
<!-- end 查询结果 -->
<script src="${sysPath}/js/com/ic/smsStatistic/smsStatisticInteract.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>