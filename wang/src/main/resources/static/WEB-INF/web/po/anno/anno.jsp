<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/common/remind.js"></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/common/DeleteCascade.js" type="text/javascript"></script>
<script type="text/javascript">
	function oTableSetButtons(mData,type,full) {
		var viewBtn = "";//查看按钮
		if(full.annoType == 0 ){//工作计划跳转
			viewBtn = "<a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"anno.loadDetailJump("+full.businessId+ ")\" role=\"button\" data-toggle=\"modal\">查看</a>"
		}else if(full.annoType == 1){//工作日程跳转
			viewBtn = "<a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"anno.showDiary("+full.businessId+ ")\" role=\"button\" data-toggle=\"modal\">查看</a>"
		}else if(full.annoType == 2){//工作日程跳转
			viewBtn = "<a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"anno.showWorklog("+full.businessId+ ")\" role=\"button\" data-toggle=\"modal\">查看</a>"
		}else{//督办协办跳转
			viewBtn = "<a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"anno.showTask("+full.businessId+ ")\" role=\"button\" data-toggle=\"modal\">查看</a>"
		}
		return viewBtn;
	};
</script>

<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="navigationMenu"></header>

<section class="panel clearfix search-box search-shrinkage">
 <div class="search-line hide">
    <form id="annoForm" name="annoForm" class="table-wrap form-table" id="annoQueryForm">
    	<input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/>
    	<input id="currentUserId" type="hidden" value="<shiro:principal property='id'/>"/>
        <table class="table table-td-striped">
            <tbody>
                <tr>
                    <td class="w140">批注文件名称</td>
                    <td><input type="text" name="annoName" id="annoName"/></td>
                    <td class="w140">批注类别</td>
                    <td style="width:40%;">
                        <select name="annoType" id="annoType">
                            <option value="">-全部-</option>
                            <option value="0">工作计划</option>
                            <option value="1">工作日程</option>
                            <option value="2">工作日志</option>
                            <option value="3">督办协办</option>
                        </select>
                    </td>
<!--                     <td>批注内容</td> -->
<!--                     <td><input type="text" name="content" id="content"/></td> -->
                </tr>
                <tr>
                    <td class="w140">批注人</td>
                    <td><div id="annoUserTree" name="annoUserTree"></div></td>
                    <td class="w140">批注部门</td>
                    <td><div id="annoDeptTree" name="annoDeptTree"></div></td>
                </tr>
                <tr>
                    <td class="w140">被批注人</td>
                    <td><div id="byAnnoUserTree" name="byAnnoUserTree"></div></td>
                    <td class="w140">被批注部门</td>
                    <td><div id="byAnnoDeptTree" name="byAnnoDeptTree"></div></td>
                </tr>
                <tr>
                    <td>批注时间</td>
                    <td>
                        <div class="input-group w-p100">
                            <input  class="datepicker-input" type="text" name="annoDateBegin" id="annoDateBegin" data-ref-obj="#annoDateEnd lt" data-date-format="yyyy-MM-dd">
                            <span class="input-group-btn w30">-</span>
                            <input class="datepicker-input" type="text" name="annoDateEnd" id="annoDateEnd"  data-ref-obj="#annoDateBegin gt" data-date-format="yyyy-MM-dd">
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
        <section class="form-btn m-b-lg">
            <button id="queryAnno" name="queryAnno" class="btn dark query-jump" type="button">查 询</button>
            <button id="resetAnno" name="resetAnno" class="btn" type="button">重 置</button>
        </section>
    </form>
    </div>
<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
</section>  
<section class="panel clearfix">
    <h2 class="panel-heading clearfix">查询结果</h2>
    <div class="table-wrap">
        <table id="annoTable" name="annoTable" class="table table-striped tab_height" >
            <thead>
                <tr>
                	<th style="width:20%">批注文件名称</th>
