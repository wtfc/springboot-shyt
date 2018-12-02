<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/common/remind.js"></script><!-- 周期性提醒 -->
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/diary/schedule.js"></script>
<script src="${sysPath}/js/com/po/diary/diaryMyList.js"></script>
<script src="${sysPath}/js/com/po/diary/diary.validate.js"></script>
<script src="${sysPath}/js/com/po/worklog/common.js"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu"></div>
</header>
<input type="hidden" id="diaryMenuFlag" name="diaryMenuFlag" value="1"/><!-- 1日程安排 2下属日程 3共享查询 4领导日程 -->
<input type="hidden" id="diaryPageFlag" name="diaryPageFlag" value="2"/><!-- 1日历 2列表 -->
<input type="hidden" id="startDateTime" name="startDateTime" />
<input type="hidden" id="endDateTime" name="endDateTime" />
<input type="hidden" id="userid" name="userid" value="<c:out value='${requestScope.userid}' default='0' escapeXml='false'></c:out>"/>
<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 begin-->
<input type="hidden" id="tempPeriodTypeTmp"   name="tempPeriodTypeTmp" />
<input type="hidden" id="tempPeriodStartdate" name="tempPeriodStartdate" />
<input type="hidden" id="tempPeriodEnddate"   name="tempPeriodEnddate" />
<input type="hidden" id="tempPeriodWay"   name="tempPeriodWay" />
<input type="hidden" id="tempPeriodWayForModify"   name="tempPeriodWayForModify"/>
<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 end-->
<input type="hidden" id="tempRemind" name="tempRemind" />
<form id="diaryForm">
<input type="hidden" id="id" name="id" value="0"/> 
<input type="hidden" id="possessorId" name="possessorId"/> 
<input type="hidden" id="businessId" name="businessId"/> 
<input type="hidden" id="token" name="token" value="0"/> 
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
<input type="hidden" id="loginUser" name="loginUser" value="${requestScope.loginUserId}"/>
<section class="tree-fluid m-t-md">
	<aside class="tree-wrap w203" id="LeftHeight_box_list" >
		<section class="panel clearfix" style="overflow-y:auto;overflow-x:hidden;height:405px;">
			<div class="table-wrap notation-btn" id="treeDemo">
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
						<td><a id="writeWorklogBtn" name="writeWorklogBtn" href="#" class="a-icon i-new"
							 data-toggle="tooltip" data-placement="top"
							 title="" data-container="body" data-original-title="日志"><i class="fa fa-compose"></i>
							 </a>
					    </td>
						<td><a id="writeWeekPlanBtn" name="writeWeekPlanBtn" href="#" class="a-icon i-new" data-toggle="tooltip" 
							   data-placement="top" title="" data-container="body" data-original-title="周计划">
							   <i class="fa fa-compose"></i>
							</a>
						</td>
						<td><a id="writeMonthPlanBtn" name="writeMonthPlanBtn" href="#" class="a-icon i-new" data-toggle="tooltip" data-placement="top" title="" 
							   data-container="body" data-original-title="月计划">
							   <i class="fa fa-compose"></i>
							</a>
						</td>
					</tr>
					<tr>
						<td>看下属</td>
						<td><a id="underlingWorklogBtn" name="underlingWorklogBtn" href="#" class="a-icon i-new"
								data-toggle="tooltip" data-placement="top" title=""
								data-container="body" data-original-title="日志">
								<i class="fa fa-table"></i>
							</a>
						</td>
						<td><a id="underlingWeekPlanBtn" name="underlingWeekPlanBtn" href="#" class="a-icon i-new" 
								data-toggle="tooltip" data-placement="top" title=""
								data-container="body" data-original-title="周计划">
								<i class="fa fa-table"></i>
							</a>
						</td>
						<td><a id="underlingMonthPlanBtn" name="underlingMonthPlanBtn" href="#" class="a-icon i-new" 
								data-toggle="tooltip" data-placement="top" title=""
								data-container="body" data-original-title="周计划">
								<i class="fa fa-table"></i>
							</a>
						</td>
					</tr>
					<tr>
						<td>上级批注</td>
						<td><a id="worklogAnnoBtn" name="worklogAnnoBtn" href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="日志"></a></td>
						<td><a id="weekPlanAnnoBtn" name="weekPlanAnnoBtn" href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="周计划"></a></td>
						<td><a id="monthPlanAnnoBtn" name="monthPlanAnnoBtn" href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="月计划"></a></td>
					</tr>
					<tr>
						<td>提交情况</td>
						<td><a id="worklogConditionBtn" name="worklogConditionBtn" href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="日志"><i
								class="fa fa-menu"></i></a></td>
						<td><a id="weekPlanConditionBtn" name="weekPlanConditionBtn" href="#" class="a-icon i-new" 
							   data-toggle="tooltip" data-placement="top" title=""
							   data-container="body" data-original-title="周计划">
							   <i class="fa fa-menu"></i>
							</a>
						</td>
						<td><a id="monthPlanConditionBtn" name="monthPlanConditionBtn" href="#" class="a-icon i-new" 
							   data-toggle="tooltip" data-placement="top" title=""
							   data-container="body" data-original-title="月计划">
							   <i class="fa fa-menu"></i>
							</a>
						</td>
					</tr>
					<tr>
						<td>汇总</td>
						<td><a href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="日志" id="collect">
							<i class="fa fa-list2"></i></a></td>
						<td><a id="weekPlanCollectBtn" name="weekPlanCollectBtn" href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="周计划">
							<i class="fa fa-list2"></i></a></td>
						<td><a id="monthPlanCollectBtn" name="monthPlanCollectBtn" href="#" class="a-icon i-new"
							data-toggle="tooltip" data-placement="top" title=""
							data-container="body" data-original-title="月计划">
							<i class="fa fa-list2"></i></a></td>
					</tr>
				</table>
			</div>
		</section>
		<div class="datepicker-input load-show m-t" data-callback="schedule.leftCallback" id="datePicker"></div>
	</aside>
	<section class="tree-right m-l-218 clearfix">
		<section class="panel" id="diaryContentOne">
			<div class="table-wrap form-table">
			<section class="form-btn m-b-lg m-l" id="buttonSection">
				<a class="btn dark" id="toModify">编 辑</a>
				<a class="btn" id="deleteDiary">删 除</a>
				<a href="#" class="btn" id="calendarView">日历形式展示</a> 
				<a class="btn" href="#" id="diarySummary" data-toggle="modal">日程汇总</a>
			</section>
				<table class="table table-td-striped brn">
					<tbody>
						<tr>
							<td class="w140">选择日程</td>
							<td>
								<div class="input-group w-p100">
									<div class="btn-in-area" id="chooseDiary">
										<select id="oneDayDiaryList" onchange="schedule.loadDiary()">
											<option value="">-请选择-</option>
											<c:forEach items="${requestScope.diaryList}" var="diaryInstance" >
												<option value="${diaryInstance.id}">${diaryInstance.title}</option>
											</c:forEach>
										</select>
									</div>
									<div class="input-group-btn p-l">
										<a class="a-icon i-new m-r-xs" type="button" href="#" role="button" data-toggle="modal" id="toNewDiary">新 增</a>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</section>
		<section class="panel m-t-md" id="diaryContentTwo">
			<div class="table-wrap form-table">
			<input type="hidden" id="delId"/>
			<input type="hidden" id="ptype"/>
				<table class="table table-td-striped">
					<tbody  id="Schedule-detail">
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
			<ul class="dialog m-r m-l m-t" id="comment"></ul>
			<!-- <form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap">-->
			<div class="m-t m-l m-r form-table">
		            <table class="table table-td-striped" id="leaderIdeaTable">
		                <tbody>
		                 	<tr>
		                    <td class="w140">批注内容</td>
		                    <td colspan="3">
		                        <div class="input-group w-p100">
		                            <div>
		                                 <textarea id="leaderIdeaContent" name="annoContent" rows="3"></textarea>
		                            </div>
		                            <div class="input-group-btn icon p-l">
		                                <a id="leaderIdea" id="leaderIdea" href="#" class="a-icon i-new m-b-xs block">保 存</a>
		                                <a class="a-icon i-trash block" href="#" id="clearleaderIdea">清 空</a>
		                            </div>
		                        </div>
		                    </td>
		                </tr>   
		                </tbody>
		            </table></div>
	           <!-- </form>  -->
			
		</section>
	 </section>
