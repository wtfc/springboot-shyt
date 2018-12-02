<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<%--<script src="${sysPath}/js/lib/common/selectControlNew.js" type="text/javascript"></script> --%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/datepicker/bootstrap-datepicker-worklog.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/worklog/worklogLeaderAnno.validate.js"></script>
<script src="${sysPath}/js/com/po/worklog/common.js"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<style>
.datepicker-input-worklog.load-show{position:relative;}
.datepicker-input-worklog.load-show .bootstrap-datetimepicker-widget{z-index:1000;position: relative;margin-bottom: 15px;}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
	    	<h1></h1>
		    <div class="crumbs"></div>
	    </div>
	    <%--
	    <h1>领导批注</h1>
	    <div class="crumbs"><a href="#">首页</a><i></i><a href="#">个人办公</a><i></i><a href="#">工作日志</a><i></i>领导批注</div>
	     --%>
	</header>
	<section class="tree-fluid m-t-md">
	    <aside class="tree-wrap" id="LeftHeight_box">
	    	<input type="hidden" id="datePickerInput"/>	
		    <div class="datepicker-input-worklog load-show"  data-callback="testCallback" id="datePicker"></div>
	    </aside>
	    <section class="tree-right">
	        <section class="panel">
	        <input type="hidden" id="userId"/>
	        <input type="hidden" id="createUserDept"/>
	        <input type="hidden" id="userName"/>
	        <input type="hidden" id="date" />
	        <input type="hidden" id="worklogId" value=""/>
	        <input type="hidden" id="token" name="token" value="${token}"/>
	        <div class="clearfix">
	        	<ul class="tip-color-box fl clearfix m-l m-t">
					<li><b class="bg-blue"></b>有日志</li>
					<li class="m-t-xxs"><b><i class="fa fa-blocked text-red m-t-xxs"></i></b>无日志</li>
					<li><b class="bg-green-dark"></b>有批注 </li>
					<li><b class="bg-red"></b>有未阅读批注</li>
				</ul>
	
	        	<div class="fr no-all m-t m-r">
	                <a href="#" class="btn" role="button" id="pref" isNext="false">前一篇</a>
	                <a href="#" class="btn" role="button" id="next" isNext="true">后一篇</a>
	                <!-- <a href="#" class="btn" role="button" data-toggle="modal">查看月度日报状态</a> -->
	                <a href="#Summary" class="btn" role="button" data-toggle="modal" id="collect">日志汇总</a>
	            </div>
	        </div>
	        <div class="table-wrap">
	        	<input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/>
		        <table class="table table-td-striped">
		            <tbody>
		                <tr>
		                    <td style="width:20%;">日志标题</td>
		                    <td id="title"></td>
		                </tr>
		                <tr>
		                	<td style="width:20%;">日志日期</td>
		                	<td id="worklogDate"></td>
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
			<h4 class="modal-heading clearfix">待办任务</h4>
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
                  
		   <h2 class="panel-heading clearfix">领导批注</h2>
				
				<form id="commentForm">
					<ul class="dialog m-l m-r m-t" id="comment">
		            </ul>
	            </form>
			    <form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table" style="padding-right:15px;">   
		            <table class="table table-td-striped">
		                <tbody>
		                 	<tr>
		                    <td class="w140">批注内容</td>
		                    <td colspan="4">
		                        <div class="input-group w-p100">
		                            <div>
		                                 <textarea id="leaderIdeaContent" name="content" rows="3"></textarea>
		                            </div>
		                            <div class="input-group-btn icon p-l">
		                                <a id="leaderIdea" id="leaderIdea" href="#" class="a-icon i-new m-b-xs fr">保 存</a>
		                                <a class="a-icon i-trash fr m-b-xs" href="#" id="clearleaderIdea">清 空</a>
		                            </div>
		                        </div>
		                    </td>
		                </tr>   
		                </tbody>
		            </table>
		        </form> 
	            <section class="form-btn m-b-lg m-l">
	                <a class="btn dark" href="#Reading" data-toggle="modal" id="readingStatus">阅读情况</a>
	            </section>
	        </section>
	    </section>
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
                                <td class="w115">日志日期</td>
                            <td>
                                <div class="input-group w-p100">
                                	<input type="hidden" id="startDateTemp" name="startDateTemp"/>
                                    <input class="datepicker-input" type="text" id="worklogDateBegin"  data-ref-obj="#worklogDateEnd lt" name="worklogDateBegin">
                                    <div class="input-group-btn w30">
                                     -
                                    </div>
                                    <input type="hidden" id="endDateTemp" name="endDateTemp"/>
                                    <input class="datepicker-input" type="text" id="worklogDateEnd" data-ref-obj="#worklogDateBegin gt" name="worklogDateEnd">
                                </div>
                            </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
                <section class="form-btn m-b-lg m-t-md">
                    <button class="btn dark" type="button" id="collectSearch">查 询</button>
                    <button class="btn" type="button" id="collectReset">重 置</button>
                </section>
                <table class="table  table-striped" id="collectTable">
                    <thead>
                        <tr>
                            <%--<th style="width:20%">日志日期</th> --%>
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
                <button type="button" class="btn" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
</div>

<!--  阅读情况弹窗-------->
<div class="modal fade panel" id="Reading" aria-hidden="false">
     <div class="modal-dialog w900">
         <div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal">×</button>
                 <h4 class="modal-title">阅读情况</h4>
             </div>
             <div class="modal-body">
                 <table class="table  table-striped" id="readingStatusTable">
                     <thead>
                         <tr>
                            <th style="width:10%;">阅读人</th>
                            <th style="width:20%">阅读时间</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr>
                    		<td colspan="2">没有匹配的记录</td>
                    	</tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer form-btn">
                <button type="button" class="btn dark" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
</div> 
<!--查看领导批注 start-->
<div class="modal fade panel" id="worklog-anno" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="worklogLeaderAnno.closeAnno();">×</button>
                <h4 class="modal-title">查看领导批注</h4>
            </div>
            <div class="modal-body">
                <form id="commentForm">
                <ul class="dialog m-t" id="annoComment">
            	</ul>
            	</form>  
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" data-dismiss="modal" onclick="worklogLeaderAnno.closeAnno();">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--查看领导批注 end-->
<!--start 上传附件  -->
<div class="modal fade panel" id="file-edit" aria-hidden="false">
	<div class="modal-dialog">
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
				<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>
<!--end 上传附件 -->
<script src="${sysPath}/js/com/po/worklog/worklogLeaderAnno.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>