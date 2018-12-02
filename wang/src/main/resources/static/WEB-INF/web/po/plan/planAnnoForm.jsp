<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<section style="margin-left:155px;">
    <input type="hidden" id="flowStatus" name="flowStatus">
	<input type="hidden" id="id" name="id">
    <input type="hidden" id="token" name="token">
    <input type="hidden" id="modifyDate" name="modifyDate">
    <input type="hidden" id="applyId" name="applyId">
    <input type="hidden" id="applyDeptid" name="applyDeptid">
 	<input type="hidden" id="applyUserId" name="applyUserId">
 	
	<section class="row-fluid m-t-md">
	       <div class="span4 cbox bg-white">
	       <div class="cbox-head">计划标题</div>
	           <div class="cbox-con" workFlowForm="textinput" itemName="planTitle" id="planTitleOther">
	               <span class="input-style">
	                   <input type="text" id="planTitle" name="planTitle">	
	               </span>
	           </div>
	       </div>
	       <div class="span2 cbox bg-white">
	           <div class="cbox-head">计划开始时间</div>
	           <div class="cbox-con" workFlowForm="textinput" itemName="planStartTime" id="planStartTimeOther">
	               <span class="input-style">
	                   <input class="datepicker-input" data-date-format="yyyy-MM-dd" type="text" id="planStartTime" name="planStartTime" data-ref-obj="#planEndTime lt">
	               </span>
	           </div>
	       </div>
	       <div class="span2 cbox bg-white">
	           <div class="cbox-head">计划结束时间</div>
	           <div class="cbox-con" workFlowForm="textinput" itemName="planEndTime" id="planEndTimeOther">
	               <span class="input-style">
	                   <input class="datepicker-input" data-date-format="yyyy-MM-dd" type="text" id="planEndTime" name="planEndTime" data-ref-obj="#planStartTime gt">
	               </span>
	           </div>
	       </div>
	       <div class="span2 cbox bg-white">
	           <div class="cbox-head">编制人</div>
	           <div class="cbox-con" workFlowForm="textinput" itemName="applyIdValue" id="applyIdValueOther">
	               <span class="input-style">
	                   <input type="text" id="applyIdValue" name="applyIdValue" readonly="readonly">
	               </span>
	           </div>
	       </div>
	       <div class="span2 cbox bg-white">
	           <div class="cbox-head">编制部门</div>
	           <div class="cbox-con" workFlowForm="textinput" itemName="applyDeptidValue" id="applyDeptidValueValueOther">
	               <span class="input-style">
	                   <input type="text" id="applyDeptidValue" name="applyDeptidValue" readonly="readonly">
	               </span>
	           </div>
	       </div>
	</section>

   <section class="tabs-wrap m-t-md">
       <ul class="nav nav-tabs">
           <li id="jyPlanId" class="active">
               <a href="#" class="planning-jy abtn">简易模板</a>
           </li>
           <li id="bzPlanId" class="">
               <a href="#" class="planning-bz abtn">标准模板</a>
           </li>
       </ul>
   </section>

   <section class="panel">
       <div class="panel-heading clearfix">
            <h2>
            	<div id="otherSpanSumDiv">
