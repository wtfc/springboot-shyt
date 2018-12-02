<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<!--查看任务弹出层Start-->
<div class="modal fade panel" id="viewTask" aria-hidden="false">
	
    <div class="modal-dialog" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">查看任务</h4>
            </div>
            <div class="modal-body">
            	<form id="taskBoxForm" name="taskBoxForm" class="table-wrap form-table">
                <div class="table-wrap form-table">
                    <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td style="width:15%">任务名称</td>
                                <td style="width:35%">
                                    <input type="text" id="taskNameShow" name="taskName"/>
                                </td>
                                <td style="width:15%">任务内容</td>
                                <td style="width:35%">
                                    <input type="text" id="content" name="content"/>
                                </td>
                            </tr>
                            <tr>
                                <td>开始时间</td>
                                <td>
                                    <div class="input-group w-p100">
                                        <input id="queryStartTime" name="startTime" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#queryEndTime lt">
                                        <div class="input-group-btn w30">-</div>
                                        <input id="queryEndTime" name="endTime" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#queryStartTime gt">
                                    </div>
                                </td>
                                <td>任务类型</td>
                                <td>
                                 	<label class="radio inline"> <input type="radio" id="taskType0" name="taskType" value="0" checked="checked" />发起人</label>
                                    <label class="radio inline m-l-md"><input type="radio" id="taskType1" name="taskType" value="1" />负责人</label>
                                </td> 
                            </tr>
                            <tr>
                            	 <td class="w140">任务类型</td>
                                 <td>
		                             <dic:select name="taskWorkType" id="taskWorkType-fqr" dictName="taskWorkType" headName="-全部-" headValue="" defaultValue=""/>       	
                                 </td>
                                 <td></td><td></td> 
                            </tr>
                        </tbody>
                    </table>
                </div>
                <section class="form-btn m-b m-t">
                    <button id="queryTaskBox" name="queryTaskBox" class="btn dark" type="button" onClick="taskRowDetail.taskRowDetailList()">查 询</button>
                    <button id="resetTaskBox" name="resetTaskBox" class="btn" type="button" onClick="taskRowDetail.resetTaskBoxList()">重 置</button>
                </section>
                <div class="table-wrap">
                    <table id="queryTaskTable" name="queryTaskTable" class="table table-striped">
                        <thead>
                            <tr>
                                <th class="w46"></th>
                                <th class="w46"></th>
                                <th style="width:150px;">任务名称</th>
                                <th class="w115">任务类型</th>
                                <th style="width:150px;">任务内容</th>
                                <th style="width:150px;">完成标准</th>
                                <th class="w115">发起人</th>
                                <th class="w115">负责人</th>
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
            <div class="modal-footer form-btn">
                <a id="importTask" name="importTask" class="btn dark" href="#" role="button" onClick="taskBoxFillData()">导入计划</a>
                <button class="btn" type="button" data-dismiss="modal">关 闭</button>
            </div>
        </div>
    </div>
    
</div>
<!--查看任务弹出层End-->