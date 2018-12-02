<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<!--查看计划弹出层Start-->
<div class="modal fade panel" id="viewPlan" aria-hidden="false">
	
    <div class="modal-dialog" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">查看计划</h4>
            </div>
            <div class="modal-body">
            	<form id="planBoxForm" name="planBoxForm" class="table-wrap form-table">
                <div class="table-wrap form-table">
                    <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td>标题</td>
                                <td colspan="3"><input id="planTitleBox" name="planTitle" type="text"></td>
                            </tr>
                            <tr>
                                <td class="w140">计划类型</td>
                                <td style="width:35%">
		                           <label class="checkbox inline">
		                               <input type="checkbox" id="weekPlanType" name="planType" value="0">周计划
		                           </label>
		                           <label class="checkbox inline">
		                               <input type="checkbox" id="monthPlanType" name="planType" value="1">月计划
		                           </label>
		                           <label class="checkbox inline">
		                               <input type="checkbox" id="yearPlanType" name="planType" value="2">年计划
		                           </label>
                                </td class="w140">
                                <td>开始时间</td>
                                <td>
                                    <div class="input-group w-p100">
                                        <input id="planStartTimeBegin" name="planStartTimeBegin" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeEnd lt">
                                        <div class="input-group-btn w30">-</div>
                                        <input id="planStartTimeEnd" name="planStartTimeEnd" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#planStartTimeBegin gt">
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <section class=" form-btn m-b m-t">
                    <button id="queryPlanBox" name="queryPlanBox" class="btn dark" type="button" onClick="planRowDetail.planRowDetailList()">查 询</button>
                    <button id="resetPlanBox" name="resetPlanBox" class="btn" type="button" onClick="planRowDetail.resetPlanBoxList()">重 置</button>
                </section>
                <div class="table-wrap">
                    <table id="queryPlanTable" name="queryPlanTable"class="table table-striped">
                        <thead>
                            <tr>
                                <th class="w46"></th>
                                <th>计划类型</th>
                                <th>标题</th>
                                <th class="w115">开始时间</th>
                                <th class="w115">结束时间</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                 </form>
            </div>
            
            <div class="modal-footer  form-btn">
				<a id="importPlanBoxBtn" name="importPlanBoxBtn" class="btn dark" href="#" onclick="planBoxFillData()">导入计划</a>
				<button class="btn" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
   
</div>
<!--查看计划弹出层End-->