<!--            	<span workFlowForm="textinput" itemName="sumSubmitYear"><input type="text" id="sumSubmitYear" name="sumSubmitYear"></span>年 -->
<!--            	<span id="sumSubmitYearSpan" name="sumSubmitYearSpan"></span> -->
<!--            	<span workFlowForm="textinput" itemName="sumSubmitMW"><input type="text" id="sumSubmitMW" name="sumSubmitMW" ></span> -->
<!--            	<span id="sumSubmitMWSpan" name="sumSubmitMWSpan"></span>工作计划 -->
            	</div>
            </h2>
       </div>
       
       <div class="table-wrap form-table-h input-textarea plan-table">
           <table id="preSum" workFlowForm="autoRow" itemName="autoSumTable" class="table table-striped first-td-tc"> 
               <thead>
                   <tr>
                       <th class="w46">序号</th>
                       <th style="width:16%;">主要完成事项</th>
                       <th style="width:16%;">完成标准</th>
                       <th>负责人</th>
                       <th style="width:120px;">开始时间</th>
                       <th style="width:120px;">完成时间</th>
                       <th style="width:100px;">比例(%)</th>
                       <th style="width:16%;">未完成原因说明</th>
                       <th operate="true" style="width:80px;">操作</th>
                   </tr>
               </thead>
               <tbody>
               </tbody>
           </table>
       </div>
       
       <section class="table-wrap form-table">
           <table class="table table-td-striped first-td-tc">
               <tbody>
                   <tr>
                       <td class="w140">备注</td>
                       <td><textarea rows="3" workFlowForm="textinput" itemName="remark" id="remark" name="remark"></textarea></td>
                   </tr>
               </tbody>
        </table>
       </section>
       
       <div class="table-wrap form-table planning-jyTbz hide">
           <table class="table table-td-striped first-td-tc">
               <tbody>
                   <tr>
                       <td class="w170">管理及创新</td>
                       <td><textarea rows="3" workFlowForm="textinput" itemName="manaInno" id="manaInno" name="manaInno"></textarea></td>
                   </tr>
                   <tr>
                       <td>成本控制及节约</td>
                       <td><textarea rows="3" workFlowForm="textinput" itemName="costControl" id="costControl" name="costControl"></textarea></td>
                   </tr>
                   <tr>
                       <td>培训总结</td>
                       <td><textarea rows="3" workFlowForm="textinput" itemName="trainingSum" id="trainingSum" name="trainingSum"></textarea></td>
                   </tr>
                   <tr>
                       <td>存在问题及解决措施</td>
                       <td><textarea rows="3" workFlowForm="textinput" itemName="problemMeas" id="problemMeas" name="problemMeas"></textarea></td>
                   </tr>
               </tbody>
           </table>
       </div>
       
       <div class="panel-heading clearfix">
           <h2>
           	  <div id="otherSpanPlanDiv">
<!--        		<span workFlowForm="textinput" itemName="planSubmitYear"><input type="text" id="planSubmitYear" name="planSubmitYear"></span>年 -->
<!--        		<span id="planSubmitYearSpan" name="planSubmitYearSpan"></span> -->
<!--        		<span workFlowForm="textinput" itemName="planSubmitMW"><input type="text" id="planSubmitMW" name="planSubmitMW"></span> -->
<!--        		<span id="planSubmitMWSpan" name="planSubmitMWSpan"></span>工作计划 -->
       	      </div>
       	   </h2>
       </div>
       
       <div class="table-wrap form-table-h input-textarea">
           <table id="prePlan" workFlowForm="autoRow" itemName="autoPlanTable" class="table table-striped first-td-tc">
               <thead>
                   <tr>
                       <th class="w46">序号</th>
                       <th style="width:16%;">主要完成事项</th>
                       <th style="width:16%;">完成标准</th>
                       <th style="width:17%;">负责人</th>
                       <th style="width:120px;">开始时间</th>
                       <th style="width:120px;">完成时间</th>
                       <th style="width:100px;">比例(%)</th>
                       <th >操作</th>
                   </tr>
               </thead>
               <tbody>
               </tbody>
           </table>
       </div>
   </section>

<!-- 领导批注 start -->
<div id="leaderIdeaDiv" name="leaderIdeaDiv" style="display:none;">
	<section class="panel">
		<h4 class="modal-heading clearfix">领导批注</h4>
	    <form id="leaderIdeaReplayForm"  class="table-wrap form-table">
            <ul class="dialog m-r table-wrap" id="comment"></ul>
        </form> 
 		<form id="leaderIdeaForm" name="leaderIdeaForm" class="table-wrap form-table" style="padding-right:15px;">   
            <table class="table table-td-striped">
                <tbody>
                 	<tr>
                    <td class="w140">批注内容</td>
                    <td colspan="4">
                        <div class="input-group w-p100">
                            <div>
                                 <textarea id="leaderIdeaContent" name="content" rows="3"></textarea>
                            </div>
                            <div class="input-group-btn icon p-l">
                                <a id="leaderIdea" id="leaderIdea" href="#" class="a-icon i-new m-b-xs fr">保 存</a>
                                <a class="a-icon i-trash fr m-b-xs" href="#" id="clearleaderIdea">清 空</a>
                            </div>
                        </div>
                    </td>
                </tr>   
                </tbody>
            </table>
        </form> 
	</section>
</div>
<!-- 领导批注 end -->
<div class="form-btn">
	<a class="btn dark" id="updateAnno" onclick="toUpdatePlanAnno()">已 阅</a>
</div>
</section>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>