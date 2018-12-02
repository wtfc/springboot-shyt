<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
</header>
<!-- 流程相关项 -->
<input type="hidden" name="entranceType" id="entranceType" value=${entranceTyp} >
<input type="hidden" name="entrance" id="entrance" value=${entrance}>

<section class="panel clearfix search-box search-shrinkage" id="body">
	<div class="search-line hide">
    <form class="table-wrap form-table" id="reqQueryForm">
        <table class="table table-td-striped">
            <tbody>
                <tr>
                   <td class="w140">申请类别</td>
                   <td style="width:40%;">
               			<select id="applyType" name="applyType">
               				<option value="">-全部-</option>
               				<option value="0">电子类教学设备</option>
               				<option value="1">固定厂家</option>
               				<option value="2">非固定厂家</option>
               			</select>
               	   </td>
               	   <td class="w140">申请日期</td>
	               <td>
	               		<div class="input-group w-p100">
							<input type="text" id="createDateBegin" name="createDateBegin" class="datepicker-input" data-date-format="yyyy-MM-dd" data-ref-obj="#createDateEnd lt">
							<div class="input-group-btn w30"> -</div>
							<input type="text" id="createDateEnd" name="createDateEnd" class="datepicker-input" data-date-format="yyyy-MM-dd" data-ref-obj="#createDateBegin gt">
						</div>
	               </td>
           		</tr>
          		<tr>
          			<td>预算总价</td>
	               <td>
                       	<input id="budget" name="budget" type="text">
	               </td>
	               <td >流程状态</td>
	               <td >
	                   <%@ include file="/WEB-INF/web/include/workflowSearch.jsp"%>
	               </td>
          			
           		</tr>
       		</tbody>
          </table>
          <section class="form-btn m-b-lg">
              <button class="btn dark" type="button" id="queryReq">查 询</button>
              <button class="btn" type="button" id="queryReset">重 置</button>
          </section>
     </form>
     </div>
	<%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%>
     
</section>
<section class="panel clearfix" id="IP-edit">
    <h2 class="panel-heading clearfix">查询结果</h2>
    <div class="table-wrap">
        <table class="table table-striped tab_height" id="reqApplyTable">
            <thead>
                <tr>
                	<th>申请类别</th>
                	<th class="w100">申请人</th>
                	<th class="w100">申请部门</th>
                    <th class="w100">预算总价</th>
                    <th class="w170">申请日期</th>
                    <th class="w115">流程状态</th>
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
          <!-- 分页信息 -->
            </section>
</section>      
</section>
<script src="${sysPath}/js/ext/pur/req/reqApplyList.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>