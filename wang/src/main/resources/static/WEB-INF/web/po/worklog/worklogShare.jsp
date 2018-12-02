<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/fullcalendar/fullcalendar.js"></script>
<script src="${sysPath}/js/com/po/anno/anno.validate.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<link href="${sysPath}/js/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css">
<link href="${sysPath}/js/fullcalendar/theme.css" rel="stylesheet" type="text/css">
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header>
	<section class="tree-fluid m-t-md">
		<aside class="tree-wrap" id="LeftHeight_box">
			<section class="panel" id="LeftHeight" style="overflow-y: auto;">
				<div class="panel-heading clearfix" id="header_2">
					<h2>选择人员</h2>
				</div>
				<div id="treeDemo" class="ztree"></div>
			</section>
		</aside>
		<section class="tree-right">
			<section class="panel m-t-md">
				<header class="panel-heading clearfix">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn m-r-xs" id="monthview"><input
							type="radio" name="options" id="option1">月</label> <label
							class="btn m-r-xs" id="weekview"><input type="radio"
							name="options" id="option2">周</label>
						<!--  <label class="btn m-r-xs" id="dayview"><input type="radio" name="options" id="option3">日</label> -->
						<label class="btn m-r-xs active" id="today"><input type="radio"
							name="options" id="option4">今日</label>
					</div>
<!-- 					<a class="btn" role="button" data-toggle="modal" id="today">今日</a> -->
					<a href="javascript:void(0)" class="btn" role="button"
						data-toggle="modal" id="toList">列表形式展示</a>
					<section class="fr">
						<div class="input-append m-b-none">
							<input class="datepicker-input" data-date-format="yyyy-MM-dd"
								id="searchtime" type="text" class="form-control"
								placeholder="快速查询...">
							<button type="button" class="btn" id="search">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</section>
				</header>
				<input type="hidden" id="shareUserId" />
				<%--当前登录用户 start--%>
				<input type="hidden" id="userId" name="userId" value="${userId}" />
				<input type="hidden" id="displayName" name="displayName"
					value="${displayName}" />
				<%--当前登录用户 end--%>
				<input type="hidden" id="createUserDept" />
				<div class="calendar fc fc-ltr table-wrap" id="shareCalendar"></div>
			</section>
		</section>
	</section>
</section>
<!--查看日志详情弹窗------------------->
<div class="modal fade panel" id="worklog-detail" aria-hidden="false">
	<div class="modal-dialog w900">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">查看日志详细</h4>
			</div>
			<div class="modal-body">
				<h4 class="fl" id="detailWorklogTitle"></h4>
				<input type="hidden" id="detailId" /> <input type="hidden"
					id="isLeaderTemp" />
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:20%;">日志标题</td>
								<td id="detailTitle"></td>
							</tr>
							<tr>
								<td>日志日期</td>
								<td id="detailWorklogDate"></td>
							</tr>
							<tr>
								<td>附件</td>
								<td>
									<ul id="attachList"></ul>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- <div class="modal-heading clearfix">
                    <h4 class="fl">待办任务</h4>
                    <div class="fr f14">
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide" style="display: none">展开</a>
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide">收起</a>
                    </div>
                </div> -->
				<!-- <table class="table  table-striped" id="detailTask">
                        <thead>
                            <tr>
                                <th>任务名称</th>
                                <th>进度</th>
                                <th>布置人</th>
                                <th>任务开始时间</th>
                            </tr>
                        </thead>
                        <tbody>
                           
                        </tbody>
                    </table> -->
				<!-- <h4 class="modal-heading clearfix">今日日程</h4>
                <table class="table table-striped " id="detailTodayDiary">
                     <thead>
                        <tr>
                            <th class="w46">序号</th>
                            <th>日程描述</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table> -->
				<h4 class="modal-heading clearfix">今日日志</h4>
				<table class="table table-striped " id="detailTodayLog">
					<thead>
						<tr>
							<th style="width:75px;">序号</th>
							<th>日志描述</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
				<h4 class="modal-heading clearfix">感悟及备注</h4>
				<p class="p_box" id="detailSentimentRemark"></p>
				<h4 class="modal-heading clearfix">明日提醒</h4>
				<table class="table table-striped " id="detailTomorrowRemind">
					<thead>
						<tr>
							<th style="width:75px;">序号</th>
							<th>提醒描述</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
				<h4 class="modal-heading clearfix">共享范围</h4>
				<p class="p_box" id="shareUserNames"></p>
				<h4 class="modal-heading clearfix">领导批注</h4>
				<%--修改领导批注 李洪宇 2014-08-19 开始 <ul class="dialog m-t" id="comment"></ul> --%>
				<form id="leaderIdeaReplayForm"
					class="table-wrap form-table padder-v">
					<ul class="dialog" id="comment">
					</ul>
				</form>
				<form id="leaderIdeaForm" name="leaderIdeaForm"
					class="table-wrap form-table padder-v" style="display: none;">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td>批注内容</td>
								<td colspan="3">
									<div class="input-group w-p100">
										<div>

											<textarea id="leaderIdeaContent" name="content" rows="3"></textarea>
										</div>
										<div class="input-group-btn icon p-l">
											<a id="leaderIdea" id="leaderIdea" href="#"
												class="a-icon i-new m-b-xs fr">保 存</a> <a
												class="a-icon i-trash fr m-b-xs" href="#"
												id="clearleaderIdea">清 空</a>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<%--修改领导批注 李洪宇 2014-08-19 结束 --%>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>

<!--start 上传附件  -->
<div class="modal fade panel" id="file-edit" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="closebtn"
					data-dismiss="modal">×</button>
				<h4 class="modal-title">添加上传文件</h4>
			</div>
			<div class="modal-body">
				<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
				<!-- islogicDel 1为逻辑删除 0为物理删除-->
				<input type="hidden" name="showDelBtn" id="showDelBtn" value="1" />
				<input type="hidden" id="islogicDel" value="1"> <input
					type="hidden" name="businessId" id="businessId" value="0" /> <input
					type="hidden" name="businessSource" id="businessSource" /> <input
					type="hidden" name="businessTable" id="businessTable"
					value="tty_po_worklog" />
				<!-- upload_type 1为单传  0为多传-->
				<input type="hidden" id="upload_type" value="0"> <input
					type="hidden" id="isshow" value="0"> <input type="hidden"
					id="iscopy" value="0">
				<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="closeModalBtn"
					data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>
<!--end 上传附件 -->
<script src="${sysPath}/js/com/po/worklog/worklogShare.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>