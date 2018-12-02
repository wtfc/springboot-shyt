<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/ic/out/outSearchForPhone.js" type="text/javascript"></script>
<script type="text/javascript">
	//根据权限判断是否显示外部人员选择树
	function getPhoneUserButtons(itemId) {
		var add = "<a class='btn btn-file no-all input-group-btn' id='"+itemId+"Div' role='button' data-toggle='modal'><i class='fa fa-users'></i></a>";
		return add;
	};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in">
	    <div class="con-heading fl" id="navigationMenu">
	       <h1></h1>
	       <div class="crumbs"></div>
	    </div>
	</header>
	<!-- start 查询条件 --> 
	<section class="panel clearfix search-box search-shrinkage">
		<input id="userPhoneName" type="hidden">
		<input id="userPhoneNum" type="hidden">
		<div class="search-line hide">
	    <form class="table-wrap form-table" id="sendForm">
	        <table class="table table-td-striped">
	            <tbody>
	                <tr>
	                    <td class="w140">内容</td>
	                    <td><input type="text" name="text" id="text"/></td>
	                    <td class="w140">收信人</td>
	                    <td>
	                    	<div id="controlTree"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="w140">类型</td>
	                    <td style="width:40%;">
	                     <dic:select name="sendType" id="sendType" dictName="sendType" headName="-全部-" headValue="" defaultValue=""/>
	                        <!-- <select name="sendType" id="sendType">
	                        </select> -->
	                    </td>
	                    <td class="w140">发送状态</td>
	                	<td>
	                		<select name="status" id="status">
	                            <option value="-1">-全部-</option>
	                            <option value="S">已发送</option>
	                            <option value="Q">排队中</option>
	                            <option value="U">未发送</option>
	                            <option value="F">失败</option>
	                        </select>
	                	</td>
	                </tr>
	                <tr>
	                	<td class="w140">电话号码</td>
	                    <td><input type="text" name="recipient" id="recipient"/></td>
	                	<td class="w140">发送时间</td>
	                    <td>
	                        <div class="input-group w-p100">
	                            <input  class="datepicker-input" type="text" name="outDateStart" id="outDateStart"  data-date-format="yyyy-MM-dd" data-ref-obj="#outDateEnd lt">
	                            <span class="input-group-btn w30">-</span>
	                            <input class="datepicker-input" type="text" name="outDateEnd" id="outDateEnd"  data-date-format="yyyy-MM-dd" data-ref-obj="#outDateStart gt">
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                	<td>
	                		外部联系人
	                	</td>
	                	<td>
	                		<span outSideUser="true" name="outIcArea" id="outIcAreaValid">
							</span>
	                	</td>
	                	<td></td>
	                	<td></td>
	                </tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark query-jump" type="button" id="querySend">查 询</button>
	            <button class="btn" type="button" id="resetSend">重 置</button>
	        </section>
	    </form>
	    </div>
	<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
	</section>  
	<!-- end 查询条件 --> 
	<!-- start 查询结果 -->
	<section class="panel clearfix">
	    <h2 class="panel-heading clearfix">
	        查询结果
	    </h2>
	    <div class="table-wrap">
	        <table class="table table-striped tab_height" id="sendTable">
	            <thead>
	                <tr>
	                    <th class="w115">收信人</th>
	                    <th >内容</th>
	                    <th style="width:15%;">类型</th>
	                    <th style="width:15%;">发送时间</th>
	                    <th style="width:15%;">发送状态</th>
	                </tr>
	            </thead>
	            <tbody>
	                
	            </tbody>
	        </table>
	    </div>
	</section>
</section>
<div id="outSearchUserTreeDiv"></div>
<!-- start 查询结果 -->
<script src="${sysPath}/js/com/ic/out/sendIc.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
