<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/treeSelectControl.js" type="text/javascript"></script>

<script type="text/javascript">
function oTableSetButtons(mData,type,full) {
	readBtn = "<a class=\"a-icon i-new m-r-xs\" href=\"#\"  onclick=\"infoMy.infoDetail("+full.id+ ")\">查看详细</a>";
	return readBtn;
};
</script>
<script src="${sysPath}/js/com/im/infoMy.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in"><div class="con-heading fl" id="navigationMenu"></div>
	</header>
	<!--start 查询条件-->
	<section class="panel clearfix search-box search-shrinkage">
		<div class="search-line hide">
			<form id="infoMyForm" class="table-wrap form-table">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td class="w140">标题</td>
							<td><input type="text" name="infoTitile" id="infoTitile"></td>
							<td class="w140">栏目</td>
							<td style="width:40%;"><div id="column"></div></td>
						</tr>
						<tr>
							<td>发布人</td>
							<td><div id="controlTree1"></div></td>
							<td>发布部门</td>
							<td><div id="controlTree2"></div></td>
						</tr>
						<tr>
							<td>发布时间</td>
							<td>
								<div class="input-group w-p100">
									<input class="datepicker-input" type="text" name="sendTimeBegin"
										id="sendTimeBegin" data-ref-obj="#sendTimeEnd lt">
									<div class="input-group-btn w30">-</div>
									<input class="datepicker-input" type="text" name="sendTimeEnd"
										id="sendTimeEnd" data-ref-obj="#sendTimeBegin gt">
								</div>
							</td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
				<section class="form-btn m-b-lg">
					<button class="btn dark query-jump" type="button" id="infoMyQuery">查
						询</button>
					<button class="btn" type="reset" id="infoMyReset">重 置</button>
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
	        <table class="table table-striped tab_height" id="infoMyTable">
	            <thead>
	                <tr>
	                <th style="width:250px;">标题</th>
	                <th style="width:200px;">栏目</th>
	                <th style="width:90px">发布人</th>
	                <th style="width:150px">发布部门</th>
	                <th style="width:200px;">发布时间</th>
	                <th style="width:90px;">是否有效</th>
	                <th style="width:100px;">操作</th>
	                </tr>
	            </thead>
	            <tbody>
	            </tbody>
	        </table>
	    </div>
	</section>
	<!--end 查询结果-->
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
</section> 
<%@ include file="/WEB-INF/web/include/foot.jsp"%>