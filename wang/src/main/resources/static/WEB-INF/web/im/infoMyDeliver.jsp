<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/treeSelectControl.js" type="text/javascript"></script>

<script type="text/javascript">
function oTableSetButtons(mData,type,full) {
	var readBtn = "";//阅读情况按钮
//	if(full.flowStatus == 7){
	if(full.flowStatus != 1&&full.flowStatus != 0){
		//toTopBtm = "<shiro:hasPermission name='info:toTop'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"infoMyDeliver.toTop("+full.id+ ",'"+full.modifyDate+"')\" role=\"button\" data-toggle=\"modal\">置顶</a></shiro:hasPermission>";
		readBtn = "<shiro:hasPermission name='info:readState'><a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"infoMyDeliver.initReadList("+full.id+ ")\">阅读情况</a></shiro:hasPermission>";
	}
		readBtn += "<a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"infoMyDeliver.infoDetail("+full.id+ ","+full.flowStatus+")\">查看详细</a>";
	return readBtn;
};
</script>
<script src="${sysPath}/js/com/im/infoMyDeliver.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in"><div class="con-heading fl" id="navigationMenu"></div>
	</header>
	<!--start 查询条件-->
	<section class="panel clearfix search-box search-shrinkage">
		<div class="search-line hide">
	  	<form id="myInfoForm" class="table-wrap form-table">
	    	<input id="sendId" type="hidden" value="0"/>
	        <table class="table table-td-striped">
	            <tbody>
	                <tr>
	                    <td class="w140">标题</td>
	                    <td><input type="text" name="infoTitile" id="infoTitile"></td>
	                    <td class="w140">栏目</td>
	                    <td style="width:40%;"><div id="column"></div></td>
	                </tr>
	                <tr>
	                    <td>发布时间</td>
	                    <td>
	                        <div class="input-group w-p100">
	                            <input class="datepicker-input" type="text" name="sendTimeBegin" id="sendTimeBegin"  data-ref-obj="#sendTimeEnd lt" >
	                            <div class="input-group-btn w30"> -</div>
	                            <input class="datepicker-input" type="text" name="sendTimeEnd"  id="sendTimeEnd"  data-ref-obj="#sendTimeBegin gt">
	                        </div>
	                    </td>
	                    <td>流程状态</td>
	                    <td><%@ include file="/WEB-INF/web/include/workflowSearch.jsp"%></td>
	                </tr>
	                <tr>
	               <td>是否有效</td>
	               <td>
						<label class="checkbox inline"> 
							<input type="checkbox" id="isFailure_t" name="isFailure_t" value="0">是  
						</label>
						<label class="checkbox inline m-l-md"> 
							<input type="checkbox" id="isFailure_f" name="isFailure_f" value="1" >否
						</label> 
				   </td>
	               <td></td>
	               <td></td>
	           </tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark query-jump" type="button" id="myInfoQuery">查 询</button>
	            <button class="btn" type="reset" id="myInfoReset">重 置</button>
	        </section>
	    </form>
	    </div>
		<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
	</section>
	<!--end 查询条件-->
	
	<!--start 查询结果-->
	<section class="panel clearfix">
		<h2 class="panel-heading clearfix">查询结果</h2>
	    <div class="table-wrap">
	        <table class="table table-striped tab_height" id="myInfoTable">
	            <thead>
	                <tr>
	                 <th style="width:20%;">标题</th>
	                 <th>栏目</th>
	                 <th>流程状态</th>
	                 <th>发布时间</th>
	                 <th>是否有效</th>
	                 <th >操作</th>
	                </tr>
	            </thead>
	            <tbody>
	            
	            </tbody>
	        </table>
	    </div>
	</section>
	<!--end 查询结果-->
</section>

<!--  阅读情况弹窗-------->
<div class="modal fade panel" id="Reading" aria-hidden="false">
     <div class="modal-dialog w500">
         <div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal">×</button>
                 <h4 class="modal-title">阅读情况</h4>
             </div>
             <div class="modal-body">
                 <table class="table table-striped" id="readingStatusTable">
                     <thead>
                         <tr>
                            <th style="width:10%;">阅读人</th>
                            <th style="width:20%">阅读时间</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr>
                    		<td colspan="2">没有匹配的记录</td>
                    	</tr>
                    </tbody>
                </table>
            </div>
         <div class="modal-footer form-btn">
             <button type="button" class="btn dark" data-dismiss="modal">关 闭</button>
         </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>