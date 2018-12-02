<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<link href="${sysPath}/js/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css">
<link href="${sysPath}/js/fullcalendar/theme.css" rel="stylesheet" type="text/css">
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/fullcalendar/fullcalendar.js"></script>
<script src="${sysPath}/js/fullcalendar/jquery-ui-1.js"></script>
<script src="${sysPath}/js/com/common/remind.js"></script><!-- 周期性提醒 -->
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/diary/schedule.js"></script>
<script src="${sysPath}/js/com/po/diary/diaryMyCalendar.js"></script>
<script src="${sysPath}/js/com/po/diary/diary.validate.js"></script>
<script src="${sysPath}/js/com/po/worklog/common.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var obj = document.getElementById("scrollable").parentNode;
		obj.ondragstart = function() {return true;}
	});	
</script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu"></div>
</header>
<section class="tree-fluid m-t-md">
	<!-- <aside class="tree-wrap w203" id="LeftHeight_box_rc" >
		<section class="panel" id="LeftHeight_rc">
			<div class="table-wrap notation-btn">
				<table class="table table-link first-striped">
					<thead>
						<tr>
							<th></th>
							<th>日</th>
							<th>周</th>
							<th>月</th>
						</tr>
					</thead>
					<tr>
						<td>写</td>
						<td><a id="writeWorklogBtn" name="writeWorklogBtn" class="btn a-icon i-new"
							 data-toggle="tooltip" data-placement="top"
							 title="" data-container="body" data-original-title="日志"><i class="fa fa-compose"></i>
							 </a>
					    </td>
						<td><a id="writeWeekPlanBtn" name="writeWeekPlanBtn" class="btn a-icon i-new" data-toggle="tooltip" 
							   data-placement="top" title="" data-container="body" data-original-title="周计划">
							   <i class="fa fa-compose"></i>
							</a>
						</td>
						<td><a id="writeMonthPlanBtn" name="writeMonthPlanBtn" class="btn a-icon i-new" data-toggle="tooltip" data-placement="top" title="" 
							   data-container="body" data-original-title="月计划">
							   <i class="fa fa-compose"></i>
							</a>
						</td>
					</tr>
					<tr>
						<td>看下属</td>
						<td><a id="underlingWorklogBtn" name="underlingWorklogBtn" class="btn a-icon i-new"
								data-toggle="tooltip" data-placement="top" title=""
								data-container="body" data-original-title="日志">
								<i class="fa fa-table"></i>
							</a>
						</td>
						<td><a id="underlingWeekPlanBtn" name="underlingWeekPlanBtn" class="btn a-icon i-new" 
								data-toggle="tooltip" data-placement="top" title=""
								data-container="body" data-original-title="周计划">
								<i class="fa fa-table"></i>
							</a>
						</td>
						<td><a id="underlingMonthPlanBtn" name="underlingMonthPlanBtn" class="btn a-icon i-new" 
								data-toggle="tooltip" data-placement="top" title=""
								data-container="body" data-original-title="月计划">
								<i class="fa fa-table"></i>
							</a>
						</td>
					</tr>
					<tr>
						<td>上级批注</td>
						<td><a id="worklogAnnoBtn" name="worklogAnnoBtn" class="btn a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="日志"></a></td>
						<td><a id="weekPlanAnnoBtn" name="weekPlanAnnoBtn" class="btn a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="周计划"></a></td>
						<td><a id="monthPlanAnnoBtn" name="monthPlanAnnoBtn" class="btn a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="月计划"></a></td>
					</tr>
					<tr>
						<td>提交情况</td>
						<td><a id="worklogConditionBtn" name="worklogConditionBtn" class="btn a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="日志">
							<i class="fa fa-menu"></i></a></td>
						<td><a id="weekPlanConditionBtn" name="weekPlanConditionBtn" class="btn a-icon i-new" 
							   data-toggle="tooltip" data-placement="top" title=""
							   data-container="body" data-original-title="周计划">
							   <i class="fa fa-menu"></i>
							</a>
						</td>
						<td><a id="monthPlanConditionBtn" name="monthPlanConditionBtn" class="btn a-icon i-new" 
							   data-toggle="tooltip" data-placement="top" title=""
							   data-container="body" data-original-title="月计划">
							   <i class="fa fa-menu"></i>
							</a>
						</td>
					</tr>
					<tr>
						<td>汇总</td>
						<td><a id="collect" href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="日志" >
							<i class="fa fa-list2"></i></a></td>
						<td><a id="weekPlanCollectBtn" name="weekPlanCollectBtn" class="btn a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="周计划">
							<i class="fa fa-list2"></i></a></td>
						<td><a id="monthPlanCollectBtn" name="monthPlanCollectBtn" class="btn a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="月计划">
							<i class="fa fa-list2"></i></a></td>
					</tr>
				</table>
			</div>
		</section>
	</aside> -->
	<section class="tree-right  clearfix">
		<section class="panel">
			<header class="panel-heading clearfix">
				<div class="btn-group" data-toggle="buttons">
					<label class="btn m-r-xs" id="monthview">
						<input type="radio" name="options" id="option1">月</label> 
					<label class="btn m-r-xs" id="weekview">
						<input type="radio" name="options" id="option2">周</label> 
					<label class="btn m-r-xs" id="dayview">
						<input type="radio" name="options" id="option3">日</label> 
					<label class="btn m-r-xs active" id="today">
						<input type="radio" name="options" id="option4">今日</label>
				</div>
				<c:if test="${requestScope.agentBtn=='1'}">
					<a class="btn" role="button" data-toggle="modal" id="diaryAgent">日程委托</a> 
				</c:if>
				<a class="btn" id="diarySummary" data-toggle="modal">日程汇总</a>
				<a class="btn" id="listView">列表形式展示</a>
				<section class="fr">
					<form role="search" id="searchFullCalendar">
						<div class="input-append m-b-none">
							<input type="text" placeholder="快速查询..." id="searchtime" name="searchtime"
								class="datepicker-input" data-date-format="yyyy-MM-dd">
							<a class="btn" id="search">
								<i class="fa fa-search"></i>
							</a>
						</div>
					</form>
				</section>
			</header>
			<div class="calendar fc fc-ltr table-wrap" id="calendar"></div>
			<ul class="tip-color-box m-l m-b-md clearfix">
				<li><b class="bg-blue"></b>普通日程</li>
				<!--<li><b class="bg-yellow"></b>日志导入</li>
				<li><b class="bg-green-dark"></b>待办任务导入</li>
				<li><b class="bg-green-lt"></b>工作计划导入</li>
				<li><b class="bg-blue-lt"></b>记事本导入</li>-->
				<li><b class="bg-yellow"></b>已共享日程</li>
			</ul>
		</section>
	</section>
