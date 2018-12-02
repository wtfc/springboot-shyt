<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/WorkflowHead.jsp"%>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script> 
<script src="${sysPath}/js/ext/finance/reimbursementForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/ext/finance/reimbursementForm.validate.js" type="text/javascript"></script>

<%-- <script src="${sysPath}/js/com/po/plan/planFormRowDetail.js"	type="text/javascript"></script>
<script src="${sysPath}/js/ext/finance/reimbursementAutoTable.js" type="text/javascript"></script>
<script src="${sysPath}/js/ext/finance/reimbursementGradeForm.js" type="text/javascript"></script> --%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu">
		<h1></h1>
		<div class="crumbs"></div>
	</div>
</header>
<!--start 申请单-->
<section class="panel m-t-md" id="body">
    <form id="reimbursementForm" class="table-wrap form-table">
    
    <!-- 流程相关 -->
    <input type="hidden" id="myBusinessUrl" value="/finance/reimbursement/reimbursementWaitList.action">
    <input type="hidden" id="todoPage" value="/ext/finance/reimbursement/reimbursementWaitTransact">
    
    	<input type="hidden" id="condition" name="condition">
    	<input type="hidden" id="deptment" name = "deptment" value="1" >
    	<input type="hidden" id="id" name="id" value="0">
       	<input type="hidden" id="token" name="token" value="0">
		<input type="hidden" id="modifyDate" name="modifyDate">
		<input type="hidden" id="flowStatus" name="flowStatus">
		<input id="gradeSum" name="gradeSum" type="hidden">
		<input id="reSunMoney" name="reSunMoney" type="hidden">
		<input id="reSunMoneyBig" name="reSunMoneyBig" type="hidden">
        <div class="table-wrap form-table">
            <table class="table table-td-striped">
                <tbody>
                    <tr>
                        <td style="width:15%;">报销人</td>
                        <td style="width:35%;" id="rePeoName">
                        </td>
                        <td style="width:15%;">报销部门</td>
                        <td id="reDeptName">
                        </td>
                    </tr>
                    <tr>
                    	<td><span workFlowForm="button" itemName="reDate" class="required">*</span>申请日期</td>
                        <td><span workFlowForm="textinput" itemName="reDate">
							<input type="text" id="reDate" name="reDate" value="" data-date-format="yyyy-MM-dd" class="datepicker-input"/>
                        	</span>
                        </td>
                        <td><span workFlowForm="button" itemName="reNum" class="required">*</span>编号</td>
               			<td id="reNum">
                        </td>
                    </tr>
                    <tr>
                        <td>项目名称</td>
                        <td colspan="3" workFlowForm="select" itemName="project">
                        	<dic:select id="project" name="project" headName="-请选择-" headValue="" dictName="reimbursement_project">
                            </dic:select>
                        </td>
                    </tr>
                    <tr>
                        <td>报销科目金额汇总（元）</td>
                        <td colspan="3" id="gradeSumText">
                        </td>
                    </tr>
                    <tr>
                        <td>报销金额合计（元）</td>
                        <td colspan="3" id="reSunMoneyText">
                        </td>
                    </tr>
                    <tr>
                        <td>核实金额（大写）</td>
                        <td colspan="3" id="reSunMoneyBigText">
                        </td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3">
	                    <input type="file" class="filestyle" data-icon="false" data-classbutton="btn btn-file input-group-btn" data-classinput="form-file" id="filestyle-0" style="position: fixed; left: -5000px;" />
						<span workFlowForm='button' itemName='queryattach'><a class="btn dark" type="button" href="#file-edit"  name="queryattach" id="queryattach"  role="button" data-toggle="modal">附件</a></span>
					 	<ul id="attachList"></ul>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
		<!-- 费用明细 -->
		<h2 class="panel-heading clearfix">费用明细</h2>
		<br>
			<table id="preSum" workFlowForm="autoRow" itemName="autoTableDec" class="table table-striped first-td-tc">
				<thead>
					<tr id="jy">
						<th style="width:5%">序号</th>
						<th >科目</th>
						<th >金额（小写）</th>
						<th >具体时间</th>
						<th >费用描述</th>
						<th >备注</th>
					</tr>
				</thead>

				<tbody id="abody">
					<tr id="tr1">
						<td >1</td>
						<td ><dic:select id="subject1" name="subject1"  onchange="sumOpt()" headName="-请选择-" headValue="" dictName="reimbursement_subject"></dic:select></td>
						<td ><input type="text" id="money1" name="money1" onchange="sumMoney()"></td>
						<td ><input type="text" id="date1" name="date1" data-date-format="yyyy-MM-dd" class="datepicker-input"/></td>
						<td ><input type="text" id="discribe1" name="discribe1"></td>
						<td ><input type="text" id="remark1" name="remark1"></td>
					</tr>
					<tr id="tr2">
						<td >2</td>
						<td ><dic:select id="subject2" name="subject2" onchange="sumOpt()" headName="-请选择-" headValue="" dictName="reimbursement_subject"></dic:select></td>
						<td ><input type="text" id="money2" name="money2" onchange="sumMoney()"></td>
						<td ><input type="text" id="date2" name="date2" data-date-format="yyyy-MM-dd" class="datepicker-input"/></td>
						<td ><input type="text" id="discribe2" name="discribe2"></td>
						<td ><input type="text" id="remark2" name="remark2"></td>
					</tr>
					<tr id="tr3">
						<td >3</td>
						<td ><dic:select id="subject3" name="subject3" onchange="sumOpt()" headName="-请选择-" headValue="" dictName="reimbursement_subject"></dic:select></td>
						<td ><input type="text" id="money3" name="money3" onchange="sumMoney()"></td>
						<td ><input type="text" id="date3" name="date3" data-date-format="yyyy-MM-dd" class="datepicker-input"/></td>
						<td ><input type="text" id="discribe3" name="discribe3"></td>
						<td ><input type="text" id="remark3" name="remark3"></td>
					</tr>
					<tr id="tr4">
						<td >4</td>
						<td ><dic:select id="subject4" name="subject4" onchange="sumOpt()" headName="-请选择-" headValue="" dictName="reimbursement_subject"></dic:select></td>
						<td ><input type="text" id="money4" name="money4" onchange="sumMoney()"></td>
						<td ><input type="text" id="date4" name="date4" data-date-format="yyyy-MM-dd" class="datepicker-input"/></td>
						<td ><input type="text" id="discribe4" name="discribe4"></td>
						<td ><input type="text" id="remark4" name="remark4"></td>
					</tr>
					<tr id="tr5">
						<td >5</td>
						<td ><dic:select id="subject5" name="subject5" onchange="sumOpt()" headName="-请选择-" headValue="" dictName="reimbursement_subject"></dic:select></td>
						<td ><input type="text" id="money5" name="money5" onchange="sumMoney()"></td>
						<td ><input type="text" id="date5" name="date5" data-date-format="yyyy-MM-dd" class="datepicker-input"/></td>
						<td ><input type="text" id="discribe5" name="discribe5"></td>
						<td ><input type="text" id="remark5" name="remark5"></td>
					</tr>
				</tbody>

			</table>
		<!--  费用明细 -->
        <h2 class="panel-heading clearfix">部门主管审批</h2>
        <section class="input-table-group table-wrap pzhf w-p100">
	       	<section workflowSuggest="true" class="m-r-lg" showLast="false" order="createTime" id="bumenzhuguanTextareaContent">
			</section>
		</section>
        <h2 class="panel-heading clearfix">领导审批</h2>
         <section class="input-table-group table-wrap pzhf w-p100">
       		<section workflowSuggest="true" class="m-r-lg" showLast="false" order="createTime" id="lingdaoTextareaContent">
			</section>
		</section>
        <h2 class="panel-heading clearfix">财务审批意见</h2>
        <section class="input-table-group table-wrap pzhf w-p100">
	        <section workflowSuggest="true" class="m-r-lg" showLast="false" order="createTime" id="caiwuTextareaContent">
			</section>
		</section>
     </form>
     <%@ include file="/WEB-INF/web/include/WorkflowButton.jsp"%>
