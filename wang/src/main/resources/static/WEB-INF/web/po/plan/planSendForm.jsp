<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="jcGOA">
<head>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/treeTable.jsp"%>
</head>

<body>
<header class="con-header pull-in">
    <div class="con-heading fl">
        <h1>计划编制</h1>
    </div>
    <div class="fr con-header-time">
        <ul>
            <li>
                <button class="tabsTime active">
                    <a href="#"><i class="fa fa-caret-left"></i></a>
                    <span>周</span>
                    <a href="#"><i class="fa fa-caret-right"></i></a>
                </button>
            </li>
            <li>
                <button class="tabsTime">
                <a href="#"><i class="fa fa-caret-left"></i></a>
                <span>月</span>
                <a href="#"><i class="fa fa-caret-right"></i></a>
                </button>
            </li>
            <li>
                <button class="tabsTime">
                <a href="#"><i class="fa fa-caret-left"></i></a>
                <span>年</span>
                <a href="#"><i class="fa fa-caret-right"></i></a>
                </button>
            </li>
        </ul>
    </div>
</header>

<form class="clearfix" id="planForm">
	<input type="hidden" id="id" name="id" value="0">
    <input type="hidden" id="token" name="token" value='${token}'>
    <input type="hidden" id="modifyDate" name="modifyDate">
	<input type="hidden" id="templateType" name="templateType" value="0">
	<input type="hidden" id="planState" name="planState" value="1">

    <section class="row-fluid m-t-md">
        <div class="span6 cbox bg-red">
            <div class="cbox-head">计划标题</div>
            <div class="cbox-con">
                <span class="input-style">
                    <input type="text" id="planTitle" name="planTitle">
                </span>
            </div>
        </div>
        <div class="span2 cbox bg-yellow-dark">
            <div class="cbox-head">计划时间</div>
            <div class="cbox-con">
                <span class="input-style">
                    <input class="datepicker-input" type="text" id="planStartTime" name="planStartTime" data-date-format="yyyy-mm-dd" >
                </span>
            </div>
        </div>
        <div class="span2 cbox bg-green">
            <div class="cbox-head">编制人</div>
            <div class="cbox-con">
                <span class="input-style">
                    <input type="text" id="applyId" name="applyId">
                </span>
            </div>
        </div>
        <div class="span2 cbox bg-blue">
            <div class="cbox-head">所属部门</div>
            <div class="cbox-con">
                <span class="input-style">
                    <input type="text" id="applyDeptid" name="applyDeptid">
                </span>
            </div>
        </div>
    </section>
    
    <section class="tabs-wrap m-t-md">
        <ul class="nav nav-tabs">
            <li class="active">
                <a href="#" class="planning-jy abtn">简易模板</a>
            </li>
            <li class="">
                <a href="#" class="planning-bz abtn">标准模板</a>
            </li>
        </ul>
    </section>

    <section class="panel">
        <div class="panel-heading clearfix">
            <h2>2013年2月份工作总结</h2>
            <div class="fr heading-btn">
                <a href="#" class="btn" role="button" >导入上期计划</a>
            </div>
        </div>
        <div class="table-wrap form-table-h input-textarea">
            <table id="prePlan" class="table table-striped first-td-tc">
                <thead>
                    <tr>
                        <th class="w46">序号</th>
                        <th>主要完成事项</th>
                        <th style="width:10%;">完成标准</th>
                        <th style="width:8%;">负责人</th>
                        <th style="width:11%;">计划开始时间</th>
                        <th style="width:11%;">计划完成时间</th>
                        <th class="w100">完成比例</th>
                        <th>未完成原因说明</th>
                        <th class="w200">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td><span class="input-style"><input type="text" value="继续重新审核2012年度全部凭证" class="down-area"></span></td>
                        <td><span class="input-style"><input type="text" value=""></span></td>
                        <td><span class="input-style"><input type="text" value="刘锡来" class="fzrOnfocus"></span></td>
                        <td><span class="input-style"><input type="text" value="2013-06-12" data-date-format="yyyy-mm-dd" class="datepicker-input"></span></td>
                        <td><span class="input-style"><input type="text" value="2013-06-30" data-date-format="yyyy-mm-dd" class="datepicker-input"></span></td>
                        <td>
                            <div class="input-group w-p100">
                                <div class="input-group-btn input-style">
                                    <input type="text" placeholder="0-100" class="tr">
                                </div>
                                <div class="input-group-btn">%</div>
                            </div>
                        </td>
                        <td><span class="input-style"><input type="text" value=""></span></td>
                        <td>
                            <a class="a-icon i-remove m-r-xs" href="#">
                                <i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td><span class="input-style"><input type="text" class="down-area" value="审核XX检察院预算"></span></td>
                        <td><span class="input-style"><input type="text" value=""></span></td>
                        <td><span class="input-style"><input type="text" value="王强" class="fzrOnfocus"></span></td>
                        <td><span class="input-style"><input type="text" value="2013-06-12" data-date-format="yyyy-mm-dd" class="datepicker-input"></span></td>
                        <td><span class="input-style"><input type="text" value="2013-06-30" data-date-format="yyyy-mm-dd" class="datepicker-input"></span></td>
                        <td>
                            <div class="input-group w-p100">
                                <div class="input-group-btn input-style">
                                    <input type="text" placeholder="0-100" class="tr">
                                </div>
                                <div class="input-group-btn">%</div>
                            </div>
                        </td>
                        <td><span class="input-style"><input type="text" value=""></span></td>
                        <td>
                            <a class="a-icon i-new m-r-xs" href="#myModal-edit" role="button">
                                <i class="fa fa-plus"></i>加入计划
                            </a>
                            <a class="a-icon i-remove m-r-xs" href="#">
                                <i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td><span class="input-style"><input type="text" value="核对XX团队业务经理业绩表"></span></td>
                        <td><span class="input-style"><input type="text" value=""></span></td>
                        <td><span class="input-style"><input type="text" value="张卓一" class="fzrOnfocus"></span></td>
                        <td><span class="input-style"><input type="text" value="2013-06-12" data-date-format="yyyy-mm-dd" class="datepicker-input"></span></td>
                        <td><span class="input-style"><input type="text" value="2013-06-30" data-date-format="yyyy-mm-dd" class="datepicker-input"></span></td>
                        <td>
                            <div class="input-group w-p100">
                                <div class="input-group-btn input-style">
                                    <input type="text" placeholder="0-100" class="tr">
                                </div>
                                <div class="input-group-btn">%</div>
                            </div>
                        </td>
                        <td><span class="input-style"><input type="text" value=""></span></td>
                        <td>
                            <a class="a-icon i-new m-r-xs" href="#myModal-edit" role="button">
                                <i class="fa fa-plus"></i>加入计划
                            </a>
                            <a class="a-icon i-remove m-r-xs" href="#">
                                <i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>
                            <span class="input-style">
                                <input type="text" placeholder="请填写...">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text" class="fzrOnfocus">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <div class="input-group w-p100">
                                <div class="input-group-btn input-style">
                                    <input type="text" placeholder="0-100" class="tr">
                                </div>
                                <div class="input-group-btn">%</div>
                            </div>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <a class="a-icon i-new m-r-xs" href="#myModal-edit" role="button">
                                <i class="fa fa-plus"></i>加入计划
                            </a>
                            <a class="a-icon i-remove m-r-xs" href="#">
                                <i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>
                            <span class="input-style">
                                <input type="text" placeholder="请填写...">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text" class="fzrOnfocus">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <div class="input-group w-p100">
                                <div class="input-group-btn input-style">
                                    <input type="text" placeholder="0-100" class="tr">
                                </div>
                                <div class="input-group-btn">%</div>
                            </div>
                        </td>
                        <td>
                            <span class="input-style">
                                <input type="text">
                            </span>
                        </td>
                        <td>
                            <a class="a-icon i-new m-r-xs" href="#myModal-edit" role="button">
                                <i class="fa fa-plus"></i>加入计划
                            </a>
                            <a class="a-icon i-remove m-r-xs" href="#">
                                <i class="fa fa-remove" data-toggle="tooltip" data-placement="top" title="" data-container="body" data-original-title="删除"></i>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <section class="table-wrap">
            <textarea rows="3" placeholder="备注..."></textarea>
        </section>
        <div class="table-wrap form-table planning-jyTbz hide">
            <table class="table table-td-striped">
                <tbody>
                    <tr>
                        <td class="w170">管理及创新</td>
                        <td><textarea rows="3"></textarea></td>
                    </tr>
                    <tr>
                        <td>成本控制及节约</td>
                        <td><textarea rows="3"></textarea></td>
                    </tr>
                    <tr>
                        <td>培训总结</td>
                        <td><textarea rows="3"></textarea></td>
                    </tr>
                    <tr>
                        <td>存在问题及解决措施</td>
                        <td>
                            <div class="input-group w-p100">
                                <div class="btn-in-area">
                                     <textarea rows="3"></textarea>
                                </div>
                                <div class="input-group-btn p-l">
                                    <a class="a-icon i-new m-r-xs" type="button" href="#selection-plan" role="button" data-toggle="modal"><i class="fa fa-libreoffice"></i>选择计划</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="panel-heading clearfix">
            <h2>2013年3月份工作计划</h2>
            <div class="fr heading-btn">
                <a href="#check-program" class="btn" role="button" data-toggle="modal">查看计划</a>
                <a href="#view-the-task" class="btn" role="button" data-toggle="modal">查看任务</a>
            </div>
        </div>
        <div class="table-wrap form-table-h">
            <table class="table table-striped first-td-tc">
                <thead>
                    <tr>
                        <th class="w46">序号</th>
                        <th>主要完成事项</th>
                        <th style="width:10%;">完成标准</th>
                        <th style="width:8%;">负责人</th>
                        <th style="width:11%;">计划开始时间</th>
                        <th style="width:11%;">计划完成时间</th>
                        <th class="w100">完成比例</th>
                        <th class="w140">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td><span class="input-style"><input type="text" placeholder="请填写..."></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text" class="tr"></span></td>
                        <td><label class="checkbox inline"><input type="checkbox">导入日程</label></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td><span class="input-style"><input type="text" placeholder="请填写..."></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text" class="tr"></span></td>
                        <td><label class="checkbox inline"><input type="checkbox">导入日程</label></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td><span class="input-style"><input type="text" placeholder="请填写..."></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text"></span></td>
                        <td><span class="input-style"><input type="text" class="tr"></span></td>
                        <td><label class="checkbox inline"><input type="checkbox">导入日程</label></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </section>
    <section class="no-all form-btn m-b-lg">
    	<button class="btn dark" type="button" id="saveTempBtn">暂 存</button>
        <button class="btn dark" type="button" id="saveBtn">提 交</button>
    </section>
</form>

<div class="modal fade panel" id="myModal" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">人员选择</h4>
            </div>
            <div class="modal-body">...</div>
            <div class="modal-footer  form-btn">
                <button class="btn dark">保 存</input>
                <button class="btn" type="reset">重 置</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
