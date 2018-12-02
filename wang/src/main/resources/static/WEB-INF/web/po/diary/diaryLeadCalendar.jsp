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
<script src="${sysPath }/js/com/common/remind.js"></script><!-- 周期性提醒 -->
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/diary/schedule.js"></script>
<script src="${sysPath}/js/com/po/diary/diaryLeadCalendar.js"></script>
<script src="${sysPath}/js/com/po/diary/queryForTree.js"></script>
<script src="${sysPath}/js/com/po/diary/diary.validate.js"></script>
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
	<aside class="tree-wrap" id="LeftHeight_box">
		<section class="panel" id="LeftHeight" style="overflow-y: auto;">
			<div class="panel-heading clearfix" id="header_2">
				<h2>选择领导</h2>
			</div>
			<div id="treeDemo" class="ztree"></div>
			<input type="hidden" id="diaryMenuFlag" name="diaryMenuFlag" value="4"/><!-- 1日程安排 2下属日程 3共享查询 4领导日程 -->
			<input type="hidden" id="diaryPageFlag" name="diaryPageFlag" value="1"/><!-- 1日历 2列表 -->
			<input type="hidden" id="mandatorId" name="mandatorId" value="0"/><!-- 委托人(领导) -->
			<input type="hidden" id="userid" name="userid" value="<c:out value='${requestScope.userid}' default='0' escapeXml='false'></c:out>"/> 
        	<input type="hidden" id="startDateTime" name="startDateTime" />
			<input type="hidden" id="endDateTime" name="endDateTime" />
			<input type="hidden" id="tempRemind" name="tempRemind" />
			<input type="hidden" id="loginUser" name="loginUser" value="${requestScope.loginUserId}"/>
			<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 begin-->
			<input type="hidden" id="tempPeriodTypeTmp"   name="tempPeriodTypeTmp" />
			<input type="hidden" id="tempPeriodStartdate" name="tempPeriodStartdate" />
			<input type="hidden" id="tempPeriodEnddate"   name="tempPeriodEnddate" />
			<input type="hidden" id="tempPeriodWay"   name="tempPeriodWay" />
			<input type="hidden" id="tempPeriodWayForModify"   name="tempPeriodWayForModify"/>
			<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 end-->
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
			<!--<ul class="tip-color-box m-l m-b-md clearfix">
				<li><b class="bg-blue"></b>手写日程</li>
				<li><b class="bg-yellow"></b>日志导入</li>
				<li><b class="bg-green-dark"></b>待办任务导入</li>
				<li><b class="bg-green-lt"></b>工作计划导入</li>
				<li><b class="bg-blue-lt"></b>记事本导入</li>
			</ul>-->
		</section>
	</section>
</section>
</section>
<form id="diaryForm">

<!-- 新建&编辑日程 -->
<input type="hidden" id="id" name="id" value="0"/> 
<input type="hidden" id="possessorId" name="possessorId"/> 
<input type="hidden" id="businessId" name="businessId"/> 
<input type="hidden" id="modifyDate" name="modifyDate"/>
<input type="hidden" id="millisecond" name="millisecond"/>
<input type="hidden" id="periodType" name="periodType" value="0"/>
<input type="hidden" id="periodWay" name="periodWay" value="0"/>
<input type="hidden" id="moduleFlag" name="moduleFlag" value="0"/>
<input type="hidden" id="diaryType" name="diaryType" value="0"/>
<input type="hidden" id="modifyFlag" name="modifyFlag"/>
<input type="hidden" id="newOrModify" name="newOrModify"/><!-- 0 新建 1 修改 -->
<input type="hidden" id="newOk" name="newOk"/><!-- 0 没点击过确定 1 点击过确定 -->
<input type="hidden" id="modifyExistValue" name="modifyExistValue"/><!-- 0 修改有值 1 修改没有值 -->
<input type="hidden" id="tmpMaster" name="tmpMaster"/> 
<input type="hidden" id="tmpPeriodType" name="tmpPeriodType" />
<div id="diaryDiv"></div>
<div id="detailDiv"></div>
<div id="periodDiv"></div>
<div id="periodModifyInfoDiv"></div>
</form>
<script src="${sysPath}/js/common-tree.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