</section>
</section>
<div id="diaryDelegation"></div>
<div id="worklogSummary">
<!--  汇总弹窗-------->
<div class="modal fade panel" id="worklog_summary" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">日志汇总</h4>
            </div>
            <div class="modal-body">
                <form class="form-table" id="collectForm">
                    <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td class="w140">开始时间</td>
                                <td>
	                                <input type="hidden" id="startDateTemp" name="startDateTemp"/>
	                                <input class="datepicker-input" type="text" id="worklogDateBegin"  data-ref-obj="#worklogDateEnd lt" name="worklogDateBegin"></td>
                            	<td class="w140">结束时间</td>
                            	<td>
	                            	<input type="hidden" id="endDateTemp" name="endDateTemp"/>
	                            	<input class="datepicker-input" type="text" id="worklogDateEnd" data-ref-obj="#worklogDateBegin gt" name="worklogDateEnd">
	                            </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
                <section class="form-btn m-b-lg m-t-md">
                    <button class="btn dark" type="button" id="collectSearch">查 询</button>
                    <button class="btn" type="button" id="collectReset">重 置</button>
                </section>
                <table class="table table-striped tab_height" id="collectTable">
                    <thead>
                        <tr>
                            <th class="w140">日志日期</th>
                            <th>内容</th>
                            <th>感悟及备注</th>
                            <th class="w100">批注</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" id="exportExcel">导出至excel</button>
                <a class="btn" id="closeWorklogSummary" data-dismiss="modal">关 闭</a> 
            </div>
        </div>
    </div>
