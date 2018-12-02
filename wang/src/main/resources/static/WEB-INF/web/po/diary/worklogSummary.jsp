<%@ page language="java" pageEncoding="UTF-8"%>
<!--  汇总弹窗-------->
<div class="modal fade panel" id="worklog_summary" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">汇总</h4>
            </div>
            <div class="modal-body">
                <form class="form-table" id="collectForm">
                    <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td class="w140">开始时间</td>
                                <td><input class="datepicker-input" type="text" id="worklogDateBegin"  data-ref-obj="#worklogDateEnd lt" name="worklogDateBegin"></td>
                            	<td class="w140">结束时间</td>
                            	<td><input class="datepicker-input" type="text" id="worklogDateEnd" data-ref-obj="#worklogDateBegin gt" name="worklogDateEnd"></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
                <section class="form-btn m-b-lg m-t-md">
                    <button class="btn dark" type="button" id="collectSearch">查 询</button>
                    <button class="btn" type="button" id="collectReset">重 置</button>
                </section>
                <table class="table table-striped tab_height" id="collectTable">
                    <thead>
                        <tr>
                            <th class="w140">日志日期</th>
                            <th>内容</th>
                            <th>感悟及备注</th>
                            <th class="w100">批注</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" id="exportExcel">导出至excel</button>
                <a class="btn" id="closeWorklogSummary" data-dismiss="modal">关 闭</a> 
            </div>
        </div>
    </div>
</div>