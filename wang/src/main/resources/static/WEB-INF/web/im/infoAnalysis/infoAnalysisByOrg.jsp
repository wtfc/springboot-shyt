<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<script src="${sysPath}/js/lib/echarts/echarts-plain.js" type="text/javascript"></script>
<script src="${sysPath}/js/lib/echarts/eccommon.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/im/infoAnalysis/recordutil.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/im/infoAnalysis/infoAnalysisByOrg.js" type="text/javascript"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
	    <div class="con-heading fl" id="navigationMenu">
	        <h1></h1>
	        <div class="crumbs"></div>
	    </div>
	</header>
	
	<section class="panel m-t-md clearfix">
		<form id="infoAnalysisByOrgForm">
			<div class="table-wrap formFiled">
				统计时间<input type="text" id="begin_time" name="begin_time" readonly="readonly" class="datepicker-input" data-date-format="yyyy-MM-dd" data-ref-obj="#end_time lt" /> 
				<span class="input-group-btn w30">-</span>
				<input type="text" id="end_time" name="end_time" readonly="readonly" class="datepicker-input" data-date-format="yyyy-MM-dd" data-ref-obj="#begin_time gt" />
				<button class="btn dark " type="button" id="getButton">统 计</button>
			</div>
		</form>
		<div class="table-wrap">
			<div id="queryInfoAnalysisByOrg_div">
			</div>
		</div>
		<div class="table-wrap tab-content">
			<table class=" formTable">
				<tbody id="queryInfoAnalysisByOrg_Content">
				</tbody>
			</table>
		</div>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>