<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<!--查看日程详细-->
<div class="modal fade panel" id="Schedule-detail" aria-hidden="false">
<input type="hidden" id="delId"/>
<input type="hidden" id="ptype"/>
<input type="hidden" id="annoToken" name="annoToken" value="${annoToken}">
<input type="hidden" id="diaryJsonDetail"  value='${diaryJsonDetail}'/>
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
							<tr id="shareDetail">
								<td>共享范围</td>
								<td colspan="3" id="detailShare" style='word-break: break-all;word-wrap: break-word;'></td>
							</tr>
							<tr>
								<td>日程内容</td>
								<td colspan="3" id="detailContent" style='word-break: break-all;word-wrap: break-word;'></td>
							</tr>
						</tbody>
					</table>
					<h4 class="modal-heading clearfix">领导批注</h4>
					<input id="currentUser" type="hidden" value="<shiro:principal property='displayName'/>"/>
					<ul class="dialog m-t" id="comment">
					</ul> 
					<!-- <form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap">-->
		            <table class="table table-td-striped" id="annoInput">
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
		            </table>
	            <!--</form>  -->
				</div>
			</div>
			<div class="modal-footer form-btn">
				<a class="btn dark" id="toModify">编 辑</a>
				<a class="btn" id="deleteDiary">删 除</a>
				<a class="btn" href="#Reading" data-toggle="modal" id="readingStatus">阅读情况</a>
				<a class="btn" id="closeDetail" data-dismiss="modal">关 闭</a>
			</div>
		</div>
	</div>
</div>
