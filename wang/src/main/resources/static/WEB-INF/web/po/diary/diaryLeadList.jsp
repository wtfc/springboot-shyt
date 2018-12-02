<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/common/remind.js"></script><!-- 周期性提醒 -->
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/diary/schedule.js"></script>
<script src="${sysPath}/js/com/po/diary/diaryLeadList.js"></script>
<script src="${sysPath}/js/com/po/diary/queryForTree.js"></script>
<script src="${sysPath}/js/com/po/diary/diary.validate.js"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" id="navigationMenu"></div>
</header>
<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 begin-->
<input type="hidden" id="tempPeriodTypeTmp"   name="tempPeriodTypeTmp" />
<input type="hidden" id="tempPeriodStartdate" name="tempPeriodStartdate" />
<input type="hidden" id="tempPeriodEnddate"   name="tempPeriodEnddate" />
<input type="hidden" id="tempPeriodWay"   name="tempPeriodWay" />
<input type="hidden" id="tempPeriodWayForModify"   name="tempPeriodWayForModify"/>
<!-- 高级设置内容暂存用，用于处理超垃圾的弹出层导致的层内容缓存 end-->
<input type="hidden" id="tempRemind" name="tempRemind" />
<!-- 新建&编辑日程 -->
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
<input type="hidden" id="modifyFlag" name="modifyFlag"/>
<input type="hidden" id="newOrModify" name="newOrModify"/><!-- 0 新建 1 修改 -->
<input type="hidden" id="newOk" name="newOk"/><!-- 0 没点击过确定 1 点击过确定 -->
<input type="hidden" id="modifyExistValue" name="modifyExistValue"/><!-- 0 修改有值 1 修改没有值 -->
<input type="hidden" id="loginUser" name="loginUser" value="${requestScope.loginUserId}"/>
<section class="tree-fluid m-t-md">
     <aside class="tree-wrap" id="LeftHeight_box_rl">
        <div class="datepicker-input load-show" data-callback="schedule.leftCallback" id="datePicker"></div> <!-- 日历在树上的时候 固定放在aside和section之间-->
        <section class="panel clearfix" id="LeftHeight_rl" style="overflow-y: auto;"><!--所有树固定加  clearfix -->
            <div class="panel-heading clearfix" id="header_2">
                <h2>选择领导</h2>
            </div>
            <div id="treeDemo" class="ztree"></div>
            <input type="hidden" id="diaryMenuFlag" name="diaryMenuFlag" value="4"/><!-- 1日程安排 2下属日程 3共享查询 4领导日程 -->
			<input type="hidden" id="diaryPageFlag" name="diaryPageFlag" value="2"/><!-- 1日历 2列表 -->
			<input type="hidden" id="mandatorId" name="mandatorId" value="0"/><!-- 委托人(领导) -->
			<input type="hidden" id="userid" name="userid" value="<c:out value='${requestScope.userid}' default='0' escapeXml='false'></c:out>"/> 
        	<input type="hidden" id="startDateTime" name="startDateTime" />
			<input type="hidden" id="endDateTime" name="endDateTime" />
        </section>
    </aside>
    <section class="tree-right clearfix">
        <section class="panel">
            <div class="table-wrap form-table">
            <section class="form-btn m-b-lg m-l" id="buttonSection">
                 <a href="#" class="btn dark" id="calendarView">日历形式展示</a> 
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
                                <div class="input-group-btn p-l" id="toNewAddButton">
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
						<!-- <tr>
							<td>共享范围</td>
							<td id="detailShare"></td>
						</tr> -->
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
			<ul class="dialog m-r m-l m-t" id="comment">
			</ul> 
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
											data-date-format="yyyy-MM-dd" data-ref-obj="#periodEnddate lt">
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
<!--  提醒设置弹窗------
<div class="modal fade panel" id="reminder" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">提醒设置</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:30%;">内容</td>
								<td><textarea rows="2" placeholder="备注..." id="" name=""></textarea></td>
							</tr>
							<tr>
								<td>提醒方式</td>
								<td>
									<label class="radio inline"> 
										<input type="radio" id="" name="ip" checked id="zdip" /> 不提醒
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="" name="ip" /> 邮件
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="" name="ip" /> 短信
									</label>
								</td>
							</tr>
							<tr>
								<td>被提醒人</td>
								<td>
									<div class="input-group">
										<input type="text" id="" name=""><a
											class="btn btn-file input-group-btn" href="#myModal"
											role="button" data-toggle="modal"><i class="fa fa-user"></i></a>
									</div>
								</td>

							</tr>
							<tr>
								<td>类型</td>
								<td><select class="remind">
										<option>-请选择-</option>
										<option>边界提醒</option>
										<option>固定间隔</option>
										<option>周期日期</option>
								</select></td>
							</tr>
							<tr>
								<td>起止时间</td>
								<td>
									<div class="input-group w-p100">
										<input class="datepicker-input" type="text" value="2014-02-28"
											data-date-format="yyyy-MM-dd HH:mm">
										<div class="input-group-btn w30">-</div>
										<input class="datepicker-input" type="text" value="2014-02-28"
											data-date-format="yyyy-MM-dd HH:mm">
									</div>
								</td>
							</tr>
							<tr class="interval" style="display:none;">
								<td>间隔时间</td>
								<td><input type="text" class="input-mini m-l-none">小时<input
									type="text" class="input-mini">分</td>
							</tr>
							<tr class="border" style="display:none;">
								<td>开始前</td>
								<td><select class="w200">
										<option>5分钟</option>
										<option>5分钟</option>
										<option>5分钟</option>
								</select> 提醒</td>
							</tr>
							<tr class="border" style="display:none;">
								<td>结束前</td>
								<td><select class="w200">
										<option>5分钟</option>
										<option>5分钟</option>
										<option>5分钟</option>
								</select> 提醒</td>
							</tr>
							<tr class="circle" style="display:none;">
								<td>提醒周期</td>
								<td><label class="radio inline m-l-n"> 按 </label> <label
									class="radio inline"> <input type="radio" name="day"
										checked id="zdip" /> 天
								</label> <label class="radio inline"> <input type="radio"
										name="day" /> 周
								</label> <label class="radio inline"> <input type="radio"
										name="day" /> 月
								</label> <label class="radio inline"> <input type="radio"
										name="day" /> 年
								</label></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="submit">保 存</button>
				<button class="btn" type="reset">重 置</button>
			</div>
		</div>
	</div>
</div>-->
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
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
