<%@ page language="java" pageEncoding="UTF-8"%>
<!--  start 日程汇总 弹出层-------->
<div class="modal fade panel" id="Program-summary" aria-hidden="false">
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">日程汇总</h4>
            </div>
            <div class="modal-body">
                <div class="form-table">
                    <table class="table table-td-striped">
                        <tbody>
                            <tr>
                                <td class="w140">日程时间</td>
                                <td>
                                    <div class="input-group w-p100">
                                        <input class="datepicker-input" id="starttimeBegin" name="starttimeBegin" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#starttimeEnd lt"><div class="input-group-btn w30"> -</div><input class="datepicker-input" id="starttimeEnd" name="starttimeEnd" type="text" data-date-format="yyyy-MM-dd" data-ref-obj="#starttimeBegin gt">
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <section class="form-btn m-b-lg m-t-md">
                    <a class="btn dark" id="searchDiarySummary">查 询</a>
                    <a class="btn" id="searchDiarySummaryReset">重 置</a>
                </section>
                <table class="table table-striped first-td-tc tab_height" id="summaryTable">
                    <thead>
                        <tr>
                            <th style="width:10%;">填写人</th>
                            <th style="width:40%">日程时间</th>
                            <th style="width:40%">日程内容</th>
                            <th style="width:10%;">批注</th>
                        </tr>
                    </thead>
                    <tbody id="innerTable">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer form-btn">
            	<form id="excelForm" method="post">
                <a class="btn dark" id="exportExcelDiary">导出至excel</a>
                <a class="btn" id="closeSummary" data-dismiss="modal">关 闭</a> 
                </form>
            </div>
        </div>
    </div>
</div>
<!--  end 日程汇总 弹出层-------->