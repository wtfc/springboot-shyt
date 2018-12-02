<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/diary/schedule.js"></script>
<script src="${sysPath}/js/com/po/diary/diaryShareList.js"></script>
<script src="${sysPath}/js/com/po/diary/queryForTree.js"></script>
<script src="${sysPath}/js/com/po/diary/diary.validate.js"></script>
<script src="${sysPath}/js/com/common/remind.js"></script><!-- 周期性提醒 -->
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" id="navigationMenu"></div>
</header>
<section class="tree-fluid m-t-md">
     <aside class="tree-wrap" id="LeftHeight_box_rl">
        <div class="datepicker-input load-show" data-callback="schedule.leftCallback" id="datePicker"></div> <!-- 日历在树上的时候 固定放在aside和section之间-->
        <section class="panel clearfix" id="LeftHeight_rl" style="overflow-y: auto;"><!--所有树固定加  clearfix -->
            <div class="panel-heading clearfix" id="header_2">
                <h2>选择共享人员</h2>
            </div>
            <div id="treeDemo" class="ztree"></div>
            <input type="hidden" id="userid" name="userid" value="<c:out value='${requestScope.userid}' default='0' escapeXml='false'></c:out>"/>
            <input type="hidden" id="diaryMenuFlag" name="diaryMenuFlag" value="3"/><!-- 1日程安排 2下属日程 3共享查询 4领导日程 -->
			<input type="hidden" id="diaryPageFlag" name="diaryPageFlag" value="2"/><!-- 1日历 2列表 -->
			<input type="hidden" id="startDateTime" name="startDateTime" />
			<input type="hidden" id="endDateTime" name="endDateTime" />
			<input type="hidden" id="delId"/>
			<input type="hidden" id="tempRemind" name="tempRemind" />
			<input type="hidden" id="loginUser" name="loginUser" value="${requestScope.loginUserId}"/>
        </section>
    </aside>
    <section class="tree-right clearfix">
        <section class="panel">
            <div class="table-wrap form-table">
            <section class="form-btn m-b-lg m-l">
                 <a class="btn dark" href="#Reading" data-toggle="modal" id="readingStatus">阅读情况</a>
                 <a href="#" class="btn" id="calendarView">日历形式展示</a> 
            </section>
                <table class="table table-td-striped brn">
                    <tbody>
                        <tr>
                        <td class="w140">选择日程</td>
                        <td colspan="3">
                            <div class="input-group w-p100">
                                <div class="btn-in-area"  id="chooseDiary">
                                    <select id="oneDayDiaryList" onchange="schedule.loadDiary()">
										<option value="">-请选择-</option>
										<c:forEach items="${requestScope.diaryList}" var="diaryInstance" >
											<option value="${diaryInstance.id}">${diaryInstance.title}</option>
										</c:forEach>
									</select>
                                </div>
                            </div>
                        </td>
                    </tr>
                   </tbody>
                </table>
            </div>
        </section>
        <section class="panel m-t-md">
            <div class="table-wrap form-table">
                <table class="table table-td-striped">
                    <tbody>
                       <tr>
							<td>日程标题</td>
							<td colspan="3" id="detailTitle" style="word-break: break-all;word-wrap: break-word;"></td>
						</tr>
						<tr>
							<td style="width:15%;">开始时间</td>
							<td id="detailStartTime" style="width:35%;"></td>
							<td style="width:15%;">结束时间</td>
							<td id="detailEndTime"></td>
						</tr>
						<tr>
							<td>周期模式</td>
							<td colspan="3" id="detailPeriodTypeStartEndDate"></td>
						</tr>
						<tr>
							<td>提醒设置</td>
							<td colspan="3" id="detailRemind"></td>
						</tr>
						<tr>
							<td>共享范围</td>
							<td colspan="3" id="detailShare"  style='word-break: break-all;word-wrap: break-word;'></td>
						</tr>
						<tr>
							<td>日程内容</td>
							<td colspan="3" id="detailContent" style="word-break: break-all;word-wrap: break-word;"></td>
						</tr>
						<!-- <tr>
							<td>提醒方式</td>
							<td id="detailRemind"></td>
						</tr> -->
                    </tbody>
                </table>
        </div>
            <h2 class="modal-heading clearfix" id="leaderIdeaTitle">领导批注</h2>
            <input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/>
            <form id="diaryForm">
			<ul class="dialog m-r m-l m-t" id="comment">
			</ul> 
			</form>
			<!-- <form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap">
            <table class="table table-td-striped">
                <tbody>
                 	<tr>
                    <td>批注内容</td>
                    <td colspan="3">
                        <div class="input-group w-p100">
                            <div class="btn-in-area">
                                 <textarea id="leaderIdeaContent" name="content" rows="3"></textarea>
                            </div>
                            <div class="input-group-btn icon p-l">
                                <a id="leaderIdea" id="leaderIdea" href="#" class="a-icon i-new m-b-xs">
                                	<i class="fa fa-comment icon m-r-sm fl"></i>保 存</a>
                                <a class="a-icon i-trash fr" href="#" id="clearleaderIdea"><i class="fa fa-trash" ></i>清 空</a>
                            </div>
                        </div>
                    </td>
                </tr>   
                </tbody>
            </table>
           </form> -->
            
        </section>
    </section>
</section>
</section>
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
                <a class="btn dark" id="closeReading" data-dismiss="modal" onclick="diaryShareList.refreshTreeForList();">关 闭</a> 
            </div>
        </div>
    </div>
</div> 
<%@ include file="/WEB-INF/web/include/foot.jsp"%>