</section>
<%@ include file="/WEB-INF/web/include/WorkflowPostscript.jsp"%> 
</section>
<!--end 申请单-->
<!--start 上传附件  -->
<div class="modal fade panel" id="file-edit" aria-hidden="false">
	<div class="modal-dialog w820">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
				<h4 class="modal-title">添加上传文件</h4>
			</div>
			<div class="modal-body">
			<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
				<!-- islogicDel 1为逻辑删除 0为物理删除-->
				<input type="hidden" name="showDelBtn" id="showDelBtn" value="1"/>
				<input type="hidden" id="islogicDel" value="1">
				<input type="hidden" name="businessId" id="businessId" value="0"/>
				<input type="hidden" name="businessSource" id="businessSource"/>
				<input type="hidden" name="businessTable" id="businessTable"  value="toa_finance_reimbursement"/> 
				<!-- upload_type 1为单传  0为多传-->
                <input type="hidden" id="upload_type" value="0"> 
                <input type="hidden" id="isshow" value="0"> 
                <input type="hidden" id="iscopy" value="0"> 
				<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
				<button class="btn dark" type="button" id="closeModalBtn" data-dismiss="modal">关 闭</button>
			</div>
		</div>
	</div>
</div>
<!--end 上传附件 -->
<%@ include file="/WEB-INF/web/include/WorkflowFoot.jsp"%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>