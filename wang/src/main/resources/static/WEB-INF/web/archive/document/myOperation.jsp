<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
<script src="${sysPath}/js/lib/common/selectControlNew.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/oa/archive/myOperation.js"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
</header>
<section class="panel clearfix search-box search-shrinkage">
<div class="search-line hide">
   <form id="audihhisForm" class="table-wrap form-table">
       <table class="table table-td-striped">
           <tbody>
               <tr>
                   <td class="w40" style ="width:60px;">操作类型</td>
                   <td class="w50" style ="width:200px;">
				    <select name="auditType" id="auditType">
						<option value="">-全部-</option>
						<option value="1">浏览</option>
						<option value="2">新建/上传/下载</option>
						<option value="3">编辑</option>
						<option value="4">删除</option>
						<option value="5">复制/剪切</option>
						<option value="7">收藏</option>
						<option value="10">文档关联</option>
						<option value="11">授权</option>
						<option value="12">回收站</option>
					</select>
					</td>
					<td class="w50" style ="width:60px;">名称</td>
                   <td class="w50" style ="width:200px;">
				   		<input id="dataName" name="dataName" type="text">
					</td>
               </tr>
               <tr>
                   <td class="w50" style ="width:60px;">类型</td>
                   <td class="w50" style ="width:200px;">
				    <select name="dataType" id="dataType">
						<option value="">-全部-</option>
						<option value="0">文件夹</option>
						<option value="1">文档</option>
					</select>
                   </td>
                   <td class="w50" style ="width:60px;">操作IP</td>
                   <td class="w50" style ="width:200px;">
				   		<input id="ip" name="ip" type="text">
					</td>
               </tr>
               <tr>
                   <td class="w50" style ="width:60px;">操作时间</td>
                   <td class="w50" style ="width:200px;">
                        <div class="input-group w-p100">
                            <input  class="datepicker-input" type="text" name="startDate" id="startDate"  data-date-format="yyyy-MM-dd" data-ref-obj="#endDate lt">
                            <span class="input-group-btn w30">-</span>
                            <input class="datepicker-input" type="text" name="endDate" id="endDate"  data-date-format="yyyy-MM-dd" data-ref-obj="#startDate gt">
                        </div>
					</td>
					<td class="w50" style ="width:60px;">事件描述</td>
                   <td class="w50" style ="width:200px;">
				   		<input id="operDesc" name="operDesc" type="text">
					</td>
               </tr>
           </tbody>
       </table>
       <section class="form-btn m-b-lg">
           <button id="audithisBtn" name="audithisBtn" class="btn dark query-jump" type="button">查 询</button>
           <button id="audithisResetBtn" name="audithisResetBtn" class="btn" type="button">重 置</button>
       </section>
   </form>
   </div>
   <%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%>
   </section>   
   <section class="panel clearfix">
	   <h2 class="panel-heading clearfix">查询结果</h2>
	   <div class="table-wrap">
	       <table id="audithisList" class="table table-striped tab_height">
	           <thead>
	               <tr>
	                   <th class="w100" style="width:24%;">名称</th>
	                   <!-- <th class="w100" style="width:6%;">操作人员</th> -->
	                   <th class="w100" style="width:12%;">操作类型</th>
	                   <th class="w100" style="width:6%;">类型</th>
	                   <th class="w100" style="width:14%;">操作时间</th>
	                   <th class="w100" style="width:10%;">操作IP</th>
	                   <th class="w100" style="width:28%;">事件描述</th>
	               </tr>
	           </thead>
	           <tbody>
	           </tbody>
	       </table>
	   </div>
	</section>
</section>
</html>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>