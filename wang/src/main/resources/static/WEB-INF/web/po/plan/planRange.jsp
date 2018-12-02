<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/plan/planRange.js"></script>
<script src="${sysPath}/js/com/common/remind.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="navigationMenu"></header>
	
	<section class="panel m-t-md">
	    <h2 class="panel-heading clearfix">范围设定<small class="m-l"><i class="fa fa-question-sign text-red m-r-xs"></i>本功能用于设定周/月/年计划编制范围，在范围内的人员可编写相应的工作计划</small></h2>
	    <form id="planRangeForm" name="planRangeForm" class="table-wrap form-table">
	    		<input id="updateHid"  name="updateHid" type="hidden" value="0"> 
	            <table class="table table-td-striped">
	                <tbody>
	                    <tr>
	                        <td class="w240">周计划编制范围</td>
	                        <td>
								<div id="showWeekTree" name="showWeekTree"></div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="w240">月计划编制范围</td>
	                        <td>
	                            <div id="showMonthTree" name="showMonthTree"></div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="w240">年计划编制范围</td>
	                        <td>
	                            <div id="showYearTree" name="showYearTree"></div>
	                        </td>
	                    </tr>
	                </tbody>
	            </table>
	    </form>
	</section>
	  
	<section class="panel m-t-md">
	    <h2 class="panel-heading clearfix">提醒设定<small class="m-l"><i class="fa fa-question-sign text-red m-r-xs"></i>本功能用于提醒计划编制人进行计划编制的时间期限</small></h2>
	    <div class="table-wrap form-table">
	        <table class="table table-td-striped">
	            <tbody>
	                <tr>
	                    <td class="w240">周计划编写提醒设定</td>
	                    <td>
	                    	<a id="weekRemind" name="weekRemind" class="a-icon i-new m-r-xs m-l" href="#" role="button" data-toggle="modal">
	                    		<i class="fa fa-alarm"></i>周计划编写提醒设置
	                    	</a>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="w240">月计划编写提醒设定</td>
	                    <td>
	                    	<a id="monthRemind" name="monthRemind" class="a-icon i-new m-r-xs m-l" href="#" role="button" data-toggle="modal">
	                    		<i class="fa fa-alarm"></i>月计划编写提醒设置
	                    	</a>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="w240">年计划编写提醒设定</td>
	                    <td>
	                    	<a id="yearRemind" name="yearRemind" class="a-icon i-new m-r-xs m-l" href="#" role="button" data-toggle="modal">
	                    		<i class="fa fa-alarm"></i>年计划编写提醒设置
	                    	</a>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
	</section>
	 
	<section class="form-btn m-b-lg">
	    <button id="saveRangeBtn" class="btn dark ok" type="button">保 存</button>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>