</div>
</div>
<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 begin-->
<input type="hidden" id="tempPeriodTypeTmp"   name="tempPeriodTypeTmp" />
<input type="hidden" id="tempPeriodStartdate" name="tempPeriodStartdate" />
<input type="hidden" id="tempPeriodEnddate"   name="tempPeriodEnddate" />
<input type="hidden" id="tempPeriodWay"   name="tempPeriodWay" />
<input type="hidden" id="tempPeriodWayForModify"   name="tempPeriodWayForModify"/>
<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 end-->
<input type="hidden" id="diaryMenuFlag" name="diaryMenuFlag" value="1"/><!-- 1日程安排 2下属日程 3共享查询 4领导日程 -->
<input type="hidden" id="diaryPageFlag" name="diaryPageFlag" value="1"/><!-- 1日历 2列表 -->
<input type="hidden" id="startDateTime" name="startDateTime" />
<input type="hidden" id="endDateTime" name="endDateTime" />
<input type="hidden" id="userid" name="userid" value="<c:out value='${requestScope.userid}' default='0' escapeXml='false'></c:out>"/>
<input type="hidden" id="tempRemind" name="tempRemind" />
<!-- 新建&编辑日程 -->
<form id="diaryForm">
<input type="hidden" id="id" name="id" value="0"/> 
<input type="hidden" id="possessorId" name="possessorId"/> 
<input type="hidden" id="businessId" name="businessId"/> 
<input type="hidden" id="modifyDate" name="modifyDate"/>
<input type="hidden" id="millisecond" name="millisecond"/>
<input type="hidden" id="periodType" name="periodType" value="0"/>
<input type="hidden" id="periodWay" name="periodWay" value="0"/>
<input type="hidden" id="moduleFlag" name="moduleFlag" value="0"/>
<input type="hidden" id="diaryType" name="diaryType" value="0"/>
<input type="hidden" id="modifyFlag" name="modifyFlag"/><!-- 0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件） -->
<input type="hidden" id="newOrModify" name="newOrModify"/><!-- 0 新建 1 修改 -->
<input type="hidden" id="newOk" name="newOk"/><!-- 0 没点击过确定 1 点击过确定 -->
<input type="hidden" id="modifyExistValue" name="modifyExistValue"/><!-- 0 修改有值 1 修改没有值 -->
<input type="hidden" id="tmpMaster" name="tmpMaster"/> 
<input type="hidden" id="tmpPeriodType" name="tmpPeriodType"/>
<input type="hidden" id="loginUser" name="loginUser" value="${requestScope.loginUserId}"/>
<div id="diaryDiv"></div>
<div id="detailDiv"></div>
<div id="periodDiv"></div>
<div id="periodModifyInfoDiv"></div>
</form>
<div id="diarySummary">
<!--  start 日程汇总 弹出层-------->
<div class="modal fade panel" id="Program-summary" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">日程汇总</h4>
            </div>
            <div class="modal-body">
                <div class="form-table">
                    <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td class="w140">日程时间</td>
                                <td>
                                    <div class="input-group w-p100">
                                    	<input type="hidden" id="startDateTemp" name="startDateTemp"/>
                                    	<input type="hidden" id="endDateTemp" name="endDateTemp"/>
                                        <input class="datepicker-input" id="starttimeBegin" name="starttimeBegin" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#starttimeEnd lt"><div class="input-group-btn w30"> -</div><input class="datepicker-input" id="starttimeEnd" name="starttimeEnd" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#starttimeBegin gt">
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <section class="form-btn m-b-lg m-t-md">
                    <a class="btn dark" id="searchDiarySummary">查 询</a>
                    <a class="btn" id="searchDiarySummaryReset">重 置</a>
                </section>
                <table class="table table-striped tab_height" id="summaryTable">
                    <thead>
                        <tr>
                            <th style="width:10%;">所属人</th>
                            <th style="width:40%">日程时间</th>
                            <th style="width:40%">日程内容</th>
                            <th style="width:10%;">批注</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer form-btn">
            	<form id="excelForm" method="post">
                <a class="btn dark" id="exportExcelDiary">导出至excel</a>
                <a class="btn" id="closeSummary" data-dismiss="modal">关 闭</a> 
                </form>
            </div>
        </div>
    </div>
</div>
<!--  end 日程汇总 弹出层-------->
</div>
<!--查看领导批注 start-->
<div class="modal fade panel" id="worklog-anno" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="diaryMyCalendar.closeAnno();">×</button>
                <h4 class="modal-title">查看领导批注</h4>
            </div>
            <div class="modal-body">
                <form id="commentForm">
                <ul class="dialog m-t" id="annoComment">
            	</ul>
            	</form>  
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" data-dismiss="modal" onclick="diaryMyCalendar.closeAnno();">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--查看领导批注 end-->
<div id="summaryAnno"></div>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>