<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<script src="${sysPath}/js/fullcalendar/fullcalendar.js"></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/worklog/myWorklogCalendar.validate.js"></script>
<script src="${sysPath}/js/com/po/worklog/common.js"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<link href="${sysPath}/js/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css">
<link href="${sysPath}/js/fullcalendar/theme.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function worklogCalendarWorkTaskTableSetButtons( source) {
		var edit = '<shiro:hasPermission name="user:update"><a class="a-icon i-edit m-r-xs" href="#" onclick="user.get('+ source.id+')" role="button" data-toggle="modal"><i class="fa fa-edit2" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="编辑"></i></a></shiro:hasPermission>';
		var initPwd = '<shiro:hasPermission name="user:initPwd"><a class="a-icon i-new m-r-xs" href="#" onclick="user.initPassword('+ source.id +')"><i class="fa fa-key"></i>初始化密码</a></shiro:hasPermission>';
		var del = '<shiro:hasPermission name="user:delete"><a class="a-icon i-remove" href="#" onclick="user.deleteUser('+ source.id+ ')"><i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></a></shiro:hasPermission>';
		if(source.isSystemAdmin){
			return null;
		} else {
			return edit + initPwd + del;
		}
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
	    <div class="con-heading fl" id="navigationMenu">
	    	<h1></h1>
		    <div class="crumbs"></div>
	    </div>
	</header>
	<section class="panel m-t-md">
		    <header class="panel-heading clearfix">
		        <div class="btn-group" data-toggle="buttons">
		        	<input type="hidden" id="token" name="token" value='${token}' />
		        	<input type="hidden" id="currentLoginUser" name="currentLoginUser" value='${currentLoginUser}' />
		        	<input type="hidden" id="workLogDay" name="workLogDay" value='${workLogDay}' /><!--获取系统设置的参数，判断所选日期的操作权限  xuweiping 20141126  -->
		            <input type="hidden" id="currentDate"/><!-- xuweiping 2014.11.25 添加隐藏域存储当前选择的日期 -->
		            <label class="btn m-r-xs active" id="monthview"><input type="radio" name="options" id="option1">月</label>
		            <label class="btn m-r-xs" id="weekview"><input type="radio" name="options" id="option2">周</label>
		            <%--李洪宇 于2014-08-26 修改 为与工作日程中的今日样式统一  开始--%>
		            <label class="btn m-r-xs" id="today"><input type="radio" name="options" id="option4"/>今日</label>
		            <%--李洪宇 于2014-08-26 修改 为与工作日程中的今日样式统一  结束--%>
		            <!-- <label class="btn m-r-xs" id="dayview"><input type="radio" name="options" id="option3">日</label> 
		            <label class="btn m-r-xs" id="today"><input type="radio" name="options1" id="option4">今日</label>-->
		        </div>
		        <%--李洪宇 于2014-08-26 修改 为与工作日程中的今日样式统一  
		         <a href="javascript:void(0)" class="btn" role="button" data-toggle="modal" id="today">今日</a>
		        --%>
		        <a href="javascript:void(0)" class="btn" role="button" data-toggle="modal" id="collect">日志汇总</a>
		        <!-- <a class="btn m-r-xs" href="#worklog-detail" id="showDetail" >日志详细页</a> -->
		        <!-- <a class="btn" href="javascript:void(0);" id="showList">列表形式展示</a> -->
		        <section class="fr">
		                <div class="input-append m-b-none">
		                	<input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/>
		                    <input class="datepicker-input" data-date-format="yyyy-MM-dd" id="searchtime" type="text" class="form-control" placeholder="快速查询...">
		                    <button  class="btn" id="search">
		                        <i class="fa fa-search"></i>
		                    </button>
		                </div>
		        </section>
		    </header>
		    <div class="calendar fc fc-ltr table-wrap" id="calendar"></div>
		    <ul class="tip-color-box m-l m-b-md clearfix">
				<li><b class="bg-blue"></b>普通日志</li>
				<li><b class="bg-yellow"></b>已共享日志</li>
			</ul>	
	</section>
