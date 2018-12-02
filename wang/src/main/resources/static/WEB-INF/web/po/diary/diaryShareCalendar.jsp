<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<link href="${sysPath}/js/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css">
<link href="${sysPath}/js/fullcalendar/theme.css" rel="stylesheet" type="text/css">
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/fullcalendar/fullcalendar.js"></script>
<script src="${sysPath}/js/fullcalendar/jquery-ui-1.js"></script>
<script src="${sysPath}/js/com/po/diary/schedule.js"></script>
<script src="${sysPath}/js/com/po/diary/diaryShareCalendar.js"></script>
<script src="${sysPath}/js/com/po/diary/queryForTree.js"></script>
<script src="${sysPath}/js/com/po/diary/diary.validate.js"></script>
<script src="${sysPath}/js/com/common/remind.js"></script><!-- 周期性提醒 -->
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu"></div>
</header>
<section class="tree-fluid m-t-md">
	<aside class="tree-wrap" id="LeftHeight_box">
		<section class="panel" id="LeftHeight" style="overflow-y: auto;">
			<div class="panel-heading clearfix" id="header_2">
				<h2>选择共享人员</h2>
			</div>
			<div id="treeDemo" class="ztree"></div>
			<input type="hidden" id="userid" name="userid" value="<c:out value='${requestScope.userid}' default='0' escapeXml='false'></c:out>"/>
			<input type="hidden" id="diaryMenuFlag" name="diaryMenuFlag" value="3"/><!-- 1日程安排 2下属日程 3共享查询 4领导日程 -->
			<input type="hidden" id="diaryPageFlag" name="diaryPageFlag" value="1"/><!-- 1日历 2列表 -->
			<input type="hidden" id="startDateTime" name="startDateTime" />
			<input type="hidden" id="endDateTime" name="endDateTime" />
			<input type="hidden" id="tempRemind" name="tempRemind" />
			<input type="hidden" id="loginUser" name="loginUser" value="${requestScope.loginUserId}"/>
		</section>
	</aside>
	<section class="tree-right">
		<section class="panel m-t-md">
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
			<!-- <ul class="tip-color-box m-l m-b-md clearfix">
				<li><b class="bg-blue"></b>手写日程</li>
				<li><b class="bg-yellow"></b>日志导入</li>
				<li><b class="bg-green-dark"></b>待办任务导入</li>
				<li><b class="bg-green-lt"></b>工作计划导入</li>
				<li><b class="bg-blue-lt"></b>记事本导入</li>
			</ul> -->
		</section>
	</section>
</section>
</section>
<form id="diaryForm">
<div id="detailDiv"></div>
</form>
<!--  阅读情况弹窗-------->
<div class="modal fade panel" id="Reading" aria-hidden="false">
     <div class="modal-dialog w900">
         <div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal">×</button>
                 <h4 class="modal-title">阅读情况</h4>
             </div>
             <div class="modal-body">
                 <table class="table table-striped tab_height" id="readingStatusTable">
                     <thead>
                         <tr>
                            <th style="width:10%;">阅读人</th>
                            <th style="width:20%">阅读时间</th>
                        </tr>
                    </thead>
                    <tbody id="readingInnerTable">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer form-btn">
                <a class="btn dark" id="closeReading" data-dismiss="modal">关 闭</a> 
            </div>
        </div>
    </div>
</div> 
<script src="${sysPath}/js/common-tree.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>