<!--                <th style="width:230px;">批注内容</th> -->
                    <th>批注类别</th>
                    <th>批注人</th>
                    <th>批注部门</th>
                    <th>被批注人</th>
                    <th>被批注部门</th>
                    <th>批注时间</th>
                    <th class="w60">操作</th>
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
            	<input type="hidden" id="detailId"/>
            	<input type="hidden" id="worklogCreateUserId"/>
            	<input type="hidden" id="worklogCreateUserDept"/>
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
                <div class="modal-heading clearfix">
                    <h4 class="fl">待办任务</h4>
                    <div class="fr f14">
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide" style="display: none">展开</a>
                       <a href="#" class="btn-link m-l-xs m-r-xs" name="detailShowAndHide">收起</a>
                    </div>
                </div>
                <table class="table first-td-tc table-striped" id="detailTask">
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
                    </table>
                <h4 class="modal-heading clearfix">今日日程</h4>
                <table class="table table-striped first-td-tc" id="detailTodayDiary">
                     <thead>
                        <tr>
                            <th style="width:75px;">序号</th>
                            <th>日程描述</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
                <h4 class="modal-heading clearfix">今日日志</h4>
                <table class="table table-striped first-td-tc"  id="detailTodayLog">
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
                <table class="table table-striped first-td-tc" id="detailTomorrowRemind">
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
                <%--李洪宇 于2014-7-10 对批注进行修改 开始 --%>
                
                <h4 class="modal-heading clearfix">领导批注</h4>
                <form id="commentForm">
                <ul class="dialog m-t" id="comment">
            	</ul>
            	</form>
            	<form id="worklogLeaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table padder-v">
		            <table class="table table-td-striped">
		                <tbody>
		                 	<tr>
		                    <td>批注内容</td>
		                    <td colspan="3">
		                        <div class="input-group w-p100">
		                            <div>
		                                 <textarea id="worklogLeaderIdeaContent" name="content" rows="3"></textarea>
		                            </div>
		                            <div class="input-group-btn icon p-l">
		                                <a id="worklogLeaderIdea" href="#" class="a-icon i-new m-b-xs fr">保 存</a>
		                                <a class="a-icon i-trash fr" href="#" id="worklogClearleaderIdea">清 空</a>
		                            </div>
		                        </div>
		                    </td>
		                </tr>   
		                </tbody>
		            </table>
	            </form>   
            <%--李洪宇 于2014-7-10 对批注进行修改 结束 --%>     
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
</div>

<!--查看日程详细-->
<div class="modal fade panel" id="Schedule-detail" aria-hidden="false">
<input id="diaryId" name="diaryId" type="hidden">
<input id="userid" name="userid" type="hidden">
<input type="hidden" id="annoTokenAnno" name="annoTokenAnno" value="${annoTokenAnno}">
	<div class="modal-dialog w900">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">查看日程详细</h4>
			</div>
			<div class="modal-body">
				<div class="form-table">
					<table class="table table-td-striped">
						<tbody>
							<tr>
								<td style="width:20%;">日程标题</td>
								<td colspan="3" id="detailTitle1"></td>
							</tr>
							<tr>
								<td style="width:20%;">开始时间</td>
								<td id="detailStartTime"></td>
								<td style="width:20%;">结束时间</td>
								<td id="detailEndTime"></td>
							</tr>
							<tr>
								<td>周期模式</td>
								<td colspan="3" id="detailPeriodTypeStartEndDate"></td>
							</tr>
							<%--新增 提醒设置 lihongyu 2014-10-21 start --%>
							<tr>
								<td>提醒设置</td>
								<td colspan="3" id="detailRemind"></td>
								<input type="hidden" id="tempRemind" name="tempRemind"/>
							</tr>
							<%--新增 提醒设置 lihongyu 2014-10-21 end --%>
							<tr>
								<td>共享范围</td>
								<td colspan="3" id="detailShare"></td>
							</tr>
							<tr>
								<td>日程内容</td>
								<td colspan="3" id="detailContent"></td>
							</tr>
							<!-- <tr>
								<td>提醒方式</td>
								<td id="detailRemind"></td>
							</tr> -->
						</tbody>
					</table>
					<h4 class="modal-heading clearfix">领导批注</h4>
					<form id="commentForm1">
					<ul class="dialog m-t" id="comment1">
					</ul>
					</form> 
					<form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table padder-v">
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
		                                <a id="leaderIdea" id="leaderIdea" href="#" class="a-icon i-new m-b-xs fr">保 存</a>
		                                <a class="a-icon i-trash fr" href="#" id="clearleaderIdea">清 空</a>
		                            </div>
		                        </div>
		                    </td>
		                </tr>   
		                </tbody>
		            </table>
	            </form> 
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="closeDetail" data-dismiss="modal">关 闭</a>
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
<script src="${sysPath}/js/com/po/anno/anno.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/anno/anno.validate.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/po/anno/showDetail.js"></script>
<script src="${sysPath}/js/com/po/anno/schedule.js"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>