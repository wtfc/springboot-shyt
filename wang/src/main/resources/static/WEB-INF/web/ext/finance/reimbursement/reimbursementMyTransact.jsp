<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/ext/finance/reimbursementMy.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<!-- 流程相关项 -->
<input type="hidden" name="entranceType" id="entranceType" value=${entranceType}>
<input type="hidden" name="entrance" id="entrance" value=${entrance}>
<input type="hidden" name="flowId" id="flowId" value=${flowId}>
<header class="con-header pull-in">
    <div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
</header>
    <!--start 查询条件-->
<section class="panel clearfix search-box search-shrinkage">
<div class="search-line hide">
        <form class="table-wrap form-table" id="sendWaitTransactListForm">
            <table class="table table-td-striped">
                <tbody>
                    <tr>
                        <td class="w140">编号</td>
                        <td style="width:40%">
                        	<input type="text" id="reNum" name="reNum"/>
                        </td>
                       <td class="w140">申请日期</td>
                        <td style="width:40%">
	                        <div class="input-group w-p100">
	                            <input class="datepicker-input" type="text" id="reDateBegin" name="reDateBegin" value="" data-date-format="yyyy-MM-dd" data-ref-obj="#ReDateEnd lt">
	                            <span class="input-group-btn w30">-</span>
	                            <input class="datepicker-input" type="text" id="ReDateEnd" name="ReDateEnd" value="" data-date-format="yyyy-MM-dd" data-ref-obj="#reDateBegin gt">
	                        </div>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="w140">项目名称</td>
                        <td style="width:40%">
                        	<dic:select id="project" name="project" headName="-全部-" headValue="" dictName="reimbursement_project">
                            </dic:select>                        
                        </td>
                        <td class="w140">审批状态</td>
                        <td><%@ include file="/WEB-INF/web/include/workflowSearch.jsp"%></td>
                    </tr>
                </tbody>
            </table>
            <section class="form-btn m-b-lg">
                <button class="btn dark query-jump" type="button" id="querySendWaitTransact">查 询</button>
                <button class="btn" type="reset"  id="queryReset">重 置</button>
            </section>
        </form>
	</div>
	<%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%>
    </section>
    <!--end 查询条件-->
    <!--start 查询结果-->
    <section class="panel clearfix" id="sendWaitTransact-list">
        <h2 class="panel-heading clearfix">查询结果</h2>
        <div class="table-wrap">
            <table class="table table-striped tab_height" id="sendWaitTransactTable">
                <thead>
                    <tr>
                        <th class="w300">编号</th>
                        <th class="w170">申请日期</th>
                        <th class="w170">科目汇总(元)</th>
                        <th class="w170">报销金额合计(元)</th>
                        <th class="w170">审核状态</th>
                        <th class="w170">操作</th>
                    </tr>
                </thead>
                <tbody>
 
                </tbody>
            </table>
        </div>
		<section class="clearfix" id="footer_height">
			<section class="form-btn fl m-l">
			</section>
		</section>
    </section>
</section>
    <!--end 查询结果-->
<%@ include file="/WEB-INF/web/include/foot.jsp"%>