</section>
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
                                <td class="w115">开始时间</td>
                            	<td>
                            		<input type="hidden" id="startDateTemp" name="startDateTemp"/>
                            		<input class="datepicker-input" type="text" id="worklogDateBegin"  data-ref-obj="#worklogDateEnd lt" name="worklogDateBegin">
                            	</td>
                            	<td class="w115">结束时间</td>
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
                <table class="table table-striped" id="collectTable">
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
                <button class="btn" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--新建日志star--------->
<div class="modal fade panel" id="New-worklog" aria-hidden="false">
    <div class="modal-dialog w1100">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" name="deleteAtta">×</button>
                <h4 class="modal-title" id="modalTille">编辑日志</h4>
            </div>
            <div class="modal-body">
                <div class="modal-heading clearfix">
                    <h4 class="fl" id="worklogTitle"></h4>
                    <div class="fr f14" id="pastFiveDays">
                    </div>
                </div>
                <form class="form-table" id="worklogCalendarForm">
                <section>
	                <input type="hidden" id="id" name="id" value="0">
	                <%--<input type="hidden" id="token" name="token" value='${token}'> --%>
	                <input type="hidden" id="modifyDate" name="modifyDate">
	                <input type="hidden" id="currentUserName" name="currentUserName">
	                <section class="form-table">
                    <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td class="w115"><span class="required">*</span>日志标题</td>
                                <td style="width:35%;"><input type="text" name="title" id="title"/></td>
                                <td class="w115"><span class="required">*</span>日志日期</td>
                                <td>
                                    <div class="input-group w-p100"><input  id="worklogDate" name="worklogDate" type="text" value="" data-date-format="yyyy-MM-dd" readOnly></div>
                                </td>
                            </tr>
                            <tr>
                                <td>附件</td>
                                <td colspan="3">
									<!-- <a class="btn dark" type="button" href="#fileUpload-edit"  role="button" data-toggle="modal" name="queryattach">附件</a> -->
								 	<%-- 李洪宇 于2014-7-9 修改 start
								 	<a class="btn dark" type="button" href="#file-edit"  name="queryattach" id="queryattach"  role="button" data-toggle="modal">附件</a>
								     --%>
								      <input type="file" class="filestyle" data-icon="false" data-classbutton="btn btn-file input-group-btn" data-classinput="form-file" id="filestyle-0" style="position: fixed; left: -5000px;" />
									  <a class="btn dark" type="button" href="#file-edit"  name="queryattach" id="queryattach"  role="button" data-toggle="modal">附件</a>
								     <%-- 李洪宇 于2014-7-9 修改 end--%>
								     <ul id="attachList"></ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    </section>
                <div class="modal-heading clearfix">
                    <h4 class="fl">待办任务</h4>
                    <div class="fr f14">
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="showAndHide" style="display: none">展开</a>
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="showAndHide">收起</a>
                    </div>
                </div>
                <table class="table table-striped" id="task">
                        <thead>
                            <tr>
                                <th>任务名称</th>
                                <th>任务进度(%)</th>
                                <th>发起人</th>
                                <th>实际开始时间</th>
                                <th style="width:200px;">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <h4 class="modal-heading clearfix">今日日程</h4>
                        <table class="table table-striped" id="todayDiary">
                             <thead>
                                <tr>
                                    <th style="width:75px;">序号</th>
                                    <th>日程内容</th>
                                    <th class="w140">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                <h4 class="modal-heading clearfix">今日日志</h4>
	                <a id="importYesterdayRemind" name="importYesterdayRemind" href="javascript:void(0);" class="btn" >导入昨日提醒</a>
                    <table class="table table-striped"  id="todayLog">
	                    <thead>
                            <tr>
                                <th style="width:75px;">序号</th>
                                <th>日志描述</th>
                                <th style="width:75px;">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                        </tbody>
                    </table>
                <h4 class="modal-heading clearfix">感悟及备注</h4>
                    <textarea id="sentimentRemark" name="sentimentRemark" rows="3" ></textarea>
                <h4 class="modal-heading clearfix">明日提醒</h4>
                    <table class="table table-striped " id="tomorrowRemind">
                    	<thead>
                            <tr>
                                <th style="width:75px;">序号</th>
                                <th>提醒描述</th>
                                <th class="w170">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                        </tbody>
                    </table>
                <h4 class="modal-heading clearfix">共享范围</h4>
					<div id="shareUserNames"></div>
					<button class="btn dark" type="button" id="reminder">提醒设置</button>
					</section>
				</form>
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" id="saveAndClose">保 存</button>
                <button class="btn" type="button" data-dismiss="modal" name="deleteAtta">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--新建日志end--------->
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
            	<input type="hidden" id="detailId"/>
            	<div class="modal-heading clearfix">
                    <div class="fr m-t-n-xs">
	               		 <!--
	               		<a href="#" class="btn" role="button" data-toggle="modal">查看月度日报状态</a>
		                <a href="#" class="btn" role="button" id="modify">修改</a>
		                <a href="#" class="btn" role="button" id="delete">删除</a> -->
	       			</div>
                </div>
                <div class="form-table">
                	 <input type="hidden" id="createUser">
                	 <input type="hidden" id="createUserDept">
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
                                	<%--<ul id="attachList"></ul> --%>
                                	<ul id="attachList_1"></ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-heading clearfix">
                    <h4 class="fl">待办任务</h4>
                    <div class="fr f14">
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide" style="display: none">展开</a>
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide">收起</a>
                    </div>
                </div>
                <table class="table  table-striped" id="detailTask">
                        <thead>
                            <tr>
                                <th>任务名称</th>
                                <th>任务进度(%)</th>
                                <th>发起人</th>
                                <th>实际开始时间</th>
                            </tr>
                        </thead>
                        <tbody>
                           
                        </tbody>
                    </table>
                <h4 class="modal-heading clearfix">今日日程</h4>
                <table class="table table-striped " id="detailTodayDiary">
                     <thead>
                        <tr>
                            <th style="width:75px;">序号</th>
                            <th>日程内容</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
                <h4 class="modal-heading clearfix">今日日志</h4>
                <table class="table table-striped "  id="detailTodayLog">
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
                <p class="p_box" id="detailShareUserNames"></p>
                <h4 class="modal-heading clearfix">领导批注</h4>
                <form id="commentForm">
                <ul class="dialog m-t" id="comment">
            	</ul>
            	</form>
            	<form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table padder-v"> 
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
			                                <a id="leaderIdea"  href="#" class="a-icon i-new m-b-xs fr">保 存</a>
			                                <a class="a-icon i-trash fr m-b-xs" href="#" id="clearleaderIdea">清 空</a>
			                            </div>
			                        </div>
			                    </td>
			                </tr>   
			                </tbody>
			            </table>
		           </form>
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" id="modify">编 辑</button>
                <button class="btn" type="button" id="delete">删 除</button>
                <button class="btn" type="button" id="logReadInfo" onclick="worklogCalendar.initReadList();">阅读情况</button>
                <button class="btn" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--汇报弹窗-->