</section>
<!--  高级设置弹窗-------->
<div class="modal fade panel" id="Advanced-settings" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onclick="schedule.closeX();">×</button>
				<h4 class="modal-title">高级设置</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td><span class="required">*</span>周期类型</td>
								<td>
									<label class="radio inline"> 
										<input type="radio" id="periodType0" name="periodTypeTmp" value="0" checked/>无定期
									</label> 
									<label class="radio inline">
										<input type="radio" id="periodType1" name="periodTypeTmp" value="1"/>按天
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="periodType2" name="periodTypeTmp" value="2"/>按周
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="periodType3" name="periodTypeTmp" value="3"/>按月
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="periodType4" name="periodTypeTmp" value="4"/>按年
									</label>
								</td>
							</tr>
							<tr id="periodTr" style="display:none" ></tr>
							<tr>
								<td nowrap><span class="required">*</span>周期开始时间</td>
								<td>
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" id="periodStartdate" name="periodStartdate"
											data-date-format="yyyy-MM-dd"  data-ref-obj="#periodEnddate lt">
									</div>
								</td>
							</tr>
							<tr>
								<td nowrap><span class="required">*</span>周期结束时间</td>
								<td>
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" id="periodEnddate" name="periodEnddate"
											data-date-format="yyyy-MM-dd" data-ref-obj="#periodStartdate gt">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="periodConfirm">确 定</a>
				<a class="btn" id="periodCancel">取 消</a>
			</div>
		</div>
	</div>
