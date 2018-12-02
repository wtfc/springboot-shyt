<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/selectDeptAndPersonControl.js" type="text/javascript"></script>
<script type='text/javascript' src='${sysPath}/js/lib/common/ChinesePY.js'></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/fullcalendar/fullcalendar.js"></script>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/anno/anno.validate.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
	    <div class="con-heading fl" id="navigationMenu">
	    	<h1></h1>
		    <div class="crumbs"></div>
	    </div>
	</header>
	
	<section class="panel clearfix search-box search-shrinkage">
       	<section class="m-b-lg m-l m-t-md">
       		<a id="showCalendar" href="javascript:void(0)" role="button" data-toggle="modal"  class="btn">日历形式展示</a>
   		</section>
   		
            <div class="search-line hide">
            	<form id="queryWorklogShareForm" class="table-wrap form-table">
            	 <%--当前登录用户 start--%>
            	  <input type="hidden" id="shareUserId"/>
	              <input type="hidden" id="userId" name="userId" value="${userId}"/>
	              <input type="hidden" id="displayName" name="displayName" value="${displayName}"/>
	             <%--当前登录用户 end--%>
                <table class="table table-td-striped">
                    <tbody>
                        <tr>
                            <td  class="w115">日志日期</td>
                            <td>
                            <div class="input-group w-p100">
	                            <input class="datepicker-input" type="text" id="worklogDateBegin"  data-ref-obj="#worklogDateEnd lt" name="worklogDateBegin">
                                <div class="input-group-btn w30">
                                 -
                                </div>
                                <input class="datepicker-input" type="text" id="worklogDateEnd" data-ref-obj="#worklogDateBegin gt" name="worklogDateEnd">
	                        </div>
                            </td>
                            <td class="w115">共享人</td>
                            <td style="width:40%;">
                            	<div id="leftAndRightSelectDiv"></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                
                <section class="form-btn m-b-lg">
                    <button id="queryInfo" class="btn dark query-jump" type="button">查 询</button>
                    <button id="resetInfo" class="btn" type="button">重 置</button>
                    <%--<button id="resetInfo" class="btn" type="reset">重 置</button> --%>
                </section>
                </form>
            </div>
            <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>
        <section class="panel clearfix" id="insideIn-list">
            <div class="panel-heading clearfix">
                <h2>查询结果</h2>
            </div>
        <div class="table-wrap">
        	<table class="table table-striped"  id="queryWorklogShareTable">
             <thead>
                <tr>
                    <th class="w50">日志标题</th>
                    <th class="w50">日志日期</th>
                    <th class="w50">共享人</th>
                    <th>操作</th>
                </tr>
             </thead>
             <tbody>
             
            </tbody>
        </table>
    </div>
    
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
            	<input type="hidden" id="detailId"/>
            	<input type="hidden" id="isLeaderTemp"/>
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
               <!--  <div class="modal-heading clearfix">
                    <h4 class="fl">待办任务</h4>
                    <div class="fr f14">
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide" style="display: none">展开</a>
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide">收起</a>
                    </div>
                </div>
                <table class="table table-striped" id="detailTask">
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
               <!--  <h4 class="modal-heading clearfix">今日日程</h4>
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
                <p class="p_box" id="shareUserNames"></p>
                <h4 class="modal-heading clearfix">领导批注</h4>
                <%--修改领导批注 李洪宇 2014-08-29 开始 <ul class="dialog m-t" id="comment">
            	</ul> --%>
            	 <form id="leaderIdeaReplayForm"  class="table-wrap form-table padder-v">
            	 <%--<ul class="dialog m-r m-l m-t" id="comment"> --%>
	             	<ul class="dialog" id="comment">
					</ul>
	            </form>    
	            <form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table padder-v" style="display: none;"> 
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
			                                <a id="leaderIdea" id="leaderIdea" href="#" class="a-icon i-new m-b-xs fr">保 存</a>
			                                <a class="a-icon i-trash fr m-b-xs" href="#" id="clearleaderIdea">清 空</a>
			                            </div>
			                        </div>
			                    </td>
			                </tr>   
			                </tbody>
			            </table>
		           </form>
		           <%--修改领导批注 李洪宇 2014-08-29 结束 --%>
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
<script src="${sysPath}/js/com/po/worklog/worklogShareList.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>