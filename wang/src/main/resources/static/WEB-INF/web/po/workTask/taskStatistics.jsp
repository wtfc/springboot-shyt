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
   <section class="panel m-t-md">
    <section class="panel clearfix search-box search-shrinkage">
	 <div class="search-line hide">
	         <h2 class="panel-heading clearfix">任务统计</h2>
	         <form class="table-wrap form-table" id="taskTotal">
	         	 <input type="hidden" id="directorId" name="directorId" value="${userId}"/>
	         	 <input type="hidden" id="startTimeTemp" name="startTimeTemp"/>
	         	 <input type="hidden" id="endTimeTemp" name="endTimeTemp"/>
	             <table class="table table-td-striped">
	                 <tbody>
	                     <tr>
	                         <td class="w140">开始时间</td>
	                         <td style="width:40%;">
	                             <div class="input-group w-p100">
	                                 <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="startTime" name="startTime" data-ref-obj="#endTime lt"/>
	                                 <div class="input-group-btn w30">-</div>
	                                 <input class="datepicker-input" type="text"  data-date-format="yyyy-MM-dd" id="endTime" name="endTime" data-ref-obj="#startTime gt"/>
	                             </div>
	                         </td>
	                         <td></td>
	                         <td></td>
	                     </tr>
	                 </tbody>
	             </table>
	             <section class="form-btn m-b-lg">
	                 <button class="btn dark" type="button" id="queryTask">查 询</button>
	                 <button class="btn" type="reset" id="queryReset">重 置</button>
	             </section>
	         </form>
        </div>
        <%@include file= "/WEB-INF/web/include/searchConditionHide.jsp"%>
        <section class="clearfix" id="insideIn-list">
            <h2 class="panel-heading clearfix">查询结果</h2>
            <div class="table-wrap">
                <table class="table table-striped  tab_height" id="queryTaskTotal">
                    <thead>
                        <tr>
                            <th>待接收任务</th>
                            <th>进行中任务</th>
                            <th>催办任务</th>
                            <th>延期任务</th>
                            <th>超期任务</th>
                            <th>已完成任务</th>
                        </tr>
                    </thead>
                    <tbody> 
                    </tbody>
                </table>
            </div>
            <section class="pagination m-r fr m-t-none">    
            </section>
        </section>
    </section>
    </section>
  </section>
   <!--start 任务统计 弹出层-->
   <div class="modal fade panel" id="taskArrangement" aria-hidden="false">
       <div class="modal-dialog w900">
           <div class="modal-content">
               <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal">×</button>
                   <h4 class="modal-title">任务统计</h4>
               </div>
               <div class="modal-body">
                   <div class="table-wrap">
                       <table class="table table-striped" id="workTaskTable">
                           <thead>
                               <tr>
                                   <th class="w100">任务名称</th>
                                   <th class="w50">任务进度</th>
                                   <th class="w100">上级任务</th>
                                   <th class="w50">发起人</th>
                                   <th class="w50">负责人</th>
                                   <th class="w50">开始时间</th>
                                   <th class="w50">结束时间</th>
                               </tr>
                           </thead>
                           <tbody>
                               
                           </tbody>
                       </table>
                   </div>
               </div>
               <div class="modal-footer  form-btn">
                   <button class="btn dark" type="submit" data-dismiss="modal">关 闭</button>
               </div>
           </div>
       </div>
   </div>
   <!--end 任务统计 弹出层-->
   <!--start 任务统计 弹出层-->
   <div class="modal fade panel" id="taskArrangementFi" aria-hidden="false">
       <div class="modal-dialog w900">
           <div class="modal-content">
               <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal">×</button>
                   <h4 class="modal-title">任务统计</h4>
               </div>
               <div class="modal-body">
                   <div class="table-wrap">
                       <table class="table table-striped" id="workTaskTableFi">
                           <thead>
                               <tr>
                                   <th>任务名称</th>
                                   <th>任务进度</th>
                                   <th>上级任务</th>
                                   <th>发起人</th>
                                   <th>负责人</th>
                                   <th>开始时间</th>
                                   <th>结束时间</th>
                               </tr>
                           </thead>
                           <tbody>
                               
                           </tbody>
                       </table>
                   </div>
               </div>
               <div class="modal-footer  form-btn">
                   <button class="btn dark" type="submit" data-dismiss="modal">关 闭</button>
               </div>
           </div>
       </div>
   </div>
   <!--end 任务统计 弹出层-->
<script src="${sysPath}/js/com/po/workTask/taskStatistics.js" type="text/javascript"></script>
<%@include file="/WEB-INF/web/include/foot.jsp"%>