<div class="modal fade panel" id="report-modal" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">汇报</h4>
            </div>
            <div class="modal-body">
                <form class="table-wrap form-table" id="reportForm">
                	<input type="hidden" id="reportTaskId" name="taskId" />
                	<input type="hidden" id="reEventTitle" name="taskEventTitle" />
                	<input type="hidden" id="reContent" name="content" />
                    <input type="hidden" id="reportTaskEventType" name="taskEventType" value="5" />
                    <table class="table table-td-striped ">
                        <tbody>
                            <tr>
                                <td style="width:120px;"><span class="required">*</span>任务进度(%)</td>
                                <td><input type="text" class="input-mini m-l-none" id="reportProc" name="reportProc"></td>
                            </tr>
                            <tr>
                                <td><span class="required">*</span>汇报内容</td>
                                <td><textarea id="reportContent" name="reportContent"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" id="reportSure">保 存</button>
                <button class="btn" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--  提醒设置弹窗-------->
<div class="modal fade panel" id="reminderModal" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">提醒设置</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
				<form id="reminderForm" class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td>提醒方式</td>
								<td>
									<label class="radio inline"> 
										<input type="radio" id="" name="remind" checked value="0" /> 不提醒
									</label> 
									<label class="radio inline"> 
										<input type="radio" id="" name="remind" value="1"/> 邮件
									</label> 
								</td>
							</tr>
							<tr>
								<td>被提醒人</td>
								<td>
									<div id="leftAndRightSelectDiv"></div>
								</td>
							</tr>
							<tr id='remindTitleTr' class="hide">
								<td style="width:30%;">邮件标题</td>
								<td><input id="remindTitle" name="" type="text" class=""/></td>
							</tr>
							<tr>
								<td style="width:30%;" id="remindContentLable">内容</td>
								<td><textarea rows="2" placeholder="" id="remindContent" name=""></textarea></td>
							</tr>
						</tbody>
					</table>
					</form>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="remindOk">保 存</button>
				<button class="btn" type="button" data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>

