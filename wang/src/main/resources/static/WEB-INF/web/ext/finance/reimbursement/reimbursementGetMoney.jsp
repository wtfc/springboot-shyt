<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script type="text/javascript">
function oTableSetButtons( mData, type, full ) {
	if(full.reStatus != "1") {
		return "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"sendFinishTransact.showDistributionDiv("+ full.id +",'"+full.modifyDate+"')\" role=\"button\" data-toggle=\"modal\"><i  data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" ></i>领款</a>";
	} else {
		return "";
	}
};
</script>
<script src="${sysPath}/js/ext/finance/reimbursementMoney.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<!-- 流程相关项 -->
<input type="hidden" name="entranceType" id="entranceType" value=${entranceType}>
<input type="hidden" name="entrance" id="entrance" value=${entrance}>
<header class="con-header pull-in">
    <div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
</header>
    <!--start 查询条件-->
    <!-- <section class="panel m-t-md search-box clearfix">-->
    <section class="panel clearfix search-box search-shrinkage">
        <input type="hidden" id="modifyDate" name="modifyDate">
      <div class="search-line hide">
        <form class="table-wrap form-table" id="sendFinishTransactListForm">
            <table class="table table-td-striped">
                <tbody>
                    <tr>
                        <td class="w140">报销人</td>
                        <td style="width:40%">
                        	<div id="queryCreateUserTree"></div>
                        </td>
                       <td class="w140">报销部门</td>
                        <td style="width:40%">
                        <div id="reDeptTree"></div>
                        </td>
                    </tr>
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
                        <td class="w140">领款状态</td>
                        <td>
                        	<dic:select id="reStatus" name="reStatus" headName="-全部-" headValue="" dictName="reimbursement_reStatus">
                            </dic:select>
                        </td>
                        <%-- <td><%@ include file="/WEB-INF/web/include/workflowSearch.jsp"%></td> --%>
                    </tr>
                    
                </tbody>
            </table>
            <section class="form-btn m-b-lg">
                <button class="btn dark query-jump" type="button" id="querySendFinishTransact">查 询</button>
                <button class="btn" type="reset"  id="queryReset">重 置</button>
            </section>
        </form>
        </div>
        <%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%>
 </section>   
    <!--end 查询条件-->
    <!--start 查询结果-->
    <section class="panel clearfix" id="sendFinishTransact-list">
        <h2 class="panel-heading clearfix">查询结果</h2>
        <div class="table-wrap">
            <table class="table table-striped tab_height" id="sendFinishTransactTable">
                <thead>
                    <tr>
                         <th class="w170">编号</th>
                        <th class="w100">报销人员</th>
                        <th class="w100">报销部门</th>
                        <th class="w170">申请日期</th>
                        <th class="w100">报销金额合计(元)</th>
                        <th class="w100">出纳</th>
                        <th class="w100">经手人</th>
                        <th class="w100">领款人</th>
                        <th class="w100">领取状态</th>
                        <th>操作</th>
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


   <div class="modal fade panel" id="addFlows" aria-hidden="false">
        <div class="modal-dialog w900">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">领款</h4>
                </div>
                 <div class="modal-body">
                	  <div id="flowsDiv" class="table-wrap">
			                <table class="table table-striped first-td-tc tab_height" id="addExchangeFlowsTable">
			                  <!--   <thead>
			                        <tr>
			                            <th class="w100"></th>
			                            <th width="*"></th>
			                        </tr>
			                    </thead> -->
			                    <tbody>
			                       	<tr class="w100">
			                       	<td class="w200">出纳:</td>
			                       	<td>
			                       	<div id="querychunaTree"></div>
			                       	</td>
			                       	</tr>
			                       	<tr>
			                       	<td>经手人:</td>
			                       	<td>
			                       	<div id="queryjingshouTree"></div>
			                       	</td>
			                       	</tr>
			                       	<tr>
			                       	<td>领款人:</td>
			                       	<td>
			                       	<div id="querylingkuanTree"></div>
			                       	</td>
			                       	</tr>
			                    </tbody>
			                </table>
			            </div>
                </div>
                <div class="modal-footer no-all form-btn">
                    <button class="btn dark"  id="saveSelectedFlows">保 存</button><button class="btn" type="reset" id="flowClose">关 闭</button>
                </div>
            </div>
        </div>
    </div>
    
    
    <!--end 查询结果-->
    <%@ include file="/WEB-INF/web/include/foot.jsp"%>