</div>
</form>
</section>
<div class="modal fade panel" id="period-diary-modify" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">编辑方式</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td class="w140">编辑方式</td>
								<td>
									<label class="radio inline"> 
										<input type="radio" id="periodModify1" name="periodModify" value="0" checked /> 编辑当前事件
									</label> 
									<label class="radio inline m-l-md"> 
										<input type="radio" id="periodModify2" name="periodModify" value="1"/> 编辑所有事件
									</label>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="modifyDiv">确 定</a>
				<a class="btn" id="closeModifyType" data-dismiss="modal">取 消</a> 
			</div>
		</div>
	</div>
</div>
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
                            		<input class="datepicker-input" type="text" id="worklogDateBegin"  data-ref-obj="#worklogDateEnd lt" name="worklogDateBegin">
                            	</td>
                            	<td class="w140">结束时间</td>
                            	<td>
                            		<input type="hidden" id="endDateTemp" name="endDateTemp"/>
                            		<input class="datepicker-input" type="text" id="worklogDateEnd" data-ref-obj="#worklogDateBegin gt" name="worklogDateEnd">
                            	</td>
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
                            <th class="w140">批注</th>
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
                <table class="table table-striped  tab_height" id="summaryTable">
                    <thead>
                        <tr>
                            <th style="width:10%;">所属人</th>
                            <th style="width:40%">日程时间</th>
                            <th style="width:40%">日程内容</th>
                            <th style="width:10%;">批注</th>
                        </tr>
                    </thead>
                    <tbody id="innerTable">
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
<!--查看领导批注 start-->
<div class="modal fade panel" id="worklog-anno" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="diaryMyList.closeAnno();">×</button>
                <h4 class="modal-title">查看领导批注</h4>
            </div>
            <div class="modal-body">
                <form id="commentForm">
                <ul class="dialog m-t" id="annoComment">
            	</ul>
            	</form>  
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" data-dismiss="modal" onclick="diaryMyList.closeAnno();">关 闭</button>
            </div>
        </div>
    </div>
</div>
<div id="summaryAnno"></div>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>