<div id="showDiaryBox" name="showDiaryBox" class="modal fade panel" aria-hidden="false">
	<div class="modal-dialog w900">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">加入日程详细</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					<input type="hidden" name="taskTitle"/>
					<input type="hidden" name="taskContent" />
					<input type="hidden" name="actStartTime" id=""/>
					<input type="hidden" name="endTime" id=""/>
					<input type="hidden" name="moduleFlag" id=""/>
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:20%;">日程所属人</td>
								<td id="diaryUser"></td>
							</tr>
							<tr>
								<td style="width:20%;">日程标题</td>
								<td id="diaryTitle"></td>
							</tr>
							<tr>
								<td style="width:20%;">开始时间</td>
								<td id="diaryStartTime"></td>
							</tr>
							<tr>
								<td style="width:20%;">结束时间</td>
								<td id="diaryEndTime"></td>
							</tr>
							<tr>
								<td>日程内容</td>
								<td id="diaryContent"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="toModify">保 存</a>
				<button class="btn" type="button" data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>
<!--start 上传附件  -->
 <div class="modal fade panel" id="file-edit" aria-hidden="false">
		<div class="modal-dialog w820" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
					<h4 class="modal-title">添加上传文件</h4>
				</div>
				<div class="modal-body">
				<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
					<!-- islogicDel 1为逻辑删除 0为物理删除-->
					<input type="hidden" name="showDelBtn" id="showDelBtn" value="1"/>
					<input type="hidden" id="islogicDel" value="1">
					<input type="hidden" name="businessId" id="businessId" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource"/>
					<input type="hidden" name="businessTable" id="businessTable"  value="tty_po_worklog"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="0"> 
	                <input type="hidden" id="iscopy" value="0"> 
	                <input type="hidden" id="upload_div_name" value="file-edit">
	                <input type="hidden" id="upload_close_callback" value="worklogCalendar.echoShowAttachList">
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
<!--end 上传附件 -->
<!--查看领导批注 start-->
<div class="modal fade panel" id="worklog-anno" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="worklogCalendar.closeAnno();">×</button>
                <h4 class="modal-title">查看领导批注</h4>
            </div>
            <div class="modal-body">
                <form id="commentForm">
                <ul class="dialog m-t" id="annoComment">
            	</ul>
            	</form>  
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" data-dismiss="modal" onclick="worklogCalendar.closeAnno();">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--查看领导批注 end-->
<!--start 阅读情况 弹出层-->
<div id="read" class="modal fade panel" aria-hidden="false">
	<form id="readForm" name="readForm" class="table-wrap form-table">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">阅读情况</h4>
            </div>
            <div class="modal-body">
                <div class="table-wrap">
                    <table id="readTable" name="readTable" class="table table-striped">
                        <thead>
                            <tr>
                                <th>阅读人</th>
                                <th>阅读时间</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer  form-btn">
                <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
    </form>
</div>
<!--end 阅读情况 弹出层-->
<script src="${sysPath}/js/com/po/worklog/myWorklogCalendar.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>